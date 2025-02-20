import java.io.*;
import java.util.*;

public class InputOutput {
    private int n, m, p;
    private String type;
    private List<Shape> shapes;

    public void InputParser(String filename){
        shapes = new ArrayList<>();
        parseFile(filename);
    }

    private void parseFile(String filename){
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String[] firstLine = br.readLine().split(" ");
            n = Integer.parseInt(firstLine[0]);
            m = Integer.parseInt(firstLine[1]);
            p = Integer.parseInt(firstLine[2]);

            type = br.readLine().trim();

            String line;
            while ((line = br.readLine()) != null){
                if (line.trim().isEmpty()) continue;

                char shapeLetter = line.charAt(0);
                List<int[]> coordinates = new ArrayList<>();
                int row = 0;
                
                while ((line = br.readLine()) != null && !line.isEmpty()){
                    char[] characters = line.toCharArray();
                    for (int col = 0; col < characters.length; col++){
                        if (characters[col] == shapeLetter){
                            coordinates.add(new int[]{row, col});
                        }
                    }
                    row++;
                }

                shapes.add(new Shape(shapeLetter, coordinates));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getBoardRows() {
        return n; 
    }
    public int getBoardCols() {
        return m;
    }
    public int getShapeCount() {
        return p;
    }
    public String getType() {
        return type;
    }
    public List<Shape> getShapes() {
        return shapes;
    }
}
