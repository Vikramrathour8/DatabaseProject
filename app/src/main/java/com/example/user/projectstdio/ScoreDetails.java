package com.example.user.projectstdio;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by user on 08-10-2017.
 */

public class ScoreDetails extends Fragment {
    private EditText stdRank;
    private EditText stdTenthMark;
    private EditText stdtenthPassYear;
    private EditText stdTwelveMark;
    private EditText stdTwelvePassYear;
    private Button scoreSubmit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.std_score_details, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle bundle=getArguments();

        final String[]  values= bundle.getStringArray("Student_details");

        stdRank=(EditText) this.getActivity().findViewById(R.id.std_rank);
        stdTenthMark=(EditText) this.getActivity().findViewById(R.id.std_ten_mark);
        stdtenthPassYear=(EditText) this.getActivity().findViewById(R.id.std_ten_pass_year);
        stdTwelveMark=(EditText) this.getActivity().findViewById(R.id.std_twelve_mark);
        stdTwelvePassYear=(EditText) this.getActivity().findViewById(R.id.std_twelve_pass_year);
        scoreSubmit=(Button) this.getActivity().findViewById(R.id.std_score_submit);
        scoreSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] strings=new String[20];
                int i;
                for(i=0;i<values.length;i++)
                    strings[i]=values[i];
                strings[15]=stdRank.getText().toString().trim();
                strings[16]=stdTenthMark.getText().toString().trim();
                strings[17]=stdtenthPassYear.getText().toString().trim();
                strings[18]=stdTwelveMark.getText().toString().trim();
                strings[19]=stdTwelvePassYear.getText().toString().trim();
                Bundle bundle = new Bundle();
                bundle.putStringArray("Student_details", strings);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fs=fragmentManager.beginTransaction();
                Fragment thirdFragment = new ContactDetails();
                thirdFragment.setArguments(bundle);
                fs.replace(R.id.fragment_container,thirdFragment);
                fs.commit();



            }
        });


    }

}
