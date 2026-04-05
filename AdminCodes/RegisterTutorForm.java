import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class RegisterTutorForm extends BaseForm {
    private JTextField tfId, tfName, tfIc, tfEmail, tfPhone, tfAddress;
    private JButton btnRegister;

    public RegisterTutorForm() {
        super("Register Tutor");

        JPanel content = new JPanel(new GridBagLayout());
        content.setBackground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(content);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 30, 8, 30);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.weightx = 1;

        Font font = new Font("Times New Roman", Font.PLAIN, 14);
        Font bold = new Font("Times New Roman", Font.BOLD, 16);

        // Header
        gbc.gridy = 0;
        JLabel header = new JLabel("Register New Tutor");
        header.setFont(bold);
        content.add(header, gbc);

        ArrayList<PersonalInfo> tutors = Main.tutorList;
        String nextID = CommonMethods.getNextID("T", tutors);

        // Fields
        tfId = createField(content, "Tutor ID:", ++gbc.gridy, font, bold, gbc);
        tfId.setText(nextID);
        tfId.setEditable(false);  //ID should not be manually changed

        tfName = createField(content, "Name:", ++gbc.gridy, font,bold, gbc);
        tfIc = createField(content, "IC Number:", ++gbc.gridy, font, bold, gbc);
        tfEmail = createField(content, "Email:", ++gbc.gridy, font, bold, gbc);
        tfPhone = createField(content, "Phone No:", ++gbc.gridy, font, bold, gbc);
        tfAddress = createField(content, "Address:", ++gbc.gridy, font, bold, gbc);

        // Buttons
        gbc.gridy++;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(Color.WHITE);

        btnRegister = new JButton("Register Tutor");
        styleButton(btnRegister, font, new Color(0, 123, 255));
        styleButton(btnBack, font, new Color(108, 117, 125));

        buttonPanel.add(btnBack);
        buttonPanel.add(btnRegister);
        content.add(buttonPanel, gbc);

        // Action listeners
        btnRegister.addActionListener(e -> registerTutor());
        btnBack.addActionListener(e -> {
            onBack();
        });

        //close window
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e){
                JLabel confirm = new JLabel("Confirm to exit the system?");
                confirm.setFont(new Font("Times New Roman", Font.BOLD,14));

                int res = JOptionPane.showConfirmDialog(
                        RegisterTutorForm.this,
                        confirm,
                        "Confirm Exit",
                        JOptionPane.OK_CANCEL_OPTION
                );

                if (res == JOptionPane.OK_OPTION){
                    JLabel exitMsg = new JLabel("Thank you for using this system!");
                    exitMsg.setFont(new Font("Times New Roman", Font.BOLD,14));
                    JOptionPane.showMessageDialog(
                            RegisterTutorForm.this,
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

    private JTextField createField(JPanel panel, String label, int y, Font font, Font labelFont, GridBagConstraints gbc) {
        gbc.gridy = y;
        JLabel lbl = new JLabel(label);
        lbl.setFont(labelFont);
        panel.add(lbl, gbc);

        gbc.gridy++;
        JTextField tf = new JTextField();
        tf.setFont(font);
        panel.add(tf, gbc);
        return tf;
    }

    private void styleButton(JButton button, Font font, Color color) {
        button.setFont(font);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(color.darker());
            }

            public void mouseExited(MouseEvent e) {
                button.setBackground(color);
            }
        });
    }

    private void registerTutor() {
        String id = tfId.getText().trim();
        String name = tfName.getText().trim();
        String ic = tfIc.getText().trim();
        String email = tfEmail.getText().trim();
        String phone = tfPhone.getText().trim();
        String address = tfAddress.getText().trim();

        if (id.isEmpty() || name.isEmpty() || ic.isEmpty() || email.isEmpty() || phone.isEmpty() || address.isEmpty()) {
            JLabel message = new JLabel("Please fill in all fields.");
            message.setFont(new Font("Times New Roman", Font.BOLD, 14));
            JOptionPane.showMessageDialog(this, message, "Missing data", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String newAddress;
        if (!CommonMethods.isValidQuotedInput(address)){
            return;
        } else {
            newAddress = CommonMethods.modifyInput(address);

        }

        //Validate Name
        if (!CommonMethods.isValidName(name)){
            return;
        }

        // Validate IC format
        if (!CommonMethods.isValidIC(ic)) {
            return;
        }

        //Validate Phone Number
        if (!CommonMethods.isValidPhoneNo(phone)){
            return;
        }

        // Load tutors
        ArrayList<PersonalInfo> tutors = Main.tutorList;

        // Check if IC already exists
        for (PersonalInfo t : tutors) {
            if (t.getIC().equals(ic) || t.getID().equalsIgnoreCase(id)) {
                JLabel message = new JLabel("Tutor already registered with this IC or ID.");
                message.setFont(new Font("Times New Roman", Font.BOLD, 14));
                JOptionPane.showMessageDialog(this,
                        message,
                        "Duplicate IC", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }


        String username = "@";
        String password = "#";
        PersonalInfo tutor = new PersonalInfo(id, name, ic, email, phone, newAddress, username, password);

        tutors.add(tutor);
        TutorManagement.saveTutors(tutors);

        JLabel message = new JLabel("Tutor registered successfully!");
        message.setFont(new Font("Times New Roman", Font.BOLD, 14));
        JOptionPane.showMessageDialog(this,
                message,
                "Success", JOptionPane.INFORMATION_MESSAGE);
        clearFields();
    }


    private void clearFields() {
        ArrayList<PersonalInfo> tutors = Main.tutorList;
        String nextID = CommonMethods.getNextID("T", tutors);
        tfId.setText(nextID);
        tfName.setText("");
        tfIc.setText("");
        tfEmail.setText("");
        tfPhone.setText("");
        tfAddress.setText("");
    }

    @Override
    protected void onBack() {
    }
}
