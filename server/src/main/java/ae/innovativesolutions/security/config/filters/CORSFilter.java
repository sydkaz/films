package ae.innovativesolutions.security.config.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Syed.
 */
@Component
public class CORSFilter implements Filter
{
    private static final Logger LOG = LoggerFactory.getLogger(CORSFilter.class);
    private String mode = "ALLOW";//"DENY";
    private String allowed_methods="POST, GET, OPTIONS";
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("Mode is set to "+mode);
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String host = "http://" + request.getServerName() + ":" + request.getServerPort();
        httpResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpResponse.addHeader("X-FRAME-OPTIONS", mode ); // that will disable iframe of the site
        httpResponse.addHeader("host", host);
        httpResponse.setHeader("Access-Control-Allow-Methods", allowed_methods);
        httpResponse.setHeader("Access-Control-Allow-Headers", "X-Auth-Token, Content-Type");
        httpResponse.setHeader("Access-Control-Expose-Headers", "custom-header1, custom-header2");
        httpResponse.setHeader("Access-Control-Allow-Credentials", "false");
        httpResponse.setHeader("Access-Control-Max-Age", "4800");
        System.out.println("---CORS Configuration Completed---");
        chain.doFilter(request, response);
    }

    public void destroy() {
    }

    public void init(FilterConfig filterConfig) {

        String configMode = filterConfig.getInitParameter("mode");
        String allowedMethods = filterConfig.getInitParameter("cors.allowed.methods");
        if ( configMode != null ) {
            mode = configMode;
        }
        if ( allowedMethods != null ) {
            mode = allowedMethods;
        }


    }

}