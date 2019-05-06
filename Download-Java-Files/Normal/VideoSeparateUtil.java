package com.hasee.minibuslocalhost.util;

import android.annotation.TargetApi;
import android.media.MediaCodec;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.media.MediaMuxer;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Created by fangju on 2018/12/4
 */
public class VideoSeparateUtil {
    /**
     * 合成视频1的音频和视频2的视频
     * @param audioVideoPath 提供音频的视频路径
     * @param audioStartTime 音频的开始时间
     * @param frameVideoPath 提供图像的视频路径
     * @param combinedVideoOutFile 合成后的文件
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static void combineTwoVideos(String audioVideoPath,
                                        long audioStartTime,
                                        String frameVideoPath,
                                        File combinedVideoOutFile){
        //MediaExtractor用于提取多路的、通常编码的视频资源的，
        // 通过它我们可以选择音频或者视频轨，然后分别对它们进行操作等；
        MediaExtractor audioVideoExtractor = new MediaExtractor();
        int mainAudioExtractorTrackIndex = -1;//提供音频的视频的音频轨
        int mainAudioMuxerTrackIndex = -1;//合成后的视频的音频轨
        int mainAudioMaxInputSize = 0;//能获取的音频的最大值
        long mainAudioDuration = 0;//音频的时长

        MediaExtractor frameVideoExtractor = new MediaExtractor();
        int frameExtractorTrackIndex = -1;//视频轨
        int frameMuxerTrackIndex = -1;//合成后的视频的视频轨
        int frameMaxInputSize = 0;//能获取的视频的最大值
        int frameRate = 0;//视频的帧率
        long frameDuration = 0;//视频的时长

        //MediaMuxer是用于复用基本流的，用它可以将音频和视频合成，
        //目前支持输出MP4,Webm和3GP格式的视频，在Android7.0以后支持多路复用帧的MP4。
        MediaMuxer muxer = null;//用于合成音频和视频
        try {
            muxer = new MediaMuxer(combinedVideoOutFile.getPath(),
                    MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4);
            audioVideoExtractor.setDataSource(audioVideoPath);//设置视频源
            //音轨信息
            int audioTrackCount = audioVideoExtractor.getTrackCount();//获取数据源的轨道数
            //在此循环轨道数，目的是找到我们想要的音频轨
            for (int i = 0; i < audioTrackCount; i++) {
                //MediaFormat封装了描述媒体数据格式的信息
                MediaFormat format = audioVideoExtractor.getTrackFormat(i);//得到指定索引的记录格式
                String mimeType = format.getString(MediaFormat.KEY_MIME);//主要描述mime媒体类型的格式
                if(mimeType.startsWith("audio/")){//找到音轨
                    mainAudioExtractorTrackIndex = i;
                    mainAudioMuxerTrackIndex = muxer.addTrack(format);//将音频轨添加到MediaMuxer,并返回新的轨道
                    mainAudioMaxInputSize = format.getInteger(MediaFormat.KEY_MAX_INPUT_SIZE);//得到能获取的有关音频的最大值
                    mainAudioDuration = format.getLong(MediaFormat.KEY_DURATION);
                }
            }

            //图像信息
            frameVideoExtractor.setDataSource(frameVideoPath);//设置视频源
            int trackCount = frameVideoExtractor.getTrackCount();//获取数据源的轨道数
            //在此循环轨道数，目的是找到我们想要的视频轨
            for (int i = 0; i < trackCount; i++) {
                MediaFormat format = frameVideoExtractor.getTrackFormat(i);//得到指定索引的记录格式
                String mineType = format.getString(MediaFormat.KEY_MIME);//主要描述mime媒体类型的格式
                if(mineType.startsWith("video/")){//找到视频轨
                    frameExtractorTrackIndex = i;
                    frameMuxerTrackIndex = muxer.addTrack(format);//将视频轨添加到MediaMuxer，并返回新的轨道
                    frameMaxInputSize = format.getInteger(MediaFormat.KEY_MAX_INPUT_SIZE);//得到能获取的有关视频的最大值
                    frameRate = format.getInteger(MediaFormat.KEY_FRAME_RATE);//获取视频的帧率
                    frameDuration = format.getLong(MediaFormat.KEY_DURATION);//获取视频的时长
                }
            }
            //合成
            muxer.start();

            audioVideoExtractor.selectTrack(mainAudioExtractorTrackIndex);//将提供音频的视频选择到音轨上
            MediaCodec.BufferInfo audioBufferInfo = new MediaCodec.BufferInfo();
            ByteBuffer audioByteBuffer = ByteBuffer.allocate(mainAudioMaxInputSize);
            while (true){
                int readSimpleSize = audioVideoExtractor.readSampleData(audioByteBuffer,0);//检索当前编码的样本并将其存储在字节缓冲区中
                if(readSimpleSize < 0){//如果没有可获取的样本则退出循环
                    audioVideoExtractor.unselectTrack(mainAudioExtractorTrackIndex);
                    break;
                }
                long sampleTime = audioVideoExtractor.getSampleTime();//获取当前展示样本的时间（单位毫秒）

                if(sampleTime < audioStartTime){//如果样本时间小于我们想要的开始时间
                    audioVideoExtractor.advance();//推进到下一个样本，类似快进
                    continue;
                }

                if(sampleTime > audioStartTime + frameDuration){//如果样本时间>开始时间+视频时长
                    break;
                }
                //设置样本编码信息
                audioBufferInfo.size = readSimpleSize;
                audioBufferInfo.offset = 0;
                audioBufferInfo.flags = audioVideoExtractor.getSampleFlags();
                audioBufferInfo.presentationTimeUs = sampleTime -audioStartTime;
                muxer.writeSampleData(mainAudioMuxerTrackIndex,audioByteBuffer,audioBufferInfo);//将样本写入
                audioVideoExtractor.advance();//推进到下一个样本，类似快进
            }

            frameVideoExtractor.selectTrack(frameExtractorTrackIndex);//将提供视频图像的视频选择到视频轨上
            MediaCodec.BufferInfo videoBufferInfo = new MediaCodec.BufferInfo();
            ByteBuffer videoByteBuffer = ByteBuffer.allocate(frameMaxInputSize);
            while(true){
                int readSampleSize = frameVideoExtractor.readSampleData(videoByteBuffer,0);//检索当前编码的样本并将其存储在字节缓冲区中
                if(readSampleSize < 0){//如果没有可获取的样本则退出循环
                    frameVideoExtractor.unselectTrack(frameExtractorTrackIndex);
                    break;
                }
                //设置样本编码信息
                videoBufferInfo.size = readSampleSize;
                videoBufferInfo.offset = 0;
                videoBufferInfo.flags = frameVideoExtractor.getSampleFlags();
                videoBufferInfo.presentationTimeUs += 1000*1000 / frameRate;
                muxer.writeSampleData(frameMuxerTrackIndex,videoByteBuffer,videoBufferInfo);
                frameVideoExtractor.advance();//推进到下一个样本，类似快进
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            //释放资源
            audioVideoExtractor.release();
            frameVideoExtractor.release();
            if(muxer != null){
                muxer.release();
            }
        }
    }

    /**
     * 视频分离成音频
     * @param audioVideoPath 提供音频的视频路径
     * @param combinedVideoOutFile 合成后的文件
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public static void videoToAudio(String audioVideoPath,
                                    File combinedVideoOutFile){
        //MediaExtractor用于提取多路的、通常编码的视频资源的，
        // 通过它我们可以选择音频或者视频轨，然后分别对它们进行操作等；
        MediaExtractor audioVideoExtractor = new MediaExtractor();
        int mainAudioExtractorTrackIndex = -1;//提供音频的视频的音频轨
        int mainAudioMuxerTrackIndex = -1;//合成后的视频的音频轨
        int mainAudioMaxInputSize = 0;//能获取的音频的最大值
        long mainAudioDuration = 0;//音频的时长

        //MediaMuxer是用于复用基本流的，用它可以将音频和视频合成，
        //目前支持输出MP4,Webm和3GP格式的视频，在Android7.0以后支持多路复用帧的MP4。
        MediaMuxer muxer = null;//用于合成音频和视频
        try {
            muxer = new MediaMuxer(combinedVideoOutFile.getPath(),
                    MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4);
            audioVideoExtractor.setDataSource(audioVideoPath);//设置视频源
            //音轨信息
            int audioTrackCount = audioVideoExtractor.getTrackCount();//获取数据源的轨道数
            //在此循环轨道数，目的是找到我们想要的音频轨
            for (int i = 0; i < audioTrackCount; i++) {
                //MediaFormat封装了描述媒体数据格式的信息
                MediaFormat format = audioVideoExtractor.getTrackFormat(i);//得到指定索引的记录格式
                String mimeType = format.getString(MediaFormat.KEY_MIME);//主要描述mime媒体类型的格式
                if(mimeType.startsWith("audio/")){//找到音轨
                    mainAudioExtractorTrackIndex = i;
                    mainAudioMuxerTrackIndex = muxer.addTrack(format);//将音频轨添加到MediaMuxer,并返回新的轨道
                    mainAudioMaxInputSize = format.getInteger(MediaFormat.KEY_MAX_INPUT_SIZE);//得到能获取的有关音频的最大值
                    mainAudioDuration = format.getLong(MediaFormat.KEY_DURATION);
                }
            }
            //合成
            muxer.start();
            audioVideoExtractor.selectTrack(mainAudioExtractorTrackIndex);//将提供音频的视频选择到音轨上
            MediaCodec.BufferInfo audioBufferInfo = new MediaCodec.BufferInfo();
            ByteBuffer audioByteBuffer = ByteBuffer.allocate(mainAudioMaxInputSize);
            while (true){
                int readSimpleSize = audioVideoExtractor.readSampleData(audioByteBuffer,0);//检索当前编码的样本并将其存储在字节缓冲区中
                if(readSimpleSize < 0){//如果没有可获取的样本则退出循环
                    break;
                }
                //设置样本编码信息
                audioBufferInfo.size = readSimpleSize;
                audioBufferInfo.offset = 0;
                audioBufferInfo.flags = audioVideoExtractor.getSampleFlags();
                audioBufferInfo.presentationTimeUs = audioVideoExtractor.getSampleTime();
                muxer.writeSampleData(mainAudioMuxerTrackIndex,audioByteBuffer,audioBufferInfo);//将样本写入
                audioVideoExtractor.advance();//推进到下一个样本，类似快进
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            //释放资源
            audioVideoExtractor.release();
            if(muxer != null){
                muxer.release();
            }
        }
    }
}
