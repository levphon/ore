package org.oreframework.common.compress;

import java.io.File;

import org.junit.Test;

/**
 * 测试ZipFileUtil的压缩和解压缩方法
 * 
 * @author Luxh
 */
public class ZipFileUtilTest
{
    
    @Test
    public void testCompressFiles2Zip()
    {
        // 存放待压缩文件的目录
        File srcFile = new File("D:/temp");
        // 压缩后的zip文件路径
        String zipFilePath = "D:/temp/test.zip";
        if (srcFile.exists())
        {
            File[] files = srcFile.listFiles();
            ZipFileUtil.compressFiles2Zip(files, zipFilePath);
        }
    }
    
    @Test
    public void testDecompressZip()
    {
        // 压缩包所在路径
        String zipFilePath = "d:/test2/test.zip";
        // 解压后的文件存放目录
        String saveFileDir = "d:/test2/";
        // 调用解压方法
        ZipFileUtil.decompressZip(zipFilePath, saveFileDir);
    }
}
