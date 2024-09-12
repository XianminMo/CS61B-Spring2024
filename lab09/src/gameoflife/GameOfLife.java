package gameoflife;

import edu.princeton.cs.algs4.StdDraw;
import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;
import utils.FileUtils;

import java.awt.event.KeyEvent;
import java.util.Random;

/**
 * Am implementation of Conway's Game of Life using StdDraw.
 * Credits to Erik Nelson, Jasmine Lin and Elana Ho for
 * creating the assignment.
 */
public class GameOfLife {

    private static final int DEFAULT_WIDTH = 50;
    private static final int DEFAULT_HEIGHT = 50;
    private static final String SAVE_FILE = "src/save.txt";
    private long prevFrameTimestep;
    private TERenderer ter;
    private Random random;
    private TETile[][] currentState;
    private int width;
    private int height;

    /**
     * Initializes our world.
     * @param seed
     */
    public GameOfLife(long seed) {
        width = DEFAULT_WIDTH;
        height = DEFAULT_HEIGHT;
        ter = new TERenderer();
        ter.initialize(width, height);
        random = new Random(seed);
        TETile[][] randomTiles = new TETile[width][height];
        fillWithRandomTiles(randomTiles);
        currentState = randomTiles;
    }

    /**
     * Constructor for loading in the state of the game from the
     * given filename and initializing it.
     * @param filename
     */
    public GameOfLife(String filename) {
        this.currentState = loadBoard(filename);
        ter = new TERenderer();
        ter.initialize(width, height);
    }

    /**
     * Constructor for loading in the state of the game from the
     * given filename and initializing it. For testing purposes only, so
     * do not modify.
     * @param filename
     */
    public GameOfLife(String filename, boolean test) {
        this.currentState = loadBoard(filename);
    }

    /**
     * Initializes our world without using StdDraw. For testing purposes only,
     * so do not modify.
     * @param seed
     */
    public GameOfLife(long seed, boolean test) {
        width = DEFAULT_WIDTH;
        height = DEFAULT_HEIGHT;
        random = new Random(seed);
        TETile[][] randomTiles = new TETile[width][height];
        fillWithRandomTiles(randomTiles);
        currentState = randomTiles;
    }

    /**
     * Initializes our world with a given TETile[][] without using StdDraw.
     * For testing purposes only, so do not modify.
     * @param tiles
     * @param test
     */
    public GameOfLife(TETile[][] tiles, boolean test) {
        TETile[][] transposeState = transpose(tiles);
        this.currentState = flip(transposeState);
        this.width = tiles[0].length;
        this.height = tiles.length;
    }

    /**
     * Flips the matrix along the x-axis.
     * @param tiles
     * @return
     */
    private TETile[][] flip(TETile[][] tiles) {
        int w = tiles.length;
        int h = tiles[0].length;

        TETile[][] rotateMatrix = new TETile[w][h];
        int y = h - 1;
        for (int j = 0; j < h; j++) {
            for (int i = 0; i < w; i++) {
                rotateMatrix[i][y] = tiles[i][j];
            }
            y--;
        }
        return rotateMatrix;
    }

