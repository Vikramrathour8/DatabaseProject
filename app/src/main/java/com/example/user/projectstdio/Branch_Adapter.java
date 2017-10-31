package com.example.user.projectstdio;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 29-10-2017.
 */

public class Branch_Adapter extends RecyclerView.Adapter<Branch_Adapter.viewHolder> {
    private List<String[]> college_list;
    private int mNumberItems;
    final private Branch_Adapter.ListItemClickListener mOnClickListener;

    public interface ListItemClickListener
    {
        void onListItemClick(int number);


    }

    public Branch_Adapter( List<String[]> list,Branch_Adapter.ListItemClickListener listItemClickListener)
    {
        college_list=list;
        mNumberItems=list.size();
        mOnClickListener=listItemClickListener;
    }
    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.branch_recycler,parent,false);
        viewHolder viewer=new viewHolder(view);
        return viewer;
    }

    @Override
    public void onBindViewHolder(viewHolder holder, int position) {

        holder.bind(position);

    }
    void setData(List<String[]> li)
    {
        college_list=li;
        Log.d("REPLY",Integer.toString(li.size()));
        mNumberItems=college_list.size();
    }

    @Override
    public int getItemCount() {
        return mNumberItems;
    }

    class viewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView branch_name;
        public viewHolder(View itemView) {
            super(itemView);
            branch_name=(TextView) itemView.findViewById(R.id.branch_name);
            itemView.setOnClickListener(this);
        }
        void bind(int position)
        {
         if(mNumberItems==0)
             return;
         branch_name.setText(college_list.get(position)[0]);

        }
        @Override
        public void onClick(View view) {
            int adapterPosition=getAdapterPosition();
            mOnClickListener.onListItemClick(adapterPosition);
        }
    }
}
