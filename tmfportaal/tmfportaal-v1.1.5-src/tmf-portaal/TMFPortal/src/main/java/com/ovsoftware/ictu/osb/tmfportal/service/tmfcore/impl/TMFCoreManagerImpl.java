package com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.impl;

import com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.IntrekInvoker;
import com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.OpvraagInvoker;
import com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.TMFCoreManager;
import com.ovsoftware.ictu.osb.tmfportal.service.tmfcore.TerugmeldInvoker;

/**
 *  Basis implementatie van de TMFCoreManager interface. 
 *  
 *  De volgende invokers kunnen benaderd worden dmv getters en setters:
 *  - intrekInvoker, voor het intrekken van een melding.
 *  - opvraagInvoker, voor het opvragen van een lijst van meldingen of details van een enkele melding.
 *  - terugmeldInvoker, voor het invoeren van een nieuwe terugmelding.
 * 
 * @author OVSoftware
 * @version 0.1 (NOV/DEC 2008)
 */
public class TMFCoreManagerImpl implements TMFCoreManager {
		private IntrekInvoker intrekInvoker;
		private OpvraagInvoker opvraagInvoker;
		private TerugmeldInvoker terugmeldInvoker;
		
		/**
		 * Getter voor intrekInvoker.
		 * 
		 * @return intrekInvoker
		 */
		public IntrekInvoker getIntrekInvoker() {
			return intrekInvoker;
		}
				
		/**
		 * Setter voor intrekInvoker.
		 * 
		 * @param intrekInvoker De nieuwe waarde voor intrekInvoker
		 */
		public void setIntrekInvoker(IntrekInvoker intrekInvoker) {
			this.intrekInvoker = intrekInvoker;
		}
		
		/**
		 * Getter voor opvraagInvoker.
		 * 
		 * @return opvraagInvoker
		 */
		public OpvraagInvoker getOpvraagInvoker() {
			return opvraagInvoker;
		}		
		
		/**
		 * Setter voor opvraagInvoker.
		 * 
		 * @param opvraagInvoker De nieuwe waarde voor opvraagInvoker
		 */
		public void setOpvraagInvoker(OpvraagInvoker opvraagInvoker) {
			this.opvraagInvoker = opvraagInvoker;
		}		
		
		/**
		 * Getter voor terugmeldInvoker.
		 * 
		 * @return terugmeldInvoker
		 */
		public TerugmeldInvoker getTerugmeldInvoker() {
			return terugmeldInvoker;
		}		
		
		/**
		 * Setter voor terugmeldInvoker.
		 * 
		 * @param terugmeldInvoker De nieuwe waarde voor terugmeldInvoker
		 */
		public void setTerugmeldInvoker(TerugmeldInvoker terugmeldInvoker) {
			this.terugmeldInvoker = terugmeldInvoker;
		}	
}
