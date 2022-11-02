import java.io.Serializable;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

class Credential implements Serializable{

    private String passwd; // password for register and login
    
    // to set the account passwords
    public void setPasswdAsHash(String passwd) throws Exception{
        this.passwd=HashCreator.createHash(passwd);
    }

    // to set managed passwords
    public void setPasswrdAsCipher(String passwd) throws Exception{
        this.passwd =HashCreator.cipher(passwd);
    }

    // to get managed passwords
    public String getDecipheredPasswd() throws Exception{
        return HashCreator.decipher(passwd);
    }

    // to get account paswords
    public String getHashedPasswd(){
        return passwd;
    }

    // Inner Class to create ciphers and hashes for given passwords
    private static class HashCreator{

        private static SecretKey key; // secret key used during 
        private static Cipher c; // Cipher object used during 

        // method to create Hash for account passwords and returing string
        static String createHash(String input) throws Exception{
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[16];
            random.nextBytes(salt);
            PBEKeySpec spec = new PBEKeySpec(input.toCharArray(), salt, 65536, 128);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hash = factory.generateSecret(spec).getEncoded();
            String a = convertToHex(hash);
            return a;
        }
        
        // method to convert the hash byte array to a string
        private static String convertToHex(byte[] messageDigest) {
            BigInteger bigint = new BigInteger(1, messageDigest);
            String hexText = bigint.toString(16);
            while (hexText.length() < 32) {
                hexText = "0".concat(hexText);
            }
            return hexText;
        }
        
        // to cipher managed passwords
        static String cipher(String input) throws Exception{
            KeyGenerator keygen = KeyGenerator.getInstance("DES");
            keygen.init(56);
            key=keygen.generateKey();
            c= Cipher.getInstance("DES/ECB/PKCS5Padding");
            c.init(Cipher.ENCRYPT_MODE, key);
            byte[] cipher = c.doFinal(input.getBytes());
            return Base64.getEncoder().encodeToString(cipher);
        }

        // to decipher managed passwords
        static String decipher(String cipher) throws Exception{
            byte[] a = Base64.getDecoder().decode(cipher);
            c.init(Cipher.DECRYPT_MODE,key);
            byte[] decipherBytes = c.doFinal(a);
            return new String(decipherBytes,"UTF8");
        }
     }
}