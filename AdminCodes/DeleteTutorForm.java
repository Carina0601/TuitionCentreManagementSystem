import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class DeleteTutorForm extends BaseForm {

    private JTable tutorTable;
    private DefaultTableModel tableModel;
    private JButton btnDelete;

    public DeleteTutorForm() {
        super("Delete Tutor");

        JPanel content = new JPanel(new GridBagLayout());
        content.setBackground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(content);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 30,8,30);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.weightx = 1;

        Font font = new Font("Times New Roman", Font.PLAIN,14);
        Font bold = new Font("Times New Roman", Font.BOLD,16);
        Font buttonBold = new Font("Times New Roman", Font.BOLD,14);

        //Header
        gbc.gridy = 0;
        JLabel header = new JLabel("Delete Tutor");
        header.setFont(bold);
        content.add(header, gbc);

        //Table
        gbc.gridy++;
        tableModel = new DefaultTableModel(new String[]{"Tutor ID", "Name"}, 0);
        tutorTable = new JTable(tableModel);
        JScrollPane tableScroll = new JScrollPane(tutorTable);
        tableScroll.setPreferredSize(new Dimension(400,200));
        tutorTable.setFont(font);
        tutorTable.getTableHeader().setFont(bold);
        content.add(tableScroll, gbc);

        //Button panel
        gbc.gridy++;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20,10));
        buttonPanel.setBackground(Color.WHITE);

        btnDelete = new JButton("Delete Selected Tutor");
        styleButton(btnDelete, font, new Color(220, 53, 69));
        styleButton(btnBack, font, new Color(108,117,125));

        buttonPanel.add(btnBack);
        buttonPanel.add(btnDelete);
        content.add(buttonPanel, gbc);

        //Load tutors
        loadTutorsToTable();

        //Action Listener
        btnDelete.addActionListener(e -> deleteSelectedTutor());
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
                        DeleteTutorForm.this,
                        confirm,
                        "Confirm Exit",
                        JOptionPane.OK_CANCEL_OPTION
                );

                if (res == JOptionPane.OK_OPTION){
                    JLabel exitMsg = new JLabel("Thank you for using this system!");
                    exitMsg.setFont(new Font("Times New Roman", Font.BOLD,14));
                    JOptionPane.showMessageDialog(
                            DeleteTutorForm.this,
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

    private void styleButton(JButton button, Font font, Color color){
        button.setFont(font);
        button.setBackground(color);
        button.setBorder(BorderFactory.createEmptyBorder(10,20,10,20));
        button.setForeground(Color.WHITE);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setFocusPainted(false);
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(color.darker());
            }
            public void mouseExited(MouseEvent e){
                button.setBackground(color.darker());
            }
        });
    }

    private void loadTutorsToTable() {
        List<PersonalInfo> tutors = Main.tutorList;
        tableModel.setRowCount(0);  // Clear table

        for (PersonalInfo tutor : tutors) {
            tableModel.addRow(new String[]{tutor.getID(), tutor.getAttributes()[1]});
        }
    }

    private void deleteSelectedTutor() {
        int selectedRow = tutorTable.getSelectedRow();
        if (selectedRow == -1) {
            JLabel msg = new JLabel("Please select a tutor to delete.");
            msg.setFont(new Font("Times New Roman", Font.BOLD, 14));

            JOptionPane.showMessageDialog(
                    this,
                    msg,
                    "Warning",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        String tutorIdToDelete = (String) tableModel.getValueAt(selectedRow, 0);
        String tutorNameToDelete = (String) tableModel.getValueAt(selectedRow,1);

        String[] options = {
                "Remove from specific subject",
                "Remove from all subjects + delete tutor",
                "Cancel"
        };

        JLabel msg = new JLabel("Choose what to do with tutor: " + tutorNameToDelete);
        msg.setFont(new Font("Times New Roman", Font.BOLD, 14));

        int choice = JOptionPane.showOptionDialog(
                this,
                msg,
                "Delete Options",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );


        switch (choice){
            case 0 -> removeFromSpecificSubject(tutorIdToDelete);
            case 1 -> removeFromAllSubjectsAndDeleteTutor(tutorIdToDelete);
            default -> {} //cancel or close
        }
    }

    private void removeFromSpecificSubject(String tutorId) {
        List<Subject> subjects = Main.subjectList;
        List<String> taughtSubjects = new ArrayList<>();

        for (Subject subject : subjects) {
            if (subject.getTutorID().equals(tutorId)) {
                taughtSubjects.add(subject.getID()+ " - " + subject.getTitle());
            }
        }

        if (taughtSubjects.isEmpty()) {
            JLabel msg = new JLabel("This tutor is not assigned to any subject.");
            msg.setFont(new Font("Times New Roman", Font.BOLD, 14));

            JOptionPane.showMessageDialog(this, msg, "Info", JOptionPane.INFORMATION_MESSAGE);

            return;
        }

        JLabel msg = new JLabel("Select a subject to unassign the tutor:");
        msg.setFont(new Font("Times New Roman", Font.BOLD, 14));

        String selected = (String) JOptionPane.showInputDialog(
                this,
                msg, // use JLabel instead of plain String
                "Subject List",
                JOptionPane.PLAIN_MESSAGE,
                null,
                taughtSubjects.toArray(),
                taughtSubjects.getFirst()
        );


        if (selected != null) {
            String selectedSubjectID = selected.split(" - ")[0];
            for (Subject subject : subjects) {
                if (subject.getID().equals(selectedSubjectID) && subject.getTutorID().equals(tutorId)) {
                    subject.setTutorID("None");
                    break;
                }
            }
            TutorManagement.saveSubjects(subjects);
            loadTutorsToTable();
            JLabel msg1 = new JLabel("Tutor unassigned from subject " + selected);
            msg1.setFont(new Font("Times New Roman", Font.BOLD, 14));

            JOptionPane.showMessageDialog(this, msg1, "Unassigned", JOptionPane.INFORMATION_MESSAGE);

        }
    }

    private void removeFromAllSubjectsAndDeleteTutor(String tutorId) {
        // Remove from subjects
        List<Subject> subjects = Main.subjectList;
        for (Subject s : subjects) {
            if (s.getTutorID().equals(tutorId)) {
                s.setTutorID("None");
            }
        }
        TutorManagement.saveSubjects(subjects);

        // Remove from tutor list
        List<PersonalInfo> tutors = Main.tutorList;
        tutors.removeIf(t -> t.getID().equals(tutorId));
        TutorManagement.saveTutors(tutors);

        // Refresh UI
        loadTutorsToTable();
        JLabel msg = new JLabel("Tutor removed from all subjects and deleted from system.");
        msg.setFont(new Font("Times New Roman", Font.BOLD, 14));

        JOptionPane.showMessageDialog(this, msg, "Success", JOptionPane.INFORMATION_MESSAGE);

    }

    @Override
    protected void onBack(){
        dispose();
    }
}

