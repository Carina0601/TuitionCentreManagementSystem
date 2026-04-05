import javax.swing.*;
import javax.swing.plaf.basic.BasicCheckBoxMenuItemUI;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class CreateSubjectForm extends BaseForm{

    private final JTextField tfID, tfTitle, tfFees, tfSchedule, tfNotes;
    public CreateSubjectForm(){
        super("Create New Subject");

        JPanel content = new JPanel(new GridBagLayout());
        content.setBackground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(content);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6,20,6,20);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font titleFont = new Font("Times New Roman", Font.BOLD, 16);
        Font bold =  new Font("Times New Roman", Font.BOLD,14);

        //Header
        gbc.gridy = 0;
        JLabel header = new JLabel("Create New Subject");
        header.setFont(titleFont);
        content.add(header,gbc);

        ArrayList<Subject> subjects = Main.subjectList;
        String nextID = CommonMethods.getNextID("SJ", subjects);

        //Fields
        tfID = createField(content, "Subject ID * ", ++gbc.gridy, bold, gbc );
        tfID.setText(nextID);
        tfID.setEditable(false);

        tfTitle= createField(content, "Subject Name *", ++gbc.gridy, bold, gbc);
        tfFees = createField(content, "Fees (RM) *", ++gbc.gridy, bold, gbc);
        tfSchedule = createField(content, "Schedule", ++gbc.gridy, bold, gbc);
        tfNotes = createField(content, "Notes Link", ++gbc.gridy, bold, gbc);

        //Buttons
        gbc.gridy++;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,20,10));
        buttonPanel.setBackground(Color.WHITE);

        JButton btnCreate = new JButton("Create Subject");
        styleButton(btnCreate, bold, new Color(0,123,255));
        styleButton(btnBack, bold, new Color(108,117,125));

        buttonPanel.add(btnBack);
        buttonPanel.add(btnCreate);
        content.add(buttonPanel, gbc);


        //actions
        btnCreate.addActionListener(e -> createSubject());
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
                        CreateSubjectForm.this,
                        confirm,
                        "Confirm Exit",
                        JOptionPane.OK_CANCEL_OPTION
                );

                if (res == JOptionPane.OK_OPTION) {
                    JLabel exitMsg = new JLabel("Thank you for using this system!");
                    exitMsg.setFont(new Font("Times New Roman", Font.BOLD, 14));
                    JOptionPane.showMessageDialog(
                            CreateSubjectForm.this,
                            exitMsg,
                            "Exit",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    System.exit(0);
                }
            }
        });
    }


    //
    private JTextField createField(JPanel content, String label,int i, Font font, GridBagConstraints gbc){
        gbc.gridy = i;
        JLabel lbl = new JLabel(label);
        lbl.setFont(font);
        content.add(lbl,gbc);

        gbc.gridy++;
        JTextField tf = new JTextField();
        tf.setFont(font);
        content.add(tf, gbc);
        return tf;
    }

    private void styleButton(JButton btn, Font font, Color color){
        btn.setFont(font);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setBorder(BorderFactory.createEmptyBorder(10,20,10,20));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setFocusPainted(false);
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                btn.setBackground(color.darker());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                btn.setBackground(color);
            }
        });
    }

    private void createSubject(){
        String id = tfID.getText().trim();
        String subject = tfTitle.getText().trim();
        String fees = tfFees.getText().trim();
        String schedule = tfSchedule.getText().trim();
        String notesLink = tfNotes.getText().trim();

        if (subject.isEmpty() || fees.isEmpty()){
            JLabel msg = new JLabel("Please fill in all fields");
            msg.setFont(new Font("Times New Roman", Font.BOLD, 14));
            JOptionPane.showMessageDialog(this, msg,
                    "Missing data", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (schedule.isBlank()) schedule = "*";
        if (notesLink.isBlank()) notesLink = "&";

        double fee;
        try{
            fee = Double.parseDouble(fees);
            if (fee < 0) throw new NumberFormatException();
        } catch (NumberFormatException e){
            JLabel msg = new JLabel("Fees must be a positive number.");
            msg.setFont(new Font("Times New Roman", Font.BOLD, 14));
            JOptionPane.showMessageDialog(this, msg,
                    "Invalid Fees", JOptionPane.WARNING_MESSAGE);
            return;
        }

        //Duplicate title check
        List<Subject> subjects = Main.subjectList;
        boolean duplicate = subjects.stream()
                .anyMatch(s -> s.getTitle().equalsIgnoreCase(subject));
        if (duplicate){
            JLabel msg = new JLabel("This subject already exists.");
            msg.setFont(new Font("Times New Roman", Font.BOLD, 14));
            JOptionPane.showMessageDialog(this, msg,
                    "Duplicate Subject", JOptionPane.WARNING_MESSAGE);
            return;
        }

        //Validate Notes Link
        String newNotesLink;
        if (!CommonMethods.isValidQuotedInput(notesLink)){
            return;
        } else {
            newNotesLink = CommonMethods.modifyInput(notesLink);

        }

        //Validate schedule
        String newSchedule;
        if (!CommonMethods.isValidQuotedInput(schedule)){
            return;
        } else {
            newSchedule = CommonMethods.modifyInput(schedule);

        }

        //Validate Name
        if (!CommonMethods.isValidName(subject)){
            return;
        }

        String tutorID = "$";
        Subject newSubject = new Subject(id, subject,tutorID, fee, newSchedule, newNotesLink);

        subjects.add(newSubject);
        SubjectManagement.saveSubjects(subjects);
        createSubjectFiles(id);

        JLabel msg = new JLabel("Subject " + subject + " created successfully!");
        msg.setFont(new Font("Times New Roman", Font.BOLD, 14));
        JOptionPane.showMessageDialog(this, msg,
                "Success", JOptionPane.INFORMATION_MESSAGE);
        clearFields();
    }

    private void createSubjectFiles(String subjectID){
        String base = "txtTCMS/";
        String enrolPath = base + "enr" + subjectID + ".txt";
        String attendPath = base + "att" + subjectID + ".txt";

        try{
            new File(enrolPath).createNewFile();
            new File(attendPath).createNewFile();

        } catch (IOException e){
            JLabel msg = new JLabel("Error creating subject files:\n " + e.getMessage());
            msg.setFont(new Font("Times New Roman", Font.BOLD, 14));
            JOptionPane.showMessageDialog(this, msg,
                    "File Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearFields(){
        ArrayList<Subject> subjects = Main.subjectList;
        String nextID = CommonMethods.getNextID("SJ", subjects);
        tfID.setText(nextID);
        tfTitle.setText("");
        tfFees.setText("");
        tfSchedule.setText("");
        tfNotes.setText("");
    }

    @Override
    protected void onBack(){
    }
}

