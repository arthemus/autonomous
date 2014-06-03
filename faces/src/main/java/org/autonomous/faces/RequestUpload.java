package org.autonomous.faces;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * Para obter arquivos enviados via Upload de páginas Web.
 * 
 * @author arthemus
 * @since 21/05/2014
 *
 */
public class RequestUpload {

	private static final int THRESHOLD_SIZE = 1024 * 1024 * 3; // 3MB
	private static final int MAX_FILE_SIZE = 1024 * 1024 * 40; // 40MB
	private static final int REQUEST_SIZE = 1024 * 1024 * 50; // 50MB
	
	private final String _filePath;
	private final HttpServletRequest _request;
	
	public RequestUpload(HttpServletRequest request) {
		_request = request;
		_filePath = this.getClass().getClassLoader().getResource("../../").getFile();
	}

	/**
	 * Obtem a lista de arquivos enviados pelo Request.
	 * 
	 * @param req
	 * @return
	 */
	public List<FileItem> getFileList() {
		boolean isMultipart = ServletFileUpload.isMultipartContent(_request);
		if (!isMultipart)
			throw new UnsupportedOperationException();
		DiskFileItemFactory factory = new DiskFileItemFactory();
		factory.setSizeThreshold(THRESHOLD_SIZE);
		factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setFileSizeMax(MAX_FILE_SIZE);
		upload.setSizeMax(REQUEST_SIZE);
		List<FileItem> formItems = null;
		try {
			formItems = upload.parseRequest(_request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return formItems;
	}
	
	/**
	 * Obtem um determinado arquivo construido com base nos arquivos enviados pelo
	 * Upload.
	 * 
	 * @param fileList
	 * @return
	 */
	public File getUploadFile(List<FileItem> fileList) {
		File uploadDir = new File(_filePath);
		System.out.println(uploadDir.getAbsolutePath());
		if (!uploadDir.exists()) {
			uploadDir.mkdir();
		}
		File uploadedFile = null;
		try {
			for (FileItem fi : fileList) {
				if (!fi.isFormField()) {
					String fileName = new File(fi.getName()).getName();
					String filePath = _filePath.concat(File.separator.concat(fileName));
					uploadedFile = new File(filePath);
					fi.write(uploadedFile);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return uploadedFile;
	}
	
}
