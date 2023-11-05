/**
 * This class generates various reports based on the provided Election data.
 */

package Election.Report;

import java.text.NumberFormat;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import Election.Election;
import Election.Domain.Candidate;
import Election.Domain.Party;

public class Report {
    private Election poll;
    private List<Candidate> sortCandidates;
    private List<Party> sortParties;
    private int seats;

    /**
     * Constructor for the Report class.
     *
     * @param poll The Election object to generate reports for.
     */
    public Report(Election poll) {
        this.poll = poll;
        this.seats = poll.getSeats();

        this.sortCandidates = poll.getCandidates();
        Collections.sort(this.sortCandidates);

        this.sortParties = poll.getParties();
        Collections.sort(this.sortParties);

    }

    /**
     * Generates a report showing the number of available seats in the election.
     */
    public void report1() {
        NumberFormat n = portugueseFormat();

        System.out.println("Número de vagas: " + n.format(poll.getSeats()) + "\n");
    }

    /**
     * Generates a report listing elected candidates for a specified office option.
     */
    public void report2() {
        NumberFormat n = portugueseFormat();

        if (poll.getOfficeOption().equals("--federal")) 
            System.out.println("Deputados federais eleitos:");

        else if (poll.getOfficeOption().equals("--estadual")) 
            System.out.println("Deputados estaduais eleitos:");

        int pos = 1;
        for (Candidate c : sortCandidates) {
            if (c.isElected()) {
                System.out.println(pos + " - " + c.changeName() + " (" + c.getPartyAcronym() + ", " + n.format(c.getNominalVotes()) + " votos)");
                pos++;
            }
        }
        System.out.print("\n");
    }

    /**
     * Generates a report listing the most voted candidates, respecting the number of available seats.
     */
    public void report3() {
        NumberFormat n = portugueseFormat();

        System.out.println("Candidatos mais votados (em ordem decrescente de votação e respeitando número de vagas):");

        int pos = 1; 
        for (Candidate c : sortCandidates) {
            if (pos > this.seats) break;
            System.out.println(pos + " - " + c.changeName() + " (" + c.getPartyAcronym() + ", " + n.format(c.getNominalVotes()) + " votos)");
            pos++;
        }

        System.out.print("\n");
    }

    /**
     * Generates a report listing candidates who would have been elected in a majoritarian system but weren't.
     */
    public void report4() {
        NumberFormat n = portugueseFormat();

        System.out.println("Teriam sido eleitos se a votação fosse majoritária, e não foram eleitos:\n(com sua posição no ranking de mais votados)");

        int pos = 1; 
        for (Candidate c : sortCandidates) {
            if (pos > this.seats) break;

            if (!c.isElected()) {
                System.out.println(pos + " - " + c.changeName() + " (" + c.getPartyAcronym() + ", " + n.format(c.getNominalVotes()) + " votos)");  
            }
            pos++;
            
        }

        System.out.print("\n");
    }

    /**
     * Generates a report listing elected candidates who benefited from the proportional system.
     */
    public void report5() {
        NumberFormat n = portugueseFormat();

        System.out.println("Eleitos, que se beneficiaram do sistema proporcional:\n(com sua posição no ranking de mais votados)");

        int pos = 1; 
        for (Candidate c : sortCandidates) {
            if (c.isElected() && pos > this.seats) {
                System.out.println(pos + " - " + c.changeName() + " (" + c.getPartyAcronym() + ", " + n.format(c.getNominalVotes()) + " votos)");
            }
            pos++;
        }

        System.out.print("\n");
    }

