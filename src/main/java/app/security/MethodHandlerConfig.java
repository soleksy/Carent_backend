package app.security;

import app.entity.UserEntity;
import app.exception.ServerErrorCode;
import app.exception.ServerException;
import app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class MethodHandlerConfig implements HandlerInterceptor {

    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            AuthorizedAs authorizedAs = ((HandlerMethod) handler).getMethodAnnotation(AuthorizedAs.class);
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && !"anonymousUser".equals(authentication.getName()) && authorizedAs != null
                    && authorizedAs.value().length > 0) {
                UserEntity user = userService.getUserByUsername(authentication.getName());
                if (user != null && user.getRole() != null) {
                    String[] allowedRoles = authorizedAs.value();
                    for (String role : allowedRoles) {
                        if (user.getRole().getRole().equals(role)) {
                            return true;
                        }
                    }
                }
                throw new ServerException(ServerErrorCode.INSUFFICIENT_PRIVILEGES);
            }
        }
        return true;
    }
}
