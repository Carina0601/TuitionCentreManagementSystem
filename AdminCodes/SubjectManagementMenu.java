import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.util.List;

public class SubjectManagementMenu extends BaseForm {

    public SubjectManagementMenu() {
        super("Subject Management Menu");

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
        JLabel lblTitle = new JLabel("Subject Management Menu", SwingConstants.CENTER);
        lblTitle.setFont(titleFont);
        content.add(lblTitle, gbc);

        Color lightGreen = new Color(215, 255, 224, 255);


        //buttons
        gbc.gridy++;
        content.add(makeButton("(a) Create Subject", btnFont, lightGreen), gbc);
        gbc.gridy++;
        content.add(makeButton("(b) Update Subject", btnFont, lightGreen), gbc);
        gbc.gridy++;
        content.add(makeButton("(c) Delete Subject", btnFont, lightGreen), gbc);
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
                        SubjectManagementMenu.this,
                        confirm,
                        "Confirm Exit",
                        JOptionPane.OK_CANCEL_OPTION
                );

                if (res == JOptionPane.OK_OPTION){
                    JLabel exitMsg = new JLabel("Thank you for using this system!");
                    exitMsg.setFont(new Font("Times New Roman", Font.BOLD,14));
                    JOptionPane.showMessageDialog(
                            SubjectManagementMenu.this,
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
            case "(a) Create Subject" -> {
                btn.addActionListener(e -> {
                    SubjectManagementMenu.this.setVisible(false);
                    new CreateSubjectForm().addWindowListener(new java.awt.event.WindowAdapter(){
                        @Override
                        public void windowClosed(WindowEvent e) {
                            super.windowClosed(e);
                            SubjectManagementMenu.this.setVisible(true);
                        }
                    });
                });
            }
            case "(b) Update Subject" -> {
                btn.addActionListener(e -> {
                    SubjectManagementMenu.this.setVisible(false);
                    new UpdateSubjectForm().addWindowListener(new java.awt.event.WindowAdapter(){
                        @Override
                        public void windowClosed(WindowEvent e){
                            super.windowClosed(e);
                            SubjectManagementMenu.this.setVisible(true);
                        }
                    });
                });
            }
            case "(c) Delete Subject" -> {
                btn.addActionListener(e -> {
                    SubjectManagementMenu.this.setVisible(false);
                    new DeleteSubjectForm().addWindowListener(new java.awt.event.WindowAdapter(){
                        @Override
                        public void windowClosed(WindowEvent e){
                            super.windowClosed(e);
                            SubjectManagementMenu.this.setVisible(true);
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
        SwingUtilities.invokeLater(SubjectManagementMenu::new);
    }

}