    /**
     * Generates a report showing the total votes and elected candidates for each party.
     */
    public void report6() {
        NumberFormat n = portugueseFormat();

        System.out.println("Votação dos partidos e número de candidatos eleitos:");

        int pos = 1;
        for (Party p : sortParties) {
            String votes = n.format(p.getNominalVotes());
            String qtdVotes =  n.format(p.getTotalVotes());

            System.out.print(pos + " - " + p.getPartyAcronym() + " - " + p.getPartyNumber() + ", " + n.format(p.getTotalVotes()));

            if (qtdVotes.equals("0")) System.out.print (" voto (" + votes);
            else System.out.print (" votos (" + votes);

            if (votes.equals("0")) System.out.print(" nominal e " + n.format(p.getListVotes()) + " de legenda), " + p.getNumberOfElecteds());
            else System.out.print(" nominais e " + n.format(p.getListVotes()) + " de legenda), " + p.getNumberOfElecteds());
            
            
            if (p.getNumberOfElecteds() > 1) System.out.println(" candidatos eleitos");
            else System.out.println(" candidato eleito");

            pos++;
        }

        System.out.print("\n");
    }

    /**
     * Generates a report listing the first and last placed candidates in each party.
     */
    public void report7() {
        NumberFormat n = portugueseFormat();

        System.out.println("Primeiro e último colocados de cada partido:");

        Candidate[] mostVoted = new Candidate[sortParties.size()];
        Candidate[] leastVoted = new Candidate[sortParties.size()];

        int i = 0;
        for (Party p : sortParties) {
            List<Candidate> partyCandidates = p.getCandidates();

            if (partyCandidates.size() >= 1 ) {
                Collections.sort(partyCandidates);

                if (partyCandidates.contains(partyCandidates.get(0)) && partyCandidates.contains(partyCandidates.get(partyCandidates.size() - 1))) {
                    Candidate first = partyCandidates.get(0);
                    Candidate last = partyCandidates.get(partyCandidates.size() - 1);

                    mostVoted[i] = first;
                    leastVoted[i] = last;
                    i++;
                }   
            }
        }
        int size = i;

        for (int j = 0; j < size; j++) {
            for (int k = j; k < size; k++) {
                if (mostVoted[k].getNominalVotes() > mostVoted[j].getNominalVotes()) {
                    Candidate aux = mostVoted[j];
                    Candidate aux2 = leastVoted[j];

                    mostVoted[j] = mostVoted[k];
                    leastVoted[j] = leastVoted[k];

                    mostVoted[k] = aux;
                    leastVoted[k] = aux2;
                }
                else if (mostVoted[k].getNominalVotes() == mostVoted[j].getNominalVotes()) {
                    if (mostVoted[k].getPartyNumber() < mostVoted[j].getPartyNumber()) {
                        Candidate aux = mostVoted[j];
                        Candidate aux2 = leastVoted[j];

                        mostVoted[j] = mostVoted[k];
                        leastVoted[j] = leastVoted[k];

                        mostVoted[k] = aux;
                        leastVoted[k] = aux2;
                    }
                }
            }
        }

        int pos = 1;
        String mostVotesString="";
        String leastVotesString="";

        for (int idx = 0; idx < size; idx++) {

            if (mostVoted[idx].getNominalVotes() > 1) {
                mostVotesString = "votos";
            }
            else mostVotesString = "voto";

            if (leastVoted[idx].getNominalVotes() > 1) {
                leastVotesString = "votos";
            }
            else leastVotesString = "voto";

            System.out.println(pos + " - " + mostVoted[idx].getPartyAcronym() + " - " + mostVoted[idx].getPartyNumber() + ", " + mostVoted[idx].getBallotName() + " (" + mostVoted[idx].getCandidateNumber() + 
                                ", " + n.format(mostVoted[idx].getNominalVotes()) + " " + mostVotesString +")" + " / " + leastVoted[idx].getBallotName() + 
                                " (" + leastVoted[idx].getCandidateNumber() + ", " + n.format(leastVoted[idx].getNominalVotes()) + " " + leastVotesString + ")");

            pos+=1;
        }

        System.out.print("\n");
    }

