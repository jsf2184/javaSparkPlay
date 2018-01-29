package com.jsf2184.collections;

public class Node<D> {
    private Node<D> _next;
    private D _data;

    Node(D data, Node<D> next) {
        _data = data;
        _next = next;
    }

    public D getData() {
        return _data;
    }

    public Node<D> getNext() {
        return _next;
    }

    void setNext(Node<D> next) {
        _next = next;
    }
}
