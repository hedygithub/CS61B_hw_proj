public class Palindrome {
    public Deque<Character> wordToDeque(String word){
        Deque<Character> deque = new LinkedListDeque<>(); //is there any better way to do it?
        for (int i = 0; i < word.length(); i++){
            char aWord =  word.charAt(i);
            deque.addLast(aWord);
        }
        return deque;
    }

//    //method 1
//    public boolean isPalindrome(String word){
//        Deque<Character> deque = wordToDeque(word);
//        int size = deque.size();
//        if (size==0 || size==1){
//            return true;
//        }
//        for (int i = 0; i < size/2; i++){
//            if (deque.get(i) != deque.get(size-1-i)){
//                return(false);
//            }
//        }
//        return(true);
//    }

    //method 2
    public boolean isPalindrome(String word){
        Deque<Character> deque = wordToDeque(word);
        return(isPalinromeDeque(deque));
    }

    private boolean isPalinromeDeque(Deque deque){
        if (deque.size() ==1 || deque.size() ==0){
            return true;
        }
        if (deque.removeFirst()!=deque.removeLast()){
            return(false);
        }
        return(isPalinromeDeque(deque));
    }

    //with OffByOne
    public boolean isPalindrome(String word, CharacterComparator cc){
        Deque<Character> deque = wordToDeque(word);
        return(isPalindromeDeque(deque, cc));
    }

    private boolean isPalindromeDeque(Deque deque, CharacterComparator cc){
        if (deque.size() ==1 || deque.size() ==0){
            return true;
        }
        if (! cc.equalChars((char)deque.removeFirst(), (char)deque.removeLast())){
            return(false);
        }
        return(isPalindromeDeque(deque, cc));
    }
}
