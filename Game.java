import java.util.Random;

public class Game {
	
	public static void main (String args[]) {
		Player player1, player2;
		Rounds round;
		int draws,scissors,rock,paper;
		int maxRounds = 5;// This value should be 1000 but for testing purposes is 5.
		round = new Rounds(maxRounds);
		player1 = new Player (1, round);
		player2 = new Player (2, round);
		
		player1.start ();
		player2.start ();
		
		round.open();
		try {
		player1.join();
		player2.join();
		}
		catch (InterruptedException e) {}
		
		draws = player1.getdraws()+player2.getdraws();
		scissors = player1.getscissorwins()+ player2.getscissorwins();
		rock = player1.getrockwins()+player2.getrockwins();
		paper = player1.getpaperwins()+player2.getpaperwins();
		
		round.close(round,maxRounds,draws, scissors, rock, paper);
		}
} // RunLibrary

class Rounds {

private boolean[] doneplaying;
private int maxRuns;
private int currentRuns;
private int player2Symbol,player1Symbol;// stores the handsymbol of the player
private int numTies; // number of Ties
private int numWins;// number of wins
private int numLose;// number of lose
private int count;
private int id;
private boolean tie = false,p1win = false,p2win = false;
	public Rounds (int mr) {
		maxRuns = mr;
		currentRuns = 0;
		numTies = 0;
		numWins = 0;
		numLose = 0;
		count = 0;
	}
	public int getmaxRounds(){
		return maxRuns;
	}
	public int getcurrentRuns(){
		return currentRuns;
	}
	public void close (Rounds round,int maxRounds,int draws, int scissors, int rock, int paper) {
			System.out.println("Summary Statistics: ");
			System.out.println("Number of draws: "+ draws);
			System.out.println("Number of times scissors won: "+ scissors);
			System.out.println("Number of times rock won: "+ rock );
			System.out.println("Number of times paper won: "+ paper);
	}
	// All Players are playing
	public synchronized void open(){
		doneplaying = new boolean[1];
		for(int i = 0; i < doneplaying.length;i++){
			doneplaying[i]= false;
		}
		notifyAll();
	}
	public synchronized void setDone(int i){
		doneplaying[i] = false;
	}
	public synchronized int isAllDone(int i1, int i2){
		// Rock = 1, Paper = 2, Scissors = 3
		//Player 1 has Rock
		count++;
		
		 if(i1 == 1){
             if(i2 == 1){
            	 numTies++;
				 tie = true;
			 }
             else if(i2 == 2){
				 p1win = false;
				 tie = false;
            	 numLose++; 
			 }
             else{
            	 numLose++;
				 tie = false;
				 p1win = true;
			 }
		 }
		 // Player 1 has paper
         else if(i1 == 2){
             if(i2 == 1){
            	 numWins++;
				 tie = false;
                 p1win = true;
			 }
             else if(i2 == 2){
            	 numTies++;
				 tie = true;
			 }
             else{
            	 numLose++;
				 p1win = false;
				 tie = false;
			 }
         }
		 //Player 1 has scissor
         else{
             if(i2 == 1){
            	 numWins++;
				 p1win = true;
				 tie = false;
			 }
             else if(i2 == 2){
            	 numWins++;
				 tie = false;
				 p1win = false;
			 }
             else{
            	 numWins++;
				 tie = true;
			 }
         }
		 return 0;
	}
	public synchronized void waitForOthers(){
		if(count < 1){
			try{
				wait();
			}
			catch(InterruptedException e){}
		}
		else {
			count = 0;
			currentRuns++;
			notifyAll();
		}
	}
	public String symbolToString(int symbol){
		if(symbol == 1)
			return "Rock";
		else if(symbol == 2)
			return "Paper";
		else
			return "Scissors";
	}
	//public synchronized void outcome(numTie, );
	public synchronized void play(int identity, int outcome){
		id = identity;
		
		if(id == 1){
			player1Symbol = outcome;
		}
		else{
			player2Symbol = outcome;
		}
		isAllDone(player1Symbol,player2Symbol);
	}
	public synchronized void results(){
		System.out.println("Round "+ currentRuns +": Player 1" + " "+ symbolToString(player1Symbol));
		System.out.println("Round "+ currentRuns +": Player 2" + " "+ symbolToString(player2Symbol));
		if(tie)
			System.out.println("Tie!");
		else if(p1win){
			System.out.println("Player 1 wins!");
			System.out.println("Player 2 loses!");
		}
		else{
			System.out.println("Player 2 wins!");
			System.out.println("Player 1 loses!");
		}
	}	
} // Rounds


class Player extends Thread {
private int id;
private Rounds rounds;
private int numdraws;
private int outcome;
private int numscissors;
private int numrock;
private int numpaper;
private int games = 5;
Random handsymbol = new Random();

public Player( int i, Rounds round) {
		id = i;
		rounds = round;
		outcome = 0;
}
public int getid(){
	return id;
}
// Number of Draws
public void setdraws(int numdraws){
	numdraws++;
}
public int getdraws(){
	return numdraws;
}
// Number of Wins by scissors
public void setscissors(int numsissors){
	numscissors++;
}
public int getscissorwins(){
	return numscissors;
}
// Number of Wins by Rock
public void setrock(int numrock){
	numrock++;
}
public int getrockwins(){
	return numrock;
}
// Number of Wins by Paper
public void setpaper(int numpaper){
	numpaper++;
}
public int getpaperwins(){
	return numpaper;
}
// Hand Symbol of each player
public int getsymbol(){
	return outcome;
}

public void run () {
	
	for(int i = 0; i < games; i++){
		outcome = 1 + handsymbol.nextInt(3);// Hand signals for each player
		rounds.play(id,outcome);
		rounds.waitForOthers();
		rounds.results();
	}
	//rounds.outcome(numdraws, numscissors, numrock, numpaper);
}
	
} // Player
