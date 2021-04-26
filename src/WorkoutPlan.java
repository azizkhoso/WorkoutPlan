import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.CardLayout;
import java.awt.Color;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import java.awt.Insets;
import javax.swing.JButton;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;
import java.awt.GridLayout;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.JSeparator;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.util.Stack;
import java.util.Queue;
import java.util.Scanner;
import java.util.Iterator;
import java.util.LinkedList;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextPane;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.event.ItemListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.event.ItemEvent;

public class WorkoutPlan {

	private JFrame frame;
	private JSpinner spinnerReps;
	private JSpinner spinnerWeight;
	private JTextField textFieldWakeUp;
	private Stack cardStack = new Stack();
	private JTable tableCardioWorkouts;
	private JTable tableWeightTrainingWorkouts;
	private JTable tableInappWorkouts;
	private JTable tableCardioWorkouts_1;
	private JTable tableWeightTrainingWorkouts_1;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	static int timeCounter = 0;
	private LinkedList selectedFullBodyExercises = new LinkedList();
	private LinkedList selectedLowerBodyExercises = new LinkedList();
	private LinkedList selectedUpperBodyExercises = new LinkedList();
	private static Timer timer = null;
	private static String currentUser = "!";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					WorkoutPlan window = new WorkoutPlan();
					window.frame.setVisible(true);
					currentUser = JOptionPane.showInputDialog(window.frame, "Please enter your name").toLowerCase();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public WorkoutPlan() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 450, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		CardLayout cards = new CardLayout(0, 0);
		frame.getContentPane().setLayout(cards);
		
		JPanel homeScreen = new JPanel();
		frame.getContentPane().add(homeScreen, "homeScreen");
		homeScreen.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Workout Plan");
		lblNewLabel.setBounds(155, 60, 124, 22);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		homeScreen.add(lblNewLabel);
		
