import java.util.*;

public class Board {
    private int rows, cols;
    private char[][] grid;

    public Board(int rows, int cols){
        this.rows = rows;
        this.cols = cols;
        this.grid = new char[rows][cols];

        for (int i = 0; i < rows; i++){
            Arrays.fill(grid[i], '.');
        }
    }

    public boolean canPlaceShape(Shape shape, int x, int y, int rotation) {
        List<int[]> shapeCoords = shape.getCoords(rotation);

        for (int[] coordinates : shapeCoords){
            int newRow = coordinates[0] + x;
            int newCol = coordinates[1] + y;

            if (newRow < 0 || newRow >= rows || newCol < 0 || newCol >= cols){
                return false;
            }

            if (!isEmpty(newRow, newCol)){
                return false;
            }
        }
        return true;
    }

    public boolean placeShape(Shape shape, int x, int y, int rotation) {
        if (canPlaceShape(shape, x, y, rotation)){
            char letter = shape.getLetter();
            List<int[]> shapeCoords = shape.getCoords(rotation);

            for (int[] coordinates : shapeCoords){
                int newRow = coordinates[0] + x;
                int newCol = coordinates[1] + y;

                grid[newRow][newCol] = letter;
            }
            return true;
        }

        return false;
    }

    public void removeShape(Shape shape, int x, int y, int rotation) {
        for (int[] offset : shape.getCoords(rotation)) {
            int newX = x + offset[0];
            int newY = y + offset[1];
            grid[newX][newY] = '.';
        }
    }
    

    public int getRows(){
        return rows;
    }

    public int getCols(){
        return cols;
    }

    public char[][] getGrid(){
        return grid;
    }

    public boolean isEmpty(int row, int col) {
        return grid[row][col] == '.'; 
    } 
}
