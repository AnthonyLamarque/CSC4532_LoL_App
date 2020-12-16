package lamarque.tsp.lol_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button bHist = (Button) findViewById(R.id.buttonHist);
        bHist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent historique = new Intent(getApplicationContext(), ListeHistorique.class);
                startActivity(historique);
            }
        });

        Button bChamp = (Button) findViewById(R.id.buttonChampions);
        bChamp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent champions = new Intent(getApplicationContext(), ListeChampions.class);
                startActivity(champions);
            }
        });

        Button buttonfav = (Button) findViewById(R.id.buttonfav);
        buttonfav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent favoris = new Intent(getApplicationContext(), Favoris.class);
                startActivity(favoris);
            }
        });

    }
}