package org.oreframework.image.im4java; 
import java.io.IOException;

import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;
import org.im4java.process.StandardStream;

/** 
 * @author  huangzz
 * @version  [1.0.0, 2015-9-22]
 */
public class GraphicsMagickDemo {
	/**
	 * 
	 * @param picPath
	 *            图片路径
	 * @param drawPicPath
	 *            draw后的路径
	 * @param width
	 *            draw后的宽度
	 * @param height
	 *            draw后的高度
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws IM4JavaException
	 */
	public static void drawImg(String picPath, String drawPicPath, int width,
			int height) throws IOException, InterruptedException,
			IM4JavaException {
		IMOperation op = new IMOperation();
		op.addImage();
		op.resize(width, height);
		op.font("Arial").fill("red").draw("text 100,100 www.taobao.com");
		op.quality(85d);
		op.addImage();
		// IM4JAVA是同时支持ImageMagick和GraphicsMagick的，如果为true则使用GM，如果为false支持IM。
		ConvertCmd cmd = new ConvertCmd(true);
		String osName = System.getProperty("os.name").toLowerCase();
		if (osName.indexOf("win") >= 0) { // linux下不要设置此值，不然会报错
			cmd.setSearchPath("E://Program Files//GraphicsMagick-1.3.18-Q8");
		}
		cmd.setErrorConsumer(StandardStream.STDERR);
		cmd.run(op, picPath, drawPicPath);
	}

	/**
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			drawImg("D://tester.jpg", "D://testerTwo.jpg", 300, 400);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
