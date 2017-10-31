package com.example.user.projectstdio;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import java.util.ArrayList;


/**
 * Created by user on 27-10-2017.
 */

public class College_list extends Fragment implements  CollegeAdapter.ListItemClickListener {

    private RecyclerView college_list;
    private CollegeAdapter collegeAdapter;
    ArrayList<String[]> list;
    String std_id=null;
    ProgressDialog pd;
    public final static String HOME_URL = "https://satishmhetre18.000webhostapp.com/college_name_extract.php";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.college_list,container,false);
        college_list=(RecyclerView) view.findViewById(R.id.college_list);
        list=new ArrayList<String[]>();
        pd=new ProgressDialog(getActivity());
        pd.setTitle("please wait");
        pd.setMessage("Data is Loading...");
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        college_list.setLayoutManager(layoutManager);
        college_list.setHasFixedSize(true);
        Bundle bundle=getArguments();
        std_id=bundle.getString("std_id");
        fetchCollegeList fs=new fetchCollegeList();
        collegeAdapter=new CollegeAdapter(list,this);
        college_list.setAdapter(collegeAdapter);
        fs.execute();
        return view;
    }


    @Override
    public void onListItemClick(int number) {

        String[] name=list.get(number);
        FragmentManager mang=getActivity().getSupportFragmentManager();
        FragmentTransaction fs=mang.beginTransaction();
        Fragment homePage=new HomePage();
        Bundle bundle=new Bundle();
        bundle.putString("id",name[3]);
        bundle.putString("table","college");
        bundle.putString("std_id",std_id);
        homePage.setArguments(bundle);
        fs.replace(R.id.frame_container,homePage);
        fs.commit();
    }

    private class fetchCollegeList extends AsyncTask<String,Void,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            try {
                return getData();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return "error";
            }
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.hide();
            extractData(s);
        }
    }
    private String getData() throws UnsupportedEncodingException {

        String Result=null;
        try {
            URL url=new URL(HOME_URL);
            HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
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
    private void extractData(String s)
    {
        if(s!=null)
        {
            try {


                JSONArray jsonArray=new JSONArray(s);
                int i=0;
                for(i=0;i<jsonArray.length();i++)
                {
                    JSONObject array=jsonArray.getJSONObject(i);
                    String college_name=array.getString("clg_name");
                    String[] splited = college_name.split("\\s+");
                    String[] data=new String[5];
                    data[0]=Character.toString(splited[0].charAt(0)) + Character.toString(splited[1].charAt(0)) + Character.toString(splited[3].charAt(0)) +Character.toString(splited[4].charAt(0)); ;
                    data[1]=splited[0] + splited[1] + splited[2] + splited[3];
                    data[2]=splited[4];
                    data[3]=array.getString("clg_id");
                    list.add(data);
                    Log.d("MESSAGE",data[0] + data[1] + data[2] +data[3]);



                }
                collegeAdapter.setData(list);
                collegeAdapter.notifyDataSetChanged();


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }


}
