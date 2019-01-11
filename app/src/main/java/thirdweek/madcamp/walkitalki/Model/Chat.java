package thirdweek.madcamp.walkitalki.Model;

public class Chat {
    public User chat_sender;
    public String chat_time;
    public String chat_content;

    public String getChat_content() {
        return chat_content;
    }

    public String getChat_time(){
        return chat_time;
    }

    public User getChat_sender(){
        return chat_sender;
    }

    public void setChat_sender(User chat_sender){
        this.chat_sender = chat_sender;
    }

    public void setChat_time(String chat_time){
        this.chat_time = chat_time;
    }

    public void setChat_content(String chat_content){
        this.chat_content=chat_content;
    }
}
