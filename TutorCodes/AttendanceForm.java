import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.*;
import java.util.List;

public class AttendanceForm {
    private JPanel mainPanel;
    private JPanel studentListPanel;
    private JButton saveButton;
    private JButton backButton;
    private JButton homeButton;
    private Container app;
    private LinkedHashMap<String, ButtonGroup> attendanceMap;
    private JComboBox<LocalDate> dateSelector;
    private JComboBox<String> levelSelector;
    private JLabel title;



    public AttendanceForm(Container app) {
        this.app = app;
        attendanceMap = new LinkedHashMap<>();

        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(218, 237, 251));


        JPanel headerPanel = new JPanel(new GridLayout(2, 1));
        headerPanel.setBackground(new Color(218, 237, 251));


        title = new JLabel("", SwingConstants.CENTER);
        title.setFont(new Font("Times New Roman", Font.BOLD, 18));
        title.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 5));
        headerPanel.add(title);


        JPanel selectorPanel = new JPanel();
        selectorPanel.setBackground(new Color(218, 237, 251));
        JLabel date = new JLabel("Select Date:");
        date.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        selectorPanel.add(date);

        dateSelector = new JComboBox<>();
        populateDateSelector();
        dateSelector.setPreferredSize(new Dimension(120, 25));
        selectorPanel.add(dateSelector);

        JLabel level = new JLabel("Select Level:");
        level.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        selectorPanel.add(level);



        levelSelector = new JComboBox<>();
        populateLevelSelector();
        levelSelector.setPreferredSize(new Dimension(100, 25));
        selectorPanel.add(levelSelector);

        headerPanel.add(selectorPanel);
        mainPanel.add(headerPanel, BorderLayout.NORTH);


        studentListPanel = new JPanel();
        studentListPanel.setBackground(new Color(218, 237, 251));
        studentListPanel.setLayout(new BoxLayout(studentListPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(studentListPanel);
        scrollPane.setBackground(new Color(218, 237, 251));
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.add(scrollPane, BorderLayout.CENTER);


        backButton = new JButton("Back");
        backButton.setBackground(new Color(145, 200, 243));
        backButton.setFont(new Font("Times New Roman", Font.BOLD, 14));

        homeButton = new JButton("Home");
        homeButton.setBackground(new Color(145, 200, 243));
        homeButton.setFont(new Font("Times New Roman", Font.BOLD, 14));

        saveButton = new JButton("Save Attendance");
        saveButton.setBackground(new Color(145, 200, 243));
        saveButton.setFont(new Font("Times New Roman", Font.BOLD, 14));

        backButton.addActionListener(e -> app.showCard("AttendanceFunction"));

        homeButton.addActionListener( e -> app.showCard("TutorMainMenu"));

        saveButton.addActionListener(e -> saveAttendance());


        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(new Color(218, 237, 251));



        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel.setBackground(new Color(218, 237, 251));
        leftPanel.add(backButton);

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setBackground(new Color(218, 237, 251));
        rightPanel.add(homeButton);

        JPanel middlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        middlePanel.setBackground(new Color(218, 237, 251));
        middlePanel.add(saveButton);

        bottomPanel.add(leftPanel, BorderLayout.WEST);
        bottomPanel.add(rightPanel, BorderLayout.EAST);
        bottomPanel.add(middlePanel, BorderLayout.CENTER);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);


        dateSelector.addActionListener(e -> refresh());
        levelSelector.addActionListener(e -> refresh());
    }

    private void populateDateSelector() {
        dateSelector.removeAllItems();
        LocalDate today = LocalDate.now();
        for (int i = -7; i <= 7; i++) {
            dateSelector.addItem(today.plusDays(i));
        }
        dateSelector.setSelectedItem(today);
    }

    private void populateLevelSelector() {
        levelSelector.removeAllItems();
        levelSelector.addItem("Select Level");
        levelSelector.addItem("F1");
        levelSelector.addItem("F2");
        levelSelector.addItem("F3");
        levelSelector.addItem("F4");
        levelSelector.addItem("F5");

        levelSelector.setSelectedIndex(0);
    }


    public void refresh() {
        studentListPanel.removeAll();
        attendanceMap.clear();

        Subject subject = AdminSelectedSubject.get();
        title.setText("Mark Attendance for " + subject.getTitle());


        String subjectId = subject.getID();
        ArrayList<Enrolment> enrolments = CommonMethods.loadEnrolment(subjectId);

        String selectedLevel = (String) levelSelector.getSelectedItem();
        LocalDate selectedDate = (LocalDate) dateSelector.getSelectedItem();

        if (selectedLevel == null || selectedLevel.equals("Select Level") || selectedDate == null) {
            studentListPanel.revalidate();
            studentListPanel.repaint();
            return;
        }

        ArrayList<Student> studentList = Main.studentList;

        List<Map.Entry<Enrolment, Student>> matched = new ArrayList<>();

        for (Enrolment enrol : enrolments) {
            if (!enrol.getLevel().trim().equalsIgnoreCase(selectedLevel)) continue;

            String studentID = enrol.getStudentID();
            for (Student student : studentList) {
                if (student.getID().equals(studentID)) {
                    matched.add(new AbstractMap.SimpleEntry<>(enrol, student));
                    break;
                }
            }
        }


        matched.sort(Comparator.comparing(e -> e.getValue().getName().toLowerCase()));


        int index = 1;
        if (matched.isEmpty()) {
            JLabel emptyLabel = new JLabel("No available students for the selected level.");
            emptyLabel.setFont(new Font("Times New Roman", Font.ITALIC, 14));
            emptyLabel.setForeground(Color.GRAY);
            emptyLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            studentListPanel.add(emptyLabel);
            saveButton.setEnabled(false);
        } else {
            saveButton.setEnabled(true);
            for (Map.Entry<Enrolment, Student> entry : matched) {
                Student student = entry.getValue();

                JPanel row = new JPanel(new GridBagLayout());
                row.setBackground(new Color(218, 237, 251));
                row.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

                GridBagConstraints gbc = new GridBagConstraints();

                String info = index++ + ". " + student.getID() + "   " + student.getName();
                JLabel infoCombine = new JLabel(info);
                infoCombine.setFont(new Font("Times New Roman", Font.PLAIN,14));
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.weightx = 1.0;
                gbc.anchor = GridBagConstraints.WEST;
                gbc.fill = GridBagConstraints.HORIZONTAL;
                row.add(infoCombine, gbc);

                JRadioButton presentButton = new JRadioButton("Present");
                presentButton.setBackground(new Color(218, 237, 251));
                presentButton.setFont(new Font("Times New Roman", Font.BOLD, 14));

                JRadioButton absentButton = new JRadioButton("Absent");
                absentButton.setBackground(new Color(218, 237, 251));
                absentButton.setFont(new Font("Times New Roman", Font.BOLD, 14));

                JRadioButton lateButton = new JRadioButton("Late");
                lateButton.setBackground(new Color(218, 237, 251));
                lateButton.setFont(new Font("Times New Roman", Font.BOLD, 14));

                absentButton.setSelected(true); // Default

                ButtonGroup group = new ButtonGroup();

                group.add(presentButton);
                group.add(absentButton);
                group.add(lateButton);
                attendanceMap.put(student.getID(), group);

                JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
                buttonPanel.setBackground(new Color(218, 237, 251));
                buttonPanel.add(presentButton);
                buttonPanel.add(absentButton);
                buttonPanel.add(lateButton);

                gbc.gridx = 1;
                gbc.weightx = 0;
                gbc.anchor = GridBagConstraints.EAST;
                gbc.fill = GridBagConstraints.NONE;
                row.add(buttonPanel, gbc);

                studentListPanel.add(row);
            }
        }

        studentListPanel.revalidate();
        studentListPanel.repaint();
    }


    private void saveAttendance() {
        Subject subject = AdminSelectedSubject.get();
        if (subject == null) {
            JLabel subjectSelected = new JLabel("No subject selected!");
            subjectSelected.setFont(new Font("Times New Roman", Font.BOLD, 14));
            JOptionPane.showMessageDialog(mainPanel, subjectSelected);
            return;
        }

        String subjectId = subject.getID();
        LocalDate selectedDate = (LocalDate) dateSelector.getSelectedItem();
        String selectedLevel = (String) levelSelector.getSelectedItem();

        if (selectedDate == null || selectedLevel == null || selectedLevel.equals("Select Level")) {
            JLabel validSelection = new JLabel("Please select a valid date and level.");
            validSelection.setFont(new Font("Times New Roman", Font.BOLD, 14));

            JOptionPane.showMessageDialog(mainPanel, validSelection);
            return;
        }

        String date = selectedDate.toString();

        ArrayList<Enrolment> enrolments = CommonMethods.loadEnrolment(subjectId);
        ArrayList<Attendance> attendanceList = CommonMethods.loadAttendance(subjectId);


        boolean existingRecordFound = attendanceList.stream().anyMatch(attend ->
                attend.getDate().equals(date) &&
                attend.getLevel().equalsIgnoreCase(selectedLevel)
        );

        if (existingRecordFound) {

            JLabel recordExists = new JLabel("Attendance records already exist for this date and level.\nPlease proceed to Update Attendance Records.");
            recordExists.setFont(new Font("Times New Roman", Font.PLAIN, 14));

            JOptionPane.showMessageDialog(
                    mainPanel,
                    recordExists,
                    "Records Exist",
                    JOptionPane.INFORMATION_MESSAGE
            );
            return;
        }


        for (Enrolment enrol : enrolments) {
            if (!enrol.getLevel().trim().equalsIgnoreCase(selectedLevel))
                continue;

            String studentId = enrol.getStudentID();
            String status = getSelectedStatus(attendanceMap.get(studentId));
            if (status == null) status = "Absent";


            attendanceList.removeIf(attend ->
                    attend.getStudentID().equals(studentId) &&
                    attend.getDate().equals(date)
            );

            Attendance newRecord = new Attendance(CommonMethods.getNextID("ATT", attendanceList), studentId, selectedLevel, date, status);
            attendanceList.add(newRecord);
        }

        CommonMethods.writeFile("txtTCMS/att" + subjectId + ".txt", attendanceList);

        JLabel saved = new JLabel("Attendance saved successfully.");
        saved.setFont(new Font("Times New Roman", Font.BOLD, 14));

        JOptionPane.showMessageDialog(mainPanel, saved);
    }



    private String getSelectedStatus(ButtonGroup group) {
        Enumeration<AbstractButton> buttons = group.getElements();
        while (buttons.hasMoreElements()) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected())
                return button.getText();
        }
        return null;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

}

