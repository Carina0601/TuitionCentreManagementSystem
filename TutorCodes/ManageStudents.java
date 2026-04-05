
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ManageStudents {
    private JPanel mainPanel;
    private JPanel subjectListPanel;
    private JButton backButton;
    private Container app;

    public ManageStudents(Container app){
        this.app = app;

        backButton.addActionListener(e -> {
            app.showCard("TutorMainMenu");
        });


    }

    public void refresh(){
        subjectListPanel.removeAll();

        ArrayList<Subject> subjectList = Main.subjectList;


        int count = 1;
        boolean hasSubjects = false;

        subjectListPanel.setLayout(new BoxLayout(subjectListPanel, BoxLayout.Y_AXIS));

        for (Subject subject : subjectList) {
            if (TutorMethods.getTutorID().equals(subject.getTutorID())) {
                hasSubjects = true;
                String title = subject.getTitle();

                JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
                buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
                buttonPanel.setOpaque(false);

                JLabel numberLabel = new JLabel(count + ".");
                numberLabel.setPreferredSize(new Dimension(30, 30));
                numberLabel.setHorizontalAlignment(SwingConstants.RIGHT);

                JButton subjectButton = new JButton(title);
                subjectButton.setPreferredSize(new Dimension(200, 40));
                subjectButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
                subjectButton.setBackground(new Color(182, 219, 247));

                subjectButton.addActionListener(e -> {
                    AdminSelectedSubject.set(subject);
                    app.showCard("StudentForm");
                });

                buttonPanel.add(numberLabel);
                buttonPanel.add(subjectButton);
                subjectListPanel.add(buttonPanel);

                count++;

            }

        }

        if (!hasSubjects) {
            JLabel noSubjectsLabel = new JLabel("No subjects found.");
            noSubjectsLabel.setFont(new Font("Times New Roman", Font.ITALIC, 16));
            noSubjectsLabel.setForeground(Color.GRAY);
            noSubjectsLabel.setHorizontalAlignment(SwingConstants.CENTER);

            JPanel messagePanel = new JPanel(new BorderLayout());
            messagePanel.add(noSubjectsLabel, BorderLayout.CENTER);

            subjectListPanel.add(messagePanel);
        }


    }
    public JPanel getMainPanel(){
        return mainPanel;
    }

}
