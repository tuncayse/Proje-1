import java.util.Random;
import java.util.Scanner;
//Proje MineSweeper sınıfı içerisinde tasarlanmıştır.
public class MineSweeper {
    // Değişkenler
    private String[][] board; // Oyun Tahtası
    private String[][] mineLocations; // Mayınların Konumu
    private int numRows; // Satır sayısı
    private int numColumns; // Sütun sayısı
    private int numMines; // Mayın sayısı
    private int revealedCellCount;

    public MineSweeper(int numRows, int numColumns, int numMines) {
        // Oyunun Satır, Sütun ve Mayın sayısı ayarlanır
        this.numRows = numRows;
        this.numColumns = numColumns;
        this.numMines = numMines;
        // Açılan Hücre sayısı başlatılır
        this.revealedCellCount = 0;
        // Oyun tahtasını ve mayın konumlarını oluşturur ve başlatır
        this.board = new String[numRows][numColumns];
        this.mineLocations = new String[numRows][numColumns];
        initializeBoard();
    }
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        //Kullanıcıdan Dizi(matris) boyutu istenir.
        System.out.print("Satir Sayisi Giriniz : ");
        int numRows = input.nextInt();
        System.out.print("Sutun Sayisi Giriniz : ");
        int numColumns = input.nextInt();
        int numMines = (numRows * numColumns) / 4;

        MineSweeper game = new MineSweeper(numRows, numColumns, numMines);
        game.run();
    }
    //  Oyun tahtası ve mayın konumları bu metot aracılığıyla başlatılarak oyunun başlangıç durumu belirlenir.
    private void initializeBoard() {
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                board[i][j] = "-";
                mineLocations[i][j] = "";
            }
        }
        // Mayınlar rastgele yerleştirilir.
        Random random = new Random();
        int minesPlaced = 0; // Yerleştirilen mayın sayısını takip eden bir sayaç başlatılır.
        while (minesPlaced < numMines) {
            int row = random.nextInt(numRows);
            int col = random.nextInt(numColumns);
            // Eğer seçilen hücre daha önce mayın olarak işaretlenmediyse, bu hücreye bir mayın eklenir.
            if (mineLocations[row][col].equals("")) {
                mineLocations[row][col] = "*";
                minesPlaced++;
            }
        }
    }
    // Oyunu başlatır
    public void run() {
        Scanner input = new Scanner(System.in);
        boolean gameOver = false;

        System.out.println("Mayinlarin Konumu");
        displayMineLocations(); // Mayınların konumu gösterilir.

        System.out.println("===========================");
        System.out.println("Mayin Tarlasi Oyununa Hosgeldiniz !");
        displayBoard();

        while (!gameOver) {
            int row, col;
            do {
                // Kullanıcıdan işaretlemek istediği satır ve sütun bilgisi alınır.
                System.out.print("Satir Giriniz : ");
                row = input.nextInt();
                System.out.print("Sütun Giriniz : ");
                col = input.nextInt();
            } while (!isValidMove(row, col)); // Kullanıcının girdiği hamle geçerli değilse, tekrar girdi alınır.

            if (mineLocations[row][col].equals("*")) {
                gameOver = true;
                System.out.println("Game Over!!");// Kullanıcı mayına bastığında oyun sona erer ve "Game Over!" mesajı görüntülenir.
                revealMines();
            } else {
                int surroundingMines = countSurroundingMines(row, col); // Seçilen hücrenin etrafındaki mayın sayısı hesaplanır.
                board[row][col] = Integer.toString(surroundingMines);
                revealedCellCount++; // Açılan hücre sayısı bir artırılır.

                if (revealedCellCount == numRows * numColumns - numMines) {
                    gameOver = true;
                    System.out.println("Tebrikler Kazandınız!");// Tüm hücreler açıldığında (mayınları hariç), kazandınız mesajı görüntülenir.
                } else {
                    displayBoard(); //Oyunun her turunda oyun tahtası, kullanıcının son hamlesine göre güncellenir
                }
            }
        }
    }

    private boolean isValidMove(int row, int col) {
        // Geçerli bir hamle mi kontrol edilir.
        return row >= 0 && row < numRows && col >= 0 && col < numColumns && board[row][col].equals("-");
    }

    private int countSurroundingMines(int row, int col) {
        // Bir hücrenin etrafındaki mayınlar sayılır.
        int count = 0;
        int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};

        for (int i = 0; i < 8; i++) {
            int newRow = row + dx[i];
            int newCol = col + dy[i];

            if (newRow >= 0 && newRow < numRows && newCol >= 0 && newCol < numColumns && mineLocations[newRow][newCol].equals("*")) {
                count++;
            }
        }

        return count;
    }

    private void displayBoard() {
        // Tahta gösterilir
        System.out.println("===========================");
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("===========================");
    }

    private void revealMines() {
        // Mayınlar gösterilir
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                if (mineLocations[i][j].equals("*")) {
                    board[i][j] = "*";
                }
            }
        }
        displayBoard();
    }
    // Mayınların konumlarını gösteren haritayı ekrana bastırır.
    public void displayMineLocations() {
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                System.out.print(mineLocations[i][j] + " ");
            }
            System.out.println();
        }
    }
}