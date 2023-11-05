/**
 * This class reads CSV files containing candidate and poll information and processes the data for an election.
 */

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import Election.Election;
import Election.Domain.Candidate;
import Election.Domain.Party;
import java.io.File;

public class CSVReader {
    private String candidatesFilePath;
    private String pollFilePath;
    
    /**
     * Constructor for the CSVReader class.
     *
     * @param candidatesFilePath The file path for the candidates CSV file.
     * @param pollFilePath       The file path for the poll CSV file.
     * @throws IllegalArgumentException if either candidates file or poll file does not exist.
     */
    public CSVReader(String candidatesFilePath, String pollFilePath) {

        File candidatesFile = new File(candidatesFilePath);
        File pollFile = new File(pollFilePath);

        if (!candidatesFile.exists() && pollFile.exists()) {
            throw new IllegalArgumentException("Candidates file does not exist!\npath: " + candidatesFilePath + "");
        }
        else if(!pollFile.exists() && candidatesFile.exists()) {
            throw new IllegalArgumentException("Poll file does not exist!\npath: " + pollFilePath + "");
        }
        else if (!candidatesFile.exists() && !pollFile.exists()) {
            throw new IllegalArgumentException("Both Candidates and Poll files does not exist!\ncandidatesFilePath: " + candidatesFilePath +
                                                "\npollFilePath: " + pollFilePath + "\n");
        }

        this.candidatesFilePath = candidatesFilePath;
        this.pollFilePath = pollFilePath;
    }

    /**
     * Gets the file path of the candidates CSV file.
     *
     * @return The file path of the candidates CSV file.
     */
    public String getCandidatesFilePath() {
        return candidatesFilePath;
    }

    /**
     * Gets the file path of the poll CSV file.
     *
     * @return The file path of the poll CSV file.
     */
    public String getPollFilePath() {
        return pollFilePath;
    }

    /**
     * Reads and processes candidate information from a CSV file.
     *
     * @param poll The Election object to which candidate information will be added.
     */
    public void candidatesReader(Election poll) {
        try (FileInputStream fin = new FileInputStream(this.candidatesFilePath);
            InputStreamReader isr = new InputStreamReader(fin, "ISO-8859-1");
            BufferedReader br = new BufferedReader(isr)) {

            String[] token = new String[80];
            int currentRow = 0;
            int officeOption = 0;

            if (poll.getOfficeOption().equals("--estadual")) {
                officeOption = 7;
            } else {
                officeOption = 6;
            }

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");
                
                for (int i = 0; i < parts.length && i < token.length; i++) {
                    token[i] = parts[i].replace("\"", "");
                }

                if (currentRow != 0) { // row != csv header && officeOption == same as command line input

                    int partyNumber = stringToInt(token[27]);           /* "NR_PARTIDO" */
                    String partyAcronym = token[28];                    /* "SG_PARTIDO" */
                    String partyName = token[29];                       /* "NM_PARTIDO" */
                    Party p = new Party(partyNumber, partyAcronym, partyName);
                    poll.addParty(partyNumber, p); 

                    if (stringToInt(token[13]) == officeOption /* "CD_CARGO" */) {

                        int candidateNumber = stringToInt(token[16]);       /* "NR_CANDIDATO" */
                        String candidateBallotName = token[18];             /* "NM_URNA_CANDIDATO" */
                        int federationNumber = stringToInt(token[30]);      /* "NR_FEDERACAO" */
                        String birthDate = token[42];                       /* "DT_NASCIMENTO" */
                        int gender = stringToInt(token[45]);                /* "CD_GENERO" */
                        int turnStatus = stringToInt(token[56]);            /* "CD_SIT_TOT_TURNO" */
                        String voteDestinationType = token[67];             /* "NM_TIPO_DESTINACAO_VOTOS" */
                        int candidacyCondition = stringToInt(token[68]);    /* "CD_SITUACAO_CANDIDATO_TOT" */

                            
                        if (candidacyCondition == 2 || candidacyCondition == 16) {
                            Candidate c = new Candidate(officeOption, candidateNumber, candidateBallotName, partyNumber, partyAcronym, 
                                                    federationNumber, birthDate, gender, turnStatus, voteDestinationType, candidacyCondition, poll.getElectionDate());

                            poll.addCandidateToParty(c);
                            poll.addCandidate(candidateNumber, c);
                        }
                        else if (candidacyCondition != 2 && candidacyCondition != 16 && voteDestinationType.equals("Válido (legenda)")) {
                            Candidate c = new Candidate(officeOption, candidateNumber, candidateBallotName, partyNumber, partyAcronym, 
                                                    federationNumber, birthDate, gender, turnStatus, voteDestinationType, candidacyCondition, poll.getElectionDate());

                            poll.addDismissedCandidateToParty(c);
                        }
                    }

                }
                currentRow++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
}

    /**
     * Reads and processes vote information from a CSV file.
     *
     * @param poll The Election object to which vote information will be added.
     */
    public void votesReader(Election poll) {
        try (FileInputStream fin = new FileInputStream(this.pollFilePath);
            InputStreamReader isr = new InputStreamReader(fin, "ISO-8859-1");
            BufferedReader br = new BufferedReader(isr)) {

            String[] token = new String[30];
            int currentRow = 0;
            int officeOption = 0;
            String officeString = "";

            if (poll.getOfficeOption().equals("--estadual")) {
                officeOption = 7;
                officeString = "--estadual";
            } else {
                officeOption = 6;
                officeString = "--federal";
            }

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");

                for (int i = 0; i < parts.length && i < token.length; i++) {
                    token[i] = parts[i].replace("\"", "");
                }

                if (currentRow != 0 && stringToInt(token[17]) == officeOption) { // row != csv header && officeOption == same as command line input

                    int votableNumber = stringToInt(token[19]);         /* "NR_VOTAVEL" */
                    int totalVotes = stringToInt(token[21]);            /* "QT_VOTOS" */

                    if (votableNumber != 95 && votableNumber != 96 && votableNumber != 97 && votableNumber != 98) {
                        poll.addVotes(totalVotes, votableNumber, officeString);
                    }

                }
                currentRow++;
            }

            poll.setTotalVotes();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Converts a string to an integer, if possible. Otherwise, prints exception.
     *
     * @param str The string to be converted.
     * @return The integer value of the string, or 0 if the conversion fails.
     */
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
