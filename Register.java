import java.util.ArrayList;

public class Register extends Login{

    private String name; // stores name
    private String secret; // stores answer to the Security Question
    private ArrayList<Entry> entries;

    public ArrayList<Entry> getEntries() throws Exception{
        return entries;
    }

    public void setEntries(ArrayList<Entry> entries){
        this.entries=entries;
    }

    public Register validate(Login login) throws Exception{
        if((getEmailId().equalsIgnoreCase(login.getEmailId()) || getUserName().equals(login.getUserName())) && getPass().validate(login.getPass().getPasswd())){
            return this;
        }
        else{
            throw new LoginRegisterExceptions("Wrong Credentials");
        }
    }

    public boolean equals(Register account) throws Exception{
        if((getEmailId().equalsIgnoreCase(account.getEmailId()) && getUserName().equals(account.getUserName())) && getPass().getPasswd().equals(account.getPass().getPasswd())){
            return true;
        }
        else{
            return false;
        }
    }

    // To set values of Name
    public void setName(String name){
        this.name=name;
    }

    // To get value of Name
    public String getName(){
        return name;
    }

    // To encrypt answer to the Security Question
    static String looseEncrypt(String secret){
        String secret1="";
        for(int i=0;i<secret.length();i++){
            if(i%2==0){
                secret1+=(char)((int)secret.charAt(i)+1);
            }
            else{
                secret1+=(char)((int)secret.charAt(i)-1);
            }
        }
        return secret1;
    }

    // To set Value of secret
    public void setSecret(String secret){
        String secret1= looseEncrypt(secret);
        this.secret=secret1;
    }
    public String getSecret(){
        return secret;
    }
    Register(String name, String userName,String emailId,String passwd,String secret) throws Exception{
        super(userName,emailId,passwd);
        setName(name);
        setSecret(secret);
    }
}