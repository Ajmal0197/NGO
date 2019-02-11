package hasan.app.com.ngodemo;

public class NGOclass {
    private String Address, Background,Imageurl, Name, Phone, Website;

    public NGOclass() {
    }

    public NGOclass(String address, String background, String imageurl, String name, String phone, String website) {
        Address = address;
        Background = background;
        Imageurl = imageurl;
        Name = name;
        Phone = phone;
        Website = website;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getBackground() {
        return Background;
    }

    public void setBackground(String background) {
        Background = background;
    }

    public String getImageurl() {
        return Imageurl;
    }

    public void setImageurl(String imageurl) {
        Imageurl = imageurl;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getWebsite() {
        return Website;
    }

    public void setWebsite(String website) {
        Website = website;
    }
}
