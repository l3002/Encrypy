import java.io.Serializable;

public class User extends Login implements Serializable{
    private String url;
    public String getUrl(){
        return url;
    }
    public void setUrl(String url){
        this.url=url;
    }
    User(String userName,String emailId,String passwd,String url) throws Exception{
        super(userName,emailId,passwd);
        setUrl(url);
    }
}
