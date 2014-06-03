package org.autonomous.functions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.apache.commons.codec.binary.Base64;

/**
 * Classe para conversão de arquivos para uma sequencia hexadecimal
 * da Base 64, ideal para guardar arquivos no banco de dados.
 * 
 * @author arthemus
 * @since 20/12/2013
 */
public class Stream {

	/**
	 * 
	 * Obtem um hash Base64 a partir de um imput stream.
	 * 
	 * @param inputStream
	 * @return String hexadecimal
	 * @throws StreamException 
	 */
	public static String getBase64(final InputStream inputStream) 
			throws StreamException {

		if (inputStream == null)
			throw new StreamException("O arquivo não foi encontrado ou não está acessivel, verifique.");

		String stringStream = null;
		
		try {
			ArrayList<Integer> listaBytes = new ArrayList<Integer>();
			
			Integer proximoByte = inputStream.read();
			while (proximoByte != -1) {
				listaBytes.add(proximoByte);
				proximoByte = inputStream.read();
			}
			
			byte[] bytes = new byte[listaBytes.size()];
			int i = 0;
			for (Integer lstByte : listaBytes) {
				bytes[i] = (byte) lstByte.intValue();
				i++;
			}

			stringStream = Base64.encodeBase64String(bytes);

		} catch (IOException e) {			
			throw new StreamException("Problemas durante a leitura do arquivo:\n".concat(e.getMessage()));
		} catch (Exception e) {
			throw new StreamException("Ocorreu um erro desconhecido durante a conversão:\n".concat(e.getMessage()));
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				// NoCommand
			}
		}

		return stringStream;
	}

	/**
	 * Obtem uma string Base64 a partir de um arquivo.
	 * 
	 * @param file
	 * @return String hexadecimal
	 * @throws StreamException 
	 */
	public static String getBase64(final File file) throws StreamException {		
		if (!file.exists() || !file.canRead()) new FileNotFoundException();		
		try {
			return getBase64(new FileInputStream(file));
		} catch (FileNotFoundException e) {			
			throw new StreamException("O arquivo não foi encontrado ou não está acessivel, verifique.");
		}
	}

	/**
	 * Converte uma string Base64 para seu formato original.
	 * 
	 * @param hashFile String hexadecimal
	 * @param fileName Nome do arquivo
	 * @return Arquivo original
	 * @throws StreamException 
	 */
	public static File getFile(final String hashFile, final String fileName) 
			throws StreamException {
		File fileReturn = new File(fileName);
		try {
			byte[] bytes = Base64.decodeBase64(hashFile);
			FileOutputStream saida = new FileOutputStream(fileReturn);
			saida.write(bytes);
			saida.flush();
			saida.close();
		} catch (FileNotFoundException e) {
			throw new StreamException("O arquivo não foi encontrado ou não está acessivel:\n".concat(e.getMessage()));
		} catch (IOException e) {			
			throw new StreamException("Problemas durante a leitura do arquivo:\n".concat(e.getMessage()));
		} catch (Exception e) {
			throw new StreamException("Ocorreu um erro desconhecido durante a conversão:\n".concat(e.getMessage()));
		}
		return fileReturn;
	}

}
