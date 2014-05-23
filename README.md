### Overview

Native executables ffmpeg and ffprobe packaged to be easily embedded and used from Java as a dependency.
The upstream ffmpeg version that is included in this jar will match the version
of this jar. So "mfz-primetv-ffmpeg-2.1.4.jar" will be based on ffmpeg v2.1.4.

### Static builds of ffmpeg

#### Windows

Supports x86/x64 version >= XP

    http://ffmpeg.zeranoe.com/builds/

#### Mac OSX

Supports x64 version >= 10.6., 10.7., 10.8. or 10.9 (Snow Leopard, Lion, Mountain Lion and Mavericks)

    http://ffmpegmac.net/

    http://www.evermeet.cx/ffmpeg/ (also has ffprobe; which we need)

#### Linux

Supports x86/x64 version >= 3.2.x

    http://ffmpeg.gusari.org/static/

### Updating all module versions to match parent

Update parent pom to version you'd like, then:

    mvn -N versions:update-child-modules

### Compiling on various platforms

Linux: FFMPEG_LINUX.md
