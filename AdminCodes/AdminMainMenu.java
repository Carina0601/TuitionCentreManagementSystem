import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;

public class AdminMainMenu extends BaseForm {

    public AdminMainMenu() {
        //frame
        super("Admin Main Menu");

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
        JLabel lblTitle = new JLabel("Admin Main Menu", SwingConstants.CENTER);
        lblTitle.setFont(titleFont);
        content.add(lblTitle, gbc);

        Color lightBlue = new Color(188, 226, 255);



        //buttons
        gbc.gridy++;
        content.add(makeButton("1. Admin Profile", btnFont, lightBlue), gbc);
        gbc.gridy++;
        content.add(makeButton("2. Tutor Management", btnFont, lightBlue), gbc);
        gbc.gridy++;
        content.add(makeButton("3. Receptionist Management", btnFont, lightBlue), gbc);
        gbc.gridy++;
        content.add(makeButton("4. Monthly Income Report", btnFont, lightBlue), gbc);
        gbc.gridy++;
        content.add(makeButton("5. Subject Management", btnFont, lightBlue), gbc);
        gbc.gridy++;
        content.add(makeButton("6. Log Out", btnFont, lightBlue), gbc);

        //close window
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e){
                JLabel confirm = new JLabel("Confirm to exit the system?");
                confirm.setFont(new Font("Times New Roman", Font.BOLD,14));

                int res = JOptionPane.showConfirmDialog(
                        AdminMainMenu.this,
                        confirm,
                        "Confirm Exit",
                        JOptionPane.OK_CANCEL_OPTION
                );

                if (res == JOptionPane.OK_OPTION){
                    JLabel exitMsg = new JLabel("Thank you for using this system!");
                    exitMsg.setFont(new Font("Times New Roman", Font.BOLD,14));
                    JOptionPane.showMessageDialog(
                            AdminMainMenu.this,
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
            public void mouseExited (MouseEvent e) {
                btn.setBackground(baseColor);
            }
        });

        //actions button
        switch (text) {
            case "1. Admin Profile" ->
                    btn.addActionListener(e -> {
                        AdminMainMenu.this.setVisible(false);

                        String adminID = AdminMethods.getAdminID();

                        CommonMethods.userProfile(
                                AdminMainMenu.this,
                                "txtTCMS/admin.txt",
                                adminID,
                                Main.adminList,
                                Main.adminList
                        );

                    });

            case "2. Tutor Management" -> {
                btn.addActionListener(e -> {
                    AdminMainMenu.this.setVisible(false);   // ← hide this frame
                    new TutorManagementMenu().addWindowListener(new java.awt.event.WindowAdapter() {
                        @Override public void windowClosed(WindowEvent ev) {
                            AdminMainMenu.this.setVisible(true);        // show again when child closes
                        }
                    });
                });
            }

            case "3. Receptionist Management" -> {
                    btn.addActionListener(e -> {
                        AdminMainMenu.this.setVisible(false);
                        new RecepManagementMenu().addWindowListener(new java.awt.event.WindowAdapter() {
                            @Override
                            public void windowClosed(WindowEvent e) {
                                super.windowClosed(e);
                                AdminMainMenu.this.setVisible(true);
                            }
                        });
                    });
            }
            case "4. Monthly Income Report" -> {
                    btn.addActionListener(e -> {
                        AdminMainMenu.this.setVisible(false);
                        new IncomeReportForm().addWindowStateListener(new java.awt.event.WindowAdapter() {
                            @Override
                            public void windowClosed(WindowEvent e) {
                                super.windowClosed(e);
                                AdminMainMenu.this.setVisible(true);
                            }
                        });
                    });
            }
            case "5. Subject Management" -> {
                    btn.addActionListener(e -> {
                        AdminMainMenu.this.setVisible(false);
                        new SubjectManagementMenu().addWindowListener(new java.awt.event.WindowAdapter() {
                            @Override
                            public void windowClosed(WindowEvent e) {
                                super.windowClosed(e);
                                AdminMainMenu.this.setVisible(true);
                            }
                        });
                    });
            }
            case "6. Log Out" -> {
                    btn.addActionListener(e -> {
                        UIManager.put("OptionPane.messageFont", new Font("Times New Roman", Font.BOLD, 14));
                        int result = JOptionPane.showConfirmDialog(
                                this,
                                "Confirm to log out?",
                                "Confirmation", JOptionPane.YES_NO_OPTION);


                        if (result == JOptionPane.YES_OPTION) {
                            dispose();
                            SwingUtilities.invokeLater(() -> new Main().showMainMenu());
                        }
                    });


            }
        }

        return btn;
    }
    @Override
    protected void onBack(){
        dispose();
    }

    //test
    public static void main(String[] args) {
        SwingUtilities.invokeLater(AdminMainMenu::new);
    }
}
