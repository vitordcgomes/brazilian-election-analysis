import java.io.FileInputStream;
import java.util.Scanner;

import Election.Election;
import Election.Domain.Candidate;

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
            
            String[] token = new String[100];
            int currentRow = 0;
            int officeOption = 0;

            if (poll.getOfficeOption().equals("--estadual")) {
                officeOption = 7;
            }
            else officeOption = 6;

            while (s.hasNextLine()) {
                String line = s.nextLine();
                System.out.println("Processando linha: " + line);
                Scanner lineScanner = new Scanner(line);
                lineScanner.useDelimiter(";");
                int i = 0;

                while(lineScanner.hasNext()) {
                    // ADD TRY-CATCH (TOKEN INDEX)

                    token[i] = lineScanner.next().replace("\"", "");

                    //System.out.println("Leu: [" + token[i] + "]");
                    i++;
                }

                if (currentRow != 0 && Integer.parseInt(token[13]) == officeOption) {
                    int candidateNumber = Integer.parseInt(token[16]); 
                    String candidateBallotName = token[18];
                    Candidate c = new Candidate(candidateNumber, candidateBallotName, officeOption);
                    poll.addCandidate(candidateNumber, c);
                }

                currentRow++;
                lineScanner.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        System.out.println(poll);
        System.out.println(poll.getOfficeOption());
    }

    public void votesReader(Election poll) {
        return;
    }
}
