package com.aliouswang.view.library;

import android.view.View;

/**
 * Created by aliouswang on 16/1/6.
 */
public interface SquareViewAdapter<T> {

    int getCount();

    T getItem(int position);

    String getImageUrl(int position);

    void onItemClick(View view, int index, T t);
}
