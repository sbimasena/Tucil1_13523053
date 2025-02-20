import java.util.*;

public class Shape {
    private char letter;
    private List<int[]> normCoords;
    private List<List<int[]>> transformations;

    public Shape(char letter, List<int[]> coords) {
        this.letter = letter;
    }
}
