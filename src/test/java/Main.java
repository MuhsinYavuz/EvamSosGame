import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


public class Main {
        static Scanner scanner = new Scanner(System.in);
        static Random random = new Random();
        static int column ;
        static int row ;
        static boolean continueGame = true ;
        static Players playerReel ;
        static Players playerBot ;
        static Players nowPlayer ;
        static String[][] matrix ;
        static boolean nowPlayerControl ;
        static int rowScanner ;
        static int columnScanner ;
        static int selectRandomRow  ;
        static int selectRandomColumn ;
        static boolean isEarnedPoint ;

    public static void main(String args[]) throws InterruptedException {
        System.out.println("SOS oyununa hoş geldiniz ");
        System.out.println("Lütfen oyuncu isminizi giriniz ");
        // gerçek oyuncu ismini alındı.
        String reelPlayerName = scanner.nextLine() ;
        System.out.println(reelPlayerName + " tekrar hoş geldin");
        // bilgisayara karşı oynandığı için final olarak kalıcak.
        final String botPlayerName = "Bot" ;
        playerReel = new Players(reelPlayerName,0 ) ;
        playerBot = new Players(botPlayerName, 0) ;
        // matrix oluşturmak için metot çağrıldı.
        createMatrix();
        // kullanıcı sırasını almak için boş bir nowPlayer oluşturuldu.
        nowPlayer = selectStartPlayer() ;
        // kullanıcılar için harf seçimi yapıldı .
        selectLetter();
        System.out.println("Oyun başladı");
        // matrixleri yazdırma işlemi
        printMatrix();
        // oyunun ilgili metotları çağırması
        playGame();

    }
    public static void createMatrix(){
        // matris için girilen değerlerin int olup olmadığını kontrol eden kısım
        try{
            System.out.println("Şimdi oyun alanını oluşturmak için ");
            System.out.println("Lütfen satır sayısını giriniz ");
            while(!scanner.hasNextInt()){
                System.out.println("Lütfen geçerli bir satır sayısını giriniz ");
                scanner.nextLine();
            }
            row = scanner.nextInt() ;

            System.out.println("Lütfen sütun sayısını giriniz ");
            while(!scanner.hasNextInt()){
                System.out.println("Lütfen geçerli bir sütun sayısını giriniz ");
                scanner.nextLine();
            }
            column = scanner.nextInt() ;
            // boş matrix oluşturuldu.
            matrix = new String[row][column];
            for(int i = 0 ; i<row; i++){
                for(int j =0  ;j<column ; j++){
                    matrix[i][j] = "_" ;
                }}
            // Istenilen projeye uygunluk boyutu kontrolu.
            if((row < 3 || row > 7) || (column<3 || column > 7) ){
                System.out.println("Hatalı matris girişi . Matris  " +
                        "minimum 3x3 maksimum 7x7 büyüklüğünde olmalıdır.");
                createMatrix();
            }

        }catch (Exception e ){
            // Hata yakalama.
            System.out.println("Hata: " + e.getLocalizedMessage());
            System.out.println("Matrix minimum 3x3 maksimum 7x7 büyüklüğünde olmalıdır.");
        }

    }
    public static void printMatrix(){
        // matrisi ekrana yazdıran program .
        for(int i = 0 ; i<row; i++){
            for(int j =0  ;j<column ; j++){
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }
    public static void selectLetter() throws InterruptedException {
        // Kullanıcılar baştan belirlenen harflerle oyuna devam eder .
        // kullanıcılara rastgele harf verilir.
        int selectRandomLetter  = random.nextInt(2);
        if(selectRandomLetter == 0 ){
            playerReel.setLetter("s");
            playerBot.setLetter("o");
        }else{
            playerReel.setLetter("o");
            playerBot.setLetter("s");
        }
        // verilen harfleri gösteren yer .
        System.out.println(playerBot.getName() + " oyuncusunun kullanacağı harf : " + playerBot.getLetter());
        System.out.println(playerReel.getName() + " oyuncusunun kullanacağı harf : " + playerReel.getLetter());
        Thread.sleep(2000);
    }
    public static Players selectStartPlayer(){
        // oyuna kimin başlıcağına random karar verilir .
        // 0 yada 1 olmak üzere 2 seçeneğe göre başlangıç oyuncusu döndürülür .
        int selectPlayer  = random.nextInt(2);
        if(selectPlayer == 0){
            System.out.println("Oyuna " + playerReel.getName() +  " başlayacaktır .");
            return playerReel ;
        }
        else{
            System.out.println("Oyuna " + playerBot.getName() + " başlayacaktır.");
            return playerBot ;
        }
    }
    public static Players selectNextPlayer(){
        // Bir sonraki kullanıcıya geçmek için kullanılır .
        // Eğer kullanıcı sos yapamassa diğer kullanıcıya geçer
        // Control ile
        nowPlayerControl = true ;
        // now player o anki kullanıcı gösteriyordu . botsa reel reel ise bota geçiş yapar .
        if(nowPlayer == playerReel){
            nowPlayer = playerBot ;
            return playerBot ;
        }
        else  {
            nowPlayer = playerReel;
            return playerReel ;
        }
    }
    public static void botPlayerEmptyMatrix(){
        // bot hangi alana işlem yapacağı rastgele belirlenir
        // rastgele olması için sınır dğeerler matrise göre belirlendi .
        selectRandomRow  = random.nextInt(row);
        selectRandomColumn = random.nextInt(column);

        while(true){
            // seçilen yer boş ise oraya ekle
            if(matrix[selectRandomRow][selectRandomColumn].equals("_")){
                matrix[selectRandomRow][selectRandomColumn] = nowPlayer.getLetter();
                break  ;
            }else{
                // eğer boş değil ise boş bulana kadar ara .
                botPlayerEmptyMatrix();
                break;
            }

        }
    }

    public static void reelPlayerEmptyMatrix(){
        System.out.println("Nereye eklemek istersiniz ? " +
                "satır sayısını giriniz");
        // satır sayısının int değer olması kontrolu yapılır
        while(!scanner.hasNextInt()){
            System.out.println("Lütfen geçerli bir satır giriniz");
            scanner.nextLine();
        }
        // satır sayısının 0 dan küçük ve matris değerinin boyutunu aşmamasını sağlar.
        while (true){
            rowScanner = scanner.nextInt();
            if (rowScanner < 0 || rowScanner > (row - 1)){
                System.out.println("Lütfen geçerli bir satır değeri giriniz");
            }else{
                break;
            }
        }
        // sütun sayısının int değer olması kontrolu yapılır
        System.out.println("Sütun sayısını giriniz");
        while(!scanner.hasNextInt()){
            System.out.println("Lütfen geçerli bir sütun giriniz");
            scanner.nextLine();
        }
        // sutun sayısının 0 dan küçük ve matris değerinin boyutunu aşmamasını sağlar.
        while (true){

            columnScanner = scanner.nextInt() ;
            if (columnScanner < 0 || columnScanner > (column - 1)){
                System.out.println("Lütfen geçerli bir sütun değeri giriniz");
            }else{
                break;
            }
        }

        while(true){
            // eğer seçilen alan boş ise ekleme yapar.
            if(matrix[rowScanner][columnScanner].equals("_")){
                matrix[rowScanner][columnScanner] = nowPlayer.getLetter();
                break  ;
            }else{
                // boş değilse mesaj döndürür ve tekrar seçim yaptırır .
                System.out.println("Seçtiğiniz alan doludur.  Lütfen boş bir alan seçiniz . ");
                reelPlayerEmptyMatrix();
                break;
            }

        }
    }

    public static void earningPoint(Players players) throws InterruptedException {
        // Player bir değer almawsı gerektiğinden bu alana göre seçim yapması sağlanır .
        int earningRow  = 0 ;
        int earningColumn = 0 ;
        // temp değeri bir elde ne kadar puan aldığını belirler .
        int tempPoint = 0  ;
        // oynanan el puan alıp almadığını belirtmek için kullanılır .
        isEarnedPoint = false ;

        // eğer gelen oyuncu bot veya reel ise ona göre satır ve sutun değerleri atanır .
        if(players.getName().equals("Bot")){
            earningRow = selectRandomRow ;
            earningColumn = selectRandomColumn ;
        }else {
            earningRow = rowScanner ;
            earningColumn = columnScanner ;
        }
        System.out.println("Oynalınan matris değerleri : ");
        System.out.println(earningRow + " " + earningColumn);


        // yukarıdan aşağı kontrol yapılmasını sağlar .
        if(!(earningRow == row -1)){
            if(matrix[earningRow][earningColumn].equals("s")){
                if(!(earningRow == row -2)){
                if(matrix[earningRow+1][earningColumn].equals("o")){
                    if(matrix[earningRow+ 2][earningColumn].equals("s")){
                        tempPoint ++ ;
                        isEarnedPoint = true;
                    }
                }
            }
            }
            // o için puan alımı yukarıdan - aşağı ve aşağıdan yukarı.
            else if(matrix[earningRow][earningColumn].equals("o")){
                if(earningRow != 0){
                    if(matrix[earningRow-1][earningColumn].equals("s")){
                        if(matrix[earningRow+1][earningColumn].equals("s")){
                            tempPoint ++ ;
                            isEarnedPoint = true;
                        }
                    }
                }
            }
        } // kontrol başarılı


        // aşağıdan yukarı kontrol eder .
        if(!(earningRow ==0)){
            if(matrix[earningRow][earningColumn].equals("s")){
            if(!(earningRow == 1)){
                    if(matrix[earningRow-1][earningColumn].equals("o")){
                        if(matrix[earningRow- 2][earningColumn].equals("s")){
                            tempPoint ++ ;
                            isEarnedPoint = true;
                        }
                    }
                } } }// kontrol edildi .

        // soldan sağa kontrol eder.
            if(earningColumn != column -1 ){
            if(matrix[earningRow][earningColumn].equals("s")){
                if(earningColumn != column - 2 ){
                if(matrix[earningRow][earningColumn+1].equals("o")){
                    if(matrix[earningRow][earningColumn+2].equals("s")){
                        tempPoint ++ ;
                        isEarnedPoint = true;
                    }
                }
            } }
            // o - soldan sağa ve sağdan sola için
            else if(matrix[earningRow][earningColumn].equals("o")){
                if(earningColumn != 0){
                    if(matrix[earningRow][earningColumn+1].equals("s")){
                        if(matrix[earningRow][earningColumn-1].equals("s")){
                            tempPoint ++ ;
                            isEarnedPoint = true;
                        }

                    }
                }
            }
        } // kontrol sağlandı .

            // sağdan sola kontrol yapar
            if(earningColumn != 0 ){
                if(earningColumn != 1){
                if(matrix[earningRow][earningColumn].equals("s")){
                    if(matrix[earningRow][earningColumn-1].equals("o")){
                        if(matrix[earningRow][earningColumn-2].equals("s")){
                            tempPoint ++ ;
                            isEarnedPoint = true;
                        }
                    }
                }  }
            }// kontrol sağlandı .
            // soldan sağa alt çaprz kontrol sağlar .

            if(earningRow != (row - 1)){
                    if(earningColumn != (column -1 )){
                        if(earningRow != (row-2)){
                        if(earningColumn != (column - 2)){
                            if(matrix[earningRow][earningColumn].equals("s")){
                                if(matrix[earningRow+1][earningColumn+1].equals("o")){
                                    if(matrix[earningRow+2][earningColumn+2].equals("s")){
                                        tempPoint ++ ;
                                        isEarnedPoint = true;
                                    }}}}
                    }
                        // o için çapraz kontrolu
                        else if(matrix[earningRow][earningColumn].equals("o")){
                            if(earningRow != 0){
                                if(earningColumn != 0){
                                    if(matrix[earningRow-1][earningColumn-1].equals("s")){
                                        if(matrix[earningRow+1][earningColumn+1].equals("s")){
                                            tempPoint ++ ;
                                            isEarnedPoint = true;
                                        }
                                    }
                                    else if(matrix[earningRow-1][earningColumn+1].equals("s")){
                                        if(matrix[earningRow+1][earningColumn-1].equals("s")){
                                            tempPoint ++ ;
                                            isEarnedPoint = true;
                                        }
                                    }
                                }
                            }
                        }  }}// kontrol sağlandı .

                        // sağdan sola alt çapraz kontrolu yapar .

                        if(earningColumn != 0){
                            if(earningRow != (row -1 )){
                                if(earningColumn != 1){
                                    if(earningRow != (row - 2)){
                                        if(matrix[earningRow][earningColumn].equals("s")){
                                            if(matrix[earningRow+1][earningColumn-1].equals("o")){
                                                if(matrix[earningRow+2][earningColumn-2].equals("s")){
                                                    tempPoint ++ ;
                                                    isEarnedPoint = true;
                                                }}}}
                                }}}


                   // soldan sağa üst çapraz kontrolu yapar .

        if(earningRow != 0){
            if(earningColumn != (column -1)){
                if(earningRow != 1){
                    if(earningColumn != (column - 2)){
                        if(matrix[earningRow][earningColumn].equals("s")){
                            if(matrix[earningRow-1][earningColumn+1].equals("o")){
                                if(matrix[earningRow-2][earningColumn+2].equals("s")){
                                    tempPoint ++ ;
                                    isEarnedPoint = true;
                                }}}}
                }

                }}

                // sağdan sola üst çağraz  kontrolu yapar :
        if(earningRow != 0){
            if(earningColumn != 0){
                if(earningRow != 1){
                    if(earningColumn != 1){
                        if(matrix[earningRow][earningColumn].equals("s")){
                            if(matrix[earningRow-1][earningColumn-1].equals("o")){
                                if(matrix[earningRow-2][earningColumn-2].equals("s")){
                                    tempPoint ++ ;
                                    isEarnedPoint = true;

                                }}}}
                }

            }}

        if(tempPoint !=0 ){
            System.out.println(players.getName() + " "+ (tempPoint) +  " puan kazandı . ");
            // puana ekleme yapar .
            players.setPoint((players.getPoint()) + (tempPoint));
            tempPoint = 0 ;
        }
        System.out.println("|** Güncel Puan durumu **|");
        // puan durumunu gösteren kod
        System.out.println("| " + playerReel.getName() + " puanı : " + playerReel.getPoint() + " |");
        System.out.println("| "+playerBot.getName() + " puanı : " + playerBot.getPoint()+" |");
        // eğer kullanıcı o el puan almıssa yine aynı kullanıcı devam edicektir .
        if(isEarnedPoint == false) {
                selectNextPlayer() ;
        }


    }



    public static void playGame() throws InterruptedException {
        // whilenın ne kadar dönüceğini satır ve sutun sayıları belirler .
        int i = row * column ;
        while (i>0){
            System.out.println("Oyun sırası : " + nowPlayer.getName());
            // eğer sıra bot ise değerler ona göre atanır .
            if(nowPlayer.getName().equals("Bot")){
                botPlayerEmptyMatrix();
                earningPoint(playerBot);
            }else {
                // eğer sıra reel oyuncuda  ise değerler ona göre atanır .
                reelPlayerEmptyMatrix();
                earningPoint(playerReel);
            }
            printMatrix();
            i-- ;
        }
        // kazanan oyuncu puanlarına göre belirlenir.
        if(playerBot.getPoint() > playerReel.getPoint()){
            System.out.println("Oyunu kazanan oyuncu :" + playerBot.getName());
        }else if(playerBot.getPoint() < playerReel.getPoint()) {
            System.out.println("Oyunu kazanan oyuncu : " + playerReel.getName());
        }
        else {
            System.out.println("Oyun berabere bitti. ");
        }

    }


} // Main class kapatıldı.
