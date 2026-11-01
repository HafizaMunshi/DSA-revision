# Two Sum Array: Code Explanation & Dry Run

This notebook demonstrates three approaches to solve the Two Sum problem, where the goal is to find indices of two numbers in an array that add up to a target value.

---

## 1. Brute Force Approach (O(n²) Time, O(1) Space)

### Code
```python
def twosum(arr, target):
  n = len(arr)
  for i in range(n):
    for j in range(i+1, n):
      if target == arr[i] + arr[j]:
        return [i, j]
  return []
```

### Explanation
- Iterate through every possible pair in the array.
- For each pair `(i, j)`, check if their sum equals the target.
- If a pair is found, return their indices.
- If no such pair exists, return an empty list.

### Dry Run
**Input:** `arr = [2, 2, 1, 3, 4]`, `target = 3`

- i = 0, j = 1 → 2 + 2 = 4 (not 3)
- i = 0, j = 2 → 2 + 1 = 3 (**found!**) → returns `[0, 2]`

---

## 2. Hash Map (Memoization) Approach (O(n) Time, O(n) Space)

### Code
```python
def twoSumMemo(arr, target):
  hashmap = {}
  for i, num in enumerate(arr):
    complement = target - num
    if complement in hashmap:
      return [hashmap[complement], i]
    hashmap[num] = i
  return []
```

### Explanation
- Use a hash map to store each number and its index as you iterate.
- For each number, calculate its complement (`target - num`).
- If the complement is already in the map, return the indices.
- Otherwise, add the current number and its index to the map.

### Dry Run
**Input:** `arr = [2, 2, 1, 3, 4]`, `target = 3`

- i = 0, num = 2, complement = 1 (not in hashmap)
  - hashmap: {2: 0}
- i = 1, num = 2, complement = 1 (not in hashmap)
  - hashmap: {2: 1}
- i = 2, num = 1, complement = 2 (2 is in hashmap at index 1)
  - returns `[1, 2]`

---

## 3. Two Pointers Approach (O(n) Time, O(1) Space)

> **Note:** This approach works only if the array is **sorted**.

### Code
```python
def twoSumWithPointers(arr, target):
  left = 0
  right = len(arr) - 1
  while left < right:
    if arr[left] + arr[right] == target:
      return [left, right]
    elif arr[left] + arr[right] < target:
      left += 1
    else:
      right -= 1
  return []
```

### Explanation
- Start with two pointers at the start (`left`) and end (`right`) of the array.
- Check if their sum equals the target:
  - If yes, return their indices.
  - If sum < target, move `left` pointer right.
  - If sum > target, move `right` pointer left.
- Continue until they cross.

### Dry Run
**Input:** `arr = [2, 2, 1, 3, 4]`, `target = 3`

- left = 0 (2), right = 4 (4); 2+4=6 > 3 → right--
- left = 0 (2), right = 3 (3); 2+3=5 > 3 → right--
- left = 0 (2), right = 2 (1); 2+1=3 (**found!**) → returns `[0, 2]`

---

## Summary Table

| Approach        | Time Complexity | Space Complexity | Input Sorted? | Fastest Case |
|-----------------|----------------|------------------|--------------|--------------|
| Brute Force     | O(n²)          | O(1)             | No           | Small arrays |
| Hash Map/Memo   | O(n)           | O(n)             | No           | Large arrays |
| Two Pointers    | O(n)           | O(1)             | Yes          | Sorted arrays|