import java.util.*;

public class Solver {
    
    public Board board;
    private List<Piece> pieces;
    public Board getBoard() { return board; }

    public Solver(int N, int M, List<Piece> pieces){
        this.board = new Board(N, M);
        this.pieces = pieces;
    }

    public boolean solve() {
        return solveRecursive(0);           // mulai dari piece pertama
    }

    /* Algoritma rekursif untuk mencoba seluruh kemungkinan dan mencari solusi */
    private boolean solveRecursive(int pieceIndex) {
        /* Basis */
        if (pieceIndex >= pieces.size()) {
            return board.isFull();
        }
    
        if (pieceIndex == 0) {
            System.out.println("\nMencari solusi...");
        }
    
        Piece piece = pieces.get(pieceIndex);
    
        /* Mencoba seluruh kemungkinan */
        for (int x = 0; x < board.getN(); x++) {
            for (int y = 0; y < board.getM(); y++) {
                if (tryPieceWithTransformations(piece, x, y, pieceIndex)) {
                    return true;
                }
            }
        }
    
        /* Kasus tidak ada solusi */
        if (pieceIndex == 0) {
            System.out.println("\nMaaf! Tidak ada solusi yang ditemukan.");
        }
        return false;
    }

    private long countIteration = 0;
    public long getCountIteration(){
        return countIteration;
    }

    /* Fungsi bantu untuk mencoba seluruh kemungkinan */
    private boolean tryPieceWithTransformations(Piece piece, int x, int y, int pieceIndex) {
        countIteration++;
    
        // ORI BOS
        if (tryPlacePiece(piece, x, y, pieceIndex)) {
            return true;
        }
    
        // Mulai berotasi~
        Piece currentPiece = piece;
        for (int rot = 1; rot < 4; rot++) {
            currentPiece = rotatePiece(currentPiece);
            if (tryPlacePiece(currentPiece, x, y, pieceIndex)) {
                return true;
            }
        }
    
        // Bercermin dan rotasi~
        currentPiece = flipVertical(piece);
        if (tryPlacePiece(currentPiece, x, y, pieceIndex)) {
            return true;
        }
    
        for (int rot = 1; rot < 4; rot++) {
            currentPiece = rotatePiece(currentPiece);
            if (tryPlacePiece(currentPiece, x, y, pieceIndex)) {
                return true;
            }
        }
    
        return false;
    }
    
    private boolean tryPlacePiece(Piece piece, int x, int y, int pieceIndex) {
        if (board.canPlacePiece(piece, x, y)) {
            board.placePiece(piece, x, y);
    
            // Jika berhasil, dilanjutkan ke piece berikutnya
            if (solveRecursive(pieceIndex + 1)) {
                return true;
            }
    
            // Jika gagal, piece dihapus dari papan
            board.removePiece(piece, x, y);
        }
        return false;
    }


    /* FUNGSI BANTU TRANSFORMASI */

    // Rotasi 90 derajat clockwise
    private Piece rotatePiece(Piece piece) {
        List<int[]> newShape = new ArrayList<>();
        
        for (int[] coord : piece.shape) {
            int newX = -coord[1];
            int newY = coord[0];
            newShape.add(new int[]{newX, newY});
        }
    
        return Piece.fromCoordinates(piece.id, normalizeShape(newShape));
    }
    
    // Flip vertical
    private Piece flipVertical(Piece piece) {
        List<int[]> newShape = new ArrayList<>();
        int maxY = 0;
        for (int[] coord : piece.shape) {
            maxY = Math.max(maxY, coord[1]);
        }
        for (int[] coord : piece.shape) {
            newShape.add(new int[]{coord[0], maxY - coord[1]});
        }
        return Piece.fromCoordinates(piece.id, normalizeShape(newShape));
    }

    /* Fungsi normalisasi */
    private List<int[]> normalizeShape(List<int[]> shape) {
        int minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE;
    
        for (int[] coord : shape) {
            minX = Math.min(minX, coord[0]);
            minY = Math.min(minY, coord[1]);
        }
    
        List<int[]> normalizedShape = new ArrayList<>();
        for (int[] coord : shape) {
            normalizedShape.add(new int[]{coord[0] - minX, coord[1] - minY});
        }
    
        return normalizedShape;
    }
}
