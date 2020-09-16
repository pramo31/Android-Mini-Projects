package edu.uic.cs478.pramodh.visitchicago;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class PlannerActivity extends AppCompatActivity {

    private static final int BROADCAST_ATTRACTIONS_INTENT_REQUEST_CODE = 101;
    private static final int BROADCAST_RESTAURANTS_INTENT_REQUEST_CODE = 102;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button attractionsBtn = findViewById(R.id.attractions_button);
        attractionsBtn.setOnClickListener(attractionsListener);

        Button restaurantsBtn = findViewById(R.id.restaurants_button);
        restaurantsBtn.setOnClickListener(restaurantsListener);
    }


    public View.OnClickListener attractionsListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast toast = Toast.makeText(getApplicationContext(), R.string.attractions_toast, Toast.LENGTH_SHORT);
            toast.show();

            checkAndRequestPermission(PlannerActivity.this.getResources().getString(R.string.attractions_intent_name));
        }
    };

    public View.OnClickListener restaurantsListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast toast = Toast.makeText(getApplicationContext(), R.string.restaurants_toast, Toast.LENGTH_SHORT);
            toast.show();

            checkAndRequestPermission(PlannerActivity.this.getResources().getString(R.string.restaurants_intent_name));
        }
    };


    private void checkAndRequestPermission(String action) {
        String permission = this.getResources().getString(R.string.broadcast_permission_name);
        int permissionCheck = ContextCompat.checkSelfPermission(this, permission);

        if (PackageManager.PERMISSION_GRANTED == permissionCheck) {
            Intent aIntent = new Intent(action);
            sendBroadcast(aIntent, null);
        } else {
            if (action == PlannerActivity.this.getResources().getString(R.string.attractions_intent_name)) {
                ActivityCompat.requestPermissions(this, new String[]{permission}, BROADCAST_ATTRACTIONS_INTENT_REQUEST_CODE);
            } else if (action == PlannerActivity.this.getResources().getString(R.string.restaurants_intent_name)) {
                ActivityCompat.requestPermissions(this, new String[]{permission}, BROADCAST_RESTAURANTS_INTENT_REQUEST_CODE);
            }

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {

        if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            switch (requestCode) {
                case BROADCAST_ATTRACTIONS_INTENT_REQUEST_CODE: {
                    Intent aIntent = new Intent(PlannerActivity.this.getResources().getString(R.string.attractions_intent_name));
                    sendBroadcast(aIntent, null);
                }
                case BROADCAST_RESTAURANTS_INTENT_REQUEST_CODE: {
                    Intent aIntent = new Intent(PlannerActivity.this.getResources().getString(R.string.restaurants_intent_name));
                    sendBroadcast(aIntent, null);
                }
                default: {
                }
            }
        } else {
            Toast toast = Toast.makeText(getApplicationContext(), R.string.permission_not_granted_to_broadcast_intent, Toast.LENGTH_LONG);
            toast.show();
        }
    }
}
