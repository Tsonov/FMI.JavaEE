package bg.uni_sofia.conf_manager.filter;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.primefaces.webapp.MultipartRequest;

/**
 * Custom fileUpload filter in order to handle cyrilic and utf-8 
 * problems with primefaces file upload
 * @author Ivo
 *
 */
public class FileUploadFilter implements Filter {
	
	private final static Logger LOGGER = Logger.getLogger(FileUploadFilter.class.getName());

	private final static String THRESHOLD_SIZE_PARAM = "thresholdSize";
	
	private final static String UPLOAD_DIRECTORY_PARAM = "uploadDirectory";
	
	private String thresholdSize;
	
	private String uploadDir;
	
	public void init(FilterConfig filterConfig) throws ServletException {
		thresholdSize = filterConfig.getInitParameter(THRESHOLD_SIZE_PARAM);
		uploadDir = filterConfig.getInitParameter(UPLOAD_DIRECTORY_PARAM);
		
		if(LOGGER.isLoggable(Level.FINE)) {
			LOGGER.fine("FileUploadFilter initiated successfully");
		}
	}
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		boolean isMultipart = ServletFileUpload.isMultipartContent(httpServletRequest);
		
		if(isMultipart) {
			if(LOGGER.isLoggable(Level.FINE)) {
				LOGGER.fine("Parsing file upload request");
			}
			
			DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
			if(thresholdSize != null) {
				diskFileItemFactory.setSizeThreshold(Integer.valueOf(thresholdSize));
			}
			if(uploadDir != null) {
				diskFileItemFactory.setRepository(new File(uploadDir));
			}
				
			ServletFileUpload servletFileUpload = new ServletFileUpload(diskFileItemFactory);
			MultipartRequest multipartRequest = new MultipartRequest(httpServletRequest, servletFileUpload);
			
			if(LOGGER.isLoggable(Level.FINE)) {
				LOGGER.fine("File upload request parsed succesfully, continuing with filter chain with a wrapped multipart request");
			}
			
			//multipartRequest.setCharacterEncoding("UTF-8");
			
			filterChain.doFilter(multipartRequest, response);
		} else {
			filterChain.doFilter(request, response);
		}
	}

	public void destroy() {
		if(LOGGER.isLoggable(Level.FINE)) {
			LOGGER.fine("Destroying FileUploadFilter");
		}
	}

}

