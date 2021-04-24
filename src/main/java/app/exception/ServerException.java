package app.exception;

import app.utils.MessageUtils;

public class ServerException extends Exception {

    private final ServerErrorCode error;

    public ServerException(ServerErrorCode error, Object... params){
        super(MessageUtils.initErrorMsg(error.getMsg(), params));
        this.error = error;
    }

    public ServerErrorCode getError() {
        return error;
    }

}
