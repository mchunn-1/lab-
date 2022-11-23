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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class LABSIX extends JFrame {
	
	private static final long serialVersionUID = 13L;
	private JTextArea textArea = new JTextArea("prime numbers here");
	private JButton startB = new JButton("Start");
	private JButton exitB = new JButton("Exit");
	private JButton cancelB = new JButton("Cancel Count");
	private boolean cancel = false;
	
	
	private class CancelActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{
			cancel = true;
		}
	}
	
	private class StartActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			cancel = false;
			startB.setEnabled(false);
			cancelB.setEnabled(true);
			textArea.setText("doing something slow\n");
			new Thread(new StartActionRunnable()).start();		}
	}
	
	private class StartActionRunnable implements Runnable 
	{
		public void run()
		{
			try 
			{
				//Scanner user = new Scanner(System.in);
				//int numTimes = user.nextInt();
				int numTimes = 0;
			
				while( ! cancel && numTimes < 100)
				{
				numTimes++;
				//textArea.append("hi");
				textArea.append("Time " + numTimes + "\n");
				Thread.sleep(1000);
				}
			
				
			}
			catch(Exception ex)
			{
				textArea.setText(ex.getMessage());
				ex.printStackTrace();
			}
	
			try
			{
				SwingUtilities.invokeLater(new Runnable()
				{
					public void run()
					{
						startB.setEnabled(true);
						cancelB.setEnabled(false);
					}
				});
				
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
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
		panel.add(textArea);;
		
		return panel;
	}
	
	private JPanel getStartExitButtons()
	{
		//sets panel for start and cancel buttons
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,3));
		//adds start button and cancel button
		panel.add(startB);
		panel.add(cancelB);
		panel.add(exitB);
		 
		
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
				
				startB.addActionListener(new StartActionListener());
				cancelB.addActionListener(new CancelActionListener());
				exitB.addActionListener(new exitActionListener());
				
				setVisible(true);
			
	}
	
	public static void main(String[] args) 
	{
		new LABSIX();
	}
	
	
}
	
	
	
	
