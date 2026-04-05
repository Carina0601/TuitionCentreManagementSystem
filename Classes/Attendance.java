public class Attendance implements GetData {

    private String attendanceID;
    private String studentID;
    private String level;
    private String date;
    private String status;

    Attendance(String attendanceID, String studentID, String level, String  date, String status){

        this.attendanceID = attendanceID;
        this.studentID = studentID;
        this.level = level;
        this.date = date;
        this.status = status;

    }
    @Override
    public String getID(){
        return this.attendanceID;
    }

    @Override
    public String[] getAttributes(){
        String[] attributes = {this.attendanceID, this.studentID, this.level, this.date, this.status};
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
    public String getStatus(){
        return this.status;
    }

}