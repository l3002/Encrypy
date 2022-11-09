import java.io.Serializable;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

class Credential implements Serializable{

    private String passwd; // password for register and login

    public void setPasswdAsText(String passwd){
        this.passwd = passwd;
    }

    public String getPasswd(){
        return passwd;
    }

    // to set the account passwords
    public void setPasswdAsHash(String passwd) throws Exception{
        this.passwd=HashCreator.createHash(passwd);
    }

    // to set managed passwords
    public void setPasswdAsCipher(String passwd) throws Exception{
        this.passwd =HashCreator.cipher(passwd);
    }

    // to get managed passwords
    public String getDecipheredPasswd() throws Exception{
        return HashCreator.decipher(passwd);
    }

    // to get account paswords
    public boolean validate(String input) throws NoSuchAlgorithmException, InvalidKeySpecException{
        byte[] testHash = HashCreator.createHash(input, passwd);
        byte[] hash = HashCreator.fromHex(passwd.split(":")[2]);
        int diff = testHash.length ^ hash.length;
        for(int i = 0; i < hash.length && i < testHash.length; i++)
        {
            diff |= hash[i] ^ testHash[i];
        }
        return diff == 0;
    }

    // Inner Class to create ciphers and hashes for given passwords
    private static class HashCreator{

        private static Cipher c; // secret key used during 
        private static KeyPair pair; // Cipher object used during

        // method to create Hash for account passwords and returing string
        static String createHash(String input) throws Exception{
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[16];
            int iteration = 65536;
            random.nextBytes(salt);
            PBEKeySpec spec = new PBEKeySpec(input.toCharArray(), salt, iteration, 64*8);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hash = factory.generateSecret(spec).getEncoded();
            return iteration + ":" + convertToHex(salt) + ":" + convertToHex(hash);
        }

        static byte[] createHash(String input, String hashedPassword) throws NoSuchAlgorithmException, InvalidKeySpecException{
            String[] parts = hashedPassword.split(":");
            int iterations = Integer.parseInt(parts[0]);

            byte[] salt = fromHex(parts[1]);
            byte[] hash = fromHex(parts[2]);
            PBEKeySpec spec = new PBEKeySpec(input.toCharArray(), salt, iterations, hash.length * 8);
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return skf.generateSecret(spec).getEncoded();
        }

        static byte[] fromHex(String hex)
        {
            byte[] bytes = new byte[hex.length() / 2];
            for(int i = 0; i < bytes.length ;i++)
            {
                bytes[i] = (byte)Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
            }
            return bytes;
        }

        // method to convert the hash byte array to a string
        private static String convertToHex(byte[] messageDigest) {
            BigInteger bigint = new BigInteger(1, messageDigest);
            String hexText = bigint.toString(16);
            int paddingLength = (messageDigest.length * 2) - hexText.length();
            if(paddingLength > 0)
            {
                return String.format("%0"  +paddingLength + "d", 0) + hexText;
            }
            else{
                return hexText;
            }
        }
        
        // to cipher managed passwords
        static String cipher(String input) throws Exception{
            KeyPairGenerator keygen = KeyPairGenerator.getInstance("RSA");
            keygen.initialize(2048);
            pair = keygen.generateKeyPair();
            PublicKey publickey = pair.getPublic();
            c= Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
            c.init(Cipher.ENCRYPT_MODE, publickey);
            byte[] cipher = c.doFinal(input.getBytes());
            return Base64.getEncoder().encodeToString(cipher);
        }

        // to decipher managed passwords
        static String decipher(String cipher) throws Exception{
            byte[] a = Base64.getDecoder().decode(cipher);
            PrivateKey privateKey = pair.getPrivate();
            c.init(Cipher.DECRYPT_MODE,privateKey);
            byte[] decipherBytes = c.doFinal(a);
            return new String(decipherBytes,"UTF8");
        }
     }
}