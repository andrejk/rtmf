package nl.rotterdam.rtmf.authentication;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * Dit filter zorgt ervoor dat alleen gebruikers met de rol zmweb deze applicatie kunnen gebruiken.
 * @author rweverwijk
 *
 */
public class AuthenticationFilter implements Filter {
//	private FilterConfig filterConfig = null;
	private Logger logger = Logger.getLogger(AuthenticationFilter.class);
	public void destroy() {
//		this.filterConfig = null;		
	}
	
	/**
	 * Stuur de gebruikers met de rol zmweb door naar Wicket.
	 * De andere gebruikers stoppen hier direct.
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain filterChain) throws IOException, ServletException {
		if (((HttpServletRequest)request).isUserInRole("zmweb")) {
			logger.debug("De gebruiker heeft de rol zmweb, dus gaat door naar wicket");
			filterChain.doFilter(request, response);
		} else {
			logger.error("Controleer of IDM werkt. De gebruiker heeft NIET de rol zmweb, dus krijgt een 401.");
			((HttpServletResponse)response).setStatus(401);
			response.getWriter().println(
            "Deze applicatie is alleen te gebruiken met de juiste rechten.");
			return;
		}
		
	}

	public void init(FilterConfig filterConfig) throws ServletException {
//		this.filterConfig = filterConfig;
		
	}

}
