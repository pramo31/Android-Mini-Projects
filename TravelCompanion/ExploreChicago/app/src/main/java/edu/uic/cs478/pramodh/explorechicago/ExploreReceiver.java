package edu.uic.cs478.pramodh.explorechicago;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.util.Objects;

public class ExploreReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (Objects.equals(intent.getAction(), context.getResources().getString(R.string.attractions_intent_name))) {
            Intent aIntent = new Intent(context, AttractionsActivity.class);
            context.startActivity(aIntent);
        } else if (Objects.equals(intent.getAction(), context.getResources().getString(R.string.restaurants_intent_name))) {
            Intent aIntent = new Intent(context, RestaurantsActivity.class);
            context.startActivity(aIntent);
        }
    }
}
