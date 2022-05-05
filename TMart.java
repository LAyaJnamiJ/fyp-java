import java.awt.image.BufferedImage;
import java.util.*;

import javax.imageio.ImageIO;

import java.lang.*;
import java.io.*;
 
/* Name of the class has to be "Main" only if the class is public. */
public class TMart
{
	public static void main (String[] args) throws java.lang.Exception
	{
		
		
		   String csvFile = "C:/Users/User/Desktop/Maths/FYP/csv/1200.csv";
	        BufferedReader br = null;
	        String line = "";
	        String cvsSplitBy = ",";

	        BufferedReader brcount = new BufferedReader(new FileReader(csvFile));
	        int count = 0;
	        while(brcount.readLine() != null)
	        {
	            count++;
	        }
			brcount.close();
	  
	        double[][] DoubleMatrix= new double[count][count];
	        try {
	        	int row= 0; 
	            br = new BufferedReader(new FileReader(csvFile));
	            while ((line = br.readLine()) != null) {

	                // use comma as separator
	                String[] country = line.split(cvsSplitBy);

	                for(int i=0;i<count;i++) DoubleMatrix[row][i]=Double.parseDouble(country[i]);
	                row=row+1;
	            }

	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            if (br != null) {
	                try {
	                    br.close();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	        }
	

        int[][] r = new int[count][count];
        int[][] g = new int[count][count];
        int[][] b = new int[count][count];
        
        for(int j=0; j<count;j++) 
        {
        	for (int k=0;k<count;k++) {
        		
        		if(DoubleMatrix[j][k]>0.008 && DoubleMatrix[j][k]<=0.01) {
        			r[j][k]=0xFF;
        			g[j][k]=0x00;
        			b[j][k]=0x00;
        			}
        		else if(DoubleMatrix[j][k]<-0.008&& DoubleMatrix[j][k]>=-0.01) {
        			r[j][k]=0xFF;
        			g[j][k]=0xFF;
        			b[j][k]=0x00;
        			}
        		else if(DoubleMatrix[j][k]>0.01 && DoubleMatrix[j][k]<=0.025) {
        			r[j][k]=0x00;
        			g[j][k]=0xFF;
        			b[j][k]=0x00;
        		}
        		else if(DoubleMatrix[j][k]<-0.01 && DoubleMatrix[j][k]>=-0.025) {
        			r[j][k]=0x00;
        			g[j][k]=0x00;
        			b[j][k]=0xFF;
        		}
        		else if(DoubleMatrix[j][k]>0.025 && DoubleMatrix[j][k]<=0.05) {
        			r[j][k]=0x00;
        			g[j][k]=0xFF;
        			b[j][k]=0xFF;
        		}
        		else if(DoubleMatrix[j][k]<-0.025 && DoubleMatrix[j][k]>-0.05) {
        			r[j][k]=0xFF;
        			g[j][k]=0xC7;
        			b[j][k]=0x50;
        		}
        		else if(DoubleMatrix[j][k]>0 && DoubleMatrix[j][k]<=0.008) {
        			r[j][k]=0x83;
        			g[j][k]=0xFE;
        			b[j][k]=0x93;
        		}
        		else if(DoubleMatrix[j][k]<0 && DoubleMatrix[j][k]>=-0.008) {
        			r[j][k]=0x56;
        			g[j][k]=0xBE;
        			b[j][k]=0xE9;
        		}
        		else if(DoubleMatrix[j][k]<-0.05&& DoubleMatrix[j][k]>=-0.1) {
        			r[j][k]=0x6C;
        			g[j][k]=0x89;
        			b[j][k]=0xEE;
        			}
        		
        		else if(DoubleMatrix[j][k]>0.05 && DoubleMatrix[j][k]<=0.1) {
        			r[j][k]=0x84;
        			g[j][k]=0xFF;
        			b[j][k]=0x00;
        			}
        		else if(DoubleMatrix[j][k]<-0.1) {
        			r[j][k]=0xE8;
        			g[j][k]=0xBA;
        			b[j][k]=0x00;
        			}
        		else if(DoubleMatrix[j][k]>0.1) {
        			r[j][k]=0xFF;
        			g[j][k]=0x76;
        			b[j][k]=0x5D;
        			}
        		else {
        			r[j][k]=0x00;
        			g[j][k]=0x00;
        			b[j][k]=0x00;
        			}
        		
        		/*
        		if(DoubleMatrix[j][k]>0.008 && DoubleMatrix[j][k]<=0.01) {
        			r[j][k]=0x48;
        			g[j][k]=0xC8;
        			b[j][k]=0xEF;
        			}
        		else if(DoubleMatrix[j][k]<-0.008&& DoubleMatrix[j][k]>=-0.01) {
        			r[j][k]=0x6D;
        			g[j][k]=0x00;
        			b[j][k]=0xFF;
        			}
        		else if(DoubleMatrix[j][k]>0.01 && DoubleMatrix[j][k]<=0.025) {
        			r[j][k]=0xC3;
        			g[j][k]=0x2E;
        			b[j][k]=0xD4;
        		}
        		else if(DoubleMatrix[j][k]<-0.01 && DoubleMatrix[j][k]>=-0.025) {
        			r[j][k]=0xAC;
        			g[j][k]=0x06;
        			b[j][k]=0xFE;
        		}
        		else if(DoubleMatrix[j][k]>0.025 && DoubleMatrix[j][k]<=0.05) {
        			r[j][k]=0x49;
        			g[j][k]=0xE1;
        			b[j][k]=0xC2;
        		}
        		else if(DoubleMatrix[j][k]<-0.025 && DoubleMatrix[j][k]>-0.05) {
        			r[j][k]=0x68;
        			g[j][k]=0xEA;
        			b[j][k]=0xB4;
        		}
        		else if(DoubleMatrix[j][k]>0 && DoubleMatrix[j][k]<=0.008) {
        			r[j][k]=0x15;
        			g[j][k]=0x74;
        			b[j][k]=0x8A;
        		}
        		else if(DoubleMatrix[j][k]<0 && DoubleMatrix[j][k]>=-0.008) {
        			r[j][k]=0x4C;
        			g[j][k]=0x5F;
        			b[j][k]=0x72;
        		}
        		else if(DoubleMatrix[j][k]<-0.05&& DoubleMatrix[j][k]>=-0.1) {
        			r[j][k]=0x17;
        			g[j][k]=0x33;
        			b[j][k]=0xAB;
        			}
        		
        		else if(DoubleMatrix[j][k]>0.05 && DoubleMatrix[j][k]<=0.1) {
        			r[j][k]=0x9A;
        			g[j][k]=0x53;
        			b[j][k]=0xD0;
        			}
        		else if(DoubleMatrix[j][k]<-0.1) {
        			r[j][k]=0x1E;
        			g[j][k]=0xD0;
        			b[j][k]=0xAF;
        			}
        		else if(DoubleMatrix[j][k]>0.1) {
        			r[j][k]=0x98;
        			g[j][k]=0x8B;
        			b[j][k]=0xD8;
        			}
        		else {
        			r[j][k]=0x00;
        			g[j][k]=0x00;
        			b[j][k]=0x00;
        			}
        		*/
        	}
        	
        	
        }
        
        int width = count;
        int height = count;
 
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB); 
 
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = r[y][x];
                rgb = (rgb << 8) + g[y][x]; 
                rgb = (rgb << 8) + b[y][x];
                image.setRGB(x, y, rgb);
            }
        }
 
  
        
        // Uncomment  to save the image 
        File outputFile = new File("output.bmp");
         ImageIO.write(image, "bmp", outputFile);
    }
}