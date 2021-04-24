package app.utils;

import app.exception.ErrorTemplate;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

public class MessageUtils {

    public static String initErrorMsg(String msg, Object... params) {
        String resultMsg = msg;
        if (!StringUtils.isEmpty(msg) && params.length > 0) {
            for (int i = 0; i < params.length; i++) {
                resultMsg = resultMsg.replace("|" + i + "|", params[i].toString());
            }
        }
        return resultMsg;
    }

    public static void setCommonErrorFields(ErrorTemplate error, HttpServletRequest request, String msg) {
        error.setMessage(msg);
        error.setTimestamp(LocalDateTime.now());
        error.setPath(request.getRequestURI());
    }

}
