package com.triples.project.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class ImageSizeUtils {
	private String SMALL = "SMALL";
	
	private String NONE = "NONE";
	
	private String BIG = "BIG";
	
	@SuppressWarnings("unused")
	private Map<String, Integer> getImageSize(String src) throws IOException{
		Map<String, Integer> sizeMap = new HashMap<String, Integer>();
		File image = new File(src);	
		
		if(image.exists()) {
			BufferedImage bImage = ImageIO.read(image);
			sizeMap.put("width", bImage.getWidth());
			sizeMap.put("height", bImage.getHeight());
		} else {
			sizeMap.put("width", 0);
			sizeMap.put("height", 0);
		}
		
		return sizeMap;
	}
	
	public String defineCardType(int width, int height) {
		String imageType = "";
		
		float ratio = width / height;
		
		if(ratio < 1.33) {
			imageType = BIG;
		} else if(ratio >= 1.33) {
			imageType = SMALL;
		} else {
			imageType = NONE;
		}
		
		return imageType;
	}
}
