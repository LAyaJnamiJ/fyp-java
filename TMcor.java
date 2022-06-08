import java.util.Scanner;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.json.*;

import java.io.IOException;

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

public class TMcor {

	

	public static void main(String[] args) throws Exception {
		
		Scanner Obj = new Scanner(System.in);
		System.out.println("How large of an art do you want to create?");
		int z=Obj.nextInt()-1;
		Obj.close();

		String path="C:/Users/User/Desktop/Maths/FYP/csv/test.csv";

		System.setOut(new PrintStream(new FileOutputStream(path)));

		for(int x=0;x<1+z;x++)
		{		String outstring="";
		
			for(int y=0;y<1+z;y++)
		{	
			int[] loopinput= new int[4];
			loopinput[0]=1;
			loopinput[1]=x;
			loopinput[2]=y;
			loopinput[3]=z;
			
			try (final CloseableHttpClient httpclient = HttpClients.createDefault()) {
	
				String url = "http://localhost:1337/api/coefficients";
				String query = "filters[x][$eq]=%d&filters[y][$eq]=%d&filters[z][$eq]=%d";
				String format = String.format(query, x, y, z);
				final HttpGet httpGet = new HttpGet(url + "?" + format);

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
			
				JSONObject coefficients = new JSONObject(responseBody);
				JSONArray data = coefficients.getJSONArray("data");
				
				try {
					JSONObject convert = data.getJSONObject(0); 
					JSONObject attribute = convert.getJSONObject("attributes");
					Float value = attribute.getFloat("value");
					if (value != null) {
						System.out.println(value + ",");
					}
				}
				

				catch (Exception e) {
			int[][][] output=tree(loopinput);
			int lastlevel=1+(int) Math.ceil(Math.log ((double) z)/Math.log(2));
			int oneplus=0;
			int oneminus=0;
			int zeroplus=0;
			int zerominus=0;
			for(int i=0;i<Math.pow(2,lastlevel-1);i++) {
				int sum =output[1][i][lastlevel-1]+output[2][i][lastlevel-1]+output[3][i][lastlevel-1];
			if(sum%2==0 && output[0][i][lastlevel-1]==1) zeroplus=zeroplus+1;
			if(sum%2==0 && output[0][i][lastlevel-1]==-1) zerominus=zerominus+1;
			if(sum%2==1 && output[0][i][lastlevel-1]==1) oneplus=oneplus+1;
			if(sum%2==1 && output[0][i][lastlevel-1]==-1) oneminus=oneminus+1;
			}
			int zerocalc=zeroplus-zerominus;
			int onecalc=oneplus-oneminus;
			double answer=(zerocalc*1.0-onecalc*(1.0/3))/Math.pow(2,lastlevel-1);

			outstring=outstring+""+ answer+",";
			
			/*final HttpPost httpPost = new HttpPost(url);
            final List<NameValuePair> nvps = new ArrayList<>();
            nvps.add(new BasicNameValuePair("x", "x"));
            nvps.add(new BasicNameValuePair("y", "y"));
            nvps.add(new BasicNameValuePair("z", "z"));
            nvps.add(new BasicNameValuePair("value", "answer"));
            httpPost.setEntity(new UrlEncodedFormEntity(nvps));

            try (final CloseableHttpResponse response2 = httpclient.execute(httpPost)) {
                System.out.println(response2.getCode() + " " + response2.getReasonPhrase());
                final HttpEntity entity2 = response2.getEntity();
                System.out.println(response2);
                EntityUtils.consume(entity2);
			
				}*/
			}

			}
	
		}
		System.out.println(outstring);

	}
			
}
		
	
	public static int[][] recursion (int[] start) {
		
		int[][] placeholder= new int[4][2];
placeholder[1][0]=(int) Math.floor(((float) start[1])/2);		
placeholder[2][0]=(int) Math.floor(((float) start[2])/2);
placeholder[3][0]=(int) Math.floor(((float) start[3])/2);	
placeholder[1][1]=(int) Math.ceil(((float) start[1])/2);		
placeholder[2][1]=(int) Math.ceil(((float) start[2])/2);
placeholder[3][1]=(int) Math.ceil(((float) start[3])/2);	
		
placeholder[0][0]=start[0];
if(placeholder[1][0]!=placeholder[1][1]) placeholder[0][0]=placeholder[0][0]*(-1);
if(placeholder[2][0]!=placeholder[2][1]) placeholder[0][0]=placeholder[0][0]*(-1);
if(placeholder[3][0]!=placeholder[3][1]) placeholder[0][0]=placeholder[0][0]*(-1);

placeholder[0][1]=placeholder[0][0];
		return placeholder;
	}
	
	//The output of tree is [sign,x,y,z][horizontal level][vertical level]
	public static int[][][] tree (int[] root){
		int levels=1+(int) Math.ceil(Math.log ((double) root[3])/Math.log(2));
		int[][][] x=new int[4][(int) Math.pow(2,levels-1)][levels];
		for(int i=0;i<4;i++) x[i][0][0]=root[i];
		for(int ell=1; ell<levels;ell++) {
		int horz=(int)Math.pow(2,ell);
		for(int j=0;j<horz/2;j++) {
			int[] temp1= new int[4];
			int[][] temp2= new int[4][2];
			for(int i=0;i<4;i++)temp1[i]=x[i][j][ell-1];
			temp2=recursion(temp1);
			for(int i=0;i<4;i++) {
		x[i][2*j][ell]=temp2[i][0];
		x[i][2*j+1][ell]=temp2[i][1];
			}
			}
		}
		
		
		return x;
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
                System.out.println(value+",");
            } 
        
            
        }
        return null;
    }
}