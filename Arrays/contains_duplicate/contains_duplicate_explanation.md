# Contains Duplicate: Code Explanation & Dry Run

LeetCode #217 | [Problem Link](https://leetcode.com/problems/contains-duplicate/)

Given an integer array `nums`, return `true` if any value appears **at least twice**, and `false` if every element is **distinct**.

---

## 1. Brute Force Approach (O(n²) Time, O(1) Space)

### Code
```java
public boolean containsDuplicate(int[] nums) {
    for (int i = 0; i < nums.length; i++) {
        for (int j = i + 1; j < nums.length; j++) {
            if (nums[i] == nums[j]) {
                return true;
            }
        }
    }
    return false;
}
```

### Explanation
- Compare every possible pair `(i, j)` in the array.
- If any two elements are equal, a duplicate exists → return `true`.
- If no duplicate found after all pairs, return `false`.

### Dry Run
**Input:** `nums = [1, 2, 3, 1]`

| i | j | nums[i] | nums[j] | Match? |
|---|---|---------|---------|--------|
| 0 | 1 | 1       | 2       | ✗      |
| 0 | 2 | 1       | 3       | ✗      |
| 0 | 3 | 1       | 1       | ✓ → return `true` |

---

## 2. Sort + Adjacent Check Approach (O(n log n) Time, O(n) Space)

### Code
```java
public boolean containsDuplicate(int[] nums) {
    Arrays.sort(nums);       // O(n log n)
    for (int i = 0; i < nums.length - 1; i++) {
        if (nums[i] == nums[i + 1]) {
            return true;
        }
    }
    return false;
}
```

### Explanation
- Sort the array first. After sorting, any duplicate elements will be **adjacent** to each other.
- Iterate through the sorted array and check if any two consecutive elements are equal.
- If yes, return `true`. Otherwise return `false`.

> **Note:** Java's `Arrays.sort()` for primitives uses a dual-pivot QuickSort which is O(n log n) time and O(log n) space.

### Dry Run
**Input:** `nums = [1, 2, 3, 1]`

After sort → `[1, 1, 2, 3]`

| i | nums[i] | nums[i+1] | Match? |
|---|---------|-----------|--------|
| 0 | 1       | 1         | ✓ → return `true` |

---

## 3. HashSet Approach — Optimal (O(n) Time, O(n) Space)

### Code
```java
public boolean containsDuplicate(int[] nums) {
    Set<Integer> visited = new HashSet<>();
    for (int i = 0; i < nums.length; i++) {
        if (visited.contains(nums[i])) {
            return true;
        }
        visited.add(nums[i]);
    }
    return false;
}
```

### Explanation
- Use a `HashSet` to track all elements seen so far.
- For each element:
  - If it's **already in the set** → duplicate found → return `true`.
  - Otherwise → add it to the set and continue.
- If the loop ends with no duplicates found, return `false`.

### Dry Run
**Input:** `nums = [1, 2, 3, 1]`

| i | num | In Set? | Set After         |
|---|-----|---------|-------------------|
| 0 | 1   | ✗       | {1}               |
| 1 | 2   | ✗       | {1, 2}            |
| 2 | 3   | ✗       | {1, 2, 3}         |
| 3 | 1   | ✓       | → return `true`   |

---

## Summary Table

| Approach           | Time Complexity | Space Complexity | Notes                        |
|--------------------|----------------|------------------|------------------------------|
| Brute Force        | O(n²)          | O(1)             | Simple, slow for large input |
| Sort + Adj Check   | O(n log n)     | O(n)             | Modifies original array      |
| HashSet (Optimal)  | O(n)           | O(n)             | Best overall performance ✅  |
