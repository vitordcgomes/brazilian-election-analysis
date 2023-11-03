import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.regex.Pattern;

import Election.Election;
import Election.Report.Report;

// how to compile/run (from 'project' directory):
// ant compile
// ant jar
// ant run-estadual || ant run-federal

public class App {
    public static void main(String[] args) {
        
        if (args.length != 4) {
            System.out.println("To run properly, use: java -jar deputies.jar --<office_option> <candidates_file_path> <poll_file_path> <election_date>");
            return;
        }
        
        String officeOption = args[0];
        String candidatesFilePath = args[1];
        String pollFilePath = args[2];
        String dateString = args[3];

        // Validate date format
        if (!Pattern.matches("\\d{2}/\\d{2}/\\d{4}", dateString)) {
            System.out.println("Invalid Date Format. Use dd/MM/yyyy");
            return;
        }

        LocalDate electionDate = null;
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.forLanguageTag("pt-BR"));

        try {
            // Parse the date string into a LocalDate object
            electionDate = LocalDate.parse(dateString, dateFormat);

        } catch (DateTimeParseException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }

        Election poll = new Election(electionDate, officeOption);
        CSVReader reader = new CSVReader(candidatesFilePath, pollFilePath);
        reader.candidatesReader(poll);
        reader.votesReader(poll);

        Report report = new Report(poll);
        report.report1(); 
        report.report2(); 
        report.report3(); 
        report.report4(); 
        report.report5(); 
        report.report6(); 
        report.report7();
        report.report8(); 
        report.report9(); 
        report.report10(); 

    }
}
