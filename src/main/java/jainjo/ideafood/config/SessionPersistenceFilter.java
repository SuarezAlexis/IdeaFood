package jainjo.ideafood.config;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;
import org.springframework.web.filter.GenericFilterBean;


public class SessionPersistenceFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if(request instanceof HttpServletRequest){
            SecurityContext sc = (SecurityContext) ((HttpServletRequest)request).getSession(true).getAttribute(SPRING_SECURITY_CONTEXT_KEY);
            if(sc != null) {
                SecurityContext securityContext = SecurityContextHolder.getContext();
                securityContext.setAuthentication(sc.getAuthentication());
            }
        }
    }
}
