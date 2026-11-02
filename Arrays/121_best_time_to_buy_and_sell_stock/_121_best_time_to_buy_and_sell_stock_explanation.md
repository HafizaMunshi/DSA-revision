# LeetCode #121 — Best Time to Buy and Sell Stock: Code Explanation & Dry Run

**Problem Link:** [leetcode.com/problems/best-time-to-buy-and-sell-stock](https://leetcode.com/problems/best-time-to-buy-and-sell-stock/)

Given an array `prices` where `prices[i]` is the stock price on day `i`, find the **maximum profit** by choosing one day to **buy** and a later day to **sell**.

Return `0` if no profit is possible.

---

## 1. Brute Force Approach (O(n²) Time, O(1) Space)

### Code
```java
public int maxProfit(int[] prices) {
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
```

### Explanation
- Try every possible pair of buy day `i` and sell day `j` (where `j > i`).
- Calculate `profit = prices[j] - prices[i]` for each pair.
- Track the maximum profit across all pairs.
- Return `0` if no profitable pair exists.

### Dry Run
**Input:** `prices = [7, 1, 5, 3, 6, 4]`

| Buy (i) | Sell (j) | Price Buy | Price Sell | Profit | Max Profit |
|---------|----------|-----------|------------|--------|------------|
| 0       | 1        | 7         | 1          | -6     | 0          |
| 1       | 2        | 1         | 5          | 4      | 4          |
| 1       | 3        | 1         | 3          | 2      | 4          |
| 1       | 4        | 1         | 6          | **5**  | **5** ✓    |
| 1       | 5        | 1         | 4          | 3      | 5          |

→ **return 5**

---

## 2. Sliding Window / Two Pointers (O(n) Time, O(1) Space)

### Code
```java
public int maxProfit(int[] prices) {
    int left = 0;       // buy pointer
    int right = 1;      // sell pointer
    int maxProfit = 0;

    while (right < prices.length) {
        if (prices[left] < prices[right]) {
            int profit = prices[right] - prices[left];
            maxProfit = Math.max(maxProfit, profit);
        } else {
            left = right;   // shift buy day to cheaper price
        }
        right++;
    }
    return maxProfit;
}
```

### Explanation
- Use two pointers — `left` (buy day) and `right` (sell day).
- If `prices[left] < prices[right]` → profitable, update maxProfit.
- If `prices[left] >= prices[right]` → right side is cheaper, move `left` to `right` (new potential buy day).
- Always advance `right` by 1 each iteration.

### Dry Run
**Input:** `prices = [7, 1, 5, 3, 6, 4]`

| Left (Buy) | Right (Sell) | Buy Price | Sell Price | Profit | Action           | maxProfit |
|------------|-------------|-----------|------------|--------|------------------|-----------|
| 0          | 1            | 7         | 1          | —      | L=R (7≥1, shift) | 0         |
| 1          | 2            | 1         | 5          | 4      | Update max       | 4         |
| 1          | 3            | 1         | 3          | 2      | No update        | 4         |
| 1          | 4            | 1         | 6          | **5**  | Update max       | **5** ✓   |
| 1          | 5            | 1         | 4          | 3      | No update        | 5         |

→ **return 5**

---

## 3. Track Minimum Price — One Pass (O(n) Time, O(1) Space)

### Code
```java
public int maxProfit(int[] prices) {
    int minPrice  = Integer.MAX_VALUE;
    int maxProfit = 0;

    for (int price : prices) {
        if (price < minPrice) {
            minPrice = price;                       // new cheapest buy day
        } else if (price - minPrice > maxProfit) {
            maxProfit = price - minPrice;           // new best profit
        }
    }
    return maxProfit;
}
```

### Explanation
- Keep track of the **minimum price seen so far** (cheapest buy day).
- For each price, calculate `profit = price - minPrice` (sell today).
- Update `maxProfit` if this is better than before.
- Update `minPrice` if today's price is cheaper.
- Single pass — no nested loops needed.

### Dry Run
**Input:** `prices = [7, 1, 5, 3, 6, 4]`

| i | Price | minPrice | Profit Today | maxProfit |
|---|-------|----------|-------------|-----------|
| 0 | 7     | 7        | 0           | 0         |
| 1 | 1     | **1**    | 0           | 0         |
| 2 | 5     | 1        | 4           | 4         |
| 3 | 3     | 1        | 2           | 4         |
| 4 | 6     | 1        | **5**       | **5** ✓   |
| 5 | 4     | 1        | 3           | 5         |

→ **return 5**

---

## Summary Table

| Approach                 | Time Complexity | Space Complexity | Notes                          |
|--------------------------|----------------|------------------|--------------------------------|
| Brute Force              | O(n²)          | O(1)             | Checks every buy/sell pair     |
| Sliding Window (2 ptr)   | O(n)           | O(1) ✅          | Intuitive two-pointer approach |
| Track Min Price (1 pass) | O(n)           | O(1) ✅          | Cleanest one-pass solution     |
