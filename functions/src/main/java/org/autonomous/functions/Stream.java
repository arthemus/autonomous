package org.autonomous.functions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class for converting files to a Base64 hexadecimal sequence, ideal for
 * storing files in the database.
 *
 * @author arthemus
 * @since 20/12/2013
 */
public class Stream {

	private static final Logger LOGGER = LoggerFactory.getLogger(Stream.class);

	/**
	 * Obtains a Base64 hash from an input stream.
	 *
	 * @param inputStream
	 *            The input stream to convert.
	 * @return The Base64-encoded string.
	 * @throws StreamException
	 */
	public static String getBase64(final InputStream inputStream)
			throws StreamException {

		if (inputStream == null)
			throw new StreamException("The file was not found or is not accessible, please verify.");

		String stringStream = null;

		try {
			ArrayList<Integer> byteList = new ArrayList<Integer>();

			Integer nextByte = inputStream.read();
			while (nextByte != -1) {
				byteList.add(nextByte);
				nextByte = inputStream.read();
			}

			byte[] bytes = new byte[byteList.size()];
			int i = 0;
			for (Integer lstByte : byteList) {
				bytes[i] = (byte) lstByte.intValue();
				i++;
			}

			stringStream = Base64.encodeBase64String(bytes);

		} catch (IOException e) {
			throw new StreamException("Problems during file reading:\n".concat(e.getMessage()));
		} catch (Exception e) {
			throw new StreamException("An unknown error occurred during conversion:\n".concat(e.getMessage()));
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				LOGGER.error("Failed to close input stream", e);
			}
		}

		return stringStream;
	}

	/**
	 * Obtains a Base64 string from a file.
	 *
	 * @param file
	 *            The file to convert.
	 * @return The Base64-encoded string.
	 * @throws StreamException
	 */
	public static String getBase64(final File file) throws StreamException {
		if (!file.exists() || !file.canRead()) new FileNotFoundException();
		try {
			return getBase64(new FileInputStream(file));
		} catch (FileNotFoundException e) {
			throw new StreamException("The file was not found or is not accessible, please verify.");
		}
	}

	/**
	 * Converts a Base64 string back to its original file format.
	 *
	 * @param hashFile
	 *            The Base64-encoded string.
	 * @param fileName
	 *            The name of the file.
	 * @return The original file.
	 * @throws StreamException
	 */
	public static File getFile(final String hashFile, final String fileName)
			throws StreamException {
		File fileReturn = new File(fileName);
		try {
			byte[] bytes = Base64.decodeBase64(hashFile);
			FileOutputStream output = new FileOutputStream(fileReturn);
			output.write(bytes);
			output.flush();
			output.close();
		} catch (FileNotFoundException e) {
			throw new StreamException("The file was not found or is not accessible:\n".concat(e.getMessage()));
		} catch (IOException e) {
			throw new StreamException("Problems during file reading:\n".concat(e.getMessage()));
		} catch (Exception e) {
			throw new StreamException("An unknown error occurred during conversion:\n".concat(e.getMessage()));
		}
		return fileReturn;
	}

}
