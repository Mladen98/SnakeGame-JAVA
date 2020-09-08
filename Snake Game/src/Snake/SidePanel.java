package Snake;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

public class SidePanel extends JPanel {
    private static final long serialVersionUID = -40557434900946408L;
    private static final Font LARGE_FONT = new Font("Tahoma", Font.BOLD, 20);
    private static final Font MEDIUM_FONT = new Font("Tahoma", Font.BOLD, 16);
    private static final Font SMALL_FONT = new Font("Tahoma", Font.BOLD, 12);
    private SnakeGame game;
    private static final int STATISTICS_OFFSET = 150;
    private static final int CONTROLS_OFFSET = 320;
    private static final int MESSAGE_STRIDE = 30;
    private static final int SMALL_OFFSET = 30;
    private static final int LARGE_OFFSET = 50;

    public SidePanel(SnakeGame game) {
        this.game = game;

        setPreferredSize(new Dimension(300, BoardPanel.ROW_COUNT * BoardPanel.TILE_SIZE));
        setBackground(Color.BLUE);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.WHITE);

        g.setFont(LARGE_FONT);
        g.drawString("Snake Game", getWidth() / 2 - g.getFontMetrics().stringWidth("Snake Game") / 2, 50);

        g.setFont(SMALL_FONT);
        g.drawString("Player name: " + game.getName(), getWidth() / 2 - 100, 110);

        g.setFont(MEDIUM_FONT);
        g.drawString("Scoreboard:", SMALL_OFFSET, STATISTICS_OFFSET);
        g.drawString("Controls:", SMALL_OFFSET, CONTROLS_OFFSET);

        g.setFont(SMALL_FONT);

        int drawY = STATISTICS_OFFSET;
        g.drawString("Total Score: " + game.getScore(), LARGE_OFFSET, drawY += MESSAGE_STRIDE);
        g.drawString("Fruit Eaten: " + game.getFruitsEaten(), LARGE_OFFSET, drawY += MESSAGE_STRIDE);
        g.drawString("Fruit Score: " + game.getNextFruitScore(), LARGE_OFFSET, drawY += MESSAGE_STRIDE);
        g.drawString("Difficulty: " + game.getDifficulty(), LARGE_OFFSET, drawY += MESSAGE_STRIDE);
        //Draw the content for the controls category.
        drawY = CONTROLS_OFFSET;
        g.drawString("Move Up: W / Up Arrowkey", LARGE_OFFSET, drawY += MESSAGE_STRIDE);
        g.drawString("Move Down: S / Down Arrowkey", LARGE_OFFSET, drawY += MESSAGE_STRIDE);
        g.drawString("Move Left: A / Left Arrowkey", LARGE_OFFSET, drawY += MESSAGE_STRIDE);
        g.drawString("Move Right: D / Right Arrowkey", LARGE_OFFSET, drawY += MESSAGE_STRIDE);
        g.drawString("Pause Game: P", LARGE_OFFSET, drawY += MESSAGE_STRIDE);
    }

}