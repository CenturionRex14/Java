import java.util.Comparator;
import java.util.ListIterator;

/**
 * Class for sorting lists that implement the IndexedUnsortedList interface,
 * using ordering defined by class of objects in list or a Comparator.
 * As written uses Quicksort algorithm.
 *
 * @author CS221
 */
public class Sort
{	
	/**
	 * Returns a new list that implements the IndexedUnsortedList interface. 
	 * As configured, uses WrappedDLL. Must be changed if using 
	 * your own IUDoubleLinkedList class. 
	 * 
	 * @return a new list that implements the IndexedUnsortedList interface
	 */
	private static <T> IndexedUnsortedList<T> newList() 
	{
		return new IUDoubleLinkedList<T>();
	}
	
	/**
	 * Sorts a list that implements the IndexedUnsortedList interface 
	 * using compareTo() method defined by class of objects in list.
	 * DO NOT MODIFY THIS METHOD
	 * 
	 * @param <T>
	 *            The class of elements in the list, must extend Comparable
	 * @param list
	 *            The list to be sorted, implements IndexedUnsortedList interface 
	 * @see IndexedUnsortedList 
	 */
	public static <T extends Comparable<T>> void sort(IndexedUnsortedList<T> list) 
	{
		quicksort(list);
	}

	/**
	 * Sorts a list that implements the IndexedUnsortedList interface 
	 * using given Comparator.
	 * DO NOT MODIFY THIS METHOD
	 * 
	 * @param <T>
	 *            The class of elements in the list
	 * @param list
	 *            The list to be sorted, implements IndexedUnsortedList interface 
	 * @param c
	 *            The Comparator used
	 * @see IndexedUnsortedList 
	 */
	public static <T> void sort(IndexedUnsortedList <T> list, Comparator<T> c) 
	{
		quicksort(list, c);
	}
	
	/**
	 * Quicksort algorithm to sort objects in a list 
	 * that implements the IndexedUnsortedList interface, 
	 * using compareTo() method defined by class of objects in list.
	 * DO NOT MODIFY THIS METHOD SIGNATURE
	 * 
	 * @param <T>
	 *            The class of elements in the list, must extend Comparable
	 * @param list
	 *            The list to be sorted, implements IndexedUnsortedList interface 
	 */
	private static <T extends Comparable<T>> void quicksort(IndexedUnsortedList<T> list)
	{
		// TODO: Implement recursive quicksort algorithm
		if(list.size() < 2) {
			return;
		}
		//pick pivot point (simplest is first element)
		T pivot = list.first();
		
		//elements less than pivot element are moved into left list, greater than to right
		IndexedUnsortedList<T> leftList = newList();
		IndexedUnsortedList<T> rightList = newList();
		
		/*
		 * This method would leave elements in previous list, complicating things
		 */
//		for(T e: list){
//			if(e.compareTo(pivot) < 0) {
//				leftList.add(e);
//			}else{
//				rightList.add(e);
//			}
//		}
		
		/*
		 * Causes ConcurrentModificationException
		 */
//		ListIterator<T> iter = list.listIterator();
//		while(!list.isEmpty()){
//			if(iter.next().compareTo(pivot) > 0){
//				leftList.add(list.removeFirst());
//			}else{
//				rightList.add(list.removeFirst());
//			}
//		}
		
		while(!list.isEmpty()){
			if(list.first().compareTo(pivot) > 0){
				leftList.add(list.removeFirst());
			}else{
				rightList.add(list.removeFirst());
			}
		}
		//left list has all elements prior to pivot, right list has all after pivot or equal to
		
		//recursively sort lists
		quicksort(leftList);
		quicksort(rightList);
		
		//left list and right lists are now sorted, must now merge two lists
		while(!leftList.isEmpty()){
			list.add(leftList.removeFirst());
		}
		while(!rightList.isEmpty()){
			list.add(rightList.removeFirst());
		}
	}
		
	/**
	 * Quicksort algorithm to sort objects in a list 
	 * that implements the IndexedUnsortedList interface,
	 * using the given Comparator.
	 * DO NOT MODIFY THIS METHOD SIGNATURE
	 * 
	 * @param <T>
	 *            The class of elements in the list
	 * @param list
	 *            The list to be sorted, implements IndexedUnsortedList interface 
	 * @param c
	 *            The Comparator used
	 */
	private static <T> void quicksort(IndexedUnsortedList<T> list, Comparator<T> c)
	{
		// TODO: Implement recursive quicksort algorithm using Comparator

	}
	
}
