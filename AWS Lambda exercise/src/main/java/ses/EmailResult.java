package ses;

//This is a simple POJO
public class EmailResult {
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    private String message;
    private String timestamp;

    public EmailResult() {}
}
