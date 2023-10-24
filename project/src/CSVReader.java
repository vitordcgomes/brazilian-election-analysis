import java.io.FileInputStream;
import java.util.Scanner;

import Election.Election;

// how to compile/run (from 'src' directory):
// compile: javac -d ../bin CSVReader.java
// run: java -cp ../bin CSVReader

public class CSVReader {
    private String candidatesFilePath;
    private String pollFilePath;


    public CSVReader(String candidatesFilePath, String pollFilePath) {

        //verify try-catch exceptions (file does not exist... )

        this.candidatesFilePath = candidatesFilePath;
        this.pollFilePath = pollFilePath;
    }

    public String getCandidatesFilePath() {
        return candidatesFilePath;
    }

    public String getPollFilePath() {
        return pollFilePath;
    }

    public void candidatesReader(Election poll) {
        try (FileInputStream fin = new FileInputStream(this.candidatesFilePath);
                Scanner s = new Scanner(fin, "ISO-8859-1")) {

            while (s.hasNextLine()) {
                String line = s.nextLine();
                System.out.println("Processando linha: " + line);
                Scanner lineScanner = new Scanner(line);
                lineScanner.useDelimiter(";");

                while(lineScanner.hasNext()) {
                    String token = lineScanner.next();
                    System.out.println("Leu: [" + token + "]");
                }

                lineScanner.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    
    }

    public void votesReader(Election poll) {
        return;
    }
}
