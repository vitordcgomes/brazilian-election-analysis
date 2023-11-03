package Election.Domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public class Candidate implements Comparable<Candidate> {
    private int officeOption; //6 == federal; 7 == state
    private int candidateNumber;
    private String ballotName;
    private int partyNumber;
    private String partyAcronym;
    private int federationNumber; // -1 == isolated party
    private LocalDate birthDate;
    private int gender; //2 male and 4 female
    private int turnStatus; //2 or 3 to elected
    private String voteDestinationType; //"Válido (Legenda)" when this votes go to "list votes"
    private int candidacyCondition; //2 or 16 to accepted candidacy
    private int nominalVotes;
    private int age;


    public Candidate(int office, int number, String ballotName, int partyNumber, String partyAcronym, int federationNumber, String birthDate,  int gender, int status, String voteDestination, int condition, LocalDate electionDate) {

        this.officeOption = office;
        this.candidateNumber = number;
        this.ballotName = ballotName;
        this.partyNumber = partyNumber;
        this.partyAcronym = partyAcronym;
        this.federationNumber = federationNumber;

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.forLanguageTag("pt-BR"));

        try {
            if (birthDate != null && !birthDate.isEmpty()) {
                this.birthDate = LocalDate.parse(birthDate, dateFormat);
                this.age = calculateAge(this.birthDate, electionDate);
            } else {
                System.out.println("Birth date is empty or null. Candidate: " + this.ballotName);
            }
        } catch (DateTimeParseException e) {
            System.out.println("Date parse exception error: " + e.getMessage());
        }


        this.gender = gender;
        this.turnStatus = status;
        this.voteDestinationType = voteDestination;
        this.candidacyCondition = condition;
        this.nominalVotes = 0;
    }

    public int calculateAge(LocalDate birthDate, LocalDate electionDate) {
        int birthYear = birthDate.getYear();
        int electionYear = electionDate.getYear();
    
        int age = electionYear - birthYear;
    
        int birthMonth = birthDate.getMonthValue();
        int electionMonth = electionDate.getMonthValue();
    
        int birthDay = birthDate.getDayOfMonth();
        int electionDay = electionDate.getDayOfMonth();
    
        if (electionMonth < birthMonth || (electionMonth == birthMonth && electionDay < birthDay)) {
            age -= 1;
        }
    
        return age;
    }
    

    public void addVotes(int votes) {
        this.nominalVotes += votes;
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
    
    public LocalDate getBirthDate() {
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

    public int getNominalVotes() {
        return nominalVotes;
    }

    public int getAge() {
        return age;
    }

    public boolean isElected() {
        if (turnStatus == 2 || turnStatus == 3) {
            return true;
        }
        else return false;
    }

    public String changeName() {
        if (this.federationNumber != -1) {
            return "*" + this.ballotName;
        }
        else return this.ballotName;
    }

    @Override
    public String toString() {
        String result="";

        result = ballotName + " (" + candidateNumber + "): " + partyAcronym + " (" + partyNumber + ") (votes: " + nominalVotes + ")\n";

        return result;
    }

    @Override
    public int compareTo(Candidate o) {
        // Returns < 0 if o < this
        // Returns 0 if o == this
        // returns > 0 if o > this
        int voteResult = Integer.compare(o.nominalVotes, this.nominalVotes);

        if (voteResult != 0) return voteResult;

        int ageResult = Integer.compare(o.age, this.age);
        return ageResult;
    }
}
