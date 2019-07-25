package com.sjani.medieve.UI.MainList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sjani.medieve.Models.Event;
import com.sjani.medieve.R;
import com.sjani.medieve.Utils.ListItemListerner;
import com.sjani.medieve.Utils.StringUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {

    ListItemListerner listItemListerner;
    List<Event> eventList;

    public ListAdapter(ListItemListerner listItemListerner) {
        this.listItemListerner = listItemListerner;
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_list_item,parent,false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListViewHolder holder, int position) {
        Event event = eventList.get(position);
        String medicationName = event.getMedication();
        String medicationType = event.getMedicationtype();
        String id = String.valueOf(event.getId());
        String dateTime = event.getDatetime();
        if(dateTime.equals("")){
            dateTime = "2015-01-01T11:32:00.000Z";
        }
        String dateTime2 = StringUtils.formatDateTime(dateTime);
        holder.medicationNameTV.setText(medicationName);
        holder.medicationTypeTV.setText(medicationType);
        holder.idTV.setText(id);
        holder.dateTimeTV.setText(dateTime2);
    }

    @Override
    public int getItemCount() {
        if (eventList == null) {
            return 0;
        }
        return eventList.size();
    }

    public void swapResults ( List<Event> result) {
        if (eventList == result) {
            return;
        }
        this.eventList = result;
        if (result != null) {
            this.notifyDataSetChanged();
        }
    }

    public class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.medication_name)
        TextView medicationNameTV;

        @BindView(R.id.medication_type)
        TextView medicationTypeTV;

        @BindView(R.id.medication_id)
        TextView idTV;

        @BindView(R.id.medication_time)
        TextView dateTimeTV;


        public ListViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
                Event event =  eventList.get(getAdapterPosition());
                listItemListerner.onItemClick(event.getMedication(),StringUtils.formatDateTime(event.getDatetime()));
        }
    }

}
