package com.yinli.yinli_ylsegmentedcontrol;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.yinli.ylsegmentedcontrol.YLSegmentedRadioGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class Sample2Fragment extends Fragment implements RadioGroup.OnCheckedChangeListener{


    public Sample2Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_sample2, container, false);
        YLSegmentedRadioGroup radioGroup1 = (YLSegmentedRadioGroup) root.findViewById(R.id.radioGroup1);
        radioGroup1.setOnCheckedChangeListener(this);
        return root;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.star:
                Toast.makeText(getActivity(), "Star", Toast.LENGTH_SHORT).show();
                break;
            case R.id.search:
                Toast.makeText(getActivity(), "Search", Toast.LENGTH_SHORT).show();
                break;
            case R.id.camera:
                Toast.makeText(getActivity(), "Camera", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
