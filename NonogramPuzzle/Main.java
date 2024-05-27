public class Main {
    public static void main(String[] args) {
        try {
            // Update the path to point to your BMP file
            String bmpFilePath = "/home/guptad6/h-drive/NonogramPuzzle/elephant.bmp"; 

            BMPReader bmpReader = new BMPReader(bmpFilePath);
            boolean[][] pixelData = bmpReader.getPixelData();
            Nonogram puzzle = new Nonogram(pixelData);
            NonogramUI ui = new NonogramUI(puzzle);
            ui.setVisible(true);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error reading the BMP file.");
        }
    }
}