package bg.uni_sofia.conf_manager.utils;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

public class MessageUtils {

	public static String getMessage(String aKey) {
		return aKey;
	}

	public static void addMessage(String clientId, FacesMessage msg) {
		FacesContext.getCurrentInstance().addMessage(clientId, msg);
	}
	
	public static void addMessage(String aMsgId) {
		addMessage(null, new FacesMessage(getMessage(aMsgId)));
	}


	public static void addErrorMessage(String aMsgId) {
		addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, aMsgId, null));
	}
	
	public static void addSuccessMessage(String aMsgId) {
		addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, aMsgId, null));
	}
}
