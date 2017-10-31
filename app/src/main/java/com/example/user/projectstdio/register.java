package com.example.user.projectstdio;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

import static android.app.Activity.RESULT_OK;
import static com.example.user.projectstdio.MainActivity.VALID_EMAIL_ADDRESS_REGEX;

/**
 * Created by user on 25-09-2017.
 */

public class register extends Fragment implements View.OnClickListener {
    private Button button;
    private EditText mUsername;
    private  EditText mPassword;
    private EditText ConfirmPassword;
    private String userName;
    private String passWord;
    private String confirmPassowrd;
    private TextView err_view;
    private  String logUrl;
    private InputStream inputStream=null;
    private  String result = null;
    private String[] gender;
    private Spinner spinner;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.register,container,false);
        mUsername=(EditText) view.findViewById(R.id.reg_username);
        mPassword=(EditText) view.findViewById(R.id.reg_pass);
        ConfirmPassword=(EditText) view.findViewById(R.id.reg_confirm_pass);
        err_view=(TextView) view.findViewById(R.id.err_reg_view);
        logUrl="https://satishmhetre18.000webhostapp.com/register.php";
        button=(Button) view.findViewById(R.id.reg_button);
        button.setOnClickListener(this);


        return view;
    }


    public static boolean validate(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
        return matcher.find();
    }

    @Override
    public void onClick(View view) {
        int id=view.getId();
        switch (id)
        {
            case R.id.reg_button:
                userName=mUsername.getText().toString().trim();
                passWord=mPassword.getText().toString().trim();
                confirmPassowrd=ConfirmPassword.getText().toString().trim();
                if(userName.isEmpty())
                {
                    err_view.setVisibility(View.VISIBLE);
                    err_view.setText("Please Enter user_name");
                    mPassword.getText().clear();
                    ConfirmPassword.getText().clear();
                }
                else if(passWord.isEmpty())
                {
                    err_view.setVisibility(View.VISIBLE);
                    err_view.setText("Please Enter Password");
                    mPassword.getText().clear();
                    ConfirmPassword.getText().clear();
                }
                else if(confirmPassowrd.isEmpty())
                {
                    err_view.setVisibility(View.VISIBLE);
                    err_view.setText("Please Confirm Password");
                    mPassword.getText().clear();
                    ConfirmPassword.getText().clear();
                }
                else if(!passWord.equals(confirmPassowrd))
                {
                    err_view.setVisibility(View.VISIBLE);
                    err_view.setText("Your password does not matches");
                    mPassword.getText().clear();
                    ConfirmPassword.getText().clear();
                }
                else
                {
                    if(!validate(userName)) {
                        err_view.setText("Enter at least 8 char password");
                        mUsername.getText().clear();
                    }
                    if(passWord.length()<8)
                    {
                        mPassword.getText().clear();
                        ConfirmPassword.getText().clear();
                        err_view.setText("Enter at least 8 char password");
                    }
                    else {
                        Register register = new Register();
                        register.execute(userName, passWord, confirmPassowrd);
                    }
                }

                    break;
            default:
                break;
        }
    }





    private class Register extends AsyncTask<String,Void,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            err_view.setVisibility(View.INVISIBLE);
        }

        @Override
        protected String doInBackground(String... strings) {
            String userName=strings[0];
            String passWord=strings[1];
            String confirmPassword=strings[2];
            try {
                return getData(userName,passWord,confirmPassword);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();

                return null;
            }

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if(s!=null)
            {
                Toast.makeText(getActivity(),s,Toast.LENGTH_LONG).show();
            if(s.equals(userName))
            {
                Intent intent=new Intent(getActivity(),Registration.class);
                intent.putExtra(Intent.EXTRA_TEXT,userName);
                startActivity(intent);

            }
            else if(s.equals("Duplicate User Name"))
            {
                err_view.setVisibility(View.VISIBLE);
                err_view.setText("user name already registered");
                mUsername.getText().clear();
                mPassword.getText().clear();
                ConfirmPassword.getText().clear();


            }
            else {
                err_view.setVisibility(View.VISIBLE);
                err_view.setText("error in Register");
                mUsername.getText().clear();
                mPassword.getText().clear();
                ConfirmPassword.getText().clear();

            }
        }
    }
    }

    private String getData(String userName,String passWord,String confirmPassowrd) throws UnsupportedEncodingException {
        String data = URLEncoder.encode("user_name", "UTF-8")
                + "=" + URLEncoder.encode(userName, "UTF-8");


        data += "&" + URLEncoder.encode("pass", "UTF-8")
                + "=" + URLEncoder.encode(passWord, "UTF-8");
        data+= "&"+URLEncoder.encode("confirmPass","UTF-8")
                + "=" + URLEncoder.encode(confirmPassowrd,"UTF-8");


        try {
            URL url=new URL(logUrl);
            //Toast.makeText(this.getActivity(),url.toString(),Toast.LENGTH_SHORT).show();

            HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);

            OutputStreamWriter outputStreamWriter=new OutputStreamWriter(httpURLConnection.getOutputStream());
            outputStreamWriter.write(data);
            Map<String,String> pic=new HashMap<>();

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


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }



}
