package Election.Domain;

import java.util.Date;

public class Candidate {
    private int officeOption; //6 == federal; 7 == state
    private int candidacyCondition; //2 or 16 to accepted candidacy
    private int candidateNumber;
    private String ballotName;
    private int partyNumber;
    private String partyAcronym;
    private int federationNumber; // -1 == isolated party
    private Date birthDate;
    private int turnStatus; //2 or 3 to elected
    private int gender; //2 male and 4 female
    private String voteDestinationType; //"Válido (Legenda)" when this votes go to "list votes"
    private int nominalVotes;
}
