//===============Importing Libraries==============


import java.io.*;
import java.io.File;
import java.util.Scanner;
import org.json.simple.parser.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.io.FileNotFoundException;
import jxl.Workbook;
import jxl.write.Label; 
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;



//===============================================


 class exec 
{
  void setresponse(String symbol)
  {
       try
        {  
         
         //String symbol = "EIHOTEL";
         String nse_api = "curl -k \"https://www.nseindia.com/api/quote-equity?symbol=" + symbol + "\" -H \"authority: beta.nseindia.com\" -H \"cache-control: max-age=0\" -H \"dnt: 1\" -H \"upgrade-insecure-requests: 1\" -H \"user-agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.117 Safari/537.36\" -H \"sec-fetch-user: ?1\" -H \"accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9\" -H \"sec-fetch-site: none\" -H \"sec-fetch-mode: navigate\" -H \"accept-encoding: gzip, deflate, br\" -H \"accept-language: en-US,en;q=0.9,hi;q=0.8\" --compressed  -o sardard.txt";
         Runtime.getRuntime().exec("cmd /c start cmd.exe /K \" " + nse_api + "&& timeout 3 && exit\"");         
         
         Thread.sleep(5000);
         
        } 
        catch (Exception e) 
        { 
            System.out.println("HEY Buddy ! U r Doing Something Wrong "); 
            e.printStackTrace(); 
        } 
      
      
  }
  
  void reader(int itr) throws IOException, FileNotFoundException 
  {
          JSONParser parser = new JSONParser();
          
          try 
          {
                File file = new File("C:\\Users\\aruna\\Documents\\NetBeansProjects\\StockerClient\\sardard.json");
                Scanner sc = new Scanner(file);  
                 /*while (sc.hasNextLine()) 
                    System.out.println(sc.nextLine());*/
                InputStream is = new FileInputStream("sardard.txt");
                BufferedReader buf = new BufferedReader(new InputStreamReader(is));
                String line = buf.readLine();
                StringBuilder sb = new StringBuilder();
                
                while(line!= null)
                {
                    sb.append(line).append("{");
                    line = buf.readLine();
                }
                
                String fileAsString = sb.toString();
                
                
                 //==============FIND LIVE STOCK VALUE=================
                 String patternString1 = "(\"lastPrice\"):(.+?),(.+?)";
                 Pattern pattern = Pattern.compile(patternString1);
                 Matcher matcher = pattern.matcher(fileAsString);
                 
                 //=============FIND COMPANY NAME===================
                 String patternString2 = "(\"symbol\"):(.+?),(.+?)";
                 Pattern pattern2 = Pattern.compile(patternString2);
                 Matcher matcher2 = pattern2.matcher(fileAsString);
                 
                 //============FIND LAST CLOSE VALUE================
                 String patternString3 = "(\"close\"):(.+?),(.+?)";
                 Pattern pattern3 = Pattern.compile(patternString3);
                 Matcher matcher3 = pattern3.matcher(fileAsString);
                 
                 
                 
                 //System.out.println("Filtered Out value");
                 String priceinfo = "";
                 String priceinfo2 = "";
                 String priceinfo3 = "";
                 while(matcher.find()) 
                        {
                          ///System.out.println("found: " + matcher.group(1)+" " + matcher.group(2));
                          priceinfo = matcher.group(2);
                        }
                 
                 
                 while(matcher2.find()) 
                        {
                          //System.out.println("found: " + matcher2.group(1)+" " + matcher2.group(2));
                          priceinfo2 = matcher2.group(2);
                        }
                 
                  while(matcher3.find()) 
                        {
                          ///System.out.println("found: " + matcher.group(1)+" " + matcher.group(2));
                          priceinfo3 = matcher3.group(2);
                        }
                 
                 
                 priceinfo2 = priceinfo2.replace("\"","");
                 //System.out.println("Current Live Stock Value for " + priceinfo2 + " is " + priceinfo);
                 //System.out.println("Final Close value for today is: " + priceinfo3);
                 //System.out.println();
                 
                 //===============Writing to Text File==================
                 Workbook wb = Workbook.getWorkbook(new File("OTF.xls"));
                 WritableWorkbook copy = Workbook.createWorkbook(new File("OTF.xls"),wb);
                 WritableSheet copySheet = copy.getSheet(0);
                 Label label1 = new Label (0,itr+1,priceinfo2);
                 Label label2 = new Label (1,itr+1,priceinfo);
                 Label label3 = new Label (2,itr+1,priceinfo3);
                 copySheet.addCell(label1);
                 copySheet.addCell(label2);
                 copySheet.addCell(label3);
                 copy.write();
                 copy.close();
                 
                                              
          }
          catch (Exception e) 
                  {
                      System.out.println("HEY Buddy ! U r Doing Something Wrong "); 
                      e.printStackTrace();
                  }
  }
}

public class Main1 {

    
    public static void main(String[] args) throws Exception
    {
        exec e1 = new exec();
        String[] symbol_list = {"EIHOTEL","HLVLTD","VSTIND"};
        int i;
        //symbol_list.length
        for(i=0;i<symbol_list.length;i++)
        {
           System.out.println("Running Stocker-Client for " + symbol_list[i] + ".....");
           e1.setresponse(symbol_list[i]);
           e1.reader(i); 
        }
        
    }
    
}