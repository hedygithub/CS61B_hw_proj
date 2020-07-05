public class OffByN implements CharacterComparator{
    public int _n;
    public OffByN (int n){
        _n = n;
    }
    @Override
    public boolean equalChars(char x, char y){
        return (y - x == _n || x - y == _n);
    }
}
