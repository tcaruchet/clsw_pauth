package edu.unice.polytech.ihm.si5.pauth.data;


import android.os.Parcel;
import android.os.Parcelable;

public class WearAuthenticator implements Parcelable {
    public int id;
    public final int icon;
    public final String issuer;
    public final int period;
    public final int digits;
    public final int ranking;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(icon);
        parcel.writeString(issuer);
        parcel.writeInt(period);
        parcel.writeInt(digits);
        parcel.writeInt(ranking);
    }

    // this is used to regenerate your object. All Parcelables must have a CREATOR that implements these two methods
    public static final Parcelable.Creator<WearAuthenticator> CREATOR = new Parcelable.Creator<WearAuthenticator>() {
        public WearAuthenticator createFromParcel(Parcel in) {
            return new WearAuthenticator(in);
        }

        public WearAuthenticator[] newArray(int size) {
            return new WearAuthenticator[size];
        }
    };

    // example constructor that takes a Parcel and gives you an object populated with it's values
    private WearAuthenticator(Parcel in) {
        id = in.readInt();
        icon = in.readInt();
        issuer = in.readString();
        period = in.readInt();
        digits = in.readInt();
        ranking = in.readInt();
    }

    public WearAuthenticator(int id, int icon, String issuer, int period, int digits, int ranking) {
        this.id = id;
        this.icon = icon;
        this.issuer = issuer;
        this.period = period;
        this.digits = digits;
        this.ranking = ranking;
    }

    public static class WearAuthenticatorDAO {
        public int icon;
        public String issuer;
        public int period;
        public int digits;
        public int ranking;

        public WearAuthenticatorDAO(int icon, String issuer, int period, int digits, int ranking) {
            this.icon = icon;
            this.issuer = issuer;
            this.period = period;
            this.digits = digits;
            this.ranking = ranking;
        }
    }
}


