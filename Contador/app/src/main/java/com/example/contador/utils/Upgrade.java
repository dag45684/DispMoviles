package com.example.contador.utils;
public class Upgrade {
    private final String upgradetitle;
    private final String upgrademain;
    private  String upgradedetails;
    private final int tag;
    private final int image;
    private final int color;


    public Upgrade(String upgrademain, String upgradedetails, String upgradetitle, int image, int tag, int color) {
        this.upgradetitle = upgradetitle;
        this.upgrademain = upgrademain;
        this.upgradedetails = upgradedetails;
        this.image = image;
        this.tag = tag;
        this.color = color;
    }

    public String getUpgradetitle() {
        return upgradetitle;
    }

    public String getUpgrademain() {
        return upgrademain;
    }

    public String getUpgradedetails() {
        return upgradedetails;
    }

    public int getImage() {
        return image;
    }
    public int getTag() {
        return tag;
    }

    public int getColor(){ return color;}
    public void setUpgradedetails(String s){
        this.upgradedetails = s;
    }
}
