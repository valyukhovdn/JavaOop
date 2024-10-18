package ru.academits.valyukhov.graph;

public class Vertex<E> {
    private E value;
    private boolean visited;

    public Vertex(E value) {
        this.value = value;
    }

    public E getValue() {
        return value;
    }

    public void setValue(E value) {
        this.value = value;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }
}