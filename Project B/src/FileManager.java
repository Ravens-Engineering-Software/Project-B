
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Jacob Larose
 */
public class FileManager {
    //Create variables for file management
    String fileSeperator = System.getProperty("file.separator"); 
    String relativePath = "src" + fileSeperator + "resorces" + fileSeperator + "incomesList.txt"; 
    File income = new File(relativePath);
    //Create variables for file reading
    FileInputStream fis = null;
    BufferedInputStream bis = null;
    DataInputStream dis = null;
    ArrayList<Double> incomeValue= new ArrayList<>();
    ArrayList<Integer> incomeFrequency =new ArrayList<>();
    ArrayList<String> incomeSource= new ArrayList<>();
    StringBuilder encryptedStringBuilder = new StringBuilder();
    
    void fullFileReader() {
        try {
            fis = new FileInputStream(income);

            // Here BufferedInputStream is added for fast reading.
            bis = new BufferedInputStream(fis);
            dis = new DataInputStream(bis);

            // dis.available() returns 0 if the file does not have more lines.
            int i = 0;
            while (dis.available() != 0) {
                String line = dis.readLine();
                int j = 0;
                /*
                encryptedStringBuilder.append(line);
                     
                String encryptedFile = encryptedStringBuilder.toString();
                */
                for (String s : line.split("\\s+")) {
                    
                    
                    if (j == 0) {
                        incomeSource.set(i, s);
                        System.out.println(incomeSource.get(i));
                    } else if (j == 1) {
                        incomeValue.set(i, Double.parseDouble(s));
                        System.out.println(incomeSource.get(i));
                    }else if (j == 2){
                        incomeFrequency.set(i, Integer.parseInt(s));
                        System.out.println(incomeFrequency.get(i));
                    } else {
                        System.out.println("file error");
                    }
                    j++;
                }
                i++;
                System.out.println();
            }

            // dispose all the resources after using them.
            fis.close();
            bis.close();
            dis.close();

            System.out.println("file read succesfully.");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("file not found.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("errorrororor.");
        }catch (NumberFormatException e){
            e.printStackTrace();
            System.out.println("errorrororor. number read");
        }
    }
}
