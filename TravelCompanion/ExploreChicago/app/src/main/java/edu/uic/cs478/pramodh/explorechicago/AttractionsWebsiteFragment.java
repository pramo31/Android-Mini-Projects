package edu.uic.cs478.pramodh.explorechicago;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.Objects;

public class AttractionsWebsiteFragment extends Fragment {

    private WebView mWebsiteView = null;
    private int currentIndex = -1;
    private int mWebsiteArrayLen;
    private String[] websiteLinks;

    int getShownIndex() {
        return currentIndex;
    }

    void showQuoteAtIndex(int newIndex) {
        if (newIndex < 0 || newIndex >= mWebsiteArrayLen) {
            return;
        }
        currentIndex = newIndex;
        mWebsiteView.setWebViewClient(new WebViewClient());
        mWebsiteView.loadUrl(websiteLinks[currentIndex]);
    }

    @Override
    public void onAttach(@NonNull Context activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.website_view, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        assert getArguments() != null;
        mWebsiteView = Objects.requireNonNull(getActivity()).findViewById(R.id.web_view);
        websiteLinks = Objects.requireNonNull(getArguments().getStringArray("list_data"));
        mWebsiteArrayLen = websiteLinks.length;
    }
}
