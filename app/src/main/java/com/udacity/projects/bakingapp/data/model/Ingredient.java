package com.udacity.projects.bakingapp.data.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;

import com.udacity.projects.bakingapp.BR;

public class Ingredient extends BaseObservable implements Parcelable {

    private Double quantity;
    private String ingredient;
    private String measure;

    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };

    public Ingredient() {
    }

    public Ingredient(Double quantity, String measure, String ingredient) {
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }

    protected Ingredient(Parcel in) {
        if (in.readByte() == 0) {
            quantity = null;
        } else {
            quantity = in.readDouble();
        }
        measure = in.readString();
        ingredient = in.readString();
    }

    public Double getQuantity() {
        return quantity;
    }

    @Bindable
    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
        notifyPropertyChanged(BR.ingredient);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (quantity == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(quantity);
        }
        dest.writeString(measure);
        dest.writeString(ingredient);
    }
}
