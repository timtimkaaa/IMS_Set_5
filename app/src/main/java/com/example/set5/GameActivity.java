package com.example.set5;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameActivity extends Activity {
    // Door flags used in each maze cell.
    private static final int LEFT = 1;
    private static final int RIGHT = 2;
    private static final int UP = 4;
    private static final int DOWN = 8;
    private static final int START = 16;

    // Row 1, column 0 is the start room: 28 = 16 start marker + 12 movement bits.
    private static final int[][] MAZE = {
            {10, 8, 10, 9},
            {28, 1, 0, 12},
            {12, 10, 9, 13},
            {6, 5, 6, 5}
    };

    private int currentRow;
    private int currentColumn;

    private TextView roomLabel;
    private TextView roomValueLabel;
    private Button upButton;
    private Button downButton;
    private Button leftButton;
    private Button rightButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        roomLabel = findViewById(R.id.roomLabel);
        roomValueLabel = findViewById(R.id.roomValueLabel);
        upButton = findViewById(R.id.upButton);
        downButton = findViewById(R.id.downButton);
        leftButton = findViewById(R.id.leftButton);
        rightButton = findViewById(R.id.rightButton);

        findStartRoom();
        setButtonListeners();
        updateRoom();
    }

    private void findStartRoom() {
        // The start marker is separate from movement and is found with bit 16.
        for (int row = 0; row < MAZE.length; row++) {
            for (int column = 0; column < MAZE[row].length; column++) {
                if ((MAZE[row][column] & START) != 0) {
                    currentRow = row;
                    currentColumn = column;
                    return;
                }
            }
        }

        currentRow = 0;
        currentColumn = 0;
    }

    private void setButtonListeners() {
        upButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                move(UP, -1, 0);
            }
        });

        downButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                move(DOWN, 1, 0);
            }
        });

        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                move(LEFT, 0, -1);
            }
        });

        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                move(RIGHT, 0, 1);
            }
        });
    }

    private void move(int direction, int rowChange, int columnChange) {
        // Block moves that are missing a door or would leave the maze grid.
        if (!canMove(direction, rowChange, columnChange)) {
            updateRoom();
            return;
        }

        currentRow += rowChange;
        currentColumn += columnChange;

        // A room value of 0 is the finish room.
        if (MAZE[currentRow][currentColumn] == 0) {
            startActivity(new Intent(this, ResultActivity.class));
            return;
        }

        updateRoom();
    }

    private boolean canMove(int direction, int rowChange, int columnChange) {
        int roomValue = MAZE[currentRow][currentColumn];
        int nextRow = currentRow + rowChange;
        int nextColumn = currentColumn + columnChange;

        // Only door bits 1, 2, 4, and 8 are used for movement.
        return (roomValue & direction) != 0 && isInsideMaze(nextRow, nextColumn);
    }

    private boolean isInsideMaze(int row, int column) {
        return row >= 0 && row < MAZE.length && column >= 0 && column < MAZE[row].length;
    }

    private void updateRoom() {
        // Refresh the room text and color/enable each direction button.
        int roomValue = MAZE[currentRow][currentColumn];
        roomLabel.setText("Room: row " + currentRow + ", column " + currentColumn);
        roomValueLabel.setText("Room value: " + roomValue);

        updateDirectionButton(upButton, canMove(UP, -1, 0));
        updateDirectionButton(downButton, canMove(DOWN, 1, 0));
        updateDirectionButton(leftButton, canMove(LEFT, 0, -1));
        updateDirectionButton(rightButton, canMove(RIGHT, 0, 1));
    }

    private void updateDirectionButton(Button button, boolean available) {
        button.setEnabled(available);
        if (available) {
            button.setBackgroundColor(Color.rgb(46, 125, 50));
            button.setTextColor(Color.WHITE);
        } else {
            button.setBackgroundColor(Color.rgb(158, 158, 158));
            button.setTextColor(Color.rgb(66, 66, 66));
        }
    }
}
