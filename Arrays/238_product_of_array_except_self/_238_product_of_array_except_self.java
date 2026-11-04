import java.util.Arrays;

/**
 * ============================================================
 *  LeetCode #238 — Product of Array Except Self
 *  https://leetcode.com/problems/product-of-array-except-self/
 * ============================================================
 *
 *  Problem:
 *  Given an integer array `nums`, return an array `answer` such
 *  that answer[i] is equal to the product of ALL elements of
 *  nums EXCEPT nums[i].
 *
 *  ⚠️ Constraint: Must run in O(n) time WITHOUT division.
 *
 *  Examples:
 *    nums = [1, 2, 3, 4]       → [24, 12, 8, 6]
 *    nums = [-1, 1, 0, -3, 3]  → [0, 0, 9, 0, 0]
 *
 *  Constraints:
 *    2 <= nums.length <= 10^5
 *    -30 <= nums[i] <= 30
 *
 * ============================================================
 */

public class _238_product_of_array_except_self {

    // ============================================================
    // APPROACH 1 — Brute Force
    // Time Complexity : O(n²)
    // Space Complexity: O(1)  [excluding output array]
    // ============================================================
    //
    // Explanation:
    //   - For each index i, multiply all elements except nums[i].
    //   - Use an inner loop that skips index i.
    //   - Store the result in the output array.
    //
    // Dry Run:
    //   nums = [1, 2, 3, 4]
    //
    //   i=0: 2×3×4 = 24  → res[0] = 24
    //   i=1: 1×3×4 = 12  → res[1] = 12
    //   i=2: 1×2×4 = 8   → res[2] = 8
    //   i=3: 1×2×3 = 6   → res[3] = 6
    //
    //   return [24, 12, 8, 6]
    //
    // ============================================================

