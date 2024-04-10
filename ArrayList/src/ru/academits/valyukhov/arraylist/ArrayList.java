package ru.academits.valyukhov.arraylist;

import java.util.*;

public class ArrayList<E> implements List<E> {
    private E[] items;
    private static final int capacity = 10;
    private int size;
    private int modCount;

    public ArrayList() {
        //noinspection unchecked
        items = (E[]) new Object[capacity];
    }

    public ArrayList(int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("В конструктор передано отрицательное значение аргумента (" + initialCapacity + "), "
                    + "задающего начальный размер списка!");
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

    public void ensureCapacity(int ensuredCapacity) {
        if (items.length < ensuredCapacity) {
            items = Arrays.copyOf(items, ensuredCapacity);
        }
    }

    private void increaseCapacity() {
        if (items.length == 0) {
            //noinspection unchecked
            items = (E[]) new Objects[capacity];
        } else {
            items = Arrays.copyOf(items, items.length * 2);
        }
    }

    public void trimToSize() {
        if (items.length > size) {
            items = Arrays.copyOf(items, size);
        }
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
    public boolean add(E item) {
        add(size, item);

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
        private final int initialModCountValue = modCount;

        @Override
        public boolean hasNext() {
            return currentIndex + 1 < size;
        }

        @Override
        public E next() {
            if (initialModCountValue != modCount) {
                throw new ConcurrentModificationException("За время обхода Итератором список изменился!");
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
            return true;
        }

        for (Object o : c) {
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

        size += c.size();

        ensureCapacity(size);

        System.arraycopy(items, index, items, index + c.size(), size - index - c.size());

        for (E o : c) {
            set(index, o);
            ++index;
        }

        ++modCount;

        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        if (size == 0 || c.isEmpty()) {
            return false;
        }

        boolean isChanged = false;

        for (int i = size - 1; i >= 0; --i) {
            if (c.contains(items[i])) {
                remove(i);
                isChanged = true;
            }
        }

        return isChanged;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        if (size == 0) {
            return false;
        }

        if (c.isEmpty()) {
            for (int i = 0; i < size; ++i) {
                items[i] = null;
            }

            size = 0;
            return true;
        }

        boolean isChanged = false;

        for (int i = size - 1; i >= 0; --i) {
            if (!c.contains(items[i])) {
                remove(i);
                isChanged = true;
            }
        }

        return isChanged;
    }

    @Override
    public void clear() {
        if (size == 0) {
            return;
        }

        for (int i = 0; i < size; ++i) {
            items[i] = null;
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
    public E set(int index, E item) {
        checkIndex(index);

        E oldItem = items[index];
        items[index] = item;

        ++modCount;

        return oldItem;
    }

    @Override
    public void add(int index, E item) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Попытка вставить элемент по индексу \"" + index + "\". "
                    + "Допустимые значения от \"0\" до \"" + size + "\".");
        }

        if (size == items.length) {
            increaseCapacity();
        }

        System.arraycopy(items, index, items, index + 1, size - index);
        items[index] = item;

        ++size;
        ++modCount;
    }

    @Override
    public E remove(int index) {
        checkIndex(index);

        E removedItem = items[index];

        System.arraycopy(items, index + 1, items, index, size - index - 1);
        items[size - 1] = null;

        --size;
        ++modCount;

        return removedItem;
    }

    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < size; ++i) {
            if (Objects.equals(items[i], o)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i = size - 1; i >= 0; --i) {
            if (Objects.equals(items[i], o)) {
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
