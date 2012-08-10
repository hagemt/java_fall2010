/**
 * Rensselaer Polytechnic Institute, Fall 2010
 * CSCI-2200 -- Programming in Java (Section 1)
 * Homework #11 -- Project 17.1 Extended Again
 */
package edu.rpi.hagemt.hw11;

// AWT Imports
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

// Swing Imports
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 * GUI class to facilitate the maintenance of a team roster.
 * @author Tor E Hagemann <hagemt@rpi.edu>
 */
public class TeamRosterWindow extends javax.swing.JFrame implements javax.swing.SwingConstants {
	// Constants
	private static final long serialVersionUID = 7487708954331778161L;

	// GUI Field Elements
	private JPanel container, selectPanel, inputPanel, controlPanel, exitPanel;
	private GridBagConstraints containerConstraints, inputConstraints;
	private JComboBox playerSelect;
	private JTextField lastNameField, firstNameField, numberField, dobField, ageField;
	private JButton addButton, editButton, deleteButton, acceptButton, cancelButton, exitButton;
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenuItem openItem, saveItem;
	private JFileChooser fc;
	private ExtFilter filter;

	// Representation
	private ArrayList<Player> players;
	private File file;

	// State Representation (null mode indicates TeamIO usage)
	private State state;
	private Mode mode;

