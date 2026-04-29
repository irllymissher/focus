package focusflow.DataAccessLayer;

import focusflow.BusinessLogicLayer.Models.SesionEstudio;
import focusflow.BusinessLogicLayer.Services.ISesionDAO;

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
 * Se encarga de guardar las sesiones de estudio en la nube y de 
 * recuperar el historial personal de cada usuario.
 */
public class SesionDAOBack4AppImpl implements ISesionDAO{
    
    private final String API_URL = "https://parseapi.back4app.com/classes/SesionEstudio";
    private final String APPLICATION_ID = "h6lniNE1cXU2UnDw92VjAtfmBUYfdsMGdxvMqpmE";
    private final String REST_API_KEY = "OkQszHeQxF9sj6iuAb3s1q8falGTBjQIp2biKdAi";

    /**
     * Envía una nueva sesión de estudio al servidor (POST).
     * @param sesion 
     */
    @Override
    public void guardarSesion(SesionEstudio sesion){
        
        /** ESTRATEGIA DE EXCLUSIÓN:
         * Back4App genera automáticamente su propio "objectId". Si le enviamos 
         * nuestro campo "id" de Java (que suele ser un UUID temporal), daría error.
         * Por eso, le decimos a GSON que ignore el campo "id" al crear el JSON.
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
        
        // Creamos el JSON ignorando el ID local
        Gson gson = new GsonBuilder()
                .addSerializationExclusionStrategy(skipIdStrategy)
                .create();
        
        String jsonBody = gson.toJson(sesion);

        try {
            OkHttpClient client = new OkHttpClient();
            RequestBody body = RequestBody.create(jsonBody, MediaType.parse("application/json"));
            
            Request request = new Request.Builder()
                    .url(API_URL)
                    .addHeader("X-Parse-Application-Id", APPLICATION_ID)
                    .addHeader("X-Parse-REST-API-Key", REST_API_KEY)
                    .addHeader("Content-Type", "application/json")
                    .post(body) // Usamos POST para crear un registro nuevo
                    .build();
            
            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    System.out.println("[NUBE] -> Sesion guardada correctamente en Back4App.");
                } else {
                    System.out.println("[ERROR] -> Fallo al guardar en la nube. Codigo: " + response.code());
                }
            }

        } catch (IOException e) {
            System.out.println("[ERROR DE RED] -> " + e.getMessage());
        }
    }

    /**
     * Descarga el historial de sesiones filtrado por el usuario actual (GET).
     * @param usuarioId
     * @return 
     */
    @Override
    public List<SesionEstudio> listarSesiones(String usuarioId){
        List<SesionEstudio> historial = new java.util.ArrayList<>();
        
        try {
            /** FILTRO DE PRIVACIDAD:
             * No queremos descargar TODAS las sesiones de la base de datos.
             * Usamos el parámetro "where" para decirle al servidor: 
             * "Dame solo las sesiones cuyo usuarioId coincida con este" .
             */
            okhttp3.HttpUrl httpUrl = new okhttp3.HttpUrl.Builder()
                .scheme("https").host("parseapi.back4app.com")
                .addPathSegment("classes").addPathSegment("SesionEstudio")
                .addQueryParameter("where", "{\"usuarioId\":\"" + usuarioId + "\"}")
                .build();
            
            OkHttpClient client = new OkHttpClient();
            
            Request request = new Request.Builder()
                    .url(httpUrl)
                    .addHeader("X-Parse-Application-Id", APPLICATION_ID)
                    .addHeader("X-Parse-REST-API-Key", REST_API_KEY)
                    .get()
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful() && response.body() != null) {
                    String responseJson = response.body().string();
                    
                    // Navegamos por el JSON para llegar al array de resultados
                    JsonObject jsonObject = new Gson().fromJson(responseJson, JsonObject.class);
                    JsonArray jsonArray = jsonObject.getAsJsonArray("results");
                    
                    // Convertimos el array de JSON en nuestra lista de SesionEstudio
                    Type listType = new TypeToken<ArrayList<SesionEstudio>>() {}.getType();
                    historial = new Gson().fromJson(jsonArray, listType);
                    
                } else {
                    System.out.println("[ERROR] -> No se pudo descargar el historial: " + response.code());
                }
            }
        } catch (IOException e) {
            System.out.println("[ERROR DE RED] -> " + e.getMessage());
        }
        return historial;
    }
    
}
