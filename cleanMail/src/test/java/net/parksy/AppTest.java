package net.parksy;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Locale;

/**
 * Unit test for simple App.
 */
public class AppTest
{
    private static final String DATE_STRING = "Fri, 20 May 2022 13:10:58 -0500 (CDT)";

    @Test
    void parseDate() {
        try {
            String[] splited = DATE_STRING.split("\\s+");
            if(splited.length >= 4) {
                System.out.println("splited[4] = " + splited[4]);
                int findPos = DATE_STRING.indexOf(splited[4]);
                String datePart = DATE_STRING.substring(0, findPos).trim();
                System.out.println("datePart = " + datePart);

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, d MMM yyyy", Locale.ENGLISH);
                LocalDate dateTime = LocalDate.parse(datePart, formatter);
                System.out.println("date = " + dateTime);
            }
        } catch (Exception e) {
            System.out.println("Error Parsing Date: " + DATE_STRING + " - " + e.getMessage());
        }
    }

    @Test
    void decodeUtf8() {

        byte[] decodedBytes = Base64.getDecoder().decode("?utf-8?b?q1ztifbob3rv?=");
        String decodedString = new String(decodedBytes);
        System.out.println("decodedString = " + decodedString);
    }
}
