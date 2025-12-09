package com.example.coach.view;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.coach.R;
import com.example.coach.contract.ICalculView;
import com.example.coach.presenter.CalculPresenter;

public class MainActivity extends AppCompatActivity implements ICalculView {

    private EditText txtPoids;
    private EditText txtAge;
    private EditText txtTaille;
    private Button btnCalculer;
    private RadioButton rdHomme;
    private RadioButton rdFemme;
    private TextView lblResultat;
    private ImageView imgSmiley;
    private CalculPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();
    }

    private void init() {
        chargeObjetsGraphiques();
        presenter = new CalculPresenter(this);
        btnCalculer.setOnClickListener(v -> btnCalculer_clic());
    }

    private void chargeObjetsGraphiques(){
        txtPoids = (EditText) findViewById(R.id.txtPoids);
        txtAge = (EditText) findViewById(R.id.txtAge);
        txtTaille = (EditText) findViewById(R.id.txtTaille);
        lblResultat = (TextView) findViewById(R.id.lblResultat);
        imgSmiley = (ImageView) findViewById(R.id.imgSmiley);
        rdHomme = (RadioButton) findViewById(R.id.rdHomme);
        rdFemme = (RadioButton) findViewById(R.id.rdFemme);
        btnCalculer = (Button) findViewById(R.id.btnCalculer);
    }

    private void btnCalculer_clic(){
        Integer poids = 0;
        Integer age = 0;
        Integer taille = 0;
        Integer sexe = 0;

        try {
            poids = Integer.parseInt(txtPoids.getText().toString());
            age = Integer.parseInt(txtAge.getText().toString());
            taille = Integer.parseInt(txtTaille.getText().toString());
        } catch (Exception ignored) {}

        if(rdHomme.isChecked()) {
            sexe = 1;
        }

        if(poids == 0 || age == 0 || taille == 0) {
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
        } else {
            presenter.creerProfil(poids, taille, age, sexe);
        }
    }

    @Override
    public void afficherResultat(String image, double img, String message, boolean normal) {
        int imageId = getResources().getIdentifier(image, "drawable", getPackageName());
        if (imageId == 0) {
            imgSmiley.setImageResource(R.drawable.normal);
        } else {
            imgSmiley.setImageResource(imageId);
        }

        String texte = (int)img + ": IMG " + message;
        lblResultat.setText(texte);
        if(normal) {
            lblResultat.setTextColor(Color.GREEN);
        } else {
            lblResultat.setTextColor(Color.RED);
        }

    }
}