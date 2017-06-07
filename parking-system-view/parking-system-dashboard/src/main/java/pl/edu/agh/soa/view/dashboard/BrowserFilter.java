package pl.edu.agh.soa.view.dashboard;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Mateusz Kluska
 */
@WebFilter(filterName = "onlyChrome")
public class BrowserFilter implements Filter {
    private static final String USER_AGENT_HEADER = "User-Agent";
    private static final String CHROME_ID = "Chrome";
    private static final String BAD_BROWSER_URI = "/parkingdashboard/badBrowser.html";

    @Override
    public void init(FilterConfig cfg) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        String userAgent = ((HttpServletRequest) req).getHeader(USER_AGENT_HEADER);
        if (userAgent.contains(CHROME_ID))
            chain.doFilter(req, resp);
        else
            ((HttpServletResponse) resp).sendRedirect(BAD_BROWSER_URI);
    }

    @Override
    public void destroy() {
    }
}
