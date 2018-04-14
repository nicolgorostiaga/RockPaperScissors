import java.awt.Color;
import java.util.Random;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
/*********************************************************************************************************
 *<pre>
 * File: Game.java
 * 
 * The class called Game, simulates a rock, paper, scissors game between two players and 1000 rounds.
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
	 * of each category. The category include draw, wins by scissors, wins by rock,
	 * and wins by paper.
	 * </pre>
	 * @param double[] values for paper, draws, scissors, rock.
	 * @param String[] names for each category.
	 * @param String[] title of the graph.
		 ****************************************************************************************/
	  public Game(double[] v, String[] n, String t) {
	    names = n;
	    values = v;
	    title = "Relative Wins and Draws with 2 Players and 1000 Rounds";
	  }
	  /****************************************************************************************
		 * 
		 * 
		 * 
		 * 
		 ****************************************************************************************/
	  public void paintComponent(Graphics g) {
		    super.paintComponent(g);
		    if (values == null || values.length == 0)
		      return;
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

		    Font titleFont = new Font("SansSerif", Font.BOLD, 12);
		    FontMetrics titleFontMetrics = g.getFontMetrics(titleFont);
		    Font labelFont = new Font("SansSerif", Font.PLAIN, 10);
		    FontMetrics labelFontMetrics = g.getFontMetrics(labelFont);

		    int titleWidth = titleFontMetrics.stringWidth(title);
		    int y = titleFontMetrics.getAscent();
		    int x = (clientWidth - titleWidth) / 2;
		    g.setFont(titleFont);
		    g.drawString(title, x, y);

		    int top = titleFontMetrics.getHeight();
		    int bottom = labelFontMetrics.getHeight();
		    if (maxValue == minValue)
		      return;
		    double scale = (clientHeight - top - bottom) / (maxValue - minValue);
		    y = clientHeight - labelFontMetrics.getDescent();
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

		      g.setColor(Color.BLACK);
		      g.fillRect(valueX, valueY, barWidth - 2, height);
		      g.setColor(Color.black);
		      g.drawRect(valueX, valueY, barWidth - 2, height);
		      int labelWidth = labelFontMetrics.stringWidth(names[i]);
		      x = i * barWidth + (barWidth - labelWidth) / 2;
		      g.drawString(names[i], x, y);
		    }
		  }
	public static void main (String args[]) {
		Player player1, player2;
		Rounds round;
		round = new Rounds();
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
		//Graph
		 JFrame f = new JFrame();
		    f.setSize(400, 300);
		    double[] values = new double[4];
		    String[] names = new String[4];
		    values[0] = 1;
		    names[0] = "Scissors";

		    values[1] = 2;
		    names[1] = "Paper";

		    values[2] = 3;
		    names[2] = "Rock";
		    values[3] = 4;
		    names[3] = "Draws";

		    f.getContentPane().add(new Game(values, names, "title"));

		    WindowListener wndCloser = new WindowAdapter() {
		      public void windowClosing(WindowEvent e) {
		        System.exit(0);
		      }
		    };
		    f.addWindowListener(wndCloser);
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
	public Rounds () {
		count = 0;
	}

	/****************************************************************************************
	 * 
	 * 
	 * 
	 * 
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
	 * 
	 * 
	 * 
	 * 
	 ****************************************************************************************/
	public synchronized int isAllDone(int i1, int i2){
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
		 
		 return 0;
	}
	/****************************************************************************************
	 * 
	 * 
	 * 
	 * 
	 ****************************************************************************************/
	public synchronized void waitForOthers(){
		count++;
		//count == 0 means only 1 player has played
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
	 * The method returns the string for each category.
	 * </pre>
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
	 * 
	 * 
	 * 
	 * 
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
	 * 
	 * 
	 * 
	 * 
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
} // Rounds
/*********************************************************************************************************
 *<pre>
 *
 * The Player class, creates a thread for each player.
 * 
 * </pre>
 *********************************************************************************************************/
class Player extends Thread {
private int id;
private Rounds round;
private int games = 1000;
/****************************************************************************************
 * 
 * 
 * 
 * 
 ****************************************************************************************/
public Player(int i, Rounds round) {
		id = i;
		this.round = round;
}
/****************************************************************************************
 * 
 * 
 * 
 * 
 ****************************************************************************************/
public void run() {
	for(int i = 0; i < games; i++){
		round.play(id,i + 1); //start round	
		round.waitForOthers(); //wait for other player to finish
	}//end for
} //run
} // Player
