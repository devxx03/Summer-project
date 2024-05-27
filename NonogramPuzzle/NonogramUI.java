import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NonogramUI extends JFrame {
    private boolean[][] userSolution;
    private Nonogram puzzle;
    private JButton[][] gridButtons;

    public NonogramUI(Nonogram puzzle) {
        this.puzzle = puzzle;
        this.userSolution = new boolean[puzzle.getPixelData().length][puzzle.getPixelData()[0].length];
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Nonogram Puzzle");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel gridPanel = new JPanel();
        int rows = userSolution.length;
        int cols = userSolution[0].length;
        gridPanel.setLayout(new GridLayout(rows, cols));
        gridButtons = new JButton[rows][cols];

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                gridButtons[y][x] = new JButton();
                gridButtons[y][x].setBackground(Color.YELLOW);
                gridButtons[y][x].setOpaque(true);
                final int fy = y;
                final int fx = x;
                gridButtons[y][x].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        toggleCell(fy, fx);
                    }
                });
                gridPanel.add(gridButtons[y][x]);
            }
        }

        add(gridPanel, BorderLayout.CENTER);

        JPanel cluePanel = new JPanel();
        cluePanel.setLayout(new GridLayout(rows + 1, cols + 1));
        cluePanel.add(new JLabel(""));

        for (int x = 0; x < cols; x++) {
            cluePanel.add(new JLabel(formatClues(puzzle.getColClues()[x])));
        }

        for (int y = 0; y < rows; y++) {
            cluePanel.add(new JLabel(formatClues(puzzle.getRowClues()[y])));
            for (int x = 0; x < cols; x++) {
                cluePanel.add(new JLabel(""));
            }
        }

        add(cluePanel, BorderLayout.NORTH);

        JButton checkButton = new JButton("Check Solution");
        checkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkSolution();
            }
        });
        add(checkButton, BorderLayout.SOUTH);
    }

    private void toggleCell(int y, int x) {
        userSolution[y][x] = !userSolution[y][x];
        gridButtons[y][x].setBackground(userSolution[y][x] ? Color.BLACK : Color.YELLOW);
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
                    gridButtons[y][x].setBackground(Color.YELLOW);
                }
            }
        }
        if (correct) {
            JOptionPane.showMessageDialog(this, "Puzzle solved correctly!");
        } else {
            JOptionPane.showMessageDialog(this, "There are mistakes in the solution.");
        }
    }

    private String formatClues(int[] clues) {
        StringBuilder sb = new StringBuilder();
        for (int clue : clues) {
            sb.append(clue).append(" ");
        }
        return sb.toString().trim();
    }
}