    public static int[] productExceptSelfBruteForce(int[] nums) {
        int n = nums.length;
        int[] res = new int[n];

        for (int i = 0; i < n; i++) {
            int product = 1;
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    product *= nums[j];
                }
            }
            res[i] = product;
        }

        return res;
    }


    // ============================================================
    // APPROACH 2 — Prefix & Suffix Arrays
    // Time Complexity : O(n)
    // Space Complexity: O(n)  [two extra arrays]
    // ============================================================
    //
    // Explanation:
    //   - Build a prefix[] array where prefix[i] = product of all
    //     elements to the LEFT of index i.
    //   - Build a suffix[] array where suffix[i] = product of all
    //     elements to the RIGHT of index i.
    //   - Result for each index i = prefix[i] * suffix[i].
    //
    // Dry Run:
    //   nums   = [1,  2,  3,  4]
    //
    //   prefix = [1,  1,  2,  6]   (1, 1×1, 1×2, 1×2×3)
    //   suffix = [24, 12, 4,  1]   (2×3×4, 3×4, 4, 1)
    //
    //   res[0] = prefix[0] * suffix[0] = 1  * 24 = 24
    //   res[1] = prefix[1] * suffix[1] = 1  * 12 = 12
    //   res[2] = prefix[2] * suffix[2] = 2  * 4  = 8
    //   res[3] = prefix[3] * suffix[3] = 6  * 1  = 6
    //
    //   return [24, 12, 8, 6]
    //
    // ============================================================

    public static int[] productExceptSelfPrefixSuffixArrays(int[] nums) {
        int n = nums.length;
        int[] prefix = new int[n];
        int[] suffix = new int[n];
        int[] res    = new int[n];

        // Build prefix products
        prefix[0] = 1;
        for (int i = 1; i < n; i++) {
            prefix[i] = prefix[i - 1] * nums[i - 1];
        }

        // Build suffix products
        suffix[n - 1] = 1;
        for (int i = n - 2; i >= 0; i--) {
            suffix[i] = suffix[i + 1] * nums[i + 1];
        }

        // Multiply prefix and suffix
        for (int i = 0; i < n; i++) {
            res[i] = prefix[i] * suffix[i];
        }

        return res;
    }


    // ============================================================
    // APPROACH 3 — Prefix & Suffix In-Place (Optimal) ✅
    // Time Complexity : O(n)
    // Space Complexity: O(1)  [output array doesn't count]
    // ============================================================
    //
    // Explanation:
    //   - Use the result array itself to store prefix products
    //     in the first pass (left → right).
    //   - In the second pass (right → left), multiply each result
    //     element by a running suffix product.
    //   - This avoids using two separate extra arrays.
    //
    //   Pass 1 (Prefix):
    //     res[i] = product of everything to the LEFT of i
    //     running prefix starts at 1, multiplied by nums[i] after each step
    //
    //   Pass 2 (Suffix):
    //     res[i] *= suffix  (product of everything to the RIGHT of i)
    //     running suffix starts at 1, multiplied by nums[i] after each step
    //
    // Dry Run:
    //   nums = [1, 2, 3, 4]
    //
    //   Pass 1 → Prefix:
    //     i=0: res[0]=1,     prefix=1×1=1
    //     i=1: res[1]=1,     prefix=1×2=2
    //     i=2: res[2]=2,     prefix=2×3=6
    //     i=3: res[3]=6,     prefix=6×4=24
    //     res = [1, 1, 2, 6]
    //
    //   Pass 2 → Suffix:
    //     i=3: res[3]=6×1=6,   suffix=1×4=4
    //     i=2: res[2]=2×4=8,   suffix=4×3=12
    //     i=1: res[1]=1×12=12, suffix=12×2=24
    //     i=0: res[0]=1×24=24, suffix=24×1=24
    //     res = [24, 12, 8, 6]
    //
    //   return [24, 12, 8, 6] ✓
    //
    // ============================================================

    public static int[] productExceptSelfOptimal(int[] nums) {
        int n      = nums.length;
        int[] res  = new int[n];
        int prefix = 1;
        int suffix = 1;

        // Pass 1: fill res with prefix products (left side of each index)
        for (int i = 0; i < n; i++) {
            res[i]  = prefix;
            prefix *= nums[i];
        }

        // Pass 2: multiply by suffix products (right side of each index)
        for (int i = n - 1; i >= 0; i--) {
            res[i] *= suffix;
            suffix *= nums[i];
        }

        return res;
    }


    // ============================================================
    // MAIN — Test all three approaches
    // ============================================================

    public static void main(String[] args) {
        int[][] testCases = {
            {1, 2, 3, 4},
            {-1, 1, 0, -3, 3}
        };
        int[][] expected = {
            {24, 12, 8, 6},
            {0, 0, 9, 0, 0}
        };

        System.out.println("=======================================================");
        System.out.println("    LeetCode #238 — Product of Array Except Self       ");
        System.out.println("=======================================================");

        for (int t = 0; t < testCases.length; t++) {
            int[] nums = testCases[t];

            int[] r1 = productExceptSelfBruteForce(nums.clone());
            int[] r2 = productExceptSelfPrefixSuffixArrays(nums.clone());
            int[] r3 = productExceptSelfOptimal(nums.clone());

            System.out.println("\nTest Case " + (t + 1) + ": nums=" + Arrays.toString(nums));
            System.out.println("  Expected                          : " + Arrays.toString(expected[t]));
            System.out.println("  Brute Force       O(n²) / O(1)  : " + Arrays.toString(r1));
            System.out.println("  Prefix+Suffix Arr O(n)  / O(n)  : " + Arrays.toString(r2));
            System.out.println("  Prefix+Suffix Opt O(n)  / O(1)  : " + Arrays.toString(r3));
        }

        System.out.println("\n-------------------------------------------------------");
        System.out.println("Summary:");
        System.out.printf("%-28s %-12s %-12s%n", "Approach", "Time", "Space");
        System.out.println("-------------------------------------------------------");
        System.out.printf("%-28s %-12s %-12s%n", "Brute Force",              "O(n²)", "O(1)");
        System.out.printf("%-28s %-12s %-12s%n", "Prefix + Suffix Arrays",   "O(n)",  "O(n)");
        System.out.printf("%-28s %-12s %-12s%n", "Prefix + Suffix In-Place", "O(n)",  "O(1) ✅");
    }
}
