package edu.unice.polytech.ihm.si5.pauth;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.util.Log;

import androidx.wear.widget.drawer.WearableDrawerController;
import androidx.wear.widget.drawer.WearableNavigationDrawerView;

import edu.unice.polytech.ihm.si5.pauth.databinding.ActivityMainBinding;
import edu.unice.polytech.ihm.si5.pauth.fragments.FragmentAddAuth;
import edu.unice.polytech.ihm.si5.pauth.fragments.FragmentList;

public class MainActivity extends WearableActivity implements WearableNavigationDrawerView.OnItemSelectedListener{
    private static final String TAG = "MainActivity";

    private ActivityMainBinding binding;

    protected FragmentManager _fragmentManager = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _fragmentManager = getFragmentManager();

        Fragment fragmentList = new FragmentList();

        FragmentTransaction ft = _fragmentManager.beginTransaction();
        ft.replace(R.id.place_holder_content, fragmentList);
        ft.commit();


        WearableNavigationDrawerView drawerView = findViewById(R.id.navigation_drawer_open);
        MeinNavigationAdapter navigationAdapter = new MeinNavigationAdapter();
        drawerView.setAdapter(navigationAdapter);
        drawerView.addOnItemSelectedListener(this);

        WearableDrawerController drawerController = drawerView.getController();
        drawerController.peekDrawer();

        setAmbientEnabled(); // Enables Always-on
    }

    @Override
    public void onItemSelected(int position) {
        Fragment fragmentNeu = null;
        switch (position) {
            case 0:
                fragmentNeu = new FragmentList();
                break;

            case 1:
                fragmentNeu = new FragmentAddAuth();
                break;

            default:
                Log.e(TAG, "PAS TROUVE LE FRAGMENT A AFFICHER=" + position);
        }

        FragmentTransaction ft = _fragmentManager.beginTransaction();
        ft.replace(R.id.place_holder_content, fragmentNeu);
        ft.commit();
    }


    private final class MeinNavigationAdapter
            extends WearableNavigationDrawerView.WearableNavigationDrawerAdapter {

        @Override
        public String getItemText(int position) {
            switch (position) {
                case 0: return "Liste des Lieux";
                case 1: return "Ajouter un lieu";
                default:
                    Log.w(TAG, "J'ai pas trouvé: " + position);
                    return "???";
            }
        }

        @Override
        public Drawable getItemDrawable(int position) {
            Log.i(TAG, "Icon for position: " + position);
            int drawableID = android.R.drawable.ic_dialog_alert;
            switch (position) {
                case 0: // FragmentList
                    drawableID = R.drawable.ic_format_list_bulleted_black_24dp;
                    break;
                case 1: // FragmentAddAuth
                    drawableID = R.drawable.ic_playlist_add_black_24dp;
                    break;
                default:
                    Log.w(TAG, "Unable to have icon: " + position);
            }
            return getDrawable(drawableID);
        }

        @Override
        public int getCount() {
            return 2;
        }

    }
}