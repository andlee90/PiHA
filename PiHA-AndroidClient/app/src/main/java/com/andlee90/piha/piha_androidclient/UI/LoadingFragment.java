package com.andlee90.piha.piha_androidclient.UI;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.andlee90.piha.piha_androidclient.R;

public class LoadingFragment extends Fragment
{
    private TextView mTextView;

    public static LoadingFragment newInstance()
    {
        return new LoadingFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_loading, container, false);
        ProgressBar progressBar = view.findViewById(R.id.progress_bar_status);
        mTextView = view.findViewById(R.id.textview_status);
        mTextView.setText("Establishing connection with servers...");

        return view;
    }

    public void updateTextView(String text)
    {
        if(mTextView != null)
        {
            String newText = mTextView.getText() + text;
            this.mTextView.setText(newText);
        }
    }
}