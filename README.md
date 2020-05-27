# minesweeper
Minesweeper, in Java CLI form

Done as part of the JetBrains Academy/Hyperskill Java curriculum.

Start by entering the desired number of mines to be placed in a 9x9 grid.

You'll be shown your view of the field. It will be empty, but that will change as you add flags and reveal squares.

To flag (or unflag) a specific coordinate as a suspected mine, type 'mine' followed by the x coordinate and the y coordinate, e.g. 'mine 7 8' to flag (7,8) with an asterisk '\*'. If you flag all mines, and only mines, you win. 

To reveal what's at a specific coordinate, use 'free' instead, e.g. 'free 7 3' to reveal (7,3). If you reveal a mine, it shows as an 'X' and you lose. All mines are then revealed. (The program ensures that the first reveal is never a mine.) If the coordinate you reveal has mines in one of it's immediate neighbors (i.e. shares a side or corner), the coordinate reveals as the number of neighbors that are mines. If the coordinate you revealed is determined to be empty (i.e. no mines or numbers indicating mines - so immediate neighbors are not mines), it shows as a '/' and the neighbors are revealed (and if a neighbor is empty, its immediate neighbors are revealed, and so on). If you reveal only safe cells, with unrevealed cells being only mines, you win.
