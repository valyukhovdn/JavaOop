package ru.academits.valyukhov.list;

public class SinglyLinkedList {

    public Node<Integer> head;
    private int size;

    public SinglyLinkedList() {
    }

    // Получение размера списка из переменной size.
    public int getSize() {
        return size;
    }

    // Получение размера списка из путём перебора его узлов.
    public int getSizeByIteratingNodes() {
        if (head == null) {
            return 0;
        }

        int currentIndex = 1;
        Node<Integer> currentNode = head;

        while (currentNode.hasNext()) {
            ++currentIndex;
            currentNode = currentNode.getNext();
        }

        return currentIndex;
    }

    // Получение значения первого элемента.
    public int getFistNodeData() {
        if (head == null) {
            throw new NullPointerException("Попытка получить первый элемент из пустого списка.");
        }

        return head.getData();
    }

    // Получение значения по указанному индексу.
    public int getDataByIndex(int index) {
        if (size == 0) {
            throw new IllegalArgumentException("Попытка получить значение узла по индексу в пустом списке.");
        }

        if (index < 0 || size - 1 < index) {
            throw new IndexOutOfBoundsException(String.format("Попытка получить значение узла по несуществующему индексу. "
                    + "В списке %d элементов, а в метод \"getDataByIndex\" передан индекс %d.", getSize(), index));
        }

        int currentIndex = 0;
        Node<Integer> currentNode = head;

        while (currentIndex != index) {
            ++currentIndex;
            currentNode = currentNode.getNext();
        }

        return currentNode.getData();
    }

    // Изменение значения по указанному индексу (выдает старое значение).
    public int setDataByIndex(int index, int data) {
        if (size == 0) {
            throw new IllegalArgumentException("Попытка изменить значение узла по индексу в пустом списке.");
        }

        if (index < 0 || size - 1 < index) {
            throw new IndexOutOfBoundsException(String.format("Попытка изменить значение узла по несуществующему индексу. "
                    + "В списке %d элементов, а в метод \"setDataByIndex\" передан индекс %d.", getSize(), index));
        }

        int currentIndex = 0;
        Node<Integer> currentNode = head;

        while (currentIndex != index) {
            ++currentIndex;
            currentNode = currentNode.getNext();
        }

        int previousData = currentNode.getData();
        currentNode.setData(data);

        return previousData;
    }

    // Удаление элемента по индексу (выдаёт значение элемента).
    public int deleteNodeByIndex(int index) {
        if (size == 0) {
            throw new IllegalArgumentException("Попытка удалить узел по индексу в пустом списке.");
        }
        if (index < 0 || size - 1 < index) {
            throw new IndexOutOfBoundsException(String.format("Попытка удалить узел по несуществующему индексу. "
                    + "В списке %d элементов, а в метод \"deleteNodeByIndex\" передан индекс %d.", getSize(), index));
        }

        if (index == 0) {                            // Удаление первого узла.
            return deleteFirstNode();
        } else {                                     // Удаление второго, или последующего узла.
            int previousIndex = 0;
            Node<Integer> previousNode = head;

            while (previousIndex < index - 1) {
                ++previousIndex;
                previousNode = previousNode.getNext();
            }

            int deletedNodeData = previousNode.getNext().getData();
            previousNode.setNext(previousNode.getNext().getNext());
            --size;

            return deletedNodeData;
        }
    }

    // Вставка элемента в начало.
    public void insertFirstNode(Node<Integer> node) {
        if (head == null) {          // Вставка узла в пустой список.
            head = new Node<>(node.getData());
        } else {                     // Вставка узла в начало НЕ пустого списка.
            head = new Node<>(node.getData(), head);
        }

        ++size;
    }

    // Вставка элемента по индексу.
    public void insertNodeByIndex(int index, Node<Integer> node) {
        if (size == 0 && index != 0) {
            throw new IndexOutOfBoundsException("Попытка вставить в пустой список узел по индексу отличному от \"0\".");
        }

        if (index < 0 || size < index) {
            throw new IndexOutOfBoundsException(String.format("Попытка вставить узел по несуществующему индексу. "
                    + "В список можно вставить узлы с индексами от \"0\" до \"%d\", а в метод \"insertNodeByIndex\" "
                    + "передан индекс %d.", getSize(), index));     // Можно index == size, если вставляем узел в конец списка.
        }

        if (size == 0) {                       // Вставка узла по индексу "0" в пустой список.
            head = new Node<>(node.getData());
        } else if (index == 0) {               // Вставка узла по индексу "0" в НЕ пустой список.
            head = new Node<>(node.getData(), head);
        } else {                               // Вставка узла по индексу >0 в НЕ пустой список.
            int previousIndex = 0;
            Node<Integer> previousNode = head;

            while (previousIndex < index - 1) {
                ++previousIndex;
                previousNode = previousNode.getNext();
            }

            if (index < size) {
                previousNode.setNext(new Node<>(node.getData(), previousNode.getNext()));
            } else {
                previousNode.setNext(new Node<>(node.getData(), null));
            }
        }

        ++size;
    }

    // Удаление узла по значению. Выдаёт true, если элемент был удалён.
    public boolean deleteNodeByData(int data) {
        if (head == null) {             // Если список пуст.
            return false;
        }

        Node<Integer> currentNode = head;

        for (int i = 0; i < size; ++i) {
            if (currentNode.getData() == data) {
                deleteNodeByIndex(i);
                return true;
            }

            currentNode = currentNode.getNext();
        }

        return false;
    }

    // Удаление первого элемента. Выдаёт значение элемента.
    public int deleteFirstNode() {
        if (size == 0) {
            throw new IllegalArgumentException("Попытка удалить первый узел в пустом списке.");
        }

        int deletedNodeData = head.getData();

        if (size == 1) {                            // Удаление единственного узла списка.
            head = null;
        } else {                                    // Удаление первого узла из списка, содержащего более 1 узла.
            head = head.getNext();
        }

        --size;

        return deletedNodeData;
    }

    // Разворот списка за линейное время.
    public void reverseList() {
        if (size == 0 || size == 1) {
            return;
        }

        SinglyLinkedList temporaryList = new SinglyLinkedList();
        Node<Integer> currentNode = head;

        while (currentNode.hasNext()) {
            temporaryList.insertFirstNode(new Node<>(currentNode.getData()));
            currentNode = currentNode.getNext();
        }

        temporaryList.insertFirstNode(new Node<>(currentNode.getData()));

        this.head = temporaryList.head;
    }

    // Вспомогательный метод добавления узла в конец списка (копированием значения узла, переданного в метод, во вновь
    // созданный последний узел).
    public static void addNode(SinglyLinkedList list, Node<Integer> node) {
        if (list.getSize() == 0) {
            list.head = new Node<>(node.getData());
        } else {
            Node<Integer> currentNode = list.head;

            while (currentNode.hasNext()) {
                currentNode = currentNode.getNext();
            }

            currentNode.setNext(new Node<>(node.getData()));
        }

        ++list.size;
    }

    // Копирование списка.
    public SinglyLinkedList getListDuplicate() {
        SinglyLinkedList listDuplicate = new SinglyLinkedList();

        Node<Integer> currentNode = head;

        while (currentNode.hasNext()) {
            addNode(listDuplicate, currentNode);
            currentNode = currentNode.getNext();
        }

        addNode(listDuplicate, currentNode);

        return listDuplicate;
    }
}
