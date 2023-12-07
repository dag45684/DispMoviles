package com.example.contador.utils;
public class Leader {
    private final String player;
    private final String score;
    private final String pos;
    private final int color;


    public Leader(String data, int pos, int color) {
        player = data.split("\\s\\|\\s")[1];
        score = data.split("\\s\\|\\s")[3];
        this.pos = Integer.toString(pos+1);
        this.color = color;
    }

    public String getPlayer() {
        return player;
    }

    public String getScore() {
        return score;
    }
    public String getPos() {
        return pos;
    }
    public int getColor(){ return color;}
}
