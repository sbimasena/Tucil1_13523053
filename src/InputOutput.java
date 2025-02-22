import java.io.*;
import java.util.*;

public class InputOutput {
    private int n, m, p;
    private String type;
    private List<Shape> shapes;

    private static final String RESET = "\u001B[0m";
    private static final String[] COLORS = {
        "\u001B[31m", // Red
        "\u001B[32m", // Green
        "\u001B[33m", // Yellow
        "\u001B[34m", // Blue
        "\u001B[35m", // Magenta
        "\u001B[36m", // Cyan
        "\u001B[91m", // Bright Red
        "\u001B[92m", // Bright Green
        "\u001B[93m", // Bright Yellow
        "\u001B[94m", // Bright Blue
        "\u001B[95m", // Bright Magenta
        "\u001B[96m", // Bright Cyan
        "\u001B[41m", // Red Background
        "\u001B[42m", // Green Background
        "\u001B[43m", // Yellow Background
        "\u001B[44m", // Blue Background
        "\u001B[45m", // Magenta Background
        "\u001B[46m", // Cyan Background
        "\u001B[101m", // Bright Red Background
        "\u001B[102m", // Bright Green Background
        "\u001B[103m", // Bright Yellow Background
        "\u001B[104m", // Bright Blue Background
        "\u001B[105m", // Bright Magenta Background
        "\u001B[106m", // Bright Cyan Background
        "\u001B[1m",  // Bold
        "\u001B[4m"   // Underline
    };

    public InputOutput(String filename){
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
            List<int[]> coordinates = new ArrayList<>();
            char shapeLetter = 0;
            int minCol = Integer.MAX_VALUE;
            int row = 0, shapeStartRow = 0;
            while ((line = br.readLine()) != null){
                if (line.trim().isEmpty()) continue;

                int firstNonSpaceIdx = -1;
                for (int i = 0; i < line.length(); i++) {
                    if (line.charAt(i) != ' ') {
                        firstNonSpaceIdx = i;
                        break;
                    }
                }
                if (firstNonSpaceIdx == -1) continue;

                char firstChar = line.charAt(firstNonSpaceIdx);
                
                if (shapeLetter == 0 || firstChar != shapeLetter){
                    if(!coordinates.isEmpty()){
                        for (int[] coord: coordinates){
                            coord[1]-= minCol;
                        }
                        shapes.add(new Shape(shapeLetter, new ArrayList<>(coordinates)));
                        coordinates.clear();
                    }
                    shapeLetter = firstChar;
                    minCol = Integer.MAX_VALUE;
                    shapeStartRow = coordinates.isEmpty() ? 0 : shapeStartRow + 1;
                }

                char[] chars = line.toCharArray();
                for (int col = 0; col < chars.length; col++){
                    if (chars[col] == shapeLetter){
                        if (shapeStartRow == -1) shapeStartRow = 0;
                        coordinates.add(new int[]{shapeStartRow, col});
                        minCol = Math.min(minCol, col);
                    }
                }
                shapeStartRow++;
            }

            if (!coordinates.isEmpty()){
                for (int[] coord: coordinates){
                    coord[1]-= minCol;
                }
                shapes.add(new Shape(shapeLetter, new ArrayList<>(coordinates)));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Map<Character, String> colorMap = new HashMap<>();
    static {
        char letter = 'A';
        for (int i = 0; i < COLORS.length && letter <= 'Z'; i++, letter++) {
            colorMap.put(letter, COLORS[i]);
        }
    }

    public static void writeOutput(Board board, Solver solver, long time) {
        System.out.println();
        if(solver.found){
            char[][] grid = board.getGrid();

            StringBuilder content = new StringBuilder();
            for (char[] row : grid) {
                StringBuilder outputRow = new StringBuilder();
                for (char cell : row) {
                    if (cell == '.') {
                        outputRow.append(cell); // Keep empty cells as normal '.'
                    } else {
                        String color = colorMap.getOrDefault(cell, RESET);
                        outputRow.append(color).append(cell).append(RESET);
                    }
                    content.append(cell);
                }
                content.append("\n");
                System.out.println(outputRow);
            }

            System.out.println();
            System.out.println("Waktu pencarian: " + time + " ms");
            content.append("Waktu pencarian: " + time + " ms\n");
            System.out.println();
            System.out.println("Banyak kasus yang ditinjau: " + solver.getTries());
            content.append("Banyak kasus yang ditinjau: " + solver.getTries()+ "\n");
            System.out.println();

            Scanner scanner = new Scanner(System.in);
            String choice;
            while (true) {
                System.out.print("Apakah anda ingin menyimpan solusi? (ya/tidak): ");
                choice = scanner.nextLine().trim().toLowerCase();
                
                if (choice.equals("ya") || choice.equals("tidak")) {
                    break;
                }
                System.out.println("Input tidak valid! Harap masukkan 'ya' atau 'tidak'.");
            }

            if (choice.equals("ya")) {
                System.out.print("Masukkan nama file untuk menyimpan hasil (namafile.txt): ");
                String fileName = scanner.next();

                String directoryPath = "test/Output/";
                String extension = ".txt";
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(directoryPath+fileName+extension))) {
                    writer.write(content.toString()); 
                } catch (IOException e) {
                    System.out.println("Terjadi kesalahan saat menulis ke file: " + e.getMessage());
                }
                System.out.println("Hasil berhasil disimpan ke dalam file " + fileName);
            } else if (choice.equals("tidak")) {
                System.out.println("Solusi tidak disimpan.");
            }

            scanner.close();
        } else {
            System.out.println("Tidak ada solusi yang ditemukan");
            System.out.println();
            System.out.println("Waktu pencarian: " + time + " ms");
            System.out.println();
            System.out.println("Banyak kasus yang ditinjau: " + solver.getTries());
            System.out.println();  
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
