public class AdminSelectedSubject {

    private static Subject selectedSubject;

    public static void set(Subject subject) {
        selectedSubject = subject;
    }

    public static Subject get() {
        return selectedSubject;
}

}