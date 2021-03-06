package com.example.myapplication.Profile;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myapplication.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

// Classe responsável por remover um registo no booklet
public class BookletDelRowActivity extends AppCompatActivity {
    private Profile profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booklet_del_row);

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }

        // Recebe os dados do profile
        profile = new Profile();
        if (getIntent().getSerializableExtra(getString(R.string.profile)) != null)
            profile = (Profile) getIntent().getSerializableExtra(getString(R.string.profile));

        // Evento para a ação de confirmar a remoção
        Button btDone = findViewById(R.id.btDoneBDR);
        btDone.setOnClickListener(v -> {

            String filename = "profile.srl";
            ObjectOutput out = null;
            try {
                out = new ObjectOutputStream(new FileOutputStream(new File(getFilesDir(),"")+File.separator+filename));
                out.writeObject(profile);
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(verifyEditTexts()){
                Intent myIntent = new Intent(BookletDelRowActivity.this, BookletEditActivity.class);
                myIntent.putExtra(getString(R.string.profile), profile);
                BookletDelRowActivity.this.startActivity(myIntent);
                finish();
            }
        });

        // Evento para a ação de cancelar a remoção
        Button bBack = findViewById(R.id.btCancelBDR);
        bBack.setOnClickListener(v -> {
            Intent myIntent = new Intent(BookletDelRowActivity.this, BookletEditActivity.class);
            myIntent.putExtra(getString(R.string.profile), profile);
            BookletDelRowActivity.this.startActivity(myIntent);
        });

        // Evento para o botão de desligar a aplicação
        Button bLogOff = findViewById(R.id.LogOutBDR);
        bLogOff.setOnClickListener(v -> {
            moveTaskToBack(true);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        });

    }

    // Classe responsável pela verificação dos campos de texto
    public boolean verifyEditTexts() {
        String vaccine;

        EditText etVaccine = findViewById(R.id.etVaccineBDR);
        vaccine = etVaccine.getText().toString();

        if(TextUtils.isEmpty(vaccine)) {
            Toast.makeText(this,
                    R.string.need_to_incert_vaccine_name,
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        for (int i = 0; i <  profile.getBooklet().getVaccinesData().size(); i++){
            if (vaccine.equals(profile.getBooklet().getVaccinesData().get(i).getVaccineName())){
                profile.getBooklet().getVaccinesData().remove(i);
            }
        }
        return true;
    }

    // Caso o utilizador carregue no botão "up" volta para a Atividade anterior
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            Intent myIntent = new Intent(BookletDelRowActivity.this, BookletEditActivity.class);
            myIntent.putExtra(getString(R.string.profile), profile);
            BookletDelRowActivity.this.startActivity(myIntent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}