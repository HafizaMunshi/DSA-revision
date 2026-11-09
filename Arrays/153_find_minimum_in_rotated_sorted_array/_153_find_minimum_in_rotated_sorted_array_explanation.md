# LeetCode #153 — Find Minimum in Rotated Sorted Array: Code Explanation & Dry Run

**Problem Link:** [leetcode.com/problems/find-minimum-in-rotated-sorted-array](https://leetcode.com/problems/find-minimum-in-rotated-sorted-array/)

A sorted array of **unique** elements has been rotated between 1 and n times. Find and return the **minimum element**.

> ⚠️ Must run in **O(log n)** time.

**What is rotation?**
```
Original : [0, 1, 2, 4, 5, 6, 7]
Rotated 4: [4, 5, 6, 7, 0, 1, 2]  ← minimum is at the rotation point
```

---

## 1. Brute Force — Linear Search (O(n) Time, O(1) Space)

### Code
```java
public int findMin(int[] nums) {
    int min = nums[0];
    for (int i = 1; i < nums.length; i++) {
        if (nums[i] < min) min = nums[i];
    }
    return min;
}
```

### Explanation
- Scan every element in the array.
- Track the minimum seen so far.
- Simple but does **not** satisfy the O(log n) requirement.

### Dry Run
**Input:** `nums = [4, 5, 6, 7, 0, 1, 2]`

| i | nums[i] | Current Min |
|---|---------|-------------|
| — | —       | 4           |
| 1 | 5       | 4           |
| 2 | 6       | 4           |
| 3 | 7       | 4           |
| 4 | **0**   | **0** ✓     |
| 5 | 1       | 0           |
| 6 | 2       | 0           |

→ **return 0**

---

## 2. Pivot / Drop Point (O(n) Time, O(1) Space)

### Code
```java
public int findMin(int[] nums) {
    for (int i = 0; i < nums.length - 1; i++) {
        if (nums[i] > nums[i + 1]) {
            return nums[i + 1];  // drop point → next is minimum
        }
    }
    return nums[0];  // no rotation, first element is minimum
}
```

### Explanation
- In a rotated sorted array, the minimum is the **only element where a "drop" occurs** — i.e., `nums[i] > nums[i+1]`.
- Scan for this drop point and return the element after it.
- If no drop is found, the array isn't rotated → `nums[0]` is the minimum.

### Dry Run
**Input:** `nums = [4, 5, 6, 7, 0, 1, 2]`

| i | nums[i] | nums[i+1] | Drop?                     |
|---|---------|-----------|---------------------------|
| 0 | 4       | 5         | ✗                         |
| 1 | 5       | 6         | ✗                         |
| 2 | 6       | 7         | ✗                         |
| 3 | **7**   | **0**     | ✓ → return `nums[4]` = **0** |

→ **return 0**

---

## 3. Binary Search — Optimal ✅ (O(log n) Time, O(1) Space)

### Code
```java
public int findMin(int[] nums) {
    int left  = 0;
    int right = nums.length - 1;

    while (left < right) {
        int mid = left + (right - left) / 2;

        if (nums[mid] > nums[right]) {
            left = mid + 1;   // min is in RIGHT half
        } else {
            right = mid;      // min is at mid or in LEFT half
        }
    }

    return nums[left];
}
```

### Explanation

The key insight: compare `nums[mid]` with `nums[right]` to decide which half the minimum is in:

```
Case 1: nums[mid] > nums[right]
  → The left half is sorted (no rotation there), so the
    minimum MUST be in the right half, beyond mid.
  → left = mid + 1

Case 2: nums[mid] <= nums[right]
  → The right half is sorted, so the minimum is either
    at mid or somewhere in the left half.
  → right = mid   (don't exclude mid, it could be the answer)
```

When `left == right`, the search space has converged to 1 element — the minimum.

### Dry Run

**Input:** `nums = [4, 5, 6, 7, 0, 1, 2]`

```
Initial: left=0, right=6
```

| Step | left | right | mid | nums[mid] | nums[right] | Condition      | Action        |
|------|------|-------|-----|-----------|-------------|----------------|---------------|
| 1    | 0    | 6     | 3   | 7         | 2           | 7 > 2 ✓        | left = 4      |
| 2    | 4    | 6     | 5   | 1         | 2           | 1 ≤ 2          | right = 5     |
| 3    | 4    | 5     | 4   | 0         | 1           | 0 ≤ 1          | right = 4     |
| 4    | 4    | 4     | —   | —         | —           | left == right  | **exit loop** |

→ **return nums[4] = 0** ✓

---

**Input:** `nums = [3, 4, 5, 1, 2]`

| Step | left | right | mid | nums[mid] | nums[right] | Condition | Action    |
|------|------|-------|-----|-----------|-------------|-----------|-----------|
| 1    | 0    | 4     | 2   | 5         | 2           | 5 > 2 ✓   | left = 3  |
| 2    | 3    | 4     | 3   | 1         | 2           | 1 ≤ 2     | right = 3 |
| 3    | 3    | 3     | —   | —         | —           | left == right | **exit** |

→ **return nums[3] = 1** ✓

---

**Input:** `nums = [11, 13, 15, 17]` (no rotation)

| Step | left | right | mid | nums[mid] | nums[right] | Condition | Action    |
|------|------|-------|-----|-----------|-------------|-----------|-----------|
| 1    | 0    | 3     | 1   | 13        | 17          | 13 ≤ 17   | right = 1 |
| 2    | 0    | 1     | 0   | 11        | 13          | 11 ≤ 13   | right = 0 |
| 3    | 0    | 0     | —   | —         | —           | left == right | **exit** |

→ **return nums[0] = 11** ✓

---

## Summary Table

| Approach              | Time Complexity | Space Complexity | Notes                              |
|-----------------------|----------------|------------------|------------------------------------|
| Brute Force (Linear)  | O(n)           | O(1)             | Check every element                |
| Pivot / Drop Point    | O(n)           | O(1)             | Stop at first drop nums[i]>nums[i+1] |
| Binary Search         | O(log n)       | O(1) ✅          | Compare mid vs right to halve search |
