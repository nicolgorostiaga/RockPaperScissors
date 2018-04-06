import java.util.Scanner;

public class Game {
	
	public static void main (String args[]) {
		Player player1, player2;
		Rounds round;
		int maxRounds = 1000;
		round = new Rounds(maxRounds);
		player1 = new Player (0, round);
		player2 = new Player (1, round);

		player1.start ();
		player2.start ();
		
		try {
		player1.join();
		player2.join();
		}
		catch (InterruptedException e) {}
		if(round.getruns() < maxRounds){
			System.out.println("Summary Statistics: ");
			System.out.println("Number of draws: ");
			System.out.println("Number of times scissors won: ");
			System.out.println("Number of times rock won: ");
			System.out.println("Number of times paper won: ");
		}
		round.close();
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
public void close () {

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

public Player ( int i, Rounds round) {
		id = i;
		rounds = round;
	}
public void run () {
	
	}
} // Student
