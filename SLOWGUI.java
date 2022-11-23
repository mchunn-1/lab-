package lab6;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class SLOWGUI extends JFrame 
{
	
	private static final long serialVersionUID = 1L;
	private JTextArea textArea = new JTextArea();
	private JButton slowButton = new JButton("Start slow thing");
	private JButton cancelButton = new JButton("Cancel");
	private boolean cancel = false;
	

	private class CancelActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{
			cancel = true;
		}
	}

	private class SlowActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			cancel = false;
			slowButton.setEnabled(false);
			cancelButton.setEnabled(true);
			textArea.setText("doing something slow\n");
			new Thread(new SlowActionRunnable()).start();		}
	}
	
	private class SlowActionRunnable implements Runnable 
	{
		public void run()
		{
			try 
			{
				//int numTimes = 0;
			
				//while( ! cancel && numTimes < 100)
				//{
				//numTimes++;
				//textArea.append("Time " + numTimes + "\n");
				//Thread.sleep(1000);
				//Scanner user = new Scanner(System.in);
				//int num = user.nextInt();
				int num = 15;
				int i = 0;
				int primeCount = 0;
				while(! cancel && i <= 10);
				{
					i++;
					textArea.append("hi");
					//for(int x=2; x < num; x++)
					//{
					//	if((num % x) ==0)
					//	primeCount = primeCount + 1;
					//	textArea.append("number of primes" + primeCount);
					//}
					 
				
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
						slowButton.setEnabled(true);
						cancelButton.setEnabled(false);
					}
				});
				
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
		}
	}

	
	
	public JPanel getBottomPanel()
	{
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,2));	
		slowButton.addActionListener(new SlowActionListener());
		cancelButton.addActionListener(new CancelActionListener());
		panel.add(slowButton);
		panel.add(cancelButton);
		
		return panel;
		
		
	}
	
	public SLOWGUI()
	{
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(textArea, BorderLayout.CENTER);
		add(getBottomPanel(), BorderLayout.SOUTH);
		setSize(400,300);
		setLocationRelativeTo(null);
		setVisible(true);
		
	}

	public static void main(String[] args)
	{
		new SLOWGUI();
	}
	

}
