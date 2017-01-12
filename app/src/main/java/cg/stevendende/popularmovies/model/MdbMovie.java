/*
 * Copyright (C) 2017 Steve NDENDE, www.github.com/steve111MV
 */

package cg.stevendende.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Represents the Model of one Movie from THEMOVIEDB API
 */
public class MdbMovie implements Parcelable{

    private String titre;
    private String imageUrl;
    private String imagePath = "";
    private String posterUrl;
    private String posterPath = "";
    private boolean adult;
    private String decription;
    private String originalTitle;
    private String originalLanguage;
    private String releaseDate;
    private String genres;
    private float rating;
    private boolean videoEnabled;
    private long id;


    public MdbMovie() {
    }

    protected MdbMovie(Parcel in) {
        titre = in.readString();
        imageUrl = in.readString();
        imagePath = in.readString();
        posterUrl = in.readString();
        posterPath = in.readString();
        adult = in.readByte() != 0;
        decription = in.readString();
        originalTitle = in.readString();
        originalLanguage = in.readString();
        releaseDate = in.readString();
        genres = in.readString();
        rating = in.readFloat();
        videoEnabled = in.readByte() != 0;
        id = in.readLong();
    }

    public static final Creator<MdbMovie> CREATOR = new Creator<MdbMovie>() {
        @Override
        public MdbMovie createFromParcel(Parcel in) {
            return new MdbMovie(in);
        }

        @Override
        public MdbMovie[] newArray(int size) {
            return new MdbMovie[size];
        }
    };

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getDecription() {
        return decription;
    }

    public void setDecription(String decription) {
        this.decription = decription;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public boolean isVideoEnabled() {
        return videoEnabled;
    }

    public void setVideoEnabled(boolean videoEnabled) {
        this.videoEnabled = videoEnabled;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    /**
     * Describe the kinds of special objects contained in this Parcelable
     * instance's marshaled representation. For example, if the object will
     * include a file descriptor in the output of {@link #writeToParcel(Parcel, int)},
     * the return value of this method must include the
     * {@link #CONTENTS_FILE_DESCRIPTOR} bit.
     *
     * @return a bitmask indicating the set of special object types marshaled
     * by this Parcelable object instance.
     * @see #CONTENTS_FILE_DESCRIPTOR
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param dest  The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(titre);
        dest.writeString(imageUrl);
        dest.writeString(imagePath);
        dest.writeString(posterUrl);
        dest.writeString(posterPath);
        dest.writeByte((byte) (adult ? 1 : 0));
        dest.writeString(decription);
        dest.writeString(originalTitle);
        dest.writeString(originalLanguage);
        dest.writeString(releaseDate);
        dest.writeString(genres);
        dest.writeFloat(rating);
        dest.writeByte((byte) (videoEnabled ? 1 : 0));
        dest.writeLong(id);
    }
}

