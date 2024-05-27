import java.util.ArrayList;

public class Nonogram {
    private boolean[][] pixelData;
    private int[][] rowClues;
    private int[][] colClues;

    public Nonogram(boolean[][] pixelData) {
        this.pixelData = pixelData;
        generateClues();
    }

    private void generateClues() {
        int height = pixelData.length;
        int width = pixelData[0].length;
        rowClues = new int[height][];
        colClues = new int[width][];

        for (int y = 0; y < height; y++) {
            rowClues[y] = getClues(pixelData[y]);
        }

        for (int x = 0; x < width; x++) {
            boolean[] colData = new boolean[height];
            for (int y = 0; y < height; y++) {
                colData[y] = pixelData[y][x];
            }
            colClues[x] = getClues(colData);
        }
    }

    private int[] getClues(boolean[] line) {
        ArrayList<Integer> clues = new ArrayList<>();
        int count = 0;
        for (boolean pixel : line) {
            if (pixel) {
                count++;
            } else if (count > 0) {
                clues.add(count);
                count = 0;
            }
        }
        if (count > 0) {
            clues.add(count);
        }
        if (clues.isEmpty()) {
            clues.add(0);
        }
        return clues.stream().mapToInt(i -> i).toArray();
    }

    public int[][] getRowClues() {
        return rowClues;
    }

    public int[][] getColClues() {
        return colClues;
    }

    public boolean[][] getPixelData() {
        return pixelData;
    }
}
