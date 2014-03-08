### To compile custom ffmpeg for linux

Compiling ffmpeg as a static exectuable on Linux -- along with latest versions
of most important dependencies.

On Unbuntu:

    # tons of libraries missing (try on fresh install system)
    sudo apt-get install libopenjpeg-dev librtmp-dev

    mkdir -p ffmpeg/root
    cd ffmpeg

Compile good, redistributable faac lib:
    
    wget http://downloads.sourceforge.net/faac/faac-1.28.tar.gz
    tar zxvf faac-1.28.tar.gz
    cd faac-1.28
    ./configure --prefix=$(realpath ../root) --enable-static

Compile the best AAC lib out there:
    
    wget http://sourceforge.net/projects/opencore-amr/files/fdk-aac/fdk-aac-0.1.3.tar.gz
    tar xzvf fdk-aac-0.1.3.tar.gz
    cd fdk-aac-0.1.3
    ./configure --prefix=$(realpath ../root) --enable-static --disable-shared
    make
    make install

RTMP:

    wget http://rtmpdump.mplayerhq.hu/download/rtmpdump-2.3.tgz
    tar zxvf rtmpdump-2.3.tgz
    cd rtmpdump-2.3
    make
    cp librtmp/librtmp.a ../root/lib/
    cp librtmp/rtmp.h ../root/include/
    

Compile latest and greatest libx264

    # install new yasm tool just for compiling...
    wget http://www.tortall.net/projects/yasm/releases/yasm-1.2.0.tar.gz
    tar xf yasm-1.2.0.tar.gz
    cd yasm-1.2.0
    ./configure
    make
    sudo make install

    git clone git://git.videolan.org/x264.git
    cd x264
    # find SHA-1 of stable release here http://download.videolan.org/pub/x264/binaries/linux-x86_64/
    git checkout 956c8d8
    ./configure --prefix=$(realpath ../root) --enable-static --without-mp4v2
    make
    make install

    wget http://www.ffmpeg.org/releases/ffmpeg-2.1.4.tar.gz
    tar zxvf ffmpeg-2.1.4.tar.gz
    cd ffmpeg-2.1.4
    ./configure --prefix=$(realpath ../root) \
    --extra-cflags='-I$(realpath ../root/include) -static' \
    --extra-ldflags='-L$(realpath ../root/lib) -static' --extra-libs="-ldl -lexpat -lfreetype" \
    --enable-static --disable-shared --disable-ffserver --disable-doc --enable-nonfree --disable-libspeex \
    --enable-libfaac --enable-libfdk_aac --enable-bzlib --enable-zlib --enable-postproc --enable-runtime-cpudetect --enable-libx264 \
    --enable-gpl --enable-libtheora --enable-libvorbis --enable-libmp3lame --enable-gray \
    --enable-libopenjpeg --enable-version3 --enable-libvpx --enable-openssl
    make
    make install