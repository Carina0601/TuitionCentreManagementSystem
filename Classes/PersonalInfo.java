public class PersonalInfo implements GetData, GetSetRoleData {

    private String id;
    private String name;
    private String ic;
    private String email;
    private String phoneNo;
    private String address;
    private String username;
    private String password;

    PersonalInfo(String id, String name, String ic, String email, String phoneNo,
                 String address, String username, String password){

        this.id = id;
        this.name = name;
        this.ic = ic;
        this.email = email;
        this.phoneNo = phoneNo;
        this.address = address;
        this.username = username;
        this.password = password;

    }

    @Override
    public String getID(){
        return this.id;
    }

    @Override
    public String[] getAttributes(){
        String[] attributes = {this.id, this.name, this.ic, this.email, this.phoneNo, this.address,
                this.username, this.password};

        return attributes;
    }

    @Override
    public void setEmail(String email){
        this.email = email;
    }

    @Override
    public void setPhoneNo(String phoneNo){
        this.phoneNo = phoneNo;
    }

    @Override
    public void setAddress(String address){
        this.address = address;
    }

    @Override
    public void setUsername(String username){
        this.username = username;
    }

    @Override
    public void setPassword(String password){
        this.password = password;
    }

    @Override
    public String getEmail(){
        return this.email;
    }

    @Override
    public String getPhoneNo(){
        return this.phoneNo;
    }

    @Override
    public String getAddress(){
        return this.address;
    }

    @Override
    public String getUsername(){
        return this.username;
    }

    @Override
    public String getPassword(){
        return this.password;
    }

    public String getIC(){
        return this.ic;
    }

    public String getName(){
        return this.name;
    }
}