    /**
     * Transposes the tiles.
     * @param tiles
     * @return
     */
    private TETile[][] transpose(TETile[][] tiles) {
        int w = tiles[0].length;
        int h = tiles.length;

        TETile[][] transposeState = new TETile[w][h]; // 3行4列
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                transposeState[x][y] = tiles[y][x];
            }
        }
        return transposeState;
    }

    /**
     * Runs the game. You don't have to worry about how this method works.
     * DO NOT MODIFY THIS METHOD!
     */
    public void runGame() {
        boolean paused = false;
        long evoTimestamp = System.currentTimeMillis();
        long pausedTimestamp = System.currentTimeMillis();
        long clickTimestamp = System.currentTimeMillis();
        while (true) {
            if (!paused && System.currentTimeMillis() - evoTimestamp > 250) {
                evoTimestamp = System.currentTimeMillis();
                currentState = nextGeneration(currentState);
            }
            if (System.currentTimeMillis() - prevFrameTimestep > 17) {
                prevFrameTimestep = System.currentTimeMillis();

                double mouseX = StdDraw.mouseX();
                double mouseY = StdDraw.mouseY();
                int tileX = (int) mouseX;
                int tileY = (int) mouseY;

                TETile currTile = currentState[tileX % width][tileY % height];

                if (StdDraw.isMousePressed() && System.currentTimeMillis() - clickTimestamp > 250) {
                    clickTimestamp = System.currentTimeMillis();
                    if (currTile == Tileset.CELL) {
                        currentState[tileX][tileY] = Tileset.NOTHING;
                    } else {
                        currentState[tileX][tileY] = Tileset.CELL;
                    }
                }
                if (StdDraw.isKeyPressed(KeyEvent.VK_SPACE) && System.currentTimeMillis() - pausedTimestamp > 500) {
                    pausedTimestamp = System.currentTimeMillis();
                    paused = !paused;
                }
                if (StdDraw.isKeyPressed(KeyEvent.VK_S)) {
                    saveBoard();
                    System.exit(0);
                }
                ter.renderFrame(currentState);
            }
        }
    }


    /**
     * Fills the given 2D array of tiles with RANDOM tiles.
     * @param tiles
     */
    public void fillWithRandomTiles(TETile[][] tiles) {
        int height = tiles[0].length;
        int width = tiles.length;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                tiles[x][y] = randomTile();
            }
        }
    }

    /**
     * Fills the 2D array of tiles with NOTHING tiles.
     * @param tiles
     */
    public void fillWithNothing(TETile[][] tiles) {
        int height = tiles[0].length;
        int width = tiles.length;
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                tiles[x][y] = Tileset.NOTHING;
            }
        }
    }

    /**
     * Selects a random tile, with a 50% change of it being a CELL
     * and a 50% change of being NOTHING.
     */
    private TETile randomTile() {
        // The following call to nextInt() uses a bound of 3 (this is not a seed!) so
        // the result is bounded between 0, inclusive, and 3, exclusive. (0, 1, or 2)
        int tileNum = random.nextInt(2);
        return switch (tileNum) {
            case 0 -> Tileset.CELL;
            default -> Tileset.NOTHING;
        };
    }

    /**
     * Returns the current state of the board.
     * @return
     */
    public TETile[][] returnCurrentState() {
        return currentState;
    }

    /**
     * At each timestep, the transitions will occur based on the following rules:
     *  1.Any live cell with fewer than two live neighbors dies, as if by underpopulation.
     *  2.Any live cell with two or three neighbors lives on to the next generation.
     *  3.Any live cell with more than three neighbors dies, as if by overpopulation,
     *  4.Any dead cell with exactly three live neighbors becomes a live cell, as if by reproduction.
     * @param tiles
     * @return
     */
    public TETile[][] nextGeneration(TETile[][] tiles) {
        TETile[][] nextGen = new TETile[width][height];
        // The board is filled with Tileset.NOTHING
        fillWithNothing(nextGen);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int liveNeighbors = countLiveNeighbors(tiles, x, y);

                if (isAlive(tiles[x][y])) {
                    if (liveNeighbors < 2 || liveNeighbors > 3) {
                        // Rule 1 and 3: Live cell dies
                        nextGen[x][y] = Tileset.NOTHING;
                    } else {
                        // Rule 2: Live cell continues to live
                        nextGen[x][y] = tiles[x][y];
                    }
                } else {
                    if (liveNeighbors == 3) {
                        // Rule 4: Dead cell becomes a live cell
                        nextGen[x][y] = Tileset.CELL;
                    }
                }
            }
        }
        return nextGen;
    }

    // Helper method: count the number of the live neighbors of the tile at (x, y)
    private int countLiveNeighbors(TETile[][] tiles, int x, int y) {
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue; // Skip the current cell
                int nx = x + i;
                int ny = y + j;
                if (isInBounds(nx, ny) && isAlive(tiles[nx][ny])) {
                    count++;
                }
            }
        }
        return count;
    }

    private boolean isAlive(TETile tile) {
        return tile == Tileset.CELL;
    }

    private boolean isInBounds(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    /**
     * Helper method for saveBoard without rendering and running the game.
     * @param tiles
     */
    public void saveBoard(TETile[][] tiles) {
        TETile[][] flipState = flip(tiles);
        this.currentState = transpose(flipState);
        this.width = currentState[0].length;
        this.height = currentState.length;
    }

    /**
     * Saves the state of the current state of the board into the
     * save.txt file (make sure it's saved into this specific file).
     * 0 represents NOTHING, 1 represents a CELL.
     */
    public void saveBoard() {
        saveBoard(currentState); // 将currentState处理成数组读取坐标系

        StringBuilder sb = new StringBuilder();

        // Save the dimensions of the board
        sb.append(width).append(" ").append(height).append('\n');

        // Save the current state of the board
        for (int x = 0; x < height; x++) {
            for (int y = 0; y < width; y++) {
                if (currentState[x][y] == Tileset.NOTHING) {
                    sb.append("0");
                } else if (currentState[x][y] == Tileset.CELL) {
                    sb.append("1");
                }
            }
            sb.append("\n"); // End each line with a newline character
        }

        // Write to the file
        FileUtils.writeFile(SAVE_FILE, sb.toString());
    }

    /**
     * Loads the board from filename and returns it in a 2D TETile array.
     * 0 represents NOTHING, 1 represents a CELL.
     */
    public TETile[][] loadBoard(String filename) {
        // Step 1: Read in the file
        String fileContents = FileUtils.readFile(filename);

        // Step 2: Split the file based on the new line character
        String[] lines = fileContents.split("\n");

        // Step 3: Grab and set the dimensions from the first line
        String[] dimensions = lines[0].split(" ");
        this.width = Integer.parseInt(dimensions[0]);
        this.height = Integer.parseInt(dimensions[1]);

        // Step 4: Create a TETile[][] to load the board from the file into
        TETile[][] board = new TETile[width][height];

        // Step 5: Load the state of the board
        for (int y = 1; y < height; y++) {
            String line = lines[y];
            for (int x = 0; x < width; x++) {
                char tile = line.charAt(x);
                if (tile == '0') {
                    board[x][y] = Tileset.NOTHING;
                } else if (tile == '1') {
                    board[x][y] = Tileset.CELL;
                }
            }
        }
        return board;
    }

    /**
     * This is where we run the program. DO NOT MODIFY THIS METHOD!
     * @param args
     */
    public static void main(String[] args) {
        if (args.length == 2) {
            // Read in the board from a file.
            if (args[0].equals("-l")) {
                GameOfLife g = new GameOfLife(args[1]);
                g.runGame();
            }
            System.out.println("Verify your program arguments!");
            System.exit(0);
        } else {
            long seed = args.length > 0 ? Long.parseLong(args[0]) : (new Random()).nextLong();
            GameOfLife g = new GameOfLife(seed);
            g.runGame();
        }
    }
}
