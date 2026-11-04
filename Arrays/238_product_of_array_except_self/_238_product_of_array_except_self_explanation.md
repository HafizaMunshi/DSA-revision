# LeetCode #238 — Product of Array Except Self: Code Explanation & Dry Run

**Problem Link:** [leetcode.com/problems/product-of-array-except-self](https://leetcode.com/problems/product-of-array-except-self/)

Given an integer array `nums`, return an array `answer` such that `answer[i]` equals the product of **all elements of nums except `nums[i]`**.

> ⚠️ Must run in **O(n)** time and **without using division**.

---

## 1. Brute Force Approach (O(n²) Time, O(1) Space)

### Code
```java
public int[] productExceptSelf(int[] nums) {
    int n = nums.length;
    int[] res = new int[n];
    for (int i = 0; i < n; i++) {
        int product = 1;
        for (int j = 0; j < n; j++) {
            if (i != j) product *= nums[j];
        }
        res[i] = product;
    }
    return res;
}
```

### Explanation
- For each index `i`, iterate through the entire array and multiply all elements **except** `nums[i]`.
- Store the result in `res[i]`.
- Simple but slow — not accepted on LeetCode due to O(n²) time.

### Dry Run
**Input:** `nums = [1, 2, 3, 4]`

| i | Skip | Product Calculated     | res[i] |
|---|------|------------------------|--------|
| 0 | 1    | 2 × 3 × 4 = **24**    | 24     |
| 1 | 2    | 1 × 3 × 4 = **12**    | 12     |
| 2 | 3    | 1 × 2 × 4 = **8**     | 8      |
| 3 | 4    | 1 × 2 × 3 = **6**     | 6      |

→ **return [24, 12, 8, 6]**

---

## 2. Prefix & Suffix Arrays (O(n) Time, O(n) Space)

### Code
```java
public int[] productExceptSelf(int[] nums) {
    int n = nums.length;
    int[] prefix = new int[n];
    int[] suffix = new int[n];
    int[] res    = new int[n];

    prefix[0] = 1;
    for (int i = 1; i < n; i++)
        prefix[i] = prefix[i - 1] * nums[i - 1];

    suffix[n - 1] = 1;
    for (int i = n - 2; i >= 0; i--)
        suffix[i] = suffix[i + 1] * nums[i + 1];

    for (int i = 0; i < n; i++)
        res[i] = prefix[i] * suffix[i];

    return res;
}
```

### Explanation
- **prefix[i]** = product of all elements **to the LEFT** of index i.
- **suffix[i]** = product of all elements **to the RIGHT** of index i.
- **res[i]** = prefix[i] × suffix[i] = everything except nums[i].

### Dry Run
**Input:** `nums = [1, 2, 3, 4]`

**Prefix array** (left products):
| i | prefix[i]            |
|---|----------------------|
| 0 | 1 (nothing to left)  |
| 1 | 1 × 1 = 1            |
| 2 | 1 × 2 = 2            |
| 3 | 2 × 3 = 6            |

→ `prefix = [1, 1, 2, 6]`

**Suffix array** (right products):
| i | suffix[i]            |
|---|----------------------|
| 3 | 1 (nothing to right) |
| 2 | 1 × 4 = 4            |
| 1 | 4 × 3 = 12           |
| 0 | 12 × 2 = 24          |

→ `suffix = [24, 12, 4, 1]`

**Result:**
| i | prefix[i] | suffix[i] | res[i]      |
|---|-----------|-----------|-------------|
| 0 | 1         | 24        | 1 × 24 = **24** |
| 1 | 1         | 12        | 1 × 12 = **12** |
| 2 | 2         | 4         | 2 × 4  = **8**  |
| 3 | 6         | 1         | 6 × 1  = **6**  |

→ **return [24, 12, 8, 6]**

---

## 3. Prefix & Suffix In-Place — Optimal ✅ (O(n) Time, O(1) Space)

### Code
```java
public int[] productExceptSelf(int[] nums) {
    int n      = nums.length;
    int prefix = 1;
    int suffix = 1;
    int[] res  = new int[n];

    // Pass 1: store prefix products in res
    for (int i = 0; i < n; i++) {
        res[i]  = prefix;
        prefix *= nums[i];
    }

    // Pass 2: multiply by suffix products in-place
    for (int i = n - 1; i >= 0; i--) {
        res[i] *= suffix;
        suffix *= nums[i];
    }

    return res;
}
```

### Explanation
- Same idea as Approach 2 but uses **no extra arrays** — only two variables (`prefix`, `suffix`) and the result array.

**Pass 1 — Left to Right (Prefix):**
- `res[i]` stores the product of everything to its **left**.
- Running `prefix` variable is multiplied by `nums[i]` after storing.

**Pass 2 — Right to Left (Suffix):**
- `res[i]` is multiplied by the running `suffix` (product of everything to its **right**).
- Running `suffix` variable is multiplied by `nums[i]` after updating.

### Dry Run
**Input:** `nums = [1, 2, 3, 4]`

**Pass 1 → Prefix Products:**
| i | res[i] = prefix | prefix after (×nums[i]) |
|---|-----------------|-------------------------|
| 0 | **1**           | 1 × 1 = 1               |
| 1 | **1**           | 1 × 2 = 2               |
| 2 | **2**           | 2 × 3 = 6               |
| 3 | **6**           | 6 × 4 = 24              |

→ `res = [1, 1, 2, 6]`

**Pass 2 → Multiply by Suffix:**
| i | res[i] × suffix | suffix after (×nums[i]) | res[i] |
|---|-----------------|-------------------------|--------|
| 3 | 6 × **1** = 6   | 1 × 4 = 4               | **6**  |
| 2 | 2 × **4** = 8   | 4 × 3 = 12              | **8**  |
| 1 | 1 × **12** = 12 | 12 × 2 = 24             | **12** |
| 0 | 1 × **24** = 24 | 24 × 1 = 24             | **24** |

→ **return [24, 12, 8, 6]** ✓

---

## Summary Table

| Approach                   | Time Complexity | Space Complexity | Notes                              |
|----------------------------|----------------|------------------|------------------------------------|
| Brute Force                | O(n²)          | O(1)             | Nested loop, too slow              |
| Prefix + Suffix Arrays     | O(n)           | O(n)             | Intuitive but uses extra arrays    |
| Prefix + Suffix In-Place   | O(n)           | O(1) ✅          | Best — only 2 extra variables      |
