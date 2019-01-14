package thirdweek.madcamp.walkitalki.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Chat {

    @SerializedName("username")
    @Expose
    public String username;

    @SerializedName("userID")
    @Expose
    public Long userID;

    @SerializedName("content")
    @Expose
    public String content;

    @SerializedName("latitude")
    @Expose
    public double latitude;

    @SerializedName("longitude")
    @Expose
    public double longitude;

    //서버에서 자동으로 시간 찍어줘서 client 쪽에서는 필요 없음
    @SerializedName("timestamp")
    @Expose
    public String timestamp;



    public Chat(){
    }

    public Chat(String chat_sender, Long chat_senderID,  String chat_content, double latitude, double longitude) {
        this.username = chat_sender;
        this.userID = chat_senderID;
        this.content = chat_content;
        this.latitude = latitude;
        this.longitude = longitude;
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

}
