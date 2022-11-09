import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;

/* Login class

 * Extended by Register Class and User Class
 * Provides methods for all the Login functionalities
 * Contains attributes which store used information 
 */
public class Login implements Serializable{

    private String userName = null; // stores usernames (default=null)
    private String emailId = null; // stores email address (default=null)
    private Credential pass = new Credential(); // stores Pass object which contains password

    // Forgotten Password functionality: used for reseting password
    static void forgottenPassword() throws Exception{
        // for reseting password

        // input for email address
        System.out.println("Enter the email address");
        String email = new BufferedReader(new InputStreamReader(System.in)).readLine();

        // To send a reset password mail
        boolean check = Menu.mail(email,"resetPassword");

        // To check if email is registered
        Register account = LoginRegisterExceptions.notRegistered(email);

        // Security Question
        System.out.println("What's your favourite fruit?");
        String secret = new BufferedReader(new InputStreamReader(System.in)).readLine();
        String a = Register.looseEncrypt(secret);
        
        // validating secret and token
        if(check && account.getSecret().equalsIgnoreCase(a)){
            String newPassword = new String(System.console().readPassword());
            account.setHashPass(newPassword); // calling updatePassword() method
        }
        else{
            System.out.println("either token or security question was incorrect.");
        }
    }

    // To Set Username
    public void setUserName(String userName){
        this.userName=userName;
    }

    // To get value stored in userName
    public String getUserName(){
        return userName;
    }

    // To set email
    public void setEmailId(String emailId){
        this.emailId=emailId;
    }

    // To get the value stored in emailId
    public String getEmailId(){
        return emailId;
    }

    public void setTextPass(String passwd){
        this.pass.setPasswdAsText(passwd);
    }

    public void setHashPass(String passwd) throws Exception{
        this.pass.setPasswdAsHash(passwd);
    }

    public void setCipherPass(String passwd) throws Exception{
        this.pass.setPasswdAsCipher(passwd);
    }

    // To get the Password stored in pass
    public Credential getPass() throws Exception{
        return pass;
    }

    // Login constructor to create register object
    Login(String userName,String emailId,String passwd) throws Exception{
        setUserName(userName);
        setEmailId(emailId);
        this.pass.setPasswdAsHash(passwd);
    }

    // Login constructor to create user object
    Login(String userNameOrEmail) throws Exception{

        // To see if the string input is a email or a userName
        if(userNameOrEmail.contains("@")){
            setEmailId(userNameOrEmail);
        }
        else{
            setUserName(userNameOrEmail);
        }
    }

    // Login constructor to create login object
    Login(String userNameOrEmail, String passwd) throws Exception{

        // To see if the string input is a email or a userName
        if(userNameOrEmail.contains("@")){
            setEmailId(userNameOrEmail);
        }
        else{
            setUserName(userNameOrEmail);
        }
        setTextPass(passwd);
    }
}
