/**
 * class Moves
 * Действия с ходами, проверки возможности хода
 */
public class Moves extends Field {

    private final int x_coordinate;

    private final int y_coordinate;

    Moves() {
        this.y_coordinate = 0;
        this.x_coordinate = 0;
    }

    Moves(int x, int y) {
        this.x_coordinate = x;
        this.y_coordinate = y;
    }

    /**
     * Проверка наличия только клеток цвета color между клетками с координатами (a, b) и c, d) по столбцу
     * @param color - цвет, который ищем
     * @param field - поле
     * @param a - координаты
     * @param b - координаты
     * @param c - координаты
     * @return
     */
    public boolean ifWhiteorBlackBetweenStolb(String color, Field field, int a, int b, int c) {
        boolean flag = true;
        //по столбцу
        for (int i = Math.min(a, c) + 1; i < Math.max(a, c); i++) {
            if (!field.getField()[i][b].getColor().equals(color)) flag = false;
        }
        if (Math.abs(a - c) == 1) flag = false;
        return flag;
    }

    /**
     * Проверка наличия только клеток цвета color между клетками с координатами (a, b) и c, d) по строке
     * @param color - цвет, который ищем
     * @param field - поле
     * @param a - координаты
     * @param b - координаты
     * @param d - координаты
     * @return
     */
    public boolean ifWhiteorBlackBetweenStroka(String color, Field field, int a, int b, int d) {
        boolean flag = true;
        //по строке
        for (int i = Math.min(b, d) + 1; i < Math.max(d, b); i++) {
            if (!field.getField()[a][i].getColor().equals(color)) flag = false;
        }
        if (Math.abs(b - d) == 1) flag = false;
        return flag;
    }

    /**
     * Проверка наличия только клеток цвета color между клетками с координатами (a, b) и c, d) по диагонали
     * @param color - цвет, который ищем
     * @param field - поле
     * @param a - координаты
     * @param b - координаты
     * @param c - координаты
     * @param d - координаты
     * @return
     */
    public boolean ifWhiteorBlackBetweenDiagonal(String color, Field field, int a, int b, int c, int d) {
        boolean flag = true;
        //по диагонали
        for (int i = Math.min(a, c) + 1; i < Math.max(a, c); i++) {
            for (int j = Math.min(b, d) + 1; j < Math.max(d, b); j++) {
                if (Math.abs(a - i) == Math.abs(b - j) && !field.getField()[i][j].getColor().equals(color)) {
                    flag = false;
                }
            }
        }
        if (Math.abs(a - c) == 1) flag = false;
        return flag;
    }

    /**
     * Проверка, может ли игрок сходить в данную клетку
     * @param field - поле
     * @param x - координата клетки
     * @param y - координата клетки
     * @param color - цвет игрока
     * @return
     */
    public boolean checkPositionPlayer(Field field, int x, int y, String color) {
        boolean flag = false;

        if (field.getField()[x][y].isEmpty()) {
            //по строке
            for (int i = 0; i < SIZE; i++) {
                if (field.getField()[x][i].getColor().equals(color) && i != y && ifWhiteorBlackBetweenStroka("white", field, x, y, i)) {
                    flag = true;
                    break;
                }
            }
            //по столбцу
            for (int i = 0; i < SIZE; i++) {
                if (field.getField()[i][y].getColor().equals(color) && i != x && ifWhiteorBlackBetweenStolb("white", field, x, y, i)) {
                    flag = true;
                    break;
                }
            }
            //по диагонали
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    if (Math.abs(x - i) == Math.abs(y - j)) {
                        if (field.getField()[i][j].getColor().equals(color) && i != x && ifWhiteorBlackBetweenDiagonal("white", field, x, y, i, j)) {
                            flag = true;
                            break;
                        }
                    }
                }
            }
        }
        return flag;
    }

    /**
     * Проверка, может ли компьютер сходить в данную клетку
     * @param field - поле
     * @param x - координата клетки
     * @param y - координата клетки
     * @return
     */
    public int checkPositionComputer(Field field, int x, int y) {
        int num = 0;
        if (field.getField()[x][y].isEmpty()) {
            //по строке
            for (int i = 0; i < SIZE; i++) {
                if (field.getField()[x][i].getColor().equals("white") && i != y && ifWhiteorBlackBetweenStroka("black", field, x, y, i)) {
                    num++;
                }
            }
            //по столбцу
            for (int i = 0; i < SIZE; i++) {
                if (field.getField()[i][y].getColor().equals("white") && i != x && ifWhiteorBlackBetweenStolb("black", field, x, y, i)) {
                    num++;
                }
            }
            //по диагонали
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    if (Math.abs(x - i) == Math.abs(y - j)) {
                        if (field.getField()[i][j].getColor().equals("white") && i != x && ifWhiteorBlackBetweenDiagonal("black", field, x, y, i, j)) {
                            num++;
                        }
                    }
                }
            }
        }
        return num;
    }
}
