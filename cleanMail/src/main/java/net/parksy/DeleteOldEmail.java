package net.parksy;

import jakarta.mail.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static net.parksy.Config.getStore;

//Based on: https://www.tutorialspoint.com/javamail_api/javamail_api_deleting_emails.htm
public class DeleteOldEmail {


    public static final int MAX_DELETE = 145;
    static String[] sixMonthEmails = {"mcinfo@ups.com", "westernjournal.com", "technofog@substack.com", "update.prageru.com", "em.bongino.com",
            "zdnet.com",  "zdnet.online.com", "email.peacocktv.com", "chick-fil-a.com", "billingquest@stlmsd.com",  "support@wealthfront.com", "linkedin.com", "gotowebinar.com",
            "firstcommunity.com", "ancestry.com", "evernote.com", "biohackingsecrets", "ameren.com", "iluv.southwest.com", "redcross.org",
            "yelp.com", "oberweis.com", "wealthfront.com", "redhat.com", "bedbathandbeyond", "verizonwireless.com", "foundmyfitness", "objectcomputing.com",
            "clarionproject.org", "theepochtimes", "rumble.com", "vmware.com", "westernjournalism", "email.udemy.com", "udemymail", "babylonbee",
    "tacobell.com", ".iherb.com", "spireenergy.com", "dailywire", "projectveritas", "theusawire", "heritageaction", "panerabread", "getrightred",
    "trump", "conservative", "newslivewire", "locals.com", "lifeextension", "freedomheadlines", "celticwoman", "nofluffjuststuff", "bestbuy", "rudygfreedomfund",
    "tubitv.com", "oracle-mail.com", "mercuryone", "patriot", "scp-email.com", "paycomonline", "audible", "paywhirl.com", "meetup.com", "dineshdsouza", "republicservices"
    , "lifelock@secure.norton.com", "byutv", "afa.net", "oreilly", "universalstudios", "amwater", "planethealthstl", "churchofjesuschrist"
    , "billiongraves", "netflix", "protonmail", "papajohns", "webcodegeeks", "alerts@citibank.com", "wailing", "officedepot", "amwater"
    , "wailing", "webcodegeeks", "hpsmart", "wallabyjs", "mypatientchart", "jetbrains"};







    static String[] oneYearOld = {"kevinfpostoldds", "microsoft.com", "blog@rieckpil.de", "capitalone@notification.capitalone.com"
    , "pluralsight.com", "southwest", "citibank.com" };

    static String[] twoYearOld = {"choicehotels.com", "westcountyhonda", "whizlabs.com", "jetbrains.com", "amazon.com", "winred.com", "lindapjones",
    "manning.com", "paypal"};

    public static void delete(String service)
    {
        LocalDateTime localDate = LocalDateTime.now().minusDays(200);
        Date sixMonthsAgo = Date.from(localDate.atZone(ZoneId.systemDefault()).toInstant());

        LocalDateTime localDate1Year = LocalDateTime.now().minusDays(365);
        Date OneYearAgo = Date.from(localDate1Year.atZone(ZoneId.systemDefault()).toInstant());

        LocalDateTime localDateTwoYear = LocalDateTime.now().minusDays(650);
        Date twoYearsAgo = Date.from(localDateTwoYear.atZone(ZoneId.systemDefault()).toInstant());

        try
        {
            Store store = getStore(service);

            // create the folder object and open it
            Folder emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_WRITE);

            Message[] messages = emailFolder.getMessages();
            System.out.println("messages.length---" + messages.length);
            int deleteCount = 0;
            String parsedDateStr ="";
            Date receivedDate = null;

            for (Message message : messages) {
                String subject = (""+message.getSubject()).toLowerCase();
                String from = "";
                try {
                    from = message.getFrom()[0].toString().toLowerCase();
                } catch (NullPointerException npe ) {
                    System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!! - FROM");
                    System.out.println("Unable to parse FROM. " + Arrays.toString(message.getFrom()) );
                    System.out.println("Subject: " + subject);
                }
                try {
                    parsedDateStr =""; // clear any old value
                    parsedDateStr = message.getHeader("Date")[0];
                    receivedDate = parseDate("" + parsedDateStr);
                } catch (NullPointerException ignore) {
                    System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                    System.out.println("Unable to parse DATE: " + parsedDateStr + " Using prev: " + receivedDate );
                    //DJP:TODO Add print headers with date in them...
                    System.out.println("Subject: " + subject);
                    System.out.println("From: " + from);
                }
                if (receivedDate == null) { continue; }

                boolean delete = false;
                if(sixMonthsAgo.after( receivedDate) ) {
                    for (String oldEmail : sixMonthEmails) {
                        if (from.contains(oldEmail)) {
                            delete = true;
                            deleteCount++;
                            break;
                        }
                    }
                }
                if( !delete && OneYearAgo.after( receivedDate )){
                    for (String oldEmail : oneYearOld) {
                        if (from.contains(oldEmail)) {
                            delete = true;
                            deleteCount++;
                            break;
                        }
                    }
                }
                if( !delete && twoYearsAgo.after( receivedDate )){
                    for (String oldEmail : twoYearOld) {
                        if (from.contains(oldEmail)) {
                            delete = true;
                            deleteCount++;
                            break;
                        }
                    }
                }

                if (delete) {
                    System.out.println("---------------------------------");
                    System.out.println("Count: " + deleteCount);
                    System.out.println("Subject: " + subject);
                    System.out.println("From: " + from);
                    // set the DELETE flag to true
                    message.setFlag(Flags.Flag.DELETED, true);
                    System.out.println("Marked DELETE for message: " + subject);
                }
                if (deleteCount >= MAX_DELETE ) break;

            }
            // expunges the folder to remove messages which are marked deleted
            emailFolder.close(true);
            store.close();
            System.out.println("============================== ");
            System.out.println("Messages DELETED: " + deleteCount + "  last date: " + parsedDateStr);

        } catch (MessagingException | IOException e) {
            e.printStackTrace();
        }
    }

    private static Date parseDate(String dateString) {
        Date parsedDate = null;
        String datePattern1 = "EEE, d MMM yyyy HH:mm:ss";
        String datePattern2 = "d MMM yyyy HH:mm:ss";
        dateString = dateString.trim().replaceAll(" +", " ");
        String[] splited = dateString.split("\\s+");
        if(splited.length >= 4) {
            int findPos = dateString.indexOf(splited[4]);
            String datePart = dateString.substring(0, findPos).trim();

            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(datePattern1, Locale.ENGLISH);
                LocalDateTime dateTime = LocalDateTime.parse(datePart +" 00:00:00", formatter);
                parsedDate = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
            } catch (Exception e) {
                try {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern(datePattern2, Locale.ENGLISH);
                    LocalDateTime dateTime = LocalDateTime.parse(datePart , formatter);
                    parsedDate = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
                } catch (Exception e2) {
                    System.out.println("Error Parsing Date: " + dateString + " - " + e2.getMessage());
                    return null;
                }
            }
        }
        return parsedDate;
    }

}
