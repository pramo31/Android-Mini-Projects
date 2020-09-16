package edu.uic.cs478.pramodh.explorechicago;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class CommonActivity extends AppCompatActivity {

    private ExploreReceiver explorerReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (explorerReceiver == null) {
            final String exploreAttractionsIntent = this.getResources().getString(R.string.attractions_intent_name);
            final String exploreRestaurantsIntent = this.getResources().getString(R.string.restaurants_intent_name);
            final String broadcastPermission = this.getResources().getString(R.string.broadcast_permission_name);

            explorerReceiver = new ExploreReceiver();
            registerReceiver(explorerReceiver, new IntentFilter(exploreAttractionsIntent), broadcastPermission, null);
            registerReceiver(explorerReceiver, new IntentFilter(exploreRestaurantsIntent), broadcastPermission, null);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(explorerReceiver);
        explorerReceiver = null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.primary_options_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.options_menu_1:
                    startActivity(new Intent(this, AttractionsActivity.class));
                return true;
            case R.id.options_menu_2:
                    startActivity(new Intent(this, RestaurantsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
