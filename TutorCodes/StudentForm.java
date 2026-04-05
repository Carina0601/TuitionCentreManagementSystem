import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;

public class StudentForm {
    private JPanel mainPanel;
    private JPanel studentListPanel;
    private JLabel titleLabel;
    private Container app;

    public StudentForm(Container app) {
        this.app = app;

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(218, 237, 251));

        titleLabel = new JLabel("Enrolled Students", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));

        studentListPanel = new JPanel();
        studentListPanel.setBackground(new Color(218, 237, 251));
        studentListPanel.setLayout(new BoxLayout(studentListPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(studentListPanel);
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        backButton.setBackground(new Color(145, 200, 243));
        backButton.addActionListener(e -> app.showCard("ManageStudents"));

        JButton homeButton = new JButton("Home");
        homeButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        homeButton.setBackground(new Color(145, 200, 243));
        homeButton.addActionListener(e -> app.showCard("TutorMainMenu"));

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(new Color(218, 237, 251));

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel.setBackground(new Color(218, 237, 251));
        leftPanel.add(backButton);

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setBackground(new Color(218, 237, 251));
        rightPanel.add(homeButton);

        bottomPanel.add(leftPanel, BorderLayout.WEST);
        bottomPanel.add(rightPanel, BorderLayout.EAST);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
    }

    public void refresh() {
        studentListPanel.removeAll();

        Subject subject = AdminSelectedSubject.get();
        if (subject == null) {
            titleLabel.setText("No subject selected.");
            titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));
            return;
        }

        titleLabel.setText("Students enrolled in: " + subject.getTitle());
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));

        String subjectId = subject.getID();

        ArrayList<Enrolment> enrolments = CommonMethods.loadEnrolment(subjectId);
        if (enrolments.isEmpty()) {
            JLabel noStudents = new JLabel("No students have been enrolled");
            noStudents.setFont(new Font("Times New Roman", Font.PLAIN, 18));
            studentListPanel.add(noStudents);
        } else {
            ArrayList<Student> enrolledStudents = new ArrayList<>();

            for (Enrolment enrols : enrolments) {
                String studentID = enrols.getStudentID();
                for (Student student : Main.studentList) {
                    if (student.getID().equals(studentID)) {
                        student.setLevel(enrols.getLevel());
                        student.setRegisterDate(enrols.getDate());
                        enrolledStudents.add(student);
                        break;
                    }
                }
            }


            enrolledStudents.sort(Comparator.comparing(Student::getName, String.CASE_INSENSITIVE_ORDER));


            for (Student student : enrolledStudents) {
                String studentID = student.getID();
                String name = student.getName();
                String phone = student.getPhoneNo();
                String form = student.getLevel();
                String date = student.getRegisterDate();

                JPanel panel = new JPanel(new GridLayout(5, 2, 5, 5));
                panel.setBackground(Color.WHITE);

                JLabel idLabel = new JLabel("Student ID:");
                JLabel idValue = new JLabel(studentID);
                JLabel nameLabel = new JLabel("Name:");
                JLabel nameValue = new JLabel(name);
                JLabel levelLabel = new JLabel("Level:");
                JLabel levelValue = new JLabel(form);
                JLabel dateLabel = new JLabel("Date Joined:");
                JLabel dateValue = new JLabel(date);
                JLabel phoneLabel = new JLabel("Phone Number:");
                JLabel phoneValue = new JLabel(phone);

                for (JLabel label : new JLabel[]{idLabel, idValue, nameLabel, nameValue, levelLabel, levelValue, dateLabel, dateValue, phoneLabel, phoneValue}) {
                    label.setFont(new Font("Times New Roman", Font.PLAIN, 14));
                }

                panel.add(idLabel); panel.add(idValue);
                panel.add(nameLabel); panel.add(nameValue);
                panel.add(levelLabel); panel.add(levelValue);
                panel.add(dateLabel); panel.add(dateValue);
                panel.add(phoneLabel); panel.add(phoneValue);

                panel.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.GRAY, 1),
                        BorderFactory.createEmptyBorder(10, 10, 10, 10)
                ));
                panel.setMaximumSize(new Dimension(530, 200));
                studentListPanel.add(Box.createVerticalStrut(5));
                studentListPanel.add(panel);
            }
        }

        studentListPanel.revalidate();
        studentListPanel.repaint();
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
