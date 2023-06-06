package RestProject1.RestAPI.util;


public class ErrorResponse {
    private String message;

    private long time;

    public ErrorResponse(){}

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
