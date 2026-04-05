import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ReceptionistMethods {
    public static void mainMenu(String id){

        JFrame mainMenuFrame = getFrame("Receptionist Main Menu");

        JLabel title = new JLabel("Main Menu", SwingConstants.CENTER);
        title.setFont(new Font("Times New Roman", Font.BOLD, 20));
        title.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        mainMenuFrame.add(title, BorderLayout.NORTH);

        JPanel optionsPanel = new JPanel(new GridLayout(6, 1));
        optionsPanel.setBackground(new Color(230, 230, 250));
        optionsPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JButton profileButton = new JButton("1. User Profile");
        JButton registrationButton = new JButton("2. Student Registration");
        JButton enrolmentButton = new JButton("3. Subject Enrolment");
        JButton billButton = new JButton("4. Bills and Receipts");
        JButton studentInfoButton = new JButton("5. View and Edit Student Info");
        JButton signOutButton = new JButton("6. Sign Out");

        profileButton.setFont(new Font("Times New Roman", Font.BOLD, 18));
        registrationButton.setFont(new Font("Times New Roman", Font.BOLD, 18));
        enrolmentButton.setFont(new Font("Times New Roman", Font.BOLD, 18));
        billButton.setFont(new Font("Times New Roman", Font.BOLD, 18));
        studentInfoButton.setFont(new Font("Times New Roman", Font.BOLD, 18));
        signOutButton.setFont(new Font("Times New Roman", Font.BOLD, 18));

        profileButton.setBackground(new Color(198, 194, 234));
        registrationButton.setBackground(new Color(224, 255, 255));
        enrolmentButton.setBackground(new Color(198, 194, 234));
        billButton.setBackground(new Color(224, 255, 255));
        studentInfoButton.setBackground(new Color(198, 194, 234));
        signOutButton.setBackground(new Color(224, 255, 255));

        optionsPanel.add(profileButton);
        optionsPanel.add(registrationButton);
        optionsPanel.add(enrolmentButton);
        optionsPanel.add(billButton);
        optionsPanel.add(studentInfoButton);
        optionsPanel.add(signOutButton);

        mainMenuFrame.add(optionsPanel, BorderLayout.CENTER);

        profileButton.addActionListener(e -> {
            mainMenuFrame.dispose();
            CommonMethods.userProfile(mainMenuFrame, Main.filePaths[3], id, Main.recepList, Main.recepList);
        });
        registrationButton.addActionListener(e -> {
            mainMenuFrame.dispose();
            studentRegistration(id);
        });
        enrolmentButton.addActionListener(e -> {
            mainMenuFrame.dispose();
            subjectEnrolment(id);
        });
        billButton.addActionListener(e -> {
            mainMenuFrame.dispose();
            billsReceipts(id);
        });
        studentInfoButton.addActionListener(e -> {
            mainMenuFrame.dispose();
            viewStudentInfo(id);
        });
        signOutButton.addActionListener(e -> {
            JLabel confirm = new JLabel("Confirm to sign out?");
            confirm.setFont(new Font("Times New Roman", Font.BOLD, 16));
            int result = JOptionPane.showConfirmDialog(null, confirm, "Confirm Sign Out", JOptionPane.OK_CANCEL_OPTION);
            if(result == JOptionPane.YES_OPTION){
                mainMenuFrame.dispose();
                Main.showMainMenu();
            }
        });
    }

    private static JFrame getFrame(String title){
        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setSize(550, 450);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.getContentPane().setBackground(new Color(230, 230, 250));

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                JLabel confirmExit = new JLabel("Confirm to exit the system?");
                confirmExit.setFont(new Font("Times New Roman", Font.BOLD, 16));

                int result = JOptionPane.showConfirmDialog(null, confirmExit, "Confirm Exit", JOptionPane.OK_CANCEL_OPTION);
                if(result == JOptionPane.YES_OPTION){
                    JLabel thankYou = new JLabel("Thank you for using this system!");
                    thankYou.setFont(new Font("Times New Roman", Font.BOLD, 16));

                    JOptionPane.showMessageDialog(null, thankYou, "Exit", JOptionPane.INFORMATION_MESSAGE);
                    System.exit(1);
                }
            }
        });

        return frame;
    }

    private static void studentRegistration(String id){
        JFrame registrationFrame = getFrame("Student Registration");

        JLabel title = new JLabel("Student Registration", SwingConstants.CENTER);
        title.setFont(new Font("Times New Roman", Font.BOLD, 20));
        title.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        registrationFrame.add(title, BorderLayout.NORTH);

        JPanel registrationPanel = new JPanel(new GridLayout(3, 1));
        registrationPanel.setBackground(new Color(230, 230, 250));
        registrationPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JButton registerStudentButton = new JButton("1. Register New Student");
        JButton removeStudentButton = new JButton("2. Remove Student");
        JButton backButton = new JButton("3. Back to Main Menu");

        registerStudentButton.setFont(new Font("Times New Roman", Font.BOLD, 18));
        removeStudentButton.setFont(new Font("Times New Roman", Font.BOLD, 18));
        backButton.setFont(new Font("Times New Roman", Font.BOLD, 18));

        registerStudentButton.setBackground(new Color(220, 197, 232));
        removeStudentButton.setBackground(new Color(237, 226, 244));
        backButton.setBackground(new Color(220, 197, 232));

        registrationPanel.add(registerStudentButton);
        registrationPanel.add(removeStudentButton);
        registrationPanel.add(backButton);

        registrationFrame.add(registrationPanel, BorderLayout.CENTER);

        registerStudentButton.addActionListener(e -> {
            registrationFrame.dispose();
            registerStudent(id);
        });
        removeStudentButton.addActionListener(e -> {
            registrationFrame.dispose();
            removeStudent(id);
        });
        backButton.addActionListener(e -> {
            registrationFrame.dispose();
            mainMenu(id);
        });
    }

    private static boolean notRegistered(String ic){
        for(Student student : Main.studentList){
            if(student.getIC().equals(ic)){
                JLabel registered = new JLabel("This student has been registered.");
                registered.setFont(new Font("Times New Roman", Font.BOLD, 16));
                JOptionPane.showMessageDialog(null, registered, "Invalid Registration", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        return true;
    }

    private static void registerStudent(String id){
        JFrame registerFrame = getFrame("Student Registration");

        JPanel studentDetailsPanel = new JPanel();
        studentDetailsPanel.setBackground(new Color(230, 230, 250));
        studentDetailsPanel.setLayout(new BoxLayout(studentDetailsPanel, BoxLayout.Y_AXIS));
        studentDetailsPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JLabel nameLabel = new JLabel("Student Name: ");
        JLabel icLabel = new JLabel("Student IC Number: ");
        JLabel emailLabel = new JLabel("Student Email: ");
        JLabel phoneNoLabel = new JLabel("Student Contact Number: ");
        JLabel addressLabel = new JLabel("Student Address: ");
        JLabel levelLabel = new JLabel("Student Level: ");
        String[] levels = {"--- Please Select ---", "F1", "F2", "F3", "F4", "F5"};
        JComboBox<String> levelOptions = new JComboBox<>(levels);
        levelOptions.setFont(new Font("Times New Roman", Font.BOLD, 16));
        levelOptions.setPreferredSize(new Dimension(210, 40));

        nameLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
        icLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
        emailLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
        phoneNoLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
        addressLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));
        levelLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));

        JTextField nameField = new JTextField();
        nameField.setPreferredSize(new Dimension(210, 40));

        JTextField icField = new JTextField();
        icField.setPreferredSize(new Dimension(210, 40));

        JTextField emailField = new JTextField();
        emailField.setPreferredSize(new Dimension(210, 40));

        JTextField phoneNoField = new JTextField();
        phoneNoField.setPreferredSize(new Dimension(210, 40));

        JTextField addressField = new JTextField();
        addressField.setPreferredSize(new Dimension(210, 40));

        JPanel namePanel = new JPanel(new BorderLayout());
        namePanel.setBackground(new Color(230, 230, 250));
        namePanel.add(nameLabel, BorderLayout.WEST);
        namePanel.add(nameField, BorderLayout.EAST);

        JPanel icPanel = new JPanel(new BorderLayout());
        icPanel.setBackground(new Color(230, 230, 250));
        icPanel.add(icLabel, BorderLayout.WEST);
        icPanel.add(icField, BorderLayout.EAST);

        JPanel emailPanel = new JPanel(new BorderLayout());
        emailPanel.setBackground(new Color(230, 230, 250));
        emailPanel.add(emailLabel, BorderLayout.WEST);
        emailPanel.add(emailField, BorderLayout.EAST);

        JPanel phoneNoPanel = new JPanel(new BorderLayout());
        phoneNoPanel.setBackground(new Color(230, 230, 250));
        phoneNoPanel.add(phoneNoLabel, BorderLayout.WEST);
        phoneNoPanel.add(phoneNoField, BorderLayout.EAST);

        JPanel addressPanel = new JPanel(new BorderLayout());
        addressPanel.setBackground(new Color(230, 230, 250));
        addressPanel.add(addressLabel, BorderLayout.WEST);
        addressPanel.add(addressField, BorderLayout.EAST);

        JPanel levelPanel = new JPanel(new BorderLayout());
        levelPanel.setBackground(new Color(230, 230, 250));
        levelPanel.add(levelLabel, BorderLayout.WEST);
        levelPanel.add(levelOptions, BorderLayout.EAST);

        studentDetailsPanel.add(namePanel);
        studentDetailsPanel.add(new JLabel(" "));

        studentDetailsPanel.add(icPanel);
        studentDetailsPanel.add(new JLabel(" "));

        studentDetailsPanel.add(emailPanel);
        studentDetailsPanel.add(new JLabel(" "));

        studentDetailsPanel.add(phoneNoPanel);
        studentDetailsPanel.add(new JLabel(" "));

        studentDetailsPanel.add(addressPanel);
        studentDetailsPanel.add(new JLabel(" "));

        studentDetailsPanel.add(levelPanel);
        studentDetailsPanel.add(new JLabel(" "));

        JScrollPane scrollPane = new JScrollPane(studentDetailsPanel);
        scrollPane.setBackground(new Color(230, 230, 250));

        registerFrame.add(scrollPane, BorderLayout.CENTER);

        JPanel button = new JPanel(new BorderLayout());
        button.setBackground(new Color(230, 230, 250));
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JButton registerButton = new JButton("Register");
        registerButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        registerButton.setPreferredSize(new Dimension(100, 30));

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        backButton.setPreferredSize(new Dimension(75, 30));

        button.add(registerButton, BorderLayout.EAST);
        button.add(backButton, BorderLayout.WEST);

        registerFrame.add(button, BorderLayout.SOUTH);

        registerButton.addActionListener(e -> {
            String newID = CommonMethods.getNextID("S", Main.studentList);
            String name = nameField.getText().strip();
            String ic = icField.getText().strip();
            String email = emailField.getText().strip();
            String phoneNo = phoneNoField.getText().strip();
            String address = addressField.getText().strip();
            String username = "@";
            String password = "#";
            String level = (String) levelOptions.getSelectedItem();

            LocalDate today = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");
            String registerDate = today.format(formatter);

            int noOfSubjects = 0;

            JPanel emptyDetails = new JPanel();
            emptyDetails.setLayout(new BoxLayout(emptyDetails, BoxLayout.Y_AXIS));

            boolean notEmpty = true;
            boolean validDetails = true;

            if(name.isEmpty()){
                notEmpty = false;
                JLabel error = new JLabel("Name cannot be empty.");
                error.setFont(new Font("Times New Roman", Font.BOLD, 16));
                emptyDetails.add(error);
            }
            else if(!CommonMethods.isValidName(name)){
                validDetails = false;
            }

            if(ic.isEmpty()){
                notEmpty = false;
                JLabel error = new JLabel("IC number cannot be empty.");
                error.setFont(new Font("Times New Roman", Font.BOLD, 16));
                emptyDetails.add(error);
            }
            else if(!CommonMethods.isValidIC(ic)){
                validDetails = false;
            }

            if(email.isEmpty()){
                notEmpty = false;
                JLabel error = new JLabel("Email cannot be empty.");
                error.setFont(new Font("Times New Roman", Font.BOLD, 16));
                emptyDetails.add(error);
            }
            else if(!email.contains("@")){
                validDetails = false;
                JLabel invalid = new JLabel("Please insert a valid email.");
                invalid.setFont(new Font("Times New Roman", Font.BOLD, 16));
                JOptionPane.showMessageDialog(null, invalid, "Invalid Email", JOptionPane.ERROR_MESSAGE);
            }

            if(phoneNo.isEmpty()){
                notEmpty = false;
                JLabel error = new JLabel("Contact number cannot be empty.");
                error.setFont(new Font("Times New Roman", Font.BOLD, 16));
                emptyDetails.add(error);
            }
            else if(!CommonMethods.isValidPhoneNo(phoneNo)){
                validDetails = false;
            }

            if(address.isEmpty()){
                notEmpty = false;
                JLabel error = new JLabel("Address cannot be empty.");
                error.setFont(new Font("Times New Roman", Font.BOLD, 16));
                emptyDetails.add(error);
            }
            else if(!CommonMethods.isValidQuotedInput(address)){
                validDetails = false;
            }

            if(level.equals("--- Please Select ---")){
                JLabel invalid = new JLabel("Please select a level.");
                invalid.setFont(new Font("Times New Roman", Font.BOLD, 16));
                JOptionPane.showMessageDialog(null, invalid, "Invalid Option", JOptionPane.ERROR_MESSAGE);
                validDetails = false;
            }

            if(!notEmpty){
                JOptionPane.showMessageDialog(null, emptyDetails, "Invalid Details", JOptionPane.ERROR_MESSAGE);
            }
            else if(validDetails){
                if(notRegistered(ic)) {
                    address = CommonMethods.modifyInput(address);

                    JPanel confirmDetails = new JPanel();
                    confirmDetails.setLayout(new BoxLayout(confirmDetails, BoxLayout.Y_AXIS));

                    JLabel line1 = new JLabel("Are you sure you want to add a student with the following details?");
                    line1.setFont(new Font("Times New Roman", Font.BOLD, 16));

                    JLabel line2 = new JLabel("ID: " + newID);
                    line2.setFont(new Font("Times New Roman", Font.PLAIN, 16));

                    JLabel line3 = new JLabel("Name: " + name);
                    line3.setFont(new Font("Times New Roman", Font.PLAIN, 16));

                    JLabel line4 = new JLabel("IC Number: " + ic);
                    line4.setFont(new Font("Times New Roman", Font.PLAIN, 16));

                    JLabel line5 = new JLabel("Email: " + email);
                    line5.setFont(new Font("Times New Roman", Font.PLAIN, 16));

                    JLabel line6 = new JLabel("Contact Number: " + phoneNo);
                    line6.setFont(new Font("Times New Roman", Font.PLAIN, 16));

                    JLabel line7 = new JLabel("Address: " + address.replace("\"", ""));
                    line7.setFont(new Font("Times New Roman", Font.PLAIN, 16));

                    JLabel line8 = new JLabel("Username: Not Available");
                    line8.setFont(new Font("Times New Roman", Font.PLAIN, 16));

                    JLabel line9 = new JLabel("Level: " + level);
                    line9.setFont(new Font("Times New Roman", Font.PLAIN, 16));

                    JLabel line10 = new JLabel("Register Date: " + registerDate);
                    line10.setFont(new Font("Times New Roman", Font.PLAIN, 16));

                    JLabel line11 = new JLabel("No. of Subjects Taken: " + noOfSubjects);
                    line11.setFont(new Font("Times New Roman", Font.PLAIN, 16));

                    confirmDetails.add(line1);
                    confirmDetails.add(new JLabel(" "));
                    confirmDetails.add(line2);
                    confirmDetails.add(line3);
                    confirmDetails.add(line4);
                    confirmDetails.add(line5);
                    confirmDetails.add(line6);
                    confirmDetails.add(line7);
                    confirmDetails.add(line8);
                    confirmDetails.add(line9);
                    confirmDetails.add(line10);
                    confirmDetails.add(line11);

                    int result = JOptionPane.showConfirmDialog(null, confirmDetails, "Confirm Student Details", JOptionPane.YES_NO_OPTION);
                    if (result == JOptionPane.YES_OPTION) {
                        Student student = new Student(newID, name, ic, email, phoneNo, address, username, password, level,
                                registerDate, noOfSubjects);
                        Main.studentList.add(student);
                        CommonMethods.appendNewLine(Main.filePaths[1], student);
                        registerFrame.dispose();

                        JLabel registered = new JLabel("New student added.");
                        registered.setFont(new Font("Times New Roman", Font.BOLD, 16));
                        JOptionPane.showMessageDialog(null, registered, "Successful Student Registration", JOptionPane.INFORMATION_MESSAGE);
                        studentRegistration(id);
                    }
                }
            }
        });
        backButton.addActionListener(e -> {
            registerFrame.dispose();
            studentRegistration(id);
        });
    }

    private static void removeStudent(String id){
        if(Main.studentList.isEmpty()){
            JLabel error = new JLabel("No students to be removed.");
            error.setFont(new Font("Times New Roman", Font.BOLD, 16));
            JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.ERROR_MESSAGE);
            studentRegistration(id);
        }
        else {
            JFrame removeStudentFrame = getFrame("Student Removal");

            JPanel removeStudentPanel = new JPanel();
            removeStudentPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
            removeStudentPanel.setBackground(new Color(230, 230, 250));
            removeStudentPanel.setLayout(new BoxLayout(removeStudentPanel, BoxLayout.Y_AXIS));

            ArrayList<JCheckBox> checkBoxes = new ArrayList<>();
            for (Student student : Main.studentList) {
                JTextArea studentDetails = new JTextArea();
                studentDetails.setEditable(false);
                studentDetails.setLineWrap(true);
                studentDetails.setWrapStyleWord(true);
                TitledBorder titledBorder = BorderFactory.createTitledBorder("ID: " + student.getID());
                titledBorder.setTitleFont(new Font("Times New Roman", Font.BOLD, 16));
                studentDetails.setBorder(titledBorder);
                studentDetails.setFont(new Font("Times New Roman", Font.PLAIN, 16));

                studentDetails.append("Name: " + student.getName());
                studentDetails.append("\nIC Number: " + student.getIC());
                studentDetails.append("\nEmail: " + student.getEmail());
                studentDetails.append("\nContact Number: " + student.getPhoneNo());
                studentDetails.append("\nAddress: " + student.getAddress().replace("\"", ""));
                studentDetails.append("\nLevel: " + student.getLevel());
                studentDetails.append("\nRegister Date: " + student.getRegisterDate());
                studentDetails.append("\nNumber of Subjects Taken: " + student.getNoOfSubjects());

                JCheckBox checkBox = new JCheckBox();
                checkBoxes.add(checkBox);

                JPanel studentCheckBox = new JPanel();
                studentCheckBox.setBackground(new Color(230, 230, 250));
                studentCheckBox.setLayout(new BoxLayout(studentCheckBox, BoxLayout.X_AXIS));
                studentCheckBox.add(checkBox);
                studentCheckBox.add(studentDetails);

                removeStudentPanel.add(studentCheckBox);
                removeStudentPanel.add(new JLabel(" "));
            }

            JScrollPane scrollPane = new JScrollPane(removeStudentPanel);
            scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
            scrollPane.setBackground(new Color(230, 230, 250));
            removeStudentFrame.add(scrollPane, BorderLayout.CENTER);

            JPanel button = new JPanel(new BorderLayout());
            button.setBackground(new Color(230, 230, 250));
            button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

            JButton removeButton = new JButton("Remove");
            removeButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
            removeButton.setPreferredSize(new Dimension(85, 30));

            JButton backButton = new JButton("Back");
            backButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
            backButton.setPreferredSize(new Dimension(75, 30));

            button.add(removeButton, BorderLayout.EAST);
            button.add(backButton, BorderLayout.WEST);

            removeStudentFrame.add(button, BorderLayout.SOUTH);

            removeButton.addActionListener(e -> {
                JLabel confirm = new JLabel("Are you sure you want to remove these students?");
                confirm.setFont(new Font("Times New Roman", Font.BOLD, 16));
                int result = JOptionPane.showConfirmDialog(null, confirm, "Confirm Student Removal", JOptionPane.YES_NO_OPTION);

                if (result == JOptionPane.YES_OPTION) {
                    ArrayList<Student> studentsToRemove = new ArrayList<>();
                    for (int i = 0; i < checkBoxes.size(); i++) {
                        if (checkBoxes.get(i).isSelected()) {
                            studentsToRemove.add(Main.studentList.get(i));
                        }
                    }

                    if (studentsToRemove.isEmpty()) {
                        JLabel invalid = new JLabel("Please select at least one student.");
                        invalid.setFont(new Font("Times New Roman", Font.BOLD, 16));
                        JOptionPane.showMessageDialog(null, invalid, "Invalid Selection", JOptionPane.ERROR_MESSAGE);
                    }
                    else {
                        for (Student student : studentsToRemove) {
                            Main.studentList.remove(student);
                            Main.requestList.removeIf(request -> request.getStudentID().equals(student.getID()) && request.getStatus().equals("P"));

                            for (Subject subject : Main.subjectList) {
                                ArrayList<Enrolment> enrolmentList = CommonMethods.loadEnrolment(subject.getID());

                                boolean removed = Main.requestList.removeIf(request -> request.getStudentID().equals(student.getID()) && request.getStatus().equals("P"));
                                if (removed) {
                                    CommonMethods.writeFile("txtTCMS/enr" + subject.getID() + ".txt", enrolmentList);
                                }
                            }
                        }

                        CommonMethods.writeFile(Main.filePaths[1], Main.studentList);
                        CommonMethods.writeFile(Main.filePaths[5], Main.requestList);
                        removeStudentFrame.dispose();
                        JLabel removed = new JLabel("Successfully removed.");
                        removed.setFont(new Font("Times New Roman", Font.BOLD, 16));
                        JOptionPane.showMessageDialog(null, removed, "Successful Student Removal", JOptionPane.INFORMATION_MESSAGE);
                        removeStudent(id);
                    }
                }
            });

            backButton.addActionListener(e -> {
                removeStudentFrame.dispose();
                studentRegistration(id);
            });
        }
    }

    private static void subjectEnrolment(String id){
        JFrame enrolmentFrame = getFrame("Subject Enrolment");

        JLabel title = new JLabel("Subject Enrolment", SwingConstants.CENTER);
        title.setFont(new Font("Times New Roman", Font.BOLD, 20));
        title.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        enrolmentFrame.add(title, BorderLayout.NORTH);

        JPanel enrolmentPanel = new JPanel(new GridLayout(4, 1));
        enrolmentPanel.setBackground(new Color(230, 230, 250));
        enrolmentPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JButton enrolStudentButton = new JButton("1. Enrol Students In Subject");
        JButton removeStudentButton = new JButton("2. Remove Students From Subject");
        JButton changeRequestButton = new JButton("3. Change Requests From Students");
        JButton backButton = new JButton("4. Back to Main Menu");

        enrolStudentButton.setFont(new Font("Times New Roman", Font.BOLD, 18));
        removeStudentButton.setFont(new Font("Times New Roman", Font.BOLD, 18));
        changeRequestButton.setFont(new Font("Times New Roman", Font.BOLD, 18));
        backButton.setFont(new Font("Times New Roman", Font.BOLD, 18));

        enrolStudentButton.setBackground(new Color(220, 197, 232));
        removeStudentButton.setBackground(new Color(237, 226, 244));
        changeRequestButton.setBackground(new Color(220, 197, 232));
        backButton.setBackground(new Color(237, 226, 244));

        enrolmentPanel.add(enrolStudentButton);
        enrolmentPanel.add(removeStudentButton);
        enrolmentPanel.add(changeRequestButton);
        enrolmentPanel.add(backButton);

        enrolmentFrame.add(enrolmentPanel, BorderLayout.CENTER);

        enrolStudentButton.addActionListener(e -> {
            enrolmentFrame.dispose();
            enrolStudent(id);

        });
        removeStudentButton.addActionListener(e -> {
            enrolmentFrame.dispose();
            removeEnrolment(id);
        });
        changeRequestButton.addActionListener(e -> {
            enrolmentFrame.dispose();
            changeRequest(id);
        });
        backButton.addActionListener(e -> {
            enrolmentFrame.dispose();
            mainMenu(id);
        });
    }

    private static void enrolStudent(String id){
        JFrame enrolStudentFrame = getFrame("Subject Enrolment");

        JPanel subjectIDPanel = new JPanel(new BorderLayout());
        subjectIDPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        subjectIDPanel.setBackground(new Color(230, 230, 250));
        JLabel subjectIDLabel = new JLabel("Subject: ");
        subjectIDLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));

        ArrayList<String> subjects = new ArrayList<>();
        subjects.add("--- Please Select ---");
        for(Subject subject : Main.subjectList){
            subjects.add(subject.getID() + " " + subject.getTitle());
        }
        String[] options = subjects.toArray(new String[0]);
        JComboBox<String> subjectOptions = new JComboBox<>(options);
        subjectOptions.setFont(new Font("Times New Roman", Font.BOLD, 18));
        subjectOptions.setPreferredSize(new Dimension(250, 30));

        subjectIDPanel.add(subjectIDLabel, BorderLayout.WEST);
        subjectIDPanel.add(subjectOptions, BorderLayout.EAST);

        JPanel adjustPanel = new JPanel();
        adjustPanel.setBackground(new Color(230, 230, 250));
        adjustPanel.setLayout(new BoxLayout(adjustPanel, BoxLayout.Y_AXIS));
        adjustPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        adjustPanel.add(new JLabel(" "));
        adjustPanel.add(subjectIDPanel);

        enrolStudentFrame.add(adjustPanel, BorderLayout.NORTH);

        JPanel button = new JPanel(new BorderLayout());
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        button.setBackground(new Color(230, 230, 250));

        JButton nextButton = new JButton("Next");
        nextButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        nextButton.setPreferredSize(new Dimension(75, 30));

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        backButton.setPreferredSize(new Dimension(75, 30));

        button.add(nextButton, BorderLayout.EAST);
        button.add(backButton, BorderLayout.WEST);

        enrolStudentFrame.add(button, BorderLayout.SOUTH);

        nextButton.addActionListener(e -> {
            String selected = (String) subjectOptions.getSelectedItem();
            if(selected.equals("--- Please Select ---")){
                JLabel invalid = new JLabel("Please select a subject.");
                invalid.setFont(new Font("Times New Roman", Font.BOLD, 16));
                JOptionPane.showMessageDialog(null, invalid, "Invalid Selection", JOptionPane.ERROR_MESSAGE);
            }
            else {
                String subjectID = "";
                for (int i = 0; i < options.length; i++) {
                    if (options[i].equals(selected)) {
                        subjectID = Main.subjectList.get(i - 1).getID();
                    }
                }
                enrolStudentFrame.dispose();
                enrolStudent(id, subjectID);
            }
        });

        backButton.addActionListener(e -> {
            enrolStudentFrame.dispose();
            subjectEnrolment(id);
        });
    }

    private static void enrolStudent(String id, String subjectID){

        ArrayList<Student> availableStudents = new ArrayList<>();
        for(Student student : Main.studentList){
            if(!CommonMethods.isEnrolled(subjectID, student.getID())){
                if(student.getNoOfSubjects() < 3){
                    availableStudents.add(student);
                }
            }
        }

        if(availableStudents.isEmpty()){
            JLabel invalid = new JLabel("All students are enrolled in this subject or have reached the maximum number of subject enrolments.");
            invalid.setFont(new Font("Times New Roman", Font.BOLD, 16));
            JOptionPane.showMessageDialog(null, invalid, "No Qualified Students", JOptionPane.ERROR_MESSAGE);
            enrolStudent(id);
        }
        else {
            JFrame chooseStudentFrame = getFrame("Select Students to Enrol");

            JLabel availableStudentsLabel = new JLabel("Available Students:");
            availableStudentsLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));
            availableStudentsLabel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
            chooseStudentFrame.add(availableStudentsLabel, BorderLayout.NORTH);

            JPanel enrolStudentPanel = new JPanel();
            enrolStudentPanel.setBackground(new Color(230, 230, 250));
            enrolStudentPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
            enrolStudentPanel.setLayout(new BoxLayout(enrolStudentPanel, BoxLayout.Y_AXIS));

            ArrayList<JCheckBox> checkBoxes = new ArrayList<>();
            int count = 0;
            for (Student student : availableStudents) {
                count++;
                JTextArea studentDetails = new JTextArea();
                studentDetails.setFont(new Font("Times New Roman", Font.PLAIN, 16));
                studentDetails.setEditable(false);
                studentDetails.setLineWrap(true);
                studentDetails.setWrapStyleWord(true);
                TitledBorder titledBorder = BorderFactory.createTitledBorder("Student " + count);
                titledBorder.setTitleFont(new Font("Times New Roman", Font.BOLD, 16));
                studentDetails.setBorder(titledBorder);

                studentDetails.append("   ID: " + student.getID());
                studentDetails.append(" ");
                studentDetails.append("\n   Name: " + student.getName());

                JCheckBox checkBox = new JCheckBox();
                checkBoxes.add(checkBox);

                JPanel studentCheckBox = new JPanel();
                studentCheckBox.setBackground(new Color(230, 230, 250));
                studentCheckBox.setLayout(new BoxLayout(studentCheckBox, BoxLayout.X_AXIS));
                studentCheckBox.add(checkBox);
                studentCheckBox.add(studentDetails);

                enrolStudentPanel.add(studentCheckBox);
                enrolStudentPanel.add(new JLabel(" "));
            }

            JScrollPane scrollPane = new JScrollPane(enrolStudentPanel);
            scrollPane.setBackground(new Color(230, 230, 250));
            scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
            chooseStudentFrame.add(scrollPane, BorderLayout.CENTER);

            JPanel button = new JPanel(new BorderLayout());
            button.setBackground(new Color(230, 230, 250));
            button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

            JButton enrolButton = new JButton("Enrol");
            enrolButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
            enrolButton.setPreferredSize(new Dimension(85, 30));

            JButton backButton = new JButton("Back");
            backButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
            backButton.setPreferredSize(new Dimension(75, 30));

            button.add(enrolButton, BorderLayout.EAST);
            button.add(backButton, BorderLayout.WEST);

            chooseStudentFrame.add(button, BorderLayout.SOUTH);

            enrolButton.addActionListener(e -> {
                JLabel confirm = new JLabel("Are you sure you want to enrol these students into subject " + subjectID + "?");
                confirm.setFont(new Font("Times New Roman", Font.BOLD, 16));
                int result = JOptionPane.showConfirmDialog(null, confirm, "Confirm Student Enrolment", JOptionPane.YES_NO_OPTION);

                if (result == JOptionPane.YES_OPTION) {
                    ArrayList<Student> chosenStudents = new ArrayList<>();
                    for (int i = 0; i < checkBoxes.size(); i++) {
                        if (checkBoxes.get(i).isSelected()) {
                            chosenStudents.add(availableStudents.get(i));
                        }
                    }

                    if (chosenStudents.isEmpty()) {
                        JLabel invalid = new JLabel("Please select at least one student.");
                        invalid.setFont(new Font("Times New Roman", Font.BOLD, 16));
                        JOptionPane.showMessageDialog(null, invalid, "Invalid Selection", JOptionPane.ERROR_MESSAGE);
                    }
                    else {
                        ArrayList<Enrolment> enrolmentList = CommonMethods.loadEnrolment(subjectID);

                        for (Student student : chosenStudents) {
                            String enrolID = CommonMethods.getNextID("ENR", enrolmentList);
                            String studentID = student.getID();
                            String level = student.getLevel();

                            LocalDate today = LocalDate.now();
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");
                            String date = today.format(formatter);

                            student.setNoOfSubjects(student.getNoOfSubjects() + 1);
                            Enrolment enrolment = new Enrolment(enrolID, studentID, level, date);
                            enrolmentList.add(enrolment);
                            CommonMethods.appendNewLine("txtTCMS/enr" + subjectID + ".txt", enrolment);
                        }
                        CommonMethods.writeFile(Main.filePaths[1], Main.studentList);
                        chooseStudentFrame.dispose();
                        JLabel enrolled = new JLabel("Successfully enrolled.");
                        enrolled.setFont(new Font("Times New Roman", Font.BOLD, 16));
                        JOptionPane.showMessageDialog(null, enrolled, "Successful Enrolment", JOptionPane.INFORMATION_MESSAGE);
                        enrolStudent(id, subjectID);
                    }

                }
            });
            backButton.addActionListener(e -> {
                chooseStudentFrame.dispose();
                enrolStudent(id);
            });
        }
    }

    private static void removeEnrolment(String id){
        JFrame removeStudentFrame = getFrame("Subject Withdrawal");

        JPanel subjectIDPanel = new JPanel(new BorderLayout());
        subjectIDPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        subjectIDPanel.setBackground(new Color(230, 230, 250));
        JLabel subjectIDLabel = new JLabel("Subject: ");
        subjectIDLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));

        ArrayList<String> subjects = new ArrayList<>();
        subjects.add("--- Please Select ---");
        for(Subject subject : Main.subjectList){
            subjects.add(subject.getID() + " " + subject.getTitle());
        }
        String[] options = subjects.toArray(new String[0]);
        JComboBox<String> subjectOptions = new JComboBox<>(options);
        subjectOptions.setFont(new Font("Times New Roman", Font.BOLD, 18));
        subjectOptions.setPreferredSize(new Dimension(250, 30));

        subjectIDPanel.add(subjectIDLabel, BorderLayout.WEST);
        subjectIDPanel.add(subjectOptions, BorderLayout.EAST);

        JPanel adjustPanel = new JPanel();
        adjustPanel.setBackground(new Color(230, 230, 250));
        adjustPanel.setLayout(new BoxLayout(adjustPanel, BoxLayout.Y_AXIS));
        adjustPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        adjustPanel.add(new JLabel(" "));
        adjustPanel.add(subjectIDPanel);

        removeStudentFrame.add(adjustPanel, BorderLayout.NORTH);

        JPanel button = new JPanel(new BorderLayout());
        button.setBackground(new Color(230, 230, 250));
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JButton nextButton = new JButton("Next");
        nextButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        nextButton.setPreferredSize(new Dimension(85, 30));

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        backButton.setPreferredSize(new Dimension(75, 30));

        button.add(nextButton, BorderLayout.EAST);
        button.add(backButton, BorderLayout.WEST);

        removeStudentFrame.add(button, BorderLayout.SOUTH);

        nextButton.addActionListener(e -> {
            String selected = (String) subjectOptions.getSelectedItem();
            if(selected.equals("--- Please Select ---")){
                JLabel invalid = new JLabel("Please select a subject.");
                invalid.setFont(new Font("Times New Roman", Font.BOLD, 16));
                JOptionPane.showMessageDialog(null, invalid, "Invalid Selection", JOptionPane.ERROR_MESSAGE);
            }
            else {
                String subjectID = "";
                for (int i = 0; i < options.length; i++) {
                    if (options[i].equals(selected)) {
                        subjectID = Main.subjectList.get(i - 1).getID();
                    }
                }
                removeStudentFrame.dispose();
                removeEnrolment(id, subjectID);
            }
        });

        backButton.addActionListener(e -> {
            removeStudentFrame.dispose();
            subjectEnrolment(id);
        });
    }

    private static void removeEnrolment(String id, String subjectID){

        ArrayList<Student> availableStudents = new ArrayList<>();
        for(Student student : Main.studentList){
            if(CommonMethods.isEnrolled(subjectID, student.getID())){
                availableStudents.add(student);
            }
        }

        if(availableStudents.isEmpty()){
            JLabel invalid = new JLabel("No students to be removed.");
            invalid.setFont(new Font("Times New Roman", Font.BOLD, 16));
            JOptionPane.showMessageDialog(null, invalid, "No Students", JOptionPane.ERROR_MESSAGE);
            removeEnrolment(id);
        }
        else {
            JFrame chooseStudentFrame = getFrame("Select Students to Remove");

            JLabel availableStudentsLabel = new JLabel("Available Students:");
            availableStudentsLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));
            availableStudentsLabel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
            chooseStudentFrame.add(availableStudentsLabel, BorderLayout.NORTH);

            JPanel removeStudentPanel = new JPanel();
            removeStudentPanel.setBackground(new Color(230, 230, 250));
            removeStudentPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
            removeStudentPanel.setLayout(new BoxLayout(removeStudentPanel, BoxLayout.Y_AXIS));

            ArrayList<JCheckBox> checkBoxes = new ArrayList<>();
            int count = 0;
            for (Student student : availableStudents) {
                count++;
                JTextArea studentDetails = new JTextArea();
                studentDetails.setFont(new Font("Times New Roman", Font.PLAIN, 16));
                studentDetails.setEditable(false);
                studentDetails.setLineWrap(true);
                studentDetails.setWrapStyleWord(true);
                TitledBorder titledBorder = BorderFactory.createTitledBorder("Student " + count);
                titledBorder.setTitleFont(new Font("Times New Roman", Font.BOLD, 16));
                studentDetails.setBorder(titledBorder);

                studentDetails.append("   ID: " + student.getID());
                studentDetails.append(" ");
                studentDetails.append("\n   Name: " + student.getName());

                JCheckBox checkBox = new JCheckBox();
                checkBoxes.add(checkBox);

                JPanel studentCheckBox = new JPanel();
                studentCheckBox.setBackground(new Color(230, 230, 250));
                studentCheckBox.setLayout(new BoxLayout(studentCheckBox, BoxLayout.X_AXIS));
                studentCheckBox.add(checkBox);
                studentCheckBox.add(studentDetails);

                removeStudentPanel.add(studentCheckBox);
                removeStudentPanel.add(new JLabel(" "));
            }

            JScrollPane scrollPane = new JScrollPane(removeStudentPanel);
            scrollPane.setBackground(new Color(230, 230, 250));
            scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
            chooseStudentFrame.add(scrollPane, BorderLayout.CENTER);

            JPanel button = new JPanel(new BorderLayout());
            button.setBackground(new Color(230, 230, 250));
            button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

            JButton removeButton = new JButton("Remove");
            removeButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
            removeButton.setPreferredSize(new Dimension(85, 30));

            JButton backButton = new JButton("Back");
            backButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
            backButton.setPreferredSize(new Dimension(75, 30));

            button.add(removeButton, BorderLayout.EAST);
            button.add(backButton, BorderLayout.WEST);

            chooseStudentFrame.add(button, BorderLayout.SOUTH);

            removeButton.addActionListener(e -> {
                JLabel confirm = new JLabel("Are you sure you want to remove these students from subject " + subjectID + "?");
                confirm.setFont(new Font("Times New Roman", Font.BOLD, 16));
                int result = JOptionPane.showConfirmDialog(null, confirm, "Confirm Student Removal", JOptionPane.YES_NO_OPTION);

                if (result == JOptionPane.YES_OPTION) {
                    boolean selected = false;
                    ArrayList<Student> chosenStudents = new ArrayList<>();
                    for (int i = 0; i < checkBoxes.size(); i++) {
                        if (checkBoxes.get(i).isSelected()) {
                            selected = true;
                            chosenStudents.add(availableStudents.get(i));
                        }
                    }
                    if (!selected) {
                        JLabel invalid = new JLabel("Please select at least one student.");
                        invalid.setFont(new Font("Times New Roman", Font.BOLD, 16));
                        JOptionPane.showMessageDialog(null, invalid, "Invalid Selection", JOptionPane.ERROR_MESSAGE);
                    } else {
                        ArrayList<Enrolment> enrolmentList = CommonMethods.loadEnrolment(subjectID);

                        for (Student student : chosenStudents) {
                            enrolmentList.removeIf(enrolment -> enrolment.getStudentID().equals(student.getID()));
                            student.setNoOfSubjects(student.getNoOfSubjects() - 1);
                        }
                        CommonMethods.writeFile("txtTCMS/enr" + subjectID + ".txt", enrolmentList);
                        CommonMethods.writeFile(Main.filePaths[1], Main.studentList);
                        chooseStudentFrame.dispose();

                        JLabel removed = new JLabel("Successfully removed.");
                        removed.setFont(new Font("Times New Roman", Font.BOLD, 16));
                        JOptionPane.showMessageDialog(null, removed, "Successful Removal", JOptionPane.INFORMATION_MESSAGE);
                        removeEnrolment(id, subjectID);
                    }
                }
            });
            backButton.addActionListener(e -> {
                chooseStudentFrame.dispose();
                removeEnrolment(id);
            });
        }
    }

    private static void changeRequest(String id){
        JFrame changeRequestFrame = getFrame("Subject Change Requests");

        JLabel title = new JLabel("Subject Change Requests", SwingConstants.CENTER);
        title.setFont(new Font("Times New Roman", Font.BOLD, 20));
        title.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        changeRequestFrame.add(title, BorderLayout.NORTH);

        JPanel optionsPanel = new JPanel(new GridLayout(5, 1));
        optionsPanel.setBackground(new Color(230, 230, 250));
        optionsPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JButton enrolmentButton = new JButton("1. Pending Enrolment Requests");
        JButton withdrawButton = new JButton("2. Pending Withdrawal Requests");
        JButton changeButton = new JButton("3. Pending Change Subject Requests");
        JButton historyButton = new JButton("4. View Request History");
        JButton backButton = new JButton("5. Back to Subject Enrolment Menu");

        enrolmentButton.setFont(new Font("Times New Roman", Font.BOLD, 18));
        withdrawButton.setFont(new Font("Times New Roman", Font.BOLD, 18));
        changeButton.setFont(new Font("Times New Roman", Font.BOLD, 18));
        historyButton.setFont(new Font("Times New Roman", Font.BOLD, 18));
        backButton.setFont(new Font("Times New Roman", Font.BOLD, 18));

        enrolmentButton.setBackground(new Color(220, 197, 232)	);
        withdrawButton.setBackground(new Color(237, 226, 244));
        changeButton.setBackground(new Color(220, 197, 232)	);
        historyButton.setBackground(new Color(237, 226, 244));
        backButton.setBackground(new Color(220, 197, 232)	);

        optionsPanel.add(enrolmentButton);
        optionsPanel.add(withdrawButton);
        optionsPanel.add(changeButton);
        optionsPanel.add(historyButton);
        optionsPanel.add(backButton);

        changeRequestFrame.add(optionsPanel, BorderLayout.CENTER);

        enrolmentButton.addActionListener(e -> {
            changeRequestFrame.dispose();
            enrolRequests(id);
        });
        withdrawButton.addActionListener(e -> {
            changeRequestFrame.dispose();
            withdrawRequests(id);
        });
        changeButton.addActionListener(e -> {
            changeRequestFrame.dispose();
            transferRequests(id);
        });
        historyButton.addActionListener(e -> {
            changeRequestFrame.dispose();
            requestHistory(id);
        });
        backButton.addActionListener(e -> {
            changeRequestFrame.dispose();
            subjectEnrolment(id);
        });
    }

    private static void enrolRequests(String id){
        JFrame enrolRequestFrame = getFrame("Pending Enrolment Requests");

        JPanel requestPanel = new JPanel();
        requestPanel.setBackground(new Color(230, 230, 250));
        requestPanel.setLayout(new BoxLayout(requestPanel, BoxLayout.Y_AXIS));
        requestPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        int count = 0;
        boolean emptyRequest = true;
        for(ChangeRequest request : Main.requestList){
            if(request.getTypeOfRequest().equals("E") && request.getStatus().equals("P") && request.getActivity().equals("A")){
                emptyRequest = false;
                count++;

                JTextArea requestDetails = new JTextArea();
                requestDetails.setFont(new Font("Times New Roman", Font.PLAIN, 16));
                requestDetails.setEditable(false);
                requestDetails.setLineWrap(true);
                requestDetails.setWrapStyleWord(true);
                TitledBorder titledBorder = BorderFactory.createTitledBorder("Request " + count);
                titledBorder.setTitleFont(new Font("Times New Roman", Font.BOLD, 16));
                requestDetails.setBorder(titledBorder);

                requestDetails.append("   Request ID   : " + request.getID());
                requestDetails.append("\n   Student ID   : " + request.getStudentID());
                requestDetails.append("\n   Student Name : " + getStudentName(request.getStudentID()));
                requestDetails.append("\n   Subject      : " + CommonMethods.getSubjectTitle(request.getNewSubjectID()));
                requestDetails.append(" ");
                requestPanel.add(requestDetails);

                JButton approveButton = new JButton("Approve");
                approveButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
                approveButton.setPreferredSize(new Dimension(95, 30));
                approveButton.addActionListener(a -> {
                    for(Student student : Main.studentList) {
                        if (student.getID().equals(request.getStudentID())) {
                            if (CommonMethods.isEnrolled(request.getNewSubjectID(), request.getStudentID())) {
                                JLabel enrolled = new JLabel("This student has been enrolled in this subject.");
                                enrolled.setFont(new Font("Times New Roman", Font.BOLD, 16));
                                JOptionPane.showMessageDialog(null, enrolled, "Invalid Enrolment", JOptionPane.ERROR_MESSAGE);
                            }
                            else if (student.getNoOfSubjects() == 3) {
                                JLabel invalid = new JLabel("You have reached the maximum number of subject enrolments of this student.");
                                invalid.setFont(new Font("Times New Roman", Font.BOLD, 16));
                                JOptionPane.showMessageDialog(null, invalid, "Maximum Subject Enrolment", JOptionPane.ERROR_MESSAGE);
                                break;
                            }
                            else {
                                JLabel confirm = new JLabel("Are you sure you want to approve this request?");
                                confirm.setFont(new Font("Times New Roman", Font.BOLD, 16));
                                int result = JOptionPane.showConfirmDialog(null, confirm, "Confirm Approval", JOptionPane.YES_NO_OPTION);

                                if(result == JOptionPane.YES_OPTION){
                                    ArrayList<Enrolment> enrolmentList = CommonMethods.loadEnrolment(request.getNewSubjectID());
                                    String enrolID = CommonMethods.getNextID("ENR", enrolmentList);
                                    String level = "";

                                    for (Student s : Main.studentList) {
                                        if (s.getID().equals(request.getStudentID())) {
                                            level = s.getLevel();
                                            s.setNoOfSubjects(s.getNoOfSubjects() + 1);
                                            break;
                                        }
                                    }
                                    LocalDate today = LocalDate.now();
                                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");
                                    String date = today.format(formatter);

                                    Enrolment enrolment = new Enrolment(enrolID, request.getStudentID(), level, date);
                                    enrolmentList.add(enrolment);
                                    CommonMethods.appendNewLine("txtTCMS/enr" + request.getNewSubjectID() + ".txt", enrolment);
                                    CommonMethods.writeFile(Main.filePaths[1], Main.studentList);
                                    request.setStatus("A");
                                    CommonMethods.writeFile(Main.filePaths[5], Main.requestList);
                                    enrolRequestFrame.dispose();

                                    JLabel enrolled = new JLabel("Successfully enrolled.");
                                    enrolled.setFont(new Font("Times New Roman", Font.BOLD, 16));
                                    JOptionPane.showMessageDialog(null, enrolled, "Successful Enrolment", JOptionPane.INFORMATION_MESSAGE);
                                    enrolRequests(id);
                                }
                            }
                            break;
                        }
                    }

                });

                JButton rejectButton = new JButton("Reject");
                rejectButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
                rejectButton.setPreferredSize(new Dimension(75, 30));
                rejectButton.addActionListener(r -> {
                    JLabel confirm = new JLabel("Are you sure you want to reject this request?");
                    confirm.setFont(new Font("Times New Roman", Font.BOLD, 16));
                    int result = JOptionPane.showConfirmDialog(null, confirm, "Confirm Rejection", JOptionPane.YES_NO_OPTION);

                    if(result == JOptionPane.YES_OPTION){
                        request.setStatus("R");
                        CommonMethods.writeFile(Main.filePaths[5], Main.requestList);
                        enrolRequestFrame.dispose();

                        JLabel rejected = new JLabel("Successfully rejected.");
                        rejected.setFont(new Font("Times New Roman", Font.BOLD, 16));
                        JOptionPane.showMessageDialog(null, rejected, "Successful Rejection", JOptionPane.INFORMATION_MESSAGE);
                        enrolRequests(id);
                    }
                });

                JPanel button = new JPanel(new BorderLayout());
                button.setBackground(new Color(230, 230, 250));
                button.add(approveButton, BorderLayout.EAST);
                button.add(rejectButton, BorderLayout.WEST);

                requestPanel.add(button);

                requestPanel.add(new JLabel(" "));
            }

        }

        if(emptyRequest){
            enrolRequestFrame.dispose();
            JLabel noRequests = new JLabel("No pending enrolment requests.");
            noRequests.setFont(new Font("Times New Roman", Font.BOLD, 16));
            JOptionPane.showMessageDialog(null, noRequests, "No Pending Request", JOptionPane.ERROR_MESSAGE);
            changeRequest(id);
        }

        JPanel backPanel = new JPanel(new BorderLayout());
        backPanel.setBackground(new Color(230, 230, 250));

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        backButton.setPreferredSize(new Dimension(75, 30));

        backButton.addActionListener(b -> {
            enrolRequestFrame.dispose();
            changeRequest(id);
        });

        backPanel.add(backButton, BorderLayout.WEST);
        backPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        enrolRequestFrame.add(backPanel, BorderLayout.SOUTH);

        JScrollPane scrollPane = new JScrollPane(requestPanel);
        scrollPane.setBackground(new Color(230, 230, 250));
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        enrolRequestFrame.add(scrollPane, BorderLayout.CENTER);
    }

    private static String getStudentName(String studentID){
        for(Student student : Main.studentList){
            if(student.getID().equals(studentID)){
                return student.getName();
            }
        }
        return "";
    }

    private static void withdrawRequests(String id){
        JFrame withdrawRequestFrame = getFrame("Pending Withdrawal Requests");

        JPanel requestPanel = new JPanel();
        requestPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        requestPanel.setBackground(new Color(230, 230, 250));
        requestPanel.setLayout(new BoxLayout(requestPanel, BoxLayout.Y_AXIS));

        int count = 0;
        boolean emptyRequest = true;
        for(ChangeRequest request : Main.requestList){
            if(request.getTypeOfRequest().equals("W") && request.getStatus().equals("P") && request.getActivity().equals("A")){
                emptyRequest = false;
                count++;

                JTextArea requestDetails = new JTextArea();
                requestDetails.setFont(new Font("Times New Roman", Font.PLAIN, 16));
                requestDetails.setEditable(false);
                requestDetails.setLineWrap(true);
                requestDetails.setWrapStyleWord(true);
                TitledBorder titledBorder = BorderFactory.createTitledBorder("Request " + count);
                titledBorder.setTitleFont(new Font("Times New Roman", Font.BOLD, 16));
                requestDetails.setBorder(titledBorder);

                requestDetails.append("   Request ID   : " + request.getID());
                requestDetails.append("\n   Student ID   : " + request.getStudentID());
                requestDetails.append("\n   Student Name : " + getStudentName(request.getStudentID()));
                requestDetails.append("\n   Subject      : " + CommonMethods.getSubjectTitle(request.getOldSubjectID()));
                requestDetails.append(" ");
                requestPanel.add(requestDetails);

                JButton approveButton = new JButton("Approve");
                approveButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
                approveButton.setPreferredSize(new Dimension(95, 30));
                approveButton.addActionListener(a -> {
                    for(Student student : Main.studentList) {
                        if (student.getID().equals(request.getStudentID())) {

                            if (!CommonMethods.isEnrolled(request.getOldSubjectID(), request.getStudentID())) {
                                JLabel notEnrolled = new JLabel("This student has not been enrolled in this subject.");
                                notEnrolled.setFont(new Font("Times New Roman", Font.BOLD, 16));
                                JOptionPane.showMessageDialog(null, notEnrolled, "Invalid Withdrawal", JOptionPane.ERROR_MESSAGE);
                            }
                            else {
                                JLabel confirm = new JLabel("Are you sure you want to approve this request?");
                                confirm.setFont(new Font("Times New Roman", Font.BOLD, 16));
                                int result = JOptionPane.showConfirmDialog(null, confirm, "Confirm Approval", JOptionPane.YES_NO_OPTION);

                                if(result == JOptionPane.YES_OPTION){

                                    for (Student s : Main.studentList) {
                                        if (s.getID().equals(request.getStudentID())) {
                                            s.setNoOfSubjects(s.getNoOfSubjects() - 1);
                                            break;
                                        }
                                    }

                                    ArrayList<Enrolment> enrolmentList = CommonMethods.loadEnrolment(request.getOldSubjectID());
                                    enrolmentList.removeIf(enrolment -> enrolment.getStudentID().equals(request.getStudentID()));

                                    CommonMethods.writeFile("txtTCMS/enr" + request.getOldSubjectID() + ".txt", enrolmentList);
                                    CommonMethods.writeFile(Main.filePaths[1], Main.studentList);
                                    request.setStatus("A");
                                    CommonMethods.writeFile(Main.filePaths[5], Main.requestList);
                                    withdrawRequestFrame.dispose();

                                    JLabel removed = new JLabel("Successfully withdrawn.");
                                    removed.setFont(new Font("Times New Roman", Font.BOLD, 16));
                                    JOptionPane.showMessageDialog(null, removed, "Successful Withdrawal", JOptionPane.INFORMATION_MESSAGE);
                                    withdrawRequests(id);
                                }
                            }
                            break;
                        }
                    }

                });

                JButton rejectButton = new JButton("Reject");
                rejectButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
                rejectButton.setPreferredSize(new Dimension(75, 30));
                rejectButton.addActionListener(r -> {
                    JLabel confirm = new JLabel("Are you sure you want to reject this request?");
                    confirm.setFont(new Font("Times New Roman", Font.BOLD, 16));
                    int result = JOptionPane.showConfirmDialog(null, confirm, "Confirm Rejection", JOptionPane.YES_NO_OPTION);

                    if(result == JOptionPane.YES_OPTION){
                        request.setStatus("R");
                        CommonMethods.writeFile(Main.filePaths[5], Main.requestList);
                        withdrawRequestFrame.dispose();

                        JLabel rejected = new JLabel("Successfully rejected.");
                        rejected.setFont(new Font("Times New Roman", Font.BOLD, 16));
                        JOptionPane.showMessageDialog(null, rejected, "Successful Rejection", JOptionPane.INFORMATION_MESSAGE);
                        withdrawRequests(id);
                    }
                });

                JPanel button = new JPanel(new BorderLayout());
                button.setBackground(new Color(230, 230, 250));
                button.add(approveButton, BorderLayout.EAST);
                button.add(rejectButton, BorderLayout.WEST);

                requestPanel.add(button);

                requestPanel.add(new JLabel(" "));
            }

        }

        if(emptyRequest){
            withdrawRequestFrame.dispose();
            JLabel noRequests = new JLabel("No pending withdrawal requests.");
            noRequests.setFont(new Font("Times New Roman", Font.BOLD, 16));
            JOptionPane.showMessageDialog(null, noRequests, "No Pending Request", JOptionPane.ERROR_MESSAGE);
            changeRequest(id);
        }

        JPanel backPanel = new JPanel(new BorderLayout());
        backPanel.setBackground(new Color(230, 230, 250));

        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(75, 30));
        backButton.setFont(new Font("Times New Roman", Font.BOLD, 16));

        backButton.addActionListener(b -> {
            withdrawRequestFrame.dispose();
            changeRequest(id);
        });

        backPanel.add(backButton, BorderLayout.WEST);
        backPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        withdrawRequestFrame.add(backPanel, BorderLayout.SOUTH);

        JScrollPane scrollPane = new JScrollPane(requestPanel);
        scrollPane.setBackground(new Color(230, 230, 250));
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        withdrawRequestFrame.add(scrollPane, BorderLayout.CENTER);
    }

    private static void transferRequests(String id){
        JFrame transferRequestFrame = getFrame("Pending Change Subject Requests");

        JPanel requestPanel = new JPanel();
        requestPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        requestPanel.setBackground(new Color(230, 230, 250));
        requestPanel.setLayout(new BoxLayout(requestPanel, BoxLayout.Y_AXIS));

        int count = 0;
        boolean emptyRequest = true;
        for(ChangeRequest request : Main.requestList){
            if(request.getTypeOfRequest().equals("C") && request.getStatus().equals("P") && request.getActivity().equals("A")){
                emptyRequest = false;
                count++;

                JTextArea requestDetails = new JTextArea();
                requestDetails.setFont(new Font("Times New Roman", Font.PLAIN, 16));
                requestDetails.setEditable(false);
                requestDetails.setLineWrap(true);
                requestDetails.setWrapStyleWord(true);
                TitledBorder titledBorder = BorderFactory.createTitledBorder("Request " + count);
                titledBorder.setTitleFont(new Font("Times New Roman", Font.BOLD, 16));
                requestDetails.setBorder(titledBorder);

                requestDetails.append("   Request ID   : " + request.getID());
                requestDetails.append("\n   Student ID   : " + request.getStudentID());
                requestDetails.append("\n   Student Name : " + getStudentName(request.getStudentID()));
                requestDetails.append("\n   Previous Subject : " + CommonMethods.getSubjectTitle(request.getOldSubjectID()));
                requestDetails.append("\n   New Subject  : " + CommonMethods.getSubjectTitle(request.getNewSubjectID()));
                requestDetails.append(" ");
                requestPanel.add(requestDetails);

                JButton approveButton = new JButton("Approve");
                approveButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
                approveButton.setPreferredSize(new Dimension(95, 30));
                approveButton.addActionListener(a -> {
                    for(Student student : Main.studentList) {
                        if (student.getID().equals(request.getStudentID())) {
                            boolean validTransfer = true;

                            if(!CommonMethods.isEnrolled(request.getOldSubjectID(), request.getStudentID())){
                                JLabel notEnrolled = new JLabel("This student has not been enrolled in subject " + CommonMethods.getSubjectTitle(request.getOldSubjectID()) + ".");
                                notEnrolled.setFont(new Font("Times New Roman", Font.BOLD, 16));
                                JOptionPane.showMessageDialog(null, notEnrolled, "Invalid Withdrawal", JOptionPane.ERROR_MESSAGE);
                                validTransfer = false;
                            }
                            if (CommonMethods.isEnrolled(request.getNewSubjectID(), request.getStudentID())) {
                                JLabel enrolled = new JLabel("This student has been enrolled in subject " + CommonMethods.getSubjectTitle(request.getNewSubjectID()) + ".");
                                enrolled.setFont(new Font("Times New Roman", Font.BOLD, 16));
                                JOptionPane.showMessageDialog(null, enrolled, "Invalid Enrolment", JOptionPane.ERROR_MESSAGE);
                                validTransfer = false;
                            }

                            if(validTransfer) {
                                JLabel confirm = new JLabel("Are you sure you want to approve this request?");
                                confirm.setFont(new Font("Times New Roman", Font.BOLD, 16));
                                int result = JOptionPane.showConfirmDialog(null, confirm, "Confirm Approval", JOptionPane.YES_NO_OPTION);

                                if(result == JOptionPane.YES_OPTION){
                                    ArrayList<Enrolment> enrolmentList = CommonMethods.loadEnrolment(request.getOldSubjectID());
                                    enrolmentList.removeIf(enrolment -> enrolment.getStudentID().equals(request.getStudentID()));

                                    CommonMethods.writeFile("txtTCMS/enr" + request.getOldSubjectID() + ".txt", enrolmentList);

                                    ArrayList<Enrolment> enrolmentList1 = CommonMethods.loadEnrolment(request.getNewSubjectID());
                                    String enrolID = CommonMethods.getNextID("ENR", enrolmentList1);
                                    String level = "";

                                    for (Student s : Main.studentList) {
                                        if (s.getID().equals(request.getStudentID())) {
                                            level = s.getLevel();
                                            break;
                                        }
                                    }
                                    LocalDate today = LocalDate.now();
                                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");
                                    String date = today.format(formatter);

                                    Enrolment enrolment = new Enrolment(enrolID, request.getStudentID(), level, date);
                                    enrolmentList1.add(enrolment);
                                    CommonMethods.appendNewLine("txtTCMS/enr" + request.getNewSubjectID() + ".txt", enrolment);
                                    request.setStatus("A");
                                    CommonMethods.writeFile(Main.filePaths[5], Main.requestList);
                                    transferRequestFrame.dispose();

                                    JLabel transferred = new JLabel("Successfully transferred.");
                                    transferred.setFont(new Font("Times New Roman", Font.BOLD, 16));
                                    JOptionPane.showMessageDialog(null, transferred, "Successful Transfer", JOptionPane.INFORMATION_MESSAGE);
                                    transferRequests(id);
                                }
                            }
                            break;
                        }
                    }

                });

                JButton rejectButton = new JButton("Reject");
                rejectButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
                rejectButton.setPreferredSize(new Dimension(75, 30));
                rejectButton.addActionListener(r -> {
                    JLabel confirm = new JLabel("Are you sure you want to reject this request?");
                    confirm.setFont(new Font("Times New Roman", Font.BOLD, 16));
                    int result = JOptionPane.showConfirmDialog(null, confirm, "Confirm Rejection", JOptionPane.YES_NO_OPTION);

                    if(result == JOptionPane.YES_OPTION){
                        request.setStatus("R");
                        CommonMethods.writeFile(Main.filePaths[5], Main.requestList);
                        transferRequestFrame.dispose();

                        JLabel rejected = new JLabel("Successfully rejected.");
                        rejected.setFont(new Font("Times New Roman", Font.BOLD, 16));
                        JOptionPane.showMessageDialog(null, rejected, "Successful Rejection", JOptionPane.INFORMATION_MESSAGE);
                        transferRequests(id);
                    }
                });

                JPanel button = new JPanel(new BorderLayout());
                button.setBackground(new Color(230, 230, 250));
                button.add(approveButton, BorderLayout.EAST);
                button.add(rejectButton, BorderLayout.WEST);

                requestPanel.add(button);

                requestPanel.add(new JLabel(" "));
            }

        }

        if(emptyRequest){
            transferRequestFrame.dispose();
            JLabel noRequests = new JLabel("No pending transfer requests.");
            noRequests.setFont(new Font("Times New Roman", Font.BOLD, 16));
            JOptionPane.showMessageDialog(null, noRequests, "No Pending Request", JOptionPane.ERROR_MESSAGE);
            changeRequest(id);
        }

        JPanel backPanel = new JPanel(new BorderLayout());
        backPanel.setBackground(new Color(230, 230, 250));
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Times New Roman", Font.BOLD, 16));
        backButton.setPreferredSize(new Dimension(75, 30));

        backButton.addActionListener(b -> {
            transferRequestFrame.dispose();
            changeRequest(id);
        });

        backPanel.add(backButton, BorderLayout.WEST);
        backPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        transferRequestFrame.add(backPanel, BorderLayout.SOUTH);

        JScrollPane scrollPane = new JScrollPane(requestPanel);
        scrollPane.setBackground(new Color(230, 230, 250));
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        transferRequestFrame.add(scrollPane, BorderLayout.CENTER);
    }

    private static void requestHistory(String id){
        JFrame requestHistoryFrame = getFrame("Request History");

        JPanel requestPanel = new JPanel();
        requestPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        requestPanel.setBackground(new Color(230, 230, 250));
        requestPanel.setLayout(new BoxLayout(requestPanel, BoxLayout.Y_AXIS));

        boolean emptyRequest = true;
        for(ChangeRequest request : Main.requestList){
            if(!request.getStatus().equals("P")) {
                emptyRequest = false;

                JTextArea requestDetails = new JTextArea();
                requestDetails.setFont(new Font("Times New Roman", Font.PLAIN, 16));
                requestDetails.setEditable(false);
                requestDetails.setLineWrap(true);
                requestDetails.setWrapStyleWord(true);
                TitledBorder titledBorder = BorderFactory.createTitledBorder("Request ID: " + request.getID());
                titledBorder.setTitleFont(new Font("Times New Roman", Font.BOLD, 16));
                requestDetails.setBorder(titledBorder);

                requestDetails.append("   Student ID  : " + request.getStudentID());
                requestDetails.append("\n   Student Name  : " + getStudentName(request.getStudentID()));

                String typeOfRequest;
                if (request.getTypeOfRequest().equals("C")) {
                    typeOfRequest = "Change Subject";
                }
                else if(request.getTypeOfRequest().equals("E")){
                    typeOfRequest = "Subject Enrolment";
                }
                else{
                    typeOfRequest = "Subject Withdrawal";
                }
                requestDetails.append("\n   Type of Request  : " + typeOfRequest);

                if (request.getTypeOfRequest().equals("C")) {
                    requestDetails.append("\n   Previous Subject  : " + CommonMethods.getSubjectTitle(request.getOldSubjectID()));
                    requestDetails.append("\n   New Subject  : " + CommonMethods.getSubjectTitle(request.getNewSubjectID()));
                }
                else if(request.getTypeOfRequest().equals("E")){
                    requestDetails.append("\n   New Subject  : " + CommonMethods.getSubjectTitle(request.getNewSubjectID()));
                }
                else{
                    requestDetails.append("\n   Previous Subject  : " + CommonMethods.getSubjectTitle(request.getOldSubjectID()));
                }

                String status = (request.getStatus().equals("A")) ? "Approved" : "Rejected";
                requestDetails.append("\n   Status  : " + status);
                requestDetails.append("\n   Date  : " + request.getDate());
                requestDetails.append(" ");
                requestPanel.add(requestDetails);

                requestPanel.add(new JLabel(" "));
            }

        }

        if(emptyRequest){
            requestHistoryFrame.dispose();
            JLabel noHistory = new JLabel("No request history data.");
            noHistory.setFont(new Font("Times New Roman", Font.BOLD, 16));
            JOptionPane.showMessageDialog(null, noHistory, "No Request History Data", JOptionPane.ERROR_MESSAGE);
            changeRequest(id);
        }

        JPanel backPanel = new JPanel(new BorderLayout());
        backPanel.setBackground(new Color(230, 230, 250));
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        backButton.setPreferredSize(new Dimension(75, 30));

        backButton.addActionListener(b -> {
            requestHistoryFrame.dispose();
            changeRequest(id);
        });

        backPanel.add(backButton, BorderLayout.WEST);
        backPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        requestHistoryFrame.add(backPanel, BorderLayout.SOUTH);

        JScrollPane scrollPane = new JScrollPane(requestPanel);
        scrollPane.setBackground(new Color(230, 230, 250));
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        requestHistoryFrame.add(scrollPane, BorderLayout.CENTER);
    }

    private static void billsReceipts(String id){
        JFrame billsReceiptsFrame = getFrame("Bills and Receipts");

        JLabel title = new JLabel("Bills and Receipts",SwingConstants.CENTER);
        title.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        title.setFont(new Font("Times New Roman", Font.BOLD, 20));
        billsReceiptsFrame.add(title, BorderLayout.NORTH);

        JPanel billsReceiptsPanel = new JPanel(new GridLayout(3, 1));
        billsReceiptsPanel.setBackground(new Color(230, 230, 250));
        JButton generateBillButton = new JButton("1. Generate Bills");
        JButton billsReceiptsButton = new JButton("2. View Bills and Generate Receipts");
        JButton backButton = new JButton("3. Back to Main Menu");

        generateBillButton.setFont(new Font("Times New Roman", Font.BOLD, 18));
        billsReceiptsButton.setFont(new Font("Times New Roman", Font.BOLD, 18));
        backButton.setFont(new Font("Times New Roman", Font.BOLD, 18));

        generateBillButton.setBackground(new Color(220, 197, 232));
        billsReceiptsButton.setBackground(new Color(237, 226, 244));
        backButton.setBackground(new Color(220, 197, 232));

        billsReceiptsPanel.add(generateBillButton);
        billsReceiptsPanel.add(billsReceiptsButton);
        billsReceiptsPanel.add(backButton);
        billsReceiptsPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        billsReceiptsFrame.add(billsReceiptsPanel, BorderLayout.CENTER);

        generateBillButton.addActionListener(e -> {
            billsReceiptsFrame.dispose();
            generateBills(id);
        });
        billsReceiptsButton.addActionListener(e -> {
            billsReceiptsFrame.dispose();
            viewBillsReceipts(id);
        });
        backButton.addActionListener(e -> {
            billsReceiptsFrame.dispose();
            mainMenu(id);
        });
    }

    private static boolean isBillExist(String studentID, String date){
        for(Bill bill : Main.billList){
            if(bill.getStudentID().equals(studentID) && bill.getDate().equals(date)){
                return true;
            }
        }
        return false;
    }

    private static void generateBills(String id){
        JFrame generateBillsFrame = getFrame("Generate Bills");

        JPanel notePanel = new JPanel();
        notePanel.setBackground(new Color(230, 230, 250));
        notePanel.setLayout(new BoxLayout(notePanel, BoxLayout.Y_AXIS));

        JLabel line1 = new JLabel("*Please take note that this action will automatically generate bills ");
        line1.setFont(new Font("Times New Roman", Font.BOLD, 18));

        JLabel line2 = new JLabel(" for the current month to each student. If you wish to generate a ");
        line2.setFont(new Font("Times New Roman", Font.BOLD, 18));

        JLabel line3 = new JLabel(" bill manually to a specific student, please click on the");
        line3.setFont(new Font("Times New Roman", Font.BOLD, 18));

        JLabel line4 = new JLabel(" \"Create Bill Manually\" button.");
        line4.setFont(new Font("Times New Roman", Font.BOLD, 18));

        notePanel.add(new JLabel(" "));
        notePanel.add(line1);
        notePanel.add(line2);
        notePanel.add(line3);
        notePanel.add(line4);

        notePanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        generateBillsFrame.add(notePanel, BorderLayout.CENTER);

        JPanel button = new JPanel(new BorderLayout());
        button.setBackground(new Color(230, 230, 250));
        JButton generateBillsButton = new JButton("Generate Bills");
        generateBillsButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        generateBillsButton.setPreferredSize(new Dimension(170, 30));

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        backButton.setPreferredSize(new Dimension(75, 30));

        JButton manualButton = new JButton("Create Bill Manually");
        manualButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        manualButton.setPreferredSize(new Dimension(170, 30));

        JPanel adjustPanel = new JPanel(new BorderLayout());
        adjustPanel.setBackground(new Color(230, 230, 250));
        adjustPanel.add(manualButton, BorderLayout.NORTH);
        adjustPanel.add(new JLabel(" "), BorderLayout.CENTER);
        adjustPanel.add(generateBillsButton, BorderLayout.SOUTH);

        JPanel adjustPanel1 = new JPanel(new BorderLayout());
        adjustPanel1.setBackground(new Color(230, 230, 250));
        adjustPanel1.add(backButton, BorderLayout.SOUTH);

        button.add(adjustPanel, BorderLayout.EAST);
        button.add(adjustPanel1, BorderLayout.WEST);
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        generateBillsFrame.add(button, BorderLayout.SOUTH);

        generateBillsButton.addActionListener(e -> {
            JLabel confirm = new JLabel("Are you sure you want to generate the bills for current month?");
            confirm.setFont(new Font("Times New Roman", Font.BOLD, 16));
            int result = JOptionPane.showConfirmDialog(null, confirm, "Confirm Bills Generation",
                    JOptionPane.YES_NO_OPTION);

            if(result == JOptionPane.YES_OPTION) {
                LocalDate today = LocalDate.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");
                String date = today.format(formatter);

                boolean newBills = true;
                for(Bill bill : Main.billList){
                    if(bill.getDate().equals(date) && bill.getWayOfGeneration().equals("A")){
                        JLabel generated = new JLabel("The bills for current month have been generated.");
                        generated.setFont(new Font("Times New Roman", Font.BOLD, 16));
                        JOptionPane.showMessageDialog(null, generated, "Invalid Bills Generation",
                                JOptionPane.ERROR_MESSAGE);
                        newBills = false;
                        break;
                    }
                }

                if(newBills) {
                    for (Student student : Main.studentList) {
                        if (student.getNoOfSubjects() != 0) {
                            if(isBillExist(student.getID(), date)){
                                continue;
                            }
                            String billID = CommonMethods.getNextID("B", Main.billList);
                            String studentID = student.getID();
                            String status = "U";
                            String wayOfGeneration = "A";

                            ArrayList<String> subjectIDs = CommonMethods.getStudentSubjectIDs(student.getID());
                            double fees = getTotalFees(subjectIDs);

                            String subjectID1 = subjectIDs.get(0);
                            String subjectID2 = (student.getNoOfSubjects() >= 2) ? subjectIDs.get(1) : "*";
                            String subjectID3 = (student.getNoOfSubjects() == 3) ? subjectIDs.get(2) : "*";

                            Bill bill = new Bill(billID, studentID, date, subjectID1, subjectID2, subjectID3, fees,
                                    status, wayOfGeneration);
                            Main.billList.add(bill);
                            CommonMethods.appendNewLine(Main.filePaths[4], bill);
                        }
                    }
                    JLabel generated = new JLabel("All the bills for current month have been successfully " +
                            "generated.");
                    generated.setFont(new Font("Times New Roman", Font.BOLD, 16));
                    JOptionPane.showMessageDialog(null, generated, "Successful Bills Generation",
                            JOptionPane.INFORMATION_MESSAGE);
                }

            }
        });
        manualButton.addActionListener(e -> {
            generateBillsFrame.dispose();
            manualGenerateBills(id);
        });
        backButton.addActionListener(e -> {
            generateBillsFrame.dispose();
            billsReceipts(id);
        });
    }

    private static void manualGenerateBills(String id){
        JFrame manualGenerateFrame = getFrame("Create Bills Manually");

        JPanel manualGeneratePanel = new JPanel();
        manualGeneratePanel.setBackground(new Color(230, 230, 250));
        manualGeneratePanel.setLayout(new BoxLayout(manualGeneratePanel, BoxLayout.Y_AXIS));
        manualGeneratePanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        manualGeneratePanel.add(new JLabel(" "));
        JPanel studentIDPanel = new JPanel(new BorderLayout());
        studentIDPanel.setBackground(new Color(230, 230, 250));
        JLabel studentIDLabel = new JLabel("Student ID: ");
        studentIDLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));
        JTextField studentIDField = new JTextField();
        studentIDField.setPreferredSize(new Dimension(250, 30));
        studentIDPanel.add(studentIDLabel, BorderLayout.WEST);
        studentIDPanel.add(studentIDField, BorderLayout.EAST);
        manualGeneratePanel.add(studentIDPanel);

        manualGeneratePanel.add(new JLabel(" "));
        manualGeneratePanel.add(new JLabel(" "));
        JLabel select = new JLabel("Please select up to 3 subjects:");
        select.setFont(new Font("Times New Roman", Font.BOLD, 18));
        manualGeneratePanel.add(select);

        manualGenerateFrame.add(manualGeneratePanel, BorderLayout.NORTH);

        JPanel checkBoxPanel = new JPanel();
        checkBoxPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        checkBoxPanel.setBackground(new Color(230, 230, 250));
        checkBoxPanel.setLayout(new BoxLayout(checkBoxPanel, BoxLayout.Y_AXIS));

        ArrayList<JCheckBox> checkBoxes = new ArrayList<>();
        for(Subject subject : Main.subjectList){
            JCheckBox checkbox = new JCheckBox(subject.getTitle());
            checkbox.setFont(new Font("Times New Roman", Font.BOLD, 18));
            checkbox.setBackground(new Color(230, 230, 250));
            checkBoxes.add(checkbox);
            checkBoxPanel.add(checkbox);
            checkBoxPanel.add(new JLabel(" "));
        }

        JScrollPane scrollPane = new JScrollPane(checkBoxPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        scrollPane.setBackground(new Color(230, 230, 250));
        manualGenerateFrame.add(scrollPane, BorderLayout.CENTER);

        JPanel button = new JPanel(new BorderLayout());
        button.setBackground(new Color(230, 230, 250));
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JButton generateBillsButton = new JButton("Generate Bill");
        generateBillsButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        generateBillsButton.setPreferredSize(new Dimension(120, 30));

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        backButton.setPreferredSize(new Dimension(75, 30));

        button.add(generateBillsButton, BorderLayout.EAST);
        button.add(backButton, BorderLayout.WEST);

        manualGenerateFrame.add(button, BorderLayout.SOUTH);

        generateBillsButton.addActionListener(e -> {
            JLabel confirm = new JLabel("Are you sure you want to create this bill?");
            confirm.setFont(new Font("Times New Roman", Font.BOLD, 16));
            int result = JOptionPane.showConfirmDialog(null, confirm, "Confirm Bill Generation", JOptionPane.YES_NO_OPTION);

            if(result == JOptionPane.YES_OPTION) {
                String studentID = studentIDField.getText().strip().toUpperCase();
                LocalDate today = LocalDate.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");
                String date = today.format(formatter);

                boolean validDetails = true;

                ArrayList<String> subjectIDs = new ArrayList<>();
                String subjectID1 = "";
                String subjectID2 = "";
                String subjectID3 = "";

                if(studentID.isEmpty()){
                    validDetails = false;
                    JLabel invalid = new JLabel("Student ID cannot be empty.");
                    invalid.setFont(new Font("Times New Roman", Font.BOLD, 16));
                    JOptionPane.showMessageDialog(null, invalid, "Invalid Student ID", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    if (!CommonMethods.isValidID(Main.studentList, studentID)) {
                        validDetails = false;
                    }
                    else{
                        ArrayList<String> subjectTitles = new ArrayList<>();
                        for (JCheckBox checkBox : checkBoxes) {
                            if (checkBox.isSelected()) {
                                subjectTitles.add(checkBox.getText());
                            }
                        }

                        if (subjectTitles.size() == 0) {
                            JLabel invalid = new JLabel("Please choose at least one subject.");
                            invalid.setFont(new Font("Times New Roman", Font.BOLD, 16));
                            JOptionPane.showMessageDialog(null, invalid, "Invalid Selection", JOptionPane.ERROR_MESSAGE);
                            validDetails = false;
                        }
                        else if (subjectTitles.size() > 3) {
                            JLabel invalid = new JLabel("Please choose not more than 3 subjects.");
                            invalid.setFont(new Font("Times New Roman", Font.BOLD, 16));
                            JOptionPane.showMessageDialog(null, invalid, "Invalid Selection", JOptionPane.ERROR_MESSAGE);
                            validDetails = false;
                        }
                        else{
                            for(String subjectTitle : subjectTitles){
                                for(Subject subject : Main.subjectList) {
                                    if (subject.getTitle().equals(subjectTitle)){
                                        subjectIDs.add(subject.getID());
                                        break;
                                    }
                                }
                            }
                            subjectID1 = subjectIDs.get(0);
                            subjectID2 = (subjectIDs.size() >= 2) ? subjectIDs.get(1) : "*";
                            subjectID3 = (subjectIDs.size() == 3) ? subjectIDs.get(2) : "*";

                            if(!CommonMethods.isEnrolled(subjectID1, studentID)){
                                JLabel notEnrolled = new JLabel("This student has not been enrolled in subject " + subjectTitles.get(0) + ".");
                                notEnrolled.setFont(new Font("Times New Roman", Font.BOLD, 16));
                                JOptionPane.showMessageDialog(null, notEnrolled, "Not Enrolled", JOptionPane.ERROR_MESSAGE);
                                validDetails = false;
                            }
                            if(subjectID2 != "*"){
                                if(!CommonMethods.isEnrolled(subjectID2, studentID)){
                                    JLabel notEnrolled = new JLabel("This student has not been enrolled in subject " + subjectTitles.get(1) + ".");
                                    notEnrolled.setFont(new Font("Times New Roman", Font.BOLD, 16));
                                    JOptionPane.showMessageDialog(null, notEnrolled, "Not Enrolled", JOptionPane.ERROR_MESSAGE);
                                    validDetails = false;
                                }
                            }
                            if(subjectID3 != "*"){
                                if(!CommonMethods.isEnrolled(subjectID3, studentID)){
                                    JLabel notEnrolled = new JLabel("This student has not been enrolled in subject " + subjectTitles.get(2) + ".");
                                    notEnrolled.setFont(new Font("Times New Roman", Font.BOLD, 16));
                                    JOptionPane.showMessageDialog(null, notEnrolled, "Not Enrolled", JOptionPane.ERROR_MESSAGE);
                                    validDetails = false;
                                }
                            }
                        }
                    }
                }

                if (validDetails) {
                    if(isBillExist(studentID, date)){
                        JLabel generated = new JLabel("Bill already exists for this student and this date. Please delete the existing bill if you wish to create " +
                                "another bill.");
                        generated.setFont(new Font("Times New Roman", Font.BOLD, 16));
                        JOptionPane.showMessageDialog(null, generated, "Bill Generated", JOptionPane.ERROR_MESSAGE);
                    }
                    else {
                        String billID = CommonMethods.getNextID("B", Main.billList);
                        double fees = getTotalFees(subjectIDs);
                        String status = "U";
                        String wayOfGeneration = "M";

                        Bill bill = new Bill(billID, studentID, date, subjectID1, subjectID2, subjectID3, fees, status, wayOfGeneration);
                        Main.billList.add(bill);
                        CommonMethods.appendNewLine(Main.filePaths[4], bill);

                        JLabel generated = new JLabel("Bill successfully generated.");
                        generated.setFont(new Font("Times New Roman", Font.BOLD, 16));
                        JOptionPane.showMessageDialog(null, generated, "Successful Bill Generation", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        });
        backButton.addActionListener(e -> {
            manualGenerateFrame.dispose();
            generateBills(id);
        });
    }

    private static double getTotalFees(ArrayList<String> subjectIDs){
        double fees = 0;

        for(String subjectID : subjectIDs){
            for(Subject subject : Main.subjectList){
                if(subject.getID().equals(subjectID)){
                    fees += subject.getFees();
                    break;
                }
            }
        }
        return fees;
    }

    private static void viewBillsReceipts(String id){
        JFrame viewBillsFrame = getFrame("View Bills and Generate Receipts");

        JPanel viewBillsPanel = new JPanel();
        viewBillsPanel.setBackground(new Color(230, 230, 250));
        viewBillsPanel.setLayout(new BoxLayout(viewBillsPanel, BoxLayout.Y_AXIS));
        viewBillsPanel.setBorder(BorderFactory.createEmptyBorder(30, 35, 30, 35));

        JLabel viewBills = new JLabel("View Bills By:");
        viewBills.setFont(new Font("Times New Roman", Font.BOLD, 18));
        viewBillsPanel.add(viewBills);

        JRadioButton studentButton = new JRadioButton("Student");
        studentButton.setFont(new Font("Times New Roman", Font.BOLD, 18));
        studentButton.setBackground(new Color(230, 230, 250));
        JRadioButton dateButton = new JRadioButton("Date");
        dateButton.setFont(new Font("Times New Roman", Font.BOLD, 18));
        dateButton.setBackground(new Color(230, 230, 250));

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(studentButton);
        buttonGroup.add(dateButton);

        viewBillsPanel.add(new JLabel(" "));
        viewBillsPanel.add(studentButton);
        viewBillsPanel.add(dateButton);

        viewBillsFrame.add(viewBillsPanel, BorderLayout.CENTER);

        JPanel button = new JPanel(new BorderLayout());
        button.setBackground(new Color(230, 230, 250));
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JButton nextButton = new JButton("Next");
        nextButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        nextButton.setPreferredSize(new Dimension(75, 30));

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        backButton.setPreferredSize(new Dimension(75, 30));

        button.add(backButton, BorderLayout.WEST);
        button.add(nextButton, BorderLayout.EAST);

        viewBillsFrame.add(button, BorderLayout.SOUTH);

        nextButton.addActionListener(e -> {
            if(studentButton.isSelected()){
                viewBillsFrame.dispose();
                studentBillsReceipts(id);
            }
            if(dateButton.isSelected()){
                viewBillsFrame.dispose();
                dateBillsReceipts(id);
            }
        });
        backButton.addActionListener(e -> {
            viewBillsFrame.dispose();
            billsReceipts(id);
        });

    }

    private static void studentBillsReceipts(String id){

        JFrame studentBillsFrame = getFrame("View Bills By Student");

        JPanel studentIDPanel = new JPanel(new BorderLayout());
        studentIDPanel.setBackground(new Color(230, 230, 250));
        JLabel studentIDLabel = new JLabel("Student ID: ");
        studentIDLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));
        JTextField studentIDField = new JTextField();
        studentIDField.setPreferredSize(new Dimension(250, 40));

        studentIDPanel.add(studentIDLabel, BorderLayout.WEST);
        studentIDPanel.add(studentIDField, BorderLayout.EAST);

        JPanel adjustPanel = new JPanel();
        adjustPanel.setBackground(new Color(230, 230, 250));
        adjustPanel.setLayout(new BoxLayout(adjustPanel, BoxLayout.Y_AXIS));
        adjustPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        adjustPanel.add(new JLabel(" "));
        adjustPanel.add(studentIDPanel);

        studentBillsFrame.add(adjustPanel, BorderLayout.NORTH);

        JPanel button = new JPanel(new BorderLayout());
        button.setBackground(new Color(230, 230, 250));
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JButton nextButton = new JButton("Next");
        nextButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        nextButton.setPreferredSize(new Dimension(85, 30));

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        backButton.setPreferredSize(new Dimension(75, 30));

        button.add(nextButton, BorderLayout.EAST);
        button.add(backButton, BorderLayout.WEST);

        studentBillsFrame.add(button, BorderLayout.SOUTH);

        nextButton.addActionListener(e -> {
            String studentID = studentIDField.getText().strip().toUpperCase();

            if(studentID.isEmpty()) {
                JLabel empty = new JLabel("Student ID cannot be empty.");
                empty.setFont(new Font("Times New Roman", Font.BOLD, 16));
                JOptionPane.showMessageDialog(null, empty, "Invalid Student ID", JOptionPane.ERROR_MESSAGE);
            }
            else {
                if (CommonMethods.isValidID(Main.studentList, studentID)) {
                    studentBillsFrame.dispose();
                    getStudentBillsFrame(id, studentID);
                }
            }
        });

        backButton.addActionListener(e -> {
            studentBillsFrame.dispose();
            viewBillsReceipts(id);
        });
    }

    private static void dateBillsReceipts(String id){
        JFrame dateBillsReceiptsFrame = getFrame("View Bills By Date");

        Set<String> uniqueDates = new HashSet<>();
        for(Bill bill : Main.billList){
            uniqueDates.add(bill.getDate());
        }

        String[] dateArray = uniqueDates.toArray(new String[0]);
        Arrays.sort(dateArray);
        String[] dateOptions = new String[dateArray.length + 1];
        dateOptions[0] = "-- Please Select --";

        for(int i = 0; i < dateArray.length; i++){
            dateOptions[i + 1] = dateArray[i];
        }
        JComboBox<String> dateComboBox = new JComboBox<>(dateOptions);
        dateComboBox.setBackground(new Color(230, 230, 250));
        dateComboBox.setFont(new Font("Times New Roman", Font.BOLD, 18));
        dateComboBox.setPreferredSize(new Dimension(200, 50));
        dateComboBox.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JPanel comboBox = new JPanel();
        comboBox.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        comboBox.setLayout(new BoxLayout(comboBox, BoxLayout.Y_AXIS));
        comboBox.setBackground(new Color(230, 230, 250));

        comboBox.add(new JLabel(" "));
        comboBox.add(dateComboBox);
        dateBillsReceiptsFrame.add(comboBox, BorderLayout.NORTH);

        JPanel button = new JPanel(new BorderLayout());
        button.setBackground(new Color(230, 230, 250));
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        backButton.setPreferredSize(new Dimension(75, 30));
        button.add(backButton, BorderLayout.WEST);

        JButton showBillsButton = new JButton("Show Bills");
        showBillsButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        showBillsButton.setPreferredSize(new Dimension(100, 30));
        button.add(showBillsButton, BorderLayout.EAST);

        dateBillsReceiptsFrame.add(button, BorderLayout.SOUTH);

        backButton.addActionListener(b -> {
            dateBillsReceiptsFrame.dispose();
            viewBillsReceipts(id);
        });

        showBillsButton.addActionListener(e -> {
            String selected = (String) dateComboBox.getSelectedItem();

            if(selected.equals("-- Please Select --")){
                JLabel invalid = new JLabel("Please select a date.");
                invalid.setFont(new Font("Times New Roman", Font.BOLD, 16));
                JOptionPane.showMessageDialog(null, invalid, "Invalid Option", JOptionPane.ERROR_MESSAGE);
            }
            else{
                dateBillsReceiptsFrame.dispose();
                getDateBillsFrame(selected, id);
            }
        });
    }

    private static void getDateBillsFrame(String selected, String id){
        if(!isDateBillExist(selected)){
            JLabel noBills = new JLabel("No bills for date " + selected + ".");
            noBills.setFont(new Font("Times New Roman", Font.BOLD, 16));
            JOptionPane.showMessageDialog(null, noBills, "No Bills", JOptionPane.ERROR_MESSAGE);
            dateBillsReceipts(id);
        }
        else {
            JFrame showDateBills = getFrame("View Bills By Date");

            JPanel dateBillsPanel = new JPanel();
            dateBillsPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
            dateBillsPanel.setBackground(new Color(230, 230, 250));
            dateBillsPanel.setLayout(new BoxLayout(dateBillsPanel, BoxLayout.Y_AXIS));

            for (Bill bill : Main.billList) {
                if (bill.getDate().equals(selected)) {
                    showDateBills(selected, id, showDateBills, dateBillsPanel, bill);
                }
            }

            JScrollPane scrollPane = new JScrollPane(dateBillsPanel);
            scrollPane.setBackground(new Color(230, 230, 250));
            scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

            showDateBills.add(scrollPane, BorderLayout.CENTER);

            JPanel buttonPanel = new JPanel(new BorderLayout());
            buttonPanel.setBackground(new Color(230, 230, 250));
            buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

            JButton backButton = new JButton("Back");
            backButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
            backButton.setPreferredSize(new Dimension(75, 30));
            buttonPanel.add(backButton, BorderLayout.WEST);

            showDateBills.add(buttonPanel, BorderLayout.SOUTH);

            backButton.addActionListener(c -> {
                showDateBills.dispose();
                dateBillsReceipts(id);
            });
        }
    }

    private static void getStudentBillsFrame(String id, String studentID){
        if (!CommonMethods.isBillExist(studentID)) {
            JLabel noBills = new JLabel("No bills for student " + studentID + ".");
            noBills.setFont(new Font("Times New Roman", Font.BOLD, 16));
            JOptionPane.showMessageDialog(null, noBills, "No Bills", JOptionPane.ERROR_MESSAGE);
            studentBillsReceipts(id);
        }
        else {
            JFrame studentBillsReceiptsFrame = getFrame("View Bills By Student");

            JPanel billsReceiptsPanel = new JPanel();
            billsReceiptsPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
            billsReceiptsPanel.setBackground(new Color(230, 230, 250));
            billsReceiptsPanel.setLayout(new BoxLayout(billsReceiptsPanel, BoxLayout.Y_AXIS));

            for (Bill bill : Main.billList) {
                if (bill.getStudentID().equals(studentID)) {
                    showStudentBills(studentID, id, bill, billsReceiptsPanel, studentBillsReceiptsFrame);
                }
            }

            JScrollPane scrollPane = new JScrollPane(billsReceiptsPanel);
            scrollPane.setBackground(new Color(230, 230, 250));
            scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

            studentBillsReceiptsFrame.add(scrollPane, BorderLayout.CENTER);

            JPanel button = new JPanel(new BorderLayout());
            button.setBackground(new Color(230, 230, 250));
            button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

            JButton backButton = new JButton("Back");
            backButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
            backButton.setPreferredSize(new Dimension(75, 30));
            button.add(backButton, BorderLayout.WEST);

            studentBillsReceiptsFrame.add(button, BorderLayout.SOUTH);

            backButton.addActionListener(e -> {
                studentBillsReceiptsFrame.dispose();
                studentBillsReceipts(id);
            });
        }
    }

    private static void showStudentBills(String studentID, String id, Bill bill, JPanel panel, JFrame frame){
        JTextArea billDetails = new JTextArea();
        billDetails.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        billDetails.setEditable(false);
        billDetails.setLineWrap(true);
        billDetails.setWrapStyleWord(true);
        TitledBorder titledBorder = BorderFactory.createTitledBorder("ID: " + bill.getID());
        titledBorder.setTitleFont(new Font("Times New Roman", Font.BOLD, 16));
        billDetails.setBorder(titledBorder);

        billDetails.append("  Student ID: " + studentID);
        billDetails.append("\n  Student Name: " + getStudentName(studentID));
        billDetails.append("\n  Date: " + bill.getDate());
        billDetails.append("\n  Subjects Involved: " + CommonMethods.getSubjectTitle(bill.getSubjectID1()));
        if (!bill.getSubjectID2().equals("*")) {
            billDetails.append(", " + CommonMethods.getSubjectTitle(bill.getSubjectID2()));
        }
        if (!bill.getSubjectID3().equals("*")) {
            billDetails.append(", " + CommonMethods.getSubjectTitle(bill.getSubjectID3()));
        }

        billDetails.append("\n  Total Fees: " + String.format("%.2f", bill.getFees()));

        String status = (bill.getStatus().equals("U")) ? "Unpaid" : "Paid";
        billDetails.append("\n  Payment Status: " + status);

        String wayOfGeneration = (bill.getWayOfGeneration().equals("A")) ? "System Automation" : "Manual";
        billDetails.append("\n  Bill Generated by: " + wayOfGeneration);

        panel.add(billDetails);

        JPanel button = new JPanel(new BorderLayout());
        button.setBackground(new Color(230, 230, 250));
        JButton markStatus;
        if (bill.getStatus().equals("U")) {
            markStatus = new JButton("Mark As Paid");
            markStatus.setFont(new Font("Times New Roman", Font.BOLD, 14));
            markStatus.setPreferredSize(new Dimension(150, 30));
            button.add(markStatus, BorderLayout.WEST);

            markStatus.addActionListener(e -> {
                JLabel confirm = new JLabel("Are you sure you want to mark the status as \"Paid\"?");
                confirm.setFont(new Font("Times New Roman", Font.BOLD, 16));
                int result = JOptionPane.showConfirmDialog(null, confirm, "Confirm Status", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    frame.dispose();
                    bill.setStatus("P");
                    CommonMethods.writeFile(Main.filePaths[4], Main.billList);
                    JLabel updated = new JLabel("Bill status updated.");
                    updated.setFont(new Font("Times New Roman", Font.BOLD, 16));
                    JOptionPane.showMessageDialog(null, updated, "Bill Status Updated", JOptionPane.INFORMATION_MESSAGE);
                    getStudentBillsFrame(id, studentID);
                }
            });
        } else {
            markStatus = new JButton("Mark As Unpaid");
            markStatus.setFont(new Font("Times New Roman", Font.BOLD, 14));
            markStatus.setPreferredSize(new Dimension(150, 30));
            button.add(markStatus, BorderLayout.WEST);

            JButton receiptButton = new JButton("Generate Receipt");
            receiptButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
            receiptButton.setPreferredSize(new Dimension(150, 30));
            button.add(receiptButton, BorderLayout.CENTER);

            markStatus.addActionListener(e -> {
                JLabel confirm = new JLabel("Are you sure you want to mark the status as \"Unpaid\"?");
                confirm.setFont(new Font("Times New Roman", Font.BOLD, 16));
                int result = JOptionPane.showConfirmDialog(null, confirm, "Confirm Status", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    frame.dispose();
                    bill.setStatus("U");
                    CommonMethods.writeFile(Main.filePaths[4], Main.billList);
                    JLabel updated = new JLabel("Bill status updated.");
                    updated.setFont(new Font("Times New Roman", Font.BOLD, 16));
                    JOptionPane.showMessageDialog(null, updated, "Bill Status Updated", JOptionPane.INFORMATION_MESSAGE);
                    getStudentBillsFrame(id, studentID);
                }
            });

            receiptButton.addActionListener(e -> {
                frame.setVisible(false);
                generateReceipts(frame, bill);
            });
        }

        JButton deleteButton = new JButton("Delete Bill");
        deleteButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        deleteButton.setPreferredSize(new Dimension(150, 30));
        button.add(deleteButton, BorderLayout.EAST);

        deleteButton.addActionListener(e -> {
            JLabel confirm = new JLabel("Are you sure you want to delete this bill?");
            confirm.setFont(new Font("Times New Roman", Font.BOLD, 16));
            int result = JOptionPane.showConfirmDialog(null, confirm, "Confirm Bill Deletion", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                frame.dispose();
                Main.billList.remove(bill);
                CommonMethods.writeFile(Main.filePaths[4], Main.billList);
                JLabel deleted = new JLabel("Bill deleted.");
                deleted.setFont(new Font("Times New Roman", Font.BOLD, 16));
                JOptionPane.showMessageDialog(null, deleted, "Bill Deletion", JOptionPane.INFORMATION_MESSAGE);
                getStudentBillsFrame(id, studentID);
            }
        });

        button.add(deleteButton, BorderLayout.EAST);
        panel.add(button);
        panel.add(new JLabel(" "));
    }

    private static void showDateBills(String selected, String id, JFrame frame, JPanel panel, Bill bill){
        JTextArea billDetails = new JTextArea();
        billDetails.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        billDetails.setEditable(false);
        billDetails.setLineWrap(true);
        billDetails.setWrapStyleWord(true);
        TitledBorder titledBorder = BorderFactory.createTitledBorder("ID: " + bill.getID());
        titledBorder.setTitleFont(new Font("Times New Roman", Font.BOLD, 16));
        billDetails.setBorder(titledBorder);

        billDetails.append("  Student ID: " + bill.getStudentID());
        billDetails.append("\n  Student Name: " + getStudentName(bill.getStudentID()));
        billDetails.append("\n  Date: " + bill.getDate());
        billDetails.append("\n  Subjects Involved: " + CommonMethods.getSubjectTitle(bill.getSubjectID1()));
        if (!bill.getSubjectID2().equals("*")) {
            billDetails.append(", " + CommonMethods.getSubjectTitle(bill.getSubjectID2()));
        }
        if (!bill.getSubjectID3().equals("*")) {
            billDetails.append(", " + CommonMethods.getSubjectTitle(bill.getSubjectID3()));
        }

        billDetails.append("\n  Total Fees: " + String.format("%.2f", bill.getFees()));

        String status = (bill.getStatus().equals("U")) ? "Unpaid" : "Paid";
        billDetails.append("\n  Payment Status: " + status);

        String wayOfGeneration = (bill.getWayOfGeneration().equals("A")) ? "System Automation" : "Manual";
        billDetails.append("\n  Bill Generated by: " + wayOfGeneration);

        panel.add(billDetails);

        JPanel button = new JPanel(new BorderLayout());
        button.setBackground(new Color(230, 230, 250));
        JButton markStatus;
        if (bill.getStatus().equals("U")) {
            markStatus = new JButton("Mark As Paid");
            markStatus.setFont(new Font("Times New Roman", Font.BOLD, 14));
            markStatus.setPreferredSize(new Dimension(150, 30));
            button.add(markStatus, BorderLayout.WEST);

            markStatus.addActionListener(e -> {
                JLabel confirm = new JLabel("Are you sure you want to mark the status as \"Paid\"?");
                confirm.setFont(new Font("Times New Roman", Font.BOLD, 16));
                int result = JOptionPane.showConfirmDialog(null, confirm, "Confirm Status", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    frame.dispose();
                    bill.setStatus("P");
                    CommonMethods.writeFile(Main.filePaths[4], Main.billList);
                    JLabel updated = new JLabel("Bill status updated.");
                    updated.setFont(new Font("Times New Roman", Font.BOLD, 16));
                    JOptionPane.showMessageDialog(null, updated, "Bill Status Updated", JOptionPane.INFORMATION_MESSAGE);
                    getDateBillsFrame(selected, id);
                }
            });
        } else {
            markStatus = new JButton("Mark As Unpaid");
            markStatus.setFont(new Font("Times New Roman", Font.BOLD, 14));
            markStatus.setPreferredSize(new Dimension(150, 30));
            button.add(markStatus, BorderLayout.WEST);

            JButton receiptButton = new JButton("Generate Receipt");
            receiptButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
            receiptButton.setPreferredSize(new Dimension(150, 30));
            button.add(receiptButton, BorderLayout.CENTER);

            markStatus.addActionListener(e -> {
                JLabel confirm = new JLabel("Are you sure you want to mark the status as \"Unpaid\"?");
                confirm.setFont(new Font("Times New Roman", Font.BOLD, 16));
                int result = JOptionPane.showConfirmDialog(null, confirm, "Confirm Status", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    frame.dispose();
                    bill.setStatus("U");
                    CommonMethods.writeFile(Main.filePaths[4], Main.billList);
                    JLabel updated = new JLabel("Bill status updated.");
                    updated.setFont(new Font("Times New Roman", Font.BOLD, 16));
                    JOptionPane.showMessageDialog(null, updated, "Bill Status Updated", JOptionPane.INFORMATION_MESSAGE);
                    getDateBillsFrame(selected, id);
                }
            });

            receiptButton.addActionListener(e -> {
                frame.setVisible(false);
                generateReceipts(frame, bill);
            });
        }

        JButton deleteButton = new JButton("Delete Bill");
        deleteButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        deleteButton.setPreferredSize(new Dimension(150, 30));
        button.add(deleteButton, BorderLayout.EAST);

        deleteButton.addActionListener(e -> {
            JLabel confirm = new JLabel("Are you sure you want to delete this bill?");
            confirm.setFont(new Font("Times New Roman", Font.BOLD, 16));
            int result = JOptionPane.showConfirmDialog(null, confirm, "Confirm Bill Deletion", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                frame.dispose();
                Main.billList.remove(bill);
                CommonMethods.writeFile(Main.filePaths[4], Main.billList);
                JLabel deleted = new JLabel("Bill deleted.");
                deleted.setFont(new Font("Times New Roman", Font.BOLD, 16));
                JOptionPane.showMessageDialog(null, deleted, "Bill Deletion", JOptionPane.INFORMATION_MESSAGE);
                getDateBillsFrame(selected, id);
            }
        });

        button.add(deleteButton, BorderLayout.EAST);
        panel.add(button);
        panel.add(new JLabel(" "));
    }

    private static boolean isDateBillExist(String date){
        for(Bill bill : Main.billList){
            if(bill.getDate().equals(date)){
                return true;
            }
        }
        return false;
    }

    private static void generateReceipts(JFrame frame, Bill bill){
        JFrame receiptFrame = getFrame("Receipt");

        JTextArea receiptsDetails = new JTextArea();
        receiptsDetails.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        receiptsDetails.setEditable(false);
        receiptsDetails.setLineWrap(true);
        receiptsDetails.setWrapStyleWord(true);
        receiptsDetails.setFont(new Font("Monospaced", Font.BOLD, 16));

        receiptsDetails.append("-------- Tuition Centre Receipt --------");
        receiptsDetails.append("\n ");
        receiptsDetails.append("\nBill ID : " + bill.getID());
        receiptsDetails.append("\n ");
        receiptsDetails.append("\nStudent ID : " + bill.getStudentID());
        receiptsDetails.append("\n ");
        receiptsDetails.append("\nStudent Name : " + getStudentName(bill.getStudentID()));
        receiptsDetails.append("\n ");
        receiptsDetails.append("\nDate of Bill : " + bill.getDate());
        receiptsDetails.append("\n ");
        receiptsDetails.append("\nSubjects Involved: " + CommonMethods.getSubjectTitle(bill.getSubjectID1()));

        if (!bill.getSubjectID2().equals("*")) {
            receiptsDetails.append(", " + CommonMethods.getSubjectTitle(bill.getSubjectID2()));
        }
        if (!bill.getSubjectID3().equals("*")) {
            receiptsDetails.append(", " + CommonMethods.getSubjectTitle(bill.getSubjectID3()));
        }
        receiptsDetails.append("\n ");
        receiptsDetails.append("\nAmount Paid: " + String.format("%.2f", bill.getFees()));
        receiptsDetails.append("\n ");
        receiptsDetails.append("\n-----------------------------------------");

        JScrollPane scrollPane = new JScrollPane(receiptsDetails);
        scrollPane.setBackground(new Color(230, 230, 250));
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        receiptFrame.add(scrollPane, BorderLayout.CENTER);

        JPanel button = new JPanel(new BorderLayout());
        button.setBackground(new Color(230, 230, 250));
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        backButton.setPreferredSize(new Dimension(75, 30));
        button.add(backButton, BorderLayout.WEST);

        receiptFrame.add(button, BorderLayout.SOUTH);

        backButton.addActionListener(e -> {
            receiptFrame.dispose();
            frame.setVisible(true);
        });
    }

    private static void viewStudentInfo(String id){

        JFrame studentInfoFrame = getFrame("View and Edit Student Info");

        JPanel studentIDPanel = new JPanel(new BorderLayout());
        studentIDPanel.setBackground(new Color(230, 230, 250));
        JLabel studentIDLabel = new JLabel("Student ID: ");
        studentIDLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));
        JTextField studentIDField = new JTextField();
        studentIDField.setPreferredSize(new Dimension(250, 40));

        studentIDPanel.add(studentIDLabel, BorderLayout.WEST);
        studentIDPanel.add(studentIDField, BorderLayout.EAST);

        JPanel adjustPanel = new JPanel();
        adjustPanel.setBackground(new Color(230, 230, 250));
        adjustPanel.setLayout(new BoxLayout(adjustPanel, BoxLayout.Y_AXIS));
        adjustPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        adjustPanel.add(new JLabel(" "));
        adjustPanel.add(studentIDPanel);

        studentInfoFrame.add(adjustPanel, BorderLayout.NORTH);

        JPanel button = new JPanel(new BorderLayout());
        button.setBackground(new Color(230, 230, 250));
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JButton nextButton = new JButton("Next");
        nextButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        nextButton.setPreferredSize(new Dimension(85, 30));

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        backButton.setPreferredSize(new Dimension(75, 30));

        button.add(nextButton, BorderLayout.EAST);
        button.add(backButton, BorderLayout.WEST);

        studentInfoFrame.add(button, BorderLayout.SOUTH);

        nextButton.addActionListener(e -> {
            String studentID = studentIDField.getText().strip().toUpperCase();

            if(studentID.isEmpty()) {
                JLabel empty = new JLabel("Student ID cannot be empty.");
                empty.setFont(new Font("Times New Roman", Font.BOLD, 16));
                JOptionPane.showMessageDialog(null, empty, "Invalid Student ID", JOptionPane.ERROR_MESSAGE);
            }
            else {
                if (CommonMethods.isValidID(Main.studentList, studentID)) {
                    studentInfoFrame.dispose();
                    viewStudentProfile(studentID, id);
                }
            }
        });

        backButton.addActionListener(e -> {
            studentInfoFrame.dispose();
            mainMenu(id);
        });
    }

    private static void viewStudentProfile(String studentID, String id){

        JFrame viewProfileFrame = getFrame("View and Edit Student Info");

        JPanel profilePanel = new JPanel();
        profilePanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        profilePanel.setBackground(new Color(230, 230, 250));
        profilePanel.setLayout(new BoxLayout(profilePanel, BoxLayout.Y_AXIS));

        JPanel editEmailPanel = new JPanel(new BorderLayout());
        editEmailPanel.setBackground(new Color(230, 230, 250));
        JButton editEmail = new JButton("Edit");
        editEmail.setFont(new Font("Times New Roman", Font.BOLD, 14));
        editEmail.setPreferredSize(new Dimension(75, 30));
        editEmailPanel.add(editEmail, BorderLayout.WEST);

        JPanel editPhoneNoPanel = new JPanel(new BorderLayout());
        editPhoneNoPanel.setBackground(new Color(230, 230, 250));
        JButton editPhoneNo = new JButton("Edit");
        editPhoneNo.setFont(new Font("Times New Roman", Font.BOLD, 14));
        editPhoneNo.setPreferredSize(new Dimension(75, 30));
        editPhoneNoPanel.add(editPhoneNo, BorderLayout.WEST);

        JPanel editAddressPanel = new JPanel(new BorderLayout());
        editAddressPanel.setBackground(new Color(230, 230, 250));
        JButton editAddress = new JButton("Edit");
        editAddress.setFont(new Font("Times New Roman", Font.BOLD, 14));
        editAddress.setPreferredSize(new Dimension(75, 30));
        editAddressPanel.add(editAddress, BorderLayout.WEST);

        JPanel editLevelPanel = new JPanel(new BorderLayout());
        editLevelPanel.setBackground(new Color(230, 230, 250));
        JButton editLevel = new JButton("Edit");
        editLevel.setFont(new Font("Times New Roman", Font.BOLD, 14));
        editLevel.setPreferredSize(new Dimension(75, 30));
        editLevelPanel.add(editLevel, BorderLayout.WEST);

        JLabel title = new JLabel("Student Info", SwingConstants.CENTER);
        title.setFont(new Font("Times New Roman", Font.BOLD, 18));
        title.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        viewProfileFrame.add(title, BorderLayout.NORTH);

        for(Student student : Main.studentList){
            if(student.getID().equals(studentID)){
                String[] attributes = student.getAttributes();

                JTextArea idArea = new JTextArea(attributes[0]);
                idArea.setFont(new Font("Times New Roman", Font.PLAIN, 16));
                idArea.setEditable(false);
                idArea.setLineWrap(true);
                idArea.setWrapStyleWord(true);
                TitledBorder titledBorder = BorderFactory.createTitledBorder("ID");
                titledBorder.setTitleFont(new Font("Times New Roman", Font.BOLD, 16));
                idArea.setBorder(titledBorder);
                profilePanel.add(idArea);
                profilePanel.add(new JLabel(" "));

                JTextArea nameArea = new JTextArea(attributes[1]);
                nameArea.setFont(new Font("Times New Roman", Font.PLAIN, 16));
                nameArea.setEditable(false);
                nameArea.setLineWrap(true);
                nameArea.setWrapStyleWord(true);
                TitledBorder titledBorder1 = BorderFactory.createTitledBorder("Name");
                titledBorder1.setTitleFont(new Font("Times New Roman", Font.BOLD, 16));
                nameArea.setBorder(titledBorder1);
                profilePanel.add(nameArea);
                profilePanel.add(new JLabel(" "));

                JTextArea icArea = new JTextArea(attributes[2]);
                icArea.setFont(new Font("Times New Roman", Font.PLAIN, 16));
                icArea.setEditable(false);
                icArea.setLineWrap(true);
                icArea.setWrapStyleWord(true);
                TitledBorder titledBorder2 = BorderFactory.createTitledBorder("IC Number");
                titledBorder2.setTitleFont(new Font("Times New Roman", Font.BOLD, 16));
                icArea.setBorder(titledBorder2);
                profilePanel.add(icArea);
                profilePanel.add(new JLabel(" "));

                JTextArea emailArea = new JTextArea(attributes[3]);
                emailArea.setFont(new Font("Times New Roman", Font.PLAIN, 16));
                emailArea.setEditable(false);
                emailArea.setLineWrap(true);
                emailArea.setWrapStyleWord(true);
                TitledBorder titledBorder3 = BorderFactory.createTitledBorder("Email");
                titledBorder3.setTitleFont(new Font("Times New Roman", Font.BOLD, 16));
                emailArea.setBorder(titledBorder3);
                profilePanel.add(emailArea);
                profilePanel.add(editEmailPanel);
                profilePanel.add(new JLabel(" "));

                JTextArea phoneNoArea = new JTextArea(attributes[4]);
                phoneNoArea.setFont(new Font("Times New Roman", Font.PLAIN, 16));
                phoneNoArea.setEditable(false);
                phoneNoArea.setLineWrap(true);
                phoneNoArea.setWrapStyleWord(true);
                TitledBorder titledBorder4 = BorderFactory.createTitledBorder("Contact Number");
                titledBorder4.setTitleFont(new Font("Times New Roman", Font.BOLD, 16));
                phoneNoArea.setBorder(titledBorder4);
                profilePanel.add(phoneNoArea);
                profilePanel.add(editPhoneNoPanel);
                profilePanel.add(new JLabel(" "));

                JTextArea addressArea = new JTextArea(attributes[5].replace("\"",""));
                addressArea.setFont(new Font("Times New Roman", Font.PLAIN, 16));
                addressArea.setEditable(false);
                addressArea.setLineWrap(true);
                addressArea.setWrapStyleWord(true);
                TitledBorder titledBorder5 = BorderFactory.createTitledBorder("Address");
                titledBorder5.setTitleFont(new Font("Times New Roman", Font.BOLD, 16));
                addressArea.setBorder(titledBorder5);
                profilePanel.add(addressArea);
                profilePanel.add(editAddressPanel);
                profilePanel.add(new JLabel(" "));

                JTextArea levelArea = new JTextArea(attributes[8]);
                levelArea.setFont(new Font("Times New Roman", Font.PLAIN, 16));
                levelArea.setEditable(false);
                levelArea.setLineWrap(true);
                levelArea.setWrapStyleWord(true);
                TitledBorder titledBorder7 = BorderFactory.createTitledBorder("Level");
                titledBorder7.setTitleFont(new Font("Times New Roman", Font.BOLD, 16));
                levelArea.setBorder(titledBorder7);
                profilePanel.add(levelArea);
                profilePanel.add(editLevelPanel);
                profilePanel.add(new JLabel(" "));

                JTextArea dateArea = new JTextArea(attributes[9]);
                dateArea.setFont(new Font("Times New Roman", Font.PLAIN, 16));
                dateArea.setEditable(false);
                dateArea.setLineWrap(true);
                dateArea.setWrapStyleWord(true);
                TitledBorder titledBorder8 = BorderFactory.createTitledBorder("Registration Date");
                titledBorder8.setTitleFont(new Font("Times New Roman", Font.BOLD, 16));
                dateArea.setBorder(titledBorder8);
                profilePanel.add(dateArea);
                profilePanel.add(new JLabel(" "));

                JTextArea subjectsArea = new JTextArea();
                subjectsArea.setFont(new Font("Times New Roman", Font.PLAIN, 16));
                subjectsArea.setEditable(false);
                subjectsArea.setLineWrap(true);
                subjectsArea.setWrapStyleWord(true);
                TitledBorder titledBorder9 = BorderFactory.createTitledBorder("Subjects Enrolled");
                titledBorder9.setTitleFont(new Font("Times New Roman", Font.BOLD, 16));
                subjectsArea.setBorder(titledBorder9);
                profilePanel.add(subjectsArea);
                profilePanel.add(new JLabel(" "));

                ArrayList<String> subjectIDs = CommonMethods.getStudentSubjectIDs(studentID);
                for(int i = 0; i < subjectIDs.size(); i++){
                    subjectsArea.append(CommonMethods.getSubjectTitle(subjectIDs.get(i)));

                    if(i != subjectIDs.size() - 1){
                        subjectsArea.append(", ");
                    }
                }

                if(subjectIDs.isEmpty()){
                    subjectsArea.append("-");
                }

                break;
            }
        }

        JScrollPane scrollPane = new JScrollPane(profilePanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        scrollPane.setBackground(new Color(230, 230, 250));

        viewProfileFrame.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        buttonPanel.setBackground(new Color(230, 230, 250));

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        backButton.setPreferredSize(new Dimension(75, 30));
        buttonPanel.add(backButton, BorderLayout.WEST);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        viewProfileFrame.add(buttonPanel, BorderLayout.SOUTH);

        backButton.addActionListener(e -> {
            viewProfileFrame.dispose();
            viewStudentInfo(id);
        });
        editEmail.addActionListener(e -> {
            viewProfileFrame.dispose();
            updateEmail(studentID, id);
        });
        editPhoneNo.addActionListener(e -> {
            viewProfileFrame.dispose();
            updatePhoneNo(studentID, id);
        });
        editAddress.addActionListener(e -> {
            viewProfileFrame.dispose();
            updateAddress(studentID, id);
        });
        editLevel.addActionListener(e -> {
            viewProfileFrame.dispose();
            editLevel(studentID, id);
        });
    }

    private static void updateEmail(String studentID, String id){
        JFrame updateEmailFrame = getFrame("Update Email");

        JPanel updateEmailPanel = new JPanel(new BorderLayout());
        updateEmailPanel.setBackground(new Color(230, 230, 250));
        JLabel updateEmailLabel = new JLabel("New Email:");
        updateEmailLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));

        JTextField updateEmailField = new JTextField();
        updateEmailField.setPreferredSize(new Dimension(250, 40));

        updateEmailPanel.add(updateEmailLabel, BorderLayout.WEST);
        updateEmailPanel.add(updateEmailField, BorderLayout.EAST);

        JPanel adjustPanel = new JPanel();
        adjustPanel.setLayout(new BoxLayout(adjustPanel, BoxLayout.Y_AXIS));
        adjustPanel.setBackground(new Color(230, 230, 250));
        adjustPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        adjustPanel.add(new JLabel(" "));
        adjustPanel.add(updateEmailPanel);

        updateEmailFrame.add(new JLabel(" "), BorderLayout.NORTH);
        updateEmailFrame.add(adjustPanel, BorderLayout.NORTH);

        JPanel button = new JPanel(new BorderLayout());
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        button.setBackground(new Color(230, 230, 250));

        JButton updateButton = new JButton("Update");
        updateButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        updateButton.setPreferredSize(new Dimension(90, 30));

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        backButton.setPreferredSize(new Dimension(75, 30));

        button.add(updateButton, BorderLayout.EAST);
        button.add(backButton, BorderLayout.WEST);
        updateEmailFrame.add(button, BorderLayout.SOUTH);

        updateButton.addActionListener(e -> {
            String email = updateEmailField.getText().strip();

            if(email.isEmpty()){
                JLabel error = new JLabel("Email cannot be empty.");
                error.setFont(new Font("Times New Roman", Font.BOLD, 16));
                JOptionPane.showMessageDialog(null, error, "Invalid Email", JOptionPane.ERROR_MESSAGE);
            }
            else {
                for (Student student : Main.studentList) {
                    if (student.getID().equals(studentID)) {
                        if (student.getEmail().equals(email)) {
                            JLabel error = new JLabel("You have inserted an old email.");
                            error.setFont(new Font("Times New Roman", Font.BOLD, 16));
                            JOptionPane.showMessageDialog(null, error, "Invalid Email", JOptionPane.ERROR_MESSAGE);
                        } else if (!email.contains("@")) {
                            JLabel error = new JLabel("Please insert a valid email.");
                            error.setFont(new Font("Times New Roman", Font.BOLD, 16));
                            JOptionPane.showMessageDialog(null, error, "Invalid Email", JOptionPane.ERROR_MESSAGE);
                        } else {
                            student.setEmail(email);
                            CommonMethods.writeFile(Main.filePaths[1], Main.studentList);
                            updateEmailFrame.dispose();
                            JLabel success = new JLabel("Email updated.");
                            success.setFont(new Font("Times New Roman", Font.BOLD, 16));
                            JOptionPane.showMessageDialog(null, success, "Successful Email Update", JOptionPane.INFORMATION_MESSAGE);
                            viewStudentProfile(studentID, id);
                        }
                        break;
                    }
                }
            }
        });

        backButton.addActionListener(e -> {
            updateEmailFrame.dispose();
            viewStudentProfile(studentID, id);
        });
    }

    private static void updatePhoneNo(String studentID, String id){

        JFrame updatePhoneNoFrame = getFrame("Update Contact Number");

        JPanel updatePhoneNoPanel = new JPanel(new BorderLayout());
        updatePhoneNoPanel.setBackground(new Color(230, 230, 250));

        JLabel updatePhoneNoLabel = new JLabel("New Contact Number:");
        updatePhoneNoLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));

        JTextField updatePhoneNoField = new JTextField();
        updatePhoneNoField.setPreferredSize(new Dimension(250, 40));

        updatePhoneNoPanel.add(updatePhoneNoLabel, BorderLayout.WEST);
        updatePhoneNoPanel.add(updatePhoneNoField, BorderLayout.EAST);

        JPanel adjustPanel = new JPanel();
        adjustPanel.setLayout(new BoxLayout(adjustPanel, BoxLayout.Y_AXIS));
        adjustPanel.setBackground(new Color(230, 230, 250));
        adjustPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        adjustPanel.add(new JLabel(" "));
        adjustPanel.add(updatePhoneNoPanel);

        updatePhoneNoFrame.add(new JLabel(" "), BorderLayout.NORTH);
        updatePhoneNoFrame.add(adjustPanel, BorderLayout.NORTH);

        JPanel button = new JPanel(new BorderLayout());
        button.setBackground(new Color(230, 230, 250));
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JButton updateButton = new JButton("Update");
        updateButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        updateButton.setPreferredSize(new Dimension(90, 30));

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        backButton.setPreferredSize(new Dimension(75, 30));

        button.add(updateButton, BorderLayout.EAST);
        button.add(backButton, BorderLayout.WEST);
        updatePhoneNoFrame.add(button, BorderLayout.SOUTH);

        updateButton.addActionListener(e -> {
            String phoneNo = updatePhoneNoField.getText().strip();

            if(phoneNo.isEmpty()){
                JLabel error = new JLabel("Phone number cannot be empty.");
                error.setFont(new Font("Times New Roman", Font.BOLD, 16));
                JOptionPane.showMessageDialog(null, error, "Invalid Phone Number", JOptionPane.ERROR_MESSAGE);
            }
            else {
                for (Student student : Main.studentList) {
                    if (student.getID().equals(studentID)) {
                        if (student.getPhoneNo().equals(phoneNo)) {
                            JLabel error = new JLabel("You have inserted an old contact number.");
                            error.setFont(new Font("Times New Roman", Font.BOLD, 16));
                            JOptionPane.showMessageDialog(null, error, "Invalid Contact Number", JOptionPane.ERROR_MESSAGE);
                        } else if (CommonMethods.isValidPhoneNo(phoneNo)) {
                            student.setPhoneNo(phoneNo);
                            CommonMethods.writeFile(Main.filePaths[1], Main.studentList);
                            updatePhoneNoFrame.dispose();
                            JLabel updated = new JLabel("Contact number updated.");
                            updated.setFont(new Font("Times New Roman", Font.BOLD, 16));
                            JOptionPane.showMessageDialog(null, updated, "Successful Contact Number Update", JOptionPane.INFORMATION_MESSAGE);
                            viewStudentProfile(studentID, id);
                        }
                        break;
                    }
                }
            }
        });

        backButton.addActionListener(e -> {
            updatePhoneNoFrame.dispose();
            viewStudentProfile(studentID, id);
        });
    }

    private static void updateAddress(String studentID, String id){
        JFrame updateAddressFrame = getFrame("Update Address");

        JPanel updateAddressPanel = new JPanel(new BorderLayout());
        updateAddressPanel.setBackground(new Color(230, 230, 250));

        JLabel updateAddressLabel = new JLabel("New Address:");
        updateAddressLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));

        JTextField updateAddressField = new JTextField();
        updateAddressField.setPreferredSize(new Dimension(250, 40));

        updateAddressPanel.add(updateAddressLabel, BorderLayout.WEST);
        updateAddressPanel.add(updateAddressField, BorderLayout.EAST);

        JPanel adjustPanel = new JPanel();
        adjustPanel.setLayout(new BoxLayout(adjustPanel, BoxLayout.Y_AXIS));
        adjustPanel.setBackground(new Color(230, 230, 250));
        adjustPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        adjustPanel.add(new JLabel(" "));
        adjustPanel.add(updateAddressPanel);

        updateAddressFrame.add(adjustPanel, BorderLayout.NORTH);

        JPanel button = new JPanel(new BorderLayout());
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        button.setBackground(new Color(230, 230, 250));

        JButton updateButton = new JButton("Update");
        updateButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        updateButton.setPreferredSize(new Dimension(90, 30));

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        backButton.setPreferredSize(new Dimension(75, 30));

        button.add(updateButton, BorderLayout.EAST);
        button.add(backButton, BorderLayout.WEST);
        updateAddressFrame.add(button, BorderLayout.SOUTH);

        updateButton.addActionListener(e -> {
            String address = updateAddressField.getText().strip();

            if(address.isEmpty()){
                JLabel error = new JLabel("Address cannot be empty.");
                error.setFont(new Font("Times New Roman", Font.BOLD, 16));
                JOptionPane.showMessageDialog(null, error, "Invalid Address", JOptionPane.ERROR_MESSAGE);
            }
            else {
                for (Student student : Main.studentList) {
                    if (student.getID().equals(studentID)) {
                        if (student.getAddress().replace("\"", "").equals(address)) {
                            JLabel error = new JLabel("You have inserted an old address.");
                            error.setFont(new Font("Times New Roman", Font.BOLD, 16));
                            JOptionPane.showMessageDialog(null, error, "Invalid Address", JOptionPane.ERROR_MESSAGE);
                        } else if (CommonMethods.isValidQuotedInput(address)) {
                            address = CommonMethods.modifyInput(address);
                            student.setAddress(address);
                            CommonMethods.writeFile(Main.filePaths[1], Main.studentList);
                            updateAddressFrame.dispose();
                            JLabel updated = new JLabel("Address updated.");
                            updated.setFont(new Font("Times New Roman", Font.BOLD, 16));
                            JOptionPane.showMessageDialog(null, updated, "Successful Address Update", JOptionPane.INFORMATION_MESSAGE);
                            viewStudentProfile(studentID, id);
                        }
                        break;
                    }
                }
            }
        });

        backButton.addActionListener(e -> {
            updateAddressFrame.dispose();
            viewStudentProfile(studentID, id);
        });
    }

    private static void editLevel(String studentID, String id){
        JFrame editLevelFrame = getFrame("Edit Level");

        JPanel editLevelPanel = new JPanel(new BorderLayout());
        editLevelPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        editLevelPanel.setBackground(new Color(230, 230, 250));

        JLabel editLevelLabel = new JLabel("Level:");
        editLevelLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));

        String[] levelOptions = {"--- Please Select ---", "F1", "F2", "F3", "F4", "F5"};
        JComboBox<String> levelComboBox = new JComboBox<>(levelOptions);
        levelComboBox.setBackground(new Color(230, 230, 250));
        levelComboBox.setFont(new Font("Times New Roman", Font.BOLD, 18));
        levelComboBox.setPreferredSize(new Dimension(220, 30));

        editLevelPanel.add(editLevelLabel, BorderLayout.WEST);
        editLevelPanel.add(levelComboBox, BorderLayout.EAST);

        JPanel adjustPanel = new JPanel();
        adjustPanel.setLayout(new BoxLayout(adjustPanel, BoxLayout.Y_AXIS));
        adjustPanel.setBackground(new Color(230, 230, 250));
        adjustPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        adjustPanel.add(new JLabel(" "));
        adjustPanel.add(editLevelPanel);

        editLevelFrame.add(adjustPanel, BorderLayout.NORTH);

        JPanel button = new JPanel(new BorderLayout());
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        button.setBackground(new Color(230, 230, 250));

        JButton updateButton = new JButton("Update");
        updateButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        updateButton.setPreferredSize(new Dimension(90, 30));

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        backButton.setPreferredSize(new Dimension(75, 30));

        button.add(updateButton, BorderLayout.EAST);
        button.add(backButton, BorderLayout.WEST);
        editLevelFrame.add(button, BorderLayout.SOUTH);

        updateButton.addActionListener(e -> {
            String level = (String) levelComboBox.getSelectedItem();

            if(level.equals("--- Please Select ---")){
                JLabel invalid = new JLabel("Please select a level.");
                invalid.setFont(new Font("Times New Roman", Font.BOLD, 16));
                JOptionPane.showMessageDialog(null, invalid, "Invalid Option", JOptionPane.ERROR_MESSAGE);
            }
            else {
                for (Student student : Main.studentList) {
                    if (student.getID().equals(studentID)) {
                        if (student.getLevel().equals(level)) {
                            JLabel error = new JLabel("Please select another level.");
                            error.setFont(new Font("Times New Roman", Font.BOLD, 16));
                            JOptionPane.showMessageDialog(null, error, "Invalid Option", JOptionPane.ERROR_MESSAGE);
                        }
                        else {
                            student.setLevel(level);
                            CommonMethods.writeFile(Main.filePaths[1], Main.studentList);
                            editLevelFrame.dispose();

                            for(Subject subject : Main.subjectList){
                                ArrayList<Enrolment> enrolmentList = CommonMethods.loadEnrolment(subject.getID());
                                for(Enrolment enrolment : enrolmentList){
                                    if(enrolment.getStudentID().equals(studentID)){
                                        enrolment.setLevel(level);
                                        CommonMethods.writeFile("txtTCMS/enr" + subject.getID() + ".txt", enrolmentList);
                                        break;
                                    }
                                }
                            }

                            JLabel updated = new JLabel("Level updated.");
                            updated.setFont(new Font("Times New Roman", Font.BOLD, 16));
                            JOptionPane.showMessageDialog(null, updated, "Successful Level Update", JOptionPane.INFORMATION_MESSAGE);
                            viewStudentProfile(studentID, id);
                        }
                        break;
                    }
                }
            }
        });

        backButton.addActionListener(e -> {
            editLevelFrame.dispose();
            viewStudentProfile(studentID, id);
        });
    }
}