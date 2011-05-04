/**
 * 
 */
package nl.rotterdam.rtmf.wicket;

import nl.rotterdam.rtmf.form.helper.SelectOption;

import org.apache.wicket.markup.html.form.IChoiceRenderer;

/**
 * @author rweverwijk
 *
 */
public class StatusChoiceRenderer<T> implements IChoiceRenderer<T> {

	private static final long serialVersionUID = -3532188616218127489L;

	/* (non-Javadoc)
	 * @see org.apache.wicket.markup.html.form.IChoiceRenderer#getDisplayValue(java.lang.Object)
	 */
	public Object getDisplayValue(T object) {
		if (object instanceof SelectOption)
		return ((SelectOption)object).getValue();
		return object;
	}

	/* (non-Javadoc)
	 * @see org.apache.wicket.markup.html.form.IChoiceRenderer#getIdValue(java.lang.Object, int)
	 */
	public String getIdValue(T object, int index) {
		if (object instanceof SelectOption)
		return ((SelectOption)object).getKey();
		return object != null ? object.toString() : null; 
	}
}
