public class Enrolment implements GetData{
    private String id;
    private String studentID;
    private String level;
    private String date;

    Enrolment(String id, String studentID, String level, String date){

        this.id = id;
        this.studentID = studentID;
        this.level = level;
        this.date = date;

    }

    @Override
    public String getID(){
        return this.id;
    }

    @Override
    public String[] getAttributes(){
        String[] attributes = {this.id, this.studentID, this.level, this.date};
        return attributes;
    }

    public String getStudentID(){
        return this.studentID;
    }
    public String getLevel(){
        return this.level;
    }
    public String getDate(){
        return this.date;
    }

    public void setLevel(String level){
        this.level = level;
    }
}