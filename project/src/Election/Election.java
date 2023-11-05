/**
 * Represents an Election with candidates, parties, and associated results.
 */

package Election;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import Election.Domain.Candidate;
import Election.Domain.Party;

public class Election {
    private Map<Integer, Candidate> candidates;
    private Map<Integer, Party> parties;
    private LocalDate electionDate;
    private String officeOption;
    private int seats; // == number of electeds
    private int listVotes;
    private int nominalVotes;
    private int totalVotes;
    
    /**
     * Constructs an Election object with the specified election date and office option.
     *
     * @param electionDate The date of the election.
     * @param officeOption The office option for the election.
     */
    public Election(LocalDate electionDate, String officeOption) {
        this.candidates = new HashMap<Integer, Candidate>();
        this.parties = new HashMap<Integer, Party>();
        this.electionDate = electionDate;
        this.officeOption = officeOption;
        this.seats = 0;
    }

    /**
     * Returns a list of all candidates in the election.
     *
     * @return A linked list of Candidate objects.
     */
    public LinkedList<Candidate> getCandidates() {
        return new LinkedList<Candidate>(this.candidates.values());
    }

    /**
     * Returns a list of all parties in the election.
     *
     * @return A linked list of Party objects.
     */
    public LinkedList<Party> getParties() {        
        return new LinkedList<Party>(this.parties.values());
    }

    /**
     * Gets the candidate with the specified candidate number.
     *
     * @param number The candidate number.
     * @return The Candidate object with the specified candidate number.
     */
    public Candidate getCandidate(int number) {
        return candidates.get(number);
    }

    /**
     * Gets the party with the specified party number.
     *
     * @param number The party number.
     * @return The Party object with the specified party number.
     */
    public Party getParty(int number) {
        return parties.get(number);
    }

    /**
     * Adds votes to the election results based on the votable number, votes, and office option.
     *
     * @param votes         The number of votes to be added.
     * @param votableNumber The number associated with the votable entity.
     * @param office        The office option.
     */
    public void addVotes(int votes, int votableNumber, String office) {
        if (hasParty(votableNumber)) {
            Party p = parties.get(votableNumber);
            p.addVotes(votes);
            this.listVotes += votes;
        }
        else if (hasCandidate(votableNumber)) {
            Candidate c = candidates.get(votableNumber);
            Party p = parties.get(c.getPartyNumber());

            if (c.getVoteDestinationType().equalsIgnoreCase("Válido (legenda)")) {
                p.addVotes(votes);
                this.listVotes += votes;
            }
            else {
                if ((c.getCandidacyCondition() == 2 || c.getCandidacyCondition() == 16) && 
                    c.getVoteDestinationType().equalsIgnoreCase("Válido")) {
                        
                    c.addVotes(totalVotes);
                    p.addCandidateVotes(votes, votableNumber);
                    this.nominalVotes += votes;
                }
            }
            
        }
        else if (!hasCandidate(votableNumber) && !hasParty(votableNumber)) {
            // votableNumber to string
            String numberString = Integer.toString(votableNumber);

            // verify if has at least 2 characters
            if (numberString.length() >= 2) {
                // get first 2 digits = party number
                String twoDigits = numberString.substring(0, 2);

                // convert twoDigits back to number
                int partyNum = Integer.parseInt(twoDigits);

                if (hasParty(partyNum)) {
                    Party p = parties.get(partyNum);

                    if (p.hasDismissedCandidate(votableNumber)) {
                        p.addVotes(votes);
                        this.listVotes += votes;
                    }
                }
            }
        }
    }

    /**
     * Adds a party to the election if it does not already exist.
     *
     * @param number The party number.
     * @param p      The Party object to be added.
     */
    public void addParty(int number, Party p) {
        if (hasParty(number) == false) {
            parties.put(number, p);
        }
    }

    /**
     * Checks if a party with the specified number exists in the election.
     *
     * @param number The party number.
     * @return True if the party exists, false otherwise.
     */
    public boolean hasParty(int number) {
        return parties.containsKey(number);
    }

    /**
     * Adds a candidate to the election if they do not already exist and updates the number of seats if the candidate is elected.
     *
     * @param candidateNumber The candidate number.
     * @param c              The Candidate object to be added.
     */
    public void addCandidate(int candidateNumber, Candidate c) {
        if (hasCandidate(candidateNumber) == false) {
            candidates.put(candidateNumber, c);

            if (c.isElected()) seats += 1;
        }
    }

    /**
     * Checks if a candidate with the specified number exists in the election.
     *
     * @param number The candidate number.
     * @return True if the candidate exists, false otherwise.
     */
    public boolean hasCandidate(int number) {
        return candidates.containsKey(number);
    }

    /**
     * Adds a candidate to the corresponding party in the election.
     *
     * @param c The Candidate object to be added.
     */
    public void addCandidateToParty(Candidate c) {
        Party p = parties.get(c.getPartyNumber());
        p.addCandidate(c.getCandidateNumber(), c);
    }

    /**
     * Adds a dismissed candidate to the corresponding party in the election.
     *
     * @param c The Candidate object to be added.
     */
    public void addDismissedCandidateToParty(Candidate c) {
        Party p = parties.get(c.getPartyNumber());
        p.addDismissedCandidate(c.getCandidateNumber(), c);
    }

    /**
     * Gets the date of the election.
     *
     * @return The date of the election.
     */
    public LocalDate getElectionDate() {
        return electionDate;
    }

    /**
     * Gets the office option of the election.
     *
     * @return The office option.
     */
    public String getOfficeOption() {
        return officeOption;
    }

    /**
     * Gets the number of seats (elected candidates) in the election.
     *
     * @return The number of seats.
     */
    public int getSeats() {
        return seats;
    }

    /**
     * Sets the number of seats (elected candidates) in the election.
     *
     * @param seats The number of seats to be set.
     */
    public void setSeats(int seats) {
        this.seats = seats;
    }

    /**
     * Gets the total number of list votes in the election.
     *
     * @return The total number of list votes.
     */
    public int getListVotes() {
        return listVotes;
    }

    /**
     * Gets the total number of nominal votes in the election.
     *
     * @return The total number of nominal votes.
     */
    public int getNominalVotes() {
        return nominalVotes;
    }

    /**
     * Gets the total number of votes (list votes + nominal votes) in the election.
     *
     * @return The total number of votes.
     */
    public int getTotalVotes() {
        return totalVotes;
    }

    /**
     * Sets the total number of votes (list votes + nominal votes) in the election and updates the total votes for each party.
     */
    public void setTotalVotes() {
        this.totalVotes = this.listVotes + this.nominalVotes;

        for (Party p : parties.values()) {
            p.setTotalVotes();
        }
    }
}
