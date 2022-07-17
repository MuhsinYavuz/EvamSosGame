public class Players {
    // oyuncuların öznitelikleri yazıldı . // ulaşmak için Getter ve setter alındı
    private String name  ;
    private int point  ;
    private String letter  ;

    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public Players(String name, int point) {
        this.name = name;
        this.point = point;
    }
    public Players(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }
}
