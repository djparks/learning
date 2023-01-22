package net.parksy;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import java.io.*;
import java.util.Map;
import java.util.Properties;

public class Config {

    public static final String USA = "usa";
    public static final String GOOGLE = "google";
    /*
    Temporary to test Reading properties file.
     */
    public static void main(String[] args) {

        String userHomeDir = System.getProperty("user.home");
        String fileName = userHomeDir + File.separator + "cleanMail" + File.separator + "email.properties";

        try (InputStream input = new FileInputStream(fileName)) {

            Properties prop = new Properties();

            // load a properties file
            prop.load(input);

            // get the property value and print it out
            System.out.println(prop.getProperty("db.url"));
            System.out.println(prop.getProperty("db.user"));
            System.out.println(prop.getProperty("db.password"));

        } catch (IOException ex) {
            ex.printStackTrace();
        }


    }

    public static Store getStore(String service) throws MessagingException, IOException {
        //name and value of all environment variables in Java  program
//        Map<String, String> env = System.getenv();
//        for (String envName : env.keySet()) {
//            System.out.format("%s=%s%n", envName, env.get(envName));
//        }

        // Get configuration stored in Environment.
        String pop3Host;
        String user;
        String password;

        String userHomeDir = System.getProperty("user.home");
        String fileName = userHomeDir + File.separator + "cleanMail" + File.separator + "email.properties";

        try (InputStream input = new FileInputStream(fileName)) {

            Properties prop = new Properties();

            // load a properties file
            prop.load(input);

            // get the property value and print it out
            pop3Host = prop.getProperty(service+".pop3Host");
            user = prop.getProperty(service+".pop3User");
            password = prop.getProperty(service+".pop3Pwd");

        } catch (IOException ex) {
            ex.printStackTrace();
            throw ex;
        }

        // Get configuration stored in Environment.
//        String pop3Host = System.getenv("pop3Host");
//        String user = System.getenv("pop3User");
//        String password = System.getenv("pop3Pwd");
//        System.out.println("Pop3 Host: " + pop3Host);


        // get the session object
        Properties properties = new Properties();
        properties.put("mail.store.protocol", "pop3");
        properties.put("mail.pop3s.host", pop3Host);
        properties.put("mail.pop3s.port", "995");
        properties.put("mail.pop3.starttls.enable", "true");
        Session emailSession = Session.getDefaultInstance(properties);
        // emailSession.setDebug(true);

        // create the POP3 store object and connect with the pop server
        Store store = emailSession.getStore("pop3s");

        store.connect(pop3Host, user, password);
        return store;
    }
}
