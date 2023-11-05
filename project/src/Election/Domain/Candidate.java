/**
 * Represents a candidate in an election, containing relevant information such as candidate number, party affiliation,
 * birthdate, gender, and election results.
 */

package Election.Domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public class Candidate implements Comparable<Candidate> {
    private int officeOption;
    private int candidateNumber;
    private String ballotName;
    private int partyNumber;
    private String partyAcronym;
    private int federationNumber;
    private LocalDate birthDate;
    private int gender;
    private int turnStatus;
    private String voteDestinationType;
    private int candidacyCondition;
    private int nominalVotes;
    private int age;

    /**
     * Constructor for the Candidate class.
     *
     * @param office           The office option (6 for federal, 7 for state).
     * @param number           The candidate number.
     * @param ballotName       The candidate's ballot name.
     * @param partyNumber      The number of the party the candidate belongs to.
     * @param partyAcronym     The acronym of the party the candidate belongs to.
     * @param federationNumber The federation number of the candidate (-1 if isolated party).
     * @param birthDate        The birth date of the candidate.
     * @param gender           The gender of the candidate (2 for male, 4 for female).
     * @param status           The turn status of the candidate (2 or 3 for elected).
     * @param voteDestination  The type of vote destination for the candidate.
     * @param condition        The candidacy condition of the candidate (2 or 16 for accepted candidacy).
     * @param electionDate     The date of the election.
     */
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
                System.out.println("Birth date is empty or null. Candidate: " + this.ballotName + " (" + this.candidateNumber + ")");
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

    /**
     * Calculates the age of the candidate based on the birth date and the election date.
     *
     * @param birthDate    The birth date of the candidate.
     * @param electionDate The date of the election.
     * @return The age of the candidate.
     */
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
    
    /**
     * Adds votes to the nominal votes count of the candidate.
     *
     * @param votes The number of votes to be added.
     */
    public void addVotes(int votes) {
        this.nominalVotes += votes;
    }

    /**
     * Gets the office option for the candidate.
     *
     * @return The office option (6 for federal, 7 for state).
     */
    public int getOfficeOption() {
        return officeOption;
    }

    /**
     * Gets the candidate number.
     *
     * @return The candidate number.
     */
    public int getCandidateNumber() {
        return candidateNumber;
    }

    /**
     * Gets the candidate's ballot name.
     *
     * @return The candidate's ballot name.
     */
    public String getBallotName() {
        return ballotName;
    }

    /**
     * Gets the number of the party the candidate belongs to.
     *
     * @return The party number.
     */
    public int getPartyNumber() {
        return partyNumber;
    }

    /**
     * Gets the acronym of the party the candidate belongs to.
     *
     * @return The party acronym.
     */
    public String getPartyAcronym() {
        return partyAcronym;
    }

    /**
     * Gets the federation number of the candidate.
     *
     * @return The federation number (-1 if isolated party).
     */
    public int getFederationNumber() {
        return federationNumber;
    }
    
    /**
     * Gets the birth date of the candidate.
     *
     * @return The birth date.
     */
    public LocalDate getBirthDate() {
        return birthDate;
    }

    /**
     * Gets the gender of the candidate.
     *
     * @return The gender (2 for male, 4 for female).
     */
    public int getGender() {
        return gender;
    }

    /**
     * Gets the turn status of the candidate.
     *
     * @return The turn status (2 or 3 for elected).
     */
    public int getTurnStatus() {
        return turnStatus;
    }

    /**
     * Gets the type of vote destination for the candidate.
     *
     * @return The vote destination type.
     */
    public String getVoteDestinationType() {
        return voteDestinationType;
    }

    /**
     * Gets the candidacy condition of the candidate.
     *
     * @return The candidacy condition (2 or 16 for accepted candidacy).
     */
    public int getCandidacyCondition() {
        return candidacyCondition;
    }

    /**
     * Gets the total number of nominal votes received by the candidate.
     *
     * @return The total number of nominal votes.
     */
    public int getNominalVotes() {
        return nominalVotes;
    }

    /**
     * Gets the age of the candidate.
     *
     * @return The age of the candidate.
     */
    public int getAge() {
        return age;
    }

    /**
     * Checks if the candidate is elected.
     *
     * @return True if the candidate is elected, false otherwise.
     */
    public boolean isElected() {
        if (turnStatus == 2 || turnStatus == 3) {
            return true;
        }
        else return false;
    }

    /**
     * Changes the candidate's name to indicate they belong to an isolated party.
     *
     * @return The modified candidate name.
     */
    public String changeName() {
        if (this.federationNumber != -1) {
            return "*" + this.ballotName;
        }
        else return this.ballotName;
    }

    /**
     * Compares this candidate to another candidate based on nominal votes and age.
     *
     * @param o The Candidate object to be compared.
     * @return A negative integer if this candidate has fewer nominal votes or, 
     *         if votes are equal, a positive integer if this candidate is younger.
     */
    @Override
    public int compareTo(Candidate o) {
        int voteResult = Integer.compare(o.nominalVotes, this.nominalVotes);

        if (voteResult != 0) return voteResult;

        int ageResult = Integer.compare(o.age, this.age);
        return ageResult;
    }
}
