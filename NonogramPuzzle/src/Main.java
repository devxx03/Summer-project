import java.io.IOException; //error handling

public class Main {
    public static void main(String[] args) {

        try {
            
            String bmpFilePath = "/home/guptad6/h-drive/NonogramPuzzle/elephant.bmp/"; 

            //reads bmp file
            BMPReader bmpReader = new BMPReader(bmpFilePath);
            boolean[][] pixelData = bmpReader.getPixelData();
            Nonogram puzzle = new Nonogram(pixelData);
            NonogramUI ui = new NonogramUI(puzzle);
            ui.setVisible(true);

        } catch (IOException e) {
            //handles the ioexception if the bmp file is not read
            e.printStackTrace();
            System.out.println("There was an error reading the BMP file.\n Please try again");
        }
    }
}