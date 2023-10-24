package Election.Domain;

import java.util.LinkedList;
import java.util.Map;

public class Party {
    private int officeOption; //6 == federal; 7 == state
    private int listVotes; //"votos de legenda"
    private int totalVotes; //listVotes + nominalVotes
    private int partyNumber;
    private String partyAcronym;
    private String partyName;
    private Map<Integer, Candidate> candidates;

    public LinkedList<Candidate> getCandidates() {
        return new LinkedList<Candidate>(this.candidates.values()); // Linked List makes it easier to sort by vote afterwards
    }
}
