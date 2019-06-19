package com.example.financerepublicassign.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.financerepublicassign.Model.StockModel;
import com.example.financerepublicassign.OnEditTextChanged;
import com.example.financerepublicassign.R;

import java.util.ArrayList;
import java.util.List;

public class StockAdapter extends RecyclerView.Adapter<StockAdapter.BaseVH> {
    private  Context context;
    List<StockModel> list;
    private OnEditTextChanged onEditTextChanged;
    private boolean isEditable=true;

    public StockAdapter(Context context, ArrayList<StockModel> list, OnEditTextChanged onEditTextChanged){
        this.list=list;
        this.context=context;
        this.onEditTextChanged=onEditTextChanged;
    }

    @NonNull
    @Override
    public BaseVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        if (isEditable) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.stockadapter, viewGroup, false);
            return new Viewholder(v);
        }
        else
        {
            View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.stockadapter_updated,viewGroup,false);
            return new ViewholderUpdated(v);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseVH viewholder, final int i) {

        viewholder.update(i);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void enableEdit(boolean b) {
        isEditable=b;
    }

    public class Viewholder extends  BaseVH {
        TextView name,price;
        EditText unit;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            price=itemView.findViewById(R.id.price);
            unit=itemView.findViewById(R.id.unit);

            unit.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int pos, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int pos, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    if(unit.getText().length()!=0){
                        //    list.get(i).setUnit(Integer.parseInt(charSequence.toString()));
                        onEditTextChanged.onTextChanged(getAdapterPosition(), unit.getText().toString());
                    }
                }
            });

        }

        @Override
        public void update(int pos) {
            name.setText(list.get(pos).getName());
            price.setText(list.get(pos).getPrice()+"("+list.get(pos).getDate()+")");
            unit.setText(list.get(pos).getUnit()+"");
            unit.setEnabled(isEditable);
            unit.setFocusable(isEditable);
        }
    }
    public class ViewholderUpdated extends  BaseVH {
        TextView name,price,updatedPrice;
        EditText unit;
        public ViewholderUpdated(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            price=itemView.findViewById(R.id.price);
            unit=itemView.findViewById(R.id.unit);
            updatedPrice=itemView.findViewById(R.id.updatedPrice);

            unit.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int pos, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int pos, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    if(unit.getText().length()!=0){
                        //    list.get(i).setUnit(Integer.parseInt(charSequence.toString()));
                        onEditTextChanged.onTextChanged(getAdapterPosition(), unit.getText().toString());
                    }
                }
            });
        }

        @Override
        public void update(int pos) {
            name.setText(list.get(pos).getName());
            price.setText(list.get(pos).getPrice()+"");
            unit.setText(list.get(pos).getUnit()+"");
            unit.setEnabled(isEditable);
            unit.setFocusable(isEditable);
            updatedPrice.setText("Updated Price Today :"+ list.get(pos).getCurrentPrice()+"("+list.get(pos).getCurrentdate()+")");
        }

    }
    public abstract class BaseVH extends  RecyclerView.ViewHolder{

        public BaseVH(@NonNull View itemView) {
            super(itemView);
        }
        abstract public void  update(int pos);
    }

}
