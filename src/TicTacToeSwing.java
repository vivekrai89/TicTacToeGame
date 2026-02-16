package tictactoe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TicTacToeSwing extends JFrame implements ActionListener {

    private JButton[] buttons = new JButton[9];
    private JButton restartButton;
    private boolean xTurn = true;
    private final Color X_COLOR = Color.RED;
    private final Color O_COLOR = Color.BLUE;

    public TicTacToeSwing() {
        setTitle("Tic-Tac-Toe Swing");
        setSize(400, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Grid panel
        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(3, 3));

        for (int i = 0; i < 9; i++) {
            buttons[i] = new JButton("");
            buttons[i].setFont(new Font("Arial", Font.BOLD, 60));
            buttons[i].setFocusPainted(false);
            buttons[i].addActionListener(this);
            gridPanel.add(buttons[i]);
        }

        // Restart button
        restartButton = new JButton("Restart");
        restartButton.setFont(new Font("Arial", Font.BOLD, 20));
        restartButton.addActionListener(e -> resetBoard());

        add(gridPanel, BorderLayout.CENTER);
        add(restartButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clicked = (JButton) e.getSource();
        int index = -1;

        // Find button index
        for (int i = 0; i < buttons.length; i++) {
            if (buttons[i] == clicked) {
                index = i;
                break;
            }
        }

        if (!clicked.getText().equals("")) return;

        clicked.setText(xTurn ? "X" : "O");
        clicked.setForeground(xTurn ? X_COLOR : O_COLOR);

        System.out.println("Player " + (xTurn ? "X" : "O") + " clicked: (" + index/3 + "," + index%3 + ")");
        xTurn = !xTurn;

        checkWinner();
    }

    private void checkWinner() {
        int[][] winPatterns = {
                {0,1,2}, {3,4,5}, {6,7,8}, // rows
                {0,3,6}, {1,4,7}, {2,5,8}, // columns
                {0,4,8}, {2,4,6}            // diagonals
        };

        for (int[] pattern : winPatterns) {
            String a = buttons[pattern[0]].getText();
            String b = buttons[pattern[1]].getText();
            String c = buttons[pattern[2]].getText();

            if (!a.equals("") && a.equals(b) && b.equals(c)) {
                // Highlight winning line
                buttons[pattern[0]].setBackground(Color.GREEN);
                buttons[pattern[1]].setBackground(Color.GREEN);
                buttons[pattern[2]].setBackground(Color.GREEN);

                JOptionPane.showMessageDialog(this, a + " Wins!");
                return;
            }
        }

        boolean draw = true;
        for (JButton btn : buttons) {
            if (btn.getText().equals("")) {
                draw = false;
                break;
            }
        }

        if (draw) {
            JOptionPane.showMessageDialog(this, "It's a Draw!");
        }
    }

    private void resetBoard() {
        for (JButton btn : buttons) {
            btn.setText("");
            btn.setBackground(null);
        }
        xTurn = true;
        System.out.println("Board Reset!");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TicTacToeSwing::new);
    }
}
