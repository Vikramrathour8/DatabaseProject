package com.example.user.projectstdio;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by user on 08-10-2017.
 */

public class StudentDetails extends Fragment  {
    private EditText stdFirstName;
    private  EditText stdMiddleName;
    private EditText stdLastName;
    private EditText stdStreetName;
    private EditText stdPinCode;
    private EditText stdDistrict;
    private EditText stdStateName;
    private  EditText stdBirthDate;
    private Button submitButton;
    private String[] gender;
    private Spinner spinner;
    private  String std_gender="Male";



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.std_details, container, false);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle bundle=getArguments();
        final String user_name=bundle.getString("user_name");
        stdFirstName=(EditText) this.getActivity().findViewById(R.id.std_first_name);
        stdMiddleName=(EditText) this.getActivity().findViewById(R.id.std_middle_name);
        stdLastName=(EditText) this.getActivity().findViewById(R.id.std_last_name);
        stdStreetName=(EditText) this.getActivity().findViewById(R.id.std_street_name);
        stdPinCode=(EditText) this.getActivity().findViewById(R.id.std_pin_code);
        stdDistrict=(EditText) this.getActivity().findViewById(R.id.std_district_name);
        stdStateName=(EditText) this.getActivity().findViewById(R.id.std_state_name);
        stdBirthDate=(EditText) this.getActivity().findViewById(R.id.std_birth_date);
        submitButton=(Button) this.getActivity().findViewById(R.id.std_detail_submit);
        gender=new String[3];
        gender[0]="Male";
        gender[1]="Female";
        gender[2]="None";
        spinner=(Spinner) this.getActivity().findViewById(R.id.std_gender);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_dropdown_item,gender);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
      spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
          @Override
          public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
              std_gender=adapterView.getItemAtPosition(i).toString();
          }

          @Override
          public void onNothingSelected(AdapterView<?> adapterView) {
              std_gender="Male";

          }
      });
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strings[]=new String[10];
                strings[0]=user_name;
                if(stdFirstName.getText().toString().equals(""))
                    stdFirstName.setText("please Enter First Name");
               else if(stdLastName.getText().toString().equals(""))
                    stdLastName.setText("please Enter Last Name");
                else if(stdBirthDate.getText().toString().equals(""))
                      Toast.makeText(getContext(),"Enter Valid Birthdate",Toast.LENGTH_LONG).show();
                else if(stdStreetName.getText().toString().equals(""))
                    stdStreetName.setText("Enter Street Name");
              else if(stdPinCode.getText().toString().equals(""))
                    stdPinCode.setText("please Enter valid code");
                else if(stdDistrict.getText().toString().equals(""))
                    stdDistrict.setText("Enter Valid District");
                 else if(stdStateName.getText().toString().equals(""))
                    stdStateName.setText("Enter Valid State");
                else
                {
                strings[1]=stdFirstName.getText().toString().trim();
                strings[2]=stdMiddleName.getText().toString().trim();
                strings[3]=stdLastName.getText().toString().trim();
                strings[4]=stdBirthDate.getText().toString().trim();
                strings[5]=std_gender;
                strings[7]=stdStreetName.getText().toString().trim();
                strings[8]=stdDistrict.getText().toString().trim();
                strings[9]=stdStateName.getText().toString().trim();
                strings[6]=stdPinCode.getText().toString().trim();
                Bundle bundle = new Bundle();
                bundle.putStringArray("Student_details",strings);
                FragmentManager fragmentManager= getActivity().getSupportFragmentManager();
                FragmentTransaction fs=fragmentManager.beginTransaction();
                Fragment secondFragment=new ParentDetails();
                secondFragment.setArguments(bundle);
                fs.replace(R.id.fragment_container,secondFragment);
                fs.commit();}

            }
        });



    }


}
