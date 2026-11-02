/**
 * ============================================================
 *  LeetCode #121 — Best Time to Buy and Sell Stock
 *  https://leetcode.com/problems/best-time-to-buy-and-sell-stock/
 * ============================================================
 *
 *  Problem:
 *  You are given an array `prices` where prices[i] is the price
 *  of a given stock on the i-th day.
 *
 *  You want to maximize profit by choosing a single day to BUY
 *  and a different day in the FUTURE to SELL.
 *
 *  Return the maximum profit you can achieve.
 *  If no profit is possible, return 0.
 *
 *  Examples:
 *    prices = [7, 1, 5, 3, 6, 4]  → 5  (buy at 1, sell at 6)
 *    prices = [7, 6, 4, 3, 1]     → 0  (prices only fall, no profit)
 *
 *  Constraints:
 *    1 <= prices.length <= 10^5
 *    0 <= prices[i] <= 10^4
 *
 * ============================================================
 */

public class _121_best_time_to_buy_and_sell_stock {

    // ============================================================
    // APPROACH 1 — Brute Force
    // Time Complexity : O(n²)
    // Space Complexity: O(1)
    // ============================================================
    //
    // Explanation:
    //   - Try every possible pair of (buy day i, sell day j) where j > i.
    //   - Calculate profit = prices[j] - prices[i] for each pair.
    //   - Track the maximum profit seen so far.
    //   - Return the max profit (or 0 if no profitable pair exists).
    //
    // Dry Run:
    //   prices = [7, 1, 5, 3, 6, 4]
    //
    //   i=0(7): j=1 → 1-7=-6, j=2 → 5-7=-2, j=3 → 3-7=-4,
    //           j=4 → 6-7=-1, j=5 → 4-7=-3
    //   i=1(1): j=2 → 5-1=4✓, j=3 → 3-1=2, j=4 → 6-1=5✓, j=5 → 4-1=3
    //   i=2(5): j=3 → 3-5=-2, j=4 → 6-5=1, j=5 → 4-5=-1
    //   i=3(3): j=4 → 6-3=3,  j=5 → 4-3=1
    //   i=4(6): j=5 → 4-6=-2
    //
    //   maxProfit = 5  (buy at i=1, sell at j=4)
    //
    // ============================================================

    public static int maxProfitBruteForce(int[] prices) {
        int maxProfit = 0;

        for (int i = 0; i < prices.length; i++) {
            for (int j = i + 1; j < prices.length; j++) {
                int profit = prices[j] - prices[i];
                if (profit > maxProfit) {
                    maxProfit = profit;
                }
            }
        }

        return maxProfit;
    }


    // ============================================================
    // APPROACH 2 — Sliding Window / Two Pointers (Optimal)
    // Time Complexity : O(n)
    // Space Complexity: O(1)
    // ============================================================
    //
    // Explanation:
    //   - Use two pointers: `left` (buy day) and `right` (sell day).
    //   - Start both at index 0 and 1.
    //   - At each step:
    //       • If prices[left] < prices[right]  → profitable, calculate
    //         profit and update maxProfit.
    //       • If prices[left] >= prices[right] → found a cheaper buy day,
    //         move left to right (reset buy day to current).
    //   - Always move right forward by 1.
    //   - Return maxProfit.
    //
    // Dry Run:
    //   prices = [7, 1, 5, 3, 6, 4]
    //
    //   L=0(7),  R=1(1)  → 7 >= 1 → move L to R → L=1
    //   L=1(1),  R=2(5)  → 1 < 5  → profit=4,  maxProfit=4
    //   L=1(1),  R=3(3)  → 1 < 3  → profit=2,  maxProfit=4
    //   L=1(1),  R=4(6)  → 1 < 6  → profit=5,  maxProfit=5 ✓
    //   L=1(1),  R=5(4)  → 1 < 4  → profit=3,  maxProfit=5
    //
    //   return 5
    //
    // ============================================================

    public static int maxProfitSlidingWindow(int[] prices) {
        int left = 0;       // buy pointer
        int right = 1;      // sell pointer
        int maxProfit = 0;

        while (right < prices.length) {
            if (prices[left] < prices[right]) {
                // profitable trade — calculate and update max
                int profit = prices[right] - prices[left];
                maxProfit = Math.max(maxProfit, profit);
            } else {
                // found cheaper price — shift buy day to current position
                left = right;
            }
            right++;
        }

        return maxProfit;
    }


    // ============================================================
    // APPROACH 3 — Track Minimum Price (One Pass)
    // Time Complexity : O(n)
    // Space Complexity: O(1)
    // ============================================================
    //
    // Explanation:
    //   - Keep track of the minimum price seen so far (best buy day).
    //   - For each price, calculate profit if we sell today:
    //       profit = prices[i] - minPrice
    //   - Update maxProfit if this profit is better.
    //   - Update minPrice if today's price is lower.
    //
    // Dry Run:
    //   prices = [7, 1, 5, 3, 6, 4]
    //
    //   i=0: price=7, minPrice=7,  profit=0,  maxProfit=0
    //   i=1: price=1, minPrice=1,  profit=0,  maxProfit=0
    //   i=2: price=5, minPrice=1,  profit=4,  maxProfit=4
    //   i=3: price=3, minPrice=1,  profit=2,  maxProfit=4
    //   i=4: price=6, minPrice=1,  profit=5,  maxProfit=5 ✓
    //   i=5: price=4, minPrice=1,  profit=3,  maxProfit=5
    //
    //   return 5
    //
    // ============================================================

    public static int maxProfitOnePass(int[] prices) {
        int minPrice  = Integer.MAX_VALUE;
        int maxProfit = 0;

        for (int price : prices) {
            if (price < minPrice) {
                minPrice = price;               // found new cheapest buy day
            } else if (price - minPrice > maxProfit) {
                maxProfit = price - minPrice;   // found new best profit
            }
        }

        return maxProfit;
    }


    // ============================================================
    // MAIN — Test all three approaches
    // ============================================================

    public static void main(String[] args) {
        int[][] testCases   = {{7, 1, 5, 3, 6, 4}, {7, 6, 4, 3, 1}, {2, 4, 1}};
        int[]   expected    = {5, 0, 2};

        System.out.println("=======================================================");
        System.out.println("   LeetCode #121 — Best Time to Buy and Sell Stock     ");
        System.out.println("=======================================================");

        for (int t = 0; t < testCases.length; t++) {
            int[] prices = testCases[t];

            int r1 = maxProfitBruteForce(prices.clone());
            int r2 = maxProfitSlidingWindow(prices.clone());
            int r3 = maxProfitOnePass(prices.clone());

            System.out.println("\nTest Case " + (t + 1) + ": prices=" +
                java.util.Arrays.toString(prices));
            System.out.println("  Expected                          : " + expected[t]);
            System.out.println("  Brute Force      O(n²)  / O(1)  : " + r1);
            System.out.println("  Sliding Window   O(n)   / O(1)  : " + r2);
            System.out.println("  Track Min Price  O(n)   / O(1)  : " + r3);
        }

        System.out.println("\n-------------------------------------------------------");
        System.out.println("Summary:");
        System.out.printf("%-25s %-16s %-16s%n", "Approach", "Time", "Space");
        System.out.println("-------------------------------------------------------");
        System.out.printf("%-25s %-16s %-16s%n", "Brute Force",        "O(n²)", "O(1)");
        System.out.printf("%-25s %-16s %-16s%n", "Sliding Window",     "O(n)",  "O(1) ✅");
        System.out.printf("%-25s %-16s %-16s%n", "Track Min Price",    "O(n)",  "O(1) ✅");
    }
}
