import java.io.FileInputStream;
import java.util.Scanner;

// how to compile/run (from 'src' directory):
// compile: javac -d ../bin CSVReader.java
// run: java -cp ../bin CSVReader

public class CSVReader {
    private String candidatesFilePath;
    private String pollFilePath;


    public CSVReader(String candidatesFilePath, String pollFilePath) {
        this.candidatesFilePath = candidatesFilePath;
        this.pollFilePath = pollFilePath;
    }

    public String getCandidatesFilePath() {
        return candidatesFilePath;
    }

    public String getPollFilePath() {
        return pollFilePath;
    }

    public void readerCandidates(String candidatesFile) {
        try (FileInputStream fin = new FileInputStream(candidatesFile);
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

    public void readerVotes(String votesFile) {
        return;
    }
}
