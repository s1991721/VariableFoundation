package com.jef.variablefoundation.image;

import android.widget.ImageView;

/**
 * Created by mr.lin on 2018/11/9
 * 对外应用层图片加载
 */
public class ImageLoader {

    public static Builder load(ImageView imageView, String url) {
        Builder builder = new Builder();
        builder.setImageView(imageView).setUrl(url);
        return builder;
    }

    public static Builder load(ImageView imageView, int resId) {
        Builder builder = new Builder();
        builder.setImageView(imageView).setResId(resId);
        return builder;
    }

    public static class Builder {
        private ImageView imageView;//target
        private String url;
        private int resId;
        private int errorResId;//占位符
        private int loadingResId;
        private int radius;//圆角
        private boolean isCircle;//圆

        public Builder setImageView(ImageView imageView) {
            this.imageView = imageView;
            return this;
        }

        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder setResId(int resId) {
            this.resId = resId;
            return this;
        }

        public Builder setPlaceHolder(int errorResId, int loadingResId) {
            this.errorResId = errorResId;
            this.loadingResId = loadingResId;
            return this;
        }

        public Builder setErrorResId(int errorResId) {
            this.errorResId = errorResId;
            return this;
        }

        public Builder setLoadingResId(int loadingResId) {
            this.loadingResId = loadingResId;
            return this;
        }

        public Builder setRadius(int radius) {
            this.radius = radius;
            return this;
        }

        public Builder setCircle(boolean circle) {
            isCircle = circle;
            return this;
        }

        public void apply() {
            IImageLoader imageLoader = new GlideImageLoader();
            imageLoader.apply(this);
        }

        public ImageView getImageView() {
            return imageView;
        }

        public String getUrl() {
            return url;
        }

        public int getResId() {
            return resId;
        }

        public int getErrorResId() {
            return errorResId;
        }

        public int getLoadingResId() {
            return loadingResId;
        }

        public int getRadius() {
            return radius;
        }

        public boolean isCircle() {
            return isCircle;
        }
    }
}
