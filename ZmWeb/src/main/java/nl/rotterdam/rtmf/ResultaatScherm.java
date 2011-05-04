package nl.rotterdam.rtmf;

import java.util.List;

import nl.interaccess.zakenmagazijn.model.Zaak;
import nl.rotterdam.rtmf.form.helper.ZaakReadHelper;
import nl.rotterdam.rtmf.service.IZaakService;

import org.apache.log4j.Logger;
import org.apache.wicket.PageParameters;
import org.apache.wicket.RestartResponseException;
import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * Homepage
 */
public class ResultaatScherm extends BasePage {
	@SpringBean
	private IZaakService zaakService;
	private static Logger logger = Logger.getLogger(ResultaatScherm.class);
	/**
	 * Constructor that is invoked when page is invoked without a session.
	 * 
	 * @param parameters
	 *            Page parameters
	 */
	public ResultaatScherm(final PageParameters parameters) {
		logger.debug("client is null: " + zaakService == null);
		logger.debug("In resultaatScherm aantal parameters gevonden: " + parameters.size());
		for (String key :parameters.keySet()) {
			logger.debug("Resultaat param key: " + key);
		}
		
		List<Zaak> zaken = zaakService.getZaken(parameters);
		logger.debug("Er zijn zaken gevonden: " + zaken.size());
		if (zaken.size() == 1) {
			logger.debug("Er is maar 1 zaak gevonden, dus we gaan direct naar de detail pagina");
			setRedirect(true);
			PageParameters detailParameter = new PageParameters();
			detailParameter.add("zaakId", zaken.get(0).getZaakidentificatie());
			throw new RestartResponseException(DetailScherm.class, detailParameter);
		}
		final DataView<Zaak> dataView = new DataView<Zaak>("zaakRegel",
				new ListDataProvider<Zaak>(zaken)) {
			private static final long serialVersionUID = 6364718764586838158L;

			@Override
			public void populateItem(final Item<Zaak> item) {
				logger.debug("Index van dit item: " + item.getIndex());
				item.add(new SimpleAttributeModifier("class", item.getIndex() % 2 == 0 ? "even" : "oneven"));
				PageParameters params = new PageParameters();
				params.add("zaakId", ZaakReadHelper.getZaaknummer(item.getModelObject()));
				// datum
				BookmarkablePageLink<Zaak> datumLink = new BookmarkablePageLink<Zaak>("datum", DetailScherm.class, params);
				datumLink.add(new Label("datumSpan", ZaakReadHelper.getDatumIngediend(item.getModelObject())));
				item.add(datumLink);
				// meldingskenmerk
				BookmarkablePageLink<Zaak> meldingskenmerkLink = new BookmarkablePageLink<Zaak>("meldingskenmerk", DetailScherm.class, params);
				meldingskenmerkLink.add(new Label("meldingskenmerkSpan", ZaakReadHelper.getMeldingskenmerk(item.getModelObject())));
				item.add(meldingskenmerkLink);

				// zaaknummer
				BookmarkablePageLink<Zaak> zaaknummerLink = new BookmarkablePageLink<Zaak>("zaaknummer", DetailScherm.class, params);
				zaaknummerLink.add(new Label("zaaknummerSpan", ZaakReadHelper.getZaaknummer(item.getModelObject())));
				item.add(zaaknummerLink);

				// basisRegistratie
				BookmarkablePageLink<Zaak> basisRegistratieLink = new BookmarkablePageLink<Zaak>("basisRegistratie", DetailScherm.class, params);
				basisRegistratieLink.add(new Label("basisRegistratieSpan", ZaakReadHelper.getBasisRegistratie(item.getModelObject())));
				item.add(basisRegistratieLink);

				// object
				BookmarkablePageLink<Zaak> objectLink = new BookmarkablePageLink<Zaak>("object", DetailScherm.class, params);
				objectLink.add(new Label("objectSpan", ZaakReadHelper.getObjectNaam(item.getModelObject())));
				item.add(objectLink);
				
				// objectidentificatie
				BookmarkablePageLink<Zaak> objectidentificatieLink = new BookmarkablePageLink<Zaak>("objectidentificatie", DetailScherm.class, params);
				objectidentificatieLink.add(new Label("objectidentificatieSpan", ZaakReadHelper.getObjectIdentificatie(item.getModelObject())));
				item.add(objectidentificatieLink);

				// status
				BookmarkablePageLink<Zaak> statusLink = new BookmarkablePageLink<Zaak>("status", DetailScherm.class, params);
				statusLink.add(new Label("statusSpan", ZaakReadHelper.getStatus(item.getModelObject())));
				item.add(statusLink);
				logger.debug("item: " + item.getIndex() + " toegevoegd aan tabel");
			}
		};
		add(dataView);
		logger.debug("tabel toegevoegd aan pagina");
	}
	
}
