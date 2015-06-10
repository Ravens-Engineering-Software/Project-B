import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.swing.JOptionPane;
import javax.xml.bind.DatatypeConverter;


public class CryptFile {
	private String cachedPassword = null;
	private CryptEngine crypt = new CryptEngine();
	
	public void clearCache(){
		cachedPassword = null;
		crypt.setIV(null);
	}
	
	public String readEncryptedString(String filename){
		if (crypt.getIV() == null){
			String ivstr = readFile(filename+".iv");
			if (ivstr == null){
				JOptionPane.showMessageDialog(null,"iv file not found.","Error", JOptionPane.ERROR_MESSAGE);
				return null;
			}else{
				crypt.setIV(DatatypeConverter.parseBase64Binary(ivstr));

			}
		
		}
		String encryptedString = readFile(filename);
		if (encryptedString==null){return null;}
		String decryptedString = null;
		
		try{
			if (cachedPassword == null){
				boolean needInput = true;
				while(needInput){
					String input = JOptionPane.showInputDialog("Please enter your password.");
					if (input == null){
						needInput = false;
					}else if (input.length()>4){
						decryptedString = crypt.decrypt(encryptedString,input);
						if (decryptedString == null){
							JOptionPane.showMessageDialog(null,"The password you entered is incorrect.","Error", JOptionPane.ERROR_MESSAGE);
						}else{
							cachedPassword = input;
							needInput = false;
						}
					}else{
						JOptionPane.showMessageDialog(null,"The password you entered is too short.","Warning", JOptionPane.WARNING_MESSAGE);
					}
				}
			}else{
				decryptedString = crypt.decrypt(encryptedString,cachedPassword);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return decryptedString;
		
	}
	
	public boolean writeEncryptedString(String filename,String data){
		String encryptedString = null;
		try{
			if (cachedPassword == null){
				boolean needInput = true;
				while(needInput){
					String input = JOptionPane.showInputDialog("Please enter a password. Make sure you don't forget it!\nIf you do, you won't be able to recover your data ever again.");
					if (input == null){
						needInput = false;
					}else if (input.length()>4){
						encryptedString = crypt.encrypt(data,input);
						if (encryptedString == null){
							JOptionPane.showMessageDialog(null,"The password you entered is incorrect.","Error", JOptionPane.ERROR_MESSAGE);
						}else{
							cachedPassword = input;
							needInput = false;
						}
					}else{
						JOptionPane.showMessageDialog(null,"The password you entered is too short.","Warning", JOptionPane.WARNING_MESSAGE);
					}
				}
			}else{
				encryptedString = crypt.encrypt(data,cachedPassword);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//Will the user remember the IV? Nooooooo
		return writeFile(filename+".iv",DatatypeConverter.printBase64Binary(crypt.getIV())) && writeFile(filename,encryptedString);
	}
	
	
	public boolean writeFile(String filename,String data){
		if (filename==null || data==null){return false;}
		try {
			PrintWriter writer = new PrintWriter(filename, "UTF-8");
			writer.print(data);
			writer.close();
			return true;
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null,e.getMessage(),"FileNotFoundException", JOptionPane.ERROR_MESSAGE);
			return false;
			//e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			JOptionPane.showMessageDialog(null,e.getMessage(),"UnsupportedEncodingException", JOptionPane.ERROR_MESSAGE);
			return false;
			//e.printStackTrace();
		}
	}
	public String readFile(String filename)
	{
	   String content = null;
	   File file = new File(filename); //for ex foo.txt
	   try {
	       FileReader reader = new FileReader(file);
	       char[] chars = new char[(int) file.length()];
	       reader.read(chars);
	       content = new String(chars);
	       reader.close();
	   } catch (IOException e) {
	       //e.printStackTrace();
	       JOptionPane.showMessageDialog(null,e.getMessage(),"IOException", JOptionPane.ERROR_MESSAGE);
	       
	   }
	   return content;
	}
}
