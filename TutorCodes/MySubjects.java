import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;


public class MySubjects {
    private JPanel mainPanel;
    private JScrollPane scrollPane;
    private JPanel subjectListPanel;
    private JButton backButton;
    private JButton homeButton;

    private Container app;

    public MySubjects(Container app) {
        this.app = app;

        subjectListPanel.setLayout(new BoxLayout(subjectListPanel, BoxLayout.Y_AXIS));
        refresh();

        backButton.addActionListener(e -> {
            app.showCard("ManageSubjects");
        });

        homeButton.addActionListener(e -> {
            app.showCard("TutorMainMenu");
        });
    }

    public void refresh() {
        subjectListPanel.removeAll();

        ArrayList<Subject> subjectList = Main.subjectList;

        boolean hasSubjects = false;


        for (Subject subject : subjectList) {
            if ((TutorMethods.getTutorID()).equals(subject.getTutorID())) {
                hasSubjects = true;
                JPanel subjectPanel = ViewSubject.createSubjectViewPanel(subject);
                TitledBorder border = BorderFactory.createTitledBorder(subject.getTitle());
                border.setTitleFont(new Font("Times New Roman", Font.BOLD, 14));
                subjectPanel.setBorder(border);
                subjectPanel.setBackground(Color.WHITE);

                JButton editButton = new JButton("Edit");
                editButton.setFocusable(false);
                editButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                editButton.setAlignmentY(Component.CENTER_ALIGNMENT);
                editButton.setPreferredSize(new Dimension(50, 25));
                editButton.setMargin(new Insets(2, 6, 2, 6));
                editButton.setFont(new Font("Times New Roman", Font.PLAIN, 14));
                editButton.setBackground(new Color(145, 200, 243));

                editButton.addActionListener(e -> {
                    AdminSelectedSubject.set(subject);
                    app.showCard("SubjectModifications");
                });

                JPanel buttonWrapper = new JPanel();
                buttonWrapper.setLayout(new BoxLayout(buttonWrapper, BoxLayout.Y_AXIS));
                buttonWrapper.setOpaque(false);
                buttonWrapper.add(Box.createVerticalGlue());
                buttonWrapper.add(editButton);
                buttonWrapper.add(Box.createVerticalGlue());

                JPanel wrapperPanel = new JPanel(new BorderLayout(10, 0));
                wrapperPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                wrapperPanel.setBackground(Color.WHITE);
                wrapperPanel.add(subjectPanel, BorderLayout.CENTER);
                wrapperPanel.add(buttonWrapper, BorderLayout.EAST);

                subjectListPanel.add(wrapperPanel);
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

        subjectListPanel.revalidate();
        subjectListPanel.repaint();
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}

