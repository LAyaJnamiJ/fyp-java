import java.io.*;
import java.net.*;
import java.util.Scanner;


public class api {
    public static void main(String[] args) throws Exception {
            
        String url = "http://localhost:1337/api/coefficients";
        String charset = "java.nio.charset.StandardCharsets.UTF_8.name()";
        String param1 = "xyz";
        String param2 = "value";

        String query = String.format("param1=%s&param2=%s",
            URLEncoder.encode(param1, charset),
            URLEncoder.encode(param2, charset));
        
        URLConnection connection = new URL(url + "?" + query).openConnection();
        connection.setRequestProperty("Accept-Charset", charset);
        InputStream response = connection.getInputStream();

        try (Scanner scanner = new Scanner(response)) {
            String responseBody = scanner.useDelimiter("\\A").next();
            System.out.println(responseBody);
        }
    }
}
