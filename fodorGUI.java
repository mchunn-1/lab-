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

public class SlowGUI extends JFrame
{
	private static final long serialVersionUID = 1975L;
	
	static JTextField input = new JTextField("input");
	static JTextArea output = new JTextArea("output");
	private JScrollPane outScroll = new JScrollPane(output);
	private JLabel directions = new JLabel("<html>Enter an integer below and press start!<html>", SwingConstants.CENTER);
	static JButton start = new JButton("Start");
	private JButton reset = new JButton("Reset");
	static JButton cancel = new JButton("Cancel");
	static JLabel time = new JLabel("", SwingConstants.CENTER);
	static JTextField progress = new JTextField("progress");
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
		threadCount = 0;
		start.setEnabled(true);
	}
	
	private class timeActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{
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
	
	public class ctrlSub implements Runnable
	{
		int userNum;
		int threadCount;
		public ctrlSub(int userNum, int threadCount)
		{
			this.userNum = userNum;
			this.threadCount = threadCount;
		}
		public void run() {
			try {
				Blah.control(userNum, threadCount);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void getThreads()
	{
		while(threadCount == 0)
		{
			threadCount = Integer.parseInt(JOptionPane.showInputDialog("Please enter the number of threads:"));
			if(threadCount < 1 || threadCount > 5)
			{
				threadCount = Integer.parseInt(JOptionPane.showInputDialog("Please enter a number greater than 0!"));
			}
			threads.setText("Number of Threads: " + threadCount.toString());
		}
		int userNum = Integer.parseInt(input.getText());
		timer.start();
		new Thread(new ctrlSub(userNum, threadCount)).start();
	}
	
	private class StartListener implements ActionListener
	{
		public void actionPerformed(ActionEvent arg0)
		{
			isCancel = false;
			start.setEnabled(false);
			cancel.setEnabled(true);
			getThreads();
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

	public SlowGUI()
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
		new SlowGUI();
	}
}