	/**
	 * Constructs a window to maintain a team roster.
	 */
	public TeamRosterWindow(File f) {
		// Initialize Panels with Layout and Constraints
		container = new JPanel(new GridBagLayout());
		selectPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		inputPanel = new JPanel(new GridBagLayout());
		controlPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		exitPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		containerConstraints = new GridBagConstraints();
		inputConstraints = new GridBagConstraints();

		// Initialize Combo Box
		playerSelect = new JComboBox();

		// Initialize Text Fields (default width 10)
		lastNameField = new JTextField(10);
		firstNameField = new JTextField(10);
		numberField = new JTextField(10);
		dobField = new JTextField(10);
		ageField = new JTextField(10);

		// Initialize Buttons
		addButton = new JButton("Add");
		editButton = new JButton("Edit");
		deleteButton = new JButton("Delete");
		acceptButton = new JButton("Accept");
		cancelButton = new JButton("Cancel");
		exitButton = new JButton("Exit");

		// Initialize Menu structure and FileChooser
		menuBar = new JMenuBar();
		fileMenu = new JMenu("File");
		openItem = new JMenuItem("Open...");
		saveItem = new JMenuItem("Save As...");
		fc = new JFileChooser();
		fc.setFileFilter(filter = new ExtFilter());
		fc.setSelectedFile(file = f);

		// Add Event Listeners to MenuItems
		openItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Display a file prompt to the user
				if (fc.showOpenDialog(TeamRosterWindow.this) != JFileChooser.APPROVE_OPTION) {
					return;
				}
				// Get the candidate file and see if it's valid
				File f = fc.getSelectedFile();
				if (f != null && f.canRead()) {
					// Check the filename and set the mode, if possible
					String filename = f.getName().toUpperCase();
					try {
						mode = Mode.valueOf(filename.substring(filename.lastIndexOf('.') + 1));
						// Use the proper utility class to read in the team data from the new file
						mode.getHandler().getMethod("getTeam", File.class, ArrayList.class).invoke(null, file = f, players);
						// Re-initialize the selection list and UI
						playerSelect.removeAllItems();
						for (Player p : players) {
							playerSelect.addItem(p);
						}
						fillFields(getSelectedPlayer());
					} catch (Exception ex) {
						// Notify the user of improper selections
						JOptionPane.showMessageDialog(TeamRosterWindow.this,
								"File lacks the proper format! " + filter.getDescription() + " are accepted!");
					} finally {
						fc.setSelectedFile(file);
					}
				} else {
					// Otherwise, give graceful feedback
					JOptionPane.showMessageDialog(TeamRosterWindow.this, "Couldn't read from " + f.getName());
				}
			}
		});
		saveItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Display a file prompt to the user
				if (fc.showSaveDialog(TeamRosterWindow.this) != JFileChooser.APPROVE_OPTION) {
					return;
				}
				// Get the candidate file and see if it's valid
				File f = fc.getSelectedFile();
				if (f != null && (!f.exists() || f.canWrite())) {
					// Get the filename and set the mode, if possible
					String filename = f.getName().toUpperCase();
					try {
						mode = Mode.valueOf(filename.substring(filename.lastIndexOf('.') + 1));
						// Create the file if it's not there
						if (!f.exists()) { f.createNewFile(); }
						// Use the proper utility class to write the team data to file
						mode.getHandler().getMethod("saveTeam", File.class, ArrayList.class).invoke(null, file = f, players);
					} catch (Exception ex) {
						// Notify the user of improper selections
						JOptionPane.showMessageDialog(TeamRosterWindow.this,
								"File lacks the proper format! " + filter.getDescription() + " are accepted!");
					} finally {
						fc.setSelectedFile(file);
					}
				} else {
					// Otherwise, give graceful feedback
					JOptionPane.showMessageDialog(TeamRosterWindow.this, "Couldn't write to " + f.getName());
				}
			}
		});

		// Add Event Listener to Combo Box
		playerSelect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				fillFields(getSelectedPlayer());
			}
		});

		// Add AutoSelect and/or IntFilter to Text Field(s)
		lastNameField.addFocusListener(new AutoSelect());
		firstNameField.addFocusListener(new AutoSelect());
		numberField.addFocusListener(new AutoSelect());
		numberField.addKeyListener(new IntFilter());
		dobField.addFocusListener(new AutoSelect());
		ageField.addFocusListener(new AutoSelect());

		// Add Event Listeners to Buttons
		addButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Disable the modifier buttons
				toggleControls();
				// Clear the fields
				fillFields(null);
				// Set the state
				state = State.ADDING;
			}
		});
		editButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Provided there are items to edit, do so
				if (playerSelect.getItemCount() != 0) {
					// Disable the modifier buttons
					toggleControls();
					// Set the state
					state = State.EDITING;
				}
			}
		});
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Provided there are items to remove, delete the currently selected one from everywhere and save
				if (playerSelect.getItemCount() != 0) {
					players.remove(playerSelect.getSelectedItem());
					playerSelect.removeItemAt(playerSelect.getSelectedIndex());
					persistPlayers();
				}
			}
		});
		acceptButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					// Attempt to create a player with the input data
					Player new_player = new Player(
							firstNameField.getText(),
							lastNameField.getText(),
							numberField.getText(),
							dobField.getText());
					switch (state) {
					case EDITING:
						// Check the new Player against the old one
						int selectedIndex = playerSelect.getSelectedIndex();
						Player old_player = players.get(selectedIndex);
						// Exit if there were no changes, otherwise, proceed to remove and re-add
						if (new_player.compareTo(old_player) == 0) { break; }
						players.remove(selectedIndex);
						playerSelect.removeItemAt(selectedIndex);
					case ADDING:
						// Ensure no players have duplicate numbers
						for (Player p : players) {
							if (new_player.playerNumber == p.playerNumber) {
								JOptionPane.showMessageDialog(TeamRosterWindow.this, p.toString() + " already has that number.");
								return;
							}
						}
						// Proceed to add and save team
						players.add(new_player);
						playerSelect.addItem(new_player);
						playerSelect.setSelectedItem(new_player);
						persistPlayers();
						break;
					default:
						// Undefined state, exit
						exitButton.doClick();
					}
					// Toggle state and re-enable modifier buttons
					state = State.BROWSING;
					toggleControls();
				} catch (IllegalArgumentException iae) {
					// Otherwise, throw back a dialog to the user
					JOptionPane.showMessageDialog(TeamRosterWindow.this, iae.getMessage());
				}
			}
		});
		cancelButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Re-enable the modifier controls and re-fill the fields, then toggle state
				toggleControls();
				if (playerSelect.getItemCount() != 0) {
					fillFields(getSelectedPlayer());
				}
				state = State.BROWSING;
			}
		});
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Close the window and exit
				dispose();
			}
		});

		if (file != null && file.canRead()) {
			String filename = file.getName().toUpperCase();
			players = new ArrayList<Player>();
			try {
				mode = Mode.valueOf(filename.substring(filename.lastIndexOf('.') + 1));
				mode.getHandler().getMethod("getTeam", File.class, ArrayList.class).invoke(null, file, players);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this,
						"File lacks the proper format! " + filter.getDescription() + " are accepted!");
			}
		} else {
			players = TeamIO.getTeam();
			mode = null;
		}
		state = State.BROWSING;

		// Populate player options from the player list
		for (Player p : players) {
			playerSelect.addItem(p);
		}

		// Disallow editing of fields disabled by default
		lastNameField.setEditable(false);
		firstNameField.setEditable(false);
		numberField.setEditable(false);
		dobField.setEditable(false);
		ageField.setEditable(false);
		acceptButton.setEnabled(false);
		cancelButton.setEnabled(false);

		// Populate text fields
		fillFields(getSelectedPlayer());

		// Populate the menus
		fileMenu.add(openItem);
		fileMenu.add(saveItem);
		menuBar.add(fileMenu);

		// Insert elements into the top panel
		selectPanel.add(new JLabel("Select Player:"));
		selectPanel.add(playerSelect);

		// Setup proper padding of elements
		inputPanel.setBorder(new EmptyBorder(10, 0, 10, 0));
		container.setBorder(new EmptyBorder(5, 5, 5, 5));
		inputConstraints.insets = new Insets(5, 5, 5, 5);

		// Insert form labels and fields appropriately
		inputConstraints.gridx = 0; inputConstraints.anchor = GridBagConstraints.EAST;
		inputConstraints.gridy = 0; inputPanel.add(new JLabel("Last name:"), inputConstraints);
		inputConstraints.gridy = 1; inputPanel.add(new JLabel("First name:"), inputConstraints);
		inputConstraints.gridy = 2; inputPanel.add(new JLabel("Number:"), inputConstraints);
		inputConstraints.gridx = 2; inputConstraints.anchor = GridBagConstraints.EAST;
		inputConstraints.gridy = 0; inputPanel.add(new JLabel("Date of Birth:"), inputConstraints);
		inputConstraints.gridy = 2; inputPanel.add(new JLabel("Age:"), inputConstraints);
		inputConstraints.gridx = 1; inputConstraints.anchor = GridBagConstraints.WEST;
		inputConstraints.gridy = 0; inputPanel.add(lastNameField, inputConstraints);
		inputConstraints.gridwidth = 3; inputConstraints.fill = GridBagConstraints.HORIZONTAL;
		inputConstraints.gridy = 1; inputPanel.add(firstNameField, inputConstraints);
		inputConstraints.gridwidth = 1; inputConstraints.fill = GridBagConstraints.NONE;
		inputConstraints.gridy = 2; inputPanel.add(numberField, inputConstraints);
		inputConstraints.gridx = 3; inputConstraints.anchor = GridBagConstraints.WEST;
		inputConstraints.gridy = 0; inputPanel.add(dobField, inputConstraints);
		inputConstraints.gridy = 2; inputPanel.add(ageField, inputConstraints);

		// Add the buttons to their container
		controlPanel.add(addButton);
		controlPanel.add(editButton);
		controlPanel.add(deleteButton);
		controlPanel.add(acceptButton);
		controlPanel.add(cancelButton);
		exitPanel.add(exitButton);

		// Layout the main window as simply as possible
		containerConstraints.anchor = GridBagConstraints.WEST;
		containerConstraints.gridy = 0; container.add(selectPanel, containerConstraints);
		containerConstraints.gridy = 1; container.add(inputPanel, containerConstraints);
		containerConstraints.anchor = GridBagConstraints.EAST;
		containerConstraints.gridy = 2; container.add(controlPanel, containerConstraints);
		containerConstraints.gridy = 3; container.add(exitPanel, containerConstraints);

		// Add the main panel and menubar
		getContentPane().add(container);
		setJMenuBar(menuBar);

		// When the the user presses "close," we should exit
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// Set our title appropriately and have fixed size
		setTitle("Team Roster");
		setResizable(false);

		// Ready the layout for display
		pack();
	}

	// Flip the enabled state of the modifier buttons
	private void toggleControls() {
		playerSelect.setEnabled(!playerSelect.isEnabled());
		lastNameField.setEditable(!lastNameField.isEditable());
		firstNameField.setEditable(!firstNameField.isEditable());
		numberField.setEditable(!numberField.isEditable());
		dobField.setEditable(!dobField.isEditable());
		addButton.setEnabled(!addButton.isEnabled());
		editButton.setEnabled(!editButton.isEnabled());
		deleteButton.setEnabled(!deleteButton.isEnabled());
		acceptButton.setEnabled(!acceptButton.isEnabled());
		cancelButton.setEnabled(!cancelButton.isEnabled());
	}

	// Fill (or empty, if p == null) then clear fields
	private void fillFields(Player p) {
		if (p != null) {
			lastNameField.setText(p.lastName);
			firstNameField.setText(p.firstName);
			numberField.setText(new Integer(p.playerNumber).toString());
			dobField.setText(p.getBirthDate());
			ageField.setText(new Integer(p.getAge()).toString());
		} else {
			lastNameField.setText("");
			firstNameField.setText("");
			numberField.setText("");
			dobField.setText("");
			ageField.setText("");
		}
	}

	// Return the player selected by the user
	private Player getSelectedPlayer() {
		return (Player)(playerSelect.getSelectedItem());
	}
	
	// Use the mode field's set handler class to call the proper "saveTeam" method
	private void persistPlayers() {
		if (mode == null) {
			TeamIO.saveTeam(players);
		} else {
			try {
				mode.getHandler().getMethod("saveTeam", File.class, ArrayList.class).invoke(null, file, players);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(TeamRosterWindow.this, "Couldn't write to " + file.getName());
			}
		}
	}

	/**
	 * USAGE: java edu.rpi.hagemt.hw9.TeamRosterWindow [pathname of CSV or DAT file to load]
	 * @param args optionally specifies a file to utilize as the team's data source
	 */
	public static void main(String... args) {
		// Initialize a file object dependent on the command-line arguments
		final File f = (args.length == 1) ? new File(args[0]) : null;
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new TeamRosterWindow(f).setVisible(true);
			}
		});
	}
}
