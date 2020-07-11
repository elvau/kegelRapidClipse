
package com.company.kegelapp.ui;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import com.company.kegelapp.dal.SpielerDAO;
import com.company.kegelapp.dal.SpieltagDAO;
import com.company.kegelapp.domain.Spieler;
import com.company.kegelapp.domain.Spieltag;
import com.company.kegelapp.domain.Strafe;
import com.flowingcode.vaadin.addons.ironicons.SocialIcons;
import com.rapidclipse.framework.server.resources.CaptionUtils;
import com.rapidclipse.framework.server.ui.ItemLabelGeneratorFactory;
import com.vaadin.flow.component.AbstractField.ComponentValueChangeEvent;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.ItemClickEvent;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.listbox.MultiSelectListBox;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.TextRenderer;
import com.vaadin.flow.router.Route;


@Route("view4")
@HtmlImport("frontend://styles/shared-styles.html")
public class SpieltagView extends VerticalLayout
{

	Set<Spieler>                 ausgewaehlteSpieler = null;
	Set<Strafe>                  ausgewaehlteStrafe  = null;
	Spieltag                     spieltag            = null;
	private final static Logger  LOGGER              = Logger.getLogger(SpieltagView.class.getName());
	private static DecimalFormat df2                 = new DecimalFormat("#.###");
	
	public SpieltagView()
	{
		super();
		this.initUI();
		System.out.println(SpieltagDAO.findCurrent(LocalDate.now()));
		this.spieltag = SpieltagDAO.findCurrent(LocalDate.now());
		this.multiSelectListBox2.setItems(this.spieltag.getSpieltagMap().keySet());
		this.multiSelectListBox
			.setItems(
				this.spieltag.getSpieltagMap().get(this.spieltag.getSpieltagMap().keySet().iterator().next()).keySet());
		this.createStatistik();
	}
	
	private void createStatistik()
	{
		this.verticalLayout2.removeAll();
		for(final Spieler spieler : this.spieltag.getSpieltagMap().keySet())
		{
			Double gesamtbetrag = 0d;
			String toConcat     = spieler.getName() + " " + spieler.getNachName() + " || ";
			for(final Strafe strafe : this.spieltag.getSpieltagMap().get(spieler).keySet())
			{
				toConcat =
					toConcat.concat(
						strafe.getName() + " : " + this.spieltag.getSpieltagMap().get(spieler).get(strafe) + " || ");
				final Double betrag = this.spieltag.getSpieltagMap().get(spieler).get(strafe) * strafe.getBetrag();
				gesamtbetrag =
					gesamtbetrag + betrag;
			}
			toConcat = toConcat.concat("GESAMT : " + SpieltagView.df2.format(gesamtbetrag) + "€");
			
			final Label textField = new Label();
			textField.setText(toConcat);
			this.verticalLayout2.add(textField);
		}

	}

	/**
	 * Event handler delegate method for the {@link Button} {@link #buttonPlusOne}.
	 *
	 * @see ComponentEventListener#onComponentEvent(ComponentEvent)
	 * @eventHandlerDelegate Do NOT delete, used by UI designer!
	 */
	private void buttonPlusOne_onClick(final ClickEvent<Button> event)
	{
		for(final Spieler spieler : this.ausgewaehlteSpieler)
		{
			for(final Strafe strafe : this.ausgewaehlteStrafe)
			{
				final Integer anzahlStrafen =
					this.spieltag.getSpieltagMap().get(spieler).get(strafe);

				this.spieltag.getSpieltagMap().get(spieler).put(strafe, anzahlStrafen + 1);
				SpieltagView.LOGGER.info("HIER EIN LOG");
				SpieltagView.LOGGER
					.info(new Date() + " Spieler : <" + spieler.getName() + "> wurde Strafe : <" + strafe.getName()
						+ ">1* hinzugefügt. \n Aktuelle Anzahl beträgt : <" + anzahlStrafen);
			}
		}
		SpieltagDAO.insert(this.spieltag);
		this.createStatistik();
	}

	/**
	 * Event handler delegate method for the {@link MultiSelectListBox} {@link #multiSelectListBox}.
	 *
	 * @see HasValue.ValueChangeListener#valueChanged(HasValue.ValueChangeEvent)
	 * @eventHandlerDelegate Do NOT delete, used by UI designer!
	 */
	private void
		multiSelectListBox_valueChanged(final ComponentValueChangeEvent<MultiSelectListBox<Strafe>, Set<Strafe>> event)
	{
		if(!event.getValue().isEmpty())
		{
			this.verticalLayout.setVisible(true);
		}
		else
		{
			this.verticalLayout.setVisible(false);
		}
		this.ausgewaehlteStrafe = event.getValue();
	}

