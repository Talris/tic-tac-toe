package game;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: talry
 * Date: 04.05.21
 * Time: 10:05
 * To change this template use File | Settings | File Templates.
 */
public class SettingsDialog extends JDialog implements ActionListener {
    public static final int DIALOG_WIDTH = 320;
    public static final int DIALOG_HEIGHT = 200;
    JPanel settingsPanel;
    JPanel bottomPanel;
    private JButton okButton;
    // JButton cancelButton;
    private JRadioButton weaponX;
    private JRadioButton weaponO;
    private JRadioButton playerFirst;
    private JRadioButton compFirst;
    private JRadioButton randomFirst;

    // default settings
    private String playerWeapon = "X";
    private String compWeapon = "O";
    private int selectedTurn = 1;

    public SettingsDialog() {
        super(Main.frame, "Settings", true);
        JPanel settingsContent = createDialogComponents();
        add(settingsContent);
        setSize(DIALOG_WIDTH, DIALOG_HEIGHT);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

    }

    private JPanel createDialogComponents() {
        JPanel root = new JPanel(new BorderLayout());

        settingsPanel = new JPanel();
        GridBagLayout gridbag = new GridBagLayout();

        settingsPanel.setLayout(gridbag);

        JLabel weaponLbl = new JLabel("Select your weapon:");
        weaponX = new JRadioButton("X", true);
        weaponO = new JRadioButton("O");
        weaponX.addActionListener(this);
        weaponO.addActionListener(this);

        JLabel firstTurn = new JLabel("Select who make a first turn:");
        playerFirst = new JRadioButton("You", true);
        compFirst = new JRadioButton("Computer");
        randomFirst = new JRadioButton("Randomly");
        playerFirst.addActionListener(this);
        compFirst.addActionListener(this);
        randomFirst.addActionListener(this);

        settingsPanel.add(weaponLbl, new GridBagConstraints(0, 0, 5, 1,
                1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(10, 0, 0, 0), 0, 0));

        settingsPanel.add(weaponX, new GridBagConstraints(1, 1, 1, 1,
                1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(5, 0, 0, 0), 0, 0));

        settingsPanel.add(weaponO, new GridBagConstraints(3, 1, 1, 1,
                1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(5, 0, 0, 0), 0, 0));

        settingsPanel.add(firstTurn, new GridBagConstraints(0, 2, 5, 1,
                1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(20, 0, 0, 0), 0, 0));

        settingsPanel.add(playerFirst, new GridBagConstraints(2, 3, 1, 1,
                1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(5, 0, 0, 0), 0, 0));
        // Insets(int top, int left, int bottom, int right)
        settingsPanel.add(compFirst, new GridBagConstraints(0, 3, 1, 1,
                1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(5, 0, 0, 0), 0, 0));

        settingsPanel.add(randomFirst, new GridBagConstraints(4, 3, 1, 1,
                1.0, 0.0, GridBagConstraints.CENTER, GridBagConstraints.NONE,
                new Insets(5, 0, 0, 0), 0, 0));



        okButton = new JButton("Ok");
        // cancelButton = new JButton("Cancel");

        // okButton.setPreferredSize(cancelButton.getPreferredSize());



        FlowLayout fl = new FlowLayout(FlowLayout.CENTER);
        bottomPanel = new JPanel(fl);
        bottomPanel.add(okButton);
        okButton.addActionListener(this);

        bottomPanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 5, 0));

        // bottomPanel.add(cancelButton);


        root.add(settingsPanel, BorderLayout.NORTH);
        root.add(bottomPanel, BorderLayout.SOUTH);

        return root;
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        try {
            Object src = evt.getSource();

            if (src == okButton) {

                // System.out.println(okButton.getHeight());
                // System.out.println(settingsPanel.getHeight());
                // System.out.println(bottomPanel.getHeight());

                if (weaponO.isSelected()) {
                    playerWeapon = "O";
                    compWeapon = "X";
                } else {
                    playerWeapon = "X";
                    compWeapon = "O";
                }

                if (compFirst.isSelected()) {
                    selectedTurn = 0;
                } else if (playerFirst.isSelected()) {
                    selectedTurn = 1;
                }
                this.dispose();
            }

            if (src == weaponO) {
                weaponX.setSelected(false);
                weaponO.setSelected(true);

            } else if (src == weaponX) {
                weaponX.setSelected(true);
                weaponO.setSelected(false);
            }


            if (src == playerFirst) {
                playerFirst.setSelected(true);
                compFirst.setSelected(false);
                randomFirst.setSelected(false);

            } else if (src == compFirst) {
                playerFirst.setSelected(false);
                compFirst.setSelected(true);
                randomFirst.setSelected(false);

            } else if (src == randomFirst) {
                playerFirst.setSelected(false);
                compFirst.setSelected(false);
                randomFirst.setSelected(true);
            }


        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public JButton getOkButton() {
        return okButton;
    }

    public void setOkButton(JButton okButton) {
        this.okButton = okButton;
    }

    public JRadioButton getWeaponX() {
        return weaponX;
    }

    public void setWeaponX(JRadioButton weaponX) {
        this.weaponX = weaponX;
    }

    public JRadioButton getWeaponO() {
        return weaponO;
    }

    public void setWeaponO(JRadioButton weaponO) {
        this.weaponO = weaponO;
    }

    public int getSelectedTurn() {
        return selectedTurn;
    }

    public void setSelectedTurn(int selectedTurn) {
        this.selectedTurn = selectedTurn;
    }

    public String getPlayerWeapon() {
        return playerWeapon;
    }

    public void setPlayerWeapon(String playerWeapon) {
        this.playerWeapon = playerWeapon;
    }

    public String getCompWeapon() {
        return compWeapon;
    }

    public void setCompWeapon(String compWeapon) {
        this.compWeapon = compWeapon;
    }

    public JRadioButton getPlayerFirst() {
        return playerFirst;
    }

    public void setPlayerFirst(JRadioButton playerFirst) {
        this.playerFirst = playerFirst;
    }

    public JRadioButton getCompFirst() {
        return compFirst;
    }

    public void setCompFirst(JRadioButton compFirst) {
        this.compFirst = compFirst;
    }

    public JRadioButton getRandomFirst() {
        return randomFirst;
    }

    public void setRandomFirst(JRadioButton randomFirst) {
        this.randomFirst = randomFirst;
    }
}
