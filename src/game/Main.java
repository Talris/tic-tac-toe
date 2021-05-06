package game;

import javax.swing.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

/**
 * Created with IntelliJ IDEA.
 * User: talry
 * Date: 31.03.21
 * Time: 16:31
 * To change this template use File | Settings | File Templates.
 */
public class Main {
    // statistic file path
    static String path = "/media/talry/ACER/Documents and Settings/Пользователь/" +
            "Мои документы/java/Проекты/IdeaProjects/TicTacToe/src/Statistic.txt";
    static File statFile;

    static JFrame frame;

    public static void createLookAndFeel() {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void prepareFile() {
        statFile = new File(path);
        PrintWriter pw = null;
        try {

            if (!statFile.exists()) {
                pw = new PrintWriter(new BufferedWriter(new FileWriter(statFile)));

                pw.print("wins = 0\nlosses = 0\nties = 0");
              }
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Write error: " + ex);
        }
        finally {
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
    public static void createGui() {
        createLookAndFeel();
        prepareFile();
        frame = new JFrame("Tic-Tac-Toe");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        TicTacToeGame comp = new TicTacToeGame();

        frame.setContentPane(comp.createContentPane());
        // frame.setPreferredSize(new Dimension(350, 400));
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createGui();
            }
        });
    }
}
