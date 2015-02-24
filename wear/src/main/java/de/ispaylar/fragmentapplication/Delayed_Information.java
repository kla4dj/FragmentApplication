package de.ispaylar.fragmentapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.view.DelayedConfirmationView;
import android.view.View;

/**
 * Created by Deniz on 23.02.15.
 */
public class Delayed_Information extends Activity implements DelayedConfirmationView.DelayedConfirmationListener {


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delayed_information_layout);

        DelayedConfirmationView delayedconfirmation_view = (DelayedConfirmationView) findViewById(R.id.delayed_confirmation_layout_view);

        delayedconfirmation_view.setTotalTimeMs(5000);
        delayedconfirmation_view.setListener(this);
        delayedconfirmation_view.start();

    }

    @Override
    public void onTimerFinished(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public void onTimerSelected(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }
}
