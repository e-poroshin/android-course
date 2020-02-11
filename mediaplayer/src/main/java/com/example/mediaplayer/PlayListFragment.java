package com.example.mediaplayer;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class PlayListFragment extends Fragment {

    public static final String FRAGMENT_TAG = PlayListFragment.class.getName();

    public static PlayListFragment newInstance() {
        return new PlayListFragment();
    }

    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;
    private PlaylistAdapter adapter;
    private List<AudioFile> files;


    public interface OnSelectedTrackListener {
        void onSelectTrack(List<AudioFile> files, int currentPosition);
    }

    private OnSelectedTrackListener onSelectedTrackListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            onSelectedTrackListener = (OnSelectedTrackListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnSelectedTrackListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_playlist, container, false);

        floatingActionButton = view.findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Add track to playlist", Toast.LENGTH_SHORT).show();
            }
        });

        files = new ArrayList<AudioFile>();
        files.add(new AudioFile ("Andrew Applepie - Fantasy Prison (Official Music)", R.drawable.ic_audiotrack_blue_24dp, R.raw.andrewapplepie));
        files.add(new AudioFile ("Two Feet - Quick Musical Doodles", R.drawable.ic_audiotrack_blue_24dp, R.raw.twofeet));
        files.add(new AudioFile ("Rompasso - Angetenar (Original Mix)", R.drawable.ic_audiotrack_blue_24dp, R.raw.rompasso));

        recyclerView = view.findViewById(R.id.recycler_view);
        adapter = new PlaylistAdapter(files);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(adapter);

        return view;
    }

    private class PlaylistAdapter extends RecyclerView.Adapter<PlaylistAdapter.PlaylistViewHolder> {

        public PlaylistAdapter(List<AudioFile> audioFiles) {
            files = audioFiles;
        }

        @NonNull
        @Override
        public PlaylistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.playlist_item, parent, false);
            return new PlaylistViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull PlaylistViewHolder holder, int position) {
            holder.imageView.setImageResource(files.get(position).getIcon());
            holder.textView.setText(files.get(position).getTitle());
        }

        @Override
        public int getItemCount() {
            if (files != null) {
                return files.size();
            } else
                return 0;
        }

        private class PlaylistViewHolder extends RecyclerView.ViewHolder {

            private ImageView imageView;
            private TextView textView;

            public PlaylistViewHolder(@NonNull View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.itemImageView);
                textView = itemView.findViewById(R.id.itemTextView);
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = getAdapterPosition();
                        onSelectedTrackListener.onSelectTrack(files, position);
                    }
                });
            }
        }
    }
}
