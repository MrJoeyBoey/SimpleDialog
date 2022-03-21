package com.j.simpledialog;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;
import androidx.appcompat.app.AlertDialog;

public class SimpleEditDialog extends AlertDialog {

    private final Window window;

    protected SimpleEditDialog(@NonNull Context context) {
        this(context, 0);
    }

    protected SimpleEditDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        window = getWindow();
        if (window != null){
            window.setWindowAnimations(R.style.animation_dialog_simple);
            window.setBackgroundDrawable(getBackgroundDrawable());
        }
    }

    private Drawable getBackgroundDrawable(){
        GradientDrawable g = new GradientDrawable();
        g.setColor(Color.WHITE);
        g.setShape(GradientDrawable.RECTANGLE);
        g.setCornerRadius(dp2px(10));
        return g;
    }

    public int dp2px(float dpValue) {
        return (int) (0.5f + dpValue * Resources.getSystem().getDisplayMetrics().density);
    }

    public static class Builder {
        private final Context context;
        private SimpleEditDialog simpleEditDialog;
        private final MyView simpleDialogView;

        private String title;
        private String contentHint;
        private int inputType;
        private String content;
        private String cancelText;
        private String confirmText;

        private float titleTextSize;

        private View.OnClickListener onNegativeButtonClick,onPositiveButtonClick;

        public Builder(Context context){
            this.context = context;
            simpleDialogView = new MyView(context, R.layout.dialog_simple_edit);
        }

        public Builder setTitle(String title){
            this.title = title;
            return this;
        }

        public Builder setContentHint(String contentHint){
            this.contentHint = contentHint;
            return this;
        }

        public Builder setInputType(int inputType){
            this.inputType = inputType;
            return this;
        }

        public Builder setContent(String content){
            this.content = content;
            return this;
        }

        public Builder setCancelText(String cancelText){
            this.cancelText = cancelText;
            return this;
        }

        public Builder setConfirmText(String confirmText){
            this.confirmText = confirmText;
            return this;
        }

        public Builder setTitleTextSize(float titleTextSize) {
            this.titleTextSize = titleTextSize;
            return this;
        }

        public Builder setNegativeButton(View.OnClickListener onNegativeButtonClick){
            return setNegativeButton("取消", onNegativeButtonClick);
        }
        public Builder setNegativeButton(String cancelText, View.OnClickListener onNegativeButtonClick){
            this.cancelText = cancelText;
            this.onNegativeButtonClick = onNegativeButtonClick;
            return this;
        }

        public Builder setPositiveButton(View.OnClickListener onPositiveButtonClick){
            return setPositiveButton("确定", onPositiveButtonClick);
        }
        public Builder setPositiveButton(String confirmText, View.OnClickListener onPositiveButtonClick){
            this.confirmText = confirmText;
            this.onPositiveButtonClick = onPositiveButtonClick;
            return this;
        }

        public String getContent(){
            return ((EditText) simpleDialogView.getView(R.id.et_content)).getText().toString();
        }

        public String getContentHint(){
            return ((EditText) simpleDialogView.getView(R.id.et_content)).getHint().toString();
        }

        public EditText getEditContentView(){
            return simpleDialogView.getView(R.id.et_content);
        }

        public SimpleEditDialog create(){
            return create(0);
        }

        public SimpleEditDialog create(@StyleRes int themeResId){
            if (simpleEditDialog == null) simpleEditDialog = new SimpleEditDialog(context, themeResId);

            if (!TextUtils.isEmpty(title)) simpleDialogView.setText(R.id.tv_title, title);
            EditText etContent = simpleDialogView.getView(R.id.et_content);
            etContent.setText(content);
            etContent.setHint(contentHint);
            if (inputType != 0) etContent.setInputType(inputType);

            if (!TextUtils.isEmpty(cancelText)) simpleDialogView.setText(R.id.tv_cancel,cancelText);
            if (!TextUtils.isEmpty(confirmText)) simpleDialogView.setText(R.id.tv_confirm,confirmText);

            if (titleTextSize != 0) simpleDialogView.setTextSize(R.id.tv_title, titleTextSize);

            simpleDialogView.setOnClickListener(R.id.tv_cancel, view -> {
                simpleEditDialog.dismiss();
                if (this.onNegativeButtonClick != null) this.onNegativeButtonClick.onClick(view);
            });
            simpleDialogView.setOnClickListener(R.id.tv_confirm, view -> {
                simpleEditDialog.dismiss();
                if (this.onPositiveButtonClick != null) this.onPositiveButtonClick.onClick(view);
            });

            simpleEditDialog.setView(simpleDialogView.getView());

            return simpleEditDialog;
        }

        public SimpleEditDialog show(){
            if (simpleEditDialog == null) simpleEditDialog = this.create();
            simpleEditDialog.show();
            return simpleEditDialog;
        }

    }

    @Override
    public void show() {
        super.show();
        if(window != null){
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.width = 720;
            window.setAttributes(lp);
        }
    }
}
