package com.dohit.huick.domain.user.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;

public class Base64DecodedMultipartFile implements MultipartFile {

	private final byte[] imgContent;

	public Base64DecodedMultipartFile(byte[] imgContent) {
		this.imgContent = imgContent;
	}

	@Override
	public String getName() {
		return "file";
	}

	@Override
	public String getOriginalFilename() {
		// TODO - you may want to handle this properly
		return "image.png";
	}

	@Override
	public String getContentType() {
		// TODO - you may want to handle this properly
		return "image/png";
	}

	@Override
	public boolean isEmpty() {
		return imgContent == null || imgContent.length == 0;
	}

	@Override
	public long getSize() {
		return imgContent.length;
	}

	@Override
	public byte[] getBytes() throws IOException {
		return imgContent;
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return new ByteArrayInputStream(imgContent);
	}

	@Override
	public void transferTo(File dest) throws IOException, IllegalStateException {
		new FileOutputStream(dest).write(imgContent);
	}
}
