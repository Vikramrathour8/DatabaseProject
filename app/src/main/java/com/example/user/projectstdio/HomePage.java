package com.example.user.projectstdio;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by user on 18-10-2017.
 */

public class HomePage extends Fragment {

    private TextView home_circ_name;
    private TextView home_name;
    private  TextView home_rank;
    private TextView home_address;
    private TextView home_tenth;
    private TextView home_twelve;
    private TextView home_mobile;
    private TextView home_email;
    private TextView home_score_header;
    private String table;
    String id=null;
    ProgressDialog pd;
    String std_id=null;
    private  Button choose_branch;
    private String HOME_URL="https://satishmhetre18.000webhostapp.com/college_or_student_info.php";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.home_page,container,false);
        home_circ_name=(TextView) view.findViewById(R.id.home_circ_name);
        home_name=(TextView) view.findViewById(R.id.home_name);
        home_address=(TextView) view.findViewById(R.id.home_address);
        home_tenth=(TextView) view.findViewById(R.id.home_tenth);
        home_twelve=(TextView) view.findViewById(R.id.home_twelve);
        home_mobile=(TextView) view.findViewById(R.id.home_mobile);
        home_email=(TextView) view.findViewById(R.id.home_email);
        pd=new ProgressDialog(getActivity());
        pd.setTitle("please wait");
        pd.setMessage("Data is Loading...");
        home_rank=(TextView) view.findViewById(R.id.home_rank);
        home_score_header=(TextView) view.findViewById(R.id.score_header);
        choose_branch=(Button) view.findViewById(R.id.choose_branch);
        Bundle bundle=getArguments();
         id=bundle.getString("id");

         table=bundle.getString("table");
        Log.d("MESSAGE",id + table );
        if(table.equals("student"))
        {
            choose_branch.setVisibility(View.GONE);
            home_tenth.setVisibility(View.VISIBLE);
            home_twelve.setVisibility(View.VISIBLE);
            home_rank.setVisibility(View.VISIBLE);
            home_score_header.setVisibility(View.VISIBLE);
        }
        else if(table.equals("college"))
        {   std_id=bundle.getString("std_id");
            home_tenth.setVisibility(View.GONE);
            home_twelve.setVisibility(View.GONE);
            home_rank.setVisibility(View.GONE);
            home_score_header.setVisibility(View.GONE);
            choose_branch.setVisibility(View.VISIBLE);
            choose_branch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    seeBranch();
                }
            });
        }

        Details details=new Details();
        details.execute(id,table);
        return view;
    }
    private void seeBranch()
    {
        Intent intent=new Intent(getActivity(),Branch.class);
        intent.putExtra("branch_id",id);
        intent.putExtra("std_id",std_id);
        startActivity(intent);
    }

    private class Details extends AsyncTask<String,Void,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            try {
                return getData(strings);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return "error";
            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.hide();
            if(s!=null)
            extractData(s);

        }
    }
    private void extractData(String s)
    {
        Log.d("MESSAGE",s);
        if(s!=null)
        {
            try {
                if(table.equals("student")) {
                    JSONArray jsonObject = new JSONArray(s);

                    int i = 0;
                    for (i = 0; i < jsonObject.length(); i++) {
                        JSONObject array = jsonObject.getJSONObject(i);
                        String std_id = array.getString("std_id");
                        String std_first_name=array.getString("std_first_name");
                        String std_middle_name=array.getString("std_middle_name");
                        String std_last_name=array.getString("std_last_name");
                        String pin_code=array.getString("std_pincode");
                        String street_name=array.getString("std_street");
                        String district_name=array.getString("std_district");
                        String state_name=array.getString("std_state");
                        String std_email=array.getString("email");
                        String std_phone=array.getString("mobile");
                        String std_tenth=array.getString("std_ten_marks");
                        String std_twelve=array.getString("std_twelve_mark");
                        String jee_rank=array.getString("std_rank");
                        home_tenth.setText("Tenth Mark : "+ std_tenth);
                        home_twelve.setText("Twelve Mark : "+ std_twelve);
                        home_rank.setText(jee_rank);
                        home_address.setText("ADDRESS : " + street_name + ", "  + pin_code +"\n" + "        " +district_name  +" , " +state_name);
                        home_name.setText(std_first_name+ " " +std_middle_name + "  " + std_last_name);
                        home_email.setText(std_email);
                        home_mobile.setText(std_phone);
                        home_circ_name.setText(Character.toString(std_first_name.charAt(0)) +  Character.toString(std_last_name.charAt(0)));

                    }
                }
                else if(table.equals("college")) {
                    JSONArray jsonObject = new JSONArray(s);
                    int i = 0;
                    for (i = 0; i < jsonObject.length(); i++) {

                        JSONObject array = jsonObject.getJSONObject(i);
                        String clg_name=array.getString("clg_name");
                        String clg_pin=array.getString("clg_pin");
                        String clg_street_name=array.getString("clg_street_name");
                        String clg_state=array.getString("clg_state");
                        String clg_district=array.getString("clg_district");
                        String clg_phone=array.getString("clg_phone");
                        String clg_email=array.getString("clg_email");
                        String[] splited = clg_name.split("\\s+");
                        home_address.setText("ADDRESS : " + clg_street_name + ", "  + clg_pin +"\n" + "        " +clg_district  +" , " +clg_state);
                         home_name.setText(clg_name);
                         home_email.setText(clg_email);
                         home_mobile.setText(clg_phone);
                         home_circ_name.setText(Character.toString(splited[0].charAt(0)) +  Character.toString(splited[1].charAt(0)) + Character.toString(splited[3].charAt(0)));

                     }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    private String getData(String[] user_name) throws UnsupportedEncodingException {   String Result=null;

        String data= URLEncoder.encode("id","UTF-8")
                + "="+URLEncoder.encode(user_name[0],"UTF-8");
        data+="&" +URLEncoder.encode("table","UTF-8")
                + "="+URLEncoder.encode(user_name[1],"UTF-8");
        try {
            URL url=new URL(HOME_URL);
            HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            OutputStreamWriter outputStreamWriter=new OutputStreamWriter(httpURLConnection.getOutputStream());
            outputStreamWriter.write(data);
            outputStreamWriter.flush();
            InputStream inputStream=new BufferedInputStream(httpURLConnection.getInputStream());
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder=new StringBuilder();
            String line=null;
            while((line=bufferedReader.readLine())!=null)
            {
                stringBuilder.append(line);
            }
            inputStream.close();
            httpURLConnection.disconnect();
            Result=stringBuilder.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return "error";
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }


        return Result;
    }
}
