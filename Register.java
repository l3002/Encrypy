import java.io.Serializable;

public class Register extends Login implements Serializable{

    private String name;
    private boolean blocked=false;
    private String secret;
    public void setName(String name){
        this.name=name;
    }
    public String getName(){
        return name;
    }
    public void setBlocked(String block){
        if(block.equals("yes")){
            this.blocked=true;
        }
        else{
            this.blocked=false;
        }
    }
    public boolean getBlocked(){
        return blocked;
    }
    public void setSecret(String secret){
        String secret1="";
        for(int i=0;i<secret.length();i++){
            if(i%2==0){
                secret1+=(char)((int)secret.charAt(i)+1);
            }
            else{
                secret1+=(char)((int)secret.charAt(i)-1);
            }
        }
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