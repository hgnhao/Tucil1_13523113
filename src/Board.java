public class Board {
    public char[][] board;
    public int N,M;            // dimensi board N x M

    public int getN(){ return N; }
    public int getM(){ return M; }

    /* Inisialisasi board dengan . */
    public Board(int N, int M){
        this.N = N;
        this.M = M;
        this.board = new char[N][M];

        for(int i = 0; i < N; i++){
            for(int j = 0; j < M; j++){
                board[i][j] = '.';
            }
        }
    }

    /* Fungsi menentukan bisa pasang piece atau tidak */
    public boolean canPlacePiece(Piece piece, int x, int y){
        for (int[] coordinate : piece.shape){
            int newCoordinateX = x + coordinate[0];
            int newCoordinateY = y + coordinate[1];

            if (newCoordinateX < 0 || newCoordinateX >= N || newCoordinateY < 0 || newCoordinateY >= M){
                return false;
            }

            if (board[newCoordinateX][newCoordinateY] != '.'){
                return false;
            }
        }

        return true;
    }

    /* Fungsi untuk memasang piece */
    public void placePiece(Piece piece, int x, int y){
        for(int[] coordinate : piece.shape){
            board[x + coordinate[0]][y + coordinate[1]] = piece.id;
        }
    }

    /* Fungsi untuk menghapus piece */
    public void removePiece(Piece piece, int x, int y){
        for(int[] coordinate : piece.shape){
            board[x + coordinate[0]][y + coordinate[1]] = '.';
        }
    }

    /* Prosedur untuk print board state saat ini jika dibutuhkan*/
    public void printBoard(){
        for(int i = 0; i < N; i++){
            for(int j = 0; j < M; j++){
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    /* Fungsi untuk menentukan apakah board sudah full atau belum */
    public boolean isFull(){
        for(int i = 0; i < N; i++){
            for(int j = 0; j < M; j++){
                if(board[i][j] == '.'){
                    return false;
                }
            }
        }
        return true;
    }

}
