public class Palindrome {
    /**
     * method to convert a word into a deque containing the
     * same characters in order they were in string
     * @source: https://stackoverflow.com/questions/11229986/get-string-character-by-index-java
     * used to get character at specified index of string
     */
    public Deque<Character> wordToDeque(String word) {
        int strlength = word.length();
        ProvidedLinkedListDeque<Character> worddeque = new ProvidedLinkedListDeque<Character>();
        for (int i = 0; i < strlength; i++) {
            worddeque.addLast(word.charAt(i));
        }
        return worddeque;
    }

    /** method to check whether a given word
     * is a palindrome or not
     * @param word
     * @return true or false
     */
    public boolean isPalindrome(String word) {
        if (word.length() == 0 || word.length() == 1) {
            return true;
        }
        String temp = "";
        for (int i = word.length() - 1; i >= 0; i--) {
            temp = temp + word.charAt(i);
        }

        return temp.equals(word);
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        if (word.length() == 0 || word.length() == 1) {
            return true;
        }
        int i = 0;
        int x = word.length() - 1;
        while (i < (word.length() / 2)) {
            if (cc.equalChars(word.charAt(i), word.charAt(x))) {
                return true;
            }
            i += 1;
            x -= 1;

        }
        return false;
    }
}
