/**
 * 
 */
package nl.rotterdam.rtmf.wicket.component;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;

/**
 * @author rweverwijk
 * Deze class kan gebruikt worden als je een &nbsp wilt hebben als het label leeg is.
 * Ik wilde dit bijvoorbeeld omdat een lijst met float:left elementen verkeerd uitgelijnd werden.
 */
public class FillEmptyLabel extends Label{
	/**
	 * @param id
	 * @param label
	 */
	public FillEmptyLabel(String id, String label) {
		super(id, label);
		
		if (StringUtils.isBlank(label)){
			this.setDefaultModel(new Model<String>("&nbsp;"));	
			this.setEscapeModelStrings(false);
		}
	}

	private static final long serialVersionUID = -8050586822059052174L;

}
