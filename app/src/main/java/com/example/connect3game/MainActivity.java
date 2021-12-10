package com.example.connect3game;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.gridlayout.widget.GridLayout;


import java.util.Arrays;

public class MainActivity extends AppCompatActivity {


    boolean gameActive = true;
    int[] gameState = new int[9];
    boolean hasEmpty = true;
    int[][] winningPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};
    int activePlayer = 1; // 0:Empty , 1:Yellow , 2:Red


    public boolean isEmpty() {
        for (int i : gameState) {
            if (i == 0) {
                hasEmpty = true;
                return true;
            }
        }
        hasEmpty = false;
        return false;
    }

    public void dropIn(View view) {

        if (isEmpty()) {
            ImageView counter = (ImageView) view;
            int tappedCounter = Integer.parseInt(counter.getTag().toString());
            if (gameState[tappedCounter] == 0 && gameActive && hasEmpty) {
                gameState[tappedCounter] = activePlayer;
                counter.setTranslationY(-1500);
                if (activePlayer == 1) {
                    counter.setImageResource(R.drawable.yellow);
                    activePlayer = 2;
                } else if (activePlayer == 2) {
                    counter.setImageResource(R.drawable.red);
                    activePlayer = 1;
                }
                counter.animate().translationYBy(1500).rotation(3600).setDuration(300);

                for (int[] position : winningPositions) {

                    if (gameState[position[0]] == gameState[position[1]] && gameState[position[1]] == gameState[position[2]] && gameState[position[0]] != 0) {
                        gameActive = false;
                        String winner;
                        if (gameState[position[0]] == 1) {
                            winner = "Yellow";
                        } else {
                            winner = "Red";
                        }
                        Button playAgainButton = (Button) findViewById(R.id.playAgainButton);
                        TextView winnerTextView = (TextView) findViewById(R.id.textView);
                        winnerTextView.setText(winner + " has won");

                        playAgainButton.setVisibility(View.VISIBLE);
                        winnerTextView.setVisibility(View.VISIBLE);

                    }
                }

            } else {
                String message = "";
                if (!gameActive) {
                    message = "game is over try another game";
                } else if (gameState[tappedCounter] != 0) {
                    message = "Box already occupied try another box";
                }
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            }
        } else {
            Button playAgainButton = (Button) findViewById(R.id.playAgainButton);
            TextView winnerTextView = (TextView) findViewById(R.id.textView);
            winnerTextView.setText("draw try another game");
            playAgainButton.setVisibility(View.VISIBLE);
            winnerTextView.setVisibility(View.VISIBLE);
        }
    }


    public void playAgain(View view) {

        Button playAgainButton = (Button) findViewById(R.id.playAgainButton);
        TextView winnerTextView = (TextView) findViewById(R.id.textView);

        playAgainButton.setVisibility(View.INVISIBLE);
        winnerTextView.setVisibility(View.INVISIBLE);

        GridLayout gridLayout = (GridLayout) findViewById(R.id.grid);
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            ImageView counter = (ImageView) gridLayout.getChildAt(i);
            counter.setImageDrawable(null);
        }


        gameActive = true;
        Arrays.fill(gameState, 0);
        activePlayer = 1; // 0:Empty , 1:Yellow , 2:Red
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}


