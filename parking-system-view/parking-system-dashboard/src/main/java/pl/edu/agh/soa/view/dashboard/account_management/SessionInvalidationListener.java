package pl.edu.agh.soa.view.dashboard.account_management;

import org.jboss.security.CacheableManager;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.security.Principal;

/**
 * @author Mateusz Kluska
 */
@WebListener
public class SessionInvalidationListener implements HttpSessionListener {

    @Inject
    private Principal principal;

    @Resource(name = "java:jboss/jaas/parking/authenticationMgr")
    private CacheableManager<?, Principal> authenticationManager;

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        // not used
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        authenticationManager.flushCache();
    }
}