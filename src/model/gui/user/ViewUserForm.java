package model.gui.user;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.entities.Equipment;
import model.entities.License;
import model.entities.Monitor;
import model.entities.Peripheral;
import model.entities.User;
import model.entities.utilitay.Item;
import model.gui.MainWindow;
import model.gui.change.ChangesPanel;
import model.gui.item.ItemList;
import model.util.MyButton;
import model.util.MyLabel;

public class ViewUserForm extends JDialog {

	private static final long serialVersionUID = 1L;

	private final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

	private final Color COLOR1 = new Color(0, 65, 83);
	private final Color COLOR2 = new Color(2, 101, 124);

	private final int SIZE_LABELS = 1;
	private final int SIZE_LABELS_SHOW = 7;

	private final int SIZEBUTTONS = 1;

	private final int COLOR_LABEL = 1;
	private final int COLOR_LABEL_SHOW = 2;

	private final int FONT = 1;

	private final int WIDTH_INTERNAL_PANEL = (100 + 400) + 20;

	private final int HEIGHT_TOP_PANEL = 10;
	private final int HEIGHT_FIELD_PANEL = 42 * 9;
	private final int HEIGHT_BUTTON_PANEL = 50;

	private final int WIDTH_MAIN_PANEL = WIDTH_INTERNAL_PANEL + 50;
	private final int HEIGHT_MAIN_PANEL = HEIGHT_FIELD_PANEL + HEIGHT_BUTTON_PANEL + 64 + 20;

	private User user;

	public ViewUserForm(User user) {
		this.user = user;
		initObjects();
		initComponents();
	}

	private void initComponents() {
		setTitle("View User");
		setModal(true);
		setLayout(new FlowLayout(FlowLayout.CENTER, 40, 10));
		setPreferredSize(new Dimension(WIDTH_MAIN_PANEL, HEIGHT_MAIN_PANEL));
		setResizable(false);
		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

		add(createTopPanel());
		add(createFieldsPanel());
		add(createButtonPanel());

		pack();
		setLocationRelativeTo(null);
	}

	private JPanel createTopPanel() {
		final JPanel buttonPanel = new JPanel();
		buttonPanel.setPreferredSize(new Dimension(WIDTH_INTERNAL_PANEL, HEIGHT_TOP_PANEL));
		buttonPanel.setBackground(COLOR1);

		return buttonPanel;
	}

	private JPanel createFieldsPanel() {
		final JPanel fieldsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
		fieldsPanel.setPreferredSize(new Dimension(WIDTH_INTERNAL_PANEL, HEIGHT_FIELD_PANEL));

		final JLabel label_Registration = new MyLabel("Registration:", SIZE_LABELS, COLOR_LABEL, FONT);
		fieldsPanel.add(label_Registration);

		final JLabel label_Show_Registration = new MyLabel(user.getRegistration(), SIZE_LABELS_SHOW, COLOR_LABEL_SHOW,
				FONT);
		fieldsPanel.add(label_Show_Registration);

		final JLabel label_Name = new MyLabel("Name:", SIZE_LABELS, COLOR_LABEL, FONT);
		fieldsPanel.add(label_Name);

		final JLabel label_Show_Name = new MyLabel(user.getName(), SIZE_LABELS_SHOW, COLOR_LABEL_SHOW, FONT);
		fieldsPanel.add(label_Show_Name);

		final JLabel label_CPF = new MyLabel("CPF:", SIZE_LABELS, COLOR_LABEL, FONT);
		fieldsPanel.add(label_CPF);

		final JLabel label_Show_CPF = new MyLabel(user.getCpf(), SIZE_LABELS_SHOW, COLOR_LABEL_SHOW, FONT);
		fieldsPanel.add(label_Show_CPF);

		final JLabel label_Phone = new MyLabel("Phone:", SIZE_LABELS, COLOR_LABEL, FONT);
		fieldsPanel.add(label_Phone);

		final JLabel label_Show_Phone = new MyLabel(user.getPhone(), SIZE_LABELS_SHOW, COLOR_LABEL_SHOW, FONT);
		fieldsPanel.add(label_Show_Phone);

		final JLabel label_Email = new MyLabel("Email:", SIZE_LABELS, COLOR_LABEL, FONT);
		fieldsPanel.add(label_Email);

		final JLabel label_Show_Email = new MyLabel(user.getEmail(), SIZE_LABELS_SHOW, COLOR_LABEL_SHOW, FONT);
		fieldsPanel.add(label_Show_Email);

		final JLabel label_Department = new MyLabel("Department:", SIZE_LABELS, COLOR_LABEL, FONT);
		fieldsPanel.add(label_Department);

		final JLabel label_Show_Department = new MyLabel(user.getDepartment(), SIZE_LABELS_SHOW, COLOR_LABEL_SHOW,
				FONT);
		fieldsPanel.add(label_Show_Department);

		final JLabel label_Project = new MyLabel("Project:", SIZE_LABELS, COLOR_LABEL, FONT);
		fieldsPanel.add(label_Project);

		final JLabel label_Show_Project = new MyLabel(user.getProject().getName(), SIZE_LABELS_SHOW, COLOR_LABEL_SHOW,
				FONT);
		fieldsPanel.add(label_Show_Project);

		JLabel label_Status = new MyLabel("Status:", SIZE_LABELS, COLOR_LABEL, FONT);
		fieldsPanel.add(label_Status);

		final JLabel label_Show_Status = new MyLabel(user.getStatus(), SIZE_LABELS_SHOW, COLOR_LABEL_SHOW, FONT);
		fieldsPanel.add(label_Show_Status);

		JLabel label_DateEntry = new MyLabel("DateEntry:", SIZE_LABELS, COLOR_LABEL, FONT);
		fieldsPanel.add(label_DateEntry);

		final JLabel label_Show_DateEntry = new MyLabel(sdf.format(user.getDateEntry()), SIZE_LABELS_SHOW,
				COLOR_LABEL_SHOW, FONT);
		fieldsPanel.add(label_Show_DateEntry);

		return fieldsPanel;
	}

