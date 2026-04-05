public class Bill implements GetData {

    private String id;
    private String studentID;
    private String date;
    private String subjectID1;
    private String subjectID2;
    private String subjectID3;
    private double fees;
    private String status;
    private String wayOfGeneration;

    Bill(String id, String studentID, String date, String subjectID1, String subjectID2, String subjectID3, double fees, String status, String wayOfGeneration){

        this.id = id;
        this.studentID = studentID;
        this.date = date;
        this.subjectID1 = subjectID1;
        this.subjectID2 = subjectID2;
        this.subjectID3 = subjectID3;
        this.fees = fees;
        this.status = status;
        this.wayOfGeneration = wayOfGeneration;

    }

    @Override
    public String getID(){
        return this.id;
    }

    @Override
    public String[] getAttributes(){
        String[] attributes = {this.id, this.studentID, this.date, this.subjectID1, this.subjectID2, this.subjectID3,
                Double.toString(this.fees), this.status, this.wayOfGeneration};

        return attributes;
    }

    public String getDate(){
        return this.date;
    }

    public String getWayOfGeneration(){
        return this.wayOfGeneration;
    }

    public String getStudentID(){
        return this.studentID;
    }

    public String getSubjectID1(){
        return this.subjectID1;
    }

    public String getSubjectID2(){
        return this.subjectID2;
    }

    public String getSubjectID3(){
        return this.subjectID3;
    }

    public double getFees(){
        return this.fees;
    }

    public String getStatus(){
        return this.status;
    }

    public void setStatus(String status){
        this.status = status;
    }
}
