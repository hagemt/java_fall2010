import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 * @author Tor E Hagemann <hagemt@rpi.edu>
 */
public class RightTrianglesWindow extends JFrame implements SwingConstants {
	private static final long serialVersionUID = -1678630159877762579L;
	private JPanel container, inputPanel, buttonPanel;
	private JTextField sideA, sideB, sideC;
	private JButton calculateButton, exitButton;

	public RightTrianglesWindow() {
		container = new JPanel(new BorderLayout());
		inputPanel = new JPanel(new GridLayout(3, 2, 0, 5));
		buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		sideA = new JTextField(12);
		sideB = new JTextField(12);
		sideC = new JTextField(12);
		calculateButton = new JButton("Calculate");
		exitButton = new JButton("Exit");
		container.setBorder(new EmptyBorder(3, 3, 3, 3));
		sideC.setEditable(false);
		calculateButton.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				try {
					double a = new Double(sideA.getText());
					double b = new Double(sideB.getText());
					sideC.setText(new DecimalFormat("0.00").format(Math.sqrt(a * a + b * b)));
				} catch (NumberFormatException nfe) { sideC.setText("Invalid Side Length(s)"); }
			}
		});
		exitButton.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				RightTrianglesWindow.this.dispose();
			}
		});
		inputPanel.add(new JLabel("Side A:"));
		inputPanel.add(sideA);
		inputPanel.add(new JLabel("Side B:"));
		inputPanel.add(sideB);
		inputPanel.add(new JLabel("Side C:"));
		inputPanel.add(sideC);
		buttonPanel.add(calculateButton);
		buttonPanel.add(exitButton);
		container.add(inputPanel, BorderLayout.CENTER);
		container.add(buttonPanel, BorderLayout.SOUTH);
		this.getContentPane().add(container);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setMinimumSize(new Dimension(250, 150));
		this.setTitle("Right Triangles");
		this.pack();
	}

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			@Override public void run() { new RightTrianglesWindow().setVisible(true); }
		});
	}
}
