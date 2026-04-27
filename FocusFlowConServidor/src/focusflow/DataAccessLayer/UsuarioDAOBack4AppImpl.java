package focusflow.DataAccessLayer;

import focusflow.BusinessLogicLayer.Services.IUsuarioDAO;
import focusflow.BusinessLogicLayer.Models.*;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;


import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Gestiona la conexión con la tabla "Usuario" en Back4App.
 * Se encarga de buscar usuarios existentes y registrar a los nuevos.
 */

public class UsuarioDAOBack4AppImpl implements IUsuarioDAO {

    private final String API_URL = "https://parseapi.back4app.com/classes/Usuario";
    private final String APPLICATION_ID = "h6lniNE1cXU2UnDw92VjAtfmBUYfdsMGdxvMqpmE";
    private final String REST_API_KEY = "OkQszHeQxF9sj6iuAb3s1q8falGTBjQIp2biKdAi";

    /**
     * Busca un usuario en la nube filtrando por su nombre exacto (GET).
     * @param nombre
     * @return 
     */
    @Override
    public Usuario getUsuarioPorNombre(String nombre) {
        Usuario usuarioEncontrado = null;
        try {
            /* * FILTRO DE BÚSQUEDA:
             * No descargamos todos los usuarios. Le pedimos a Back4App que busque 
             * solo el registro donde la columna "nombre" coincida con el que nos dan.
             */
            HttpUrl httpUrl = new HttpUrl.Builder()
                .scheme("https").host("parseapi.back4app.com")
                .addPathSegment("classes").addPathSegment("Usuario")
                .addQueryParameter("where", "{\"nombre\":\"" + nombre + "\"}")
                .build();
            
            Request request = new Request.Builder()
                .url(httpUrl)
                .addHeader("X-Parse-Application-Id", APPLICATION_ID)
                .addHeader("X-Parse-REST-API-Key", REST_API_KEY)
                .get().build();
            
            OkHttpClient client = new OkHttpClient();
            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful() && response.body() != null) {
                    
                    // Navegamos por el JSON para ver si hay resultados
                    JsonObject jsonObject = new Gson().fromJson(response.body().string(), JsonObject.class);
                    JsonArray jsonArray = jsonObject.getAsJsonArray("results");
                    
                    // Si el array tiene datos, es que el usuario ya existe en la nube
                    if (jsonArray.size() > 0) {
                        usuarioEncontrado = new Gson().fromJson(jsonArray.get(0), Usuario.class);
                    }
                } else {
                    System.out.println("[ERROR] -> Fallo al buscar usuario. Código: " + response.code());
                }
            }
            
        } catch (IOException e) {
            System.out.println("[ERROR DE RED] -> " + e.getMessage());
        }
        
        return usuarioEncontrado;
    }

    /**
     * Crea un nuevo registro de usuario en Back4App (POST).
     * @param usuario
     * @return 
     */
    @Override
    public String insertarUsuario(Usuario usuario) {
        String newObjectId = null;
        
        /** ESTRATEGIA DE EXCLUSIÓN:
         * Al igual que con las sesiones, ignoramos el campo "id" local para que 
         * Back4App genere su propio "objectId" oficial [cite: 1227-1244, 1037-1038].
         */
        ExclusionStrategy skipIdStrategy = new ExclusionStrategy() {
            @Override
            public boolean shouldSkipClass(Class<?> clazz) {
                return false;
            }
            @Override
            public boolean shouldSkipField(FieldAttributes fa) {
                return fa.getName().equals("id"); 
            }
        };

        Gson gson = new GsonBuilder()
                .addSerializationExclusionStrategy(skipIdStrategy)
                .create();
                
        String jsonUsuario = gson.toJson(usuario);
        
        try {
            Request request = new Request.Builder()
                .url(API_URL)
                .addHeader("X-Parse-Application-Id", APPLICATION_ID)
                .addHeader("X-Parse-REST-API-Key", REST_API_KEY)
                .post(RequestBody.create(jsonUsuario, MediaType.parse("application/json")))
                .build();
            
            OkHttpClient client = new OkHttpClient();
            
            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful() && response.body() != null) {
                    /* * CAPTURA DEL ID OFICIAL:
                     * Tras crear el usuario, Back4App nos responde con el nuevo "objectId".
                     * Lo extraemos para que el resto de la app sepa quién es este usuario .
                     */
                    JsonObject respuestaJson = new Gson().fromJson(response.body().string(), JsonObject.class);
                    newObjectId = respuestaJson.get("objectId").getAsString();
                    System.out.println("[NUBE] -> Usuario creado correctamente con ID: " + newObjectId);
                } else {
                    System.out.println("[ERROR] -> Fallo al crear usuario. Código: " + response.code());
                }
            }
            
        } catch (IOException e) {
           System.out.println("[ERROR DE RED] -> " + e.getMessage());
        }
        
        return newObjectId;
    }
}