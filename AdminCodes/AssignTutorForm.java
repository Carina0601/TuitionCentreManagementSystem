import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class AssignTutorForm extends BaseForm {
    public JComboBox<String> subjectCombo;
    public JComboBox<String> tutorCombo;
    public JButton assignButton;

    public AssignTutorForm() {
        super("Assign Tutor");

        // Load data
        ArrayList<Subject> subjectList = Main.subjectList;
        ArrayList<PersonalInfo> tutorList = Main.tutorList;

        // Main panel
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

        Font tnrFont = new Font("Times New Roman", Font.PLAIN, 14);
        Font tnrBold = new Font("Times New Roman", Font.BOLD, 16);

        // Header
        gbc.gridy = 0;
        JLabel header = new JLabel("Assign Tutor to Subject");
        header.setFont(tnrBold);
        content.add(header, gbc);

        // Subject title
        gbc.gridy++;
        content.add(createLabel("Subject Title:", tnrBold), gbc);
        gbc.gridy++;
        subjectCombo = new JComboBox<>();
        subjectCombo.setFont(tnrFont);
        subjectCombo.addItem("--- Select Subject ---");
        TreeSet<String> titles = new TreeSet<>();
        for (Subject s : subjectList) titles.add(s.getTitle());
        for (String title : titles) subjectCombo.addItem(title);
        content.add(subjectCombo, gbc);

        // Tutor
        gbc.gridy++;
        content.add(createLabel("Tutor:", tnrBold), gbc);
        gbc.gridy++;
        tutorCombo = new JComboBox<>();
        tutorCombo.setFont(tnrFont);
        tutorCombo.addItem("--- Select Tutor ---");
        for (PersonalInfo tutor : tutorList) {
            tutorCombo.addItem(tutor.getID() + " - " + tutor.getAttributes()[1]);
        }
        content.add(tutorCombo, gbc);

        // Buttons
        gbc.gridy++;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(Color.WHITE);

        assignButton = new JButton("Assign Tutor");
        styleButton(assignButton, tnrFont, new Color(0, 123, 255));
        styleButton(btnBack, tnrFont, new Color(108, 117, 125));

        buttonPanel.add(btnBack);
        buttonPanel.add(assignButton);
        content.add(buttonPanel, gbc);

        // Listeners
        assignButton.addActionListener(e -> assign(subjectCombo, tutorCombo));
        btnBack.addActionListener(e -> {
            dispose();
        });

        //close window
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e){
                JLabel confirm = new JLabel("Confirm to exit the system?");
                confirm.setFont(new Font("Times New Roman", Font.BOLD,14));

                int res = JOptionPane.showConfirmDialog(
                        AssignTutorForm.this,
                        confirm,
                        "Confirm Exit",
                        JOptionPane.OK_CANCEL_OPTION
                );

                if (res == JOptionPane.OK_OPTION){
                    JLabel exitMsg = new JLabel("Thank you for using this system!");
                    exitMsg.setFont(new Font("Times New Roman", Font.BOLD,14));
                    JOptionPane.showMessageDialog(
                            AssignTutorForm.this,
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

    private JLabel createLabel(String text, Font font) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        return label;
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

    private void assign(JComboBox<String> subjectCombo, JComboBox<String> tutorCombo) {
        try {
            String selectedSubjectTitle = (String) subjectCombo.getSelectedItem();
            String selectedTutor = (String) tutorCombo.getSelectedItem();

            if (selectedSubjectTitle == null || selectedTutor == null ||
                    selectedSubjectTitle.startsWith("---") || selectedTutor.startsWith("---")) {

                JLabel msg = new JLabel("Please select both subject and tutor.");
                msg.setFont(new Font("Times New Roman", Font.BOLD, 14));
                JOptionPane.showMessageDialog(this, msg,
                        "Missing Fields", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String tutorID = selectedTutor.split(" - ")[0];

            // Load current subjects
            ArrayList<Subject> subjectList = Main.subjectList;

            Subject target = null;
            for (Subject s : subjectList){
                if (s.getTitle().equalsIgnoreCase(selectedSubjectTitle)){
                    target = s;
                    break;
                }
            }

            if (target == null){
                JLabel msg = new JLabel("Selected subject not found in subject.txt.");
                msg.setFont(new Font("Times New Roman",Font.BOLD, 14));
                JOptionPane.showMessageDialog(
                        this,
                        msg,
                        "Error", JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            String currentTutorID = target.getTutorID();
            boolean unassigned =
                    currentTutorID == null || currentTutorID.isBlank() || currentTutorID.equals("$") || currentTutorID.equalsIgnoreCase("None");

            if (!unassigned){
                if (currentTutorID.equalsIgnoreCase(tutorID)){
                    //same tutor already assigned
                    JLabel msg = new JLabel("This tutor is already assigned to this subject.");
                    msg.setFont(new Font("Times New Roman", Font.BOLD, 14));

                    JOptionPane.showMessageDialog(
                            this,
                            msg,
                            "Duplicate Assignment",
                            JOptionPane.WARNING_MESSAGE
                    );

                    return;
                } else {
                   //ask for confirmation b4 overwriting
                    JLabel msg = new JLabel("Subject \"" + selectedSubjectTitle + "\" is currently assigned to tutor " + currentTutorID + ". Replace with " + tutorID + "?");
                    msg.setFont(new Font("Times New Roman", Font.BOLD, 14));
                   int choice = JOptionPane.showOptionDialog(this,
                           msg,
                           "Tutor Already Assigned",
                           JOptionPane.YES_NO_OPTION,
                           JOptionPane.QUESTION_MESSAGE,
                           null,null,null
                   );
                    if (choice != JOptionPane.YES_OPTION) {
                        return;   // user chose “No” – abort
                    }
                }
            }

            // Either unassigned, or user confirmed replacement → proceed
            target.setTutorID(tutorID);
            TutorManagement.saveSubjects(subjectList);

            JLabel msg = new JLabel("Assignment successful. Subject: "+ selectedSubjectTitle
                    + " - Tutor: " + tutorID);
            msg.setFont(new Font("Times New Roman", Font.BOLD, 14));
            JOptionPane.showMessageDialog(
                    this,
                     msg,
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch (Exception ex) {
            JLabel errorMsg = new JLabel("Error during assignment: " + ex.getMessage());
            errorMsg.setFont(new Font("Times New Roman", Font.BOLD, 14));

            JOptionPane.showMessageDialog(
                    this,
                    errorMsg,
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

        }

    }


    @Override
    protected void onBack() {
        dispose();
    }
}
