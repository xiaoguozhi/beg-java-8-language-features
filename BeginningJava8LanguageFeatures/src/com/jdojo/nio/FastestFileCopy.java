// FastestFileCopy.java
package com.jdojo.nio;

import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

public class FastestFileCopy {
	public static void main(String[] args) {
		File sourceFile = new File("luci5.txt");
		File sinkFile = new File("luci5_copy.txt");
		try {
			copy(sourceFile, sinkFile, false);
			System.out.println(sourceFile.getAbsoluteFile() + 
			                   " has been copied to " + 
			                   sinkFile.getAbsolutePath());
		}
		catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void copy(File sourceFile, 
		File sinkFile, boolean overwrite) throws IOException {

		String msg = "";

		// Perform some error checks
		if (!sourceFile.exists()) {
			msg = "Source file " + sourceFile.getAbsolutePath() + 
			      " does not exist.";
			throw new IOException(msg);
		}
		
		if (sinkFile.exists() && !overwrite) {
			msg = "Destination file " + 
			      sinkFile.getAbsolutePath() + 
			      " already exists.";
			throw new IOException(msg);
		}

		// Obtain source and sink file channels in a 
		// try-with-resources block, so they are closed automatically.
		try (FileChannel srcChannel = 
				new FileInputStream(sourceFile).getChannel();
			 FileChannel sinkChannel =	
				new FileOutputStream(sinkFile).getChannel()) {

			// Copy source file contents to the sink file
			srcChannel.transferTo(0, srcChannel.size(), sinkChannel);
		}		
	}
}
