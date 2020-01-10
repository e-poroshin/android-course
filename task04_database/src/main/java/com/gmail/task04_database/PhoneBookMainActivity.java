package com.gmail.task04_database;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public class PhoneBookMainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private SearchView searchView;
    private FloatingActionButton fabAddContact;
    private TextView textView;
    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;
    private List<Contact> contacts;
    private ContactViewModel mContactViewModel;
    public static final int REQUEST_CODE_ADD_CONTACT = 1;
    public static final int REQUEST_CODE_EDIT_CONTACT = 2;
    public static final int REQUEST_CODE_DELET_CONTACT = 3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phonebook_main);

        toolbar = findViewById(R.id.toolbar_phonebook);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        searchView = findViewById(R.id.sv_search);
        searchView.setActivated(true);
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

        fabAddContact = findViewById(R.id.floatingActionButton);
        fabAddContact.setOnClickListener(view -> {
            Intent intent = new Intent(this, PhoneBookAddContactActivity.class);
            startActivityForResult(intent, REQUEST_CODE_ADD_CONTACT);
        });
        textView = findViewById(R.id.textViewPhone);
        recyclerView = findViewById(R.id.recyclerView);

        adapter = new RecyclerAdapter(new ArrayList<Contact>());

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        }
        recyclerView.setAdapter(adapter);

        if (savedInstanceState != null) {
            contacts = (ArrayList<Contact>) savedInstanceState.getSerializable("key");
        }

        mContactViewModel = ViewModelProviders.of(this).get(ContactViewModel.class);
        mContactViewModel.getAllContacts().observe(this, contacts -> adapter.setContacts(contacts));
    }

    @Override
    protected void onResume() {        /////////////////////////////////не работает
        super.onResume();
        if (contacts.size() != 0) {
            textView.setVisibility(View.GONE);
        } else {
            textView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD_CONTACT && resultCode == RESULT_OK) {
            Contact contact = (Contact) data.getSerializableExtra(PhoneBookAddContactActivity.EXTRA_REPLY);
            mContactViewModel.insert(contact);
        } else if (requestCode == REQUEST_CODE_EDIT_CONTACT && resultCode == RESULT_OK) {
            Contact contact = (Contact) data.getSerializableExtra(PhoneBookEditContactActivity.EXTRA_REPLY_EDIT);
            mContactViewModel.update(contact);
        } else if (requestCode == REQUEST_CODE_DELET_CONTACT && resultCode == RESULT_OK) {
            Contact contact = (Contact)data.getSerializableExtra(PhoneBookEditContactActivity.EXTRA_REPLY_DELETE);
            mContactViewModel.delete(contact);
        } else {
            Toast.makeText(getApplicationContext(),
                    "Something wrong",
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("key", (Serializable) contacts);
    }

    public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> implements Filterable {

        private List<Contact> contactListFull;

        public RecyclerAdapter(ArrayList<Contact> contacts1) {
            contacts = contacts1;
            contactListFull = new ArrayList<>(contacts1);
        }

        @NonNull
        @Override
        public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_phonebook, parent, false);
            return new RecyclerViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
            if (contactListFull.get(position).getOption() == ContactSelectedOption.EMAIL) {
                holder.imageView.setImageResource(R.drawable.ic_contact_mail_magenta_24dp);
            } else if (contactListFull.get(position).getOption() == ContactSelectedOption.PHONE) {
                holder.imageView.setImageResource(R.drawable.ic_contact_phone_blue_24dp);
            }
            holder.textViewName.setText(contactListFull.get(position).getName());
            holder.textViewData.setText(contactListFull.get(position).getData());
        }


        void setContacts(List<Contact> contacts1) {
            contacts = contacts1;
            contactListFull = contacts1;
            notifyDataSetChanged();
        }


        @Override
        public int getItemCount() {
            if (contactListFull != null) {
                return contactListFull.size();
            } else
                return 0;
        }

        @Override
        public Filter getFilter() {
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence charSequence) {
                    String filterPattern = charSequence.toString().toLowerCase().trim();
                    List<Contact> filteredList = new ArrayList<>();

                    if (charSequence == null || charSequence.length() == 0) {
                        filteredList.addAll(contacts);
                    } else {
                        for (Contact cont : contacts) {
                            if (cont.getName().toLowerCase().contains(filterPattern)) {
                                filteredList.add(cont);
                                Log.d("tag", cont.getName());
                            }
                        }
                    }
                    FilterResults results = new FilterResults();
                    results.values = filteredList;

                    return results;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                    contactListFull.clear();
                    contactListFull.addAll((ArrayList<Contact>) filterResults.values);
                    notifyDataSetChanged();
                    String fl = contactListFull.toString();
                    Log.d("tag", fl);
                }
            };
        }

        private class RecyclerViewHolder extends RecyclerView.ViewHolder {

            private ImageView imageView;
            private TextView textViewName;
            private TextView textViewData;

            public RecyclerViewHolder(@NonNull View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.item_imageView);
                textViewName = itemView.findViewById(R.id.item_name_textView);
                textViewData = itemView.findViewById(R.id.item_phoneOrMail_textView);

                itemView.setOnClickListener(v -> {
                    int position = getAdapterPosition();
                    Contact currentContact = contactListFull.get(position);

                    Intent intent = new Intent(PhoneBookMainActivity.this, PhoneBookEditContactActivity.class);
                    intent.putExtra(Contact.class.getSimpleName(), (Serializable) currentContact);

                    startActivityForResult(intent, REQUEST_CODE_EDIT_CONTACT);
                });
            }
        }
    }

}