package com.ovsoftware.ictu.osb.tmfportal.web.intrekken;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Deze validator behoort tot het intrekformulier.
 * De input wordt vergeleken met de eisen waaraan deze moet voldoen
 * 
 * @author OVSoftware
 *
 */
public class IntrekValidator implements Validator {

	/**
	 * Retourneert of validatie van een bepaalde klasse ondersteund wordt.
	 * Dit is echter alleen het geval bij IntrekFormData.
	 * 
	 * @param clazz De klasse waarvan de ondersteuning gecontroleerd moet worden
	 * 
	 * @return True indien van type IntrekFormData, anders false
	 */
	@SuppressWarnings("unchecked")
	public boolean supports(Class clazz) {
		return clazz.equals(IntrekFormdata.class);
	}

	/**
	 * Valideert het command-object, wat eigenlijk van het type IntrekFormData is.
	 * 
	 * @param command Een IntrekFormData-object
	 * @param errors Een lijst om foutmeldingen aan toe te voegen
	 */
	public void validate(Object command, Errors errors) {

		if (command != null) {
			IntrekFormdata data = (IntrekFormdata) command;

			if (data.getReason().equals("")) {
				errors.rejectValue("reason", "invalid.reason",
						"Toelichting is een verplicht veld.");
			} else if(data.getReason().length() > 255) {
				errors.rejectValue("reason", "invalid.reason",
						"Toelichting mag maximaal 255 karakters bevatten.");
			}
		}
	}
}
