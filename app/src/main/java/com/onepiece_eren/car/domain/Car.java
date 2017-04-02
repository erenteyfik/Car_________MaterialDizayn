package com.onepiece_eren.car.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Set;

/**
 * Created by Teyfik on 23.3.2017.
 */
public class Car implements Parcelable{
    private String model;
    private String brand;
    private String description;
    private String tel;
    private int category;
    private int foto;


    public Car() {}

    public Car(String model, String brand, int foto) {
        this.model = model;
        this.brand = brand;
        this.foto = foto;
        /*this.description=description;
        this.tel=tel;
        this.category=category;
        */
    }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public String getTel() { return tel; }

    public void setTel(String tel) { this.tel = tel; }

    public int getCategory() { return category; }

    public void setCategory(int category) { this.category = category; }
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getFoto() {
        return foto;
    }

    public void setFoto(int foto) { this.foto = foto; }

    //PARCALABLE
    public Car(Parcel parcel){
        setModel(parcel.readString());
        setBrand(parcel.readString());
        setDescription(parcel.readString());
        setCategory(parcel.readInt());
        setTel(parcel.readString());
        setFoto(parcel.readInt());
    }

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeString( getModel() );
        dest.writeString( getBrand() );
        dest.writeString( getDescription() );
        dest.writeInt( getCategory() );
        dest.writeString( getTel() );
        dest.writeInt( getFoto() );
    }
    public static final Parcelable.Creator<Car> CREATOR = new Parcelable.Creator<Car>() {
        @Override
        public Car createFromParcel(Parcel parcel) { return new Car(parcel); }
        @Override
        public Car[] newArray(int size) { return new Car[size]; }
    };

}
