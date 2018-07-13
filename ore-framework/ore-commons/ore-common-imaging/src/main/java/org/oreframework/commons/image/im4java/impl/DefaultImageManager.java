package org.oreframework.commons.image.im4java.impl;

import java.awt.Dimension;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import magick.ImageInfo;
import magick.MagickException;
import magick.MagickImage;

import org.apache.commons.io.FileUtils;
import org.oreframework.commons.image.im4java.ImageManager;
import org.oreframework.commons.image.im4java.result.Result;
import org.oreframework.commons.image.im4java.result.impl.ResultSupport;

/**
 * 图片储存 <li>商品中图（详细页显示）：350*350 像素</li>
 * 
 * <li>中图下面的5个小图（详细页显示）：45*45 像素</li>
 * 
 * <li>列表页图片（列表形式）：100*100 像素</li>
 * 
 * <li>列表页图片（大图形式）：220*220 像素</li>
 * 
 * <li>商品大图显示：500*500 像素</li>
 * @author  huangzz
 * @version  [1.0.0, 2015-9-22]
 */
public class DefaultImageManager implements ImageManager {

	private static final int BUFFER_SIZE = 16 * 1024;
	private String imagePath;     // 图片路径
	private int maxSize;          // 图片最大值
	private List<String> styles;  // 压缩图片类型

	public List<String> getStyles() {
		return styles;
	}

	public void setStyles(List<String> styles) {
		this.styles = styles;
	}

	public int getMaxSize() {
		return maxSize;
	}

	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	private void scaleImage(List<String> styles,File file, String fileName, String suffix) throws MagickException {

		if (styles != null && styles.size() > 0) {
			ImageInfo info = new ImageInfo(file.getPath());
			MagickImage image = new MagickImage(new ImageInfo(file.getPath()));

			Dimension imageDim = image.getDimension();
			for (String item : styles) {
				int width = Integer.parseInt(item.substring(0, item.indexOf("*")));
				int height = Integer.parseInt(item.substring(item.indexOf("*") + 1, item.length()));
				double fileWidth = imageDim.getWidth();
				double fileHeight = imageDim.getHeight();
				double n = width / fileWidth;
				double m = height / fileHeight;
				MagickImage scaled;
				// 如果需要压缩的图片尺寸比需要压缩的小,则不压缩,尺寸和原来一样
				if (n >= 1 && m >= 1) {
					scaled = image.scaleImage((int) fileWidth, (int) fileHeight);
					scaled.setFileName(this.getImagePath() + "/" + fileName + "." + width + "x"
							+ height + "." + suffix);
					scaled.writeImage(info);
				} else {
					// 最优比例压缩
					if (n > m) {
						scaled = image.scaleImage((int) (fileWidth * m), height);
					} else {
						scaled = image.scaleImage(width, (int) (fileHeight * n));
					}
					scaled.setFileName(this.getImagePath() + "/" + fileName + "." + width + "x"
							+ height + "." + suffix);
					scaled.writeImage(info);
				}
			}
		}
	}

	public Result writeImage(File file) throws IOException {
		Result result = this.UploadImage(file);
		if (this.getStyles() != null && this.getStyles().size() > 0 && result.isSuccess()) {
			try {
				this.scaleImage(this.styles,file, result.getModels().get("fileName").toString(), result
						.getModels().get("suffix").toString());
			} catch (MagickException e) {
				result.setSuccess(false);
				result.setError(e);
			}
		}
		return result;
	}
	

	public boolean checkImageTypeVailable(File file) {
		return this.getImageType(file) != null;
	}

