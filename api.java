import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.*;





public class api {
    public static void main(String[] args) throws Exception {
            
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:1337/api/coefficients?fields[0]=value&fields[1]=x&fields[2]=y&fields[3]=z")).build();
        client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(api::parse)
                .join();
    }

    public static String parse(String responseBody) {
        JSONArray coefficients = new JSONArray(responseBody);
        for (int i = 0; i < coefficients.length(); i++) {
            JSONObject coefficient = coefficients.getJSONObject(i);
            int x = coefficient.getInt("x");
            int y = coefficient.getInt("y");
            int z = coefficient.getInt("z");
            int value = coefficient.getInt("value");
            System.out.println(x + "," + y + "," + z + "," + value);
            
        }
        return null;
    }
}
