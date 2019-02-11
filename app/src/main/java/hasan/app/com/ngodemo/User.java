package hasan.app.com.ngodemo;

public class User {

    private String uid, udisplayName, ugender, uphone, ucityName, uprofileImage;

    public User() {
    }

    public User(String id, String displayName, String gender, String phone, String cityName, String profileImage){
        this.uid=id;
        this.udisplayName=displayName;
        this.ugender= gender;
        this.uphone=phone;
        this.ucityName=cityName;
        this.uprofileImage=profileImage;
    }

    public String getUid() {
        return uid;
    }

    public String getUdisplayName() {
        return udisplayName;
    }

    public String getUgender() {
        return ugender;
    }

    public String getUphone() {
        return uphone;
    }

    public String getUcityName() {
        return ucityName;
    }

    public String getUprofileImage() {
        return uprofileImage;
    }
}
