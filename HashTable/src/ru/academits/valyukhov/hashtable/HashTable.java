package ru.academits.valyukhov.hashtable;

import java.util.*;

public class HashTable<E> implements Collection<E> {
    private static final int DEFAULT_LISTS_COUNT = 5;

    private final ArrayList<E>[] lists;
    private int size;
    private int modCount;

    public HashTable() {
        //noinspection unchecked
        lists = new ArrayList[DEFAULT_LISTS_COUNT];
    }

    public HashTable(int listsCount) {
        if (listsCount < 1) {
            throw new IllegalArgumentException("В конструктор \"HashTable(int listsCount)\" передано значение "
                    + "аргумента (" + listsCount + "). Минимально допустимое значение: 1.");
        }

        //noinspection unchecked
        lists = new ArrayList[listsCount];
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append('[').append(System.lineSeparator());

        for (ArrayList<E> list : lists) {
            sb.append(list).append(System.lineSeparator());
        }

        sb.append(']');

        return sb.toString();
    }

    private int getListIndex(Object o) {
        return Math.abs(Objects.hashCode(o) % lists.length);
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

        int listIndex = getListIndex(o);

        return lists[listIndex] != null && lists[listIndex].contains(o);
    }

    public class HashTableIterator implements Iterator<E> {
        private int visitedItemsCount;
        private int currentListIndex;
        private int currentItemIndex = -1;
        private final int initialModCount = modCount;

        @Override
        public boolean hasNext() {
            return visitedItemsCount < size;
        }

        @Override
        public E next() {
            if (initialModCount != modCount) {
                throw new ConcurrentModificationException("За время обхода Итератором Хэш-таблица изменилась!");
            }

            if (!hasNext()) {
                throw new NoSuchElementException("Итератор дошёл до конца списка. Следующего элемента нет!");
            }

            while (lists[currentListIndex] == null || currentItemIndex >= lists[currentListIndex].size() - 1) {
                ++currentListIndex;
                currentItemIndex = -1;
            }

            ++currentItemIndex;
            ++visitedItemsCount;

            return lists[currentListIndex].get(currentItemIndex);
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

        for (E item : this) {
            //noinspection unchecked
            a[i] = (T) item;
            ++i;
        }

        if (size < a.length) {
            a[size] = null;
        }

        return a;
    }

    @Override
    public boolean add(E item) {
        int listIndex = getListIndex(item);

        if (lists[listIndex] == null) {
            lists[listIndex] = new ArrayList<>();
        }

        lists[listIndex].add(item);

        ++size;
        ++modCount;

        return true;
    }

    @Override
    public boolean remove(Object o) {
        int listIndex = getListIndex(o);

        if (lists[listIndex] != null && lists[listIndex].remove(o)) {
            --size;
            ++modCount;
            return true;
        }

        return false;
    }

    private static void isCollectionNull(Collection<?> collection) {
        if (collection == null) {
            throw new NullPointerException("Вместо коллекции в метод передан \"null\".");
        }
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        isCollectionNull(c);

        for (Object o : c) {
            if (!contains(o)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        isCollectionNull(c);

        if (c.isEmpty()) {
            return false;
        }

        for (E e : c) {
            add(e);
        }

        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        isCollectionNull(c);

        if (size == 0 || c.isEmpty()) {
            return false;
        }

        boolean isChanged = false;

        for (ArrayList<E> list : lists) {
            if (list != null && !list.isEmpty()) {
                int initialListSize = list.size();

                if (list.removeAll(c)) {
                    isChanged = true;
                    size -= initialListSize - list.size();
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
        isCollectionNull(c);

        if (size == 0) {
            return false;
        }

        if (c.isEmpty()) {
            clear();
            return true;
        }

        boolean isChanged = false;

        for (ArrayList<E> list : lists) {
            if (list != null && !list.isEmpty()) {
                int initialListSize = list.size();

                if (list.retainAll(c)) {
                    isChanged = true;
                    size -= initialListSize - list.size();
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

        for (ArrayList<E> list : lists) {
            if (list != null && !list.isEmpty()) {
                list.clear();
            }
        }

        size = 0;
        ++modCount;
    }
}
