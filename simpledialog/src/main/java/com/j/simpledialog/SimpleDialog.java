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

    private Drawable getBackgroundDrawable() {
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
        private String negativeButtonText;
        private String positiveButtonText;
        private String neutralButtonText;

        private boolean negativeButtonVisible = true;
        private boolean neutralButtonVisible = false;

        private View.OnClickListener onNegativeButtonClick, onPositiveButtonClick, onNeutralButtonClick;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setContent(String content) {
            this.content = content;
            return this;
        }

        public Builder setPositiveButtonText(String positiveButtonText) {
            this.positiveButtonText = positiveButtonText;
            return this;
        }
        public Builder setPositiveButton(View.OnClickListener onPositiveButtonClick) {
            return setPositiveButton("确定", onPositiveButtonClick);
        }
        public Builder setPositiveButton(String positiveButtonText, View.OnClickListener onPositiveButtonClick) {
            this.positiveButtonText = positiveButtonText;
            this.onPositiveButtonClick = onPositiveButtonClick;
            return this;
        }

        public Builder setNegativeButtonText(String negativeButtonText) {
            this.negativeButtonText = negativeButtonText;
            return this;
        }
        public Builder setNegativeButton(View.OnClickListener onNegativeButtonClick) {
            return setNegativeButton("取消", onNegativeButtonClick);
        }
        public Builder setNegativeButton(String negativeButtonText, View.OnClickListener onNegativeButtonClick) {
            this.negativeButtonText = negativeButtonText;
            this.onNegativeButtonClick = onNegativeButtonClick;
            return this;
        }
        public Builder setNegativeButtonVisible(boolean visible) {
            this.negativeButtonVisible = visible;
            return this;
        }

        public Builder setNeutralButtonText(String neutralButtonText) {
            this.neutralButtonText = neutralButtonText;
            return this;
        }
        public Builder setNeutralButton(View.OnClickListener onNeutralButtonClick) {
            return setNeutralButton("稍后", true, onNeutralButtonClick);
        }
        public Builder setNeutralButton(String neutralButtonText, View.OnClickListener onNeutralButtonClick) {
            return setNeutralButton(neutralButtonText, true, onNeutralButtonClick);
        }
        public Builder setNeutralButton(String neutralButtonText, boolean visible, View.OnClickListener onNeutralButtonClick) {
            this.neutralButtonText = neutralButtonText;
            this.neutralButtonVisible = visible;
            this.onNeutralButtonClick = onNeutralButtonClick;
            return this;
        }
        public Builder setNeutralButtonVisible(boolean visible) {
            this.neutralButtonVisible = visible;
            return this;
        }

        public SimpleDialog create() {
            SimpleDialog simpleDialog = new SimpleDialog(context);

            MyView simpleDialogView = new MyView(context, R.layout.dialog_simple);
            if (!TextUtils.isEmpty(title)) simpleDialogView.setText(R.id.tv_title, title);
            simpleDialogView.setText(R.id.tv_content, content);
            simpleDialogView.setVisible(R.id.tv_content, !TextUtils.isEmpty(content));

            if (!TextUtils.isEmpty(positiveButtonText)) simpleDialogView.setText(R.id.tv_positive, positiveButtonText);
            if (!TextUtils.isEmpty(negativeButtonText)) simpleDialogView.setText(R.id.tv_negative, negativeButtonText);
            if (!TextUtils.isEmpty(neutralButtonText)) simpleDialogView.setText(R.id.tv_neutral, neutralButtonText);

            simpleDialogView.setVisible(R.id.tv_negative, negativeButtonVisible);
            simpleDialogView.setVisible(R.id.tv_neutral, neutralButtonVisible);

            simpleDialogView.setOnClickListener(R.id.tv_positive,view -> {
                simpleDialog.dismiss();
                if (this.onPositiveButtonClick != null) this.onPositiveButtonClick.onClick(view);
            });
            simpleDialogView.setOnClickListener(R.id.tv_negative, view -> {
                simpleDialog.dismiss();
                if (this.onNegativeButtonClick != null) this.onNegativeButtonClick.onClick(view);
            });
            simpleDialogView.setOnClickListener(R.id.tv_neutral, view -> {
                simpleDialog.dismiss();
                if (this.onNeutralButtonClick != null) this.onNeutralButtonClick.onClick(view);
            });

            simpleDialog.setView(simpleDialogView.getView());

            return simpleDialog;
        }

        private void setMarginTop(View view, int top) {
            ViewGroup.LayoutParams lp = view.getLayoutParams();
            ViewGroup.MarginLayoutParams mlp;
            if (lp instanceof ViewGroup.MarginLayoutParams) {
                mlp = (ViewGroup.MarginLayoutParams) lp;
            } else {
                mlp = new ViewGroup.MarginLayoutParams(lp);
            }
            mlp.setMargins(0, top, 0, 0);
            view.setLayoutParams(mlp);
        }

        public SimpleDialog show() {
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
