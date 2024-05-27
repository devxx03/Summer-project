import java.io.FileInputStream;
import java.io.IOException;

public class BMPReader {
    private int width;
    private int height;
    private boolean[][] pixelData; 

    public BMPReader(String filePath) throws IOException {
        FileInputStream fis = new FileInputStream(filePath); //to read bmp files
        byte[] header = new byte[54];
        fis.read(header, 0, 54);

        // this extracts width and height info from header
        width = ((header[21] & 0xFF) << 24) | ((header[20] & 0xFF) << 16) | ((header[19] & 0xFF) << 8) | (header[18] & 0xFF);
        height = ((header[25] & 0xFF) << 24) | ((header[24] & 0xFF) << 16) | ((header[23] & 0xFF) << 8) | (header[22] & 0xFF);

        pixelData = new boolean[height][width];
        int rowSize = ((width * 1 + 31) / 32) * 4;  
        byte[] row = new byte[rowSize];

        for (int y = height - 1; y >= 0; y--) {
            fis.read(row, 0, rowSize);
            for (int x = 0; x < width; x++) {
                int byteIndex = x / 8;
                int bitIndex = x % 8;
                boolean pixel = (row[byteIndex] & (1 << (7 - bitIndex))) != 0;
                pixelData[y][x] = pixel;
            }
        }
        fis.close(); // closes fileinputstream
    }

    public boolean[][] getPixelData() {
        return pixelData;
    }
}