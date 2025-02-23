import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class Gambar {
    private static final int CELL_SIZE = 50;
    private static final int PADDING = 20;
    private static final Color[] COLORS = {
        Color.RED, Color.BLUE, Color.GREEN, Color.ORANGE, Color.MAGENTA, 
        Color.CYAN, Color.PINK, Color.YELLOW, Color.GRAY, Color.LIGHT_GRAY, 
        new Color(128, 0, 128), new Color(255, 165, 0), new Color(0, 255, 127), 
        new Color(75, 0, 130), new Color(255, 20, 147), new Color(0, 139, 139), 
        new Color(139, 69, 19), new Color(255, 99, 71), new Color(173, 255, 47), 
        new Color(70, 130, 180), new Color(220, 20, 60), new Color(123, 104, 238), 
        new Color(255, 182, 193), new Color(0, 250, 154), new Color(199, 21, 133), 
        new Color(30, 144, 255)
    };

    public static void saveImage(char[][] grid, String filename) {
        int rows = grid.length;
        int cols = grid[0].length;

        int width = cols * CELL_SIZE + 2 * PADDING;
        int height = rows * CELL_SIZE + 2 * PADDING;

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);

        for(int r = 0; r < rows; r++){
            for(int c = 0; c < cols; c++){
                int x = PADDING + c * CELL_SIZE;
                int y = PADDING + r * CELL_SIZE;
                
                g.setColor(Color.BLACK);
                g.drawRect(x, y, CELL_SIZE, CELL_SIZE);

                if (grid[r][c] != '.'){
                    g.setColor(getColorForShape(grid[r][c]));
                    g.fillRect(x + 1, y + 1, CELL_SIZE - 2, CELL_SIZE - 2);
                }
            }
        }

        g.dispose();

        try {
            String directory = "test/Output";
            ImageIO.write(image, "png", new File(directory+filename));
            System.out.println("Grid saved as " + filename);
        } catch (Exception e) {
            e.printStackTrace();
        }     
    }

    private static Color getColorForShape(char shape) {
        int index = shape - 'A';
        if (index >= 0 && index < COLORS.length) {
            return COLORS[index];
        }
        return Color.DARK_GRAY;
    }
}