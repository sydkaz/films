package ae.innovativesolutions.security.config.configs;

import ae.innovativesolutions.security.config.interceptor.HttpSessionIdHandshakeInterceptor;
import ae.innovativesolutions.security.config.interceptor.WebSocketHandshakeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Map;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

    /*
	  * Register Stomp endpoints: the url to open the WebSocket connection.
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {

        // Register the "/ws" endpoint, enabling the SockJS protocol.
        // SockJS is used (both client and server side) to allow alternative
        // messaging options if WebSocket is not available.
        /*registry.addEndpoint("/ws").setHandshakeHandler(
                new DefaultHandshakeHandler() {

                    @Override
                    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
                        if (request instanceof ServletServerHttpRequest) {
                            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
                            HttpSession session = servletRequest.getServletRequest().getSession();
                            attributes.put("sessionId", session.getId());
                            return new Principal() {
                                @Override
                                public String getName() {
                                    System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> here");
                                    return session.getId();
                                }
                            };
                        } else {
                            return null;
                        }
                    }
                }
        ).setAllowedOrigins("*").withSockJS().setInterceptors(new HttpSessionIdHandshakeInterceptor());;*/
        //registry.addEndpoint("/ws").setAllowedOrigins("*").withSockJS();

        registry.addEndpoint("/ws")
                .setHandshakeHandler(webSocketHandshakeHandler()).setAllowedOrigins("*")
                .withSockJS();



    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.setInterceptors(new ChannelInterceptorAdapter() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                Map<String, Object> sessionHeaders = SimpMessageHeaderAccessor.getSessionAttributes(message.getHeaders());
                String sessionId = (String) sessionHeaders.get("session-id");
                if (sessionId != null) {

                }
                return super.preSend(message, channel);
            }
        });
    }

    @Bean
    WebSocketHandshakeHandler webSocketHandshakeHandler() {

        return new WebSocketHandshakeHandler();
    }
}
