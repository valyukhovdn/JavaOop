package ru.academits.valyukhov.hashtable;

import java.util.*;

public class HashTable<E> implements Collection<E> {
    private int defaultHashCodesCount = 5;
    ArrayList<ArrayList<E>> hashTable;
    private int size;
    private int modCount;

    public HashTable() {
        hashTable = new ArrayList<>(defaultHashCodesCount);

        for (int i = 0; i < defaultHashCodesCount; ++i) {
            hashTable.add(null);
        }
    }

    public HashTable(int hashCodesCount) {
        defaultHashCodesCount = hashCodesCount;
        hashTable = new ArrayList<>(defaultHashCodesCount);

        for (int i = 0; i < defaultHashCodesCount; ++i) {
            hashTable.add(null);
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("[\n");

        for (ArrayList<E> list : hashTable) {
            sb.append(list).append("\n");
        }

        sb.append("[");

        return sb.toString();
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

        if (hashTable.get(Objects.hashCode(o) % defaultHashCodesCount) == null) {
            return false;
        }

        return hashTable.get(Objects.hashCode(o) % defaultHashCodesCount).contains(o);
    }

    public class HashTableIterator implements Iterator<E> {
        private int itemsCounter = -1;
        private int currentHashCodIndex = 0;
        private int currentListItemsIndex = -1;
        private final int initialModCountValue = modCount;

        @Override
        public boolean hasNext() {
            return itemsCounter + 1 < size;
        }

        @Override
        public E next() {
            if (initialModCountValue != modCount) {
                throw new ConcurrentModificationException("За время обхода Итератором Хэш-таблица изменилась!");
            }

            if (itemsCounter + 1 > size) {
                throw new NoSuchElementException("Итератор дошёл до конца списка. Следующего элемента нет!");
            }

            while (hashTable.get(currentHashCodIndex) == null || currentListItemsIndex >= hashTable.get(currentHashCodIndex).size() - 1) {
                ++currentHashCodIndex;
                currentListItemsIndex = -1;
            }

            ++currentListItemsIndex;
            ++itemsCounter;

            return hashTable.get(currentHashCodIndex).get(currentListItemsIndex);
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new HashTableIterator();
    }

    @Override
    public Object[] toArray() {
        Object[][] array = new Object[hashTable.size()][];

        for (int i = 0; i < hashTable.size(); ++i) {
            array[i] = hashTable.get(i).toArray();
        }

        return array;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (size == 0) {
            return a;
        }

        int insertIndex = 0;

        if (size > a.length) {
            //noinspection unchecked
            a = (T[]) new Objects[size];

            for (ArrayList<E> list : hashTable) {
                //noinspection ReassignedVariable,SuspiciousSystemArraycopy
                System.arraycopy(list.toArray(), 0, a, insertIndex, list.size());
                insertIndex += list.size();
            }

            return a;
        }

        for (ArrayList<E> list : hashTable) {
            //noinspection ReassignedVariable,SuspiciousSystemArraycopy
            System.arraycopy(list.toArray(), 0, a, insertIndex, list.size());
            insertIndex += list.size();
        }

        if (size < a.length) {
            a[insertIndex] = null;
        }

        return a;
    }

    @Override
    public boolean add(E e) {
        if (hashTable.get(Objects.hashCode(e) % defaultHashCodesCount) == null) {
            hashTable.set(Objects.hashCode(e) % defaultHashCodesCount, new ArrayList<>());
        }

        hashTable.get(Objects.hashCode(e) % defaultHashCodesCount).add(e);

        ++size;
        ++modCount;

        return true;
    }

    @Override
    public boolean remove(Object o) {
        if (size == 0) {
            return false;
        }

        if (hashTable.get(Objects.hashCode(o) % defaultHashCodesCount).remove(o)) {
            --size;
            ++modCount;
            return true;
        }

        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        if (size == 0 || c.isEmpty()) {
            return false;
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
        if (size == 0 || c.isEmpty()) {
            return false;
        }

        boolean isHashTableChanged = false;

        for (ArrayList<E> list : hashTable) {
            for (int i = 0; i < list.size(); ++i) {
                if (c.contains(list.get(i))) {
                    list.remove(i);
                    --i;
                    isHashTableChanged = true;
                    --size;
                    ++modCount;
                }
            }
        }

        return isHashTableChanged;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        if (size == 0 || c.isEmpty()) {
            return false;
        }

        boolean isHashTableChanged = false;

        for (ArrayList<E> list : hashTable) {
            if (Objects.equals(list, null) || list.isEmpty()) {
                continue;
            }

            for (int i = 0; i < list.size(); ++i) {
                if (!c.contains(list.get(i))) {
                    list.remove(i);
                    --i;
                    isHashTableChanged = true;
                    --size;
                    ++modCount;
                }
            }
        }

        return isHashTableChanged;
    }

    @Override
    public void clear() {
        if (size == 0) {
            return;
        }

        for (ArrayList<E> hashCodeList : hashTable) {
            hashCodeList.clear();
        }

        size = 0;
        ++modCount;
    }
}
