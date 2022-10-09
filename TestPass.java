import java.io.Serializable;
import java.math.BigInteger;
import java.security.SecureRandom;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
class TestPass implements Serializable{
    private String passwd;
    public void setPasswdForUser(String passwd) throws Exception{
        HashCreator hc= new HashCreator();
        this.passwd=hc.createSHAHash(passwd);
    }
    public String getPasswd(){
        return passwd;
    }
    TestPass(String passwd) throws Exception{
        setPasswdForUser(passwd);
    }
    private class HashCreator {
        public String createSHAHash(String input) throws Exception{
            SecureRandom random = new SecureRandom();
            byte[] salt = new byte[16];
            random.nextBytes(salt);
            PBEKeySpec spec = new PBEKeySpec(input.toCharArray(), salt, 65536, 128);
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hash = factory.generateSecret(spec).getEncoded();
            String a = convertToHex(hash);
            return a;
        }
     
        private String convertToHex(final byte[] messageDigest) {
            BigInteger bigint = new BigInteger(1, messageDigest);
            String hexText = bigint.toString(16);
            while (hexText.length() < 32) {
                hexText = "0".concat(hexText);
            }
            return hexText;
        }
     }
}