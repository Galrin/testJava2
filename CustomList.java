public interface CustomList<E> {
	void add(E e);
	void add(int index, E e);
	void clear();
	E get(int index );
	int indexOf(E e);
	void remove(int index);
	void remove(E e);
	void set(int index, E e);
	int size();
}