package snapshot;

import java.util.*;

public  class SynchronizedUnbalancedSnapshotList <E>  implements SnapshotList<E> {

    private volatile int size = 0;
    private volatile int currentVersion = 0;

    /** Pointer to first node. */
    private Node<E> first;

    /**  Pointer to last node. */
    private Node<E> last;


    @Override
    public synchronized void dropPriorSnapshots(int version) {

        int nodes = 0;

        Node requiredElement = first;
        while (requiredElement.next != null){

            if (requiredElement.version == version){

                first = requiredElement;
                size = size -nodes;
                break;
            }
            nodes ++;
            requiredElement = requiredElement.next;
        }
    }

    @Override
    public synchronized E getAtVersion(int index, int version) {

        Node requiredElement = first;
        while (requiredElement.next != null){

            if (requiredElement.version == version){

                if (index-- == 0){
                    return (E)requiredElement.item;
                }
            }

            requiredElement = requiredElement.next;
        }

        return null;
    }

    @Override
    public synchronized int snapshot() {
        currentVersion++;

        Node currentNode = first;
        while (currentNode.next != null){

            currentNode = currentNode.next;
        }
        currentNode.version = currentVersion;
        return currentVersion;
    }

    @Override
    public int version() {
        return currentVersion;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {

        Object[] result = new Object[size];
        int i = 0;
        for (Node<E> x = first; x != null; x = x.next)
            result[i++] = x.item;
        return result;

    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public synchronized boolean add(E e) {

        final Node<E> l = last;
        final Node<E> newNode = new Node<>(l, e, null);
        last = newNode;
        if (l == null)
            first = newNode;
        else
            l.next = newNode;
        size++;

        return true;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public E get(int index) {
        return null;
    }

    @Override
    public E set(int index, E element) {
        return null;
    }

    @Override
    public void add(int index, E element) {

    }

    @Override
    public E remove(int index) {
        return null;
    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    @Override
    public ListIterator<E> listIterator() {
        return null;
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return null;
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return null;
    }



    private class Node<E> {
        E item;
        Node<E> next;
        Node<E> prev;
        int version = currentVersion;

        Node(Node<E> prev, E element, Node<E> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    Node<E> node(int index) {

        if (index < (size >> 1)) {
            Node<E> x = first;
            for (int i = 0; i < index; i++)
                x = x.next;
            return x;
        } else {
            Node<E> x = last;
            for (int i = size - 1; i > index; i--)
                x = x.prev;
            return x;
        }
    }

}
