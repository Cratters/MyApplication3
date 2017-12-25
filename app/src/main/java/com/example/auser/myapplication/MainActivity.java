package com.example.auser.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.*;

public class MainActivity extends AppCompatActivity {

    File f,myfile;

    Button button,button2,button3,button4;
    TextView textView;
    EditText editText;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        setListeners();
        f = getFilesDir();
        Log.d("File", f.getAbsolutePath());
    }

    void findViews() {
        button = (Button) findViewById(R.id.button);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);
        textView = (TextView) findViewById(R.id.textView);
        editText = (EditText) findViewById(R.id.editText);
    }

    void setListeners() {
        button.setOnClickListener(listener);
        button2.setOnClickListener(listener);
        button3.setOnClickListener(listener);
        button4.setOnClickListener(listener);
    }

    Button.OnClickListener listener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {

            myfile = new File(f.getAbsolutePath() + f.separator + "text.txt");

            switch (v.getId()) {
                case R.id.button:

                    try {
                        FileOutputStream fw = new FileOutputStream(myfile);
                        OutputStreamWriter osw = new OutputStreamWriter(fw,"UTF-8");
                        BufferedWriter bw = new BufferedWriter(osw);
                        bw.write(editText.getText().toString());
                        bw.flush();
                        bw.close();
                        osw.close();
                        fw.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;

                case R.id.button2:
                    if (myfile.exists()) {
                        try {
                            FileInputStream fis = new FileInputStream(myfile);
                            InputStreamReader isr = new InputStreamReader(fis, "Big5");
                            BufferedReader br = new BufferedReader(isr);
                            String str;
                            StringBuilder sb = new StringBuilder();
                            while ((str = br.readLine()) != null) {
                                sb.append(str);
                                sb.append("\n");
                            }
                            //System.out.println("NAME:\tSCORE:");
                            //System.out.println(sb);
                            //textView.setText(sb);
                            Toast.makeText(context, sb, Toast.LENGTH_SHORT).show();
                            fis.close();
                            isr.close();
                            br.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(context, "檔案不存在", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.button3:
                    Intent it = new Intent(MainActivity.this, SettingsActivity.class);
                    startActivity(it);
                    break;
                case R.id.button4:
                    SharedPreferences sp = getSharedPreferences(getPackageName() + "_preferences",MODE_PRIVATE);
                    String str = sp.getString("example_text","");
                    Toast.makeText(context,"Hello" + str, Toast.LENGTH_SHORT).show();

            }

        }
    };
}
