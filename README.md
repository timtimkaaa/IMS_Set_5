# Introduction to Mobile Systems — Set 5

## Student Information
- **Student Name:** Tsimafei Mardashou
- **Student ID:** 46011

## Short Description
This Android maze game lets the player navigate between rooms using left, right, up, and down buttons. The maze is stored as a 2D integer grid, where each room uses bitmask values to define available exits, and the start room is marked with bit 16. The game blocks invalid moves, highlights available directions, and shows a win screen when the player reaches the finish room with value 0.

## Implementation Notes
- The maze is implemented in Java in `GameActivity`.
- The start room is row 1, column 0, with value 28 (`16 + 12`).
- Movement uses only door bits 1, 2, 4, and 8.
- The result screen provides options to restart the game or return to the main menu.
