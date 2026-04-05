import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class Main {

    static String[] filePaths = {"txtTCMS/admin.txt", "txtTCMS/student.txt", "txtTCMS/tutor.txt", "txtTCMS/receptionist.txt", "txtTCMS/bill.txt", "txtTCMS/changeRequest.txt",
            "txtTCMS/subject.txt"};

    static ArrayList<PersonalInfo> adminList = new ArrayList<>();
    static ArrayList<Student> studentList = new ArrayList<>();
    static ArrayList<PersonalInfo> tutorList = new ArrayList<>();
    static ArrayList<PersonalInfo> recepList = new ArrayList<>();
    static ArrayList<Bill> billList = new ArrayList<>();
    static ArrayList<ChangeRequest> requestList = new ArrayList<>();
    static ArrayList<Subject> subjectList = new ArrayList<>();

    static ArrayList[] lists = {adminList, studentList, tutorList, recepList, billList, requestList, subjectList};

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().showMain());
    }

    public static void showMain() {

        JFrame mainFrame = new JFrame("Tuition Centre Management System");
        mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mainFrame.setSize(550, 450);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.getContentPane().setBackground(new Color(224, 255, 255));

        mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
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
                    mainFrame.setVisible(true);
                }
            }
        });

        JLabel loadFilesLabel = new JLabel("Loading files...");
        loadFilesLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));
        loadFilesLabel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        mainFrame.add(loadFilesLabel, BorderLayout.NORTH);

        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);

        boolean fileOpened = true;
        for (String filePath : filePaths) {
            if (!CommonMethods.loadFiles(filePath)) {
                fileOpened = false;
            }
        }

        if (!fileOpened) {
            JLabel error = new JLabel("Error: Program stopped");
            error.setFont(new Font("Times New Roman", Font.BOLD, 16));

            JOptionPane.showMessageDialog(null, error, "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }

        JLabel filesLoaded = new JLabel("Files loaded successfully.");
        filesLoaded.setFont(new Font("Times New Roman", Font.BOLD, 16));

        JOptionPane.showMessageDialog(null, filesLoaded, "Success", JOptionPane.INFORMATION_MESSAGE);
        mainFrame.setVisible(false);

        showMainMenu();
    }

    public static void showMainMenu(){
        JFrame mainFrame = new JFrame("Tuition Centre Management System");
        mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        mainFrame.setSize(550, 450);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.getContentPane().setBackground(new Color(224, 255, 255));
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);

        mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
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
                    mainFrame.setVisible(true);
                }
            }
        });

        JLabel welcomeMessage = new JLabel("\nWelcome to the Advanced Tuition Centre!", SwingConstants.CENTER);
        welcomeMessage.setFont(new Font("Times New Roman", Font.BOLD, 20));
        welcomeMessage.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        mainFrame.add(welcomeMessage, BorderLayout.NORTH);

        JButton signUpButton = new JButton("1. Sign Up");
        JButton logInButton = new JButton("2. Log In");
        JButton exitButton = new JButton("3. Exit Program");

        signUpButton.setFont(new Font("Times New Roman", Font.BOLD, 18));
        logInButton.setFont(new Font("Times New Roman", Font.BOLD, 18));
        exitButton.setFont(new Font("Times New Roman", Font.BOLD, 18));

        signUpButton.setBackground(new Color(173, 232, 255));
        logInButton.setBackground(new Color(194, 238, 255));
        exitButton.setBackground(new Color(173, 232, 255));

        JPanel homePagePanel = new JPanel();
        homePagePanel.setLayout(new GridLayout(3, 1));
        homePagePanel.setBackground(new Color(224, 255, 255));
        homePagePanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        homePagePanel.add(signUpButton);
        homePagePanel.add(logInButton);
        homePagePanel.add(exitButton);
        mainFrame.add(homePagePanel, BorderLayout.CENTER);

        signUpButton.addActionListener(e -> {
            mainFrame.setVisible(false);
            SignUp.showWindow(mainFrame, filePaths, lists);
        });
        logInButton.addActionListener(e -> {
            mainFrame.setVisible(false);
            Login.showWindow(mainFrame);
        });
        exitButton.addActionListener(e -> {
            JLabel confirmExit = new JLabel("Confirm to exit the system?");
            confirmExit.setFont(new Font("Times New Roman", Font.BOLD, 16));

            int result = JOptionPane.showConfirmDialog(null, confirmExit, "Confirm Exit", JOptionPane.OK_CANCEL_OPTION);
            if(result == JOptionPane.YES_OPTION){
                JLabel thankYou = new JLabel("Thank you for using this system!");
                thankYou.setFont(new Font("Times New Roman", Font.BOLD, 16));

                JOptionPane.showMessageDialog(null, thankYou, "Exit", JOptionPane.INFORMATION_MESSAGE);
                System.exit(1);
            }
        });
    }
}

