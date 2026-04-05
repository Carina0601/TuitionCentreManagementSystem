import javax.swing.*;

public class AdminMethods {
    public static String adminID;
    public static void mainMenu(String id){
        adminID = id;
        SwingUtilities.invokeLater(AdminMainMenu::new);

    }
    public static String getAdminID(){
        return adminID;
    }
}
