import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Test;

import java.util.*;

/**
 * @author yihui
 * @date 20/12/7
 */
public class BasicTest {

    // NxN 矩阵旋转

    /**
     * 1 2 3
     * 4 5 6   ->
     * 7 8 9
     * <p>
     * 7 4 1
     * 8 5 2
     * 9 6 3
     * <p>
     * 如过是n，
     * n == 3
     * (0, 0) -> （0, 2) -> (0, n - 1)
     * (0, 1) -> (1, 2) -> (1, n - 1)
     * (0, 2) -> (2, 2) -> (2, n - 1)
     * <p>
     * 1 2 3 4
     * 5 6 7 8
     * 9 10 11 12
     * 13 14 15 16
     * <p>
     * ->
     * <p>
     * 13  9   5   1
     * 14  10  6   2
     * 15  11  7   3
     * 16  12  8   4
     * <p>
     * n = 4
     * 12 : (2, 3) -> (3, 1)
     * 7 : (1, 2) -> (2, 4 - 1 -1 ) -> (2, 2)
     * <p>
     * --> (x, y) -> (y, n - x - 1) -> (xxx) --> (xxx)
     */
    public void transfer(List<List<Integer>> list, int n) {
        // fixme 确保是数组长度一致

        // 这个swapTags 可以沈略，比如统计二维矩阵中的最小值和最大值，然后替换的值，全部加上这个 abs(max) + abs(min) + 1，
        // 这样可以确保新矩阵中，所有值都比 max 大，以此来判断是否迁移过
        Set<Pair> swapTags = new HashSet<>();
        for (int x = 0; x < n; x++) {
            for (int y = 0; y < n; y++) {
                swapXY(x, y, n, list.get(x).get(y), list, swapTags);
            }
        }

        // 输出
        for (List<Integer> sub : list) {
            for (Integer val : sub) {
                System.out.print(val + " , ");
            }
            System.out.println();
        }
    }


    private void swapXY(int x, int y, int n, int sourceValue, List<List<Integer>> list, Set<Pair> set) {
        int targetX = y;
        int targetY = n - x - 1;
        Pair pair = new Pair(targetX, targetY);
        if (set.contains(pair)) {
            return;
        }

        int tmp = list.get(targetX).get(targetY);
        list.get(targetX).set(targetY, sourceValue);
        set.add(pair);

        swapXY(targetX, targetY, n, tmp, list, set);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class Pair {
        int x, y;
    }


    @Test
    public void testTransfer() {
        List<List<Integer>> list = new ArrayList<>();
        list.add(Arrays.asList(1, 2, 3));
        list.add(Arrays.asList(4, 5, 6));
        list.add(Arrays.asList(7, 8, 9));
        BasicTest basicTest = new BasicTest();
        basicTest.transfer(list, 3);

        list.clear();
        list.add(Arrays.asList(1, 2, 3, 4));
        list.add(Arrays.asList(5, 6, 7, 8));
        list.add(Arrays.asList(9, 10, 11, 12));
        list.add(Arrays.asList(13, 14, 15, 16));
        basicTest.transfer(list, 4);
    }

}
