package com.example.bmicalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.StringBuilderPrinter;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.text.DecimalFormat;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity {

    private static final String FILE_NAME ="datastore.txt";
    private static final String FILE_NAME1 = "datastore1.txt";

    EditText wEditText;
    EditText hEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        wEditText = findViewById(R.id.etweight);
        hEditText = findViewById(R.id.etheight);

        EditText etweight, etheight;
        TextView outBMI, outClass, outHR;
        Button btcalc, btreset, button;

        etweight = (EditText) findViewById(R.id.etweight);
        etheight = (EditText) findViewById(R.id.etheight);

        outBMI = (TextView) findViewById(R.id.outBMI);
        outClass = (TextView) findViewById(R.id.outClass);
        outHR = (TextView) findViewById(R.id.outHR);

        btcalc = (Button) findViewById(R.id.btcalc);
        btreset = (Button) findViewById(R.id.btreset);
        button = (Button) findViewById(R.id.button);

        btcalc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String strweg = etweight.getText().toString();
                String strhei = etheight.getText().toString();

                if (strweg.equals("")) {
                    etweight.setError("Please Enter Your Weight");
                    etweight.requestFocus();
                    return;
                }
                if (strhei.equals("")) {
                    etheight.setError("Please Enter Your Height");
                    etheight.requestFocus();
                    return;
                }

                float weight = Float.parseFloat(strweg);
                float height = Float.parseFloat(strhei) / 100;

                float bmiVlaue = BMICalculate(weight,height);

                outClass.setText(interpreteBMI(bmiVlaue));
                outBMI.setText(String.valueOf(Math.round(bmiVlaue*100.00)/100.00));
                outHR.setText(interpreteHR(bmiVlaue));
            }
        });
        btreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etweight.setText("");
                etheight.setText("");
                outBMI.setText("");
                outClass.setText("");
                outHR.setText("");
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAboutUs();
            }
        });
    }
    public void openAboutUs(){
        Intent intent = new Intent(this, AboutUs.class);
        startActivity(intent);
    }

    public float BMICalculate(float weight, float height) {
        return weight / (height * height);
    }

    public String interpreteBMI(float bmiValue) {
        if (bmiValue <= 18.4) {
            return "Underweight";
        } else if (bmiValue >= 18.5 && bmiValue <= 24.9) {
            return "Normal Weight";
        } else if (bmiValue >= 25.0 && bmiValue <= 29.9) {
            return "Overweight";
        } else if (bmiValue >= 30.0 && bmiValue <= 34.9) {
            return "Moderately Obese";
        } else if (bmiValue >= 35.0 && bmiValue <= 39.9) {
            return "Severely Obese";
        } else
            return "Very Severely Obese";
    }
    public String interpreteHR(float bmiValue) {
        if (bmiValue <= 18.4) {
            return "Mainutrition Risk";
        } else if (bmiValue >= 18.5 && bmiValue <= 24.9) {
            return "Low Risk";
        } else if (bmiValue >= 25.0 && bmiValue <= 29.9) {
            return "Enchanced Risk";
        } else if (bmiValue >= 30.0 && bmiValue <= 34.9) {
            return "Medium Risk";
        } else if (bmiValue >= 35.0 && bmiValue <= 39.9) {
            return "High Risk";
        } else
            return "Very High Risk";
    }

    public void save(View view){
        String text = wEditText.getText().toString();
        String text1 =hEditText.getText().toString();

        FileOutputStream fos = null;
        try {
            fos = openFileOutput(FILE_NAME, MODE_PRIVATE);
            fos.write(text.getBytes());

            wEditText.getText().clear();
            Toast.makeText(this, "Saved to" + getFilesDir() + "/" + FILE_NAME, Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        FileOutputStream fos1 = null;
        try {
            fos1 = openFileOutput(FILE_NAME1, MODE_PRIVATE);
            fos1.write(text1.getBytes());

            hEditText.getText().clear();
            Toast.makeText(this, "Saved to" + getFilesDir() + "/" + FILE_NAME1, Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(fos1 != null){
                try {
                    fos1.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void load(View view){
        FileInputStream fis = null;

        try {
            fis = openFileInput(FILE_NAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb =new StringBuilder();
            String text;

            while((text = br.readLine()) != null){
                sb.append(text).append("\n");
            }

            wEditText.setText(sb.toString());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if(fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        FileInputStream fis1 = null;

        try {
            fis1 = openFileInput(FILE_NAME1);
            InputStreamReader isr = new InputStreamReader(fis1);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb =new StringBuilder();
            String text1;

            while((text1 = br.readLine()) != null){
                sb.append(text1).append("\n");
            }

            hEditText.setText(sb.toString());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if(fis1 != null){
                try {
                    fis1.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}