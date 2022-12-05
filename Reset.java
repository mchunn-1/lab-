package examples;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class MEXGUI extends JFrame
{
	private static final long serialVersionUID = 1975L;
	
	static JTextField input = new JTextField();
	static JTextArea output = new JTextArea();
	private JScrollPane outScroll = new JScrollPane(output);
	//wrapping in a JLabel: https://stackoverflow.com/questions/1090098/newline-in-jlabel/1090112#1090112
	private JLabel directions = new JLabel("<html>Enter an integer below, select the number of threads, and press start!<html>", SwingConstants.CENTER);
	static JButton start = new JButton("Start");
	private JButton reset = new JButton("Reset");
	static JButton cancel = new JButton("Cancel");
	static JLabel time = new JLabel("", SwingConstants.CENTER);
	static JTextField progress = new JTextField();
	private JLabel threads = new JLabel("", SwingConstants.CENTER);
	static boolean isCancel = false;
	
	private Integer threadCount = 0;
	Timer timer = new Timer(1000, new timeActionListener());
	int timeRun = 0;

	public void reset() 
	{
		input.setText("");
		output.setText("");
		progress.setText("");
		threads.setText("Number of Threads: ");
		
		
	}
	
	private class timeActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) {
			timeRun++;
			time.setText(String.valueOf(timeRun));
		}
	}
	
	private class CancelListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			System.out.println("hit cancel");
			progress.setText("hit cancel");
			isCancel = true;
			timer.stop();
		}
	}
	
	private class StartListener implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0)
		{
			isCancel = false;
			start.setEnabled(false);
			cancel.setEnabled(true);
			//MAKE IT SO YOU CAN RE-SELECT THREADS AFTER A RUN
			while(threadCount == 0)
			{
				threadCount = Integer.parseInt(JOptionPane.showInputDialog("Number of threads (1-5):"));
				if(threadCount < 1 || threadCount > 5)
				{
					threadCount = Integer.parseInt(JOptionPane.showInputDialog("Please enter a number between 1 and 5!"));
				}
				threads.setText("Number of Threads: " + threadCount.toString());
			}
			int userNum = Integer.parseInt(input.getText());
			timer.start();
			try 
			{
				MEXnoGUI.control
				(userNum, threadCount);
			} catch (Exception e1) 
			{
				e1.printStackTrace();
			}
		}
	}
	
	private class ResetActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			reset();
			
		}
	}
	
	private JPanel timeBar()
	{
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2, 0));
		panel.add(threads);
		panel.add(time);
		return panel;
	}

	private JPanel inputTime()
	{
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2, 0));
		panel.add(input);
		panel.add(timeBar());
		return panel;
	}
	
	private JPanel north()
	{
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2, 0));
		panel.add(directions);
		panel.add(inputTime());
		
		return panel;
		
	}
	
	private JPanel center()
	{
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2, 0));
		panel.add(progress);
		progress.setEditable(false);
		panel.add(outScroll);
		output.setLineWrap(true);
		output.setWrapStyleWord(true);
		output.setEditable(false);
		return panel;
	}
	
	private JPanel south()
	{
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 3));
		panel.add(start);
		panel.add(reset);
		panel.add(cancel);
		
		
		start.addActionListener(new StartListener());
		cancel.addActionListener(new CancelListener());
		reset.addActionListener(new ResetActionListener());
		
		return panel;
	}

	public MEXGUI()
	{
		super("Very slow thing");
		setLocationRelativeTo(null);
		setSize(400, 400);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().add(north(), BorderLayout.NORTH);
		getContentPane().add(center(), BorderLayout.CENTER);
		getContentPane().add(south(), BorderLayout.SOUTH);
		start.addActionListener(new StartListener());
		cancel.addActionListener(new CancelListener());
		setVisible(true);
	}

	
	public static void main(String[] args)
	{
		new MEXGUI();
	}
}
