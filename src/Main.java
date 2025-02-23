import java.util.Scanner;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        System.out.println("Selamat datang di IQ Puzzler Solver!\n");
        Scanner userInput = new Scanner(System.in);
        boolean fileReadSuccess = false;
        Solver solver = null;
        long startTime = 0, endTime = 0;
        boolean hasSolution = false;

        /* Proses membaca file */
        while (!fileReadSuccess) {
            try {
                System.out.print("Masukkan nama file (.txt): ");
                String filePath = userInput.nextLine();

                IO.readFile(filePath);
                fileReadSuccess = true;
            } catch (FileNotFoundException e) {
                System.out.println("Error: File tidak ditemukan!\nSilakan coba lagi.\n");
            } catch (Exception e) {
                System.out.println("Error: File tidak valid atau format salah!\nSilakan coba lagi.\n");
            }
        }

        if (!fileReadSuccess) {
            System.out.println("Program dihentikan karena file tidak valid.");
            userInput.close();
            return;
        }

        solver = new Solver(IO.N, IO.M, IO.pieces);
        startTime = System.currentTimeMillis();
        hasSolution = solver.solve();
        endTime = System.currentTimeMillis();

        /* Mencari solusi */
        if (solver != null) {
            if (hasSolution) {
                System.out.println("\nSolusi ditemukan!");
                System.out.println("\nFinal board:\n");
                IO.printColorBoard(solver.getBoard());
            }

            /* Data waktu dan banyak kasus yang ditinjau */
            System.out.println("\nWaktu pencarian: " + (endTime - startTime) + " ms");
            System.out.println("Banyak kasus yang ditinjau: " + solver.getCountIteration());

            String saveChoice;
            boolean validChoice = false;

            /* Prompt untuk menyimpan solusi */
            while (!validChoice) {
                System.out.print("\nApakah Anda ingin menyimpan solusi ke dalam file? (Y/N): ");
                saveChoice = userInput.nextLine().toUpperCase();

                if (saveChoice.equals("Y")) {
                    System.out.print("Masukkan nama file (.txt): ");
                    String savePath = userInput.nextLine();
                    IO.saveToFile(savePath, solver.getBoard(), (endTime-startTime), solver.getCountIteration(), hasSolution);
                    System.out.println("File berhasil disimpan!");
                    validChoice = true;
                } else if (saveChoice.equals("N")) {
                    validChoice = true;
                } else {
                    System.out.println("Input tidak valid! Mohon masukkan Y atau N.");
                }
            }

            String imageChoice;
            validChoice = false;
            while (!validChoice) {
                System.out.print("\nApakah Anda ingin menyimpan solusi ke dalam image? (Y/N): ");
                imageChoice = userInput.nextLine().toUpperCase();

                if (imageChoice.equals("Y")) {
                    System.out.print("Masukkan nama file (.png): ");
                    String savePath = userInput.nextLine();
                    ImageOutput.saveBoardAsImage(savePath, solver.getBoard());
                    System.out.println("Image berhasil disimpan!");
                    validChoice = true;
                } else if (imageChoice.equals("N")) {
                    validChoice = true;
                } else {
                    System.out.println("Input tidak valid! Mohon masukkan Y atau N.");
                }
            }
        }

        System.out.println("\nTerima kasih sudah menggunakan program ini!");
        userInput.close();
    }
}
