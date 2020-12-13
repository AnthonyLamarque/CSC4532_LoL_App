package lamarque.tsp.lol_app;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Vector;

public class AdapterHistList extends BaseAdapter {

    public Vector<String> vectordate;
    public Vector<String> vectortext;
    public Vector<Bitmap> vectorimage;
    public Vector<String> vectorgame;

    public AdapterHistList() {
        this.vectordate = new Vector<String>();
        this.vectorimage = new Vector<Bitmap>();
        this.vectortext = new Vector<String>();
        this.vectorgame = new Vector<String>();
    }

    public void add(String url) {
        this.vectordate.add(url);
    }

    @Override
    public int getCount() {
        return this.vectordate.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.listhistorique, parent, false);
        }

        TextView textdate = (TextView) convertView.findViewById(R.id.textlisthist1);
        textdate.setText(this.vectordate.get(position));

        TextView text = (TextView) convertView.findViewById(R.id.textlisthist2);
        text.setText(this.vectortext.get(position));
        if (text.getText().equals("Victoire")) {
            text.setTextColor(0xff008000);
        } else {
            text.setTextColor(0xffFF0000);
        }


        ImageView image = (ImageView) convertView.findViewById(R.id.imageChampion);
        if (this.vectorimage.get(position) != null) {
            image.setImageBitmap(this.vectorimage.get(position));
        }

        TextView textgame = (TextView) convertView.findViewById(R.id.textgame);
        textgame.setText(this.vectorgame.get(position));


        return convertView;

    }


}
