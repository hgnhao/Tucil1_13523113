import java.io.*;
import java.util.*;

public class IO {

    public static int N, M, P;
    public static String S;
    public static List<Piece> pieces = new ArrayList<>();

    /* Fungsi untuk membaca file.txt */
    public static void readFile(String fileName) throws IOException {
        pieces.clear();
        try(BufferedReader br = new BufferedReader(new FileReader("../test/" + fileName))){

            // Baca line pertama untuk N, M, dan P
            String firstLine = br.readLine();
            if (firstLine == null) throw new IOException("Maaf, file yang diinput kosong.");

            StringTokenizer st = new StringTokenizer(firstLine);
            if (st.countTokens() < 3) throw new IOException("Maaf, format file salah. Harus ada N, M, dan P.");

            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());
            P = Integer.parseInt(st.nextToken());

            // Baca line berikutnya untuk dapet S
            S = br.readLine();
            if (S == null) throw new IOException("Maaf, format file salah! Tidak ada jenis kasus (S).");


            char currentPieceId = ' ';
            List<String> currentPieceStrings = new ArrayList<>();

            String line;
            while ((line = br.readLine()) != null){
                if (line.trim().isEmpty()){
                    if(!currentPieceStrings.isEmpty()){
                        pieces.add(new Piece(currentPieceId, currentPieceStrings));
                        currentPieceStrings = new ArrayList<>();
                        currentPieceId = ' ';
                    }
                    continue;
                }

                // Mencari non-space char pertama
                char firstNonSpace = ' ';
                for (char c : line.toCharArray()){
                    if (c != ' '){
                        firstNonSpace = c;
                        break;
                    }
                }

                // Jika new piece atau first piece
                if (currentPieceId == ' ' || (firstNonSpace != ' ' && firstNonSpace != currentPieceId)) {
                    if (!currentPieceStrings.isEmpty()) {
                        pieces.add(new Piece(currentPieceId, currentPieceStrings));
                        currentPieceStrings = new ArrayList<>();
                    }
                    currentPieceId = firstNonSpace;
                }

                currentPieceStrings.add(line);
            }

            if (!currentPieceStrings.isEmpty()){
                pieces.add(new Piece(currentPieceId, currentPieceStrings));
            }

            if (pieces.size() != P){
                throw new IOException("Seharusnya ada "+P+" pieces, tapi hanya ada" + pieces.size());
            } 

        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("File tidak ditemukan: " + fileName);
        } catch (IOException e) {
            throw new IOException("Kesalahan membaca file: " + e.getMessage());
        }   
    }
    
    /* Untuk pewarnaan board dengan ANSI */
    public static final String[] FORMATS = {
        "\u001B[1;31m",    
        "\u001B[1;32m",    
        "\u001B[1;33m",    
        "\u001B[1;34m",    
        "\u001B[1;35m",    
        "\u001B[1;36m",    
        "\u001B[31m",      
        "\u001B[32m",      
        "\u001B[33m",      
        "\u001B[34m",      
        "\u001B[35m",      
        "\u001B[36m",      
        "\u001B[1;91m",    
        "\u001B[1;92m",    
        "\u001B[1;93m",    
        "\u001B[1;94m",    
        "\u001B[1;95m",    
        "\u001B[1;96m",    
        "\u001B[91m",      
        "\u001B[92m",      
        "\u001B[93m",      
        "\u001B[94m",      
        "\u001B[95m",      
        "\u001B[96m",      
        "\u001B[1;37m",    
        "\u001B[37m"       
    };
    public static final String RESET = "\u001B[0m";
    
    /* Fungsi untuk menyimpan ke dalam file */
    public static void saveToFile(String fileName, Board board, long executionTime, long iterationCount, boolean hasSolution){
        try (PrintWriter writer = new PrintWriter(new FileWriter("../test/output_" + fileName))) {
            if (hasSolution) {
                writer.println("Solusi ditemukan!");
                writer.println("\nFinal board:");
                
                char[][] boardState = board.board;
                for (int i = 0; i < N; i++) {
                    for (int j = 0; j < M; j++) {
                        writer.print(boardState[i][j] + " ");
                    }
                    writer.println();
                }
            } else {
                writer.println("Maaf! Sepertinya tidak ada solusi.");
            }

            writer.println("Waktu pencarian: " + executionTime + " ms");
            writer.println("Banyak kasus yang ditinjau: " + iterationCount);
            
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    /* Fungsi untuk print board berwarna */
    public static void printColorBoard(Board board) {
        char[][] boardState = board.board;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                char piece = boardState[i][j];
                if (piece != '.') {
                    int formatIndex = piece - 'A';  // ngambil warna berdasar id
                    System.out.print(FORMATS[formatIndex] + piece + " " + RESET);
                } else {
                    System.out.print(piece + " ");
                }
            }
            System.out.println();
        }
    }

    
}
