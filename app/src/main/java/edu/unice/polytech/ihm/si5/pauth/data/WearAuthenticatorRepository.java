package edu.unice.polytech.ihm.si5.pauth.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import edu.unice.polytech.ihm.si5.pauth.R;

public class WearAuthenticatorRepository {
    public static ArrayList<WearAuthenticator> authenticators;

    //static to non-use sqlite database
    static {
        authenticators = new ArrayList<>();
        authenticators.add(new WearAuthenticator(0, R.drawable.ic_action_star, "NICEMONTEL", 30, 6, 1));
        authenticators.add(new WearAuthenticator(1, R.drawable.ic_action_star, "CHAMPDEMARS", 30, 6, 1));
        authenticators.add(new WearAuthenticator(2, R.drawable.ic_action_star, "SOPHIA", 30, 6, 1));
        authenticators.add(new WearAuthenticator(3, R.drawable.ic_action_star, "ISSY1", 30, 6, 1));
        authenticators.add(new WearAuthenticator(4, R.drawable.ic_action_star, "ISSY2", 30, 6, 1));
        authenticators.add(new WearAuthenticator(5, R.drawable.ic_action_star, "NICEVILLE", 30, 6, 1));

        authenticators.sort(Comparator.comparing(o -> o.issuer));
    }

    public static void addAuthenticator(WearAuthenticator.WearAuthenticatorDAO authenticatorDAO) {
        WearAuthenticator authenticator = new WearAuthenticator(getNextId(), authenticatorDAO.icon, authenticatorDAO.issuer, authenticatorDAO.period, authenticatorDAO.digits, authenticatorDAO.ranking);
        authenticators.add(authenticator);
        authenticators.sort(Comparator.comparing(o -> o.issuer));
    }

    private static int getNextId() {
        int id = 0;
        for(WearAuthenticator authenticator : authenticators)
            if(authenticator.id >= id)
                id = authenticator.id + 1;
        return id;
    }
}
