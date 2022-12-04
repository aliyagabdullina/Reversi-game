import java.util.ArrayList;
import java.util.Scanner;

/**
 * class Game
 * Организация игры
 */
public class Game extends Moves {

    public static Scanner scanner = new Scanner(System.in);

    public static Field game_field = new Field();

    /**
     * Выбор режима игры
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("Добро пожаловать в игру!");
        System.out.println("Выберите режим игры: 1 - игра с компьютером, 2 - игра вдвоем");
        int mode = scanner.nextInt();
        if (mode == 1) {
            modeComputerPlayer();
        } else {
            modePlayerPlayer();
        }
    }

    /**
     * Организация режима игрок-игрок
     */
    private static void modePlayerPlayer() {
        System.out.println("Введите имя первого игрока:");
        String name = scanner.next();
        Player player1 = new Player(name, "black");
        System.out.println("Введите имя второго игрока:");
        name = scanner.next();
        Player player2 = new Player(name, "white");

        game_field.showField();
        while (!game_field.isFull()) {
            removePossibleCells();
            System.out.println("Ходит игрок " + player1.getName());
            gamer_possible_move(1);
            game_field.showField();
            removePossibleCells();
            System.out.println("Отличный ход! Ходит " + player2.getName());
            gamer_possible_move(2);
            game_field.showField();
            player1.setScore(game_field.countScore(player1.getColor()));
            player2.setScore(game_field.countScore(player2.getColor()));
            System.out.println("Статистика по игре:");
            System.out.println("Очки " + player1.getName() + " " + player1.getScore());
            System.out.println("Очки " + player2.getName() + " " + player2.getScore());
        }
        System.out.println("Игра окончена!");
        if (player1.getScore() > player2.getScore()) System.out.println("Победил" + player1.getName());
        else System.out.println("Победил " + player2.getName());

    }

    /**
     * Организация режима игрок-компьютер
     */
    private static void modeComputerPlayer() {
        System.out.println("Выберите режим игры: 1 - легкий, 2 - сложный:");
        int mode = scanner.nextInt();
        System.out.println("Введите имя:");
        String name = scanner.next();
        Player player1 = new Player(name, "black");
        Player computer = new Player("Computer", "white");
        game_field.showField();
        while (!game_field.isFull()) {
            gamer_possible_move(1);
            game_field.showField();
            removePossibleCells();
            System.out.println("Отличный ход! Компьютер сходил так:");
            if (mode == 1) {
                computer_move_easy();
            } else {
                computer_move_hard();
            }
            game_field.showField();
            player1.setScore(game_field.countScore(player1.getColor()));
            computer.setScore(game_field.countScore(computer.getColor()));
            System.out.println("Статистика по игре:");
            System.out.println("Очки " + player1.getName() + " " + player1.getScore());
            System.out.println("Очки " + computer.getName() + " " + computer.getScore());
        }
        System.out.println("Игра окончена!");
        if (player1.getScore() > computer.getScore()) System.out.println("Вы победили!!!");
        else System.out.println("Вы проиграли((( Попробуйте еще раз");
    }

