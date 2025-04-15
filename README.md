## How It Works

### 1. Segment Tree Construction
The segment tree is built using the `build` method. For example, if there are 5 rows (n = 5) and each row has 10 seats (m = 10), the tree will look like this:

```
Root (0-4): total=50, mx=10
├── Left (0-2): total=30, mx=10
│   ├── Left (0-1): total=20, mx=10
│   │   ├── Left (0-0): total=10, mx=10
│   │   └── Right (1-1): total=10, mx=10
│   └── Right (2-2): total=10, mx=10
└── Right (3-4): total=20, mx=10
    ├── Left (3-3): total=10, mx=10
    └── Right (4-4): total=10, mx=10
```

Each node stores the total and maximum seats for its range, enabling efficient queries and updates.

### 2. `gather(k, maxRow)` Example
Suppose we want to reserve 5 seats (k = 5) in a single row, and the maximum row we can consider is 3 (maxRow = 3).

- The `maxQuery` method traverses the segment tree to find the first row (up to row 3) with at least 5 seats available.
- If row 2 has enough seats, it returns `[2, remainingSeats]` (e.g., `[2, 5]` if row 2 had 10 seats initially).
- The `update` method is called to subtract 5 seats from row 2, updating the segment tree and the seats array.

### 3. `scatter(k, maxRow)` Example
Suppose we want to reserve 12 seats (k = 12) across multiple rows, considering only rows 0 to 3 (maxRow = 3).

- The `sumQuery` method checks if there are at least 12 seats available in rows 0 to 3.
- Starting from row 0, it reserves seats row by row:
  - If row 0 has 10 seats, it reserves all 10 and moves to row 1.
  - It then reserves 2 seats from row 1.
- The `update` method is called for each row to adjust the segment tree and the seats array.

### Benefits of This Approach

#### Efficiency:
- The segment tree allows for efficient range queries (O(log n)) and updates (O(log n)), making it suitable for large-scale systems with many rows and seats.

#### Flexibility:
- The `gather` method supports single-row reservations, while `scatter` handles multi-row reservations, catering to different use cases.

#### Scalability:
- The system can handle a large number of rows (n) and seats per row (m) without performance degradation.

#### Real-Time Updates:
- The segment tree ensures that updates (e.g., reserving seats) propagate efficiently, keeping the data structure consistent.

### Example Usage

```scala
val bms = BookMyShow(5, 10) // 5 rows, 10 seats per row

// Gather 5 seats in a single row, considering rows 0 to 3
println(bms.gather(5, 3)) // Output: List(0, 5)

// Scatter 12 seats across rows 0 to 3
println(bms.scatter(12, 3)) // Output: true

// Check remaining seats
println(bms.gather(5, 3)) // Output: List(1, 3) (row 1 now has 3 seats left)
```

This approach ensures that seat reservations are handled efficiently and accurately, even for large theaters.