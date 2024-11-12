package edu.monash.tehjiaxuan_assignment1;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class EventRecyclerViewAdapter extends RecyclerView.Adapter<EventRecyclerViewAdapter.ViewHolder> {
    ArrayList<EventEntity> data;
    public void setData(ArrayList<EventEntity> data){
        this.data = data;
    }

    @NonNull
    @Override
    public EventRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_event_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull EventRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.tvId.setText(data.get(position).getEventID());
        holder.tvCategoryId.setText(data.get(position).getCategoryID());
        holder.tvTicket.setText(String.valueOf(data.get(position).getTicketsAvailable()));
        holder.tvName.setText(data.get(position).getEventName());


        if (String.valueOf(data.get(position).getIsActive()).equals("true")){
            holder.tvActive.setText("Active");
        }
        else{
            holder.tvActive.setText("Not Active");
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), EventGoogleResult.class);
                intent.putExtra("Name", holder.tvName.getText().toString());
                holder.itemView.getContext().startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return data.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvId;
        public TextView tvName;
        public TextView tvCategoryId;
        public TextView tvTicket;
        public TextView tvActive;
        public EditText ETCatLocation;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.Card_event_id);
            tvName = itemView.findViewById(R.id.Card_Event_name);
            tvCategoryId = itemView.findViewById(R.id.Card_Category_Id);
            tvActive= itemView.findViewById(R.id.Card_Event_Active);
            tvTicket = itemView.findViewById(R.id.Card_Event_Ticket);
        }
    }
}
