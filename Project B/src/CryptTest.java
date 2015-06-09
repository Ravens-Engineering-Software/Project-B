
// Testing purposes only
public class CryptTest {

    public static void main(String[] args) throws Exception {
        // TODO Auto-generated method stub
 
        CryptFile d = new CryptFile();
        
        boolean didWork = d.writeEncryptedString("test.txt", "Hello, world!");
        if (didWork){
        	System.out.println("Encryped!");
        	d.clearCache();
        	String decoded = d.readEncryptedString("test.txt");
        	if (decoded != null){
        		System.out.println("Decrypted - "+decoded);
        	}else{
        		System.out.println("Didn't decrypt");
        	}
        }else{
        	System.out.println("Didn't encrypt");
        }
        

          
 
    }
}
