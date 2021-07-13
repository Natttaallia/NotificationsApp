package com.example.lab6kulbaka;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.function.Consumer;

/**
 * @author Kulbaka Nataly
 * @date 07.05.2021
 */
class NotificationAdapter extends ArrayAdapter<Notification> {

    private final int resourceLayout;
    private final Context mContext;
    private final Consumer<Notification> mOnDeleteClick;
    private final Consumer<Integer> mOnOpenClick;

    public NotificationAdapter(Context context, int resource, List<Notification> items,
                               Consumer<Notification> onClick, Consumer<Integer> onOpenClick) {
        super(context, resource, items);
        this.resourceLayout = resource;
        this.mContext = context;
        mOnDeleteClick = onClick;
        mOnOpenClick = onOpenClick;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(mContext);
            v = vi.inflate(resourceLayout, null);
        }
        Notification p = getItem(position);
        if (p != null) {
            TextView tt1 = (TextView) v.findViewById(R.id.text1);
            if (tt1 != null) {
                tt1.setText(tt1.getContext().getString(
                        R.string.data_item,
                        p.Theme,
                        p.Date.getTime()));
                tt1.setOnClickListener((view -> mOnOpenClick.accept(p.Id)));
            }
            ImageView iv = (ImageView) v.findViewById(R.id.delete);
            iv.setOnClickListener((view) -> mOnDeleteClick.accept(p));
        }
        
        return v;
    }

}