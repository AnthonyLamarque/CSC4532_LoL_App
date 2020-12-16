package lamarque.tsp.lol_app;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.Vector;

public class AdapterChampList extends BaseAdapter {

    public Vector<String> vector;
    public Vector<Bitmap> vectorimage;

    public AdapterChampList() {
        this.vector = new Vector<String>();
        this.vectorimage = new Vector<Bitmap>();
    }

    public void add(String url) {
        this.vector.add(url);
    }

    @Override
    public int getCount() {
        return this.vector.size();
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
                    .inflate(R.layout.listchampions, parent, false);
        }
        System.out.println("coucou");
        TextView urlfinal = (TextView) convertView.findViewById(R.id.textlistChampions);
        urlfinal.setText(this.vector.get(position));

        ImageView imageView = (ImageView) convertView.findViewById(R.id.imagelistChampions);

        if (this.vectorimage.size() != 0) {
            imageView.setImageBitmap(this.vectorimage.get(position));
        }

        return convertView;

    }


}
