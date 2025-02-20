import java.util.*;

public class Solver {
    private Board board;
    private List<Shape> shapes;
    private int tries;

    public Solver(Board board, List<Shape> shapes){
        this.board = board;
        this.shapes = shapes;
        this.tries = 0;
    }

    public boolean solve() {
        return solveRecursive(0);
    }

    private boolean solveRecursive(int shapeIndex) {
        if (shapeIndex == shapes.size()) return true; 

        Shape shape = shapes.get(shapeIndex);

        for (int r = 0; r < 4; r++) {
            for (int x = 0; x < board.getRows(); x++) {
                for (int y = 0; y < board.getCols(); y++) {
                    if (board.canPlaceShape(shape, x, y, r)) {
                        board.placeShape(shape, x, y, r);
                        tries++;
                        if (solveRecursive(shapeIndex + 1)) return true;
                        board.removeShape(shape, x, y, r);
                    }
                }
            }
        }
        return false;
    }

    public int getTries(){
        return tries;
    }
}
