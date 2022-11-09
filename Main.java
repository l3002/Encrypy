import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream; 
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
public class Main {
    final static File file = new File("/home/l3002/.encrypy");
    public static ArrayList<Register> getContent(File file) throws Exception{
        if(!file.exists()){
            file.createNewFile();
        }
        FileInputStream fis = new FileInputStream(file);
        ObjectInputStream ois = new ObjectInputStream(fis);
        ArrayList<Register> fileContent = (ArrayList<Register>) ois.readObject();
        ois.close();
        fis.close();
        return fileContent;
    }

    public static void writeData(File file,Register account) throws Exception{
        try{
            ArrayList<Register> fileContent = getContent(file);
            boolean flag = true;
            for(int i=0;i<fileContent.size();i++){
                if(fileContent.get(i).equals(account)){
                    flag=false;
                    System.out.println("set");
                    fileContent.set(i,account);
                    break;
                }
            }
            if(flag){
                fileContent.add(account);
            }
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(fileContent);
            oos.close();
            fos.close();
        }
        catch(EOFException eof){
            ArrayList<Register> firstList = new ArrayList<Register>();
            firstList.add(account);
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(firstList);
            oos.close();
            fos.close();
        }
    }
    public static void main(String[] args) throws Exception{
        Register r2 = new Register("Tanmay", "tnamysd9", "Tanmaymathpal4545@gmail.com", "Thakdjfd", "tsdkf");
        ArrayList<Entry> a = new ArrayList<Entry>();
        a.add(new Entry("Username", "Tanmay", "www.codechef.com"));
        a.add(new Entry("Tnm3","Max=numb12","www.adidas.com"));
        r2.setEntries(a);
        Menu.displayPasswords(r2);
        
        // Credential c = new Credential();
        // c.setPasswdAsCipher("Tanmay2003");
        // System.out.println(c.getDecipheredPasswd());
    }
}