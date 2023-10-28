package Election.Domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Candidate {
    private int officeOption; //6 == federal; 7 == state
    private int candidateNumber;
    private String ballotName;
    private int partyNumber;
    private String partyAcronym;
    private int federationNumber; // -1 == isolated party
    private Date birthDate;
    private int gender; //2 male and 4 female
    private int turnStatus; //2 or 3 to elected
    private String voteDestinationType; //"Válido (Legenda)" when this votes go to "list votes"
    private int candidacyCondition; //2 or 16 to accepted candidacy
    private int nominalVotes;

    public Candidate(int office, int number, String ballotName, int partyNumber, String partyAcronym, int federationNumber, String birthDate,  int gender, int status, String voteDestination, int condition) {

        this.officeOption = office;
        this.candidateNumber = number;
        this.ballotName = ballotName;
        this.partyNumber = partyNumber;
        this.partyAcronym = partyAcronym;
        this.federationNumber = federationNumber;

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            this.birthDate = sdf.parse(birthDate);
        } catch (ParseException e) {
            System.out.println("Date parse exception error: " + e.getMessage());
        }

        this.gender = gender;
        this.turnStatus = status;
        this.voteDestinationType = voteDestination;
        this.candidacyCondition = condition;
    }

    public int getOfficeOption() {
        return officeOption;
    }

    public int getCandidateNumber() {
        return candidateNumber;
    }

    public String getBallotName() {
        return ballotName;
    }

    public int getPartyNumber() {
        return partyNumber;
    }

    public String getPartyAcronym() {
        return partyAcronym;
    }

    public int getFederationNumber() {
        return federationNumber;
    }
    
    public Date getBirthDate() {
        return birthDate;
    }

    public int getGender() {
        return gender;
    }

    public int getTurnStatus() {
        return turnStatus;
    }

    public String getVoteDestinationType() {
        return voteDestinationType;
    }

    public int getCandidacyCondition() {
        return candidacyCondition;
    }
}
