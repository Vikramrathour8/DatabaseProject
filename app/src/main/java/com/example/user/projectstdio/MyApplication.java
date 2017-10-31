package com.example.user.projectstdio;


import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget. RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 29-10-2017.
 */

public class MyApplication extends Fragment {
    String std_id=null;
    private RecyclerView myapp_list;
    private ArrayList<String[]> list;
    ProgressDialog pd;
    private CollegeAdapter collegeAdapter;
    private String HOME_URL="https://satishmhetre18.000webhostapp.com/clg_and_branch_name.php";
    private  String DELETE_URL="https://satishmhetre18.000webhostapp.com/delete_branch_selection.php";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.college_list,container,false);
        myapp_list=(RecyclerView) view.findViewById(R.id.college_list);
        list=new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        myapp_list.setLayoutManager(layoutManager);
        myapp_list.setHasFixedSize(true);
        pd=new ProgressDialog(getActivity());
        pd.setTitle("please wait");
        pd.setMessage("Data is Loading...");
        Bundle bundle=getArguments();
        std_id=bundle.getString("std_id");
        fetchCollegeList fs=new fetchCollegeList();
        collegeAdapter=new CollegeAdapter(list);
        myapp_list.setAdapter(collegeAdapter);
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                String[] data = collegeAdapter.getData(viewHolder.getAdapterPosition());
                collegeAdapter.dismissApplication(viewHolder.getAdapterPosition());
                deleteData d=new deleteData();
                Log.d("ACTION",data[3].trim() + data[4].trim() +data[5].trim());
                d.execute(data[3].trim(),data[4].trim(),data[5].trim());


            }
        }).attachToRecyclerView(myapp_list);
        fs.execute();
        return view;
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
            if(s!=null)
            {

                extractData(s);


            }

        }
    }
    private String getData() throws UnsupportedEncodingException {
        String data= URLEncoder.encode("id","UTF-8")
                + "="+URLEncoder.encode(std_id,"UTF-8");

        String Result=null;
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
    private class deleteData extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd.setMessage("Deleting...");
            pd.show();

        }

        @Override
        protected String doInBackground(String... strings) {

            try {
                return getData(strings);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return "error";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.hide();
            Toast.makeText(getContext(),s,Toast.LENGTH_LONG).show();
        }
    }

    private String getData(String[] strings) throws UnsupportedEncodingException {
        String Result;
        String data = URLEncoder.encode("std_id", "UTF-8")
                + "=" + URLEncoder.encode(strings[0], "UTF-8");
        data += "&" + URLEncoder.encode("clg_id", "UTF-8")
                + "=" + URLEncoder.encode(strings[1], "UTF-8");
        data += "&" + URLEncoder.encode("branch_id", "UTF-8")
                + "=" + URLEncoder.encode(strings[2], "UTF-8");
        try {
            URL url = new URL(DELETE_URL);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(httpURLConnection.getOutputStream());
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
        } catch (ProtocolException e) {
            e.printStackTrace();
            return "error";
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return "error";
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }
        Log.d("REPLY ",Result);
        return Result;

    }

    private void extractData(String s) {
        if (s != null) {
            try {


                JSONArray jsonArray = new JSONArray(s);
                int i = 0;
                for (i = 0; i < jsonArray.length(); i++) {
                    JSONObject array = jsonArray.getJSONObject(i);
                    String college_name = array.getString("clg_name");
                    String branch_name = array.getString("branch_name");
                    String[] splited = college_name.split("\\s+");
                    String[] data = new String[6];
                    data[0] = branch_name;
                    data[1] = splited[0] + " " + splited[1] + " " + splited[2] + " " + splited[3];
                    data[2] = splited[4];
                    data[3] = std_id;
                    data[4]= array.getString("clg_id");
                    data[5]=array.getString("branch_id");
                    list.add(data);


                }
                collegeAdapter.setData(list);
                collegeAdapter.notifyDataSetChanged();


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
