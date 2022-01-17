package model.gui.equipment;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import db.DBException;
import exception.ValidationException;
import model.entities.Equipment;
import model.entities.Option;
import model.services.EquipmentService;
import model.services.EquipmentTableModel;
import model.services.OptionService;
import model.util.JTextFieldFilter;
import model.util.Utils;

public class NewEquipmentForm extends JDialog {

	private static final long serialVersionUID = 1L;

	private static final int COLUMN1 = 20;
	private static final int COLUMN2 = 170;
	private static final int COLUMN3 = 330;

	private static final int WIDTH = 150;
	private static final int HEIGHT = 25;

	private static final Dimension DIMENSIONMAINPANEL = new Dimension(600, 550);

	private JTextField myTextField_SerialNumber;
	private JTextField myTextField_HostName;
	private JTextField myTextField_AddressMAC;
	private JComboBox<String> comboBox_Type;
	private JTextField myTextField_PatrimonyNumber;
	private JComboBox<String> comboBox_Brand;
	private JComboBox<String> comboBox_Model;
	private JComboBox<String> comboBox_MemoryRam;
	private JComboBox<String> comboBox_HardDisk;
	private JComboBox<String> comboBox_CostType;
	private JTextField myTextField_Value;

	private JLabel labelError_SerialNumber;
	private JLabel labelError_HostName;
	private JLabel labelError_AddressMAC;
	private JLabel labelError_Type;
	private JLabel labelError_PatrimonyNumber;
	private JLabel labelError_Brand;
	private JLabel labelError_Model;
	private JLabel labelError_MemoryRam;
	private JLabel labelError_HardDisk;

	private Equipment equipment;
	private EquipmentTableModel model;
	private List<Option> options;

	public NewEquipmentForm(EquipmentTableModel model) {
		this.model = model;
		this.options = loadDataOption();
		initComponents();
	}

	private void initComponents() {
		setModal(true);

		add(createPanelMain());

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("New Equipment");
		setPreferredSize(DIMENSIONMAINPANEL);
		setResizable(false);

		pack();
		setLocationRelativeTo(null);
	}

	private JPanel createPanelMain() {
		final JPanel panel = new JPanel(new FlowLayout());
		panel.setLayout(null);

		addLabelsShow(panel);
		addTextFieldsAndComboBoxes(panel);
		addLabelsError(panel);
		addButtons(panel);

		return panel;
	}

	private void addLabelsShow(JPanel panel) {
		JLabel label_SerialNumber = new JLabel("Serial Number:");
		label_SerialNumber.setBounds(COLUMN1, 10, WIDTH, HEIGHT);
		panel.add(label_SerialNumber);

		JLabel label_HostName = new JLabel("Host Name:");
		label_HostName.setBounds(COLUMN1, 50, WIDTH, HEIGHT);
		panel.add(label_HostName);

		JLabel label_AddressMAC = new JLabel("Address MAC:");
		label_AddressMAC.setBounds(COLUMN1, 90, WIDTH, HEIGHT);
		panel.add(label_AddressMAC);

		JLabel label_Type = new JLabel("Type:");
		label_Type.setBounds(COLUMN1, 130, WIDTH, HEIGHT);
		panel.add(label_Type);
		
		JLabel label_PatrimonyNumber = new JLabel("PatrimonyNumber:");
		label_PatrimonyNumber.setBounds(COLUMN1, 170, WIDTH, HEIGHT);
		panel.add(label_PatrimonyNumber);
		
		JLabel label_Brand = new JLabel("Brand:");
		label_Brand.setBounds(COLUMN1, 210, WIDTH, HEIGHT);
		panel.add(label_Brand);
		
		JLabel label_Model = new JLabel("Model:");
		label_Model.setBounds(COLUMN1, 250, WIDTH, HEIGHT);
		panel.add(label_Model);
		
		JLabel label_MemoryRam = new JLabel("MemoryRam:");
		label_MemoryRam.setBounds(COLUMN1, 290, WIDTH, HEIGHT);
		panel.add(label_MemoryRam);
		
		JLabel label_HardDisk = new JLabel("HardDisk:");
		label_HardDisk.setBounds(COLUMN1, 330, WIDTH, HEIGHT);
		panel.add(label_HardDisk);
		
		JLabel label_CostType = new JLabel("CostType:");
		label_CostType.setBounds(COLUMN1, 370, WIDTH, HEIGHT);
		panel.add(label_CostType);
		
		JLabel label_Value = new JLabel("Value:");
		label_Value.setBounds(COLUMN1, 410, WIDTH, HEIGHT);
		panel.add(label_Value);
	}

