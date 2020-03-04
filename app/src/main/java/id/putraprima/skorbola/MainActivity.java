package id.putraprima.skorbola;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.service.autofill.FieldClassification;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;

import static android.os.Build.VERSION_CODES.M;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "soccer";

    public static final String DATA_KEY = "data";
    public static final String SCORE_KEY = "score";

    public static final String HOMETEAM_KEY = "home_team";
    public static final String AWAYTEAM_KEY = "away_team";

    public static final String HOMELOGO_KEY = "home_logo";
    public static final String AWAYLOGO_KEY = "away_logo";

    private Uri homeUri = null;
    private Uri awayUri = null;

    private EditText hometeamInput;
    private EditText awayteamInput;

    private ImageView homelogo;
    private ImageView awaylogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hometeamInput = findViewById(R.id.home_team);
        awayteamInput = findViewById(R.id.away_team);

        homelogo = findViewById(R.id.home_logo);
        awaylogo = findViewById(R.id.away_logo);
        //TODO
        //Fitur Main Activity
        //1. Validasi Input Home Team
        //2. Validasi Input Away Team
        //3. Ganti Logo Home Team
        //4. Ganti Logo Away Team
        //5. Next Button Pindah Ke MatchActivity
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_CANCELED)
        {
            return;
        }

        if (requestCode == 1) {
            if (data != null) {
                try {
                    homeUri = data.getData();
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), homeUri);
                    homelogo.setImageBitmap(bitmap);
                } catch (IOException e) {
                    Toast.makeText(this, "Gagal memuat Gambar", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, e.getMessage());
                }
            }
        }
        if (requestCode == 2) {
            if (data != null) {
                try {
                    awayUri = data.getData();
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), awayUri);
                    awaylogo.setImageBitmap(bitmap);
                } catch (IOException e) {
                    Toast.makeText(this, "Gagal memuat Gambar", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, e.getMessage());
                }
            }
        }
    }

    private boolean checkAway(String away) {
        if(away.isEmpty()){
            awayteamInput.setError("Isi nama home team :)");
            return false;
        }else{
            return true;
        }
    }

    private boolean checkHome(String home) {
        if(home.isEmpty()){
            hometeamInput.setError("Isi nama away team :)");
            return false;
        }else{
            return true;
        }
    }

    public void handleHomeLogo(View view) {
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, 1);

    }

    public void handleAwayLogo(View view) {
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, 2);
    }


    public void handelNext(View view) {
        int errorCount = 4;
        String home = hometeamInput.getText().toString().trim();
        String away = awayteamInput.getText().toString().trim();
        Intent intent = new Intent(this, MatchActivity.class);

        if(checkHome(home)){
            errorCount -= 1;
        }

        if(checkAway(away)){
            errorCount -= 1;
        }

        if(homeUri != null){
            errorCount -= 1;
        }else{
            //            HandleHomeImage(view);
            Toast.makeText(this, "Tambahkan Logo "+ home +" :)", Toast.LENGTH_SHORT).show();
        }

        if(awayUri != null){
            errorCount -= 1;
        }else{
            //            HandleAwayImage(view);
            Toast.makeText(this, "Tambahkan Logo "+ away +" :)", Toast.LENGTH_SHORT).show();
        }
//        if(errorCount == 0){
//            FieldClassification.Match matchData = new FieldClassification.Match(home, homeUri, away, awayUri,0 ,0);
//            intent.putExtra(DATA_KEY, matchData);
//            startActivity(intent);
//        }

        Log.d("count", "This need"+ errorCount);
    }
}
