public class Subject implements GetData {

    private String id;
    private String title;
    private String tutorID;
    private double fees;
    private String schedule;
    private String notesLink;

    Subject(String id, String title, String tutorID, double fees, String schedule, String notesLink){
        this.id = id;
        this.title = title;
        this.tutorID = tutorID;
        this.fees = fees;
        this.schedule = schedule;
        this.notesLink = notesLink;
    }


    public String getTitle() { return this.title; }
    public String getTutorID() { return this.tutorID; }
    public double getFees() { return this.fees; }
    public String getSchedule() { return this.schedule; }
    public String getNotesLink() { return this.notesLink; }

    public void setID(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTutorID(String tutorID) {
        this.tutorID = tutorID;
    }

    public void setFees(double fees) {
        this.fees = fees;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public void setNotesLink(String notesLink) {
        this.notesLink = notesLink;
    }

    @Override
    public String getID(){
        return this.id;
    }

    @Override
    public String[] getAttributes(){
        String[] attributes = {this.id, this.title, this.tutorID, Double.toString(this.fees), this.schedule, this.notesLink};

        return attributes;
    }
}