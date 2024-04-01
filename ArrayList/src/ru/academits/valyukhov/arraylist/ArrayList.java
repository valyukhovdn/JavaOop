package ru.academits.valyukhov.arraylist;

import java.util.*;

public class ArrayList<E> implements List<E> {
    private E[] items;
    private int size;
    private int modCount;

    public ArrayList() {
        final int capacity = 10;
        //noinspection unchecked
        items = (E[]) new Object[capacity];
    }

    public ArrayList(int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("В конструктор передано отрицательное значение аргумента, задающего "
                    + "начальный размер списка!");
        }
        //noinspection unchecked
        items = (E[]) new Object[initialCapacity];
    }

    @Override
    public String toString() {
        if (size == 0) {
            return "[]";
        }

        StringBuilder sb = new StringBuilder();

        sb.append('[');

        for (int i = 0; i < size; ++i) {
            sb.append(items[i]).append(", ");
        }

        sb.delete(sb.length() - 2, sb.length());

        sb.append(']');

        return sb.toString();
    }

    public String toStringFullCapacity() {          // Для отладки.
        if (items.length == 0) {
            return "[]";
        }

        StringBuilder sb = new StringBuilder();

        sb.append('[');

        for (E item : items) {
            sb.append(item).append(", ");
        }

        sb.delete(sb.length() - 2, sb.length());

        sb.append(']');

        return sb.toString();
    }

    public void ensureCapacity(int capacity) {
        if (items.length >= capacity) {
            return;
        }

        items = Arrays.copyOf(items, capacity);
    }

    private void increaseCapacity() {
        if (items.length == 0) {
            items = Arrays.copyOf(items, 10);
        }

        items = Arrays.copyOf(items, items.length * 2);
    }

    public void trimToSize() {
        if (items.length == 0) {
            return;
        }

        E[] arrayForTrim;
        arrayForTrim = Arrays.copyOf(items, size);
        items = arrayForTrim;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new ArrayListIterator();
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(items, size);
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (size > a.length) {
            //noinspection unchecked
            return (T[]) Arrays.copyOf(items, size, a.getClass());
        }

        //noinspection SuspiciousSystemArraycopy
        System.arraycopy(items, 0, a, 0, size);

        if (size < a.length) {
            a[size] = null;
        }

        return a;
    }

    @Override
    public boolean add(E element) {
        add(size, element);

        return true;
    }

    @Override
    public boolean remove(Object o) {
        int index = indexOf(o);

        if (index == -1) {
            return false;
        }

        remove(index);
        return true;
    }

    private class ArrayListIterator implements Iterator<E> {
        private int currentIndex = -1;
        private int initialModCountValue = modCount;

        @Override
        public boolean hasNext() {
            return currentIndex + 1 < size;
        }

        @Override
        public E next() {
            if (initialModCountValue != modCount) {
                throw new ConcurrentModificationException("За время обхода Итератором списка количество его элементов "
                        + "могло измениться!");
            }

            if (!hasNext()) {
                throw new NoSuchElementException("Итератор дошёл до конца списка. Следующего элемента нет!");
            }

            ++currentIndex;
            return items[currentIndex];
        }
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        if (c.isEmpty()) {
            throw new IllegalArgumentException("Попытка передать в метод \"containsAll(Collection<?> c)\" пустую коллекцию!");
        }

        Object[] cArray = c.toArray();

        for (Object o : cArray) {
            if (!contains(o)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return addAll(size, c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Попытка вставить коллекцию по индексу \"" + index + "\". "
                    + "Допустимые значения от \"0\" до \"" + size + "\".");
        }

        if (c.isEmpty()) {
            return false;
        }

        int newSize = size + c.size();

        if (items.length < newSize) {
            ensureCapacity(newSize);
        }

        System.arraycopy(items, index, items, index + c.size(), size - index);

        Object[] cArray = c.toArray();

        //noinspection SuspiciousSystemArraycopy
        System.arraycopy(cArray, 0, items, index, c.size());

        size = newSize;
        ++modCount;

        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        if (size == 0 || c.isEmpty()) {
            return false;
        }

        boolean isThisListChanged = false;

        for (int i = 0; i < size; ++i) {
            if (c.contains(items[i])) {
                remove(i);
                --i;
                isThisListChanged = true;
            }
        }

        return isThisListChanged;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        if (size == 0 || c.isEmpty()) {
            return false;
        }

        boolean isThisListChanged = false;

        for (int i = 0; i < size; ++i) {
            if (!c.contains(items[i])) {
                remove(i);
                --i;
                isThisListChanged = true;
            }
        }

        return isThisListChanged;
    }

    @Override
    public void clear() {
        if (size == 0) {
            return;
        }

        for (Object o : this) {
            o = null;
        }

        size = 0;
        ++modCount;
    }

    // Вспомогательный метод проверки индекса на допустимые значения.
    private void checkIndex(int index) {
        if (size == 0) {
            throw new IndexOutOfBoundsException("Попытка обратиться к элементу по индексу \"" + index + "\" в пустом списке!");
        }

        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Попытка обратиться к элементу по несуществующему индексу \"" + index + "\". "
                    + "В списке элементы с индексами от \"0\" до \"" + (size - 1) + "\".");
        }
    }

    @Override
    public E get(int index) {
        checkIndex(index);

        return items[index];
    }

    @Override
    public E set(int index, E element) {
        checkIndex(index);

        E oldElement = items[index];

        items[index] = element;

        return oldElement;
    }

    @Override
    public void add(int index, E element) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Попытка вставить элемент по индексу \"" + index + "\". "
                    + "Допустимые значения от \"0\" до \"" + size + "\".");
        }

        if (size == items.length) {
            increaseCapacity();
        }

        System.arraycopy(items, index, items, index + 1, size - index);
        items[index] = element;

        ++size;
        ++modCount;
    }

    @Override
    public E remove(int index) {
        checkIndex(index);

        E removedElement = items[index];

        System.arraycopy(items, index + 1, items, index, size - index - 1);
        items[size - 1] = null;

        --size;
        ++modCount;

        return removedElement;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < size; ++i) {
            if (items[i] == null) {
                if (o == null) {
                    return i;
                }

                continue;
            }

            if (items[i].equals(o)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i = size - 1; i >= 0; --i) {
            if (items[i] == null) {
                if (o == null) {
                    return i;
                }

                continue;
            }

            if (items[i].equals(o)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public ListIterator<E> listIterator() {                 // Не реализовывать.
        return null;
    }

    @Override
    public ListIterator<E> listIterator(int index) {        // Не реализовывать.
        return null;
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {    // Не реализовывать.
        return null;
    }
}
