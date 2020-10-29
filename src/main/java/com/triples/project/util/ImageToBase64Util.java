package com.triples.project.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Base64;
import java.util.Base64.Encoder;

import javax.imageio.ImageIO;

public class ImageToBase64Util {
	
	/*
	byte[] imageBytes = extractBytes("https://s.pstatic.net/static/www/mobile/edit/2016/0705/mobile_212852414260.png");
	byte[] baseIncodingBytes = encodingBase64(imageBytes);
	
	System.out.println(makeBase64String(baseIncodingBytes));
	 */
	
	private static byte[] extractBytes(String imageUrl) throws IOException {
		byte[] imageInByte;

		URL url = new URL(imageUrl);
		BufferedImage originalImage = ImageIO.read(url);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(originalImage, "png", baos);
		baos.flush();
		 
		imageInByte = baos.toByteArray();

		return imageInByte;
	}
	
	private static byte[] encodingBase64(byte[] targetBytes) {
		Encoder encoder = Base64.getEncoder();
		return encoder.encode(targetBytes);
	}
	
	private static String makeBase64String(byte[] base64Str) {
		return "data:image/gif;base64," + new String(base64Str);
	}
	
	public static String getBase64String(String imageUrl) throws IOException {
		byte[] imageBytes = extractBytes(imageUrl);
		byte[] baseIncodingBytes = encodingBase64(imageBytes);
		
		return makeBase64String(baseIncodingBytes);
	}
}