	@Override
	public Result UploadImage(File file) throws IOException {
		Result result = new ResultSupport();
		InputStream in = null;
		OutputStream out = null;
		try {
			String suffix = this.getImageType(file);
			if (suffix == null) {
				result.setSuccess(false);
				result.setError("文件格式错误");
				return result;
			}
			in = new BufferedInputStream(new FileInputStream(file), BUFFER_SIZE);
			String fileName = System.currentTimeMillis() + "";
			out = new BufferedOutputStream(new FileOutputStream(this.getImagePath() + "/"
					+ fileName + "." + suffix), BUFFER_SIZE);
			byte[] buffer = new byte[BUFFER_SIZE];
			// while (in.read(buffer) > 0) {
			// out.write(buffer);//改方法会导致文件变大,请其他同事注意
			// }
			int len = -1;
			while ((len = in.read(buffer)) > 0) {
				out.write(buffer, 0, len);
			}
			result.setSuccess(true);
			result.setDefaultModel(fileName + "." + suffix);
			result.setDefaultModel("suffix", suffix);
			result.setDefaultModel("fileName", fileName);
		} catch (Exception e) {
			result.setSuccess(false);
		} finally {
			if (null != in) {
				in.close();
			}
			if (null != out) {
				out.close();
			}
		}
		return result;
	}

	/**
	 * 组合图片名称 例如：根据A.jpg 获得小图 A.100x100.jpg
	 */
	public String imageCombine(String size, String imageName) throws Exception {
		size = size.replace("*", "x");
		String format = imageName.substring(imageName.lastIndexOf("."), imageName.length());
		String name = imageName.substring(0, imageName.lastIndexOf("."));
		String url = name + "." + size + format;
		return url;
	}

	public boolean delPhysicalImage(List<String> listdel, int sellerId) {
		for (int m = 0; m < listdel.size(); m++) {
			String imgName = listdel.get(m);
			this.deleteFile(imgName);
			if (null != styles) {
				for (String imgsize : styles) {
					try {
						String subimgName = this.imageCombine(imgsize, imgName);
						this.deleteFile(subimgName);
					} catch (Exception e) {
						// e.printStackTrace();
					}
				}
			}
		}
		return true;
	}

	public void deleteFile(String imageurl) {
		imageurl=imagePath +"/"+imageurl;// 路径还需要改，还要加上商户号sellerId文件夹
		try {
			java.io.File imgfile = new java.io.File(imageurl);
			if (imgfile.exists() && imgfile.isFile()) { // 防止删除文件夹
				imgfile.delete();
			}
			imgfile = null;
		} catch (Exception e) {
			// e.printStackTrace();
		}
	}

	public boolean checkFileSize(File file) {

		long filesize = file.length();
		if (filesize > maxSize) {
			return false;
		} else {
			return true;
		}
	}
	
	public Result createImage(String imageurl){
		 Result result = new ResultSupport();
		 String	picUrl=imageurl.substring(0, imageurl.lastIndexOf("."));
		 imageurl=imagePath +"/"+imageurl;
		 File file = new File(imageurl);
		try {
			String suffix = this.getImageType(file);
			if (suffix == null) {
				result.setSuccess(false);
				result.setError("文件格式错误");
			}else{
				try {
					this.scaleImage(this.styles,file,picUrl,suffix);
				} catch (MagickException e) {
					result.setSuccess(false);
					result.setError(e);
				}
			}
			}catch (Exception e) {
				result.setSuccess(false);
			}
		return result;
	}
	