	private void addTextFieldsAndComboBoxes(JPanel panel) {
		myTextField_SerialNumber = new JTextField();
		myTextField_SerialNumber.setDocument(new JTextFieldFilter(JTextFieldFilter.SERIALNUMBER, 12));
		myTextField_SerialNumber.setBounds(COLUMN2, 10, WIDTH, HEIGHT);
		panel.add(myTextField_SerialNumber);

		myTextField_HostName = new JTextField();
		myTextField_HostName.setDocument(new JTextFieldFilter(JTextFieldFilter.UPPERCASE_NUMERIC_NO_SPACE, 10));
		myTextField_HostName.setBounds(COLUMN2, 50, WIDTH, HEIGHT);
		panel.add(myTextField_HostName);

		myTextField_AddressMAC = new JTextField();
		myTextField_AddressMAC.setDocument(new JTextFieldFilter(JTextFieldFilter.ADDRESS_MAC, 17));
		myTextField_AddressMAC.setBounds(COLUMN2, 90, WIDTH, HEIGHT);
		panel.add(myTextField_AddressMAC);
		
		comboBox_Type = new JComboBox<>(new Vector<>(options.stream()
				.filter(o -> o.getType().equals("TYPE") && o.getStatusOption().equals("ACTIVE"))
				.map(Option::getOption).collect(Collectors.toList())));
		comboBox_Type.setSelectedIndex(-1);
		comboBox_Type.setBounds(COLUMN2, 130, WIDTH, HEIGHT);
		panel.add(comboBox_Type);
		
		myTextField_PatrimonyNumber = new JTextField();
		myTextField_PatrimonyNumber.setDocument(new JTextFieldFilter(JTextFieldFilter.NUMERIC, 6));
		myTextField_PatrimonyNumber.setBounds(COLUMN2, 170, WIDTH, HEIGHT);
		panel.add(myTextField_PatrimonyNumber);
		
		comboBox_Brand = new JComboBox<>(new Vector<>(options.stream()
				.filter(o -> o.getType().equals("BRAND") && o.getStatusOption().equals("ACTIVE"))
				.map(Option::getOption).collect(Collectors.toList())));
		comboBox_Brand.setSelectedIndex(-1);
		comboBox_Brand.setBounds(COLUMN2, 210, WIDTH, HEIGHT);
		panel.add(comboBox_Brand);
		
		comboBox_Model = new JComboBox<>(new Vector<>(options.stream()
				.filter(o -> o.getType().equals("MODEL") && o.getStatusOption().equals("ACTIVE"))
				.map(Option::getOption).collect(Collectors.toList())));
		comboBox_Model.setSelectedIndex(-1);
		comboBox_Model.setBounds(COLUMN2, 250, WIDTH, HEIGHT);
		panel.add(comboBox_Model);
		
		comboBox_MemoryRam = new JComboBox<>(new Vector<>(options.stream()
				.filter(o -> o.getType().equals("MEMORY RAM") && o.getStatusOption().equals("ACTIVE"))
				.map(Option::getOption).collect(Collectors.toList())));
		comboBox_MemoryRam.setSelectedIndex(-1);
		comboBox_MemoryRam.setBounds(COLUMN2, 290, WIDTH, HEIGHT);
		panel.add(comboBox_MemoryRam);
		
		comboBox_HardDisk = new JComboBox<>(new Vector<>(options.stream()
				.filter(o -> o.getType().equals("HARD DISK") && o.getStatusOption().equals("ACTIVE"))
				.map(Option::getOption).collect(Collectors.toList())));
		comboBox_HardDisk.setSelectedIndex(-1);
		comboBox_HardDisk.setBounds(COLUMN2, 330, WIDTH, HEIGHT);
		panel.add(comboBox_HardDisk);
		
		comboBox_CostType = new JComboBox<>(new Vector<>(options.stream()
				.filter(o -> o.getType().equals("COST TYPE") && o.getStatusOption().equals("ACTIVE"))
				.map(Option::getOption).collect(Collectors.toList())));
		comboBox_CostType.setSelectedIndex(-1);
		comboBox_CostType.setBounds(COLUMN2, 370, WIDTH, HEIGHT);
		panel.add(comboBox_CostType);
		
		myTextField_Value = new JTextField();
		myTextField_Value.setDocument(new JTextFieldFilter(JTextFieldFilter.DECIMAL, 5));
		myTextField_Value.setBounds(COLUMN2, 410, WIDTH, HEIGHT);
		panel.add(myTextField_Value);
	}

