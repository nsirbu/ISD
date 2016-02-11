package com.udp.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import isd.model.Message;

import com.udp.helper.*;

/**
 * Contains all the methods related to the Server and Client side communication.
 * @author sscerbatiuc
 */
public class Server {

	private static Server instance;

	private byte[] tempBuffer;
	private final short bufferLength = 512;

	private byte[] receivedData;
	private byte[] responseData;
	private DatagramSocket serverSocket;

	// --------------- GETTERS & SETTERS ---------------

	public void setTempBuffer(byte[] tempBuffer) {
		this.tempBuffer = tempBuffer;
	}

	public static void setInstance(Server instance) {
		Server.instance = instance;
	}

	public void setServerSocket(DatagramSocket serverSocket) {
		this.serverSocket = serverSocket;
	}

	public void setReceivedData(byte[] receivedData) {
		this.receivedData = receivedData;
	}

	public void setResponseData(byte[] responseData) {
		this.responseData = responseData;
	}

	public byte[] getTempBuffer() {
		return tempBuffer;
	}

	public short getBufferLength() {
		return bufferLength;
	}

	public byte[] getReceivedData() {
		return receivedData;
	}

	public byte[] getResponseData() {
		return responseData;
	}

	public DatagramSocket getServerSocket() {
		return serverSocket;
	}

	// -------------------------------------------
	/**
	 * CONSTRUCTOR
	 */
	private Server() throws SocketException {

		this.setServerSocket(new DatagramSocket(Constants.SERVER_PORT));

	}

	/**
	 * Returns a single instance of UDP Server
	 * 
	 * @return Server
	 */
	public static Server getInstance() throws SocketException {
		if (instance == null) {
			instance = new Server();
			return instance;
		} else {
			return instance;
		}
	}

	/**
	 * Receives the data <code>DatagramPacket</code> from the client, parses the
	 * textual representation of the data and creates and extracts the textual
	 * representation of the data in the packet
	 * 
	 * @return
	 */
	public Message readMessage() {
		try {
			String receivedString = this.readString();
			System.out.println("Received data: " + receivedString);
			String[] splitMessage = receivedString.split("|");
			Message receivedMessage = new Message(false,
					Boolean.parseBoolean(splitMessage[1]),
					Integer.parseInt(splitMessage[3]),
					TimeHelper.getCurrentTime());

			return receivedMessage;

		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * Receives the String from the client
	 * 
	 * @return String; null - in case of error
	 */
	public String readString() {
		try {
			this.setTempBuffer(new byte[this.getBufferLength()]);
			DatagramPacket receivedPacket = new DatagramPacket(
					this.getTempBuffer(), this.getBufferLength());
			serverSocket.receive(receivedPacket);

			// Initialize array for received data
			this.receivedData = new byte[receivedPacket.getLength()];
			System.arraycopy(receivedPacket.getData(),
					receivedPacket.getOffset(), this.getReceivedData(), 0,
					receivedPacket.getLength());

			String receivedString = new String(this.getReceivedData());
			System.out.println("Received string: " + receivedString);
			return receivedString;

		} catch (IOException ioEx) {
			return null;
		}
	}

	/**
	 * Sends the message to a specified client
	 * 
	 * @param message
	 *            <code>String</code>
	 * @param clientAddress
	 *            <code>InetAddress</code>
	 * @param port
	 *            <code>Integer</code>
	 * @return boolean
	 */
	public boolean respondToClient(String message, InetAddress clientAddress,
			int port) {
		try {
			responseData = message.getBytes();
			DatagramPacket responsePacket = new DatagramPacket(responseData,
					responseData.length, clientAddress, port);
			serverSocket.send(responsePacket);
			return true;
		} catch (SocketException sockEx) {
			return false;

		} catch (IOException ioEx) {
			return false;
		}

	}

}
