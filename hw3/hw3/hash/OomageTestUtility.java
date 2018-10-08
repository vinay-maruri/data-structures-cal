package hw3.hash;

import java.util.ArrayList;
import java.util.List;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {
        int bucketNum;
        Oomage o;
        int size = oomages.size();
        List<Integer> buckets = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            o = oomages.get(i);
            bucketNum = (o.hashCode() & 0x7FFFFFFF) % M;
            buckets.add(bucketNum);
        }
        for (int k = 0; k < M; k++) {
            int tracker = 0;
            for (int j = 0; j < buckets.size(); j++) {
                if (buckets.get(j) == k) {
                    tracker += 1;
                }
            }
            if (tracker < (size / 50) || tracker > (size / 2.5)) {
                return false;
            }
        }
        return true;
    }
}
