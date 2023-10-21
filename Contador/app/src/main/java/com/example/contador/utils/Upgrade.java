package com.example.contador.utils;
public class Upgrade {
    private final String upgradetitle;
    private final String upgrademain;
    private  String upgradedetails;
    private final int tag;
    private final int image;


    public Upgrade(String upgrademain, String upgradedetails, String upgradetitle, int image, int tag) {
        this.upgradetitle = upgradetitle;
        this.upgrademain = upgrademain;
        this.upgradedetails = upgradedetails;
        this.image = image;
        this.tag = tag;
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

    public void setUpgradedetails(String s){
        this.upgradedetails = s;
    }
}
