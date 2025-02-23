import java.util.ArrayList;
import java.util.List;

public class Piece {
    char id;                // Menjadi identitas blok A-Z, P <= 26
    List<int[]> shape;      // Daftar koordinat relatif setiap piece

    public Piece(char id, List<String> shapeStrings){
        this.id = id;
        this.shape = parseShape(shapeStrings);
    }

    public Piece(Piece other) {
        this.id = other.id;
        this.shape = new ArrayList<>();
        for (int[] coord : other.shape) {
            this.shape.add(new int[]{coord[0], coord[1]});
        }
    }

    public static Piece fromCoordinates(char id, List<int[]> coordinates) {
        Piece piece = new Piece(id, new ArrayList<String>());
        piece.shape = coordinates;
        return piece;
    }

    /* Mengubah bentuk puzzle dari List<String> menjadi List<int[]> (koordinat relatif) */
    public List<int[]> parseShape(List<String> shapeStrings) {
        List<int[]> coordinate = new ArrayList<>();
        for(int i = 0; i < shapeStrings.size(); i++){
            for(int j = 0; j < shapeStrings.get(i).length(); j++){
                if(shapeStrings.get(i).charAt(j) == id){
                    coordinate.add(new int[]{i,j});
                }
            }
        }

        return coordinate;
    }

    /* Fungsi untuk debugging jika perlu */
    public void printPiece(){
        System.out.println("piece: " + id);
        for(int[] coordinate: shape){
            System.out.println("(" + coordinate[0] + "," + coordinate[1] + ")");
        }
    }

}
