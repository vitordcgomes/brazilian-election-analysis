import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class App {
    public static void main(String[] args) {
        
        
        if (args.length != 4) {
            System.out.println("Correct use: java -jar deputados.jar <opção_de_cargo> <caminho_arquivo_candidatos> <caminho_arquivo_votacao> <data>");
            return;
        }
        
        String cargo = args[0];
        String arquivoCandidatos = args[1];
        String arquivoVotacao = args[2];
        String dataEleicao = args[3];

        // Validate date format
        if (!Pattern.matches("\\d{2}/\\d{2}/\\d{4}", dataEleicao)) {
            System.out.println("Invalid Date Format. Use dd/MM/yyyy");
            return;
        }

        System.out.println("Cargo: " + cargo);
        System.out.println("Arquivo de Candidatos: " + arquivoCandidatos);
        System.out.println("Arquivo de Votação: " + arquivoVotacao);
        System.out.println("Data da Eleição: " + dataEleicao);

        CSVReader reader = new CSVReader();
        reader.readerCandidates(arquivoCandidatos);

    }
}
