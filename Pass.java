import java.io.Serializable;
import java.math.BigInteger;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
class Pass implements Serializable{
    private String passwd;
    private byte[] cipherText;
    public void setPasswd(String passwd) throws Exception{
        this.passwd=HashCreator.createHash(passwd);
    }
    public void setPasswdForUser(String passwd) throws Exception{
        this.cipherText=HashCreator.cipher(passwd);
    }
    public String getPasswdForUser() throws Exception{
        return HashCreator.decipher(cipherText);
    }
    public String getPasswd(){
        return passwd;
    }
    Pass(String passwd) throws Exception{
        setPasswdForUser(passwd);
    }
    private static class HashCreator{
        private static SecretKey key;
        private static Cipher c;
        public static String createHash(String input) throws Exception{
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[16];
            random.nextBytes(salt);
            PBEKeySpec spec = new PBEKeySpec(input.toCharArray(), salt, 65536, 128);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hash = factory.generateSecret(spec).getEncoded();
            String a = convertToHex(hash);
            return a;
        }
        private static void setcipherObj() throws Exception{
            KeyGenerator keygen = KeyGenerator.getInstance("DES");
            keygen.init(56);
            key=keygen.generateKey();
            c= Cipher.getInstance("DES/ECB/PKCS5Padding");
        }
        public static byte[] cipher(String input) throws Exception{
            setcipherObj();
            c.init(Cipher.ENCRYPT_MODE, key);
            return c.doFinal(input.getBytes());
        }
        public static String decipher(byte[] input) throws Exception{
            c.init(Cipher.DECRYPT_MODE,key);
            byte[] decipherBytes = c.doFinal(input);
            return new String(decipherBytes,"UTF8");
        }
        private static String convertToHex(final byte[] messageDigest) {
            BigInteger bigint = new BigInteger(1, messageDigest);
            String hexText = bigint.toString(16);
            while (hexText.length() < 32) {
                hexText = "0".concat(hexText);
            }
            return hexText;
        }
     }
}