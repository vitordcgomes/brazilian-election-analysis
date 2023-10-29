import java.io.FileInputStream;
import java.util.Scanner;

import Election.Election;
import Election.Domain.Candidate;
import Election.Domain.Party;

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
            
            String[] token = new String[80];
            int currentRow = 0;
            int officeOption = 0;

            if (poll.getOfficeOption().equals("--estadual")) {
                officeOption = 7;
            }
            else officeOption = 6;

            while (s.hasNextLine()) {

                String line = s.nextLine();
                Scanner lineScanner = new Scanner(line);
                lineScanner.useDelimiter(";");

                int i = 0;

                while(lineScanner.hasNext()) {

                    try {
                        token[i] = lineScanner.next().replace("\"", "");
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Invalid array index access error: " + e.getMessage());
                    }

                    i++;
                }   

                if (currentRow != 0) { // row != csv header && officeOption == same as command line input

                    int partyNumber = stringToInt(token[27]);           /* "NR_PARTIDO" */
                    String partyAcronym = token[28];                    /* "SG_PARTIDO" */
                    String partyName = token[29];                       /* "NM_PARTIDO" */
                    Party p = new Party(partyNumber, partyAcronym, partyName);
                    poll.addParty(partyNumber, p); // os partidos "sumidos" nem chegam a entrar aqui

                    if (stringToInt(token[13]) == officeOption /* "CD_CARGO" */) {

                        int candidateNumber = stringToInt(token[16]);       /* "NR_CANDIDATO" */
                        String candidateBallotName = token[18];             /* "NM_URNA_CANDIDATO" */
                        int federationNumber = stringToInt(token[30]);      /* "NR_FEDERACAO" */
                        String birthDate = token[42];                       /* "DT_NASCIMENTO" */
                        int gender = stringToInt(token[45]);                /* "CD_GENERO" */
                        int turnStatus = stringToInt(token[56]);            /* "CD_SIT_TOT_TURNO" */
                        String voteDestinationType = token[67];             /* "NM_TIPO_DESTINACAO_VOTOS" */
                        int candidacyCondition = stringToInt(token[68]);    /* "CD_SITUACAO_CANDIDATO_TOT" */

                        Candidate c = new Candidate(officeOption, candidateNumber, candidateBallotName, partyNumber, partyAcronym, 
                                                    federationNumber, birthDate, gender, turnStatus, voteDestinationType, candidacyCondition);
                            
                        if (candidacyCondition == 2 || candidacyCondition == 16) {
                            poll.addCandidateToParty(c);
                            poll.addCandidate(candidateNumber, c);
                        }
                        else if (candidacyCondition != 2 && candidacyCondition != 16 && voteDestinationType.equals("Válido (legenda)")) {
                            poll.addDismissedCandidateToParty(c);
                        }
                    }
        
                }
                currentRow++;
                lineScanner.close();

            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void votesReader(Election poll) {
        try (FileInputStream fin = new FileInputStream(this.pollFilePath);
        Scanner s = new Scanner(fin, "ISO-8859-1")) {

            String[] token = new String[30];
            int currentRow = 0;
            int officeOption = 0;
            String officeString="";

            if (poll.getOfficeOption().equals("--estadual")) {
                officeOption = 7;
                officeString = "--estadual";
            }
            else  {
                officeOption = 6;
                officeString = "--federal";
            }

            while (s.hasNextLine()) {
                String line = s.nextLine();
                Scanner lineScanner = new Scanner(line);
                lineScanner.useDelimiter(";");

                int i = 0;

                while(lineScanner.hasNext()) {

                    try {
                        token[i] = lineScanner.next().replace("\"", "");
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.out.println("Invalid array index access error: " + e.getMessage());
                    }

                    i++;
                }                  

                if (currentRow != 0 && stringToInt(token[17]) == officeOption /* "CD_CARGO" */) { // row != csv header && officeOption == same as command line input

                
                    int votableNumber = stringToInt(token[19]);         /* "NR_VOTAVEL" */
                    int totalVotes = stringToInt(token[21]);            /* "QT_VOTOS" */

                    if (votableNumber != 95 && votableNumber != 96 && votableNumber != 97 && votableNumber != 98) { // blank, invalid or spoiled votes
                        poll.addVotes(totalVotes, votableNumber, officeString); 
                    }
                    
                }
                currentRow++;
                lineScanner.close();
            }

            poll.setTotalVotes();
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int stringToInt(String str) {
        int value = 0;

        try {
            value =  Integer.parseInt(str);
        }
        catch (NumberFormatException nfe){
            System.out.println("Error converting String (" + str + ") to Integer: " + nfe.getMessage());
        }

        return value;
    }
}
