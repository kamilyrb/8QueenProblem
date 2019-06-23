import java.util.Random;

public class QueenProblem {

    static int[][] chessBoard,copyMatrices;
    static Random random;
    public static void main(String[] args) {
        random = new Random();
        chessBoard = new int[8][8];
        copyMatrices = new int[8][8];

        //10 defa problem oluşturup çözme işlemi
        for (int i = 0; i < 10; i++) {
            System.out.println("*********" + (i+1)+". Problem***********");
            clearMatrices();
            chessBoard = putQueensRandom();
            matriceCopy(copyMatrices, chessBoard);
            printMatrices();
            init();
            System.out.print("\n\n");
            printMatrices();
            System.out.print("\n\n");
        }


    }

    //Metodlaron genel işleyişi buradan yönetiliyor.İlk olarak elimizdeki matris bir çözüme ulaştırılmaya çalışılıyor.Eğer çözüme ulaştırılamazsa
    //matrisin ilk hali ele alınarak yeniden yerleştirme işlemlerine başlanıyor.Yani burada yeni bir random matris oluşturulmuyor, aksine mevcut matriste
    //çözüme gidilmeye çalışılıyor.
    public static void init(){
        try{
            rearrangeMatrices();
        }
        catch(StackOverflowError e){
            matriceCopy(chessBoard, copyMatrices);
            init();
        }


    }

    //Sorunun başlangıcında queen ler rasgele yerleştiriliyor.
    public static int[][] putQueensRandom(){
        int c = 0;
        while(c != 8)
        {
            int i =random.nextInt(8);
            int j = random.nextInt(8);
            if(chessBoard[i][j] == -1)
            {
                chessBoard[i][j] = 1;
                c++;
            }
        }
        return chessBoard;

    }


    //İlk olarak işaretleme amaçlı matrisin tüm elemanları -1 ile işaretleniyor.-1 bu algoritmada
    //queen yerleştirilebilir olarak kabul edilmiştir.
    public static void clearMatrices(){
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                chessBoard[i][j] = -1;
            }
        }
    }

    //Düzenleme işleminin ana metodu budur.Bu metod ve buradan çağırılan metodlar kural dışı queen yerleşimini
    //kontrol ederek kuralı bozan queenin yeniden yerleştirilmesini sağlamakta.
    public static void rearrangeMatrices(){

        for (int i = 0; i < 8; i++) {

            for (int j = 0; j < 8; j++) {
                if(chessBoard[i][j] == 1){
                    if(checkMatrices(i,j) == false)
                    {
                        i=0;
                        j=-1;
                    }
                }

            }

        }

    }

    public static boolean checkMatrices(int row,int column){

        //satır kontrolü
        for (int i = 0; i < 8; i++) {
            if(i == column)
                continue;
            else
            if(chessBoard[row][i] == -1)
                chessBoard[row][i] = 0;
            else if(chessBoard[row][i] == 1)
            {
                changeQueen(row,i,row,column);
                chessBoard[row][i] = 0;
                return false;
            }

        }

        //sütun kontrolü
        for (int i = 0; i < 8; i++) {
            if(i == row)
                continue;
            else
            if(chessBoard[i][column] == -1)
                chessBoard[i][column] = 0;
            else if(chessBoard[i][column] == 1)
            {
                changeQueen(i,column,row,column);
                chessBoard[i][column] = 0;
                return false;
            }

        }

        //çapraz kontrolü
        //sol üst
        for (int i = row -1,j=column -1; i>=0 && j>=0; i--,j--) {
            if(chessBoard[i][j] == -1)
                chessBoard[i][j] = 0;
            else if(chessBoard[i][j] ==1){
                changeQueen(i, j, row, column);
                chessBoard[i][j] = 0;
                return false;
            }

        }

        //sağ üst
        for (int i = row -1,j=column +1; i>=0 && j<8; i--,j++) {
            if(chessBoard[i][j] == -1)
                chessBoard[i][j] = 0;
            else if(chessBoard[i][j] ==1){
                changeQueen(i, j, row, column);
                chessBoard[i][j] = 0;
                return false;
            }

        }

        //sol alt
        for (int i = row +1,j=column -1; i<8 && j>=0; i++,j--) {
            if(chessBoard[i][j] == -1)
                chessBoard[i][j] = 0;
            else if(chessBoard[i][j] ==1){
                changeQueen(i, j, row, column);
                chessBoard[i][j] = 0;
                return false;
            }

        }
        //sağ alt
        for (int i = row +1, j=column + 1; i<8 && j<8; i++,j++) {
            if(chessBoard[i][j] == -1)
                chessBoard[i][j] = 0;
            else if(chessBoard[i][j] ==1){
                changeQueen(i, j, row, column);
                chessBoard[i][j] = 0;
                return false;
            }

        }
        return true;
    }


    //Bu metod kuralı bozan queen in indexlerini alarak bu queeni yeni bir indise taşır.
    public static void changeQueen(int i,int j,int dfrOfRow, int dfrOfColumn){
        int row = random.nextInt(8);
        int column = random.nextInt(8);
        if(row == i && column == j && row == dfrOfRow && column == dfrOfColumn)
            changeQueen(i,j,dfrOfRow,dfrOfColumn);
        if(chessBoard[row][column] == -1)
        {
            chessBoard[row][column] = 1;
        }
        else
            changeQueen(i,j,dfrOfRow,dfrOfColumn);

    }


    //Matrisi bastırma fonksiyonu
    public static void printMatrices(){
        for (int i = 0; i < chessBoard.length; i++) {
            for (int j = 0; j < chessBoard.length; j++) {
                if(chessBoard[i][j] != -1)
                    System.out.print(" ");
                System.out.print(chessBoard[i][j] + "  ");
            }
            System.out.println();
        }
    }


    public static void matriceCopy( int matricesOne[][], int matricesTwo[][]){
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                matricesOne[i][j] = matricesTwo[i][j];
            }
        }
    }

}

























