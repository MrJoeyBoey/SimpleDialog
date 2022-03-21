package com.j.simpledialog;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

public class SimpleDialog extends AlertDialog {

    private final Window window;

    protected SimpleDialog(@NonNull Context context) {
        super(context);
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

    public static int dp2px(float dpValue) {
        return (int) (0.5f + dpValue * Resources.getSystem().getDisplayMetrics().density);
    }

    public static class Builder {

        private final Context context;

        private String title;
        private String content;
        private String cancelText;
        private String confirmText;

        private boolean negativeButtonVisible = true;

        private View.OnClickListener onNegativeButtonClick,onPositiveButtonClick;

        public Builder(Context context){
            this.context = context;
        }

        public Builder setTitle(String title){
            this.title = title;
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

        public Builder setNegativeButton(View.OnClickListener onNegativeButtonClick){
            setNegativeButton("取消",onNegativeButtonClick);
            return this;
        }
        public Builder setNegativeButton(String cancelText, View.OnClickListener onNegativeButtonClick){
            this.cancelText = cancelText;
            this.onNegativeButtonClick = onNegativeButtonClick;
            return this;
        }

        public Builder setNegativeButtonVisible(boolean visible){
            this.negativeButtonVisible = visible;
            return this;
        }

        public Builder setPositiveButton(View.OnClickListener onPositiveButtonClick){
            setPositiveButton("确定", onPositiveButtonClick);
            return this;
        }
        public Builder setPositiveButton(String confirmText, View.OnClickListener onPositiveButtonClick){
            this.confirmText = confirmText;
            this.onPositiveButtonClick = onPositiveButtonClick;
            return this;
        }

        public SimpleDialog create(){
            SimpleDialog simpleDialog = new SimpleDialog(context);

            MyView simpleDialogView = new MyView(context, R.layout.dialog_simple);
            if (!TextUtils.isEmpty(title)) simpleDialogView.setText(R.id.tv_title, title);
            if (TextUtils.isEmpty(content)) {
                simpleDialogView.setVisible(R.id.tv_content, false);
                setMargin(simpleDialogView.getView(R.id.tv_title), 0, dp2px(20), 0, 0);
                setMargin(simpleDialogView.getView(R.id.ll_btn), 0, dp2px(20), 0, 0);
            } else {
                simpleDialogView.setText(R.id.tv_content, content);
            }
            if (!TextUtils.isEmpty(cancelText)) simpleDialogView.setText(R.id.tv_cancel, cancelText);
            if (!TextUtils.isEmpty(confirmText)) simpleDialogView.setText(R.id.tv_confirm, confirmText);
            simpleDialogView.setVisible(R.id.tv_cancel, negativeButtonVisible);
            simpleDialogView.setOnClickListener(R.id.tv_cancel, view -> {
                simpleDialog.dismiss();
                if (this.onNegativeButtonClick != null) this.onNegativeButtonClick.onClick(view);
            });
            simpleDialogView.setOnClickListener(R.id.tv_confirm,view -> {
                simpleDialog.dismiss();
                if (this.onPositiveButtonClick != null) this.onPositiveButtonClick.onClick(view);
            });

            simpleDialog.setView(simpleDialogView.getView());

            return simpleDialog;
        }

        private void setMargin(View view, int left, int top, int right, int bottom){
            ViewGroup.LayoutParams lp = view.getLayoutParams();
            ViewGroup.MarginLayoutParams mlp;
            if (lp instanceof ViewGroup.MarginLayoutParams) {
                mlp = (ViewGroup.MarginLayoutParams) lp;
            } else {
                mlp = new ViewGroup.MarginLayoutParams(lp);
            }
            mlp.setMargins(left, top, right, bottom);
            view.setLayoutParams(mlp);
        }

        public SimpleDialog show(){
            SimpleDialog simpleDialog = this.create();
            simpleDialog.show();
            return simpleDialog;
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
