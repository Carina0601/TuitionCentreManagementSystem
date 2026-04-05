import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class RegisterRecepForm extends BaseForm{

    private JTextField tfId, tfName, tfIc, tfEmail, tfPhone, tfAddress;
    private JButton btnRegister, btnBack;

    public RegisterRecepForm() {
        super("Register Receptionist");

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

        //Header
        gbc.gridy = 0;
        JLabel header = new JLabel("Register New Receptionist");
        header.setFont(bold);
        content.add(header, gbc);

        ArrayList<PersonalInfo> receptionists = Main.recepList;
        String nextID = CommonMethods.getNextID("R", receptionists);

        //Fields
        tfId = createField(content, "Receptionist ID:", ++gbc.gridy, font, bold, gbc);
        tfId.setText(nextID);
        tfId.setEditable(false);

        tfName = createField(content, "Name:", ++gbc.gridy, font, bold, gbc);
        tfIc = createField(content, "IC Number:", ++gbc.gridy, font, bold, gbc);
        tfEmail = createField(content, "Email:", ++gbc.gridy, font, bold, gbc);
        tfPhone = createField(content, "Phone No:", ++gbc.gridy, font, bold, gbc);
        tfAddress = createField(content, "Address:", ++gbc.gridy, font, bold, gbc);

        //Buttons
        gbc.gridy++;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(Color.WHITE);

        btnRegister = new JButton("Register Receptionist");
        btnBack = new JButton("Back");
        styleButton(btnRegister, font, new Color(0, 123, 255));
        styleButton(btnBack, font, new Color(108, 117, 125));

        buttonPanel.add(btnBack);
        buttonPanel.add(btnRegister);
        content.add(buttonPanel, gbc);

        //Action Listener
        btnRegister.addActionListener(e -> registerRecep());
        btnBack.addActionListener(e -> {
            dispose();

        });

        setVisible(true);

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                JLabel confirm = new JLabel("Confirm to exit the system?");
                confirm.setFont(new Font("Times New Roman", Font.BOLD, 14));

                int res = JOptionPane.showConfirmDialog(
                        RegisterRecepForm.this,
                        confirm,
                        "Confirm Exit",
                        JOptionPane.OK_CANCEL_OPTION
                );

                if (res == JOptionPane.OK_OPTION) {
                    JLabel exitMsg = new JLabel("Thank you for using this system!");
                    exitMsg.setFont(new Font("Times New Roman", Font.BOLD, 14));
                    JOptionPane.showMessageDialog(
                            RegisterRecepForm.this,
                            exitMsg,
                            "Exit",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    System.exit(0);
                }
            }
        });
    }

    private JTextField createField(JPanel panel, String label, int y, Font font, Font labelFont, GridBagConstraints gbc){
        gbc.gridy = y;
        JLabel Label = new JLabel(label);
        Label.setFont(labelFont);
        panel.add(Label, gbc);

        gbc.gridy++;
        JTextField tf = new JTextField();
        tf.setFont(font);
        panel.add(tf, gbc);
        return tf;
    }


    private void styleButton(JButton button, Font font, Color color){
        button.setFont(font);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(10,20,10,20));
        button.setFocusPainted(false);
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(color.darker());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(color);
            }

        });
    }

    private void registerRecep() {
        ArrayList<PersonalInfo> receptionists = Main.recepList;
        String id = tfId.getText().trim();
        String name = tfName.getText().trim();
        String ic = tfIc.getText().trim();
        String email = tfEmail.getText().trim();
        String phone = tfPhone.getText().trim();
        String address = tfAddress.getText().trim();

        if (id.isEmpty() || name.isEmpty() || ic.isEmpty() || email.isEmpty() || phone.isEmpty()
                || address.isEmpty()) {
            JLabel message = new JLabel("Please fill in all fields.");
            message.setFont(new Font("Times New Roman", Font.BOLD, 14));
            JOptionPane.showMessageDialog(this, message, "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        //Validate address
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

        //validate IC format
        if (!CommonMethods.isValidIC(ic)){
            return;
        }

        //Validate Phone Number
        if (!CommonMethods.isValidPhoneNo(phone)){
            return;
        }


        for (PersonalInfo recep : receptionists) {
            if (recep.getID().equalsIgnoreCase(id) || recep.getIC().equals(ic)) {
                JLabel message = new JLabel("Receptionist already registered with same ID or IC");
                message.setFont(new Font("Times New Roman", Font.BOLD, 14));
                JOptionPane.showMessageDialog(this,
                        message,
                        "Duplicate IC", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }

        String username = "@";
        String password = "#";
        PersonalInfo recep = new PersonalInfo(id, name, ic, email, phone, newAddress, username, password);

        receptionists.add(recep);
        RecepManagement.saveRecep(receptionists);

        JLabel message = new JLabel("Receptionist registered successfully.");
        message.setFont(new Font("Times New Roman", Font.BOLD, 14));
        JOptionPane.showMessageDialog(this,
                message,
                "Success", JOptionPane.INFORMATION_MESSAGE);
        clearFields();
    }

    private void clearFields(){
        ArrayList<PersonalInfo> receptionists = Main.recepList;
        String nextID = CommonMethods.getNextID("R", receptionists);
        tfId.setText(nextID);
        tfName.setText("");
        tfIc.setText("");
        tfEmail.setText("");
        tfPhone.setText("");
        tfAddress.setText("");
    }

    @Override
    protected void onBack(){
        dispose();
    }
}
