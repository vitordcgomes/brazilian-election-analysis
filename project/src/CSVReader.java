import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

// how to compile/run (from 'src' directory):
// compile: javac -d ../bin CSVReader.java
// run: java -cp ../bin CSVReader

public class CSVReader {
    public static void main(String[] args) {
        /*
        if (args.length != 4) {
            System.out.println("Uso correto: java -jar deputados.jar <opção_de_cargo> <caminho_arquivo_candidatos> <caminho_arquivo_votacao> <data>");
            return;
        }
        */

        String tipo = args[0];
        String arquivoCandidatos = args[1];
        String arquivoVotacao = args[2];
        String dataEleicaoString = args[3];

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date dataEleicao;
        
        try {
            dataEleicao = dateFormat.parse(dataEleicaoString);
        } catch (Exception e) {
            System.out.println("Formato de data inválido. Use dd/MM/yyyy");
            return;
        }

        System.out.println("Tipo: " + tipo);
        System.out.println("Arquivo de Candidatos: " + arquivoCandidatos);
        System.out.println("Arquivo de Votação: " + arquivoVotacao);
        System.out.println("Data da Eleição: " + dataEleicao);

        try (FileInputStream finCandidatos = new FileInputStream(arquivoCandidatos);
             Scanner sCandidatos = new Scanner(finCandidatos, "ISO-8859-1");
             FileInputStream finVotacao = new FileInputStream(arquivoVotacao);
             Scanner sVotacao = new Scanner(finVotacao, "ISO-8859-1")) {

            // Processar arquivo de consulta de candidatos
            while (sCandidatos.hasNextLine()) {
                String line = sCandidatos.nextLine();
                System.out.println("Processando linha de candidato: " + line);
                Scanner lineScanner = new Scanner(line);
                lineScanner.useDelimiter(";");

                while(lineScanner.hasNext()) {
                    String token = lineScanner.next();
                    System.out.println("Leu candidato: [" + token + "]");
                    // Faça o processamento dos dados do candidato aqui
                }

                lineScanner.close();
            }

            // Processar arquivo de seção de votação
            while (sVotacao.hasNextLine()) {
                String line = sVotacao.nextLine();
                System.out.println("Processando linha de votação: " + line);
                Scanner lineScanner = new Scanner(line);
                lineScanner.useDelimiter(";");

                while(lineScanner.hasNext()) {
                    String token = lineScanner.next();
                    System.out.println("Leu votação: [" + token + "]");
                    // Faça o processamento dos dados de votação aqui
                }

                lineScanner.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
