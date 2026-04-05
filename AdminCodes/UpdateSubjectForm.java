import javax.swing.*;
import java.awt.*;
import java.util.List;

public class UpdateSubjectForm extends BaseForm {

    private final JComboBox<Subject> cbSubject;         // dropdown
    private final JTextField tfTutor, tfFees, tfSchedule, tfNotes;
    private Subject current;                            // subject being edited

    public UpdateSubjectForm() {
        super("Update Subject");

        //layout
        JPanel content = new JPanel(new GridBagLayout());
        content.setBackground(Color.WHITE);
        content.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        add(new JScrollPane(content), BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 0, 5, 0);
        gbc.gridx = 0;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font headerF = new Font("Times New Roman", Font.BOLD, 16);
        Font labelF  = new Font("Times New Roman", Font.BOLD, 14);
        Font fieldF  = new Font("Times New Roman", Font.PLAIN, 14);

        //header
        gbc.gridy = 0;
        JLabel header = new JLabel("Update Subject", SwingConstants.CENTER);
        header.setFont(headerF);
        content.add(header, gbc);

        //selected subject
        gbc.gridy++;
        JLabel lblSel = new JLabel("Choose Subject:", SwingConstants.LEFT);
        lblSel.setFont(labelF);
        content.add(lblSel, gbc);

        gbc.gridy++;

        //build combo model
        List<Subject> all = Main.subjectList;
        DefaultComboBoxModel<Subject> model = new DefaultComboBoxModel<>(all.toArray(new Subject[0]));
        cbSubject = new JComboBox<>(model);
        cbSubject.setFont(fieldF);

        //show “ID - Title”
        cbSubject.setRenderer((list, value, index, isSel, cellHasFocus) -> {
            JLabel l = new JLabel();
            if (value != null) {
                l.setText(value.getID() + " - " + value.getTitle());
            }
            if (isSel) {
                l.setBackground(list.getSelectionBackground());
                l.setForeground(list.getSelectionForeground());
                l.setOpaque(true);
            }
            l.setFont(fieldF);
            return l;
        });

        content.add(cbSubject, gbc);

        //edit field
        tfTutor    = addField(content, "Tutor ID:",    ++gbc.gridy, labelF, fieldF, gbc);
        tfTutor.setEditable(false);
        tfFees     = addField(content, "Fees (RM):",   ++gbc.gridy, labelF, fieldF, gbc);
        tfSchedule = addField(content, "Schedule:",    ++gbc.gridy, labelF, fieldF, gbc);
        tfNotes    = addField(content, "Notes Link:",  ++gbc.gridy, labelF, fieldF, gbc);

        //buttons
        gbc.gridy++;
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        btnPanel.setBackground(Color.WHITE);

        JButton btnUpdate = new JButton("Update");
        JButton btnBack   = new JButton("Back");
        styleBtn(btnUpdate, fieldF, new Color(0,123,255));
        styleBtn(btnBack,   fieldF, new Color(108,117,125));

        btnPanel.add(btnBack);
        btnPanel.add(btnUpdate);
        content.add(btnPanel, gbc);

        //listeners
        cbSubject.addActionListener(e -> populateFields());
        btnUpdate.addActionListener(e -> updateSubject());
        btnBack.addActionListener(e -> onBack());

        // show first subject by default
        if (cbSubject.getItemCount() > 0) {
            cbSubject.setSelectedIndex(0);
            populateFields();
        } else {
            JLabel message = new JLabel("No subjects found.");
            message.setFont(new Font("Times New Roman", Font.BOLD, 14));
            JOptionPane.showMessageDialog(this, message, "Empty", JOptionPane.WARNING_MESSAGE);
            dispose();
        }

        //close window
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e){
                JLabel confirm = new JLabel("Confirm to exit the system?");
                confirm.setFont(new Font("Times New Roman", Font.BOLD,14));

                int res = JOptionPane.showConfirmDialog(
                        UpdateSubjectForm.this,
                        confirm,
                        "Confirm Exit",
                        JOptionPane.OK_CANCEL_OPTION
                );

                if (res == JOptionPane.OK_OPTION){
                    JLabel exitMsg = new JLabel("Thank you for using this system!");
                    exitMsg.setFont(new Font("Times New Roman", Font.BOLD,14));
                    JOptionPane.showMessageDialog(
                            UpdateSubjectForm.this,
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

    /* populate text fields when combo changes */
    private void populateFields() {
        current = (Subject) cbSubject.getSelectedItem();
        if (current == null) return;
        tfTutor.setText(current.getTutorID());
        tfFees.setText(String.valueOf(current.getFees()));
        tfSchedule.setText(current.getSchedule().replace("\"",""));
        tfNotes.setText(current.getNotesLink().replace("\"",""));
    }

    //update subject
    private void updateSubject() {
        if (current == null) return;

        String tutor = tfTutor.getText().trim();
        String feeS  = tfFees.getText().trim();
        String sched = tfSchedule.getText().trim();
        String notes = tfNotes.getText().trim();

        if (tutor.isEmpty() || feeS.isEmpty() || sched.isEmpty() || notes.isEmpty()) {
            JLabel message = new JLabel("Please fill all fields.");
            message.setFont(new Font("Times New Roman", Font.BOLD, 14));
            JOptionPane.showMessageDialog(this, message, "Missing", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!feeS.matches("\\d+(\\.\\d{1,2})?")) {
            JLabel message = new JLabel("Fees must be numeric.");
            message.setFont(new Font("Times New Roman", Font.BOLD, 14));
            JOptionPane.showMessageDialog(this, message, "Invalid", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!CommonMethods.isValidQuotedInput(sched) || !CommonMethods.isValidQuotedInput(notes)) return;

        current.setTutorID(tutor);
        current.setFees(Double.parseDouble(feeS));
        current.setSchedule(CommonMethods.modifyInput(sched));
        current.setNotesLink(CommonMethods.modifyInput(notes));

        /* persist */
        List<Subject> list = Main.subjectList;
        for (int i = 0; i < list.size(); i++)
            if (list.get(i).getID().equals(current.getID())) { list.set(i, current); break; }
        SubjectManagement.saveSubjects(list);

        JLabel message = new JLabel("Subject updated.");
        message.setFont(new Font("Times New Roman", Font.BOLD, 14));
        JOptionPane.showMessageDialog(this, message);
        dispose();
    }

    //label + text field
    private JTextField addField(JPanel p, String label, int y,
                                Font lab, Font fld, GridBagConstraints gbc) {
        gbc.gridy = y;
        JLabel l = new JLabel(label); l.setFont(lab);
        p.add(l, gbc);

        gbc.gridy++;
        JTextField tf = new JTextField(); tf.setFont(fld);
        p.add(tf, gbc);
        return tf;
    }

    //style button
    private void styleBtn(JButton btn, Font font, Color color) {
        btn.setFont(font);
        btn.setForeground(Color.WHITE);
        btn.setBackground(color);
        btn.setBorder(BorderFactory.createEmptyBorder(10,20,10,20));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setFocusPainted(false);
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e){
                btn.setBackground(color.darker());
            }
            @Override
            public void mouseExited (java.awt.event.MouseEvent e){
                btn.setBackground(color);
            }
        });
    }

    @Override protected void onBack() {
        dispose();
    }

    /* Optional standalone test */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(UpdateSubjectForm::new);
    }
}
