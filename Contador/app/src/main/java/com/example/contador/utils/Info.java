package com.example.contador.utils;
public class Info {
    private final String infotitle;
    private final String infomain;
    private final String infodetails;
    private final int image;


    public Info(String infotitle,String infomain, String infodetails, int image) {
        this.infotitle = infotitle;
        this.infomain = infomain;
        this.infodetails = infodetails;
        this.image = image;
    }

    public String getInfotitle() {
        return infotitle;
    }

    public String getInfomain() {
        return infomain;
    }
    public String getInfodetails() {
        return infodetails;
    }

    public int getImage() {
        return image;
    }
}
