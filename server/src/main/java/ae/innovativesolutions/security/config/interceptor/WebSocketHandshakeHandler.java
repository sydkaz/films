package ae.innovativesolutions.security.config.interceptor;

import com.sun.security.auth.UserPrincipal;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Map;
import java.util.UUID;

public class WebSocketHandshakeHandler extends DefaultHandshakeHandler {

    @Override
    protected Principal determineUser(final ServerHttpRequest request,
                                      final WebSocketHandler wsHandler,
                                      final Map<String, Object> attributes) {
        ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
        HttpSession session = servletRequest.getServletRequest().getSession();
        attributes.put("sessionId", session.getId());

        Principal principal = super.determineUser(request, wsHandler, attributes);

        Principal randomPrincipal = new UserPrincipal(UUID.randomUUID().toString());
        System.out.println("Random Principal:  " + randomPrincipal.getName());
        return principal != null ? principal : randomPrincipal;
    }
}
