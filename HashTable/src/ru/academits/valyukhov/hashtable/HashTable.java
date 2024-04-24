package ru.academits.valyukhov.hashtable;

import java.util.*;

public class HashTable<E> implements Collection<E> {
    private static final int DEFAULT_HASH_TABLE_ARRAY_SIZE = 5;

    private final ArrayList<E>[] hashTableArray;
    private int size;
    private int modCount;

    public HashTable() {
        //noinspection unchecked
        hashTableArray = new ArrayList[DEFAULT_HASH_TABLE_ARRAY_SIZE];
    }

    public HashTable(int hashTableArraySize) {
        //noinspection unchecked
        hashTableArray = new ArrayList[hashTableArraySize];
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append('[').append(System.lineSeparator());

        for (ArrayList<E> list : hashTableArray) {
            sb.append(list).append(System.lineSeparator());
        }

        sb.append(']');

        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || o.getClass() != getClass()) {
            return false;
        }

        @SuppressWarnings("unchecked")
        HashTable<E> hashTable = (HashTable<E>) o;

        if (size != hashTable.size) {
            return false;
        }

        for (int i = 0; i < hashTableArray.length; ++i) {
            if (!hashTableArray[i].equals(hashTable.hashTableArray[i])) {
                return false;
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 37;
        int hashCode = 1;

        hashCode = prime * hashCode + size;

        for (ArrayList<E> list : hashTableArray) {
            hashCode = prime * hashCode + (list != null ? list.hashCode() : 0);
        }

        return hashCode;
    }

    private int listIndex(Object o) {
        return Math.abs(Objects.hashCode(o) % hashTableArray.length);
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
        if (size == 0) {
            return false;
        }

        int listIndex = listIndex(o);

        return hashTableArray[listIndex] != null && hashTableArray[listIndex].contains(o);
    }

    public class HashTableIterator implements Iterator<E> {
        private int itemsVisitedAmount = -1;
        private int currentListIndex;
        private int currentItemIndex = -1;
        private final int initialModCount = modCount;

        @Override
        public boolean hasNext() {
            return itemsVisitedAmount + 1 < size;
        }

        @Override
        public E next() {
            if (initialModCount != modCount) {
                throw new ConcurrentModificationException("За время обхода Итератором Хэш-таблица изменилась!");
            }

            if (!hasNext()) {
                throw new NoSuchElementException("Итератор дошёл до конца списка. Следующего элемента нет!");
            }

            while (hashTableArray[currentListIndex] == null || currentItemIndex >= hashTableArray[currentListIndex].size() - 1) {
                ++currentListIndex;
                currentItemIndex = -1;
            }

            ++currentItemIndex;
            ++itemsVisitedAmount;

            return hashTableArray[currentListIndex].get(currentItemIndex);
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new HashTableIterator();
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[size];

        int i = 0;

        for (Object o : this) {
            array[i] = o;
            ++i;
        }

        return array;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (size > a.length) {
            //noinspection unchecked
            return (T[]) Arrays.copyOf(toArray(), size, a.getClass());
        }

        int i = 0;

        for (ArrayList<E> list : hashTableArray) {
            if (list != null && !list.isEmpty()) {
                for (E item : list) {
                    //noinspection unchecked
                    a[i] = (T) item;
                    ++i;
                }
            }
        }

        if (size > 0 && size < a.length) {
            a[i] = null;
        }

        return a;
    }

    @Override
    public boolean add(E item) {
        int currentListIndex = Objects.hashCode(item) % hashTableArray.length;

        if (hashTableArray[currentListIndex] == null) {
            hashTableArray[currentListIndex] = new ArrayList<>();
        }

        hashTableArray[currentListIndex].add(item);

        ++size;
        ++modCount;

        return true;
    }

    @Override
    public boolean remove(Object o) {
        int listIndex = listIndex(o);

        if (hashTableArray[listIndex] != null && hashTableArray[listIndex].remove(o)) {
            --size;
            ++modCount;
            return true;
        }

        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        if (c == null || c.isEmpty()) {
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
        if (c == null || c.isEmpty()) {
            return false;
        }

        for (E e : c) {
            add(e);
        }

        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        if (size == 0 || c == null || c.isEmpty()) {
            return false;
        }

        boolean isChanged = false;

        for (ArrayList<E> list : hashTableArray) {
            if (list != null && !list.isEmpty()) {
                int currentListSize = list.size();

                if (list.removeAll(c)) {
                    isChanged = true;
                    size -= currentListSize - list.size();
                }
            }
        }

        if (isChanged) {
            ++modCount;
        }

        return isChanged;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        if (size == 0 || c == null || c.isEmpty()) {
            return false;
        }

        boolean isChanged = false;

        for (ArrayList<E> list : hashTableArray) {
            if (list != null && !list.isEmpty()) {
                int currentListSize = list.size();

                if (list.retainAll(c)) {
                    isChanged = true;
                    size -= currentListSize - list.size();
                }
            }
        }

        if (isChanged) {
            ++modCount;
        }

        return isChanged;
    }

    @Override
    public void clear() {
        if (size == 0) {
            return;
        }

        boolean isChanged = false;

        for (ArrayList<E> list : hashTableArray) {
            if (list != null && !list.isEmpty()) {
                list.clear();
                isChanged = true;
            }
        }

        if (isChanged) {
            size = 0;
            ++modCount;
        }
    }
}
