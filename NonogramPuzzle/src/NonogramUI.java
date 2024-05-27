import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class NonogramUI extends JFrame {
    private boolean[][] userSolution;
    private Nonogram puzzle;
    private JButton[][] gridButtons;
    private JLabel[] rowLabels;
    private JLabel[] colLabels;

    public NonogramUI(Nonogram puzzle) {
        this.puzzle = puzzle;
        this.userSolution = new boolean[puzzle.getPixelData().length][puzzle.getPixelData()[0].length];
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.insets = new Insets(5, 5, 5, 5); // padding for compoents

        // clues to the top of the grid 
        gbc.gridx = 1;
        gbc.gridy = 0;
        JPanel colPanel = new JPanel(new GridLayout(1, 15));
        colLabels = new JLabel[15];
        int maxColClueLength = getMaxClueLength(puzzle.getColClues());
        for (int i = 0; i < 15; i++) {
            colLabels[i] = new JLabel("<html>" + getColClues(i).replace(" ", "<br>") + "</html>"); // br is for a line break HTML makes the text be interpreted as html (provides more control of layout)
            colLabels[i].setHorizontalAlignment(SwingConstants.CENTER);
            colLabels[i].setVerticalAlignment(SwingConstants.BOTTOM);
            colLabels[i].setPreferredSize(new Dimension(30, 30 * maxColClueLength));
            colPanel.add(colLabels[i]);
        }
        add(colPanel, gbc);

        // for the clues on the left of the grid 
        gbc.gridx = 0;
        gbc.gridy = 1;
        JPanel rowPanel = new JPanel(new GridLayout(15, 1));
        rowLabels = new JLabel[15];
        int maxRowClueLength = getMaxClueLength(puzzle.getRowClues());
        for (int i = 0; i < 15; i++) {
            rowLabels[i] = new JLabel(getRowClues(i));
            rowLabels[i].setHorizontalAlignment(SwingConstants.RIGHT);
            rowLabels[i].setPreferredSize(new Dimension(30 * maxRowClueLength, 30));
            rowPanel.add(rowLabels[i]);
        }
        add(rowPanel, gbc);

        // grid
        gbc.gridx = 1;
        gbc.gridy = 1;
        JPanel gridPanel = new JPanel(new GridLayout(15, 15));
        gridButtons = new JButton[15][15];
        for (int y = 0; y < 15; y++) {
            for (int x = 0; x < 15; x++) {
                gridButtons[y][x] = new JButton();
                gridButtons[y][x].setPreferredSize(new Dimension(30, 30));
                gridButtons[y][x].setBackground(Color.lightGray);
                gridButtons[y][x].addActionListener(new GridButtonListener(y, x));
                gridPanel.add(gridButtons[y][x]);
            }
        }
        add(gridPanel, gbc);

        // Adding the check solution button
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton checkButton = new JButton("Check Solution");
        checkButton.setBackground(Color.gray);
        checkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkSolution();
            }
        });
        add(checkButton, gbc);

                // Adding the check solution button
                gbc.gridx = 1;
                gbc.gridy = 3;
                gbc.gridwidth = 2;
                gbc.anchor = GridBagConstraints.CENTER;
                JButton hintButton = new JButton("HINT");
                hintButton.setBackground(Color.green);
                hintButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        giveHint();
                    }
                });
                add(hintButton, gbc);

                // reset puzzle button 
                gbc.gridx =1;
                gbc.gridy =4;
                JButton resetButton = new JButton("Reset puzzle");
                resetButton.setBackground(Color.LIGHT_GRAY);
                resetButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e){
                    resetPuzzle();
                    }  
                } );
                add(resetButton, gbc);
    
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Nonogram Puzzle");
        setVisible(true);
    }

    private String getRowClues(int rowIndex) {
        int[] rowClues = getClues(puzzle.getPixelData()[rowIndex]);
        return formatClues(rowClues);
    }

    private String getColClues(int colIndex) {
        boolean[] colData = new boolean[puzzle.getPixelData().length];
        for (int i = 0; i < puzzle.getPixelData().length; i++) {
            colData[i] = puzzle.getPixelData()[i][colIndex];
        }
        int[] colClues = getClues(colData);
        return formatClues(colClues);
    }

    private int getMaxClueLength(int[][] clues) {
        int maxLength = 0;
        for (int[] clueSet : clues) {
            int sum = clueSet.length;
            maxLength = Math.max(maxLength, sum);
        }
        return maxLength;
    }

    private int[] getClues(boolean[] data) {
        List<Integer> clues = new ArrayList<>();
        int count = 0;
        for (boolean pixel : data) {
            if (pixel) {
                count++;
            } else {
                if (count > 0) {
                    clues.add(count);
                    count = 0;
                }
            }
        }
        if (count > 0) {
            clues.add(count);
        }
        return clues.stream().mapToInt(Integer::intValue).toArray();
    }

    private String formatClues(int[] clues) {
        StringBuilder sb = new StringBuilder();
        for (int clue : clues) {
            sb.append(clue).append(" ");
        }
        return sb.toString().trim();
    }

    private void checkSolution() {
        boolean correct = true;
        boolean[][] solution = puzzle.getPixelData();
        for (int y = 0; y < solution.length; y++) {
            for (int x = 0; x < solution[0].length; x++) {
                if (userSolution[y][x] != solution[y][x]) {
                    correct = false;
                    gridButtons[y][x].setBackground(Color.RED);
                } else if (userSolution[y][x]) {
                    gridButtons[y][x].setBackground(Color.BLACK);
                } else {
                    gridButtons[y][x].setBackground(Color.WHITE);
                }
            }
        }
    
    if (correct) {
            JOptionPane.showMessageDialog(this, "Puzzle solved correctly!");
        } else {
            JOptionPane.showMessageDialog(this, "There are mistakes in the solution.");
        }
    }
    
    private void resetPuzzle() {
         for(int i=0; i< userSolution.length; i++){
                for (int j=0; j<userSolution[i].length; j++) {
                        userSolution[i][j] = false;
                        gridButtons[i][j].setBackground(Color.lightGray);
                    }
                }
     }

    

       

    private void giveHint() {
            JOptionPane.showMessageDialog(this, "It's the biggest mammal on land");
        }
    
    private class GridButtonListener implements ActionListener {
        private int y;
        private int x;

        public GridButtonListener(int y, int x) {
            this.y = y;
            this.x = x;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            userSolution[y][x] = !userSolution[y][x];
            if (userSolution[y][x]) {
                gridButtons[y][x].setBackground(Color.BLACK);
            } else {
                gridButtons[y][x].setBackground(Color.WHITE);
            }
        }
    }
}

