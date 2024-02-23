package ru.academits.valyukhov.list;

import java.util.NoSuchElementException;

public class SinglyLinkedList<E> {
    private Node<E> head;
    private int size;

    // Получение размера списка из переменной size.
    public int getSize() {
        return size;
    }

    @Override
    public String toString() {
        if (size == 0) {
            return "Список пуст.";
        }

        Node<E> currentNode = head;

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('{');

        while (currentNode.hasNext()) {
            stringBuilder.append(currentNode.getData()).append(", ");
            currentNode = currentNode.getNext();
        }

        stringBuilder.append(currentNode.getData());
        stringBuilder.append('}');

        return stringBuilder.toString();
    }

    // Вспомогательный метод получения элемента по индексу.
    private Node<E> getElement(int index) {
        int currentIndex = 0;
        Node<E> element = head;

        while (currentIndex != index) {
            ++currentIndex;
            element = element.getNext();
        }

        return element;
    }

    // Вспомогательный метод проверки индекса на допустимые значения.
    private void checkIndex(int index) {
        if (size == 0) {
            throw new NoSuchElementException("Попытка метода обратиться к элементу по индексу в пустом списке.");
        }

        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(String.format("Попытка метода обратиться к элементу по несуществующему "
                    + "индексу \"%d\", а в списке элементы с индексами от \"0\" до \"%d\".", index, size - 1));
        }
    }

    // Получение значения первого элемента.
    public E getFirst() {
        if (size == 0) {
            throw new NoSuchElementException("Попытка получить первый элемент из пустого списка.");
        }

        return head.getData();
    }

    // Получение значения элемента по указанному индексу.
    public E get(int index) {
        checkIndex(index);

        return getElement(index).getData();
    }

    // Изменение значения элемента по указанному индексу (выдает старое значение).
    public E set(int index, E data) {
        checkIndex(index);

        E oldData = get(index);

        getElement(index).setData(data);

        return oldData;
    }

    // Удаление элемента по индексу (выдаёт значение элемента).
    public E deleteElementByIndex(int index) {
        checkIndex(index);

        if (index == 0) {                            // Удаление первого элемента.
            return deleteFirst();
        }

        // Удаление второго, или последующего элемента.
        E deletedElementData = getElement(index).getData();

        Node<E> previousElement = getElement(index - 1);
        previousElement.setNext(previousElement.getNext().getNext());
        --size;

        return deletedElementData;
    }

    // Вставка элемента в начало.
    public void insertFirst(E data) {
        head = new Node<>(data, head);
        ++size;
    }

    // Вставка элемента по индексу.
    public void insertElement(int index, E data) {
        if (size == 0 && index != 0) {
            throw new IndexOutOfBoundsException("Попытка вставить в пустой список элемент по индексу отличному от \"0\".");
        }

        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(String.format("Попытка вставить в список элемент по несуществующему "
                    + "индексу \"%d\", а в списке элементы с индексами от \"0\" до \"%d\".", index, size - 1));
        }

        // Вставка на место первого элемента списка.
        if (index == 0) {
            insertFirst(data);
            return;
        }

        // Вставка на место НЕ первого элемента списка.
        getElement(index - 1).setNext(new Node<>(data, getElement(index - 1).getNext()));
        ++size;
    }

    // Удаление элемента по значению (первое вхождение). Выдаёт true, если элемент был удалён.
    public boolean deleteElement(E data) {
        if (size == 0) {                 // Если список пуст.
            return false;
        }

        if (head.getData() == data) {    // Удаление первого элемента, если он содержит искомое значение.
            deleteFirst();
            return true;
        }

        Node<E> currentNode = head;

        while (currentNode.hasNext()) {
            if (currentNode.getNext().getData() == data) {
                currentNode.setNext(currentNode.getNext().getNext());
                --size;
                return true;
            }

            currentNode = currentNode.getNext();
        }

        return false;
    }

    // Удаление первого элемента. Выдаёт значение элемента.
    public E deleteFirst() {
        if (size == 0) {
            throw new NoSuchElementException("Попытка удалить первый элемент из пустого списка.");
        }

        E deletedData = head.getData();

        head = head.getNext();
        --size;

        return deletedData;
    }

    // Разворот списка за линейное время.
    public void reverse() {
        if (size <= 1) {
            return;
        }

        int index = 0;

        while (index < size / 2) {
            set(index, set(size - 1 - index, get(index)));
            ++index;
        }
    }

    // Копирование списка.
    public SinglyLinkedList<E> getListCopy() {
        SinglyLinkedList<E> listCopy = new SinglyLinkedList<>();

        if (size == 0) {
            return listCopy;
        }

        Node<E> currentElement = head;
        listCopy.insertFirst(head.getData());
        Node<E> duplicateElement = listCopy.head;

        while (currentElement.hasNext()) {
            currentElement = currentElement.getNext();
            duplicateElement.setNext(new Node<>(currentElement.getData()));
            duplicateElement = duplicateElement.getNext();
            ++listCopy.size;
        }

        return listCopy;
    }
}
