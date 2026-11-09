import java.util.Arrays;

/**
 * ============================================================
 *  LeetCode #153 — Find Minimum in Rotated Sorted Array
 *  https://leetcode.com/problems/find-minimum-in-rotated-sorted-array/
 * ============================================================
 *
 *  Problem:
 *  A sorted array of unique elements has been rotated between
 *  1 and n times. Find the minimum element.
 *
 *  Example rotations:
 *    [0,1,2,4,5,6,7] rotated 4 times → [4,5,6,7,0,1,2]
 *    [0,1,2,4,5,6,7] rotated 7 times → [0,1,2,4,5,6,7]
 *
 *  Examples:
 *    nums = [3, 4, 5, 1, 2]       → 1
 *    nums = [4, 5, 6, 7, 0, 1, 2] → 0
 *    nums = [11, 13, 15, 17]      → 11  (no rotation)
 *
 *  Constraints:
 *    1 <= n <= 5000
 *    -5000 <= nums[i] <= 5000
 *    All elements are UNIQUE
 *    ⚠️ Must run in O(log n) time
 *
 * ============================================================
 */

public class _153_find_minimum_in_rotated_sorted_array {

    // ============================================================
    // APPROACH 1 — Brute Force (Linear Search)
    // Time Complexity : O(n)
    // Space Complexity: O(1)
    // ============================================================
    //
    // Explanation:
    //   - Simply scan through the entire array and track the minimum.
    //   - Does NOT satisfy the O(log n) requirement but serves as
    //     a baseline to verify correctness.
    //
    // Dry Run:
    //   nums = [4, 5, 6, 7, 0, 1, 2]
    //
    //   min = 4
    //   i=1: 5 > 4 → skip
    //   i=2: 6 > 4 → skip
    //   i=3: 7 > 4 → skip
    //   i=4: 0 < 4 → min = 0
    //   i=5: 1 > 0 → skip
    //   i=6: 2 > 0 → skip
    //
    //   return 0
    //
    // ============================================================

    public static int findMinBruteForce(int[] nums) {
        int min = nums[0];
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] < min) {
                min = nums[i];
            }
        }
        return min;
    }


    // ============================================================
    // APPROACH 2 — Find Pivot (Drop Point)
    // Time Complexity : O(n)
    // Space Complexity: O(1)
    // ============================================================
    //
    // Explanation:
    //   - In a rotated sorted array, the minimum is the only element
    //     where nums[i] > nums[i+1] (the "drop point").
    //   - Scan for this drop point; the element after it is the min.
    //   - If no drop point found, the array is not rotated → nums[0]
    //     is the minimum.
    //
    // Dry Run:
    //   nums = [4, 5, 6, 7, 0, 1, 2]
    //
    //   i=0: 4 < 5 → continue
    //   i=1: 5 < 6 → continue
    //   i=2: 6 < 7 → continue
    //   i=3: 7 > 0 → DROP found! → return nums[4] = 0
    //
    // ============================================================

    public static int findMinPivot(int[] nums) {
        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i] > nums[i + 1]) {
                return nums[i + 1];  // drop point found — next element is min
            }
        }
        return nums[0];  // no rotation (or rotated n times) — first is min
    }


    // ============================================================
    // APPROACH 3 — Binary Search (Optimal) ✅
    // Time Complexity : O(log n)
    // Space Complexity: O(1)
    // ============================================================
    //
    // Key Insight:
    //   A rotated sorted array has two sorted halves split by the
    //   minimum. We can use binary search to decide which half
    //   the minimum lies in by comparing nums[mid] with nums[right]:
    //
    //   • nums[mid] > nums[right]:
    //       The right half is sorted and the minimum is in the
    //       LEFT half (beyond mid) → move left = mid + 1
    //
    //   • nums[mid] <= nums[right]:
    //       The left half contains the rotation or is sorted, so
    //       the minimum is at mid or to its LEFT → move right = mid
    //
    //   When left == right, we've found the minimum.
    //
    // Dry Run:
    //   nums = [4, 5, 6, 7, 0, 1, 2],  left=0, right=6
    //
    //   Step 1: mid = 3, nums[3]=7, nums[6]=2
    //           7 > 2 → min is in RIGHT half → left = mid+1 = 4
    //
    //   Step 2: left=4, right=6, mid=5, nums[5]=1, nums[6]=2
    //           1 <= 2 → min is in LEFT half (incl. mid) → right = mid = 5
    //
    //   Step 3: left=4, right=5, mid=4, nums[4]=0, nums[5]=1
    //           0 <= 1 → min is in LEFT half (incl. mid) → right = mid = 4
    //
    //   Step 4: left=4 == right=4 → exit loop
    //
    //   return nums[4] = 0 ✓
    //
    // ============================================================

    public static int findMinBinarySearch(int[] nums) {
        int left  = 0;
        int right = nums.length - 1;

        while (left < right) {
            int mid = left + (right - left) / 2;  // avoids overflow

            if (nums[mid] > nums[right]) {
                // min is in the right half, exclude mid
                left = mid + 1;
            } else {
                // min is at mid or in the left half, keep mid
                right = mid;
            }
        }

        return nums[left];  // left == right → minimum element
    }


    // ============================================================
    // MAIN — Test all three approaches
    // ============================================================

    public static void main(String[] args) {
        int[][] testCases = {
            {3, 4, 5, 1, 2},
            {4, 5, 6, 7, 0, 1, 2},
            {11, 13, 15, 17},
            {2, 1}
        };
        int[] expected = {1, 0, 11, 1};

        System.out.println("=========================================================");
        System.out.println("  LeetCode #153 — Find Minimum in Rotated Sorted Array   ");
        System.out.println("=========================================================");

        for (int t = 0; t < testCases.length; t++) {
            int[] nums = testCases[t];

            int r1 = findMinBruteForce(nums.clone());
            int r2 = findMinPivot(nums.clone());
            int r3 = findMinBinarySearch(nums.clone());

            System.out.println("\nTest Case " + (t + 1) + ": nums=" + Arrays.toString(nums));
            System.out.println("  Expected                          : " + expected[t]);
            System.out.println("  Brute Force (Linear) O(n) / O(1) : " + r1);
            System.out.println("  Pivot / Drop Point   O(n) / O(1) : " + r2);
            System.out.println("  Binary Search     O(logn) / O(1) : " + r3);
        }

        System.out.println("\n---------------------------------------------------------");
        System.out.println("Summary:");
        System.out.printf("%-25s %-14s %-12s%n", "Approach", "Time", "Space");
        System.out.println("---------------------------------------------------------");
        System.out.printf("%-25s %-14s %-12s%n", "Brute Force (Linear)", "O(n)",      "O(1)");
        System.out.printf("%-25s %-14s %-12s%n", "Pivot / Drop Point",   "O(n)",      "O(1)");
        System.out.printf("%-25s %-14s %-12s%n", "Binary Search",        "O(log n)",  "O(1) ✅");
    }
}
