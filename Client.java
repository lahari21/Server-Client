import java.net.*;
import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import java.util.*;

public class Client {
    public Socket requestSocket; // socket connect to the server
    FileInputStream f; // read stream data from file
    FileOutputStream fo; // write stream data into file
    DataInputStream in; // strream to read from socket
    DataOutputStream out; // stream to write to socket

    public void Client() {
    }

    void run() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            String[] port = new String[2];
            while (true) {
                System.out.println("Enter the <ftpclient port> number: "); // read ftp port from the user.
                String p = bufferedReader.readLine();
                port = p.split(" ");
                System.out.println(port[1]);
                if (port[1].equals("8000")) {
                    break;
                } else {
                    System.out.println("Invalid port number");
                }
            }
            // create a socket to connect to the server
            System.out.println("reached here");
            requestSocket = new Socket("localhost", Integer.parseInt(port[1]));
            System.out.println("Connected to localhost in port " + port[1]);

            // initialize inputStream and outputStream
            out = new DataOutputStream(requestSocket.getOutputStream()); // new DataInputStream(c.getInputStream());
            out.flush();
            in = new DataInputStream(requestSocket.getInputStream());

            while (true) {
                // get Input from standard input
                System.out.print("Hello, please select Your Choice: get file or upload file or exit loop: ");
                // read choice from the standard input
                String choice = bufferedReader.readLine();
                String[] Str = choice.split(" ");
                System.out.println(Str[1]);
                // Send the sentence to the server
                // sendMessage(message);
                // Receive the upperCase sentence from the server
                // MESSAGE = (String)in.readObject();
                // show the message to the user
                if (Str[0].equals("get")) {
                    out.writeUTF("DOWNLOAD");
                    out.writeUTF(Str[1]);
                    DownloadFile(Str[1]);
                    // break;
                } else if (Str[0].equals("upload")) {
                    out.writeUTF("UPLOAD");
                    out.writeUTF(Str[1]);
                    UploadFile(Str[1]);
                    // break;
                } else if (Str[0].equals("exit")) {
                    break;
                } else {
                    System.out.println("Invalid option!");
                }

            }
        } catch (ConnectException e) {
            System.err.println("Connection refused. You need to initiate a server first.");
        } catch (Exception e) {
            System.err.println("Error!");
        } finally {
            // Close connections
            try {
                in.close();
                out.close();
                requestSocket.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    // send a message to the output stream
    void sendMessage(String msg) {
        try {
            // stream write the message
            out.writeUTF(msg);
            // out.flush();
            System.out.println("Send message: " + msg);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    void UploadFile(String filename) {
        try {
            System.out.println("Uploading file:" + filename);
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
                System.out.println("File uploaded successfully");

            } else {
                out.writeLong(file.length());
                out.writeUTF(" ");
                System.out.println("No file found");
                // System.out.println("file not found!");
            }
        } catch (Exception e) {
            System.out.println("exception" + e);
        }
    }

    void DownloadFile(String filename) {
        System.out.println("Download file:" + filename);
        try {
            int read = 0;
            String filename_new = "new" + filename;// test_download.pptx";
            fo = new FileOutputStream(filename_new);
            long l = in.readLong();
            // System.out.println("lon");
            if (l == 0) {
                System.out.println("File not found");
            } else {
                byte[] data = new byte[1000];
                while (l > 0 && (read = in.read(data, 0, (int) Math.min(data.length, l))) != -1) {
                    fo.write(data, 0, read);
                    l -= read;
                }
                fo.close();
                System.out.println("Successfully downloaded file");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // main method
    public static void main(String args[]) {
        Client client = new Client();
        client.run();
    }

}
