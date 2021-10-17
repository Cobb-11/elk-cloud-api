package elk.cloud.api.service.vo;

public class LoginVO {

    public static final String SUCCESS = "0";

    public static final String FAIL = "-1";

    private String status = SUCCESS;

    private String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
