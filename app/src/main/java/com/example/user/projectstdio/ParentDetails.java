package com.example.user.projectstdio;

import android.os.Bundle;
import android.support.annotation.Nullable;
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

public class ParentDetails  extends Fragment{
    private EditText parentFirstName;
    private EditText parentMiddleName;
    private EditText parentLastName;
    private EditText parentMobile;
    private EditText parentAddress;
    private Button detailSubmit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return  inflater.inflate(R.layout.parent_details,container,false);
    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle bundle=getArguments();
        final String[] values=bundle.getStringArray("Student_details");
        parentFirstName=(EditText) this.getActivity().findViewById(R.id.parent_first_name);
        parentMiddleName=(EditText) this.getActivity().findViewById(R.id.parent_middle_name);
        parentLastName=(EditText) this.getActivity().findViewById(R.id.parent_last_name);
        parentMobile=(EditText) this.getActivity().findViewById(R.id.parent_mobile);
        parentAddress=(EditText) this.getActivity().findViewById(R.id.parent_address);
        detailSubmit=(Button) this.getActivity().findViewById(R.id.parent_detail_submit);
        detailSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strings[]=new String[15];
                int i;
                for(i=0;i<values.length;i++)
                    strings[i]=values[i];
                // strings=values;
                strings[10]=parentFirstName.getText().toString().trim();
                strings[11]=parentMiddleName.getText().toString().trim();
                if(strings[11].equals(""))
                    strings[11]="Kumar";
                strings[12]=parentLastName.getText().toString().trim();
                strings[13]=parentMobile.getText().toString().trim();
                strings[14]=parentAddress.getText().toString().trim();

                Bundle bundle = new Bundle();
                bundle.putStringArray("Student_details", strings);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fs=fragmentManager.beginTransaction();
                Fragment thirdFragment = new ScoreDetails();
                thirdFragment.setArguments(bundle);
                fs.replace(R.id.fragment_container,thirdFragment);
                fs.commit();

            }
        });

    }

}
