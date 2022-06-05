import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.*;
import java.io.*;


public class api {
    public static void main(String[] args) throws Exception {
        
        Integer x = 1;
        Integer y = 1;
        Integer z = 1;

        HttpClient client = HttpClient.newHttpClient();
        String path = "http://localhost:1337/api/coefficients";
        String query = "filters[x][$eq]=%d&filters[y][$eq]=%d&filters[z][$eq]=%d";
        String format = String.format(query, x, y, z);
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(path + "?" + format)).build();
        System.out.println(request);   
        
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
            .thenApply(HttpResponse::body)
            .thenApply(api::parse)
            .join();

        double test = 0;
        
            String charset = "UTF-8";
            URLConnection connection = new URL(path).openConnection();
            connection.setDoOutput(true); // Triggers POST.
            connection.setRequestProperty("Accept-Charset", charset);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);
            
            try (OutputStream output = connection.getOutputStream()) {
                output.write(format.getBytes(charset));
            }
            
            InputStream response = connection.getInputStream();
    }

    public static String parse(String responseBody) {
        JSONObject coefficients = new JSONObject(responseBody);
        JSONArray data = coefficients.getJSONArray("data");
        for (int i = 0; i < data.length(); i++) {
            JSONObject convert = data.getJSONObject(i);
            JSONObject attribute = convert.getJSONObject("attributes");
            Float value = attribute.getFloat("value");
            int x = attribute.getInt("x");
            int y = attribute.getInt("y");
            int z = attribute.getInt("z");
            System.out.println(x+","+y+","+z+","+value);
            if (value != null) {
                System.out.println(value);
            } 
        
            
        }
        return null;
    }

    /*public static void qs(String[] args) throws Exception {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("javascript");

        // evaluate JavaScript code
        engine.eval("print('Hello, World')");
    }*/

}

