package edu.unice.polytech.ihm.si5.pauth.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import edu.unice.polytech.ihm.si5.pauth.R;
import edu.unice.polytech.ihm.si5.pauth.data.WearAuthenticator;
import edu.unice.polytech.ihm.si5.pauth.data.WearAuthenticatorRepository;

public class FragmentAddAuth extends Fragment
        implements View.OnClickListener{

    private EditText mLocationEditText = null;
    private Context _context = null;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate( R.layout.fragment_addauth, container, false );
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button button = view.findViewById(R.id.addButton);
        button.setOnClickListener(this);

        mLocationEditText = view.findViewById(R.id.locationEditText);
        _context = view.getContext();
    }


    @Override
    public void onClick(View view) {
        String inputText = mLocationEditText.getText().toString().trim();
        Log.i("MainActivity",
                "Button clicked: \"" + inputText + "\"");

        if (inputText.length() == 0) {
            Toast.makeText(_context, "Vous n'avez rien écrit !", Toast.LENGTH_LONG).show();

        } else {
            if (inputText.length() < 10) {
                WearAuthenticator.WearAuthenticatorDAO wearAuthenticatorDAO = new WearAuthenticator.WearAuthenticatorDAO(R.drawable.ic_action_star, inputText, 30, 6, 1);
                WearAuthenticatorRepository.addAuthenticator(wearAuthenticatorDAO);
                Toast.makeText(_context, "Authentifieur ajouté !", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(_context, "Le nom est trop long.", Toast.LENGTH_LONG).show();
                return;
            }
        }

        mLocationEditText.setText("");
    }
}
