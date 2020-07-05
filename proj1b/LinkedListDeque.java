public class LinkedListDeque<T> implements Deque<T>{

    private IntNode sentinel;
    private int size;

    public class IntNode {
        public T item;
        public IntNode prev;
        public IntNode next;

        public IntNode(T item, IntNode prev, IntNode next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }


    /**Create an empty linkedlistdeque.*/
    public LinkedListDeque() {
        sentinel = new IntNode(null,null,null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }

    /**Create a deep copy of other.*/
    public LinkedListDeque(LinkedListDeque <T> other) {
        sentinel = new IntNode(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
        for (int i = 0; i < other.size(); i++) {
            this.addLast(other.get(i));
        }
    }

    @Override
    /**Adds an item of the type T to the front of the deque.*/
    public void addFirst(T item){
        //nullIntNodeisatlast
        //sentinel=newIntNode(item,sentinel.prev,sentinel);
        //sentinel.prev.next=sentinel;
        //sentinel.next.prev=sentinel;

        //nullIntNodeisatfirst
        sentinel.next=new IntNode(item,sentinel,sentinel.next);
        sentinel.next.next.prev=sentinel.next;
        size+=1;
    }

    @Override
    /**Adds an item of the type T to the back of the deque.*/
    public void addLast(T item){
        sentinel.prev=new IntNode(item,sentinel.prev,sentinel);
        sentinel.prev.prev.next=sentinel.prev;
        size+=1;
    }


//    /**Returns true if deque is empty,false otherwise.*/
//    public boolean isEmpty(){
//        return(size==0);
//    }


    @Override
    /**Returns the number of items in the deque.*/
    public int size(){
        return(size);
    }

    @Override
    /**Prints the items in the deque from first to last, separated by a space.
     *Once all the items have been printed, print out a new line.*/
    public void printDeque(){
        printIntNode(sentinel.next);
    }

    private void printIntNode(IntNode sentinel){
        if(sentinel.next.item==null){
            System.out.print(sentinel.item);
            System.out.println();
            return;
        }
        System.out.print(sentinel.item);
        System.out.print(' ');
        printIntNode(sentinel.next);
    }

    @Override
    /**Removes and returns the item at the front of the deque.
     *If no such item exists, returns null.*/
    public T removeFirst(){
        if(size==0){
            return null;
        }
        T first = sentinel.next.item;
        sentinel.next.next.prev=sentinel;
        sentinel.next=sentinel.next.next;
        size-=1;
        return(first);
    }

    @Override
    /**Removes and returns the item at the back of the deque.
     *If no such item exists, returns null.*/
    public T removeLast(){
        if(size==0){
            return null;
        }
        T last = sentinel.prev.item;
        sentinel.prev.prev.next=sentinel;
        sentinel.prev=sentinel.prev.prev;
        size-=1;
        return(last);
    }

    @Override
    /**Gets the item at the given index, where 0 is the front, 1 is the next item,
     * and so forth.
     *If no such item exists, returns null.
     *Must not alter the deque!.*/
    public T get(int index){
        IntNode temp=sentinel.next;
        for(int i=0;i<size;i++){
            if(i==index){
                return(temp.item);
            }
            temp=temp.next;
        }
        return null;
    }

    /**Same as get,but uses recursion.*/
    public T getRecurisive(int index){
        return(getIntNodeRecurisive(sentinel.next,index));
    }

    private T getIntNodeRecurisive(IntNode sentinel,int index){
        if(index==0){
            return(sentinel.item);
        }
        if(sentinel.next.item==null){
            return null;
        }
        return(getIntNodeRecurisive(sentinel.next,index-1));
    }
}
