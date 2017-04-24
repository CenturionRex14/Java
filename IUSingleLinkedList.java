import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class IUSingleLinkedList<T> implements IndexedUnsortedList<T> {

	private LinearNode<T> head, tail;
	private int size, modCount;

	public IUSingleLinkedList() {
		head = tail = null;
		size = modCount = 0;
	}
	@Override
	public void addToFront(T element) {
		LinearNode<T> newNode = new LinearNode<T>(element);
		if(isEmpty()){
			tail = newNode;
		}
		newNode.setNext(head);
		head = newNode;
		size++;
		modCount++;
	}

	@Override
	public void addToRear(T element) {
		LinearNode<T> newNode = new LinearNode<T>(element);
		if(isEmpty()){
			head = newNode;
		}else{
			tail.setNext(newNode);
		}
		tail = newNode;
		size++;
		modCount++;
	}

	@Override
	public void add(T element) {
		addToRear(element);

	}

	@Override
	public void addAfter(T element, T target) {
		LinearNode<T> current = head;
		while(current != null 
				&& !current.getElement().equals(target)){
			current = current.getNext();
		}
		if(current == null){
			throw new NoSuchElementException();
		}
		LinearNode<T> newNode = new LinearNode<T>(element);
		newNode.setNext(current.getNext());
		current.setNext(newNode);
		if(current == tail){
			tail = newNode;
		}
		size++;
		modCount++;
	}

	@Override
	public void add(int index, T element) {
		if(index < 0 || index > size){
			throw new IndexOutOfBoundsException();
		}
		LinearNode<T> newNode = new LinearNode<T>(element);
		if(index == 0){
			addToFront(element);
		}else if(index == size){
			addToRear(element);
		}else{
			LinearNode<T> current = head;
			for(int i = 0; i < index - 1; i++){
				current = current.getNext();
			}
			newNode.setNext(current.getNext());
			current.setNext(newNode);
			size++;
			modCount++;
		}

	}

	@Override
	public T removeFirst() {
		if(isEmpty()){
			throw new IllegalStateException();
		}
		T retVal = head.getElement();
		if(head == tail){
			head = tail = null;
		}else{
			head = head.getNext();
		}
		size--;
		modCount++;
		return retVal;
	}

	@Override
	public T removeLast() {
		if(isEmpty()){
			throw new IllegalStateException();
		}
		T retVal = tail.getElement();
		if(head == tail){
			head = tail = null;
		}else{
			LinearNode<T> current = head;
			while(current.getNext() != tail){
				current = current.getNext();
			}
			tail = current;
			current.setNext(null);
		}
		size--;
		modCount++;
		return retVal;
	}

	@Override
	public T remove(T element) {
		if(isEmpty()){
			throw new NoSuchElementException();
		}
		LinearNode<T> current = head;
		while(current != null && !current.getElement().equals(element)){
			current = current.getNext();
		}
		if(current == null){
			throw new NoSuchElementException();
		}
		T retVal = current.getElement();
		if(current == head){
			removeFirst();
		}else if(current == tail){
			removeLast();
		}else{
			LinearNode<T> prevNode = head;
			while(prevNode.getNext() != current){
				prevNode = prevNode.getNext();
			}
			prevNode.setNext(current.getNext());
			size--;
			modCount++;
		}
		return retVal;
	}

	@Override
	public T remove(int index) {
		if(index < 0 || index >= size){
			throw new IndexOutOfBoundsException();
		}
		LinearNode<T> current = head;
		for(int i = 0; i < index; i++){
			current = current.getNext();
		}
		return remove(current.getElement());
	}

	@Override
	public void set(int index, T element) {
		if(index < 0 || index >= size){
			throw new IndexOutOfBoundsException();
		}
		LinearNode<T> current = head;
		for(int i = 0; i < index; i++){
			current = current.getNext();
		}
		current.setElement(element);
		modCount++;
	}

	@Override
	public T get(int index) {
		if(index < 0 || index >= size){
			throw new IndexOutOfBoundsException();
		}
		LinearNode<T> current = head;
		for(int i = 0; i < index; i++){
			current = current.getNext();
		}
		return current.getElement();
	}

	@Override
	public int indexOf(T element) {
		if(isEmpty()){
			return -1;
		}
		int index = 0;
		LinearNode<T> current = head;
		while(current != null && !current.getElement().equals(element)){
			current = current.getNext();
			index++;
		}
		if(current == null){
			index = -1;
		}
		return index;
	}

	@Override
	public T first() {
		if(isEmpty()){
			throw new IllegalStateException();
		}
		return head.getElement();
	}

	@Override
	public T last() {
		if(isEmpty()){
			throw new IllegalStateException();
		}
		return tail.getElement();
	}

	@Override
	public boolean contains(T target) {
		LinearNode<T> current = head;
		while(current != null 
				&& !current.getElement().equals(target)){
			current = current.getNext();
		}
		return (current != null);
	}

	@Override
	public boolean isEmpty() {

		return head == null;
	}

	@Override
	public int size() {

		return size;
	}

	public String toString(){
		if(isEmpty()){
			return "[]";
		}
		String retString = "[";
		for(int i = 0; i < size; i++){
			if(i == size - 1){
				retString += get(i).toString() + "]";
			}else{
				retString += get(i).toString() + ", ";
			}
		}
		return retString;
	}

	@Override
	public Iterator<T> iterator() {
		SLLIterator iter = new SLLIterator();
		return iter;
	}

	@Override
	public ListIterator<T> listIterator() {
		throw new UnsupportedOperationException();
	}

	@Override
	public ListIterator<T> listIterator(int startingIndex) {
		throw new UnsupportedOperationException();
	}

	private class SLLIterator<T> implements Iterator {

		private boolean calledNext, calledRemove;
		private LinearNode<T> nextNode;
		private int iterModCount;
		private T nextRet;

		private SLLIterator(){
			calledNext = calledRemove = false;
			nextNode = (LinearNode<T>) head;
			iterModCount = modCount;
		}

		@Override
		public boolean hasNext() {
			boolean retVal;
			if(iterModCount != modCount){
				throw new ConcurrentModificationException();
			}
			return nextNode != null;
		}

		@Override
		public Object next() {
			if(iterModCount != modCount){
				throw new ConcurrentModificationException();
			}
			if(nextNode == null){
				throw new NoSuchElementException();
			}
			T retVal = nextNode.getElement();
			nextRet = retVal;
			nextNode = nextNode.getNext();
			calledNext = true;
			calledRemove = false;
			return retVal;
		}

		public void remove(){
			if(iterModCount != modCount){
				throw new ConcurrentModificationException();
			}
			if(!calledNext || calledRemove){
				throw new IllegalStateException();
			}
			if(nextNode == head){
				throw new IllegalStateException();
			}
			/*
			 * remove(nextRet)
			 * don't remember how to use outer class' methods
			 * compiler doesn't like it
			 */
			if(isEmpty()){
				throw new NoSuchElementException();
			}
			LinearNode<T> current = (LinearNode<T>) head;
			while(current != null && !current.getElement().equals(nextRet)){
				current = current.getNext();
			}
			if(current == null){
				throw new NoSuchElementException();
			}
			T retVal = current.getElement();
			if(current == head){
				removeFirst();
				iterModCount++;
			}else if(current == tail){
				removeLast();
				iterModCount++;
			}else{
				LinearNode<T> prevNode = (LinearNode<T>) head;
				while(prevNode.getNext() != current){
					prevNode = prevNode.getNext();
				}
				prevNode.setNext(current.getNext());
				size--;
				modCount++;
				iterModCount++;
			}
			
			calledNext = false;
			calledRemove = true;
		}

	}

}
