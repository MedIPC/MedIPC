package com.example.myapplication.Alarms;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

// Classe responsável pela edição de um alarme
public class AlarmEditActivity extends AppCompatActivity {
    private AlarmProfile alarmprofile;
    private ArrayList<EditText> editTexts;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_edit);

        editTexts = new ArrayList<>();
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }

        // Recebe os dados dos alarmes
        alarmprofile = new AlarmProfile();
        if(getIntent().getSerializableExtra(getString(R.string.alarm)) != null)
            alarmprofile = (AlarmProfile) getIntent().getSerializableExtra(getString(R.string.alarm));

        // Evento para a ação de adicionar um novo alarme
        Button btAddRow = findViewById(R.id.btAddColAlarm);
        btAddRow.setOnClickListener(v -> {
            Intent myIntent = new Intent(AlarmEditActivity.this, AlarmAddRowActivity.class);
            myIntent.putExtra(getString(R.string.alarm), alarmprofile);
            AlarmEditActivity.this.startActivity(myIntent);
        });

        // Evento para a ação de remover um alarme existente
        Button btDelRow = findViewById(R.id.btDelColAlarm);
        btDelRow.setOnClickListener(v -> {
            Intent myIntent = new Intent(AlarmEditActivity.this, AlarmDelRowActivity.class);
            myIntent.putExtra(getString(R.string.alarm), alarmprofile);
            AlarmEditActivity.this.startActivity(myIntent);
        });

        // Evento para a ação de confirmar a edição dos alarmes
        Button btDone = findViewById(R.id.btDoneAE);
        btDone.setOnClickListener(v -> {
            updateData();

            String filename = "alarm.srl";
            ObjectOutput out = null;

            try {
                out = new ObjectOutputStream(new FileOutputStream(new File(getFilesDir(),"")+File.separator+filename));
                out.writeObject(alarmprofile);
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Intent myIntent = new Intent(AlarmEditActivity.this, AlarmActivity.class);
            myIntent.putExtra(getString(R.string.alarm), alarmprofile);
            AlarmEditActivity.this.startActivity(myIntent);
            finish();
        });

        // Evento para o botão de desligar a aplicação
        Button bLogOff = findViewById(R.id.LogOutAE);
        bLogOff.setOnClickListener(v -> {
            moveTaskToBack(true);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        });
        createFill(alarmprofile.getAlarm());

        TableLayout tableLayout = findViewById(R.id.tableDataAlarmEdit);
        tableLayout.invalidate();
    }


    // Cria a lista dos alarmes
    @SuppressLint("UseCompatLoadingForDrawables")
    public void createFill(Alarm alarmsTable) {
        TableLayout stk = findViewById(R.id.tableDataAlarmEdit);
        for (int i = 0; i < alarmsTable.getAlarmsData().size(); i++) {
            Drawable border;

            if ((i % 2) == 0)
                border = this.getResources().getDrawable(R.drawable.cell_par);
            else
                border = this.getResources().getDrawable(R.drawable.cell_impar);

            TableRow tbrow = new TableRow(this);
            EditText t1v = new EditText(this);
            t1v.setText(alarmsTable.getAlarmsData().get(i).getName());
            t1v.setTextColor(getResources().getColor(R.color.black));
            t1v.setTextSize(16);
            t1v.setGravity(Gravity.CENTER);
            t1v.setBackground(border);
            t1v.setPadding(20,20,20,20);
            t1v.setId(count++);
            tbrow.addView(t1v);
            editTexts.add(t1v);

            EditText t2v = new EditText(this);
            t2v.setText(alarmsTable.getAlarmsData().get(i).getDescription());
            t2v.setTextColor(getResources().getColor(R.color.black));
            t2v.setTextSize(16);
            t2v.setGravity(Gravity.CENTER);
            t2v.setBackground(border);
            t2v.setPadding(20,20,20,20);
            t2v.setId(count++);
            tbrow.addView(t2v);
            editTexts.add(t2v);

            EditText t3v = new EditText(this);
            t3v.setText(alarmsTable.getAlarmsData().get(i).getSetTime());
            t3v.setTextColor(getResources().getColor(R.color.black));
            t3v.setTextSize(16);
            t3v.setGravity(Gravity.CENTER);
            t3v.setBackground(border);
            t3v.setPadding(20,20,20,20);
            t3v.setId(count++);
            tbrow.addView(t3v);
            stk.addView(tbrow);
            editTexts.add(t3v);
        }
    }

    // Atualiza a tabela dos alarmes
    public void  updateData() {
        ArrayList<AlarmData> alarms = new ArrayList<>();

        for(int i = 0; i < editTexts.size(); i+=3){
            AlarmData alarmData = new AlarmData(editTexts.get(i).getText().toString(), editTexts.get(i+1).getText().toString(),editTexts.get(i+2).getText().toString());
            alarms.add(alarmData);
        }
        alarmprofile.getAlarm().setAlarmsData(alarms);
    }

    // Caso o utilizador carregue no botão "up" volta para a Atividade anterior
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            Intent myIntent = new Intent(AlarmEditActivity.this, AlarmActivity.class);
            myIntent.putExtra(getString(R.string.alarm), alarmprofile);
            AlarmEditActivity.this.startActivity(myIntent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}