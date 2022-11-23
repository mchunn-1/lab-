package lab6;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

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
	private JTextField textField = new JTextField();
	private JTextField textField3 = new JTextField();
	private JTextField textField4 = new JTextField();
	private JLabel enterLabel = new JLabel("Enter a big integer and press start!");
	private JLabel timerLabel = new JLabel();
	private JLabel finalTimeLabel = new JLabel();
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
			new Thread(new StartActionRunnable()).start();		
		}
	}
	
	private class StartActionRunnable implements Runnable 
	{
		public void run()
		{
			try 
			{
		        int num = Integer.parseInt(textField.getText());

		        
		        List<Integer> primeNumbers = new ArrayList<>();
		        List<Integer> nonPrimeNumbers = new ArrayList<>();

		        int i = 2;
		        long start = System.currentTimeMillis();
		        while (! cancel && i <= num)
		        {
		            boolean passPrimeCheck = true;
		            
		            
		            for (int x = 2; x < i; x++)
		            {
		                if (i % x == 0)
		                {
		                    passPrimeCheck = false;
		                    nonPrimeNumbers.add(i);
		                    break; 
		                }
		            }

		            if (passPrimeCheck) 
		            {
		                primeNumbers.add(i);

		            }
		           
			        textField.setText(i + " out of " + num);
			        
			        long stop = System.currentTimeMillis();
			        long time = (stop -start)/1000;
			      
			        if(time % 2 == 0)
			        {
			        	int numPrimes = primeNumbers.size();
				        timerLabel.setText(numPrimes + " prime numbers out of " + i + " numbers checked so far in " + time + " seconds");

			        }
			        
		            i++;
		           
		            
		            
		       }
		        //String allNonPrime = nonPrimeNumbers.toString();
		       // System.out.println("Not Prime Numbers: " + allNonPrime);
		        long stopFinal = System.currentTimeMillis();
		        long timeFinal = (stopFinal - start)/1000;
	            int numPrimesFinal = primeNumbers.size();
		        String allPrime = primeNumbers.toString();
		        finalTimeLabel.setText("It took " + timeFinal + " seconds to find the prime numbers.");
		        textField3.setText("There are " + numPrimesFinal + " prime numbers including: " + allPrime);
			
				
			}
			
			catch(Exception ex)
			{
				textField.setText(ex.getMessage());
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
	
	
	private JPanel getPrimeNumers()
	{
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3,0));
		timerLabel.setForeground (Color.red);
		panel.add(timerLabel);
		finalTimeLabel.setForeground (Color.blue);
		panel.add(finalTimeLabel);
		panel.add(textField3);
		


		
		return panel;
	}
	
	private JPanel getUserInput()
	{
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,2));
		panel.add(enterLabel);
		panel.add(textField);
		return panel;
	}
	
	private JPanel getStartExitButtons()
	{
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,3));
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
			getContentPane().add(getUserInput(), BorderLayout.NORTH);
			getContentPane().add(getStartExitButtons(), BorderLayout.SOUTH);
			getContentPane().add(getPrimeNumers(), BorderLayout.CENTER);
				
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
	
	
	
