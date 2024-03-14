package ru.academits.valyukhov.arraylist;

import java.util.*;
import java.util.function.UnaryOperator;

public class ArrayList<E> implements List<E> {
    int capacity;
    private E[] items;
    private int size;
    private int modCount;

    public ArrayList() {
        capacity = 10;
        //noinspection unchecked
        items = (E[]) new Object[capacity];
    }

    public ArrayList(int initialCapacity) {
        capacity = initialCapacity;
        //noinspection unchecked
        items = (E[]) new Object[capacity];
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append('[');

        for (E e : this) {
            sb.append(e).append(", ");
        }

        if (size > 0) {
            sb.delete(sb.length() - 2, sb.length());
        }

        sb.append(']');

        return sb.toString();
    }

    public String toStringFullCapacity() {          // Для отладки.
        StringBuilder sb = new StringBuilder();

        sb.append('[');

        for (E item : items) {
            sb.append(item).append(", ");
        }

        if (size > 0) {
            sb.delete(sb.length() - 2, sb.length());
        }

        sb.append(']');

        return sb.toString();
    }

    public void ensureCapacity(int ensuredCapacity) {
        if (items.length >= ensuredCapacity) {
            return;
        }

        //noinspection unchecked
        E[] newItemsArray = (E[]) new Object[ensuredCapacity];
        System.arraycopy(items, 0, newItemsArray, 0, size);
        items = newItemsArray;
    }

    private void increaseCapacity() {
        items = Arrays.copyOf(items, items.length * 2);
    }

