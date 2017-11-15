package com.naca.navarrocantero.simonSays;

import static com.naca.navarrocantero.simonSays.PlayButton.inMoving;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Handler;
import android.support.v7.app.AlertDialog.Builder;
import android.util.Log;
import android.view.View;
import java.util.ArrayList;

/**
 * Created by navarrocantero on 05/11/2017.
 */

public class Game implements View.OnClickListener {

  // For the Game logic
  static private String TAG = "Game";
  static private String GAME_END = "Game end in level ";
  static int BLUE = R.color.colorBlue;
  static int RED = R.color.colorRed;
  static int GREEN = R.color.colorGreen;
  static int YELLOW = R.color.colorYellow;

  private View actualUserTap;
  private int userTapCount;
  private Boolean isIaMoving;
  private Boolean isPlaying;
  private int iAMovesIndex;
  private ArrayList<View> iaMovs;

  // For the game object
  private PlayButton playButtonOne;
  private PlayButton playButtonTwo;
  private PlayButton playButtonThree;
  private PlayButton playButtonFour;
  private Context context;

  // Constructors

  public Game() {

  }

  public Game(PlayButton playButtonOne, PlayButton playButtonTwo,
      PlayButton playButtonThree, PlayButton playButtonFour, Context context) {
    this.playButtonOne = playButtonOne;
    this.playButtonTwo = playButtonTwo;
    this.playButtonThree = playButtonThree;
    this.playButtonFour = playButtonFour;
    this.context = context;
  }

  // Getters
  public ArrayList<View> getIaMovs() {
    return iaMovs;
  }

  public Boolean getPlaying() {
    return isPlaying;
  }

  // Setters
  public void setIaMovs(ArrayList<View> iaMovs) {
    this.iaMovs = iaMovs;
  }

  public void setPlaying(Boolean playing) {
    isPlaying = playing;
  }

  //Helpers

  @Override
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.btn_one:
      case R.id.btn_two:
      case R.id.btn_three:
      case R.id.btn_four:

        try {
          if (!isIaMoving) {  //this is for block the user actions while the IA is making something
            actualUserTap = view;
            inMoving(view);          //Call to PlayButton's function to simulate move on view
            compareViews(actualUserTap); // if is the same view a count will be count++
            break;
          }
        } catch (ArrayIndexOutOfBoundsException e) {
        }
    }
  }

  // Function called when user taps on view,comparing the view with the IA same
  // pos movement and after evaluating if is the final IA mov.
  //
  //
  private void compareViews(View actualUserTap) {

    View iaActualMov = iaMovs.get(userTapCount);
    if (iaActualMov.equals(actualUserTap)) {
      userTapCount++;
      isIaMoving = false;
      if (userTapCount == iaMovs.size()) {
        iaMovs.add(newRandomMove());
        inizialiteGameValues(iaMovs);
        iaMoving(iAMovesIndex); // Start of next level
        isIaMoving = true;
      }
    } else {
      showGameMenu(GAME_END + "" + iAMovesIndex); // If user fails the game restart
    }
  }

  // Recursive function to print the IA movements
  public void iaMoving(final int index) {

    final Handler handler = new Handler();
    final View thisTap = iaMovs.get(index);
    handler.postDelayed(new Runnable() {
      public void run() {

        //Call to PlayButton's function to simulate click move on view
        inMoving(thisTap);
        iAMovesIndex++;
        Log.d(TAG, "run: insideInmoving iATapIndex " + iAMovesIndex);
        Log.d(TAG, "run: insideInmoving index " + index);

        if (iAMovesIndex < iaMovs.size()) {
          iaMoving(iAMovesIndex);
        } else {
          isIaMoving = false;
          isPlaying = false;
        }
      }
    }, 1000);
  }

  //Function that return one of four views ramdonly
  private View newRandomMove() {
    int randomNumber = (int) (Math.random() * 4 + 1);
    View view = null;
    switch (randomNumber) {
      case 1:
        view = playButtonOne.getView();
        break;
      case 2:
        view = playButtonTwo.getView();
        break;
      case 3:
        view = playButtonThree.getView();
        break;
      case 4:
        view = playButtonFour.getView();
        break;
    }
    return view;
  }

  // Function  call to inizialiteGameValues and iaMoving function
  protected void playGame() {
    inizialiteGameValues();
    iaMoving(iAMovesIndex);
  }

  // Function that initialize all values to the start's values.
  // If the game is in level > 1, the iaMovsArgs will be the used
  // instead a new Object
  protected void inizialiteGameValues(ArrayList<View>... iaMovsArg) {
    iaMovs = (new ArrayList<View>());

    if (iaMovs.isEmpty()) {
      iaMovs.add(newRandomMove());
    }

    try {
      if (iaMovsArg[0] != null) {
        iaMovs = iaMovsArg[0];
      } else {
        iaMovs.add(newRandomMove());
      }
    } catch (ArrayIndexOutOfBoundsException e) {
    }
    iAMovesIndex = iaMovs.size();
    iAMovesIndex = 0;
    userTapCount = 0;
    isPlaying = true;
    isIaMoving = true;
    this.playButtonOne.getView().setOnClickListener(this);
    this.playButtonTwo.getView().setOnClickListener(this);
    this.playButtonThree.getView().setOnClickListener(this);
    this.playButtonFour.getView().setOnClickListener(this);
  }

  // Function that shows popup when the game is finished showing the user puntuation
  // and rebooting game when user click it
  protected void showGameMenu(String string) {
    new Builder(context).setTitle(R.string.simon).setMessage(string)
        .setPositiveButton(android.R.string.ok, new OnClickListener() {
          @Override
          public void onClick(DialogInterface dialogInterface, int i) {
            playGame();
            isIaMoving = true;
          }
        }).show();
  }

}
