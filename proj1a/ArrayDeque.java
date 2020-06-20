/*
    A class that implements deque of any type by arrays
    Author: Yuchen Liu
 */
public class ArrayDeque<T> {
    /*
        Constants
     */
    // for capacity >= 16, the usage factor (= _size / _capacity) should be at least MIN_USAGE
    static public final double MIN_USAGE = 0.25;
    static public final int MIN_SIZE_APPLICABLE = 16;
    static public final int INIT_CAPACITY = 8;
    static public final int INCR_FACTOR = Math.max(2, (int)Math.ceil(0.5 / MIN_USAGE)) ;

    /*
        Variables
     */
    private T[] _items;
    private int _size;
    private int _capacity = INIT_CAPACITY; // May change in the future. Always >= _size.

    // for the purpose of add/remove first/last
    // the element at _idxFirst/_idxLast is considered as the NEXT first/last element by the user
    // the array is full when _idxFirst + 1 = _idxLast
    // when querying the array, both indices need to be rescaled to be valid indices
    private int _idxFirst = _capacity - 1;
    private int _idxLast = 0;

    /*
        Constructors
     */
    public ArrayDeque() {
        _items = (T[]) new Object [_capacity]; // this is weird but maybe the only way ...
        _size = 0;
    }

    public ArrayDeque(T[] items) {
        int size = items.length;
        _size = size;
        // this makes sure our _capacity >= _size
        if (size > _capacity) {
            // N.B. the array is already full now
            _capacity = size;
        }
        _items = (T[]) new Object [_capacity];
        // copy all elements in items to _items at position 0 to size-1
        System.arraycopy(items, 0, _items, 0, size);
        // the above line is "equivalent" to call addLast() "size" times, therefore
        _idxFirst = _capacity - 1;
        _idxLast = size;
    }

    public ArrayDeque(ArrayDeque<T> other) {
        _items = (T[]) new Object [other.capacity()]; // so we make sure we wont overflow here
        _size = 0; // the addLast() below will increment _size anyway

        for (int i = 0; i < other.size(); i++) {
            addLast(other.get(i));
        }

    }

    /*
        Function Methods
     */

    // resize the array when its capacity is not enough or too much
    // the idea is to preserve the order of the old array and starts at position 0,
    // so that the new _idxFirst is always capacity - 1 and _idxLast is always _size
    private void _resize(int capacity) {
        T[] new_items = (T[]) new Object [capacity];

        if (_idxFirst + 1 <  _idxLast) {
            // here _idxFirst + 1 < _idxLast < _capacity
            // "consecutive" elements within the array
            System.arraycopy(_items, _idxFirst + 1, new_items, 0, _size);

        }
        else {
            // first copy [_idxFirst + 1, _capacity - 1] to [0, _capacity - _idxFirst - 2]
            System.arraycopy(_items,  _rescale(_idxFirst + 1), new_items, 0, _capacity - _rescale(_idxFirst + 1));
            // then copy [0, _idxLast - 1] after that
            System.arraycopy(_items, 0, new_items, _capacity - _rescale(_idxFirst + 1), _idxLast);

        }

        _items = new_items;
        _idxFirst = capacity - 1;
        _idxLast = _size;
        _capacity = capacity;

    }

    // rescale the index within [0, _capacity - 1]
    // it ensures _idxFirst and _idxLast can be safely used afterwards
    // N.B. it should be called when _idxFirst + k or _idxLast + k is indexed
    private int _rescale (int index) {
        index %= _capacity;
        if (index < 0) {
            index += _capacity;
        }
        return index;
    }

    public void addFirst(T item) {
        if (_size == _capacity) {
            _resize(_capacity * INCR_FACTOR);
        }

        _items[_idxFirst] = item;
        _size += 1;
        _idxFirst = _rescale(_idxFirst - 1);
    }

    public void addLast(T item) {
        if (_size == _capacity) {
            _resize(_capacity * INCR_FACTOR);
        }

        _items[_idxLast] = item;
        _size += 1;
        _idxLast = _rescale(_idxLast + 1);
    }

    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }

        _idxFirst = _rescale(_idxFirst + 1);
        T elem = _items[_idxFirst];
        _size -= 1;
        if (_capacity >= MIN_SIZE_APPLICABLE && usage() < MIN_USAGE) {
            _resize((int)Math.ceil((double)_capacity * (usage() + MIN_USAGE) ));
        }
        return elem;
    }

    public T removeLast() {
        if (isEmpty()) {
            return null;
        }

        _idxLast = _rescale(_idxLast - 1);
        T elem = _items[_idxLast];
        _size -= 1;
        if (_capacity >= MIN_SIZE_APPLICABLE && usage() < MIN_USAGE) {
            _resize((int)Math.ceil((double)_capacity * (usage() + MIN_USAGE) ));
        }
        return elem;
    }

    public void printDeque() {
        for (int i = 0; i < _size; i++) {
            System.out.print(get(i).toString());
            if (i < _size - 1) {
                System.out.print(", ");
            }
        }
        System.out.println();
    }

    // check if it's empty
    public boolean isEmpty() {

        return _size == 0;
    }

    // getters
    public T get(int index) {
        if (index < 0 || index >= _size) {
            return null;
        }
        // can use either _idxFirst or _idxLast
        return _items[_rescale(_idxFirst + index + 1)];
    }

    public int size() {

        return _size;
    }

    public int capacity() {

        return _capacity;
    }

    public double usage() {

        return (double)_size / (double)_capacity;
    }

}
