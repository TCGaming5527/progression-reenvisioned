package net.lixir.pre.common.util;

import org.jetbrains.annotations.NotNull;

import java.util.*;

public class EvictingList<T> implements List<T> {
    private final int maxSize;
    private final Deque<T> history;

    public EvictingList(T initialValue, int maxSize) {
        if (maxSize < 2)
            throw new IllegalArgumentException("Max size cannot be less than 2");
        this.maxSize = maxSize;
        this.history = new ArrayDeque<>(maxSize);
        this.history.add(initialValue);
    }

    public EvictingList(int maxSize) {
        if (maxSize < 2)
            throw new IllegalArgumentException("Max size cannot be less than 2");
        this.maxSize = maxSize;
        this.history = new ArrayDeque<>(maxSize);
    }

    @Override
    public boolean add(T value) {
        if (history.size() == maxSize) {
            history.pollFirst();
        }
        history.addLast(value);
        return true;
    }

    public T getRecent(int past) {
        if (past < 0)
            throw new IllegalArgumentException("Past index cannot be negative");
        int index = Math.max(history.size() - 1 - past, 0);
        return new ArrayList<>(history).get(index);
    }

    public List<T> getAll() {
        return List.copyOf(history);
    }

    @Override
    public int size() {
        return history.size();
    }

    @Override
    public boolean isEmpty() {
        return history.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return history.contains(o);
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return history.iterator();
    }

    @NotNull
    @Override
    public Object @NotNull [] toArray() {
        return history.toArray();
    }

    @NotNull
    @Override
    public <T1> T1 @NotNull [] toArray(@NotNull T1 @NotNull [] a) {
        return history.toArray(a);
    }

    @Override
    public boolean remove(Object o) {
        return history.remove(o);
    }

    @Override
    public boolean containsAll(@NotNull Collection<?> c) {
        for (Object item : c) {
            if (!history.contains(item)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public T remove(int index) {
        return new ArrayList<>(history).remove(index);
    }

    @Override
    public boolean removeAll(@NotNull Collection<?> c) {
        return history.removeAll(c);
    }

    @Override
    public boolean retainAll(@NotNull Collection<?> c) {
        return history.retainAll(c);
    }

    @Override
    public void clear() {
        history.clear();
    }

    @Override
    public T get(int index) {
        return new ArrayList<>(history).get(index);
    }

    @Override
    public T set(int index, T element) {
        throw new UnsupportedOperationException("Setting elements by index is not supported.");
    }

    @Override
    public void add(int index, T element) {
        throw new UnsupportedOperationException("Adding elements at an index is not supported.");
    }

    @Override
    public boolean addAll(@NotNull Collection<? extends T> c) {
        for (T value : c) add(value);
        return true;
    }

    @Override
    public boolean addAll(int index, @NotNull Collection<? extends T> c) {
        throw new UnsupportedOperationException("AddAll at index is not supported.");
    }

    @Override
    public int indexOf(Object o) {
        return new ArrayList<>(history).indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return new ArrayList<>(history).lastIndexOf(o);
    }

    @NotNull
    @Override
    public ListIterator<T> listIterator() {
        return new ArrayList<>(history).listIterator();
    }

    @NotNull
    @Override
    public ListIterator<T> listIterator(int index) {
        return new ArrayList<>(history).listIterator(index);
    }

    @NotNull
    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return new ArrayList<>(history).subList(fromIndex, toIndex);
    }
}
