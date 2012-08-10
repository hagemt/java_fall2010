import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
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

public class InvoiceApp extends JFrame implements SwingConstants {
	private static final long serialVersionUID = 8020941103963799241L;
	private static final DecimalFormat PRICE_FORMAT = new DecimalFormat("$0.00");
	private JPanel container, inputPanel, buttonPanel;
	private JTextField subtotal, percent, discount, total;
	private JButton clearButton, calculateButton, exitButton;

	public InvoiceApp() {
		container = new JPanel(new BorderLayout());
		inputPanel = new JPanel(new GridLayout(4, 2, 0, 5));
		buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		subtotal = new JTextField(12);
		percent = new JTextField(12);
		discount = new JTextField(12);
		total = new JTextField(12);
		clearButton = new JButton("Clear");
		calculateButton = new JButton("Calculate");
		exitButton = new JButton("Exit");
		inputPanel.setBorder(new EmptyBorder(3, 3, 3, 3));
		percent.setEditable(false);
		discount.setEditable(false);
		total.setEditable(false);
		clearButton.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				subtotal.setText("");
				percent.setText("");
				discount.setText("");
				total.setText("");
			}
		});
		calculateButton.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				try {
					double sub = new Double(subtotal.getText());
					double per = (sub >= 200) ? 0.2d : (sub >= 100) ? 0.1d : 0.0d;
					percent.setText(new Double(per).toString());
					discount.setText(PRICE_FORMAT.format(sub * per));
					total.setText(PRICE_FORMAT.format(sub * (1 - per)));
				} catch (NumberFormatException nfe) { clearButton.doClick(); }
			}
		});
		exitButton.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) { InvoiceApp.this.dispose(); }
		});
		inputPanel.add(new JLabel("Subtotal:"));
		inputPanel.add(subtotal);
		inputPanel.add(new JLabel("Discount Percent:"));
		inputPanel.add(percent);
		inputPanel.add(new JLabel("Discount:"));
		inputPanel.add(discount);
		inputPanel.add(new JLabel("Invoice Total:"));
		inputPanel.add(total);
		buttonPanel.add(clearButton);
		buttonPanel.add(calculateButton);
		buttonPanel.add(exitButton);
		container.add(inputPanel, BorderLayout.CENTER);
		container.add(buttonPanel, BorderLayout.SOUTH);
		this.getContentPane().add(container);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setTitle("Invoice Total Calculator");
		this.setResizable(false);
		this.pack();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int width = new Double(screenSize.getWidth() / 2 - this.getWidth() / 2).intValue();
		int height = new Double(screenSize.getHeight() / 2 - this.getHeight() / 2).intValue();
		this.setLocation(width, height);
	}
	
	public static void main(String... args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			@Override public void run() { new InvoiceApp().setVisible(true); }
		});
	}
}
