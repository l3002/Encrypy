public class Login{
    private String userName = null;
    private String emailId = null;
    private TestPass pass;
    public void setUserName(String userName){
        this.userName=userName;
    }
    public String getUserName(){
        return userName;
    }
    public void setEmailId(String emailId){
        this.emailId=emailId;
    }
    public String getEmailId(){
        return emailId;
    }
    public void setHashPass(String passwd) throws Exception{
        pass = new TestPass(passwd);
    }
    public String getPass(){
        return pass.getPasswd();
    }
    Login(String userName,String emailId,String passwd) throws Exception{
        setUserName(userName);
        setHashPass(passwd);
        setEmailId(emailId);
    }
    Login(String userNameOrEmail,String passwd) throws Exception{
        if(userNameOrEmail.contains("@")){
            setEmailId(userNameOrEmail);
        }
        else{
            setUserName(userNameOrEmail);
        }
        setHashPass(passwd);
    }
}
