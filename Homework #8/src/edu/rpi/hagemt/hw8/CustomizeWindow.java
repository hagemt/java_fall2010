/**
 * Rensselaer Polytechnic Institute, Fall 2010
 * CSCI-2200 -- Programming in Java (Section 1)
 * Homework #8 -- Project 16.2 -- Controls and Layout Managers
 */
package edu.rpi.hagemt.hw8;

// AWT Imports
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Swing Imports
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

/**
 * GUI class to facilitate the order of a user-customized computer.
 * @author Tor E Hagemann <hagemt@rpi.edu>
 */
public class CustomizeWindow extends javax.swing.JFrame implements javax.swing.SwingConstants {
	// Constants
	private static final long serialVersionUID = 8985577929131536450L;
	private static final double BASE_PRICE = 500D;
	private static final java.text.DecimalFormat PRICE_FORMAT = new java.text.DecimalFormat("$0.00");

	// GUI Field Elements
	private JPanel hardwarePanel, softwarePanel, buttonPanel;
	private GridBagConstraints containerConstraints, hardwareConstraints, softwareConstraints;
	private JComboBox processorSelect, memorySelect, diskSelect;
	private ButtonGroup osSelect;
	private JRadioButton windowsXPHomeEdition, windowsXPProfessional;
	private JCheckBox officePackage, accountingPackage, graphicsPackage;
	private JLabel totalPrice;
	private JButton calculateButton, exitButton;

	/**
	 * Utility class for storing extra data in this application's JComboBoxes.
	 * Basically a pair of product name and cost, identified by the former.
	 */
	private class Item {
		private String id; private double cost;
		public Item(String name, double price) { id = name; cost = price; }
		public double getPrice() { return cost; }
		@Override public String toString() { return id; }
	}

