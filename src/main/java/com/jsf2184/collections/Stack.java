package com.jsf2184.collections;

public class Stack<D> {
    Node<D> _top;

    public Stack() {
        _top = null;
    }

    public D pop() {
        Node<D> res = _top;
        if (res != null) {
            _top = res.getPrev();
            return res.getData();
        }
        return null;
    }

    public void push(D data) {
        _top = new Node<D>(data, _top);
    }
}
