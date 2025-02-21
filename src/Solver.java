import java.util.*;

public class Solver {
    private Board board;
    private List<Shape> shapes;
    private int tries;
    public boolean found = false;

    public Solver(Board board, List<Shape> shapes) {
        this.board = board;
        this.shapes = shapes;
        this.tries = 0;
    }

    public boolean solve() {
        if(solveRecursive(new ArrayList<>(shapes), 0, 0)){
            found = true;
            return true;
        } else {
            return false;
        }
    }

    private boolean solveRecursive(List<Shape> remainingShapes, int x, int y) {
        if (remainingShapes.isEmpty()) {
            return true;
        }

        if (x >= board.getRows()) {
            return false;
        }

        int nextX = x, nextY = y + 1;
        if (nextY >= board.getCols()) {
            nextX = x + 1;
            nextY = 0;
        }

        if (!board.isEmpty(x, y)) {
            return solveRecursive(remainingShapes, nextX, nextY);
        }

        for (int i = 0; i < remainingShapes.size(); i++) {
            Shape shape = remainingShapes.get(i);

            for (int r = 0; r < shape.getTransformationCount(); r++) {
                tries++;
                if (board.canPlaceShape(shape, x, y, r)) {                  
                    board.placeShape(shape, x, y, r);
                    remainingShapes.remove(i);

                    if (solveRecursive(remainingShapes, nextX, nextY)) {
                        return true;  
                    }
                    board.removeShape(shape, x, y, r);
                    remainingShapes.add(i, shape);
                }
            }
        }

        return solveRecursive(remainingShapes, nextX, nextY);
    }

    public int getTries() {
        return tries;
    }
}
