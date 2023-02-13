package com.exemple.x_o_game;
import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


public class MainActivity extends AppCompatActivity {
    private  boolean turn = true;
    private int numberOfAttempt = 0;
    private final TextView[] Sq = new TextView[9];
    private final ArrayList<Integer> whoIsNotAvailable = new ArrayList<>();
    private final ArrayList<String> ScoreOfX = new ArrayList<>();
    private final ArrayList<String> ScoreOfY = new ArrayList<>();
    private RelativeLayout PopUp;
    private RelativeLayout ExitPopUp;
    private RelativeLayout Profile;
    private RelativeLayout Creator;
    private Boolean Creator_Toggle = true;
    private TextView SoundBtn;
    private MediaPlayer mplayer;
    private Boolean ToggleBtn = true;
    private Boolean ToggleSound = false;

    private int X_score = 0;
    private int Y_score = 0;

    private RelativeLayout Online_o;
    private RelativeLayout Online_x;

    private Drawable inc;
    private Drawable dec;

    private int Index = 0;


    public void Initialize(){
        Sq[0] = (TextView) findViewById(R.id.square_1);
        Sq[1] = (TextView) findViewById(R.id.square_2);
        Sq[2] = (TextView) findViewById(R.id.square_3);
        Sq[3] = (TextView) findViewById(R.id.square_4);
        Sq[4] = (TextView) findViewById(R.id.square_5);
        Sq[5] = (TextView) findViewById(R.id.square_6);
        Sq[6] = (TextView) findViewById(R.id.square_7);
        Sq[7] = (TextView) findViewById(R.id.square_8);
        Sq[8] = (TextView) findViewById(R.id.square_9);

        PopUp = findViewById(R.id.pupUpRelativeLayout);

        SoundBtn = (TextView) findViewById(R.id.btn_sound);

        mplayer = MediaPlayer.create(this, R.raw.calm_song);

        inc = SoundBtn.getContext().getResources().getDrawable( R.drawable.sound_up );
        dec = SoundBtn.getContext().getResources().getDrawable( R.drawable.sound_down );

        Online_o = (RelativeLayout) findViewById(R.id.player_o_online);
        Online_x = (RelativeLayout) findViewById(R.id.player_x_online);

        ExitPopUp = (RelativeLayout) findViewById(R.id.popUpExit);
        Profile = (RelativeLayout) findViewById(R.id.owner_profile);
        Creator = (RelativeLayout) findViewById(R.id.IBA_profile);
    }

    public boolean didHeWin(ArrayList<String> Arr){
        for(int i = 0; i< 9 ; i++)
        {
            if(Collections.frequency(Arr, (i+1)+"") == 3)
                return true;
        }
        return false;
    }

    public void ContinueClickHandler(View view){
        turn = !turn;
        if(turn){
            Online_o.setAlpha(0f);
            Online_x.setAlpha(1f);
        }else{
            Online_o.setAlpha(1f);
            Online_x.setAlpha(0f);
        }
        numberOfAttempt = 0;
        whoIsNotAvailable.clear();
        ScoreOfX.clear();
        ScoreOfY.clear();
        for(TextView TV : Sq)
            TV.setText("");
        PopUp.animate().translationY(-2000f).setDuration(1000);
    }

    public void RestartClickHandler(View view){
        ContinueClickHandler(SoundBtn);
        X_score = 0;
        Y_score = 0;
        TextView score_o_counter = (TextView) findViewById(R.id.player_o_score);
        score_o_counter.setText("0");
        TextView score_x_counter = (TextView) findViewById(R.id.player_x_score);
        score_x_counter.setText("0");
    }

