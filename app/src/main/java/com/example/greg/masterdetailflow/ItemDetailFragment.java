package com.example.greg.masterdetailflow;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.webkit.WebView;
import android.widget.ZoomButtonsController;

import com.example.greg.masterdetailflow.dummy.DummyContent;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link ItemListActivity}
 * in two-pane mode (on tablets) or a {@link ItemDetailActivity}
 * on handsets.
 */
public class ItemDetailFragment extends Fragment {
    ZoomButtonsController zoom_control = null;
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private DummyContent.DummyItem mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_item_detail, container, false);
        WebView myWebView =  ((WebView) rootView.findViewById(R.id.website_detail));


        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            final WebSettings settings = myWebView.getSettings();
            settings.setDefaultTextEncodingName("utf-8");
            settings.setSupportZoom(true);
            settings.setBuiltInZoomControls(true);

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
                // Use the API 11+ calls to disable the controls
                settings.setBuiltInZoomControls(true);
                settings.setDisplayZoomControls(false);
            } else {
                // Use the reflection magic to make it work on earlier APIs
                getControls();
            }

            if (zoom_control!=null && zoom_control.getZoomControls()!=null)
            {
                // Hide the controlls AFTER they where made visible by the default implementation.
                zoom_control.getZoomControls().setVisibility(View.GONE);
            }


            //myWebView.loadUrl(mItem.website_url);
            myWebView.loadUrl("file:///android_asset/a1.html");
            // line below will set web page inside web view
            myWebView.setWebViewClient(new WebViewClient());
        }

        return rootView;

    }
    private void getControls() {
        try {
            Class webview = Class.forName("android.webkit.WebView");
            //Method method = webview.getMethod("getZoomButtonsController");
            zoom_control = (ZoomButtonsController) webview.getMethod("getZoomButtonsController").invoke(this, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
