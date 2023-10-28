package Election.Domain;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Party {
    private int partyNumber;
    private String partyAcronym;
    private String partyName;
    private int numberOfCandidates;
    private int listVotes; //"votos de legenda"
    private int nominalVotes; 
    private int totalVotes; //listVotes + nominalVotes

    private Map<Integer, Candidate> candidates;

    public Party(int number, String acronym, String name) {
        this.candidates = new HashMap<Integer, Candidate>();
        this.partyNumber = number;
        this.partyAcronym = acronym;
        this.partyName = name;
        this.numberOfCandidates = 0;
        this.listVotes = 0;
        this.nominalVotes = 0;
        this.totalVotes = 0;
    }

    public void addCandidate(int candidateNumber, Candidate c) {
        candidates.put(candidateNumber, c);
        numberOfCandidates+=1;
    }

    public void addVotes(int votes) {
        this.listVotes += votes;
    }

    public void addCandidateVotes(int votes, int candidateNumber) {
        Candidate c = candidates.get(candidateNumber);
        c.addVotes(votes);
        this.nominalVotes += votes;
    }
    
    public LinkedList<Candidate> getCandidates() {
        return new LinkedList<Candidate>(this.candidates.values()); // Linked List makes it easier to sort by vote afterwards
    }

    public int getPartyNumber() {
        return partyNumber;
    }

    public String getPartyAcronym() {
        return partyAcronym;
    }

    public String getPartyName() {
        return partyName;
    }

    public int getNumberOfCandidates() {
        return numberOfCandidates;
    }

    public int getListVotes() {
        return listVotes;
    }

    public int getNominalVotes() {
        return nominalVotes;
    }

    public int getTotalVotes() {
        return totalVotes;
    }

    public void setTotalVotes(int listVotes, int nominalVotes) {
        this.totalVotes = listVotes + nominalVotes;
    }

    @Override
    public String toString() {
        String result="";
        
        result = partyName + " (" + partyAcronym + "): " + partyNumber + " => " + candidates.size() + " candidatos, " + this.totalVotes + " votes.\n";

        for (Candidate c : candidates.values()) {
            result += "     " + c;
        }
        
        return result;
    }
}
