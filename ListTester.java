import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * A unit test class for lists that implement IndexedUnsortedList. 
 * This set of black box tests should work for any implementation
 * of this interface.
 * 
 * NOTE: One example test is given for each interface method using a new list to
 * get you started.
 * 
 * @author mvail, mhthomas
 * @author Nathan King
 */
public class ListTester {
	//possible lists that could be tested
	private enum ListToUse {
		goodList, badList, arrayList, singleLinkedList, doubleLinkedList
	};

	// TODO: THIS IS WHERE YOU CHOOSE WHICH LIST TO TEST
	private final ListToUse LIST_TO_USE = ListToUse.doubleLinkedList;

	// possible results expected in tests
	private enum Result {
		NoSuchElement, IndexOutOfBounds, IllegalState, 
		ConcurrentModification, UnsupportedOperation,  
		NoException, UnexpectedException,
		True, False, Pass, Fail, 
		MatchingValue,
		ValidString
	};

	// named elements for use in tests
	private static final Integer ELEMENT_A = new Integer(1);
	private static final Integer ELEMENT_B = new Integer(2);
	private static final Integer ELEMENT_C = new Integer(3);
	private static final Integer ELEMENT_D = new Integer(4);

	// instance variables for tracking test results
	private int passes = 0;
	private int failures = 0;
	private int total = 0;

	/**
	 * @param args not used
	 */
	public static void main(String[] args) {
		// to avoid every method being static
		ListTester tester = new ListTester();
		tester.runTests();
	}

	/**
	 * Print test results in a consistent format
	 * 
	 * @param testDesc description of the test
	 * @param result indicates if the test passed or failed
	 */
	private void printTest(String testDesc, boolean result) {
		total++;
		if (result) { passes++; }
		else { failures++; }
		System.out.printf("%-46s\t%s\n", testDesc, (result ? "   PASS" : "***FAIL***"));
	}

	/** Print a final summary */
	private void printFinalSummary() {
		System.out.printf("\nTotal Tests: %d,  Passed: %d,  Failed: %d\n",
				total, passes, failures);
	}

	/** XXX runTests()
	 *  XXX <- see the blue box on the right of the scroll bar? this tag aids in navigating long files
	 * Run tests to confirm required functionality from list constructors and methods */
	private void runTests() {
		//recommended scenario naming: start_change_result
		test_newList(); // #1 aka noList_constructor_emptyList
		test_emptyList_addToFrontA_A(); //#2 bonus		
		test_A_addToFrontB_BA(); //aka Scenario 6
		test_ABC_remove1_AC(); //aka Scenario 39
		// # of added tests: 21
		test_emptyList_addToRearA_A(); //#3 Completed* 
		test_emptyList_addA_A(); //#4 Completed* 		
		test_emptyList_add0A_A(); //#5 Completed* 

		test_A_addToRearB_AB(); //#7 Completed* 		
		test_A_addAfterBA_AB(); //#8 Completed*
		test_A_addB_AB(); //#9 Completed*
		test_A_add0B_BA(); //#10 Completed* 
		test_A_add1B_AB(); //#11 Completed*
		test_A_removeFirst_emptyList(); //#12 Completed*
		test_A_removeLast_emptyList(); //#13 Completed*
		test_A_removeA_emptyList(); //#14 Completed* 
		test_A_remove0_emptyList(); //#15 Completed* 
		test_A_set0B_B(); //#16 Completed*

		test_AB_removeFirst_B(); //#25 Completed*
		test_AB_removeLast_A(); //#26 Completed*
		test_AB_removeA_B(); //#27 Completed*

		test_AB_set1C_AC(); //#32 Completed* 
		test_ABC_removeLast_AB(); //#34 Completed* 	
		test_ABC_removeC_AB(); //#37 Completed*	
		test_ABC_removeB_AC(); //#36 Completed*  		
		test_ABC_remove2_AB(); //#40 Completed* 			
		
		// example iter test
		test_A_iterRemoveAfterNextA_emptyList();
		//listIterator tests
		test_A_iterRemoveAfterPreviousA_emptyList(); //#56 Completed*
		test_AB_iterRemoveAfterPreviousA_B(); //#57 Completed*
		test_A_iterAddBAfterPreviousA_BA(); //#65 Completed*
		test_A_iterSetBAfterPreviousA_B(); //#72 Completed*
		// provided test
		test_IterConcurrency();
		// report final verdict
		printFinalSummary();
	}

	//////////////////////////////////////
	// XXX SCENARIO: NEW EMPTY LIST
	// XXX <- see the blue box on the right? this tag aids in navigating a long file
	//////////////////////////////////////

	/**
	 * Returns a IndexedUnsortedList for the "new empty list" scenario.
	 * Scenario: no list -> constructor -> [ ]
	 * 
	 * @return a new, empty IndexedUnsortedList
	 */
	private IndexedUnsortedList<Integer> newList() {
		IndexedUnsortedList<Integer> listToUse;
		switch (LIST_TO_USE) {
//		case goodList:
//			listToUse = new GoodList<Integer>();
//			break;
//		case badList:
//			listToUse = new BadList<Integer>();
//			break;
//		case arrayList:
//			listToUse = new IUArrayList<Integer>();
//			break;
//		case singleLinkedList:
//			listToUse = new IUSingleLinkedList<Integer>();
//			break;
		case doubleLinkedList:
			listToUse = new IUDoubleLinkedList<Integer>();
			break;
		default:
			listToUse = null;
		}
		return listToUse;
	}

