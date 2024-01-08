## Introduction 

> This is a java project that transfers files of any type from server to client. 

## Server

> Server.java contains the Server class with its port fixed to 8000.


## Client

> Client.java contains the Client class.
> It has the upload and download functions inside it.
> It asks you to enter port number(enter 'ftpclient 8000').
> For uploading you have to give 'upload <filename>' in the terminal.
> For downloading you have to give 'get <filename>' in the terminal.

## Instruction to run the project

1. Compile and run Server.java in one terminal.
`javac Server.java`
`java Server`
2. Open new terminal, compile and run Client.java
`javac Client.java`
`java Client`
3. On Client terminal, it asks you to enter port number. Enter:
`Ftpclient 8000`
4. The it asks you to choose upload, download or exit loop:
`If you want to upload file enter: 
upload <filename>
If you want to download fle enter:
get <filename>
If you want to exit enter:
exit loop`
