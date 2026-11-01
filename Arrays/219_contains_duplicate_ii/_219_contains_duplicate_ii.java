import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * ============================================================
 *  LeetCode #219 — Contains Duplicate II
 *  https://leetcode.com/problems/contains-duplicate-ii/
 * ============================================================
 *
 *  Problem:
 *  Given an integer array `nums` and an integer `k`, return true
 *  if there are two distinct indices i and j such that:
 *    - nums[i] == nums[j]  (same value)
 *    - abs(i - j) <= k     (within distance k)
 *
 *  Examples:
 *    nums = [1, 2, 3, 1],    k = 3  → true  (i=0, j=3, |0-3|=3 ≤ 3)
 *    nums = [1, 0, 1, 1],    k = 1  → true  (i=2, j=3, |2-3|=1 ≤ 1)
 *    nums = [1, 2, 3, 1, 2, 3], k = 2  → false (duplicates too far apart)
 *
 * ============================================================
 */

public class _219_contains_duplicate_ii {

    // ============================================================
    // APPROACH 1 — Brute Force
    // Time Complexity : O(n²)
    // Space Complexity: O(1)
    // ============================================================
    //
    // Explanation:
    //   - For every element at index i, look ahead at all j from i+1
    //     to min(i+k, n-1) — only check within window of size k.
    //   - If nums[i] == nums[j] within that range, return true.
    //   - If no such pair found, return false.
    //
    // Dry Run:
    //   nums = [1, 2, 3, 1], k = 3
    //
    //   i=0(1): j=1(2)→✗, j=2(3)→✗, j=3(1)→ 1==1 & |0-3|=3≤3 ✓ → return true
    //
    // ============================================================

    public static boolean containsNearbyDuplicateBruteForce(int[] nums, int k) {
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j <= Math.min(i + k, n - 1); j++) {
                if (nums[i] == nums[j]) {
                    return true;
                }
            }
        }
        return false;
    }


    // ============================================================
    // APPROACH 2 — HashMap (Last Seen Index)
    // Time Complexity : O(n)
    // Space Complexity: O(n)
    // ============================================================
    //
    // Explanation:
    //   - Use a HashMap to store each number's most recent index.
    //   - For each element, check if it was seen before:
    //       • If yes, check if the distance (i - lastIndex) <= k.
    //         If so, return true.
    //   - Always update the map with the current index (most recent).
    //
    // Dry Run:
    //   nums = [1, 2, 3, 1], k = 3
    //
    //   i=0, num=1 → not in map → map: {1→0}
    //   i=1, num=2 → not in map → map: {1→0, 2→1}
    //   i=2, num=3 → not in map → map: {1→0, 2→1, 3→2}
    //   i=3, num=1 → in map at 0, |3-0|=3 ≤ 3 ✓ → return true
    //
    // ============================================================

    public static boolean containsNearbyDuplicateHashMap(int[] nums, int k) {
        HashMap<Integer, Integer> map = new HashMap<>();  // num → last seen index

        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(nums[i])) {
                int lastIndex = map.get(nums[i]);
                if (i - lastIndex <= k) {
                    return true;
                }
            }
            map.put(nums[i], i);  // update to most recent index
        }

        return false;
    }


    // ============================================================
    // APPROACH 3 — HashSet Sliding Window (Optimal)
    // Time Complexity : O(n)
    // Space Complexity: O(k)  ← only stores at most k+1 elements
    // ============================================================
    //
    // Explanation:
    //   - Maintain a sliding window HashSet of size at most k.
    //   - For each element:
    //       • If it's already IN the window → duplicate within k → return true.
    //       • Add the element to the window.
    //       • If the window size exceeds k, remove the oldest element
    //         (the element that's now out of range: nums[i - k]).
    //
    // Dry Run:
    //   nums = [1, 2, 3, 1], k = 3
    //
    //   i=0, num=1 → not in set → set: {1}           (size 1 ≤ 3)
    //   i=1, num=2 → not in set → set: {1, 2}        (size 2 ≤ 3)
    //   i=2, num=3 → not in set → set: {1, 2, 3}     (size 3 ≤ 3)
    //   i=3, num=1 → 1 IS in set ✓ → return true
    //
    // ============================================================

    public static boolean containsNearbyDuplicateHashSet(int[] nums, int k) {
        Set<Integer> window = new HashSet<>();

        for (int i = 0; i < nums.length; i++) {
            if (window.contains(nums[i])) {
                return true;
            }
            window.add(nums[i]);

            // shrink window: remove element that is now out of range
            if (window.size() > k) {
                window.remove(nums[i - k]);
            }
        }

        return false;
    }


    // ============================================================
    // MAIN — Test all three approaches
    // ============================================================

    public static void main(String[] args) {
        int[][] testNums = {
            {1, 2, 3, 1},
            {1, 0, 1, 1},
            {1, 2, 3, 1, 2, 3}
        };
        int[] testK      = {3, 1, 2};
        boolean[] expected = {true, true, false};

        System.out.println("=======================================================");
        System.out.println("       LeetCode #219 — Contains Duplicate II           ");
        System.out.println("=======================================================");

        for (int t = 0; t < testNums.length; t++) {
            boolean r1 = containsNearbyDuplicateBruteForce(testNums[t], testK[t]);
            boolean r2 = containsNearbyDuplicateHashMap(testNums[t], testK[t]);
            boolean r3 = containsNearbyDuplicateHashSet(testNums[t], testK[t]);

            System.out.println("\nTest Case " + (t + 1) + ": nums=" +
                java.util.Arrays.toString(testNums[t]) + ", k=" + testK[t]);
            System.out.println("  Expected                        : " + expected[t]);
            System.out.println("  Brute Force      O(n²)  / O(1) : " + r1);
            System.out.println("  HashMap          O(n)   / O(n) : " + r2);
            System.out.println("  HashSet Window   O(n)   / O(k) : " + r3);
        }

        System.out.println("\n-----------------------------------------------------");
        System.out.println("Summary:");
        System.out.printf("%-22s %-16s %-16s%n", "Approach", "Time", "Space");
        System.out.println("-----------------------------------------------------");
        System.out.printf("%-22s %-16s %-16s%n", "Brute Force",         "O(n²)",  "O(1)");
        System.out.printf("%-22s %-16s %-16s%n", "HashMap Last Index",  "O(n)",   "O(n)");
        System.out.printf("%-22s %-16s %-16s%n", "HashSet Sliding Win", "O(n)",   "O(k) ✅");
    }
}
