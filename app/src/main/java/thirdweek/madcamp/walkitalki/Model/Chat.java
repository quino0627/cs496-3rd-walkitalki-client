package thirdweek.madcamp.walkitalki.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Chat {

    @SerializedName("username")
    @Expose
    public String username;

    @SerializedName("content")
    @Expose
    public String content;

    @SerializedName("latitude")
    @Expose
    public double latitude;

    @SerializedName("longitude")
    @Expose
    public double longitude;

    @SerializedName("timestamp")
    @Expose
    public String timestamp;

    @SerializedName("userID")
    @Expose
    public Long userID;

    public Chat(){
    }

    public Chat(String chat_sender,  String chat_content, double latitude, double longitude, String timestamp) {
        this.username = chat_sender;
        this.content = chat_content;
        this.latitude = latitude;
        this.longitude = longitude;
        this.timestamp = timestamp;
    }

    public String getContent() {
        return content;
    }
    public Long getUserID(){
        return userID;
    }

    public String getUserName(){
        return username;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setChat_sender(String chat_sender){
        this.username = chat_sender;
    }

    public void setChat_content(String chat_content){
        this.content=chat_content;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