	/**
	 * Event handler delegate method for the {@link MultiSelectListBox} {@link #multiSelectListBox2}.
	 *
	 * @see HasValue.ValueChangeListener#valueChanged(HasValue.ValueChangeEvent)
	 * @eventHandlerDelegate Do NOT delete, used by UI designer!
	 */
	private void
		multiSelectListBox2_valueChanged(
			final ComponentValueChangeEvent<MultiSelectListBox<Spieler>, Set<Spieler>> event)
	{
		if(!event.getValue().isEmpty())
		{
			this.multiSelectListBox.setVisible(true);
		}
		else
		{
			this.multiSelectListBox.setVisible(false);
			this.multiSelectListBox.clear();
			this.verticalLayout.setVisible(false);
		}

		this.ausgewaehlteSpieler = event.getValue();
	}

	/**
	 * Event handler delegate method for the {@link Button} {@link #buttonPlusFive}.
	 *
	 * @see ComponentEventListener#onComponentEvent(ComponentEvent)
	 * @eventHandlerDelegate Do NOT delete, used by UI designer!
	 */
	private void buttonPlusFive_onClick(final ClickEvent<Button> event)
	{
		SpieltagView.LOGGER
			.info("+5 für : " + this.ausgewaehlteSpieler + " mit folgenden Strafen " + this.ausgewaehlteStrafe);
		for(final Spieler spieler : this.ausgewaehlteSpieler)
		{
			for(final Strafe strafe : this.ausgewaehlteStrafe)
			{
				final Integer anzahlStrafen =
					this.spieltag.getSpieltagMap().get(spieler).get(strafe);

				this.spieltag.getSpieltagMap().get(spieler).put(strafe, anzahlStrafen + 5);
				SpieltagView.LOGGER
					.info(new Date() + " Spieler : <" + spieler.getName() + "> wurde Strafe : <" + strafe.getName()
						+ ">5* hinzugefügt. \n Aktuelle Anzahl beträgt : <" + anzahlStrafen);
			}
		}
		SpieltagDAO.insert(this.spieltag);
		this.createStatistik();
	}
	
	/**
	 * Event handler delegate method for the {@link Button} {@link #button}.
	 *
	 * @see ComponentEventListener#onComponentEvent(ComponentEvent)
	 * @eventHandlerDelegate Do NOT delete, used by UI designer!
	 */
	private void button_onClick(final ClickEvent<Button> event)
	{
		UI.getCurrent().navigate(TeamSpielErstellen.class);
	}

	/**
	 * Event handler delegate method for the {@link Button} {@link #button2}.
	 *
	 * @see ComponentEventListener#onComponentEvent(ComponentEvent)
	 * @eventHandlerDelegate Do NOT delete, used by UI designer!
	 */
	private void button2_onClick(final ClickEvent<Button> event)
	{
		this.verticalLayout3.setVisible(true);
		final List<Spieler>       spielerListe      = new ArrayList<>();
		final Collection<Spieler> spielerCollection = SpielerDAO.findAll();
		for(final Spieler spieler : spielerCollection)
		{
			if(!this.spieltag.getSpieltagMap().containsKey(spieler))
			{
				spielerListe.add(spieler);
			}
		}
		this.grid.setItems(spielerListe);
	}

	/**
	 * Event handler delegate method for the {@link Grid} {@link #grid}.
	 *
	 * @see ComponentEventListener#onComponentEvent(ComponentEvent)
	 * @eventHandlerDelegate Do NOT delete, used by UI designer!
	 */
	private void grid_onItemClick(final ItemClickEvent<Spieler> event)
	{
		if(!this.grid.getSelectedItems().isEmpty())
		{
			this.button4.setVisible(true);
		}
		else
		{
			this.button4.setVisible(false);
		}
	}
	
	/**
	 * Event handler delegate method for the {@link Button} {@link #button4}.
	 *
	 * @see ComponentEventListener#onComponentEvent(ComponentEvent)
	 * @eventHandlerDelegate Do NOT delete, used by UI designer!
	 */
	private void button4_onClick(final ClickEvent<Button> event)
	{
		final Set<Spieler>         spielerZumHinzufuegen = this.grid.getSelectedItems();
		final Map<Strafe, Integer> strafenMap            = new HashMap<>();
		for(final Strafe strafe : this.spieltag.getSpieltagMap()
			.get(this.spieltag.getSpieltagMap().keySet().iterator().next()).keySet())
		{
			strafenMap.put(strafe, 0);
		}
		for(final Spieler spieler : spielerZumHinzufuegen)
		{
			this.spieltag.getSpieltagMap().put(spieler, strafenMap);
		}
		this.verticalLayout3.setVisible(false);
		this.multiSelectListBox2.setItems(this.spieltag.getSpieltagMap().keySet());
		if(this.multiSelectListBox2.isEmpty())
		{
			this.verticalLayout3.setVisible(false);
		}
		this.createStatistik();
		
	}
	
