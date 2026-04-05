import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;

public class RecepManagementMenu extends BaseForm {

    public RecepManagementMenu() {
        super("Receptionist Management Menu");

        //layout directly on the frame
        JPanel content = new JPanel(new GridBagLayout());
        content.setBackground(Color.WHITE);
        content.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        add(content, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets   = new Insets(5, 0, 5, 0);    // spacing between rows
        gbc.gridx    = 0;
        gbc.weightx  = 1;
        gbc.fill     = GridBagConstraints.HORIZONTAL;

        Font titleFont = new Font("Times New Roman", Font.BOLD, 16);
        Font btnFont   = new Font("Times New Roman", Font.BOLD, 14);

        //title  label
        gbc.gridy = 0;
        JLabel lblTitle = new JLabel("Receptionist Management Menu", SwingConstants.CENTER);
        lblTitle.setFont(titleFont);
        content.add(lblTitle, gbc);

        Color lightPurple = new Color(228, 203, 255);



        //buttons
        gbc.gridy++;
        content.add(makeButton("(a) Register Receptionist", btnFont, lightPurple), gbc);
        gbc.gridy++;
        content.add(makeButton("(b) Delete Receptionist", btnFont, lightPurple), gbc);
        gbc.gridy++;
        content.add(makeButton("Back", btnFont, lightPurple), gbc);


        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e){
                JLabel confirm = new JLabel("Confirm to exit the system?");
                confirm.setFont(new Font("Times New Roman", Font.BOLD,14));

                int res = JOptionPane.showConfirmDialog(
                        RecepManagementMenu.this,
                        confirm,
                        "Confirm Exit",
                        JOptionPane.OK_CANCEL_OPTION
                );

                if (res == JOptionPane.OK_OPTION){
                    JLabel exitMsg = new JLabel("Thank you for using this system!");
                    exitMsg.setFont(new Font("Times New Roman", Font.BOLD,14));
                    JOptionPane.showMessageDialog(
                            RecepManagementMenu.this,
                            exitMsg,
                            "Exit",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    System.exit(0);
                }
            }
        });

        setVisible(true);
    }

    //style button with hover effect
    private JButton makeButton(String text, Font font, Color baseColor) {
        JButton btn = new JButton(text);
        btn.setFont(font);
        btn.setForeground(Color.BLACK);
        btn.setBackground(baseColor);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(12, 25, 12, 25));
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(baseColor.darker());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(baseColor);
            }
        });

        //actions button
        switch (text) {
            case "(a) Register Receptionist" -> {
                btn.addActionListener(e -> {
                    RecepManagementMenu.this.setVisible(false);
                    new RegisterRecepForm().addWindowListener(new java.awt.event.WindowAdapter(){
                        @Override
                        public void windowClosed(WindowEvent e) {
                            super.windowClosed(e);
                            RecepManagementMenu.this.setVisible(true);
                        }
                    });
                });
            }
            case "(b) Delete Receptionist" -> {
                btn.addActionListener(e -> {
                    RecepManagementMenu.this.setVisible(false);
                    new DeleteRecepForm().addWindowListener(new java.awt.event.WindowAdapter() {
                        @Override
                        public void windowClosed(WindowEvent e) {
                            super.windowClosed(e);
                            RecepManagementMenu.this.setVisible(true);
                        }
                    });
                });
            }
            case "Back" -> btn.addActionListener(e -> onBack());
        }

        return btn;
    }

    @Override
    protected void onBack() {
        dispose();

    }
    //test

    public static void main(String[] args){
        SwingUtilities.invokeLater(RecepManagementMenu::new);
    }
}