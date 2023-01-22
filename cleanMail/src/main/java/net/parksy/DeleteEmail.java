package net.parksy;

import javax.mail.*;
import javax.mail.internet.MimeUtility;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;

import static java.lang.System.exit;
import static net.parksy.Config.getStore;


public class DeleteEmail {

    public static final int PROCESS_LIMIT = getLimit();

    private static int getLimit() {
        try {
            return Integer.parseInt(System.getenv("mailLimit"));
        } catch (NumberFormatException nfe) {
            return 70;
        }
    }

    /* from & subject:
       From: whatsapp   Subject: [spam]incomingvoicemessage
       From: netflix <info@mailer.netflix.com>   Subject: [spam]comingfriday,july15th...persuasion
       From: linkedin<messages-noreply@linkedin.com>   Subject: 💬darryl,addheatherkobayashi toyournetwork
       From: news@nofluffjuststuff.com Subject: Deep Dive
       From: updates@themorningcaller.com   Subject: 1776 Action
       From: updates@themorningcaller.com   Second Notice
       From: budget@e.budget.com    Subject: special offer
     */

    public static void delete( String service )
    {
        long count = 0;

        try
        {
            Store store = getStore(service);

            String userHomeDir = System.getProperty("user.home");
            String fileName = userHomeDir + File.separator + "cleanMail"+File.separator+"emailsToDelete.txt";
            String badSubjectsFileName = userHomeDir + File.separator + "cleanMail"+File.separator+
                    "emailsSubjectToDelete.txt";
            System.out.printf("Emails to delete file: %s", fileName);

            ArrayList<String> badSubjects = new ArrayList<>();
            ArrayList<String> badEmails = new ArrayList<>();
            try {
                badSubjects = new ArrayList<>(Files.readAllLines(Paths.get(badSubjectsFileName)));
            }
            catch (IOException e) {
                System.out.printf("Error getting badSubjects file: %s", badSubjectsFileName);
                exit(1);
            }
            try {
                badEmails = new ArrayList<>(Files.readAllLines(Paths.get(fileName)));
            }
            catch (IOException e) {
                System.out.printf("Error getting badEmail file: %s", fileName);
                exit(1);
            }

            // create the folder object and open it
            Folder emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_WRITE);

            Message[] messages = emailFolder.getMessages();
            System.out.println("messages.length---" + messages.length);
            int limit = PROCESS_LIMIT;
            String from = "";
            String subject = "";
            for (Message message : messages) {
                limit--;
                if (limit <0) break;
                try {
                    subject = ("" + MimeUtility.decodeText(message.getSubject()).toLowerCase().replaceAll("\\s+",""));
                } catch (UnsupportedEncodingException | MessagingException ignore) {
                    subject = ("" + message.getSubject()).toLowerCase().replaceAll("\\s+", "");
                }
                try {
                    from = MimeUtility.decodeText(message.getFrom()[0].toString()).toLowerCase().replaceAll("\\s+","");;
                } catch (UnsupportedEncodingException | MessagingException ignore) {
                    from = message.getFrom()[0].toString().toLowerCase().replaceAll("\\s+","");
                }

                boolean delete = false;

                for (String badEmail : badEmails) {
                    if (!badEmail.trim().isEmpty() && from.contains(badEmail)) {
                        delete = true;
                        break;
                    }
                }

                if (!delete) {
                    for (String badSubject : badSubjects) {
                        if (subject.contains(badSubject)) {
                            delete = true;
                            break;
                        }
                    }
                }
                if (delete) {
                    // set the DELETE flag to true
                    message.setFlag(Flags.Flag.DELETED, true);
                    count++;
                    System.out.println("---------------------------------");
                    System.out.println("DELETE Subject: " + subject);
                    System.out.println("From: " + from);
                }

            }
            // expunges the folder to remove messages which are marked deleted
            emailFolder.close(true);
            store.close();
            System.out.println("Messages DELETED: " + count + "  " + new Date() );
            System.out.println("============================== ");

        } catch (MessagingException | IOException e) {
            e.printStackTrace();
        }
    }



    public static void main(String[] args) {

        if (args.length > 0 && args[0].equalsIgnoreCase("o")) {
            DeleteOldEmail.delete(Config.USA);
        } else if (args.length > 0 && args[0].equalsIgnoreCase("a")) {
            AnaylyzeEmail.analyze(Config.USA);
        } else {
            delete(Config.USA);
//            delete(Config.GOOGLE);
        }

    }

}
