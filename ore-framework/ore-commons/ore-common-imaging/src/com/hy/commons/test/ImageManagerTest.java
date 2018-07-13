package com.hy.commons.test; 

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.hy.commons.file.impl.DefaultImageManager;
import com.hy.commons.result.Result;

/**
 * Description:
 * @author  LiChunming
 * @version V1.0 
 * @Company: MSD. 
 * @Copyright: Copyright (c) 2011
 **/
public class ImageManagerTest {
	public static void main( String[] args )
    {
		UploadImage();
	//writeImage();
	}
	public static void UploadImage(){
		DefaultImageManager imageManager=new DefaultImageManager();
		imageManager.setImagePath("D:/Image");
		System.out.println("=========start====================");
		Long times =System.currentTimeMillis();
		File file=new File("D:\\Image\\test232.jpg");
		 Result result=null;
		try {
			result = imageManager.UploadResizeImage(500, 100, file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 System.out.println("result:"+ result.getDefaultModel().toString());
		System.out.println("times:"+(System.currentTimeMillis()-times));
		System.out.println("=========end====================");
	}
	public static void writeImage(){
		DefaultImageManager imageManager=new DefaultImageManager();
		System.out.println("=========start====================");
		Long times =System.currentTimeMillis();
		imageManager.setImagePath("D:/Image");
		File file=new File("D:\\Image\\test232.jpg");
		imageManager.setMaxSize(204800);
		List<String> style=new ArrayList<String>();
		style.add("100*100");
		style.add("200*200");
		style.add("300*300");
		style.add("400*400");
		style.add("500*500");
		style.add("600*600");
		style.add("700*700");
		style.add("800*800");
		style.add("900*900");
		style.add("1000*1000");
		imageManager.setStyles(style);
		System.out.println("Java path:"+System.getProperty("java.library.path"));
		System.setProperty("jmagick.systemclassloader","no");
		try {
			imageManager.writeImage(file);
			//imageManager.UploadResizeImage(style, file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("times:"+(System.currentTimeMillis()-times));
		System.out.println("=========end====================");
	}
}
 