package com.naca.navarrocantero.simonSays;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


  final private String TAG = "MainActivity";
  final int BLUE = R.color.colorBlue;
  final int RED = R.color.colorRed;
  final int GREEN = R.color.colorGreen;
  final int YELLOW = R.color.colorYellow;

  private boolean firstMenuVal;

  private ArrayList<View> iAmovs;
  private int iAMovsIndex;

  private PlayButton playButtonOne;
  private PlayButton playButtonTwo;
  private PlayButton playButtonThree;
  private PlayButton playButtonFour;
  private Game game;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    getSupportActionBar().hide();
    showGameMenu();

  }

  protected void initializeValues() {

    firstMenuVal = false;

    playButtonOne = new PlayButton(BLUE, (findViewById(R.id.btn_one)));
    playButtonTwo = new PlayButton(RED, (findViewById(R.id.btn_two)));
    playButtonThree = new PlayButton(YELLOW, (findViewById(R.id.btn_three)));
    playButtonFour = new PlayButton(GREEN, (findViewById(R.id.btn_four)));

    game = new Game(
        playButtonOne, playButtonTwo,
        playButtonThree, playButtonFour,this);
  }

  private void showGameMenu() {

    initializeValues();   //new game object
      showAlertMenu();
  }

  // Function that shows popup, and when click it, game start
  public void showAlertMenu() {

    do {
      new AlertDialog.Builder(MainActivity.this).setTitle(
          R.string.simon).setMessage(
          getResources().getText(R.string.about))
          .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
              firstMenuVal = false;
              if (!firstMenuVal) {

                game.playGame();
              }
            }
          }).show();
    } while (firstMenuVal);
  }
}