package com.example.user.projectstdio;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 27-10-2017.
 */

public class CollegeAdapter extends RecyclerView.Adapter<CollegeAdapter.NumberViewHolder> {
    private static int viewHolderCount;
    private ArrayList<String[]> college_list;
    private int mNumberItems;
    final private ListItemClickListener mOnClickListener;

    public interface ListItemClickListener
    {
        void onListItemClick(int number);


    }

    public CollegeAdapter( ArrayList<String[]> list,ListItemClickListener listItemClickListener)
    {
        college_list=list;
        mNumberItems=list.size();
        mOnClickListener=listItemClickListener;
    }
    public CollegeAdapter(ArrayList<String[]> list)
    {
        college_list=list;
        mNumberItems=list.size();
        mOnClickListener=null;
    }
    public void dismissApplication(int pos)
    {
        college_list.remove(pos);
        setData(college_list);
        notifyDataSetChanged();
    }
    public String[] getData(int pos)
    {
        return college_list.get(pos);
    }
    @Override
    public NumberViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context=parent.getContext();
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.college_list_layout,parent,false);
       NumberViewHolder viewHolder=new NumberViewHolder(view);
       return viewHolder;
    }

    @Override
    public void onBindViewHolder(NumberViewHolder holder, int position) {

        holder.bind(position);

    }
    void setData(ArrayList<String[]> li)
    {
        college_list=li;
        mNumberItems=college_list.size();
    }

    @Override
    public int getItemCount() {
        return mNumberItems;
    }


     class NumberViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView icon;
        TextView college_name;
        TextView college_place;

        public NumberViewHolder(View itemView) {
            super(itemView);
            icon=(TextView) itemView.findViewById(R.id.college_icon);
            college_name=(TextView) itemView.findViewById(R.id.college_name);
            college_place=(TextView) itemView.findViewById(R.id.college_location);
            itemView.setOnClickListener(this);

        }

        void bind(int listIndex)
        {
            if(mNumberItems==0)
                return ;
            icon.setText(college_list.get(listIndex)[0]);
            college_name.setText(college_list.get(listIndex)[1]);
            college_place.setText(college_list.get(listIndex)[2]);
        }


         @Override
         public void onClick(View view) {
             int adapterPosition=getAdapterPosition();
             mOnClickListener.onListItemClick(adapterPosition);
         }
     }

}