	/**
	 * Event handler delegate method for the {@link Button} {@link #buttonMinusOne}.
	 *
	 * @see ComponentEventListener#onComponentEvent(ComponentEvent)
	 * @eventHandlerDelegate Do NOT delete, used by UI designer!
	 */
	private void buttonMinusOne_onClick(final ClickEvent<Button> event)
	{
		for(final Spieler spieler : this.ausgewaehlteSpieler)
		{
			for(final Strafe strafe : this.ausgewaehlteStrafe)
			{
				final Integer anzahlStrafen =
					this.spieltag.getSpieltagMap().get(spieler).get(strafe);
				
				this.spieltag.getSpieltagMap().get(spieler).put(strafe, anzahlStrafen - 1);
				SpieltagView.LOGGER
					.info(new Date() + " Spieler : <" + spieler.getName() + "> wurde Strafe : <" + strafe.getName()
						+ "> 1* abgezogen. \n Aktuelle Anzahl beträgt : <" + anzahlStrafen);
			}
		}
		SpieltagDAO.insert(this.spieltag);
		this.createStatistik();
	}

	/**
	 * Event handler delegate method for the {@link Button} {@link #buttonMinusFive}.
	 *
	 * @see ComponentEventListener#onComponentEvent(ComponentEvent)
	 * @eventHandlerDelegate Do NOT delete, used by UI designer!
	 */
	private void buttonMinusFive_onClick(final ClickEvent<Button> event)
	{
		for(final Spieler spieler : this.ausgewaehlteSpieler)
		{
			for(final Strafe strafe : this.ausgewaehlteStrafe)
			{
				final Integer anzahlStrafen =
					this.spieltag.getSpieltagMap().get(spieler).get(strafe);
				
				this.spieltag.getSpieltagMap().get(spieler).put(strafe, anzahlStrafen - 5);
				SpieltagView.LOGGER
					.info(new Date() + " Spieler : <" + spieler.getName() + "> wurde Strafe : <" + strafe.getName()
						+ "> 5* abgezogen. \n Aktuelle Anzahl beträgt : <" + anzahlStrafen);
			}
		}
		SpieltagDAO.insert(this.spieltag);
		this.createStatistik();
	}

	/**
	 * Event handler delegate method for the {@link Button} {@link #button3}.
	 *
	 * @see ComponentEventListener#onComponentEvent(ComponentEvent)
	 * @eventHandlerDelegate Do NOT delete, used by UI designer!
	 */
	private void button3_onClick(final ClickEvent<Button> event)
	{
		Spieltag spieltag = SpieltagDAO.findCurrent(LocalDate.now());
		if(spieltag == null)
		{
			spieltag = SpieltagDAO.findCurrent(LocalDate.now().minusDays(1l));
		}
		SpieltagDAO.delete(this.spieltag);
		SpieltagDAO.insert(spieltag);
		
		final Label   textField     = new Label();
		Double        gesamtStrafen = 0d;
		final Integer anzahlSpieler = this.spieltag.getSpieltagMap().keySet().size();
		for(final Spieler spieler : this.spieltag.getSpieltagMap().keySet())
		{
			for(final Strafe strafe : this.spieltag.getSpieltagMap().get(spieler).keySet())
			{
				gesamtStrafen =
					gesamtStrafen + (strafe.getBetrag() * this.spieltag.getSpieltagMap().get(spieler).get(strafe));
			}
		}
		System.out.println(gesamtStrafen);
		textField.setText("DURCHSCHNITTSTRAFE : " + gesamtStrafen / anzahlSpieler + "€");
		this.verticalLayout2.add(textField);
	}

