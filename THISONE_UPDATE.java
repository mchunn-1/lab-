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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class LABSIX extends JFrame {
	
	private static final long serialVersionUID = 13L;
	
	private JTextArea scrollPaneText = new JTextArea();
	private JScrollPane scrollPane = new JScrollPane(scrollPaneText);

	
	private JLabel enterLabel = new JLabel("Enter a big integer and press start!");
	private JTextField userNumField = new JTextField();

	private JLabel runningTimerLabel = new JLabel();
	private JLabel runningCount = new JLabel();
	
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
		        int num = Integer.parseInt(userNumField.getText());

		        
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
		           
		            userNumField.setText("On number " + i + " out of " + num);
			        
			        long stop = System.currentTimeMillis();
			        long time = (stop -start)/1000;
			      
			        if(time % 2 == 0)
			        {
			        	int numPrimes = primeNumbers.size();
			        	runningCount.setText("There are " + numPrimes + " prime numbers out of the numbers checked!");

			        }
			        
			        if (time % 1 == 0)
			        {
			        	 runningTimerLabel.setText("Time elapsed: "+ time + " seconds");
			        }
			        
		            i++;
		           
		            
		            
		       }
		    
		        long stopFinal = System.currentTimeMillis();
		        long timeFinal = (stopFinal - start)/1000;
		        
		    
		        for (Integer element : primeNumbers) 
		        {
		        	scrollPaneText.append(""+ element +"\n");
		        }
		        
				scrollPaneText.append("Final Time Elapsed: " + timeFinal + " seconds.");
				
			}
			
			catch(Exception ex)
			{
				userNumField.setText(ex.getMessage());
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
			System.exit(0);
		}
	}
	
	
	private JPanel getPrimeNumers()
	{
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2,0));
		
	
		runningCount.setForeground (Color.blue);
		panel.add(runningCount);
		panel.add(scrollPane);
		


		
		return panel;
	}
	
	private JPanel getUserInput()
	{
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,2));
		
		panel.add(enterLabel);
		panel.add(userNumField);
		runningTimerLabel.setForeground (Color.red);
		panel.add(runningTimerLabel);
		
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
	
	
	
