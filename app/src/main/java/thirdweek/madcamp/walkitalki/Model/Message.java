package thirdweek.madcamp.walkitalki.Model;

public class Message {
    private String name;
    String message;

    public Message(){}

    public Message(String newName, String newMessage) {
        this.name = newName;
        this.message = newMessage;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public void setMessage(String newMessage) {
        this.message = newMessage;
    }
}
