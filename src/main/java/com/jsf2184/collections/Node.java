package com.jsf2184.collections;

public class Node<D> {
    Node<D> _prev;
    D _data;

    public Node(D data, Node<D> prev) {
        _data = data;
        _prev = prev;
    }

    public D getData() {
        return _data;
    }

    public Node<D> getPrev() {
        return _prev;
    }
}
