import java.net.*;
import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import java.util.*;

public class Server {
	int sPort = 8000; // The server will be listening on this port number
	ServerSocket sSocket; // serversocket used to lisen on port number 8000
	Socket connection = null; // socket for the connection with the client
	FileOutputStream f; // writing data into file
	FileInputStream fin; // reading data from file
	DataInputStream in; // stream to read from socket
	DataOutputStream out; // stream to write to the socket

	public void Server() {
	}

	void run() {
		try {
			// create a serversocket
			sSocket = new ServerSocket(sPort, 10);
			// Wait for connection
			System.out.println("Waiting for connection");
			// accept a connection from the client
			connection = sSocket.accept();
			System.out.println("Connection received from " + connection.getInetAddress().getHostName());
			// initialize Input and Output streams
			System.out.println("here");
			out = new DataOutputStream(connection.getOutputStream());
			// System.out.print("recieved output");
			out.flush();
			in = new DataInputStream(connection.getInputStream());
			// try {
			while (true) {
				// receive the message sent from the client
				// message = (String)in.readObject();
				// show the message to the user
				// System.out.println("Receive message: " + message);
				// Capitalize all letters in the message
				// MESSAGE = message.toUpperCase();
				// send MESSAGE back to the client
				// sendMessage(MESSAGE);
				try {
					String input = in.readUTF();
					System.out.println(input);
					if (input.equals("UPLOAD")) {
						int read = 0;
						String f_name = in.readUTF();
						String filename = "new" + f_name;// test_upload.pptx";
						f = new FileOutputStream(filename);
						long l = in.readLong();
						// System.out.println("lon");
						if (l == 0) {
							System.out.println("file not found!");
						} else {
							byte[] data = new byte[1000];
							while (l > 0 && (read = in.read(data, 0, (int) Math.min(data.length, l))) != -1) {
								f.write(data, 0, read);
								l -= read;
							}
							f.close();
							System.out.println("File uploaded successfully");
						}
					} else if (input.equals("DOWNLOAD")) {
						String filename = in.readUTF();
						System.out.println("Getting the file: " + filename);
						int read = 0;
						File file = new File(filename);
						if (file.isFile()) {
							FileInputStream fin = new FileInputStream(file);
							byte[] data = new byte[1000];
							out.writeLong(file.length());
							while ((read = fin.read(data)) != -1) {
								out.write(data, 0, read);
								out.flush();
							}
							fin.close();
							System.out.println(filename + " file sent to client");
						} else {
							out.writeLong(file.length());
							// out.writeUTF(" ");
							System.out.println("No file found");
						}
					} else {
						System.out.println("Error at Server");
					}
				} catch (Exception e) {

				}
			}
			// } catch (ClassNotFoundException classnot) {
			// System.err.println("Data received in unknown format");
			// }
		} catch (IOException ioException) {
			ioException.printStackTrace();
		} finally {
			// Close connections
			try {
				in.close();
				out.close();
				sSocket.close();
			} catch (IOException ioException) {
				ioException.printStackTrace();
			}
		}
	}

	// send a message to the output stream
	void sendMessage(String msg) {
		try {
			out.writeUTF(msg);
			out.flush();
			System.out.println("Send message: " + msg);
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}

	public static void main(String args[]) {
		Server s = new Server();
		s.run();

	}

}
