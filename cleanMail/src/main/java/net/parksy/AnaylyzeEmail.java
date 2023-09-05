package net.parksy;



import jakarta.mail.Folder;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Store;
import jakarta.mail.internet.MimeUtility;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static net.parksy.Config.getStore;

public class AnaylyzeEmail {

    public static final int PROCESS_LIMIT = 20;

    public static void analyze( String service )
    {
        long count = 0;

        try
        {

            Store store = getStore(service);

            // create the folder object and open it
            Folder emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_ONLY);

            Message[] messages = emailFolder.getMessages();
            System.out.println("messages.length---" + messages.length);
            for (Message message : messages) {
                if (count++ > PROCESS_LIMIT) break;
                String decodedSubject = "";
                try {
                    decodedSubject = ("" + MimeUtility.decodeText(message.getSubject()).toLowerCase().replaceAll("\\s+",""));
                } catch (UnsupportedEncodingException | MessagingException ignore) { }
                String subject = ("" + message.getSubject()).toLowerCase().replaceAll("\\s+","");
                String decodedFrom = "";
                try {
                   decodedFrom = MimeUtility.decodeText(message.getFrom()[0].toString()).toLowerCase().replaceAll("\\s+","");;
                } catch (UnsupportedEncodingException | MessagingException ignore) { }
                String from = message.getFrom()[0].toString().toLowerCase().replaceAll("\\s+","");

                System.out.println("---------------------------------");
                System.out.println("decoded subject: " + decodedSubject );
                System.out.println("        Subject: " + subject);
                System.out.println("Decoded From: " + decodedFrom);
                System.out.println("        From: " + from);

            }
            // expunges the folder to remove messages which are marked deleted
            emailFolder.close(true);
            store.close();

        } catch (MessagingException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        analyze(Config.USA);
    }


}
