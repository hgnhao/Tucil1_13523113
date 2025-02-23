import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageOutput {

    /* Fungsi untuk menyimpan board ke image */
    public static void saveBoardAsImage(String fileName, Board board) {
        int cellSize = 50;
        int width = board.getN() * cellSize;
        int height = board.getM() * cellSize;

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();

        // Background
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);

        // Bikin board-boardnya
        char[][] boardState = board.board;
        for (int i = 0; i < board.getN(); i++) {
            for (int j = 0; j < board.getM(); j++) {
                char piece = boardState[i][j];
                if (piece != '.') {
                    int formatIndex = piece - 'A';          // Warna berdasarkan ID
                    g2d.setColor(getColor(formatIndex));
                    g2d.fillRect(j * cellSize, i * cellSize, cellSize, cellSize);
                    g2d.setColor(Color.BLACK);
                    g2d.drawRect(j * cellSize, i * cellSize, cellSize, cellSize);
                    g2d.setColor(Color.BLACK);
                    g2d.drawString(String.valueOf(piece), j * cellSize + cellSize / 2 - 5, i * cellSize + cellSize / 2 + 5);
                } else {
                    g2d.setColor(Color.LIGHT_GRAY);
                    g2d.fillRect(j * cellSize, i * cellSize, cellSize, cellSize);
                    g2d.setColor(Color.BLACK);
                    g2d.drawRect(j * cellSize, i * cellSize, cellSize, cellSize);
                }
            }
        }

        g2d.dispose();

        try {
            ImageIO.write(image, "png", new File("../test/output_" + fileName));
        } catch (IOException e) {
            System.out.println("Error writing image to file: " + e.getMessage());
        }
    }

    private static Color getColor(int index) {
        Color[] colors = {
            Color.RED, Color.GREEN, Color.YELLOW, Color.BLUE, Color.MAGENTA, Color.CYAN,
            Color.PINK, Color.ORANGE, Color.GRAY, Color.DARK_GRAY, Color.LIGHT_GRAY, Color.BLACK,
            new Color(255, 102, 102), new Color(102, 255, 102), new Color(255, 255, 102),
            new Color(102, 102, 255), new Color(255, 102, 255), new Color(102, 255, 255),
            new Color(255, 51, 51), new Color(51, 255, 51), new Color(255, 255, 51),
            new Color(51, 51, 255), new Color(255, 51, 255), new Color(51, 255, 255),
            Color.WHITE, Color.BLACK
        };
        return colors[index % colors.length];
    }
}