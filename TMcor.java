	import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.*;
	


public class TMcor {

	

	public static void main(String[] args) throws FileNotFoundException {
		
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
			
			HttpClient client = HttpClient.newHttpClient();
        	String url = "http://localhost:1337/api/coefficients";
        	String query = "filters[x][$eq]=%d&filters[y][$eq]=%d&filters[z][$eq]=%d";
        	String format = String.format(query, x, y, z);
        	HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url + "?" + format)).build();

			client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
            	.thenApply(HttpResponse::body)
				.thenApply(TMcor::parse)
				.join();


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
			/*if answer is a fraction
			 * 
			int answernum= 3* zerocalc-onecalc;
			int answerdenum =(int)Math.pow(2,lastlevel-1)*3;
			
			  if(answernum==0) answerdenum=1;
			else {int gcd=BigInteger.valueOf(answernum).gcd(BigInteger.valueOf(answerdenum)).intValue();
			
				answernum=answernum/gcd;
			answerdenum=answerdenum/gcd;}
			
			outstring=outstring+""+ answernum+"/"+answerdenum+",";
			*/
			double answer=(zerocalc*1.0-onecalc*(1.0/3))/Math.pow(2,lastlevel-1);

			outstring=outstring+""+ answer+",";
			
		} System.out.println(outstring);

		}
			
		}
		
		
		
/* Code if we want user input	
 * 
 * 
 Scanner reader = new Scanner(System.in);  // Reading from System.in
		System.out.println("This program takes three integers as input. Please enter the first integer: ");
		input[1] = reader.nextInt();
		System.out.println(" Please enter the second integer: ");
		input[2] = reader.nextInt();
		System.out.println(" Please enter the third integer. This should be the largest one: ");
		input[3] = reader.nextInt();
		System.out.println("Our three integers are "+input[1]+" "+input[2]+" "+input[3]);
		reader.close();
	*/
		
		/* Code for a single triple
		 * 
		 * 
		int xyz=input[1]+input[3]-input[2];
		System.out.println ("x+z-y is equal to "+ xyz);
int[][][] output=tree(input);
int lastlevel=1+(int) Math.ceil(Math.log ((double) input[3])/Math.log(2));
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
System.out.println("The number of (0+) terms is: " + zeroplus);
System.out.println("The number of (0-) terms is: " + zerominus);
System.out.println("The number of (1+) terms is: " + oneplus);
System.out.println("The number of (1-) terms is: " + oneminus);

int zerocalc=zeroplus-zerominus;
int onecalc=oneplus-oneminus;
int answernum= 3* zerocalc-onecalc;
int answerdenum =(int)Math.pow(2,lastlevel-1)*3;
if(answernum==0) answerdenum=1;
else {int gcd=BigInteger.valueOf(answernum).gcd(BigInteger.valueOf(answerdenum)).intValue();
answernum=answernum/gcd;
answerdenum=answerdenum/gcd;
}
System.out.println("The value of the correlation is: " + answernum+"/"+answerdenum );

	}
**/	
	
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