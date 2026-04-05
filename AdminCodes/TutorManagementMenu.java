import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;

public class TutorManagementMenu extends BaseForm{

    public TutorManagementMenu() {
        super("Tutor Management Menu");

        JPanel content = new JPanel(new GridBagLayout());
        content.setBackground(Color.WHITE);
        content.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        add(content, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 0, 5, 0);
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        Font titleFont = new Font("Times New Roman", Font.BOLD, 16);
        Font btnFont = new Font("Times New Roman", Font.BOLD, 14);

        //title label
        gbc.gridy =0;
        JLabel lblTitle = new JLabel("Tutor Management Menu", SwingConstants.CENTER);
        lblTitle.setFont(titleFont);
        content.add(lblTitle, gbc);

        Color lightGreen = new Color(255, 205, 205, 255);


        //buttons
        gbc.gridy++;
        content.add(makeButton("(a) Register Tutor", btnFont, lightGreen), gbc);
        gbc.gridy++;
        content.add(makeButton("(b) View Tutor", btnFont, lightGreen), gbc);
        gbc.gridy++;
        content.add(makeButton("(c) Delete Tutor", btnFont, lightGreen), gbc);
        gbc.gridy++;
        content.add(makeButton("(d) Assign Tutor", btnFont, lightGreen), gbc);
        gbc.gridy++;
        content.add(makeButton("Back", btnFont, lightGreen), gbc);

        //close window
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e){
                JLabel confirm = new JLabel("Confirm to exit the system?");
                confirm.setFont(new Font("Times New Roman", Font.BOLD,14));

                int res = JOptionPane.showConfirmDialog(
                        TutorManagementMenu.this,
                        confirm,
                        "Confirm Exit",
                        JOptionPane.OK_CANCEL_OPTION
                );

                if (res == JOptionPane.OK_OPTION){
                    JLabel exitMsg = new JLabel("Thank you for using this system!");
                    exitMsg.setFont(new Font("Times New Roman", Font.BOLD,14));
                    JOptionPane.showMessageDialog(
                            TutorManagementMenu.this,
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
            case "(a) Register Tutor" -> {
                btn.addActionListener(e -> {
                    TutorManagementMenu.this.setVisible(false);
                    new RegisterTutorForm().addWindowListener(new java.awt.event.WindowAdapter() {
                        @Override
                        public void windowClosed(WindowEvent e) {
                            super.windowClosed(e);
                            TutorManagementMenu.this.setVisible(true);
                        }
                    });
                });
            }
            case "(b) View Tutor" -> {
                btn.addActionListener(e -> {
                    TutorManagementMenu.this.setVisible(false);
                    new ViewTutorForm().addWindowListener(new java.awt.event.WindowAdapter() {
                        @Override
                        public void windowClosed(WindowEvent e){
                            super.windowClosed(e);
                            TutorManagementMenu.this.setVisible(true);
                        }
                    });
                });
            }
            case "(c) Delete Tutor" -> {
                btn.addActionListener(e -> {
                    TutorManagementMenu.this.setVisible(false);
                    new DeleteTutorForm().addWindowListener(new java.awt.event.WindowAdapter() {
                        @Override
                        public void windowClosed(WindowEvent e){
                            super.windowClosed(e);
                            TutorManagementMenu.this.setVisible(true);
                        }
                    });
                });
            }
            case "(d) Assign Tutor" -> {
                btn.addActionListener(e -> {
                    TutorManagementMenu.this.setVisible(false);
                    new AssignTutorForm().addWindowListener(new java.awt.event.WindowAdapter() {
                        @Override
                        public void windowClosed(WindowEvent e){
                            super.windowClosed(e);
                            TutorManagementMenu.this.setVisible(true);
                        }
                    });
                });
            }
            case "Back" -> btn.addActionListener(e -> onBack());
        }
        return btn;

    }

    @Override
    protected void onBack(){
        dispose();

    }
    //test
    public static void main(String[] args){
        SwingUtilities.invokeLater(TutorManagementMenu::new);
    }

}
