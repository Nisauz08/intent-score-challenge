package id.putraprima.skorbola;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class ScorerActivity extends AppCompatActivity {
    public static final String ADD_KEY = "add";
    EditText scorer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scorer);
        scorer = findViewById(R.id.et_scorer);
    }

    public void handleButtonScore(View view)
    {
        Intent intent = new Intent();
        intent.putExtra(ADD_KEY, scorer.getText().toString());
        setResult(RESULT_OK, intent);
        finish();
    }


}