	/** Run all tests on scenario: no list -> constructor -> [ ] */
	private void test_newList() {
		// recommended test naming: start_change_result_testName
		// e.g. A_addToFront_BA_testSize
		// AB_addC1_ACB_testFirst
		// A_remove0_empty_testLast

		System.out.println("\nSCENARIO: no list -> constructor -> []\n");
		//try-catch prevents an Exception from the scenario builder 
		// method from bringing down the whole test suite
		try {
			printTest("newList_testAddToFrontA", testAddToFront(newList(), ELEMENT_A, Result.NoException));
			printTest("newList_testAddToRearA", testAddToRear(newList(), ELEMENT_A, Result.NoException));
			printTest("newList_testAddAfterBA", testAddAfter(newList(), ELEMENT_B, ELEMENT_A, Result.NoSuchElement));
			printTest("newList_testAddAtIndexNeg1", testAddAtIndex(newList(), -1, ELEMENT_A, Result.IndexOutOfBounds));
			printTest("newList_testAddAtIndex0", testAddAtIndex(newList(), 0, ELEMENT_A, Result.NoException));
			printTest("newList_testAddAtIndex1", testAddAtIndex(newList(), 1, ELEMENT_A, Result.IndexOutOfBounds));
			printTest("newList_testAddA", testAdd(newList(), ELEMENT_A, Result.NoException));
			printTest("newList_testRemoveFirst", testRemoveFirst(newList(), null, Result.IllegalState));
			printTest("newList_testRemoveLast", testRemoveLast(newList(), null, Result.IllegalState));
			printTest("newList_testRemoveA", testRemoveElement(newList(), null, Result.NoSuchElement));
			printTest("newList_testRemoveNeg1", testRemoveIndex(newList(), -1, null, Result.IndexOutOfBounds));
			printTest("newList_testRemove0", testRemoveIndex(newList(), 0, null, Result.IndexOutOfBounds));
			printTest("newList_testFirst", testFirst(newList(), null, Result.IllegalState));
			printTest("newList_testLast", testLast(newList(), null, Result.IllegalState));
			printTest("newList_testContainsA", testContains(newList(), ELEMENT_A, Result.False));
			printTest("newList_testIsEmpty", testIsEmpty(newList(), Result.True));
			printTest("newList_testSize", testSize(newList(), 0));
			printTest("newList_testToString", testToString(newList(), Result.ValidString));
			printTest("newList_testSetNeg1A", testSet(newList(), -1, ELEMENT_A, Result.IndexOutOfBounds));
			printTest("newList_testSet0A", testSet(newList(), 0, ELEMENT_A, Result.IndexOutOfBounds));
			printTest("newList_testGetNeg1", testGet(newList(), -1, null, Result.IndexOutOfBounds));
			printTest("newList_testGet0", testGet(newList(), 0, null, Result.IndexOutOfBounds));
			printTest("newList_testIndexOfA", testIndexOf(newList(), ELEMENT_A, -1));
			// Iterator
			printTest("newList_testIter", testIter(newList(), Result.NoException));
			printTest("newList_testIterHasNext", testIterHasNext(newList().iterator(), Result.False));
			printTest("newList_testIterNext", testIterNext(newList().iterator(), null, Result.NoSuchElement));
			printTest("newList_testIterRemove", testIterRemove(newList().iterator(), Result.IllegalState));
			// ListIterator
			printTest("newList_testListIter", testListIter(newList(), Result.NoException));
			printTest("newList_testListIter", testListIter(newList(), 0, Result.NoException));
		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "test_newList");
			e.printStackTrace();
		}
	}

	////////////////////////////////////////////////
	// XXX SCENARIO: [A] -> removeFirst() -> [ ]
	////////////////////////////////////////////////
	/** Scenario: [A] -> removeFirst() -> [ ] 
	 * @return [ ] after removeFirst()
	 */
	private IndexedUnsortedList<Integer> A_removeFirst_emptyList() {
		IndexedUnsortedList<Integer> list = emptyList_addToFrontA_A(); //starting state
		list.removeFirst(); //the change
		return list;
	}
	/** Run all tests on scenario: [A] -> removeFirst() -> [ ] */
	private void test_A_removeFirst_emptyList() {
		System.out.println("\nSCENARIO: [A] -> removeFirst() -> [ ]\n");
		try{
			printTest("A_removeFirst_emptyList_testAddToFrontA", testAddToFront(A_removeFirst_emptyList(), ELEMENT_A, Result.NoException));
			printTest("A_removeFirst_emptyList_testAddToRearA", testAddToRear(A_removeFirst_emptyList(), ELEMENT_A, Result.NoException));
			printTest("A_removeFirst_emptyList_testAddAfterBA", testAddAfter(A_removeFirst_emptyList(), ELEMENT_B, ELEMENT_A, Result.NoSuchElement));
			printTest("A_removeFirst_emptyList_testAddAtIndexNeg1", testAddAtIndex(A_removeFirst_emptyList(), -1, ELEMENT_A, Result.IndexOutOfBounds));
			printTest("A_removeFirst_emptyList_testAddAtIndex0", testAddAtIndex(A_removeFirst_emptyList(), 0, ELEMENT_A, Result.NoException));
			printTest("A_removeFirst_emptyList_testAddAtIndex1", testAddAtIndex(A_removeFirst_emptyList(), 1, ELEMENT_A, Result.IndexOutOfBounds));
			printTest("A_removeFirst_emptyList_testAddA", testAdd(A_removeFirst_emptyList(), ELEMENT_A, Result.NoException));
			printTest("A_removeFirst_emptyList_testRemoveFirst", testRemoveFirst(A_removeFirst_emptyList(), null, Result.IllegalState));
			printTest("A_removeFirst_emptyList_testRemoveLast", testRemoveLast(A_removeFirst_emptyList(), null, Result.IllegalState));
			printTest("A_removeFirst_emptyList_testRemoveA", testRemoveElement(A_removeFirst_emptyList(), null, Result.NoSuchElement));
			printTest("A_removeFirst_emptyList_testRemoveNeg1", testRemoveIndex(A_removeFirst_emptyList(), -1, null, Result.IndexOutOfBounds));
			printTest("A_removeFirst_emptyList_testRemove0", testRemoveIndex(A_removeFirst_emptyList(), 0, null, Result.IndexOutOfBounds));
			printTest("A_removeFirst_emptyList_testFirst", testFirst(A_removeFirst_emptyList(), null, Result.IllegalState));
			printTest("A_removeFirst_emptyList_testLast", testLast(A_removeFirst_emptyList(), null, Result.IllegalState));
			printTest("A_removeFirst_emptyList_testContainsA", testContains(A_removeFirst_emptyList(), ELEMENT_A, Result.False));
			printTest("A_removeFirst_emptyList_testIsEmpty", testIsEmpty(A_removeFirst_emptyList(), Result.True));
			printTest("A_removeFirst_emptyList_testSize", testSize(A_removeFirst_emptyList(), 0));
			printTest("A_removeFirst_emptyList_testToString", testToString(A_removeFirst_emptyList(), Result.ValidString));
			printTest("A_removeFirst_emptyList_testSetNeg1A", testSet(A_removeFirst_emptyList(), -1, ELEMENT_A, Result.IndexOutOfBounds));
			printTest("A_removeFirst_emptyList_testSet0A", testSet(A_removeFirst_emptyList(), 0, ELEMENT_A, Result.IndexOutOfBounds));
			printTest("A_removeFirst_emptyList_testGetNeg1", testGet(A_removeFirst_emptyList(), -1, null, Result.IndexOutOfBounds));
			printTest("A_removeFirst_emptyList_testGet0", testGet(A_removeFirst_emptyList(), 0, null, Result.IndexOutOfBounds));
			printTest("A_removeFirst_emptyList_testIndexOfA", testIndexOf(A_removeFirst_emptyList(), ELEMENT_A, -1));
			// Iterator
			printTest("A_removeFirst_emptyList_testIter", testIter(A_removeFirst_emptyList(), Result.NoException));
			printTest("A_removeFirst_emptyList_testIterHasNext", testIterHasNext(A_removeFirst_emptyList().iterator(), Result.False));
			printTest("A_removeFirst_emptyList_testIterNext", testIterNext(A_removeFirst_emptyList().iterator(), null, Result.NoSuchElement));
			printTest("A_removeFirst_emptyList_testIterRemove", testIterRemove(A_removeFirst_emptyList().iterator(), Result.IllegalState));
			// ListIterator
			printTest("A_removeFirst_emptyList_testListIter", testListIter(A_removeFirst_emptyList(), Result.NoException));
			printTest("A_removeFirst_emptyList_testListIter", testListIter(A_removeFirst_emptyList(), 0, Result.NoException));
		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "A_removeFirst_emptyList");
			e.printStackTrace();
		}
	}

	////////////////////////////////////////////////
	// XXX SCENARIO: [A] -> removeLast() -> [ ]
	////////////////////////////////////////////////
	/** Scenario: [A] -> removeLast() -> [ ] 
	 * @return [ ] after removeLast()
	 */
	private IndexedUnsortedList<Integer> A_removeLast_emptyList() {
		IndexedUnsortedList<Integer> list = emptyList_addToFrontA_A(); //starting state
		list.removeLast(); //the change
		return list;
	}
	/** Run all tests on scenario: [A] -> removeFirst() -> [ ] */
	private void test_A_removeLast_emptyList() {
		System.out.println("\nSCENARIO: [A] -> removeLast() -> [ ]\n");
		try{
			printTest("A_removeLast_emptyList_testAddToFrontA", testAddToFront(A_removeLast_emptyList(), ELEMENT_A, Result.NoException));
			printTest("A_removeLast_emptyList_testAddToRearA", testAddToRear(A_removeLast_emptyList(), ELEMENT_A, Result.NoException));
			printTest("A_removeLast_emptyList_testAddAfterBA", testAddAfter(A_removeLast_emptyList(), ELEMENT_B, ELEMENT_A, Result.NoSuchElement));
			printTest("A_removeLast_emptyList_testAddAtIndexNeg1", testAddAtIndex(A_removeLast_emptyList(), -1, ELEMENT_A, Result.IndexOutOfBounds));
			printTest("A_removeLast_emptyList_testAddAtIndex0", testAddAtIndex(A_removeLast_emptyList(), 0, ELEMENT_A, Result.NoException));
			printTest("A_removeLast_emptyList_testAddAtIndex1", testAddAtIndex(A_removeLast_emptyList(), 1, ELEMENT_A, Result.IndexOutOfBounds));
			printTest("A_removeLast_emptyList_testAddA", testAdd(A_removeLast_emptyList(), ELEMENT_A, Result.NoException));
			printTest("A_removeLast_emptyList_testRemoveFirst", testRemoveFirst(A_removeLast_emptyList(), null, Result.IllegalState));
			printTest("A_removeLast_emptyList_testRemoveLast", testRemoveLast(A_removeLast_emptyList(), null, Result.IllegalState));
			printTest("A_removeLast_emptyList_testRemoveA", testRemoveElement(A_removeLast_emptyList(), null, Result.NoSuchElement));
			printTest("A_removeLast_emptyList_testRemoveNeg1", testRemoveIndex(A_removeLast_emptyList(), -1, null, Result.IndexOutOfBounds));
			printTest("A_removeLast_emptyList_testRemove0", testRemoveIndex(A_removeLast_emptyList(), 0, null, Result.IndexOutOfBounds));
			printTest("A_removeLast_emptyList_testFirst", testFirst(A_removeLast_emptyList(), null, Result.IllegalState));
			printTest("A_removeLast_emptyList_testLast", testLast(A_removeLast_emptyList(), null, Result.IllegalState));
			printTest("A_removeLast_emptyList_testContainsA", testContains(A_removeLast_emptyList(), ELEMENT_A, Result.False));
			printTest("A_removeLast_emptyList_testIsEmpty", testIsEmpty(A_removeLast_emptyList(), Result.True));
			printTest("A_removeLast_emptyList_testSize", testSize(A_removeLast_emptyList(), 0));
			printTest("A_removeLast_emptyList_testToString", testToString(A_removeLast_emptyList(), Result.ValidString));
			printTest("A_removeLast_emptyList_testSetNeg1A", testSet(A_removeLast_emptyList(), -1, ELEMENT_A, Result.IndexOutOfBounds));
			printTest("A_removeLast_emptyList_testSet0A", testSet(A_removeLast_emptyList(), 0, ELEMENT_A, Result.IndexOutOfBounds));
			printTest("A_removeLast_emptyList_testGetNeg1", testGet(A_removeLast_emptyList(), -1, null, Result.IndexOutOfBounds));
			printTest("A_removeLast_emptyList_testGet0", testGet(A_removeLast_emptyList(), 0, null, Result.IndexOutOfBounds));
			printTest("A_removeLast_emptyList_testIndexOfA", testIndexOf(A_removeLast_emptyList(), ELEMENT_A, -1));
			// Iterator
			printTest("A_removeLast_emptyList_testIter", testIter(A_removeLast_emptyList(), Result.NoException));
			printTest("A_removeLast_emptyList_testIterHasNext", testIterHasNext(A_removeLast_emptyList().iterator(), Result.False));
			printTest("A_removeLast_emptyList_testIterNext", testIterNext(A_removeLast_emptyList().iterator(), null, Result.NoSuchElement));
			printTest("A_removeLast_emptyList_testIterRemove", testIterRemove(A_removeLast_emptyList().iterator(), Result.IllegalState));
			// ListIterator
			printTest("A_removeLast_emptyList_testListIter", testListIter(A_removeLast_emptyList(), Result.NoException));
			printTest("A_removeLast_emptyList_testListIter", testListIter(A_removeLast_emptyList(), 0, Result.NoException));
		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "A_removeLast_emptyList");
			e.printStackTrace();
		}
	}

	////////////////////////////////////////////////
	// XXX SCENARIO: [A] -> remove(A) -> [ ]
	////////////////////////////////////////////////
	/** Scenario: [A] -> remove(A) -> [ ] 
	 * @return [ ] after remove(A)
	 */
	private IndexedUnsortedList<Integer> A_removeA_emptyList() {
		IndexedUnsortedList<Integer> list = emptyList_addToFrontA_A(); //starting state
		list.remove(ELEMENT_A); //the change
		return list;
	}
	/** Run all tests on scenario: [A] -> remove(A) -> [ ] */
	private void test_A_removeA_emptyList() {
		System.out.println("\nSCENARIO: [A] -> remove(A) -> [ ]\n");
		try{
			printTest("A_removeA_emptyList_testAddToFrontA", testAddToFront(A_removeA_emptyList(), ELEMENT_A, Result.NoException));
			printTest("A_removeA_emptyList_testAddToRearA", testAddToRear(A_removeA_emptyList(), ELEMENT_A, Result.NoException));
			printTest("A_removeA_emptyList_testAddAfterBA", testAddAfter(A_removeA_emptyList(), ELEMENT_B, ELEMENT_A, Result.NoSuchElement));
			printTest("A_removeA_emptyList_testAddAtIndexNeg1", testAddAtIndex(A_removeA_emptyList(), -1, ELEMENT_A, Result.IndexOutOfBounds));
			printTest("A_removeA_emptyList_testAddAtIndex0", testAddAtIndex(A_removeA_emptyList(), 0, ELEMENT_A, Result.NoException));
			printTest("A_removeA_emptyList_testAddAtIndex1", testAddAtIndex(A_removeA_emptyList(), 1, ELEMENT_A, Result.IndexOutOfBounds));
			printTest("A_removeA_emptyList_testAddA", testAdd(A_removeA_emptyList(), ELEMENT_A, Result.NoException));
			printTest("A_removeA_emptyList_testRemoveFirst", testRemoveFirst(A_removeA_emptyList(), null, Result.IllegalState));
			printTest("A_removeA_emptyList_testRemoveLast", testRemoveLast(A_removeA_emptyList(), null, Result.IllegalState));
			printTest("A_removeA_emptyList_testRemoveA", testRemoveElement(A_removeA_emptyList(), null, Result.NoSuchElement));
			printTest("A_removeA_emptyList_testRemoveNeg1", testRemoveIndex(A_removeA_emptyList(), -1, null, Result.IndexOutOfBounds));
			printTest("A_removeA_emptyList_testRemove0", testRemoveIndex(A_removeA_emptyList(), 0, null, Result.IndexOutOfBounds));
			printTest("A_removeA_emptyList_testFirst", testFirst(A_removeA_emptyList(), null, Result.IllegalState));
			printTest("A_removeA_emptyList_testLast", testLast(A_removeA_emptyList(), null, Result.IllegalState));
			printTest("A_removeA_emptyList_testContainsA", testContains(A_removeA_emptyList(), ELEMENT_A, Result.False));
			printTest("A_removeA_emptyList_testIsEmpty", testIsEmpty(A_removeA_emptyList(), Result.True));
			printTest("A_removeA_emptyList_testSize", testSize(A_removeA_emptyList(), 0));
			printTest("A_removeA_emptyList_testToString", testToString(A_removeA_emptyList(), Result.ValidString));
			printTest("A_removeA_emptyList_testSetNeg1A", testSet(A_removeA_emptyList(), -1, ELEMENT_A, Result.IndexOutOfBounds));
			printTest("A_removeA_emptyList_testSet0A", testSet(A_removeA_emptyList(), 0, ELEMENT_A, Result.IndexOutOfBounds));
			printTest("A_removeA_emptyList_testGetNeg1", testGet(A_removeA_emptyList(), -1, null, Result.IndexOutOfBounds));
			printTest("A_removeA_emptyList_testGet0", testGet(A_removeA_emptyList(), 0, null, Result.IndexOutOfBounds));
			printTest("A_removeA_emptyList_testIndexOfA", testIndexOf(A_removeA_emptyList(), ELEMENT_A, -1));
			// Iterator
			printTest("A_removeA_emptyList_testIter", testIter(A_removeA_emptyList(), Result.NoException));
			printTest("A_removeA_emptyList_testIterHasNext", testIterHasNext(A_removeA_emptyList().iterator(), Result.False));
			printTest("A_removeA_emptyList_testIterNext", testIterNext(A_removeA_emptyList().iterator(), null, Result.NoSuchElement));
			printTest("A_removeA_emptyList_testIterRemove", testIterRemove(A_removeA_emptyList().iterator(), Result.IllegalState));
			// ListIterator
			printTest("A_removeA_emptyList_testListIter", testListIter(A_removeA_emptyList(), Result.NoException));
			printTest("A_removeA_emptyList_testListIter", testListIter(A_removeA_emptyList(), 0, Result.NoException));
		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "A_removeA_emptyList");
			e.printStackTrace();
		}
	}

	////////////////////////////////////////////////
	// XXX SCENARIO: [A] -> remove(0) -> [ ]
	////////////////////////////////////////////////
	/** Scenario: [A] -> remove(0) -> [ ] 
	 * @return [ ] after remove(0)
	 */
	private IndexedUnsortedList<Integer> A_remove0_emptyList() {
		IndexedUnsortedList<Integer> list = emptyList_addToFrontA_A(); //starting state
		list.remove(0); //the change
		return list;
	}
	/** Run all tests on scenario: [A] -> remove(0) -> [ ] */
	private void test_A_remove0_emptyList() {
		System.out.println("\nSCENARIO: [A] -> remove(0) -> [ ]\n");
		try{
			printTest("A_remove0_emptyList_testAddToFrontA", testAddToFront(A_remove0_emptyList(), ELEMENT_A, Result.NoException));
			printTest("A_remove0_emptyList_testAddToRearA", testAddToRear(A_remove0_emptyList(), ELEMENT_A, Result.NoException));
			printTest("A_remove0_emptyList_testAddAfterBA", testAddAfter(A_remove0_emptyList(), ELEMENT_B, ELEMENT_A, Result.NoSuchElement));
			printTest("A_remove0_emptyList_testAddAtIndexNeg1", testAddAtIndex(A_remove0_emptyList(), -1, ELEMENT_A, Result.IndexOutOfBounds));
			printTest("A_remove0_emptyList_testAddAtIndex0", testAddAtIndex(A_remove0_emptyList(), 0, ELEMENT_A, Result.NoException));
			printTest("A_remove0_emptyList_testAddAtIndex1", testAddAtIndex(A_remove0_emptyList(), 1, ELEMENT_A, Result.IndexOutOfBounds));
			printTest("A_remove0_emptyList_testAddA", testAdd(A_remove0_emptyList(), ELEMENT_A, Result.NoException));
			printTest("A_remove0_emptyList_testRemoveFirst", testRemoveFirst(A_remove0_emptyList(), null, Result.IllegalState));
			printTest("A_remove0_emptyList_testRemoveLast", testRemoveLast(A_remove0_emptyList(), null, Result.IllegalState));
			printTest("A_remove0_emptyList_testremove0", testRemoveElement(A_remove0_emptyList(), null, Result.NoSuchElement));
			printTest("A_remove0_emptyList_testRemoveNeg1", testRemoveIndex(A_remove0_emptyList(), -1, null, Result.IndexOutOfBounds));
			printTest("A_remove0_emptyList_testRemove0", testRemoveIndex(A_remove0_emptyList(), 0, null, Result.IndexOutOfBounds));
			printTest("A_remove0_emptyList_testFirst", testFirst(A_remove0_emptyList(), null, Result.IllegalState));
			printTest("A_remove0_emptyList_testLast", testLast(A_remove0_emptyList(), null, Result.IllegalState));
			printTest("A_remove0_emptyList_testContainsA", testContains(A_remove0_emptyList(), ELEMENT_A, Result.False));
			printTest("A_remove0_emptyList_testIsEmpty", testIsEmpty(A_remove0_emptyList(), Result.True));
			printTest("A_remove0_emptyList_testSize", testSize(A_remove0_emptyList(), 0));
			printTest("A_remove0_emptyList_testToString", testToString(A_remove0_emptyList(), Result.ValidString));
			printTest("A_remove0_emptyList_testSetNeg1A", testSet(A_remove0_emptyList(), -1, ELEMENT_A, Result.IndexOutOfBounds));
			printTest("A_remove0_emptyList_testSet0A", testSet(A_remove0_emptyList(), 0, ELEMENT_A, Result.IndexOutOfBounds));
			printTest("A_remove0_emptyList_testGetNeg1", testGet(A_remove0_emptyList(), -1, null, Result.IndexOutOfBounds));
			printTest("A_remove0_emptyList_testGet0", testGet(A_remove0_emptyList(), 0, null, Result.IndexOutOfBounds));
			printTest("A_remove0_emptyList_testIndexOfA", testIndexOf(A_remove0_emptyList(), ELEMENT_A, -1));
			// Iterator
			printTest("A_remove0_emptyList_testIter", testIter(A_remove0_emptyList(), Result.NoException));
			printTest("A_remove0_emptyList_testIterHasNext", testIterHasNext(A_remove0_emptyList().iterator(), Result.False));
			printTest("A_remove0_emptyList_testIterNext", testIterNext(A_remove0_emptyList().iterator(), null, Result.NoSuchElement));
			printTest("A_remove0_emptyList_testIterRemove", testIterRemove(A_remove0_emptyList().iterator(), Result.IllegalState));
			// ListIterator
			printTest("A_remove0_emptyList_testListIter", testListIter(A_remove0_emptyList(), Result.NoException));
			printTest("A_remove0_emptyList_testListIter", testListIter(A_remove0_emptyList(), 0, Result.NoException));
		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "A_remove0_emptyList");
			e.printStackTrace();
		}
	}
	////////////////////////////////////////////////
	// XXX SCENARIO: [ ] -> addToFront(A) -> [A]
	////////////////////////////////////////////////

	/** Scenario: empty list -> addToFront(A) -> [A] 
	 * @return [A] after addToFront(A)
	 */
	private IndexedUnsortedList<Integer> emptyList_addToFrontA_A() {
		// It's a good idea to get the "starting state" from a previous
		// scenario's builder method. That way, you only add on the next
		// change and more advanced scenarios can build on previously
		// tested scenarios.
		IndexedUnsortedList<Integer> list = newList(); //starting state
		list.addToFront(ELEMENT_A); //the change
		return list; //return the resulting state
	}

	/** Run all tests on scenario: empty list -> addToFront(A) -> [A] */
	private void test_emptyList_addToFrontA_A() {
		System.out.println("\nSCENARIO: [] -> addToFront(A) -> [A]\n");
		try {
			printTest("emptyList_addToFrontA_A_testAddToFrontB", testAddToFront(emptyList_addToFrontA_A(), ELEMENT_B, Result.NoException));
			printTest("emptyList_addToFrontA_A_testAddToRearB", testAddToRear(emptyList_addToFrontA_A(), ELEMENT_A, Result.NoException));
			printTest("emptyList_addToFrontA_A_testAddAfterCB", testAddAfter(emptyList_addToFrontA_A(), ELEMENT_C, ELEMENT_B, Result.NoSuchElement));
			printTest("emptyList_addToFrontA_A_testAddAfterAB", testAddAfter(emptyList_addToFrontA_A(), ELEMENT_A, ELEMENT_B, Result.NoException));
			printTest("emptyList_addToFrontA_A_testAddAtIndexNeg1B", testAddAtIndex(emptyList_addToFrontA_A(), -1, ELEMENT_B, Result.IndexOutOfBounds));
			printTest("emptyList_addToFrontA_A_testAddAtIndex0B", testAddAtIndex(emptyList_addToFrontA_A(), 0, ELEMENT_B, Result.NoException));
			printTest("emptyList_addToFrontA_A_testAddAtIndex1B", testAddAtIndex(emptyList_addToFrontA_A(), 1, ELEMENT_B, Result.NoException));
			printTest("emptyList_addToFrontA_A_testAddB", testAdd(emptyList_addToFrontA_A(), ELEMENT_B, Result.NoException));
			printTest("emptyList_addToFrontA_A_testRemoveFirst", testRemoveFirst(emptyList_addToFrontA_A(), ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_addToFrontA_A_testRemoveLast", testRemoveLast(emptyList_addToFrontA_A(), ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_addToFrontA_A_testRemoveA", testRemoveElement(emptyList_addToFrontA_A(), ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_addToFrontA_A_testRemoveB", testRemoveElement(emptyList_addToFrontA_A(), ELEMENT_B, Result.NoSuchElement));
			printTest("emptyList_addToFrontA_A_testRemoveNeg1", testRemoveIndex(emptyList_addToFrontA_A(), -1, null, Result.IndexOutOfBounds));
			printTest("emptyList_addToFrontA_A_testRemove0", testRemoveIndex(emptyList_addToFrontA_A(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_addToFrontA_A_testRemove1", testRemoveIndex(emptyList_addToFrontA_A(), 1, null, Result.IndexOutOfBounds));
			printTest("emptyList_addToFrontA_A_testFirst", testFirst(emptyList_addToFrontA_A(), ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_addToFrontA_A_testLast", testLast(emptyList_addToFrontA_A(), ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_addToFrontA_A_testContainsA", testContains(emptyList_addToFrontA_A(), ELEMENT_A, Result.True));
			printTest("emptyList_addToFrontA_A_testContainsB", testContains(emptyList_addToFrontA_A(), ELEMENT_B, Result.False));
			printTest("emptyList_addToFrontA_A_testIsEmpty", testIsEmpty(emptyList_addToFrontA_A(), Result.False));
			printTest("emptyList_addToFrontA_A_testSize", testSize(emptyList_addToFrontA_A(), 1));
			printTest("emptyList_addToFrontA_A_testToString", testToString(emptyList_addToFrontA_A(), Result.ValidString));			
			printTest("emptyList_addToFrontA_A_testSetNeg1B", testSet(emptyList_addToFrontA_A(), -1, ELEMENT_B, Result.IndexOutOfBounds));
			printTest("emptyList_addToFrontA_A_testSet0B", testSet(emptyList_addToFrontA_A(), 0, ELEMENT_B, Result.NoException));
			printTest("emptyList_addToFrontA_A_testGetNeg1", testGet(emptyList_addToFrontA_A(), -1, null, Result.IndexOutOfBounds));
			printTest("emptyList_addToFrontA_A_testGet0", testGet(emptyList_addToFrontA_A(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_addToFrontA_A_testIndexOfA", testIndexOf(emptyList_addToFrontA_A(), ELEMENT_A, 0));
			printTest("emptyList_addToFrontA_A_testIndexOfB", testIndexOf(emptyList_addToFrontA_A(), ELEMENT_B, -1));
			// Iterator
			printTest("emptyList_addToFrontA_A_testIter", testIter(emptyList_addToFrontA_A(), Result.NoException));
			printTest("emptyList_addToFrontA_A_testIterHasNext", testIterHasNext(emptyList_addToFrontA_A().iterator(), Result.True));
			printTest("emptyList_addToFrontA_A_testIterNext", testIterNext(emptyList_addToFrontA_A().iterator(), ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_addToFrontA_A_testIterRemove", testIterRemove(emptyList_addToFrontA_A().iterator(), Result.IllegalState));
			printTest("emptyList_addToFrontA_A_iteratorNext_testIterHasNext", testIterHasNext(iterAfterNext(emptyList_addToFrontA_A(), 1), Result.False));
			printTest("emptyList_addToFrontA_A_iteratorNext_testIterNext", testIterNext(iterAfterNext(emptyList_addToFrontA_A(), 1), null, Result.NoSuchElement));
			printTest("emptyList_addToFrontA_A_iteratorNext_testIterRemove", testIterRemove(iterAfterNext(emptyList_addToFrontA_A(), 1), Result.NoException));
			printTest("emptyList_addToFrontA_A_iteratorNextRemove_testIterHasNext", testIterHasNext(iterAfterRemove(iterAfterNext(emptyList_addToFrontA_A(), 1)), Result.False));
			printTest("emptyList_addToFrontA_A_iteratorNextRemove_testIterNext", testIterNext(iterAfterRemove(iterAfterNext(emptyList_addToFrontA_A(), 1)), null, Result.NoSuchElement));
			printTest("emptyList_addToFrontA_A_iteratorNextRemove_testIterRemove", testIterRemove(iterAfterRemove(iterAfterNext(emptyList_addToFrontA_A(), 1)), Result.IllegalState));
			// ListIterator
			printTest("newList_testListIter", testListIter(newList(), Result.NoException));
			printTest("newList_testListIter", testListIter(newList(), 0, Result.NoException));
		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "test_emptyList_addToFrontA_A");
			e.printStackTrace();
		}
	}

	////////////////////////////////////////////////
	// XXX SCENARIO: [ ] -> addToRear(A) -> [A]
	////////////////////////////////////////////////
	/** Scenario: empty list -> addToRear(A) -> [A] 
	 * @return [A] after addToRear(A)
	 */
	private IndexedUnsortedList<Integer> emptyList_addToRearA_A() {

		IndexedUnsortedList<Integer> list = newList(); //starting state
		list.addToRear(ELEMENT_A); //the change
		return list;
	}
	/** Run all tests on scenario: empty list -> addToRear(A) -> [A] */
	private void test_emptyList_addToRearA_A() {
		System.out.println("\nSCENARIO: [] -> addToRear(A) -> [A]\n");
		try {
			printTest("emptyList_addToRearA_A_testAddToFrontB", testAddToFront(emptyList_addToRearA_A(), ELEMENT_B, Result.NoException));
			printTest("emptyList_addToRearA_A_testAddToRearB", testAddToRear(emptyList_addToRearA_A(), ELEMENT_A, Result.NoException));
			printTest("emptyList_addToRearA_A_testAddAfterCB", testAddAfter(emptyList_addToRearA_A(), ELEMENT_C, ELEMENT_B, Result.NoSuchElement));
			printTest("emptyList_addToRearA_A_testAddAfterAB", testAddAfter(emptyList_addToRearA_A(), ELEMENT_A, ELEMENT_B, Result.NoException));
			printTest("emptyList_addToRearA_A_testAddAtIndexNeg1B", testAddAtIndex(emptyList_addToRearA_A(), -1, ELEMENT_B, Result.IndexOutOfBounds));
			printTest("emptyList_addToRearA_A_testAddAtIndex0B", testAddAtIndex(emptyList_addToRearA_A(), 0, ELEMENT_B, Result.NoException));
			printTest("emptyList_addToRearA_A_testAddAtIndex1B", testAddAtIndex(emptyList_addToRearA_A(), 1, ELEMENT_B, Result.NoException));
			printTest("emptyList_addToRearA_A_testAddB", testAdd(emptyList_addToRearA_A(), ELEMENT_B, Result.NoException));
			printTest("emptyList_addToRearA_A_testRemoveFirst", testRemoveFirst(emptyList_addToRearA_A(), ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_addToRearA_A_testRemoveLast", testRemoveLast(emptyList_addToRearA_A(), ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_addToRearA_A_testRemoveA", testRemoveElement(emptyList_addToRearA_A(), ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_addToRearA_A_testRemoveB", testRemoveElement(emptyList_addToRearA_A(), ELEMENT_B, Result.NoSuchElement));
			printTest("emptyList_addToRearA_A_testRemoveNeg1", testRemoveIndex(emptyList_addToRearA_A(), -1, null, Result.IndexOutOfBounds));
			printTest("emptyList_addToRearA_A_testRemove0", testRemoveIndex(emptyList_addToRearA_A(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_addToRearA_A_testRemove1", testRemoveIndex(emptyList_addToRearA_A(), 1, null, Result.IndexOutOfBounds));
			printTest("emptyList_addToRearA_A_testFirst", testFirst(emptyList_addToRearA_A(), ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_addToRearA_A_testLast", testLast(emptyList_addToRearA_A(), ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_addToRearA_A_testContainsA", testContains(emptyList_addToRearA_A(), ELEMENT_A, Result.True));
			printTest("emptyList_addToRearA_A_testContainsB", testContains(emptyList_addToRearA_A(), ELEMENT_B, Result.False));
			printTest("emptyList_addToRearA_A_testIsEmpty", testIsEmpty(emptyList_addToRearA_A(), Result.False));
			printTest("emptyList_addToRearA_A_testSize", testSize(emptyList_addToRearA_A(), 1));
			printTest("emptyList_addToRearA_A_testToString", testToString(emptyList_addToRearA_A(), Result.ValidString));			
			printTest("emptyList_addToRearA_A_testSetNeg1B", testSet(emptyList_addToRearA_A(), -1, ELEMENT_B, Result.IndexOutOfBounds));
			printTest("emptyList_addToRearA_A_testSet0B", testSet(emptyList_addToRearA_A(), 0, ELEMENT_B, Result.NoException));
			printTest("emptyList_addToRearA_A_testGetNeg1", testGet(emptyList_addToRearA_A(), -1, null, Result.IndexOutOfBounds));
			printTest("emptyList_addToRearA_A_testGet0", testGet(emptyList_addToRearA_A(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_addToRearA_A_testIndexOfA", testIndexOf(emptyList_addToRearA_A(), ELEMENT_A, 0));
			printTest("emptyList_addToRearA_A_testIndexOfB", testIndexOf(emptyList_addToRearA_A(), ELEMENT_B, -1));
			// Iterator
			printTest("emptyList_addToRearA_A_testIter", testIter(emptyList_addToRearA_A(), Result.NoException));
			printTest("emptyList_addToRearA_A_testIterHasNext", testIterHasNext(emptyList_addToRearA_A().iterator(), Result.True));
			printTest("emptyList_addToRearA_A_testIterNext", testIterNext(emptyList_addToRearA_A().iterator(), ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_addToRearA_A_testIterRemove", testIterRemove(emptyList_addToRearA_A().iterator(), Result.IllegalState));
			printTest("emptyList_addToRearA_A_iteratorNext_testIterHasNext", testIterHasNext(iterAfterNext(emptyList_addToRearA_A(), 1), Result.False));
			printTest("emptyList_addToRearA_A_iteratorNext_testIterNext", testIterNext(iterAfterNext(emptyList_addToRearA_A(), 1), null, Result.NoSuchElement));
			printTest("emptyList_addToRearA_A_iteratorNext_testIterRemove", testIterRemove(iterAfterNext(emptyList_addToRearA_A(), 1), Result.NoException));
			printTest("emptyList_addToRearA_A_iteratorNextRemove_testIterHasNext", testIterHasNext(iterAfterRemove(iterAfterNext(emptyList_addToRearA_A(), 1)), Result.False));
			printTest("emptyList_addToRearA_A_iteratorNextRemove_testIterNext", testIterNext(iterAfterRemove(iterAfterNext(emptyList_addToRearA_A(), 1)), null, Result.NoSuchElement));
			printTest("emptyList_addToRearA_A_iteratorNextRemove_testIterRemove", testIterRemove(iterAfterRemove(iterAfterNext(emptyList_addToRearA_A(), 1)), Result.IllegalState));
			// ListIterator
			printTest("newList_testListIter", testListIter(newList(), Result.NoException));
			printTest("newList_testListIter", testListIter(newList(), 0, Result.NoException));
		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "test_emptyList_addToRearA_A");
			e.printStackTrace();
		}
	}

	////////////////////////////////////////////////
	// XXX SCENARIO: [ ] -> add(A) -> [A]
	////////////////////////////////////////////////
	/** Scenario: empty list -> add(A) -> [A] 
	 * @return [A] after add(A)
	 */
	private IndexedUnsortedList<Integer> emptyList_addA_A() {

		IndexedUnsortedList<Integer> list = newList(); //starting state
		list.add(ELEMENT_A); //the change
		return list;
	}
	/** Run all tests on scenario: empty list -> add(A) -> [A] */
	private void test_emptyList_addA_A() {
		System.out.println("\nSCENARIO: [] -> add(A) -> [A]\n");
		try{
			printTest("emptyList_addA_A_testAddToFrontB", testAddToFront(emptyList_addA_A(), ELEMENT_B, Result.NoException));
			printTest("emptyList_addA_A_testAddToRearB", testAddToRear(emptyList_addA_A(), ELEMENT_A, Result.NoException));
			printTest("emptyList_addA_A_testAddAfterCB", testAddAfter(emptyList_addA_A(), ELEMENT_C, ELEMENT_B, Result.NoSuchElement));
			printTest("emptyList_addA_A_testAddAfterAB", testAddAfter(emptyList_addA_A(), ELEMENT_A, ELEMENT_B, Result.NoException));
			printTest("emptyList_addA_A_testAddAtIndexNeg1B", testAddAtIndex(emptyList_addA_A(), -1, ELEMENT_B, Result.IndexOutOfBounds));
			printTest("emptyList_addA_A_testAddAtIndex0B", testAddAtIndex(emptyList_addA_A(), 0, ELEMENT_B, Result.NoException));
			printTest("emptyList_addA_A_testAddAtIndex1B", testAddAtIndex(emptyList_addA_A(), 1, ELEMENT_B, Result.NoException));
			printTest("emptyList_addA_A_testAddB", testAdd(emptyList_addA_A(), ELEMENT_B, Result.NoException));
			printTest("emptyList_addA_A_testRemoveFirst", testRemoveFirst(emptyList_addA_A(), ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_addA_A_testRemoveLast", testRemoveLast(emptyList_addA_A(), ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_addA_A_testRemoveA", testRemoveElement(emptyList_addA_A(), ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_addA_A_testRemoveB", testRemoveElement(emptyList_addA_A(), ELEMENT_B, Result.NoSuchElement));
			printTest("emptyList_addA_A_testRemoveNeg1", testRemoveIndex(emptyList_addA_A(), -1, null, Result.IndexOutOfBounds));
			printTest("emptyList_addA_A_testRemove0", testRemoveIndex(emptyList_addA_A(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_addA_A_testRemove1", testRemoveIndex(emptyList_addA_A(), 1, null, Result.IndexOutOfBounds));
			printTest("emptyList_addA_A_testFirst", testFirst(emptyList_addA_A(), ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_addA_A_testLast", testLast(emptyList_addA_A(), ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_addA_A_testContainsA", testContains(emptyList_addA_A(), ELEMENT_A, Result.True));
			printTest("emptyList_addA_A_testContainsB", testContains(emptyList_addA_A(), ELEMENT_B, Result.False));
			printTest("emptyList_addA_A_testIsEmpty", testIsEmpty(emptyList_addA_A(), Result.False));
			printTest("emptyList_addA_A_testSize", testSize(emptyList_addA_A(), 1));
			printTest("emptyList_addA_A_testToString", testToString(emptyList_addA_A(), Result.ValidString));			
			printTest("emptyList_addA_A_testSetNeg1B", testSet(emptyList_addA_A(), -1, ELEMENT_B, Result.IndexOutOfBounds));
			printTest("emptyList_addA_A_testSet0B", testSet(emptyList_addA_A(), 0, ELEMENT_B, Result.NoException));
			printTest("emptyList_addA_A_testGetNeg1", testGet(emptyList_addA_A(), -1, null, Result.IndexOutOfBounds));
			printTest("emptyList_addA_A_testGet0", testGet(emptyList_addA_A(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_addA_A_testIndexOfA", testIndexOf(emptyList_addA_A(), ELEMENT_A, 0));
			printTest("emptyList_addA_A_testIndexOfB", testIndexOf(emptyList_addA_A(), ELEMENT_B, -1));
			// Iterator
			printTest("emptyList_addA_A_testIter", testIter(emptyList_addA_A(), Result.NoException));
			printTest("emptyList_addA_A_testIterHasNext", testIterHasNext(emptyList_addA_A().iterator(), Result.True));
			printTest("emptyList_addA_A_testIterNext", testIterNext(emptyList_addA_A().iterator(), ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_addA_A_testIterRemove", testIterRemove(emptyList_addA_A().iterator(), Result.IllegalState));
			printTest("emptyList_addA_A_iteratorNext_testIterHasNext", testIterHasNext(iterAfterNext(emptyList_addA_A(), 1), Result.False));
			printTest("emptyList_addA_A_iteratorNext_testIterNext", testIterNext(iterAfterNext(emptyList_addA_A(), 1), null, Result.NoSuchElement));
			printTest("emptyList_addA_A_iteratorNext_testIterRemove", testIterRemove(iterAfterNext(emptyList_addA_A(), 1), Result.NoException));
			printTest("emptyList_addA_A_iteratorNextRemove_testIterHasNext", testIterHasNext(iterAfterRemove(iterAfterNext(emptyList_addA_A(), 1)), Result.False));
			printTest("emptyList_addA_A_iteratorNextRemove_testIterNext", testIterNext(iterAfterRemove(iterAfterNext(emptyList_addA_A(), 1)), null, Result.NoSuchElement));
			printTest("emptyList_addA_A_iteratorNextRemove_testIterRemove", testIterRemove(iterAfterRemove(iterAfterNext(emptyList_addA_A(), 1)), Result.IllegalState));
			// ListIterator
			printTest("newList_testListIter", testListIter(newList(), Result.NoException));
			printTest("newList_testListIter", testListIter(newList(), 0, Result.NoException));
		}catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "test_emptyList_addA_A");
			e.printStackTrace();
		}
	}	

	////////////////////////////////////////////////
	// XXX SCENARIO: [ ] -> add(0,A) -> [A]
	////////////////////////////////////////////////
	/** Scenario: empty list -> add(0,A) -> [A] 
	 * @return [A] after add(0,A)
	 */
	private IndexedUnsortedList<Integer> emptyList_add0A_A() {

		IndexedUnsortedList<Integer> list = newList(); //starting state
		list.add(0,ELEMENT_A); //the change
		return list;
	}
	/** Run all tests on scenario: empty list -> add(0,A) -> [A] */
	private void test_emptyList_add0A_A() {
		System.out.println("\nSCENARIO: [] -> add(0,A) -> [A]\n");
		try{
			printTest("emptyList_add0A_A_testAddToFrontB", testAddToFront(emptyList_add0A_A(), ELEMENT_B, Result.NoException));
			printTest("emptyList_add0A_A_testAddToRearB", testAddToRear(emptyList_add0A_A(), ELEMENT_A, Result.NoException));
			printTest("emptyList_add0A_A_testAddAfterCB", testAddAfter(emptyList_add0A_A(), ELEMENT_C, ELEMENT_B, Result.NoSuchElement));
			printTest("emptyList_add0A_A_testAddAfterAB", testAddAfter(emptyList_add0A_A(), ELEMENT_A, ELEMENT_B, Result.NoException));
			printTest("emptyList_add0A_A_testAddAtIndexNeg1B", testAddAtIndex(emptyList_add0A_A(), -1, ELEMENT_B, Result.IndexOutOfBounds));
			printTest("emptyList_add0A_A_testAddAtIndex0B", testAddAtIndex(emptyList_add0A_A(), 0, ELEMENT_B, Result.NoException));
			printTest("emptyList_add0A_A_testAddAtIndex1B", testAddAtIndex(emptyList_add0A_A(), 1, ELEMENT_B, Result.NoException));
			printTest("emptyList_add0A_A_testAddB", testAdd(emptyList_add0A_A(), ELEMENT_B, Result.NoException));
			printTest("emptyList_add0A_A_testRemoveFirst", testRemoveFirst(emptyList_add0A_A(), ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_add0A_A_testRemoveLast", testRemoveLast(emptyList_add0A_A(), ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_add0A_A_testRemoveA", testRemoveElement(emptyList_add0A_A(), ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_add0A_A_testRemoveB", testRemoveElement(emptyList_add0A_A(), ELEMENT_B, Result.NoSuchElement));
			printTest("emptyList_add0A_A_testRemoveNeg1", testRemoveIndex(emptyList_add0A_A(), -1, null, Result.IndexOutOfBounds));
			printTest("emptyList_add0A_A_testRemove0", testRemoveIndex(emptyList_add0A_A(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_add0A_A_testRemove1", testRemoveIndex(emptyList_add0A_A(), 1, null, Result.IndexOutOfBounds));
			printTest("emptyList_add0A_A_testFirst", testFirst(emptyList_add0A_A(), ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_add0A_A_testLast", testLast(emptyList_add0A_A(), ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_add0A_A_testContainsA", testContains(emptyList_add0A_A(), ELEMENT_A, Result.True));
			printTest("emptyList_add0A_A_testContainsB", testContains(emptyList_add0A_A(), ELEMENT_B, Result.False));
			printTest("emptyList_add0A_A_testIsEmpty", testIsEmpty(emptyList_add0A_A(), Result.False));
			printTest("emptyList_add0A_A_testSize", testSize(emptyList_add0A_A(), 1));
			printTest("emptyList_add0A_A_testToString", testToString(emptyList_add0A_A(), Result.ValidString));			
			printTest("emptyList_add0A_A_testSetNeg1B", testSet(emptyList_add0A_A(), -1, ELEMENT_B, Result.IndexOutOfBounds));
			printTest("emptyList_add0A_A_testSet0B", testSet(emptyList_add0A_A(), 0, ELEMENT_B, Result.NoException));
			printTest("emptyList_add0A_A_testGetNeg1", testGet(emptyList_add0A_A(), -1, null, Result.IndexOutOfBounds));
			printTest("emptyList_add0A_A_testGet0", testGet(emptyList_add0A_A(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_add0A_A_testIndexOfA", testIndexOf(emptyList_add0A_A(), ELEMENT_A, 0));
			printTest("emptyList_add0A_A_testIndexOfB", testIndexOf(emptyList_add0A_A(), ELEMENT_B, -1));
			// Iterator
			printTest("emptyList_add0A_A_testIter", testIter(emptyList_add0A_A(), Result.NoException));
			printTest("emptyList_add0A_A_testIterHasNext", testIterHasNext(emptyList_add0A_A().iterator(), Result.True));
			printTest("emptyList_add0A_A_testIterNext", testIterNext(emptyList_add0A_A().iterator(), ELEMENT_A, Result.MatchingValue));
			printTest("emptyList_add0A_A_testIterRemove", testIterRemove(emptyList_add0A_A().iterator(), Result.IllegalState));
			printTest("emptyList_add0A_A_iteratorNext_testIterHasNext", testIterHasNext(iterAfterNext(emptyList_add0A_A(), 1), Result.False));
			printTest("emptyList_add0A_A_iteratorNext_testIterNext", testIterNext(iterAfterNext(emptyList_add0A_A(), 1), null, Result.NoSuchElement));
			printTest("emptyList_add0A_A_iteratorNext_testIterRemove", testIterRemove(iterAfterNext(emptyList_add0A_A(), 1), Result.NoException));
			printTest("emptyList_add0A_A_iteratorNextRemove_testIterHasNext", testIterHasNext(iterAfterRemove(iterAfterNext(emptyList_add0A_A(), 1)), Result.False));
			printTest("emptyList_add0A_A_iteratorNextRemove_testIterNext", testIterNext(iterAfterRemove(iterAfterNext(emptyList_add0A_A(), 1)), null, Result.NoSuchElement));
			printTest("emptyList_add0A_A_iteratorNextRemove_testIterRemove", testIterRemove(iterAfterRemove(iterAfterNext(emptyList_add0A_A(), 1)), Result.IllegalState));
			// ListIterator
			printTest("newList_testListIter", testListIter(newList(), Result.NoException));
			printTest("newList_testListIter", testListIter(newList(), 0, Result.NoException));
		}catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "test_emptyList_add0A_A");
			e.printStackTrace();
		}
	}

	////////////////////////////////////////////////
	// XXX SCENARIO: [A] -> addToFront(B) -> [B,A]
	////////////////////////////////////////////////

	/** Scenario: [A] -> addToFront(B) -> [B,A] 
	 * @return [B,A] after addToFront(B)
	 */
	private IndexedUnsortedList<Integer> A_addToFrontB_BA() {
		// It's a good idea to get the "starting state" from a previous
		// scenario's builder method. That way, you only add on the next
		// change and more advanced scenarios can build on previously
		// tested scenarios.
		IndexedUnsortedList<Integer> list = emptyList_addToFrontA_A(); //starting state 
		list.addToFront(ELEMENT_B); //the change
		return list; //return resulting state
	}

	/** Run all tests on scenario: A -> addToFront(B) -> [B,A] */
	private void test_A_addToFrontB_BA() {
		System.out.println("\nSCENARIO: [A] -> addToFront(B) -> [B,A]\n");
		try {
			// IndexedUnsortedList
			printTest("A_addToFrontB_BA_testAddToFrontC", testAddToFront(A_addToFrontB_BA(), ELEMENT_C, Result.NoException));
			printTest("A_addToFrontB_BA_testAddToRearC", testAddToRear(A_addToFrontB_BA(), ELEMENT_C, Result.NoException));
			printTest("A_addToFrontB_BA_testAddAfterDC", testAddAfter(A_addToFrontB_BA(), ELEMENT_D, ELEMENT_C, Result.NoSuchElement));
			printTest("A_addToFrontB_BA_testAddAfterAC", testAddAfter(A_addToFrontB_BA(), ELEMENT_A, ELEMENT_C, Result.NoException));
			printTest("A_addToFrontB_BA_testAddAfterBC", testAddAfter(A_addToFrontB_BA(), ELEMENT_B, ELEMENT_C, Result.NoException));
			printTest("A_addToFrontB_BA_testAddAtIndexNeg1C", testAddAtIndex(A_addToFrontB_BA(), -1, ELEMENT_C, Result.IndexOutOfBounds));
			printTest("A_addToFrontB_BA_testAddAtIndex0C", testAddAtIndex(A_addToFrontB_BA(), 0, ELEMENT_C, Result.NoException));
			printTest("A_addToFrontB_BA_testAddAtIndex1C", testAddAtIndex(A_addToFrontB_BA(), 1, ELEMENT_C, Result.NoException));
			printTest("A_addToFrontB_BA_testAddAtIndex2C", testAddAtIndex(A_addToFrontB_BA(), 2, ELEMENT_C, Result.NoException));
			printTest("A_addToFrontB_BA_testAddAtIndex3C", testAddAtIndex(A_addToFrontB_BA(), 3, ELEMENT_C, Result.IndexOutOfBounds));
			printTest("A_addToFrontB_BA_testAddC", testAdd(A_addToFrontB_BA(), ELEMENT_C, Result.NoException));
			printTest("A_addToFrontB_BA_testRemoveFirst", testRemoveFirst(A_addToFrontB_BA(), ELEMENT_B, Result.MatchingValue));
			printTest("A_addToFrontB_BA_testRemoveLast", testRemoveLast(A_addToFrontB_BA(), ELEMENT_A, Result.MatchingValue));
			printTest("A_addToFrontB_BA_testRemoveA", testRemoveElement(A_addToFrontB_BA(), ELEMENT_A, Result.MatchingValue));
			printTest("A_addToFrontB_BA_testRemoveB", testRemoveElement(A_addToFrontB_BA(), ELEMENT_B, Result.MatchingValue));
			printTest("A_addToFrontB_BA_testRemoveC", testRemoveElement(A_addToFrontB_BA(), ELEMENT_C, Result.NoSuchElement));
			printTest("A_addToFrontB_BA_testRemoveNeg1", testRemoveIndex(A_addToFrontB_BA(), -1, null, Result.IndexOutOfBounds));
			printTest("A_addToFrontB_BA_testRemove0", testRemoveIndex(A_addToFrontB_BA(), 0, ELEMENT_B, Result.MatchingValue));
			printTest("A_addToFrontB_BA_testRemove1", testRemoveIndex(A_addToFrontB_BA(), 1, ELEMENT_A, Result.MatchingValue));
			printTest("A_addToFrontB_BA_testRemove2", testRemoveIndex(A_addToFrontB_BA(), 2, null, Result.IndexOutOfBounds));
			printTest("A_addToFrontB_BA_testFirst", testFirst(A_addToFrontB_BA(), ELEMENT_B, Result.MatchingValue));
			printTest("A_addToFrontB_BA_testLast", testLast(A_addToFrontB_BA(), ELEMENT_A, Result.MatchingValue));
			printTest("A_addToFrontB_BA_testContainsA", testContains(A_addToFrontB_BA(), ELEMENT_A, Result.True));
			printTest("A_addToFrontB_BA_testContainsB", testContains(A_addToFrontB_BA(), ELEMENT_B, Result.True));
			printTest("A_addToFrontB_BA_testContainsC", testContains(A_addToFrontB_BA(), ELEMENT_C, Result.False));
			printTest("A_addToFrontB_BA_testIsEmpty", testIsEmpty(A_addToFrontB_BA(), Result.False));
			printTest("A_addToFrontB_BA_testSize", testSize(A_addToFrontB_BA(), 2));
			printTest("A_addToFrontB_BA_testToString", testToString(A_addToFrontB_BA(), Result.ValidString));
			printTest("A_addToFrontB_BA_testSetNeg1C", testSet(A_addToFrontB_BA(), -1, ELEMENT_C, Result.IndexOutOfBounds));
			printTest("A_addToFrontB_BA_testSet0C", testSet(A_addToFrontB_BA(), 0, ELEMENT_C, Result.NoException));
			printTest("A_addToFrontB_BA_testSet1C", testSet(A_addToFrontB_BA(), 1, ELEMENT_C, Result.NoException));
			printTest("A_addToFrontB_BA_testSet2C", testSet(A_addToFrontB_BA(), 2, ELEMENT_C, Result.IndexOutOfBounds));
			printTest("A_addToFrontB_BA_testGetNeg1", testGet(A_addToFrontB_BA(), -1, null, Result.IndexOutOfBounds));
			printTest("A_addToFrontB_BA_testGet0", testGet(A_addToFrontB_BA(), 0, ELEMENT_B, Result.MatchingValue));
			printTest("A_addToFrontB_BA_testGet1", testGet(A_addToFrontB_BA(), 1, ELEMENT_A, Result.MatchingValue));
			printTest("A_addToFrontB_BA_testGet2", testGet(A_addToFrontB_BA(), 2, null, Result.IndexOutOfBounds));
			printTest("A_addToFrontB_BA_testIndexOfA", testIndexOf(A_addToFrontB_BA(), ELEMENT_A, 1));
			printTest("A_addToFrontB_BA_testIndexOfB", testIndexOf(A_addToFrontB_BA(), ELEMENT_B, 0));
			printTest("A_addToFrontB_BA_testIndexOfC", testIndexOf(A_addToFrontB_BA(), ELEMENT_C, -1));
			// Iterator
			printTest("A_addToFrontB_BA_testIter", testIter(A_addToFrontB_BA(), Result.NoException));
			printTest("A_addToFrontB_BA_testIterHasNext", testIterHasNext(A_addToFrontB_BA().iterator(), Result.True));
			printTest("A_addToFrontB_BA_testIterNext", testIterNext(A_addToFrontB_BA().iterator(), ELEMENT_B, Result.MatchingValue));
			printTest("A_addToFrontB_BA_testIterRemove", testIterRemove(A_addToFrontB_BA().iterator(), Result.IllegalState));
			printTest("A_addToFrontB_BA_iterNext_testIterHasNext", testIterHasNext(iterAfterNext(A_addToFrontB_BA(), 1), Result.True));
			printTest("A_addToFrontB_BA_iterNext_testIterNext", testIterNext(iterAfterNext(A_addToFrontB_BA(), 1), ELEMENT_A, Result.MatchingValue));
			printTest("A_addToFrontB_BA_iterNext_testIterRemove", testIterRemove(iterAfterNext(A_addToFrontB_BA(), 1), Result.NoException));
			printTest("A_addToFrontB_BA_iterNextRemove_testIterHasNext", testIterHasNext(iterAfterRemove(iterAfterNext(A_addToFrontB_BA(), 1)), Result.True));
			printTest("A_addToFrontB_BA_iterNextRemove_testIterNext", testIterNext(iterAfterRemove(iterAfterNext(A_addToFrontB_BA(), 1)), ELEMENT_A, Result.MatchingValue));
			printTest("A_addToFrontB_BA_iterNextRemove_testIterRemove", testIterRemove(iterAfterRemove(iterAfterNext(A_addToFrontB_BA(), 1)), Result.IllegalState));
			printTest("A_addToFrontB_BA_iterNextNext_testIterHasNext", testIterHasNext(iterAfterNext(A_addToFrontB_BA(), 2), Result.False));
			printTest("A_addToFrontB_BA_iterNextNext_testIterNext", testIterNext(iterAfterNext(A_addToFrontB_BA(), 2), null, Result.NoSuchElement));
			printTest("A_addToFrontB_BA_iterNextNext_testIterRemove", testIterRemove(iterAfterNext(A_addToFrontB_BA(), 2), Result.NoException));
			printTest("A_addToFrontB_BA_iterNextNextRemove_testIterHasNext", testIterHasNext(iterAfterRemove(iterAfterNext(A_addToFrontB_BA(), 2)), Result.False));
			printTest("A_addToFrontB_BA_iterNextNextRemove_testIterNext", testIterNext(iterAfterRemove(iterAfterNext(A_addToFrontB_BA(), 2)), null, Result.NoSuchElement));
			printTest("A_addToFrontB_BA_iterNextNextRemove_testIterRemove", testIterRemove(iterAfterRemove(iterAfterNext(A_addToFrontB_BA(), 2)), Result.IllegalState));
			// ListIterator
			printTest("newList_testListIter", testListIter(newList(), Result.NoException));
			printTest("newList_testListIter", testListIter(newList(), 0, Result.NoException));
		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "test_A_addToFrontB_BA");
			e.printStackTrace();
		}
	}

	////////////////////////////////////////////////
	// XXX SCENARIO: [A] -> add(0,B) -> [B,A]
	////////////////////////////////////////////////

	/** Scenario: [A] -> add(0,B) -> [B,A] 
	 * @return [B,A] after add(0,B)
	 */
	private IndexedUnsortedList<Integer> A_add0B_BA() {
		IndexedUnsortedList<Integer> list = emptyList_addToFrontA_A(); //starting state 
		list.add(0,ELEMENT_B); //the change
		return list; //return resulting state
	}

	/** Run all tests on scenario: A -> add(0,B) -> [B,A] */
	private void test_A_add0B_BA() {
		System.out.println("\nSCENARIO: [A] -> add(0,B) -> [B,A]\n");
		try {
			// IndexedUnsortedList
			printTest("A_add0B_BA_testAddToFrontC", testAddToFront(A_add0B_BA(), ELEMENT_C, Result.NoException));
			printTest("A_add0B_BA_testAddToRearC", testAddToRear(A_add0B_BA(), ELEMENT_C, Result.NoException));
			printTest("A_add0B_BA_testAddAfterDC", testAddAfter(A_add0B_BA(), ELEMENT_D, ELEMENT_C, Result.NoSuchElement));
			printTest("A_add0B_BA_testAddAfterAC", testAddAfter(A_add0B_BA(), ELEMENT_A, ELEMENT_C, Result.NoException));
			printTest("A_add0B_BA_testAddAfterBC", testAddAfter(A_add0B_BA(), ELEMENT_B, ELEMENT_C, Result.NoException));
			printTest("A_add0B_BA_testAddAtIndexNeg1C", testAddAtIndex(A_add0B_BA(), -1, ELEMENT_C, Result.IndexOutOfBounds));
			printTest("A_add0B_BA_testAddAtIndex0C", testAddAtIndex(A_add0B_BA(), 0, ELEMENT_C, Result.NoException));
			printTest("A_add0B_BA_testAddAtIndex1C", testAddAtIndex(A_add0B_BA(), 1, ELEMENT_C, Result.NoException));
			printTest("A_add0B_BA_testAddAtIndex2C", testAddAtIndex(A_add0B_BA(), 2, ELEMENT_C, Result.NoException));
			printTest("A_add0B_BA_testAddAtIndex3C", testAddAtIndex(A_add0B_BA(), 3, ELEMENT_C, Result.IndexOutOfBounds));
			printTest("A_add0B_BA_testAddC", testAdd(A_add0B_BA(), ELEMENT_C, Result.NoException));
			printTest("A_add0B_BA_testRemoveFirst", testRemoveFirst(A_add0B_BA(), ELEMENT_B, Result.MatchingValue));
			printTest("A_add0B_BA_testRemoveLast", testRemoveLast(A_add0B_BA(), ELEMENT_A, Result.MatchingValue));
			printTest("A_add0B_BA_testRemoveA", testRemoveElement(A_add0B_BA(), ELEMENT_A, Result.MatchingValue));
			printTest("A_add0B_BA_testRemoveB", testRemoveElement(A_add0B_BA(), ELEMENT_B, Result.MatchingValue));
			printTest("A_add0B_BA_testRemoveC", testRemoveElement(A_add0B_BA(), ELEMENT_C, Result.NoSuchElement));
			printTest("A_add0B_BA_testRemoveNeg1", testRemoveIndex(A_add0B_BA(), -1, null, Result.IndexOutOfBounds));
			printTest("A_add0B_BA_testRemove0", testRemoveIndex(A_add0B_BA(), 0, ELEMENT_B, Result.MatchingValue));
			printTest("A_add0B_BA_testRemove1", testRemoveIndex(A_add0B_BA(), 1, ELEMENT_A, Result.MatchingValue));
			printTest("A_add0B_BA_testRemove2", testRemoveIndex(A_add0B_BA(), 2, null, Result.IndexOutOfBounds));
			printTest("A_add0B_BA_testFirst", testFirst(A_add0B_BA(), ELEMENT_B, Result.MatchingValue));
			printTest("A_add0B_BA_testLast", testLast(A_add0B_BA(), ELEMENT_A, Result.MatchingValue));
			printTest("A_add0B_BA_testContainsA", testContains(A_add0B_BA(), ELEMENT_A, Result.True));
			printTest("A_add0B_BA_testContainsB", testContains(A_add0B_BA(), ELEMENT_B, Result.True));
			printTest("A_add0B_BA_testContainsC", testContains(A_add0B_BA(), ELEMENT_C, Result.False));
			printTest("A_add0B_BA_testIsEmpty", testIsEmpty(A_add0B_BA(), Result.False));
			printTest("A_add0B_BA_testSize", testSize(A_add0B_BA(), 2));
			printTest("A_add0B_BA_testToString", testToString(A_add0B_BA(), Result.ValidString));
			printTest("A_add0B_BA_testSetNeg1C", testSet(A_add0B_BA(), -1, ELEMENT_C, Result.IndexOutOfBounds));
			printTest("A_add0B_BA_testSet0C", testSet(A_add0B_BA(), 0, ELEMENT_C, Result.NoException));
			printTest("A_add0B_BA_testSet1C", testSet(A_add0B_BA(), 1, ELEMENT_C, Result.NoException));
			printTest("A_add0B_BA_testSet2C", testSet(A_add0B_BA(), 2, ELEMENT_C, Result.IndexOutOfBounds));
			printTest("A_add0B_BA_testGetNeg1", testGet(A_add0B_BA(), -1, null, Result.IndexOutOfBounds));
			printTest("A_add0B_BA_testGet0", testGet(A_add0B_BA(), 0, ELEMENT_B, Result.MatchingValue));
			printTest("A_add0B_BA_testGet1", testGet(A_add0B_BA(), 1, ELEMENT_A, Result.MatchingValue));
			printTest("A_add0B_BA_testGet2", testGet(A_add0B_BA(), 2, null, Result.IndexOutOfBounds));
			printTest("A_add0B_BA_testIndexOfA", testIndexOf(A_add0B_BA(), ELEMENT_A, 1));
			printTest("A_add0B_BA_testIndexOfB", testIndexOf(A_add0B_BA(), ELEMENT_B, 0));
			printTest("A_add0B_BA_testIndexOfC", testIndexOf(A_add0B_BA(), ELEMENT_C, -1));
			// Iterator
			printTest("A_add0B_BA_testIter", testIter(A_add0B_BA(), Result.NoException));
			printTest("A_add0B_BA_testIterHasNext", testIterHasNext(A_add0B_BA().iterator(), Result.True));
			printTest("A_add0B_BA_testIterNext", testIterNext(A_add0B_BA().iterator(), ELEMENT_B, Result.MatchingValue));
			printTest("A_add0B_BA_testIterRemove", testIterRemove(A_add0B_BA().iterator(), Result.IllegalState));
			printTest("A_add0B_BA_iterNext_testIterHasNext", testIterHasNext(iterAfterNext(A_add0B_BA(), 1), Result.True));
			printTest("A_add0B_BA_iterNext_testIterNext", testIterNext(iterAfterNext(A_add0B_BA(), 1), ELEMENT_A, Result.MatchingValue));
			printTest("A_add0B_BA_iterNext_testIterRemove", testIterRemove(iterAfterNext(A_add0B_BA(), 1), Result.NoException));
			printTest("A_add0B_BA_iterNextRemove_testIterHasNext", testIterHasNext(iterAfterRemove(iterAfterNext(A_add0B_BA(), 1)), Result.True));
			printTest("A_add0B_BA_iterNextRemove_testIterNext", testIterNext(iterAfterRemove(iterAfterNext(A_add0B_BA(), 1)), ELEMENT_A, Result.MatchingValue));
			printTest("A_add0B_BA_iterNextRemove_testIterRemove", testIterRemove(iterAfterRemove(iterAfterNext(A_add0B_BA(), 1)), Result.IllegalState));
			printTest("A_add0B_BA_iterNextNext_testIterHasNext", testIterHasNext(iterAfterNext(A_add0B_BA(), 2), Result.False));
			printTest("A_add0B_BA_iterNextNext_testIterNext", testIterNext(iterAfterNext(A_add0B_BA(), 2), null, Result.NoSuchElement));
			printTest("A_add0B_BA_iterNextNext_testIterRemove", testIterRemove(iterAfterNext(A_add0B_BA(), 2), Result.NoException));
			printTest("A_add0B_BA_iterNextNextRemove_testIterHasNext", testIterHasNext(iterAfterRemove(iterAfterNext(A_add0B_BA(), 2)), Result.False));
			printTest("A_add0B_BA_iterNextNextRemove_testIterNext", testIterNext(iterAfterRemove(iterAfterNext(A_add0B_BA(), 2)), null, Result.NoSuchElement));
			printTest("A_add0B_BA_iterNextNextRemove_testIterRemove", testIterRemove(iterAfterRemove(iterAfterNext(A_add0B_BA(), 2)), Result.IllegalState));
			// ListIterator
			printTest("newList_testListIter", testListIter(newList(), Result.NoException));
			printTest("newList_testListIter", testListIter(newList(), 0, Result.NoException));
		}  catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "test_A_add0B_BA");
			e.printStackTrace();
		}
	}
	
	////////////////////////////////////////////////
	// XXX SCENARIO: [A] -> set(0,B) -> [B]
	////////////////////////////////////////////////

	/** Scenario: [A] -> set(0,B) -> [B] 
	 * @return [B,A] after set(0,B)
	 */
	private IndexedUnsortedList<Integer> A_set0B_B() {
		IndexedUnsortedList<Integer> list = emptyList_addToFrontA_A(); //starting state 
		list.set(0,ELEMENT_B); //the change
		return list; //return resulting state
	}

	/** Run all tests on scenario: A -> set(0,B) -> [B] */
	private void test_A_set0B_B() {
		System.out.println("\nSCENARIO: [A] -> set(0,B) -> [B]\n");
		try {
			printTest("A_set0B_B_testAddToFrontB", testAddToFront(A_set0B_B(), ELEMENT_A, Result.NoException));
			printTest("A_set0B_B_testAddToRearB", testAddToRear(A_set0B_B(), ELEMENT_B, Result.NoException));
			printTest("A_set0B_B_testAddAfterCB", testAddAfter(A_set0B_B(), ELEMENT_C, ELEMENT_A, Result.NoSuchElement));
			printTest("A_set0B_B_testAddAfterAB", testAddAfter(A_set0B_B(), ELEMENT_B, ELEMENT_A, Result.NoException));
			printTest("A_set0B_B_testAddAtIndexNeg1B", testAddAtIndex(A_set0B_B(), -1, ELEMENT_A, Result.IndexOutOfBounds));
			printTest("A_set0B_B_testAddAtIndex0B", testAddAtIndex(A_set0B_B(), 0, ELEMENT_A, Result.NoException));
			printTest("A_set0B_B_testAddAtIndex1B", testAddAtIndex(A_set0B_B(), 1, ELEMENT_A, Result.NoException));
			printTest("A_set0B_B_testAddB", testAdd(A_set0B_B(), ELEMENT_A, Result.NoException));
			printTest("A_set0B_B_testRemoveFirst", testRemoveFirst(A_set0B_B(), ELEMENT_B, Result.MatchingValue));
			printTest("A_set0B_B_testRemoveLast", testRemoveLast(A_set0B_B(), ELEMENT_B, Result.MatchingValue));
			printTest("A_set0B_B_testRemoveA", testRemoveElement(A_set0B_B(), ELEMENT_B, Result.MatchingValue));
			printTest("A_set0B_B_testRemoveB", testRemoveElement(A_set0B_B(), ELEMENT_A, Result.NoSuchElement));
			printTest("A_set0B_B_testRemoveNeg1", testRemoveIndex(A_set0B_B(), -1, null, Result.IndexOutOfBounds));
			printTest("A_set0B_B_testRemove0", testRemoveIndex(A_set0B_B(), 0, ELEMENT_B, Result.MatchingValue));
			printTest("A_set0B_B_testRemove1", testRemoveIndex(A_set0B_B(), 1, null, Result.IndexOutOfBounds));
			printTest("A_set0B_B_testFirst", testFirst(A_set0B_B(), ELEMENT_B, Result.MatchingValue));
			printTest("A_set0B_B_testLast", testLast(A_set0B_B(), ELEMENT_B, Result.MatchingValue));
			printTest("A_set0B_B_testContainsA", testContains(A_set0B_B(), ELEMENT_B, Result.True));
			printTest("A_set0B_B_testContainsB", testContains(A_set0B_B(), ELEMENT_A, Result.False));
			printTest("A_set0B_B_testIsEmpty", testIsEmpty(A_set0B_B(), Result.False));
			printTest("A_set0B_B_testSize", testSize(A_set0B_B(), 1));
			printTest("A_set0B_B_testToString", testToString(A_set0B_B(), Result.ValidString));			
			printTest("A_set0B_B_testSetNeg1B", testSet(A_set0B_B(), -1, ELEMENT_A, Result.IndexOutOfBounds));
			printTest("A_set0B_B_testSet0B", testSet(A_set0B_B(), 0, ELEMENT_A, Result.NoException));
			printTest("A_set0B_B_testGetNeg1", testGet(A_set0B_B(), -1, null, Result.IndexOutOfBounds));
			printTest("A_set0B_B_testGet0", testGet(A_set0B_B(), 0, ELEMENT_B, Result.MatchingValue));
			printTest("A_set0B_B_testIndexOfA", testIndexOf(A_set0B_B(), ELEMENT_B, 0));
			printTest("A_set0B_B_testIndexOfB", testIndexOf(A_set0B_B(), ELEMENT_A, -1));
			// Iterator
			printTest("A_set0B_B_testIter", testIter(A_set0B_B(), Result.NoException));
			printTest("A_set0B_B_testIterHasNext", testIterHasNext(A_set0B_B().iterator(), Result.True));
			printTest("A_set0B_B_testIterNext", testIterNext(A_set0B_B().iterator(), ELEMENT_B, Result.MatchingValue));
			printTest("A_set0B_B_testIterRemove", testIterRemove(A_set0B_B().iterator(), Result.IllegalState));
			printTest("A_set0B_B_iteratorNext_testIterHasNext", testIterHasNext(iterAfterNext(A_set0B_B(), 1), Result.False));
			printTest("A_set0B_B_iteratorNext_testIterNext", testIterNext(iterAfterNext(A_set0B_B(), 1), null, Result.NoSuchElement));
			printTest("A_set0B_B_iteratorNext_testIterRemove", testIterRemove(iterAfterNext(A_set0B_B(), 1), Result.NoException));
			printTest("A_set0B_B_iteratorNextRemove_testIterHasNext", testIterHasNext(iterAfterRemove(iterAfterNext(A_set0B_B(), 1)), Result.False));
			printTest("A_set0B_B_iteratorNextRemove_testIterNext", testIterNext(iterAfterRemove(iterAfterNext(A_set0B_B(), 1)), null, Result.NoSuchElement));
			printTest("A_set0B_B_iteratorNextRemove_testIterRemove", testIterRemove(iterAfterRemove(iterAfterNext(A_set0B_B(), 1)), Result.IllegalState));
			// ListIterator
			printTest("newList_testListIter", testListIter(newList(), Result.NoException));
			printTest("newList_testListIter", testListIter(newList(), 0, Result.NoException));

		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "test_A_set0B_B");
			e.printStackTrace();
		}
	}
	
	////////////////////////////////////////////////
	// XXX SCENARIO: [A,B] -> removeFirst() -> [B]
	////////////////////////////////////////////////
	/** Scenario: [A,B] -> removeFirst() -> [B] 
	 * @return [B] after removeFirst()
	 */
	private IndexedUnsortedList<Integer> AB_removeFirst_B() {
		IndexedUnsortedList<Integer> list = A_addToRearB_AB(); //starting state
		list.removeFirst(); //the change
		return list;
	}
	/** Run all tests on scenario: [A,B] -> removeFirst() -> [B] */
	private void test_AB_removeFirst_B() {
		System.out.println("\nSCENARIO: [A,B] -> removeFirst() -> [B]\n");
		try{
			printTest("AB_removeFirst_B_testAddToFrontB", testAddToFront(AB_removeFirst_B(), ELEMENT_A, Result.NoException));
			printTest("AB_removeFirst_B_testAddToRearB", testAddToRear(AB_removeFirst_B(), ELEMENT_B, Result.NoException));
			printTest("AB_removeFirst_B_testAddAfterCB", testAddAfter(AB_removeFirst_B(), ELEMENT_C, ELEMENT_A, Result.NoSuchElement));
			printTest("AB_removeFirst_B_testAddAfterAB", testAddAfter(AB_removeFirst_B(), ELEMENT_B, ELEMENT_A, Result.NoException));
			printTest("AB_removeFirst_B_testAddAtIndexNeg1B", testAddAtIndex(AB_removeFirst_B(), -1, ELEMENT_A, Result.IndexOutOfBounds));
			printTest("AB_removeFirst_B_testAddAtIndex0B", testAddAtIndex(AB_removeFirst_B(), 0, ELEMENT_A, Result.NoException));
			printTest("AB_removeFirst_B_testAddAtIndex1B", testAddAtIndex(AB_removeFirst_B(), 1, ELEMENT_A, Result.NoException));
			printTest("AB_removeFirst_B_testAddB", testAdd(AB_removeFirst_B(), ELEMENT_A, Result.NoException));
			printTest("AB_removeFirst_B_testRemoveFirst", testRemoveFirst(AB_removeFirst_B(), ELEMENT_B, Result.MatchingValue));
			printTest("AB_removeFirst_B_testRemoveLast", testRemoveLast(AB_removeFirst_B(), ELEMENT_B, Result.MatchingValue));
			printTest("AB_removeFirst_B_testRemoveA", testRemoveElement(AB_removeFirst_B(), ELEMENT_B, Result.MatchingValue));
			printTest("AB_removeFirst_B_testRemoveB", testRemoveElement(AB_removeFirst_B(), ELEMENT_A, Result.NoSuchElement));
			printTest("AB_removeFirst_B_testRemoveNeg1", testRemoveIndex(AB_removeFirst_B(), -1, null, Result.IndexOutOfBounds));
			printTest("AB_removeFirst_B_testRemove0", testRemoveIndex(AB_removeFirst_B(), 0, ELEMENT_B, Result.MatchingValue));
			printTest("AB_removeFirst_B_testRemove1", testRemoveIndex(AB_removeFirst_B(), 1, null, Result.IndexOutOfBounds));
			printTest("AB_removeFirst_B_testFirst", testFirst(AB_removeFirst_B(), ELEMENT_B, Result.MatchingValue));
			printTest("AB_removeFirst_B_testLast", testLast(AB_removeFirst_B(), ELEMENT_B, Result.MatchingValue));
			printTest("AB_removeFirst_B_testContainsA", testContains(AB_removeFirst_B(), ELEMENT_B, Result.True));
			printTest("AB_removeFirst_B_testContainsB", testContains(AB_removeFirst_B(), ELEMENT_A, Result.False));
			printTest("AB_removeFirst_B_testIsEmpty", testIsEmpty(AB_removeFirst_B(), Result.False));
			printTest("AB_removeFirst_B_testSize", testSize(AB_removeFirst_B(), 1));
			printTest("AB_removeFirst_B_testToString", testToString(AB_removeFirst_B(), Result.ValidString));			
			printTest("AB_removeFirst_B_testSetNeg1B", testSet(AB_removeFirst_B(), -1, ELEMENT_A, Result.IndexOutOfBounds));
			printTest("AB_removeFirst_B_testSet0B", testSet(AB_removeFirst_B(), 0, ELEMENT_A, Result.NoException));
			printTest("AB_removeFirst_B_testGetNeg1", testGet(AB_removeFirst_B(), -1, null, Result.IndexOutOfBounds));
			printTest("AB_removeFirst_B_testGet0", testGet(AB_removeFirst_B(), 0, ELEMENT_B, Result.MatchingValue));
			printTest("AB_removeFirst_B_testIndexOfA", testIndexOf(AB_removeFirst_B(), ELEMENT_B, 0));
			printTest("AB_removeFirst_B_testIndexOfB", testIndexOf(AB_removeFirst_B(), ELEMENT_A, -1));
			// Iterator
			printTest("AB_removeFirst_B_testIter", testIter(AB_removeFirst_B(), Result.NoException));
			printTest("AB_removeFirst_B_testIterHasNext", testIterHasNext(AB_removeFirst_B().iterator(), Result.True));
			printTest("AB_removeFirst_B_testIterNext", testIterNext(AB_removeFirst_B().iterator(), ELEMENT_B, Result.MatchingValue));
			printTest("AB_removeFirst_B_testIterRemove", testIterRemove(AB_removeFirst_B().iterator(), Result.IllegalState));
			printTest("AB_removeFirst_B_iteratorNext_testIterHasNext", testIterHasNext(iterAfterNext(AB_removeFirst_B(), 1), Result.False));
			printTest("AB_removeFirst_B_iteratorNext_testIterNext", testIterNext(iterAfterNext(AB_removeFirst_B(), 1), null, Result.NoSuchElement));
			printTest("AB_removeFirst_B_iteratorNext_testIterRemove", testIterRemove(iterAfterNext(AB_removeFirst_B(), 1), Result.NoException));
			printTest("AB_removeFirst_B_iteratorNextRemove_testIterHasNext", testIterHasNext(iterAfterRemove(iterAfterNext(AB_removeFirst_B(), 1)), Result.False));
			printTest("AB_removeFirst_B_iteratorNextRemove_testIterNext", testIterNext(iterAfterRemove(iterAfterNext(AB_removeFirst_B(), 1)), null, Result.NoSuchElement));
			printTest("AB_removeFirst_B_iteratorNextRemove_testIterRemove", testIterRemove(iterAfterRemove(iterAfterNext(AB_removeFirst_B(), 1)), Result.IllegalState));
			// ListIterator
			printTest("newList_testListIter", testListIter(newList(), Result.NoException));
			printTest("newList_testListIter", testListIter(newList(), 0, Result.NoException));

		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "test_AB_removeFirst_B");
			e.printStackTrace();
		}
	}
	
	////////////////////////////////////////////////
	// XXX SCENARIO: [A,B] -> remove(A) -> [B]
	////////////////////////////////////////////////
	/** Scenario: [A,B] -> remove(A) -> [B] 
	 * @return [B] after remove(A)
	 */
	private IndexedUnsortedList<Integer> AB_removeA_B() {
		IndexedUnsortedList<Integer> list = A_addToRearB_AB(); //starting state
		list.remove(ELEMENT_A); //the change
		return list;
	}
	/** Run all tests on scenario: [A,B] -> remove(A) -> [B] */
	private void test_AB_removeA_B() {
		System.out.println("\nSCENARIO: [A,B] -> remove(A) -> [B]\n");
		try{
			printTest("AB_removeA_B_testAddToFrontB", testAddToFront(AB_removeA_B(), ELEMENT_A, Result.NoException));
			printTest("AB_removeA_B_testAddToRearB", testAddToRear(AB_removeA_B(), ELEMENT_B, Result.NoException));
			printTest("AB_removeA_B_testAddAfterCB", testAddAfter(AB_removeA_B(), ELEMENT_C, ELEMENT_A, Result.NoSuchElement));
			printTest("AB_removeA_B_testAddAfterAB", testAddAfter(AB_removeA_B(), ELEMENT_B, ELEMENT_A, Result.NoException));
			printTest("AB_removeA_B_testAddAtIndexNeg1B", testAddAtIndex(AB_removeA_B(), -1, ELEMENT_A, Result.IndexOutOfBounds));
			printTest("AB_removeA_B_testAddAtIndex0B", testAddAtIndex(AB_removeA_B(), 0, ELEMENT_A, Result.NoException));
			printTest("AB_removeA_B_testAddAtIndex1B", testAddAtIndex(AB_removeA_B(), 1, ELEMENT_A, Result.NoException));
			printTest("AB_removeA_B_testAddB", testAdd(AB_removeA_B(), ELEMENT_A, Result.NoException));
			printTest("AB_removeA_B_testRemoveFirst", testRemoveFirst(AB_removeA_B(), ELEMENT_B, Result.MatchingValue));
			printTest("AB_removeA_B_testRemoveLast", testRemoveLast(AB_removeA_B(), ELEMENT_B, Result.MatchingValue));
			printTest("AB_removeA_B_testRemoveA", testRemoveElement(AB_removeA_B(), ELEMENT_B, Result.MatchingValue));
			printTest("AB_removeA_B_testRemoveB", testRemoveElement(AB_removeA_B(), ELEMENT_A, Result.NoSuchElement));
			printTest("AB_removeA_B_testRemoveNeg1", testRemoveIndex(AB_removeA_B(), -1, null, Result.IndexOutOfBounds));
			printTest("AB_removeA_B_testRemove0", testRemoveIndex(AB_removeA_B(), 0, ELEMENT_B, Result.MatchingValue));
			printTest("AB_removeA_B_testRemove1", testRemoveIndex(AB_removeA_B(), 1, null, Result.IndexOutOfBounds));
			printTest("AB_removeA_B_testFirst", testFirst(AB_removeA_B(), ELEMENT_B, Result.MatchingValue));
			printTest("AB_removeA_B_testLast", testLast(AB_removeA_B(), ELEMENT_B, Result.MatchingValue));
			printTest("AB_removeA_B_testContainsA", testContains(AB_removeA_B(), ELEMENT_B, Result.True));
			printTest("AB_removeA_B_testContainsB", testContains(AB_removeA_B(), ELEMENT_A, Result.False));
			printTest("AB_removeA_B_testIsEmpty", testIsEmpty(AB_removeA_B(), Result.False));
			printTest("AB_removeA_B_testSize", testSize(AB_removeA_B(), 1));
			printTest("AB_removeA_B_testToString", testToString(AB_removeA_B(), Result.ValidString));			
			printTest("AB_removeA_B_testSetNeg1B", testSet(AB_removeA_B(), -1, ELEMENT_A, Result.IndexOutOfBounds));
			printTest("AB_removeA_B_testSet0B", testSet(AB_removeA_B(), 0, ELEMENT_A, Result.NoException));
			printTest("AB_removeA_B_testGetNeg1", testGet(AB_removeA_B(), -1, null, Result.IndexOutOfBounds));
			printTest("AB_removeA_B_testGet0", testGet(AB_removeA_B(), 0, ELEMENT_B, Result.MatchingValue));
			printTest("AB_removeA_B_testIndexOfA", testIndexOf(AB_removeA_B(), ELEMENT_B, 0));
			printTest("AB_removeA_B_testIndexOfB", testIndexOf(AB_removeA_B(), ELEMENT_A, -1));
			// Iterator
			printTest("AB_removeA_B_testIter", testIter(AB_removeA_B(), Result.NoException));
			printTest("AB_removeA_B_testIterHasNext", testIterHasNext(AB_removeA_B().iterator(), Result.True));
			printTest("AB_removeA_B_testIterNext", testIterNext(AB_removeA_B().iterator(), ELEMENT_B, Result.MatchingValue));
			printTest("AB_removeA_B_testIterRemove", testIterRemove(AB_removeA_B().iterator(), Result.IllegalState));
			printTest("AB_removeA_B_iteratorNext_testIterHasNext", testIterHasNext(iterAfterNext(AB_removeA_B(), 1), Result.False));
			printTest("AB_removeA_B_iteratorNext_testIterNext", testIterNext(iterAfterNext(AB_removeA_B(), 1), null, Result.NoSuchElement));
			printTest("AB_removeA_B_iteratorNext_testIterRemove", testIterRemove(iterAfterNext(AB_removeA_B(), 1), Result.NoException));
			printTest("AB_removeA_B_iteratorNextRemove_testIterHasNext", testIterHasNext(iterAfterRemove(iterAfterNext(AB_removeA_B(), 1)), Result.False));
			printTest("AB_removeA_B_iteratorNextRemove_testIterNext", testIterNext(iterAfterRemove(iterAfterNext(AB_removeA_B(), 1)), null, Result.NoSuchElement));
			printTest("AB_removeA_B_iteratorNextRemove_testIterRemove", testIterRemove(iterAfterRemove(iterAfterNext(AB_removeA_B(), 1)), Result.IllegalState));
			// ListIterator
			printTest("newList_testListIter", testListIter(newList(), Result.NoException));
			printTest("newList_testListIter", testListIter(newList(), 0, Result.NoException));

		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "test_AB_removeA_B");
			e.printStackTrace();
		}
	}
	
	////////////////////////////////////////////////
	// XXX SCENARIO: [A,B] -> removeLast() -> [A]
	////////////////////////////////////////////////
	/** Scenario: [A,B] -> removeLast() -> [A] 
	 * @return [B] after removeLast()
	 */
	private IndexedUnsortedList<Integer> AB_removeLast_A() {
		IndexedUnsortedList<Integer> list = A_addToRearB_AB(); //starting state
		list.removeLast(); //the change
		return list;
	}
	/** Run all tests on scenario: [A,B] -> removeLast() -> [A] */
	private void test_AB_removeLast_A() {
		System.out.println("\nSCENARIO: [A,B] -> removeLast() -> [A]\n");
		try{
			printTest("AB_removeLast_A_testAddToFrontB", testAddToFront(AB_removeLast_A(), ELEMENT_B, Result.NoException));
			printTest("AB_removeLast_A_testAddToRearB", testAddToRear(AB_removeLast_A(), ELEMENT_A, Result.NoException));
			printTest("AB_removeLast_A_testAddAfterCB", testAddAfter(AB_removeLast_A(), ELEMENT_C, ELEMENT_B, Result.NoSuchElement));
			printTest("AB_removeLast_A_testAddAfterAB", testAddAfter(AB_removeLast_A(), ELEMENT_A, ELEMENT_B, Result.NoException));
			printTest("AB_removeLast_A_testAddAtIndexNeg1B", testAddAtIndex(AB_removeLast_A(), -1, ELEMENT_B, Result.IndexOutOfBounds));
			printTest("AB_removeLast_A_testAddAtIndex0B", testAddAtIndex(AB_removeLast_A(), 0, ELEMENT_B, Result.NoException));
			printTest("AB_removeLast_A_testAddAtIndex1B", testAddAtIndex(AB_removeLast_A(), 1, ELEMENT_B, Result.NoException));
			printTest("AB_removeLast_A_testAddB", testAdd(AB_removeLast_A(), ELEMENT_B, Result.NoException));
			printTest("AB_removeLast_A_testRemoveFirst", testRemoveFirst(AB_removeLast_A(), ELEMENT_A, Result.MatchingValue));
			printTest("AB_removeLast_A_testRemoveLast", testRemoveLast(AB_removeLast_A(), ELEMENT_A, Result.MatchingValue));
			printTest("AB_removeLast_A_testRemoveA", testRemoveElement(AB_removeLast_A(), ELEMENT_A, Result.MatchingValue));
			printTest("AB_removeLast_A_testRemoveB", testRemoveElement(AB_removeLast_A(), ELEMENT_B, Result.NoSuchElement));
			printTest("AB_removeLast_A_testRemoveNeg1", testRemoveIndex(AB_removeLast_A(), -1, null, Result.IndexOutOfBounds));
			printTest("AB_removeLast_A_testRemove0", testRemoveIndex(AB_removeLast_A(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("AB_removeLast_A_testRemove1", testRemoveIndex(AB_removeLast_A(), 1, null, Result.IndexOutOfBounds));
			printTest("AB_removeLast_A_testFirst", testFirst(AB_removeLast_A(), ELEMENT_A, Result.MatchingValue));
			printTest("AB_removeLast_A_testLast", testLast(AB_removeLast_A(), ELEMENT_A, Result.MatchingValue));
			printTest("AB_removeLast_A_testContainsA", testContains(AB_removeLast_A(), ELEMENT_A, Result.True));
			printTest("AB_removeLast_A_testContainsB", testContains(AB_removeLast_A(), ELEMENT_B, Result.False));
			printTest("AB_removeLast_A_testIsEmpty", testIsEmpty(AB_removeLast_A(), Result.False));
			printTest("AB_removeLast_A_testSize", testSize(AB_removeLast_A(), 1));
			printTest("AB_removeLast_A_testToString", testToString(AB_removeLast_A(), Result.ValidString));			
			printTest("AB_removeLast_A_testSetNeg1B", testSet(AB_removeLast_A(), -1, ELEMENT_B, Result.IndexOutOfBounds));
			printTest("AB_removeLast_A_testSet0B", testSet(AB_removeLast_A(), 0, ELEMENT_B, Result.NoException));
			printTest("AB_removeLast_A_testGetNeg1", testGet(AB_removeLast_A(), -1, null, Result.IndexOutOfBounds));
			printTest("AB_removeLast_A_testGet0", testGet(AB_removeLast_A(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("AB_removeLast_A_testIndexOfA", testIndexOf(AB_removeLast_A(), ELEMENT_A, 0));
			printTest("AB_removeLast_A_testIndexOfB", testIndexOf(AB_removeLast_A(), ELEMENT_B, -1));
			// Iterator
			printTest("AB_removeLast_A_testIter", testIter(AB_removeLast_A(), Result.NoException));
			printTest("AB_removeLast_A_testIterHasNext", testIterHasNext(AB_removeLast_A().iterator(), Result.True));
			printTest("AB_removeLast_A_testIterNext", testIterNext(AB_removeLast_A().iterator(), ELEMENT_A, Result.MatchingValue));
			printTest("AB_removeLast_A_testIterRemove", testIterRemove(AB_removeLast_A().iterator(), Result.IllegalState));
			printTest("AB_removeLast_A_iteratorNext_testIterHasNext", testIterHasNext(iterAfterNext(AB_removeLast_A(), 1), Result.False));
			printTest("AB_removeLast_A_iteratorNext_testIterNext", testIterNext(iterAfterNext(AB_removeLast_A(), 1), null, Result.NoSuchElement));
			printTest("AB_removeLast_A_iteratorNext_testIterRemove", testIterRemove(iterAfterNext(AB_removeLast_A(), 1), Result.NoException));
			printTest("AB_removeLast_A_iteratorNextRemove_testIterHasNext", testIterHasNext(iterAfterRemove(iterAfterNext(AB_removeLast_A(), 1)), Result.False));
			printTest("AB_removeLast_A_iteratorNextRemove_testIterNext", testIterNext(iterAfterRemove(iterAfterNext(AB_removeLast_A(), 1)), null, Result.NoSuchElement));
			printTest("AB_removeLast_A_iteratorNextRemove_testIterRemove", testIterRemove(iterAfterRemove(iterAfterNext(AB_removeLast_A(), 1)), Result.IllegalState));
			// ListIterator
			printTest("newList_testListIter", testListIter(newList(), Result.NoException));
			printTest("newList_testListIter", testListIter(newList(), 0, Result.NoException));
	
		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "test_AB_removeLast_A");
			e.printStackTrace();
		}
	}
	
	////////////////////////////////////////////////
	// XXX SCENARIO: [A,B,C] -> remove1 -> [A,C]
	////////////////////////////////////////////////

	/** Scenario: [A,B,C] -> remove(1) -> [A,C] 
	 * @return [A,C] after remove(1)
	 */
	private IndexedUnsortedList<Integer> ABC_remove1_AC() {
		//once a builder method resulting in list [A,B,C] is available,
		//the following 4 statements leading up to remove(1) can be
		//replaced as shown in the previous examples
		IndexedUnsortedList<Integer> list = newList();
		list.add(ELEMENT_A);
		list.add(ELEMENT_B);
		list.add(ELEMENT_C);
		list.remove(1);
		return list;
	}

	/** Run all tests on scenario: [A,B,C] -> remove(1) -> [A,C] */
	private void test_ABC_remove1_AC() {
		System.out.println("\nSCENARIO: [A,B,C] -> remove(1) -> [A,C]\n");
		try {
			printTest("ABC_remove1_AC_testAddToFrontD", testAddToFront(ABC_remove1_AC(), ELEMENT_D, Result.NoException));
			printTest("ABC_remove1_AC_testAddToRearD", testAddToRear(ABC_remove1_AC(), ELEMENT_D, Result.NoException));
			printTest("ABC_remove1_AC_testAddAfterBD", testAddAfter(ABC_remove1_AC(), ELEMENT_B, ELEMENT_D, Result.NoSuchElement));
			printTest("ABC_remove1_AC_testAddAfterAD", testAddAfter(ABC_remove1_AC(), ELEMENT_A, ELEMENT_D, Result.NoException));
			printTest("ABC_remove1_AC_testAddAfterCD", testAddAfter(ABC_remove1_AC(), ELEMENT_C, ELEMENT_D, Result.NoException));
			printTest("ABC_remove1_AC_testAddAtIndexNeg1D", testAddAtIndex(ABC_remove1_AC(), -1, ELEMENT_D, Result.IndexOutOfBounds));
			printTest("ABC_remove1_AC_testAddAtIndex0D", testAddAtIndex(ABC_remove1_AC(), 0, ELEMENT_D, Result.NoException));
			printTest("ABC_remove1_AC_testAddAtIndex1D", testAddAtIndex(ABC_remove1_AC(), 1, ELEMENT_D, Result.NoException));
			printTest("ABC_remove1_AC_testAddAtIndex2D", testAddAtIndex(ABC_remove1_AC(), 2, ELEMENT_D, Result.NoException));
			printTest("ABC_remove1_AC_testAddAtIndex3D", testAddAtIndex(ABC_remove1_AC(), 3, ELEMENT_D, Result.IndexOutOfBounds));
			printTest("ABC_remove1_AC_testAddD", testAdd(ABC_remove1_AC(), ELEMENT_D, Result.NoException));
			printTest("ABC_remove1_AC_testRemoveFirst", testRemoveFirst(ABC_remove1_AC(), ELEMENT_A, Result.MatchingValue));
			printTest("ABC_remove1_AC_testRemoveLast", testRemoveLast(ABC_remove1_AC(), ELEMENT_C, Result.MatchingValue));
			printTest("ABC_remove1_AC_testRemoveA", testRemoveElement(ABC_remove1_AC(), ELEMENT_A, Result.MatchingValue));
			printTest("ABC_remove1_AC_testRemoveB", testRemoveElement(ABC_remove1_AC(), ELEMENT_B, Result.NoSuchElement));
			printTest("ABC_remove1_AC_testRemoveC", testRemoveElement(ABC_remove1_AC(), ELEMENT_C, Result.MatchingValue));
			printTest("ABC_remove1_AC_testRemoveNeg1", testRemoveIndex(ABC_remove1_AC(), -1, null, Result.IndexOutOfBounds));
			printTest("ABC_remove1_AC_testRemove0", testRemoveIndex(ABC_remove1_AC(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("ABC_remove1_AC_testRemove1", testRemoveIndex(ABC_remove1_AC(), 1, ELEMENT_C, Result.MatchingValue));
			printTest("ABC_remove1_AC_testRemove2", testRemoveIndex(ABC_remove1_AC(), 2, null, Result.IndexOutOfBounds));
			printTest("ABC_remove1_AC_testFirst", testFirst(ABC_remove1_AC(), ELEMENT_A, Result.MatchingValue));
			printTest("ABC_remove1_AC_testLast", testLast(ABC_remove1_AC(), ELEMENT_C, Result.MatchingValue));
			printTest("ABC_remove1_AC_testContainsA", testContains(ABC_remove1_AC(), ELEMENT_A, Result.True));
			printTest("ABC_remove1_AC_testContainsB", testContains(ABC_remove1_AC(), ELEMENT_B, Result.False));
			printTest("ABC_remove1_AC_testContainsC", testContains(ABC_remove1_AC(), ELEMENT_C, Result.True));
			printTest("ABC_remove1_AC_testIsEmpty", testIsEmpty(ABC_remove1_AC(), Result.False));
			printTest("ABC_remove1_AC_testSize", testSize(ABC_remove1_AC(), 2));
			printTest("ABC_remove1_AC_testToString", testToString(ABC_remove1_AC(), Result.ValidString));
			printTest("ABC_remove1_AC_testSetNeg1D", testSet(ABC_remove1_AC(), -1, ELEMENT_D, Result.IndexOutOfBounds));
			printTest("ABC_remove1_AC_testSet0D", testSet(ABC_remove1_AC(), 0, ELEMENT_D, Result.NoException));
			printTest("ABC_remove1_AC_testSet1D", testSet(ABC_remove1_AC(), 1, ELEMENT_D, Result.NoException));
			printTest("ABC_remove1_AC_testSet2D", testSet(ABC_remove1_AC(), 2, ELEMENT_D, Result.IndexOutOfBounds));
			printTest("ABC_remove1_AC_testGetNeg1", testGet(ABC_remove1_AC(), -1, null, Result.IndexOutOfBounds));
			printTest("ABC_remove1_AC_testGet0", testGet(ABC_remove1_AC(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("ABC_remove1_AC_testGet1", testGet(ABC_remove1_AC(), 1, ELEMENT_C, Result.MatchingValue));
			printTest("ABC_remove1_AC_testGet2", testGet(ABC_remove1_AC(), 2, null, Result.IndexOutOfBounds));
			printTest("ABC_remove1_AC_testIndexOfA", testIndexOf(ABC_remove1_AC(), ELEMENT_A, 0));
			printTest("ABC_remove1_AC_testIndexOfB", testIndexOf(ABC_remove1_AC(), ELEMENT_B, -1));
			printTest("ABC_remove1_AC_testIndexOfC", testIndexOf(ABC_remove1_AC(), ELEMENT_C, 1));
			// Iterator
			printTest("ABC_remove1_AC_testIter", testIter(ABC_remove1_AC(), Result.NoException));
			printTest("ABC_remove1_AC_testIterHasNext", testIterHasNext(ABC_remove1_AC().iterator(), Result.True));
			printTest("ABC_remove1_AC_testIterNext", testIterNext(ABC_remove1_AC().iterator(), ELEMENT_A, Result.MatchingValue));
			printTest("ABC_remove1_AC_testIterRemove", testIterRemove(ABC_remove1_AC().iterator(), Result.IllegalState));
			printTest("ABC_remove1_AC_iterNext_testIterHasNext", testIterHasNext(iterAfterNext(ABC_remove1_AC(), 1), Result.True));
			printTest("ABC_remove1_AC_iterNext_testIterNext", testIterNext(iterAfterNext(ABC_remove1_AC(), 1), ELEMENT_C, Result.MatchingValue));
			printTest("ABC_remove1_AC_iterNext_testIterRemove", testIterRemove(iterAfterNext(ABC_remove1_AC(), 1), Result.NoException));
			printTest("ABC_remove1_AC_iterNextRemove_testIterHasNext", testIterHasNext(iterAfterRemove(iterAfterNext(ABC_remove1_AC(), 1)), Result.True));
			printTest("ABC_remove1_AC_iterNextRemove_testIterNext", testIterNext(iterAfterRemove(iterAfterNext(ABC_remove1_AC(), 1)), ELEMENT_C, Result.MatchingValue));
			printTest("ABC_remove1_AC_iterNextRemove_testIterRemove", testIterRemove(iterAfterRemove(iterAfterNext(ABC_remove1_AC(), 1)), Result.IllegalState));
			printTest("ABC_remove1_AC_iterNextNext_testIterHasNext", testIterHasNext(iterAfterNext(ABC_remove1_AC(), 2), Result.False));
			printTest("ABC_remove1_AC_iterNextNext_testIterNext", testIterNext(iterAfterNext(ABC_remove1_AC(), 2), null, Result.NoSuchElement));
			printTest("ABC_remove1_AC_iterNextNext_testIterRemove", testIterRemove(iterAfterNext(ABC_remove1_AC(), 2), Result.NoException));
			printTest("ABC_remove1_AC_iterNextNextRemove_testIterHasNext", testIterHasNext(iterAfterRemove(iterAfterNext(ABC_remove1_AC(), 2)), Result.False));
			printTest("ABC_remove1_AC_iterNextNextRemove_testIterNext", testIterNext(iterAfterRemove(iterAfterNext(ABC_remove1_AC(), 2)), null, Result.NoSuchElement));
			printTest("ABC_remove1_AC_iterNextNextRemove_testIterRemove", testIterRemove(iterAfterRemove(iterAfterNext(ABC_remove1_AC(), 2)), Result.IllegalState));
			// ListIterator
			printTest("newList_testListIter", testListIter(newList(), Result.NoException));
			printTest("newList_testListIter", testListIter(newList(), 0, Result.NoException));
		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "test_ABC_remove1_AC");
			e.printStackTrace();
		}
	}

	////////////////////////////////////////////////
	// XXX SCENARIO: [A,B,C] -> removeB -> [A,C]
	////////////////////////////////////////////////

	/** Scenario: [A,B,C] -> remove(B) -> [A,C] 
	 * @return [A,C] after remove(B)
	 */
	private IndexedUnsortedList<Integer> ABC_removeB_AC() {
		//once a builder method resulting in list [A,B,C] is available,
		//the following 4 statements leading up to remove(B) can be
		//replaced as shown in the previous examples
		IndexedUnsortedList<Integer> list = newList();
		list.add(ELEMENT_A);
		list.add(ELEMENT_B);
		list.add(ELEMENT_C);
		list.remove(ELEMENT_B);
		return list;
	}

	/** Run all tests on scenario: [A,B,C] -> remove(B) -> [A,C] */
	private void test_ABC_removeB_AC() {
		System.out.println("\nSCENARIO: [A,B,C] -> remove(B) -> [A,C]\n");
		try {
			printTest("ABC_removeB_AC_testAddToFrontD", testAddToFront(ABC_removeB_AC(), ELEMENT_D, Result.NoException));
			printTest("ABC_removeB_AC_testAddToRearD", testAddToRear(ABC_removeB_AC(), ELEMENT_D, Result.NoException));
			printTest("ABC_removeB_AC_testAddAfterBD", testAddAfter(ABC_removeB_AC(), ELEMENT_B, ELEMENT_D, Result.NoSuchElement));
			printTest("ABC_removeB_AC_testAddAfterAD", testAddAfter(ABC_removeB_AC(), ELEMENT_A, ELEMENT_D, Result.NoException));
			printTest("ABC_removeB_AC_testAddAfterCD", testAddAfter(ABC_removeB_AC(), ELEMENT_C, ELEMENT_D, Result.NoException));
			printTest("ABC_removeB_AC_testAddAtIndexNeg1D", testAddAtIndex(ABC_removeB_AC(), -1, ELEMENT_D, Result.IndexOutOfBounds));
			printTest("ABC_removeB_AC_testAddAtIndex0D", testAddAtIndex(ABC_removeB_AC(), 0, ELEMENT_D, Result.NoException));
			printTest("ABC_removeB_AC_testAddAtIndex1D", testAddAtIndex(ABC_removeB_AC(), 1, ELEMENT_D, Result.NoException));
			printTest("ABC_removeB_AC_testAddAtIndex2D", testAddAtIndex(ABC_removeB_AC(), 2, ELEMENT_D, Result.NoException));
			printTest("ABC_removeB_AC_testAddAtIndex3D", testAddAtIndex(ABC_removeB_AC(), 3, ELEMENT_D, Result.IndexOutOfBounds));
			printTest("ABC_removeB_AC_testAddD", testAdd(ABC_removeB_AC(), ELEMENT_D, Result.NoException));
			printTest("ABC_removeB_AC_testRemoveFirst", testRemoveFirst(ABC_removeB_AC(), ELEMENT_A, Result.MatchingValue));
			printTest("ABC_removeB_AC_testRemoveLast", testRemoveLast(ABC_removeB_AC(), ELEMENT_C, Result.MatchingValue));
			printTest("ABC_removeB_AC_testRemoveA", testRemoveElement(ABC_removeB_AC(), ELEMENT_A, Result.MatchingValue));
			printTest("ABC_removeB_AC_testRemoveB", testRemoveElement(ABC_removeB_AC(), ELEMENT_B, Result.NoSuchElement));
			printTest("ABC_removeB_AC_testRemoveC", testRemoveElement(ABC_removeB_AC(), ELEMENT_C, Result.MatchingValue));
			printTest("ABC_removeB_AC_testRemoveNeg1", testRemoveIndex(ABC_removeB_AC(), -1, null, Result.IndexOutOfBounds));
			printTest("ABC_removeB_AC_testRemove0", testRemoveIndex(ABC_removeB_AC(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("ABC_removeB_AC_testremoveB", testRemoveIndex(ABC_removeB_AC(), 1, ELEMENT_C, Result.MatchingValue));
			printTest("ABC_removeB_AC_testRemove2", testRemoveIndex(ABC_removeB_AC(), 2, null, Result.IndexOutOfBounds));
			printTest("ABC_removeB_AC_testFirst", testFirst(ABC_removeB_AC(), ELEMENT_A, Result.MatchingValue));
			printTest("ABC_removeB_AC_testLast", testLast(ABC_removeB_AC(), ELEMENT_C, Result.MatchingValue));
			printTest("ABC_removeB_AC_testContainsA", testContains(ABC_removeB_AC(), ELEMENT_A, Result.True));
			printTest("ABC_removeB_AC_testContainsB", testContains(ABC_removeB_AC(), ELEMENT_B, Result.False));
			printTest("ABC_removeB_AC_testContainsC", testContains(ABC_removeB_AC(), ELEMENT_C, Result.True));
			printTest("ABC_removeB_AC_testIsEmpty", testIsEmpty(ABC_removeB_AC(), Result.False));
			printTest("ABC_removeB_AC_testSize", testSize(ABC_removeB_AC(), 2));
			printTest("ABC_removeB_AC_testToString", testToString(ABC_removeB_AC(), Result.ValidString));
			printTest("ABC_removeB_AC_testSetNeg1D", testSet(ABC_removeB_AC(), -1, ELEMENT_D, Result.IndexOutOfBounds));
			printTest("ABC_removeB_AC_testSet0D", testSet(ABC_removeB_AC(), 0, ELEMENT_D, Result.NoException));
			printTest("ABC_removeB_AC_testSet1D", testSet(ABC_removeB_AC(), 1, ELEMENT_D, Result.NoException));
			printTest("ABC_removeB_AC_testSet2D", testSet(ABC_removeB_AC(), 2, ELEMENT_D, Result.IndexOutOfBounds));
			printTest("ABC_removeB_AC_testGetNeg1", testGet(ABC_removeB_AC(), -1, null, Result.IndexOutOfBounds));
			printTest("ABC_removeB_AC_testGet0", testGet(ABC_removeB_AC(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("ABC_removeB_AC_testGet1", testGet(ABC_removeB_AC(), 1, ELEMENT_C, Result.MatchingValue));
			printTest("ABC_removeB_AC_testGet2", testGet(ABC_removeB_AC(), 2, null, Result.IndexOutOfBounds));
			printTest("ABC_removeB_AC_testIndexOfA", testIndexOf(ABC_removeB_AC(), ELEMENT_A, 0));
			printTest("ABC_removeB_AC_testIndexOfB", testIndexOf(ABC_removeB_AC(), ELEMENT_B, -1));
			printTest("ABC_removeB_AC_testIndexOfC", testIndexOf(ABC_removeB_AC(), ELEMENT_C, 1));
			// Iterator
			printTest("ABC_removeB_AC_testIter", testIter(ABC_removeB_AC(), Result.NoException));
			printTest("ABC_removeB_AC_testIterHasNext", testIterHasNext(ABC_removeB_AC().iterator(), Result.True));
			printTest("ABC_removeB_AC_testIterNext", testIterNext(ABC_removeB_AC().iterator(), ELEMENT_A, Result.MatchingValue));
			printTest("ABC_removeB_AC_testIterRemove", testIterRemove(ABC_removeB_AC().iterator(), Result.IllegalState));
			printTest("ABC_removeB_AC_iterNext_testIterHasNext", testIterHasNext(iterAfterNext(ABC_removeB_AC(), 1), Result.True));
			printTest("ABC_removeB_AC_iterNext_testIterNext", testIterNext(iterAfterNext(ABC_removeB_AC(), 1), ELEMENT_C, Result.MatchingValue));
			printTest("ABC_removeB_AC_iterNext_testIterRemove", testIterRemove(iterAfterNext(ABC_removeB_AC(), 1), Result.NoException));
			printTest("ABC_removeB_AC_iterNextRemove_testIterHasNext", testIterHasNext(iterAfterRemove(iterAfterNext(ABC_removeB_AC(), 1)), Result.True));
			printTest("ABC_removeB_AC_iterNextRemove_testIterNext", testIterNext(iterAfterRemove(iterAfterNext(ABC_removeB_AC(), 1)), ELEMENT_C, Result.MatchingValue));
			printTest("ABC_removeB_AC_iterNextRemove_testIterRemove", testIterRemove(iterAfterRemove(iterAfterNext(ABC_removeB_AC(), 1)), Result.IllegalState));
			printTest("ABC_removeB_AC_iterNextNext_testIterHasNext", testIterHasNext(iterAfterNext(ABC_removeB_AC(), 2), Result.False));
			printTest("ABC_removeB_AC_iterNextNext_testIterNext", testIterNext(iterAfterNext(ABC_removeB_AC(), 2), null, Result.NoSuchElement));
			printTest("ABC_removeB_AC_iterNextNext_testIterRemove", testIterRemove(iterAfterNext(ABC_removeB_AC(), 2), Result.NoException));
			printTest("ABC_removeB_AC_iterNextNextRemove_testIterHasNext", testIterHasNext(iterAfterRemove(iterAfterNext(ABC_removeB_AC(), 2)), Result.False));
			printTest("ABC_removeB_AC_iterNextNextRemove_testIterNext", testIterNext(iterAfterRemove(iterAfterNext(ABC_removeB_AC(), 2)), null, Result.NoSuchElement));
			printTest("ABC_removeB_AC_iterNextNextRemove_testIterRemove", testIterRemove(iterAfterRemove(iterAfterNext(ABC_removeB_AC(), 2)), Result.IllegalState));
			// ListIterator
			printTest("newList_testListIter", testListIter(newList(), Result.NoException));
			printTest("newList_testListIter", testListIter(newList(), 0, Result.NoException));
		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "test_ABC_removeB_AC");
			e.printStackTrace();
		}
	}

	////////////////////////////////////////////////
	// XXX SCENARIO: [A] -> addToRear(B) -> [A,B]
	////////////////////////////////////////////////

	/** Scenario: [A] -> addToRear(B) -> [A,B] 
	 * @return [A,B] after addToRear(B)
	 */
	private IndexedUnsortedList<Integer> A_addToRearB_AB() {
		IndexedUnsortedList<Integer> list = emptyList_addToFrontA_A(); //starting state 
		list.addToRear(ELEMENT_B); //the change
		return list; //return resulting state
	}

	/** Run all tests on scenario: A -> addToRear(B) -> [A,B] */
	private void test_A_addToRearB_AB() {
		System.out.println("\nSCENARIO: [A] -> addToRear(B) -> [A,B]\n");
		try {
			printTest("A_addToRearB_AB_testAddToFrontD", testAddToFront(A_addToRearB_AB(), ELEMENT_D, Result.NoException));
			printTest("A_addToRearB_AB_testAddToRearD", testAddToRear(A_addToRearB_AB(), ELEMENT_D, Result.NoException));
			printTest("A_addToRearB_AB_testAddAfterBD", testAddAfter(A_addToRearB_AB(), ELEMENT_C, ELEMENT_D, Result.NoSuchElement));
			printTest("A_addToRearB_AB_testAddAfterAD", testAddAfter(A_addToRearB_AB(), ELEMENT_A, ELEMENT_D, Result.NoException));
			printTest("A_addToRearB_AB_testAddAfterCD", testAddAfter(A_addToRearB_AB(), ELEMENT_B, ELEMENT_D, Result.NoException));
			printTest("A_addToRearB_AB_testAddAtIndexNeg1D", testAddAtIndex(A_addToRearB_AB(), -1, ELEMENT_D, Result.IndexOutOfBounds));
			printTest("A_addToRearB_AB_testAddAtIndex0D", testAddAtIndex(A_addToRearB_AB(), 0, ELEMENT_D, Result.NoException));
			printTest("A_addToRearB_AB_testAddAtIndex1D", testAddAtIndex(A_addToRearB_AB(), 1, ELEMENT_D, Result.NoException));
			printTest("A_addToRearB_AB_testAddAtIndex2D", testAddAtIndex(A_addToRearB_AB(), 2, ELEMENT_D, Result.NoException));
			printTest("A_addToRearB_AB_testAddAtIndex3D", testAddAtIndex(A_addToRearB_AB(), 3, ELEMENT_D, Result.IndexOutOfBounds));
			printTest("A_addToRearB_AB_testAddD", testAdd(A_addToRearB_AB(), ELEMENT_D, Result.NoException));
			printTest("A_addToRearB_AB_testRemoveFirst", testRemoveFirst(A_addToRearB_AB(), ELEMENT_A, Result.MatchingValue));
			printTest("A_addToRearB_AB_testRemoveLast", testRemoveLast(A_addToRearB_AB(), ELEMENT_B, Result.MatchingValue));
			printTest("A_addToRearB_AB_testRemoveA", testRemoveElement(A_addToRearB_AB(), ELEMENT_A, Result.MatchingValue));
			printTest("A_addToRearB_AB_testRemoveC", testRemoveElement(A_addToRearB_AB(), ELEMENT_C, Result.NoSuchElement));
			printTest("A_addToRearB_AB_testRemoveB", testRemoveElement(A_addToRearB_AB(), ELEMENT_B, Result.MatchingValue));
			printTest("A_addToRearB_AB_testRemoveNeg1", testRemoveIndex(A_addToRearB_AB(), -1, null, Result.IndexOutOfBounds));
			printTest("A_addToRearB_AB_testRemove0", testRemoveIndex(A_addToRearB_AB(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("A_addToRearB_AB_testRemove1", testRemoveIndex(A_addToRearB_AB(), 1, ELEMENT_B, Result.MatchingValue));
			printTest("A_addToRearB_AB_testRemove2", testRemoveIndex(A_addToRearB_AB(), 2, null, Result.IndexOutOfBounds));
			printTest("A_addToRearB_AB_testFirst", testFirst(A_addToRearB_AB(), ELEMENT_A, Result.MatchingValue));
			printTest("A_addToRearB_AB_testLast", testLast(A_addToRearB_AB(), ELEMENT_B, Result.MatchingValue));
			printTest("A_addToRearB_AB_testContainsA", testContains(A_addToRearB_AB(), ELEMENT_A, Result.True));
			printTest("A_addToRearB_AB_testContainsB", testContains(A_addToRearB_AB(), ELEMENT_B, Result.True));
			printTest("A_addToRearB_AB_testContainsC", testContains(A_addToRearB_AB(), ELEMENT_C, Result.False));
			printTest("A_addToRearB_AB_testIsEmpty", testIsEmpty(A_addToRearB_AB(), Result.False));
			printTest("A_addToRearB_AB_testSize", testSize(A_addToRearB_AB(), 2));
			printTest("A_addToRearB_AB_testToString", testToString(A_addToRearB_AB(), Result.ValidString));
			printTest("A_addToRearB_AB_testSetNeg1D", testSet(A_addToRearB_AB(), -1, ELEMENT_D, Result.IndexOutOfBounds));
			printTest("A_addToRearB_AB_testSet0D", testSet(A_addToRearB_AB(), 0, ELEMENT_D, Result.NoException));
			printTest("A_addToRearB_AB_testSet1D", testSet(A_addToRearB_AB(), 1, ELEMENT_D, Result.NoException));
			printTest("A_addToRearB_AB_testSet2D", testSet(A_addToRearB_AB(), 2, ELEMENT_D, Result.IndexOutOfBounds));
			printTest("A_addToRearB_AB_testGetNeg1", testGet(A_addToRearB_AB(), -1, null, Result.IndexOutOfBounds));
			printTest("A_addToRearB_AB_testGet0", testGet(A_addToRearB_AB(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("A_addToRearB_AB_testGet1", testGet(A_addToRearB_AB(), 1, ELEMENT_B, Result.MatchingValue));
			printTest("A_addToRearB_AB_testGet2", testGet(A_addToRearB_AB(), 2, null, Result.IndexOutOfBounds));
			printTest("A_addToRearB_AB_testIndexOfA", testIndexOf(A_addToRearB_AB(), ELEMENT_A, 0));
			printTest("A_addToRearB_AB_testIndexOfB", testIndexOf(A_addToRearB_AB(), ELEMENT_B, 1));
			printTest("A_addToRearB_AB_testIndexOfC", testIndexOf(A_addToRearB_AB(), ELEMENT_C, -1));
			// Iterator
			printTest("A_addToRearB_AB_testIter", testIter(A_addToRearB_AB(), Result.NoException));
			printTest("A_addToRearB_AB_testIterHasNext", testIterHasNext(A_addToRearB_AB().iterator(), Result.True));
			printTest("A_addToRearB_AB_testIterNext", testIterNext(A_addToRearB_AB().iterator(), ELEMENT_A, Result.MatchingValue));
			printTest("A_addToRearB_AB_testIterRemove", testIterRemove(A_addToRearB_AB().iterator(), Result.IllegalState));
			printTest("A_addToRearB_AB_iterNext_testIterHasNext", testIterHasNext(iterAfterNext(A_addToRearB_AB(), 1), Result.True));
			printTest("A_addToRearB_AB_iterNext_testIterNext", testIterNext(iterAfterNext(A_addToRearB_AB(), 1), ELEMENT_B, Result.MatchingValue));
			printTest("A_addToRearB_AB_iterNext_testIterRemove", testIterRemove(iterAfterNext(A_addToRearB_AB(), 1), Result.NoException));
			printTest("A_addToRearB_AB_iterNextRemove_testIterHasNext", testIterHasNext(iterAfterRemove(iterAfterNext(A_addToRearB_AB(), 1)), Result.True));
			printTest("A_addToRearB_AB_iterNextRemove_testIterNext", testIterNext(iterAfterRemove(iterAfterNext(A_addToRearB_AB(), 1)), ELEMENT_B, Result.MatchingValue));
			printTest("A_addToRearB_AB_iterNextRemove_testIterRemove", testIterRemove(iterAfterRemove(iterAfterNext(A_addToRearB_AB(), 1)), Result.IllegalState));
			printTest("A_addToRearB_AB_iterNextNext_testIterHasNext", testIterHasNext(iterAfterNext(A_addToRearB_AB(), 2), Result.False));
			printTest("A_addToRearB_AB_iterNextNext_testIterNext", testIterNext(iterAfterNext(A_addToRearB_AB(), 2), null, Result.NoSuchElement));
			printTest("A_addToRearB_AB_iterNextNext_testIterRemove", testIterRemove(iterAfterNext(A_addToRearB_AB(), 2), Result.NoException));
			printTest("A_addToRearB_AB_iterNextNextRemove_testIterHasNext", testIterHasNext(iterAfterRemove(iterAfterNext(A_addToRearB_AB(), 2)), Result.False));
			printTest("A_addToRearB_AB_iterNextNextRemove_testIterNext", testIterNext(iterAfterRemove(iterAfterNext(A_addToRearB_AB(), 2)), null, Result.NoSuchElement));
			printTest("A_addToRearB_AB_iterNextNextRemove_testIterRemove", testIterRemove(iterAfterRemove(iterAfterNext(A_addToRearB_AB(), 2)), Result.IllegalState));
			// ListIterator
			printTest("newList_testListIter", testListIter(newList(), Result.NoException));
			printTest("newList_testListIter", testListIter(newList(), 0, Result.NoException));

		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "test_A_addToRearB_AB");
			e.printStackTrace();
		}
	}

	////////////////////////////////////////////////
	// XXX SCENARIO: [A] -> addAfter(B,A) -> [A,B]
	////////////////////////////////////////////////

	/** Scenario: [A] -> addAfter(B,A) -> [A,B] 
	 * @return [A,B] after addAfter(B,A)
	 */
	private IndexedUnsortedList<Integer> A_addAfterBA_AB() {
		IndexedUnsortedList<Integer> list = emptyList_addToFrontA_A(); //starting state 
		list.addAfter(ELEMENT_B,ELEMENT_A); //the change
		return list; //return resulting state
	}

	/** Run all tests on scenario: A -> addAfter(B,A) -> [A,B] */
	private void test_A_addAfterBA_AB() {
		System.out.println("\nSCENARIO: [A] -> addAfter(B,A) -> [A,B]\n");
		try {
			printTest("A_addAfterBA_AB_testAddToFrontD", testAddToFront(A_addAfterBA_AB(), ELEMENT_D, Result.NoException));
			printTest("A_addAfterBA_AB_testAddToRearD", testAddToRear(A_addAfterBA_AB(), ELEMENT_D, Result.NoException));
			printTest("A_addAfterBA_AB_testAddAfterBD", testAddAfter(A_addAfterBA_AB(), ELEMENT_C, ELEMENT_D, Result.NoSuchElement));
			printTest("A_addAfterBA_AB_testAddAfterAD", testAddAfter(A_addAfterBA_AB(), ELEMENT_A, ELEMENT_D, Result.NoException));
			printTest("A_addAfterBA_AB_testAddAfterCD", testAddAfter(A_addAfterBA_AB(), ELEMENT_B, ELEMENT_D, Result.NoException));
			printTest("A_addAfterBA_AB_testAddAtIndexNeg1D", testAddAtIndex(A_addAfterBA_AB(), -1, ELEMENT_D, Result.IndexOutOfBounds));
			printTest("A_addAfterBA_AB_testAddAtIndex0D", testAddAtIndex(A_addAfterBA_AB(), 0, ELEMENT_D, Result.NoException));
			printTest("A_addAfterBA_AB_testAddAtIndex1D", testAddAtIndex(A_addAfterBA_AB(), 1, ELEMENT_D, Result.NoException));
			printTest("A_addAfterBA_AB_testAddAtIndex2D", testAddAtIndex(A_addAfterBA_AB(), 2, ELEMENT_D, Result.NoException));
			printTest("A_addAfterBA_AB_testAddAtIndex3D", testAddAtIndex(A_addAfterBA_AB(), 3, ELEMENT_D, Result.IndexOutOfBounds));
			printTest("A_addAfterBA_AB_testAddD", testAdd(A_addAfterBA_AB(), ELEMENT_D, Result.NoException));
			printTest("A_addAfterBA_AB_testRemoveFirst", testRemoveFirst(A_addAfterBA_AB(), ELEMENT_A, Result.MatchingValue));
			printTest("A_addAfterBA_AB_testRemoveLast", testRemoveLast(A_addAfterBA_AB(), ELEMENT_B, Result.MatchingValue));
			printTest("A_addAfterBA_AB_testRemoveA", testRemoveElement(A_addAfterBA_AB(), ELEMENT_A, Result.MatchingValue));
			printTest("A_addAfterBA_AB_testRemoveC", testRemoveElement(A_addAfterBA_AB(), ELEMENT_C, Result.NoSuchElement));
			printTest("A_addAfterBA_AB_testRemoveB", testRemoveElement(A_addAfterBA_AB(), ELEMENT_B, Result.MatchingValue));
			printTest("A_addAfterBA_AB_testRemoveNeg1", testRemoveIndex(A_addAfterBA_AB(), -1, null, Result.IndexOutOfBounds));
			printTest("A_addAfterBA_AB_testRemove0", testRemoveIndex(A_addAfterBA_AB(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("A_addAfterBA_AB_testRemove1", testRemoveIndex(A_addAfterBA_AB(), 1, ELEMENT_B, Result.MatchingValue));
			printTest("A_addAfterBA_AB_testRemove2", testRemoveIndex(A_addAfterBA_AB(), 2, null, Result.IndexOutOfBounds));
			printTest("A_addAfterBA_AB_testFirst", testFirst(A_addAfterBA_AB(), ELEMENT_A, Result.MatchingValue));
			printTest("A_addAfterBA_AB_testLast", testLast(A_addAfterBA_AB(), ELEMENT_B, Result.MatchingValue));
			printTest("A_addAfterBA_AB_testContainsA", testContains(A_addAfterBA_AB(), ELEMENT_A, Result.True));
			printTest("A_addAfterBA_AB_testContainsB", testContains(A_addAfterBA_AB(), ELEMENT_B, Result.True));
			printTest("A_addAfterBA_AB_testContainsC", testContains(A_addAfterBA_AB(), ELEMENT_C, Result.False));
			printTest("A_addAfterBA_AB_testIsEmpty", testIsEmpty(A_addAfterBA_AB(), Result.False));
			printTest("A_addAfterBA_AB_testSize", testSize(A_addAfterBA_AB(), 2));
			printTest("A_addAfterBA_AB_testToString", testToString(A_addAfterBA_AB(), Result.ValidString));
			printTest("A_addAfterBA_AB_testSetNeg1D", testSet(A_addAfterBA_AB(), -1, ELEMENT_D, Result.IndexOutOfBounds));
			printTest("A_addAfterBA_AB_testSet0D", testSet(A_addAfterBA_AB(), 0, ELEMENT_D, Result.NoException));
			printTest("A_addAfterBA_AB_testSet1D", testSet(A_addAfterBA_AB(), 1, ELEMENT_D, Result.NoException));
			printTest("A_addAfterBA_AB_testSet2D", testSet(A_addAfterBA_AB(), 2, ELEMENT_D, Result.IndexOutOfBounds));
			printTest("A_addAfterBA_AB_testGetNeg1", testGet(A_addAfterBA_AB(), -1, null, Result.IndexOutOfBounds));
			printTest("A_addAfterBA_AB_testGet0", testGet(A_addAfterBA_AB(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("A_addAfterBA_AB_testGet1", testGet(A_addAfterBA_AB(), 1, ELEMENT_B, Result.MatchingValue));
			printTest("A_addAfterBA_AB_testGet2", testGet(A_addAfterBA_AB(), 2, null, Result.IndexOutOfBounds));
			printTest("A_addAfterBA_AB_testIndexOfA", testIndexOf(A_addAfterBA_AB(), ELEMENT_A, 0));
			printTest("A_addAfterBA_AB_testIndexOfB", testIndexOf(A_addAfterBA_AB(), ELEMENT_B, 1));
			printTest("A_addAfterBA_AB_testIndexOfC", testIndexOf(A_addAfterBA_AB(), ELEMENT_C, -1));
			// Iterator
			printTest("A_addAfterBA_AB_testIter", testIter(A_addAfterBA_AB(), Result.NoException));
			printTest("A_addAfterBA_AB_testIterHasNext", testIterHasNext(A_addAfterBA_AB().iterator(), Result.True));
			printTest("A_addAfterBA_AB_testIterNext", testIterNext(A_addAfterBA_AB().iterator(), ELEMENT_A, Result.MatchingValue));
			printTest("A_addAfterBA_AB_testIterRemove", testIterRemove(A_addAfterBA_AB().iterator(), Result.IllegalState));
			printTest("A_addAfterBA_AB_iterNext_testIterHasNext", testIterHasNext(iterAfterNext(A_addAfterBA_AB(), 1), Result.True));
			printTest("A_addAfterBA_AB_iterNext_testIterNext", testIterNext(iterAfterNext(A_addAfterBA_AB(), 1), ELEMENT_B, Result.MatchingValue));
			printTest("A_addAfterBA_AB_iterNext_testIterRemove", testIterRemove(iterAfterNext(A_addAfterBA_AB(), 1), Result.NoException));
			printTest("A_addAfterBA_AB_iterNextRemove_testIterHasNext", testIterHasNext(iterAfterRemove(iterAfterNext(A_addAfterBA_AB(), 1)), Result.True));
			printTest("A_addAfterBA_AB_iterNextRemove_testIterNext", testIterNext(iterAfterRemove(iterAfterNext(A_addAfterBA_AB(), 1)), ELEMENT_B, Result.MatchingValue));
			printTest("A_addAfterBA_AB_iterNextRemove_testIterRemove", testIterRemove(iterAfterRemove(iterAfterNext(A_addAfterBA_AB(), 1)), Result.IllegalState));
			printTest("A_addAfterBA_AB_iterNextNext_testIterHasNext", testIterHasNext(iterAfterNext(A_addAfterBA_AB(), 2), Result.False));
			printTest("A_addAfterBA_AB_iterNextNext_testIterNext", testIterNext(iterAfterNext(A_addAfterBA_AB(), 2), null, Result.NoSuchElement));
			printTest("A_addAfterBA_AB_iterNextNext_testIterRemove", testIterRemove(iterAfterNext(A_addAfterBA_AB(), 2), Result.NoException));
			printTest("A_addAfterBA_AB_iterNextNextRemove_testIterHasNext", testIterHasNext(iterAfterRemove(iterAfterNext(A_addAfterBA_AB(), 2)), Result.False));
			printTest("A_addAfterBA_AB_iterNextNextRemove_testIterNext", testIterNext(iterAfterRemove(iterAfterNext(A_addAfterBA_AB(), 2)), null, Result.NoSuchElement));
			printTest("A_addAfterBA_AB_iterNextNextRemove_testIterRemove", testIterRemove(iterAfterRemove(iterAfterNext(A_addAfterBA_AB(), 2)), Result.IllegalState));
			// ListIterator
			printTest("newList_testListIter", testListIter(newList(), Result.NoException));
			printTest("newList_testListIter", testListIter(newList(), 0, Result.NoException));

		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "test_A_addAfterBA_AB");
			e.printStackTrace();
		}
	}

	////////////////////////////////////////////////
	// XXX SCENARIO: [A] -> add(B) -> [A,B]
	////////////////////////////////////////////////

	/** Scenario: [A] -> add(B) -> [A,B] 
	 * @return [A,B] after add(B)
	 */
	private IndexedUnsortedList<Integer> A_addB_AB() {
		IndexedUnsortedList<Integer> list = emptyList_addToFrontA_A(); //starting state 
		list.add(ELEMENT_B); //the change
		return list; //return resulting state
	}

	/** Run all tests on scenario: A -> add(B) -> [A,B] */
	private void test_A_addB_AB() {
		System.out.println("\nSCENARIO: [A] -> add(B) -> [A,B]\n");
		try {
			printTest("A_addB_AB_testAddToFrontD", testAddToFront(A_addB_AB(), ELEMENT_D, Result.NoException));
			printTest("A_addB_AB_testAddToRearD", testAddToRear(A_addB_AB(), ELEMENT_D, Result.NoException));
			printTest("A_addB_AB_testAddAfterBD", testAddAfter(A_addB_AB(), ELEMENT_C, ELEMENT_D, Result.NoSuchElement));
			printTest("A_addB_AB_testAddAfterAD", testAddAfter(A_addB_AB(), ELEMENT_A, ELEMENT_D, Result.NoException));
			printTest("A_addB_AB_testAddAfterCD", testAddAfter(A_addB_AB(), ELEMENT_B, ELEMENT_D, Result.NoException));
			printTest("A_addB_AB_testAddAtIndexNeg1D", testAddAtIndex(A_addB_AB(), -1, ELEMENT_D, Result.IndexOutOfBounds));
			printTest("A_addB_AB_testAddAtIndex0D", testAddAtIndex(A_addB_AB(), 0, ELEMENT_D, Result.NoException));
			printTest("A_addB_AB_testAddAtIndex1D", testAddAtIndex(A_addB_AB(), 1, ELEMENT_D, Result.NoException));
			printTest("A_addB_AB_testAddAtIndex2D", testAddAtIndex(A_addB_AB(), 2, ELEMENT_D, Result.NoException));
			printTest("A_addB_AB_testAddAtIndex3D", testAddAtIndex(A_addB_AB(), 3, ELEMENT_D, Result.IndexOutOfBounds));
			printTest("A_addB_AB_testAddD", testAdd(A_addB_AB(), ELEMENT_D, Result.NoException));
			printTest("A_addB_AB_testRemoveFirst", testRemoveFirst(A_addB_AB(), ELEMENT_A, Result.MatchingValue));
			printTest("A_addB_AB_testRemoveLast", testRemoveLast(A_addB_AB(), ELEMENT_B, Result.MatchingValue));
			printTest("A_addB_AB_testRemoveA", testRemoveElement(A_addB_AB(), ELEMENT_A, Result.MatchingValue));
			printTest("A_addB_AB_testRemoveC", testRemoveElement(A_addB_AB(), ELEMENT_C, Result.NoSuchElement));
			printTest("A_addB_AB_testRemoveB", testRemoveElement(A_addB_AB(), ELEMENT_B, Result.MatchingValue));
			printTest("A_addB_AB_testRemoveNeg1", testRemoveIndex(A_addB_AB(), -1, null, Result.IndexOutOfBounds));
			printTest("A_addB_AB_testRemove0", testRemoveIndex(A_addB_AB(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("A_addB_AB_testRemove1", testRemoveIndex(A_addB_AB(), 1, ELEMENT_B, Result.MatchingValue));
			printTest("A_addB_AB_testRemove2", testRemoveIndex(A_addB_AB(), 2, null, Result.IndexOutOfBounds));
			printTest("A_addB_AB_testFirst", testFirst(A_addB_AB(), ELEMENT_A, Result.MatchingValue));
			printTest("A_addB_AB_testLast", testLast(A_addB_AB(), ELEMENT_B, Result.MatchingValue));
			printTest("A_addB_AB_testContainsA", testContains(A_addB_AB(), ELEMENT_A, Result.True));
			printTest("A_addB_AB_testContainsB", testContains(A_addB_AB(), ELEMENT_B, Result.True));
			printTest("A_addB_AB_testContainsC", testContains(A_addB_AB(), ELEMENT_C, Result.False));
			printTest("A_addB_AB_testIsEmpty", testIsEmpty(A_addB_AB(), Result.False));
			printTest("A_addB_AB_testSize", testSize(A_addB_AB(), 2));
			printTest("A_addB_AB_testToString", testToString(A_addB_AB(), Result.ValidString));
			printTest("A_addB_AB_testSetNeg1D", testSet(A_addB_AB(), -1, ELEMENT_D, Result.IndexOutOfBounds));
			printTest("A_addB_AB_testSet0D", testSet(A_addB_AB(), 0, ELEMENT_D, Result.NoException));
			printTest("A_addB_AB_testSet1D", testSet(A_addB_AB(), 1, ELEMENT_D, Result.NoException));
			printTest("A_addB_AB_testSet2D", testSet(A_addB_AB(), 2, ELEMENT_D, Result.IndexOutOfBounds));
			printTest("A_addB_AB_testGetNeg1", testGet(A_addB_AB(), -1, null, Result.IndexOutOfBounds));
			printTest("A_addB_AB_testGet0", testGet(A_addB_AB(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("A_addB_AB_testGet1", testGet(A_addB_AB(), 1, ELEMENT_B, Result.MatchingValue));
			printTest("A_addB_AB_testGet2", testGet(A_addB_AB(), 2, null, Result.IndexOutOfBounds));
			printTest("A_addB_AB_testIndexOfA", testIndexOf(A_addB_AB(), ELEMENT_A, 0));
			printTest("A_addB_AB_testIndexOfB", testIndexOf(A_addB_AB(), ELEMENT_B, 1));
			printTest("A_addB_AB_testIndexOfC", testIndexOf(A_addB_AB(), ELEMENT_C, -1));
			// Iterator
			printTest("A_addB_AB_testIter", testIter(A_addB_AB(), Result.NoException));
			printTest("A_addB_AB_testIterHasNext", testIterHasNext(A_addB_AB().iterator(), Result.True));
			printTest("A_addB_AB_testIterNext", testIterNext(A_addB_AB().iterator(), ELEMENT_A, Result.MatchingValue));
			printTest("A_addB_AB_testIterRemove", testIterRemove(A_addB_AB().iterator(), Result.IllegalState));
			printTest("A_addB_AB_iterNext_testIterHasNext", testIterHasNext(iterAfterNext(A_addB_AB(), 1), Result.True));
			printTest("A_addB_AB_iterNext_testIterNext", testIterNext(iterAfterNext(A_addB_AB(), 1), ELEMENT_B, Result.MatchingValue));
			printTest("A_addB_AB_iterNext_testIterRemove", testIterRemove(iterAfterNext(A_addB_AB(), 1), Result.NoException));
			printTest("A_addB_AB_iterNextRemove_testIterHasNext", testIterHasNext(iterAfterRemove(iterAfterNext(A_addB_AB(), 1)), Result.True));
			printTest("A_addB_AB_iterNextRemove_testIterNext", testIterNext(iterAfterRemove(iterAfterNext(A_addB_AB(), 1)), ELEMENT_B, Result.MatchingValue));
			printTest("A_addB_AB_iterNextRemove_testIterRemove", testIterRemove(iterAfterRemove(iterAfterNext(A_addB_AB(), 1)), Result.IllegalState));
			printTest("A_addB_AB_iterNextNext_testIterHasNext", testIterHasNext(iterAfterNext(A_addB_AB(), 2), Result.False));
			printTest("A_addB_AB_iterNextNext_testIterNext", testIterNext(iterAfterNext(A_addB_AB(), 2), null, Result.NoSuchElement));
			printTest("A_addB_AB_iterNextNext_testIterRemove", testIterRemove(iterAfterNext(A_addB_AB(), 2), Result.NoException));
			printTest("A_addB_AB_iterNextNextRemove_testIterHasNext", testIterHasNext(iterAfterRemove(iterAfterNext(A_addB_AB(), 2)), Result.False));
			printTest("A_addB_AB_iterNextNextRemove_testIterNext", testIterNext(iterAfterRemove(iterAfterNext(A_addB_AB(), 2)), null, Result.NoSuchElement));
			printTest("A_addB_AB_iterNextNextRemove_testIterRemove", testIterRemove(iterAfterRemove(iterAfterNext(A_addB_AB(), 2)), Result.IllegalState));
			// ListIterator
			printTest("newList_testListIter", testListIter(newList(), Result.NoException));
			printTest("newList_testListIter", testListIter(newList(), 0, Result.NoException));

		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "test_A_addB_AB");
			e.printStackTrace();
		}
	}
	
	////////////////////////////////////////////////
	// XXX SCENARIO: [A] -> add(1,B) -> [A,B]
	////////////////////////////////////////////////

	/** Scenario: [A] -> add(1,B) -> [A,B] 
	 * @return [A,B] after add(1,B)
	 */
	private IndexedUnsortedList<Integer> A_add1B_AB() {
		IndexedUnsortedList<Integer> list = emptyList_addToFrontA_A(); //starting state 
		list.add(1,ELEMENT_B); //the change
		return list; //return resulting state
	}

	/** Run all tests on scenario: A -> add(1,B) -> [A,B] */
	private void test_A_add1B_AB() {
		System.out.println("\nSCENARIO: [A] -> add(1,B) -> [A,B]\n");
		try {
			printTest("A_add1B_AB_testAddToFrontD", testAddToFront(A_add1B_AB(), ELEMENT_D, Result.NoException));
			printTest("A_add1B_AB_testAddToRearD", testAddToRear(A_add1B_AB(), ELEMENT_D, Result.NoException));
			printTest("A_add1B_AB_testAddAfterBD", testAddAfter(A_add1B_AB(), ELEMENT_C, ELEMENT_D, Result.NoSuchElement));
			printTest("A_add1B_AB_testAddAfterAD", testAddAfter(A_add1B_AB(), ELEMENT_A, ELEMENT_D, Result.NoException));
			printTest("A_add1B_AB_testAddAfterCD", testAddAfter(A_add1B_AB(), ELEMENT_B, ELEMENT_D, Result.NoException));
			printTest("A_add1B_AB_testAddAtIndexNeg1D", testAddAtIndex(A_add1B_AB(), -1, ELEMENT_D, Result.IndexOutOfBounds));
			printTest("A_add1B_AB_testAddAtIndex0D", testAddAtIndex(A_add1B_AB(), 0, ELEMENT_D, Result.NoException));
			printTest("A_add1B_AB_testAddAtIndex1D", testAddAtIndex(A_add1B_AB(), 1, ELEMENT_D, Result.NoException));
			printTest("A_add1B_AB_testAddAtIndex2D", testAddAtIndex(A_add1B_AB(), 2, ELEMENT_D, Result.NoException));
			printTest("A_add1B_AB_testAddAtIndex3D", testAddAtIndex(A_add1B_AB(), 3, ELEMENT_D, Result.IndexOutOfBounds));
			printTest("A_add1B_AB_testAddD", testAdd(A_add1B_AB(), ELEMENT_D, Result.NoException));
			printTest("A_add1B_AB_testRemoveFirst", testRemoveFirst(A_add1B_AB(), ELEMENT_A, Result.MatchingValue));
			printTest("A_add1B_AB_testRemoveLast", testRemoveLast(A_add1B_AB(), ELEMENT_B, Result.MatchingValue));
			printTest("A_add1B_AB_testRemoveA", testRemoveElement(A_add1B_AB(), ELEMENT_A, Result.MatchingValue));
			printTest("A_add1B_AB_testRemoveC", testRemoveElement(A_add1B_AB(), ELEMENT_C, Result.NoSuchElement));
			printTest("A_add1B_AB_testRemoveB", testRemoveElement(A_add1B_AB(), ELEMENT_B, Result.MatchingValue));
			printTest("A_add1B_AB_testRemoveNeg1", testRemoveIndex(A_add1B_AB(), -1, null, Result.IndexOutOfBounds));
			printTest("A_add1B_AB_testRemove0", testRemoveIndex(A_add1B_AB(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("A_add1B_AB_testRemove1", testRemoveIndex(A_add1B_AB(), 1, ELEMENT_B, Result.MatchingValue));
			printTest("A_add1B_AB_testRemove2", testRemoveIndex(A_add1B_AB(), 2, null, Result.IndexOutOfBounds));
			printTest("A_add1B_AB_testFirst", testFirst(A_add1B_AB(), ELEMENT_A, Result.MatchingValue));
			printTest("A_add1B_AB_testLast", testLast(A_add1B_AB(), ELEMENT_B, Result.MatchingValue));
			printTest("A_add1B_AB_testContainsA", testContains(A_add1B_AB(), ELEMENT_A, Result.True));
			printTest("A_add1B_AB_testContainsB", testContains(A_add1B_AB(), ELEMENT_B, Result.True));
			printTest("A_add1B_AB_testContainsC", testContains(A_add1B_AB(), ELEMENT_C, Result.False));
			printTest("A_add1B_AB_testIsEmpty", testIsEmpty(A_add1B_AB(), Result.False));
			printTest("A_add1B_AB_testSize", testSize(A_add1B_AB(), 2));
			printTest("A_add1B_AB_testToString", testToString(A_add1B_AB(), Result.ValidString));
			printTest("A_add1B_AB_testSetNeg1D", testSet(A_add1B_AB(), -1, ELEMENT_D, Result.IndexOutOfBounds));
			printTest("A_add1B_AB_testSet0D", testSet(A_add1B_AB(), 0, ELEMENT_D, Result.NoException));
			printTest("A_add1B_AB_testSet1D", testSet(A_add1B_AB(), 1, ELEMENT_D, Result.NoException));
			printTest("A_add1B_AB_testSet2D", testSet(A_add1B_AB(), 2, ELEMENT_D, Result.IndexOutOfBounds));
			printTest("A_add1B_AB_testGetNeg1", testGet(A_add1B_AB(), -1, null, Result.IndexOutOfBounds));
			printTest("A_add1B_AB_testGet0", testGet(A_add1B_AB(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("A_add1B_AB_testGet1", testGet(A_add1B_AB(), 1, ELEMENT_B, Result.MatchingValue));
			printTest("A_add1B_AB_testGet2", testGet(A_add1B_AB(), 2, null, Result.IndexOutOfBounds));
			printTest("A_add1B_AB_testIndexOfA", testIndexOf(A_add1B_AB(), ELEMENT_A, 0));
			printTest("A_add1B_AB_testIndexOfB", testIndexOf(A_add1B_AB(), ELEMENT_B, 1));
			printTest("A_add1B_AB_testIndexOfC", testIndexOf(A_add1B_AB(), ELEMENT_C, -1));
			// Iterator
			printTest("A_add1B_AB_testIter", testIter(A_add1B_AB(), Result.NoException));
			printTest("A_add1B_AB_testIterHasNext", testIterHasNext(A_add1B_AB().iterator(), Result.True));
			printTest("A_add1B_AB_testIterNext", testIterNext(A_add1B_AB().iterator(), ELEMENT_A, Result.MatchingValue));
			printTest("A_add1B_AB_testIterRemove", testIterRemove(A_add1B_AB().iterator(), Result.IllegalState));
			printTest("A_add1B_AB_iterNext_testIterHasNext", testIterHasNext(iterAfterNext(A_add1B_AB(), 1), Result.True));
			printTest("A_add1B_AB_iterNext_testIterNext", testIterNext(iterAfterNext(A_add1B_AB(), 1), ELEMENT_B, Result.MatchingValue));
			printTest("A_add1B_AB_iterNext_testIterRemove", testIterRemove(iterAfterNext(A_add1B_AB(), 1), Result.NoException));
			printTest("A_add1B_AB_iterNextRemove_testIterHasNext", testIterHasNext(iterAfterRemove(iterAfterNext(A_add1B_AB(), 1)), Result.True));
			printTest("A_add1B_AB_iterNextRemove_testIterNext", testIterNext(iterAfterRemove(iterAfterNext(A_add1B_AB(), 1)), ELEMENT_B, Result.MatchingValue));
			printTest("A_add1B_AB_iterNextRemove_testIterRemove", testIterRemove(iterAfterRemove(iterAfterNext(A_add1B_AB(), 1)), Result.IllegalState));
			printTest("A_add1B_AB_iterNextNext_testIterHasNext", testIterHasNext(iterAfterNext(A_add1B_AB(), 2), Result.False));
			printTest("A_add1B_AB_iterNextNext_testIterNext", testIterNext(iterAfterNext(A_add1B_AB(), 2), null, Result.NoSuchElement));
			printTest("A_add1B_AB_iterNextNext_testIterRemove", testIterRemove(iterAfterNext(A_add1B_AB(), 2), Result.NoException));
			printTest("A_add1B_AB_iterNextNextRemove_testIterHasNext", testIterHasNext(iterAfterRemove(iterAfterNext(A_add1B_AB(), 2)), Result.False));
			printTest("A_add1B_AB_iterNextNextRemove_testIterNext", testIterNext(iterAfterRemove(iterAfterNext(A_add1B_AB(), 2)), null, Result.NoSuchElement));
			printTest("A_add1B_AB_iterNextNextRemove_testIterRemove", testIterRemove(iterAfterRemove(iterAfterNext(A_add1B_AB(), 2)), Result.IllegalState));
			// ListIterator
			printTest("newList_testListIter", testListIter(newList(), Result.NoException));
			printTest("newList_testListIter", testListIter(newList(), 0, Result.NoException));

		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "test_A_add1B_AB");
			e.printStackTrace();
		}
	}
	////////////////////////////////////////////////
	// XXX SCENARIO: [A,B,C] -> removeLast -> [A,B]
	////////////////////////////////////////////////

	/** Scenario: [A,B,C] -> removeLast -> [A,B] 
	 * @return [A,B] after removeLast
	 */
	private IndexedUnsortedList<Integer> ABC_removeLast_AB() {
		IndexedUnsortedList<Integer> list = newList();
		list.add(ELEMENT_A);
		list.add(ELEMENT_B);
		list.add(ELEMENT_C);
		list.removeLast();
		return list;
	}

	/** Run all tests on scenario: [A,B,C] -> removeLast -> [A,B] */
	private void test_ABC_removeLast_AB() {
		System.out.println("\nSCENARIO: [A,B,C] -> removeLast -> [A,B]\n");
		try {
			printTest("ABC_removeLast_AB_testAddToFrontD", testAddToFront(ABC_removeLast_AB(), ELEMENT_D, Result.NoException));
			printTest("ABC_removeLast_AB_testAddToRearD", testAddToRear(ABC_removeLast_AB(), ELEMENT_D, Result.NoException));
			printTest("ABC_removeLast_AB_testAddAfterBD", testAddAfter(ABC_removeLast_AB(), ELEMENT_C, ELEMENT_D, Result.NoSuchElement));
			printTest("ABC_removeLast_AB_testAddAfterAD", testAddAfter(ABC_removeLast_AB(), ELEMENT_A, ELEMENT_D, Result.NoException));
			printTest("ABC_removeLast_AB_testAddAfterCD", testAddAfter(ABC_removeLast_AB(), ELEMENT_B, ELEMENT_D, Result.NoException));
			printTest("ABC_removeLast_AB_testAddAtIndexNeg1D", testAddAtIndex(ABC_removeLast_AB(), -1, ELEMENT_D, Result.IndexOutOfBounds));
			printTest("ABC_removeLast_AB_testAddAtIndex0D", testAddAtIndex(ABC_removeLast_AB(), 0, ELEMENT_D, Result.NoException));
			printTest("ABC_removeLast_AB_testAddAtIndex1D", testAddAtIndex(ABC_removeLast_AB(), 1, ELEMENT_D, Result.NoException));
			printTest("ABC_removeLast_AB_testAddAtIndex2D", testAddAtIndex(ABC_removeLast_AB(), 2, ELEMENT_D, Result.NoException));
			printTest("ABC_removeLast_AB_testAddAtIndex3D", testAddAtIndex(ABC_removeLast_AB(), 3, ELEMENT_D, Result.IndexOutOfBounds));
			printTest("ABC_removeLast_AB_testAddD", testAdd(ABC_removeLast_AB(), ELEMENT_D, Result.NoException));
			printTest("ABC_removeLast_AB_testRemoveFirst", testRemoveFirst(ABC_removeLast_AB(), ELEMENT_A, Result.MatchingValue));
			printTest("ABC_removeLast_AB_testRemoveLast", testRemoveLast(ABC_removeLast_AB(), ELEMENT_B, Result.MatchingValue));
			printTest("ABC_removeLast_AB_testRemoveA", testRemoveElement(ABC_removeLast_AB(), ELEMENT_A, Result.MatchingValue));
			printTest("ABC_removeLast_AB_testRemoveC", testRemoveElement(ABC_removeLast_AB(), ELEMENT_C, Result.NoSuchElement));
			printTest("ABC_removeLast_AB_testRemoveB", testRemoveElement(ABC_removeLast_AB(), ELEMENT_B, Result.MatchingValue));
			printTest("ABC_removeLast_AB_testRemoveNeg1", testRemoveIndex(ABC_removeLast_AB(), -1, null, Result.IndexOutOfBounds));
			printTest("ABC_removeLast_AB_testRemove0", testRemoveIndex(ABC_removeLast_AB(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("ABC_removeLast_AB_testRemove1", testRemoveIndex(ABC_removeLast_AB(), 1, ELEMENT_B, Result.MatchingValue));
			printTest("ABC_removeLast_AB_testRemove2", testRemoveIndex(ABC_removeLast_AB(), 2, null, Result.IndexOutOfBounds));
			printTest("ABC_removeLast_AB_testFirst", testFirst(ABC_removeLast_AB(), ELEMENT_A, Result.MatchingValue));
			printTest("ABC_removeLast_AB_testLast", testLast(ABC_removeLast_AB(), ELEMENT_B, Result.MatchingValue));
			printTest("ABC_removeLast_AB_testContainsA", testContains(ABC_removeLast_AB(), ELEMENT_A, Result.True));
			printTest("ABC_removeLast_AB_testContainsB", testContains(ABC_removeLast_AB(), ELEMENT_B, Result.True));
			printTest("ABC_removeLast_AB_testContainsC", testContains(ABC_removeLast_AB(), ELEMENT_C, Result.False));
			printTest("ABC_removeLast_AB_testIsEmpty", testIsEmpty(ABC_removeLast_AB(), Result.False));
			printTest("ABC_removeLast_AB_testSize", testSize(ABC_removeLast_AB(), 2));
			printTest("ABC_removeLast_AB_testToString", testToString(ABC_removeLast_AB(), Result.ValidString));
			printTest("ABC_removeLast_AB_testSetNeg1D", testSet(ABC_removeLast_AB(), -1, ELEMENT_D, Result.IndexOutOfBounds));
			printTest("ABC_removeLast_AB_testSet0D", testSet(ABC_removeLast_AB(), 0, ELEMENT_D, Result.NoException));
			printTest("ABC_removeLast_AB_testSet1D", testSet(ABC_removeLast_AB(), 1, ELEMENT_D, Result.NoException));
			printTest("ABC_removeLast_AB_testSet2D", testSet(ABC_removeLast_AB(), 2, ELEMENT_D, Result.IndexOutOfBounds));
			printTest("ABC_removeLast_AB_testGetNeg1", testGet(ABC_removeLast_AB(), -1, null, Result.IndexOutOfBounds));
			printTest("ABC_removeLast_AB_testGet0", testGet(ABC_removeLast_AB(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("ABC_removeLast_AB_testGet1", testGet(ABC_removeLast_AB(), 1, ELEMENT_B, Result.MatchingValue));
			printTest("ABC_removeLast_AB_testGet2", testGet(ABC_removeLast_AB(), 2, null, Result.IndexOutOfBounds));
			printTest("ABC_removeLast_AB_testIndexOfA", testIndexOf(ABC_removeLast_AB(), ELEMENT_A, 0));
			printTest("ABC_removeLast_AB_testIndexOfB", testIndexOf(ABC_removeLast_AB(), ELEMENT_B, 1));
			printTest("ABC_removeLast_AB_testIndexOfC", testIndexOf(ABC_removeLast_AB(), ELEMENT_C, -1));
			// Iterator
			printTest("ABC_removeLast_AB_testIter", testIter(ABC_removeLast_AB(), Result.NoException));
			printTest("ABC_removeLast_AB_testIterHasNext", testIterHasNext(ABC_removeLast_AB().iterator(), Result.True));
			printTest("ABC_removeLast_AB_testIterNext", testIterNext(ABC_removeLast_AB().iterator(), ELEMENT_A, Result.MatchingValue));
			printTest("ABC_removeLast_AB_testIterRemove", testIterRemove(ABC_removeLast_AB().iterator(), Result.IllegalState));
			printTest("ABC_removeLast_AB_iterNext_testIterHasNext", testIterHasNext(iterAfterNext(ABC_removeLast_AB(), 1), Result.True));
			printTest("ABC_removeLast_AB_iterNext_testIterNext", testIterNext(iterAfterNext(ABC_removeLast_AB(), 1), ELEMENT_B, Result.MatchingValue));
			printTest("ABC_removeLast_AB_iterNext_testIterRemove", testIterRemove(iterAfterNext(ABC_removeLast_AB(), 1), Result.NoException));
			printTest("ABC_removeLast_AB_iterNextRemove_testIterHasNext", testIterHasNext(iterAfterRemove(iterAfterNext(ABC_removeLast_AB(), 1)), Result.True));
			printTest("ABC_removeLast_AB_iterNextRemove_testIterNext", testIterNext(iterAfterRemove(iterAfterNext(ABC_removeLast_AB(), 1)), ELEMENT_B, Result.MatchingValue));
			printTest("ABC_removeLast_AB_iterNextRemove_testIterRemove", testIterRemove(iterAfterRemove(iterAfterNext(ABC_removeLast_AB(), 1)), Result.IllegalState));
			printTest("ABC_removeLast_AB_iterNextNext_testIterHasNext", testIterHasNext(iterAfterNext(ABC_removeLast_AB(), 2), Result.False));
			printTest("ABC_removeLast_AB_iterNextNext_testIterNext", testIterNext(iterAfterNext(ABC_removeLast_AB(), 2), null, Result.NoSuchElement));
			printTest("ABC_removeLast_AB_iterNextNext_testIterRemove", testIterRemove(iterAfterNext(ABC_removeLast_AB(), 2), Result.NoException));
			printTest("ABC_removeLast_AB_iterNextNextRemove_testIterHasNext", testIterHasNext(iterAfterRemove(iterAfterNext(ABC_removeLast_AB(), 2)), Result.False));
			printTest("ABC_removeLast_AB_iterNextNextRemove_testIterNext", testIterNext(iterAfterRemove(iterAfterNext(ABC_removeLast_AB(), 2)), null, Result.NoSuchElement));
			printTest("ABC_removeLast_AB_iterNextNextRemove_testIterRemove", testIterRemove(iterAfterRemove(iterAfterNext(ABC_removeLast_AB(), 2)), Result.IllegalState));
			// ListIterator
			printTest("newList_testListIter", testListIter(newList(), Result.NoException));
			printTest("newList_testListIter", testListIter(newList(), 0, Result.NoException));

		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "test_ABC_removeLast_AB");
			e.printStackTrace();
		}
	}

	////////////////////////////////////////////////
	// XXX SCENARIO: [A,B,C] -> remove(C) -> [A,B]
	////////////////////////////////////////////////

	/** Scenario: [A,B,C] -> remove(C) -> [A,B] 
	 * @return [A,B] after remove(C)
	 */
	private IndexedUnsortedList<Integer> ABC_removeC_AB() {
		IndexedUnsortedList<Integer> list = newList();
		list.add(ELEMENT_A);
		list.add(ELEMENT_B);
		list.add(ELEMENT_C);
		list.remove(ELEMENT_C);
		return list;
	}

	/** Run all tests on scenario: [A,B,C] -> remove(C) -> [A,B] */
	private void test_ABC_removeC_AB() {
		System.out.println("\nSCENARIO: [A,B,C] -> remove(C) -> [A,B]\n");
		try {
			printTest("ABC_removeC_AB_testAddToFrontD", testAddToFront(ABC_removeC_AB(), ELEMENT_D, Result.NoException));
			printTest("ABC_removeC_AB_testAddToRearD", testAddToRear(ABC_removeC_AB(), ELEMENT_D, Result.NoException));
			printTest("ABC_removeC_AB_testAddAfterBD", testAddAfter(ABC_removeC_AB(), ELEMENT_C, ELEMENT_D, Result.NoSuchElement));
			printTest("ABC_removeC_AB_testAddAfterAD", testAddAfter(ABC_removeC_AB(), ELEMENT_A, ELEMENT_D, Result.NoException));
			printTest("ABC_removeC_AB_testAddAfterCD", testAddAfter(ABC_removeC_AB(), ELEMENT_B, ELEMENT_D, Result.NoException));
			printTest("ABC_removeC_AB_testAddAtIndexNeg1D", testAddAtIndex(ABC_removeC_AB(), -1, ELEMENT_D, Result.IndexOutOfBounds));
			printTest("ABC_removeC_AB_testAddAtIndex0D", testAddAtIndex(ABC_removeC_AB(), 0, ELEMENT_D, Result.NoException));
			printTest("ABC_removeC_AB_testAddAtIndex1D", testAddAtIndex(ABC_removeC_AB(), 1, ELEMENT_D, Result.NoException));
			printTest("ABC_removeC_AB_testAddAtIndex2D", testAddAtIndex(ABC_removeC_AB(), 2, ELEMENT_D, Result.NoException));
			printTest("ABC_removeC_AB_testAddAtIndex3D", testAddAtIndex(ABC_removeC_AB(), 3, ELEMENT_D, Result.IndexOutOfBounds));
			printTest("ABC_removeC_AB_testAddD", testAdd(ABC_removeC_AB(), ELEMENT_D, Result.NoException));
			printTest("ABC_removeC_AB_testRemoveFirst", testRemoveFirst(ABC_removeC_AB(), ELEMENT_A, Result.MatchingValue));
			printTest("ABC_removeC_AB_testRemoveLast", testRemoveLast(ABC_removeC_AB(), ELEMENT_B, Result.MatchingValue));
			printTest("ABC_removeC_AB_testRemoveA", testRemoveElement(ABC_removeC_AB(), ELEMENT_A, Result.MatchingValue));
			printTest("ABC_removeC_AB_testRemoveC", testRemoveElement(ABC_removeC_AB(), ELEMENT_C, Result.NoSuchElement));
			printTest("ABC_removeC_AB_testRemoveB", testRemoveElement(ABC_removeC_AB(), ELEMENT_B, Result.MatchingValue));
			printTest("ABC_removeC_AB_testRemoveNeg1", testRemoveIndex(ABC_removeC_AB(), -1, null, Result.IndexOutOfBounds));
			printTest("ABC_removeC_AB_testRemove0", testRemoveIndex(ABC_removeC_AB(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("ABC_removeC_AB_testRemove1", testRemoveIndex(ABC_removeC_AB(), 1, ELEMENT_B, Result.MatchingValue));
			printTest("ABC_removeC_AB_testRemove2", testRemoveIndex(ABC_removeC_AB(), 2, null, Result.IndexOutOfBounds));
			printTest("ABC_removeC_AB_testFirst", testFirst(ABC_removeC_AB(), ELEMENT_A, Result.MatchingValue));
			printTest("ABC_removeC_AB_testLast", testLast(ABC_removeC_AB(), ELEMENT_B, Result.MatchingValue));
			printTest("ABC_removeC_AB_testContainsA", testContains(ABC_removeC_AB(), ELEMENT_A, Result.True));
			printTest("ABC_removeC_AB_testContainsB", testContains(ABC_removeC_AB(), ELEMENT_B, Result.True));
			printTest("ABC_removeC_AB_testContainsC", testContains(ABC_removeC_AB(), ELEMENT_C, Result.False));
			printTest("ABC_removeC_AB_testIsEmpty", testIsEmpty(ABC_removeC_AB(), Result.False));
			printTest("ABC_removeC_AB_testSize", testSize(ABC_removeC_AB(), 2));
			printTest("ABC_removeC_AB_testToString", testToString(ABC_removeC_AB(), Result.ValidString));
			printTest("ABC_removeC_AB_testSetNeg1D", testSet(ABC_removeC_AB(), -1, ELEMENT_D, Result.IndexOutOfBounds));
			printTest("ABC_removeC_AB_testSet0D", testSet(ABC_removeC_AB(), 0, ELEMENT_D, Result.NoException));
			printTest("ABC_removeC_AB_testSet1D", testSet(ABC_removeC_AB(), 1, ELEMENT_D, Result.NoException));
			printTest("ABC_removeC_AB_testSet2D", testSet(ABC_removeC_AB(), 2, ELEMENT_D, Result.IndexOutOfBounds));
			printTest("ABC_removeC_AB_testGetNeg1", testGet(ABC_removeC_AB(), -1, null, Result.IndexOutOfBounds));
			printTest("ABC_removeC_AB_testGet0", testGet(ABC_removeC_AB(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("ABC_removeC_AB_testGet1", testGet(ABC_removeC_AB(), 1, ELEMENT_B, Result.MatchingValue));
			printTest("ABC_removeC_AB_testGet2", testGet(ABC_removeC_AB(), 2, null, Result.IndexOutOfBounds));
			printTest("ABC_removeC_AB_testIndexOfA", testIndexOf(ABC_removeC_AB(), ELEMENT_A, 0));
			printTest("ABC_removeC_AB_testIndexOfB", testIndexOf(ABC_removeC_AB(), ELEMENT_B, 1));
			printTest("ABC_removeC_AB_testIndexOfC", testIndexOf(ABC_removeC_AB(), ELEMENT_C, -1));
			// Iterator
			printTest("ABC_removeC_AB_testIter", testIter(ABC_removeC_AB(), Result.NoException));
			printTest("ABC_removeC_AB_testIterHasNext", testIterHasNext(ABC_removeC_AB().iterator(), Result.True));
			printTest("ABC_removeC_AB_testIterNext", testIterNext(ABC_removeC_AB().iterator(), ELEMENT_A, Result.MatchingValue));
			printTest("ABC_removeC_AB_testIterRemove", testIterRemove(ABC_removeC_AB().iterator(), Result.IllegalState));
			printTest("ABC_removeC_AB_iterNext_testIterHasNext", testIterHasNext(iterAfterNext(ABC_removeC_AB(), 1), Result.True));
			printTest("ABC_removeC_AB_iterNext_testIterNext", testIterNext(iterAfterNext(ABC_removeC_AB(), 1), ELEMENT_B, Result.MatchingValue));
			printTest("ABC_removeC_AB_iterNext_testIterRemove", testIterRemove(iterAfterNext(ABC_removeC_AB(), 1), Result.NoException));
			printTest("ABC_removeC_AB_iterNextRemove_testIterHasNext", testIterHasNext(iterAfterRemove(iterAfterNext(ABC_removeC_AB(), 1)), Result.True));
			printTest("ABC_removeC_AB_iterNextRemove_testIterNext", testIterNext(iterAfterRemove(iterAfterNext(ABC_removeC_AB(), 1)), ELEMENT_B, Result.MatchingValue));
			printTest("ABC_removeC_AB_iterNextRemove_testIterRemove", testIterRemove(iterAfterRemove(iterAfterNext(ABC_removeC_AB(), 1)), Result.IllegalState));
			printTest("ABC_removeC_AB_iterNextNext_testIterHasNext", testIterHasNext(iterAfterNext(ABC_removeC_AB(), 2), Result.False));
			printTest("ABC_removeC_AB_iterNextNext_testIterNext", testIterNext(iterAfterNext(ABC_removeC_AB(), 2), null, Result.NoSuchElement));
			printTest("ABC_removeC_AB_iterNextNext_testIterRemove", testIterRemove(iterAfterNext(ABC_removeC_AB(), 2), Result.NoException));
			printTest("ABC_removeC_AB_iterNextNextRemove_testIterHasNext", testIterHasNext(iterAfterRemove(iterAfterNext(ABC_removeC_AB(), 2)), Result.False));
			printTest("ABC_removeC_AB_iterNextNextRemove_testIterNext", testIterNext(iterAfterRemove(iterAfterNext(ABC_removeC_AB(), 2)), null, Result.NoSuchElement));
			printTest("ABC_removeC_AB_iterNextNextRemove_testIterRemove", testIterRemove(iterAfterRemove(iterAfterNext(ABC_removeC_AB(), 2)), Result.IllegalState));
			// ListIterator
			printTest("newList_testListIter", testListIter(newList(), Result.NoException));
			printTest("newList_testListIter", testListIter(newList(), 0, Result.NoException));

		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "test_ABC_removeC_AB");
			e.printStackTrace();
		}
	}

	////////////////////////////////////////////////
	// XXX SCENARIO: [A,B,C] -> remove(2) -> [A,B]
	////////////////////////////////////////////////

	/** Scenario: [A,B,C] -> remove(2) -> [A,B] 
	 * @return [A,B] after remove(2)
	 */
	private IndexedUnsortedList<Integer> ABC_remove2_AB() {
		IndexedUnsortedList<Integer> list = newList();
		list.add(ELEMENT_A);
		list.add(ELEMENT_B);
		list.add(ELEMENT_C);
		list.remove(2);
		return list;
	}

	/** Run all tests on scenario: [A,B,C] -> remove(2) -> [A,B] */
	private void test_ABC_remove2_AB() {
		System.out.println("\nSCENARIO: [A,B,C] -> remove(2) -> [A,B]\n");
		try {
			printTest("ABC_remove2_AB_testAddToFrontD", testAddToFront(ABC_remove2_AB(), ELEMENT_D, Result.NoException));
			printTest("ABC_remove2_AB_testAddToRearD", testAddToRear(ABC_remove2_AB(), ELEMENT_D, Result.NoException));
			printTest("ABC_remove2_AB_testAddAfterBD", testAddAfter(ABC_remove2_AB(), ELEMENT_C, ELEMENT_D, Result.NoSuchElement));
			printTest("ABC_remove2_AB_testAddAfterAD", testAddAfter(ABC_remove2_AB(), ELEMENT_A, ELEMENT_D, Result.NoException));
			printTest("ABC_remove2_AB_testAddAfterCD", testAddAfter(ABC_remove2_AB(), ELEMENT_B, ELEMENT_D, Result.NoException));
			printTest("ABC_remove2_AB_testAddAtIndexNeg1D", testAddAtIndex(ABC_remove2_AB(), -1, ELEMENT_D, Result.IndexOutOfBounds));
			printTest("ABC_remove2_AB_testAddAtIndex0D", testAddAtIndex(ABC_remove2_AB(), 0, ELEMENT_D, Result.NoException));
			printTest("ABC_remove2_AB_testAddAtIndex1D", testAddAtIndex(ABC_remove2_AB(), 1, ELEMENT_D, Result.NoException));
			printTest("ABC_remove2_AB_testAddAtIndex2D", testAddAtIndex(ABC_remove2_AB(), 2, ELEMENT_D, Result.NoException));
			printTest("ABC_remove2_AB_testAddAtIndex3D", testAddAtIndex(ABC_remove2_AB(), 3, ELEMENT_D, Result.IndexOutOfBounds));
			printTest("ABC_remove2_AB_testAddD", testAdd(ABC_remove2_AB(), ELEMENT_D, Result.NoException));
			printTest("ABC_remove2_AB_testRemoveFirst", testRemoveFirst(ABC_remove2_AB(), ELEMENT_A, Result.MatchingValue));
			printTest("ABC_remove2_AB_testRemoveLast", testRemoveLast(ABC_remove2_AB(), ELEMENT_B, Result.MatchingValue));
			printTest("ABC_remove2_AB_testRemoveA", testRemoveElement(ABC_remove2_AB(), ELEMENT_A, Result.MatchingValue));
			printTest("ABC_remove2_AB_testRemoveC", testRemoveElement(ABC_remove2_AB(), ELEMENT_C, Result.NoSuchElement));
			printTest("ABC_remove2_AB_testRemoveB", testRemoveElement(ABC_remove2_AB(), ELEMENT_B, Result.MatchingValue));
			printTest("ABC_remove2_AB_testRemoveNeg1", testRemoveIndex(ABC_remove2_AB(), -1, null, Result.IndexOutOfBounds));
			printTest("ABC_remove2_AB_testRemove0", testRemoveIndex(ABC_remove2_AB(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("ABC_remove2_AB_testRemove1", testRemoveIndex(ABC_remove2_AB(), 1, ELEMENT_B, Result.MatchingValue));
			printTest("ABC_remove2_AB_testRemove2", testRemoveIndex(ABC_remove2_AB(), 2, null, Result.IndexOutOfBounds));
			printTest("ABC_remove2_AB_testFirst", testFirst(ABC_remove2_AB(), ELEMENT_A, Result.MatchingValue));
			printTest("ABC_remove2_AB_testLast", testLast(ABC_remove2_AB(), ELEMENT_B, Result.MatchingValue));
			printTest("ABC_remove2_AB_testContainsA", testContains(ABC_remove2_AB(), ELEMENT_A, Result.True));
			printTest("ABC_remove2_AB_testContainsB", testContains(ABC_remove2_AB(), ELEMENT_B, Result.True));
			printTest("ABC_remove2_AB_testContainsC", testContains(ABC_remove2_AB(), ELEMENT_C, Result.False));
			printTest("ABC_remove2_AB_testIsEmpty", testIsEmpty(ABC_remove2_AB(), Result.False));
			printTest("ABC_remove2_AB_testSize", testSize(ABC_remove2_AB(), 2));
			printTest("ABC_remove2_AB_testToString", testToString(ABC_remove2_AB(), Result.ValidString));
			printTest("ABC_remove2_AB_testSetNeg1D", testSet(ABC_remove2_AB(), -1, ELEMENT_D, Result.IndexOutOfBounds));
			printTest("ABC_remove2_AB_testSet0D", testSet(ABC_remove2_AB(), 0, ELEMENT_D, Result.NoException));
			printTest("ABC_remove2_AB_testSet1D", testSet(ABC_remove2_AB(), 1, ELEMENT_D, Result.NoException));
			printTest("ABC_remove2_AB_testSet2D", testSet(ABC_remove2_AB(), 2, ELEMENT_D, Result.IndexOutOfBounds));
			printTest("ABC_remove2_AB_testGetNeg1", testGet(ABC_remove2_AB(), -1, null, Result.IndexOutOfBounds));
			printTest("ABC_remove2_AB_testGet0", testGet(ABC_remove2_AB(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("ABC_remove2_AB_testGet1", testGet(ABC_remove2_AB(), 1, ELEMENT_B, Result.MatchingValue));
			printTest("ABC_remove2_AB_testGet2", testGet(ABC_remove2_AB(), 2, null, Result.IndexOutOfBounds));
			printTest("ABC_remove2_AB_testIndexOfA", testIndexOf(ABC_remove2_AB(), ELEMENT_A, 0));
			printTest("ABC_remove2_AB_testIndexOfB", testIndexOf(ABC_remove2_AB(), ELEMENT_B, 1));
			printTest("ABC_remove2_AB_testIndexOfC", testIndexOf(ABC_remove2_AB(), ELEMENT_C, -1));
			// Iterator
			printTest("ABC_remove2_AB_testIter", testIter(ABC_remove2_AB(), Result.NoException));
			printTest("ABC_remove2_AB_testIterHasNext", testIterHasNext(ABC_remove2_AB().iterator(), Result.True));
			printTest("ABC_remove2_AB_testIterNext", testIterNext(ABC_remove2_AB().iterator(), ELEMENT_A, Result.MatchingValue));
			printTest("ABC_remove2_AB_testIterRemove", testIterRemove(ABC_remove2_AB().iterator(), Result.IllegalState));
			printTest("ABC_remove2_AB_iterNext_testIterHasNext", testIterHasNext(iterAfterNext(ABC_remove2_AB(), 1), Result.True));
			printTest("ABC_remove2_AB_iterNext_testIterNext", testIterNext(iterAfterNext(ABC_remove2_AB(), 1), ELEMENT_B, Result.MatchingValue));
			printTest("ABC_remove2_AB_iterNext_testIterRemove", testIterRemove(iterAfterNext(ABC_remove2_AB(), 1), Result.NoException));
			printTest("ABC_remove2_AB_iterNextRemove_testIterHasNext", testIterHasNext(iterAfterRemove(iterAfterNext(ABC_remove2_AB(), 1)), Result.True));
			printTest("ABC_remove2_AB_iterNextRemove_testIterNext", testIterNext(iterAfterRemove(iterAfterNext(ABC_remove2_AB(), 1)), ELEMENT_B, Result.MatchingValue));
			printTest("ABC_remove2_AB_iterNextRemove_testIterRemove", testIterRemove(iterAfterRemove(iterAfterNext(ABC_remove2_AB(), 1)), Result.IllegalState));
			printTest("ABC_remove2_AB_iterNextNext_testIterHasNext", testIterHasNext(iterAfterNext(ABC_remove2_AB(), 2), Result.False));
			printTest("ABC_remove2_AB_iterNextNext_testIterNext", testIterNext(iterAfterNext(ABC_remove2_AB(), 2), null, Result.NoSuchElement));
			printTest("ABC_remove2_AB_iterNextNext_testIterRemove", testIterRemove(iterAfterNext(ABC_remove2_AB(), 2), Result.NoException));
			printTest("ABC_remove2_AB_iterNextNextRemove_testIterHasNext", testIterHasNext(iterAfterRemove(iterAfterNext(ABC_remove2_AB(), 2)), Result.False));
			printTest("ABC_remove2_AB_iterNextNextRemove_testIterNext", testIterNext(iterAfterRemove(iterAfterNext(ABC_remove2_AB(), 2)), null, Result.NoSuchElement));
			printTest("ABC_remove2_AB_iterNextNextRemove_testIterRemove", testIterRemove(iterAfterRemove(iterAfterNext(ABC_remove2_AB(), 2)), Result.IllegalState));
			// ListIterator
			printTest("newList_testListIter", testListIter(newList(), Result.NoException));
			printTest("newList_testListIter", testListIter(newList(), 0, Result.NoException));

		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "test_ABC_remove2_AB");
			e.printStackTrace();
		}
	}
	////////////////////////////////////////////////
	// XXX SCENARIO: [A,B] -> set(1,C) -> [A,C]
	////////////////////////////////////////////////

	/** Scenario: [A,B] -> set(1,C) -> [A,C] 
	 * @return [A,C] after set(1,C)
	 */
	private IndexedUnsortedList<Integer> AB_set1C_AC() {
		IndexedUnsortedList<Integer> list = A_addToRearB_AB(); //starting state 
		list.set(1,ELEMENT_C); //the change
		return list; //return resulting state
	}

	/** Run all tests on scenario: [A,B] -> set(1,C) -> [A,C] */
	private void test_AB_set1C_AC() {
		System.out.println("\nSCENARIO: [A,B] -> set(1,C) -> [A,C]\n");
		try {
			printTest("AB_set1C_AC_testAddToFrontD", testAddToFront(AB_set1C_AC(), ELEMENT_D, Result.NoException));
			printTest("AB_set1C_AC_testAddToRearD", testAddToRear(AB_set1C_AC(), ELEMENT_D, Result.NoException));
			printTest("AB_set1C_AC_testAddAfterBD", testAddAfter(AB_set1C_AC(), ELEMENT_B, ELEMENT_D, Result.NoSuchElement));
			printTest("AB_set1C_AC_testAddAfterAD", testAddAfter(AB_set1C_AC(), ELEMENT_A, ELEMENT_D, Result.NoException));
			printTest("AB_set1C_AC_testAddAfterCD", testAddAfter(AB_set1C_AC(), ELEMENT_C, ELEMENT_D, Result.NoException));
			printTest("AB_set1C_AC_testAddAtIndexNeg1D", testAddAtIndex(AB_set1C_AC(), -1, ELEMENT_D, Result.IndexOutOfBounds));
			printTest("AB_set1C_AC_testAddAtIndex0D", testAddAtIndex(AB_set1C_AC(), 0, ELEMENT_D, Result.NoException));
			printTest("AB_set1C_AC_testAddAtIndex1D", testAddAtIndex(AB_set1C_AC(), 1, ELEMENT_D, Result.NoException));
			printTest("AB_set1C_AC_testAddAtIndex2D", testAddAtIndex(AB_set1C_AC(), 2, ELEMENT_D, Result.NoException));
			printTest("AB_set1C_AC_testAddAtIndex3D", testAddAtIndex(AB_set1C_AC(), 3, ELEMENT_D, Result.IndexOutOfBounds));
			printTest("AB_set1C_AC_testAddD", testAdd(AB_set1C_AC(), ELEMENT_D, Result.NoException));
			printTest("AB_set1C_AC_testRemoveFirst", testRemoveFirst(AB_set1C_AC(), ELEMENT_A, Result.MatchingValue));
			printTest("AB_set1C_AC_testRemoveLast", testRemoveLast(AB_set1C_AC(), ELEMENT_C, Result.MatchingValue));
			printTest("AB_set1C_AC_testRemoveA", testRemoveElement(AB_set1C_AC(), ELEMENT_A, Result.MatchingValue));
			printTest("AB_set1C_AC_testRemoveB", testRemoveElement(AB_set1C_AC(), ELEMENT_B, Result.NoSuchElement));
			printTest("AB_set1C_AC_testRemoveC", testRemoveElement(AB_set1C_AC(), ELEMENT_C, Result.MatchingValue));
			printTest("AB_set1C_AC_testRemoveNeg1", testRemoveIndex(AB_set1C_AC(), -1, null, Result.IndexOutOfBounds));
			printTest("AB_set1C_AC_testRemove0", testRemoveIndex(AB_set1C_AC(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("AB_set1C_AC_testRemove1", testRemoveIndex(AB_set1C_AC(), 1, ELEMENT_C, Result.MatchingValue));
			printTest("AB_set1C_AC_testRemove2", testRemoveIndex(AB_set1C_AC(), 2, null, Result.IndexOutOfBounds));
			printTest("AB_set1C_AC_testFirst", testFirst(AB_set1C_AC(), ELEMENT_A, Result.MatchingValue));
			printTest("AB_set1C_AC_testLast", testLast(AB_set1C_AC(), ELEMENT_C, Result.MatchingValue));
			printTest("AB_set1C_AC_testContainsA", testContains(AB_set1C_AC(), ELEMENT_A, Result.True));
			printTest("AB_set1C_AC_testContainsB", testContains(AB_set1C_AC(), ELEMENT_B, Result.False));
			printTest("AB_set1C_AC_testContainsC", testContains(AB_set1C_AC(), ELEMENT_C, Result.True));
			printTest("AB_set1C_AC_testIsEmpty", testIsEmpty(AB_set1C_AC(), Result.False));
			printTest("AB_set1C_AC_testSize", testSize(AB_set1C_AC(), 2));
			printTest("AB_set1C_AC_testToString", testToString(AB_set1C_AC(), Result.ValidString));
			printTest("AB_set1C_AC_testSetNeg1D", testSet(AB_set1C_AC(), -1, ELEMENT_D, Result.IndexOutOfBounds));
			printTest("AB_set1C_AC_testSet0D", testSet(AB_set1C_AC(), 0, ELEMENT_D, Result.NoException));
			printTest("AB_set1C_AC_testSet1D", testSet(AB_set1C_AC(), 1, ELEMENT_D, Result.NoException));
			printTest("AB_set1C_AC_testSet2D", testSet(AB_set1C_AC(), 2, ELEMENT_D, Result.IndexOutOfBounds));
			printTest("AB_set1C_AC_testGetNeg1", testGet(AB_set1C_AC(), -1, null, Result.IndexOutOfBounds));
			printTest("AB_set1C_AC_testGet0", testGet(AB_set1C_AC(), 0, ELEMENT_A, Result.MatchingValue));
			printTest("AB_set1C_AC_testGet1", testGet(AB_set1C_AC(), 1, ELEMENT_C, Result.MatchingValue));
			printTest("AB_set1C_AC_testGet2", testGet(AB_set1C_AC(), 2, null, Result.IndexOutOfBounds));
			printTest("AB_set1C_AC_testIndexOfA", testIndexOf(AB_set1C_AC(), ELEMENT_A, 0));
			printTest("AB_set1C_AC_testIndexOfB", testIndexOf(AB_set1C_AC(), ELEMENT_B, -1));
			printTest("AB_set1C_AC_testIndexOfC", testIndexOf(AB_set1C_AC(), ELEMENT_C, 1));
			// Iterator
			printTest("AB_set1C_AC_testIter", testIter(AB_set1C_AC(), Result.NoException));
			printTest("AB_set1C_AC_testIterHasNext", testIterHasNext(AB_set1C_AC().iterator(), Result.True));
			
			printTest("AB_set1C_AC_testIterHasPrevious", testIterHasPrevious(AB_set1C_AC().listIterator(), Result.False));
			
			printTest("AB_set1C_AC_testIterNext", testIterNext(AB_set1C_AC().iterator(), ELEMENT_A, Result.MatchingValue));
			printTest("AB_set1C_AC_testIterRemove", testIterRemove(AB_set1C_AC().iterator(), Result.IllegalState));
			printTest("AB_set1C_AC_iterNext_testIterHasNext", testIterHasNext(iterAfterNext(AB_set1C_AC(), 1), Result.True));
			printTest("AB_set1C_AC_iterNext_testIterNext", testIterNext(iterAfterNext(AB_set1C_AC(), 1), ELEMENT_C, Result.MatchingValue));
			printTest("AB_set1C_AC_iterNext_testIterRemove", testIterRemove(iterAfterNext(AB_set1C_AC(), 1), Result.NoException));
			printTest("AB_set1C_AC_iterNextRemove_testIterHasNext", testIterHasNext(iterAfterRemove(iterAfterNext(AB_set1C_AC(), 1)), Result.True));
			printTest("AB_set1C_AC_iterNextRemove_testIterNext", testIterNext(iterAfterRemove(iterAfterNext(AB_set1C_AC(), 1)), ELEMENT_C, Result.MatchingValue));
			printTest("AB_set1C_AC_iterNextRemove_testIterRemove", testIterRemove(iterAfterRemove(iterAfterNext(AB_set1C_AC(), 1)), Result.IllegalState));
			printTest("AB_set1C_AC_iterNextNext_testIterHasNext", testIterHasNext(iterAfterNext(AB_set1C_AC(), 2), Result.False));
			
			printTest("AB_set1C_AC_iterNextNext_testIterNext", testIterNext(iterAfterNext(AB_set1C_AC(), 2), null, Result.NoSuchElement));
			printTest("AB_set1C_AC_iterNextNext_testIterRemove", testIterRemove(iterAfterNext(AB_set1C_AC(), 2), Result.NoException));
			printTest("AB_set1C_AC_iterNextNextRemove_testIterHasNext", testIterHasNext(iterAfterRemove(iterAfterNext(AB_set1C_AC(), 2)), Result.False));
			printTest("AB_set1C_AC_iterNextNextRemove_testIterNext", testIterNext(iterAfterRemove(iterAfterNext(AB_set1C_AC(), 2)), null, Result.NoSuchElement));
			printTest("AB_set1C_AC_iterNextNextRemove_testIterRemove", testIterRemove(iterAfterRemove(iterAfterNext(AB_set1C_AC(), 2)), Result.IllegalState));
			// ListIterator
			printTest("newList_testListIter", testListIter(newList(), Result.NoException));
			printTest("newList_testListIter", testListIter(newList(), 0, Result.NoException));

		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "test_AB_set1C_AC");
			e.printStackTrace();
		}
	}

	////////////////////////////
	// XXX LIST TEST METHODS
	////////////////////////////

	/**
	 * Runs addToFront() method on a given list and checks result against expectedResult
	 * @param list a list already prepared for a given change scenario
	 * @param element
	 * @param expectedResult
	 * @return test success
	 */
	private boolean testAddToFront(IndexedUnsortedList<Integer> list, Integer element, Result expectedResult) {
		Result result;
		try {
			list.addToFront(element);
			result = Result.NoException;
		} catch (Exception e) {
			System.out.printf("%s caught unexpected %s\n", "testAddToFront",  e.toString());
			result = Result.UnexpectedException;
		}
		return result == expectedResult;
	}

	/**
	 * Runs addToRear() method on a given list and checks result against expectedResult
	 * @param list a list already prepared for a given change scenario
	 * @param element
	 * @param expectedResult
	 * @return test success
	 */
	private boolean testAddToRear(IndexedUnsortedList<Integer> list, Integer element, Result expectedResult) {
		Result result;
		try {
			list.addToRear(element);
			result = Result.NoException;
		} catch (Exception e) {
			System.out.printf("%s caught unexpected %s\n", "testAddToRear", e.toString());
			e.printStackTrace();
			result = Result.UnexpectedException;
		}
		return result == expectedResult;
	}

	/**
	 * Runs addAfter() method on a given list and checks result against expectedResult
	 * @param list a list already prepared for a given change scenario
	 * @param target
	 * @param element
	 * @param expectedResult
	 * @return test success
	 */
	private boolean testAddAfter(IndexedUnsortedList<Integer> list, Integer target, Integer element, Result expectedResult) {
		Result result;
		try {
			list.addAfter(element, target);
			result = Result.NoException;
		} catch (NoSuchElementException e) {
			result = Result.NoSuchElement;
		} catch (Exception e) {
			System.out.printf("%s caught unexpected %s\n", "testAddAfter", e.toString());
			e.printStackTrace();
			result = Result.UnexpectedException;
		}
		return result == expectedResult;
	}

	/**
	 * Runs add(int, T) method on a given list and checks result against expectedResult
	 * @param list a list already prepared for a given change scenario
	 * @param index
	 * @param element
	 * @param expectedResult
	 * @return test success
	 */
	private boolean testAddAtIndex(IndexedUnsortedList<Integer> list, int index, Integer element, Result expectedResult) {
		Result result;
		try {
			list.add(index, element);
			result = Result.NoException;
		} catch (IndexOutOfBoundsException e) {
			result = Result.IndexOutOfBounds;
		} catch (Exception e) {
			System.out.printf("%s caught unexpected %s\n", "testAddAtIndex", e.toString());
			e.printStackTrace();
			result = Result.UnexpectedException;
		}
		return result == expectedResult;
	}

	/**
	 * Runs add(T) method on a given list and checks result against expectedResult
	 * @param list a list already prepared for a given change scenario
	 * @param element
	 * @param expectedResult
	 * @return test success
	 */
	private boolean testAdd(IndexedUnsortedList<Integer> list, Integer element, Result expectedResult) {
		Result result;
		try {
			list.add(element);
			result = Result.NoException;
		} catch (IndexOutOfBoundsException e) {
			result = Result.IndexOutOfBounds;
		} catch (Exception e) {
			System.out.printf("%s caught unexpected %s\n", "testAddAtIndex", e.toString());
			e.printStackTrace();
			result = Result.UnexpectedException;
		}
		return result == expectedResult;
	}

	/**
	 * Runs removeFirst() method on given list and checks result against expectedResult
	 * @param list a list already prepared for a given change scenario
	 * @param expectedElement element or null if expectedResult is an Exception
	 * @param expectedResult
	 * @return test success
	 */
	private boolean testRemoveFirst(IndexedUnsortedList<Integer> list, Integer expectedElement, Result expectedResult) {
		Result result;
		try {
			Integer retVal = list.removeFirst();
			if (retVal.equals(expectedElement)) {
				result = Result.MatchingValue;
			} else {
				result = Result.Fail;
			}
		} catch (IllegalStateException e) {
			result = Result.IllegalState;
		} catch (Exception e) {
			System.out.printf("%s caught unexpected %s\n", "testRemoveFirst", e.toString());
			e.printStackTrace();
			result = Result.UnexpectedException;
		}
		return result == expectedResult;
	}

	/**
	 * Runs removeLast() method on given list and checks result against expectedResult
	 * @param list a list already prepared for a given change scenario
	 * @param expectedElement element or null if expectedResult is an Exception
	 * @param expectedResult
	 * @return test success
	 */
	private boolean testRemoveLast(IndexedUnsortedList<Integer> list, Integer expectedElement, Result expectedResult) {
		Result result;
		try {
			Integer retVal = list.removeLast();
			if (retVal.equals(expectedElement)) {
				result = Result.MatchingValue;
			} else {
				result = Result.Fail;
			}
		} catch (IllegalStateException e) {
			result = Result.IllegalState;
		} catch (Exception e) {
			System.out.printf("%s caught unexpected %s\n", "testRemoveLast", e.toString());
			e.printStackTrace();
			result = Result.UnexpectedException;
		}
		return result == expectedResult;
	}

	/**
	 * Runs removeLast() method on given list and checks result against expectedResult
	 * @param list a list already prepared for a given change scenario
	 * @param element element to remove
	 * @param expectedResult
	 * @return test success
	 */
	private boolean testRemoveElement(IndexedUnsortedList<Integer> list, Integer element, Result expectedResult) {
		Result result;
		try {
			Integer retVal = list.remove(element);
			if (retVal.equals(element)) {
				result = Result.MatchingValue;
			} else {
				result = Result.Fail;
			}
		} catch (NoSuchElementException e) {
			result = Result.NoSuchElement;
		} catch (Exception e) {
			System.out.printf("%s caught unexpected %s\n", "testRemoveElement", e.toString());
			e.printStackTrace();
			result = Result.UnexpectedException;
		}
		return result == expectedResult;
	}

	/**
	 * Runs remove(index) method on a given list and checks result against expectedResult
	 * @param list a list already prepared for a given change scenario
	 * @param index
	 * @param expectedElement
	 * @param expectedResult
	 * @return test success
	 */
	private boolean testRemoveIndex(IndexedUnsortedList<Integer> list, int index, Integer expectedElement, Result expectedResult) {
		Result result;
		try {
			Integer retVal = list.remove(index);
			if (retVal.equals(expectedElement)) {
				result = Result.MatchingValue;
			} else {
				result = Result.Fail;
			}
		} catch (IndexOutOfBoundsException e) {
			result = Result.IndexOutOfBounds;
		} catch (Exception e) {
			System.out.printf("%s caught unexpected %s\n", "testRemoveIndex", e.toString());
			e.printStackTrace();
			result = Result.UnexpectedException;
		}
		return result == expectedResult;
	}

	/**
	 * Runs set(int, T) method on a given list and checks result against expectedResult
	 * @param list a list already prepared for a given change scenario
	 * @param index
	 * @param element
	 * @param expectedResult
	 * @return test success
	 */
	private boolean testSet(IndexedUnsortedList<Integer> list, int index, Integer element, Result expectedResult) {
		Result result;
		try {
			list.set(index, element);
			result = Result.NoException;
		} catch (IndexOutOfBoundsException e) {
			result = Result.IndexOutOfBounds;
		} catch (Exception e) {
			System.out.printf("%s caught unexpected %s\n", "testSet", e.toString());
			e.printStackTrace();
			result = Result.UnexpectedException;
		}
		return result == expectedResult;
	}

	/**
	 * Runs get() method on a given list and checks result against expectedResult
	 * @param list a list already prepared for a given change scenario
	 * @param index
	 * @param expectedElement
	 * @param expectedResult
	 * @return test success
	 */
	private boolean testGet(IndexedUnsortedList<Integer> list, int index, Integer expectedElement, Result expectedResult) {
		Result result;
		try {
			Integer retVal = list.get(index);
			if (retVal.equals(expectedElement)) {
				result = Result.MatchingValue;
			} else {
				result = Result.Fail;
			}
		} catch (IndexOutOfBoundsException e) {
			result = Result.IndexOutOfBounds;
		} catch (Exception e) {
			System.out.printf("%s caught unexpected %s\n", "testGet", e.toString());
			e.printStackTrace();
			result = Result.UnexpectedException;
		}
		return result == expectedResult;
	}

	/**
	 * Runs indexOf() method on a given list and checks result against expectedResult
	 * @param list a list already prepared for a given change scenario
	 * @param element
	 * @param expectedIndex
	 * @return test success
	 */
	private boolean testIndexOf(IndexedUnsortedList<Integer> list, Integer element, int expectedIndex) {
		try {
			return list.indexOf(element) == expectedIndex;
		} catch (Exception e) {
			System.out.printf("%s caught unexpected %s\n", "testIndexOf", e.toString());
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Runs first() method on a given list and checks result against expectedResult
	 * @param list a list already prepared for a given change scenario
	 * @param expectedElement element or null if expectedResult is an Exception
	 * @param expectedResult
	 * @return test success
	 */
	private boolean testFirst(IndexedUnsortedList<Integer> list, Integer expectedElement, Result expectedResult) {
		Result result;
		try {
			Integer retVal = list.first();
			if (retVal.equals(expectedElement)) {
				result = Result.MatchingValue;
			} else {
				result = Result.Fail;
			}
		} catch (IllegalStateException e) {
			result = Result.IllegalState;
		} catch (Exception e) {
			System.out.printf("%s caught unexpected %s\n", "testFirst", e.toString());
			e.printStackTrace();
			result = Result.UnexpectedException;
		}
		return result == expectedResult;
	}

	/**
	 * Runs last() method on a given list and checks result against expectedResult
	 * @param list a list already prepared for a given change scenario
	 * @param expectedElement element or null if expectedResult is an Exception
	 * @param expectedResult
	 * @return test success
	 */
	private boolean testLast(IndexedUnsortedList<Integer> list, Integer expectedElement, Result expectedResult) {
		Result result;
		try {
			Integer retVal = list.last();
			if (retVal.equals(expectedElement)) {
				result = Result.MatchingValue;
			} else {
				result = Result.Fail;
			}
		} catch (IllegalStateException e) {
			result = Result.IllegalState;
		} catch (Exception e) {
			System.out.printf("%s caught unexpected %s\n", "testLast", e.toString());
			e.printStackTrace();
			result = Result.UnexpectedException;
		}
		return result == expectedResult;
	}

	/**
	 * Runs contains() method on a given list and element and checks result against expectedResult
	 * @param list a list already prepared for a given change scenario
	 * @param element
	 * @param expectedResult
	 * @return test success
	 */
	private boolean testContains(IndexedUnsortedList<Integer> list, Integer element, Result expectedResult) {
		Result result;
		try {
			if (list.contains(element)) {
				result = Result.True;
			} else {
				result = Result.False;
			}
		} catch (Exception e) {
			System.out.printf("%s caught unexpected %s\n", "testContains", e.toString());
			e.printStackTrace();
			result = Result.UnexpectedException;
		}
		return result == expectedResult;
	}

	/**
	 * Runs isEmpty() method on a given list and checks result against expectedResult
	 * @param list a list already prepared for a given change scenario
	 * @param expectedResult
	 * @return test success
	 */
	private boolean testIsEmpty(IndexedUnsortedList<Integer> list, Result expectedResult) {
		Result result;
		try {
			if (list.isEmpty()) {
				result = Result.True;
			} else {
				result = Result.False;
			}
		} catch (Exception e) {
			System.out.printf("%s caught unexpected %s\n", "testIsEmpty", e.toString());
			e.printStackTrace();
			result = Result.UnexpectedException;
		}
		return result == expectedResult;
	}

	/**
	 * Runs size() method on a given list and checks result against expectedResult
	 * @param list a list already prepared for a given change scenario
	 * @param expectedSize
	 * @return test success
	 */
	private boolean testSize(IndexedUnsortedList<Integer> list, int expectedSize) {
		try {
			return (list.size() == expectedSize);
		} catch (Exception e) {
			System.out.printf("%s caught unexpected %s\n", "testSize", e.toString());
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Runs toString() method on given list and attempts to confirm non-default or empty String
	 * difficult to test - just confirm that default address output has been overridden
	 * @param list a list already prepared for a given change scenario
	 * @param expectedResult
	 * @return test success
	 */
	private boolean testToString(IndexedUnsortedList<Integer> list, Result expectedResult) {
		Result result;
		try {
			String str = list.toString();
			System.out.println("toString() output: " + str);
			if (str.length() == 0) {
				result = Result.Fail;
			}
			char lastChar = str.charAt(str.length() - 1);
			if (str.contains("@")
					&& !str.contains(" ")
					&& Character.isLetter(str.charAt(0))
					&& (Character.isDigit(lastChar) || (lastChar >= 'a' && lastChar <= 'f'))) {
				result = Result.Fail; // looks like default toString()
			} else {
				result = Result.ValidString;
			}
		} catch (Exception e) {
			System.out.printf("%s caught unexpected %s\n", "testToString", e.toString());
			e.printStackTrace();
			result = Result.UnexpectedException;
		}
		return result == expectedResult;
	}

	/**
	 * Runs iterator() method on a given list and checks result against expectedResult
	 * @param list a list already prepared for a given change scenario
	 * @param expectedResult
	 * @return test success
	 */
	private boolean testIter(IndexedUnsortedList<Integer> list, Result expectedResult) {
		Result result;
		try {
			@SuppressWarnings("unused")
			Iterator<Integer> it = list.iterator();
			result = Result.NoException;
		} catch (Exception e) {
			System.out.printf("%s caught unexpected %s\n", "testIter", e.toString());
			e.printStackTrace();
			result = Result.UnexpectedException;
		}
		return result == expectedResult;
	}

	/**
	 * Runs listIterator() method on a given list and checks result against expectedResult
	 * @param list a list already prepared for a given change scenario
	 * @param expectedResult
	 * @return test success
	 */
	private boolean testListIter(IndexedUnsortedList<Integer> list, Result expectedResult) {
		Result result;
		try {
			@SuppressWarnings("unused")
			ListIterator<Integer> it = list.listIterator();
			result = Result.NoException;
		} catch (UnsupportedOperationException e) {
			result = Result.UnsupportedOperation;
		} catch (Exception e) {
			System.out.printf("%s caught unexpected %s\n", "testIter", e.toString());
			e.printStackTrace();
			result = Result.UnexpectedException;
		}
		return result == expectedResult;
	}

	/**
	 * Runs listIterator(int startingIndex) method on a given list and checks result against expectedResult
	 * @param list a list already prepared for a given change scenario
	 * @param startingIndex
	 * @param expectedResult
	 * @return test success
	 */
	private boolean testListIter(IndexedUnsortedList<Integer> list, int startingIndex, Result expectedResult) {
		Result result;
		try {
			@SuppressWarnings("unused")
			ListIterator<Integer> it = list.listIterator(startingIndex);
			result = Result.NoException;
		} catch (UnsupportedOperationException e) {
			result = Result.UnsupportedOperation;
		} catch (IndexOutOfBoundsException e) {
			result = Result.IndexOutOfBounds;
		} catch (Exception e) {
			System.out.printf("%s caught unexpected %s\n", "testIter", e.toString());
			e.printStackTrace();
			result = Result.UnexpectedException;
		}
		return result == expectedResult;
	}

	//////////////////////////////
	// XXX ITERATOR TEST METHODS
	//////////////////////////////

	/**
	 * Runs list's iterator hasNext() method on a given list and checks result against expectedResult
	 * @param iterator an iterator already positioned for the call to hasNext()
	 * @param expectedResult
	 * @return test success
	 */
	private boolean testIterHasNext(Iterator<Integer> iterator, Result expectedResult) {
		Result result;
		try {
			if (iterator.hasNext()) {
				result = Result.True;
			} else {
				result = Result.False;
			}
		} catch (ConcurrentModificationException e) {
			result = Result.ConcurrentModification;
		} catch (Exception e) {
			System.out.printf("%s caught unexpected %s\n", "testIterHasNext", e.toString());
			e.printStackTrace();
			result = Result.UnexpectedException;
		}
		return result == expectedResult;
	}
	
	/**
	 * Runs list's ListIterator hasPrevious() method on a given list and checks result against expectedResult
	 * @param iterator an iterator already positioned for the call to hasNext()
	 * @param expectedResult
	 * @return test success
	 */
	private boolean testIterHasPrevious(ListIterator<Integer> iterator, Result expectedResult) {
		Result result;
		try {
			if (iterator.hasPrevious()) {
				result = Result.True;
			} else {
				result = Result.False;
			}
		} catch (ConcurrentModificationException e) {
			result = Result.ConcurrentModification;
		} catch (Exception e) {
			System.out.printf("%s caught unexpected %s\n", "testIterHasNext", e.toString());
			e.printStackTrace();
			result = Result.UnexpectedException;
		}
		return result == expectedResult;
	}

	/**
	 * Runs list's iterator next() method on a given list and checks result against expectedResult
	 * @param iterator an iterator already positioned for the call to hasNext()
	 * @param expectedValue the Integer expected from next() or null if an exception is expected
	 * @param expectedResult MatchingValue or expected exception
	 * @return test success
	 */
	private boolean testIterNext(Iterator<Integer> iterator, Integer expectedValue, Result expectedResult) {
		Result result;
		try {
			Integer retVal = iterator.next();
			if (retVal.equals(expectedValue)) {
				result = Result.MatchingValue;
			} else {
				result = Result.Fail;
			}
		} catch (NoSuchElementException e) {
			result = Result.NoSuchElement;
		} catch (ConcurrentModificationException e) {
			result = Result.ConcurrentModification;
		} catch (Exception e) {
			System.out.printf("%s caught unexpected %s\n", "testIterNext", e.toString());
			e.printStackTrace();
			result = Result.UnexpectedException;
		}
		return result == expectedResult;
	}

	/**
	 * Runs list's iterator remove() method on a given list and checks result against expectedResult
	 * @param iterator an iterator already positioned for the call to remove()
	 * @param expectedResult
	 * @return test success
	 */
	private boolean testIterRemove(Iterator<Integer> iterator, Result expectedResult) {
		Result result;
		try {
			iterator.remove();
			result = Result.NoException;
		} catch (IllegalStateException e) {
			result = Result.IllegalState;
		} catch (ConcurrentModificationException e) {
			result = Result.ConcurrentModification;
		} catch (Exception e) {
			System.out.printf("%s caught unexpected %s\n", "testIterRemove", e.toString());
			e.printStackTrace();
			result = Result.UnexpectedException;
		}
		return result == expectedResult;
	}

	/** Scenario: [A] -> iteratorRemoveAfterNextA -> [ ] 
	 * @return [ ] after iteratorRemoveAfterNextA
	 */
	private IndexedUnsortedList<Integer> A_iterRemoveAfterNextA_emptyList() {
		IndexedUnsortedList<Integer> list = emptyList_addToFrontA_A(); 
		Iterator<Integer> it = list.iterator();
		it.next();
		it.remove();
		return list;
	}

	/** Run all tests on scenario: [A] -> it.next()->it.remove() -> [ ] */
	private void test_A_iterRemoveAfterNextA_emptyList() {
		System.out.println("\nSCENARIO: [A] -> iterRemoveAfterNextA -> [ ]\n");
		try {
			printTest("A_iterRemoveAfterNextA_emptyList_testRemoveFirst", testRemoveFirst(A_iterRemoveAfterNextA_emptyList(), null, Result.IllegalState));
			printTest("A_iterRemoveAfterNextA_emptyList_testRemoveLast", testRemoveLast(A_iterRemoveAfterNextA_emptyList(), null, Result.IllegalState));
			printTest("A_iterRemoveAfterNextA_emptyList_testRemoveA",	testRemoveElement(A_iterRemoveAfterNextA_emptyList(), null, Result.NoSuchElement));
			printTest("A_iterRemoveAfterNextA_emptyList_testFirst", testFirst(A_iterRemoveAfterNextA_emptyList(), null, Result.IllegalState));
			printTest("A_iterRemoveAfterNextA_emptyList_testLast", testLast(A_iterRemoveAfterNextA_emptyList(), null, Result.IllegalState));
			printTest("A_iterRemoveAfterNextA_emptyList_testContainsA", 	testContains(A_iterRemoveAfterNextA_emptyList(), ELEMENT_A, Result.False));
			printTest("A_iterRemoveAfterNextA_emptyList_testIsEmpty",	testIsEmpty(A_iterRemoveAfterNextA_emptyList(), Result.True));
			printTest("A_iterRemoveAfterNextA_emptyList_testSize", testSize(A_iterRemoveAfterNextA_emptyList(), 0));
			printTest("A_iterRemoveAfterNextA_emptyList_testToString", testToString(A_iterRemoveAfterNextA_emptyList(), Result.ValidString));
			printTest("A_iterRemoveAfterNextA_emptyList_testAddToFrontA", testAddToFront(A_iterRemoveAfterNextA_emptyList(), ELEMENT_A, Result.NoException));
			printTest("A_iterRemoveAfterNextA_emptyList_testAddToRearA", testAddToRear(A_iterRemoveAfterNextA_emptyList(), ELEMENT_A, Result.NoException));
			printTest(	"A_iterRemoveAfterNextA_emptyList_testAddAfterBA", testAddAfter(A_iterRemoveAfterNextA_emptyList(), ELEMENT_B, ELEMENT_A, Result.NoSuchElement));			printTest("A_iterRemoveAfterNextA_emptyList_testAddAtIndexNeg1", testAddAtIndex(A_iterRemoveAfterNextA_emptyList(), -1, ELEMENT_A, Result.IndexOutOfBounds));
			printTest("A_iterRemoveAfterNextA_emptyList_testAddAtIndex0", testAddAtIndex(A_iterRemoveAfterNextA_emptyList(), 0, ELEMENT_A, Result.NoException));
			printTest("A_iterRemoveAfterNextA_emptyList_testAddAtIndex1", testAddAtIndex(A_iterRemoveAfterNextA_emptyList(), 1, ELEMENT_A, Result.IndexOutOfBounds));
			printTest("A_iterRemoveAfterNextA_emptyList_testSetNeg1A", testSet(A_iterRemoveAfterNextA_emptyList(), -1, ELEMENT_A, Result.IndexOutOfBounds));
			printTest("A_iterRemoveAfterNextA_emptyList_testSet0A", testSet(A_iterRemoveAfterNextA_emptyList(), 0, ELEMENT_A, Result.IndexOutOfBounds));
			printTest("A_iterRemoveAfterNextA_emptyList_testAddA", testAdd(A_iterRemoveAfterNextA_emptyList(), ELEMENT_A, Result.NoException));
			printTest("A_iterRemoveAfterNextA_emptyList_testGetNeg1", testGet(A_iterRemoveAfterNextA_emptyList(), -1, null, Result.IndexOutOfBounds));
			printTest("A_iterRemoveAfterNextA_emptyList_testGet0", testGet(A_iterRemoveAfterNextA_emptyList(), 0, null, Result.IndexOutOfBounds));
			printTest("A_iterRemoveAfterNextA_emptyList_testIndexOfA", testIndexOf(A_iterRemoveAfterNextA_emptyList(), ELEMENT_A, -1));
			printTest("A_iterRemoveAfterNextA_emptyList_testRemoveNeg1", testRemoveIndex(A_iterRemoveAfterNextA_emptyList(), -1, null, Result.IndexOutOfBounds));
			printTest("A_iterRemoveAfterNextA_emptyList_testRemove0", testRemoveIndex(A_iterRemoveAfterNextA_emptyList(), 0, null, Result.IndexOutOfBounds));
			// Iterator
			printTest("A_iterRemoveAfterNextA_emptyList_testIter", testIter(A_iterRemoveAfterNextA_emptyList(), Result.NoException));
			printTest("A_iterRemoveAfterNextA_emptyList_testIterHasNext", testIterHasNext(A_iterRemoveAfterNextA_emptyList().iterator(), Result.False));
			printTest("A_iterRemoveAfterNextA_emptyList_testIterNext", testIterNext(A_iterRemoveAfterNextA_emptyList().iterator(), null, Result.NoSuchElement));
			printTest("A_iterRemoveAfterNextA_emptyList_testIterRemove", testIterRemove(A_iterRemoveAfterNextA_emptyList().iterator(), Result.IllegalState));
		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "test_A_iterRemoveAfterNextA_emptyList");
			e.printStackTrace();
		}
	}
	
	/** Scenario: [A] -> iteratorRemoveAfterPreviousA -> [ ] 
	 * @return [ ] after iteratorRemoveAfterPreviousA
	 */
	private IndexedUnsortedList<Integer> A_iterRemoveAfterPreviousA_emptyList() {
		IndexedUnsortedList<Integer> list = emptyList_addToFrontA_A(); 
		ListIterator<Integer> lit = list.listIterator(1);
		lit.previous();
		lit.remove();
		return list;
	}

	/** Run all tests on scenario: [A] -> it.previous()->it.remove() -> [ ] */
	private void test_A_iterRemoveAfterPreviousA_emptyList() {
		System.out.println("\nSCENARIO: [A] -> iterRemoveAfterPreviousA -> [ ]\n");
		try {
			printTest("A_iterRemoveAfterPreviousA_emptyList_testRemoveFirst", testRemoveFirst(A_iterRemoveAfterPreviousA_emptyList(), null, Result.IllegalState));
			printTest("A_iterRemoveAfterPreviousA_emptyList_testRemoveLast", testRemoveLast(A_iterRemoveAfterPreviousA_emptyList(), null, Result.IllegalState));
			printTest("A_iterRemoveAfterPreviousA_emptyList_testRemoveA",	testRemoveElement(A_iterRemoveAfterPreviousA_emptyList(), null, Result.NoSuchElement));
			printTest("A_iterRemoveAfterPreviousA_emptyList_testFirst", testFirst(A_iterRemoveAfterPreviousA_emptyList(), null, Result.IllegalState));
			printTest("A_iterRemoveAfterPreviousA_emptyList_testLast", testLast(A_iterRemoveAfterPreviousA_emptyList(), null, Result.IllegalState));
			printTest("A_iterRemoveAfterPreviousA_emptyList_testContainsA", 	testContains(A_iterRemoveAfterPreviousA_emptyList(), ELEMENT_A, Result.False));
			printTest("A_iterRemoveAfterPreviousA_emptyList_testIsEmpty",	testIsEmpty(A_iterRemoveAfterPreviousA_emptyList(), Result.True));
			printTest("A_iterRemoveAfterPreviousA_emptyList_testSize", testSize(A_iterRemoveAfterPreviousA_emptyList(), 0));
			printTest("A_iterRemoveAfterPreviousA_emptyList_testToString", testToString(A_iterRemoveAfterPreviousA_emptyList(), Result.ValidString));
			printTest("A_iterRemoveAfterPreviousA_emptyList_testAddToFrontA", testAddToFront(A_iterRemoveAfterPreviousA_emptyList(), ELEMENT_A, Result.NoException));
			printTest("A_iterRemoveAfterPreviousA_emptyList_testAddToRearA", testAddToRear(A_iterRemoveAfterPreviousA_emptyList(), ELEMENT_A, Result.NoException));
			printTest("A_iterRemoveAfterPreviousA_emptyList_testAddAfterBA", testAddAfter(A_iterRemoveAfterPreviousA_emptyList(), ELEMENT_B, ELEMENT_A, Result.NoSuchElement));
			printTest("A_iterRemoveAfterPreviousA_emptyList_testAddAtIndexNeg1", testAddAtIndex(A_iterRemoveAfterPreviousA_emptyList(), -1, ELEMENT_A, Result.IndexOutOfBounds));
			printTest("A_iterRemoveAfterPreviousA_emptyList_testAddAtIndex0", testAddAtIndex(A_iterRemoveAfterPreviousA_emptyList(), 0, ELEMENT_A, Result.NoException));
			printTest("A_iterRemoveAfterPreviousA_emptyList_testAddAtIndex1", testAddAtIndex(A_iterRemoveAfterPreviousA_emptyList(), 1, ELEMENT_A, Result.IndexOutOfBounds));
			printTest("A_iterRemoveAfterPreviousA_emptyList_testSetNeg1A", testSet(A_iterRemoveAfterPreviousA_emptyList(), -1, ELEMENT_A, Result.IndexOutOfBounds));
			printTest("A_iterRemoveAfterPreviousA_emptyList_testSet0A", testSet(A_iterRemoveAfterPreviousA_emptyList(), 0, ELEMENT_A, Result.IndexOutOfBounds));
			printTest("A_iterRemoveAfterPreviousA_emptyList_testAddA", testAdd(A_iterRemoveAfterPreviousA_emptyList(), ELEMENT_A, Result.NoException));
			printTest("A_iterRemoveAfterPreviousA_emptyList_testGetNeg1", testGet(A_iterRemoveAfterPreviousA_emptyList(), -1, null, Result.IndexOutOfBounds));
			printTest("A_iterRemoveAfterPreviousA_emptyList_testGet0", testGet(A_iterRemoveAfterPreviousA_emptyList(), 0, null, Result.IndexOutOfBounds));
			printTest("A_iterRemoveAfterPreviousA_emptyList_testIndexOfA", testIndexOf(A_iterRemoveAfterPreviousA_emptyList(), ELEMENT_A, -1));
			printTest("A_iterRemoveAfterPreviousA_emptyList_testRemoveNeg1", testRemoveIndex(A_iterRemoveAfterPreviousA_emptyList(), -1, null, Result.IndexOutOfBounds));
			printTest("A_iterRemoveAfterPreviousA_emptyList_testRemove0", testRemoveIndex(A_iterRemoveAfterPreviousA_emptyList(), 0, null, Result.IndexOutOfBounds));
			// Iterator
			printTest("A_iterRemoveAfterPreviousA_emptyList_testIter", testIter(A_iterRemoveAfterPreviousA_emptyList(), Result.NoException));
			printTest("A_iterRemoveAfterPreviousA_emptyList_testIterHasNext", testIterHasNext(A_iterRemoveAfterPreviousA_emptyList().iterator(), Result.False));
			printTest("A_iterRemoveAfterPreviousA_emptyList_testIterNext", testIterNext(A_iterRemoveAfterPreviousA_emptyList().iterator(), null, Result.NoSuchElement));
			printTest("A_iterRemoveAfterPreviousA_emptyList_testIterRemove", testIterRemove(A_iterRemoveAfterPreviousA_emptyList().iterator(), Result.IllegalState));

		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "test_A_iterRemoveAfterPreviousA_emptyList");
			e.printStackTrace();
		}
	}
	
	/** Scenario: [A,B] -> iteratorRemoveAfterPreviousA -> [B] 
	 * @return [B] after iteratorRemoveAfterPreviousA
	 */
	private IndexedUnsortedList<Integer> AB_iterRemoveAfterPreviousA_B() {
		IndexedUnsortedList<Integer> list = emptyList_addToFrontA_A(); 
		list.addToRear(ELEMENT_B); // [A,B]
		ListIterator<Integer> lit = list.listIterator(1);
		lit.previous();
		lit.remove();
		return list;
	}

	/** Run all tests on scenario: [A,B] -> it.previous()->it.remove() -> [B] */
	private void test_AB_iterRemoveAfterPreviousA_B() {
		System.out.println("\nSCENARIO: [A,B] -> iterRemoveAfterPreviousA -> [B]\n");
		try {
			printTest("AB_iterRemoveAfterPreviousA_B_testAddToFrontB", testAddToFront(AB_iterRemoveAfterPreviousA_B(), ELEMENT_A, Result.NoException));
			printTest("AB_iterRemoveAfterPreviousA_B_testAddToRearB", testAddToRear(AB_iterRemoveAfterPreviousA_B(), ELEMENT_B, Result.NoException));
			printTest("AB_iterRemoveAfterPreviousA_B_testAddAfterCB", testAddAfter(AB_iterRemoveAfterPreviousA_B(), ELEMENT_C, ELEMENT_A, Result.NoSuchElement));
			printTest("AB_iterRemoveAfterPreviousA_B_testAddAfterAB", testAddAfter(AB_iterRemoveAfterPreviousA_B(), ELEMENT_B, ELEMENT_A, Result.NoException));
			printTest("AB_iterRemoveAfterPreviousA_B_testAddAtIndexNeg1B", testAddAtIndex(AB_iterRemoveAfterPreviousA_B(), -1, ELEMENT_A, Result.IndexOutOfBounds));
			printTest("AB_iterRemoveAfterPreviousA_B_testAddAtIndex0B", testAddAtIndex(AB_iterRemoveAfterPreviousA_B(), 0, ELEMENT_A, Result.NoException));
			printTest("AB_iterRemoveAfterPreviousA_B_testAddAtIndex1B", testAddAtIndex(AB_iterRemoveAfterPreviousA_B(), 1, ELEMENT_A, Result.NoException));
			printTest("AB_iterRemoveAfterPreviousA_B_testAddB", testAdd(AB_iterRemoveAfterPreviousA_B(), ELEMENT_A, Result.NoException));
			printTest("AB_iterRemoveAfterPreviousA_B_testRemoveFirst", testRemoveFirst(AB_iterRemoveAfterPreviousA_B(), ELEMENT_B, Result.MatchingValue));
			printTest("AB_iterRemoveAfterPreviousA_B_testRemoveLast", testRemoveLast(AB_iterRemoveAfterPreviousA_B(), ELEMENT_B, Result.MatchingValue));
			printTest("AB_iterRemoveAfterPreviousA_B_testRemoveA", testRemoveElement(AB_iterRemoveAfterPreviousA_B(), ELEMENT_B, Result.MatchingValue));
			printTest("AB_iterRemoveAfterPreviousA_B_testRemoveB", testRemoveElement(AB_iterRemoveAfterPreviousA_B(), ELEMENT_A, Result.NoSuchElement));
			printTest("AB_iterRemoveAfterPreviousA_B_testRemoveNeg1", testRemoveIndex(AB_iterRemoveAfterPreviousA_B(), -1, null, Result.IndexOutOfBounds));
			printTest("AB_iterRemoveAfterPreviousA_B_testRemove0", testRemoveIndex(AB_iterRemoveAfterPreviousA_B(), 0, ELEMENT_B, Result.MatchingValue));
			printTest("AB_iterRemoveAfterPreviousA_B_testRemove1", testRemoveIndex(AB_iterRemoveAfterPreviousA_B(), 1, null, Result.IndexOutOfBounds));
			printTest("AB_iterRemoveAfterPreviousA_B_testFirst", testFirst(AB_iterRemoveAfterPreviousA_B(), ELEMENT_B, Result.MatchingValue));
			printTest("AB_iterRemoveAfterPreviousA_B_testLast", testLast(AB_iterRemoveAfterPreviousA_B(), ELEMENT_B, Result.MatchingValue));
			printTest("AB_iterRemoveAfterPreviousA_B_testContainsA", testContains(AB_iterRemoveAfterPreviousA_B(), ELEMENT_B, Result.True));
			printTest("AB_iterRemoveAfterPreviousA_B_testContainsB", testContains(AB_iterRemoveAfterPreviousA_B(), ELEMENT_A, Result.False));
			printTest("AB_iterRemoveAfterPreviousA_B_testIsEmpty", testIsEmpty(AB_iterRemoveAfterPreviousA_B(), Result.False));
			printTest("AB_iterRemoveAfterPreviousA_B_testSize", testSize(AB_iterRemoveAfterPreviousA_B(), 1));
			printTest("AB_iterRemoveAfterPreviousA_B_testToString", testToString(AB_iterRemoveAfterPreviousA_B(), Result.ValidString));			
			printTest("AB_iterRemoveAfterPreviousA_B_testSetNeg1B", testSet(AB_iterRemoveAfterPreviousA_B(), -1, ELEMENT_A, Result.IndexOutOfBounds));
			printTest("AB_iterRemoveAfterPreviousA_B_testiterSetBAfterPreviousA", testSet(AB_iterRemoveAfterPreviousA_B(), 0, ELEMENT_A, Result.NoException));
			printTest("AB_iterRemoveAfterPreviousA_B_testGetNeg1", testGet(AB_iterRemoveAfterPreviousA_B(), -1, null, Result.IndexOutOfBounds));
			printTest("AB_iterRemoveAfterPreviousA_B_testGet0", testGet(AB_iterRemoveAfterPreviousA_B(), 0, ELEMENT_B, Result.MatchingValue));
			printTest("AB_iterRemoveAfterPreviousA_B_testIndexOfA", testIndexOf(AB_iterRemoveAfterPreviousA_B(), ELEMENT_B, 0));
			printTest("AB_iterRemoveAfterPreviousA_B_testIndexOfB", testIndexOf(AB_iterRemoveAfterPreviousA_B(), ELEMENT_A, -1));
			// Iterator
			printTest("AB_iterRemoveAfterPreviousA_B_testIter", testIter(AB_iterRemoveAfterPreviousA_B(), Result.NoException));
			printTest("AB_iterRemoveAfterPreviousA_B_testIterHasNext", testIterHasNext(AB_iterRemoveAfterPreviousA_B().iterator(), Result.True));
			printTest("AB_iterRemoveAfterPreviousA_B_testIterNext", testIterNext(AB_iterRemoveAfterPreviousA_B().iterator(), ELEMENT_B, Result.MatchingValue));
			printTest("AB_iterRemoveAfterPreviousA_B_testIterRemove", testIterRemove(AB_iterRemoveAfterPreviousA_B().iterator(), Result.IllegalState));
			printTest("AB_iterRemoveAfterPreviousA_B_iteratorNext_testIterHasNext", testIterHasNext(iterAfterNext(AB_iterRemoveAfterPreviousA_B(), 1), Result.False));
			printTest("AB_iterRemoveAfterPreviousA_B_iteratorNext_testIterNext", testIterNext(iterAfterNext(AB_iterRemoveAfterPreviousA_B(), 1), null, Result.NoSuchElement));
			printTest("AB_iterRemoveAfterPreviousA_B_iteratorNext_testIterRemove", testIterRemove(iterAfterNext(AB_iterRemoveAfterPreviousA_B(), 1), Result.NoException));
			printTest("AB_iterRemoveAfterPreviousA_B_iteratorNextRemove_testIterHasNext", testIterHasNext(iterAfterRemove(iterAfterNext(AB_iterRemoveAfterPreviousA_B(), 1)), Result.False));
			printTest("AB_iterRemoveAfterPreviousA_B_iteratorNextRemove_testIterNext", testIterNext(iterAfterRemove(iterAfterNext(AB_iterRemoveAfterPreviousA_B(), 1)), null, Result.NoSuchElement));
			printTest("AB_iterRemoveAfterPreviousA_B_iteratorNextRemove_testIterRemove", testIterRemove(iterAfterRemove(iterAfterNext(AB_iterRemoveAfterPreviousA_B(), 1)), Result.IllegalState));
			// ListIterator
			printTest("newList_testListIter", testListIter(newList(), Result.NoException));
			printTest("newList_testListIter", testListIter(newList(), 0, Result.NoException));

		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "test_AB_iterRemoveAfterPreviousA_B");
			e.printStackTrace();
		}
	}
	
	/** Scenario: [A] -> iteratorAddBAfterPreviousA -> [B,A] 
	 * @return [B,A] after iteratorAddBAfterPreviousA
	 */
	private IndexedUnsortedList<Integer> A_iterAddBAfterPreviousA_BA() {
		IndexedUnsortedList<Integer> list = emptyList_addToFrontA_A(); 
		ListIterator<Integer> lit = list.listIterator(1);
		lit.previous();
		lit.add(ELEMENT_B);
		return list;
	}

	/** Run all tests on scenario: [A] -> it.previous()->it.add(B) -> [B,A] */
	private void test_A_iterAddBAfterPreviousA_BA() {
		System.out.println("\nSCENARIO: [A] -> iterAddBAfterPreviousA -> [B,A]\n");
		try {
			// IndexedUnsortedList
			printTest("A_iterAddBAfterPreviousA_BA_testAddToFrontC", testAddToFront(A_iterAddBAfterPreviousA_BA(), ELEMENT_C, Result.NoException));
			printTest("A_iterAddBAfterPreviousA_BA_testAddToRearC", testAddToRear(A_iterAddBAfterPreviousA_BA(), ELEMENT_C, Result.NoException));
			printTest("A_iterAddBAfterPreviousA_BA_testAddAfterDC", testAddAfter(A_iterAddBAfterPreviousA_BA(), ELEMENT_D, ELEMENT_C, Result.NoSuchElement));
			printTest("A_iterAddBAfterPreviousA_BA_testAddAfterAC", testAddAfter(A_iterAddBAfterPreviousA_BA(), ELEMENT_A, ELEMENT_C, Result.NoException));
			printTest("A_iterAddBAfterPreviousA_BA_testAddAfterBC", testAddAfter(A_iterAddBAfterPreviousA_BA(), ELEMENT_B, ELEMENT_C, Result.NoException));
			printTest("A_iterAddBAfterPreviousA_BA_testAddAtIndexNeg1C", testAddAtIndex(A_iterAddBAfterPreviousA_BA(), -1, ELEMENT_C, Result.IndexOutOfBounds));
			printTest("A_iterAddBAfterPreviousA_BA_testAddAtIndex0C", testAddAtIndex(A_iterAddBAfterPreviousA_BA(), 0, ELEMENT_C, Result.NoException));
			printTest("A_iterAddBAfterPreviousA_BA_testAddAtIndex1C", testAddAtIndex(A_iterAddBAfterPreviousA_BA(), 1, ELEMENT_C, Result.NoException));
			printTest("A_iterAddBAfterPreviousA_BA_testAddAtIndex2C", testAddAtIndex(A_iterAddBAfterPreviousA_BA(), 2, ELEMENT_C, Result.NoException));
			printTest("A_iterAddBAfterPreviousA_BA_testAddAtIndex3C", testAddAtIndex(A_iterAddBAfterPreviousA_BA(), 3, ELEMENT_C, Result.IndexOutOfBounds));
			printTest("A_iterAddBAfterPreviousA_BA_testAddC", testAdd(A_iterAddBAfterPreviousA_BA(), ELEMENT_C, Result.NoException));
			printTest("A_iterAddBAfterPreviousA_BA_testRemoveFirst", testRemoveFirst(A_iterAddBAfterPreviousA_BA(), ELEMENT_B, Result.MatchingValue));
			printTest("A_iterAddBAfterPreviousA_BA_testRemoveLast", testRemoveLast(A_iterAddBAfterPreviousA_BA(), ELEMENT_A, Result.MatchingValue));
			printTest("A_iterAddBAfterPreviousA_BA_testRemoveA", testRemoveElement(A_iterAddBAfterPreviousA_BA(), ELEMENT_A, Result.MatchingValue));
			printTest("A_iterAddBAfterPreviousA_BA_testRemoveB", testRemoveElement(A_iterAddBAfterPreviousA_BA(), ELEMENT_B, Result.MatchingValue));
			printTest("A_iterAddBAfterPreviousA_BA_testRemoveC", testRemoveElement(A_iterAddBAfterPreviousA_BA(), ELEMENT_C, Result.NoSuchElement));
			printTest("A_iterAddBAfterPreviousA_BA_testRemoveNeg1", testRemoveIndex(A_iterAddBAfterPreviousA_BA(), -1, null, Result.IndexOutOfBounds));
			printTest("A_iterAddBAfterPreviousA_BA_testRemove0", testRemoveIndex(A_iterAddBAfterPreviousA_BA(), 0, ELEMENT_B, Result.MatchingValue));
			printTest("A_iterAddBAfterPreviousA_BA_testRemove1", testRemoveIndex(A_iterAddBAfterPreviousA_BA(), 1, ELEMENT_A, Result.MatchingValue));
			printTest("A_iterAddBAfterPreviousA_BA_testRemove2", testRemoveIndex(A_iterAddBAfterPreviousA_BA(), 2, null, Result.IndexOutOfBounds));
			printTest("A_iterAddBAfterPreviousA_BA_testFirst", testFirst(A_iterAddBAfterPreviousA_BA(), ELEMENT_B, Result.MatchingValue));
			printTest("A_iterAddBAfterPreviousA_BA_testLast", testLast(A_iterAddBAfterPreviousA_BA(), ELEMENT_A, Result.MatchingValue));
			printTest("A_iterAddBAfterPreviousA_BA_testContainsA", testContains(A_iterAddBAfterPreviousA_BA(), ELEMENT_A, Result.True));
			printTest("A_iterAddBAfterPreviousA_BA_testContainsB", testContains(A_iterAddBAfterPreviousA_BA(), ELEMENT_B, Result.True));
			printTest("A_iterAddBAfterPreviousA_BA_testContainsC", testContains(A_iterAddBAfterPreviousA_BA(), ELEMENT_C, Result.False));
			printTest("A_iterAddBAfterPreviousA_BA_testIsEmpty", testIsEmpty(A_iterAddBAfterPreviousA_BA(), Result.False));
			printTest("A_iterAddBAfterPreviousA_BA_testSize", testSize(A_iterAddBAfterPreviousA_BA(), 2));
			printTest("A_iterAddBAfterPreviousA_BA_testToString", testToString(A_iterAddBAfterPreviousA_BA(), Result.ValidString));
			printTest("A_iterAddBAfterPreviousA_BA_testSetNeg1C", testSet(A_iterAddBAfterPreviousA_BA(), -1, ELEMENT_C, Result.IndexOutOfBounds));
			printTest("A_iterAddBAfterPreviousA_BA_testSet0C", testSet(A_iterAddBAfterPreviousA_BA(), 0, ELEMENT_C, Result.NoException));
			printTest("A_iterAddBAfterPreviousA_BA_testSet1C", testSet(A_iterAddBAfterPreviousA_BA(), 1, ELEMENT_C, Result.NoException));
			printTest("A_iterAddBAfterPreviousA_BA_testSet2C", testSet(A_iterAddBAfterPreviousA_BA(), 2, ELEMENT_C, Result.IndexOutOfBounds));
			printTest("A_iterAddBAfterPreviousA_BA_testGetNeg1", testGet(A_iterAddBAfterPreviousA_BA(), -1, null, Result.IndexOutOfBounds));
			printTest("A_iterAddBAfterPreviousA_BA_testGet0", testGet(A_iterAddBAfterPreviousA_BA(), 0, ELEMENT_B, Result.MatchingValue));
			printTest("A_iterAddBAfterPreviousA_BA_testGet1", testGet(A_iterAddBAfterPreviousA_BA(), 1, ELEMENT_A, Result.MatchingValue));
			printTest("A_iterAddBAfterPreviousA_BA_testGet2", testGet(A_iterAddBAfterPreviousA_BA(), 2, null, Result.IndexOutOfBounds));
			printTest("A_iterAddBAfterPreviousA_BA_testIndexOfA", testIndexOf(A_iterAddBAfterPreviousA_BA(), ELEMENT_A, 1));
			printTest("A_iterAddBAfterPreviousA_BA_testIndexOfB", testIndexOf(A_iterAddBAfterPreviousA_BA(), ELEMENT_B, 0));
			printTest("A_iterAddBAfterPreviousA_BA_testIndexOfC", testIndexOf(A_iterAddBAfterPreviousA_BA(), ELEMENT_C, -1));
			// Iterator
			printTest("A_iterAddBAfterPreviousA_BA_testIter", testIter(A_iterAddBAfterPreviousA_BA(), Result.NoException));
			printTest("A_iterAddBAfterPreviousA_BA_testIterHasNext", testIterHasNext(A_iterAddBAfterPreviousA_BA().iterator(), Result.True));
			printTest("A_iterAddBAfterPreviousA_BA_testIterNext", testIterNext(A_iterAddBAfterPreviousA_BA().iterator(), ELEMENT_B, Result.MatchingValue));
			printTest("A_iterAddBAfterPreviousA_BA_testIterRemove", testIterRemove(A_iterAddBAfterPreviousA_BA().iterator(), Result.IllegalState));
			printTest("A_iterAddBAfterPreviousA_BA_iterNext_testIterHasNext", testIterHasNext(iterAfterNext(A_iterAddBAfterPreviousA_BA(), 1), Result.True));
			printTest("A_iterAddBAfterPreviousA_BA_iterNext_testIterNext", testIterNext(iterAfterNext(A_iterAddBAfterPreviousA_BA(), 1), ELEMENT_A, Result.MatchingValue));
			printTest("A_iterAddBAfterPreviousA_BA_iterNext_testIterRemove", testIterRemove(iterAfterNext(A_iterAddBAfterPreviousA_BA(), 1), Result.NoException));
			printTest("A_iterAddBAfterPreviousA_BA_iterNextRemove_testIterHasNext", testIterHasNext(iterAfterRemove(iterAfterNext(A_iterAddBAfterPreviousA_BA(), 1)), Result.True));
			printTest("A_iterAddBAfterPreviousA_BA_iterNextRemove_testIterNext", testIterNext(iterAfterRemove(iterAfterNext(A_iterAddBAfterPreviousA_BA(), 1)), ELEMENT_A, Result.MatchingValue));
			printTest("A_iterAddBAfterPreviousA_BA_iterNextRemove_testIterRemove", testIterRemove(iterAfterRemove(iterAfterNext(A_iterAddBAfterPreviousA_BA(), 1)), Result.IllegalState));
			printTest("A_iterAddBAfterPreviousA_BA_iterNextNext_testIterHasNext", testIterHasNext(iterAfterNext(A_iterAddBAfterPreviousA_BA(), 2), Result.False));
			printTest("A_iterAddBAfterPreviousA_BA_iterNextNext_testIterNext", testIterNext(iterAfterNext(A_iterAddBAfterPreviousA_BA(), 2), null, Result.NoSuchElement));
			printTest("A_iterAddBAfterPreviousA_BA_iterNextNext_testIterRemove", testIterRemove(iterAfterNext(A_iterAddBAfterPreviousA_BA(), 2), Result.NoException));
			printTest("A_iterAddBAfterPreviousA_BA_iterNextNextRemove_testIterHasNext", testIterHasNext(iterAfterRemove(iterAfterNext(A_iterAddBAfterPreviousA_BA(), 2)), Result.False));
			printTest("A_iterAddBAfterPreviousA_BA_iterNextNextRemove_testIterNext", testIterNext(iterAfterRemove(iterAfterNext(A_iterAddBAfterPreviousA_BA(), 2)), null, Result.NoSuchElement));
			printTest("A_iterAddBAfterPreviousA_BA_iterNextNextRemove_testIterRemove", testIterRemove(iterAfterRemove(iterAfterNext(A_iterAddBAfterPreviousA_BA(), 2)), Result.IllegalState));
			// ListIterator
			printTest("newList_testListIter", testListIter(newList(), Result.NoException));
			printTest("newList_testListIter", testListIter(newList(), 0, Result.NoException));
		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "test_A_iterAddBAfterPreviousA_BA");
			e.printStackTrace();
		}
	}
	
	/** Scenario: [A] -> iteratorSetBAfterPreviousA -> [B] 
	 * @return [B] after iteratorSetBAfterPreviousA
	 */
	private IndexedUnsortedList<Integer> A_iterSetBAfterPreviousA_B() {
		IndexedUnsortedList<Integer> list = emptyList_addToFrontA_A(); 
		ListIterator<Integer> lit = list.listIterator(1);
		lit.previous();
		lit.set(ELEMENT_B);
		return list;
	}

	/** Run all tests on scenario: [A] -> it.previous()->it.set(B) -> [B] */
	private void test_A_iterSetBAfterPreviousA_B() {
		System.out.println("\nSCENARIO: [A] -> iterSetBAfterPreviousA -> [B]\n");
		try {
			printTest("A_iterSetBAfterPreviousA_B_testAddToFrontB", testAddToFront(A_iterSetBAfterPreviousA_B(), ELEMENT_A, Result.NoException));
			printTest("A_iterSetBAfterPreviousA_B_testAddToRearB", testAddToRear(A_iterSetBAfterPreviousA_B(), ELEMENT_B, Result.NoException));
			printTest("A_iterSetBAfterPreviousA_B_testAddAfterCB", testAddAfter(A_iterSetBAfterPreviousA_B(), ELEMENT_C, ELEMENT_A, Result.NoSuchElement));
			printTest("A_iterSetBAfterPreviousA_B_testAddAfterAB", testAddAfter(A_iterSetBAfterPreviousA_B(), ELEMENT_B, ELEMENT_A, Result.NoException));
			printTest("A_iterSetBAfterPreviousA_B_testAddAtIndexNeg1B", testAddAtIndex(A_iterSetBAfterPreviousA_B(), -1, ELEMENT_A, Result.IndexOutOfBounds));
			printTest("A_iterSetBAfterPreviousA_B_testAddAtIndex0B", testAddAtIndex(A_iterSetBAfterPreviousA_B(), 0, ELEMENT_A, Result.NoException));
			printTest("A_iterSetBAfterPreviousA_B_testAddAtIndex1B", testAddAtIndex(A_iterSetBAfterPreviousA_B(), 1, ELEMENT_A, Result.NoException));
			printTest("A_iterSetBAfterPreviousA_B_testAddB", testAdd(A_iterSetBAfterPreviousA_B(), ELEMENT_A, Result.NoException));
			printTest("A_iterSetBAfterPreviousA_B_testRemoveFirst", testRemoveFirst(A_iterSetBAfterPreviousA_B(), ELEMENT_B, Result.MatchingValue));
			printTest("A_iterSetBAfterPreviousA_B_testRemoveLast", testRemoveLast(A_iterSetBAfterPreviousA_B(), ELEMENT_B, Result.MatchingValue));
			printTest("A_iterSetBAfterPreviousA_B_testRemoveA", testRemoveElement(A_iterSetBAfterPreviousA_B(), ELEMENT_B, Result.MatchingValue));
			printTest("A_iterSetBAfterPreviousA_B_testRemoveB", testRemoveElement(A_iterSetBAfterPreviousA_B(), ELEMENT_A, Result.NoSuchElement));
			printTest("A_iterSetBAfterPreviousA_B_testRemoveNeg1", testRemoveIndex(A_iterSetBAfterPreviousA_B(), -1, null, Result.IndexOutOfBounds));
			printTest("A_iterSetBAfterPreviousA_B_testRemove0", testRemoveIndex(A_iterSetBAfterPreviousA_B(), 0, ELEMENT_B, Result.MatchingValue));
			printTest("A_iterSetBAfterPreviousA_B_testRemove1", testRemoveIndex(A_iterSetBAfterPreviousA_B(), 1, null, Result.IndexOutOfBounds));
			printTest("A_iterSetBAfterPreviousA_B_testFirst", testFirst(A_iterSetBAfterPreviousA_B(), ELEMENT_B, Result.MatchingValue));
			printTest("A_iterSetBAfterPreviousA_B_testLast", testLast(A_iterSetBAfterPreviousA_B(), ELEMENT_B, Result.MatchingValue));
			printTest("A_iterSetBAfterPreviousA_B_testContainsA", testContains(A_iterSetBAfterPreviousA_B(), ELEMENT_B, Result.True));
			printTest("A_iterSetBAfterPreviousA_B_testContainsB", testContains(A_iterSetBAfterPreviousA_B(), ELEMENT_A, Result.False));
			printTest("A_iterSetBAfterPreviousA_B_testIsEmpty", testIsEmpty(A_iterSetBAfterPreviousA_B(), Result.False));
			printTest("A_iterSetBAfterPreviousA_B_testSize", testSize(A_iterSetBAfterPreviousA_B(), 1));
			printTest("A_iterSetBAfterPreviousA_B_testToString", testToString(A_iterSetBAfterPreviousA_B(), Result.ValidString));			
			printTest("A_iterSetBAfterPreviousA_B_testSetNeg1B", testSet(A_iterSetBAfterPreviousA_B(), -1, ELEMENT_A, Result.IndexOutOfBounds));
			printTest("A_iterSetBAfterPreviousA_B_testiterSetBAfterPreviousA", testSet(A_iterSetBAfterPreviousA_B(), 0, ELEMENT_A, Result.NoException));
			printTest("A_iterSetBAfterPreviousA_B_testGetNeg1", testGet(A_iterSetBAfterPreviousA_B(), -1, null, Result.IndexOutOfBounds));
			printTest("A_iterSetBAfterPreviousA_B_testGet0", testGet(A_iterSetBAfterPreviousA_B(), 0, ELEMENT_B, Result.MatchingValue));
			printTest("A_iterSetBAfterPreviousA_B_testIndexOfA", testIndexOf(A_iterSetBAfterPreviousA_B(), ELEMENT_B, 0));
			printTest("A_iterSetBAfterPreviousA_B_testIndexOfB", testIndexOf(A_iterSetBAfterPreviousA_B(), ELEMENT_A, -1));
			// Iterator
			printTest("A_iterSetBAfterPreviousA_B_testIter", testIter(A_iterSetBAfterPreviousA_B(), Result.NoException));
			printTest("A_iterSetBAfterPreviousA_B_testIterHasNext", testIterHasNext(A_iterSetBAfterPreviousA_B().iterator(), Result.True));
			printTest("A_iterSetBAfterPreviousA_B_testIterNext", testIterNext(A_iterSetBAfterPreviousA_B().iterator(), ELEMENT_B, Result.MatchingValue));
			printTest("A_iterSetBAfterPreviousA_B_testIterRemove", testIterRemove(A_iterSetBAfterPreviousA_B().iterator(), Result.IllegalState));
			printTest("A_iterSetBAfterPreviousA_B_iteratorNext_testIterHasNext", testIterHasNext(iterAfterNext(A_iterSetBAfterPreviousA_B(), 1), Result.False));
			printTest("A_iterSetBAfterPreviousA_B_iteratorNext_testIterNext", testIterNext(iterAfterNext(A_iterSetBAfterPreviousA_B(), 1), null, Result.NoSuchElement));
			printTest("A_iterSetBAfterPreviousA_B_iteratorNext_testIterRemove", testIterRemove(iterAfterNext(A_iterSetBAfterPreviousA_B(), 1), Result.NoException));
			printTest("A_iterSetBAfterPreviousA_B_iteratorNextRemove_testIterHasNext", testIterHasNext(iterAfterRemove(iterAfterNext(A_iterSetBAfterPreviousA_B(), 1)), Result.False));
			printTest("A_iterSetBAfterPreviousA_B_iteratorNextRemove_testIterNext", testIterNext(iterAfterRemove(iterAfterNext(A_iterSetBAfterPreviousA_B(), 1)), null, Result.NoSuchElement));
			printTest("A_iterSetBAfterPreviousA_B_iteratorNextRemove_testIterRemove", testIterRemove(iterAfterRemove(iterAfterNext(A_iterSetBAfterPreviousA_B(), 1)), Result.IllegalState));
			// ListIterator
			printTest("newList_testListIter", testListIter(newList(), Result.NoException));
			printTest("newList_testListIter", testListIter(newList(), 0, Result.NoException));

		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "test_A_iterSetBAfterPreviousA_B");
			e.printStackTrace();
		}
	}
	
	/////////////////////////////////////////////
	// XXX HELPER METHODS FOR TESTING ITERATORS
	/////////////////////////////////////////////

	/**
	 * Helper for testing iterators. Return an Iterator that has been advanced numCallsToNext times.
	 * @param list
	 * @param numCallsToNext
	 * @return Iterator for given list, after numCallsToNext
	 */
	private Iterator<Integer> iterAfterNext(IndexedUnsortedList<Integer> list, int numCallsToNext) {
		Iterator<Integer> it = list.iterator();
		for (int i = 0; i < numCallsToNext; i++) {
			it.next();
		}
		return it;
	}

	/**
	 * Helper for testing iterators. Return an Iterator that has had remove() called once.
	 * @param iterator
	 * @return same Iterator following a call to remove()
	 */
	private Iterator<Integer> iterAfterRemove(Iterator<Integer> iterator) {
		iterator.remove();
		return iterator;
	}

	/** run Iterator concurrency tests */
	private void test_IterConcurrency() {
		System.out.println("\nIterator Concurrency Tests\n");		
		try {
			printTest("emptyList_testConcurrentIter", testIterConcurrent(newList(), Result.NoException));
			IndexedUnsortedList<Integer> list = newList();
			Iterator<Integer> it1 = list.iterator();
			Iterator<Integer> it2 = list.iterator();
			it1.hasNext();
			printTest("emptyList_iter1HasNext_testIter2HasNext", testIterHasNext(it2, Result.False));
			list = newList();
			it1 = list.iterator();
			it2 = list.iterator();
			it1.hasNext();
			printTest("emptyList_iter1HasNext_testIter2Next", testIterNext(it2, null, Result.NoSuchElement));
			list = newList();
			it1 = list.iterator();
			it2 = list.iterator();
			it1.hasNext();
			printTest("emptyList_iter1HasNext_testIter2Remove", testIterRemove(it2, Result.IllegalState));

			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			it2 = list.iterator();
			it1.hasNext();
			printTest("A_iter1HasNext_testIter2HasNext", testIterHasNext(it2, Result.True));
			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			it2 = list.iterator();
			it1.hasNext();
			printTest("A_iter1HasNext_testIter2Next", testIterNext(it2, ELEMENT_A, Result.MatchingValue));
			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			it2 = list.iterator();
			it1.hasNext();
			printTest("A_iter1HasNext_testIter2Remove", testIterRemove(it2, Result.IllegalState));

			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			it2 = list.iterator();
			it1.next();
			printTest("A_iter1Next_testIter2HasNext", testIterHasNext(it2, Result.True));
			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			it2 = list.iterator();
			it1.next();
			printTest("A_iter1Next_testIter2Next", testIterNext(it2, ELEMENT_A, Result.MatchingValue));
			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			it2 = list.iterator();
			it1.next();
			printTest("A_iter1Next_testIter2Remove", testIterRemove(it2, Result.IllegalState));

			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			it2 = list.iterator();
			it1.next();
			it1.remove();
			printTest("A_iter1NextRemove_testIter2HasNext", testIterHasNext(it2, Result.ConcurrentModification));
			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			it2 = list.iterator();
			it1.next();
			it1.remove();
			printTest("A_iter1NextRemove_testIter2Next", testIterNext(it2, ELEMENT_A, Result.ConcurrentModification));
			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			it2 = list.iterator();
			it1.next();
			it1.remove();
			printTest("A_iter1NextRemove_testIter2Remove", testIterRemove(it2, Result.ConcurrentModification));

			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.removeFirst();
			printTest("A_removeFirst_testIterHasNextConcurrent", testIterHasNext(it1, Result.ConcurrentModification));
			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.removeFirst();
			printTest("A_removeFirst_testIterNextConcurrent", testIterNext(it1, ELEMENT_A, Result.ConcurrentModification));
			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.removeFirst();
			printTest("A_removeLast_testIterRemoveConcurrent", testIterRemove(it1, Result.ConcurrentModification));

			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.removeLast();
			printTest("A_removeLast_testIterHasNextConcurrent", testIterHasNext(it1, Result.ConcurrentModification));
			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.removeLast();
			printTest("A_removeLast_testIterNextConcurrent", testIterNext(it1, ELEMENT_A, Result.ConcurrentModification));
			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.removeLast();
			printTest("A_removeLast_testIterRemoveConcurrent", testIterRemove(it1, Result.ConcurrentModification));			

			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.remove(ELEMENT_A);
			printTest("A_removeA_testIterHasNextConcurrent", testIterHasNext(it1, Result.ConcurrentModification));
			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.remove(ELEMENT_A);
			printTest("A_remove_testIterNextConcurrent", testIterNext(it1, ELEMENT_A, Result.ConcurrentModification));
			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.remove(ELEMENT_A);
			printTest("A_remove_testIterRemoveConcurrent", testIterRemove(it1, Result.ConcurrentModification));

			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.first();
			printTest("A_first_testIterHasNextConcurrent", testIterHasNext(it1, Result.True));
			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.first();
			printTest("A_first_testIterNextConcurrent", testIterNext(it1, ELEMENT_A, Result.MatchingValue));
			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.first();
			printTest("A_first_testIterRemoveConcurrent", testIterRemove(it1, Result.IllegalState));

			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.last();
			printTest("A_last_testIterHasNextConcurrent", testIterHasNext(it1, Result.True));
			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.last();
			printTest("A_last_testIterNextConcurrent", testIterNext(it1, ELEMENT_A, Result.MatchingValue));
			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.last();
			printTest("A_last_testIterRemoveConcurrent", testIterRemove(it1, Result.IllegalState));

			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.contains(ELEMENT_A);
			printTest("A_containsA_testIterHasNextConcurrent", testIterHasNext(it1, Result.True));
			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.contains(ELEMENT_A);
			printTest("A_containsA_testIterNextConcurrent", testIterNext(it1, ELEMENT_A, Result.MatchingValue));
			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.contains(ELEMENT_A);
			printTest("A_containsA_testIterRemoveConcurrent", testIterRemove(it1, Result.IllegalState));

			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.isEmpty();
			printTest("A_isEmpty_testIterHasNextConcurrent", testIterHasNext(it1, Result.True));
			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.isEmpty();
			printTest("A_isEmpty_testIterNextConcurrent", testIterNext(it1, ELEMENT_A, Result.MatchingValue));
			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.isEmpty();
			printTest("A_isEmpty_testIterRemoveConcurrent", testIterRemove(it1, Result.IllegalState));

			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.size();
			printTest("A_size_testIterHasNextConcurrent", testIterHasNext(it1, Result.True));
			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.size();
			printTest("A_size_testIterNextConcurrent", testIterNext(it1, ELEMENT_A, Result.MatchingValue));
			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.size();
			printTest("A_size_testIterRemoveConcurrent", testIterRemove(it1, Result.IllegalState));

			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.toString();
			printTest("A_toString_testIterHasNextConcurrent", testIterHasNext(it1, Result.True));
			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.toString();
			printTest("A_toString_testIterNextConcurrent", testIterNext(it1, ELEMENT_A, Result.MatchingValue));
			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.toString();
			printTest("A_toString_testIterRemoveConcurrent", testIterRemove(it1, Result.IllegalState));

			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.addToFront(ELEMENT_B);
			printTest("A_addToFrontB_testIterHasNextConcurrent", testIterHasNext(it1, Result.ConcurrentModification));
			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.addToFront(ELEMENT_B);
			printTest("A_addToFrontB_testIterNextConcurrent", testIterNext(it1, ELEMENT_B, Result.ConcurrentModification));
			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.addToFront(ELEMENT_B);
			printTest("A_addToFrontB_testIterRemoveConcurrent", testIterRemove(it1, Result.ConcurrentModification));

			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.addToRear(ELEMENT_B);
			printTest("A_addToRearB_testIterHasNextConcurrent", testIterHasNext(it1, Result.ConcurrentModification));
			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.addToRear(ELEMENT_B);
			printTest("A_addToRearB_testIterNextConcurrent", testIterNext(it1, ELEMENT_A, Result.ConcurrentModification));
			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.addToRear(ELEMENT_B);
			printTest("A_addToRearB_testIterRemoveConcurrent", testIterRemove(it1, Result.ConcurrentModification));

			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.addAfter(ELEMENT_B, ELEMENT_A);
			printTest("A_addAfterAB_testIterHasNextConcurrent", testIterHasNext(it1, Result.ConcurrentModification));
			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.addAfter(ELEMENT_B, ELEMENT_A);
			printTest("A_addAfterAB_testIterNextConcurrent", testIterNext(it1, ELEMENT_A, Result.ConcurrentModification));
			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.addAfter(ELEMENT_B, ELEMENT_A);
			printTest("A_addAfterAB_testIterRemoveConcurrent", testIterRemove(it1, Result.ConcurrentModification));

			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.add(0,ELEMENT_B);
			printTest("A_add0B_testIterHasNextConcurrent", testIterHasNext(it1, Result.ConcurrentModification));
			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.add(0,ELEMENT_B);
			printTest("A_add0B_testIterNextConcurrent", testIterNext(it1, ELEMENT_A, Result.ConcurrentModification));
			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.add(0,ELEMENT_B);
			printTest("A_add0B_testIterRemoveConcurrent", testIterRemove(it1, Result.ConcurrentModification));

			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.set(0,ELEMENT_B);
			printTest("A_set0B_testIterHasNextConcurrent", testIterHasNext(it1, Result.ConcurrentModification));
			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.set(0,ELEMENT_B);
			printTest("A_set0B_testIterNextConcurrent", testIterNext(it1, ELEMENT_A, Result.ConcurrentModification));
			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.set(0,ELEMENT_B);
			printTest("A_set0B_testIterRemoveConcurrent", testIterRemove(it1, Result.ConcurrentModification));

			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.add(ELEMENT_B);
			printTest("A_addB_testIterHasNextConcurrent", testIterHasNext(it1, Result.ConcurrentModification));
			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.add(ELEMENT_B);
			printTest("A_addB_testIterNextConcurrent", testIterNext(it1, ELEMENT_A, Result.ConcurrentModification));
			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.add(ELEMENT_B);
			printTest("A_addB_testIterRemoveConcurrent", testIterRemove(it1, Result.ConcurrentModification));

			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.get(0);
			printTest("A_get0_testIterHasNextConcurrent", testIterHasNext(it1, Result.True));
			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.get(0);
			printTest("A_get0_testIterNextConcurrent", testIterNext(it1, ELEMENT_A, Result.MatchingValue));
			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.get(0);
			printTest("A_get_testIterRemoveConcurrent", testIterRemove(it1, Result.IllegalState));

			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.indexOf(ELEMENT_A);
			printTest("A_indexOfA_testIterHasNextConcurrent", testIterHasNext(it1, Result.True));
			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.indexOf(ELEMENT_A);
			printTest("A_indexOfA_testIterNextConcurrent", testIterNext(it1, ELEMENT_A, Result.MatchingValue));
			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.indexOf(ELEMENT_A);
			printTest("A_indexOfA_testIterRemoveConcurrent", testIterRemove(it1, Result.IllegalState));

			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.remove(0);
			printTest("A_remove0_testIterHasNextConcurrent", testIterHasNext(it1, Result.ConcurrentModification));
			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.remove(0);
			printTest("A_remove0_testIterNextConcurrent", testIterNext(it1, ELEMENT_A, Result.ConcurrentModification));
			list = emptyList_addToFrontA_A();
			it1 = list.iterator();
			list.remove(0);
			printTest("A_remove0_testIterRemoveConcurrent", testIterRemove(it1, Result.ConcurrentModification));
		} catch (Exception e) {
			System.out.printf("***UNABLE TO RUN/COMPLETE %s***\n", "test_IteratorConcurrency");
			e.printStackTrace();
		}
	}

	/**
	 * Runs iterator() method twice on a given list and checks result against expectedResult
	 * @param list a list already prepared for a given change scenario
	 * @param expectedResult
	 * @return test success
	 */
	private boolean testIterConcurrent(IndexedUnsortedList<Integer> list, Result expectedResult) {
		Result result;
		try {
			@SuppressWarnings("unused")
			Iterator<Integer> it1 = list.iterator();
			@SuppressWarnings("unused")
			Iterator<Integer> it2 = list.iterator();
			result = Result.NoException;
		} catch (Exception e) {
			System.out.printf("%s caught unexpected %s\n", "testIterConcurrent", e.toString());
			e.printStackTrace();
			result = Result.UnexpectedException;
		}
		return result == expectedResult;
	}


}// end class ListTester
