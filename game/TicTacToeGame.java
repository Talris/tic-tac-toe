package game;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: talry
 * Date: 31.03.21
 * Time: 17:26
 * To change this template use File | Settings | File Templates.
 */
public class TicTacToeGame implements ActionListener{
    public static final long SLEEP_TIME = 750;
    Button[] squares;
    int [] duplicateClics;
    JButton newGameButton;
    JLabel score;
    int emptySquaresLeft = 9;
    int wins = readStatFile("wins");
    int losses = readStatFile("losses");
    int ties = readStatFile("ties");
    JLabel winStatLbl;
    JLabel lossStatLbl;
    JLabel tieStatLbl;

    public JComponent createContentPane() {
        duplicateClics = new int[9];
        for (int i = 0; i < 9; i++) {
            duplicateClics[i] = 0;
        }
        JPanel root = new JPanel(new BorderLayout());
        root.setPreferredSize(new Dimension(350, 400));


        Font font =  new Font("Monospased", Font.BOLD, 24);

        newGameButton = new JButton("New game");
        newGameButton.setEnabled(false);
        newGameButton.setFont(font);
        newGameButton.addActionListener(this);

        JPanel topPanel = new JPanel();
        topPanel.add(newGameButton, BorderLayout.CENTER);
        topPanel.setBorder(new EmptyBorder(15, 100, 15, 100));

        score = new JLabel("Your turn");
        score.setFont(font);
        // JLabel p1 = new JLabel("player 1");
        // JLabel p2 = new JLabel("player 2");

        GridLayout grid = new GridLayout(3, 3, 5, 5);
        JPanel gameBoard = new JPanel(grid);
        squares = new Button[9];

        for (int i = 0; i < 9; i++) {
            squares[i] = new Button();
            squares[i].addActionListener(this);
            // squares[i].setPreferredSize(new Dimension(90, 90));
            squares[i].setBackground(Color.orange);
            squares[i].setFont(new Font("Monospased", Font.BOLD, 90));
            squares[i].setLabel("");

            gameBoard.add(squares[i]);
        }
        JPanel infoPanel = new JPanel(new BorderLayout());
        JPanel statPanel = new JPanel(new FlowLayout());
        //statPanel.setLayout();
        winStatLbl = new JLabel("Wins: " + wins + ";");
        lossStatLbl = new JLabel("Losses: " + losses + ";");
        tieStatLbl = new JLabel("Ties: " + ties + ".");

        infoPanel.add(score, BorderLayout.NORTH);
        statPanel.add(winStatLbl);
        statPanel.add(lossStatLbl);
        statPanel.add(tieStatLbl);
        infoPanel.add(statPanel, BorderLayout.SOUTH);

        root.add(topPanel, BorderLayout.NORTH);
        root.add(gameBoard, BorderLayout.CENTER);
        // root.add(score, BorderLayout.SOUTH);
        root.add(infoPanel, BorderLayout.SOUTH);

        return root;
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        try {
            String winner = "";

            Object src = evt.getSource();

            if (src == newGameButton) {
                newGameButton.setEnabled(false);
                score.setText("Your turn!");
                for (int i = 0; i < 9; i++) {
                    squares[i].setEnabled(true);
                    squares[i].setLabel("");
                    squares[i].setBackground(Color.orange);
                }
                emptySquaresLeft = 9;
            }

            for (int i = 0; i < 9; i++) {
                if (src == squares[i]) {
                    if (duplicateClics[i] == 0) {
                        duplicateClics[i] = 1;
                        squares[i].setLabel("X");

                        winner = lookForWinner();

                        if (!winner.equals("")) {
                            endTheGame(winner);
                        } else {
                            score.setText("Computer`s turn!");
                            computurMoveThread();
                        }
                        break;
                    }
                    break;
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void computurMoveThread() {
        SwingWorker sw = new SwingWorker() {
            @Override
            protected String doInBackground() throws Exception {
                String winner = "";
                delay();
                computerMove();
                winner = lookForWinner();
                if (!winner.equals("")) {
                    score.setText(winner);
                    endTheGame(winner);
                } else {
                    String turn;
                    turn = "Your turn!";
                    // System.out.println("chng turn");
                    publish(turn);
                }
                return null;
            }

            @Override
            protected void process(java.util.List chunks) {
                String s = chunks.get(0).toString();
                score.setText(s);
            }

            //
        };
        sw.execute();
    }

    private int readStatFile(String str) {
        int num = 0;
        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader(Main.statFile));
            String line = null;

            while ((line = br.readLine()) != null) {
                if (line.startsWith(str)) {
                    String value = line.substring(line.lastIndexOf(" ") + 1);
                    num = Integer.parseInt(value);
                    break;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            }  catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return num;
    }
    /*
    private int readStatFile(String str) {
        int n = 0;
        try {

            Scanner sc = new Scanner(game.Main.statFile);
            while (sc.hasNext()) {
                String s = sc.next();
                // System.out.println(sc.next());
                if (s.startsWith(str)) {
                    while (!sc.hasNextInt()) {
                        sc.next();
                    }
                    n = sc.nextInt();
                    break;

                } else {
                    sc.nextLine();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return n;
    }
    */

    private void delay() {

        for (int i = 0; i < 9; i++) {
            if (duplicateClics[i] == 0) {
                squares[i].setEnabled(false);
            }
        }
        try {
            Thread.sleep(SLEEP_TIME);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        for (int i = 0; i < 9; i++) {
            squares[i].setEnabled(true);
        }
    }


    private void computerMove() {

        int selectedSquare;
        selectedSquare = findEmptySquare("O");

        if (selectedSquare == -1) {
            selectedSquare = findEmptySquare("X");
        }
        if (selectedSquare == -1 && squares[4].getLabel().equals("")) {
            selectedSquare = 4;
        }
        if (selectedSquare == -1) {
            selectedSquare = randomeSquare();
        }
        squares[selectedSquare].setLabel("O");
        duplicateClics[selectedSquare] = 1;
    }

    private int randomeSquare() {
        int selectedSquare = -1;
        boolean isEmptySquare = false;

        Random rd = new Random();

        while (!isEmptySquare) {
            selectedSquare = rd.nextInt(9);
            // System.out.println(selectedSquare);
            if (squares[selectedSquare].getLabel().equals("")) {
                isEmptySquare = true;
            }
        }

        return selectedSquare;
    }

    private int findEmptySquare(String player) {
        int[] weight = new int[9];

        for (int i = 0; i < 9; i++) {
            if (squares[i].getLabel().equals("O")) {
                weight[i] = -1;
            } else if (squares[i].getLabel().equals("X")) {
                weight[i] = 1;
            } else {
                weight[i] = 0;
            }
        }
        int twoWeights = player.equals("O") ? -2 : 2;

        //top row
        if (weight[0] + weight[1] + weight[2] == twoWeights) {
            if (weight[0] == 0) {
                return 0;
            } else if (weight[1] == 0) {
                return 1;
            } else {
                return 2;
            }
        }

        // middle row
        if (weight[3] + weight[4] + weight[5] == twoWeights) {
            if (weight[3] == 0) {
                return 3;
            } else if (weight[4] == 0) {
                return 4;
            } else {
                return 5;
            }
        }
        // bottom row
        if (weight[6] + weight[7] + weight[8] == twoWeights) {
            if (weight[6] == 0) {
                return 6;
            } else if (weight[7] == 0) {
                return 7;
            } else {
                return 8;
            }
        }
        // left column
        if (weight[0] + weight[3] + weight[6] == twoWeights) {
            if (weight[0] == 0) {
                return 0;
            } else if (weight[3] == 0) {
                return 3;
            } else {
                return 6;
            }
        }
        // middle column
        if (weight[1] + weight[4] + weight[7] == twoWeights) {
            if (weight[1] == 0) {
                return 1;
            } else if (weight[4] == 0) {
                return 4;
            } else {
                return 7;
            }
        }
        // right column
        if (weight[2] + weight[5] + weight[8] == twoWeights) {
            if (weight[2] == 0) {
                return 2;
            } else if (weight[5] == 0) {
                return 5;
            } else {
                return 8;
            }
        }
        //first diagonal
        if (weight[0] + weight[4] + weight[8] == twoWeights) {
            if (weight[0] == 0) {
                return 0;
            } else if (weight[4] == 0) {
                return 4;
            } else {
                return 8;
            }
        }
        //second diagonal
        if (weight[2] + weight[4] + weight[6] == twoWeights) {
            if (weight[2] == 0) {
                return 2;
            } else if (weight[4] == 0) {
                return 4;
            } else {
                return 6;
            }
        }

        return -1;
    }

    private String lookForWinner() {
        String winner = "";
        emptySquaresLeft--;
        if (emptySquaresLeft == 0) {
            return  "T";
        }
        // top row
        if (!squares[0].getLabel().equals("") &&
                squares[0].getLabel().equals(squares[1].getLabel()) &&
                    squares[0].getLabel().equals(squares[2].getLabel())) {
            winner = squares[0].getLabel();
            highlitedWinner(winner, 0, 1, 2);
        // middle row
        } else if (!squares[3].getLabel().equals("") &&
                squares[3].getLabel().equals(squares[4].getLabel()) &&
                squares[3].getLabel().equals(squares[5].getLabel())) {
            winner = squares[3].getLabel();
            highlitedWinner(winner, 3, 4, 5);
        // bottom roe
        } else if (!squares[6].getLabel().equals("") &&
                squares[6].getLabel().equals(squares[7].getLabel()) &&
                squares[6].getLabel().equals(squares[8].getLabel())) {
            winner = squares[6].getLabel();
            highlitedWinner(winner, 6, 7, 8);
        // left column
        } else if (!squares[0].getLabel().equals("") &&
                squares[0].getLabel().equals(squares[3].getLabel()) &&
                squares[0].getLabel().equals(squares[6].getLabel())) {
            winner = squares[0].getLabel();
            highlitedWinner(winner, 0, 3, 6);
        // middle column
        } else if (!squares[1].getLabel().equals("") &&
                squares[1].getLabel().equals(squares[4].getLabel()) &&
                squares[1].getLabel().equals(squares[7].getLabel())) {
            winner = squares[1].getLabel();
            highlitedWinner(winner, 1, 4, 7);
        // right column
        } else if (!squares[2].getLabel().equals("") &&
                squares[2].getLabel().equals(squares[5].getLabel()) &&
                squares[2].getLabel().equals(squares[8].getLabel())) {
            winner = squares[2].getLabel();
            highlitedWinner(winner, 2, 5, 8);
        // first diagonal
        } else if (!squares[0].getLabel().equals("") &&
                squares[0].getLabel().equals(squares[4].getLabel()) &&
                squares[0].getLabel().equals(squares[8].getLabel())) {
            winner = squares[0].getLabel();
            highlitedWinner(winner, 0, 4, 8);
        // second diagonal
        } else if (!squares[2].getLabel().equals("") &&
                squares[2].getLabel().equals(squares[4].getLabel()) &&
                squares[2].getLabel().equals(squares[6].getLabel())) {
            winner = squares[2].getLabel();
            highlitedWinner(winner, 2, 4, 6);

        }
        return winner;
    }

    private void highlitedWinner(String winner, int a1, int a2, int a3) {
        if (winner.equals("X")) {
            squares[a1].setBackground(Color.GREEN);
            squares[a2].setBackground(Color.GREEN);
            squares[a3].setBackground(Color.GREEN);
        } else {
            squares[a1].setBackground(Color.red);
            squares[a2].setBackground(Color.red);
            squares[a3].setBackground(Color.red);
        }
    }

    private void endTheGame(String winner) {
        newGameButton.setEnabled(true);
        for (int i = 0; i < 9; i++) {
            squares[i].setEnabled(false);
            duplicateClics[i] = 0;
        }

        if (winner.equals("X")) {
            score.setText("You won!");
            wins++;
            winStatLbl.setText("Wins: " + wins + ";");
        }
        else if (winner.equals("O")) {
            score.setText("You lost!");
            losses++;
            lossStatLbl.setText("Losses: " + losses + ";");
        }
        else if (winner.equals("T")) {
            score.setText("It`s a tie!");
            ties++;
            tieStatLbl.setText("Ties: " + ties + "." );
        }
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new BufferedWriter(new FileWriter(Main.statFile)));
            pw.format("wins = %d%n" +
                    "losses = %d%n" +
                    "ties = %d", wins, losses, ties);

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (pw != null) {
                    pw.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println("Closing error: " + ex);
            }
        }
    }

}
