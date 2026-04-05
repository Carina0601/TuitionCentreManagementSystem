import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;

public class ViewSubject {
    private JPanel mainPanel;
    private JLabel titleLabel;
    private JScrollPane scrollPane;
    private JPanel subjectListPanel;
    private JButton backButton;
    private JButton homeButton;

    private Container app;

    public ViewSubject(Container app) {
        this.app = app;


        titleLabel.setText("List of Subjects");
        refresh();

        backButton.addActionListener(e -> {
            app.showCard("ManageSubjects");
        });

        homeButton.addActionListener(e -> {
            app.showCard("TutorMainMenu");
        });
    }

    public void refresh(){
        subjectListPanel.removeAll();



        subjectListPanel.setLayout(new BoxLayout(subjectListPanel, BoxLayout.Y_AXIS));
        subjectListPanel.removeAll();
        ArrayList<Subject> subjectList = Main.subjectList;
        for (Subject subject : subjectList) {
            JPanel subjectPanel = createSubjectViewPanel(subject);
            TitledBorder border = BorderFactory.createTitledBorder(subject.getTitle());
            border.setTitleFont(new Font("Times New Roman", Font.BOLD, 14));
            subjectPanel.setBorder(border);
            subjectListPanel.add(subjectPanel);
        }
    }

    public static JPanel createSubjectViewPanel(Subject subject) {
        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 5));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 600));
        panel.setBackground(Color.WHITE);

        JLabel idLabel = new JLabel("ID:");
        JLabel idValue = new JLabel(subject.getID());

        JLabel titleLabel = new JLabel("Title:");
        JLabel titleValue = new JLabel(subject.getTitle());

        JLabel tutorIdLabel = new JLabel("Tutor ID:");
        JLabel tutorIdValue = new JLabel(subject.getTutorID());

        JLabel feesLabel = new JLabel("Fees:");
        JLabel feesValue = new JLabel(String.valueOf(subject.getFees()));

        JLabel scheduleLabel = new JLabel("Schedule:");
        JLabel notesLabel = new JLabel("Notes Link:");


        for (JLabel label : new JLabel[]{idLabel, idValue, titleLabel, titleValue,
                tutorIdLabel, tutorIdValue, feesLabel, feesValue, scheduleLabel, notesLabel}) {
            label.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        }

        panel.add(idLabel); panel.add(idValue);
        panel.add(titleLabel); panel.add(titleValue);
        panel.add(tutorIdLabel); panel.add(tutorIdValue);
        panel.add(feesLabel); panel.add(feesValue);
        panel.add(scheduleLabel);

        if (subject.getSchedule().equals("*")) {
            JLabel scheduleValue = new JLabel("Not Available");
            scheduleValue.setFont(new Font("Times New Roman", Font.PLAIN, 14));
            panel.add(scheduleValue);
        } else {
            ArrayList<String> scheduleList = CommonMethods.splitRespectQuotes(subject.getSchedule());
            String removeQuotes = scheduleList.get(0).replace("\"", "");
            JTextArea scheduleArea = new JTextArea(removeQuotes);
            scheduleArea.setFont(new Font("Times New Roman", Font.PLAIN, 14));
            scheduleArea.setLineWrap(true);
            scheduleArea.setWrapStyleWord(true);
            scheduleArea.setEditable(false);
            scheduleArea.setOpaque(false);
            scheduleArea.setBorder(null);
            scheduleArea.setHighlighter(null);
            scheduleArea.setFocusable(false);
            panel.add(scheduleArea);
        }

        panel.add(notesLabel);
        if (subject.getNotesLink().equals("&")) {
            JLabel notesValue = new JLabel("Not Available");
            notesValue.setFont(new Font("Times New Roman", Font.PLAIN, 14));
            panel.add(notesValue);
        } else {
            ArrayList<String> notesList = CommonMethods.splitRespectQuotes(subject.getNotesLink());
            String removeQuotes = notesList.get(0).replace("\"", "");
            JTextArea notesArea = new JTextArea(removeQuotes);
            notesArea.setFont(new Font("Times New Roman", Font.PLAIN, 14));
            notesArea.setLineWrap(true);
            notesArea.setWrapStyleWord(true);
            notesArea.setEditable(false);
            notesArea.setOpaque(false);
            notesArea.setBorder(null);
            notesArea.setHighlighter(null);
            notesArea.setFocusable(false);
            panel.add(notesArea);
        }

        return panel;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
