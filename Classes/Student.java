public class Student extends PersonalInfo{

    private String level;
    private String registerDate;
    private int noOfSubjects;

    Student(String id, String name, String ic, String email, String phoneNo,
            String address, String username, String password, String level, String registerDate,
            int noOfSubjects){

        super(id, name, ic, email, phoneNo, address, username, password);
        this.level = level;
        this.registerDate = registerDate;
        this.noOfSubjects = noOfSubjects;

    }

    @Override
    public String[] getAttributes(){
        String[] attributes = {getID(), getName(), getIC(), getEmail(), getPhoneNo(), getAddress(),
                getUsername(), getPassword(), this.level, this.registerDate, Integer.toString(this.noOfSubjects)};

        return attributes;
    }

    public String getLevel(){
        return this.level;
    }

    public String getRegisterDate(){
        return this.registerDate;
    }

    public int getNoOfSubjects(){
        return this.noOfSubjects;
    }

    public void setNoOfSubjects(int noOfSubjects){
        this.noOfSubjects = noOfSubjects;
    }

    public void setRegisterDate(String registerDate){
        this.registerDate = registerDate;
    }

    public void setLevel (String level){
        this.level = level;
    }

}