# LeetCode #219 — Contains Duplicate II: Code Explanation & Dry Run

**Problem Link:** [leetcode.com/problems/contains-duplicate-ii](https://leetcode.com/problems/contains-duplicate-ii/)

Given an integer array `nums` and an integer `k`, return `true` if there exist two **distinct indices** `i` and `j` such that:
- `nums[i] == nums[j]` — same value
- `abs(i - j) <= k` — within distance `k`

---

## 1. Brute Force Approach (O(n²) Time, O(1) Space)

### Code
```java
public boolean containsNearbyDuplicate(int[] nums, int k) {
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
```

### Explanation
- For every index `i`, look at all indices `j` from `i+1` up to `i+k` (no need to go further since `abs(i-j) > k` would be false anyway).
- If any `nums[i] == nums[j]` within that window, return `true`.
- If no such pair is found after all iterations, return `false`.

### Dry Run
**Input:** `nums = [1, 2, 3, 1]`, `k = 3`

| i | j | nums[i] | nums[j] | Equal? | \|i-j\| ≤ k? |
|---|---|---------|---------|--------|--------------|
| 0 | 1 | 1       | 2       | ✗      | —            |
| 0 | 2 | 1       | 3       | ✗      | —            |
| 0 | 3 | 1       | 1       | ✓      | \|0-3\|=3 ≤ 3 ✓ → **return true** |

---

## 2. HashMap Approach — Last Seen Index (O(n) Time, O(n) Space)

### Code
```java
public boolean containsNearbyDuplicate(int[] nums, int k) {
    HashMap<Integer, Integer> map = new HashMap<>();  // num → last seen index

    for (int i = 0; i < nums.length; i++) {
        if (map.containsKey(nums[i])) {
            if (i - map.get(nums[i]) <= k) {
                return true;
            }
        }
        map.put(nums[i], i);  // update to most recent index
    }
    return false;
}
```

### Explanation
- Use a `HashMap` to store each number mapped to its **most recently seen index**.
- For each element at index `i`:
  - If it's already in the map, check if the distance (`i - lastIndex`) is `<= k`.
  - If yes → return `true`.
  - Always update the map with the **current index** (to keep it fresh).

### Dry Run
**Input:** `nums = [1, 2, 3, 1]`, `k = 3`

| i | num | In Map? | Distance | ≤ k? | Map After      |
|---|-----|---------|----------|------|----------------|
| 0 | 1   | ✗       | —        | —    | {1→0}          |
| 1 | 2   | ✗       | —        | —    | {1→0, 2→1}     |
| 2 | 3   | ✗       | —        | —    | {1→0, 2→1, 3→2}|
| 3 | 1   | ✓ (idx=0)| 3-0=3  | ✓    | → **return true** |

---

## 3. HashSet Sliding Window — Optimal (O(n) Time, O(k) Space)

### Code
```java
public boolean containsNearbyDuplicate(int[] nums, int k) {
    Set<Integer> window = new HashSet<>();

    for (int i = 0; i < nums.length; i++) {
        if (window.contains(nums[i])) {
            return true;
        }
        window.add(nums[i]);

        // Remove element that slides out of window
        if (window.size() > k) {
            window.remove(nums[i - k]);
        }
    }
    return false;
}
```

### Explanation
- Maintain a `HashSet` as a **sliding window** of size at most `k`.
- For each element at index `i`:
  - If it's already in the window → a duplicate exists within distance `k` → return `true`.
  - Add the current element to the window.
  - If the window grows beyond size `k`, remove the oldest element (`nums[i - k]`) to keep it bounded.
- This is the most space-efficient O(n) approach — the set never holds more than `k+1` elements.

### Dry Run
**Input:** `nums = [1, 2, 3, 1]`, `k = 3`

| i | num | In Window? | Window After | Size > k? |
|---|-----|-----------|--------------|-----------|
| 0 | 1   | ✗         | {1}          | 1 > 3? No |
| 1 | 2   | ✗         | {1, 2}       | 2 > 3? No |
| 2 | 3   | ✗         | {1, 2, 3}    | 3 > 3? No |
| 3 | 1   | ✓         | → **return true** | — |

**Input:** `nums = [1, 2, 3, 1, 2, 3]`, `k = 2`

| i | num | In Window? | Window After        | Remove?     |
|---|-----|-----------|---------------------|-------------|
| 0 | 1   | ✗         | {1}                 | No          |
| 1 | 2   | ✗         | {1, 2}              | No          |
| 2 | 3   | ✗         | {1, 2, 3}           | No          |
| 3 | 1   | ✗         | {1, 2, 3} → add 1 → remove nums[1]=2 → {1, 3} | Yes (remove 2) |
| 4 | 2   | ✗         | {1, 3} → add 2 → remove nums[2]=3 → {1, 2} | Yes (remove 3) |
| 5 | 3   | ✗         | {1, 2} → add 3 → remove nums[3]=1 → {2, 3} | Yes (remove 1) |

→ Loop ends, **return false** ✓

---

## Summary Table

| Approach                | Time Complexity | Space Complexity | Notes                              |
|-------------------------|----------------|------------------|------------------------------------|
| Brute Force             | O(n²)          | O(1)             | Simple but slow for large input    |
| HashMap (Last Index)    | O(n)           | O(n)             | Stores all seen elements           |
| HashSet Sliding Window  | O(n)           | O(k) ✅          | Best — window bounded by k         |