	private JPanel createButtonPanel() {
		final JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
		buttonPanel.setPreferredSize(new Dimension(WIDTH_INTERNAL_PANEL, HEIGHT_BUTTON_PANEL));
		buttonPanel.setBackground(COLOR2);

		final JButton buttonItens = new MyButton("Itens", SIZEBUTTONS);
		buttonItens.addActionListener(new buttonItensListener());
		buttonPanel.add(buttonItens);

		final JButton buttonChanges = new MyButton("Changes", SIZEBUTTONS);
		buttonChanges.addActionListener(new buttonChangesListener());
		buttonPanel.add(buttonChanges);

		final JButton buttonClose = new MyButton("Close", SIZEBUTTONS);
		buttonClose.addActionListener(new buttonCloseListener());
		buttonPanel.add(buttonClose);

		return buttonPanel;
	}

	private class buttonItensListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			final List<Item> itens = getItens();
			new ItemList(itens, user).setVisible(true);
		}
	}

	private class buttonChangesListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			new ChangesPanel(user.getChanges());
		}
	}

	private class buttonCloseListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			dispose();
		}
	}

	private void initObjects() {
		if (user.getEquipment().getSerialNumber() != null) {
			Equipment equipment = MainWindow.getEquipment(user.getEquipment().getSerialNumber());
			user.setEquipment(equipment);
		}
		if (user.getMonitor1().getSerialNumber() != null) {
			Monitor monitor = MainWindow.getMonitor(user.getMonitor1().getSerialNumber());
			user.setMonitor1(monitor);
		}
		if (user.getMonitor2().getSerialNumber() != null) {
			Monitor monitor = MainWindow.getMonitor(user.getMonitor2().getSerialNumber());
			user.setMonitor2(monitor);
		}

		{ // SET Peripheral
			List<Peripheral> listPeripheral = new ArrayList<Peripheral>();

			for (Peripheral peripheralss : user.getPeripherals()) {
				if (peripheralss.getCode() != null) {
					Peripheral p = MainWindow.getPeripheral(peripheralss.getCode());
					listPeripheral.add(p);
				}
			}

			if (listPeripheral.size() > 0) {
				user.setPeripherals(listPeripheral);
			} 
		}

		{ // SET License
			List<License> listLicense = new ArrayList<License>();

			for (License license : user.getLicenses()) {
				if (license.getCode() != null) {
					License l = MainWindow.getLicense(license.getCode());
					listLicense.add(l);
				}
			}

			if (listLicense.size() > 0) {
				user.setLicenses(listLicense);
			} 
		}
	}

	private List<Item> getItens() {
		final List<Item> itens = new ArrayList<Item>();

		//GET Equipment
		if (user.getEquipment().getSerialNumber() != null) {
			Equipment equipment = user.getEquipment();

			final Item item = new Item();
			item.setIndex(itens.size() + 1);
			item.setType("Equipamento");
			item.setCode(equipment.getSerialNumber());
			item.setName(equipment.getType());
			item.setBrand(equipment.getBrand());
			item.setValue(equipment.getValue());
			itens.add(item);
		}
		
		//GET Monitors
		if (user.getMonitor1().getSerialNumber() != null) {
			Monitor monitor = user.getMonitor1();

			final Item item = new Item();
			item.setIndex(itens.size() + 1);
			item.setType("Monitor");
			item.setCode(monitor.getSerialNumber());
			item.setName(monitor.getModel());
			item.setBrand(monitor.getBrand());
			item.setValue(monitor.getValue());
			itens.add(item);
		}
		if (user.getMonitor2().getSerialNumber() != null) {
			Monitor monitor = user.getMonitor2();

			final Item item = new Item();
			item.setIndex(itens.size() + 1);
			item.setType("Monitor");
			item.setCode(monitor.getSerialNumber());
			item.setName(monitor.getModel());
			item.setBrand(monitor.getBrand());
			item.setValue(monitor.getValue());
			itens.add(item);
		}

		// GET Peripherals
		if (user.getPeripherals() != null && user.getPeripherals().size() > 0) {
			for (Peripheral peripheral : user.getPeripherals()) {

				final Item item = new Item();
				item.setIndex(itens.size() + 1);
				item.setType("Perif�rico");
				item.setCode(peripheral.getCode());
				item.setName(peripheral.getName());
				item.setBrand(peripheral.getBrand());
				item.setValue(peripheral.getValue());
				itens.add(item);
			}
		}

		// GET Licenses
		if (user.getLicenses() != null && user.getLicenses().size() > 0) {
			for (License license : user.getLicenses()) {

				final Item item = new Item();
				item.setIndex(itens.size() + 1);
				item.setType("Licen�a");
				item.setCode(license.getCode());
				item.setName(license.getName());
				item.setBrand(license.getBrand());
				item.setValue(license.getValue());
				itens.add(item);
			}
		}
		return itens;
	}
}
