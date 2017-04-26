import java.io.FileNotFoundException;

public class CircuitBoardTester {

	public static void main(String[] args) throws FileNotFoundException {
		
		CircuitBoard board = new CircuitBoard(args[0]);
		System.out.println(board.toString());
	}

}
