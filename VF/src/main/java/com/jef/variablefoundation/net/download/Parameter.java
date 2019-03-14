package com.jef.variablefoundation.net.download;

/**
 * Created by mr.lin on 2018/11/30
 * 下载参数
 */
public class Parameter {

    String remoteUrl;
    String directory;
    String fileName;

    long totalLength;//文件总长
    int threadCount;//多线程数
    long[] threadLength;//每条线程负责的长度
    long[] startPosition;//每条线程起始位置
    long[] endPosition;//每条线程结束位置
    long[] currentPosition;//每条线程结束位置

}
