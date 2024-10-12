package tetris;

import edu.princeton.cs.algs4.StdDraw;
import net.sf.saxon.trans.SymbolicName;
import tileengine.TETile;
import tileengine.TERenderer;
import tileengine.Tileset;

import java.util.*;

/**
 *  Provides the logic for Tetris.
 *
 *  @author Erik Nelson, Omar Yu, Noah Adhikari, Jasmine Lin
 */

public class Tetris {

    private static int WIDTH = 10;
    private static int HEIGHT = 20;

    // Tetrominoes spawn above the area we display, so we'll have our Tetris board have a
    // greater height than what is displayed.
    private static int GAME_HEIGHT = 25;

    // Contains the tiles for the board.
    private TETile[][] board;

    // Helps handle movement of pieces.
    private Movement movement;

    // Checks for if the game is over.
    private boolean isGameOver;

    // The current Tetromino that can be controlled by the player.
    private Tetromino currentTetromino;

    // The current game's score.
    private int score;

    /**
     * Checks for if the game is over based on the isGameOver parameter.
     * @return boolean representing whether the game is over or not
     */
    private boolean isGameOver() {
        return isGameOver;
    }

    /**
     * Renders the game board and score to the screen.
     */
    private void renderBoard() {
        ter.drawTiles(board);
        renderScore();
        StdDraw.show();

        if (auxFilled) {
            auxToBoard();
        } else {
            fillBoard(Tileset.NOTHING);
        }
    }

    /**
     * Creates a new Tetromino and updates the instance variable
     * accordingly. Flags the game to end if the top of the board
     * is filled and the new piece cannot be spawned.
     */
    private void spawnPiece() {
        // The game ends if this tile is filled
        if (board[4][19] != Tileset.NOTHING) {
            isGameOver = true;
        }

        // Otherwise, spawn a new piece and set its position to the spawn point
        currentTetromino = Tetromino.values()[bagRandom.getValue()];
        currentTetromino.reset();
    }

    /**
     * Updates the board based on the user input. Makes the appropriate moves
     * depending on the user's input.
     */
    private void updateBoard() {
        // Grabs the current piece.
        Tetromino t = currentTetromino;
        if (actionDeltaTime() > 1000) {
            movement.dropDown();
            resetActionTimer();
            Tetromino.draw(t, board, t.pos.x, t.pos.y);
            return;
        }

        if (StdDraw.hasNextKeyTyped()) {
            char key = StdDraw.nextKeyTyped();
            moveTetromino(key);
        }

       Tetromino.draw(t, board, t.pos.x, t.pos.y);
    }

    // Move or rotate the tetromino
    private void moveTetromino(char key) {
        switch (key) {
            case 'w': movement.rotateRight(); break;
            case 'a': movement.tryMove(-1, 0); break;
            case 's': movement.dropDown(); break;
            case 'd': movement.tryMove(1, 0); break;
            case 'q': movement.rotateLeft(); break;
        }
    }

    /**
     * Increments the score based on the number of lines that are cleared.
     *
     * @param linesCleared
     */
    private void incrementScore(int linesCleared) {
        if (linesCleared < 1 || linesCleared > 4) {
            return;
        }

        switch (linesCleared) {
            case 1:
                score += 100;
                break;
            case 2:
                score += 300;
                break;
            case 3:
                score += 500;
                break;
            case 4:
                score += 800;
                break;
        }
    }

    /**
     * Clears lines/rows on the provided tiles/board that are horizontally filled.
     * Repeats this process for cascading effects and updates score accordingly.
     * @param tiles
     */
    public void clearLines(TETile[][] tiles) {
        // Keeps track of the current number lines cleared
        int linesCleared = 0;

//        printBoard(tiles);

        for (int row = 0; row < tiles[0].length; row++) {
            boolean isComplete = true;

            for (int col = 0; col < tiles.length; col++) {
                if (tiles[col][row].equals(Tileset.NOTHING)) {
//                    System.out.println("Tile at (" + row + ", " + col + "): " + tiles[col][row].toString());
                    isComplete = false;
                    break;
                }
            }

            if (isComplete) {
                clearRow(tiles, row);
                linesCleared++;
                row--;
            }
//            System.out.println("Row " + row + " is complete: " + isComplete);

        }

//        System.out.println("Lines cleared: " + linesCleared);  // 调试信息


        incrementScore(linesCleared);
        fillAux();
    }

    private void printBoard(TETile[][] board) {
        for (int row = 0; row < board[0].length; row++) {
            for (int col = 0; col < board.length; col++) {
                System.out.print(board[col][row] + " ");
            }
            System.out.println();
        }
    }

    private void clearRow(TETile[][] tiles, int row) {
        for (int r = row; r < tiles[0].length - 1; r++) {
            for (int c = 0; c < tiles.length; c++) {
                tiles[c][r] = tiles[c][r + 1];
            }
        }

        for (int c = 0; c < tiles.length; c++) {
            tiles[c][tiles[0].length - 1] = Tileset.NOTHING;
        }
    }

