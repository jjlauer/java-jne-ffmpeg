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
import java.io.IOException;
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
        String streamLink = "https://r7---sn-jvhj5nu-a5me.googlevideo.com/videoplayback?source=youtube&ip=67.171.19.56&expire=1393665982&sparams=id%2Cip%2Cipbits%2Citag%2Cratebypass%2Crequiressl%2Csource%2Cupn%2Cexpire&ipbits=0&signature=309B8D9011DD116FF8CBC5A97E24F4C4A124C609.A8FF677E2B67F079958A9410D334ACAA052F8C98&upn=kj5JkB4dlq0&key=yt5&id=4a429ea82245176b&fexp=943902%2C912300%2C940000%2C932260%2C919007%2C914930%2C914095%2C916612%2C937417%2C937416%2C913434%2C936910%2C936913%2C902907&mt=1393639387&ratebypass=yes&itag=43&sver=3&mv=m&requiressl=yes&ms=au";
        
        String exeName = "ffmpeg";
        File ffmpegExeFile = JNE.find(exeName, JNE.FindType.EXECUTABLE);
        
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
        
        String thumbnailSeekPos = "00:00:01";
        
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
        
        
        
        
        /**
        
        
        ProcessBuilder pb = new ProcessBuilder(commands);
        pb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
        pb.redirectError(ProcessBuilder.Redirect.INHERIT);
        Process p = pb.start();
        
        // very simple method to wait for process
        long now = System.currentTimeMillis(); 
        long finish = now + timeoutMillis;
        
        while (isAlive(p)) {
            // wait in chunks of 1sec
            Thread.sleep(1000);
            if (System.currentTimeMillis() >= finish && isAlive(p)) {             
                // if still alive after expiration, kill the process
                p.destroy();
                throw new IOException("ffmpeg process exceeded timeout [" + timeoutMillis + " ms]");
            }
        }
        
        // if we get here an exit value SHOULD exist
        int retVal = p.exitValue();
        if (retVal != 0) {
            throw new IOException("ffmpeg exited with non-zero value [" + retVal + "]");
        }
        
        // does the thumbnail exist?
        if (!targetThumbnailFile.exists()) {
            throw new IOException("ffmpeg process did not generate thumbnail file (not sure what happened)");
        }
        
        ProcessBuilder pb = new ProcessBuilder(
            ffmpegExeFile.getAbsolutePath(),
            "-version"
        );
        */
        
    }
    
}
