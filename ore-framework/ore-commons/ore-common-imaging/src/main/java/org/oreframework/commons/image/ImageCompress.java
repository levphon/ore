package org.oreframework.commons.image;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Locale;

import javax.imageio.ImageIO;

/**
 * @author huangzz
 * @version [1.0.0, 2015-3-18]
 */
public class ImageCompress
{
    // 输入图路径
    String inputDir;
    
    // 输出图路径
    String outputDir;
    
    // 输入图文件名
    String inputFileName;
    
    // 输出图文件名
    String outputFileName;
    
    int outPutWidth = 110;
    
    // 默认输出图片高
    int outPutHeight = 80;
    
    int rate = 0;
    
    // 是否等比缩放标记
    boolean proportion = true;
    
    public ImageCompress()
    {
        // 初始化变量
        inputDir = "";
        outputDir = "";
        inputFileName = "";
        outputFileName = "";
        outPutWidth = 110;
        outPutHeight = 80;
        rate = 0;
    }
    
    public void setInputDir(String inputDir)
    {
        this.inputDir = inputDir;
    }
    
    public void setOutputDir(String outputDir)
    {
        this.outputDir = outputDir;
    }
    
    public void setInputFileName(String inputFileName)
    {
        this.inputFileName = inputFileName;
    }
    
    public void setOutputFileName(String outputFileName)
    {
        this.outputFileName = outputFileName;
    }
    
    public void setoutPutWidth(int outPutWidth)
    {
        this.outPutWidth = outPutWidth;
    }
    
    public void setoutPutHeight(int outPutHeight)
    {
        this.outPutHeight = outPutHeight;
    }
    
    public void setWidthAndHeight(int width, int height)
    {
        this.outPutWidth = width;
        this.outPutHeight = height;
    }
    
    public String createThumble()
        throws Exception
    {
        // ext是图片的格式 gif JPG 或png
        String ext = "";
        
        File F = new File(inputDir, inputFileName);
        
        if (!F.isFile())
        {
            throw new Exception(F + " is not image file error in CreateThumbnail!");
        }
        
        // 首先判断上传的图片是gif还是JPG ImageIO只能将gif转换为png
        if (isJpg(inputFileName))
        {
            ext = "jpg";
        }
        else
        {
            ext = "png";
        }
        
        File ThF = new File(outputDir, outputFileName + "." + ext);
        
        BufferedImage Bi = ImageIO.read(F);
        
        if (Bi.getWidth(null) == -1)
        {
            // System.out.println("   can't read,retry!" + "<BR>");
            return "no";
        }
        else
        {
            int new_w;
            int new_h;
            
            // 判断是否是等比缩放.
            if (this.proportion == false)
            {
                // 为等比缩放计算输出的图片宽度及高度
                
                double rate1 = ((double)Bi.getWidth(null)) / (double)outPutWidth + 0.1;
                double rate2 = ((double)Bi.getHeight(null)) / (double)outPutHeight + 0.1;
                double rate = rate1 > rate2 ? rate1 : rate2;
                new_w = (int)(((double)Bi.getWidth(null)) / rate);
                new_h = (int)(((double)Bi.getHeight(null)) / rate);
            }
            else
            {
                // 输出的图片宽度
                new_w = outPutWidth;
                
                // 输出的图片高度
                new_h = outPutHeight;
            }
            
            try
            {
                AffineTransform transform = new AffineTransform();//
                transform.setToScale(new_w, new_h);//
                AffineTransformOp ato = new AffineTransformOp(transform, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
                BufferedImage newBI = new BufferedImage(new_w, new_h, BufferedImage.TYPE_INT_ARGB);
                ato.filter(Bi, newBI);
                ImageIO.write(newBI, ext, ThF);
            }
            catch (Exception ex)
            {
                throw new Exception(" ImageIo.write error in CreatThum.: " + ex.getMessage());
            }
        }
        return "ok";
    }
    
    public boolean isJpg(String imageFile)
    {
        String ext = "";
        int lastindex = imageFile.lastIndexOf(".");
        if ((lastindex == imageFile.length()) || (lastindex == -1))
        {
            ext = " ";
        }
        else
        {
            ext = imageFile.substring(lastindex + 1, imageFile.length()).toLowerCase(Locale.getDefault());
        }
        
        if (ext != null && !"".equals(ext) && "jpg".equals(ext))
        {
            return true;
        }
        
        return false;
    }
    
    public String minipic(String inputDir, String inputFile, String outputDir, String outputFile, int width,
        int height, boolean gp)
        throws Exception
    {
        // 输入图路径
        this.inputDir = inputDir;
        
        // 输出图路径
        this.outputDir = outputDir;
        
        // 输入图文件名
        this.inputFileName = inputFile;
        
        // 输出图文件名
        this.outputFileName = outputFile;
        
        // 设置图片长宽
        setWidthAndHeight(width, height);
        
        // 是否是等比缩放 标记
        this.proportion = gp;
        
        return createThumble();
        
    }
    
    public int getRate()
    {
        return rate;
    }
    
    public void setRate(int rate)
    {
        this.rate = rate;
    }
    
    public static void main(String argv[])
        throws Exception
    {
        ImageCompress myCopress = new ImageCompress();
        
        System.out.println(myCopress.minipic("D:\\", "pp01.gif", "D:\\", "rong", 110, 80, true));
    }
    
}
