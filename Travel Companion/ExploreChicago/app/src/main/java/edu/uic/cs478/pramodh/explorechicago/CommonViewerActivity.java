package edu.uic.cs478.pramodh.explorechicago;

import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

public abstract class CommonViewerActivity extends CommonActivity implements AttractionsListFragment.ListSelectionListener {

    protected AttractionsWebsiteFragment mAttractionsWebFragment = null;
    protected AttractionsListFragment mAttractionsListFragment = new AttractionsListFragment();
    protected FragmentManager mFragmentManager;

    protected FrameLayout mListFrameLayout, mWebsiteFrameLayout;
    protected static final int MATCH_PARENT = LinearLayout.LayoutParams.MATCH_PARENT;

    protected int mShownIndex = -1;
    protected static String OLD_ITEM = "old_item";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.explorer_acivity);
        mListFrameLayout = findViewById(R.id.list_fragment_container);
        mWebsiteFrameLayout = findViewById(R.id.website_fragment_container);

        mFragmentManager = getSupportFragmentManager();
        mFragmentManager.beginTransaction().replace(R.id.list_fragment_container, mAttractionsListFragment).commit();
        mFragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            public void onBackStackChanged() {
                setLayout();
            }
        });

        if (savedInstanceState != null) {
            mShownIndex = savedInstanceState.getInt(OLD_ITEM);
            if (mShownIndex >= 0) {
                mAttractionsWebFragment = new AttractionsWebsiteFragment();
                addWebLinks(mAttractionsWebFragment);
                mFragmentManager.beginTransaction().replace(R.id.website_fragment_container, mAttractionsWebFragment).addToBackStack(null).commit();
                mFragmentManager.executePendingTransactions();
                if (mAttractionsWebFragment.getShownIndex() != mShownIndex) {
                    mAttractionsWebFragment.showQuoteAtIndex(mShownIndex);
                }
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        if (mShownIndex >= 0) {
            if (mAttractionsWebFragment.getShownIndex() != mShownIndex) {
                mAttractionsWebFragment.showQuoteAtIndex(mShownIndex);
            }
            mAttractionsListFragment.setSelection(mShownIndex);
            mAttractionsListFragment.getListView().setItemChecked(mShownIndex, true);
        }
    }

    @Override
    public void onListSelection(int index) {

        if (mShownIndex != index) {
            mShownIndex = index;
            mAttractionsWebFragment = new AttractionsWebsiteFragment();
            addWebLinks(mAttractionsWebFragment);
            mFragmentManager.beginTransaction().replace(R.id.website_fragment_container, mAttractionsWebFragment).addToBackStack(null).commit();
            mFragmentManager.executePendingTransactions();
            if (mAttractionsWebFragment.getShownIndex() != index) {
                mAttractionsWebFragment.showQuoteAtIndex(index);
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mShownIndex >= 0) {
            outState.putInt(OLD_ITEM, mShownIndex);
        } else {
            outState.putInt(OLD_ITEM, -1);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mShownIndex >= 0) {
            mAttractionsListFragment.getListView().setItemChecked(mShownIndex, false);
            mShownIndex = -1;
        }
    }

    private void setLayout() {
        if (!mAttractionsWebFragment.isAdded()) {

            mListFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    MATCH_PARENT, MATCH_PARENT));
            mWebsiteFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                    MATCH_PARENT));
            mShownIndex = -1;
        } else {
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                mListFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                        MATCH_PARENT, 1f));

                mWebsiteFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                        MATCH_PARENT, 2f));
            } else {
                mListFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0,
                        MATCH_PARENT));
                mWebsiteFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(
                        MATCH_PARENT, MATCH_PARENT));
            }
        }
    }

    protected abstract AttractionsWebsiteFragment addWebLinks(AttractionsWebsiteFragment webFragmentObject);
}
