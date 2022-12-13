package edu.unice.polytech.ihm.si5.pauth.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.wear.widget.WearableLinearLayoutManager;
import androidx.wear.widget.WearableRecyclerView;

import java.util.ArrayList;

import edu.unice.polytech.ihm.si5.pauth.AuthActivity;
import edu.unice.polytech.ihm.si5.pauth.adapters.MainMenuAdapter;
import edu.unice.polytech.ihm.si5.pauth.MenuItem;
import edu.unice.polytech.ihm.si5.pauth.R;
import edu.unice.polytech.ihm.si5.pauth.data.WearAuthenticator;
import edu.unice.polytech.ihm.si5.pauth.data.WearAuthenticatorRepository;

public class FragmentList extends Fragment {
    private static final String TAG = "FragmentList";
    protected Context _context = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate( R.layout.fragment_list, container, false );
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        _context = view.getContext();

        // Get the list of authenticators and display them
        WearableRecyclerView recyclerView = view.findViewById(R.id.listAuth);
        recyclerView.setHasFixedSize(true);
        recyclerView.setEdgeItemsCenteringEnabled(true);
        recyclerView.setLayoutManager(new WearableLinearLayoutManager(_context));

        ArrayList<MenuItem> menuItems = new ArrayList<>();
        for(WearAuthenticator authenticator : WearAuthenticatorRepository.authenticators) {
            menuItems.add(new MenuItem(authenticator.icon, authenticator.issuer));
        }

        recyclerView.setAdapter(new MainMenuAdapter(_context, menuItems, new MainMenuAdapter.AdapterCallback() {
            @Override
            public void onItemClicked(final Integer menuPosition) {
                if(menuPosition != null) {
                    try{
                        WearAuthenticator authenticator = WearAuthenticatorRepository.authenticators.get(menuPosition);
                        if(authenticator != null) {
                            Intent intent = new Intent(_context, AuthActivity.class);
                            intent.putExtra("authenticator", authenticator);
                            startActivity(intent);
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "onItemClicked: ", e);
                    }
                } else {
                    Log.e(TAG, "null clicked");
                }
            }
        }));
    }
}
