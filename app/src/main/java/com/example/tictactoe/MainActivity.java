package com.example.tictactoe;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button[][] buttons = new Button[3][3];
    private boolean turn; //0 = P1 turn, 1 = P2 turn
    private int count;
    private TextView textViewGameStatus;

    //is there a better way to use string references in concatenations? look this up later if there's time
    private String winString;
    private String drawString;
    private String turnString;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater findMenuItems = getMenuInflater();
        findMenuItems.inflate(R.menu.activity_tictactoe, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        winString = getResources().getString(R.string.gameStatusWinString);
        drawString = getResources().getString(R.string.gameStatusDrawString);
        turnString = getResources().getString(R.string.gameStatusTurnString);
        textViewGameStatus = findViewById(R.id.textViewGameStatus);

//        buttons[0][0] = findViewById(R.id.button00);
//        buttons[0][1] = findViewById(R.id.button01);
//        buttons[0][2] = findViewById(R.id.button02);
//        buttons[1][0] = findViewById(R.id.button10);
//        buttons[1][1] = findViewById(R.id.button11);
//        buttons[1][2] = findViewById(R.id.button12);
//        buttons[2][0] = findViewById(R.id.button20);
//        buttons[2][1] = findViewById(R.id.button21);
//        buttons[2][2] = findViewById(R.id.button22);
//
//        Found better way of doing this with for loops

        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++) {

                int resID = getResources().getIdentifier("button" + i+j, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //handles menu options
        switch (item.getItemId()) {
            case R.id.menuSettings:
                Toast.makeText(this, "Settings",
                        Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menuNewGame:
                newGame();
                return true;
            case R.id.menuExit:
                System.exit(0);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v) { //onClick for game buttons

        if (!turn){ //Player 1
            ((Button) v).setText("X");
            ((Button) v).setEnabled((false));
            count++;
        }
        else{ //Player 2
            ((Button) v).setText("O");
            ((Button) v).setEnabled((false));
            count++;
        }

        if (checkWin()){ //checking for conditions
            if (!turn){
                textViewGameStatus.setText("Player 1" + " " + winString);
                endGame();
                return;
            }
            else {
                textViewGameStatus.setText("Player 2" + " " + winString);
                endGame();
                return;
            }
        }

        if (count == 9){ //checking for draw by seeing if all nine buttons have been clicked
            textViewGameStatus.setText(drawString);
            endGame();
            return;
        }

        turn = !turn; //switching turns
        if (!turn){
            textViewGameStatus.setText("Player 1" + turnString);
        }
        else {
            textViewGameStatus.setText("Player 2" + turnString);
        }
    }

    private boolean checkWin(){
        String [][] game = new String [3][3];

        //populate a 3x3 array with the current text in each button
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                game[i][j] = buttons[i][j].getText().toString();
            }
        }
        //checking all win conditions

        for (int i = 0; i < 3; i++) { //columns
            if (game[i][0].equals(game[i][1])
                    && game[i][0].equals(game[i][2])
                    && !game[i][0].equals("")) {
                return true;
            }
        }

        for (int i = 0; i < 3; i++) { //rows
            if (game[0][i].equals(game[1][i])
                    && game[0][i].equals(game[2][i])
                    && !game[0][i].equals("")) {
                return true;
            }
        }

        if (game[0][0].equals(game[1][1]) //diagonal ➘
                && game[0][0].equals(game[2][2])
                && !game[0][0].equals("")) {
            return true;
        }

        if (game[0][2].equals(game[1][1]) //diagonal ➚
                && game[0][2].equals(game[2][0])
                && !game[0][2].equals("")) {
            return true;
        }

        return false; //no win found
    }

    private void endGame(){ //used to disable all buttons to prevent playing after a win is detected
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setEnabled((false));
            }
        }
    }
    private void newGame(){ //used to reset the text of and enable all buttons for a new game
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
                buttons[i][j].setEnabled((true));
                count = 0;
                turn = false;
                textViewGameStatus.setText("Player 1" + turnString);
            }
        }
    }

}