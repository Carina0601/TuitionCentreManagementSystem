import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;


public class SubjectModifications {
    private JPanel mainPanel;
    private JTextField idField;
    private JTextField titleField;
    private JTextField tutorIdField;
    private JTextField feesField;
    private JTextField scheduleField;
    private JTextField notesLinkField;
    private JButton updateButton;
    private JButton backButton;
    private JButton deleteButton;
    private JButton homeButton;
    private JLabel titleLabel;


    public void refresh(){

        Subject subject = AdminSelectedSubject.get();

        titleLabel.setText("Update " + subject.getTitle());
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));

        if(subject != null){
            idField.setText(subject.getID());
            titleField.setText(subject.getTitle());
            tutorIdField.setText(subject.getTutorID());
            feesField.setText(String.valueOf(subject.getFees()));
            scheduleField.setText(subject.getSchedule().replace("\"",""));
            notesLinkField.setText(subject.getNotesLink().replace("\"",""));
        }
    }

    public SubjectModifications(Container app) {
        mainPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                refresh();
            }

        });

        updateButton.addActionListener( e -> {
            Subject subject = AdminSelectedSubject.get();

            String newID = idField.getText().trim();
            String newTitle = titleField.getText().trim();
            String newTutorID = tutorIdField.getText().trim();
            String newFees = feesField.getText().trim();
            String newSchedule = scheduleField.getText().trim();
            String newNotesLink = notesLinkField.getText().trim();


            if (newID.isEmpty() || newTitle.isEmpty() || newTutorID.isEmpty() ||
                    newFees.isEmpty() || newSchedule.isEmpty() || newNotesLink.isEmpty()) {

                JLabel messageLabel = new JLabel("Please fill in all fields.");
                messageLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));

                JOptionPane.showMessageDialog(
                        null,
                        messageLabel,
                        "Missing Information",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }


            if (!newFees.matches("\\d+(\\.\\d{1,2})?")) {
                JLabel messageLabel = new JLabel("Fees must be a valid number (e.g., 120 or 120.50).");
                messageLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));

                JOptionPane.showMessageDialog(
                        null,
                        messageLabel,
                        "Invalid Fees",
                        JOptionPane.ERROR_MESSAGE
                );
                return;

            }


            double newFeesValue = Double.parseDouble(newFees);
            String newScheduleQ;
            if (!CommonMethods.isValidQuotedInput(newSchedule)){
                return;

            }
            else{
                newScheduleQ = CommonMethods.modifyInput(newSchedule);
            }

            String newNotesLinkQ;
            if (!CommonMethods.isValidQuotedInput(newNotesLink)){
                return;

            }
            else{
                newNotesLinkQ = CommonMethods.modifyInput(newNotesLink);

            }


            subject.setID(newID);
            subject.setTitle(newTitle);
            subject.setTutorID(newTutorID);
            subject.setFees(newFeesValue);
            subject.setSchedule(newScheduleQ);
            subject.setNotesLink(newNotesLinkQ);

            ArrayList<Subject> subjectList = Main.subjectList;

            for (int i = 0; i < subjectList.size(); i++) {
                if (subjectList.get(i).getID().equals(subject.getID())) {
                    subjectList.set(i, subject);
                    break;
                }
            }

            CommonMethods.writeFile("txtTCMS/subject.txt", subjectList);

            JLabel messageLabel = new JLabel("Subject updated successfully!");
            messageLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));

            JOptionPane.showMessageDialog(
                    null,
                    messageLabel);

        });

        deleteButton.addActionListener( e ->{
            Subject subject = AdminSelectedSubject.get();

            JLabel messageLabel = new JLabel("Are you sure you want to delete?");
            messageLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));
            int result = JOptionPane.showConfirmDialog(
                    null,
                    messageLabel,
                    "Confirm Deletion",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );

            if (result != JOptionPane.YES_OPTION) {
                return;
            }

            ArrayList<Subject> subjectList = Main.subjectList;

            for (int i = 0; i < subjectList.size(); i++) {
                if (subjectList.get(i).getID().equals(subject.getID())) {
                    subjectList.remove(i);
                    break;
                }
            }

            CommonMethods.writeFile("txtTCMS/subject.txt", subjectList);

            JOptionPane.showMessageDialog(null, "Subject deleted successfully!");

            app.showCard("MySubjects");

        });

        backButton.addActionListener( e -> {
            app.showCard("MySubjects");
        });

        homeButton.addActionListener( e -> {
            app.showCard("TutorMainMenu");
        });
    }

    public JPanel getMainPanel(){
        return mainPanel;
    }

}
