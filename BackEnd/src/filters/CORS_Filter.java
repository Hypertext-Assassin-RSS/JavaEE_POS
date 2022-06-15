package filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author : Rajith Sanjaya
 * @project : JavaEE_POS
 * @created : 2022 June 15
 **/

@WebFilter(urlPatterns = "/*")
public class CORS_Filter implements Filter{
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        filterChain.doFilter(servletRequest,servletResponse);

        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.addHeader("Access-Control-Allow-Origin","*");
        response.addHeader("Access-Control-Allow-Methods","DELETE,PUT");
        response.addHeader("Access-Control-Allow-Headers","Content-Type");

    }

    @Override
    public void destroy() {

    }


}
