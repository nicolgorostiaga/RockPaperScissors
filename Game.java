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
private int maxRuns;
private int currentRuns;
private boolean playing;
private int symbol;
	public Rounds (int mr) {
		maxRuns = mr;
		currentRuns = 0;
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
		playing = true;
		notifyAll();
	}
	// Current number of round
	public int getruns () {
		currentRuns++;
		return (currentRuns);
	}
	// When players are playing
	public void play(int symbol){
		while(getruns() <= maxRuns){
			System.out.println("hello");
		}
	}
/*public synchronized boolean checkOut(int id, boolean playing, int currentRuns) {
	
	return playing;

}*/
} // Rounds


class Player extends Thread {
private int id;
private Rounds rounds;
private String outcome;
private int numdraws;
private int numscissors;
private int numrock;
private int numpaper;

public Player ( int i, Rounds round) {
		id = i;
		rounds = round;
	}
public int getdraws(){
	return numdraws;
}
public int getscissorwins(){
	return numscissors;
}
public int getrockwins(){
	return numrock;
}
public int getpaperwins(){
	return numpaper;
}
public int gethandsymbol(){
	Random handsymbol = new Random();
	int outcome = 1 + handsymbol.nextInt(3);
	return outcome;
}
public void run () {
	rounds.play(gethandsymbol());
	
}
	
} // Player
