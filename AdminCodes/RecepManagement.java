import java.util.ArrayList;
import java.util.List;

public class RecepManagement {

    private static final String FILE_NAME = "./txtTCMS/receptionist.txt";

    //save receptionist
    public static void saveRecep(List<PersonalInfo> receptionists) {
        CommonMethods.writeFile(FILE_NAME, new ArrayList<>(receptionists));
            System.out.println("Successfully wrote " + receptionists.size() + " receptionists.");

    }
}