	@Override
	public Result UploadResizeImage(int imgHeight, int imgWidth, File file)
			throws IOException {
		Result result=this.UploadImage(file);
		try {
			String fileName =this.resizeImage(imgHeight, imgWidth, file, result.getModels().get("fileName").toString(), result.getModels().get("suffix").toString());
			result.setDefaultModel("fileName", fileName);
			result.setDefaultModel(fileName);
		} catch (MagickException e) {
			result.setSuccess(false);
			result.setError(e);
		}
		return result;
		
	}
	@Override
	public Result UploadResizeImageList(List<String> sizeList, File file)
			throws IOException {
		Result result = this.UploadImage(file);
		if (this.getStyles() != null && this.getStyles().size() > 0 && result.isSuccess()) {
			try {
				this.scaleImage(sizeList,file, result.getModels().get("fileName").toString(), result
						.getModels().get("suffix").toString());
			} catch (MagickException e) {
				result.setSuccess(false);
				result.setError(e);
			}
		}
		return result;
	}
	public String resizeImage(int imgHeight, int imgWidth,File file, String fileName, String suffix) throws MagickException{
		String filePath="";
		ImageInfo info = new ImageInfo(file.getPath());
		MagickImage image = new MagickImage(new ImageInfo(file.getPath()));
		Dimension imageDim = image.getDimension();
			double fileWidth = imageDim.getWidth();
			double fileHeight = imageDim.getHeight();
			double n = imgWidth / fileWidth;
			double m = imgHeight / fileHeight;
			MagickImage scaled;
			// 如果需要压缩的图片尺寸比需要压缩的小,则不压缩,尺寸和原来一样
			if (n >= 1 && m >= 1) {
				scaled = image.scaleImage((int) fileWidth, (int) fileHeight);
				scaled.setFileName(this.getImagePath() + "/" + fileName + "." + imgWidth + "x"
						+ imgHeight + "." + suffix);
				scaled.writeImage(info);
			} else {
				// 最优比例压缩
				if (n > m) {
					scaled = image.scaleImage((int) (fileWidth * m), imgHeight);
				} else {
					scaled = image.scaleImage(imgWidth, (int) (fileHeight * n));
				}
				filePath=fileName+ "." + imgWidth + "x"+ imgHeight+"."+ suffix;
				scaled.setFileName(this.getImagePath() + "/" + fileName + "." + imgWidth + "x"
						+ imgHeight + "." + suffix);
				scaled.writeImage(info);
			}
		return filePath;
	}

	@Override
	public String getImageType(File file) {
		// TODO Auto-generated method stub
		if (file == null ) {
			
			System.out.println("========file is null=======");
			return null;
		}
		if( !file.exists()){
			System.out.println("========file path======="+file.getPath());
			System.out.println("========file is exists=======");
			return null;
		}
		try {
			byte[] imgContent = FileUtils.readFileToByteArray(file);
			int len = imgContent.length;
			byte n1 = imgContent[len - 2];
			byte n2 = imgContent[len - 1];
			byte b0 = imgContent[0];
			byte b1 = imgContent[1];
			byte b2 = imgContent[2];
			byte b3 = imgContent[3];
			byte b4 = imgContent[4];
			byte b5 = imgContent[5];
			byte b6 = imgContent[6];
			byte b7 = imgContent[7];
			byte b8 = imgContent[8];
			byte b9 = imgContent[9];

			// GIF(G I F 8 7 a)
			if (b0 == (byte) 'G' && b1 == (byte) 'I' && b2 == (byte) 'F' && b3 == (byte) '8'
					&& b4 == (byte) '7' && b5 == (byte) 'a') {
				return "gif";
				// GIF(G I F 8 9 a)
			} else if (b0 == (byte) 'G' && b1 == (byte) 'I' && b2 == (byte) 'F' && b3 == (byte) '8'
					&& b4 == (byte) '9' && b5 == (byte) 'a') {
				return "gif";
				// PNG(89 P N G 0D 0A 1A)
			} else if (b0 == -119 && b1 == (byte) 'P' && b2 == (byte) 'N' && b3 == (byte) 'G'
					&& b4 == 13 && b5 == 10 && b6 == 26) {
				return "png";
				// JPG JPEG(FF D8 --- FF D9)
			} else if (b0 == -1 && b1 == -40 && n1 == -1 && n2 == -39) {
				return "jpg";
			} else if (b6 == (byte) 'J' && b7 == (byte) 'F' && b8 == (byte) 'I' && b9 == (byte) 'F') {
				return "jpg";
			} else if (b6 == (byte) 'E' && b7 == (byte) 'x' && b8 == (byte) 'i' && b9 == (byte) 'f') {
				return "jpg";
			}
			// else if (b0 == (byte) 'B' && b1 == (byte) 'M') {
			// return true;
			// }
			else {
				return null;
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			return null;
		} catch (IOException e) {
			return null;
		}
	}

	
}
