import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream; 
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
public class Main {

    static File file0 = new File("/home/l3002/.encrypy_register");
    static File file1 = new File("/home/l3002/.encrypy_pass");
    public static void createIfAbsent(File file) throws Exception{
        if(!file.exists()){
            file.createNewFile();
        }
    }

    public static Object getContent(File file) throws Exception{
        FileInputStream fis = new FileInputStream(file);
        ObjectInputStream ois = new ObjectInputStream(fis);
        Object fileContent = ois.readObject();
        ois.close();
        fis.close();
        return fileContent;
    }

    public static void writeData(File file,Object content) throws Exception{
        FileOutputStream fos = new FileOutputStream(file);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(content);
        oos.close();
        fos.close();
    }
    public static void main(String[] args) throws Exception{
        Register a = new Register("Hello", "Hello", "Hello", "Hello", "Hello");
        Entry b = new Entry("Hello", "Hello", "Hello");
        System.out.println(a.getPass().getHashedPasswd());
        System.out.println(b.getPass().getDecipheredPasswd());
        
    }
}