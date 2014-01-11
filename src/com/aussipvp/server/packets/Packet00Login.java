package com.aussipvp.server.packets;

import com.aussipvp.server.Connection;

public class Packet00Login extends Packet {

	private String username;
    private int x, y;

    public Packet00Login(byte[] data) {
        super(00);
        String[] dataArray = readData(data).split(",");
        this.x = Integer.parseInt(dataArray[0]);
        this.y = Integer.parseInt(dataArray[1]);
    }

    public Packet00Login(String username, int x, int y) {
        super(00);
        this.username = username;
        this.x = x;
        this.y = y;
    }

    public void writeData(Connection client) {
        client.sendData(getData());
    }

    @Override
    public byte[] getData() {
        return ("00" + this.username + "," + getX() + "," + getY()).getBytes();
    }

    public String getUsername() {
        return username;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
