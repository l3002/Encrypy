import java.io.Console;
import java.util.ArrayList;

public class Entry extends Login{
    
    private String url; // to store the url
    
    static void createNewEntry(Register account) throws Exception{
        Console c = System.console();
        String userNameOrEmail = c.readLine("Enter the Username or Email-ID: ");
        String url = c.readLine("Enter the URL: ");
        char[] passwdCharArr = c.readPassword("Enter the Password: ");
        String passwd = new String(passwdCharArr);
        LoginRegisterExceptions.confirmPassword(passwd, c);
        Entry entry = new Entry(userNameOrEmail, passwd, url);
        ArrayList<Entry> entryList = account.getEntries();
        entryList.add(entry);
        account.setEntries(entryList);
        // write 
        // Menu.mail(account.getEmailId(),"passwordAdded");
    }

    // to get the value of url
    public String getUrl(){
        return url;
    }

    // to set the value of url
    public void setUrl(String url){
        this.url=url;
    }

    // To construct a Entry object
    Entry(String userNameOrEmail,String passwd,String url) throws Exception{
        super(userNameOrEmail);
        super.setCipherPass(passwd);
        setUrl(url);
    }
}