    public void trimToSize() {
        @SuppressWarnings("unchecked")
        E[] arrayForTrim = (E[]) new Object[size];

        System.arraycopy(items, 0, arrayForTrim, 0, size);

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
        if (o == null) {
            throw new IllegalArgumentException("В метод \"contains(Object o)\" в качестве аргумента передан null");
        }

        if (size == 0) {
            return false;
        }

        for (E e : this) {
            if (e.equals(o)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new ArrayListIterator();
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(items, size);
    }

    @SuppressWarnings("SuspiciousSystemArraycopy")
    @Override
    public <T> T[] toArray(T[] a) {
        if (size > a.length) {
            //noinspection unchecked
            a = (T[]) new Object[size];
        }

        if (size < a.length) {
            for (int i = size; i < a.length; ++i) {
                a[i] = null;
            }
        }

        //noinspection ReassignedVariable
        System.arraycopy(items, 0, a, 0, size);

        return a;
    }

    @Override
    public boolean add(E e) {
        if (size >= items.length) {
            increaseCapacity();
        }

        items[size] = e;
        ++size;
        ++modCount;

        return true;
    }

    @Override
    public boolean remove(Object o) {
        int index = -1;

        for (int i = 0; i < size; ++i) {
            if (items[i].equals(o)) {
                index = i;
            }
        }

        if (index == -1) {
            return false;
        }

        System.arraycopy(items, index + 1, items, index, size - index - 1);
        items[size - 1] = null;
        --size;
        ++modCount;

        return true;
    }

    private class ArrayListIterator implements Iterator<E> {
        private int currentIndex = -1;
        private final int modCounterValue = ArrayList.this.modCount;

        @Override
        public boolean hasNext() {
            return (currentIndex + 1 < size);
        }

        @Override
        public E next() {
            if (modCounterValue != ArrayList.this.modCount) {
                throw new ConcurrentModificationException("За время обхода Итератором списка количество его элементов "
                        + "могло измениться!");
            }

            if (currentIndex >= size) {
                throw new NoSuchElementException("Итератор дошёл до конца списка. Следующего элемента нет!");
            }

            ++currentIndex;
            return items[currentIndex];
        }
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        Iterator<?> cIterator = c.iterator();
        E cCollectionElement;

        while (cIterator.hasNext()) {
            //noinspection unchecked
            cCollectionElement = (E) cIterator.next();
            boolean haveSuchElement = false;

            Iterator<E> thisIterator = this.iterator();
            E thisListElement;

            while (thisIterator.hasNext()) {
                thisListElement = thisIterator.next();

                if (cCollectionElement.equals(thisListElement)) {
                    haveSuchElement = true;
                    // След. строка комментария для теста.
                    // System.out.println(cCollectionElement + " - " + thisListElement + " haveSuchElement = " + haveSuchElement);
                    break;
                }

                // След. строка комментария для теста.
                // System.out.println(cCollectionElement + " - " + thisListElement + " haveSuchElement = " + haveSuchElement);
            }

            if (!haveSuchElement) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        final int modCounterValue = ArrayList.this.modCount;
        int newSize = size + c.size();
        @SuppressWarnings("unchecked")
        E[] newItems = (E[]) new Object[newSize];

        System.arraycopy(items, 0, newItems, 0, size);

        Iterator<? extends E> cCollectionIterator = c.iterator();

        for (int i = size; i < newSize; ++i) {
            newItems[i] = cCollectionIterator.next();
        }

        if (modCounterValue == ArrayList.this.modCount) {
            items = newItems;
            size = newSize;
            return true;
        }

        throw new ConcurrentModificationException("Во время выполнения метода \"addAll(Collection<? extends E> c)\" "
                + "исходный список возможно изменился. Метод не выполнен!");
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        checkIndex(index);

        final int modCounterValue = ArrayList.this.modCount;
        int newSize = size + c.size();
        @SuppressWarnings("unchecked")
        E[] newItems = (E[]) new Object[newSize];

        System.arraycopy(items, 0, newItems, 0, index);
        System.arraycopy(items, index, newItems, index + c.size(), size - index);

        Iterator<? extends E> cCollectionIterator = c.iterator();

        for (int i = index; i < index + c.size(); ++i) {
            newItems[i] = cCollectionIterator.next();
        }

        if (modCounterValue == ArrayList.this.modCount) {
            items = newItems;
            size = newSize;
            return true;
        }

        throw new ConcurrentModificationException("Во время выполнения метода \"addAll(int index, Collection<? extends E> c)\" "
                + "исходный список возможно изменился. Метод не выполнен!");
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        ArrayList<E> cItemsWithoutDuplicates = new ArrayList<>(c.size());

        // Создаём копию переданной коллекции без дубликатов.
        for (Object e : c) {
            //noinspection unchecked
            if (!cItemsWithoutDuplicates.contains((E) e)) {
                //noinspection unchecked
                cItemsWithoutDuplicates.add((E) e);
            }
        }

        for (int i = 0; i < size; ++i) {
            for (E e : cItemsWithoutDuplicates) {
                if (items[i] == e) {
                    remove(items[i]);
                    --i;
                }
            }
        }

        return true;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        ArrayList<E> cItemsWithoutDuplicates = new ArrayList<>(c.size());

        // Создаём копию переданной коллекции без дубликатов.
        for (Object e : c) {
            //noinspection unchecked
            if (!cItemsWithoutDuplicates.contains((E) e)) {
                //noinspection unchecked
                cItemsWithoutDuplicates.add((E) e);
            }
        }

        for (int i = 0; i < size; ++i) /* E e1 : this) */ {
            boolean elementShouldBeDelete = true;

            for (E e : cItemsWithoutDuplicates) {
                if (items[i] == e) {
                    elementShouldBeDelete = false;
                    break;
                }
            }

            if (elementShouldBeDelete) {
                remove(i);
                --i;
            }
        }

        return true;
    }

    @Override
    public void replaceAll(UnaryOperator<E> operator) {
        List.super.replaceAll(operator);
    }

    public void sort(Comparator<? super E> c) {
        List.super.sort(c);
    }

    @Override
    public void clear() {
        //noinspection unchecked
        items = (E[]) new Object[items.length];
        size = 0;
        ++modCount;
    }

    // Вспомогательный метод проверки индекса на допустимые значения.
    private void checkIndex(int index) {
        if (size == 0) {
            throw new IndexOutOfBoundsException("Попытка обратиться к элементу по индексу \"" + index + "\" в ПУСТОМ списке!");
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

        E deletedElement = items[index];

        items[index] = element;

        return deletedElement;
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
            if (items[i].equals(o)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i = size - 1; i >= 0; --i) {
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

    @Override
    public Spliterator<E> spliterator() {
        return List.super.spliterator();
    }
}