    /**
     * Generates a report showing the age groups of elected candidates.
     */
    public void report8() {
        NumberFormat nf = portugueseFormat();
        doubleFormat(nf);

        int[] ageGroup = new int[5];
        int sum = 0;

        System.out.println("Eleitos, por faixa etária (na data da eleição):");

        for (Candidate c : sortCandidates) {
            if (c.isElected()) {
                int age = c.getAge();

                if (age < 30) {
                    ageGroup[0] += 1;
                }
                else if (age >= 30 && age < 40) {
                    ageGroup[1] += 1;
                }
                else if (age >= 40 && age < 50) {
                    ageGroup[2] += 1;
                }
                else if (age >= 50 && age < 60) {
                    ageGroup[3] += 1;
                }
                else if (age >= 60) {
                    ageGroup[4] += 1;
                }

                sum += 1;

            }
        }

        double[] ageGroupPercentage = new double[5];
        for (int i = 0; i < 5; i++) {
            ageGroupPercentage[i] = ((double)ageGroup[i]) / ((double) sum) * 100;
        }

        System.out.println("      Idade < 30: " + ageGroup[0] + " (" + nf.format(ageGroupPercentage[0]) + "%)");
        System.out.println("30 <= Idade < 40: " + ageGroup[1] + " (" + nf.format(ageGroupPercentage[1]) + "%)");
        System.out.println("40 <= Idade < 50: " + ageGroup[2] + " (" + nf.format(ageGroupPercentage[2]) + "%)");
        System.out.println("50 <= Idade < 60: " + ageGroup[3] + " (" + nf.format(ageGroupPercentage[3]) + "%)");
        System.out.println("60 <= Idade     : " + ageGroup[4] + " (" + nf.format(ageGroupPercentage[4]) + "%)\n");

    }

    /**
     * Generates a report showing the gender distribution of elected candidates.
     */
    public void report9() {
        NumberFormat nf = portugueseFormat();
        doubleFormat(nf);

        int male = 0;
        int female = 0;

        for (Candidate c : sortCandidates) {
            if (c.isElected()) {
                if (c.getGender() == 2) {
                    male++;
                }
                else if (c.getGender() == 4) {
                    female++;
                }
            }
        }

        double malePercentage = (double)male / (double)poll.getSeats() * 100;
        double femalePercentage = (double)female / (double)poll.getSeats() * 100;

        System.out.println("Eleitos, por gênero:\n" + 
                            "Feminino:\t" + female + " (" + nf.format(femalePercentage) + "%)\n" + 
                            "Masculino:\t" + male + " (" + nf.format(malePercentage) + "%)\n");

    }

    /**
     * Generates a report summarizing the total votes, nominal votes, and list votes.
     */
    public void report10() {
        double nominalPercentage = (double)poll.getNominalVotes() / (double)poll.getTotalVotes() * 100;
        double listPercentage = (double)poll.getListVotes() / (double)poll.getTotalVotes() * 100;

        NumberFormat n = portugueseFormat();
        NumberFormat nf = portugueseFormat();
        doubleFormat(nf);

        System.out.println("Total de votos válidos:\t" + n.format(poll.getTotalVotes()) + 
                        "\nTotal de votos nominais:\t" + n.format(poll.getNominalVotes()) + " (" + nf.format(nominalPercentage) + "%)" + 
                        "\nTotal de votos de legenda:\t" + n.format(poll.getListVotes()) + " (" + nf.format(listPercentage) + "%)");
    }

    /**
     * Returns a NumberFormat instance for the Portuguese locale.
     *
     * @return A NumberFormat instance for Portuguese formatting.
     */
    public NumberFormat portugueseFormat() {
        return NumberFormat.getInstance(Locale.forLanguageTag("pt-BR"));
    }

    /**
     * Sets the minimum and maximum fraction digits for a NumberFormat instance.
     *
     * @param nf The NumberFormat instance to be formatted.
     */
    public void doubleFormat(NumberFormat nf) {
        nf.setMinimumFractionDigits(2); 
        nf.setMaximumFractionDigits(2);
    }   
}
