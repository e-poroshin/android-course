package com.gmail.task02_view;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class PhoneBookMainActivity extends AppCompatActivity {

    private Toolbar myToolbar;
    private FloatingActionButton fab;
    private TextView textView;
    private static RecyclerView recyclerView;
    private Observer.Listener listener;
    private RecyclerAdapter adapter;
    private ArrayList<Contact> contacts;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phonebook_main);

        myToolbar = findViewById(R.id.toolbar_phonebook);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("");

        fab = findViewById(R.id.floatingActionButton);
                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(PhoneBookMainActivity.this, PhoneBookAddContactActivity.class);
                        if (intent != null) {
                            startActivity(intent);
                        }
            }
        });
        textView = findViewById(R.id.textViewPhone);
        recyclerView = findViewById(R.id.recyclerView);


        adapter = new RecyclerAdapter(new ArrayList<Contact>());

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        }
        else{
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        }
        recyclerView.setAdapter(adapter);

        if(savedInstanceState!=null) {
            recyclerView = savedInstanceState.getParcelable("key");
        }

        Observer.getInstance().subscribe(listener);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("key", recyclerView.getLayoutManager().onSaveInstanceState());
    }

    public static void addItemAdapter(Contact contact) {
        RecyclerAdapter recyclerAdapter = (RecyclerAdapter)recyclerView.getAdapter();
        recyclerAdapter.addItem(contact);
    }

    public static void changeItemAdapter(Contact contact) {
        RecyclerAdapter recyclerAdapter = (RecyclerAdapter)recyclerView.getAdapter();
        recyclerAdapter.onContactChange(contact);
    }

    public static void removeItemAdapter(Contact contact) {
        RecyclerAdapter recyclerAdapter = (RecyclerAdapter)recyclerView.getAdapter();
        recyclerAdapter.removeItem(contact);
    }

    public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder> implements Observer.Listener, Filterable {

        private List<Contact> contactListFull;
        Context context;

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

            if (contacts.get(position).isEmailSelected()) {
                holder.imageView.setImageResource(R.drawable.ic_contact_mail_magenta_24dp);
            } else {
                holder.imageView.setImageResource(R.drawable.ic_contact_phone_blue_24dp);
            }
            holder.textViewName.setText(contacts.get(position).getName());
            holder.textViewData.setText(contacts.get(position).getData());
        }

        @Override
        public int getItemCount() {
            if (contacts != null) {
                return contacts.size();
            } else
                return 0;
        }


        @Override
        public Filter getFilter() {
            return new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence charSequence) {

                    List<Contact> filteredList = new ArrayList<>();

                    if (charSequence == null || charSequence.length() == 0) {
                        filteredList.addAll(contactListFull);
                    } else {
                        String filterPattern = charSequence.toString().toLowerCase().trim();
                        for (Contact contact : contactListFull) {
                            if (contact.getName().toLowerCase().contains(filterPattern)) {
                                filteredList.add(contact);
                            }
                        }

                    }
                    FilterResults results = new FilterResults();
                    results.values = filteredList;

                    /////RecyclerAdapter.this.filteredList = filteredList;

                    return results;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                    contacts.clear();
                    contacts.addAll((List)filterResults.values);
                    notifyDataSetChanged();
                }
            };
        }

        @Override
        public void onContactChange(Contact contact) {
            int position = contact.getPosition();
            Contact currentContact = contacts.get(position);
            currentContact.setName(contact.getName());
            currentContact.setData(contact.getData());
            notifyItemChanged(position);
        }

        public void addItem (Contact contact) {
            contacts.add(contact);
            notifyDataSetChanged();
            textView.setVisibility(View.GONE);
        }

        public void removeItem(Contact contact) {
            int position = contact.getPosition();
            contacts.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, contacts.size());
            if (contacts.size() == 0) {
                textView.setVisibility(View.VISIBLE);
            }
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

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = getAdapterPosition();
                        Contact currentContact = contacts.get(position);

                        Intent intent = new Intent(context, PhoneBookEditContactActivity.class);
                        intent.putExtra(Contact.class.getSimpleName(), (Serializable) currentContact);
                        intent.putExtra("position", position);
                        startActivity(intent);
                    }
                });
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_main_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView)searchItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        //SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        //searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        //searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                adapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
