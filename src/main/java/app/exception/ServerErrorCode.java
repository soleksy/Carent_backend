package app.exception;

public enum ServerErrorCode {

    INVALID_LOGIN_OR_PASSWORD("Invalid login or password"),
    INVALID_ACCESS_TOKEN("Malformed token"),
    USER_LOGGED_OUT("User has been logged out"),
    PASSWORDS_DONT_MATCH("confirmPassword field does not match password field"),
    EMAIL_NOT_UNIQUE("Email address: |0| is already in use");

    private final String msg;

    ServerErrorCode(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

}
