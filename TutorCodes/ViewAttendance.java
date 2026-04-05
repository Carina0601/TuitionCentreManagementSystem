import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class ViewAttendance {
    private JPanel mainPanel;
    private JPanel recordListPanel;
    private Container app;
    private JLabel titleLabel;


    public ViewAttendance(Container app) {
        this.app = app;

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(218, 237, 251));


        titleLabel = new JLabel("", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        recordListPanel = new JPanel();
        recordListPanel.setBackground(new Color(218, 237, 251));
        recordListPanel.setLayout(new BoxLayout(recordListPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(recordListPanel);
        scrollPane.setBackground(new Color(218, 237, 251));
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        mainPanel.add(scrollPane, BorderLayout.CENTER);


        JButton backButton = new JButton("Back");
        backButton.setBackground(new Color(145, 200, 243));
        backButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        backButton.addActionListener(e -> app.showCard("AttendanceFunction"));

        JButton homeButton = new JButton("Home");
        homeButton.setBackground(new Color(145, 200, 243));
        homeButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        homeButton.addActionListener( e -> app.showCard("TutorMainMenu"));


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
        recordListPanel.removeAll();



        Subject subject = AdminSelectedSubject.get();

        titleLabel.setText("Attendance Records for " + subject.getTitle());

        if (subject != null) {
            List<Attendance> records = CommonMethods.loadAttendance(subject.getID());


            Map<LocalDate, List<Attendance>> grouped = records.stream()
                    .collect(Collectors.groupingBy(
                            attend -> LocalDate.parse(attend.getDate()),
                            TreeMap::new,
                            Collectors.toList()
                    ));

            List<LocalDate> sortedDates = new ArrayList<>(grouped.keySet());
            Collections.reverse(sortedDates);

            int sectionIndex = 1;
            for (LocalDate date : sortedDates) {
                List<Attendance> group = grouped.get(date);


                JLabel dateLabel = new JLabel(sectionIndex++ + ". Date: " + date);
                dateLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));

                JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
                datePanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0));
                datePanel.setOpaque(false);
                datePanel.add(dateLabel);

                recordListPanel.add(datePanel);



                JPanel header = new JPanel(new GridLayout(1, 4));
                JLabel studentID = new JLabel("Student ID", SwingConstants.CENTER);
                studentID.setFont(new Font("Times New Roman", Font.BOLD, 14));
                header.add(studentID);

                JLabel name = new JLabel("Name", SwingConstants.CENTER);
                name.setFont(new Font("Times New Roman", Font.BOLD, 14));
                header.add(name);

                JLabel status = new JLabel("Status", SwingConstants.CENTER);
                status.setFont(new Font("Times New Roman", Font.BOLD, 14));
                header.add(status);

                JLabel level = new JLabel("Level", SwingConstants.CENTER);
                level.setFont(new Font("Times New Roman", Font.BOLD, 14));
                header.add(level);

                header.setBackground(new Color(220, 220, 220));
                header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
                recordListPanel.add(header);


                for (Attendance record : group) {
                    String studentName = "Unknown";
                    ArrayList<Student> studentList = Main.studentList;
                    for (Student student : studentList) {
                        if (student.getID().equals(record.getStudentID())) {
                            studentName = student.getName();
                            break;
                        }
                    }

                    JPanel row = new JPanel(new GridLayout(1, 4));

                    JLabel studID = new JLabel(record.getStudentID(), SwingConstants.CENTER);
                    studID.setFont(new Font("Times New Roman", Font.BOLD, 14));
                    row.add(studID);

                    JLabel studName = new JLabel(studentName, SwingConstants.CENTER);
                    studName.setFont(new Font("Times New Roman", Font.BOLD, 14));
                    row.add(studName);




                    JLabel statusLabel = new JLabel(record.getStatus(), SwingConstants.CENTER);
                    statusLabel.setFont(new Font("Times New Roman", Font.BOLD, 14));
                    statusLabel.setOpaque(true);
                    statusLabel.setForeground(Color.BLACK);


                    switch (record.getStatus().toLowerCase()) {
                        case "absent":
                            statusLabel.setBackground(new Color(255, 102, 102));
                            break;
                        case "late":
                            statusLabel.setBackground(new Color(255, 255, 153));
                            break;
                        case "present":
                            statusLabel.setBackground(new Color(153, 255, 153));
                            break;
                        default:
                            statusLabel.setBackground(Color.LIGHT_GRAY);
                    }

                    statusLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                    row.add(statusLabel);

                    JLabel studLevel = new JLabel(record.getLevel(), SwingConstants.CENTER);
                    studLevel.setFont(new Font("Times New Roman", Font.BOLD, 14));
                    row.add(studLevel);


                    row.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));
                    recordListPanel.add(row);
                    recordListPanel.add(Box.createVerticalStrut(5));

                }
            }

            if (records.isEmpty()) {
                JLabel empty = new JLabel("No attendance records found.");
                empty.setFont(new Font("Times New Roman", Font.ITALIC, 14));
                empty.setForeground(Color.GRAY);
                recordListPanel.add(empty);
            }
        }

        recordListPanel.revalidate();
        recordListPanel.repaint();
    }


    public JPanel getMainPanel() {
        return mainPanel;
    }
}