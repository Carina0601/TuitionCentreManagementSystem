import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class CommonMethods {

    public static ArrayList<String> splitRespectQuotes(String line){

        ArrayList<String> dataList = new ArrayList<>();
        String data = "";
        boolean insideQuotes = false;

        for(int i = 0; i < line.length(); i++){
            if(line.charAt(i) == '"'){
                insideQuotes = !insideQuotes;
                data += line.charAt(i);
            }

            else if(line.charAt(i) == ',' && insideQuotes){
                data += line.charAt(i);
            }

            else if(line.charAt(i) == ',' && !insideQuotes){
                dataList.add(data);
                data = "";
            }

            else{
                data += line.charAt(i);
            }
        }
        dataList.add(data);
        return dataList;
    }

    public static boolean loadFiles(String filePath){

        try(BufferedReader reader = new BufferedReader(new FileReader(filePath))){
            String line;

            while((line = reader.readLine()) != null){

                if(line.startsWith("//") || line.isEmpty()){
                    continue;
                }

                String newLine = line.strip();
                ArrayList<String> dataList = splitRespectQuotes(newLine);

                String[] data = new String[dataList.size()];
                for(int i = 0; i < dataList.size(); i++){
                    String element = dataList.get(i);
                    data[i] = element;
                }

                switch(filePath){
                    case "txtTCMS/admin.txt" -> {
                        PersonalInfo admin = new PersonalInfo((data[0]),data[1],data[2],data[3],data[4],data[5],data[6],data[7]);
                        Main.adminList.add(admin);
                    }
                    case "txtTCMS/student.txt" -> {
                        Student student = new Student(data[0],data[1],data[2],data[3],data[4],data[5],data[6],data[7],data[8],data[9], Integer.parseInt(data[10]));
                        Main.studentList.add(student);
                    }
                    case "txtTCMS/tutor.txt" -> {
                        PersonalInfo tutor = new PersonalInfo((data[0]),data[1],data[2],data[3],data[4],data[5],data[6],data[7]);
                        Main.tutorList.add(tutor);
                    }
                    case "txtTCMS/receptionist.txt" -> {
                        PersonalInfo receptionist = new PersonalInfo((data[0]),data[1],data[2],data[3],data[4],data[5],data[6],data[7]);
                        Main.recepList.add(receptionist);
                    }
                    case "txtTCMS/bill.txt" -> {
                        Bill bill = new Bill(data[0],data[1],data[2],data[3],data[4],data[5],Double.parseDouble(data[6]),data[7],data[8]);
                        Main.billList.add(bill);
                    }
                    case "txtTCMS/changeRequest.txt" -> {
                        ChangeRequest changeRequest = new ChangeRequest(data[0],data[1],data[2],data[3],data[4],data[5],data[6],data[7]);
                        Main.requestList.add(changeRequest);
                    }
                    case "txtTCMS/subject.txt" -> {
                        Subject subject = new Subject(data[0],data[1],data[2],Double.parseDouble(data[3]),data[4],data[5]);
                        Main.subjectList.add(subject);
                    }
                    default -> {
                        JLabel invalid = new JLabel("Invalid file path.");
                        invalid.setFont(new Font("Times New Roman", Font.BOLD, 16));
                        JOptionPane.showMessageDialog(null, invalid, "Invalid File Path", JOptionPane.ERROR_MESSAGE);
                    }
                }

            }
            return true;
        }
        catch(FileNotFoundException e){
            JLabel error = new JLabel("Error: " + filePath + " not found.");
            error.setFont(new Font("Times New Roman", Font.BOLD, 16));
            JOptionPane.showMessageDialog(null, error, "File Not Found", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        catch(IOException e){
            JLabel error = new JLabel("Error: " + filePath + " cannot be accessed.");
            error.setFont(new Font("Times New Roman", Font.BOLD, 16));
            JOptionPane.showMessageDialog(null, error, "File Cannot Be Accessed", JOptionPane.ERROR_MESSAGE);
            return false;
        }

    }

    public static boolean isValidUserID(String id){
        for(PersonalInfo admin : Main.adminList){
            if(admin.getID().equals(id)){
                return true;
            }
        }
        for(Student student : Main.studentList){
            if(student.getID().equals(id)){
                return true;
            }
        }
        for(PersonalInfo tutor : Main.tutorList){
            if(tutor.getID().equals(id)){
                return true;
            }
        }
            for(PersonalInfo receptionist : Main.recepList){
            if(receptionist.getID().equals(id)){
                return true;
            }
        }
        JLabel invalid = new JLabel("Please insert a valid user ID.");
        invalid.setFont(new Font("Times New Roman", Font.BOLD, 16));
        JOptionPane.showMessageDialog(null, invalid, "Invalid User ID", JOptionPane.ERROR_MESSAGE);
        return false;
    }

    public static boolean isValidID(ArrayList<? extends GetData> list, String id){
        for(GetData object : list){
            if(object.getID().equals(id)){
                return true;
            }
        }
        JLabel invalid = new JLabel("Please insert a valid ID.");
        invalid.setFont(new Font("Times New Roman", Font.BOLD, 16));
        JOptionPane.showMessageDialog(null, invalid, "Invalid ID", JOptionPane.ERROR_MESSAGE);
        return false;
    }

    public static boolean isAllDigits(String string){
        for(int i = 0; i < 0; i++){
            if(!Character.isDigit(string.charAt(i))){
                return false;
            }
        }
        return true;
    }

    public static boolean isValidIC(String ic){
        if(ic.length() == 12 && isAllDigits(ic)){
            return true;
        }
        JLabel invalid = new JLabel("Please insert a valid IC number.");
        invalid.setFont(new Font("Times New Roman", Font.BOLD, 16));
        JOptionPane.showMessageDialog(null, invalid, "Invalid IC", JOptionPane.ERROR_MESSAGE);
        return false;
    }

    public static boolean isValidCredentials(String id, String ic){
        if(id.startsWith("A")){
            for(PersonalInfo admin : Main.adminList){
                if(admin.getID().equals(id) && admin.getIC().equals(ic)){
                    return true;
                }
            }
        }
        else if(id.startsWith("S")){
            for(Student student : Main.studentList){
                if(student.getID().equals(id) && student.getIC().equals(ic)){
                    return true;
                }
            }
        }
        else if(id.startsWith("T")){
            for(PersonalInfo tutor : Main.tutorList){
                if(tutor.getID().equals(id) && tutor.getIC().equals(ic)){
                    return true;
                }
            }
        }
        else{
            for(PersonalInfo receptionist : Main.recepList){
                if(receptionist.getID().equals(id) && receptionist.getIC().equals(ic)){
                    return true;
                }
            }
        }
        JLabel invalid = new JLabel("Invalid credentials.");
        invalid.setFont(new Font("Times New Roman", Font.BOLD, 16));
        JOptionPane.showMessageDialog(null, invalid, "Invalid Credentials", JOptionPane.ERROR_MESSAGE);
        return false;
    }

    public static boolean isNewUser(String id){
        if(id.startsWith("A")){
            for(PersonalInfo admin : Main.adminList){
                if(admin.getID().equals(id) && admin.getUsername().equals("@") && admin.getPassword().equals("#")){
                    return true;
                }
            }
        }
        else if(id.startsWith("S")){
            for(Student student : Main.studentList){
                if(student.getID().equals(id) && student.getUsername().equals("@") && student.getPassword().equals("#")){
                    return true;
                }
            }
        }
        else if(id.startsWith("T")){
            for(PersonalInfo tutor : Main.tutorList){
                if(tutor.getID().equals(id) && tutor.getUsername().equals("@") && tutor.getPassword().equals("#")){
                    return true;
                }
            }
        }
        else{
            for(PersonalInfo receptionist : Main.recepList){
                if(receptionist.getID().equals(id) && receptionist.getUsername().equals("@") && receptionist.getPassword().equals("#")){
                    return true;
                }
            }
        }
        JLabel accountExists = new JLabel("Account exists.");
        accountExists.setFont(new Font("Times New Roman", Font.BOLD, 16));
        JOptionPane.showMessageDialog(null, accountExists, "Account Exists ", JOptionPane.ERROR_MESSAGE);
        return false;
    }

    public static boolean isValidUsername(String username){
        boolean valid = true;
        for(PersonalInfo admin : Main.adminList){
            if(admin.getUsername().equals(username)){
                valid = false;
            }
        }
        for(Student student : Main.studentList){
            if(student.getUsername().equals(username)){
                valid = false;
            }
        }
        for(PersonalInfo tutor : Main.tutorList){
            if(tutor.getUsername().equals(username)){
                valid = false;
            }
        }
        for(PersonalInfo receptionist : Main.recepList){
            if(receptionist.getUsername().equals(username)){
                valid = false;
            }
        }

        if(!valid){
            JLabel invalid = new JLabel("Username taken. Please choose another one.");
            invalid.setFont(new Font("Times New Roman", Font.BOLD, 16));
            JOptionPane.showMessageDialog(null, invalid, "Invalid Username", JOptionPane.ERROR_MESSAGE);
            return valid;
        }
        else {
            return valid;
        }
    }

    public static int countChar(String string, char character){
        int count = 0;

        for(int i = 0; i < string.length(); i++){
            if(string.charAt(i) == character){
                count++;
            }
        }
        return count;
    }

    public static boolean isValidQuotedInput(String string){

        if(string.contains("\"")) {
            if(string.startsWith("\"") && string.endsWith("\"") && countChar(string, '"') == 2){
                return true;
            }
            else {
                JLabel invalid = new JLabel("Please ensure your input is properly quoted.");
                invalid.setFont(new Font("Times New Roman", Font.BOLD, 16));
                JOptionPane.showMessageDialog(null, invalid, "Improperly Quoted Input", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        return true;
    }

    public static boolean hasUpperAndLowerCase(String string){
        boolean hasUpper = false;
        boolean hasLower = false;

        for(int i = 0; i < string.length(); i++){
            if(Character.isUpperCase(string.charAt(i))){
                hasUpper = true;
                break;
            }
        }
        for(int i = 0; i < string.length(); i++){
            if(Character.isLowerCase(string.charAt(i))){
                hasLower = true;
                break;
            }
        }

        if(hasUpper && hasLower){
            return true;
        }
        else{
            return false;
        }
    }

    public static boolean hasDigit(String string){
        for(int i = 0; i < string.length(); i++){
            if(Character.isDigit(string.charAt(i))){
                return true;
            }
        }
        return false;
    }

    public static boolean hasSpecialCharacter(String string){
        for(int i = 0; i < string.length(); i++){
            if(!Character.isLetterOrDigit(string.charAt(i))){
                return true;
            }
        }
        return false;
    }

    public static boolean isValidPassword(String password){
        JPanel invalidPassword = new JPanel();
        invalidPassword.setLayout(new BoxLayout(invalidPassword, BoxLayout.Y_AXIS));

        boolean valid = true;

        if(password.length() < 8){
            JLabel line = new JLabel("Password should contain at least 8 characters.");
            line.setFont(new Font("Times New Roman", Font.BOLD, 16));
            invalidPassword.add(line);
            valid = false;
        }
        if(!hasUpperAndLowerCase(password)){
            JLabel line = new JLabel("Password should contain at least one lowercase and one uppercase character.");
            line.setFont(new Font("Times New Roman", Font.BOLD, 16));
            invalidPassword.add(line);
            valid = false;
        }
        if(!hasDigit(password)){
            JLabel line = new JLabel("Password should contain at least one digit or number.");
            line.setFont(new Font("Times New Roman", Font.BOLD, 16));
            invalidPassword.add(line);
            valid = false;
        }
        if(!hasSpecialCharacter(password)){
            JLabel line = new JLabel("Password should contain at least one special character.");
            line.setFont(new Font("Times New Roman", Font.BOLD, 16));
            invalidPassword.add(line);
            valid = false;
        }
        if(password.contains(" ")){
            JLabel line = new JLabel("Password should not contain empty spaces.");
            line.setFont(new Font("Times New Roman", Font.BOLD, 16));
            invalidPassword.add(line);
            valid = false;
        }

        if(valid){
            return true;
        }
        else{
            JOptionPane.showMessageDialog(null, invalidPassword, "Invalid Password", JOptionPane.ERROR_MESSAGE);
            return false;
        }

    }

    public static String modifyInput(String string){
        if(string.contains(",") && !string.contains("\"")){

            string = '"' + string + '"';
            return string;

        }
        return string;
    }

    public static String updateUserInfo(String id, String username, String password){
        if(id.startsWith("A")){
            for(PersonalInfo admin : Main.adminList){
                if(admin.getID().equals(id)){
                    admin.setUsername(username);
                    admin.setPassword(password);
                    return "admin";
                }
            }
        }
        else if(id.startsWith("S")){
            for(Student student : Main.studentList){
                if(student.getID().equals(id)){
                    student.setUsername(username);
                    student.setPassword(password);
                    return "student";
                }
            }
        }
        else if(id.startsWith("T")){
            for(PersonalInfo tutor : Main.tutorList){
                if(tutor.getID().equals(id)){
                    tutor.setUsername(username);
                    tutor.setPassword(password);
                    return "tutor";
                }
            }
        }
        else{
            for(PersonalInfo receptionist : Main.recepList){
                if(receptionist.getID().equals(id)){
                    receptionist.setUsername(username);
                    receptionist.setPassword(password);
                    return "receptionist";
                }
            }
        }
        return "";
    }

    public static void writeFile(String filePath, ArrayList<? extends GetData> list){

        try(FileWriter fileWriter = new FileWriter(filePath)){
            for(GetData obj : list){
                String[] attributes = obj.getAttributes();
                String newLine = String.join(",", attributes) + "\n";
                fileWriter.write(newLine);
            }
        }
        catch(FileNotFoundException e){
            JLabel error = new JLabel("Error: " + filePath + " not found.");
            error.setFont(new Font("Times New Roman", Font.BOLD, 16));
            JOptionPane.showMessageDialog(null, error, "File Not Found", JOptionPane.ERROR_MESSAGE);
        }
        catch(IOException e){
            JLabel error = new JLabel("Error: " + filePath + " cannot be accessed.");
            error.setFont(new Font("Times New Roman", Font.BOLD, 16));
            JOptionPane.showMessageDialog(null, error, "File Cannot Be Accessed", JOptionPane.ERROR_MESSAGE);
        }

    }

    public static String[] getRoleID(String username, String password){

        String[] roleID = {"",""};
        for(PersonalInfo admin : Main.adminList){
            if(admin.getUsername().replace("\"","").equals(username) && admin.getPassword().replace("\"","").equals(password)){
                roleID[0] = "admin";
                roleID[1] = admin.getID();
                return roleID;
            }
        }
        for(Student student : Main.studentList){
            if(student.getUsername().replace("\"","").equals(username) && student.getPassword().replace("\"","").equals(password)){
                roleID[0] = "student";
                roleID[1] = student.getID();
                return roleID;
            }
        }
        for(PersonalInfo tutor : Main.tutorList){
            if(tutor.getUsername().replace("\"","").equals(username) && tutor.getPassword().replace("\"","").equals(password)){
                roleID[0] = "tutor";
                roleID[1] = tutor.getID();
                return roleID;
            }
        }
        for(PersonalInfo receptionist : Main.recepList){
            if(receptionist.getUsername().replace("\"","").equals(username) && receptionist.getPassword().replace("\"","").equals(password)){
                roleID[0] = "receptionist";
                roleID[1] = receptionist.getID();
                return roleID;
            }
        }
        return roleID;
    }


    public static void updateEmail(JFrame mainMenuFrame, String filePath, String id, ArrayList<? extends GetSetRoleData> list,
                                      ArrayList<? extends GetData> list1){
        JFrame updateEmailFrame = getJFrame("Update Email");

        JPanel updateEmailPanel = new JPanel(new BorderLayout());
        updateEmailPanel.setBackground(new Color(224, 255, 255));
        JLabel updateEmailLabel = new JLabel("New Email:");
        updateEmailLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));

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
                JOptionPane.showMessageDialog(null, error, "Invalid Email", JOptionPane.ERROR_MESSAGE);
            }
            else {
                for (GetSetRoleData obj : list) {
                    if (obj.getID().equals(id)) {
                        if (obj.getEmail().equals(email)) {
                            JLabel error = new JLabel("You have inserted an old email.");
                            error.setFont(new Font("Times New Roman", Font.BOLD, 16));
                            JOptionPane.showMessageDialog(null, error, "Invalid Email", JOptionPane.ERROR_MESSAGE);
                        } else if (!email.contains("@")) {
                            JLabel error = new JLabel("Please insert a valid email.");
                            error.setFont(new Font("Times New Roman", Font.BOLD, 16));
                            JOptionPane.showMessageDialog(null, error, "Invalid Email", JOptionPane.ERROR_MESSAGE);
                        } else {
                            obj.setEmail(email);
                            writeFile(filePath, list1);
                            updateEmailFrame.dispose();
                            JLabel success = new JLabel("Email updated.");
                            success.setFont(new Font("Times New Roman", Font.BOLD, 16));
                            JOptionPane.showMessageDialog(null, success, "Successful Email Update", JOptionPane.INFORMATION_MESSAGE);
                            userProfile(mainMenuFrame, filePath, id, list, list1);
                        }
                        break;
                    }
                }
            }
        });

        backButton.addActionListener(e -> {
            updateEmailFrame.dispose();
            userProfile(mainMenuFrame, filePath, id, list, list1);
        });
    }

    public static boolean isValidPhoneNo(String phoneNo){
        if(phoneNo.length() < 10 || phoneNo.length() > 11 || !isAllDigits(phoneNo)){
            JLabel invalid = new JLabel("Please insert a valid phone number.");
            invalid.setFont(new Font("Times New Roman", Font.BOLD, 16));
            JOptionPane.showMessageDialog(null, invalid, "Invalid Phone Number", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    public static void updatePhoneNo(JFrame mainMenuFrame, String filePath, String id, ArrayList<? extends GetSetRoleData> list,
                                        ArrayList<? extends GetData> list1){

        JFrame updatePhoneNoFrame = getJFrame("Update Contact Number");

        JPanel updatePhoneNoPanel = new JPanel(new BorderLayout());
        updatePhoneNoPanel.setBackground(new Color(224, 255, 255));

        JLabel updatePhoneNoLabel = new JLabel("New Contact Number:");
        updatePhoneNoLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));

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
                for (GetSetRoleData obj : list) {
                    if (obj.getID().equals(id)) {
                        if (obj.getPhoneNo().equals(phoneNo)) {
                            JLabel error = new JLabel("You have inserted an old contact number.");
                            error.setFont(new Font("Times New Roman", Font.BOLD, 16));
                            JOptionPane.showMessageDialog(null, error, "Invalid Contact Number", JOptionPane.ERROR_MESSAGE);
                        } else if (isValidPhoneNo(phoneNo)) {
                            obj.setPhoneNo(phoneNo);
                            writeFile(filePath, list1);
                            updatePhoneNoFrame.dispose();
                            JLabel updated = new JLabel("Contact number updated.");
                            updated.setFont(new Font("Times New Roman", Font.BOLD, 16));
                            JOptionPane.showMessageDialog(null, updated, "Successful Contact Number Update", JOptionPane.INFORMATION_MESSAGE);
                            userProfile(mainMenuFrame, filePath, id, list, list1);
                        }
                        break;
                    }
                }
            }
        });

        backButton.addActionListener(e -> {
            updatePhoneNoFrame.dispose();
            userProfile(mainMenuFrame, filePath, id, list, list1);
        });
    }

    public static void updateAddress(JFrame mainMenuFrame, String filePath, String id, ArrayList<? extends GetSetRoleData> list,
                                        ArrayList<? extends GetData> list1){
        JFrame updateAddressFrame = getJFrame("Update Address");

        JPanel updateAddressPanel = new JPanel(new BorderLayout());
        updateAddressPanel.setBackground(new Color(224, 255, 255));

        JLabel updateAddressLabel = new JLabel("New Address:");
        updateAddressLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));

        JTextField updateAddressField = new JTextField();
        updateAddressField.setPreferredSize(new Dimension(250, 40));

        updateAddressPanel.add(updateAddressLabel, BorderLayout.WEST);
        updateAddressPanel.add(updateAddressField, BorderLayout.EAST);

        JPanel adjustPanel = new JPanel();
        adjustPanel.setLayout(new BoxLayout(adjustPanel, BoxLayout.Y_AXIS));
        adjustPanel.setBackground(new Color(224, 255, 255));
        adjustPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
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
                JOptionPane.showMessageDialog(null, error, "Invalid Address", JOptionPane.ERROR_MESSAGE);
            }
            else {
                for (GetSetRoleData obj : list) {
                    if (obj.getID().equals(id)) {
                        if (obj.getAddress().replace("\"", "").equals(address)) {
                            JLabel error = new JLabel("You have inserted an old address.");
                            error.setFont(new Font("Times New Roman", Font.BOLD, 16));
                            JOptionPane.showMessageDialog(null, error, "Invalid Address", JOptionPane.ERROR_MESSAGE);
                        } else if (isValidQuotedInput(address)) {
                            address = modifyInput(address);
                            obj.setAddress(address);
                            writeFile(filePath, list1);
                            updateAddressFrame.dispose();
                            JLabel updated = new JLabel("Address updated.");
                            updated.setFont(new Font("Times New Roman", Font.BOLD, 16));
                            JOptionPane.showMessageDialog(null, updated, "Successful Address Update", JOptionPane.INFORMATION_MESSAGE);
                            userProfile(mainMenuFrame, filePath, id, list, list1);
                        }
                        break;
                    }
                }
            }
        });

        backButton.addActionListener(e -> {
            updateAddressFrame.dispose();
            userProfile(mainMenuFrame, filePath, id, list, list1);
        });
    }

    public static void changeUsername(JFrame mainMenuFrame, String filePath, String id, ArrayList<? extends GetSetRoleData> list,
                                         ArrayList<? extends GetData> list1){

        JFrame changeUsernameFrame = getJFrame("Change Username");

        JPanel changeUsernamePanel = new JPanel(new BorderLayout());
        changeUsernamePanel.setBackground(new Color(224, 255, 255));

        JLabel changeUsernameLabel = new JLabel("New Username:");
        changeUsernameLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));

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
                JOptionPane.showMessageDialog(null, error, "Invalid Username", JOptionPane.ERROR_MESSAGE);
            }
            else {
                for (GetSetRoleData obj : list) {
                    if (obj.getID().equals(id)) {
                        if (obj.getUsername().replace("\"", "").equals(username)) {
                            JLabel error = new JLabel("You have inserted an old username.");
                            error.setFont(new Font("Times New Roman", Font.BOLD, 16));
                            JOptionPane.showMessageDialog(null, error, "Invalid Username", JOptionPane.ERROR_MESSAGE);
                        } else {
                            boolean valid = true;
                            if (!isValidUsername(username)) {
                                valid = false;
                            }
                            if (!isValidQuotedInput(username)) {
                                valid = false;
                            }

                            if (valid) {
                                username = modifyInput(username);
                                obj.setUsername(username);
                                writeFile(filePath, list1);
                                changeUsernameFrame.dispose();
                                JLabel updated = new JLabel("Username updated.");
                                updated.setFont(new Font("Times New Roman", Font.BOLD, 16));
                                JOptionPane.showMessageDialog(null, updated, "Successful Username Update", JOptionPane.INFORMATION_MESSAGE);
                                userProfile(mainMenuFrame, filePath, id, list, list1);
                            }
                        }
                        break;
                    }
                }
            }
        });

        backButton.addActionListener(e -> {
            changeUsernameFrame.dispose();
            userProfile(mainMenuFrame, filePath, id, list, list1);
        });
    }

    public static void checkPassword(JFrame mainMenuFrame, String filePath, String id, ArrayList<? extends GetSetRoleData> list,
                                      ArrayList<? extends GetData> list1){

        JFrame changePasswordFrame = getJFrame("Check Current Password");

        JPanel changePasswordPanel = new JPanel(new BorderLayout());
        changePasswordPanel.setBackground(new Color(224, 255, 255));

        JLabel changePasswordLabel = new JLabel("Current Password:");
        changePasswordLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));

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
                for (GetSetRoleData obj : list) {
                    if (obj.getID().equals(id)) {
                        if (obj.getPassword().replace("\"", "").equals(password)) {
                            changePasswordFrame.dispose();
                            JLabel correct = new JLabel("Password correct.");
                            correct.setFont(new Font("Times New Roman", Font.BOLD, 16));
                            JOptionPane.showMessageDialog(null, correct, "Valid Password", JOptionPane.INFORMATION_MESSAGE);
                            changePassword(mainMenuFrame, filePath, id, list, list1);
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
            userProfile(mainMenuFrame, filePath, id, list, list1);
        });
    }

    public static void changePassword(JFrame mainMenuFrame, String filePath, String id, ArrayList<? extends GetSetRoleData> list,
                                         ArrayList<? extends GetData> list1){

        JFrame changePasswordFrame = getJFrame("Change Password");

        JPanel changePasswordPanel = new JPanel(new BorderLayout());
        changePasswordPanel.setBackground(new Color(224, 255, 255));

        JLabel changePasswordLabel = new JLabel("New Password:");
        changePasswordLabel.setFont(new Font("Times New Roman", Font.BOLD, 16));

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
                JOptionPane.showMessageDialog(null, error, "Invalid Password.", JOptionPane.ERROR_MESSAGE);
            }
            else {
                for (GetSetRoleData obj : list) {
                    if (obj.getID().equals(id)) {
                        if (obj.getPassword().replace("\"", "").equals(password)) {
                            JLabel error = new JLabel("You have inserted an old password.");
                            error.setFont(new Font("Times New Roman", Font.BOLD, 16));
                            JOptionPane.showMessageDialog(null, error, "Invalid Password", JOptionPane.ERROR_MESSAGE);
                        } else {
                            boolean valid = true;
                            if (!isValidPassword(password)) {
                                valid = false;
                            }
                            if (!isValidQuotedInput(password)) {
                                valid = false;
                            }

                            if (valid) {
                                password = modifyInput(password);
                                obj.setPassword(password);
                                writeFile(filePath, list1);
                                changePasswordFrame.dispose();
                                JLabel updated = new JLabel("Password updated.");
                                updated.setFont(new Font("Times New Roman", Font.BOLD, 16));
                                JOptionPane.showMessageDialog(null, updated, "Successful Password Update", JOptionPane.INFORMATION_MESSAGE);
                                userProfile(mainMenuFrame, filePath, id, list, list1);
                            }
                        }
                        break;
                    }
                }
            }
        });

        backButton.addActionListener(e -> {
            changePasswordFrame.dispose();
            checkPassword(mainMenuFrame, filePath, id, list, list1);
        });
    }

    public static void userProfile(JFrame mainMenuFrame, String filePath, String id, ArrayList<? extends GetSetRoleData> list,
                                   ArrayList<? extends GetData> list1){

        JFrame viewProfileFrame = getJFrame("View Profile");

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

        for(GetData obj : list1){
            if(obj.getID().equals(id)){
                String[] attributes = obj.getAttributes();

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
            mainMenuFrame.setVisible(true);
        });
        editEmail.addActionListener(e -> {
            viewProfileFrame.dispose();
            updateEmail(mainMenuFrame, filePath, id, list, list1);
        });
        editPhoneNo.addActionListener(e -> {
            viewProfileFrame.dispose();
            updatePhoneNo(mainMenuFrame, filePath, id, list, list1);
        });
        editAddress.addActionListener(e -> {
            viewProfileFrame.dispose();
            updateAddress(mainMenuFrame, filePath, id, list, list1);
        });
        editUsername.addActionListener(e -> {
            viewProfileFrame.dispose();
            changeUsername(mainMenuFrame, filePath, id, list, list1);
        });
        changePassword.addActionListener(e -> {
            viewProfileFrame.dispose();
            checkPassword(mainMenuFrame, filePath, id, list, list1);
        });
    }

    public static JFrame getJFrame(String title) {
        JFrame viewProfileFrame = new JFrame(title);
        viewProfileFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        viewProfileFrame.setSize(550, 450);
        viewProfileFrame.setLayout(new BorderLayout());
        viewProfileFrame.setLocationRelativeTo(null);
        viewProfileFrame.setVisible(true);
        viewProfileFrame.getContentPane().setBackground(new Color(224, 255, 255));

        viewProfileFrame.addWindowListener(new WindowAdapter() {
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
        return viewProfileFrame;
    }

    public static ArrayList<Enrolment> loadEnrolment(String subjectID){

        String filePath = "txtTCMS/enr" + subjectID + ".txt";
        ArrayList<Enrolment> enrolmentList = new ArrayList<>();

        try(BufferedReader reader = new BufferedReader(new FileReader(filePath))){
            String line;

            while((line = reader.readLine()) != null){

                if(line.startsWith("//") || line.isEmpty()){
                    continue;
                }

                String newLine = line.strip();
                ArrayList<String> dataList = splitRespectQuotes(newLine);

                String[] data = new String[dataList.size()];
                for(int i = 0; i < dataList.size(); i++){
                    String element = dataList.get(i);
                    data[i] = element;
                }

                Enrolment enrolment = new Enrolment(data[0],data[1],data[2],data[3]);
                enrolmentList.add(enrolment);

            }
        }
        catch(FileNotFoundException e){
            JLabel error = new JLabel("Error: " + filePath + " not found.");
            error.setFont(new Font("Times New Roman", Font.BOLD, 16));
            JOptionPane.showMessageDialog(null, error, "File Not Found", JOptionPane.ERROR_MESSAGE);
        }
        catch(IOException e){
            JLabel error = new JLabel("Error: " + filePath + " cannot be accessed.");
            error.setFont(new Font("Times New Roman", Font.BOLD, 16));
            JOptionPane.showMessageDialog(null, error, "File Cannot Be Accessed", JOptionPane.ERROR_MESSAGE);
        }
        return enrolmentList;

    }

    public static String getNextID(String prefix, ArrayList<? extends GetData> list){

        String nextID;

        if(list.isEmpty()){
            nextID = prefix + "001";
            return nextID;
        }

        int[] numbers = new int[list.size()];
        for(int i = 0; i < list.size(); i++) {
            String id = list.get(i).getID();
            int number = Integer.parseInt(id.replace(prefix,""));
            numbers[i] = number;
        }

        Arrays.sort(numbers);
        int nextNum = numbers[numbers.length - 1] + 1;
        nextID = prefix + String.format("%03d", nextNum);

        return nextID;
    }

    public static boolean isValidName(String string){
        for(int i = 0; i < string.length(); i++){
            if(!Character.isLetter(string.charAt(i)) && !Character.isSpaceChar(string.charAt(i))){
                JLabel invalid = new JLabel("Please insert a valid name.");
                invalid.setFont(new Font("Times New Roman", Font.BOLD, 16));
                JOptionPane.showMessageDialog(null, invalid, "Invalid Name", JOptionPane.ERROR_MESSAGE);

                return false;
            }
        }
        return true;
    }

    public static void appendNewLine(String filePath, GetData object){

        try(FileWriter fileWriter = new FileWriter(filePath, true)){
            String[] data = object.getAttributes();
            String newline = String.join(",", data) + "\n";
            fileWriter.write(newline);
        }
        catch(FileNotFoundException e){
            JLabel error = new JLabel("Error: " + filePath + " not found.");
            error.setFont(new Font("Times New Roman", Font.BOLD, 16));
            JOptionPane.showMessageDialog(null, error, "File Not Found", JOptionPane.ERROR_MESSAGE);
        }
        catch(IOException e){
            JLabel error = new JLabel("Error: " + filePath + " cannot be accessed.");
            error.setFont(new Font("Times New Roman", Font.BOLD, 16));
            JOptionPane.showMessageDialog(null, error, "File Cannot Be Accessed", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static ArrayList<String> getStudentSubjectIDs(String studentID){
        ArrayList<String> subjectIDs = new ArrayList<>();

        for(Subject subject : Main.subjectList){
            ArrayList<Enrolment> enrolmentList = loadEnrolment(subject.getID());

            for(Enrolment enrolment : enrolmentList){
                if(enrolment.getStudentID().equals(studentID)){
                    subjectIDs.add(subject.getID());
                    break;
                }
            }
        }

        return subjectIDs;
    }

    public static String getSubjectTitle(String subjectID){
        for(Subject subject : Main.subjectList){
            if(subject.getID().equals(subjectID)){
                return subject.getTitle();
            }
        }
        return "";
    }

    public static boolean isBillExist(String studentID){
        for(Bill bill : Main.billList){
            if(bill.getStudentID().equals(studentID)){
                return true;
            }
        }
        return false;
    }

    public static ArrayList<Attendance> loadAttendance(String subjectID){

        String filePath = "txtTCMS/att" + subjectID + ".txt";
        ArrayList<Attendance> attendanceList = new ArrayList<>();

        try(BufferedReader reader = new BufferedReader(new FileReader(filePath))){
            String line;

            while((line = reader.readLine()) != null){

                if(line.startsWith("//") || line.isEmpty()){
                    continue;
                }

                String newLine = line.strip();
                ArrayList<String> dataList = splitRespectQuotes(newLine);

                String[] data = new String[dataList.size()];
                for(int i = 0; i < dataList.size(); i++){
                    String element = dataList.get(i);
                    data[i] = element;
                }

                Attendance attendance = new Attendance(data[0],data[1],data[2],data[3],data[4]);
                attendanceList.add(attendance);

            }
        }
        catch(FileNotFoundException e){
            JLabel error = new JLabel("Error: " + filePath + " not found.");
            error.setFont(new Font("Times New Roman", Font.BOLD, 16));
            JOptionPane.showMessageDialog(null, error, "File Not Found", JOptionPane.ERROR_MESSAGE);
        }
        catch(IOException e){
            JLabel error = new JLabel("Error: " + filePath + " cannot be accessed.");
            error.setFont(new Font("Times New Roman", Font.BOLD, 16));
            JOptionPane.showMessageDialog(null, error, "File Cannot Be Accessed", JOptionPane.ERROR_MESSAGE);
        }
        return attendanceList;
    }

    public static boolean isEnrolled(String subjectID, String studentID){
        ArrayList<Enrolment> enrolmentList = CommonMethods.loadEnrolment(subjectID);
        for(Enrolment enrolment : enrolmentList){
            if(enrolment.getStudentID().equals(studentID)){
                return true;
            }
        }
        return false;
    }
}
