public class LinkedListDeque<T>{

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

    /**Createanemptylinkedlistdeque.*/
    public LinkedListDeque() {
        sentinel = new IntNode(null,null,null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }

    /**Createadeepcopyofother.*/
    public LinkedListDeque(LinkedListDeque <T> other) {
        sentinel = new IntNode(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
        for (int i = 0; i < other.size(); i++) {
            this.addLast(other.get(i));
        }
    }



    /**AddsanitemofthetypeTtothefrontofthedeque.*/
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

    /**AddsanitemofthetypeTtothebackofthedeque.*/
    public void addLast(T item){
        sentinel.prev=new IntNode(item,sentinel.prev,sentinel);
        sentinel.prev.prev.next=sentinel.prev;
        size+=1;
    }


    /**Returnstrueifdequeisempty,falseotherwise.*/
    public boolean isEmpty(){
        return(size==0);
    }


    /**Returnsthenumberofitemsinthedeque.*/
    public int size(){
        return(size);
    }

    /**Printstheitemsinthedequefromfirsttolast,separatedbyaspace.
     *Oncealltheitemshavebeenprinted,printoutanewline.*/
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

    /**Removesandreturnstheitematthefrontofthedeque.
     *Ifnosuchitemexists,returnsnull.*/
    public T removeFirst(){
        if(size==0){
            return null;
        }
        sentinel.next.next.prev=sentinel;
        sentinel.next=sentinel.next.next;
        size-=1;
        return(sentinel.next.item);
    }

    /**Removesandreturnstheitematthebackofthedeque.
     *Ifnosuchitemexists,returnsnull.*/
    public T removeLast(){
        if(size==0){
            return null;
        }
        sentinel.prev.prev.next=sentinel;
        sentinel.prev=sentinel.prev.prev;
        size-=1;
        return(sentinel.prev.item);
    }

    /**Getstheitematthegivenindex,where0isthefront,1isthenextitem,andsoforth.
     *Ifnosuchitemexists,returnsnull.
     *Mustnotalterthedeque!.*/
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

    /**Sameasget,butusesrecursion.*/
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


    /**justfortest*/
    public static void main(String[]arg){
        LinkedListDeque<Integer>testList=new LinkedListDeque<>();
        System.out.println(testList.isEmpty());
        System.out.println(testList.size());
        testList.printDeque();
        System.out.println(testList.get(0));
        System.out.println(testList.getRecurisive(0));
        testList.addFirst(4);
        testList.addLast(5);
        testList.addFirst(3);
        testList.addFirst(2);
        testList.addFirst(1);
        System.out.println(testList.get(4));
        System.out.println(testList.get(5));
        System.out.println(testList.getRecurisive(3));
        System.out.println(testList.getRecurisive(5));
        testList.printDeque();
        System.out.println(testList.removeFirst());
        System.out.println(testList.removeLast());
        testList.printDeque();
        LinkedListDeque copyTestList=new LinkedListDeque(testList);
        copyTestList.printDeque();
    }
}
