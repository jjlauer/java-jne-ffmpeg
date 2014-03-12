package com.mfizz.jne.ffmpeg;

/*
 * #%L
 * mfz-ffmpeg
 * %%
 * Copyright (C) 2012 - 2014 mfizz
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import com.mfizz.jne.JNE;
import com.mfizz.jne.StreamGobbler;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author joelauer
 */
public class ThumbnailDemo {
    private static final Logger logger = LoggerFactory.getLogger(ThumbnailDemo.class);
    
    static public void main(String[] args) throws Exception {
        // various media files on web based on big bunny
        String streamLink = "http://download.blender.org/peach/bigbuckbunny_movies/big_buck_bunny_1080p_h264.mov";
        //String streamLink = "http://download.blender.org/peach/bigbuckbunny_movies/big_buck_bunny_1080p_surround.avi";
        //String streamLink = "http://download.blender.org/peach/bigbuckbunny_movies/big_buck_bunny_1080p_stereo.ogg";
        //String streamLink = "http://download.blender.org/peach/bigbuckbunny_movies/BigBuckBunny_640x360.m4v";
        String thumbnailSeekPos = "00:00:59";
        
        String exeName = "ffmpeg";
        File ffmpegExeFile = JNE.findExecutable(exeName);
        
        if (ffmpegExeFile == null) {
            logger.error("Unable to find executable [" + exeName + "]");
            System.exit(1);
        }
        
        logger.info("java version: " + System.getProperty("java.version"));
        logger.info("java home: " + System.getProperty("java.home"));
        logger.info("using exe: " + ffmpegExeFile.getAbsolutePath());
        
        // file to write thumbnail to
        File storageDir = new File("target");
        File targetThumbnailFile = new File(storageDir, "thumbnail.jpg");
        
        
        
        List<String> commands = new ArrayList<String>();
        commands.add(ffmpegExeFile.getAbsolutePath());
        commands.addAll(Arrays.asList(("-ss " + thumbnailSeekPos + " -i").split(" ")));
        commands.add(streamLink);
        commands.addAll(Arrays.asList("-y -vcodec mjpeg -vframes 1".split(" ")));
        commands.add(targetThumbnailFile.getAbsolutePath());
        
        ProcessBuilder pb = new ProcessBuilder(commands);
        pb.redirectErrorStream(true);
        Process p = pb.start();
        StreamGobbler outputGobbler = new StreamGobbler(p.getInputStream()) {
            @Override
            public void onLine(String line) {
                logger.debug(line);
            }
            @Override
            public void onException(Exception e) {
                logger.error("Unable to cleanly gobble process output", e);
            }
        };
        outputGobbler.start();
        
        int retVal = p.waitFor();
        logger.info("ret val: " + retVal);
    }
    
}
