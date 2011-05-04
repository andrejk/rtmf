package com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.impl.mock;

import com.ovsoftware.ictu.osb.tmfportal.service.common.InvokerException;
import com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.IntrekInvoker;

/**
 * MockIntrekInvokerImpl kan worden gebruikt om een eerder gedane Terugmelding
 * in te trekken.
 * 
 * @author ktinselboer
 *
 */
public class MockIntrekInvokerImpl implements IntrekInvoker {

	/**
	 * Trekt melding in adhv argumenten. Intrekking slaagt alleen indien de melding
	 * gevonden kan worden en deze de status 'gemeld' of 'in onderzoek' heeft.
	 * LET OP: tijdstempelAanlevering en meldingKenmerk worden niet gebruikt omdat
	 * betreftTmfKenmerk de terugmelding al uniek identificeert.
	 * 
	 * @param betreftTmfKenmerk Het kenmerk van de terugmelding als bepaald door TMFCore
	 * @param toelichting Een korte toelichting mbt de intrekking
	 * @throws InvokerException Indien de melding niet gevonden kan worden
	 */
	@Override
	public void intrekking(String betreftTmfKenmerk, String toelichting) throws InvokerException {
		//trek de melding in
		MockOpvraagInvokerImpl.trekinMelding(betreftTmfKenmerk, toelichting);
	}
}
