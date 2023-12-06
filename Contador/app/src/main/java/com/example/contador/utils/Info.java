package com.example.contador.utils;
public class Info {
    private final String infotitle;
    private final String infomain;
    private final String infodetails;
    private final int image;
    private final int color;


    public Info(String infotitle,String infomain, String infodetails, int image, int color) {
        this.infotitle = infotitle;
        this.infomain = infomain;
        this.infodetails = infodetails;
        this.image = image;
        this.color = color;
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
    public int getColor(){ return color;}

    public int getImage() {
        return image;
    }
}