	/* WARNING: Do NOT edit!<br>The content of this method is always regenerated by the UI designer. */
	// <generated-code name="initUI">
	private void initUI()
	{
		this.lblContent          = new Label();
		this.horizontalLayout    = new HorizontalLayout();
		this.button              = new Button();
		this.button2             = new Button();
		this.button3             = new Button();
		this.horizontalLayout2   = new HorizontalLayout();
		this.multiSelectListBox2 = new MultiSelectListBox<>();
		this.multiSelectListBox  = new MultiSelectListBox<>();
		this.verticalLayout      = new VerticalLayout();
		this.buttonPlusOne       = new Button();
		this.buttonPlusFive      = new Button();
		this.buttonMinusOne      = new Button();
		this.buttonMinusFive     = new Button();
		this.verticalLayout3     = new VerticalLayout();
		this.grid                = new Grid<>(Spieler.class, false);
		this.button4             = new Button();
		this.verticalLayout2     = new VerticalLayout();
		
		this.setClassName("my-view my-view4");
		this.getStyle().set("overflow-x", "hidden");
		this.getStyle().set("overflow-y", "auto");
		this.lblContent.setText("KEGELN");
		this.lblContent.getStyle().set("font-size", "2em");
		this.button.setText("Teamspiel");
		this.button.setIcon(SocialIcons.PEOPLE.create());
		this.button2.setText("Spieler Hinzufügen");
		this.button2.setIcon(VaadinIcon.PLUS.create());
		this.button3.setMinHeight("");
		this.button3.setMaxHeight("");
		this.button3.setText("Spiel Ende");
		this.button3.setMaxWidth("");
		this.button3.setIcon(VaadinIcon.EXIT.create());
		this.multiSelectListBox2.setRenderer(new TextRenderer<>(
			ItemLabelGeneratorFactory.NonNull(v -> CaptionUtils.resolveCaption(v, "{%name} {%nachName}"))));
		this.multiSelectListBox.setVisible(false);
		this.multiSelectListBox.setRenderer(new TextRenderer<>(ItemLabelGeneratorFactory.NonNull(Strafe::getName)));
		this.verticalLayout.setVisible(false);
		this.buttonPlusOne.setText("+ 1");
		this.buttonPlusFive.setText("+5");
		this.buttonMinusOne.setText("-1");
		this.buttonMinusFive.setText("-5");
		this.verticalLayout3.setVisible(false);
		this.grid.addColumn(Spieler::getName).setKey("name")
			.setHeader(CaptionUtils.resolveCaption(Spieler.class, "name"))
			.setSortable(true);
		this.grid.addColumn(Spieler::getNachName).setKey("nachName")
			.setHeader(CaptionUtils.resolveCaption(Spieler.class, "nachName")).setSortable(true);
		this.grid.setSelectionMode(Grid.SelectionMode.SINGLE);
		this.button4.setText("Hinzufügen");
		this.button4.setVisible(false);
		
		this.button.setSizeUndefined();
		this.button2.setSizeUndefined();
		this.button3.setSizeUndefined();
		this.horizontalLayout.add(this.button, this.button2, this.button3);
		this.horizontalLayout.setVerticalComponentAlignment(FlexComponent.Alignment.START, this.button);
		this.horizontalLayout.setVerticalComponentAlignment(FlexComponent.Alignment.STRETCH, this.button3);
		this.buttonPlusOne.setSizeUndefined();
		this.buttonPlusFive.setSizeUndefined();
		this.buttonMinusOne.setSizeUndefined();
		this.buttonMinusFive.setSizeUndefined();
		this.verticalLayout.add(this.buttonPlusOne, this.buttonPlusFive, this.buttonMinusOne, this.buttonMinusFive);
		this.multiSelectListBox2.setSizeUndefined();
		this.multiSelectListBox.setSizeUndefined();
		this.verticalLayout.setSizeUndefined();
		this.horizontalLayout2.add(this.multiSelectListBox2, this.multiSelectListBox, this.verticalLayout);
		this.grid.setWidthFull();
		this.grid.setHeight(null);
		this.button4.setSizeUndefined();
		this.verticalLayout3.add(this.grid, this.button4);
		this.lblContent.setSizeUndefined();
		this.horizontalLayout.setSizeUndefined();
		this.horizontalLayout2.setSizeUndefined();
		this.verticalLayout3.setSizeFull();
		this.verticalLayout2.setSizeFull();
		this.add(this.lblContent, this.horizontalLayout, this.horizontalLayout2, this.verticalLayout3,
			this.verticalLayout2);
		this.setSizeUndefined();
		
		this.button.addClickListener(this::button_onClick);
		this.button2.addClickListener(this::button2_onClick);
		this.button3.addClickListener(this::button3_onClick);
		this.multiSelectListBox2.addValueChangeListener(this::multiSelectListBox2_valueChanged);
		this.multiSelectListBox.addValueChangeListener(this::multiSelectListBox_valueChanged);
		this.buttonPlusOne.addClickListener(this::buttonPlusOne_onClick);
		this.buttonPlusFive.addClickListener(this::buttonPlusFive_onClick);
		this.buttonMinusOne.addClickListener(this::buttonMinusOne_onClick);
		this.buttonMinusFive.addClickListener(this::buttonMinusFive_onClick);
		this.grid.addItemClickListener(this::grid_onItemClick);
		this.button4.addClickListener(this::button4_onClick);
	} // </generated-code>
	
	// <generated-code name="variables">
	private Button                      button, button2, button3, buttonPlusOne, buttonPlusFive, buttonMinusOne,
		buttonMinusFive, button4;
	private Grid<Spieler>               grid;
	private HorizontalLayout            horizontalLayout, horizontalLayout2;
	private VerticalLayout              verticalLayout, verticalLayout3, verticalLayout2;
	private Label                       lblContent;
	private MultiSelectListBox<Spieler> multiSelectListBox2;
	private MultiSelectListBox<Strafe>  multiSelectListBox;
	// </generated-code>

}
