package thirdweek.madcamp.walkitalki.Model;

public class User {
    public Long user_id;
    public String user_name;

    public User(){
    }

    public void setUser_id(Long user_id){
        this.user_id = user_id;
    }

    public void setUser_name(String user_name){
        this.user_name = user_name;
    }

    public Long getUser_id(){
        return this.user_id;
    }

    public String getUser_name() {
        return this.user_name;
    }

    public User( String user_name,Long user_id){
        this.user_id = user_id;
        this.user_name = user_name;
    }

}