    public void OKExitClickHandler(View view){
        Toast.makeText(MainActivity.this, "IBA: GoodBey :)", Toast.LENGTH_SHORT).show();
        ExitPopUp.animate().translationY(800f).setDuration(1000);
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        finish();
                    }
                }, 500);
    }

    public void ExitClickHandler(View view){
        ExitPopUp.animate().translationY(0f).setDuration(1000);
    }

    public void NOExitClickHandler(View view){
        ExitPopUp.animate().translationY(2200f).setDuration(1000);
    }

    public void StartClickHandler(View view){
        Profile.animate().setDuration(1000).alpha(0f);
        Creator.animate().setDuration(1000).translationY(-2500);
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        Profile.setVisibility(View.GONE);
                    }
                }, 1000);
    }

    public void playSound() {
        if(ToggleSound){
            MediaPlayer Sound = MediaPlayer.create(this, R.raw.chup_sound);
            Sound.start();
        }
    }

    public void ChangeImageHandler(View view){
        int []Images = {
                R.drawable.photo_1,
                R.drawable.photo_2,
                R.drawable.photo_3,
                R.drawable.photo_4,
                R.drawable.photo_5};
        ImageView FirstImage = (ImageView) findViewById(R.id.first_img);
        ImageView SecondImage = (ImageView) findViewById(R.id.seconde_img);

        FirstImage.setImageResource(Images[Index]);
        FirstImage.setRotation(0f);FirstImage.setScaleX(1f);FirstImage.setScaleY(1f);
        FirstImage.animate().scaleX(0f).scaleY(0f).rotation(1800f).setDuration(800);
        Index = (Index+1)%Images.length;
        SecondImage.setImageResource(Images[Index]);
        SecondImage.setAlpha(0f);
        SecondImage.animate().alpha(1).setDuration(3000);
    }

    public void RevealInfoHandler(View view){
        if(Creator_Toggle)
            Creator.animate().setDuration(1000).translationY(0);
        else
            Creator.animate().setDuration(1000).translationY(-2500);
        Creator_Toggle = !Creator_Toggle;
    }

    public void changeContent(int index,String ...ids){
        if(!whoIsNotAvailable.contains(index)) {
            whoIsNotAvailable.add(index);

            if (numberOfAttempt > 9) {
                Toast.makeText(this, "Game is Over( " + numberOfAttempt + " )", Toast.LENGTH_SHORT).show();
            } else {
                numberOfAttempt++;
                playSound();
                if (this.turn) {
                    Sq[index].setText("X");
                    Sq[index].setTextColor(Color.rgb(255, 0, 0));
                    ScoreOfX.addAll(Arrays.asList(ids));
                    Online_o.setAlpha(1f);
                    Online_x.setAlpha(0f);
                } else {
                    Sq[index].setText("O");
                    Sq[index].setTextColor(Color.rgb(0, 255, 0));
                    ScoreOfY.addAll(Arrays.asList(ids));
                    Online_o.setAlpha(0f);
                    Online_x.setAlpha(1f);
                }
                if( numberOfAttempt >= 5 ){

                    if(turn){
                        if(didHeWin(ScoreOfX)){
                            numberOfAttempt = 9;
                            TextView tv = (TextView)findViewById(R.id.popUpTitle);
                            tv.setTextColor(Color.rgb(255,255,255));
                            tv.setText("Congratulations 'X' you are the winner");
                            PopUp.animate().translationY(0).setDuration(500);
                            TextView score = (TextView) findViewById(R.id.player_x_score);
                            score.setText(""+(++X_score));
                        }
                    }else{
                        if(didHeWin(ScoreOfY)){
                            numberOfAttempt = 9;
                            TextView tv = (TextView)findViewById(R.id.popUpTitle);
                            tv.setTextColor(Color.rgb(255,255,255));
                            tv.setText("Congratulations 'O' you are the winner");
                            PopUp.animate().translationY(0).setDuration(500);
                            TextView score = (TextView) findViewById(R.id.player_o_score);
                            score.setText(""+(++Y_score));
                        }
                    }

                    if( !didHeWin(ScoreOfX) && !didHeWin(ScoreOfY) && numberOfAttempt == 9 )
                    {
                        TextView tv = (TextView)findViewById(R.id.popUpTitle);
                        tv.setText("Draw : no winner no loser");
                        tv.setTextColor(Color.rgb(225,10,10));
                        PopUp.animate().translationY(0).setDuration(500);
                    }
                }
                this.turn = !this.turn;
            }
        }
    }

    public void MuteSoundHandler(View view){
        if(ToggleBtn){
            SoundBtn.setCompoundDrawablesWithIntrinsicBounds(inc,null,null,null);
            mplayer.start();
            Toast.makeText(this, "Play sound", Toast.LENGTH_SHORT).show();
        }else {
            SoundBtn.setCompoundDrawablesWithIntrinsicBounds(dec,null,null,null);
            mplayer.pause();
            Toast.makeText(this, "Stop sound", Toast.LENGTH_SHORT).show();
        }
        ToggleSound = ToggleBtn;
        ToggleBtn = !ToggleBtn;
    }

    public void oneHandler(View view){
        changeContent(0,"1","4","7");
    }
    public void twoHandler(View view){
        changeContent(1,"1","5");
    }
    public void threeHandler(View view){
        changeContent(2,"1","6","8");
    }
    public void fourHandler(View view){
        changeContent(3,"2","4");
    }
    public void fiveHandler(View view){
        changeContent(4,"2","5","7","8");
    }
    public void sixHandler(View view){
        changeContent(5,"2","6");
    }
    public void sevenHandler(View view){
        changeContent(6,"3","4","8");
    }
    public void eightHandler(View view){
        changeContent(7,"3","5");
    }
    public void nineHandler(View view){
        changeContent(8,"3","6","7");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Initialize();

    }
}