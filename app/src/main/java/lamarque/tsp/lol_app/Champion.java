package lamarque.tsp.lol_app;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Champion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_champion);

        Bundle extras = getIntent().getExtras();
        String name = new String(extras.getString("name"));
        Bitmap bitmap = (Bitmap) extras.get("image");

        TextView champName = (TextView) findViewById(R.id.champName);
        ImageView champImage = (ImageView) findViewById(R.id.champImage);
        champName.setText(name);
        champImage.setImageBitmap(bitmap);

        AsyncJSONDataForChamp a = new AsyncJSONDataForChamp(this, name);
        a.execute("http://ddragon.leagueoflegends.com/cdn/10.25.1/data/fr_FR/champion/" + name + ".json");



    }
}