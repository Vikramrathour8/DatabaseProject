package com.example.user.projectstdio;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.telephony.PhoneNumberUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import java.util.regex.Matcher;

import static android.app.Activity.RESULT_OK;
import static com.example.user.projectstdio.MainActivity.VALID_EMAIL_ADDRESS_REGEX;

public class ContactDetails extends Fragment implements View.OnClickListener{
    private EditText stdEmail;
    private EditText stdMobile;
    private Button submitButton;
    private String Result=null;
    private InputStream inputStream=null;
    public final static Integer REQUEST_CODE=1;
    private String user_name=null;
    private String logUrl="https://satishmhetre18.000webhostapp.com/details.php";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.contact_details, container, false);


        stdEmail=(EditText) view.findViewById(R.id.std_email);
        stdMobile=(EditText) view.findViewById(R.id.std_mobile);
        submitButton=(Button) view.findViewById(R.id.final_submit);

        submitButton.setOnClickListener(this);


        return view;
    }


    public static boolean validate(String emailStr) {
        Matcher matcher =VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
        return matcher.find();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.final_submit:
                Bundle bundle=getArguments();
                 String[] values=bundle.getStringArray("Student_details");
                String[] strings = new String[22];
                user_name = strings[0];
                int i;
                for (i = 0; i < values.length; i++)
                    strings[i] = values[i];
                strings[20] = stdEmail.getText().toString().trim();
                strings[21] = stdMobile.getText().toString().trim();

                if (!validate(strings[20])) {
                    stdEmail.getText().clear();
                    Toast.makeText(getContext(), "Please Enter Valid Email", Toast.LENGTH_LONG).show();
                } else if (!PhoneNumberUtils.isGlobalPhoneNumber("+91" + strings[21])) {
                    stdMobile.getText().clear();
                    Toast.makeText(getContext(), "Please Enter Valid Phone Number", Toast.LENGTH_LONG).show();
                } else {
                    Register register = new Register();

                    register.execute(strings);

                }
                break;
             default:
                break;
        }
    }
    private class Register extends AsyncTask<String,Void,String>
    {
        @Override
        protected String doInBackground(String... strings) {
            try {
                return getData(strings);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return null;
            }
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s != null)
            {
            if(s.equals("error"))
            {
                Toast.makeText(getActivity(),s,Toast.LENGTH_LONG).show();
            }
            else
            {
                Intent intent=new Intent(getActivity(),navigation_side_bar.class);
                intent.putExtra(Intent.EXTRA_TEXT,s);
                startActivity(intent);
            }
        }
    }
    private String getData(String[] strings) throws UnsupportedEncodingException {
        String data = URLEncoder.encode("std_first_name", "UTF-8")
                + "=" + URLEncoder.encode(strings[1], "UTF-8");
        data+= "&" + URLEncoder.encode("user_name", "UTF-8")
                + "=" + URLEncoder.encode(strings[0], "UTF-8");
        data += "&" + URLEncoder.encode("std_middle_name", "UTF-8")
                + "=" + URLEncoder.encode(strings[2], "UTF-8");
        data += "&" + URLEncoder.encode("std_last_name", "UTF-8")
                + "=" + URLEncoder.encode(strings[3], "UTF-8");
        data += "&" + URLEncoder.encode("std_birth_date", "UTF-8")
                + "=" + URLEncoder.encode(strings[4], "UTF-8");
        data += "&" + URLEncoder.encode("std_gender", "UTF-8")
                + "=" + URLEncoder.encode(strings[5], "UTF-8");
        data += "&" + URLEncoder.encode("std_street_name", "UTF-8")
                + "=" + URLEncoder.encode(strings[6], "UTF-8");
        data += "&" + URLEncoder.encode("std_pin_code", "UTF-8")
                + "=" + URLEncoder.encode((strings[7]), "UTF-8");
        data += "&" + URLEncoder.encode("std_district_name", "UTF-8")
                + "=" + URLEncoder.encode(strings[8], "UTF-8");
        data += "&" + URLEncoder.encode("std_state_name", "UTF-8")
                + "=" + URLEncoder.encode(strings[9], "UTF-8");
        data += "&" + URLEncoder.encode("parent_first_name", "UTF-8")
                + "=" + URLEncoder.encode(strings[10], "UTF-8");
        data += "&" + URLEncoder.encode("parent_middle_name", "UTF-8")
                + "=" + URLEncoder.encode(strings[11], "UTF-8");
        data += "&" + URLEncoder.encode("parent_last_name", "UTF-8")
                + "=" + URLEncoder.encode(strings[12], "UTF-8");
        data += "&" + URLEncoder.encode("parent_mobile", "UTF-8")
                + "=" + URLEncoder.encode(strings[13], "UTF-8");
        data += "&" + URLEncoder.encode("parent_address", "UTF-8")
                + "=" + URLEncoder.encode(strings[14], "UTF-8");
        data += "&" + URLEncoder.encode("std_rank", "UTF-8")
                + "=" + URLEncoder.encode(strings[15], "UTF-8");
        data += "&" + URLEncoder.encode("std_tenth_mark", "UTF-8")
                + "=" + URLEncoder.encode(strings[16], "UTF-8");
        data += "&" + URLEncoder.encode("std_tenth_pass_year", "UTF-8")
                + "=" + URLEncoder.encode(strings[17], "UTF-8");
        data += "&" + URLEncoder.encode("std_twelve_mark", "UTF-8")
                + "=" + URLEncoder.encode(strings[18], "UTF-8");
        data += "&" + URLEncoder.encode("std_twelve_pass_year", "UTF-8")
                + "=" + URLEncoder.encode(strings[19], "UTF-8");
        data += "&" + URLEncoder.encode("std_email", "UTF-8")
                + "=" + URLEncoder.encode(strings[20], "UTF-8");
        data += "&" + URLEncoder.encode("std_contact", "UTF-8")
                + "=" + URLEncoder.encode(strings[21], "UTF-8");

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
            Result=stringBuilder.toString();


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Result;
    }

}
}
