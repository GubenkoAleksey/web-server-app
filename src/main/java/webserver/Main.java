package webserver;

public class Main {
    public static void main(String[] args) {

        WebServer webServer = new WebServer();
        webServer.config();
        webServer.start();

        // GET /index.html HTTP/1.1
        // path to resource = webappPath + URI =>
        // src/main/resources/webapp/index.html

        // // GET /css/styles.css HTTP/1.1
        // path to resource = webappPath + URI =>
        // src/main/resources/webapp/css/styles.css
    }
}
