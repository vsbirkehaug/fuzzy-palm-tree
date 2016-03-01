package com.groupb.artrec.views.setup_views;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.groupb.artrec.R;

/**
 * Created by VSB on 23/02/2016.
 */
public class JournalPicker extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.journal_picker, container, false);
    }
}