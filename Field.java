/**
 * class Field
 * Свойства поля и действия с ним
 */
public class Field extends Cells {

    public static final int SIZE = 8;

    private final Cells[][] field = new Cells[SIZE][SIZE];

    /**
     * Конструктор - задание поля
     */
    Field() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if ((i == 3 && j == 3) || (i == 4 && j == 4)) {
                    field[i][j] = new Cells("black");
                } else if ((i == 3 && j == 4) || (i == 4 && j == 3)) {
                    field[i][j] = new Cells("white");
                } else {
                    field[i][j] = new Cells("grey");
                }
            }
        }
    }

    /**
     * Вывод поля на экран
     */
    public void showField() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (field[j][SIZE - 1 - i].getColor().equals("white")) {
                    System.out.print("W | ");
                } else if (field[j][SIZE - 1 - i].getColor().equals("black")) {
                    System.out.print("B | ");
                } else if(field[j][SIZE - 1 - i].getColor().equals("possible")){
                    System.out.print("* | ");
                }
                else {
                    System.out.print("  | ");
                }
            }
            System.out.println();
        }
    }

    /**
     *
     * @return поле
     */
    public Cells[][] getField() {
        return field;
    }

    /**
     * Проверка на заполненность поля
     * @return
     */
    public boolean isFull() {
        boolean flag = false;

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (field[i][j].isEmpty()) {
                    flag = true;
                    break;
                }
            }
        }
        return !flag;
    }

    /**
     * Подсчет очков на поле
     * @param color - цвет игрока, чьи очки считаем
     * @return
     */
    public int countScore(String color) {
        int score = 0;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (field[i][j].getColor().equals(color)) score++;
            }
        }
        return score;
    }
}
