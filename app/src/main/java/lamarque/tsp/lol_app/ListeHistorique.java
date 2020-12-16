package lamarque.tsp.lol_app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ListeHistorique extends AppCompatActivity implements AdapterView.OnItemClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_historique);

        Activity activity = this;

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String textpartie = extras.getString("name");
            EditText edithisto = (EditText) findViewById(R.id.SearchHist);
            edithisto.setText(textpartie);
            Toast.makeText(getApplicationContext(), "Chargement de l'historique...", Toast.LENGTH_LONG).show();
            AdapterHistList adapter = new AdapterHistList();

            String playertrue = textpartie.replaceAll(" ", "%20");
            AsyncJSONDataForListHistorique a = new AsyncJSONDataForListHistorique(activity, adapter);
            a.execute("https://euw1.api.riotgames.com/lol/summoner/v4/summoners/by-name/" + playertrue + "?api_key=" + Api.API_KEY);
            adapter.notifyDataSetChanged();
        }


        ListView listhist = (ListView) findViewById(R.id.listHist);
        listhist.setOnItemClickListener(this);

        Button bListHist = (Button) findViewById(R.id.buttonlistHist);
        bListHist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(v.getContext(), "Chargement de l'historique...", Toast.LENGTH_LONG).show();
                AdapterHistList adapter = new AdapterHistList();
                EditText edithist =  (EditText) findViewById(R.id.SearchHist);
                String player = edithist.getText().toString();
                String playertrue = player.replaceAll(" ", "%20");
                AsyncJSONDataForListHistorique a = new AsyncJSONDataForListHistorique(activity, adapter);
                a.execute("https://euw1.api.riotgames.com/lol/summoner/v4/summoners/by-name/" + playertrue + "?api_key=" + Api.API_KEY);
                adapter.notifyDataSetChanged();

            }
        });

        Button bFav = (Button) findViewById(R.id.buttonhistfav);
        MyDatabase mydb = new MyDatabase(this);
        bFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edithist = (EditText) findViewById(R.id.SearchHist);
                mydb.insertData(edithist.getText());
                Toast.makeText(activity.getApplicationContext(), "Ajouté au favori !", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent partie = new Intent(getApplicationContext(), Partie.class);

        TextView textgame = (TextView) view.findViewById(R.id.textgame);
        partie.putExtra("gameID", textgame.getText());
        startActivity(partie);

    }
}
