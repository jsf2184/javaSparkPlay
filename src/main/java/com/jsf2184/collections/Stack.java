package com.jsf2184.collections;

import java.util.Iterator;

public class Stack<D> implements Iterable<D> {
    private Node<D> _top;

    public Stack() {
        _top = new Node<>( null, null);
    }

    public D pop() {
        Node<D> node = _top.getNext();
        if (node != null) {
            _top = node;
            return node.getData();
        }
        return null;
    }

    public void push(D data) {
        _top.setNext(new Node<>(data, _top.getNext()));
    }

    public Iterator<D> iterator() {
        return new Iterator<D>() {

            Node<D> _prev = null;
            Node<D> _current = _top;
            public boolean hasNext() {
                return _current.getNext() != null;
            }

            public D next() {
                if (_current.getNext() != null) {
                    D res = _current.getNext().getData();
                    _prev = _current;
                    _current = _current.getNext();
                    return res;
                }
                return null;
            }

            public void remove() {
                if (_prev == null) {
                    throw new IllegalStateException("next() has not been called yet");
                }
                _prev.setNext(_current.getNext());

            }
        };
    }
}
