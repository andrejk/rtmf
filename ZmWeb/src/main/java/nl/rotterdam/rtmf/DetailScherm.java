package nl.rotterdam.rtmf;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import nl.interaccess.zakenmagazijn.model.ZaakDetail;
import nl.rotterdam.rtmf.exception.ZMWebException;
import nl.rotterdam.rtmf.exception.ZMWebMailException;
import nl.rotterdam.rtmf.form.helper.SelectOption;
import nl.rotterdam.rtmf.form.helper.ZaakDetailReadHelper;
import nl.rotterdam.rtmf.form.pojo.Attribuut;
import nl.rotterdam.rtmf.form.pojo.BijwerkenStatusForm;
import nl.rotterdam.rtmf.service.IZaakService;
import nl.rotterdam.rtmf.wicket.StatusChoiceRenderer;
import nl.rotterdam.rtmf.wicket.component.FillEmptyLabel;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.wicket.PageParameters;
import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * Homepage
 */
public class DetailScherm extends BasePage {
	private static final String PROPERTY_PREFIX = "zakenmagazijn.status.opties.";
	private static final String PROPERTY_SPLIT = ";";

	
	
	@SpringBean
	private Properties properties;
	@SpringBean
	private IZaakService zaakService;
	private BijwerkenStatusForm bijwerkenStatusForm = new BijwerkenStatusForm();
	private ZaakDetail zaak;
	private static Logger logger = Logger.getLogger(DetailScherm.class); 
    /**
	 * Constructor that is invoked when page is invoked without a session.
	 * 
	 * @param parameters
	 *            Page parameters
	 */
	public DetailScherm(final PageParameters parameters) {
    	if (	parameters.get("zaakId") instanceof String[] && 
    			StringUtils.isNotBlank(((String[])parameters.get("zaakId"))[0])) {
    		String zaakId = ((String[]) parameters.get("zaakId"))[0];
			zaak = zaakService.getZaak(zaakId);			
			// ophalen contact gegevens
    		add(new FillEmptyLabel("contactNaam", ZaakDetailReadHelper.getContactNaam(zaak)));
    		add(new FillEmptyLabel("contactTelefoon", ZaakDetailReadHelper.getContactTelefoon(zaak)));
    		add(new FillEmptyLabel("contactEmail", ZaakDetailReadHelper.getContactEmail(zaak)));
    		// terugmelding gegevens
    		add(new FillEmptyLabel("meldingskenmerk", ZaakDetailReadHelper.getMeldingskenmerk(zaak)));
    		add(new FillEmptyLabel("zaaknummer", ZaakDetailReadHelper.getZaaknummer(zaak)));
    		add(new FillEmptyLabel("toelichting", ZaakDetailReadHelper.getToelichting(zaak)));
    		add(new FillEmptyLabel("datumAanlevering", ZaakDetailReadHelper.getDatumIngediend(zaak)));
    		// huidige status
    		add(new FillEmptyLabel("status", ZaakDetailReadHelper.getStatus(zaak)));
    		add(new FillEmptyLabel("statusToelichting", ZaakDetailReadHelper.getStatusToelichting(zaak)).setEscapeModelStrings(false));
    		add(new FillEmptyLabel("laatsteMomentWijziging", ZaakDetailReadHelper.getDatumLaatsteStap(zaak)));
    		// kern registratie
    		add(new FillEmptyLabel("kernregistratie", ZaakDetailReadHelper.getBasisRegistratie(zaak)));
    		add(new FillEmptyLabel("object", ZaakDetailReadHelper.getObjectNaam(zaak)));
    		add(new FillEmptyLabel("objectIdentificatie", ZaakDetailReadHelper.getObjectIdentificatie(zaak)));
    		// attributen
    		final DataView<Attribuut> dataView = new DataView<Attribuut>("attribuutRegel",
    				new ListDataProvider<Attribuut>(ZaakDetailReadHelper.getAttributen(zaak))) {
    			private static final long serialVersionUID = 6364718764586838158L;

    			@Override
    			public void populateItem(final Item<Attribuut> item) {
    				item.add(new SimpleAttributeModifier("class", item.getIndex() % 2 == 0 ? "even" : "oneven"));
    				item.add(new FillEmptyLabel("id", item.getModelObject().getId()));
    				item.add(new FillEmptyLabel("betwijfeldeWaarde", item.getModelObject().getBetwijfeldeWaarde()));
    				item.add(new FillEmptyLabel("voorstel", item.getModelObject().getVoorstel()));
    				    			}
    		};
    		add(dataView);
    		
    		Form<BijwerkenStatusForm> form = new Form<BijwerkenStatusForm>("bijwerkenForm") {
    			private static final long serialVersionUID = -8268205324274178766L;

    			@Override
    			protected String getMethod()
    			{
    				return METHOD_GET;
    			}
    			
    			@Override
    			protected void onSubmit()
    			{
//    				setRedirect(false);
    				PageParameters params = new PageParameters();
    				params.add("zaakId", zaak.getZaakidentificatie());
    				try {
						zaakService.statusBijwerken(zaak, bijwerkenStatusForm.getStatusTerugmelding().getKey(), bijwerkenStatusForm.getToelichting());
						getSession().info("De status is succesvol bijgewerkt");
						setResponsePage(DetailScherm.class, params);
					} catch (ZMWebMailException e) {
						logger.error(e);
						getSession().error("Er is een fout opgetreden bij het versturen van de email naar de terugmelder.");
					} catch (ZMWebException e) {
						getSession().error("Er is een fout opgetreden bij het wijzigen van de status. Neem contact op met de applicatiebeheerder.");
					}
    			}
    		};
    		add(form);
    		add(new FeedbackPanel("feedback"));
    		
    		DropDownChoice<SelectOption> selectStatus = new DropDownChoice<SelectOption>("statusTerugmelding");
    		selectStatus.setRequired(true);
			form.add(selectStatus);
    		TextArea<String> toelichting = new TextArea<String>("toelichting", new PropertyModel<String>(bijwerkenStatusForm, "toelichting"));
    		toelichting.setRequired(true);
			form.add(toelichting);
    		
    		// buttons
    		Button submitButton = new Button("submitButton");
			form.add(submitButton);
			WebMarkupContainer vorigeButton = new Button("vorigeButton");
			form.add(vorigeButton);
    		
    		// labels
    		FillEmptyLabel statusLabel = new FillEmptyLabel("statusLabel", "Status terugmelding *");
			form.add(statusLabel);
    		FillEmptyLabel toelichtingLabel = new FillEmptyLabel("toelichtingLabel", "Status toelichting *");
			form.add(toelichtingLabel);
			FillEmptyLabel nieuweStatusHeader = new FillEmptyLabel("nieuweStatusHeader", "Invoer nieuwe status");
    		form.add(nieuweStatusHeader);
    		if (editable(zaak)) {
	    		// statussen
    			List<SelectOption> determenSelectOptions = determenSelectOptions(zaak.getResultaatcode());
    			selectStatus.setModel(new PropertyModel<SelectOption>(
        				bijwerkenStatusForm, "statusTerugmelding"));
    			selectStatus.setChoices(determenSelectOptions);
    			selectStatus.setChoiceRenderer(new StatusChoiceRenderer<SelectOption>());
    		} else {
				nieuweStatusHeader.setVisible(false);
    			selectStatus.setVisible(false);
    			toelichting.setVisible(false);
    			statusLabel.setVisible(false);
    			toelichtingLabel.setVisible(false);
    			submitButton.setVisible(false);
    			
    			if (pageHasSuccesMessage()) {
        			vorigeButton = new SubmitLink("vorigeButton", new Model<String>("Terug naar zoeken")) {
    					private static final long serialVersionUID = -5908285363652905468L;

    					/* (non-Javadoc)
        				 * @see org.apache.wicket.markup.html.form.Button#onSubmit()
        				 */
        				@Override
        				public void onSubmit() {
        					setResponsePage(HomePage.class);
        				}
        			};
        			((SubmitLink)vorigeButton).setDefaultFormProcessing(false);
        			form.replace(vorigeButton);
        		}
    		}
    	}
    	
    }
    
