package com.example.marvelapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.marvelapp.R;
import com.example.marvelapp.models.EnterpriseEmail;

import java.util.ArrayList;
import java.util.List;

public class RecyclerEnterpriseEmailAdapter extends RecyclerView.Adapter<RecyclerEnterpriseEmailAdapter.MyViewHolder> implements Filterable {

    private static final String TAG = "RecyclerAdapter";

    private List<EnterpriseEmail> enterpriseEmails = new ArrayList<>();
    private List<EnterpriseEmail> enterpriseEmailsFull ;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_ee_list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.bind(enterpriseEmails.get(position));
    }

    @Override
    public int getItemCount() {
        return enterpriseEmails.size();
    }

    public void setEnterpriseEmail(List<EnterpriseEmail> enterpriseEmails){
        this.enterpriseEmails = enterpriseEmails;
        enterpriseEmailsFull = new ArrayList<>(enterpriseEmails);
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            List<EnterpriseEmail> emailfilterdList = new ArrayList<>();

            if(charSequence == null || charSequence.length() == 0)
            {
                emailfilterdList.addAll(enterpriseEmailsFull);
            }
            else
            {
                String filterpattern= charSequence.toString().toLowerCase().trim();

                for(EnterpriseEmail model : enterpriseEmailsFull)
                {
                    if(model.getTitle().toLowerCase().contains(filterpattern))
                    {
                        emailfilterdList.add(model);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = emailfilterdList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            enterpriseEmails.clear();
            enterpriseEmails.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };


    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView title, numbackers,by;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            numbackers = itemView.findViewById(R.id.no);
            by = itemView.findViewById(R.id.by);
        }

        public void bind(EnterpriseEmail enterpriseEmail){
            title.setText(enterpriseEmail.getTitle());
            numbackers.setText(enterpriseEmail.getNumbackers());
            by.setText(enterpriseEmail.getBy());
        }

    }
}
