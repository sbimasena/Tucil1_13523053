import java.util.*;

public class Shape {
    private char letter;
    private List<int[]> normCoords;
    private List<List<int[]>> transformations;

    public Shape(char letter, List<int[]> coords) {
        this.letter = letter;
        this.normCoords = normalize(coords);
        this.transformations = genTransforms();
    }

    private List<int[]> normalize(List<int[]> coords){
        int minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE;
        for (int[] point : coords) {
            minX = Math.min(minX, point[0]);
            minY = Math.min(minY, point[1]);
        }
        List<int[]> normalized = new ArrayList<>();
        for (int[] point : coords) {
            normalized.add(new int[]{point[0] - minX, point[1] - minY});
        }
        return normalized;
    }

    private List<int[]> rotate(List<int[]> coords){
        List<int[]> result = new ArrayList<>();
        for (int[] point : coords){
            result.add(new int[]{point[1], -point[0]});
        }
        return normalize(result);
    }

    private List<int[]> mirror(List<int[]> coords){
        List<int[]> result = new ArrayList<>();
        for (int[] point : coords){
            result.add(new int[]{point[0], -point[1]});
        }
        return normalize(result);
    }

    private List<List<int[]>> genTransforms(){
        List<List<int[]>> results = new ArrayList<>();
        List<int[]> current = normCoords;

        for(int i = 0; i<4; i++){
            results.add(current);
            current = rotate(current);
        }

        current = mirror(normCoords);
        for(int i = 0; i<4; i++){
            results.add(current);
            current = rotate(current);
        }

        return results;
    }

    public char getLetter() {
        return letter;
    }

    public List<int[]> getCoords(int rotationIndex) {
        return transformations.get(rotationIndex);
    }

    public int getTransformationCount() {
        return transformations.size();
    }
}
