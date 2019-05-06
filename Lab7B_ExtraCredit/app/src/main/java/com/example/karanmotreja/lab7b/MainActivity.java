package com.example.karanmotreja.lab7b;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Set up toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        ArrayList<Pair<String, String>> frags = new ArrayList<>();


        // Tabs
        frags.add(new Pair<>("49ers", "https://bleacherreport.com/san-francisco-49ers"));
        frags.add(new Pair<>("Warriors", "https://bleacherreport.com/golden-state-warriors"));
        frags.add(new Pair<>("SJ Sharks", "https://bleacherreport.com/san-jose-sharks"));

        mSectionsPagerAdapter.setFragments(frags);


        ViewPager mViewPager = (ViewPager) findViewById(R.id.content);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // Set up tabs
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(mViewPager);
    }


    public static class WebViewFragment extends Fragment {

        private static final String finalTitle = "title";
        private static final String finalURL = "url";

        public WebViewFragment()
        {
        }


        public static WebViewFragment newInstance(String title, String URL) {
            WebViewFragment webViewFragment = new WebViewFragment();
            Bundle args = new Bundle();
            args.putString(finalTitle, title);
            args.putString(finalURL, URL);
            webViewFragment.setArguments(args);
            return webViewFragment;
        }

        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {

            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            assert getArguments() != null;
            String url = getArguments().getString(finalURL);

            WebView webView = (WebView) rootView.findViewById(R.id.webView);


            //Progress Bar

            final ProgressBar progressBar = (ProgressBar) rootView.findViewById(R.id.progress_bar);
            webView.setWebViewClient(new WebViewClient()
            {

                @Override
                public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request)
                {
                    view.loadUrl(request.getUrl().toString());
                    progressBar.setVisibility(View.VISIBLE);
                    return true;
                }

                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                    progressBar.setVisibility(View.VISIBLE);
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    progressBar.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error){
                    handler.proceed();
                }
            });

            webView.setWebChromeClient(new WebChromeClient() {
                @Override
                public void onProgressChanged(WebView view, int newProgress) {
                    super.onProgressChanged(view, newProgress);
                    progressBar.setProgress(newProgress);
                }
            });
            webView.loadUrl(url);
            return rootView;
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        ArrayList<Pair<String, String>> frags;

        SectionsPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
             Pair<String, String> pair = frags.get(position);
            return WebViewFragment.newInstance(pair.first, pair.second);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return frags.get(position).first;
        }


        void setFragments(ArrayList<Pair<String, String>> fragmentConfigs) {
            this.frags = fragmentConfigs;
        }

        @Override
        public int getCount() {
            return frags.size();
        }
    }
}