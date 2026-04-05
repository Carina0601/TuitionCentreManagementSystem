import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class SignUp {
    public static void showWindow(JFrame mainFrame, String[] filePaths, ArrayList[] lists){
        JFrame signUpFrame = new JFrame("Sign Up");
        signUpFrame.setSize(550, 450);
        signUpFrame.setLayout(new BorderLayout());
        signUpFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        signUpFrame.setLocationRelativeTo(null);
        signUpFrame.setVisible(true);
        signUpFrame.getContentPane().setBackground(new Color(255,250,205));

        signUpFrame.addWindowListener(new WindowAdapter() {
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

        //Credentials Panel
        JPanel credentials = new JPanel();
        credentials.setBackground(new Color(255, 250, 205));
        credentials.setLayout(new BoxLayout(credentials, BoxLayout.Y_AXIS));
        credentials.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        JPanel userIDPanel = new JPanel(new BorderLayout());
        userIDPanel.setBackground(new Color(255, 250, 205));
        JLabel userIDLabel = new JLabel("ID: ");
        userIDLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));
        JTextField userIDField = new JTextField();
        userIDField.setPreferredSize(new Dimension(250, 40));

        JPanel icPanel = new JPanel(new BorderLayout());
        icPanel.setBackground(new Color(255, 250, 205));
        JLabel icLabel = new JLabel("IC Number: ");
        icLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));
        JTextField icField = new JTextField();
        icField.setPreferredSize(new Dimension(250, 40));

        userIDPanel.add(userIDLabel, BorderLayout.WEST);
        userIDPanel.add(userIDField, BorderLayout.EAST);

        icPanel.add(icLabel, BorderLayout.WEST);
        icPanel.add(icField, BorderLayout.EAST);

        credentials.add(new JLabel(" "));
        credentials.add(userIDPanel);
        credentials.add(new JLabel(" "));

        JPanel adjustPanel = new JPanel(new BorderLayout());
        adjustPanel.setBackground(new Color(255, 250, 205));
        adjustPanel.add(icPanel, BorderLayout.NORTH);
        credentials.add(adjustPanel);

        signUpFrame.add(credentials, BorderLayout.NORTH);

        //Buttons Panel
        JPanel buttons = new JPanel(new BorderLayout());
        buttons.setBackground(new Color(255, 250, 205));
        buttons.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JButton nextButton = new JButton("Next");
        nextButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        nextButton.setPreferredSize(new Dimension(75, 30));

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        backButton.setPreferredSize(new Dimension(75, 30));

        buttons.add(nextButton, BorderLayout.EAST);
        buttons.add(backButton, BorderLayout.WEST);
        signUpFrame.add(buttons, BorderLayout.SOUTH);

        nextButton.addActionListener(e -> {
            String id = userIDField.getText().strip().toUpperCase();
            String ic = icField.getText().strip();

            boolean notEmpty = true;
            boolean validIDIC = true;
            if(id.isEmpty()){
                notEmpty = false;
                JLabel error = new JLabel("Please fill in your ID.");
                error.setFont(new Font("Times New Roman", Font.BOLD, 16));

                JOptionPane.showMessageDialog(null, error, "ID Empty", JOptionPane.ERROR_MESSAGE);
            }
            if(ic.isEmpty()){
                notEmpty = false;
                JLabel error = new JLabel("Please fill in your IC number.");
                error.setFont(new Font("Times New Roman", Font.BOLD, 16));

                JOptionPane.showMessageDialog(null, "Please fill in your IC number.", "IC Empty", JOptionPane.ERROR_MESSAGE);
            }

            if(notEmpty) {
                if (!CommonMethods.isValidUserID(id)) {
                    validIDIC = false;
                }
                if (!CommonMethods.isValidIC(ic)) {
                    validIDIC = false;
                }

                if(validIDIC){
                    if(CommonMethods.isValidCredentials(id, ic)){
                        JLabel valid = new JLabel("Valid credentials.");
                        valid.setFont(new Font("Times New Roman", Font.BOLD, 16));

                        JOptionPane.showMessageDialog(null, valid,
                                "Valid Credentials", JOptionPane.INFORMATION_MESSAGE);

                        if(CommonMethods.isNewUser(id)){
                            signUpFrame.setVisible(false);
                            createAccount(mainFrame, signUpFrame,id, filePaths, lists);
                        }
                    }
                }
            }
        });

        backButton.addActionListener(e -> {
            signUpFrame.setVisible(false);
            mainFrame.setVisible(true);
        });

    }

    private static void createAccount(JFrame mainFrame, JFrame signUpFrame, String id, String[] filePaths, ArrayList[] lists){
        JFrame createAccountFrame = new JFrame("Create Account");
        createAccountFrame.setSize(550, 450);
        createAccountFrame.setLayout(new BorderLayout());
        createAccountFrame.setLocationRelativeTo(null);
        createAccountFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        createAccountFrame.setVisible(true);
        createAccountFrame.getContentPane().setBackground(new Color(255, 250, 205));

        createAccountFrame.addWindowListener(new WindowAdapter() {
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

        //User Details Panel
        JPanel userDetails = new JPanel();
        userDetails.setBackground(new Color(255, 250, 205));
        userDetails.setLayout(new BoxLayout(userDetails, BoxLayout.Y_AXIS));
        userDetails.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        JPanel usernamePanel = new JPanel(new BorderLayout());
        usernamePanel.setBackground(new Color(255, 250, 205));
        JLabel usernameLabel = new JLabel("New Username: ");
        usernameLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));
        JTextField usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(250, 40));

        JPanel passwordPanel = new JPanel(new BorderLayout());
        passwordPanel.setBackground(new Color(255, 250, 205));
        JLabel passwordLabel = new JLabel("New Password: ");
        passwordLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));
        JPasswordField passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(250, 40));

        usernamePanel.add(usernameLabel, BorderLayout.WEST);
        usernamePanel.add(usernameField, BorderLayout.EAST);

        passwordPanel.add(passwordLabel, BorderLayout.WEST);
        passwordPanel.add(passwordField, BorderLayout.EAST);

        userDetails.add(usernamePanel, BorderLayout.NORTH);
        userDetails.add(new JLabel(" "));

        JPanel passwordRequirements = new JPanel();
        passwordRequirements.setBackground(new Color(255, 250, 205));
        passwordRequirements.setLayout(new BoxLayout(passwordRequirements, BoxLayout.Y_AXIS));
        passwordRequirements.add(new JLabel(" "));

        JLabel line1 = new JLabel("Password Requirements:");
        line1.setFont(new Font("Times New Roman", Font.BOLD, 16));
        JLabel line2 = new JLabel("*Min 8 characters");
        line2.setFont(new Font("Times New Roman", Font.BOLD, 16));
        JLabel line3 = new JLabel("*At least 1 lowercase alphabet");
        line3.setFont(new Font("Times New Roman", Font.BOLD, 16));
        JLabel line4 = new JLabel("*At least 1 uppercase alphabet");
        line4.setFont(new Font("Times New Roman", Font.BOLD, 16));
        JLabel line5 = new JLabel("*At least 1 digit or number");
        line5.setFont(new Font("Times New Roman", Font.BOLD, 16));
        JLabel line6 = new JLabel("*At least 1 special character");
        line6.setFont(new Font("Times New Roman", Font.BOLD, 16));
        JLabel line7 = new JLabel("*No spaces");
        line7.setFont(new Font("Times New Roman", Font.BOLD, 16));

        passwordRequirements.add(line1);
        passwordRequirements.add(line2);
        passwordRequirements.add(line3);
        passwordRequirements.add(line4);
        passwordRequirements.add(line5);
        passwordRequirements.add(line6);
        passwordRequirements.add(line7);

        JPanel adjustPanel = new JPanel(new BorderLayout());
        adjustPanel.setBackground(new Color(255, 250, 205));
        adjustPanel.add(passwordPanel, BorderLayout.NORTH);
        adjustPanel.add(passwordRequirements, BorderLayout.CENTER);
        userDetails.add(adjustPanel, BorderLayout.CENTER);

        createAccountFrame.add(userDetails, BorderLayout.NORTH);


        //Buttons Panel
        JPanel buttons = new JPanel(new BorderLayout());
        buttons.setBackground(new Color(255, 250, 205));
        buttons.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JButton createButton = new JButton("Create Account");
        createButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        createButton.setPreferredSize(new Dimension(130, 30));

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        backButton.setPreferredSize(new Dimension(75, 30));

        buttons.add(createButton, BorderLayout.EAST);
        buttons.add(backButton, BorderLayout.WEST);
        createAccountFrame.add(buttons, BorderLayout.SOUTH);

        createButton.addActionListener(e -> {
            String username = usernameField.getText().strip();
            char[] passwordChars = passwordField.getPassword();
            String password = new String(passwordChars).strip();

            boolean notEmpty = true;
            boolean validUserDetails = true;

            if(username.isEmpty()){
                notEmpty = false;
                JLabel error = new JLabel("Please fill in your username.");
                error.setFont(new Font("Times New Roman", Font.BOLD, 16));

                JOptionPane.showMessageDialog(null, error, "Username Empty", JOptionPane.ERROR_MESSAGE);
            }
            if(password.isEmpty()){
                notEmpty = false;
                JLabel error = new JLabel("Please fill in your password.");
                error.setFont(new Font("Times New Roman", Font.BOLD, 16));

                JOptionPane.showMessageDialog(null, error, "Password Empty", JOptionPane.ERROR_MESSAGE);
            }

            if(notEmpty) {
                if (!CommonMethods.isValidUsername(username) || !CommonMethods.isValidQuotedInput(username)) {
                    validUserDetails = false;
                }
                if (!CommonMethods.isValidPassword(password) || !CommonMethods.isValidQuotedInput(password)) {
                    validUserDetails = false;
                }

                if(validUserDetails) {
                    username = CommonMethods.modifyInput(username);
                    password = CommonMethods.modifyInput(password);

                    String role = CommonMethods.updateUserInfo(id, username, password);
                    switch (role) {
                        case "admin" -> CommonMethods.writeFile(filePaths[0], lists[0]);
                        case "student" -> CommonMethods.writeFile(filePaths[1], lists[1]);
                        case "tutor" -> CommonMethods.writeFile(filePaths[2], lists[2]);
                        case "receptionist" -> CommonMethods.writeFile(filePaths[3], lists[3]);
                        default -> {
                            JLabel invalidRole = new JLabel("Invalid role.");
                            invalidRole.setFont(new Font("Times New Roman", Font.BOLD, 16));

                            JOptionPane.showMessageDialog(null, invalidRole, "Invalid Role", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    createAccountFrame.setVisible(false);
                    JLabel accountCreated = new JLabel("Account successfully created.");
                    accountCreated.setFont(new Font("Times New Roman", Font.BOLD, 16));

                    JOptionPane.showMessageDialog(null, accountCreated, "Successful Account Creation", JOptionPane.INFORMATION_MESSAGE);

                    JLabel confirmLogin = new JLabel("Proceed to log in?");
                    confirmLogin.setFont(new Font("Times New Roman", Font.BOLD, 16));

                    int result = JOptionPane.showConfirmDialog(null, confirmLogin, "Log In Confirm", JOptionPane.YES_NO_OPTION);
                    if(result == JOptionPane.YES_OPTION){
                        Login.showWindow(mainFrame);
                    }
                    else{
                        mainFrame.setVisible(true);
                    }

                }
            }

        });

        backButton.addActionListener(e -> {
            createAccountFrame.setVisible(false);
            signUpFrame.setVisible(true);
        });
    }

}
