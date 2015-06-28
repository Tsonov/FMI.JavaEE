package bg.uni_sofia.conf_manager.utils;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

/**
 * 
 * @author Nia
 *
 */
public class MessageUtils {

	public static String getMessage(String aKey) {
		return aKey;
	}

	public static void addMessage(String clientId, FacesMessage msg) {
		FacesContext.getCurrentInstance().addMessage(clientId, msg);
	}

	public static void addFlashMessage(String aMsgId) {
		String message = getMessage(aMsgId);
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		if(flash != null) {
			flash.put("flashMessage", message);
		}
	}
	
	public static void addMessage(String aMsgId) {
		addMessage(null, new FacesMessage(getMessage(aMsgId)));
	}


	public static void addErrorMessage(String aMsgId) {
		addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, getMessage(aMsgId), null));
	}


	/**
	 * Removes HTML special characters from a Text message.
	 * 
	 * @param aTextToClean
	 *            a text to clean
	 * @return the input text without any special characters
	 */
	public static String cleanHTML(String aText) {
		String cleanedText = aText.replace("\"", "");
		cleanedText = cleanedText.replace("'", "");
		cleanedText = cleanedText.replace("`", "");

		return cleanedText;
	}
}
