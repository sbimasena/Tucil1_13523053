import java.util.List;

public class Main {
    public static void main(String[] args) {
        String filename = "test/puzzle.txt";
        InputOutput io = new InputOutput(filename);

        int n = io.getBoardRows();
        int m = io.getBoardCols();
        List<Shape> shapes = io.getShapes();
        
        Board board = new Board(n, m);
        Solver solver = new Solver(board, shapes);

        //System.out.println(shapes.getTransformationCount());

        if (solver.solve()) {
            InputOutput.writeOutput(board);
        } else {
            System.out.println("No solution found.");
            System.out.println(solver.getTries());
        }
    }
}
