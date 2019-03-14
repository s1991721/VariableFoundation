package com.jef.variablefoundation.image;

import android.graphics.drawable.Drawable;
import android.text.TextUtils;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

/**
 * Created by mr.lin on 2018/11/9
 * Glide加载框架
 */
public class GlideImageLoader implements IImageLoader {
    @Override
    public void apply(ImageLoader.Builder builder) {

        RequestOptions requestOptions = null;
        if (builder.getLoadingResId() > 0 || builder.getErrorResId() > 0) {//占位图
            requestOptions = new RequestOptions()
                    .placeholder(builder.getLoadingResId())
                    .error(builder.getErrorResId());
        }


        RequestManager requestManager = Glide.with(builder.getImageView());

        RequestBuilder<Drawable> requestBuilder;

        if (TextUtils.isEmpty(builder.getUrl())) {//加载网络还是本地
            requestBuilder = requestManager.load(builder.getResId());
        } else {
            requestBuilder = requestManager.load(builder.getUrl());
        }

        if (builder.getRadius() > 0) {//圆角
            requestBuilder = requestBuilder
                    .apply(RequestOptions.bitmapTransform(new RoundedCorners(builder.getRadius())));
        }

        if (builder.isCircle()) {//圆
            requestBuilder = requestBuilder
                    .apply(RequestOptions.bitmapTransform(new CircleCrop()));
        }


        if (requestOptions == null) {//占位图与过渡冲突
            requestBuilder
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(builder.getImageView());
        } else {
            requestBuilder.apply(requestOptions).into(builder.getImageView());
        }


    }

}
