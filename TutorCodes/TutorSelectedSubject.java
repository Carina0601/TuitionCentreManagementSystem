public class TutorSelectedSubject {

    private static Subject selectedSubject;

    public static void set(Subject subject) {
        selectedSubject = subject;
    }

    public static Subject get() {
        return selectedSubject;
    }

}
