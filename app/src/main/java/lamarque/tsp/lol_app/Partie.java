package lamarque.tsp.lol_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class Partie extends AppCompatActivity implements AdapterView.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partie);

        Bundle extras = getIntent().getExtras();
        String gameID = new String(extras.getString("gameID"));

        AdapterPartieList adapter = new AdapterPartieList();

        ListView listpartie = (ListView) findViewById(R.id.listpartie);
        listpartie.setOnItemClickListener(this);

        AsyncJSONDataForPartie a = new AsyncJSONDataForPartie(this, adapter);
        a.execute("https://euw1.api.riotgames.com/lol/match/v4/matches/" + gameID + "?api_key=" + Api.API_KEY);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent histo = new Intent(getApplicationContext(), ListeHistorique.class);

        TextView textpartie = (TextView) view.findViewById(R.id.textlistpartie1);
        histo.putExtra("name", textpartie.getText());
        startActivity(histo);


    }
}