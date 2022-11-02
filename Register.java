public class Register extends Login{

    private String name; // stores name
    private String secret; // stores answer to the Security Question

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
        super(userName,emailId);
        super.setHashPass(passwd);
        setName(name);
        setSecret(secret);
    }
}