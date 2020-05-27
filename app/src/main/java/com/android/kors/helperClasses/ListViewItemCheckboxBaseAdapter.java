package com.android.kors.helperClasses;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.android.kors.R;

import java.util.List;

public class ListViewItemCheckboxBaseAdapter extends BaseAdapter {
    private List<ListViewItemDataObject> listItem;
    private Context context;

    public ListViewItemCheckboxBaseAdapter(Context ctx, List<ListViewItemDataObject> list) {
        this.context = ctx;
        this.listItem = list;
    }

    @Override
    public int getCount() {
        int output = 0;
        if(listItem!=null) {
            output = listItem.size();
        }
        return output;
    }

    @Override
    public Object getItem(int itemIndex) {
        Object output = null;

        if(listItem!=null) {
            output = listItem.get(itemIndex);
        }
        return output;
    }

    @Override
    public long getItemId(int itemIndex) {
        return itemIndex;
    }

    @Override
    public View getView(int itemIndex, View convertView, ViewGroup viewGroup) {
        ListViewItemViewHolder viewHolder;

        if(convertView!=null)        {
            viewHolder = (ListViewItemViewHolder) convertView.getTag();

        } else        {
            convertView = View.inflate(context, R.layout.item_checkbox_row, null);
            CheckBox listItemCheckbox = convertView.findViewById(R.id.checkBoxTabItem);
            TextView listItemText = convertView.findViewById(R.id.textViewHomeTabRoomTaskItem);

            viewHolder = new ListViewItemViewHolder(convertView);
            viewHolder.setItemCheckbox(listItemCheckbox);
            viewHolder.setItemTextView(listItemText);

            convertView.setTag(viewHolder);
        }

        ListViewItemDataObject listViewItemDataObject = listItem.get(itemIndex);
        viewHolder.getItemCheckbox().setChecked(listViewItemDataObject.isChecked());
        viewHolder.getItemTextView().setText(listViewItemDataObject.getItemText());

        return convertView;
    }

}
