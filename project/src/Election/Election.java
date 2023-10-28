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

    public void addParty(String acronym, Party p) {
        if (hasParty(acronym) == false) {
            parties.put(acronym, p);
        }
    }

    public boolean hasParty(String acronym) {
        return parties.containsKey(acronym);
    }

    public void addCandidate(int candidateNumber, Candidate c) {
        if (hasCandidate(candidateNumber) == false) {
            candidates.put(candidateNumber, c);
        }
    }

    public boolean hasCandidate(int number) {
        return candidates.containsKey(number);
    }

    public void addCandidateToParty(Candidate c) {
        Party p = parties.get(c.getPartyAcronym());
        p.addCandidate(c.getCandidateNumber(), c);
        //System.out.println("number: " + c.getCandidateNumber() + "\n");
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
            result += c.getBallotName() + ": (NR: " + c.getCandidateNumber() + ") (OF: " + c.getOfficeOption() + ") (PNR: " + c.getPartyNumber() 
                        + ") (PSG: " + c.getPartyAcronym() + ") (FNR: " + c.getFederationNumber() + ") (BD: " + c.getBirthDate() + ") (GN: " + c.getGender() 
                        + ") (TS: " + c.getTurnStatus() + ") (VD: " + c.getVoteDestinationType() + ") (CD: " + c.getCandidacyCondition() + ")\n";
        }

        int sum = 0;

        for (Party p : parties.values()) {
            result += p.toString();
            sum += p.getNumberOfCandidates();
        }
        result += parties.size() + " (" + sum + ")\n";

        result += candidates.size();
        return result;
    }

}
