package minesweeper;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        final int n = 9; //rows
        final int m = 9; //columns
        boolean firstFreeDone=false;

        Scanner scanner = new Scanner(System.in);
        int numMines = scanner.nextInt();

        Minefield minefield = new Minefield(n, m, 0);
        Minefield userMinefield = new Minefield(n, m, 0);

        while (!userMinefield.gameEnd) {
            userMinefield.printMinefield();
            System.out.println("Set/unset mine marks or claim a cell as free: ");
            Scanner scannerMineMark = new Scanner(System.in);
            int userCol = scannerMineMark.nextInt() - 1; //fka userX
            int userRow = scannerMineMark.nextInt() - 1; //fka userY
            String option = scannerMineMark.next();
            if (option.matches("mine")) {
                userMinefield.markMine(minefield, userRow, userCol);
            } else if (option.matches("free")) {
                if (!firstFreeDone) {
                    minefield = new Minefield(n, m, numMines, userRow, userCol);
                    firstFreeDone=true;
                }
                userMinefield.free(minefield, userRow, userCol);
            }
        }
    }
}
