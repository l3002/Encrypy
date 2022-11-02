public class Entry extends Login{
    private String url;
    public String getUrl(){
        return url;
    }
    public void setUrl(String url){
        this.url=url;
    }
    Entry(String userNameOrEmail,String passwd,String url) throws Exception{
        super(userNameOrEmail);
        super.setCipherPass(passwd);
        setUrl(url);
    }
}
