package models;

import java.io.*;
import java.time.LocalDate;

/*
    This class stores the User's recent activity, then stores that info into a file then sends it back.
    This works same as banking apps that send monthly reports 
*/
public class Report {
    final File path = new File("./event_files");
    final String brackets = "=".repeat(10);

    public String newUserSpecial(User user){
        LocalDate todaysDate = LocalDate.now();
        return String.format(
            "%s: Hello %s! Thank you for trying this amazing market platform. As a thank you gift, you will be given $5!",
            todaysDate.toString(), user.getUsername()
        );
    }

    public boolean saveReportUser(User user, String details) throws IOException{

        LocalDate todaysDate = LocalDate.now();

        final String fileName = String.format("%s%d.txt", user.getUsername(), user.getId());

        FileOutputStream fileStream = new FileOutputStream(fileName, true);
        PrintWriter writer = new PrintWriter(fileStream);

        String content = String.format("%s%n%s: %s%n%s", this.brackets, todaysDate.toString(), details, this,brackets);
        writer.println(content);

        writer.close();
        fileStream.close();
        return true;   
    }

    public boolean saveReportCompany(Company company, String details) throws IOException{

        LocalDate todaysDate = LocalDate.now();

        final String fileName = String.format("%s%d.txt", company.getName(), company.getId());

        FileOutputStream fileStream = new FileOutputStream(fileName, true);
        PrintWriter writer = new PrintWriter(fileStream);
        
        String content = String.format("%s%n%s: %s%n%s", this.brackets, todaysDate.toString(), details, this,brackets);
        writer.println(content);

        writer.close();
        fileStream.close();
        return true;   
    }
}
