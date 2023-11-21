package com.example.contador.utils;

import java.math.BigInteger;

public class Jugador {
    private final int id;
    public final String name;
    public final String pass;
    public BigInteger score;
    DB_Handler db = new DB_Handler(null);

    public Jugador(String name, String pass){
        this.name = name;
        this.pass = pass;
        id = Integer.parseInt(db.rawQuery("Select COUNT(*)+1 from Jugadores").get(0));
        score = new BigInteger("0");
    }
    public Jugador(int id, String name, String pass){
        this.id = id;
        String[] data = db.readFromDB("id=" + id).get(0).split(" | ");
        this.name = data[0];
        this.pass = data[1];
        score = new BigInteger(data[2]);
    }

    public void setScore(BigInteger score) {
        this.score = score;
        db.rawUpdate("Update Jugadores set score = " + score.toString() + " where id = " + this.id);
    }
}
