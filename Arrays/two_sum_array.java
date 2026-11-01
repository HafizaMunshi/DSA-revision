import java.util.Arrays;
import java.util.HashMap;

/**
 * ============================================================
 *  Two Sum Array — Three Approaches
 * ============================================================
 *
 *  Problem:
 *  Given an integer array `arr` and a target value, return the
 *  indices of the two numbers that add up to the target.
 *
 *  Assume exactly one solution exists.
 *
 *  Example:
 *    Input : arr = [2, 2, 1, 3, 4], target = 3
 *    Output: [0, 2]  (arr[0] + arr[2] = 2 + 1 = 3)
 *
 * ============================================================
 */

public class two_sum_array {

    // ============================================================
    // APPROACH 1 — Brute Force
    // Time Complexity : O(n²)
    // Space Complexity: O(1)
    // ============================================================
    //
    // Explanation:
    //   - Iterate through every possible pair (i, j) in the array.
    //   - For each pair, check if arr[i] + arr[j] equals the target.
    //   - If a matching pair is found, return their indices.
    //   - If no pair is found after all iterations, return an empty array.
    //
    // Dry Run:
    //   arr = [2, 2, 1, 3, 4], target = 3
    //
    //   i=0, j=1 → 2 + 2 = 4  ✗
    //   i=0, j=2 → 2 + 1 = 3  ✓  → return [0, 2]
    //
    // ============================================================

    public static int[] twoSum(int[] arr, int target) {
        int n = arr.length;

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (arr[i] + arr[j] == target) {
                    return new int[]{i, j};
                }
            }
        }

        return new int[]{};  // no pair found
    }


    // ============================================================
    // APPROACH 2 — HashMap (Memoization)
    // Time Complexity : O(n)
    // Space Complexity: O(n)
    // ============================================================
    //
    // Explanation:
    //   - Use a HashMap to store each number and its index as we iterate.
    //   - For each number `num`, calculate its complement = target - num.
    //   - If the complement already exists in the map, we found our pair
    //     → return [map.get(complement), i].
    //   - Otherwise, store the current number and its index in the map.
    //
    // Dry Run:
    //   arr = [2, 2, 1, 3, 4], target = 3
    //
    //   i=0, num=2, complement=1 → not in map → map: {2→0}
    //   i=1, num=2, complement=1 → not in map → map: {2→1}
    //   i=2, num=1, complement=2 → 2 IS in map at index 1
    //                            → return [1, 2]
    //
    // ============================================================

    public static int[] twoSumMemo(int[] arr, int target) {
        HashMap<Integer, Integer> hashmap = new HashMap<>();  // num → index

        for (int i = 0; i < arr.length; i++) {
            int complement = target - arr[i];

            if (hashmap.containsKey(complement)) {
                return new int[]{hashmap.get(complement), i};
            }

            hashmap.put(arr[i], i);
        }

        return new int[]{};  // no pair found
    }


    // ============================================================
    // APPROACH 3 — Two Pointers
    // Time Complexity : O(n)
    // Space Complexity: O(1)
    //
    // ⚠️  NOTE: This approach works ONLY if the array is SORTED.
    // ============================================================
    //
    // Explanation:
    //   - Start with two pointers: `left` at index 0, `right` at last index.
    //   - Check the sum of arr[left] + arr[right]:
    //       • If sum == target  → found! return [left, right]
    //       • If sum < target   → move left pointer RIGHT (need bigger value)
    //       • If sum > target   → move right pointer LEFT (need smaller value)
    //   - Repeat until left >= right (pointers cross).
    //
    // Dry Run:
    //   arr = [2, 2, 1, 3, 4], target = 3
    //
    //   left=0(2), right=4(4) → 2+4=6 > 3 → right--
    //   left=0(2), right=3(3) → 2+3=5 > 3 → right--
    //   left=0(2), right=2(1) → 2+1=3 = 3 ✓ → return [0, 2]
    //
    // ============================================================

    public static int[] twoSumWithPointers(int[] arr, int target) {
        int left = 0;
        int right = arr.length - 1;

        while (left < right) {
            int sum = arr[left] + arr[right];

            if (sum == target) {
                return new int[]{left, right};
            } else if (sum < target) {
                left++;   // need a larger value → move left pointer right
            } else {
                right--;  // need a smaller value → move right pointer left
            }
        }

        return new int[]{};  // no pair found
    }


    // ============================================================
    // MAIN — Test all three approaches
    // ============================================================

    public static void main(String[] args) {
        int[] arr = {2, 2, 1, 3, 4};
        int target = 3;

        System.out.println("Input Array : " + Arrays.toString(arr));
        System.out.println("Target      : " + target);
        System.out.println();

        // Approach 1: Brute Force
        int[] result1 = twoSum(arr, target);
        System.out.println("Approach 1 — Brute Force     O(n²) Time | O(1) Space : "
                + Arrays.toString(result1));

        // Approach 2: HashMap
        int[] result2 = twoSumMemo(arr, target);
        System.out.println("Approach 2 — HashMap Memo    O(n)  Time | O(n) Space : "
                + Arrays.toString(result2));

        // Approach 3: Two Pointers (works on sorted arrays)
        int[] result3 = twoSumWithPointers(arr, target);
        System.out.println("Approach 3 — Two Pointers    O(n)  Time | O(1) Space : "
                + Arrays.toString(result3));

        System.out.println();
        System.out.println("---------------------------------------------------");
        System.out.println("Summary:");
        System.out.printf("%-20s %-18s %-18s %-15s%n",
                "Approach", "Time Complexity", "Space Complexity", "Sorted Needed?");
        System.out.println("---------------------------------------------------");
        System.out.printf("%-20s %-18s %-18s %-15s%n",
                "Brute Force", "O(n²)", "O(1)", "No");
        System.out.printf("%-20s %-18s %-18s %-15s%n",
                "HashMap / Memo", "O(n)", "O(n)", "No");
        System.out.printf("%-20s %-18s %-18s %-15s%n",
                "Two Pointers", "O(n)", "O(1)", "Yes");
    }
}
