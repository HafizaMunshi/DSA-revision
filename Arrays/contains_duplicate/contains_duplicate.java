import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * ============================================================
 *  Contains Duplicate — Three Approaches
 *  LeetCode #217 | https://leetcode.com/problems/contains-duplicate/
 * ============================================================
 *
 *  Problem:
 *  Given an integer array `nums`, return true if any value appears
 *  at least twice in the array, and return false if every element
 *  is distinct.
 *
 *  Examples:
 *    Input : nums = [1, 2, 3, 1]     → Output: true
 *    Input : nums = [1, 2, 3, 4]     → Output: false
 *    Input : nums = [1, 1, 1, 3, 3]  → Output: true
 *
 * ============================================================
 */

public class contains_duplicate {

    // ============================================================
    // APPROACH 1 — Brute Force
    // Time Complexity : O(n²)
    // Space Complexity: O(1)
    // ============================================================
    //
    // Explanation:
    //   - Compare every pair of elements (i, j) in the array.
    //   - If any two elements are equal, return true immediately.
    //   - If no duplicate is found after all comparisons, return false.
    //
    // Dry Run:
    //   nums = [1, 2, 3, 1]
    //
    //   i=0(1), j=1(2) → 1 == 2? ✗
    //   i=0(1), j=2(3) → 1 == 3? ✗
    //   i=0(1), j=3(1) → 1 == 1? ✓ → return true
    //
    // ============================================================

    public static boolean containsDuplicateBruteForce(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] == nums[j]) {
                    return true;
                }
            }
        }
        return false;
    }


    // ============================================================
    // APPROACH 2 — Sort + Adjacent Check
    // Time Complexity : O(n log n)
    // Space Complexity: O(n) — Java's Arrays.sort uses O(n) for objects;
    //                          O(log n) for primitives (TimSort stack)
    // ============================================================
    //
    // Explanation:
    //   - Sort the array. After sorting, duplicate elements will be
    //     next to each other.
    //   - Iterate through the sorted array and check if any adjacent
    //     elements are equal.
    //   - If yes, return true. Otherwise return false.
    //
    // Dry Run:
    //   nums = [1, 2, 3, 1]
    //
    //   After sort → [1, 1, 2, 3]
    //
    //   i=0 → nums[0]==nums[1]? 1==1 ✓ → return true
    //
    // ============================================================

    public static boolean containsDuplicateSorted(int[] nums) {
        Arrays.sort(nums);                   // O(n log n)
        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i] == nums[i + 1]) {
                return true;
            }
        }
        return false;
    }


    // ============================================================
    // APPROACH 3 — HashSet (Optimal)
    // Time Complexity : O(n)
    // Space Complexity: O(n)
    // ============================================================
    //
    // Explanation:
    //   - Use a HashSet to track elements we have already seen.
    //   - For each element, check if it already exists in the set.
    //       • If yes → duplicate found → return true.
    //       • If no  → add it to the set and continue.
    //   - If we finish the loop with no duplicates, return false.
    //
    // Dry Run:
    //   nums = [1, 2, 3, 1]
    //
    //   i=0, num=1 → not in set → set: {1}
    //   i=1, num=2 → not in set → set: {1, 2}
    //   i=2, num=3 → not in set → set: {1, 2, 3}
    //   i=3, num=1 → 1 IS in set ✓ → return true
    //
    // ============================================================

    public static boolean containsDuplicateHashSet(int[] nums) {
        Set<Integer> visited = new HashSet<>();

        for (int i = 0; i < nums.length; i++) {
            if (visited.contains(nums[i])) {
                return true;
            }
            visited.add(nums[i]);
        }

        return false;
    }


    // ============================================================
    // MAIN — Test all three approaches
    // ============================================================

    public static void main(String[] args) {
        int[][] testCases = {
            {1, 2, 3, 1},      // true  — has duplicate 1
            {1, 2, 3, 4},      // false — all unique
            {1, 1, 1, 3, 3}    // true  — has duplicates 1 and 3
        };

        boolean[] expected = {true, false, true};

        System.out.println("=================================================");
        System.out.println("          Contains Duplicate — Test Results       ");
        System.out.println("=================================================");

        for (int t = 0; t < testCases.length; t++) {
            int[] nums       = testCases[t];
            int[] numsC1     = Arrays.copyOf(nums, nums.length);  // brute force copy
            int[] numsC2     = Arrays.copyOf(nums, nums.length);  // sort modifies array

            boolean r1 = containsDuplicateBruteForce(numsC1);
            boolean r2 = containsDuplicateSorted(numsC2);
            boolean r3 = containsDuplicateHashSet(nums);

            System.out.println("\nTest Case " + (t + 1) + ": " + Arrays.toString(testCases[t]));
            System.out.println("  Expected              : " + expected[t]);
            System.out.println("  Brute Force  O(n²)/O(1)     : " + r1);
            System.out.println("  Sort + Check O(nlogn)/O(n)  : " + r2);
            System.out.println("  HashSet      O(n)/O(n)      : " + r3);
        }

        System.out.println("\n---------------------------------------------------");
        System.out.println("Summary:");
        System.out.printf("%-22s %-18s %-18s%n", "Approach", "Time Complexity", "Space Complexity");
        System.out.println("---------------------------------------------------");
        System.out.printf("%-22s %-18s %-18s%n", "Brute Force",      "O(n²)",      "O(1)");
        System.out.printf("%-22s %-18s %-18s%n", "Sort + Adj Check", "O(n log n)", "O(n)");
        System.out.printf("%-22s %-18s %-18s%n", "HashSet (Optimal)","O(n)",       "O(n)");
    }
}
