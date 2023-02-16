package com.dam.uf5_weatherapp_api.data;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Location implements Parcelable {
    private double lat;
    private double lon;

    public Location(Double latitud, Double longitud) {
        this.lat = latitud;
        this.lon = longitud;
    }

    protected Location(Parcel in) {
        lat = in.readDouble();
        lon = in.readDouble();
    }

    public static final Creator<Location> CREATOR = new Creator<Location>() {
        @Override
        public Location createFromParcel(Parcel in) {
            return new Location(in);
        }

        @Override
        public Location[] newArray(int size) {
            return new Location[size];
        }
    };


    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeDouble(lat);
        dest.writeDouble(lon);
    }
}
