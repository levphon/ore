package org.oreframework.commons.image;

public class ImageUtil
{
    /**
     * 判断文件是否为图片文件
     * 
     * @param fileName
     * @return
     */
    public static Boolean isImageFile(String fileName)
    {
        String[] img_type = new String[] {".jpg", ".jpeg", ".png", ".gif", ".bmp"};
        if (fileName == null)
        {
            return false;
        }
        fileName = fileName.toLowerCase();
        for (String type : img_type)
        {
            if (fileName.endsWith(type))
            {
                return true;
            }
        }
        return false;
    }

}
