package lab6;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

public class LABSIX extends JFrame {
	
	private static final long serialVersionUID = 13L;
	private JTextField showNumbers = new JTextField("prime numbers here");
	private JButton start = new JButton("Start");
	private JButton exit = new JButton("Exit");
	private JButton cancel = new JButton("Cancel Count");
	
	private class startActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent amino)
		{
			//calls method to generate amino acid 
			//getAA();
			//starts timer when start button is clicked 
			//timer.start();
		}
	}
	
	private class exitActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent exit)
		{
			//closes GUI when exit button is clicked
			System.exit(0);
		}
	}
	
	private JPanel getPrimeNumersOrCancel()
	{
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,0));
		panel.add(showNumbers);;
		
		return panel;
	}
	
	private JPanel getStartExitButtons()
	{
		//sets panel for start and cancel buttons
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,3));
		//adds start button and cancel button
		panel.add(start);
		panel.add(cancel);
		panel.add(exit);
		 
		
		return panel;
	}
	
	public LABSIX()
	{
				//adds title to GUI
				super("Lab 6: How many prime numbers?");
				setLocationRelativeTo(null);
				//sets the size of the GUI
				setSize(500,300);
				setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				//puts the start/cancel buttons on the bottom
				getContentPane().add(getStartExitButtons(), BorderLayout.SOUTH);
				//puts the place for the amino acid text field and user input text field in the center
				getContentPane().add(getPrimeNumersOrCancel(), BorderLayout.CENTER);
				
				start.addActionListener(new startActionListener());
				exit.addActionListener(new exitActionListener());
				
				setVisible(true);
			
	}
	
	public static void main(String[] args) 
	{
		new LABSIX();
	}
	
	
	
	
	
	
}
