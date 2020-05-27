package minesweeper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

class Minefield {
    char[][] arr;
    private final int numRows; //aka n, array.length
    private final int numCol; //aka m, array[i].length
    private final int numMines;
    boolean gameEnd=false;

    public Minefield(int numRows, int numCol, int numMines) {
        this.numRows=numRows;
        this.numCol=numCol;
        this.numMines=numMines;
        arr = new char[numRows][numCol];
        for (int i=0; i<numRows; i++) {
            Arrays.fill(arr[i], '.');
        }
        if (numMines>0) {
            genMinefield();
        }
    }

    public Minefield(int numRows, int numCol, int numMines, int rowToAvoid, int colToAvoid) {
        this.numRows=numRows;
        this.numCol=numCol;
        this.numMines=numMines;
        arr = new char[numRows][numCol];
        for (int i=0; i<numRows; i++) {
            Arrays.fill(arr[i], '.');
        }
        if (numMines>0) {
            genMinefield(rowToAvoid, colToAvoid);
        }
    }

    private void genMinefield() {
        //possibilities
        var poss = new ArrayList<Integer[]>();
        for (int i=0; i<numRows; i++) {
            for (int j=0; j<numCol; j++) {
                poss.add(new Integer[]{i, j});
            }
        }

        // filling
        int curMineCount = 0;
        Random random = new Random();
        int possIndex;

        while (curMineCount<numMines) {
            possIndex = random.nextInt(poss.size());
            Integer[] possRandom = poss.get(possIndex);

            arr[possRandom[0]][possRandom[1]] = 'X';
            placingNumMinefield(possRandom[0], possRandom[1]);
            poss.remove(possIndex);
            curMineCount++;
        }
    }

    private void genMinefield(int rowToAvoid, int colToAvoid) {
        //possibilities
        var poss = new ArrayList<Integer[]>();
        for (int i=0; i<numRows; i++) {
            for (int j=0; j<numCol; j++) {
                if (!(i==rowToAvoid && j==colToAvoid)) {
                    poss.add(new Integer[]{i, j});
                }
            }
        }

        // filling
        int curMineCount = 0;
        Random random = new Random();
        int possIndex;

        while (curMineCount<numMines) {
            possIndex = random.nextInt(poss.size());
            Integer[] possRandom = poss.get(possIndex);

            arr[possRandom[0]][possRandom[1]] = 'X';
            placingNumMinefield(possRandom[0], possRandom[1]);
            poss.remove(possIndex);
            curMineCount++;

        }
    }

    private void placingNumMinefield(int row, int column) {
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                try {
                    if (Character.isDigit(arr[row+i][column+j])) {
                        arr[row+i][column+j]++;
                    } else if (arr[row+i][column+j]=='.') {
                        arr[row+i][column+j]='1';
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    // do nothing
                }
            }
        }
    }

    public void printMinefield() {
        //printing
        for (int i=-2; i<numRows+1; i++) {
            if (i==-2) {
                System.out.print(" |");
                for (int k=0; k<numRows; k++) {
                    System.out.print(k+1);
                }
                System.out.print("|\n");
            } else if (i==-1 || i==numRows) {
                System.out.print("-|");
                for (int k=0; k<numRows; k++) {
                    System.out.print("-");
                }
                System.out.print("|\n");
            } else if (i<numRows) {
                for (int j=-2; j<numCol+1; j++) {
                    if (j==-2) {
                        System.out.print(i+1);
                    } else if (j==-1 || j==numCol) {
                        System.out.print("|");
                    } else if (j<numCol) {
                        System.out.print(arr[i][j]);
                    }
                }
                System.out.print("\n");
            }
        }
    }

    public void free(Minefield keyMinefield, int userRow, int userCol) {
        if (keyMinefield.arr[userRow][userCol] == 'X') {
            for (int i = 0; i < numRows; i++) {
                for (int j = 0; j < numCol; j++) {
                    if (keyMinefield.arr[i][j] == 'X') {
                        arr[i][j] = 'X';
                    }
                }
            }
            revealMines(keyMinefield);
            System.out.println("You stepped on a mine and failed!");
            gameEnd = true;
        } else if (keyMinefield.arr[userRow][userCol] == '.') {
            arr[userRow][userCol] = '/';
            clearingNeighbors(keyMinefield);
        } else if (Character.isDigit(keyMinefield.arr[userRow][userCol])) {
            arr[userRow][userCol] = keyMinefield.arr[userRow][userCol];
        }
        isDone(keyMinefield);
    }


    private void isDone(Minefield keyMinefield) {
        boolean winByMarks=true;
        boolean winByUnexploredMines=true;

        for (int i=0; i<keyMinefield.arr.length; i++) {
            for (int j=0; j<keyMinefield.arr[i].length; j++) {
                if ( (keyMinefield.arr[i][j]=='X' && arr[i][j]!='*') || ( ( keyMinefield.arr[i][j]=='.' || Character.isDigit(keyMinefield.arr[i][j]) ) && arr[i][j]=='*') ) {
                    // user is incorrect or not done yet
                    winByMarks = false;
                } else if ( (keyMinefield.arr[i][j]=='X' && arr[i][j]!='.') || ( ( keyMinefield.arr[i][j]=='.' || Character.isDigit(keyMinefield.arr[i][j]) ) && arr[i][j]=='.') ) {
                    winByUnexploredMines = false;
                }
            }
        }

        if (winByMarks || winByUnexploredMines) {
            gameEnd=true;
        }

        if (gameEnd) {
            System.out.println("Congratulations! You found all mines!");
        }
    }

    public void markMine(Minefield keyMinefield, int userRow, int userCol) {

        if (arr[userRow][userCol]=='.') {
            arr[userRow][userCol]='*';
        } else if (arr[userRow][userCol]=='*') {
            arr[userRow][userCol]='.';
        } else {
            System.out.println("Location has already been processed.");
        }

        isDone(keyMinefield);
    }

    private void revealMines(Minefield keyMinefield) {
        for (int i=0; i<numRows; i++) {
            for (int j=0;j<numCol; j++) {
                if (keyMinefield.arr[i][j]=='X') {
                    arr[i][j]='X';
                }
            }
        }
        printMinefield();
    }

    private void clearingNeighbors(Minefield keyMinefield) {
        boolean newValsToLookAt = true;
        while (newValsToLookAt) {
            newValsToLookAt=false;
            for (int i = 0; i < numRows; i++) {
                for (int j = 0; j < numCol; j++) {
                    if (arr[i][j] == '/') {
                        for (int di = -1; di < 2; di++) {
                            for (int dj = -1; dj < 2; dj++) {
                                try {
                                    if ((arr[i+di][j+dj]=='.' || arr[i+di][j+dj]=='*') && keyMinefield.arr[i+di][j+dj]=='.') {
                                        arr[i+di][j+dj]='/';
                                        newValsToLookAt=true;
                                    } else if ((arr[i+di][j+dj]=='.' || arr[i+di][j+dj]=='*') && Character.isDigit(keyMinefield.arr[i+di][j+dj])) {
                                        arr[i+di][j+dj]=keyMinefield.arr[i+di][j+dj];
                                        newValsToLookAt=true;
                                    }
                                } catch (ArrayIndexOutOfBoundsException e) {
                                    // do nothing
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}