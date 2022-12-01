package lab6;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class LABSIX extends JFrame {
	
	private static final long serialVersionUID = 13L;
	
	private JTextArea scrollPaneText = new JTextArea();
	private JScrollPane scrollPane = new JScrollPane(scrollPaneText);

	
	private JLabel enterLabel = new JLabel("Currently on number:");
	private JTextField userNumField = new JTextField();

	private JLabel runningTimerLabel = new JLabel();
	private JLabel runningCount = new JLabel();
		
	private JButton startB = new JButton("Start");
	private JButton exitB = new JButton("Exit");
	private JButton cancelB = new JButton("Cancel Count");
	private JButton resetB = new JButton("Reset");
	
	
	private volatile boolean cancel = false;
	
    
	private class StartActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			cancel = false;
			cancelB.setEnabled(true);
			
		
		}
	}
	
	private class CancelActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{
			cancel = true;
			
		}
	}
	
	private class resetActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{
			//startB.setEnabled(true);
			userNumField.setText("");
			runningTimerLabel.setText("");
			runningCount.setText("");
			scrollPaneText.setText("");
			
		}
	}
	
	private class slowRunnable implements Runnable 
	{
        private int num;

		private final Semaphore semaphore;
		
		public slowRunnable(int num, Semaphore semaphore)
		{
			this.num = num;
			this.semaphore = semaphore;
		}
		
		@Override
		public void run()
		{
			try 
			{
				resetB.setEnabled(false);
		        //int num = Integer.parseInt(userNumField.getText());
		        
		        List<Integer> primeNumbers = new ArrayList<>();

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
		                    break; 
		                }
		            }

		            if (passPrimeCheck) 
		            {
		                primeNumbers.add(i);

		            }
		           
		            userNumField.setText(i + " out of " + num);
			        
			        long stop = System.currentTimeMillis();
			        long time = (stop -start)/1000;
			      
			        if(time % 2 == 0)
			        {
			        	int numPrimes = primeNumbers.size();
			        	runningCount.setText("There are " + numPrimes + " prime numbers out of the numbers checked!");

			        }
					//scrollPaneText.append("Final primes Elapsed: " + primeNumbers.size() + " seconds.");

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
				scrollPaneText.append("Primes " + primeNumbers.size());
				resetB.setEnabled(true);
			}
			
			catch(Exception ex)
			{
				userNumField.setText(ex.getMessage());
				ex.printStackTrace();
			}
		
			finally 
			{
				semaphore.release();
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
		panel.setLayout(new GridLayout(0,4));
		
		panel.add(startB);
		panel.add(cancelB);
		panel.add(resetB);
		panel.add(exitB);
		 
		
		return panel;
	}
	
	public LABSIX() throws Exception
	
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
			resetB.addActionListener(new resetActionListener());
			setVisible(true);
			
			
			//int numIter = 1;
		    //int num = Integer.parseInt(userNumField.getText());
		    int num = Integer.parseInt(JOptionPane.showInputDialog("You can chose the number. Please enter a big integer. "));

		    
		    int threadInput = Integer.parseInt(JOptionPane.showInputDialog("You can chose the number of threads to run this calculation. Please enter a integer between 1 and 5. "));
			Semaphore s = new Semaphore(threadInput);

			    
			for (int x = 0; x < threadInput; x++)
			 {
			    	
				s.acquire();
			    slowRunnable sr = new slowRunnable(num, s);
			    new Thread(sr).start();
			 }
			    
			//for (int x=0; x< threadInput; x++)
			//	s.acquire(); 
				
			
			
			
	}
	
	
	public static void main(String[] args) throws Exception 
	
	{
		new LABSIX();
		
	}
	
	
}
	
	
	
	
