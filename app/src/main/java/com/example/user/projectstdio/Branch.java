package com.example.user.projectstdio;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ListView;
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
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by user on 29-10-2017.
 */

public class Branch extends AppCompatActivity implements Branch_Adapter.ListItemClickListener {
    private RecyclerView branch_list;
    List<String[]> list;
    Branch_Adapter branch_adapter;
    String std_id = null;
    String branch_id = null;
    String clg_id = null;
    ProgressDialog pd;
    private String logUrl = "https://satishmhetre18.000webhostapp.com/branch_display.php";
    private  String postUrl="https://satishmhetre18.000webhostapp.com/branch_selection.php";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.branch_layout);
        branch_list = (RecyclerView) findViewById(R.id.branch_list);
        list = new ArrayList<String[]>();

        branch_adapter = new Branch_Adapter(list, this);
        pd=new ProgressDialog(Branch.this);
        pd.setTitle("please wait");
        pd.setMessage("Logging...");
        Intent intent = getIntent();
        std_id = intent.getStringExtra("std_id");
        clg_id = intent.getStringExtra("branch_id");
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        branch_list.setLayoutManager(layoutManager);
        branch_list.setHasFixedSize(true);
        branch_list.setAdapter(branch_adapter);
        fetchBranchList fetChList=new fetchBranchList();
        fetChList.execute();
    }

    @Override
    public void onListItemClick(int number) {
        branch_id = list.get(number)[1];
        sendData senddata=new sendData();
        senddata.execute(std_id,clg_id,branch_id);
        Intent intent=new Intent(Branch.this,navigation_side_bar.class);
        intent.putExtra("id",std_id);
        startActivity(intent);

    }

    private class sendData extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                return postData(strings);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return "error";
            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.hide();
            Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
        }
    }

    private String postData(String[] data) throws UnsupportedEncodingException {
        String Result=null;

        String data_to_send = URLEncoder.encode("std_id", "UTF-8")
                + "=" + URLEncoder.encode(data[0], "UTF-8");
        data_to_send += "&" + URLEncoder.encode("clg_id", "UTF-8")
                + "=" + URLEncoder.encode(data[1], "UTF-8");
        data_to_send += "&" + URLEncoder.encode("branch_id", "UTF-8")
                + "=" + URLEncoder.encode(data[2], "UTF-8");
        try {
            URL url = new URL(postUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(httpURLConnection.getOutputStream());
            outputStreamWriter.write(data_to_send);
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


    private class fetchBranchList extends AsyncTask<String, Void, String> {
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
            String Result = null;

            try {
                URL url = new URL(logUrl);

                //Toast.makeText(this.getActivity(),url.toString(),Toast.LENGTH_SHORT).show();
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }

                inputStream.close();
                httpURLConnection.disconnect();
                Result = stringBuilder.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return "error";
            } catch (IOException e) {
                e.printStackTrace();
                return "error";
            }
            return Result;
        }

        private void extractData(String s) {
            if (s != null) {
                try {


                    JSONArray jsonArray = new JSONArray(s);
                    int i = 0;
                    for (i = 0; i < jsonArray.length(); i++) {
                        JSONObject array = jsonArray.getJSONObject(i);
                        String branch_name = array.getString("branch_name");
                        String branch_id = array.getString("branch_id");
                        Log.d("MESSAGE",branch_id + branch_name);
                        list.add(new String[]{branch_name.split("\\(")[0], branch_id});
                    }
                    branch_adapter.setData(list);
                    branch_adapter.notifyDataSetChanged();


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }
    }


