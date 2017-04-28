import java.awt.Point;
import java.io.FileNotFoundException;
import java.util.ArrayList;


/**
 * Search for shortest paths between start and end points on a circuit board
 * as read from an input file using either a stack or queue as the underlying
 * search state storage structure and displaying output to the console or to
 * a GUI according to options specified via command-line arguments.
 * 
 * @author mvail
 */
public class CircuitTracer {
	private CircuitBoard board;
	private Storage<TraceState> stateStore;
	private ArrayList<TraceState> bestPaths;

	/** launch the program
	 * @param args three required arguments:
	 *  first arg: -s for stack or -q for queue
	 *  second arg: -c for console output or -g for GUI output
	 *  third arg: input file name 
	 */
	public static void main(String[] args) {
		if (args.length != 3) {
			printUsage();
			System.exit(1);
		}
		try {
			new CircuitTracer(args); //create this with args
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	/** Print instructions for running CircuitTracer from the command line. */
	private static void printUsage() {
		//TODO: print out clear usage instructions when there are problems with
		// any command line args
		System.out.println("Usage: $ java CircuitTracer stack(-s)/queue(-q)" 
							+"console(-c)/GUI(-g) file");
	}
	
	/** 
	 * Set up the CircuitBoard and all other components based on command
	 * line arguments.
	 * 
	 * @param args command line arguments passed through from main()
	 */
	private CircuitTracer(String[] args) {
		//TODO: parse command line args
		/*
		 * checks args and assigns variables if valid, prints usage if invalid
		 */
		IUDoubleLinkedList<String> validFirstArgs = new IUDoubleLinkedList<String>();
		validFirstArgs.add("-s");
		validFirstArgs.add("-q");
		String firstArg = args[0]; //first argument
		if(!validFirstArgs.contains(firstArg)){
			printUsage();
		}
		IUDoubleLinkedList<String> validSecondArgs = new IUDoubleLinkedList<String>();
		validSecondArgs.add("-c");
		validSecondArgs.add("-g");
		String secondArg = args[1]; //second argument
		if(!validSecondArgs.contains(secondArg)){
			printUsage();
		}
		String filename = args[2]; //filename
		if(secondArg.equals("-g")){
			System.out.println("GUI(-q) not supported, running in console(-c).\n");
			secondArg = "-c";
		}
		//TODO: initialize the Storage to use either a stack or queue
		if(firstArg.equals("-s")){
			stateStore = new Storage<TraceState>(Storage.DataStructure.stack);
		}else if(firstArg.equals("-q")){
			stateStore = new Storage<TraceState>(Storage.DataStructure.queue);
		}
		//TODO: read in the CircuitBoard from the given file
		try {
			board = new CircuitBoard(filename);
		} catch (FileNotFoundException e) {
			System.err.println("File not found.");
			return;
		} catch (InvalidFileFormatException e){
			System.err.println("Invalid file format.");
			return;
		} catch (NumberFormatException e){
			System.err.println("Invalid number format.");
			return;
		}
		//TODO: run the search for best paths
		bestPaths = new ArrayList<TraceState>();
		/*
		 * manual solution to finding first trace points
		 */
		if(board.isOpen(board.getStartingPoint().x + 1, board.getStartingPoint().y)){ //checks point below starting
			TraceState below = new TraceState(board, board.getStartingPoint().x + 1, board.getStartingPoint().y);
			stateStore.store(below);
		}
		if(board.isOpen(board.getStartingPoint().x - 1, board.getStartingPoint().y)){ //checks point above starting
			TraceState above = new TraceState(board, board.getStartingPoint().x - 1, board.getStartingPoint().y);
			stateStore.store(above);
		}
		if(board.isOpen(board.getStartingPoint().x, board.getStartingPoint().y - 1)){ //checks point left of starting
			TraceState left = new TraceState(board, board.getStartingPoint().x, board.getStartingPoint().y - 1);
			stateStore.store(left);
		}
		if(board.isOpen(board.getStartingPoint().x, board.getStartingPoint().y + 1)){ //checks point right of starting
			TraceState right = new TraceState(board, board.getStartingPoint().x, board.getStartingPoint().y + 1);
			stateStore.store(right);
		}
		while(!stateStore.isEmpty()){
			TraceState nextTrace = stateStore.retreive();
			if(nextTrace.isComplete()){
				if(!bestPaths.isEmpty()){
					if(nextTrace.pathLength() == bestPaths.get(0).pathLength()){
						bestPaths.add(nextTrace);
					}else if(nextTrace.pathLength() < bestPaths.get(0).pathLength()){
						bestPaths.clear();
						bestPaths.add(nextTrace);
					}
				}else{
					bestPaths.add(nextTrace);
				}
			}else{ //generate all valid next TraceState objects from the current TraceState and add them to stateStore
				if(nextTrace.getBoard().isOpen(nextTrace.getRow() + 1, nextTrace.getCol())){ //checks point below nextTrace
					TraceState newBelow = new TraceState(nextTrace, nextTrace.getRow() + 1, nextTrace.getCol());
					stateStore.store(newBelow);
				}
				if(nextTrace.getBoard().isOpen(nextTrace.getRow() - 1, nextTrace.getCol())){ //checks point above nextTrace
					TraceState newAbove = new TraceState(nextTrace, nextTrace.getRow() - 1, nextTrace.getCol());
					stateStore.store(newAbove);
				}
				if(nextTrace.getBoard().isOpen(nextTrace.getRow(), nextTrace.getCol() - 1)){ //checks point left of nextTrace
					TraceState newLeft = new TraceState(nextTrace, nextTrace.getRow(), nextTrace.getCol() - 1);
					stateStore.store(newLeft);
				}
				if(nextTrace.getBoard().isOpen(nextTrace.getRow(), nextTrace.getCol() + 1)){ //checks point right of nextTrace
					TraceState newRight = new TraceState(nextTrace, nextTrace.getRow(), nextTrace.getCol() + 1);
					stateStore.store(newRight);
				}
			}
		}
		//TODO: output results to console or GUI, according to specified choice
		if(bestPaths.isEmpty()){
			System.out.println("No solutions.");
		}
		for(TraceState t: bestPaths){
			System.out.println(t.getBoard().toString());
		}
	}
	
	
	
} // class CircuitTracer
