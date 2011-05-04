package nl.rotterdam.rtmf;

import java.util.Arrays;
import java.util.List;

import nl.rotterdam.rtmf.form.helper.SelectOption;
import nl.rotterdam.rtmf.form.pojo.ZoekForm;
import nl.rotterdam.rtmf.wicket.StatusChoiceRenderer;

import org.apache.log4j.Logger;
import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.PropertyModel;



/**
 * Homepage
 */
public class HomePage extends BasePage {
	ZoekForm zaakForm = new ZoekForm();
	private static Logger logger = Logger.getLogger(HomePage.class);
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor that is invoked when page is invoked without a session.
	 * 
	 * @param parameters
	 *            Page parameters
	 */
	public HomePage(final PageParameters parameters) {

		Form<ZoekForm> form = new Form<ZoekForm>("zoekForm") {
			private static final long serialVersionUID = -8268205324274178766L;

			@Override
			protected String getMethod()
			{
				return METHOD_GET;
			}
			
			@Override
			protected void onSubmit()
			{
				setRedirect(false);
				logger.debug("De zoekvelden bevatten nu: " + zaakForm);
				PageParameters params = new PageParameters();
				params.add("meldingskenmerk", zaakForm.getMeldingskenmerk());
				params.add("zaaknummer", zaakForm.getZaaknummer());
				params.add("objectIdentificatie", zaakForm.getObjectIdentificatie());
				params.add("vanafDatum", String.format("%s-%s-%s", zaakForm.getVanafDatumDag(), zaakForm.getVanafDatumMaand(), zaakForm.getVanafDatumJaar()));
				params.add("totDatum", String.format("%s-%s-%s", zaakForm.getTotDatumDag(), zaakForm.getTotDatumMaand(), zaakForm.getTotDatumJaar()));
				params.add("statusTerugmelding", zaakForm.getStatusTerugmelding() != null ? zaakForm.getStatusTerugmelding().getKey() : null);
				setResponsePage(ResultaatScherm.class, params);
			}
		};
		add(form);
		form.add(new TextField<String>("meldingskenmerk",
				new PropertyModel<String>(zaakForm, "meldingskenmerk")));
		form.add(new TextField<String>("zaaknummer", new PropertyModel<String>(
				zaakForm, "zaaknummer")));
		form.add(new TextField<String>("objectIdentificatie", new PropertyModel<String>(
				zaakForm, "objectIdentificatie")));
		form.add(new TextField<String>("vanafDatumDag", new PropertyModel<String>(
				zaakForm, "vanafDatumDag")));
		form.add(new TextField<String>("vanafDatumMaand", new PropertyModel<String>(
				zaakForm, "vanafDatumMaand")));
		form.add(new TextField<String>("vanafDatumJaar", new PropertyModel<String>(
				zaakForm, "vanafDatumJaar")));
		form.add(new TextField<String>("totDatumDag", new PropertyModel<String>(
				zaakForm, "totDatumDag")));
		form.add(new TextField<String>("totDatumMaand", new PropertyModel<String>(
				zaakForm, "totDatumMaand")));
		form.add(new TextField<String>("totDatumJaar", new PropertyModel<String>(
				zaakForm, "totDatumJaar")));
		form.add(new DropDownChoice<SelectOption>("statusTerugmelding", new PropertyModel<SelectOption>(
				zaakForm, "statusTerugmelding"),SelectOption.statusList , new StatusChoiceRenderer()));

		form.add(new Button("submit"));
	}
}
