import java.awt.Color;
import java.util.Random;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;
/*********************************************************************************************************
 *<pre>
 * File: Game.java
 * 
 * The class Game simulates a rock, paper, scissors game between two players and 1000 rounds.
 * 
 * </pre>
 * @author Nicol Salgado, Adam Orrick, Joel Kanamugire
 *********************************************************************************************************/
public class Game extends JPanel{

	private static final long serialVersionUID = 1L;

	 private double[] values;
	  private String[] names;
	  private String title;
	  /****************************************************************************************
	   * <pre>
	   * The Game constructor sets the name of each category, the title, and the values
	   * for each category. The category include draw, wins by scissors, wins by rock,
	   * and wins by paper.
	   * </pre>
	   * @param double[] values for paper, draws, scissors, rock.
	   * @param String[] names for each category.
		 ****************************************************************************************/
	  public Game(double[] v, String[] n) {
	    names = n;
	    values = v;
	    title = "Relative Wins and Draws with 2 Players and 1000 Rounds";
	  }
	  /****************************************************************************************
	   * <pre>
	   * The paintComponent method creates the bar graph to illustrate the game's wins and draws.
	   * </pre>
	   * @param Graphics creates all graphics context.
	   ****************************************************************************************/
	  public void paintComponent(Graphics g) {
		    double minValue = 0;
		    double maxValue = 0;
		    
		    for (int i = 0; i < values.length; i++) {
		      if (minValue > values[i])
		        minValue = values[i];
		      if (maxValue < values[i])
		        maxValue = values[i];
		    }

		    Dimension d = getSize();
		    int clientWidth = d.width;
		    int clientHeight = d.height;
		    int barWidth = clientWidth / values.length;
		    
		    // Title of Bar graph
		    Font titleFont = new Font("arial",Font.BOLD, 12);
		    FontMetrics titleMetrics = g.getFontMetrics(titleFont);
		    
		    // Title for each Category
		    Font labelFont = new Font("arial", Font.PLAIN, 10);
		    FontMetrics labelMetrics = g.getFontMetrics(labelFont);
		    
		  
		    int titleWidth = titleMetrics.stringWidth(title);
		    int y = titleMetrics.getAscent();
		    int x = (clientWidth - titleWidth) / 2;
		    g.setFont(titleFont);
		    g.drawString(title, x, y);
		    
		    int top = titleMetrics.getHeight();
		    int bottom = labelMetrics.getHeight();
		    
		    
		    double scale = (clientHeight - top - bottom) / (maxValue - minValue);
		    y = clientHeight - labelMetrics.getDescent();
		    g.setFont(labelFont);

		    for (int i = 0; i < values.length; i++) {
			      int valueX = i * barWidth + 1;
			      int valueY = top;
			      int height = (int) (values[i] * scale);
			      if (values[i] >= 0)
			        valueY += (int) ((maxValue - values[i]) * scale);
			      else {
			        valueY += (int) (maxValue * scale);
			        height = -height;
			      }
		      
		      g.setColor(Color.black);
		      g.fillRect(valueX, valueY, barWidth - 2, height);
		      g.setColor(Color.blue);
		      g.drawRect(valueX, valueY, barWidth - 2, height);
		      
		      int labelWidth = labelMetrics.stringWidth(names[i]);
		      x = i * barWidth + (barWidth - labelWidth) / 2;
		      g.drawString(names[i], x, y);
		    }
		  }
	public static void main (String args[]) {
		Player player1, player2;
		Rounds round;
		round = new Rounds();
		
		JFrame f = new JFrame();// sets the frame for the bar graph
		double[] values = new double[4];
	    String[] names = new String[4];
	    f.setSize(400, 300);
	    
		player1 = new Player (1, round);
		player2 = new Player (2, round);
		
		player1.start ();
		player2.start ();

		try {
			player1.join();
			player2.join();
		}
		catch (InterruptedException e) {}
		
			round.close();

		    values[0] = round.getscissors();// store scissor wins
		    names[0] = "Scissors";

		    values[1] = round.getpaper();// store paper wins
		    names[1] = "Paper";

		    values[2] = round.getrock();// store rock wins
		    names[2] = "Rock";
		    values[3] = round.getdraws();// store draws
		    names[3] = "Draws";

		    f.getContentPane().add(new Game(values, names));
		    f.setVisible(true);
		  }
}
/*********************************************************************************************************
 *<pre>
 *
 * The class Rounds, simulates 1000 rounds played between the two players.
 * 
 * </pre>
 *********************************************************************************************************/
class Rounds {
Random handsymbol = new Random();
private int currentRuns;
private int player2Symbol = -1,player1Symbol = -1;// stores the handsymbol of the player
private int draws, scissors, rock, paper;
private int count;
private int id;
private int outcome;
private boolean tie, p1win;
	
	/****************************************************************************************
	 * <pre>
	 * The method getdraws, returns the total number of draws.
	 * </pre>
	 ****************************************************************************************/
	public int getdraws(){
		return draws;
	}
	/****************************************************************************************
	 * <pre>
	 * The method getscissors, returns the total number of wins by scissors.
	 * </pre>
	 ****************************************************************************************/
	public int getscissors(){
		return scissors;
	}
	/****************************************************************************************
	 * <pre>
	 * The method getscissors, returns the total number of wins by scissors.
	 * </pre>
	 ****************************************************************************************/
	public int getrock(){
		return rock;
	}
	/****************************************************************************************
	 * <pre>
	 * The method getpaper, returns the total number of wins by paper.
	 * </pre>
	 ****************************************************************************************/
	public int getpaper(){
		return paper;
	}
	/****************************************************************************************
	 * <pre>
	 * The Rounds constructor, initialized the number of players, number of wins by scissors,
	 * the number of wins by paper, the number of wins by rock and the number of draws.
	 * </pre>
	 ****************************************************************************************/
	public Rounds () {
		count = 0;
		draws = 0;
		scissors = 0;
		rock = 0;
		paper = 0;
	}

