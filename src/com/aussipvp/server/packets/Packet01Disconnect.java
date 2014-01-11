package com.aussipvp.server.packets;

import com.aussipvp.server.Connection;

public class Packet01Disconnect extends Packet {

	public Packet01Disconnect(int packetId) {
	    super(packetId);
    }

    public void writeData(Connection client) {
	    
    }

    public byte[] getData() {
	    return null;
    }

}
