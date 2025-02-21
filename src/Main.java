import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String filename;
        System.out.print("Masukkan nama file .txt input yang berada di folder test: ");
        filename = "test/"+ input.nextLine();
        InputOutput io = new InputOutput(filename);

        int n = io.getBoardRows();
        int m = io.getBoardCols();
        List<Shape> shapes = io.getShapes();
        
        Board board = new Board(n, m);
        Solver solver = new Solver(board, shapes);

        long startTime = System.nanoTime();

        solver.solve();

        long endTime = System.nanoTime();
        long executionTime = (endTime - startTime)/1_000_000;

        InputOutput.writeOutput(board, solver, executionTime);
    }
}