    /**
     * Удаление временных пометок "possible" на клетке
     */
    private static void removePossibleCells() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (game_field.getField()[i][j].getColor().equals("possible")) {
                    game_field.getField()[i][j].setColor("grey");
                }
            }
        }
    }

    /**
     * Определение и вывод возможных ходов для игрока
     * @param number_of_player - игрок номер 1 или 2
     */
    public static void gamer_possible_move(int number_of_player) {
        if (number_of_player == 1) {
            System.out.println("Возможные ходы:");
            ArrayList<int[]> possible_moves = new ArrayList<>();
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    Moves cur_move = new Moves(i, j);
                    if (cur_move.checkPositionPlayer(game_field, i, j, "black")) {
                        int[] mass = new int[2];
                        mass[0] = i;
                        mass[1] = j;
                        possible_moves.add(mass);
                        game_field.getField()[i][j].setColor("possible");
                        System.out.print("[" + i + "," + j + "], ");
                    }
                }
            }
            System.out.println();
            game_field.showField();
            if (possible_moves.isEmpty()) {
                System.out.println("Нет возможных ходов. Вы пропускаете ход");
            } else {
                choosePlayerMove(possible_moves, number_of_player);
            }
        } else {
            System.out.println("Возможные ходы:");
            ArrayList<int[]> possible_moves = new ArrayList<>();
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    Moves cur_move = new Moves(i, j);
                    if (cur_move.checkPositionComputer(game_field, i, j) != 0) {
                        int[] mass = new int[2];
                        mass[0] = i;
                        mass[1] = j;
                        possible_moves.add(mass);
                        game_field.getField()[i][j].setColor("possible");
                        System.out.print("[" + i + "," + j + "], ");
                    }
                }
            }
            System.out.println();
            game_field.showField();
            if (possible_moves.isEmpty()) {
                System.out.println("Нет возможных ходов. Вы пропускаете ход");
            } else {
                choosePlayerMove(possible_moves, number_of_player);
            }
        }
    }

    /**
     * Выбор хода для игрока
     * @param possible_moves - лист с возможными ходами, которые определили в gamer_possible_move
     * @param number_of_player - игрок номер 1 или 2
     */
    private static void choosePlayerMove(ArrayList<int[]> possible_moves, int number_of_player) {
        System.out.println("Выберите клетку, куда ходите сходить в формате (x y). Обратите внимание, нумерация строк и столбцов начинается с нуля");
        int x_player, y_player;
        x_player = scanner.nextInt();
        y_player = scanner.nextInt();
        while (!isItPossibleMove(possible_moves, x_player, y_player)) {
            System.out.println("В эту клетку нельзя сходить. Попробуйте еще раз");
            x_player = scanner.nextInt();
            y_player = scanner.nextInt();
        }
        if (number_of_player == 1) make_player_move(x_player, y_player);
        else make_computer_move(x_player, y_player);
    }

    /**
     * Проверка возможности хода
     * @param move - лист с возмодными ходами в формате (x, y)
     * @param x - координата, куда хочет сходить игрок
     * @param y - координата, куда хочет сходить игрок
     * @return true, если ход возможен, false - иначе
     */
    private static boolean isItPossibleMove(ArrayList<int[]> move, int x, int y) {
        boolean flag = false;
        for (int i = 0; i < move.size(); i++) {
            if (move.get(i)[0] == x && move.get(i)[1] == y) flag = true;
        }
        return flag;
    }

    /**
     * Перекраска доски после хода игрока/компьютера
     * @param a - номер игрока
     * @param x_coordinate - координата, куда сходил игрок/компьютер
     * @param y_coordinate - координата, куда сходил игрок/компьютер
     */
    public static void repainting(int a, int x_coordinate, int y_coordinate) {
        String first;
        String second;
        if (a == 0) {
            first = "white";
            second = "black";
        } else {
            first = "black";
            second = "white";
        }
        //по строке
        for (int i = 0; i < SIZE; i++) {
            Moves cur_move = new Moves(x_coordinate, i);
            if (cur_move.ifWhiteorBlackBetweenStroka(first, game_field, x_coordinate, y_coordinate, i) && game_field.getField()[x_coordinate][i].getColor().equals(second)) {
                for (int j = Math.min(y_coordinate, i) + 1; j < Math.max(y_coordinate, i); j++) {
                    game_field.getField()[x_coordinate][j].setColor(second);
                }
            }
        }
        //по столбцу
        for (int i = 0; i < SIZE; i++) {
            Moves cur_move = new Moves(i, y_coordinate);
            if (cur_move.ifWhiteorBlackBetweenStolb(first, game_field, x_coordinate, y_coordinate, i) && game_field.getField()[i][y_coordinate].getColor().equals(second)) {
                for (int j = Math.min(x_coordinate, i) + 1; j < Math.max(x_coordinate, i); j++) {
                    game_field.getField()[j][y_coordinate].setColor(second);
                }
            }
        }
        //по диагонали
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                Moves cur_move = new Moves(i, j);
                if (Math.abs(x_coordinate - i) == Math.abs(y_coordinate - j)) {
                    if (cur_move.ifWhiteorBlackBetweenDiagonal(first, game_field, x_coordinate, y_coordinate, i, j) && game_field.getField()[i][j].getColor().equals(second)) {
                        for (int k = Math.min(x_coordinate, i) + 1; k < Math.max(x_coordinate, i); k++) {
                            for (int l = Math.min(y_coordinate, j) + 1; l < Math.max(y_coordinate, j); l++) {
                                if (Math.abs(x_coordinate - k) == Math.abs(y_coordinate - l))
                                    game_field.getField()[k][l].setColor(second);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Ход игрока 1 в координаты:
     * @param x_coordinate
     * @param y_coordinate
     */
    public static void make_player_move(int x_coordinate, int y_coordinate) {
        game_field.getField()[x_coordinate][y_coordinate].setColor("black");
        repainting(0, x_coordinate, y_coordinate);
    }

    /**
     * Ход компьютера/игрока 2 в координаты:
     * @param x_coordinate
     * @param y_coordinate
     */
    public static void make_computer_move(int x_coordinate, int y_coordinate) {
        game_field.getField()[x_coordinate][y_coordinate].setColor("white");
        repainting(1, x_coordinate, y_coordinate);
    }

    /**
     * Организация легкого режима с компьютером
     */
    public static void computer_move_easy() {
        int x_coordinate = -1, y_coordinate = -1;
        int max_profit = 0;
        //надо просчитать максимумы во всех возможных ходах
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                Moves cur_move = new Moves(i, j);
                if (cur_move.checkPositionComputer(game_field, i, j) > max_profit) {
                    x_coordinate = i;
                    y_coordinate = j;
                }
            }
        }
        if (x_coordinate == -1) {
            System.out.println("Игрок не может сходить. Ход переходит Вам.");
        } else {
            make_computer_move(x_coordinate, y_coordinate);
        }
    }

    /**
     * Организация легкого режима с компьютером
     */

    private static void computer_move_hard() {
        int x_coordinate = -1, y_coordinate = -1;
        double max_profit = -1000000;
        //надо просчитать максимумы во всех возможных ходах
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                Moves cur_move = new Moves(i, j);
                if (cur_move.checkPositionComputer(game_field, i, j) != 0) {
                    double r_computer = R(game_field, i, j, "black");
                    double max_r_player = 0;

                    for (int k = 0; k < SIZE; k++) {
                        for (int l = 0; l < SIZE; l++) {
                            Moves c = new Moves(k, l);
                            if (c.checkPositionPlayer(game_field, k, l, "black")) {
                                double r_player = R(game_field, k, l, "white");
                                if (r_player > max_r_player) max_r_player = r_player;
                            }
                        }
                    }

                    r_computer -= max_r_player;
                    if (r_computer > max_profit) {
                        max_profit = r_computer;
                        x_coordinate = i;
                        y_coordinate = j;
                    }
                }
            }
        }

        if (x_coordinate == -1) {
            System.out.println("Игрок не может сходить. Ход переходит Вам.");
        } else {
            make_computer_move(x_coordinate, y_coordinate);
        }
    }

    /**
     * Вычисление оценочной функции R
     * @param field - поле
     * @param x_coordinate - координата клетки, для которой вычисляется функция
     * @param y_coordinate - координата клетки, для которой вычисляется функция
     * @param color - цвет текущего игрока/компьютера
     * @return значение функции R
     */
    private static double R(Field field, int x_coordinate, int y_coordinate, String color) {
        String anticolor = "black";
        if (color.equals("black")) anticolor = "white";
        double sum = 0;

        double ss;
        if ((x_coordinate == 0 && y_coordinate == 0) || (x_coordinate == 7 && y_coordinate == 0) || (x_coordinate == 0 && y_coordinate == 7) || (x_coordinate == 7 && y_coordinate == 7)) {
            ss = 0.8;
        } else if (x_coordinate == 0 || x_coordinate == 7 || y_coordinate == 0 || y_coordinate == 7) {
            ss = 0.4;
        } else {
            ss = 0;
        }
        sum += ss;


        boolean stop_white = true;
        double sum_cur = 0;
        //по строке вправо
        for (int i = x_coordinate + 1; i < SIZE; i++) {
            if (!field.getField()[i][y_coordinate].getColor().equals(color)) {
                if (!field.getField()[i][y_coordinate].getColor().equals(anticolor)) stop_white = false;
                break;
            }
            if (i == 0 || i == 7 || y_coordinate == 0 || y_coordinate == 7) sum_cur += 2;
            else sum_cur += 1;
        }
        if (stop_white) sum += sum_cur;

        stop_white = true;
        sum_cur = 0;
        //по строке влево
        for (int i = x_coordinate - 1; i >= 0; i--) {
            if (!field.getField()[i][y_coordinate].getColor().equals(color)) {
                if (!field.getField()[i][y_coordinate].getColor().equals(anticolor)) stop_white = false;
                break;
            }
            if (i == 0 || i == 7 || y_coordinate == 0 || y_coordinate == 7) sum_cur += 2;
            else sum_cur += 1;
        }
        if (stop_white) sum += sum_cur;

        stop_white = true;
        sum_cur = 0;
        //по столбцу вверх
        for (int i = y_coordinate + 1; i < SIZE; i++) {
            if (!field.getField()[x_coordinate][i].getColor().equals(color)) {
                if (!field.getField()[x_coordinate][i].getColor().equals(anticolor)) stop_white = false;
                break;
            }
            if (i == 0 || i == 7 || x_coordinate == 0 || x_coordinate == 7) sum_cur += 2;
            else sum_cur += 1;

        }
        if (stop_white) sum += sum_cur;

        stop_white = true;
        sum_cur = 0;
        //по столбцу вниз
        for (int i = y_coordinate - 1; i >= 0; i--) {
            if (!field.getField()[x_coordinate][i].getColor().equals(color)) {
                if (!field.getField()[x_coordinate][i].getColor().equals(anticolor)) stop_white = false;
                break;
            }
            if (i == 0 || i == 7 || x_coordinate == 0 || x_coordinate == 7) sum_cur += 2;
            else sum_cur += 1;

        }
        if (stop_white) sum += sum_cur;


        stop_white = true;
        sum_cur = 0;
        //по диагонали вверх вправо
        for (int i = x_coordinate + 1; i < SIZE; i++) {
            for (int j = y_coordinate + 1; j < SIZE; j++) {
                if (Math.abs(x_coordinate - i) == Math.abs(y_coordinate - j)) {
                    if (!field.getField()[i][j].getColor().equals(color)) {
                        if (!field.getField()[i][j].getColor().equals(anticolor)) stop_white = false;
                        break;
                    }
                    if (i == 0 || i == 7 || j == 0 || j == 7) sum_cur += 2;
                    else sum_cur += 1;
                }
            }
        }
        if (stop_white) sum += sum_cur;

        stop_white = true;
        sum_cur = 0;
        //по диагонали вверх влево
        for (int i = x_coordinate + 1; i < SIZE; i++) {
            for (int j = 0; j < y_coordinate; j++) {
                if (Math.abs(x_coordinate - i) == Math.abs(y_coordinate - j)) {
                    if (!field.getField()[i][j].getColor().equals(color)) {
                        if (!field.getField()[i][j].getColor().equals(anticolor)) stop_white = false;
                        break;
                    }
                    if (i == 0 || i == 7 || j == 0 || j == 7) sum_cur += 2;
                    else sum_cur += 1;
                }
            }
        }
        if (stop_white) sum += sum_cur;

        stop_white = true;
        sum_cur = 0;
        //по диагонали вниз вправо
        for (int i = 0; i < x_coordinate; i++) {
            for (int j = y_coordinate + 1; j < SIZE; j++) {
                if (Math.abs(x_coordinate - i) == Math.abs(y_coordinate - j)) {
                    if (!field.getField()[i][j].getColor().equals(color)) {
                        if (!field.getField()[i][j].getColor().equals(anticolor)) stop_white = false;
                        break;
                    }
                    if (i == 0 || i == 7 || j == 0 || j == 7) sum_cur += 2;
                    else sum_cur += 1;
                }
            }
        }
        if (stop_white) sum += sum_cur;

        stop_white = true;
        sum_cur = 0;
        //по диагонали вверх вправо
        for (int i = 0; i < x_coordinate; i++) {
            for (int j = 0; j < y_coordinate; j++) {
                if (Math.abs(x_coordinate - i) == Math.abs(y_coordinate - j)) {
                    if (!field.getField()[i][j].getColor().equals(color)) {
                        if (!field.getField()[i][j].getColor().equals(anticolor)) stop_white = false;
                        break;
                    }
                    if (i == 0 || i == 7 || j == 0 || j == 7) sum_cur += 2;
                    else sum_cur += 1;
                }
            }
        }
        if (stop_white) sum += sum_cur;

        return sum;
    }
}
