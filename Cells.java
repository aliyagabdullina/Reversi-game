/**
 * class Cells
 * Свойства клеток и действия с ними
 */
public class Cells {

    private String color;

    Cells() {
        this.color = "grey";
    }

    Cells(String color) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String new_color) {
        color = new_color;
    }

    public boolean isEmpty() {
        return color.equals("grey");
    }
}
