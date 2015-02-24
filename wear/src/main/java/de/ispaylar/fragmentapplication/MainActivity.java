package de.ispaylar.fragmentapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Selection;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends Activity {

    LinearLayout delayed_information_layout;
    Button buttonselection, buttondelayedinformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selection);

        delayed_information_layout = (LinearLayout) findViewById(R.id.delayed_information_layout);
        buttondelayedinformation = (Button) findViewById(R.id.buttondelayedinformation);
        buttonselection = (Button) findViewById(R.id.buttonselection);
    }



    public void OnClickDelayedInformation(View v){

        startActivity(new Intent(MainActivity.this, Delayed_Information.class));
    }

    public void OnClickSelection(View v){

        startActivity(new Intent(MainActivity.this, Selection.class));
    }



}
