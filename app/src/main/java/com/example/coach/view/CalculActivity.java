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
import com.example.coach.model.Profil;
import com.example.coach.presenter.CalculPresenter;

public class CalculActivity extends AppCompatActivity implements ICalculView {

    private EditText txtPoids;
    private EditText txtAge;
    private EditText txtTaille;
    private Button btnCalculer;
    private RadioButton rdHomme;
    private RadioButton rdFemme;
    private TextView lblResultat;
    private ImageView imgSmiley;
    private CalculPresenter presenter;

    /**
     * Méthode de génération de l'Activite
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_calcul);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        init();
    }

    /**
     * Initialisation de la vue. Demande le chargement des objets graphiques,
     * active le listener sur le btnCalculer et initialise CalculPresenter
     */
    private void init() {
        chargeObjetsGraphiques();
        presenter = new CalculPresenter(this);
        btnCalculer.setOnClickListener(v -> btnCalculer_clic());
        recupProfil();
    }

    /**
     * Charge les objets graphiques
     */
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

    /**
     * S'active pour chaque clic du bouton calculer. Récupère les valeurs des champs indiqués. Les transforme
     * en string et demande la création d'un profil à presenter
     * Si tous les champs ne sont pas remplis, indique une erreur
     */
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

    /**
     * Affiche à l'utilisateur l'image appropriée (par défaut, normal), le message et l'IMG
     * @param image
     * @param img
     * @param message
     * @param normal
     */
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

    /**
     * Rempli les champs en fonction de ceux indiqués en paramètre
     * @param poids
     * @param taille
     * @param age
     * @param sexe
     */
    @Override
    public void remplirChamps(Integer poids, Integer taille, Integer age, Integer sexe) {
        txtAge.setText(age.toString());
        txtPoids.setText(poids.toString());
        txtTaille.setText(taille.toString());
        if (sexe == 1) {
            rdHomme.setChecked(true);
        }
    }

    @Override
    public void afficherMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void recupProfil(){
        Profil profil = (Profil) getIntent().getSerializableExtra("profil");
        if (profil != null) {
            remplirChamps(profil.getPoids(), profil.getTaille(), profil.getAge(), profil.getSexe());
        } else {
            presenter.chargerProfil();
        }
    }
}