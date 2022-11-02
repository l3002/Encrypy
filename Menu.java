import java.io.*;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import java.math.BigInteger;
import java.security.SecureRandom;

public class Menu {
    static void createNewAccount() throws Exception{
        Console c = System.console();
        String name = c.readLine("Enter your Name: ");
        String userName = c.readLine("Enter Username: ");
        String emailId = c.readLine("Enter your EmailId: ");
        String passwd = new String(c.readPassword());
        LoginRegisterExceptions.WeakPasswordException(passwd);
        LoginRegisterExceptions.confirmPassword(passwd, c);
        String secret = c.readLine();
        Register newAccount = new Register(name, userName, emailId, passwd, secret);
        try{
            ArrayList<Register> fileContent = (ArrayList<Register>) Main.getContent(Main.file0);
            fileContent.add(newAccount);
            Main.writeData(Main.file0, fileContent);
        }
        catch(EOFException e){
            ArrayList<Register> firstArrayList = new ArrayList<Register>();
            firstArrayList.add(newAccount);
            Main.writeData(Main.file0, firstArrayList);
        }
        finally{
            mail(emailId,"accountAdded");
        }
    }

    static void createNewEntry(String uid,String email) throws Exception{
        Console c = System.console();
        String userNameOrEmail = c.readLine("Enter the Username or Email-ID: ");
        String url = c.readLine("Enter the URL: ");
        char[] passwdCharArr = c.readPassword("Enter the Password: ");
        String passwd = new String(passwdCharArr);
        LoginRegisterExceptions.confirmPassword(passwd, c);
        Entry entry = new Entry(userNameOrEmail, passwd, url);
        HashMap<String,ArrayList<Entry>> fileContent = (HashMap<String,ArrayList<Entry>>) Main.getContent(Main.file1);
        try{
            ArrayList<Entry> entryList = fileContent.get(uid);
            entryList.add(entry);
            fileContent.put(uid,entryList);
            Main.writeData(Main.file1, fileContent);
        }
        catch(EOFException e){
            ArrayList<Entry> firstEntry = new ArrayList<>();
            firstEntry.add(entry);
            fileContent.put(uid,firstEntry);
            Main.writeData(Main.file1, fileContent);
        }
        finally{
            mail(email,"passwordAdded");
        }
    }

    static boolean mail(String email, String seq) throws Exception{
        // Recipient's email ID needs to be mentioned.
        String to = email ;

        // Sender's email ID needs to be mentioned
        String from = "tanmaymathpal4545@gmail.com";

        // Assuming you are sending email from through gmails smtp
        String host = "smtp.gmail.com";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        // Get the Session object.// and pass username and password
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication("tanmaymathpal4545@gmail.com", "vrvfaxccelzlehqd");

            }

        });

        // Create a default MimeMessage object.
        MimeMessage message = new MimeMessage(session);

        // Set From: header field of the header.
        message.setFrom(new InternetAddress(from));

        // Set To: header field of the header.
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

        switch (seq) {
            case "accountAdded":
                message.setSubject("Thank you for choosing Encrypy");
                message.setText("Dear Customer,\nEncrypy's whole team gracious thank you for choosing encrypy as your password manager.\nAt Encrypy we highly value our customers and their privacy, that's why Encrypy's uses high grade encryption algorthims and hashes to keep your password safe. In Addition, the interface is highly user-friendly so that you can manage your passwords without any fuss.\nAgain, Thanks for joining us in the journey of making password security easy and simple.\nRegards,\nTeam Encrypy");
                Transport.send(message);
                break;
            case "passwordAdded":
                message.setSubject("New Password Added");
                message.setText("Dear Customer,\nThis is to notify you that a new password has been added to your account.\nRegards,\nTeam Encrypy");
                Transport.send(message);
                break;
            case "resetPassword":

                // To Create Random Token
                SecureRandom random = new SecureRandom();
                byte[] bytes = new byte[16];
                random.nextBytes(bytes);
                BigInteger bigint = new BigInteger(1,bytes);
                String token = bigint.toString(16);

                // Set Subject: 
                message.setSubject("Forgotten Password Requested");

                // Now set the actual message
                message.setText("The secret token for reseting your password is "+token);

                // Send message
                Transport.send(message);

                System.out.println("We have sent you a token at your registered email address");

                // Verify the token
                System.out.println("Enter the token:");
                String tokenValue = new BufferedReader(new InputStreamReader(System.in)).readLine();

                if(!tokenValue.equals(token)){
                    return false;
                }
                break;
        }
        return true;
    }

    static void updatePassword() throws Exception{
        // to updatePassword for a certain Account or Username 
        Console c = System.console();
        String newPasswd = new String(c.readPassword("Enter the new Password"));
        LoginRegisterExceptions.confirmPassword(newPasswd, c);
        System.out.println("Password Updated");
    }
}
