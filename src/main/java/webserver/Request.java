package webserver;

public class Request {

    String httpMethod;
    String uri;
    String protocolVersion;
    boolean isRightAppName;

    public Request() {
    }

    public Request(String httpMethod, String uri, String protocolVersion, boolean isRightAppName) {
        this.httpMethod = httpMethod;
        this.uri = uri;
        this.protocolVersion = protocolVersion;
        this.isRightAppName = isRightAppName;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getProtocolVersion() {
        return protocolVersion;
    }

    public void setProtocolVersion(String protocolVersion) {
        this.protocolVersion = protocolVersion;
    }

    public boolean isRightAppName() {
        return isRightAppName;
    }

    public void setRightAppName(boolean rightAppName) {
        isRightAppName = rightAppName;
    }

    @Override
    public String toString() {
        return "Request{" +
                "httpMethod='" + httpMethod + '\'' +
                ", uri='" + uri + '\'' +
                ", protocolVersion='" + protocolVersion + '\'' +
                ", isRightAppName=" + isRightAppName +
                '}';
    }
}
