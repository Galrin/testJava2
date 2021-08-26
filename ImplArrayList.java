public class ImplArrayList<E> implements CustomList<E> {
		public static final int STACK_SIZE = 16; // стак не в смысле стек вызовов, но в смысле множество элементов(копна)

 	 	private Object[] array;
 	 	private int size = 0; // первичная инициализация

 	 	{
 	 		array = new Object[STACK_SIZE];
 	 	}

 	 	private void reorder() { // меняет порядок элементов (сортирует) так чтобы null не попадался вначале или середине массива
 	 		Object[] oldarray = array.clone();

			array = new Object[array.length]; // пересоздаём массив (он заполняется null по умолчанию)

 	 		for(int i = 0, j = 0; i < array.length; i++) {
 	 			if(oldarray[i] != null) array[j++] = oldarray[i];
 	 		}
 	 	}

		@Override
		public void clear() { // возвращаем массив в исходное состояние
			array = new Object[STACK_SIZE];
		}

		@Override
		public int size() {
			return size;
		}

		@Override
		public void add(E e) {

			try { // поскольку массив сортирован, можно добавлять сразу после последнего добавленного элемента
				array[size] = e;
				size++;
			}
			catch (IndexOutOfBoundsException ex) { // выход за границы массива, значит массив полон. выделяем массиву больше места
				Object[] oldarray = array.clone();
				array = new Object[array.length + STACK_SIZE];
    			System.arraycopy(oldarray, 0, array, 0, oldarray.length);	
				array[size] = e;
				size++;
			}
		}

		@Override
		public E get(int index) {
			return (E) array[index];
		}

		@Override
		public void remove(int index) {
			array[index] = null;
			-- size;

			// небольшой хак, если мы удаляем элемент с самого края, то массиву ненужна пересортировка
			if(size != index) reorder();
		}

		@Override
		public void remove(E e) {
			for (int i = 0; i < array.length; i++) {
				if(array[i] == null) continue;

				if(array[i].equals(e)) {
					array[i] = null;
					-- size;
					break;
				}
			}
			reorder();
		}

//		@Override
		public void removeAll(E e) {
			for ( int i = 0; i < array.length; i++) {
				if(array[i] == null) continue;
				
				if(array[i].equals(e)) {
					array[i] = null;
					-- size;
				}
			}
			reorder();
		}

		@Override
		public int indexOf(E e) {
			for ( int i = 0; i < array.length; i++) {
				if(array[i] == null) continue;
				
				if(array[i].equals(e)) {
					return i;
				}
			}
			return -1;
		}

		@Override
		public void set(int index, E e) {
			array[index] = e;
		}

		@Override
		public void add(int index, E e) {
			if(array[index] == null) {
				set(index, e);
				return;
			}
			

			if( (size + 1) > array.length ) { // у нас выход за рамки выделенной области
				
				Object[] oldarray = array.clone();
				array = new Object[array.length + STACK_SIZE];
	    		System.arraycopy(oldarray, 0, array, 0, oldarray.length);	
			}



			Object[] slice = java.util.Arrays.copyOfRange(array, index, array.length);
			System.arraycopy(slice, 0, array, index+1, slice.length-1);

			array[index] = e;
			size++;
		}

}