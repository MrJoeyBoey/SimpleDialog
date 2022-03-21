package com.j.simpledialog;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.LayoutRes;

public class MyView {

    private final View view;
    private final SparseArray<View> mViews = new SparseArray<>();

    public MyView(Context c, @LayoutRes int res) {
        this.view = LayoutInflater.from(c).inflate(res,null,false);
    }

    public View getView(){
        return this.view;
    }

    @SuppressWarnings("unchecked")
    public <T extends View> T getView(int id) {
        T t = (T) mViews.get(id);
        if (t == null) {
            t = (T) view.findViewById(id);
            mViews.put(id, t);
        }
        return t;
    }

    public void setOnClickListener(int id, View.OnClickListener onClickListener){
        getView(id).setOnClickListener(onClickListener);
    }

    public void setText(int id, String text){
        View view = getView(id);
        if (view instanceof TextView) {
            ((TextView) view).setText(text);
        }
    }

    public CharSequence getText(int id){
        View view = getView(id);
        if (view instanceof TextView) {
            return ((TextView) view).getText();
        }
        return null;
    }

    public void setTextSize(int id, float textSize){
        View view = getView(id);
        if (view instanceof TextView) {
            ((TextView) view).setTextSize(textSize);
        }
    }

    public void setVisible(int id, boolean visible){
        View view = getView(id);
        if (visible) view.setVisibility(View.VISIBLE);
        else view.setVisibility(View.GONE);
    }

}
