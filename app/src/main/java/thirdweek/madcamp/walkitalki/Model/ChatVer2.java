package thirdweek.madcamp.walkitalki.Model;

public class ChatVer2 {
    public User chat_sender;
    public String chat_content;

    public ChatVer2(){

    }

    public ChatVer2(User chat_sender,  String chat_content) {
        this.chat_sender = chat_sender;
        this.chat_content = chat_content;

    }

    public String getChat_content() {
        return chat_content;
    }


    public User getChat_sender(){
        return chat_sender;
    }

    public void setChat_sender(User chat_sender){
        this.chat_sender = chat_sender;
    }

    public void setChat_content(String chat_content){
        this.chat_content=chat_content;
    }
}