    /**
     * Where the game logic takes place. The game should continue as long as the game isn't
     * over.
     */
    public void runGame() {
        resetActionTimer();
        spawnPiece();  // 游戏一开始就生成一个方块

        // 游戏主循环，直到游戏结束
        while (!isGameOver()) {
            updateBoard();  // 检查用户输入并更新当前方块的位置

            // 如果当前方块无法再下落（游戏逻辑中会自动将其设置为null）
            if (getCurrentTetromino() == null) {
                clearLines(getBoard());  // 清除已满的行并更新分数
                spawnPiece();  // 生成新的方块
            }

            renderBoard();  // 渲染最新的棋盘状态，确保界面更新
        }
    }

    /**
     * Renders the score using the StdDraw library.
     */
    private void renderScore() {
        // 设置文字颜色为白色
        StdDraw.setPenColor(255, 255, 255);


        StdDraw.setFont();


        StdDraw.text(7, 19, "Score: " + score);


        StdDraw.show();

    }

    /**
     * Use this method to run Tetris.
     * @param args
     */
    public static void main(String[] args) {
        long seed = args.length > 0 ? Long.parseLong(args[0]) : (new Random()).nextLong();
        Tetris tetris = new Tetris(seed);
        tetris.runGame();
    }

    /**
     * Everything below here you don't need to touch.
     */

    // This is our tile rendering engine.
    private final TERenderer ter = new TERenderer();

    // Used for randomizing which pieces are spawned.
    private Random random;
    private BagRandomizer bagRandom;

    private long prevActionTimestamp;
    private long prevFrameTimestamp;

    // The auxiliary board. At each time step, as the piece moves down, the board
    // is cleared and redrawn, so we keep an auxiliary board to track what has been
    // placed so far to help render the current game board as it updates.
    private TETile[][] auxiliary;
    private boolean auxFilled;

    public Tetris() {
        board = new TETile[WIDTH][GAME_HEIGHT];
        auxiliary = new TETile[WIDTH][GAME_HEIGHT];
        random = new Random(new Random().nextLong());
        bagRandom = new BagRandomizer(random, Tetromino.values().length);
        auxFilled = false;
        movement = new Movement(WIDTH, GAME_HEIGHT, this);
        fillBoard(Tileset.NOTHING);
        fillAux();
    }

    public Tetris(long seed) {
        board = new TETile[WIDTH][GAME_HEIGHT];
        auxiliary = new TETile[WIDTH][GAME_HEIGHT];
        random = new Random(seed);
        bagRandom = new BagRandomizer(random, Tetromino.values().length);
        auxFilled = false;
        movement = new Movement(WIDTH, GAME_HEIGHT, this);

        ter.initialize(WIDTH, HEIGHT);
        fillBoard(Tileset.NOTHING);
        fillAux();
    }

    // Setter and getter methods.

    /**
     * Returns the current game board.
     * @return
     */
    public TETile[][] getBoard() {
        return board;
    }

    /**
     * Returns the score.
     */
    public int getScore() {
        return score;
    }

    /**
     * Returns the current auxiliary board.
     * @return
     */
    public TETile[][] getAuxiliary() {
        return auxiliary;
    }


    /**
     * Returns the current Tetromino/piece.
     * @return
     */
    public Tetromino getCurrentTetromino() {
        return currentTetromino;
    }

    /**
     * Sets the current Tetromino to null.
     * @return
     */
    public void setCurrentTetromino() {
        currentTetromino = null;
    }

    /**
     * Sets the boolean auxFilled to true;
     */
    public void setAuxTrue() {
        auxFilled = true;
    }

    /**
     * Fills the entire board with the specific tile that is passed in.
     * @param tile
     */
    private void fillBoard(TETile tile) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j] = tile;
            }
        }
    }

    /**
     * Copies the contents of the src array into the dest array using
     * System.arraycopy.
     * @param src
     * @param dest
     */
    private static void copyArray(TETile[][] src, TETile[][] dest) {
        for (int i = 0; i < src.length; i++) {
            System.arraycopy(src[i], 0, dest[i], 0, src[0].length);
        }
    }

    /**
     * Copies over the tiles from the game board to the auxiliary board.
     */
    public void fillAux() {
        copyArray(board, auxiliary);
    }

    /**
     * Copies over the tiles from the auxiliary board to the game board.
     */
    private void auxToBoard() {
        copyArray(auxiliary, board);
    }

    /**
     * Calculates the delta time with the previous action.
     * @return the amount of time between the previous Tetromino movement with the present
     */
    private long actionDeltaTime() {
        return System.currentTimeMillis() - prevActionTimestamp;
    }

    /**
     * Resets the action timestamp to the current time in milliseconds.
     */
    private void resetActionTimer() {
        prevActionTimestamp = System.currentTimeMillis();
    }

}
