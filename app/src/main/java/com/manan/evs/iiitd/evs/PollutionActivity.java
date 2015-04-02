package com.manan.evs.iiitd.evs;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by manan on 02-04-2015.
 */
public class PollutionActivity extends Fragment {
    TextView text;
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle args) {
        View view = inflater.inflate(R.layout.pollution, container, false);
        text= (TextView) view.findViewById(R.id.textView);
        text.setText(R.string.pollution);
        return view;
    }
}
