
package com.company.kegelapp.ui;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.Month;
import java.util.Collection;

import org.apache.commons.io.FileUtils;

import com.company.kegelapp.dal.SpielerDAO;
import com.company.kegelapp.domain.Spieler;
import com.rapidclipse.framework.server.resources.CaptionUtils;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.FormItem;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.FinishedEvent;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.selection.SelectionEvent;
import com.vaadin.flow.data.selection.SelectionListener;
import com.vaadin.flow.router.Route;


@Route("spielerverwaltenview")
@HtmlImport("frontend://styles/shared-styles.html")
public class SpielerVerwaltenView extends VerticalLayout
{
	MemoryBuffer memoryBuffer = null;

	public SpielerVerwaltenView()
	{
		super();
		this.initUI();
		this.updateList();
		this.saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		this.setPlayer(null);
		this.memoryBuffer = new MemoryBuffer();
		this.upload.setReceiver(this.memoryBuffer);
	}
	
	// <generated-code name="variables">
	private FormLayout       form;
	private Checkbox         chkActive;
	private Button           saveButton, deleteButton, button;
	private DatePicker       dateBirthday;
	private Grid<Spieler>    grid;
	private Upload           upload;
	private Binder<Spieler>  binder;
	private HorizontalLayout horizontalLayout;
	private Label            lblContent, lblActive, lblBirthday, lblNachName, lblName, label;
	private TextField        txtNachName, txtName;
	private FormItem         formItem, formItem2, formItem3, formItem4, formItem5;
	// </generated-code>
	
	/**
	 * Event handler delegate method for the {@link Button} {@link #button}.
	 *
	 * @see ComponentEventListener#onComponentEvent(ComponentEvent)
	 * @eventHandlerDelegate Do NOT delete, used by UI designer!
	 */
	private void button_onClick(final ClickEvent<Button> event)
	{
		this.grid.asSingleSelect().clear();
		final Spieler spielerToSave = new Spieler();
		this.setPlayer(spielerToSave);
	}

	private void updateList()
	{
		System.out.println("UPDATE LIST");
		final Collection<Spieler> spieler = SpielerDAO.findAll();
		for(final Spieler einSpieler : spieler)
		{
			System.out.println(einSpieler);
		}
		this.grid.setItems(SpielerDAO.findAll());
	}
	
	private void setPlayer(final Spieler spieler)
	{
		this.binder.setBean(spieler);
		if(spieler == null)
		{
			this.form.setVisible(false);
		}
		else
		{
			this.form.setVisible(true);
			this.txtName.focus();
		}
	}

	/**
	 * Event handler delegate method for the {@link Button} {@link #saveButton}.
	 *
	 * @see ComponentEventListener#onComponentEvent(ComponentEvent)
	 * @eventHandlerDelegate Do NOT delete, used by UI designer!
	 */
	private void saveButton_onClick(final ClickEvent<Button> event)
	{
		final Spieler spieler = this.binder.getBean();

		System.out.println(spieler);

		if(this.binder.writeBeanIfValid(spieler))

		{
			if(SpielerDAO.findAll().contains(spieler))
			{
				SpielerDAO.update(spieler);
			}
			else
			{
				SpielerDAO.insert(spieler);
			}
			this.updateList();

			this.setPlayer(null);

		}

		else

		{

			System.out.println("NICHT OK");

		}
	}

	/**
	 * Event handler delegate method for the {@link Button} {@link #deleteButton}.
	 *
	 * @see ComponentEventListener#onComponentEvent(ComponentEvent)
	 * @eventHandlerDelegate Do NOT delete, used by UI designer!
	 */
	private void deleteButton_onClick(final ClickEvent<Button> event)
	{
		final Spieler spieler = this.binder.getBean();

		System.out.println(spieler);

		{
			SpielerDAO.delete(spieler);

			this.updateList();

			this.setPlayer(null);
		}
	}
	
	/**
	 * Event handler delegate method for the {@link Grid} {@link #grid}.
	 *
	 * @see SelectionListener#selectionChange(SelectionEvent)
	 * @eventHandlerDelegate Do NOT delete, used by UI designer!
	 */
	private void grid_selectionChange(final SelectionEvent<Grid<Spieler>, Spieler> event)
	{
		this.setPlayer(this.grid.asSingleSelect().getValue());
	}

	/**
	 * Event handler delegate method for the {@link Upload} {@link #upload}.
	 *
	 * @see ComponentEventListener#onComponentEvent(ComponentEvent)
	 * @eventHandlerDelegate Do NOT delete, used by UI designer!
	 */
	private void upload_onFinished(final FinishedEvent event)
	{
		final InputStream inputStream = this.memoryBuffer.getInputStream();
		System.out.println(inputStream);
		System.out.println(this.memoryBuffer.getFileName());
		System.out.println(this.memoryBuffer.getFileData());

		final File targetFile = new File("images/" + this.memoryBuffer.getFileName());

		try
		{
			FileUtils.copyInputStreamToFile(inputStream, targetFile);
		}
		catch(final IOException e)
		{
			
			e.printStackTrace();
		}

	}
	
