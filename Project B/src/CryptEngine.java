import java.io.UnsupportedEncodingException;
import java.security.AlgorithmParameters;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;


public class CryptEngine {

    private static String salt;
    private static int pswdIterations = 65536  ;
    private static int keySize = 128;
    private byte[] ivBytes;
 
    public String encrypt(String plainText, String pass) throws Exception {  
         
        //get salt
        salt = generateSalt();     
        byte[] saltBytes = salt.getBytes("UTF-8");
         
        // Derive the key
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        PBEKeySpec spec = new PBEKeySpec(
        		pass.toCharArray(),
                saltBytes,
                pswdIterations,
                keySize
                );
 
        SecretKey secretKey = factory.generateSecret(spec);
        SecretKeySpec secret = new SecretKeySpec(secretKey.getEncoded(), "AES");
 
        //encrypt the message
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, secret);
        AlgorithmParameters params = cipher.getParameters();
        ivBytes = params.getParameterSpec(IvParameterSpec.class).getIV();
        byte[] encryptedTextBytes = cipher.doFinal(plainText.getBytes("UTF-8"));
        return DatatypeConverter.printBase64Binary(encryptedTextBytes);
    }
 
    @SuppressWarnings("static-access")
    public String decrypt(String encryptedText,String pass) throws Exception {
 
        byte[] saltBytes = salt.getBytes("UTF-8");
        byte[] encryptedTextBytes = DatatypeConverter.parseBase64Binary(encryptedText);
 
        // Derive the key
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        PBEKeySpec spec = new PBEKeySpec(
        		pass.toCharArray(),
                saltBytes,
                pswdIterations,
                keySize
                );
 
        SecretKey secretKey = factory.generateSecret(spec);
        SecretKeySpec secret = new SecretKeySpec(secretKey.getEncoded(), "AES");
 
        // Decrypt the message
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(ivBytes));
     
 
        byte[] decryptedTextBytes = null;
        try {
            decryptedTextBytes = cipher.doFinal(encryptedTextBytes);
            return new String(decryptedTextBytes);
        } catch (IllegalBlockSizeException e) {
            //e.printStackTrace();
            return null;
        } catch (BadPaddingException e) {
            //e.printStackTrace();
            return null;
        }
 
       
    }
 
    public String generateSalt() throws UnsupportedEncodingException, NoSuchAlgorithmException{ //The things are pre-set anyway, why does eclipse complain??
    	Properties sysinfo = System.getProperties();
    	String uniqueStuffs = sysinfo.getProperty("os.arch") + sysinfo.getProperty("os.name") + sysinfo.getProperty("os.version") + sysinfo.getProperty("user.name") + sysinfo.getProperty("user.home");
    	byte[] bytesOfMessage = uniqueStuffs.getBytes("UTF-8");

    	MessageDigest md = MessageDigest.getInstance("MD5");
    	byte[] thedigest = md.digest(bytesOfMessage);
    	return new String(thedigest);
    }
}
