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
	public Rounds (int mr) {
		maxRuns = mr;
		currentRuns = 0;
		numTies = 0;
		numWins = 0;
		numLose = 0;
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
	for (int h=0 ; h > maxRounds ; h++)
	     {
	public synchronized int isAllDone(int i1, int i2){
		// Rock = 1, Paper = 2, Scissors = 3
		//Player 1 has Rock
		 if(i1 == 1){
             if(i2 == 1){
            	 numTies++;
                 System.out.println("ITS A TIE!");}    
             else if(i2 == 2){
            	 numLose++;
                 System.out.println("You lose! Player 1" );}    
             else{
            	 numLose++;
                 System.out.println("You lose! Player 1");} 
		 }
		 // Player 1 has paper
         else if(i1 == 2){
             if(i2 == 1){
            	 numWins++;
                 System.out.println("You win! Player 1");}   
             else if(i2 == 2){
            	 numTies++;
                 System.out.println("Its a tie!");}
             else{
            	 numLose++;
                 System.out.println("You lose! Player 1"); }
         }
		 //Player 1 has scissor
         else{
             if(i2 == 1){
            	 numWins++;
                 System.out.println("You win!Player 1");}
             else if(i2 == 2){
            	 numWins++;
                 System.out.println("you win! Player 1");}
             else{
            	 numWins++;
                 System.out.println("Its a tie!");}
         }
		 return 0;
	}
	     }
	//public synchronized void outcome(numTie, );
	public synchronized void play(int id, int outcome){
		if(id == 1){
			player1Symbol = outcome;
			System.out.println("Round "+ currentRuns +": Player " + id +" "+ player1Symbol);
		}
		else{
				currentRuns++;
				player2Symbol = outcome;
				System.out.println("Round "+ currentRuns +": Player " + id +" "+ player2Symbol);
				isAllDone(player1Symbol,player2Symbol);
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
Random handsymbol = new Random();
public Player( int i, Rounds round) {
		id = i;
		rounds = round;
		outcome = 1 + handsymbol.nextInt(3);// Hand signals for each player
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
	rounds.play(id,getsymbol());
	//rounds.outcome(numdraws, numscissors, numrock, numpaper);
}
	
} // Player
