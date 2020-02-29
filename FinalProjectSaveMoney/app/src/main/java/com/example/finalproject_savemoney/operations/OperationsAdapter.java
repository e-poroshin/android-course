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
import com.example.finalproject_savemoney.repo.database.AccountEntity;
import com.example.finalproject_savemoney.repo.database.CategoryEntity;
import com.example.finalproject_savemoney.repo.database.Operation;
import com.example.finalproject_savemoney.repo.database.OperationEntity;

import java.util.ArrayList;
import java.util.List;

public class OperationsAdapter extends RecyclerView.Adapter<OperationsAdapter.RecyclerViewHolder> {

    private List<OperationEntity> operations;
    private FragmentCommunicator communicator;
    private List<CategoryEntity> categories;
    private List<AccountEntity> accounts;

    public OperationsAdapter(List<OperationEntity> operationList, FragmentCommunicator communication,
                             List<CategoryEntity> categoryList, List<AccountEntity> accountList) {
        this.operations = new ArrayList<>(operationList);
        this.communicator = communication;
        this.categories = new ArrayList<>(categoryList);
        this.accounts = new ArrayList<>(accountList);
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.operation_list_item, parent, false);
        return new RecyclerViewHolder(view, communicator);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {

        for (int i = 0; i < categories.size(); i++) {
            if (operations.get(position).getCategoryId() == categories.get(i).getId()) {
                holder.imageViewIcon.setImageResource(R.drawable.group_18);
                holder.textViewName.setText("Продукты");
            }
            if (operations.get(position).getCategoryId() == categories.get(i).getId()) {
                holder.imageViewIcon.setImageResource(R.drawable.group_19);
                holder.textViewName.setText("Здоровье");
            }
            if (operations.get(position).getCategoryId() == categories.get(i).getId()) {
                holder.imageViewIcon.setImageResource(R.drawable.group_20);
                holder.textViewName.setText("Кафе");
            }
            if (operations.get(position).getCategoryId() == categories.get(i).getId()) {
                holder.imageViewIcon.setImageResource(R.drawable.group_21);
                holder.textViewName.setText("Досуг");
            }
            if (operations.get(position).getCategoryId() == categories.get(i).getId()) {
                holder.imageViewIcon.setImageResource(R.drawable.group_22);
                holder.textViewName.setText("Транспорт");
            }
            if (operations.get(position).getCategoryId() == categories.get(i).getId()) {
                holder.imageViewIcon.setImageResource(R.drawable.group_23);
                holder.textViewName.setText("Подарки");
            }
            if (operations.get(position).getCategoryId() == categories.get(i).getId()) {
                holder.imageViewIcon.setImageResource(R.drawable.group_24);
                holder.textViewName.setText("Покупки");
            }
            if (operations.get(position).getCategoryId() == categories.get(i).getId()) {
                holder.imageViewIcon.setImageResource(R.drawable.group_25);
                holder.textViewName.setText("Семья");
            }
            if (operations.get(position).getCategoryId() == categories.get(i).getId()) {
                holder.imageViewIcon.setImageResource(R.drawable.group_29);
                holder.textViewName.setText("Зарплата");
            }
        }

        for (int i = 0; i < accounts.size(); i++) {
            if (operations.get(position).getAccountId() == accounts.get(i).getId()) {
                holder.textViewAccount.setText("Наличные");
                holder.textViewCurrency.setText("BYN");
            }
            if (operations.get(position).getAccountId() == accounts.get(i).getId()) {
                holder.textViewAccount.setText("Карта");
                holder.textViewCurrency.setText("BYN");
            }
        }
        holder.textViewSum.setText(String.valueOf(operations.get(position).getSum()));
    }

    @Override
    public int getItemCount() {
        if (operations != null) {
            return operations.size();
        } else
            return 0;
    }

    public void setOperations(List<OperationEntity> operationList) {
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
                    String text = operations.get(position).getDescription();
                    mCommunication.onItemClickListener(text);
                }
            });
        }
    }
}
