package com.example.finalproject_savemoney.operations;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject_savemoney.R;
import com.example.finalproject_savemoney.fragments.FragmentCommunicator;
import com.example.finalproject_savemoney.repo.database.Operation;

import java.util.ArrayList;
import java.util.List;

public class OperationsAdapter extends RecyclerView.Adapter<OperationsAdapter.RecyclerViewHolder> {

    private List<Operation> operations;
    private FragmentCommunicator communicator;

    public OperationsAdapter(List<Operation> operationList, FragmentCommunicator communication) {
        this.operations = new ArrayList<>(operationList);
        this.communicator = communication;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.operation_list_item, parent, false);
        return new RecyclerViewHolder(view, communicator);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {

        if (operations.get(position).getCategory().getName().equals("Продукты")) {
            holder.imageViewIcon.setImageResource(R.drawable.group_18);
        } else if (operations.get(position).getCategory().getName().equals("Здоровье")) {
            holder.imageViewIcon.setImageResource(R.drawable.group_19);
        } else if (operations.get(position).getCategory().getName().equals("Кафе")) {
            holder.imageViewIcon.setImageResource(R.drawable.group_20);
        } else if (operations.get(position).getCategory().getName().equals("Досуг")) {
            holder.imageViewIcon.setImageResource(R.drawable.group_21);
        } else if (operations.get(position).getCategory().getName().equals("Транспорт")) {
            holder.imageViewIcon.setImageResource(R.drawable.group_22);
        } else if (operations.get(position).getCategory().getName().equals("Подарки")) {
            holder.imageViewIcon.setImageResource(R.drawable.group_23);
        } else if (operations.get(position).getCategory().getName().equals("Покупки")) {
            holder.imageViewIcon.setImageResource(R.drawable.group_24);
        } else if (operations.get(position).getCategory().getName().equals("Семья")) {
            holder.imageViewIcon.setImageResource(R.drawable.group_25);
        } else if (operations.get(position).getCategory().getName().equals("Зарплата")) {
            holder.imageViewIcon.setImageResource(R.drawable.group_29);
        } else {
            holder.imageViewIcon.setImageResource(R.drawable.group_26);
        }
        holder.textViewName.setText(operations.get(position).getCategory().getName());
        holder.textViewAccount.setText(operations.get(position).getAccount().getName());
        holder.textViewCurrency.setText(operations.get(position).getAccount().getCurrency());
        holder.textViewSum.setText(String.valueOf(operations.get(position).getOperationEntity().getSum()));
    }

    @Override
    public int getItemCount() {
        if (operations != null) {
            return operations.size();
        } else
            return 0;
    }

    public void setOperations(List<Operation> operationList) {
        operations = operationList;
        notifyDataSetChanged();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageViewIcon;
        private TextView textViewName;
        private TextView textViewAccount;
        private TextView textViewSum;
        private TextView textViewCurrency;
        private FragmentCommunicator mCommunication;

        public RecyclerViewHolder(@NonNull View itemView, FragmentCommunicator mCommunicator) {
            super(itemView);
            imageViewIcon = itemView.findViewById(R.id.itemOperationImageViewIcon);
            textViewName = itemView.findViewById(R.id.itemOperationTextViewName);
            textViewAccount = itemView.findViewById(R.id.itemOperationTextViewAccount);
            textViewSum = itemView.findViewById(R.id.itemOperationTextViewSum);
            textViewCurrency = itemView.findViewById(R.id.itemOperationTextViewCurrency);
            mCommunication = mCommunicator;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    String text = operations.get(position).getCategory().getName();
                    mCommunication.onItemClickListener(text);
                }
            });
        }
    }
}
