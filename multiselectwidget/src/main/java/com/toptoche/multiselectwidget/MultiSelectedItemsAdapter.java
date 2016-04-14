package com.toptoche.multiselectwidget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


public class MultiSelectedItemsAdapter extends ArrayAdapter {

    private Context _context;

    public MultiSelectedItemsAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
        this._context = context;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View rowView = convertView;

        final ViewHolder viewHolder;

        if (rowView == null) {

            LayoutInflater inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            rowView = inflater.inflate(R.layout.removable_cell, null);

            viewHolder = new ViewHolder();

            viewHolder.txtName = (TextView) rowView.findViewById(R.id.txtValue);
            viewHolder.txtDelete = (TextView) rowView.findViewById(R.id.txtDelete);

            rowView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) rowView.getTag();
        }

        viewHolder.txtName.setText(getItem(position).toString());

        viewHolder.txtDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove(getItem(position));
                notifyDataSetChanged();
            }
        });

        return rowView;
    }

    class ViewHolder {
        private TextView txtName;
        private TextView txtDelete;
    }
}