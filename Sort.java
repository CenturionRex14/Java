import java.util.Comparator;
import java.util.ListIterator;

/**
 * Class for sorting lists that implement the IndexedUnsortedList interface,
 * using ordering defined by class of objects in list or a Comparator.
 * As written uses Quicksort algorithm.
 *
 * @author CS221, Nathan King
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
		// Base case
		if(list.size() < 2) {
			return;
		}
		//pick pivot point (simplest is first element)
		T pivot = list.removeFirst();
		
		//elements less than pivot element are moved into left list, greater than to right
		IndexedUnsortedList<T> leftList = newList();
		IndexedUnsortedList<T> rightList = newList();
		
		while(!list.isEmpty()){
			if(list.first().compareTo(pivot) > 0){
				rightList.add(list.removeFirst());
			}else{
				leftList.add(list.removeFirst());
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
		
		list.add(pivot);
		
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
		// Base case
				if(list.size() < 2) {
					return;
				}
				//pick pivot point (simplest is first element)
				T pivot = list.removeFirst();
				
				//elements less than pivot element are moved into left list, greater than to right
				IndexedUnsortedList<T> leftList = newList();
				IndexedUnsortedList<T> rightList = newList();
				
				while(!list.isEmpty()){
					if(c.compare(list.first(), pivot) > 0){
						rightList.add(list.removeFirst());
					}else{
						leftList.add(list.removeFirst());
					}
				}
				//left list has all elements prior to pivot, right list has all after pivot or equal to
				
				//recursively sort lists
				quicksort(leftList, c);
				quicksort(rightList, c);
				
				//left list and right lists are now sorted, must now merge two lists
				while(!leftList.isEmpty()){
					list.add(leftList.removeFirst());
				}
				
				list.add(pivot);
				
				while(!rightList.isEmpty()){
					list.add(rightList.removeFirst());
				}
				

	}
	
}
