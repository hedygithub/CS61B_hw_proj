public interface Deque<T> {
    /**AddsanitemofthetypeTtothefrontofthedeque.*/
    public void addFirst(T item);

    /**AddsanitemofthetypeTtothebackofthedeque.*/
    public void addLast(T item);

    /**Returnstrueifdequeisempty,falseotherwise.*/
    default public boolean isEmpty(){
        return(size()==0);
    }

    /**Returnsthenumberofitemsinthedeque.*/
    public int size();

    /**Printstheitemsinthedequefromfirsttolast,separatedbyaspace.
     *Oncealltheitemshavebeenprinted,printoutanewline.*/
    public void printDeque();


    /**Removesandreturnstheitematthefrontofthedeque.
     *Ifnosuchitemexists,returnsnull.*/
    public T removeFirst();

    /**Removesandreturnstheitematthebackofthedeque.
     *Ifnosuchitemexists,returnsnull.*/
    public T removeLast();

    /**Getstheitematthegivenindex,where0isthefront,1isthenextitem,andsoforth.
     *Ifnosuchitemexists,returnsnull.
     *Mustnotalterthedeque!.*/
    public T get(int index);
}
