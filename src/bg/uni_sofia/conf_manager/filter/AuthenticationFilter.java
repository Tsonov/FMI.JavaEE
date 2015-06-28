package bg.uni_sofia.conf_manager.filter;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Properties;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;

import bg.uni_sofia.conf_manager.entity.UserModel;

public class AuthenticationFilter implements Filter, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7416819956817532083L;

	public static final String PERMISSIONS_PROPERTIES = "/resources/permissions.properties";

	@SuppressWarnings("unused")
	private FilterConfig mFilterConfig = null;
	private static Properties prop = new Properties();
	
	static {
		InputStream in = AuthenticationFilter.class
				.getResourceAsStream(PERMISSIONS_PROPERTIES);
		try {
			prop.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException ioex) {
				ioex.printStackTrace();
			}
		}
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		mFilterConfig = filterConfig;
	}

	public void destroy() {
		mFilterConfig = null;
	}

	/**
	 * @param request
	 * @param response
	 * @param chain
	 * @throws IOException
	 * @throws ServletException
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest httRequest = (HttpServletRequest) request;

		String requestedPath = httRequest.getRequestURI().substring(
				httRequest.getContextPath().length());

		/*
		 * Skip the action with path "index.jsp" - there is no logged user, but
		 * the user is redirected to login screen
		 */
		if ("/index.jsp".equals(requestedPath)) {
			chain.doFilter(request, response);
			return;
		}
		/*
		 * Skip the action with path "/login.login.html" - there is no logged
		 * user, but the user is trying to log in
		 */
		if ("/page/login.xhtml".equals(requestedPath)) {
			chain.doFilter(request, response);
			return;
		}

		/*
		 * Get the permissions needed to access the requested page form
		 * permissions.properties file
		 */
		String permissions = prop.getProperty(requestedPath);

		if (StringUtils.isBlank(permissions)) {
//			LOGGER.warn("No permissions found for " + requestedPath);
			return;
		}

		if ("all".equals(permissions)) { // allow access for everybody
			chain.doFilter(request, response);
			return;
		}

		/*
		 * Get logged user from the HttpSession
		 */
		HttpSession session = httRequest.getSession();
		UserModel loggedUser = (UserModel) session
				.getAttribute("_loggedUser");

		/*
		 * Redirect to login page if there is no logged user and trying to
		 * access proctected resource
		 */
		if (loggedUser == null) {
			RequestDispatcher requestDispatcher = request
					.getRequestDispatcher("/page/login.xhtml");
			requestDispatcher.forward(request, response);
			return;
		}

		boolean hasUserPermission = loggedUser.hasPermissions(permissions);

		/*
		 * If the user has sufficient permissions then let him access the
		 * requested resource If not redirect to error page
		 */
		if (hasUserPermission) {
			chain.doFilter(request, response);
			return;
		} else {
			RequestDispatcher requestDispatcher = request
					.getRequestDispatcher("/error/error.xhtml");
			requestDispatcher.forward(request, response);
		}
	}

}