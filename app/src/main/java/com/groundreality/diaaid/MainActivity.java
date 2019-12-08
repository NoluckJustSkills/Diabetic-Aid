package com.groundreality.diaaid;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

import static java.util.logging.Logger.global;

public class MainActivity extends AppCompatActivity {


    EditText before,after,intake,burnt,years,sleep;

    LinearLayout getresults;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        before =findViewById(R.id.before);
        after=findViewById(R.id.after);
        intake=findViewById(R.id.intake);
        burnt=findViewById(R.id.burnt);
        years=findViewById(R.id.years);
        sleep=findViewById(R.id.sleep);

        getresults=findViewById(R.id.getresults);

        getresults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(before.getText().toString().trim().isEmpty() ||
                        after.getText().toString().trim().isEmpty() ||
                        intake.getText().toString().trim().isEmpty() ||
                        burnt.getText().toString().trim().isEmpty() ||
                        years.getText().toString().trim().isEmpty() ){

                    Toast.makeText(MainActivity.this,"Please enter all the values",Toast.LENGTH_SHORT).show();
                    return;
                }else{

                    loadDialog(MainActivity.this,false);
                    changeDialog("Please wait, while we process your data...");
                    loadAppSettings();
                }

            }
        });

    }


    public void loadAppSettings(){

        JSONObject jsonParams=new JSONObject();
        try {
            jsonParams.put("Sleep",Integer.parseInt(sleep.getText().toString()));
            jsonParams.put("sugarCountBefore",Integer.parseInt(before.getText().toString()));
            jsonParams.put("sugarCountAfter",Integer.parseInt(after.getText().toString()));
            jsonParams.put("totalCaloriesIntake",Integer.parseInt(intake.getText().toString()));
            jsonParams.put("totalCaloriesBurnt",Integer.parseInt(burnt.getText().toString().trim()));


        } catch (JSONException e) {
            e.printStackTrace();
        }


        AsyncRequestHandler handler = AsyncRequestHandler.getInstance();
        handler.makeRequest(jsonParams, "http://192.168.43.63:5600/health-check", getApplicationContext(), new RequestListener() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                dismissAlertDialog();
                String status = response.optString("status");
                if (status != null) {
                    if (status.matches("200")) {

                        // processAppSettings(response);
                        Intent intent = new Intent(MainActivity.this,Report.class);
                        intent.putExtra("aioutput",response.optString("data"));
                        intent.putExtra("sleep",sleep.getText().toString());
                        intent.putExtra("intake",intake.getText().toString());
                        intent.putExtra("burn",burnt.getText().toString().trim());
                        intent.putExtra("sugar",after.getText().toString());

                        startActivity(intent);

                    }
                }
                else {

                    Toast.makeText(MainActivity.this,"Error in contacting server",Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, JSONObject response) {
                dismissAlertDialog();

            }




        });
    }

    ProgressDialog dialog;

    ///Dialog
    public void loadDialog(Activity activity, Boolean cancel) {
        try {
            dialog = new ProgressDialog(activity, ProgressDialog.THEME_HOLO_LIGHT);
            dialog.setIndeterminate(false);
            dialog.setCancelable(cancel);
            dialog.show();
        } catch (Exception ex) {

        }

    }

    public void changeDialog(String msg) {
        try {
            if (dialog != null && dialog.isShowing()) {
                dialog.setMessage(msg);
            }
        } catch (Exception ex) {

        }

    }


    public void dismissAlertDialog() {
        try {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