	/**
	 * @param zaak2
	 * @return
	 */
	private boolean editable(ZaakDetail zaakDetail) {
		String resultaatcode = zaakDetail.getResultaatcode();
		return (resultaatcode.equals("gemeld") || resultaatcode.equals("ingetr") || resultaatcode.equals("onderzoek")) && ! pageHasSuccesMessage();
	}

	/**
	 * @return
	 */
	private boolean pageHasSuccesMessage() {
		return !getSession().getFeedbackMessages().messages(new ZMWebErrorLevelFeedbackMessageFilter(FeedbackMessage.INFO)).isEmpty();
	}

	private List<SelectOption> determenSelectOptions(String resultaatCode) {
		List<SelectOption> result = new ArrayList<SelectOption>();
		String statusOpties = null;
		if (resultaatCode != null) {
			statusOpties = (String) properties.get(PROPERTY_PREFIX + resultaatCode);
		}
		if (statusOpties == null) {
			return null;
		} 
		String[] opties = statusOpties.split(PROPERTY_SPLIT);
		for (String optie : opties) {
			int indexOf = SelectOption.statusList.indexOf(new SelectOption(optie, null));
			if (indexOf != -1) {
				result.add(SelectOption.statusList.get(indexOf));
			}
		}
		return result;
	}
}
