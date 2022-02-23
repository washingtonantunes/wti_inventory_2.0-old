package model.gui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.entities.Change;
import model.entities.Collaborator;
import model.entities.Equipment;
import model.entities.Monitor;
import model.entities.Project;
import model.entities.User;
import model.entities.WorkPosition;
import model.services.change.ChangeService;
import model.services.equipment.EquipmentService;
import model.services.monitor.MonitorService;
import model.services.project.ProjectService;
import model.services.user.UserService;
import model.services.workposition.WorkPositionService;
import model.util.MenuBar;

public class MainWindow extends JFrame {

	private static final long serialVersionUID = 1L;

	private static final Dimension DIMENSIONMAINPANEL = new Dimension(1350, 710);

	private MenuBar menu;

	private JDesktopPane main;

	public static Collaborator collaborator = new Collaborator("Washington Antunes", "853373", "853373", 0, "Analista",
			"ACTIVE", null);

	private static List<Change> changes;
	private static Map<String, Project> projects;
	private static Map<String, User> users;
	private static Map<String, WorkPosition> workPositions;
	private static Map<String, Equipment> equipments;
	private static Map<String, Monitor> monitors;

	public MainWindow() {
		changes = loadDataChanges();
		projects = loadDataProjects();
		users = loadDataUsers();
		workPositions = loadDataWorkPositions();
		equipments = loadDataEquipments();
		monitors = loadDataMonitors();
		initComponents();
	}

	private void initComponents() {
		add(create());

		menu = new MenuBar(main);
		setJMenuBar(menu);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("wTI Inventory");
		setPreferredSize(DIMENSIONMAINPANEL);
		setResizable(false);

		pack();
		setLocationRelativeTo(null);
	}

	public JPanel createPanelMain() {
		JPanel panel = new JPanel();
		panel.setLayout(null);

		JLabel label_text = new JLabel("Welcome to wTI Inventory");
		label_text.setFont(new Font("fontLabel", ALLBITS, 50));
		label_text.setForeground(new Color(4, 77, 92));
		label_text.setBounds(120, 305, 600, 50);
		panel.add(label_text);

		JLabel label_icon = new JLabel(new ImageIcon(getClass().getResource("/model/icon/iconMain.jpg")));
		label_icon.setBounds(820, 220, 330, 210);
		panel.add(label_icon);

		JLabel label_author = new JLabel("Author: Washington Antunes");
		label_author.setFont(new Font("fontLabel", ALLBITS, 10));
		label_author.setForeground(new Color(4, 77, 92));
		label_author.setBounds(1100, 610, 600, 50);
		panel.add(label_author);

		return panel;
	}

	private JDesktopPane create() {
		main = new JDesktopPane();
		main.setLayout(new CardLayout());

		main.add(createPanelMain());

		return main;
	}

	public JDesktopPane getMain() {
		return main;
	}

	public static void addChange(Change change) {
		changes.add(change);
	}

	public static List<Change> getChanges() {
		return changes;
	}

	// User
	public static void addUser(User User) {
		users.put(User.getRegistration(), User);
	}

	public static Map<String, User> getUsers() {
		return users;
	}

	public static User getUser(String registration) {
		User user;

		if (users.containsKey(registration)) {
			user = users.get(registration);
		} 
		else {
			user = new User();
		}
		return user;
	}

	// Project
	public static void addProject(Project project) {
		projects.put(project.getCostCenter(), project);
	}

	public static Map<String, Project> getProjects() {
		return projects;
	}

	public static Project getProject(String costCenter) {
		Project project;

		if (projects.containsKey(costCenter)) {
			project = projects.get(costCenter);
		} 
		else {
			project = new Project();
		}
		return project;
	}

	// Work Position
	public static void addWorkPosition(WorkPosition workPosition) {
		workPositions.put(workPosition.getWorkPoint(), workPosition);
	}

	public static Map<String, WorkPosition> getWorkPositions() {
		return workPositions;
	}

	public static WorkPosition getWorkPosition(String workPoint) {
		WorkPosition workPosition;

		if (workPositions.containsKey(workPoint)) {
			workPosition = workPositions.get(workPoint);
		} 
		else {
			workPosition = new WorkPosition();
		}
		return workPosition;
	}

	// Equipment
	public static void addEquipment(Equipment equipment) {
		equipments.put(equipment.getSerialNumber(), equipment);
	}

	public static Map<String, Equipment> getEquipments() {
		return equipments;
	}

	public static Equipment getEquipment(String equipment) {
		Equipment Equipment;

		if (equipments.containsKey(equipment)) {
			Equipment = equipments.get(equipment);
		} 
		else {
			Equipment = new Equipment();
		}
		return Equipment;
	}
	
	// Monitor
	public static void addMonitor(Monitor monitor) {
		monitors.put(monitor.getSerialNumber(), monitor);
	}

	public static Map<String, Monitor> getMonitors() {
		return monitors;
	}

	public static Monitor getMonitor(String monitor) {
		Monitor Monitor;

		if (monitors.containsKey(monitor)) {
			Monitor = monitors.get(monitor);
		} else {
			Monitor = new Monitor();
		}
		return Monitor;
	}

	private List<Change> loadDataChanges() {
		final ChangeService service = new ChangeService();
		List<Change> list = service.findAll();
		return list;
	}

	private Map<String, User> loadDataUsers() {
		final UserService service = new UserService();
		Map<String, User> list = service.findAll();
		return list;
	}

	private Map<String, WorkPosition> loadDataWorkPositions() {
		final WorkPositionService service = new WorkPositionService();
		Map<String, WorkPosition> list = service.findAll();
		return list;
	}

	private Map<String, Project> loadDataProjects() {
		final ProjectService service = new ProjectService();
		Map<String, Project> list = service.findAll();
		return list;
	}
	
	private Map<String, Equipment> loadDataEquipments() {
		final EquipmentService service = new EquipmentService();
		Map<String, Equipment> list = service.findAll();
		return list;
	}
	
	private Map<String, Monitor> loadDataMonitors() {
		final MonitorService service = new MonitorService();
		Map<String, Monitor> list = service.findAll();
		return list;
	}
}
