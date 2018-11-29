package com.example.alberto.popularmovies;

import android.graphics.Bitmap;

import com.squareup.picasso.Cache;

public class CacheClass implements Cache {
    @Override
    public Bitmap get(String key) {
        return null;
    }

    @Override
    public void set(String key, Bitmap bitmap) {

    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public int maxSize() {
        return 0;
    }

    @Override
    public void clear() {

    }

    @Override
    public void clearKeyUri(String keyPrefix) {

    }
}
