package com.mygdx.utils;

public class RoomItem {
    private String roomID;
    private String player1;
    private String player2;
    private String p1ID;
    private String p2ID;


    public RoomItem(String roomID, String player1, String player2, String p1ID, String p2ID) {
        this.roomID = roomID;
        this.player1 = player1;
        this.player2 = player2;
        this.p1ID = p1ID;
        this.p2ID = p2ID;
    }


    public String getRoomID() {
        return roomID;
    }

    public String getPlayer1() {
        return player1;
    }

    public String getPlayer2() {
        return player2;
    }

    public String getP1ID() {
        return p1ID;
    }

    public String getP2ID() {
        return p2ID;
    }
}
