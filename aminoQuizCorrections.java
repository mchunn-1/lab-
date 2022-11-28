package quizGUI;

import java.awt.BorderLayout;
import java.awt.Color;
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


public class Quiz extends JFrame 
{

	private static final long serialVersionUID = 1L;
	
	//index for arrays 
	int index = 0;
	//counter for correct/incorrect answers 
	int rightCounter = 0;
	int wrongCounter = 0;
	//setting the seconds remaining 
	int secondsRemaining = 31;
	
	//amino acid's full name
	private JLabel aminoAcid  = new JLabel();
	//text filed for the users input 
	private JTextField userAnswer = new JTextField();
	//label for the correct counter 
	private JLabel correctlabel = new JLabel ("Number correct: ");
	//label for the incorrect counter text field 
	private JLabel incorrectlabel = new JLabel ("Number incorrect: ");
	//shows number correct
	private JLabel correctCounter  = new JLabel();
	//shows number incorrect
	private JLabel incorrectCounter  = new JLabel();
	//label for timer 
	private JLabel timeRemaining = new JLabel ("Time remaining: ");
	private JTextField time  = new JTextField();
	//start button 
	private JButton start = new JButton("Start Quiz");
	//cancel button
	private JButton cancelB = new JButton("Cancel");	
	
	//1000 -> timer will have 1 second between each number 
	Timer timer = new Timer(1000, new timeActionListener());
	
	private class timeActionListener implements ActionListener
	{
		
	    public void actionPerformed(ActionEvent t)
	    {
	    	//minus 1 from seconds remaining 
	    	secondsRemaining--;
	    	//update time text field will the number of seconds left
	        time.setText(String.valueOf(secondsRemaining));
	        //if the timer hits 0
	        if(secondsRemaining <= 0){
	        	//stop the timer
	            timer.stop();
	            //disable the text fields 
	            userAnswer.setEnabled(false);
	            aminoAcid.setEnabled(false);
	        }
	    }

	}

	private class startActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent amino)
		{
			//calls method to generate amino acid 
			getAA();
			//starts timer when start button is clicked 
			timer.start();
			userAnswer.setEnabled(true);
			//resetting the counters 
			rightCounter = 0;
			wrongCounter = 0;
			//resetting the seconds remaining 
			secondsRemaining = 31;
			//resetting the labels 
			correctCounter.setText("");
			incorrectCounter.setText("");

		}
	}
	
	private class cancelActionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent e) 
		{
			timer.stop();
			userAnswer.setEnabled(false);
		}
	}
	
	
	private class inputActionListener implements ActionListener

	{
		public void actionPerformed(ActionEvent input)
		{
			//starts timer when start button is clicked 
			
			String userInput = userAnswer.getText().toUpperCase();
			//sets up array of amino acid 1 letter code 
			String[] SHORT_NAMES = 
				{ "A","R", "N", "D", "C", "Q", "E", 
				"G",  "H", "I", "L", "K", "M", "F", 
				"P", "S", "T", "W", "Y", "V" };
			
			//if the user input matches the amino acid a the given index
			if (userInput.equals(SHORT_NAMES[index]))
			{
				//update the counter in the text field representing the correct counter
				rightCounter = rightCounter + 1;
				correctCounter.setText(String.valueOf(rightCounter));
			}
			
			else 
			{
				//update the counter in the text field representing the incorrect counter
				wrongCounter = wrongCounter + 1;
				incorrectCounter.setText( String.valueOf(wrongCounter));

			}
			userAnswer.setText("");
			getAA();
		}
	}
	
	
 	private void getAA()
	{
		String aa = "";
		Random random = new Random();
		//sets up array of amino acid full name  
		String[] FULL_NAMES = 
			{
			"alanine","arginine", "asparagine", 
			"aspartic acid", "cysteine",
			"glutamine",  "glutamic acid",
			"glycine" ,"histidine","isoleucine",
			"leucine",  "lysine", "methionine", 
			"phenylalanine", "proline", 
			"serine","threonine","tryptophan", 
			"tyrosine", "valine"};
		//index is set to a random number from 0-19
		index = random.nextInt(19);
		//the amino acid name at the index is shown in the text field 
		aa = FULL_NAMES[index];
		updateAATextField(aa);
	}
	
	private void updateAATextField(String aa)
	{
		//amino acid text field is updated when this method is called 
		aminoAcid.setText("Amino Acid:      " + aa);
		validate();
	}
	
	
	private JPanel getStartCancelButtons()
	{
		//sets panel for start and cancel buttons
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,2));
		//adds start button and cancel button
		panel.add(start);
		panel.add(cancelB);
		
		return panel; 
	}
	
	private JPanel getAminoAnswer()
	{
		//sets panel for the amino acid text field and the user answer text field 
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(2,0));
		//adds amino acid text field and user answer text field 
		panel.add(aminoAcid);
		panel.add(userAnswer);
		return panel; 
	}
	
	private JPanel getTopPanel()
	{
		//set panel for the correct counter, incorrect counter and timer text fields 
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3,0));
		//adds number correct label 
		panel.add(correctlabel);
		//adds text field  to show number correct 
		panel.add(correctCounter);
		correctCounter.setForeground (Color.blue);
		//adds number incorrect label
		panel.add(incorrectlabel);
		//adds text field to show number incorrect 
		panel.add(incorrectCounter);
		incorrectCounter.setForeground (Color.red);
		//adds label for time remaining 
		panel.add(timeRemaining);
		//adds text field to show time remaining 
		panel.add(time);
		
		
		return panel; 
	}
	
	public Quiz()
	{
		//adds title to GUI
		super("Amino Acid Quiz");
		setLocationRelativeTo(null);
		//sets the size of the GUI
		setSize(500,300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//puts the start/cancel buttons on the bottom
		getContentPane().add(getStartCancelButtons(), BorderLayout.SOUTH);
		//puts the place for the amino acid text field and user input text field in the center
		getContentPane().add(getAminoAnswer(), BorderLayout.CENTER);
		//puts the counters and timer at the top 
		getContentPane().add(getTopPanel(), BorderLayout.NORTH);
		
		//calls on the action listeners defined above 
		start.addActionListener(new startActionListener());
		cancelB.addActionListener(new cancelActionListener());
		userAnswer.addActionListener(new inputActionListener());
		time.addActionListener(new timeActionListener());
		time.setEnabled(false);


		
		setVisible(true);
		
	}
	

	public static void main(String[] args) 
	{
		new Quiz();
	}
}