	/****************************************************************************************
	 * <pre>
	 * The method close displays the number of wins by scissors, the number of wins by paper,
	 * the number of wins by rock and the number of draws after 1000 rounds.
	 * </pre>
	 ****************************************************************************************/
	public void close() {
			System.out.println("Summary Statistics: ");
			System.out.println("Number of draws: "+ draws);
			System.out.println("Number of times scissors won: "+ scissors);
			System.out.println("Number of times rock won: "+ rock );
			System.out.println("Number of times paper won: "+ paper);
	}
	// All Players are playing
	/****************************************************************************************
	 * <pre>
	 * The method isAllDone determines if player 1/player 2 is the winner, 
	 * loser or if the game is a tie.
	 * </pre>
	 * @param int passes the value for player 1 representing rock, paper, or scissor.
	 * @param int passes the value for player 2 representing rock, paper, or scissor.
	 ****************************************************************************************/
	public synchronized void isAllDone(int i1, int i2){
		// Rock = 1, Paper = 2, Scissors = 3
		//Player 1 has Rock
		 if(i1 == 1){
             if(i2 == 1){
				 tie = true;
			 }
             else if(i2 == 2){
				 p1win = false;
				 tie = false; 
			 }
             else{
				 tie = false;
				 p1win = true;
			 }
		 }
		 // Player 1 has paper
         else if(i1 == 2){
             if(i2 == 1){
				 tie = false;
                 p1win = true;
			 }
             else if(i2 == 2){
				 tie = true;
			 }
             else{
				 p1win = false;
				 tie = false;
			 }
         }
		 //Player 1 has scissor
         else{
             if(i2 == 1){
				 p1win = false;
				 tie = false;
			 }
             else if(i2 == 2){
				 tie = false;
				 p1win = true;
			 }
             else{
				 tie = true;
			 }
         }
	}
	/****************************************************************************************
	 * <pre>
	 * The method waitForOthers makes player 1 wait for player 2.
	 * </pre>
	 * 
	 ****************************************************************************************/
	public synchronized void waitForOthers(){
		count++;// count represents the number of players
		if(count < 2){
			try{
				wait();
			}
			catch(InterruptedException e){}
		}
		else {
			count = 0;
			results();
			notifyAll();
		}
	}
	/****************************************************************************************
	 * <pre>
	 * The method returns the string for hand symbol.
	 * </pre>
	 * @param int hand symbol value.
	 * @return String rock
	 * @return String paper
	 * @return String scissors
	 ****************************************************************************************/
	public String symbolToString(int symbol){
		if(symbol == 1)
			return "Rock";
		else if(symbol == 2)
			return "Paper";
		else
			return "Scissors";
	}
	/****************************************************************************************
	 * <pre>
	 * The method play assigns each player their hand symbol and passes the hand symbol
	 * of each player to isAllDone method.
	 * </pre>
	 * @param int passes the id for each player.
	 * @param int passes the current round.
	 ****************************************************************************************/
	public synchronized void play(int identity, int run){
		id = identity;
		currentRuns = run;
		outcome = 1 + handsymbol.nextInt(3);// Hand signals for each player
		
		if(id == 1){
			player1Symbol = outcome;
		}
		else if(id == 2){
			player2Symbol = outcome;
		}
		isAllDone(player1Symbol,player2Symbol);
	}
	/****************************************************************************************
	 * <pre>
	 * The result method displays the number of rounds with the players hand symbol. It 
	 * also displays if the round is a tie or which player was the winner and loser.
	 * </pre>
	 ****************************************************************************************/
	public synchronized void results(){
		
		System.out.println("Round "+ currentRuns +": Player 1" + " "+ symbolToString(player1Symbol));
		System.out.println("Round "+ currentRuns +": Player 2" + " "+ symbolToString(player2Symbol));
		if(tie){
			System.out.println("Tie!");
			draws++;
		}
		else if(p1win){
			if(player1Symbol == 1)
				rock++;
			else if(player1Symbol == 2)
				paper++;
			else
				scissors++;
			System.out.println("Player 1 wins!");
			System.out.println("Player 2 loses!");
		}
		else{
			if(player2Symbol == 1)
				rock++;
			else if(player2Symbol == 2)
				paper++;
			else
				scissors++;
			System.out.println("Player 2 wins!");
			System.out.println("Player 1 loses!");
		}
	}	
} // end of class Rounds
/*********************************************************************************************************
 *<pre>
 *
 * The class player, creates a thread for each player.
 * 
 * </pre>
 *********************************************************************************************************/
class Player extends Thread {
private int id;
private Rounds round;
private int games = 1000;// total number of rounds
/****************************************************************************************
 * <pre>
 * The player constructor initialized the id for each player and object round.
 * </pre>
 ****************************************************************************************/
public Player(int i, Rounds round) {
		id = i;
		this.round = round;
}
/****************************************************************************************
 * <pre>
 * The method run coordinates the player class with the rounds class. 
 * </pre>
 ****************************************************************************************/
public void run() {
	for(int i = 0; i < games; i++){
		round.play(id,i + 1); //start round	
		round.waitForOthers(); //wait for other player to finish
	}//end for
} //run
} // end of class Player
