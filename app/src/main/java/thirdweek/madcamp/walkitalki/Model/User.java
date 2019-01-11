package thirdweek.madcamp.walkitalki.Model;

public class User {
    public String user_id;
    public String user_name;

    public User(){
    }

    public void setUser_id(String user_id){
        this.user_id = user_id;
    }

    public void setUser_name(String user_name){
        this.user_name = user_name;
    }

    public String getUser_id(){
        return this.user_id;
    }

    public String getUser_name() {
        return this.user_name;
    }

    public User(String user_id, String user_name){
        this.user_id = user_id;
        this.user_name = user_name;
    }

}
