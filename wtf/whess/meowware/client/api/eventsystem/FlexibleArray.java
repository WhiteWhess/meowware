package wtf.whess.meowware.client.api.eventsystem;

import java.util.Iterator;

/**
 * @author DarkMagician6
 *
 * @since 2014-07-07
 */
public class FlexibleArray<T> implements Iterable<T> {
    private T[] elements;

    public FlexibleArray(T[] array) {
        this.elements = array;
    }

    public FlexibleArray() {
        this.elements = (T[]) new Object[0];
    }

    public void add(T t) {
        if (t != null) {
            Object[] array = new Object[this.size() + 1];
            int i2 = 0;
            while (i2 < array.length) {
                array[i2] = i2 < this.size() ? this.get(i2) : t;
                ++i2;
            }
            this.set((T[]) array);
        }
    }

    public void remove(T t) {
        if (this.contains(t)) {
            Object[] array = new Object[this.size() - 1];
            boolean b2 = true;
            int i2 = 0;
            while (i2 < this.size()) {
                if (b2 && this.get(i2).equals(t)) {
                    b2 = false;
                } else {
                    array[b2 ? i2 : i2 - 1] = this.get(i2);
                }
                ++i2;
            }
            this.set((T[]) array);
        }
    }

    public boolean contains(T t) {
        T[] TArray = this.array();
        int n = TArray.length;
        int n2 = 0;
        while (n2 < n) {
            T entry = TArray[n2];
            if (entry.equals(t)) {
                return true;
            }
            ++n2;
        }
        return false;
    }

    private void set(T[] array) {
        this.elements = array;
    }

    public void clear() {
        this.elements = (T[]) new Object[0];
    }

    public T get(int index) {
        return this.array()[index];
    }

    public int size() {
        return this.array().length;
    }

    public T[] array() {
        return this.elements;
    }

    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>(){
            private int index = 0;

            @Override
            public boolean hasNext() {
                return this.index < FlexibleArray.this.size() && FlexibleArray.this.get(this.index) != null;
            }

            @Override
            public T next() {
                return FlexibleArray.this.get(this.index++);
            }

            @Override
            public void remove() {
                FlexibleArray.this.remove(FlexibleArray.this.get(this.index));
            }
        };
    }
}