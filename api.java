/*import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.*;
import java.io.*;
import java.util.concurrent.*;
import org.apache.hc.client5.*;
import org.apache.hc.core5.*;


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
        
        HttpPost httppost = new HttpPost("http://www.a-domain.com/foo/");

        // Request parameters and other properties.
        List<NameValuePair> params = new ArrayList<NameValuePair>(2);
        params.add(new BasicNameValuePair("param-1", "12345"));
        params.add(new BasicNameValuePair("param-2", "Hello!"));
        httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
        
        //Execute and get the response.
        HttpResponse response = httpclient.execute(httppost);
        HttpEntity entity = response.getEntity();
        
        if (entity != null) {
            try (InputStream instream = entity.getContent()) {
                // do something useful
            }
        }
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

    public static void qs(String[] args) throws Exception {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("javascript");

        // evaluate JavaScript code
        engine.eval("print('Hello, World')");
    }

}


 * ====================================================================
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.*;

import org.apache.hc.client5.http.ClientProtocolException;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ClassicHttpResponse;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.HttpClientResponseHandler;
import org.apache.hc.core5.http.io.entity.EntityUtils;

import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.NameValuePair;
import org.apache.hc.core5.http.message.BasicNameValuePair;

public class api {

    public static void main(final String[] args) throws Exception {
        try (final CloseableHttpClient httpclient = HttpClients.createDefault()) {
            Integer x = 0;
            Integer y = 0;
            Integer z = 9;    

            String path = "http://localhost:1337/api/coefficients";
            String query = "filters[x][$eq]=%d&filters[y][$eq]=%d&filters[z][$eq]=%d";
            String format = String.format(query, x, y, z);
            final HttpGet httpGet = new HttpGet(path + "?" + format);
            
            final HttpClientResponseHandler<String> responseHandler = new HttpClientResponseHandler<String>() {

                @Override
                public String handleResponse(
                        final ClassicHttpResponse response) throws IOException {
                    final int status = response.getCode();
                    if (status >= HttpStatus.SC_SUCCESS && status < HttpStatus.SC_REDIRECTION) {
                        final HttpEntity entity = response.getEntity();
                        try {
                            return entity != null ? EntityUtils.toString(entity) : null;
                        } catch (final ParseException ex) {
                            throw new ClientProtocolException(ex);
                        }
                    } else {
                        throw new ClientProtocolException("Unexpected response status: " + status);
                    }
                }

            };
            final String responseBody = httpclient.execute(httpGet, responseHandler);
            System.out.println("----------------------------------------");
            System.out.println(responseBody);
        
            JSONObject coefficients = new JSONObject(responseBody);
            JSONArray data = coefficients.getJSONArray("data");
            
                JSONObject convert = data.getJSONObject(0);
                JSONObject attribute = convert.getJSONObject("attributes");
                Float value = attribute.getFloat("value");
                int m = attribute.getInt("x");
                int k = attribute.getInt("y");
                int h = attribute.getInt("z");
                System.out.println(m+","+k+","+h+","+value);
                if (value != null) {
                    System.out.println(value);
            }

            final HttpPost httpPost = new HttpPost(path);
            final List<NameValuePair> nvps = new ArrayList<>();
            nvps.add(new BasicNameValuePair("x", "1"));
            nvps.add(new BasicNameValuePair("y", "2"));
            nvps.add(new BasicNameValuePair("z", "3"));
            nvps.add(new BasicNameValuePair("value", "0"));
            httpPost.setEntity(new UrlEncodedFormEntity(nvps));

            try (final CloseableHttpResponse response2 = httpclient.execute(httpPost)) {
                System.out.println(response2.getCode() + " " + response2.getReasonPhrase());
                final HttpEntity entity2 = response2.getEntity();
                System.out.println(response2);
                EntityUtils.consume(entity2);
            }
        }
    }

}*/


import javax.script.*;
import jdk.nashorn.api.scripting.NashornScriptEngineFactory;

public class api {
    public static void main(String[] args) throws Exception {
        NashornScriptEngineFactory factory = new NashornScriptEngineFactory();
        ScriptEngine engine = factory.getScriptEngine("application/javascript");

        // evaluate JavaScript code
        engine.eval(new java.io.FileReader("script.js"));
    }
}