import java.util.regex.Pattern;
import java.util.ArrayList;
import java.util.regex.Matcher;

/* LoginRegisterException class:
 * 
 * Extends Exception class
 * Contains all the different fuctions to check for Exceptions
*/
public class LoginRegisterExceptions extends Exception{

    // To eliminate username redundancy in data center
    public static void UserNameException(String userName,Register users) throws LoginRegisterExceptions{
        if(!userName.equals(users.getUserName())){
            throw new LoginRegisterExceptions("Username already taken");
        }
    }

    // To eliminate email address redundancy in data center
    public static void EmailException(String emailId,Register users) throws LoginRegisterExceptions{
        if(!emailId.equals(users.getEmailId())){
            throw new LoginRegisterExceptions("Email Id is already Registered");
        }
    }

    // To eliminate the possibility of weak passwords
    public static void WeakPasswordException(String passwd) throws LoginRegisterExceptions{
        Pattern pattern1 = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%&*()])(?=\\S+$).{8,20}$");
        Matcher match = pattern1.matcher(passwd);
        if(!match.find()){
            throw new LoginRegisterExceptions("Passoword is weak");
        }
    }

    // To confirm Entered Password
    public static void confirmPassword(String newpasswd) throws LoginRegisterExceptions{
        String confirmPassword = new String(System.console().readPassword("Confirm the password"));
        if(!newpasswd.equals(confirmPassword)){
            throw new LoginRegisterExceptions("Passwords don't match");
        }
    }

    // For Account Not Found Exception
    public static Register notRegistered(String email) throws Exception{
        ArrayList<Register> fileContent = (ArrayList<Register>) Main.getContent(Main.file);
        boolean flag = true;
        Register account = null;
        for(Register a: fileContent){
            if(a.getEmailId().equalsIgnoreCase(email)){
                account = a;
                flag=false;
                break;
            }
        }
        if(flag){
            throw new LoginRegisterExceptions("Email is not registered");
        }
        return account;
    }
    
    LoginRegisterExceptions(String a){
        super(a);
    }
}
