package com.example.android.sunshine.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

//import android.view.LayoutInflater;

public class DetailActivity extends ActionBarActivity {
    private final String LOG_TAG = DetailActivity.class.getSimpleName();

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_launcher);
        setContentView(R.layout.activity_detail);
        setTitle("Details");


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new DetailFragment())
                    .commit();
        }
    }

    // Call to update the share intent
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent settingsIntent =  new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class DetailFragment extends Fragment {
        private String mWeatherStr;
        private static final String LOG_TAG = DetailFragment.class.getSimpleName();
        private static final String HASH_TAG = " #SunshineApp";
        public DetailFragment() {
            setHasOptionsMenu(true);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            Intent intent = getActivity().getIntent();

            View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
             if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)){
                mWeatherStr = intent.getStringExtra(Intent.EXTRA_TEXT);
                TextView textView = (TextView)rootView.findViewById(R.id.detail_text);
                textView.setText(mWeatherStr);
            }


            return rootView;
        }
        private Intent createShareIntent(   ) {
            Intent ShareIntent = new Intent();
            ShareIntent.setAction(Intent.ACTION_SEND);
            ShareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
            ShareIntent.setType("text/plain");
            ShareIntent.putExtra(Intent.EXTRA_TEXT, mWeatherStr + HASH_TAG );
            return ShareIntent;

        }
        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            // Inflate the menu; this adds items to the action bar if it is present.
            inflater.inflate(R.menu.detailfragment, menu);

            // Locate MenuItem with ShareActionProvider
            MenuItem  item =   menu.findItem(R.id.menu_item_share);

            // Fetch and store ShareActionProvider
            ShareActionProvider mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item)  ;

            // Connect the dots: give the ShareActionProvider its Share Intent
            if (mShareActionProvider != null) {
                mShareActionProvider.setShareIntent(createShareIntent() );
            }

         }
    }
}