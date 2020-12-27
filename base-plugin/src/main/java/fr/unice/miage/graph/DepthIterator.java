package fr.unice.miage.graph;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class DepthIterator<T> implements Iterator<T> {
    private Iterator<T> nextNodeIterator = null;
    private final Iterator<Node<T>> root;
    private final Set<Node<T>> isVisited;
    private Node<T> current;

    public DepthIterator(Iterator<Node<T>> root) {
        this.root = root;
        current = null;
        isVisited = new HashSet<>();
        if(root.hasNext()) {
            while (current == null && root.hasNext())
                current = root.next();
            if(current == null)
                return;
            isVisited.add(current);
            List<Node<T>> array = current.getSub();
            if (!array.isEmpty())
                nextNodeIterator = new DepthIterator<>(array.iterator(), isVisited);
        }

    }

    public DepthIterator(Iterator<Node<T>> root, Set<Node<T>> isVisited) {
        this.root = root;
        current = null;
        this.isVisited = isVisited;
        if(root.hasNext()) {
            do {
                current = root.next();
            } while ((isVisited.contains(current) || current == null) && root.hasNext());
            if(isVisited.contains(current)|| current == null){
                current = null;
                return;
            }
            isVisited.add(current);
            List<Node<T>> array = current.getSub();
            if (!array.isEmpty())
                nextNodeIterator = new DepthIterator<>(array.iterator(), isVisited);
        }
    }

    @Override
    public boolean hasNext() {
        return current != null;
    }

    @Override
    public T next() {
        if(nextNodeIterator != null && nextNodeIterator.hasNext())
            return nextNodeIterator.next();
        T result = current.getValue();
        current = null;
        if(root.hasNext()) {
            do {
                current = root.next();
            } while ((isVisited.contains(current) || current == null) && root.hasNext());
            if(isVisited.contains(current) || current == null){
                current = null;
                return result;
            }
            isVisited.add(current);
            List<Node<T>> array = current.getSub();
            if (!array.isEmpty())
                nextNodeIterator = new DepthIterator<>(array.iterator(), isVisited);
        }
        return result;
    }
}
