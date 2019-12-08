package com.groundreality.diaaid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class Report extends AppCompatActivity {

    TextView aireport,aireport2;

    float aioutput;


    int sleep,intake,burn,sugar;
    ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);


        aireport = findViewById(R.id.aireport);
        aireport2 = findViewById(R.id.aireport);

        iv = findViewById(R.id.iv);

        aioutput = Float.parseFloat(getIntent().getExtras().getString("aioutput"));
        sleep = Integer.parseInt(getIntent().getExtras().getString("sleep"));
        intake= Integer.parseInt(getIntent().getExtras().getString("intake"));
        burn= Integer.parseInt(getIntent().getExtras().getString("burn"));
        sugar=Integer.parseInt(getIntent().getExtras().getString("sugar"));

        if(aioutput<=0.2 && aioutput >= 0.0){
            aireport.setText("Congragulations! your Routine for the day was very healthy");
            iv.setImageDrawable(getResources().getDrawable(R.drawable.best));
            return;
        }else if(aioutput<=0.4 && aioutput >= 0.21){
            aireport.setText("your Routine for the day was Good");
            iv.setImageDrawable(getResources().getDrawable(R.drawable.good));


        }

        generateStatement();
    }


    private void generateStatement(){

        StringBuilder str = new StringBuilder();
        if(sleep < 7 || sleep>9){
            str.append("\nPlease try to have 8 hours sleep") ;
        }


        if((intake-burn) < 600){
            str.append("\nPlease increase your diet quantity") ;

        }else if((intake-burn) > 600){
            str.append("\nPlease try to have work out sessions daily") ;

        }

        if(sugar >= 200){
            str.append("\nPlease try to control your sugar intake") ;

        }
        aireport2.setText(str);
    }

}
