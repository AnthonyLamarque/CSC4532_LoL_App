package lamarque.tsp.lol_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ListeChampions extends AppCompatActivity implements AdapterView.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_champions);

        AdapterChampList adapter = new AdapterChampList();
        ListView list = (ListView) findViewById(R.id.listChampions);
        list.setOnItemClickListener(this);
        AsyncJSONDataForListChamp a = new AsyncJSONDataForListChamp(this, adapter);
        a.execute("https://ddragon.leagueoflegends.com/cdn/10.24.1/data/fr_FR/champion.json");
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent champion = new Intent(getApplicationContext(), Champion.class);

        ImageView image = (ImageView) view.findViewById(R.id.imagelistChampions);
        TextView text = (TextView) view.findViewById(R.id.textlistChampions);
        champion.putExtra("name", text.getText());
        champion.putExtra("image", ((BitmapDrawable)image.getDrawable()).getBitmap());
        champion.putExtra("position", position);
        champion.putExtra("id", id);
        startActivity(champion);

    }
}