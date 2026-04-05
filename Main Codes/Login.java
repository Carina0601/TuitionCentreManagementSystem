import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Login {
    public static void showWindow(JFrame mainFrame){
        JFrame loginFrame = new JFrame("Log In");
        loginFrame.setSize(550, 450);
        loginFrame.setLayout(new BorderLayout());
        loginFrame.setLocationRelativeTo(null);
        loginFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        loginFrame.setVisible(true);
        loginFrame.getContentPane().setBackground(new Color(240, 255, 240));

        loginFrame.addWindowListener(new WindowAdapter() {
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

        int[] attempts = {3};
        double[] times = {0};

        JPanel userDetailsPanel = new JPanel();
        userDetailsPanel.setBackground(new Color(240, 255, 240));
        userDetailsPanel.setLayout(new BoxLayout(userDetailsPanel, BoxLayout.Y_AXIS));
        userDetailsPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        JPanel usernamePanel = new JPanel(new BorderLayout());
        usernamePanel.setBackground(new Color(240, 255, 240));
        JLabel usernameLabel = new JLabel("Username: ");
        usernameLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));
        JTextField usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(250, 40));

        JPanel passwordPanel = new JPanel(new BorderLayout());
        passwordPanel.setBackground(new Color(240, 255, 240));
        JLabel passwordLabel = new JLabel("Password: ");
        passwordLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));
        JPasswordField passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(250, 40));

        usernamePanel.add(usernameLabel, BorderLayout.WEST);
        usernamePanel.add(usernameField, BorderLayout.EAST);

        passwordPanel.add(passwordLabel, BorderLayout.WEST);
        passwordPanel.add(passwordField, BorderLayout.EAST);

        userDetailsPanel.add(new JLabel(" "));
        userDetailsPanel.add(usernamePanel);
        userDetailsPanel.add(new JLabel(" "));

        JPanel adjustPanel = new JPanel(new BorderLayout());
        adjustPanel.setBackground(new Color(240, 255, 240));
        adjustPanel.add(passwordPanel, BorderLayout.NORTH);
        userDetailsPanel.add(adjustPanel);

        loginFrame.add(userDetailsPanel, BorderLayout.NORTH);

        //Button Panel
        JPanel button = new JPanel(new BorderLayout());
        button.setBackground(new Color(240, 255, 240));
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JButton loginButton = new JButton("Log In");
        loginButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        loginButton.setPreferredSize(new Dimension(85, 30));

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Times New Roman", Font.BOLD, 14));
        backButton.setPreferredSize(new Dimension(75, 30));

        button.add(loginButton, BorderLayout.EAST);
        button.add(backButton, BorderLayout.WEST);
        loginFrame.add(button, BorderLayout.SOUTH);

        loginButton.addActionListener(e -> {
            String username = usernameField.getText().strip();
            char[] passwordChars = passwordField.getPassword();
            String password = new String(passwordChars).strip();

            boolean notEmpty = true;
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
            if(notEmpty){
                String[] roleID = CommonMethods.getRoleID(username, password);
                String role = roleID[0];
                String id = roleID[1];

                if(role.isEmpty()){
                    attempts[0]--;
                    JLabel invalid = new JLabel("Invalid username or password. " + attempts[0] + " attempts left.");
                    invalid.setFont(new Font("Times New Roman", Font.BOLD, 16));
                    JOptionPane.showMessageDialog(null, invalid, "Login Unsuccessful", JOptionPane.ERROR_MESSAGE);

                    if(attempts[0] == 0){
                        loginFrame.setVisible(false);
                        double sec = 60 * Math.pow(3,times[0]);
                        countDownDialog(sec);
                        times[0]++;
                        attempts[0] = 3;
                        loginFrame.setVisible(true);
                    }
                }
                else{
                    loginFrame.setVisible(false);
                    JLabel login = new JLabel("Logged in successfully.");
                    login.setFont(new Font("Times New Roman", Font.BOLD, 16));
                    JOptionPane.showMessageDialog(null, login, "Successful Login", JOptionPane.INFORMATION_MESSAGE);

                    switch(role){
                        case "admin" -> AdminMethods.mainMenu(id);
                        case "student" -> StudentMethods.mainMenu(id);
                        case "tutor" -> TutorMethods.mainMenu(id);
                        case "receptionist" -> ReceptionistMethods.mainMenu(id);
                        default -> {
                            JLabel error = new JLabel("Invalid file path.");
                            error.setFont(new Font("Times New Roman", Font.BOLD, 16));
                            JOptionPane.showMessageDialog(null, error, "Invalid File Path", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }

        });

        backButton.addActionListener(e -> {
            loginFrame.setVisible(false);
            mainFrame.setVisible(true);
        });
    }

    private static void countDownDialog(double sec){

        JDialog countDown = new JDialog();
        countDown.setModal(true);
        countDown.setTitle("Retrying");
        countDown.setSize(300, 120);
        countDown.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        countDown.setLocationRelativeTo(null);
        countDown.setFont(new Font("Times New Roman", Font.BOLD, 16));

        JLabel countDownMessage = new JLabel("Please retry in " + sec + " seconds.");
        countDownMessage.setFont(new Font("Times New Roman", Font.BOLD, 16));
        countDownMessage.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        countDown.add(countDownMessage);

        Timer timer = new Timer(1000, null);
        timer.addActionListener(new ActionListener() {
            double timeLeft = sec;

            @Override
            public void actionPerformed(ActionEvent e) {
                timeLeft--;
                if(timeLeft > 0){
                    countDownMessage.setText("Please retry in " + timeLeft + " seconds.");
                }
                else{
                    timer.stop();
                    countDown.dispose();
                }
            }
        });

        timer.start();
        countDown.setVisible(true);

    }
}
