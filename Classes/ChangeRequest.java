public class ChangeRequest implements GetData {

    private String id;
    private String studentID;
    private String typeOfRequest;
    private String oldSubjectID;
    private String newSubjectID;
    private String status;
    private String date;
    private String activity;

    ChangeRequest(String id, String studentID, String typeOfRequest, String oldSubjectID, String newSubjectID, String status, String date, String  activity){

        this.id = id;
        this.studentID = studentID;
        this.typeOfRequest = typeOfRequest;
        this.oldSubjectID = oldSubjectID;
        this.newSubjectID = newSubjectID;
        this.status = status;
        this.date = date;
        this.activity = activity;

    }

    @Override
    public String getID(){
        return this.id;
    }

    @Override
    public String[] getAttributes(){
        String[] attributes = {this.id, this.studentID, this.typeOfRequest, this.oldSubjectID, this.newSubjectID, this.status, this.date, this.activity};

        return attributes;
    }

    public String getStudentID(){
        return this.studentID;
    }

    public String getTypeOfRequest(){
        return this.typeOfRequest;
    }

    public String getOldSubjectID(){
        return this.oldSubjectID;
    }

    public String getNewSubjectID(){
        return this.newSubjectID;
    }

    public String getStatus(){
        return this.status;
    }

    public String getDate(){
        return this.date;
    }

    public void setStatus(String status){
        this.status = status;
    }

    public String getActivity(){
        return this.activity;
    }

    public void setActivity(String activity){
        this.activity = activity;
    }
}
