public class Entry extends Login{
    
    private String url; // to store the url

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
