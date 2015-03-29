package com.yinli.yinli_ylsegmentedcontrol;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.yinli.ylsegmentedcontrol.YLSegmentedRadioButton;
import com.yinli.ylsegmentedcontrol.YLSegmentedRadioGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class Sample1Fragment extends Fragment implements RadioGroup.OnCheckedChangeListener{


    public Sample1Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_sample1, container, false);
        YLSegmentedRadioGroup radioGroup1 = (YLSegmentedRadioGroup) root.findViewById(R.id.radioGroup1);
        YLSegmentedRadioGroup radioGroup2 = (YLSegmentedRadioGroup) root.findViewById(R.id.radioGroup2);

        radioGroup2.setActiveColor(Color.parseColor("#36CD51"));
        radioGroup2.setInactiveColor(Color.LTGRAY);
        radioGroup2.setGravity(Gravity.CENTER);
        radioGroup2.setRadius(8);
        radioGroup2.setStrokeWidth(5);
        addRadioButton(radioGroup2);
        addRadioButton(radioGroup2);
        addRadioButton(radioGroup2);
        radioGroup2.updateGroup();

        radioGroup1.setOnCheckedChangeListener(this);
        radioGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                Toast.makeText(getActivity(), "Button", Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }

    private void addRadioButton(YLSegmentedRadioGroup group) {
        YLSegmentedRadioButton radioButton = new YLSegmentedRadioButton(getActivity());
        radioButton.setText("Button");
        radioButton.setCompoundDrawables(getResources().getDrawable(R.drawable.ic_action_paste), null, null, null);
        radioButton.updateButton();
        group.addView(radioButton);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.hButton1:
                Toast.makeText(getActivity(), "Button 1", Toast.LENGTH_SHORT).show();
                break;
            case R.id.hButton2:
                Toast.makeText(getActivity(), "Button 2", Toast.LENGTH_SHORT).show();
                break;
            case R.id.hButton3:
                Toast.makeText(getActivity(), "Button 3", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
