package com.aussipvp.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import com.aussipvp.Game;
import com.aussipvp.entity.mob.MultiPlayer;
import com.aussipvp.server.packets.Packet;
import com.aussipvp.server.packets.Packet.PacketTypes;
import com.aussipvp.server.packets.Packet00Login;
import com.aussipvp.server.packets.Packet02Move;

public class Connection {

	private DatagramSocket socket;
	private String address;
	private int port;
	private InetAddress ip;
	private Thread send;
	private Game game;

	public Connection() {
		address = "localhost";
		port = 9999;
		openConnection(address + ":" + port);
	}

	private boolean openConnection(String address) {
		try {
			socket = new DatagramSocket();
			ip = InetAddress.getByName(address);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return false;
		} catch (SocketException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public void run() {
		while (true) {
			byte[] data = new byte[1024];
			DatagramPacket packet = new DatagramPacket(data, data.length);
			try {
				socket.receive(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
			this.parsePacket(packet.getData(), packet.getAddress(), packet.getPort());
		}
	}

	public void sendData(byte[] data) {
		DatagramPacket packet = new DatagramPacket(data, data.length, ip, port);
		try {
			socket.send(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void parsePacket(byte[] data, InetAddress address, int port) {
		String message = new String(data).trim();
		PacketTypes type = Packet.lookupPacket(message.substring(0, 2));
		Packet packet = null;
		switch (type) {
		default:
		case INVALID:
			break;
		case LOGIN:
			packet = new Packet00Login(data);
			handleLogin((Packet00Login) packet);
		case MOVE:
			packet = new Packet02Move(data);
			handleMove((Packet02Move) packet);
		}
	}

	private void handleLogin(Packet00Login packet) {
		game.level.addPlayer(new MultiPlayer(packet.getX(), packet.getY(), packet.getUsername()));
	}

	private void handleMove(Packet02Move packet) {
		game.level.movePlayer(packet.getUsername(), packet.getX(), packet.getY(), packet.isMoving(), packet.getMovingDir());
	}
}
