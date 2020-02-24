package com.example.task_contentresolver;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerViewContactList;
    private ContentResolver contentResolver;
    private List<Contact> contactList;
    private String uriString = "content://com.gmail.task04_database.contentprovider/contact";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        contentResolver = getContentResolver();
        setContentView(R.layout.activity_main);
        recyclerViewContactList = findViewById(R.id.recyclerViewContactList);
    }

    @Override
    protected void onResume() {
        super.onResume();
        contactList = getContactList();
        showContactList(contactList);
    }

    private List<Contact> getContactList() {

        String[] projection = new String[]{"name", "data", "option", "id"};

        Cursor resultCursor = contentResolver.query(
                Uri.parse(uriString),
                projection,
                null,
                null,
                "name"
        );

        ArrayList<Contact> contactsList = new ArrayList<>();
        if (resultCursor != null) {

            if (resultCursor.moveToFirst()) {
                int nameInd = resultCursor.getColumnIndex("name");
                int dataInd = resultCursor.getColumnIndex("data");
                int optionInd = resultCursor.getColumnIndex("option");
                int idInd = resultCursor.getColumnIndex("id");

                do {
                    String contactName = resultCursor.getString(nameInd);
                    String contactData = resultCursor.getString(dataInd);
                    String contactOption = resultCursor.getString(optionInd);
                    String contactId = resultCursor.getString(idInd);

                    Contact contact = new Contact(contactName, contactData, contactOption, contactId);
                    contactsList.add(contact);
                } while (resultCursor.moveToNext());
            }
            resultCursor.close();
        }
        return contactsList;
    }

    private int deleteContactById(int position) {
        return  contentResolver.delete(Uri.parse(uriString), contactList.get(position).getId(), null);
    }

    private void showContactList(List<Contact> contactList) {
        ContactListAdapter adapter = new ContactListAdapter(contactList);
        recyclerViewContactList.setAdapter(adapter);
    }

    private class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ContactListViewHolder> {

        private List<Contact> contactList;

        public ContactListAdapter(List<Contact> contactList) {
            this.contactList = contactList;
        }

        @NonNull
        @Override
        public ContactListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact, parent, false);
            return new ContactListViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ContactListViewHolder holder, int position) {
            holder.bindData(contactList.get(position));
        }

        @Override
        public int getItemCount() {
            return contactList != null ? contactList.size() : 0;
        }


        private class ContactListViewHolder extends RecyclerView.ViewHolder {

            private ImageView viewContactImage;
            private TextView viewContactPhoneText;
            private TextView viewContactDataText;

            public ContactListViewHolder(@NonNull View itemView) {
                super(itemView);
                viewContactPhoneText = itemView.findViewById(R.id.viewContactPhoneText);
                viewContactDataText = itemView.findViewById(R.id.viewContactNameText);
                viewContactImage = itemView.findViewById(R.id.viewContactImage);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (deleteContactById(getAdapterPosition()) == 1) {
                            notifyItemRemoved(getAdapterPosition());
                        }
                    }
                });
            }

            private void bindData (Contact contact) {
                viewContactDataText.setText(contact.getName());
                viewContactPhoneText.setText(contact.getData());

                if (contact.getOption().equals("Email")) {
                    viewContactImage.setImageResource(R.drawable.ic_contact_mail_magenta_24dp);
                } else if (contact.getOption().equals("Phone")) {
                    viewContactImage.setImageResource(R.drawable.ic_contact_phone_blue_24dp);
                }
            }
        }
    }
}