		JButton btnStartWorkout = new JButton("Start a Workout");
		btnStartWorkout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cards.show(frame.getContentPane(), "startWorkout");
				cardStack.push("homeScreen");
			}
		});
		btnStartWorkout.setBounds(123, 117, 196, 25);
		btnStartWorkout.setFont(new Font("Tahoma", Font.PLAIN, 14));
		homeScreen.add(btnStartWorkout);
		
		JButton btnAddWorkoutInfo = new JButton("Add Workout Info");
		btnAddWorkoutInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cards.show(frame.getContentPane(), "addWorkout");
				cardStack.push("homeScreen");
			}
		});
		btnAddWorkoutInfo.setBounds(123, 177, 196, 25);
		btnAddWorkoutInfo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		homeScreen.add(btnAddWorkoutInfo);
		
		JButton btnViewMyWorkout = new JButton("View My Workout Info");
		btnViewMyWorkout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cards.show(frame.getContentPane(), "myWorkouts");
				cardStack.push("homeScreen");
				populateTables();
			}
		});
		btnViewMyWorkout.setBounds(123, 237, 196, 25);
		btnViewMyWorkout.setFont(new Font("Tahoma", Font.PLAIN, 14));
		homeScreen.add(btnViewMyWorkout);
		
		JButton btnWorkoutsDoneBy = new JButton("Workouts done by Others");
		btnWorkoutsDoneBy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cards.show(frame.getContentPane(), "othersWorkouts");
				cardStack.push("homeScreen");
				populateTables();
			}
		});
		btnWorkoutsDoneBy.setBounds(123, 297, 196, 25);
		btnWorkoutsDoneBy.setFont(new Font("Tahoma", Font.PLAIN, 14));
		homeScreen.add(btnWorkoutsDoneBy);
		
		JButton btnMyInfo = new JButton("My Info");
		btnMyInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cards.show(frame.getContentPane(), "myInfo");
				cardStack.push("homeScreen");
			}
		});
		btnMyInfo.setBounds(123, 357, 196, 25);
		btnMyInfo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		homeScreen.add(btnMyInfo);
		
		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refresh();
			}
		});
		btnRefresh.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnRefresh.setBounds(10, 425, 99, 25);
		homeScreen.add(btnRefresh);
		
		JButton btnLogout = new JButton("<-Log Out");
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(frame, "Logged out Successfully");
				currentUser="!";
				currentUser = JOptionPane.showInputDialog(frame, "Please enter your name");
			}
		});
		btnLogout.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnLogout.setBounds(325, 425, 99, 25);
		homeScreen.add(btnLogout);
		
		JPanel addWorkout = new JPanel();
		frame.getContentPane().add(addWorkout, "addWorkout");
		addWorkout.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Add Workout");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblNewLabel_1.setBounds(10, 39, 414, 32);
		addWorkout.add(lblNewLabel_1);
		
		JButton btnNewButton = new JButton("<- Back");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cards.show(frame.getContentPane(), (String) cardStack.pop());
				cardStack.push("addWorkout");
			}
		});
		btnNewButton.setBounds(335, 5, 89, 23);
		addWorkout.add(btnNewButton);
		
		JLabel lblWorkout = new JLabel("Workout");
		lblWorkout.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblWorkout.setBounds(68, 124, 90, 32);
		addWorkout.add(lblWorkout);
		
		JLabel lblReps = new JLabel("Reps");
		lblReps.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblReps.setBounds(68, 210, 90, 32);
		addWorkout.add(lblReps);
		
		JLabel lblWight = new JLabel("Weight");
		lblWight.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblWight.setBounds(68, 296, 90, 32);
		addWorkout.add(lblWight);
		
		spinnerReps = new JSpinner();
		spinnerReps.setFont(new Font("Tahoma", Font.PLAIN, 14));
		spinnerReps.setBounds(168, 210, 167, 28);
		addWorkout.add(spinnerReps);
		
		spinnerWeight = new JSpinner();
		spinnerWeight.setFont(new Font("Tahoma", Font.PLAIN, 14));
		spinnerWeight.setBounds(168, 296, 167, 28);
		addWorkout.add(spinnerWeight);
		
		JLabel lblDuration = new JLabel("Duration(min)");
		lblDuration.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDuration.setBounds(68, 253, 90, 32);
		addWorkout.add(lblDuration);
		
		JSpinner spinnerDuration = new JSpinner();
		spinnerDuration.setFont(new Font("Tahoma", Font.PLAIN, 14));
		spinnerDuration.setBounds(168, 257, 167, 28);
		addWorkout.add(spinnerDuration);
		
		JSpinner spinnerDistane = new JSpinner();
		spinnerDistane.setFont(new Font("Tahoma", Font.PLAIN, 14));
		spinnerDistane.setBounds(168, 171, 167, 28);
		addWorkout.add(spinnerDistane);
		
		JLabel lblDistance = new JLabel("Distance(m)");
		lblDistance.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDistance.setBounds(68, 167, 90, 32);
		addWorkout.add(lblDistance);
		
		//--------------------------------------------------------
		lblReps.setVisible(false);
		lblWight.setVisible(false);
		lblDuration.setVisible(true);
		spinnerReps.setVisible(false); spinnerReps.setValue(0);
		spinnerWeight.setVisible(false); spinnerWeight.setValue(0);
		spinnerDuration.setVisible(true);
		lblDistance.setVisible(false);
		spinnerDistane.setVisible(false); spinnerDistane.setValue(0);
		//---------------------------------------------------------
		
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				spinnerDistane.setValue(0);
				spinnerWeight.setValue(0);
				spinnerReps.setValue(0);
				spinnerDuration.setValue(0);
			}
		});
		btnClear.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnClear.setBounds(10, 373, 89, 23);
		addWorkout.add(btnClear);

		JComboBox comboBoxWorkout = new JComboBox(new String[] {"Full Body", "Upper Body", "Lower Body","Running", "Walking", "Swimming", "Bench Press", "Back Squat", "Deadlifting"});
		comboBoxWorkout.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				String selected = (String) comboBoxWorkout.getSelectedItem();
				lblReps.setVisible(false);
				lblWight.setVisible(false);
				lblDuration.setVisible(false);
				spinnerReps.setVisible(false); spinnerReps.setValue(0);
				spinnerWeight.setVisible(false); spinnerWeight.setValue(0);
				spinnerDuration.setVisible(false);
				lblDistance.setVisible(false);
				spinnerDistane.setVisible(false); spinnerDistane.setValue(0);
				if(selected.endsWith("Body")) {
					spinnerDuration.setVisible(true);
					lblDuration.setVisible(true);
				}
				else if(selected.endsWith("ing")&&!selected.equals("Deadlifting")) {
					lblDistance.setVisible(true);
					spinnerDistane.setVisible(true);
					spinnerDuration.setVisible(true);
					lblDuration.setVisible(true);			
				}
				else {
					lblReps.setVisible(true);
					lblWight.setVisible(true);
					spinnerReps.setVisible(true);
					spinnerWeight.setVisible(true);
				}
			}
		});
		comboBoxWorkout.setBounds(168, 131, 167, 22);
		addWorkout.add(comboBoxWorkout);
		
		JButton btnAddInfo = new JButton("Add Info");
		btnAddInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selected = (String) comboBoxWorkout.getSelectedItem();
					if(selected.endsWith("Body")) {
						int dur = (int) spinnerDuration.getValue();
						saveToFile("inAppWorkouts.txt",currentUser+"#"+selected+"#"+dur);
					}
					else if(selected.endsWith("ing")) {
						int dist = (int) spinnerDistane.getValue();
						int dur = (int) spinnerDuration.getValue();
						saveToFile("cardioWorkouts.txt",currentUser+"#"+selected+"#"+dur+"#"+dist);
					}
					else {
						int reps = (int) spinnerReps.getValue();
						int wgt = (int) spinnerWeight.getValue();
						saveToFile("weightTrainingWorkouts.txt",currentUser+"#"+selected+"#"+reps+"#"+wgt);
					}
			}
		});
		btnAddInfo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnAddInfo.setBounds(335, 373, 89, 23);
		addWorkout.add(btnAddInfo);
		
		JPanel myInfo = new JPanel();
		frame.getContentPane().add(myInfo, "myInfo");
		myInfo.setLayout(null);
		
		JButton btnNewButton_1 = new JButton("<- Back");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cards.show(frame.getContentPane(), (String) cardStack.pop());
				cardStack.push("myInfo");
			}
		});
		btnNewButton_1.setBounds(335, 11, 89, 23);
		myInfo.add(btnNewButton_1);
		
		JLabel lblMyInfo = new JLabel("My Info");
		lblMyInfo.setHorizontalAlignment(SwingConstants.CENTER);
		lblMyInfo.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblMyInfo.setBounds(10, 45, 414, 32);
		myInfo.add(lblMyInfo);
		
		JSpinner spinnerWeight_1 = new JSpinner();
		spinnerWeight_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		spinnerWeight_1.setBounds(157, 88, 167, 28);
		myInfo.add(spinnerWeight_1);
		
		JLabel lblWeightkg = new JLabel("Weight(kg)");
		lblWeightkg.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblWeightkg.setBounds(57, 88, 90, 32);
		myInfo.add(lblWeightkg);
		
		JSpinner spinnerWeight_1_1 = new JSpinner();
		spinnerWeight_1_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		spinnerWeight_1_1.setBounds(157, 131, 167, 28);
		myInfo.add(spinnerWeight_1_1);
		
		JLabel lblWight_1_1 = new JLabel("Height(m)");
		lblWight_1_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblWight_1_1.setBounds(57, 131, 90, 32);
		myInfo.add(lblWight_1_1);
		
		JSpinner spinnerWeight_1_2 = new JSpinner();
		spinnerWeight_1_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		spinnerWeight_1_2.setBounds(240, 170, 84, 28);
		myInfo.add(spinnerWeight_1_2);
		
		JLabel lblWaterIntake = new JLabel("Water Intake(Glasses)");
		lblWaterIntake.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblWaterIntake.setBounds(57, 170, 173, 32);
		myInfo.add(lblWaterIntake);
		
		JLabel lblBedTimeRoutine = new JLabel("Bedtime Routine");
		lblBedTimeRoutine.setHorizontalAlignment(SwingConstants.CENTER);
		lblBedTimeRoutine.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblBedTimeRoutine.setBounds(57, 213, 267, 32);
		myInfo.add(lblBedTimeRoutine);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(57, 209, 267, 2);
		myInfo.add(separator);
		
		JLabel lblGoToBed = new JLabel("Go to Bed");
		lblGoToBed.setHorizontalAlignment(SwingConstants.CENTER);
		lblGoToBed.setBounds(57, 256, 84, 14);
		myInfo.add(lblGoToBed);
		
		JTextField textFieldGoToBed = new JTextField();
		textFieldGoToBed.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textFieldGoToBed.setBounds(57, 270, 84, 28);
		myInfo.add(textFieldGoToBed);
		
		JLabel lblWakeUp = new JLabel("Go to Bed");
		lblWakeUp.setHorizontalAlignment(SwingConstants.CENTER);
		lblWakeUp.setBounds(240, 256, 84, 14);
		myInfo.add(lblWakeUp);
		
		textFieldWakeUp = new JTextField();
		textFieldWakeUp.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textFieldWakeUp.setBounds(240, 270, 84, 28);
		myInfo.add(textFieldWakeUp);
		
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(57, 309, 267, 2);
		myInfo.add(separator_1);
		
		JLabel lblDailyStepTarget = new JLabel("Daily Step Target");
		lblDailyStepTarget.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblDailyStepTarget.setBounds(57, 322, 173, 32);
		myInfo.add(lblDailyStepTarget);
		
		JSpinner spinnerStepTarget = new JSpinner();
		spinnerStepTarget.setFont(new Font("Tahoma", Font.PLAIN, 14));
		spinnerStepTarget.setBounds(240, 326, 84, 28);
		myInfo.add(spinnerStepTarget);
		
		JLabel lblTargetAchieved = new JLabel("Target Achieved");
		lblTargetAchieved.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTargetAchieved.setBounds(57, 365, 173, 32);
		myInfo.add(lblTargetAchieved);
		
		JLabel lblTargetMessage = new JLabel("Target Message");
		lblTargetMessage.setHorizontalAlignment(SwingConstants.CENTER);
		lblTargetMessage.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblTargetMessage.setBounds(10, 408, 414, 32);
		lblTargetMessage.setVisible(false);
		myInfo.add(lblTargetMessage);
		
		JButton btnDone = new JButton("");
		btnDone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblTargetMessage.setForeground(Color.green);
				lblTargetMessage.setText("Well Done! Keep it going");
			}
		});
		btnDone.setIcon(new ImageIcon("D:\\Softwares\\Eclipse\\workspace\\WorkoutPlan\\src\\tick.png"));
		btnDone.setBounds(250, 365, 33, 23);
		myInfo.add(btnDone);
		
		JButton btnNotDone = new JButton("");
		btnNotDone.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lblTargetMessage.setForeground(Color.orange);
				lblTargetMessage.setText("Don’t give up! Try again Tomorrow.");
			}
		});
		btnNotDone.setIcon(new ImageIcon("D:\\Softwares\\Eclipse\\workspace\\WorkoutPlan\\src\\cross.png"));
		btnNotDone.setBounds(291, 365, 33, 23);
		myInfo.add(btnNotDone);
		
		JPanel myWorkouts = new JPanel();
		frame.getContentPane().add(myWorkouts, "myWorkouts");
		myWorkouts.setLayout(null);
		
		JLabel lblMyWorkouts = new JLabel("My Workouts");
		lblMyWorkouts.setHorizontalAlignment(SwingConstants.CENTER);
		lblMyWorkouts.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblMyWorkouts.setBounds(10, 45, 414, 32);
		myWorkouts.add(lblMyWorkouts);
		
		JButton btnBack = new JButton("<- Back");
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cards.show(frame.getContentPane(), (String) cardStack.pop());
				cardStack.push("myWorkouts");
			}
		});
		btnBack.setBounds(335, 11, 89, 23);
		myWorkouts.add(btnBack);
		
		JLabel lblCardioWorkouts = new JLabel("Cardio Workouts");
		lblCardioWorkouts.setHorizontalAlignment(SwingConstants.CENTER);
		lblCardioWorkouts.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCardioWorkouts.setBounds(10, 88, 414, 32);
		myWorkouts.add(lblCardioWorkouts);
		
		JScrollPane scrollPaneCardioWorkouts = new JScrollPane();
		scrollPaneCardioWorkouts.setBounds(10, 131, 414, 65);
		myWorkouts.add(scrollPaneCardioWorkouts);
		
		tableCardioWorkouts = new JTable();
		tableCardioWorkouts.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Workout", "Distance", "Duration"
			}
		));
		scrollPaneCardioWorkouts.setViewportView(tableCardioWorkouts);
		
		JScrollPane scrollPaneWeightTrainingWorkouts = new JScrollPane();
		scrollPaneWeightTrainingWorkouts.setBounds(10, 250, 414, 65);
		myWorkouts.add(scrollPaneWeightTrainingWorkouts);
		
		tableWeightTrainingWorkouts = new JTable();
		tableWeightTrainingWorkouts.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Workout", "Reps", "Weight"
			}
		));
		scrollPaneWeightTrainingWorkouts.setViewportView(tableWeightTrainingWorkouts);
		
		JLabel lblWeightTrainingWorkouts = new JLabel("Weight Training Workouts");
		lblWeightTrainingWorkouts.setHorizontalAlignment(SwingConstants.CENTER);
		lblWeightTrainingWorkouts.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblWeightTrainingWorkouts.setBounds(10, 207, 414, 32);
		myWorkouts.add(lblWeightTrainingWorkouts);
		
		JLabel lblInappWorkouts = new JLabel("In-App Workouts");
		lblInappWorkouts.setHorizontalAlignment(SwingConstants.CENTER);
		lblInappWorkouts.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblInappWorkouts.setBounds(10, 321, 414, 32);
		myWorkouts.add(lblInappWorkouts);
		
		JScrollPane scrollPaneInappWorkouts = new JScrollPane();
		scrollPaneInappWorkouts.setBounds(10, 364, 414, 86);
		myWorkouts.add(scrollPaneInappWorkouts);
		
		tableInappWorkouts = new JTable();
		tableInappWorkouts.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Workout", "Duration"
			}
		));
		scrollPaneInappWorkouts.setViewportView(tableInappWorkouts);
		
		JPanel othersWorkouts = new JPanel();
		frame.getContentPane().add(othersWorkouts, "othersWorkouts");
		othersWorkouts.setLayout(null);
		
		JButton btnBack_1 = new JButton("<- Back");
		btnBack_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cards.show(frame.getContentPane(), (String) cardStack.pop());
				cardStack.push("othersWorkouts");
			}
		});
		btnBack_1.setBounds(335, 11, 89, 23);
		othersWorkouts.add(btnBack_1);
		
		JLabel lblOthersWorkouts = new JLabel("Workouts done by Others");
		lblOthersWorkouts.setHorizontalAlignment(SwingConstants.CENTER);
		lblOthersWorkouts.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblOthersWorkouts.setBounds(10, 45, 414, 32);
		othersWorkouts.add(lblOthersWorkouts);
		
		JLabel lblCardioWorkouts_1 = new JLabel("Cardio Workouts");
		lblCardioWorkouts_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblCardioWorkouts_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCardioWorkouts_1.setBounds(10, 88, 414, 32);
		othersWorkouts.add(lblCardioWorkouts_1);
		
		JScrollPane scrollPaneCardioWorkouts_1 = new JScrollPane();
		scrollPaneCardioWorkouts_1.setBounds(10, 131, 414, 138);
		othersWorkouts.add(scrollPaneCardioWorkouts_1);
		
		tableCardioWorkouts_1 = new JTable();
		tableCardioWorkouts_1.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Name", "Workout", "Distance", "Duration"
			}
		));
		scrollPaneCardioWorkouts_1.setViewportView(tableCardioWorkouts_1);
		
		JLabel lblWeightTrainingWorkouts_1 = new JLabel("Weight Training Workouts");
		lblWeightTrainingWorkouts_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblWeightTrainingWorkouts_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblWeightTrainingWorkouts_1.setBounds(10, 280, 414, 32);
		othersWorkouts.add(lblWeightTrainingWorkouts_1);
		
		JScrollPane scrollPaneWeightTrainingWorkouts_1 = new JScrollPane();
		scrollPaneWeightTrainingWorkouts_1.setBounds(10, 323, 414, 127);
		othersWorkouts.add(scrollPaneWeightTrainingWorkouts_1);
		
		tableWeightTrainingWorkouts_1 = new JTable();
		tableWeightTrainingWorkouts_1.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Name", "Workout", "Reps", "Weight"
			}
		));
		scrollPaneWeightTrainingWorkouts_1.setViewportView(tableWeightTrainingWorkouts_1);
		
		JPanel startWorkout = new JPanel();
		frame.getContentPane().add(startWorkout, "startWorkout");
		startWorkout.setLayout(null);
		
		JLabel lblStartWorkout = new JLabel("Start Workout");
		lblStartWorkout.setHorizontalAlignment(SwingConstants.CENTER);
		lblStartWorkout.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblStartWorkout.setBounds(10, 45, 414, 32);
		startWorkout.add(lblStartWorkout);
		
		JLabel lblWorkout_1 = new JLabel("Workout");
		lblWorkout_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblWorkout_1.setBounds(73, 88, 90, 32);
		startWorkout.add(lblWorkout_1);
		
		JComboBox comboBoxWorkout_1 = new JComboBox(new String[] {"Full Body", "Upper Body", "Lower Body"});
		comboBoxWorkout_1.setBounds(173, 88, 167, 29);
		startWorkout.add(comboBoxWorkout_1);
		
		JLabel lblExerciseName = new JLabel("Exercise-Name");
		lblExerciseName.setHorizontalAlignment(SwingConstants.CENTER);
		lblExerciseName.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblExerciseName.setBounds(10, 131, 414, 32);
		startWorkout.add(lblExerciseName);
		
		JButton btnFilterMenu = new JButton("Filter Menu");
		btnFilterMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cards.show(frame.getContentPane(), "filterMenu");
				cardStack.push("startWorkout");
				if(!selectedFullBodyExercises.isEmpty()) selectedFullBodyExercises.clear();
				if(!selectedLowerBodyExercises.isEmpty()) selectedLowerBodyExercises.clear();
				if(!selectedUpperBodyExercises.isEmpty()) selectedUpperBodyExercises.clear();
				timeCounter = 0; 
				if(timer.isRunning()) timer.stop();
			}
		});
		btnFilterMenu.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnFilterMenu.setBounds(125, 329, 196, 25);
		startWorkout.add(btnFilterMenu);
		
		JLabel lblTimer_1 = new JLabel("Timer");
		lblTimer_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblTimer_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblTimer_1.setBounds(10, 174, 414, 32);
		startWorkout.add(lblTimer_1);
		
		JLabel lblTime = new JLabel("0 0 : 0 0");
		lblTime.setHorizontalAlignment(SwingConstants.CENTER);
		lblTime.setFont(new Font("Cambria Math", Font.BOLD, 50));
		lblTime.setBounds(10, 217, 414, 101);
		startWorkout.add(lblTime);
		
		JPanel filterMenu = new JPanel();
		frame.getContentPane().add(filterMenu, "filterMenu");
		filterMenu.setLayout(null);
		
		JButton btnNewButton_2_1 = new JButton("<- Back");
		btnNewButton_2_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cards.show(frame.getContentPane(), (String)cardStack.pop());
			}
		});
		btnNewButton_2_1.setBounds(335, 11, 89, 23);
		filterMenu.add(btnNewButton_2_1);
		
		JLabel lblFilterMenu = new JLabel("Filter Menu");
		lblFilterMenu.setHorizontalAlignment(SwingConstants.CENTER);
		lblFilterMenu.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblFilterMenu.setBounds(10, 45, 414, 32);
		filterMenu.add(lblFilterMenu);
		
		CardLayout exerciseCards = new CardLayout(0, 0);
		JPanel exercises = new JPanel();
		exercises.setBounds(10, 114, 414, 223);
		filterMenu.add(exercises);
		exercises.setLayout(exerciseCards);
		
		JPanel fullBodyExercieses = new JPanel();
		exercises.add(fullBodyExercieses, "fullBodyExercises");
		fullBodyExercieses.setLayout(null);
		
		JCheckBox chckbxNewCheckBox_1 = new JCheckBox("Jogging in Place");
		chckbxNewCheckBox_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		chckbxNewCheckBox_1.setBounds(6, 7, 152, 23);
		fullBodyExercieses.add(chckbxNewCheckBox_1);
		
		JCheckBox chckbxJumpingJacks_1 = new JCheckBox("Jumping Jacks");
		chckbxJumpingJacks_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		chckbxJumpingJacks_1.setBounds(6, 33, 152, 23);
		fullBodyExercieses.add(chckbxJumpingJacks_1);
		
		JCheckBox chckbxMountainClimbers_1 = new JCheckBox("Mountain Climbers");
		chckbxMountainClimbers_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		chckbxMountainClimbers_1.setBounds(6, 111, 152, 23);
		fullBodyExercieses.add(chckbxMountainClimbers_1);
		
		JCheckBox chckbxSquatJacks_1 = new JCheckBox("Squat Jacks");
		chckbxSquatJacks_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		chckbxSquatJacks_1.setBounds(6, 59, 152, 23);
		fullBodyExercieses.add(chckbxSquatJacks_1);
		
		JCheckBox chckbxJumpRope_1 = new JCheckBox("Jump Rope");
		chckbxJumpRope_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		chckbxJumpRope_1.setBounds(6, 85, 152, 23);
		fullBodyExercieses.add(chckbxJumpRope_1);
		
		JCheckBox chckbxLunges_1 = new JCheckBox("Lunges");
		chckbxLunges_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		chckbxLunges_1.setBounds(160, 59, 85, 23);
		fullBodyExercieses.add(chckbxLunges_1);
		
		JCheckBox chckbxStairClimb_1 = new JCheckBox("Stair Climb");
		chckbxStairClimb_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		chckbxStairClimb_1.setBounds(256, 7, 152, 23);
		fullBodyExercieses.add(chckbxStairClimb_1);
		
		JCheckBox chckbxBurpees_1 = new JCheckBox("Burpees");
		chckbxBurpees_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		chckbxBurpees_1.setBounds(160, 85, 85, 23);
		fullBodyExercieses.add(chckbxBurpees_1);
		
		JCheckBox chckbxWallSits_1 = new JCheckBox("Wall Sits");
		chckbxWallSits_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		chckbxWallSits_1.setBounds(256, 85, 152, 23);
		fullBodyExercieses.add(chckbxWallSits_1);
		
		JCheckBox chckbxPressUp_1 = new JCheckBox("Press up");
		chckbxPressUp_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		chckbxPressUp_1.setBounds(6, 189, 152, 23);
		fullBodyExercieses.add(chckbxPressUp_1);
		
		JCheckBox chckbxPullUp_1 = new JCheckBox("Pull up");
		chckbxPullUp_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		chckbxPullUp_1.setBounds(256, 163, 152, 23);
		fullBodyExercieses.add(chckbxPullUp_1);
		
		JCheckBox chckbxPlank_1 = new JCheckBox("Plank");
		chckbxPlank_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		chckbxPlank_1.setBounds(256, 189, 152, 23);
		fullBodyExercieses.add(chckbxPlank_1);
		
		JCheckBox chckbxDips_1 = new JCheckBox("Dips");
		chckbxDips_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		chckbxDips_1.setBounds(160, 33, 62, 23);
		fullBodyExercieses.add(chckbxDips_1);
		
		JCheckBox chckbxBox_1 = new JCheckBox("Box");
		chckbxBox_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		chckbxBox_1.setBounds(160, 7, 62, 23);
		fullBodyExercieses.add(chckbxBox_1);
		
		JCheckBox chckbxUpwardFacingPlank_1 = new JCheckBox("Upward facing Plank");
		chckbxUpwardFacingPlank_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		chckbxUpwardFacingPlank_1.setBounds(256, 59, 152, 23);
		fullBodyExercieses.add(chckbxUpwardFacingPlank_1);
		
		JCheckBox chckbxRollingSidePlank_1 = new JCheckBox("Rolling Side Plank");
		chckbxRollingSidePlank_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		chckbxRollingSidePlank_1.setBounds(6, 137, 152, 23);
		fullBodyExercieses.add(chckbxRollingSidePlank_1);
		
		JCheckBox chckbxTricepDips_1 = new JCheckBox("Tricep Dips");
		chckbxTricepDips_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		chckbxTricepDips_1.setBounds(6, 163, 152, 23);
		fullBodyExercieses.add(chckbxTricepDips_1);
		
		JCheckBox chckbxArmCirclesReverse_1 = new JCheckBox("Arm Circles Reverse");
		chckbxArmCirclesReverse_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		chckbxArmCirclesReverse_1.setBounds(256, 137, 152, 23);
		fullBodyExercieses.add(chckbxArmCirclesReverse_1);
		
		JCheckBox chckbxSidePushUp_1 = new JCheckBox("Side push up");
		chckbxSidePushUp_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		chckbxSidePushUp_1.setBounds(256, 33, 152, 23);
		fullBodyExercieses.add(chckbxSidePushUp_1);
		
		JCheckBox chckbxArmCircles_1 = new JCheckBox("Arm Circles");
		chckbxArmCircles_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		chckbxArmCircles_1.setBounds(256, 111, 152, 23);
		fullBodyExercieses.add(chckbxArmCircles_1);
		
		JPanel lowerBodyExercises = new JPanel();
		exercises.add(lowerBodyExercises, "lowerBodyExercises");
		lowerBodyExercises.setLayout(null);
		
		JCheckBox chckbxNewCheckBox = new JCheckBox("Jogging in Place");
		chckbxNewCheckBox.setFont(new Font("Tahoma", Font.PLAIN, 14));
		chckbxNewCheckBox.setBounds(6, 7, 152, 23);
		lowerBodyExercises.add(chckbxNewCheckBox);
		
		JCheckBox chckbxJumpingJacks = new JCheckBox("Jumping Jacks");
		chckbxJumpingJacks.setFont(new Font("Tahoma", Font.PLAIN, 14));
		chckbxJumpingJacks.setBounds(6, 45, 152, 23);
		lowerBodyExercises.add(chckbxJumpingJacks);
		
		JCheckBox chckbxSquatJacks = new JCheckBox("Squat Jacks");
		chckbxSquatJacks.setFont(new Font("Tahoma", Font.PLAIN, 14));
		chckbxSquatJacks.setBounds(6, 82, 152, 23);
		lowerBodyExercises.add(chckbxSquatJacks);
		
		JCheckBox chckbxJumpRope = new JCheckBox("Jump Rope");
		chckbxJumpRope.setFont(new Font("Tahoma", Font.PLAIN, 14));
		chckbxJumpRope.setBounds(6, 122, 152, 23);
		lowerBodyExercises.add(chckbxJumpRope);
		
		JCheckBox chckbxLunges = new JCheckBox("Lunges");
		chckbxLunges.setFont(new Font("Tahoma", Font.PLAIN, 14));
		chckbxLunges.setBounds(6, 158, 152, 23);
		lowerBodyExercises.add(chckbxLunges);
		
		JCheckBox chckbxWallSits = new JCheckBox("Wall Sits");
		chckbxWallSits.setFont(new Font("Tahoma", Font.PLAIN, 14));
		chckbxWallSits.setBounds(256, 124, 152, 23);
		lowerBodyExercises.add(chckbxWallSits);
		
		JCheckBox chckbxBurpees = new JCheckBox("Burpees");
		chckbxBurpees.setFont(new Font("Tahoma", Font.PLAIN, 14));
		chckbxBurpees.setBounds(256, 84, 152, 23);
		lowerBodyExercises.add(chckbxBurpees);
		
		JCheckBox chckbxMountainClimbers = new JCheckBox("Mountain Climbers");
		chckbxMountainClimbers.setFont(new Font("Tahoma", Font.PLAIN, 14));
		chckbxMountainClimbers.setBounds(256, 47, 152, 23);
		lowerBodyExercises.add(chckbxMountainClimbers);
		
		JCheckBox chckbxStairClimb = new JCheckBox("Stair Climb");
		chckbxStairClimb.setFont(new Font("Tahoma", Font.PLAIN, 14));
		chckbxStairClimb.setBounds(256, 7, 152, 23);
		lowerBodyExercises.add(chckbxStairClimb);
		
		JPanel upperBodyExercises = new JPanel();
		exercises.add(upperBodyExercises, "upperBodyExercises");
		upperBodyExercises.setLayout(null);
		
		JCheckBox chckbxPressUp = new JCheckBox("Press up");
		chckbxPressUp.setFont(new Font("Tahoma", Font.PLAIN, 14));
		chckbxPressUp.setBounds(6, 7, 152, 23);
		upperBodyExercises.add(chckbxPressUp);
		
		JCheckBox chckbxPullUp = new JCheckBox("Pull up");
		chckbxPullUp.setFont(new Font("Tahoma", Font.PLAIN, 14));
		chckbxPullUp.setBounds(6, 43, 152, 23);
		upperBodyExercises.add(chckbxPullUp);
		
		JCheckBox chckbxPlank = new JCheckBox("Plank");
		chckbxPlank.setFont(new Font("Tahoma", Font.PLAIN, 14));
		chckbxPlank.setBounds(6, 77, 152, 23);
		upperBodyExercises.add(chckbxPlank);
		
		JCheckBox chckbxUpwardFacingPlank = new JCheckBox("Upward facing Plank");
		chckbxUpwardFacingPlank.setFont(new Font("Tahoma", Font.PLAIN, 14));
		chckbxUpwardFacingPlank.setBounds(6, 103, 152, 23);
		upperBodyExercises.add(chckbxUpwardFacingPlank);
		
		JCheckBox chckbxRollingSidePlank = new JCheckBox("Rolling Side Plank");
		chckbxRollingSidePlank.setFont(new Font("Tahoma", Font.PLAIN, 14));
		chckbxRollingSidePlank.setBounds(6, 139, 152, 23);
		upperBodyExercises.add(chckbxRollingSidePlank);
		
		JCheckBox chckbxBox = new JCheckBox("Box");
		chckbxBox.setFont(new Font("Tahoma", Font.PLAIN, 14));
		chckbxBox.setBounds(256, 7, 152, 23);
		upperBodyExercises.add(chckbxBox);
		
		JCheckBox chckbxDips = new JCheckBox("Dips");
		chckbxDips.setFont(new Font("Tahoma", Font.PLAIN, 14));
		chckbxDips.setBounds(256, 45, 152, 23);
		upperBodyExercises.add(chckbxDips);
		
		JCheckBox chckbxSidePushUp = new JCheckBox("Side push up");
		chckbxSidePushUp.setFont(new Font("Tahoma", Font.PLAIN, 14));
		chckbxSidePushUp.setBounds(256, 77, 152, 23);
		upperBodyExercises.add(chckbxSidePushUp);
		
		JCheckBox chckbxArmCircles = new JCheckBox("Arm Circles");
		chckbxArmCircles.setFont(new Font("Tahoma", Font.PLAIN, 14));
		chckbxArmCircles.setBounds(256, 105, 152, 23);
		upperBodyExercises.add(chckbxArmCircles);
		
		JCheckBox chckbxArmCirclesReverse = new JCheckBox("Arm Circles Reverse");
		chckbxArmCirclesReverse.setFont(new Font("Tahoma", Font.PLAIN, 14));
		chckbxArmCirclesReverse.setBounds(256, 141, 152, 23);
		upperBodyExercises.add(chckbxArmCirclesReverse);
		
		JCheckBox chckbxTricepDips = new JCheckBox("Tricep Dips");
		chckbxTricepDips.setFont(new Font("Tahoma", Font.PLAIN, 14));
		chckbxTricepDips.setBounds(6, 173, 152, 23);
		upperBodyExercises.add(chckbxTricepDips);
		
		JRadioButton rdbtnFullBody = new JRadioButton("Full Body");
		rdbtnFullBody.setSelected(true);
		rdbtnFullBody.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				exerciseCards.show(exercises, "fullBodyExercises");
			}
		});
		buttonGroup.add(rdbtnFullBody);
		rdbtnFullBody.setBounds(10, 84, 109, 23);
		filterMenu.add(rdbtnFullBody);
		
		JRadioButton rdbtnLowerBody = new JRadioButton("Lower Body");
		rdbtnLowerBody.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				exerciseCards.show(exercises, "lowerBodyExercises");
			}
		});
		buttonGroup.add(rdbtnLowerBody);
		rdbtnLowerBody.setBounds(121, 84, 109, 23);
		filterMenu.add(rdbtnLowerBody);
		
		JRadioButton rdbtnUpperBody = new JRadioButton("Upper Body");
		rdbtnUpperBody.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				exerciseCards.show(exercises, "upperBodyExercises");
			}
		});
		buttonGroup.add(rdbtnUpperBody);
		rdbtnUpperBody.setBounds(232, 84, 109, 23);
		filterMenu.add(rdbtnUpperBody);
		
		//---------------------------------------------------------------------------
		JCheckBox[] lowerBodyExercisesBoxes = {chckbxJumpingJacks, chckbxNewCheckBox, chckbxSquatJacks, chckbxJumpRope, chckbxLunges, chckbxWallSits, chckbxBurpees, chckbxMountainClimbers, chckbxStairClimb};
		JCheckBox[] upperBodyExercisesBoxes = {chckbxPressUp, chckbxPullUp, chckbxPlank, chckbxUpwardFacingPlank, chckbxRollingSidePlank, chckbxBox, chckbxDips, chckbxSidePushUp, chckbxArmCircles, chckbxArmCirclesReverse, chckbxTricepDips };
		JCheckBox[] fullBodyExercisesBoxes = 	{chckbxJumpingJacks_1, chckbxNewCheckBox_1, chckbxSquatJacks_1, chckbxJumpRope_1, chckbxLunges_1, chckbxWallSits_1, chckbxBurpees_1, chckbxMountainClimbers_1, chckbxStairClimb_1,
												 chckbxPressUp_1, chckbxPullUp_1, chckbxPlank_1, chckbxUpwardFacingPlank_1, chckbxRollingSidePlank_1, chckbxBox_1, chckbxDips_1, chckbxSidePushUp_1, chckbxArmCircles_1, chckbxArmCirclesReverse_1, chckbxTricepDips_1};
		//---------------------------------------------------------------------------
		
		JButton btnComplete = new JButton("Complete");
		btnComplete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(rdbtnFullBody.isSelected()) {
					for(JCheckBox b : fullBodyExercisesBoxes) {
						if(b.isSelected()) selectedFullBodyExercises.add(b.getText());
					}
				}
				else if(rdbtnLowerBody.isSelected()) {
					for(JCheckBox b : lowerBodyExercisesBoxes) {
						if(b.isSelected()) selectedLowerBodyExercises.add(b.getText());
					}
				}
				else if(rdbtnUpperBody.isSelected()) {
					for(JCheckBox b : upperBodyExercisesBoxes) {
						if(b.isSelected()) selectedUpperBodyExercises.add(b.getText());
					}
				}
				cards.show(frame.getContentPane(), (String)cardStack.pop());
				for(int i=0; i<14; i++) {
					if(selectedFullBodyExercises.size()>0) selectedFullBodyExercises.add(selectedFullBodyExercises.get(i));
					if(selectedUpperBodyExercises.size()>0) selectedUpperBodyExercises.add(selectedUpperBodyExercises.get(i));
					if(selectedLowerBodyExercises.size()>0) selectedLowerBodyExercises.add(selectedLowerBodyExercises.get(i));
				}
			}
		});
		btnComplete.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnComplete.setBounds(121, 355, 196, 25);
		filterMenu.add(btnComplete);
		
		//--------------------------------------------------------------------------------
		//Timer timer = null; 
		//System.out.println(selectedItem+": Selected Item");
		timer = new Timer(300, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String hrs = timeCounter/60+"";
				String mins = timeCounter%60+"";
				lblTime.setText(hrs+" : "+mins);
				String selectedItem = (String) comboBoxWorkout_1.getSelectedItem();
				if(timeCounter/60==15 && timer!=null) timer.stop();
				if(selectedItem == "Full Body" && selectedFullBodyExercises.size()>0) {
					lblExerciseName.setText((String) selectedFullBodyExercises.get(selectedFullBodyExercises.size() - timeCounter/60-1));
				}
				else if(selectedItem == "Upper Body" && selectedUpperBodyExercises.size()>0) {
					lblExerciseName.setText((String) selectedUpperBodyExercises.get(selectedUpperBodyExercises.size() - timeCounter/60-1));					
				}
				else if(selectedItem == "Lower Body" && selectedLowerBodyExercises.size()>0) {
					int temp = selectedLowerBodyExercises.size() - timeCounter/60-1;
					lblExerciseName.setText((String) selectedLowerBodyExercises.get(temp));					
				}	
				timeCounter++;
			}
		});
		//--------------------------------------------------------------------------------
		
		//---------------------------
		JButton btnPause = new JButton("Pause");
		btnPause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(timer.isRunning()) timer.stop();
			}
		});
		btnPause.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnPause.setBounds(125, 402, 196, 25);
		startWorkout.add(btnPause);
				
		JButton btnStart = new JButton("Start");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String selectedItem = (String) comboBoxWorkout_1.getSelectedItem();
				if(selectedItem == "Full Body" && selectedFullBodyExercises.size()==0) {
					JOptionPane.showMessageDialog(frame, "Please select exercises in "+selectedItem);
					return;
				}
				else if(selectedItem == "Upper Body" && selectedUpperBodyExercises.size()==0) {
					JOptionPane.showMessageDialog(frame, "Please select exercises in "+selectedItem);
					return;				
				}
				else if(selectedItem == "Lower Body" && selectedLowerBodyExercises.size()==0) {
					JOptionPane.showMessageDialog(frame, "Please select exercises in "+selectedItem);
					return;				
				}	
				if(!timer.isRunning()) timer.start();
				else timer.restart();
			}
		});
		btnStart.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnStart.setBounds(125, 365, 196, 25);
		startWorkout.add(btnStart);
		
		JButton btnNewButton_2 = new JButton("<- Back");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cards.show(frame.getContentPane(), (String) cardStack.pop());
				timeCounter = 0; timer.stop();
				selectedFullBodyExercises.clear();
				selectedLowerBodyExercises.clear();
				selectedUpperBodyExercises.clear();
				lblExerciseName.setText("Exercise-Name");
			}
		});
		btnNewButton_2.setBounds(335, 11, 89, 23);
		startWorkout.add(btnNewButton_2);
		//-------------------------------------
	}
	private void saveToFile(String file, String str) {
		System.out.println("Writing to file "+file+"\t"+str);
		File f = new File(file);
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(f, true));
			bw.write("\n"+str);
			bw.close();
			JOptionPane.showMessageDialog(this.frame,"Operation Successful");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private Stack<String[]> loadFromFile(String file) {
		Stack<String[]> data = new Stack<String[]>();
		Scanner reader;
		try {
			reader = new Scanner(new File(file));
			while(reader.hasNextLine())
				data.push(reader.nextLine().split("#"));
			return data;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	private Stack<String[]> loadFromFile(String file, String user) {
		Stack<String[]> data = new Stack<String[]>();
		Scanner reader;
		try {
			reader = new Scanner(new File(file));
			while(reader.hasNextLine()) {
				String line = reader.nextLine();
				if(line.startsWith(user)) {
					data.push(line.split("#"));
				}
			}
			return data;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	private void populateTables() {
		DefaultTableModel modelInApp = (DefaultTableModel) tableInappWorkouts.getModel(); 
		DefaultTableModel modelCardio = (DefaultTableModel) tableCardioWorkouts.getModel();
		DefaultTableModel modelWeightTraining = (DefaultTableModel) tableWeightTrainingWorkouts.getModel();
		DefaultTableModel modelCardio_1 = (DefaultTableModel) tableCardioWorkouts_1.getModel();
		DefaultTableModel modelWeightTraining_1 = (DefaultTableModel) tableWeightTrainingWorkouts_1.getModel();
		if(modelInApp.getRowCount()==0){
			Stack data = loadFromFile("inAppWorkouts.txt", currentUser);
			while(!data.isEmpty()) {
				String[] row = (String[]) data.pop();
				modelInApp.addRow(new String[] {row[1], row[2]});
			}
		}
		if(modelCardio.getRowCount()==0){
			Stack data = loadFromFile("cardioWorkouts.txt", currentUser);
			while(!data.isEmpty()) {
				String[] row = (String[]) data.pop();
				modelCardio.addRow(new String[] {row[1], row[2], row[3]});
			}
		}
		if(modelWeightTraining.getRowCount()==0){
			Stack data = loadFromFile("weightTrainingWorkouts.txt", currentUser);
			while(!data.isEmpty()) {
				String[] row = (String[]) data.pop();
				modelWeightTraining.addRow(new String[] {row[1], row[2], row[3]});
			}
		}
		if(modelCardio_1.getRowCount()==0){
			Stack data = loadFromFile("cardioWorkouts.txt");
			while(!data.isEmpty()) {
				String[] row = (String[]) data.pop();
				modelCardio_1.addRow(row);
			}
		}
		if(modelWeightTraining_1.getRowCount()==0){
			Stack data = loadFromFile("weightTrainingWorkouts.txt");
			while(!data.isEmpty()) {
				String[] row = (String[]) data.pop();
				modelWeightTraining_1.addRow(row);
			}
		}
		
	}
	private void refresh() {
		this.frame.dispose();
		(new WorkoutPlan()).frame.setVisible(true);
	}
}