	/**
	 * Constructs a window to calculate pricing for a new computer order.
	 */
	public CustomizeWindow() {
		// Initialize Panels with Layout and Constraints
		hardwarePanel = new JPanel(new GridBagLayout());
		hardwareConstraints = new GridBagConstraints();
		softwarePanel = new JPanel(new GridBagLayout());
		softwareConstraints = new GridBagConstraints();
		buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		containerConstraints = new GridBagConstraints();
		
		// Initialize Drop-down Boxes
		processorSelect = new JComboBox();
		memorySelect = new JComboBox();
		diskSelect = new JComboBox();

		// Initialize Radio Buttons and Button Group
		windowsXPHomeEdition = new JRadioButton("Windows XP Home Edition", true);
		windowsXPProfessional = new JRadioButton("Windows XP Professional", false);
		osSelect = new ButtonGroup();

		// Initialize Check Boxes
		officePackage = new JCheckBox("Office Package");
		accountingPackage = new JCheckBox("Accounting Package");
		graphicsPackage = new JCheckBox("Graphics Package");

		// Initialize Labels (only one ever changes)
		totalPrice = new JLabel("Price: " + PRICE_FORMAT.format(BASE_PRICE));

		// Initialize Buttons
		calculateButton = new JButton("Calculate");
		exitButton = new JButton("Exit");

		// Set Border Style, Padding, and Frame Layout
		hardwarePanel.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED), "Hardware"));
		softwarePanel.setBorder(new TitledBorder(new BevelBorder(BevelBorder.LOWERED), "Software"));
		containerConstraints.insets = hardwareConstraints.insets = softwareConstraints.insets = new Insets(5, 5, 5, 5);
		this.getContentPane().setLayout(new GridBagLayout());

		// Set Selection and Radio Button Options
		processorSelect.addItem(new Item("P4 2.2GHz", 0));
		processorSelect.addItem(new Item("P4 2.4GHz", 50));
		processorSelect.addItem(new Item("P4 2.6GHz", 150));
		memorySelect.addItem(new Item("256MB", 0));
		memorySelect.addItem(new Item("512MB", 50));
		memorySelect.addItem(new Item("1GB", 100));
		memorySelect.addItem(new Item("2GB", 150));
		diskSelect.addItem(new Item("80GB", 0));
		diskSelect.addItem(new Item("120GB", 50));
		diskSelect.addItem(new Item("170GB", 150));
		osSelect.add(windowsXPHomeEdition);
		osSelect.add(windowsXPProfessional);

		// Add Event Listeners to Buttons
		calculateButton.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				// Add up the prices from each of the components
				double price = BASE_PRICE;
				// Add contributions from the drop-down boxes
				price += ((Item)processorSelect.getSelectedItem()).getPrice();
				price += ((Item)memorySelect.getSelectedItem()).getPrice();
				price += ((Item)diskSelect.getSelectedItem()).getPrice();
				// Add contribution from the operating system radio button group
				if (windowsXPProfessional.isSelected()) { price += 100D; }
				// Add contribution from each of the checked optional software packages
				if (officePackage.isSelected()) { price += 400D; }
				if (accountingPackage.isSelected()) { price += 200D; }
				if (graphicsPackage.isSelected()) { price += 600D; }
				// Display the correct price
				totalPrice.setText("Price: " + PRICE_FORMAT.format(price));
			}
		});
		exitButton.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				// Close the Window and release the associated resources when this button is pressed
				CustomizeWindow.this.dispose();
			}
		});

		// Add the Drop-down Boxes to the "Hardware" section
		// Fill the left column with Labels, justified right
		hardwareConstraints.gridx = 0; hardwareConstraints.anchor = GridBagConstraints.EAST;
		hardwareConstraints.gridy = 0; hardwarePanel.add(new JLabel("Processor:"), hardwareConstraints);
		hardwareConstraints.gridy = 1; hardwarePanel.add(new JLabel("Memory:"), hardwareConstraints);
		hardwareConstraints.gridy = 2; hardwarePanel.add(new JLabel("Disk:"), hardwareConstraints);
		// Fill the right column with Drop-down Boxes, justified left
		hardwareConstraints.gridx = 1; hardwareConstraints.anchor = GridBagConstraints.WEST;
		hardwareConstraints.gridy = 0; hardwarePanel.add(processorSelect, hardwareConstraints);
		hardwareConstraints.gridy = 1; hardwarePanel.add(memorySelect, hardwareConstraints);
		hardwareConstraints.gridy = 2; hardwarePanel.add(diskSelect, hardwareConstraints);

		// Add the Radio Buttons and Check Boxes to the "Software" section
		softwareConstraints.gridx = 0; softwareConstraints.anchor = GridBagConstraints.WEST;
		softwareConstraints.gridy = 0; softwarePanel.add(windowsXPHomeEdition, softwareConstraints);
		softwareConstraints.gridy = 1; softwarePanel.add(windowsXPProfessional, softwareConstraints);
		softwareConstraints.gridy = 2; softwarePanel.add(officePackage, softwareConstraints);
		softwareConstraints.gridy = 3; softwarePanel.add(accountingPackage, softwareConstraints);
		softwareConstraints.gridy = 4; softwarePanel.add(graphicsPackage, softwareConstraints);

		// Fill the Button section
		buttonPanel.add(calculateButton);
		buttonPanel.add(exitButton);

		// Add the "Hardware" and "Software" panels toward the top
		containerConstraints.gridy = 0; containerConstraints.anchor = GridBagConstraints.NORTH;
		containerConstraints.gridx = 0; this.getContentPane().add(hardwarePanel, containerConstraints);
		containerConstraints.gridx = 1; this.getContentPane().add(softwarePanel, containerConstraints);

		// Add the price Label and Button panel to the bottom right
		containerConstraints.gridy = 1;	containerConstraints.anchor = GridBagConstraints.EAST;
		containerConstraints.gridx = 0; this.getContentPane().add(totalPrice, containerConstraints);
		containerConstraints.gridx = 1; this.getContentPane().add(buttonPanel, containerConstraints);

		// When the Window is closed, we should exit
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		// The Window should be titled "Computer" and have fixed size
		this.setTitle("Computer");
		this.setResizable(false);

		// Ready the Layout for display
		this.pack();
	}

	/**
	 * USAGE: java edu.rpi.hagemt.hw8.CustomizeWindow
	 * @param args arguments from the command line
	 */
	public static void main(String... args) {
		// Add this Window's invocation to the Event Thread's queue, and make sure it's visible
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			@Override public void run() { new CustomizeWindow().setVisible(true); }
		});
	}
}
