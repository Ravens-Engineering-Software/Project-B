
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

    ArrayList<Double> incomeValue = new ArrayList<>();
    ArrayList<Integer> incomeFrequency = new ArrayList<>();
    ArrayList<String> incomeSource = new ArrayList<>();
    CryptFile encryptedFile = new CryptFile();
    int j = 0;
    int i;

    void fullFileReader() {
        String string = encryptedFile.readEncryptedString("incomes.txt");
        if (string == null) {
            return;
        }
        for (String a : string.split("\\.")) {
            for (String s : a.split("\\s+")) {

                if (j == 0) {
                    incomeSource.set(i, s);
                    System.out.println(incomeSource.get(i));
                } else if (j == 1) {
                    incomeValue.set(i, Double.parseDouble(s));
                    System.out.println(incomeSource.get(i));
                } else if (j == 2) {
                    incomeFrequency.set(i, Integer.parseInt(s));
                    System.out.println(incomeFrequency.get(i));
                } else {
                    System.out.println("file error");
                }
                j++;
            }
        }

    }
}