	private void addLabelsError(JPanel panel) {
		labelError_SerialNumber = new JLabel();
		labelError_SerialNumber.setForeground(Color.RED);
		labelError_SerialNumber.setBounds(COLUMN3, 10, WIDTH + 90, HEIGHT);
		panel.add(labelError_SerialNumber);

		labelError_HostName = new JLabel();
		labelError_HostName.setForeground(Color.RED);
		labelError_HostName.setBounds(COLUMN3, 50, WIDTH + 90, HEIGHT);
		panel.add(labelError_HostName);

		labelError_AddressMAC = new JLabel();
		labelError_AddressMAC.setForeground(Color.RED);
		labelError_AddressMAC.setBounds(COLUMN3, 90, WIDTH + 90, HEIGHT);
		panel.add(labelError_AddressMAC);
		
		labelError_Type = new JLabel();
		labelError_Type.setForeground(Color.RED);
		labelError_Type.setBounds(COLUMN3, 130, WIDTH + 90, HEIGHT);
		panel.add(labelError_Type);
		
		labelError_PatrimonyNumber = new JLabel();
		labelError_PatrimonyNumber.setForeground(Color.RED);
		labelError_PatrimonyNumber.setBounds(COLUMN3, 170, WIDTH + 90, HEIGHT);
		panel.add(labelError_PatrimonyNumber);
		
		labelError_Brand = new JLabel();
		labelError_Brand.setForeground(Color.RED);
		labelError_Brand.setBounds(COLUMN3, 210, WIDTH + 90, HEIGHT);
		panel.add(labelError_Brand);
		
		labelError_Model = new JLabel();
		labelError_Model.setForeground(Color.RED);
		labelError_Model.setBounds(COLUMN3, 250, WIDTH + 90, HEIGHT);
		panel.add(labelError_Model);
		
		labelError_MemoryRam = new JLabel();
		labelError_MemoryRam.setForeground(Color.RED);
		labelError_MemoryRam.setBounds(COLUMN3, 290, WIDTH + 90, HEIGHT);
		panel.add(labelError_MemoryRam);
		
		labelError_HardDisk = new JLabel();
		labelError_HardDisk.setForeground(Color.RED);
		labelError_HardDisk.setBounds(COLUMN3, 330, WIDTH + 90, HEIGHT);
		panel.add(labelError_HardDisk);
	}

	private void addButtons(JPanel panel) {
		JButton buttonSave = new JButton("Save");
		buttonSave.setBounds(180, 470, WIDTH - 30, HEIGHT);
		buttonSave.addActionListener(new buttonSaveListener());
		panel.add(buttonSave);

		JButton buttonClose = new JButton("Close");
		buttonClose.setBounds(320, 470, WIDTH - 30, HEIGHT);
		buttonClose.addActionListener(new buttonCloseListener());
		panel.add(buttonClose);
	}
	
