package com.example.krishna.eventregistration;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.HashMap;

public class ManageActivity extends AppCompatActivity {

    TextView getRes;
    EditText rollNumber;
    String URL="https://script.google.com/macros/s/" +
            "AKfycbyeOfBMlF1ERSgrChk2GIUifAyhpr91wLM2FELyAW4r5AM6_W5Z/exec";

    private final String id =  "1HWUCmpPB5Pl4vTx3YJTVjKImfMSBxYCxE4H8t6Wo-m0";
    private final String sheet =  "Sheet1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);

        getRes = findViewById(R.id.getRes);
        rollNumber = findViewById(R.id.rollNumber);
    }

    public void OnClickGET(View view) {
        new myAsyncTask("GET").execute(URL,id, sheet, rollNumber.getText().toString());
    }

    private class myAsyncTask extends AsyncTask<String,Integer, String> {

       ProgressDialog myDialog = new ProgressDialog(ManageActivity.this);

        String ReqTYPE = "GET";

        myAsyncTask(String TYPE){
            ReqTYPE = TYPE;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            myDialog.setMessage("Waiting for response !");
            myDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            JSONParser myRquest = new JSONParser();

//            JSONObject myObj = new JSONObject();
            String res = "Invalid Roll";
            HashMap<String, String> myParam = new HashMap<>();
            myParam.put("id",params[1]);
            myParam.put("sheet",params[2]);
            myParam.put("roll",params[3]);

            if(ReqTYPE.equals("GET")){
                res = myRquest.makeHttpRequest(params[0],"GET",myParam);
            }
           publishProgress(10);

            return res;
        }

        @Override
        protected void onPostExecute(String resp) {
            super.onPostExecute(resp);
            myDialog.dismiss();
            Log.d("HTTP Class", "Resp Log: "+resp);
            getRes.setText(resp);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }

}
