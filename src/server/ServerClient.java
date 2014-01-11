package server;

import java.net.InetAddress;

public class ServerClient {

	public InetAddress address;
	public int port;
	private final int ID;
	public int attempt = 0;
	
	public int x = 3;
	public int y = 3;
	public int dir = 2;
	public boolean walking = false;
	public boolean frozen = false;

	public ServerClient(final int id, InetAddress address, int port) {
		this.address = address;
		this.port = port;
		this.ID = id;
	}
	
	public int getID() {
		return ID;
	}

}
