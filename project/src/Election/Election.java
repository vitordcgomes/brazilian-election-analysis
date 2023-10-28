package Election;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import Election.Domain.Candidate;
import Election.Domain.Party;

public class Election {
    private Map<Integer, Candidate> candidates;
    private Map<Integer, Party> parties;
    private Date electionDate;
    private String officeOption;
    private int seats; // == number of electeds
    private int listVotes;
    private int nominalVotes;
    private int totalVotes;
    

    public Election(Date electionDate, String officeOption) {
        this.candidates = new HashMap<Integer, Candidate>();
        this.parties = new HashMap<Integer, Party>();
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

    public Candidate getCandidate(int number) {
        return candidates.get(number);
    }

    public Party getParty(int number) {
        return parties.get(number);
    }

    public void addVotes(int votes, int votableNumber) {
        if (hasParty(votableNumber)) {
            Party p = parties.get(votableNumber);
            p.addVotes(votes);
            this.listVotes += votes;
        }
        else if (hasCandidate(votableNumber)) {
            Candidate c = candidates.get(votableNumber);
            Party p = parties.get(c.getPartyNumber());

            if (c.getVoteDestinationType().equals("Válido (legenda)")) {
                p.addVotes(votes);
                this.listVotes += votes;
            }
            else {
                if (c.getCandidacyCondition() == 2 || c.getCandidacyCondition() == 16) {
                    c.addVotes(totalVotes);
                    p.addCandidateVotes(votes, votableNumber);
                    this.nominalVotes += votes;
                }
            }
            
        }
    }

    public void addParty(int number, Party p) {
        if (hasParty(number) == false) {
            parties.put(number, p);
        }
    }

    public boolean hasParty(int number) {
        return parties.containsKey(number);
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
        Party p = parties.get(c.getPartyNumber());
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

        result += candidates.size() + "\n";

        result += "list votes: " + listVotes + "\nnominal votes: " + nominalVotes + "\ntotal votes: " + totalVotes + "\n";
        result += candidates.get(5512) + "\n";

        return result;
    }

}
