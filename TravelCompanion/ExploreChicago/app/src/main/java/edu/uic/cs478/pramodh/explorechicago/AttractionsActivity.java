package edu.uic.cs478.pramodh.explorechicago;

import android.os.Bundle;

public class AttractionsActivity extends CommonViewerActivity {

    private static String[] mListArray;
    private static String[] mWebsiteArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListArray = getResources().getStringArray(R.array.attractions);
        mWebsiteArray = getResources().getStringArray(R.array.attractions_links);

        Bundle bundle = new Bundle();
        bundle.putStringArray("list_data", mListArray);
        mAttractionsListFragment.setArguments(bundle);
    }

    @Override
    protected AttractionsWebsiteFragment addWebLinks(AttractionsWebsiteFragment webFragmentObject) {
        Bundle bundle = new Bundle();
        bundle.putStringArray("list_data", mWebsiteArray);
        webFragmentObject.setArguments(bundle);
        return webFragmentObject;
    }
}