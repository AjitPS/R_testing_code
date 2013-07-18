package rintegration;

import java.util.Random;
import rcaller.*;

public class Test3 {
    
    public Test3(){
        try
           {
            // Creating Java's random number generator
            Random random = new Random();
            
            // Creating RCaller & RCode.
            RCaller caller = new RCaller();
            RCode rCode= new RCode();
           
            // Full path of the Rscript. Rscript is an executable file shipped with R.
            // It is something like C:\\Program File\\R\\bin.... in Windows             
//            caller.setRscriptExecutable("/usr/bin/Rscript");
            caller.setRscriptExecutable("C:\\Program Files\\R\\R-3.0.1\\bin\\Rscript.exe");
            // ^^^Note: our Rscript is at: "C:\Program Files\R\R-3.0.1\bin\Rscript.exe".
            
            // We are creating a random data array of size 100 from a normal distribution with zero mean & unit variance.
            double[] data = new double[100];
            for (int i=0;i<data.length;i++) {
                 data[i] = random.nextGaussian();
                }
            
/*            
            // Display all the values for user's reference.
            for (int j=0;j<data.length;j++) {
                 System.out.print(data[j] + " , ");
                 if(j %5 ==0)
                   {
                    System.out.print("\n");
                   }
                }
*/            

// In RCaller v.3 & higher, we have to manually set the RCode to the RCaller.
//    RCode rc= new RCode();
//    rCode.addRCode("my.mean<-mean(x)");
//    rcaller.setRCode(rc);
            // Set the RCode to the RCaller (i.e, the Java-to-R harness).
            caller.setRCode(rCode);
            // Transferring the double array with 100 random numbers to R.
            rCode.addDoubleArray("x", data);            
            // Adding R Code.
            rCode.addRCode("my.mean<-mean(x)");
            rCode.addRCode("my.var<-var(x)"); // variance.
            rCode.addRCode("my.sd<-sd(x)"); // SD
            rCode.addRCode("my.min<-min(x)");
            rCode.addRCode("my.max<-max(x)");
            rCode.addRCode("my.standardized<-scale(x)");
            // Combining all of them in a single list() object
            rCode.addRCode("my.all<-list(mean=my.mean, variance=my.var, sd=my.sd, min=my.min, max=my.max, "
                    + "std=my.standardized)");
            
            // We want to handle the list 'my.all'.             
            caller.runAndReturnResult("my.all");
            
            // Retrieving the returned double[] Array & the 'mean' element of the returned list:'my.all'.
            double[] results = caller.getParser().getAsDoubleArray("mean");
            System.out.println("\n \n Mean = "+results[0]);

            // Retrieving the 'variance' element of list 'my.all'.
            results = caller.getParser().getAsDoubleArray("variance");
            System.out.println("\n Variance = "+results[0]);

            // Retrieving the 'sd' element of list 'my.all'.
            results = caller.getParser().getAsDoubleArray("sd");
            System.out.println("\n Standard Deviation is = "+results[0]);
            
            // Retrieving the 'min' element of list 'my.all'.
            results = caller.getParser().getAsDoubleArray("min");
            System.out.println("\n Minimum = "+results[0]);
            
            // Retrieving the 'max' element of list 'my.all'.
            results = caller.getParser().getAsDoubleArray("max");
            System.out.println("\n Maximum = "+results[0]);
            
            // Retrieving the 'std' element of list 'my.all'.
            results = caller.getParser().getAsDoubleArray("std");
            
            // Now we are retrieving the standardized form of vector 'x'.
            System.out.println("\n Standardized 'x' :-");            
            for (int i=0;i<results.length;i++) 
                { System.out.print(results[i]+", "); }
            
            System.out.print("\n");

// test...
Globals.detect_current_rscript();
System.out.println("\t RCaller.getCranRepository() : "+ caller.getCranRepos());
System.out.println("\n \t Globals.Current Rscript: "+ Globals.Rscript_current + " \t & Globals.Windows Rscript: " + 
       Globals.RScript_Windows);
System.out.println("\t Globals.Windows 'R': "+ Globals.R_Windows + " \t & Globals.Current 'R': "+ Globals.R_current);
System.out.println("\n \t Globals.Linux Rscript: "+ Globals.RScript_Linux + " \t & Globals.Linux 'R': "+ Globals.R_Linux);
//RscriptExecutable rx= new RscriptExecutable();

           }
        
        catch(Exception e){
              System.out.println(e.toString());
             }
        
    }
    
    public static void main(String[] args){
        new Test3();
    }
    
}