	/* WARNING: Do NOT edit!<br>The content of this method is always regenerated by the UI designer. */
	// <generated-code name="initUI">
	private void initUI()
	{
		this.lblContent       = new Label();
		this.grid             = new Grid<>(Spieler.class, false);
		this.form             = new FormLayout();
		this.formItem         = new FormItem();
		this.lblActive        = new Label();
		this.chkActive        = new Checkbox();
		this.formItem2        = new FormItem();
		this.lblBirthday      = new Label();
		this.dateBirthday     = new DatePicker();
		this.formItem3        = new FormItem();
		this.lblNachName      = new Label();
		this.txtNachName      = new TextField();
		this.formItem4        = new FormItem();
		this.lblName          = new Label();
		this.txtName          = new TextField();
		this.horizontalLayout = new HorizontalLayout();
		this.saveButton       = new Button();
		this.deleteButton     = new Button();
		this.formItem5        = new FormItem();
		this.label            = new Label();
		this.upload           = new Upload();
		this.binder           = new Binder<>();
		this.button           = new Button();

		this.lblContent.setText("Spieler Verwalten");
		this.lblContent.getStyle().set("font-size", "2em");
		this.grid.addColumn(Spieler::getName).setKey("name")
			.setHeader(CaptionUtils.resolveCaption(Spieler.class, "name"))
			.setSortable(true);
		this.grid.addColumn(Spieler::getNachName).setKey("nachName")
			.setHeader(CaptionUtils.resolveCaption(Spieler.class, "nachName")).setSortable(true);
		this.grid.addColumn(Spieler::getActive).setKey("active")
			.setHeader(CaptionUtils.resolveCaption(Spieler.class, "active")).setSortable(true);
		this.grid.setSelectionMode(Grid.SelectionMode.SINGLE);
		this.form.setResponsiveSteps(
			new FormLayout.ResponsiveStep("0px", 1, FormLayout.ResponsiveStep.LabelsPosition.TOP),
			new FormLayout.ResponsiveStep("500px", 2, FormLayout.ResponsiveStep.LabelsPosition.TOP),
			new FormLayout.ResponsiveStep("1000px", 3, FormLayout.ResponsiveStep.LabelsPosition.ASIDE));
		this.lblActive.setText("active");
		this.chkActive.setTabIndex(1);
		this.chkActive.setLabel("Checkbox");
		this.lblBirthday.setText("birthday");
		this.dateBirthday.setTabIndex(2);
		this.dateBirthday.setMin(LocalDate.of(1950, Month.JUNE, 18));
		this.dateBirthday.setPlaceholder("");
		this.dateBirthday.setValue(LocalDate.of(1990, Month.JANUARY, 1));
		this.dateBirthday.setMax(LocalDate.of(2020, Month.JUNE, 18));
		this.dateBirthday.setInitialPosition(LocalDate.of(1990, Month.APRIL, 5));
		this.lblNachName.setText("nachName");
		this.txtNachName.setTabIndex(3);
		this.lblName.setText("name");
		this.txtName.setTabIndex(4);
		this.saveButton.setText("SAVE");
		this.deleteButton.setText("DELETE");
		this.label.setText("Label");
		this.upload.setMaxFiles(1);
		this.upload.setMaxFileSize(52428800);
		this.upload.setDropLabelIcon(VaadinIcon.DROP.create());
		this.button.setText("Spieler hinzufügen");

		this.binder.forField(this.chkActive).withNullRepresentation(false).bind(Spieler::getActive, Spieler::setActive);
		this.binder.forField(this.txtNachName).asRequired().withNullRepresentation("").bind(Spieler::getNachName,
			Spieler::setNachName);
		this.binder.forField(this.txtName).asRequired().withNullRepresentation("").bind(Spieler::getName,
			Spieler::setName);
		this.binder.forField(this.dateBirthday).withNullRepresentation(LocalDate.of(1991, Month.MARCH, 14))
			.bind(Spieler::getBirthday, Spieler::setBirthday);

		this.lblActive.setSizeUndefined();
		this.lblActive.getElement().setAttribute("slot", "label");
		this.chkActive.setWidthFull();
		this.chkActive.setHeight(null);
		this.formItem.add(this.lblActive, this.chkActive);
		this.lblBirthday.setSizeUndefined();
		this.lblBirthday.getElement().setAttribute("slot", "label");
		this.dateBirthday.setWidthFull();
		this.dateBirthday.setHeight(null);
		this.formItem2.add(this.lblBirthday, this.dateBirthday);
		this.lblNachName.setSizeUndefined();
		this.lblNachName.getElement().setAttribute("slot", "label");
		this.txtNachName.setWidthFull();
		this.txtNachName.setHeight(null);
		this.formItem3.add(this.lblNachName, this.txtNachName);
		this.lblName.setSizeUndefined();
		this.lblName.getElement().setAttribute("slot", "label");
		this.txtName.setWidthFull();
		this.txtName.setHeight(null);
		this.formItem4.add(this.lblName, this.txtName);
		this.saveButton.setSizeUndefined();
		this.deleteButton.setSizeUndefined();
		this.horizontalLayout.add(this.saveButton, this.deleteButton);
		this.label.setSizeUndefined();
		this.label.getElement().setAttribute("slot", "label");
		this.upload.setWidthFull();
		this.upload.setHeight(null);
		this.formItem5.add(this.label, this.upload);
		this.horizontalLayout.setWidth("100px");
		this.horizontalLayout.setHeight("100px");
		this.form.add(this.formItem, this.formItem2, this.formItem3, this.formItem4, this.horizontalLayout,
			this.formItem5);
		this.lblContent.setSizeUndefined();
		this.grid.setWidthFull();
		this.grid.setHeight(null);
		this.form.setSizeUndefined();
		this.button.setSizeUndefined();
		this.add(this.lblContent, this.grid, this.form, this.button);
		this.setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER, this.lblContent);
		this.setHorizontalComponentAlignment(FlexComponent.Alignment.CENTER, this.button);
		this.setSizeUndefined();

		this.grid.addSelectionListener(this::grid_selectionChange);
		this.saveButton.addClickListener(this::saveButton_onClick);
		this.deleteButton.addClickListener(this::deleteButton_onClick);
		this.upload.addFinishedListener(this::upload_onFinished);
		this.button.addClickListener(this::button_onClick);
	} // </generated-code>

}
