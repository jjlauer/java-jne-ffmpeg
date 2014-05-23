demo-version:
	mvn -f mfz-primetv-ffmpeg-demo/pom.xml -e compile exec:java -Dexec.classpathScope="compile" -Dexec.mainClass="com.mfizz.jne.ffmpeg.VersionDemo"
	
demo-thumbnail:
	mvn -f mfz-primetv-ffmpeg-demo/pom.xml -e compile exec:java -Dexec.classpathScope="compile" -Dexec.mainClass="com.mfizz.jne.ffmpeg.ThumbnailDemo"
