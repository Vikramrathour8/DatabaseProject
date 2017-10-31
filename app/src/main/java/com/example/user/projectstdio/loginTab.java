package com.example.user.projectstdio;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ShareCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

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

import Utils.NetworkLogin;

import static android.app.Activity.RESULT_OK;

/**
 * Created by user on 25-09-2017.
 */

public class loginTab extends Fragment  {
    private EditText uname;
    private EditText pass;
    private Button  log_button;
    private TextView error_view;
    private ProgressDialog pd;
    private String logUrl;
    private InputStream inputStream=null;
    private String result=null;
    private String user_name=null;
    private Button upload_photo;
    private ImageView see_photo;
    public final static Integer REQUEST_CODE=1;
    private Bitmap bitmap;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.login,container,false);
        logUrl="https://satishmhetre18.000webhostapp.com/login1.php";
        uname=(EditText) view.findViewById(R.id.login_username);
        pd=new ProgressDialog(getActivity());
        pd.setTitle("please wait");
        pd.setMessage("Logging...");

        pass=(EditText) view.findViewById(R.id.login_pass);
        log_button=(Button) view.findViewById(R.id.log_button);
        error_view=(TextView) view.findViewById(R.id.error_text_view);
        log_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user_name=uname.getText().toString().trim();
                String password=pass.getText().toString().trim();
                if(user_name.isEmpty()||password.isEmpty())
                {
                    error_view.setVisibility(View.VISIBLE);
                    error_view.setText("please enter all details");

                }
                else
                {
                    error_view.setVisibility(View.INVISIBLE);
                    Authenticate authenticate=new Authenticate();
                    authenticate.execute(user_name,password);
                }
            }
        });
        return view;
    }
    private class Authenticate extends  AsyncTask<String,Void,String> {

       @Override
       protected void onPreExecute() {
           super.onPreExecute();
           error_view.setVisibility(View.INVISIBLE);
           pd.show();
       }
       @Override
       protected String doInBackground(String... string) {
            user_name=string[0];
           String password=string[1];

           //Toast.makeText(getActivity(), "happened", Toast.LENGTH_SHORT).show();
           try {
               return getData(user_name,password);
           } catch (UnsupportedEncodingException e) {
               e.printStackTrace();
               return null;
           }
       }

       @Override
       protected void onPostExecute(String s) {
           super.onPostExecute(s);
           pd.hide();
           if (s != null) {
               if(s.equals("error")){
                   error_view.setVisibility(View.VISIBLE);
                   error_view.setText("please enter correct password");
                   pass.getText().clear();
               }
                else  {
                   Intent intent = new Intent(getActivity(), navigation_side_bar.class);
                   intent.putExtra(Intent.EXTRA_TEXT, s);
                   startActivity(intent);
               }
           }
       }
   }
        private String  getData(String user_name,String password) throws UnsupportedEncodingException {
        String data = URLEncoder.encode("user_name", "UTF-8")
                + "=" + URLEncoder.encode(user_name, "UTF-8");
        data += "&" + URLEncoder.encode("pass", "UTF-8")
                + "=" + URLEncoder.encode(password, "UTF-8");
        try {
            URL url=new URL(logUrl);
            //Toast.makeText(this.getActivity(),url.toString(),Toast.LENGTH_SHORT).show();
            HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            OutputStreamWriter outputStreamWriter=new OutputStreamWriter(httpURLConnection.getOutputStream());
            outputStreamWriter.write(data);
            outputStreamWriter.flush();
            inputStream= new BufferedInputStream(httpURLConnection.getInputStream());
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder=new StringBuilder();
            String line=null;
            while((line=bufferedReader.readLine())!=null)
            {
                        stringBuilder.append(line);
            }

            inputStream.close();
            httpURLConnection.disconnect();
            result=stringBuilder.toString();
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
            return "error";
        }
        catch (IOException e) {
            e.printStackTrace();
            return "error";
        }

         return result;
    }
}
