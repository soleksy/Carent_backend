package app.exception;

import app.utils.MessageUtils;

public class ServerRuntimeException extends RuntimeException {

    private final ServerErrorCode error;

    public ServerRuntimeException(ServerErrorCode error, Object... params){
        super(MessageUtils.initErrorMsg(error.getMsg(), params));
        this.error = error;
    }

    public ServerErrorCode getError() {
        return error;
    }

}
