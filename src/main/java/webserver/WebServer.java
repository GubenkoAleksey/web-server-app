package webserver;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class WebServer {

    int port;
    String appName;
    Map<String, String> listOfWebPages;
    Request request;
    final String FILE_NOT_FOUND = "src\\main\\resources\\webapp\\404.html";

    // TO DO - config from file
    public void config() {
        port = 3000;
        appName = "web-server-app";
        listOfWebPages = new HashMap<>();
        listOfWebPages.put("\\web-server-app\\index", "src\\main\\resources\\webapp\\index.html");
        listOfWebPages.put("\\web-server-app", "src\\main\\resources\\webapp\\home.html");
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println(" === Server start to work === ");
            try (Socket socket = serverSocket.accept();
                 BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                 BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                 BufferedOutputStream dataOut = new BufferedOutputStream(socket.getOutputStream())) {


                String line = "";

                System.out.println("Path - " + new File("").getPath());
                System.out.println("AbsPath" + new File("").getAbsolutePath());

                int lineCount = 0;
                while (!(line = reader.readLine()).isEmpty()) {
                    System.out.println(line);
                    if (lineCount == 0) {
                        request = parseFirstLineOfRequest(line, appName);
                    }
                    lineCount++;
                }

                System.out.println("Ready to write response");

                if (request.getHttpMethod().equals("GET")) {
                    if (request.isRightAppName && listOfWebPages.containsKey(request.getUri())) {
                        File file = new File(listOfWebPages.get(request.getUri()));
                        int fileLength = (int) file.length();
                        String content = "text/html";

                        byte[] fileData = readFileData(file, fileLength);

                        writer.write("HTTP/1.1 200 OK");
                        writer.write("Server: Java HTTP Server from SSaurel : 1.0");
                        writer.write("Date: " + new Date());
                        writer.write("Content-type: " + content);
                        writer.write("Content-length: " + fileLength);

                        writer.newLine();
                        writer.newLine();
                        writer.flush();

                        dataOut.write(fileData, 0, fileLength);
                        dataOut.flush();
                    } else {
                        fileNotFound(writer, dataOut, request.uri);
                    }

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Request parseFirstLineOfRequest(String firstLineOfRequest, String appName) {

        List<String> stringList = new ArrayList<>(Arrays.asList(firstLineOfRequest.split(" ")));
        List<String> urlList = new ArrayList<>(Arrays.asList(stringList.get(1).split("/")));

        Request request = new Request();
        request.setHttpMethod(stringList.get(0));
        request.setProtocolVersion(stringList.get(stringList.size() - 1));

        if (urlList.size() == 1) {
            request.setRightAppName(false);
        } else if (urlList.size() > 1) {
            request.setRightAppName(appName.equals(urlList.get(1)));
            urlList.remove(0);
            if (!urlList.isEmpty()) {
                String uri = "";
                for (String temp : urlList) {
                    uri = uri + "\\" + temp;
                }
                request.setUri(uri);
            }
        }
        return request;
    }

    private byte[] readFileData(File file, int fileLength) throws IOException {
        FileInputStream fileIn = null;
        byte[] fileData = new byte[fileLength];

        try {
            fileIn = new FileInputStream(file);
            fileIn.read(fileData);
        } finally {
            if (fileIn != null)
                fileIn.close();
        }

        return fileData;
    }

    private void fileNotFound(BufferedWriter writer, OutputStream dataOut, String fileRequested) throws IOException {
        File file = new File(FILE_NOT_FOUND);
        int fileLength = (int) file.length();
        String content = "text/html";
        byte[] fileData = readFileData(file, fileLength);

        writer.write("HTTP/1.1 404 File Not Found");
        writer.write("Server: Java HTTP Server from SSaurel : 1.0");
        writer.write("Date: " + new Date());
        writer.write("Content-type: " + content);
        writer.write("Content-length: " + fileLength);

        writer.newLine();
        writer.newLine();
        writer.flush();

        dataOut.write(fileData, 0, fileLength);
        dataOut.flush();

        System.out.println("File " + fileRequested + " not found");
    }
}
