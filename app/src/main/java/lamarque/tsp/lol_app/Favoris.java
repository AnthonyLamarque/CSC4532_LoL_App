package lamarque.tsp.lol_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Favoris extends AppCompatActivity implements AdapterView.OnItemClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoris);

        ListView listfav = (ListView) findViewById(R.id.listfav);
        listfav.setOnItemClickListener(this);
        AdapterFavList adapter = new AdapterFavList();

        MyDatabase mydb = new MyDatabase(this);
        ArrayList<String> resultat = mydb.readData();

        for (int i=0; i<resultat.size(); i++) {
            adapter.vectortext.add(resultat.get(i));
        }
        listfav.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent histo = new Intent(getApplicationContext(), ListeHistorique.class);

        TextView textlistfav = (TextView) view.findViewById(R.id.textlistfav);
        histo.putExtra("name", textlistfav.getText());
        startActivity(histo);
    }
}