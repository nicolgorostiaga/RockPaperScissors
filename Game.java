public class Game {
	
	public static void main (String args[]) {
		Player player1, player2;
		Rounds round;
		int draws,scissors,rock,paper;
		int maxRounds = 1000;
		round = new Rounds(maxRounds);
		player1 = new Player (1, round);
		player2 = new Player (2, round);

		player1.start ();
		player2.start ();
		
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

	public Rounds (int mr) {
		maxRuns = mr;
		currentRuns = 0;
		playing = true;
	}

	public void close (Rounds round,int maxRounds,int draws, int scissors, int rock, int paper) {
		if(round.getruns() < maxRounds){
			System.out.println("Summary Statistics: ");
			System.out.println("Number of draws: "+ draws);
			System.out.println("Number of times scissors won: "+ scissors);
			System.out.println("Number of times rock won: "+ rock );
			System.out.println("Number of times paper won: "+ paper);
		}
	}
	public int getruns () {
		return ( currentRuns + 1);
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
public void run () {
	int handsymbol = (int)(Math.random()*2);
	switch(handsymbol){
		case 0: outcome = "Rock";
		break;
		case 1: outcome = "Paper";
		break;
		case 2: outcome = "Scissor";
		break;
	}
}
	
} // Player
