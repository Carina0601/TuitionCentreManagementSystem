import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class HomePage extends JFrame {

    private JPanel HomePage;
    private JButton scheduleButton;
    private JButton requestFormButton;
    private JButton paymentButton;
    private JButton profileButton;
    private JButton logoutButton;
    private JPanel selection;
    private JLabel mainMenu;


    public HomePage(String studentID){

        setTitle("HomePage");
        setContentPane(HomePage);
        setMinimumSize(new Dimension(550, 450));
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        pack();
        setVisible(true);

        addWindowListener(new WindowAdapter() {
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
                else{
                    setVisible(true);
                }
            }
        });

        // Button Style
        scheduleButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        requestFormButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        paymentButton.setFont(new Font("Times New Roman", Font.BOLD,14));
        profileButton.setFont(new Font("Times New Roman", Font.BOLD ,14));
        logoutButton.setFont(new Font("Times New Roman",Font.BOLD,14));

        // title font style and size
        mainMenu.setFont(new Font("Times New Roman", Font.BOLD, 24));


        requestFormButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                String [] options = {"Enrol", "Withdraw", "Change", "View request", "Back"};
                JLabel chooseRequest = new JLabel("Choose your request type.");
                chooseRequest.setFont(new Font("Times New Roman", Font.BOLD, 16));
                while(true) {
                    int requestTypeIndex = JOptionPane.showOptionDialog(
                            null,
                            chooseRequest,
                            "Request type",
                            JOptionPane.DEFAULT_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            options,
                            options[0]
                    );

                    String requestType;
                    if(requestTypeIndex == 4 || requestTypeIndex == -1){
                        setVisible(true);
                        break;
                    }
                    else if (requestTypeIndex == 2) {

                        requestType = "C";
                        String requestID = CommonMethods.getNextID("CR", Main.requestList);
                        String oldSubjectID = "";
                        String newSubjectID = "";

                        while (true) {
                            JLabel enterID = new JLabel("Enter old subject ID: ");
                            enterID.setFont(new Font("Times New Roman", Font.BOLD, 16));
                            oldSubjectID = JOptionPane.showInputDialog(null, enterID);

                            if (oldSubjectID == null) {
                                break;
                            }
                            oldSubjectID = oldSubjectID.strip().toUpperCase();
                            // check if id is valid or not
                            if (!CommonMethods.isValidID(Main.subjectList, oldSubjectID)) {
                                continue;
                            }
                            if (!CommonMethods.isEnrolled(oldSubjectID, studentID)) {
                                JLabel notEnrolled = new JLabel("You have not been enrolled in this subject.");
                                notEnrolled.setFont(new Font("Times New Roman", Font.BOLD, 16));
                                JOptionPane.showMessageDialog(null, notEnrolled, "Not Enrolled", JOptionPane.ERROR_MESSAGE);
                                continue;
                            }

                            while (true) {
                                JLabel enterID1 = new JLabel("Enter new subject ID: ");
                                enterID1.setFont(new Font("Times New Roman", Font.BOLD, 16));
                                newSubjectID = JOptionPane.showInputDialog(null, enterID1);

                                if (newSubjectID == null) {
                                    break;
                                }

                                newSubjectID = newSubjectID.strip().toUpperCase();
                                if (!CommonMethods.isValidID(Main.subjectList, newSubjectID)) {
                                    continue;
                                }
                                if (CommonMethods.isEnrolled(newSubjectID, studentID)) {
                                    JLabel enrolled = new JLabel("You have been enrolled in this subject.");
                                    enrolled.setFont(new Font("Times New Roman", Font.BOLD, 16));
                                    JOptionPane.showMessageDialog(null, enrolled, "Enrolled", JOptionPane.ERROR_MESSAGE);
                                    continue;
                                }
                                if(!isNewRequest(studentID, "C", oldSubjectID, newSubjectID)){
                                    continue;
                                }
                                LocalDate today = LocalDate.now();
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");

                                String date = today.format(formatter);

                                // New object
                                ChangeRequest newRequest = new ChangeRequest(requestID, studentID, requestType, oldSubjectID, newSubjectID, "P", date, "A");
                                // add to request list
                                Main.requestList.add(newRequest);

                                CommonMethods.appendNewLine(Main.filePaths[5], newRequest);

                                JLabel label = new JLabel("Request added successfully!");
                                label.setFont(new Font("Times New Roman", Font.BOLD, 16));
                                JOptionPane.showMessageDialog(null, label, "Request Information", JOptionPane.INFORMATION_MESSAGE);
                                break;
                            }
                            break;
                        }

                    } else if (requestTypeIndex == 0) {
                        requestType = "E";
                        String requestID = CommonMethods.getNextID("CR", Main.requestList);
                        String newSubjectID = "";

                        while (true) {
                            JLabel enterID1 = new JLabel("Enter new subject ID: ");
                            enterID1.setFont(new Font("Times New Roman", Font.BOLD, 16));
                            newSubjectID = JOptionPane.showInputDialog(null, enterID1);

                            if (newSubjectID == null) {
                                break;
                            }

                            newSubjectID = newSubjectID.strip().toUpperCase();
                            if (!CommonMethods.isValidID(Main.subjectList, newSubjectID)) {
                                continue;
                            }
                            if (CommonMethods.isEnrolled(newSubjectID, studentID)) {
                                JLabel enrolled = new JLabel("You have been enrolled in this subject.");
                                enrolled.setFont(new Font("Times New Roman", Font.BOLD, 16));
                                JOptionPane.showMessageDialog(null, enrolled, "Enrolled", JOptionPane.ERROR_MESSAGE);
                                continue;
                            }
                            boolean reachedMax = false;
                            for(Student student : Main.studentList){
                                if(student.getID().equals(studentID) && student.getNoOfSubjects() == 3){
                                    reachedMax = true;
                                }
                            }
                            if(reachedMax){
                                JLabel max = new JLabel("You have reached the maximum number of subject enrolments. You can choose to change a subject.");
                                max.setFont(new Font("Times New Roman", Font.BOLD, 16));
                                JOptionPane.showMessageDialog(null, max, "Maximum Enrolment", JOptionPane.ERROR_MESSAGE);
                                continue;
                            }
                            if(!isNewRequest(studentID, "E", "*", newSubjectID)){
                                continue;
                            }
                            LocalDate today = LocalDate.now();
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");

                            String date = today.format(formatter);
                            // New object
                            ChangeRequest newRequest = new ChangeRequest(requestID, studentID, requestType, "*", newSubjectID, "P", date, "A");
                            // add to request list
                            Main.requestList.add(newRequest);

                            CommonMethods.appendNewLine(Main.filePaths[5], newRequest);

                            JLabel label = new JLabel("Request added successfully!");
                            label.setFont(new Font("Times New Roman", Font.BOLD, 16));
                            JOptionPane.showMessageDialog(null, label, "Request Information", JOptionPane.INFORMATION_MESSAGE);
                            break;
                        }
                    }
                    else if (requestTypeIndex == 1) {
                        requestType = "W";
                        String requestID = CommonMethods.getNextID("CR", Main.requestList);
                        String oldSubjectID = "";

                        while (true) {
                            JLabel enterID = new JLabel("Enter old subject ID: ");
                            enterID.setFont(new Font("Times New Roman", Font.BOLD, 16));
                            oldSubjectID = JOptionPane.showInputDialog(null, enterID);

                            if (oldSubjectID == null) {
                                break;
                            }

                            oldSubjectID = oldSubjectID.strip().toUpperCase();
                            // check if id is valid or not
                            if (!CommonMethods.isValidID(Main.subjectList, oldSubjectID)) {
                                continue;
                            }
                            if (!CommonMethods.isEnrolled(oldSubjectID, studentID)) {
                                JLabel notEnrolled = new JLabel("You have not been enrolled in this subject.");
                                notEnrolled.setFont(new Font("Times New Roman", Font.BOLD, 16));
                                JOptionPane.showMessageDialog(null, notEnrolled, "Not Enrolled", JOptionPane.ERROR_MESSAGE);
                                continue;
                            }
                            if(!isNewRequest(studentID, "W", oldSubjectID, "*")){
                                continue;
                            }
                            LocalDate today = LocalDate.now();
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");

                            String date = today.format(formatter);
                            // New object
                            ChangeRequest newRequest = new ChangeRequest(requestID, studentID, requestType, oldSubjectID, "*", "P", date, "A");
                            // add to request list
                            Main.requestList.add(newRequest);

                            CommonMethods.appendNewLine(Main.filePaths[5], newRequest);

                            JLabel label = new JLabel("Request added successfully!");
                            label.setFont(new Font("Times New Roman", Font.BOLD, 16));
                            JOptionPane.showMessageDialog(null, label, "Request Information", JOptionPane.INFORMATION_MESSAGE);
                            break;
                        }
                    }
                    else if (requestTypeIndex == 3) {
                        changeRequest(studentID);
                        break;
                    }
                }
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JLabel confirmLogout = new JLabel("Are you sure you want to log out?");
                confirmLogout.setFont(new Font("Times New Roman", Font.BOLD, 16));
                int result = JOptionPane.showConfirmDialog(null, confirmLogout, "Confirm Log Out", JOptionPane.YES_NO_OPTION);

                if(result == JOptionPane.YES_OPTION) {
                    dispose();
                    Main.showMainMenu();
                }

            }
        });

        scheduleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                ArrayList<String> subjectIDs = CommonMethods.getStudentSubjectIDs(studentID);

                if(subjectIDs.isEmpty()){
                    JLabel error = new JLabel("You have not been enrolled in any subject.");
                    error.setFont(new Font("Times New Roman", Font.BOLD, 16));
                    JOptionPane.showMessageDialog(null, error, "Not Enrolled", JOptionPane.ERROR_MESSAGE);
                    new HomePage(studentID);
                }
                else {

                    JFrame studentScheduleFrame = getFrame("View Subjects");

                    JPanel schedulePanel = new JPanel();
                    schedulePanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
                    schedulePanel.setBackground(new Color(224, 255, 255));
                    schedulePanel.setLayout(new BoxLayout(schedulePanel, BoxLayout.Y_AXIS));

                    for (String subjectID : subjectIDs) {
                        for (Subject subject : Main.subjectList) {

                            if (subject.getID().equals(subjectID)) {
                                JTextArea subjectDetails = new JTextArea();
                                subjectDetails.setFont(new Font("Times New Roman", Font.PLAIN, 16));
                                subjectDetails.setEditable(false);
                                subjectDetails.setLineWrap(true);
                                subjectDetails.setWrapStyleWord(true);

                                TitledBorder titledBorder = BorderFactory.createTitledBorder("ID: " + subject.getID());
                                titledBorder.setTitleFont(new Font("Times New Roman", Font.BOLD, 16));
                                subjectDetails.setBorder(titledBorder);

                                subjectDetails.append("Title: " + subject.getTitle() + "\n");
                                String scheduleText = subject.getSchedule().equals("*") ? "Not Available" : subject.getSchedule().replace("\"","");
                                subjectDetails.append("Schedule: " + scheduleText + "\n");
                                String notesLink = subject.getNotesLink().equals("&") ? "Not Available" : subject.getNotesLink().replace("\"","");
                                subjectDetails.append("Notes Link: " + notesLink);

                                schedulePanel.add(subjectDetails);
                                schedulePanel.add(new JLabel(" "));
                                break;
                            }
                        }
                    }

                    JScrollPane scrollPane = new JScrollPane(schedulePanel);
                    scrollPane.setBackground(new Color(224, 255, 255));
                    scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

                    studentScheduleFrame.add(scrollPane, BorderLayout.CENTER);

                    JPanel button = new JPanel(new BorderLayout());
                    button.setBackground(new Color(224, 255, 255));
                    button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

                    JButton backButton = new JButton("Back");
                    backButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
                    backButton.setPreferredSize(new Dimension(75, 30));
                    button.add(backButton, BorderLayout.WEST);

                    studentScheduleFrame.add(button, BorderLayout.SOUTH);

                    backButton.addActionListener(ex -> {
                        JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(backButton);
                        currentFrame.dispose();

                        setVisible(true);
                    });
                }
           }
        });

        paymentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                getStudentBillsFrame(studentID);
            }
        });

        profileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                studentProfile(studentID);
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
        frame.getContentPane().setBackground(new Color(224, 255, 255));

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

    private static boolean isNewRequest(String studentID, String typeOfRequest, String oldSubjectID, String newSubjectID){
        JLabel oldRequest = new JLabel("You have already made this request and it is still pending.");
        oldRequest.setFont(new Font("Times New Roman", Font.BOLD, 16));

        for(ChangeRequest request : Main.requestList){
            if(request.getStudentID().equals(studentID) && request.getStatus().equals("P") && request.getTypeOfRequest().equals(typeOfRequest)
            && request.getOldSubjectID().equals(oldSubjectID) && request.getNewSubjectID().equals(newSubjectID) && request.getActivity().equals("A")) {
                JOptionPane.showMessageDialog(null, oldRequest, "Invalid Request", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        return true;
    }

    private static void changeRequest(String studentID) {
        // store all the student's requests
        ArrayList<ChangeRequest> requests = new ArrayList<>();

        // loop the request
        for(ChangeRequest request : Main.requestList){
            if (request.getStudentID().equals(studentID) && request.getActivity().equals("A")){
                requests.add(request);
            }
        }

        if (requests.isEmpty()){
            JLabel noRequest = new JLabel("No request to be viewed. ");
            noRequest.setFont(new Font("Times New Roman", Font.BOLD, 16));
            JOptionPane.showMessageDialog(null, noRequest, "No Request", JOptionPane.ERROR_MESSAGE);
            new HomePage(studentID);
        }
        else {
            JFrame requestFrame = getFrame("View Request");

            JPanel requestPanel = new JPanel();
            requestPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
            requestPanel.setBackground(new Color(224, 255, 255));
            requestPanel.setLayout(new BoxLayout(requestPanel, BoxLayout.Y_AXIS));

            for (ChangeRequest request : requests) {
                JTextArea requestDetail = new JTextArea();
                requestDetail.setFont(new Font("Times New Roman", Font.PLAIN, 16));
                requestDetail.setEditable(false);
                requestDetail.setLineWrap(true);
                requestDetail.setWrapStyleWord(true);

                TitledBorder titledBorder = BorderFactory.createTitledBorder("ID: " + request.getID());
                titledBorder.setTitleFont(new Font("Times New Roman", Font.BOLD, 16));
                requestDetail.setBorder(titledBorder);

                String typeOfRequest = "";
                if (request.getTypeOfRequest().equals("E")) {
                    typeOfRequest = "Subject Enrolment ";
                } else if (request.getTypeOfRequest().equals("W")) {
                    typeOfRequest = "Subject Withdraw";
                } else if (request.getTypeOfRequest().equals("C")) {
                    typeOfRequest = "Change Subject";
                }

                requestDetail.append("Type of request: " + typeOfRequest + "\n");

                if (request.getTypeOfRequest().equals("C")) {
                    requestDetail.append("Previous subject : " + CommonMethods.getSubjectTitle(request.getOldSubjectID()) +"\n");
                    requestDetail.append("New subject : " + CommonMethods.getSubjectTitle(request.getNewSubjectID())+"\n");
                } else if (request.getTypeOfRequest().equals("E")) {
                    requestDetail.append("New subject : " + CommonMethods.getSubjectTitle(request.getNewSubjectID())+"\n");
                } else {
                    requestDetail.append("Previous subject : " + CommonMethods.getSubjectTitle(request.getOldSubjectID())+"\n");
                }

                String status = "";
                if (request.getStatus().equals("P")) {
                    status = "Pending";
                }
                else if (request.getStatus().equals("R")) {
                    status = "Rejected";
                }
                else {
                    status = "Approved";
                }

                requestDetail.append("Status: " + status+"\n");


                requestDetail.append("Date: " + request.getDate());
                requestPanel.add(requestDetail);

                if(request.getStatus().equals("P")) {
                    JButton deleteButton = new JButton("Delete");
                    deleteButton.setFont(new Font("Times New Roman", Font.BOLD, 16));

                    JPanel deletePanel = new JPanel(new BorderLayout());
                    deletePanel.setBackground(new Color(224, 255, 255));
                    deletePanel.add(deleteButton, BorderLayout.EAST);
                    requestPanel.add(deletePanel);

                    deleteButton.addActionListener(e -> {
                        JLabel confirm = new JLabel("Are you sure you want to delete? ");
                        confirm.setFont(new Font("Times New Roman", Font.BOLD, 16));
                        int result = JOptionPane.showConfirmDialog(null, confirm, "Confirm Deletion ", JOptionPane.YES_NO_OPTION);
                        if (result == JOptionPane.YES_OPTION) {
                            request.setActivity("I");

                            // rewrite file
                            CommonMethods.writeFile(Main.filePaths[5], Main.requestList);
                            requestFrame.dispose();
                            JLabel deleted = new JLabel("Request deleted successfully!");
                            deleted.setFont(new Font("Times New Roman", Font.BOLD, 16));
                            JOptionPane.showMessageDialog(null, deleted, "Successful Deletion", JOptionPane.INFORMATION_MESSAGE);
                            changeRequest(studentID);
                        }
                    });
                }
                requestPanel.add(new JLabel(" "));
            }

            JScrollPane scrollPane = new JScrollPane(requestPanel);
            scrollPane.setBackground(new Color(224, 255, 255));
            scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

            requestFrame.add(scrollPane, BorderLayout.CENTER);

            JPanel button = new JPanel(new BorderLayout());
            button.setBackground(new Color(224, 255, 255));
            button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

            JButton backButton = new JButton("Back");
            backButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
            backButton.setPreferredSize(new Dimension(75, 30));
            button.add(backButton, BorderLayout.WEST);

            requestFrame.add(button, BorderLayout.SOUTH);

            backButton.addActionListener(ex -> {
                JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(backButton);
                currentFrame.dispose();

                new HomePage(studentID);

            });
        }
    }

    private static void getStudentBillsFrame(String studentID){
        if(!CommonMethods.isBillExist(studentID)){
            JLabel noBills = new JLabel("No bills to be viewed.");
            noBills.setFont(new Font("Times New Roman", Font.BOLD, 16));
            JOptionPane.showMessageDialog(null, noBills, "No Bills", JOptionPane.ERROR_MESSAGE);
            new HomePage(studentID);
        }
        else {
            JFrame studentBillsReceiptsFrame = getFrame("View Bills");

            JPanel billsReceiptsPanel = new JPanel();
            billsReceiptsPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
            billsReceiptsPanel.setBackground(new Color(224, 255, 255));
            billsReceiptsPanel.setLayout(new BoxLayout(billsReceiptsPanel, BoxLayout.Y_AXIS));

            for (Bill bill : Main.billList) {
                if (bill.getStudentID().equals(studentID)) {
                    JTextArea billDetails = new JTextArea();
                    billDetails.setFont(new Font("Times New Roman", Font.PLAIN, 16));
                    billDetails.setEditable(false);
                    billDetails.setLineWrap(true);
                    billDetails.setWrapStyleWord(true);
                    TitledBorder titledBorder = BorderFactory.createTitledBorder("ID: " + bill.getID());
                    titledBorder.setTitleFont(new Font("Times New Roman", Font.BOLD, 16));
                    billDetails.setBorder(titledBorder);

                    billDetails.append("Bill Date: " + bill.getDate());
                    billDetails.append("\nSubjects Involved: " + CommonMethods.getSubjectTitle(bill.getSubjectID1()));
                    if (!bill.getSubjectID2().equals("*")) {
                        billDetails.append(", " + CommonMethods.getSubjectTitle(bill.getSubjectID2()));
                    }
                    if (!bill.getSubjectID3().equals("*")) {
                        billDetails.append(", " + CommonMethods.getSubjectTitle(bill.getSubjectID3()));
                    }

                    billDetails.append("\nTotal Fees: " + bill.getFees());

                    String status = (bill.getStatus().equals("U")) ? "Unpaid" : "Paid";
                    billDetails.append("\nStatus: " + status);

                    billsReceiptsPanel.add(billDetails);
                    billsReceiptsPanel.add(new JLabel(" "));
                }
            }

            JScrollPane scrollPane = new JScrollPane(billsReceiptsPanel);
            scrollPane.setBackground(new Color(224, 255, 255));
            scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

            studentBillsReceiptsFrame.add(scrollPane, BorderLayout.CENTER);

            JPanel button = new JPanel(new BorderLayout());
            button.setBackground(new Color(224, 255, 255));
            button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

            JButton backButton = new JButton("Back");
            backButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
            backButton.setPreferredSize(new Dimension(75, 30));
            button.add(backButton, BorderLayout.WEST);

            studentBillsReceiptsFrame.add(button, BorderLayout.SOUTH);

            backButton.addActionListener(e -> {
                studentBillsReceiptsFrame.dispose();
                new HomePage(studentID);
            });
        }
    }

    private static void studentProfile(String studentID){

        JFrame viewProfileFrame = CommonMethods.getJFrame("View Profile");

        JPanel profilePanel = new JPanel();
        profilePanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        profilePanel.setBackground(new Color(224, 255, 255));
        profilePanel.setLayout(new BoxLayout(profilePanel, BoxLayout.Y_AXIS));

        JPanel editEmailPanel = new JPanel(new BorderLayout());
        editEmailPanel.setBackground(new Color(224, 255, 255));
        JButton editEmail = new JButton("Edit");
        editEmail.setFont(new Font("Times New Roman", Font.BOLD, 14));
        editEmail.setPreferredSize(new Dimension(75, 30));
        editEmailPanel.add(editEmail, BorderLayout.WEST);

        JPanel editPhoneNoPanel = new JPanel(new BorderLayout());
        editPhoneNoPanel.setBackground(new Color(224, 255, 255));
        JButton editPhoneNo = new JButton("Edit");
        editPhoneNo.setFont(new Font("Times New Roman", Font.BOLD, 14));
        editPhoneNo.setPreferredSize(new Dimension(75, 30));
        editPhoneNoPanel.add(editPhoneNo, BorderLayout.WEST);

        JPanel editAddressPanel = new JPanel(new BorderLayout());
        editAddressPanel.setBackground(new Color(224, 255, 255));
        JButton editAddress = new JButton("Edit");
        editAddress.setFont(new Font("Times New Roman", Font.BOLD, 14));
        editAddress.setPreferredSize(new Dimension(75, 30));
        editAddressPanel.add(editAddress, BorderLayout.WEST);

        JPanel editUsernamePanel = new JPanel(new BorderLayout());
        editUsernamePanel.setBackground(new Color(224, 255, 255));
        JButton editUsername = new JButton("Edit");
        editUsername.setFont(new Font("Times New Roman", Font.BOLD, 14));
        editUsername.setPreferredSize(new Dimension(75, 30));
        editUsernamePanel.add(editUsername, BorderLayout.WEST);

        JPanel changePasswordPanel = new JPanel(new BorderLayout());
        changePasswordPanel.setBackground(new Color(224, 255, 255));
        JButton changePassword = new JButton("Change Password");
        changePassword.setFont(new Font("Times New Roman", Font.BOLD, 14));
        changePassword.setPreferredSize(new Dimension(150, 30));
        changePasswordPanel.add(changePassword, BorderLayout.WEST);

        JLabel title = new JLabel("User Profile", SwingConstants.CENTER);
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

                JTextArea usernameArea = new JTextArea(attributes[6].replace("\"",""));
                usernameArea.setFont(new Font("Times New Roman", Font.PLAIN, 16));
                usernameArea.setEditable(false);
                usernameArea.setLineWrap(true);
                usernameArea.setWrapStyleWord(true);
                TitledBorder titledBorder6 = BorderFactory.createTitledBorder("Username");
                titledBorder6.setTitleFont(new Font("Times New Roman", Font.BOLD, 16));
                usernameArea.setBorder(titledBorder6);
                profilePanel.add(usernameArea);
                profilePanel.add(editUsernamePanel);
                profilePanel.add(new JLabel(" "));

                profilePanel.add(changePasswordPanel);
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
        scrollPane.setBackground(new Color(224, 255, 255));

        viewProfileFrame.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        buttonPanel.setBackground(new Color(224, 255, 255));

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        backButton.setPreferredSize(new Dimension(75, 30));
        buttonPanel.add(backButton, BorderLayout.WEST);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        viewProfileFrame.add(buttonPanel, BorderLayout.SOUTH);

        backButton.addActionListener(e -> {
            viewProfileFrame.dispose();
            new HomePage(studentID);
        });
        editEmail.addActionListener(e -> {
            viewProfileFrame.dispose();
            updateEmail(studentID);
        });
        editPhoneNo.addActionListener(e -> {
            viewProfileFrame.dispose();
            updatePhoneNo(studentID);
        });
        editAddress.addActionListener(e -> {
            viewProfileFrame.dispose();
            updateAddress(studentID);
        });
        editUsername.addActionListener(e -> {
            viewProfileFrame.dispose();
            changeUsername(studentID);
        });
        changePassword.addActionListener(e -> {
            viewProfileFrame.dispose();
            checkPassword(studentID);
        });
    }

    private static void updateEmail(String studentID){
        JFrame updateEmailFrame = getFrame("Update Email");

        JPanel updateEmailPanel = new JPanel(new BorderLayout());
        updateEmailPanel.setBackground(new Color(224, 255, 255));
        JLabel updateEmailLabel = new JLabel("New Email:");
        updateEmailLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));

        JTextField updateEmailField = new JTextField();
        updateEmailField.setPreferredSize(new Dimension(250, 40));

        updateEmailPanel.add(updateEmailLabel, BorderLayout.WEST);
        updateEmailPanel.add(updateEmailField, BorderLayout.EAST);

        JPanel adjustPanel = new JPanel();
        adjustPanel.setLayout(new BoxLayout(adjustPanel, BoxLayout.Y_AXIS));
        adjustPanel.setBackground(new Color(224, 255, 255));
        adjustPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        adjustPanel.add(new JLabel(" "));
        adjustPanel.add(updateEmailPanel);

        updateEmailFrame.add(new JLabel(" "), BorderLayout.NORTH);
        updateEmailFrame.add(adjustPanel, BorderLayout.NORTH);

        JPanel button = new JPanel(new BorderLayout());
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        button.setBackground(new Color(224, 255, 255));

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
                JOptionPane.showMessageDialog(null, error, "Invalid Email.", JOptionPane.ERROR_MESSAGE);
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
                            studentProfile(studentID);
                        }
                        break;
                    }
                }
            }
        });

        backButton.addActionListener(e -> {
            updateEmailFrame.dispose();
            studentProfile(studentID);
        });
    }

    private static void updatePhoneNo(String studentID){

        JFrame updatePhoneNoFrame = getFrame("Update Contact Number");

        JPanel updatePhoneNoPanel = new JPanel(new BorderLayout());
        updatePhoneNoPanel.setBackground(new Color(224, 255, 255));

        JLabel updatePhoneNoLabel = new JLabel("New Contact Number:");
        updatePhoneNoLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));

        JTextField updatePhoneNoField = new JTextField();
        updatePhoneNoField.setPreferredSize(new Dimension(250, 40));

        updatePhoneNoPanel.add(updatePhoneNoLabel, BorderLayout.WEST);
        updatePhoneNoPanel.add(updatePhoneNoField, BorderLayout.EAST);

        JPanel adjustPanel = new JPanel();
        adjustPanel.setLayout(new BoxLayout(adjustPanel, BoxLayout.Y_AXIS));
        adjustPanel.setBackground(new Color(224, 255, 255));
        adjustPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        adjustPanel.add(new JLabel(" "));
        adjustPanel.add(updatePhoneNoPanel);

        updatePhoneNoFrame.add(new JLabel(" "), BorderLayout.NORTH);
        updatePhoneNoFrame.add(adjustPanel, BorderLayout.NORTH);

        JPanel button = new JPanel(new BorderLayout());
        button.setBackground(new Color(224, 255, 255));
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
                            studentProfile(studentID);
                        }
                        break;
                    }
                }
            }
        });

        backButton.addActionListener(e -> {
            updatePhoneNoFrame.dispose();
            studentProfile(studentID);
        });
    }

    private static void updateAddress(String studentID){
        JFrame updateAddressFrame = getFrame("Update Address");

        JPanel updateAddressPanel = new JPanel(new BorderLayout());
        updateAddressPanel.setBackground(new Color(224, 255, 255));

        JLabel updateAddressLabel = new JLabel("New Address:");
        updateAddressLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));

        JTextField updateAddressField = new JTextField();
        updateAddressField.setPreferredSize(new Dimension(250, 40));

        updateAddressPanel.add(updateAddressLabel, BorderLayout.WEST);
        updateAddressPanel.add(updateAddressField, BorderLayout.EAST);

        JPanel adjustPanel = new JPanel();
        adjustPanel.setLayout(new BoxLayout(adjustPanel, BoxLayout.Y_AXIS));
        adjustPanel.setBackground(new Color(224, 255, 255));
        adjustPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        adjustPanel.add(new JLabel(" "));
        adjustPanel.add(updateAddressPanel);

        updateAddressFrame.add(adjustPanel, BorderLayout.NORTH);

        JPanel button = new JPanel(new BorderLayout());
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        button.setBackground(new Color(224, 255, 255));

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
                JOptionPane.showMessageDialog(null, error, "Invalid Address.", JOptionPane.ERROR_MESSAGE);
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
                            studentProfile(studentID);
                        }
                        break;
                    }
                }
            }
        });

        backButton.addActionListener(e -> {
            updateAddressFrame.dispose();
            studentProfile(studentID);
        });
    }

    private static void changeUsername(String studentID){

        JFrame changeUsernameFrame = getFrame("Change Username");

        JPanel changeUsernamePanel = new JPanel(new BorderLayout());
        changeUsernamePanel.setBackground(new Color(224, 255, 255));

        JLabel changeUsernameLabel = new JLabel("New Username:");
        changeUsernameLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));

        JTextField changeUsernameField = new JTextField();
        changeUsernameField.setPreferredSize(new Dimension(250, 40));

        changeUsernamePanel.add(changeUsernameLabel, BorderLayout.WEST);
        changeUsernamePanel.add(changeUsernameField, BorderLayout.EAST);

        JPanel adjustPanel = new JPanel();
        adjustPanel.setLayout(new BoxLayout(adjustPanel, BoxLayout.Y_AXIS));
        adjustPanel.setBackground(new Color(224, 255, 255));
        adjustPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        adjustPanel.add(new JLabel(" "));
        adjustPanel.add(changeUsernamePanel);

        changeUsernameFrame.add(new JLabel(" "));
        changeUsernameFrame.add(adjustPanel, BorderLayout.NORTH);

        JPanel button = new JPanel(new BorderLayout());
        button.setBackground(new Color(224, 255, 255));
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JButton updateButton = new JButton("Update");
        updateButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        updateButton.setPreferredSize(new Dimension(90, 30));

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        backButton.setPreferredSize(new Dimension(75, 30));

        button.add(updateButton, BorderLayout.EAST);
        button.add(backButton, BorderLayout.WEST);
        changeUsernameFrame.add(button, BorderLayout.SOUTH);

        updateButton.addActionListener(e -> {
            String username = changeUsernameField.getText().strip();

            if(username.isEmpty()){
                JLabel error = new JLabel("Username cannot be empty.");
                error.setFont(new Font("Times New Roman", Font.BOLD, 16));
                JOptionPane.showMessageDialog(null, error, "Invalid Username.", JOptionPane.ERROR_MESSAGE);
            }
            else {
                for (Student student : Main.studentList) {
                    if (student.getID().equals(studentID)) {
                        if (student.getUsername().replace("\"", "").equals(username)) {
                            JLabel error = new JLabel("You have inserted an old username.");
                            error.setFont(new Font("Times New Roman", Font.BOLD, 16));
                            JOptionPane.showMessageDialog(null, error, "Invalid Username", JOptionPane.ERROR_MESSAGE);
                        } else {
                            boolean valid = true;
                            if (!CommonMethods.isValidUsername(username)) {
                                valid = false;
                            }
                            if (!CommonMethods.isValidQuotedInput(username)) {
                                valid = false;
                            }

                            if (valid) {
                                username = CommonMethods.modifyInput(username);
                                student.setUsername(username);
                                CommonMethods.writeFile(Main.filePaths[1], Main.studentList);
                                changeUsernameFrame.dispose();
                                JLabel updated = new JLabel("Username updated.");
                                updated.setFont(new Font("Times New Roman", Font.BOLD, 16));
                                JOptionPane.showMessageDialog(null, updated, "Successful Username Update", JOptionPane.INFORMATION_MESSAGE);
                                studentProfile(studentID);
                            }
                        }
                        break;
                    }
                }
            }
        });

        backButton.addActionListener(e -> {
            changeUsernameFrame.dispose();
            studentProfile(studentID);
        });
    }

    private static void checkPassword(String studentID){

        JFrame changePasswordFrame = getFrame("Check Current Password");

        JPanel changePasswordPanel = new JPanel(new BorderLayout());
        changePasswordPanel.setBackground(new Color(224, 255, 255));

        JLabel changePasswordLabel = new JLabel("Current Password:");
        changePasswordLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));

        JPasswordField changePasswordField = new JPasswordField();
        changePasswordField.setPreferredSize(new Dimension(250, 40));

        changePasswordPanel.add(changePasswordLabel, BorderLayout.WEST);
        changePasswordPanel.add(changePasswordField, BorderLayout.EAST);

        JPanel adjustPanel = new JPanel();
        adjustPanel.setLayout(new BoxLayout(adjustPanel, BoxLayout.Y_AXIS));
        adjustPanel.setBackground(new Color(224, 255, 255));
        adjustPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        adjustPanel.add(new JLabel(" "));
        adjustPanel.add(changePasswordPanel);

        changePasswordFrame.add(new JLabel(" "), BorderLayout.NORTH);
        changePasswordFrame.add(adjustPanel, BorderLayout.NORTH);

        JPanel button = new JPanel(new BorderLayout());
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        button.setBackground(new Color(224, 255, 255));

        JButton checkButton = new JButton("Next");
        checkButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        checkButton.setPreferredSize(new Dimension(75, 30));

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        backButton.setPreferredSize(new Dimension(75, 30));

        button.add(checkButton, BorderLayout.EAST);
        button.add(backButton, BorderLayout.WEST);
        changePasswordFrame.add(button, BorderLayout.SOUTH);

        checkButton.addActionListener(e -> {
            char[] passwordChars= changePasswordField.getPassword();
            String password = new String(passwordChars).strip();

            if(password.isEmpty()){
                JLabel error = new JLabel("Password cannot be empty.");
                error.setFont(new Font("Times New Roman", Font.BOLD, 16));
                JOptionPane.showMessageDialog(null, error, "Invalid Password", JOptionPane.ERROR_MESSAGE);
            }
            else {
                for (Student student : Main.studentList) {
                    if (student.getID().equals(studentID)) {
                        if (student.getPassword().replace("\"", "").equals(password)) {
                            changePasswordFrame.dispose();
                            JLabel correct = new JLabel("Password correct.");
                            correct.setFont(new Font("Times New Roman", Font.BOLD, 16));
                            JOptionPane.showMessageDialog(null, correct, "Valid Password", JOptionPane.INFORMATION_MESSAGE);
                            changePassword(studentID);
                        } else {
                            JLabel incorrect = new JLabel("Password incorrect.");
                            incorrect.setFont(new Font("Times New Roman", Font.BOLD, 16));
                            JOptionPane.showMessageDialog(null, incorrect, "Invalid Password", JOptionPane.ERROR_MESSAGE);
                        }
                        break;
                    }
                }
            }
        });

        backButton.addActionListener(e -> {
            changePasswordFrame.dispose();
            new HomePage(studentID);
        });
    }

    private static void changePassword(String studentID){

        JFrame changePasswordFrame = getFrame("Change Password");

        JPanel changePasswordPanel = new JPanel(new BorderLayout());
        changePasswordPanel.setBackground(new Color(224, 255, 255));

        JLabel changePasswordLabel = new JLabel("New Password:");
        changePasswordLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));

        JPasswordField changePasswordField = new JPasswordField();
        changePasswordField.setPreferredSize(new Dimension(250, 40));

        changePasswordPanel.add(changePasswordLabel, BorderLayout.WEST);
        changePasswordPanel.add(changePasswordField, BorderLayout.EAST);

        JPanel adjustPanel = new JPanel();
        adjustPanel.setLayout(new BoxLayout(adjustPanel, BoxLayout.Y_AXIS));
        adjustPanel.setBackground(new Color(224, 255, 255));
        adjustPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        adjustPanel.add(new JLabel(" "));
        adjustPanel.add(changePasswordPanel);

        changePasswordFrame.add(new JLabel(" "), BorderLayout.NORTH);
        changePasswordFrame.add(adjustPanel, BorderLayout.NORTH);

        JPanel button = new JPanel(new BorderLayout());
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        button.setBackground(new Color(224, 255, 255));

        JButton updateButton = new JButton("Update");
        updateButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        updateButton.setPreferredSize(new Dimension(90, 30));

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        backButton.setPreferredSize(new Dimension(75, 30));

        button.add(updateButton, BorderLayout.EAST);
        button.add(backButton, BorderLayout.WEST);
        changePasswordFrame.add(button, BorderLayout.SOUTH);

        updateButton.addActionListener(e -> {
            char[] passwordChars= changePasswordField.getPassword();
            String password = new String(passwordChars).strip();

            if(password.isEmpty()){
                JLabel error = new JLabel("Password cannot be empty.");
                error.setFont(new Font("Times New Roman", Font.BOLD, 16));
                JOptionPane.showMessageDialog(null, error, "Invalid Password", JOptionPane.ERROR_MESSAGE);
            }
            else {
                for (Student student : Main.studentList) {
                    if (student.getID().equals(studentID)) {
                        if (student.getPassword().replace("\"", "").equals(password)) {
                            JLabel error = new JLabel("You have inserted an old password.");
                            error.setFont(new Font("Times New Roman", Font.BOLD, 16));
                            JOptionPane.showMessageDialog(null, error, "Invalid Password", JOptionPane.ERROR_MESSAGE);
                        } else {
                            boolean valid = true;
                            if (!CommonMethods.isValidPassword(password)) {
                                valid = false;
                            }
                            if (!CommonMethods.isValidQuotedInput(password)) {
                                valid = false;
                            }

                            if (valid) {
                                password = CommonMethods.modifyInput(password);
                                student.setPassword(password);
                                CommonMethods.writeFile(Main.filePaths[1], Main.studentList);
                                changePasswordFrame.dispose();
                                JLabel updated = new JLabel("Password updated.");
                                updated.setFont(new Font("Times New Roman", Font.BOLD, 16));
                                JOptionPane.showMessageDialog(null, updated, "Successful Password Update", JOptionPane.INFORMATION_MESSAGE);
                                studentProfile(studentID);
                            }
                        }
                        break;
                    }
                }
            }
        });

        backButton.addActionListener(e -> {
            changePasswordFrame.dispose();
            checkPassword(studentID);
        });
    }
}
