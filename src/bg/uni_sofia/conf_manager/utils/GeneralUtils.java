package bg.uni_sofia.conf_manager.utils;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.util.encoders.Hex;

import bg.uni_sofia.conf_manager.entity.UserModel;

public class GeneralUtils {
	
	private static Properties prop;
	private static final Logger LOGGER = Logger.getLogger(GeneralUtils.class.getName());
	

	public static Properties getProp() {
		return prop;
	}

	public static void setProp(Properties prop) {
		GeneralUtils.prop = prop;
	}

	public static UserModel getLoggedUser(Object request) {
		if (request instanceof HttpServletRequest) {
			HttpServletRequest req = (HttpServletRequest) request;
			UserModel loggedUser = (UserModel) req.getSession().getAttribute("_loggedUser");
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
	
	public static byte[] resizeProfilePicture(byte[] imageData, String fileName, Integer newWidth, Integer newHeight) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		String extension = "jpeg";
		if (StringUtils.isNotBlank(fileName)) {
			extension = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
		}

		BufferedImage sourceImage = null;
		try {
			sourceImage = ImageIO.read(new ByteArrayInputStream(imageData));

			Image thumbnail = sourceImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
			BufferedImage bufferedThumbnail = new BufferedImage(thumbnail.getWidth(null), thumbnail.getHeight(null), BufferedImage.TYPE_INT_RGB);

			bufferedThumbnail.getGraphics().drawImage(thumbnail, 0, 0, null);
			ImageIO.write(bufferedThumbnail, extension, baos);
			return baos.toByteArray();

		} catch (IOException e) {
			e.printStackTrace();
			return imageData;
		} finally {
			try {
				baos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
}