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
                Register r = fileContent.get(i);
                System.out.println("this is emailID : "+r.getEmailId());
                System.out.println("this is emailID account : "+account.getEmailId());
                System.out.println("this is emailID : "+r.getUserName());
                System.out.println("this is emailID account : "+account.getUserName());
                System.out.println("this is emailID : "+r.getPass());
                System.out.println("this is emailID account : "+account.getPass());
                if(r.equals(account)){
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
        // Register r2 = new Register("Tanmay", "tnamysd9", "Tanmaymathpal4545@gmail.com", "Thakdjfd", "tsdkf");
        // writeData(file, r2);
        // Register r1 = r2;
        // writeData(file,r1);
        // Register c = new Register("Thissdfsd", "tna9", "Tanmaymathpal9948@gmail.com", "Thakdd", "tkf");
        // writeData(file,c);
        // ArrayList<Register> b = getContent(file);
        // for(Register n : b){
        //     System.out.println("This is userName " + n.getUserName());
        // }
    }
}