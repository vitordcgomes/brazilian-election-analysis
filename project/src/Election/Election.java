package Election;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import Election.Domain.Candidate;
import Election.Domain.Party;

public class Election {
    private Map<Integer, Candidate> candidates;
    private Map<String, Party> parties;
    private Date electionDate;
    private String officeOption;
    private int seats; // == number of electeds 
    

    public Election(Date electionDate, String officeOption) {
        this.candidates = new HashMap<Integer, Candidate>();
        this.parties = new HashMap<String, Party>();
        this.electionDate = electionDate;
        this.officeOption = officeOption;
        this.seats = 0;
    }

    public LinkedList<Candidate> getCandidates() {
        return new LinkedList<Candidate>(this.candidates.values()); // Linked List makes it easier to sort by vote afterwards
    }

    public LinkedList<Party> getParties() {
        return new LinkedList<Party>(this.parties.values());
    }

    public void addCandidate(int candidateNumber, Candidate c) {
        candidates.put(candidateNumber, c);
    }

    public Date getElectionDate() {
        return electionDate;
    }

    public String getOfficeOption() {
        return officeOption;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    @Override
    public String toString() {
        String result="";

        for (Candidate c : candidates.values()) {
            result += c.getBallotName() + ": (" + c.getCandidateNumber() + ") (" + c.getOfficeOption() + ")\n";
        }

        result += candidates.size();
        return result;
    }

}
