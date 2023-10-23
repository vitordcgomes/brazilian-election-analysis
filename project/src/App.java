import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class App {
    public static void main(String[] args) {
        
        
        if (args.length != 4) {
            System.out.println("Correct use: java -jar deputies.jar <office_option> <candidates_file_path> <poll_file_path> <election_date>");
            return;
        }
        
        String office = args[0];
        String candidatesFilePath = args[1];
        String pollFilePath = args[2];
        String electionDate = args[3];

        // Validate date format
        if (!Pattern.matches("\\d{2}/\\d{2}/\\d{4}", electionDate)) {
            System.out.println("Invalid Date Format. Use dd/MM/yyyy");
            return;
        }

        System.out.println("Cargo: " + office);
        System.out.println("Arquivo de Candidatos: " + candidatesFilePath);
        System.out.println("Arquivo de Votação: " + pollFilePath);
        System.out.println("Data da Eleição: " + electionDate);

        CSVReader reader = new CSVReader(candidatesFilePath, pollFilePath);
        reader.readerCandidates(candidatesFilePath);

    }
}
