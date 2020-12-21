package cat.itb.m08_uf1_p6_retrofit.models;

import com.google.gson.annotations.SerializedName;

public class People {
    private String name;
    private String height;
    @SerializedName("eye_color")
    private String eyeColor;
    @SerializedName("birth_year")
    private String birthYear;
    private String[] films;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getEyeColor() {
        return eyeColor;
    }

    public void setEyeColor(String eyeColor) {
        this.eyeColor = eyeColor;
    }

    public String getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(String birthYear) {
        this.birthYear = birthYear;
    }

    public String[] getFilms() {
        return films;
    }

    public void setFilms(String[] films) {
        this.films = films;
    }
}
