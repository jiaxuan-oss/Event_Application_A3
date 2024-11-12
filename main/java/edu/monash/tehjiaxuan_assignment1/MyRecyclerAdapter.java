package edu.monash.tehjiaxuan_assignment1;

import static java.security.AccessController.getContext;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.CustomViewHolder>{
    private ArrayList<CategoryEntity> data;
    public MyRecyclerAdapter() {
    }

    public void setData(ArrayList<CategoryEntity> categoryEntity){
        this.data = categoryEntity;
    }


    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_category_list, parent, false);
        CustomViewHolder viewHolder = new CustomViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        holder.tvName.setText(data.get(position).getName());
        holder.tvEventCount.setText(String.valueOf(data.get(position).getEventCount()));
        holder.tvId.setText(data.get(position).getCatId());
        String location = data.get(position).getEventLocation();


        if(String.valueOf(data.get(position).getActive()).equals("true")){
            holder.tvIsActive.setText("Yes");
        }
        else{
            holder.tvIsActive.setText("No");
            }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), GoogleMapActivity.class);
                intent.putExtra("Country", location);
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        public TextView tvName;
        public TextView tvId;
        public TextView tvIsActive;
        public TextView tvEventCount;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            tvId = itemView.findViewById(R.id.card_Id);
            tvName = itemView.findViewById(R.id.card_name);
            tvEventCount = itemView.findViewById(R.id.card_event_count);
            tvIsActive = itemView.findViewById(R.id.card_active);

        }
    }
}
