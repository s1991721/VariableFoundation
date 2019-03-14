package com.jef.variablefoundation.image;

/**
 * Created by mr.lin on 2018/11/9
 * 通用图片加载接口
 */
public interface IImageLoader {

    void apply(ImageLoader.Builder builder);

    // TODO: 2018/11/9 清除缓存功能 
}
