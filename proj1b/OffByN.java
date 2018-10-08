
public class OffByN implements CharacterComparator {

    private int size;

    public OffByN(int N) {
        this.size = N;
    }
    @Override
    public boolean equalChars(char x, char y) {
        if (x - y == size) {
            return true;
        }
        if (y - x == size) {
            return true;
        }
        return false;
    }
}
