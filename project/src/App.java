import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import Election.Election;

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

        Date electionDate = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        try {
            // Parse the date string into a Date object
            electionDate = dateFormat.parse(dateString);

        } catch (ParseException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }

        Election poll = new Election(electionDate, officeOption);
        CSVReader reader = new CSVReader(candidatesFilePath, pollFilePath);
        reader.candidatesReader(poll);
        //reader.votesReader(poll);

        System.out.println("Cargo: " + poll.getOfficeOption());
        System.out.println("Arquivo de Candidatos: " + candidatesFilePath);
        System.out.println("Arquivo de Votação: " + pollFilePath);
        System.out.println("Data da Eleição: " + poll.getElectionDate());

    }
}
