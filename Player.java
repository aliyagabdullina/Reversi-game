/**
 * class Player
 * Информация о игроках
 */
public class Player {

    private final String name;

    private int score;

    private final String color;

    Player(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public void setScore(int score1){
        score = score1;
    }

    public String getName() {
        return name;
    }

    public int getScore(){
        return score;
    }

    public String getColor(){
        return color;
    }
}
