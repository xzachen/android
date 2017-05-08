package com.toddway.sandbox;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import butterknife.InjectView;


public class ThingRecyclerAdapter extends BaseRecyclerAdapter<Thing> {

    @Override
    public ThingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ThingHolder(view);
    }

    public class ThingHolder extends BaseRecyclerAdapter<Thing>.ViewHolder {
        @InjectView(R.id.content)
        TextView contentTextView;
        public ThingHolder(View itemView) {
            super(itemView);
        }

        public void populate(Thing thing) {
            contentTextView.setText(thing.toString());
        }
    }
}
