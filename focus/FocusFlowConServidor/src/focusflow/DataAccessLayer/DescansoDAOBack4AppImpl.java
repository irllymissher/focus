package focusflow.DataAccessLayer;

import focusflow.BusinessLogicLayer.Models.Descanso;
import focusflow.BusinessLogicLayer.Services.IDescansoDAO;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Implementación real que se conecta a la API de Back4App para obtener los descansos.
 * Aquí manejamos la comunicación HTTP y la traducción de JSON a objetos Java.
 */
public class DescansoDAOBack4AppImpl implements IDescansoDAO {

    // CAMBIA "TipoDescanso" POR EL NOMBRE EXACTO DE TU TABLA EN BACK4APP
    private final String API_URL = "https://parseapi.back4app.com/classes/TipoDescanso"; 
    private final String APPLICATION_ID = "h6lniNE1cXU2UnDw92VjAtfmBUYfdsMGdxvMqpmE";
    private final String REST_API_KEY = "OkQszHeQxF9sj6iuAb3s1q8falGTBjQIp2biKdAi";

    /**
     * Descarga todos los registros de la tabla TipoDescanso.
     * @return Una lista de objetos Descanso rellenados con datos de la nube.
     */
    @Override
    public List<Descanso> obtenerTodosLosDescansos() {
        List<Descanso> listaDescansos = new ArrayList<>();
        
        try {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(API_URL)
                    .addHeader("X-Parse-Application-Id", APPLICATION_ID)
                    .addHeader("X-Parse-REST-API-Key", REST_API_KEY)
                    .get()
                    .build();

            // EL metodo eecute en Andorid da un error y bloquea la pantalla
            // TODO: se tiene que cambiar a .enqueue() para descargar lo que ocurra
            // en segundo plano
            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful() && response.body() != null) {
                    
                    // Si el servidor dice "OK", leemos el texto JSON recibido
                    String responseJson = response.body().string();
                    
                    // Back4App siempre mete los datos dentro de un objeto llamado "results"  
                    JsonObject jsonObject = new Gson().fromJson(responseJson, JsonObject.class);
                    JsonArray jsonArray = jsonObject.getAsJsonArray("results");
                    
                    // Usamos TypeToken para decirle a Java que 
                    // queremos convertir ese array de texto en 
                    // una lista de objetos "Descanso" 
                    Type listType = new TypeToken<ArrayList<Descanso>>() {}.getType();
                    listaDescansos = new Gson().fromJson(jsonArray, listType);
                    
                } else {
                    System.out.println("[ERROR] -> No se pudieron descargar los descansos: " + response.code());
                }
            }
        } catch (IOException e) {
            System.out.println("[ERROR DE RED] -> " + e.getMessage());
        }
        return listaDescansos;
    }
}