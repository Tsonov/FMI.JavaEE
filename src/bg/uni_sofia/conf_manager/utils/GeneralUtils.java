package bg.uni_sofia.conf_manager.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;

import org.bouncycastle.util.encoders.Hex;

import bg.uni_sofia.conf_manager.entity.LecturerModel;

public class GeneralUtils {
	
	private static Properties prop;
	private static final Logger LOGGER = Logger.getLogger(GeneralUtils.class.getName());
	

	public static Properties getProp() {
		return prop;
	}

	public static void setProp(Properties prop) {
		GeneralUtils.prop = prop;
	}

	public static LecturerModel getLoggedUser(Object request) {
		if (request instanceof HttpServletRequest) {
			HttpServletRequest req = (HttpServletRequest) request;
			LecturerModel loggedUser = (LecturerModel) req.getSession().getAttribute("_loggedUser");
			return loggedUser;
		}
		return null;
	}

	public static String encodeSha256Password(String aPlainTextPassword) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(aPlainTextPassword.getBytes("UTF-8"));
            return new String(Hex.encode(hash));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("No encoding algorythm found", e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("No encoding support", e);
        }
    }
	
	/**
	 * load permissions.properties file and populate all permissions
	 * this should be used only for test purposes
	 * 
	 * @return
	 */
/*	public static Set<String> getAllPermissions() {
		Properties prop = new Properties();

		InputStream in = AuthenticationFilter.class.getResourceAsStream(WebConstants.PERMISSIONS_PROPERTIES);
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

		Set<String> permissions = new HashSet<String>();

		for (Object key : prop.keySet()) {
			if (key == null) {
				continue;
			}

			String value = prop.getProperty(key.toString());
			if (StringUtils.isNotBlank(value)) {
				value = value.trim();
				String[] strPermissions = null;

				if (value.indexOf(";") != -1) {
					strPermissions = value.split(";");
				} else if (value.indexOf(",") != -1) {
					strPermissions = value.split(",");
				} else if (value.equals(WebConstants.PERMISSION_ALLOW_ALL)) {
					continue;
				}

				if (strPermissions != null && strPermissions.length > 0) {
					for (int i = 0; i < strPermissions.length; i++) {
						permissions.add(strPermissions[i]);
					}
				} else {
					permissions.add(value);
				}
			}
		}

		return permissions;
	}*/
	
}