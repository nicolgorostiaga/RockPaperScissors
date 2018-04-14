import java.util.Random;

public class Game {
	
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
	}
} // RunLibrary

class Rounds {
	
Random handsymbol = new Random();
private int currentRuns;
private int player2Symbol = -1,player1Symbol = -1;// stores the handsymbol of the player
private int draws, scissors, rock, paper;
private int count;
private int id;
private int outcome;
private boolean[] doneplaying;
private boolean tie, p1win;
	public Rounds () {
		count = 0;
	}


	public void close() {
			System.out.println("Summary Statistics: ");
			System.out.println("Number of draws: "+ draws);
			System.out.println("Number of times scissors won: "+ scissors);
			System.out.println("Number of times rock won: "+ rock );
			System.out.println("Number of times paper won: "+ paper);
	}
	// All Players are playing
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
	//wait for other player to finish
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
	public String symbolToString(int symbol){
		if(symbol == 1)
			return "Rock";
		else if(symbol == 2)
			return "Paper";
		else
			return "Scissors";
	}
	
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
	
	//results for round
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


class Player extends Thread {
private int id;
private Rounds round;
//private int outcome;
private int games = 1000;
private int[] rand = new int[1000];
//Random handsymbol = new Random();

public Player(int i, Rounds round) {
		id = i;
		this.round = round;
}

/*
private void genRandIntArray(){
	for(int i = 0; i < games; i++){
		rand[i] = 1 + handsymbol.nextInt(3);
	}
}
*/
public void run() {
	//genRandIntArray();
	for(int i = 0; i < games; i++){
		//outcome = 1 + handsymbol.nextInt(3);// Hand signals for each player
		round.play(id,i + 1); //start round	
		round.waitForOthers(); //wait for other player to finish
		//round.results(); //print round results
	}//end for
} //run
} // Player
