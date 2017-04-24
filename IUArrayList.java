import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;
/**
 * 
 * @author Nathan King
 *
 * @param <T>
 */
public class IUArrayList<T> implements IndexedUnsortedList<T> {

	//Instance Variables
	private static int INITIAL_SIZE = 10, rear, modCount;
	private T[] arrayList;

	public IUArrayList(){
		arrayList = (T[])(new Object[INITIAL_SIZE]);
		rear = 0;
		modCount = 0;
	}

	@Override
	public void addToFront(T element) {
		if(isEmpty()){
			arrayList[0] = element;
		}else{
			if(rear == arrayList.length){
				T[] temp = (T[])(new Object[arrayList.length * 2]);
				for(int i = 0; i < rear; i++){
					temp[i] = arrayList[i];
				}
				arrayList = temp;
			}
			for(int i = rear; i > 0; i--){
				arrayList[i] = arrayList[i-1];
			}
			arrayList[0] = element;

		}
		rear++;
		modCount++;
	}

	@Override
	public void addToRear(T element) {
		add(element);
	}

	@Override
	public void add(T element) {
		if(rear == arrayList.length){
			T[] temp = (T[])(new Object[arrayList.length * 2]);
			for(int i = 0; i < rear; i++){
				temp[i] = arrayList[i];
			}
			arrayList = temp;
		}
		arrayList[rear] = element;
		rear++;
		modCount++;
	}

	@Override
	public void addAfter(T element, T target) {
		if (!isEmpty()) {
			for (int i = 0; i < rear; i++) {
				if (arrayList[i].equals(target)) {
					add(i + 1, element);
					break;
				} else if (i + 1 == rear) {
					throw new NoSuchElementException();
				}
			} 
		}else{
			throw new NoSuchElementException();
		}

	}

	@Override
	public void add(int index, T element) {
		if(index == 0){
			addToFront(element);
		}else if(index == rear){
			add(element);
		}else{
			if(index < 0 || index > rear){
				throw new IndexOutOfBoundsException();
			}
			if(rear == arrayList.length){
				T[] temp = (T[])(new Object[arrayList.length * 2]);
				for(int i = 0; i < rear; i++){
					temp[i] = arrayList[i];
				}
				arrayList = temp;
			}
			for(int i = rear; i > index; i--){
				arrayList[i] = arrayList[i-1];
			}
			arrayList[index] = element;
			rear++;
			modCount++;
		}

	}

	@Override
	public T removeFirst() {
		if(isEmpty()){
			throw new IllegalStateException();
		}
		T retVal = arrayList[0];
		if(rear == arrayList.length){
			T[] temp = (T[])(new Object[arrayList.length * 2]);
			for(int i = 0; i < rear; i++){
				temp[i] = arrayList[i];
			}
			arrayList = temp;
		}
		for(int i = 0; i < rear; i++){
			arrayList[i] = arrayList[i + 1];
		}
		rear--;
		modCount++;
		return retVal;
	}

	@Override
	public T removeLast() {
		if(isEmpty()){
			throw new IllegalStateException();
		}		
		T retVal = arrayList[rear - 1];
		arrayList[rear - 1] = null;
		rear--;
		modCount++;
		return retVal;
	}

	@Override
	public T remove(T element) {
		if(isEmpty()){
			throw new NoSuchElementException();
		}
		T retVal = null;
		for (int i = 0; i < rear; i++) {
			if (arrayList[i].equals(element)) {
				retVal = arrayList[i];
				remove(i);
				break;
			} else if (i + 1 == rear) {
				throw new NoSuchElementException();
			}
		} 
		return retVal;
	}

	@Override
	public T remove(int index) {
		if(index < 0 || index >= rear){
			throw new IndexOutOfBoundsException();
		}
		if(rear == arrayList.length){
			T[] temp = (T[])(new Object[arrayList.length * 2]);
			for(int i = 0; i < rear; i++){
				temp[i] = arrayList[i];
			}
			arrayList = temp;
		}
		T retVal = arrayList[index];
		for(int i = index; i < rear; i++){
			arrayList[i] = arrayList[i+1];
		}
		rear--;
		modCount++;
		return retVal;
	}

	@Override
	public void set(int index, T element) {
		if(index < 0 || index >= rear){
			throw new IndexOutOfBoundsException();
		}
		arrayList[index] = element;
		modCount++;
	}

	@Override
	public T get(int index) {
		if(index < 0 || index >= rear){
			throw new IndexOutOfBoundsException();
		}
		T retVal = arrayList[index];
		return retVal;
	}

	@Override
	public int indexOf(T element) {
		int retVal = -1;
		for(int i = 0; i < rear; i++){
			if(arrayList[i] == element){
				retVal = i;
				break;
			}
		}
		return retVal;
	}

	@Override
	public T first() {
		if(isEmpty()){
			throw new IllegalStateException();
		}
		T retVal = arrayList[0];
		return retVal;
	}

	@Override
	public T last() {
		if(isEmpty()){
			throw new IllegalStateException();
		}
		T retVal = arrayList[rear - 1];
		return retVal;
	}

	@Override
	public boolean contains(T target) {
		boolean retVal = false;
		for(T obj: arrayList){
			if(obj == target){
				retVal = true;
			}
		}
		return retVal;
	}

	@Override
	public boolean isEmpty() {

		return rear == 0;
	}

	@Override
	public int size() {

		return rear;
	}

	public String toString(){
		return arrayList.toString();
	}

	@Override
	public Iterator<T> iterator() {
		IUArrayListIterator it = new IUArrayListIterator();
		return it;
	}

	@Override
	public ListIterator<T> listIterator() {
		throw new UnsupportedOperationException();
	}

	@Override
	public ListIterator<T> listIterator(int startingIndex) {
		throw new UnsupportedOperationException();
	}

	private class IUArrayListIterator implements Iterator {

		private boolean calledNext, calledRemove;
		private int itIndex, itModCount;
		private IUArrayListIterator(){
			itIndex = 0;
			itModCount = modCount;
			calledNext = false;
			calledRemove = false;
		}
		@Override
		public boolean hasNext() {
			boolean retVAl = false;
			if(itModCount != modCount){
				throw new ConcurrentModificationException();
			}
			if(itIndex < rear){
				retVAl = true;
			}
			return retVAl;
		}

		@Override
		public Object next() {
			T retVal;
			if(itModCount != modCount){
				throw new ConcurrentModificationException();
			}
			if(itIndex < rear){
				retVal = arrayList[itIndex];
				itIndex++;
				calledNext = true;
				calledRemove = false;
			}else{
				throw new NoSuchElementException();
			}
			return retVal;
		}

		public void remove() {
			if(itModCount != modCount){
				throw new ConcurrentModificationException();
			}
			if(calledNext = false || calledRemove == true){
				throw new IllegalStateException();
			}
			if(itIndex > rear || itIndex <= 0){
				throw new IllegalStateException();
			}
			if(rear == arrayList.length){
				T[] temp = (T[])(new Object[arrayList.length * 2]);
				for(int i = 0; i < rear; i++){
					temp[i] = arrayList[i];
				}
				arrayList = temp;
			}
			T retVal = arrayList[itIndex - 1];
			for(int i = itIndex - 1; i < rear; i++){
				arrayList[i] = arrayList[i+1];
			}
			rear--;
			itIndex--;
			modCount++;
			itModCount++;
			calledNext = false;
			calledRemove = true;
		}
	}

}