	private List<Option> loadDataOption() {
		final OptionService service = new OptionService();
		List<Option> list = service.findAll();
		return list;
	}

	private class buttonSaveListener implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			try {
				equipment = getFormData();
				EquipmentService service = new EquipmentService();
				service.save(equipment);
				model.addEquipment(equipment);
				JOptionPane.showMessageDialog(rootPane, "Equipment successfully added", "Success saving object", JOptionPane.INFORMATION_MESSAGE);
				dispose();
			} 
			catch (ValidationException e) {
				setErrorMessages(e.getErrors());
			} 
			catch (DBException e) {
				setErroMessagesDBException(e);
			}
		}
	}

	private class buttonCloseListener implements ActionListener {

		public void actionPerformed(ActionEvent event) {
			dispose();
		}
	}

	private Equipment getFormData() {
		Equipment equipment = new Equipment();

		ValidationException exception = new ValidationException("Validation error");

		// validation SerialNumber
		if (myTextField_SerialNumber.getText() == null || myTextField_SerialNumber.getText().trim().equals("")) {
			exception.addError("serialNumber", "Field can't be empty");
		} 
		else if (myTextField_SerialNumber.getText().length() < 6) {
			exception.addError("serialNumber", "Invalid serial number - Ex: > 6");
		} 
		else {
			equipment.setSerialNumber(myTextField_SerialNumber.getText().trim().toUpperCase());
		}

		// Validation HostName
		if (myTextField_HostName.getText() == null || myTextField_HostName.getText().trim().equals("")) {
			exception.addError("hostName", "Field can't be empty");
		} 
		else if (myTextField_HostName.getText().length() < 10) {
			exception.addError("hostName", "Invalid HostName - Ex: > 10");
		} 
		else if (Utils.ToCheckHostName(myTextField_HostName.getText())) {
			exception.addError("hostName", "Invalid format - Ex: 'SPODSK' or 'SPONTB'");
		} 
		else {
			equipment.setHostName(myTextField_HostName.getText().trim().toUpperCase());
		}

		// Validation AddressMac
		if (myTextField_AddressMAC.getText() == null || myTextField_AddressMAC.getText().trim().equals("")) {
			exception.addError("addressMAC", "Field can't be empty");
		} 
		else if (myTextField_AddressMAC.getText().length() < 16) {
			exception.addError("addressMAC", "Invalid Address MAC - Ex: > 16");
		} 
		else if (Utils.ToCheckAddressMAC(myTextField_AddressMAC.getText())) {
			exception.addError("addressMAC", "Invalid format - Ex: '0A-00-27-00-00-0B'");
		} 
		else {
			equipment.setAddressMAC(myTextField_AddressMAC.getText().trim().toUpperCase());
		}
		
		// Validation Type
		if (comboBox_Type.getSelectedIndex() < 0 || comboBox_Type.getSelectedItem() == null) {
			exception.addError("type", "Field can't be empty");
		} 
		else {
			equipment.setType(comboBox_Type.getSelectedItem().toString());
		}

		// Validation Patrimony Number
		if (myTextField_PatrimonyNumber.getText() == null || myTextField_PatrimonyNumber.getText().trim().equals("")) {
			exception.addError("patrimonyNumber", "Field can't be empty");
		} 
		else if (myTextField_PatrimonyNumber.getText().length() < 4) {
			exception.addError("patrimonyNumber", "Invalid Patrimony Number - Ex: > 4");
		} 
		else {
			equipment.setPatrimonyNumber(myTextField_PatrimonyNumber.getText().trim().toUpperCase());
		}

		// Validation Brand
		if (comboBox_Brand.getSelectedIndex() < 0
				|| comboBox_Brand.getSelectedItem() == null) {
			exception.addError("brand", "Field can't be empty");
		} 
		else {
			equipment.setBrand(comboBox_Brand.getSelectedItem().toString());
		}

		// Validation Model
		if (comboBox_Model.getSelectedIndex() < 0
				|| comboBox_Model.getSelectedItem() == null) {
			exception.addError("model", "Field can't be empty");
		} 
		else {
			equipment.setModel(comboBox_Model.getSelectedItem().toString());
		}

		// Validation Memory Ram
		if (comboBox_MemoryRam.getSelectedIndex() < 0
				|| comboBox_MemoryRam.getSelectedItem() == null) {
			exception.addError("memoryRam", "Field can't be empty");
		} 
		else {
			equipment.setMemoryRam(comboBox_MemoryRam.getSelectedItem().toString());
		}

		// Validation Hard Disk
		if (comboBox_HardDisk.getSelectedIndex() < 0
				|| comboBox_HardDisk.getSelectedItem() == null) {
			exception.addError("hardDisk", "Field can't be empty");
		} 
		else {
			equipment.setHardDisk(comboBox_HardDisk.getSelectedItem().toString());
		}

		equipment.setCostType(Utils.tryParseToString(comboBox_CostType));
		equipment.setValue(Utils.tryParseToDouble(myTextField_Value.getText()));
		equipment.setStatus("STAND BY");
		equipment.setDateEntry(new Date());
		equipment.setReason(null);

		if (exception.getErrors().size() > 0) {
			throw exception;
		}

		return equipment;
	}

	private void setErrorMessages(Map<String, String> errors) {
		Set<String> fields = errors.keySet();

		labelError_SerialNumber.setText(fields.contains("serialNumber") ? errors.get("serialNumber") : "");
		labelError_HostName.setText(fields.contains("hostName") ? errors.get("hostName") : "");
		labelError_AddressMAC.setText(fields.contains("addressMAC") ? errors.get("addressMAC") : "");
		labelError_Type.setText(fields.contains("type") ? errors.get("type") : "");
		labelError_PatrimonyNumber.setText(fields.contains("patrimonyNumber") ? errors.get("patrimonyNumber") : "");
		labelError_Brand.setText(fields.contains("brand") ? errors.get("brand") : ""); 
		labelError_Model.setText(fields.contains("model") ? errors.get("model") : ""); 
		labelError_MemoryRam.setText(fields.contains("memoryRam") ? errors.get("memoryRam") : "");
		labelError_HardDisk.setText(fields.contains("hardDisk") ?errors.get("hardDisk") : "");
	}
	
	private void setErroMessagesDBException(DBException e) {
		if (e.getMessage().contains("Duplicate entry")) {
			if (e.getMessage().contains("equipments.PRIMARY")) {
				JOptionPane.showMessageDialog(rootPane, "This serial number already exists", "Error saving object", JOptionPane.ERROR_MESSAGE);
			} 
			else if (e.getMessage().contains("equipments.hostname_UNIQUE")) {
				JOptionPane.showMessageDialog(rootPane, "This host name already exists", "Error saving object", JOptionPane.ERROR_MESSAGE);
			} 
			else if (e.getMessage().contains("equipments.addressMAC_UNIQUE")) {
				JOptionPane.showMessageDialog(rootPane, "This address MAC already exists", "Error saving object", JOptionPane.ERROR_MESSAGE);
			} 
			else if (e.getMessage().contains("equipments.patrimonyNumberEquipment_UNIQUE")) {
				JOptionPane.showMessageDialog(rootPane, "This patrimony number already exists", "Error saving object", JOptionPane.ERROR_MESSAGE);
			} 
			else {
				JOptionPane.showMessageDialog(rootPane, e.getMessage(), "Error saving object", JOptionPane.ERROR_MESSAGE);
			}
		} 
		else {
			JOptionPane.showMessageDialog(rootPane, e.getMessage(), "Error saving object", JOptionPane.ERROR_MESSAGE);
		}
	}
}
