package id.putraprima.skorbola;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.service.autofill.FieldClassification;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import id.putraprima.skorbola.model.Match;

import static id.putraprima.skorbola.MainActivity.AWAYLOGO_KEY;
import static id.putraprima.skorbola.MainActivity.AWAYTEAM_KEY;
import static id.putraprima.skorbola.MainActivity.HOMELOGO_KEY;
import static id.putraprima.skorbola.MainActivity.HOMETEAM_KEY;

public class MatchActivity extends AppCompatActivity {
    public static final String DATA_KEY = "data";
    public static final String ADD_KEY = "add";

    TextView tvScoreHome, tvGoalHome;
    TextView tvScoreAway, tvGoalAway;

    TextView tvHome;
    TextView tvAway;

    ImageView ivHome;
    ImageView ivAway;

    Match match;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_match);

        tvHome = findViewById(R.id.txt_home);
        tvAway = findViewById(R.id.txt_away);

        tvScoreHome = findViewById(R.id.score_home);
        tvScoreAway = findViewById(R.id.score_away);

        ivHome = findViewById(R.id.home_logo);
        ivAway = findViewById(R.id.away_logo);
        initData();

        //TODO
        //1.Menampilkan detail match sesuai data dari main activity
        //2.Tombol add score menambahkan memindah activity ke scorerActivity dimana pada scorer activity di isikan nama pencetak gol
        //3.Dari activity scorer akan mengirim kembali ke activity matchactivity otomatis nama pencetak gol dan skor bertambah +1
        //4.Tombol Cek Result menghitung pemenang dari kedua tim dan mengirim nama pemenang beserta nama pencetak gol ke ResultActivity, jika seri di kirim text "Draw",
    }

    private void initData() {
        Bundle extras = getIntent().getExtras();
        match = getIntent().getParcelableExtra(DATA_KEY);
        if (extras != null)
        {
            tvHome.setText(match.getHomeTeam());
            tvAway.setText(match.getAwayTeam());
            tvScoreHome.setText(String.valueOf(match.getHomeScore()));
            tvScoreAway.setText(String.valueOf(match.getAwayScore()));
            Bitmap bitmapHome = null;
            Bitmap bitmapAway = null;
            try
            {
                bitmapHome = MediaStore.Images.Media.getBitmap(this.getContentResolver(), match.getHomeLogo());
                bitmapAway = MediaStore.Images.Media.getBitmap(this.getContentResolver(), match.getAwayLogo());
            } catch (Exception e) {
                Toast.makeText(this, "Failed to load images", Toast.LENGTH_SHORT).show();
            }
            ivHome.setImageBitmap(bitmapHome);
            ivAway.setImageBitmap(bitmapAway);
        }
    }

    public void handleCekHasil(View view) {
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra(DATA_KEY, (Parcelable) match);
        startActivity(intent);
    }


    public void handleHomeScore(View view) {
        Intent i = new Intent(this, ScorerActivity.class);
        startActivityForResult(i, 1);

    }

    public void handleAwayScore(View view) {
        Intent i = new Intent(this, ScorerActivity.class);
        startActivityForResult(i, 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            return;
        }
        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                match.addHomeScore(data.getStringExtra(ADD_KEY));
                tvScoreHome.setText(String.valueOf(match.getHomeScore()));
//                tvGoalHome.setText(match.homeScorer());
                Log.d("who?", "scorer is " + match.getHomeScorer());
            }
        }else if(requestCode == 2){
            if(resultCode == RESULT_OK) {
                match.addAwayScore(data.getStringExtra(ADD_KEY));
                tvScoreAway.setText(String.valueOf(match.getAwayScore()));
//                tvGoalAway.setText(match.awayScorer());
                Log.d("who?", "scorer is " + match.getHomeScorer());
            }
        }
    }
}
