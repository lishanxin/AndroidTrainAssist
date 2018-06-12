package com.example.androidtrainassist;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void getShareFile(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/jpg");
        startActivityForResult(intent, 101);
    }

    /**
     * Dispatch incoming result to the correct fragment.
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Intent returnIntent = getIntent();
        if (resultCode == RESULT_OK){
            if (requestCode == 101){
                Uri returnUri = returnIntent.getData();
            }
        }

    }
}
