/**
 * This class represents a political party in an election.
 */

package Election.Domain;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Party implements Comparable<Party> {
    private int partyNumber;
    private String partyAcronym;
    private String partyName;
    private int numberOfCandidates;
    private int listVotes; //"votos de legenda"
    private int nominalVotes; 
    private int totalVotes; //listVotes + nominalVotes

    private Map<Integer, Candidate> candidates;
    private Map<Integer, Candidate> dismissedCandidates;

    /**
     * Constructor for the Party class.
     *
     * @param number   The party number.
     * @param acronym  The party acronym.
     * @param name     The name of the party.
     */
    public Party(int number, String acronym, String name) {
        this.candidates = new HashMap<Integer, Candidate>();
        this.dismissedCandidates = new HashMap<Integer, Candidate>();
        this.partyNumber = number;
        this.partyAcronym = acronym;
        this.partyName = name;
        this.numberOfCandidates = 0;
        this.listVotes = 0;
        this.nominalVotes = 0;
        this.totalVotes = 0;
    }

    /**
     * Adds a candidate to the party's list of candidates.
     *
     * @param candidateNumber The candidate number.
     * @param c               The Candidate object to be added.
     */
    public void addCandidate(int candidateNumber, Candidate c) {
        if (candidates.containsKey(candidateNumber) == false) {
            candidates.put(candidateNumber, c);
            numberOfCandidates+=1;
        }
    }

    /**
     * Adds a dismissed candidate to the party's list of dismissed candidates.
     *
     * @param candidateNumber The candidate number.
     * @param c               The Candidate object to be added.
     */
    public void addDismissedCandidate(int candidateNumber, Candidate c) {
        if (dismissedCandidates.containsKey(candidateNumber) == false) {
            dismissedCandidates.put(candidateNumber, c);
        }
    }

    /**
     * Checks if the party has a dismissed candidate with the specified candidate number.
     *
     * @param candidateNumber The candidate number.
     * @return True if the party has a dismissed candidate with the specified number, false otherwise.
     */
    public boolean hasDismissedCandidate(int candidateNumber) {
        return dismissedCandidates.containsKey(candidateNumber);
    }

    /**
     * Adds votes to the party's list votes count.
     *
     * @param votes The number of votes to be added.
     */
    public void addVotes(int votes) {
        this.listVotes += votes;
    }

    /**
     * Adds votes to a specific candidate of the party.
     *
     * @param votes           The number of votes to be added.
     * @param candidateNumber The candidate number.
     */
    public void addCandidateVotes(int votes, int candidateNumber) {
        Candidate c = candidates.get(candidateNumber);
        c.addVotes(votes);
        this.nominalVotes += votes;
    }
    
    /**
     * Gets a linked list of all candidates belonging to the party.
     *
     * @return A linked list of Candidate objects.
     */
    public LinkedList<Candidate> getCandidates() {
        return new LinkedList<Candidate>(this.candidates.values()); // Linked List makes it easier to sort by vote afterwards
    }

    /**
     * Gets the party number.
     *
     * @return The party number.
     */
    public int getPartyNumber() {
        return partyNumber;
    }

    /**
     * Gets the party acronym.
     *
     * @return The party acronym.
     */
    public String getPartyAcronym() {
        return partyAcronym;
    }

    /**
     * Gets the party name.
     *
     * @return The party name.
     */
    public String getPartyName() {
        return partyName;
    }

    /**
     * Gets the number of candidates belonging to the party.
     *
     * @return The number of candidates.
     */
    public int getNumberOfCandidates() {
        return numberOfCandidates;
    }

    /**
     * Gets the number of elected candidates belonging to the party.
     *
     * @return The number of elected candidates.
     */
    public int getNumberOfElecteds() {
        int sum = 0;

        for (Candidate c : candidates.values()) {
            if (c.isElected()) sum++;
        }

        return sum;
    }

    /**
     * Gets the total number of list votes received by the party.
     *
     * @return The total number of list votes.
     */
    public int getListVotes() {
        return listVotes;
    }

    /**
     * Gets the total number of nominal votes received by the party.
     *
     * @return The total number of nominal votes.
     */
    public int getNominalVotes() {
        return nominalVotes;
    }

    /**
     * Gets the total number of votes received by the party from both list and nominal votes.
     *
     * @return The total number of votes.
     */
    public int getTotalVotes() {
        return totalVotes;
    }

    /**
     * Sets the total number of votes received by the party from both list and nominal votes.
     */
    public void setTotalVotes() {
        this.totalVotes = this.listVotes + this.nominalVotes;
    }

    /**
     * Compares this party to another party based on total votes and party number.
     *
     * @param o The Party object to be compared.
     * @return A negative integer if this party has fewer total votes than o or, 
     *         if votes are equal, a positive integer if this party has a greater party number.
     */
    @Override
    public int compareTo(Party o) {
        if (o.totalVotes == this.totalVotes) {
            return Integer.compare(this.partyNumber, o.partyNumber);
        }
        return Integer.compare(o.totalVotes, this.totalVotes);
    }

}
