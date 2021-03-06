package com.example.myapplication.Profile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
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

// Classe responsável por editar um registo no booklet
public class BookletEditActivity extends AppCompatActivity {
    private Profile profile;
    private ArrayList<EditText> editTexts;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booklet_edit);

        editTexts = new ArrayList<>();
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }

        // Recebe os dados do profile
        profile = new Profile();
        if(getIntent().getSerializableExtra(getString(R.string.profile)) != null)
            profile = (Profile)getIntent().getSerializableExtra(getString(R.string.profile));

        // Evento para a ação de adicionar um registo
        Button btAddRow = findViewById(R.id.btAddColuBE);
        btAddRow.setOnClickListener(v -> {
            Intent myIntent = new Intent(BookletEditActivity.this, BookletAddRowActivity.class);
            myIntent.putExtra(getString(R.string.profile), profile);
            BookletEditActivity.this.startActivity(myIntent);
        });

        // Evento para a ação de remover um registo
        Button btDelRow = findViewById(R.id.btDelColuBE);
        btDelRow.setOnClickListener(v -> {
            Intent myIntent = new Intent(BookletEditActivity.this, BookletDelRowActivity.class);
            myIntent.putExtra(getString(R.string.profile), profile);
            BookletEditActivity.this.startActivity(myIntent);
        });

        // Evento para a ação de confirmar a edição
        Button btDone = findViewById(R.id.btDoneBE);
        btDone.setOnClickListener(v -> {
            updateData();

            String filename = "profile.srl";
            ObjectOutput out = null;
            try {
                out = new ObjectOutputStream(new FileOutputStream(new File(getFilesDir(),"")+File.separator+filename));
                out.writeObject(profile);
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Intent myIntent = new Intent(BookletEditActivity.this, BookletActivity.class);
            myIntent.putExtra(getString(R.string.profile), profile);
            BookletEditActivity.this.startActivity(myIntent);
            finish();
        });

        // Evento para o botão de desligar a aplicação
        Button bLogOff = findViewById(R.id.LogOutBE);
        bLogOff.setOnClickListener(v -> {
            moveTaskToBack(true);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        });
        createFill(profile.getBooklet());

        TableLayout tableLayout = findViewById(R.id.tableDataBE);
        tableLayout.invalidate();
    }

    // Classe responsável por mostrar uma tabela com os registos do booklet
    @SuppressLint("UseCompatLoadingForDrawables")
    public void createFill(Booklet vaccinesTable) {
        TableLayout stk = findViewById(R.id.tableDataBE);
        for (int i = 0; i < vaccinesTable.getVaccinesData().size(); i++) {
            Drawable border;

            if ((i % 2) == 0)
                border = this.getResources().getDrawable(R.drawable.cell_par);
            else
                border = this.getResources().getDrawable(R.drawable.cell_impar);

            TableRow tbrow = new TableRow(this);
            EditText t1v = new EditText(this);
            t1v.setText(vaccinesTable.getVaccinesData().get(i).getVaccineName());
            t1v.setTextColor(getResources().getColor(R.color.black));
            t1v.setTextSize(16);
            t1v.setGravity(Gravity.CENTER);
            t1v.setBackground(border);
            t1v.setPadding(20,20,20,20);
            t1v.setId(count++);
            tbrow.addView(t1v);
            editTexts.add(t1v);

            EditText t2v = new EditText(this);
            t2v.setText(vaccinesTable.getVaccinesData().get(i).getDate());
            t2v.setTextColor(getResources().getColor(R.color.black));
            t2v.setTextSize(16);
            t2v.setGravity(Gravity.CENTER);
            t2v.setBackground(border);
            t2v.setPadding(20,20,20,20);
            t2v.setId(count++);
            tbrow.addView(t2v);
            stk.addView(tbrow);
            editTexts.add(t2v);
        }
    }

    // Atualiza a informação
    public void  updateData() {
        ArrayList<VaccineData> vaccines = new ArrayList<>();

        for(int i = 0; i < editTexts.size(); i+=2){
            VaccineData vaccineData = new VaccineData(editTexts.get(i).getText().toString(), editTexts.get(i+1).getText().toString());
            vaccines.add(vaccineData);
        }
        profile.getBooklet().setVaccinesData(vaccines);
    }

    // Caso o utilizador carregue no botão "up" volta para a Atividade anterior
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            Intent myIntent = new Intent(BookletEditActivity.this, BookletActivity.class);
            myIntent.putExtra(getString(R.string.profile), profile);
            BookletEditActivity.this.startActivity(myIntent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
