package Snake;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.LinkedList;
import java.util.Random;
import java.util.Date;

import Highscores.EasyHighScores;
import Highscores.HardHighScores;
import Highscores.MediumHighScores;
import Sound.Music;



import javax.swing.*;

public class SnakeGame extends JFrame {
    private static final long serialVersionUID = 6678292058307426314L;
    private static final long FRAME_TIME = 1000L / 50L;
    private static final int MIN_SNAKE_LENGTH = 5;
    private static final int MAX_DIRECTIONS = 3;
    private BoardPanel board;
    private SidePanel side;
    private Random random;
    private Clock logicTimer;
    private boolean isNewGame;
    private boolean isGameOver;
    private boolean isPaused;
    private boolean viewHigscores;
    private LinkedList<Point> snake;
    private LinkedList<Direction> directions;
    private int score;
    private int fruitsEaten;
    private int nextFruitScore;
    private Music music;
    private String difficulty;
    private String name;
    private EasyHighScores easy;
    private MediumHighScores medium;
    private HardHighScores hard;
    private Date date;
    private JFrame frame;

    public SnakeGame() {
        super("Snake");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        this.board = new BoardPanel(this);
        this.side = new SidePanel(this);
        this.music = new Music();

        add(board, BorderLayout.CENTER);
        add(side, BorderLayout.EAST);

        addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                switch(e.getKeyCode()) {
                    case KeyEvent.VK_W:
                    case KeyEvent.VK_UP:
                        if(!isPaused && !isGameOver) {
                            if(directions.size() < MAX_DIRECTIONS) {
                                Direction last = directions.peekLast();
                                if(last != Direction.Down && last != Direction.Up) {
                                    directions.addLast(Direction.Up);
                                }
                            }
                        }
                        break;

                    case KeyEvent.VK_S:
                    case KeyEvent.VK_DOWN:
                        if(!isPaused && !isGameOver) {
                            if(directions.size() < MAX_DIRECTIONS) {
                                Direction last = directions.peekLast();
                                if(last != Direction.Up && last != Direction.Down) {
                                    directions.addLast(Direction.Down);
                                }
                            }
                        }
                        break;

                    case KeyEvent.VK_A:
                    case KeyEvent.VK_LEFT:
                        if(!isPaused && !isGameOver) {
                            if(directions.size() < MAX_DIRECTIONS) {
                                Direction last = directions.peekLast();
                                if(last != Direction.Right && last != Direction.Left) {
                                    directions.addLast(Direction.Left);
                                }
                            }
                        }
                        break;

                    case KeyEvent.VK_D:
                    case KeyEvent.VK_RIGHT:
                        if(!isPaused && !isGameOver) {
                            if(directions.size() < MAX_DIRECTIONS) {
                                Direction last = directions.peekLast();
                                if(last != Direction.Left && last != Direction.Right) {
                                    directions.addLast(Direction.Right);
                                }
                            }
                        }
                        break;

                    case KeyEvent.VK_P:
                        if(!isGameOver) {
                            isPaused = !isPaused;
                            logicTimer.setPaused(isPaused);
                            music.pauseMusic();
                        }
                        break;

                    case KeyEvent.VK_1:
                        if(viewHigscores) {
                            try {
                                File file = new File("EasyHighScores.txt");

                                if (!Desktop.isDesktopSupported()) {
                                    JOptionPane.showMessageDialog(frame, "File Not Found!");
                                }

                                Desktop desktop = Desktop.getDesktop();
                                if (file.exists()) {
                                    desktop.open(file);
                                    file.setReadOnly();
                                }

                            }
                            catch(Exception e1) {
                                e1.printStackTrace();
                            }

                            break;
                        }
                        if(isNewGame) {
                            name = JOptionPane.showInputDialog(frame, "Enter Your Name: ", JOptionPane.OK_CANCEL_OPTION);
                            if(name == null || name.isBlank()) {
                                break;
                            }
                            else {
                                newEasyGame();
                            }
                        }
                        break;

                    case KeyEvent.VK_2:
                        if(viewHigscores) {
                            try {
                                File file = new File("MediumHighScores.txt");
                                if (!Desktop.isDesktopSupported()) {
                                    JOptionPane.showMessageDialog(frame, "File Not Found!");
                                    break;
                                }

                                Desktop desktop = Desktop.getDesktop();
                                if (file.exists()) {
                                    desktop.open(file);
                                    file.setReadOnly();

                                }
                            }
                            catch(Exception e1) {
                                e1.printStackTrace();
                            }

                            break;
                        }
                        if(isNewGame) {
                            name = JOptionPane.showInputDialog(frame, "Enter Your Name: ", JOptionPane.OK_CANCEL_OPTION);
                            if(name == null || name.isBlank()) {
                                break;
                            }
                            else {
                                newMediumGame();
                            }
                        }

                    case KeyEvent.VK_3:
                        if(viewHigscores) {
                            try {
                                File file = new File("HardHighScores.txt");
                                if (!Desktop.isDesktopSupported()) {
                                    JOptionPane.showMessageDialog(frame, "File Not Found!");
                                    break;
                                }

                                Desktop desktop = Desktop.getDesktop();
                                if (file.exists()) {
                                    desktop.open(file);
                                    file.setReadOnly();
                                }
                            }
                            catch(Exception e1) {
                                e1.printStackTrace();
                            }

                            break;
                        }
                        if(isNewGame) {
                            name = JOptionPane.showInputDialog(frame, "Enter Your Name: ", JOptionPane.OK_CANCEL_OPTION);
                            if(name == null || name.isBlank()) {
                                break;
                            }
                            else {
                                newHardGame();
                            }
                        }
                        break;

                    case KeyEvent.VK_4:
                        if(isNewGame) {
                            viewHigscores = true;
                            if(viewHigscores) {
                                isNewGame = false;
                                isGameOver = false;
                            }
                        }
                        break;

                    case KeyEvent.VK_ENTER:
                        if(!isGameOver) {
                            break;
                        }
                        if(isGameOver) {
                            isNewGame = true;
                            board.clearBoard();
                            score = 0;
                            fruitsEaten = 0;
                            nextFruitScore = 0;
                        }
                        break;

                    case KeyEvent.VK_ESCAPE:
                        if(viewHigscores) {
                            viewHigscores = false;
                            isNewGame = true;
                            break;
                        }

                        if(isNewGame) {
                            System.exit(0);
                        }
                        break;
                }
            }

        });

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void startGame() {
        this.random = new Random();
        this.snake = new LinkedList<>();
        this.directions = new LinkedList<>();
        this.logicTimer = new Clock(10.0f);
        this.isNewGame = true;


        logicTimer.setPaused(true);

        while(true) {
            long start = System.nanoTime();

            logicTimer.update();

            if(logicTimer.hasElapsedCycle()) {
                updateGame();
            }

            board.repaint();
            side.repaint();

            long delta = (System.nanoTime() - start) / 1000000L;
            if(delta < FRAME_TIME) {
                try {
                    Thread.sleep(FRAME_TIME - delta);
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void updateGame() {

        TileType collision = updateSnake();

        if(collision == TileType.Fruit) {
            fruitsEaten++;
            score += nextFruitScore;
            spawnFruit();
            music.playEatAppleSound();
        } else if(collision == TileType.SnakeBody) {
            isGameOver = true;
            logicTimer.setPaused(true);
            music.playGameOverSound();
            music.stopMusic();
        } else if (collision == TileType.Wall) {
            isGameOver = true;
            logicTimer.setPaused(true);
            music.playGameOverSound();
            music.stopMusic();
        } else if(nextFruitScore > 10) {
            nextFruitScore--;
        }

        if(isGameOver) {
            if(getDifficulty() == "Easy") {
                File file = new File("EasyHighScores.txt");
                file.setWritable(true);
                file.setReadable(true);
                easy = new EasyHighScores();
                easy.write("Score: " + getScore() + " | " + "Fruits eaten: " + getFruitsEaten() + " | " +
                        "Player: " + getName() + " | " + "Date: " + getDate() + " | ");
                easy.sort();
                file.setReadOnly();
            }
            else if(getDifficulty() == "Medium") {
                File file = new File("MediumHighScore.txt");
                file.setWritable(true);
                file.setReadable(true);
                medium = new MediumHighScores();
                medium.write("Score: " + getScore() + " | " + "Fruits eaten: " + getFruitsEaten() + " | " +
                        "Player: " + getName() + " | " + "Date: " + getDate() + " | ");
                medium.sort();
                file.setReadOnly();
            }
            else if(getDifficulty() == "Hard") {
                File file = new File("HardHighScores.txt");
                file.setWritable(true);
                file.setReadable(true);
                hard = new HardHighScores();
                hard.write("Score: " + getScore() + " | " + "Fruits eaten: " + getFruitsEaten() + " | " +
                        "Player: " + getName() + " | " + "Date: " + getDate() + " | ");
                hard.sort();
                file.setReadOnly();
            }
        }
    }

    private TileType updateSnake() {

        Direction direction = directions.peekFirst();

        Point head = new Point(snake.peekFirst());
        switch(direction) {
            case Up:
                head.y--;
                break;

            case Down:
                head.y++;
                break;

            case Left:
                head.x--;
                break;

            case Right:
                head.x++;
                break;
        }

        if(head.x < 0 || head.x >= BoardPanel.COL_COUNT || head.y < 0 || head.y >= BoardPanel.ROW_COUNT) {
            return TileType.SnakeBody; //Pretend we collided with our body.
        }

        TileType old = board.getTile(head.x, head.y);
        if(old != TileType.Fruit && snake.size() > MIN_SNAKE_LENGTH) {
            Point tail = snake.removeLast();
            board.setTile(tail, null);
            old = board.getTile(head.x, head.y);
        }

        if(old != TileType.SnakeBody) {
            board.setTile(snake.peekFirst(), TileType.SnakeBody);
            snake.push(head);
            board.setTile(head, TileType.SnakeHead);
            if(directions.size() > 1) {
                directions.poll();
            }
        }

        return old;
    }

    private void newEasyGame() {
        this.score = 0;
        this.fruitsEaten = 0;
        this.logicTimer = new Clock(15.0f);
        this.difficulty = "Easy";
        this.date = new Date();
        File file = new File("EasyHighScores.txt");
        file.setReadOnly();

        this.isNewGame = false;
        this.isGameOver = false;

        Point head = new Point(BoardPanel.COL_COUNT / 2, BoardPanel.ROW_COUNT / 2);

        snake.clear();
        snake.add(head);

        board.clearBoard();
        board.setTile(head, TileType.SnakeHead);

        directions.clear();
        directions.add(Direction.Up);

        logicTimer.reset();
        spawnFruit();
        music.playMusic();
    }

    private void newMediumGame() {
        this.score = 0;
        this.fruitsEaten = 0;
        this.logicTimer = new Clock(20.0f);
        this.difficulty = "Medium";
        this.date = new Date();
        File file = new File("MediumHighScores.txt");
        file.setReadOnly();

        this.isNewGame = false;
        this.isGameOver = false;

        Point head = new Point(BoardPanel.COL_COUNT / 2, BoardPanel.ROW_COUNT / 2);

        snake.clear();
        snake.add(head);

        board.clearBoard();
        board.setTile(head, TileType.SnakeHead);

        directions.clear();
        directions.add(Direction.Up);

        logicTimer.reset();
        drawMediumLevelWalls();

        spawnFruit();
        music.playMusic();
    }

    private void newHardGame() {
        this.score = 0;
        this.fruitsEaten = 0;
        this.logicTimer = new Clock(25.0f);
        this.difficulty = "Hard";
        this.date = new Date();
        File file = new File("HardHighScores.txt");
        file.setReadOnly();


        this.isNewGame = false;
        this.isGameOver = false;

        Point head = new Point(BoardPanel.COL_COUNT / 2, BoardPanel.ROW_COUNT / 2);

        snake.clear();
        snake.add(head);

        board.clearBoard();
        board.setTile(head, TileType.SnakeHead);

        directions.clear();
        directions.add(Direction.Up);

        logicTimer.reset();
        drawHardLevelWalls();

        spawnFruit();
        music.playMusic();
    }

    public boolean isViewHigscores() {
        return viewHigscores;
    }

    public boolean isNewGame() {
        return isNewGame;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public boolean isPaused() {
        return isPaused;
    }

    private void drawMediumLevelWalls() {
        int x1, x2, x3, x4, x5, x6, x7, x8, x9;
        int y1, y2, y3, y4, y5, y6, y7, y8, y9;
        x1 = 5; x2 = 10; x3 = 15; x4 = 20; x5 = 25; x6 = 7; x7 = 1; x8 = 20; x9 = 21;
        y1 = 7; y2 = 3; y3 = 9; y4 = 2; y5 = 8; y6 = 20; y7 = 15; y8 = 18; y9 = 15;

        board.setTile(x1, y1, TileType.Wall);
        board.setTile(x2, y2, TileType.Wall);
        board.setTile(x3, y3, TileType.Wall);
        board.setTile(x4, y4, TileType.Wall);
        board.setTile(x5, y5, TileType.Wall);
        board.setTile(x6, y6, TileType.Wall);
        board.setTile(x7, y7, TileType.Wall);
        board.setTile(x8, y8, TileType.Wall);
        board.setTile(x9, y9, TileType.Wall);

    }


    private void drawHardLevelWalls() {
        int x1, x2, x3, x4, x5, x6, x7, x8, x9, x10, x11, x12;
        int y1, y2, y3, y4, y5, y6, y7, y8, y9, y10, y11, y12;

        x1 = 1; x2 = 2; x3 = 3; x4 = 4; x5 = 5; x6 = 6;
        y1 = 1; y2 = 2; y3 = 3; y4 = 4; y5 = 5; y6 = 6;

        x7 = 18; x8 = 19; x9 = 20; x10 = 21; x11 = 22; x12 = 23;
        y7 = 18; y8 = 19; y9 = 20; y10 = 21; y11 = 22; y12 = 23;

        board.setTile(x1, y1, TileType.Wall);
        board.setTile(x2, y2, TileType.Wall);
        board.setTile(x3, y3, TileType.Wall);
        board.setTile(x4, y4, TileType.Wall);
        board.setTile(x5, y5, TileType.Wall);
        board.setTile(x6, y6, TileType.Wall);
        board.setTile(x7, y7, TileType.Wall);
        board.setTile(x8, y8, TileType.Wall);
        board.setTile(x9, y9, TileType.Wall);
        board.setTile(x10, y10, TileType.Wall);
        board.setTile(x11, y11, TileType.Wall);
        board.setTile(x12, y12, TileType.Wall);

    }

    private void spawnFruit() {
        this.nextFruitScore = 100;

        int index = random.nextInt(BoardPanel.COL_COUNT * BoardPanel.ROW_COUNT - snake.size());

        int freeFound = -1;
        for(int x = 0; x < BoardPanel.COL_COUNT; x++)  {
            for (int y = 0; y < BoardPanel.ROW_COUNT; y++) {
                TileType tileType = board.getTile(x, y);
                if(tileType == TileType.Wall) {
                    if(++freeFound == index) {
                        board.setTile(x + 1, y, TileType.Fruit);
                        break;
                    }
                }
                else if(tileType == null || tileType == TileType.Fruit){
                    if(++freeFound == index) {
                        board.setTile(x, y, TileType.Fruit);
                        break;
                    }
                }
            }
        }
    }
    public Date getDate() {
        return date;
    }

    public String getName() {
        return name;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void clearDifficulty() {
        this.difficulty = "?";
    }

    public void clearName() {
        this.name = "?";
    }

    public int getScore() {
        return score;
    }

    public int getFruitsEaten() {
        return fruitsEaten;
    }

    public int getNextFruitScore() {
        return nextFruitScore;
    }

    public Direction getDirection() {
        return directions.peek();
    }

    public static void main(String[] args) {
        SnakeGame snakeGame = new SnakeGame();
        snakeGame.startGame();
    }

}