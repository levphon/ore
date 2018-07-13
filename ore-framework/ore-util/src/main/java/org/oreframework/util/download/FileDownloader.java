/*
 * Copyright Bruce Liang (ldcsaa@gmail.com)
 *
 * Version	: JessMA 3.2.2
 * Author	: Bruce Liang
 * Website	: http://www.jessma.org
 * Porject	: https://code.google.com/p/portal-basic
 * Bolg		: http://www.cnblogs.com/ldcsaa
 * WeiBo	: http://weibo.com/u/1402935851
 * QQ Group	: 75375912
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.oreframework.util.download;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

/** 文件下载器 */
public class FileDownloader
{
    /** 空字符串 */
    public static final String DEFAULT_ENCODING = "UTF-8";
    
    /** 默认字节交换缓冲区大小 */
    public static final int DEFAULT_BUFFER_SIZE = 1024 * 4;
    
    /** 下载文件的默认 Mime Type */
    public static final String DEFAULT_CONTENT_TYPE = "application/force-download";
    
    private static final String WINDOWS_AGENT_CHARSET = "GBK";
    
    private static final String DEFAULT_AGENT_CHARSET = DEFAULT_ENCODING;
    
    private String saveFileName;
    
    private String contentType = DEFAULT_CONTENT_TYPE;
    
    private int bufferSize = DEFAULT_BUFFER_SIZE;
    
    private String filePath;
    
    private byte[] bytes;
    
    private InputStream stream;
    
    private Mode mode;
    
    private Throwable cause;
    
    public FileDownloader()
    {
        
    }
    
    /**
     * 构造函数
     * 
     * @param filePath : 下载文件的路径（包含文件名），可能是相对路径或绝对路径，参考：{@link FileDownloader#setFilePath(String)}
     */
    public FileDownloader(String filePath)
    {
        this(filePath, DEFAULT_CONTENT_TYPE);
    }
    
    /**
     * 构造函数
     * 
     * @param filePath : 下载文件的路径（包含文件名），可能是相对路径或绝对路径，参考：{@link FileDownloader#setFilePath(String)}
     * @param contentType : 下载文件的 Mime Type，默认：{@link FileDownloader#DEFAULT_CONTENT_TYPE}
     */
    public FileDownloader(String filePath, String contentType)
    {
        this(filePath, contentType, new File(filePath).getName());
    }
    
    /**
     * 构造函数
     * 
     * @param filePath : 下载文件的路径（包含文件名），可能是相对路径或绝对路径，参考：{@link FileDownloader#setFilePath(String)}
     * @param contentType : 下载文件的 Mime Type，默认：{@link FileDownloader#DEFAULT_CONTENT_TYPE}
     * @param saveFileName : 显示在浏览器的下载对话框中的文件名称，默认与 filePath 参数中的文件名一致
     */
    public FileDownloader(String filePath, String contentType, String saveFileName)
    {
        this(filePath, contentType, saveFileName, DEFAULT_BUFFER_SIZE);
    }
    
    /**
     * 构造函数
     * 
     * @param filePath : 下载文件的路径（包含文件名），可能是相对路径或绝对路径，参考：{@link FileDownloader#setFilePath(String)}
     * @param contentType : 下载文件的 Mime Type，默认：{@link FileDownloader#DEFAULT_CONTENT_TYPE}
     * @param saveFileName : 显示在浏览器的下载对话框中的文件名称，默认与 filePath 参数中的文件名一致
     * @param bufferSize : 字节缓冲区大小，默认：{@link FileDownloader#DEFAULT_CONTENT_TYPE}
     */
    public FileDownloader(String filePath, String contentType, String saveFileName, int bufferSize)
    {
        this.filePath = filePath;
        this.contentType = contentType;
        this.saveFileName = saveFileName;
        this.bufferSize = bufferSize;
        this.mode = Mode.FILE;
    }
    
    /**
     * 构造函数
     * 
     * @param bytes : 下载内容字节数组
     * @param saveFileName : 显示在浏览器的下载对话框中的文件名称
     */
    public FileDownloader(byte[] bytes, String saveFileName)
    {
        this(bytes, DEFAULT_CONTENT_TYPE, saveFileName);
    }
    
    /**
     * 构造函数
     * 
     * @param bytes : 下载内容字节数组
     * @param contentType : 下载文件的 Mime Type，默认：{@link FileDownloader#DEFAULT_CONTENT_TYPE}
     * @param saveFileName : 显示在浏览器的下载对话框中的文件名称
     */
    public FileDownloader(byte[] bytes, String contentType, String saveFileName)
    {
        this(bytes, contentType, saveFileName, DEFAULT_BUFFER_SIZE);
    }
    
    /**
     * 构造函数
     * 
     * @param bytes : 下载内容字节数组
     * @param contentType : 下载文件的 Mime Type，默认：{@link FileDownloader#DEFAULT_CONTENT_TYPE}
     * @param saveFileName : 显示在浏览器的下载对话框中的文件名称
     * @param bufferSize : 字节缓冲区大小，默认：{@link FileDownloader#DEFAULT_CONTENT_TYPE}
     */
    public FileDownloader(byte[] bytes, String contentType, String saveFileName, int bufferSize)
    {
        this.bytes = bytes;
        this.contentType = contentType;
        this.saveFileName = saveFileName;
        this.bufferSize = bufferSize;
        this.mode = Mode.BYTES;
    }
    
    /**
     * 构造函数
     * 
     * @param is : 下载内容字节流
     * @param saveFileName : 显示在浏览器的下载对话框中的文件名称
     */
    public FileDownloader(InputStream is, String saveFileName)
    {
        this(is, DEFAULT_CONTENT_TYPE, saveFileName);
    }
    
    /**
     * 构造函数
     * 
     * @param is : 下载内容字节流
     * @param contentType : 下载文件的 Mime Type，默认：{@link FileDownloader#DEFAULT_CONTENT_TYPE}
     * @param saveFileName : 显示在浏览器的下载对话框中的文件名称
     */
    public FileDownloader(InputStream is, String contentType, String saveFileName)
    {
        this(is, contentType, saveFileName, DEFAULT_BUFFER_SIZE);
    }
    
    /**
     * 构造函数
     * 
     * @param is : 下载内容字节流
     * @param contentType : 下载文件的 Mime Type，默认：{@link FileDownloader#DEFAULT_CONTENT_TYPE}
     * @param saveFileName : 显示在浏览器的下载对话框中的文件名称
     * @param bufferSize : 字节缓冲区大小，默认：{@link FileDownloader#DEFAULT_CONTENT_TYPE}
     */
    public FileDownloader(InputStream is, String contentType, String saveFileName, int bufferSize)
    {
        this.stream = is;
        this.contentType = contentType;
        this.saveFileName = saveFileName;
        this.bufferSize = bufferSize;
        this.mode = Mode.STREAM;
    }
    
    /** 获取下载文件的路径（包含文件名） */
    public String getFilePath()
    {
        return filePath;
    }
    
    /**
     * 设置下载文件的路径（包含文件名）
     * 
     * @param filePath : 文件路径，可能是绝对路径或相对路径<br>
     *            1) 绝对路径：以根目录符开始（如：'/'、'D:\'），是服务器文件系统的路径<br>
     *            2) 相对路径：不以根目录符开始，是相对于 WEB 应用程序 Context 的路径，（如：mydir/myfile 是指 '${WEB-APP-DIR}/mydir/myfile'）
     * 
     */
    public void setFilePath(String filePath)
    {
        this.filePath = filePath;
        this.mode = Mode.FILE;
    }
    
    /** 获取下载内容字节数组 */
    public byte[] getBytes()
    {
        return bytes;
    }
    
    /** 设置下载内容字节数组 */
    public void setBytes(byte[] bytes)
    {
        this.bytes = bytes;
        this.mode = Mode.BYTES;
    }
    
    /** 获取下载内容字节流 */
    public InputStream getStream()
    {
        return stream;
    }
    
    /** 设置下载内容字节流 */
    public void setStream(InputStream stream)
    {
        this.stream = stream;
        this.mode = Mode.STREAM;
    }
    
    /** 获取当前下载模式，参考：{@link Mode} */
    public Mode getMode()
    {
        return mode;
    }
    
    /** 获取下载文件的 Mime Type */
    public String getContentType()
    {
        return contentType;
    }
    
    /** 设置下载文件的 Mime Type */
    public void setContentType(String contentType)
    {
        this.contentType = contentType;
    }
    
    /** 获取显示在浏览器的下载对话框中的文件名称 */
    public String getSaveFileName()
    {
        return saveFileName;
    }
    
    /** 设置显示在浏览器的下载对话框中的文件名称 */
    public void setSaveFileName(String saveFileName)
    {
        this.saveFileName = saveFileName;
    }
    
    /** 获取字节交换缓冲区的大小 */
    public int getBufferSize()
    {
        return bufferSize;
    }
    
    /** 设置字节交换缓冲区的大小 */
    public void setBufferSize(int bufferSize)
    {
        this.bufferSize = bufferSize;
    }
    
    /** 获取文件下载失败的原因（文件下载失败时使用） */
    public Throwable getCause()
    {
        return cause;
    }
    
    private void reset()
    {
        cause = null;
    }
    
    /**
     * 执行下载
     * 
     * @param request : {@link HttpServletRequest} 对象
     * @param response : {@link HttpServletResponse} 对象
     * 
     * @return : 成功：返回 {@link Result#SUCCESS} ，失败：返回其他结果， 失败原因通过 {@link FileDownloader#getCause()} 获取
     * 
     */
    public Result download(HttpServletRequest request, HttpServletResponse response)
    {
        reset();
        
        try
        {
            if (mode == null)
                throw new IllegalStateException("can not distinguish download mode");
            
            switch (mode)
            {
                case FILE:
                    File file = getFile(request);
                    downloadFile(request, response, file);
                    break;
                case BYTES:
                    checkBytes();
                    downloadBytes(request, response);
                    break;
                case STREAM:
                    checkStream();
                    downloadStream(request, response);
                    break;
            }
        }
        catch (Exception e)
        {
            cause = e;
            
            if (e instanceof FileNotFoundException)
                return Result.FILE_NOT_FOUND;
            if (e instanceof IOException)
                return Result.READ_WRITE_FAIL;
            if (e instanceof IllegalStateException)
                return Result.ILLEGAL_STATE;
            if (e instanceof IllegalArgumentException)
                return Result.ILLEGAL_ARG;
            return Result.UNKNOWN_EXCEPTION;
        }
        
        return Result.SUCCESS;
    }
    
    private void downloadFile(HttpServletRequest request, HttpServletResponse response, File file)
        throws IOException
    {
        int length = (int)file.length();
        Range<Integer> range = prepareDownload(request, response, length);
        InputStream is = new BufferedInputStream(new FileInputStream(file));
        
        doDownload(response, is, range);
    }
    
    private void downloadBytes(HttpServletRequest request, HttpServletResponse response)
        throws IOException
    {
        int length = bytes.length;
        Range<Integer> range = prepareDownload(request, response, length);
        InputStream is = new BufferedInputStream(new ByteArrayInputStream(bytes));
        
        doDownload(response, is, range);
    }
    
    private void downloadStream(HttpServletRequest request, HttpServletResponse response)
        throws IOException
    {
        int length = stream.available();
        Range<Integer> range = prepareDownload(request, response, length);
        InputStream is = new BufferedInputStream(stream);
        
        doDownload(response, is, range);
    }
    
    private Range<Integer> prepareDownload(HttpServletRequest request, HttpServletResponse response, int length)
        throws UnsupportedEncodingException
    {
        String charset = isRequestNotComeFromWidnows(request) ? DEFAULT_AGENT_CHARSET : WINDOWS_AGENT_CHARSET;
        String fileName = new String(saveFileName.getBytes(charset), "ISO-8859-1");
        Range<Integer> range = parseDownloadRange(request);
        int begin = 0;
        int end = length - 1;
        
        response.setContentType(contentType);
        response.setContentLength(length);
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        
        if (range != null)
        {
            if (range.getBegin() != null)
            {
                begin = range.getBegin();
                if (range.getEnd() != null)
                    end = range.getEnd();
            }
            else
            {
                if (range.getEnd() != null)
                    begin = Math.max(end + range.getEnd() + 1, 0);
            }
            
            String contentRange = String.format("bytes %d-%d/%d", begin, end, length);
            response.setHeader("Accept-Ranges", "bytes");
            response.setHeader("Content-Range", contentRange);
            response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
        }
        
        return new Range<Integer>(begin, end);
    }
    
    private static Range<Integer> parseDownloadRange(HttpServletRequest request)
    {
        Range<Integer> range = null;
        String header = request.getHeader("Range");
        
        if (header != null)
        {
            int preIndex = header.indexOf('=');
            if (preIndex != -1)
            {
                header = header.substring(preIndex + 1).trim();
                int length = header.length();
                
                if (length > 0)
                {
                    int sepIndex = header.indexOf('-');
                    range = new Range<Integer>();
                    
                    if (sepIndex >= 0)
                    {
                        String str = header.substring(0, sepIndex);
                        int begin = str2Int(str, -1);
                        
                        if (begin != -1)
                            range.setBegin(begin);
                        
                        if (sepIndex < length - 1)
                        {
                            str = header.substring(sepIndex + 1, length);
                            int end = str2Int(str, -1);
                            
                            if (end != -1)
                                range.setEnd((begin != -1) ? end : -end);
                        }
                    }
                    else
                    {
                        int point = str2Int(header, -1);
                        
                        if (point != -1)
                        {
                            range.setBegin(point);
                            range.setEnd(point);
                        }
                    }
                }
            }
        }
        
        return range;
    }
    
    private void doDownload(HttpServletResponse response, InputStream is, Range<Integer> range)
        throws IOException
    {
        int begin = range.getBegin();
        int end = range.getEnd();
        
        OutputStream os = null;
        
        try
        {
            byte[] b = new byte[bufferSize];
            os = new BufferedOutputStream(response.getOutputStream());
            
            is.skip(begin);
            for (int i, left = end - begin + 1; left > 0
                && ((i = is.read(b, 0, Math.min(b.length, left))) != -1); left -= i)
                os.write(b, 0, i);
            
            os.flush();
        }
        finally
        {
            if (is != null)
            {
                try
                {
                    is.close();
                }
                catch (IOException e)
                {
                }
            }
            if (os != null)
            {
                try
                {
                    os.close();
                }
                catch (IOException e)
                {
                }
            }
        }
    }
    
    private File getFile(HttpServletRequest request)
        throws FileNotFoundException
    {
        File file = new File(filePath);
        
        if (!file.isAbsolute())
            file = new File(getRequestRealPath(request, filePath));
        if (!file.isFile())
            throw new FileNotFoundException(String.format("file '%s' not found or is directory", filePath));
        
        checkDownloadParam(file.getName());
        
        return file;
    }
    
    private void checkBytes()
        throws FileNotFoundException, IllegalArgumentException
    {
        if (bytes == null)
            throw new FileNotFoundException("input byte array is null");
        
        checkDownloadParam(null);
    }
    
    private void checkStream()
        throws FileNotFoundException, IllegalArgumentException
    {
        if (stream == null)
            throw new FileNotFoundException("input stream is null");
        
        checkDownloadParam(null);
    }
    
    private void checkDownloadParam(String defSaveFileName)
        throws IllegalArgumentException
    {
        if (saveFileName == null)
        {
            if (defSaveFileName != null)
                saveFileName = defSaveFileName;
            else
                throw new IllegalArgumentException("save file name is null");
        }
        
        if (contentType == null)
            contentType = DEFAULT_CONTENT_TYPE;
        if (bufferSize <= 0)
            bufferSize = DEFAULT_BUFFER_SIZE;
    }
    
    /** 检查请求是否来自非 Windows 系统的浏览器 */
    private boolean isRequestNotComeFromWidnows(HttpServletRequest request)
    {
        String agent = request.getHeader("user-agent");
        
        if (StringUtils.isNotEmpty(agent))
            return agent.toLowerCase().indexOf("windows") == -1;
        
        return false;
    }
    
    /** String -> Integer，如果转换不成功则返回 null */
    private Integer str2Int(String s)
    {
        Integer returnVal;
        try
        {
            returnVal = Integer.decode(safeTrimString(s));
        }
        catch (Exception e)
        {
            returnVal = null;
        }
        return returnVal;
    }
    
    /** 把参数 str 转换为安全字符串并执行去除前后空格：如果 str = null，则把它转换为空字符串 */
    private static String safeTrimString(String str)
    {
        return safeString(str).trim();
    }
    
    /** 把参数 str 转换为安全字符串：如果 str = null，则把它转换为空字符串 */
    private static String safeString(String str)
    {
        if (str == null)
            str = "";
        
        return str;
    }
    
    /** String -> int，如果转换不成功则返回默认值 d */
    private static int str2Int(String s, int d)
    {
        int returnVal;
        try
        {
            returnVal = Integer.parseInt(safeTrimString(s));
        }
        catch (Exception e)
        {
            returnVal = d;
        }
        return returnVal;
    }
    
    /**
     * 获取 URL 地址在文件系统的绝对路径,
     * 
     * Servlet 2.4 以上通过 request.getServletContext().getRealPath() 获取, Servlet 2.4 以下通过 request.getRealPath() 获取。
     * 
     */
    @SuppressWarnings("deprecation")
    private static final String getRequestRealPath(HttpServletRequest request, String path)
    {
        try
        {
            Method m = request.getClass().getMethod("getServletContext");
            ServletContext sc = (ServletContext)m.invoke(request);
            return sc.getRealPath(path);
        }
        catch (Exception e)
        {
            return request.getRealPath(path);
        }
    }
    
    /** 下载结果 */
    public static enum Result
    {
        /** 成功 */
        SUCCESS,
        /** 失败：非法状态 */
        ILLEGAL_STATE,
        /** 失败：非法参数 */
        ILLEGAL_ARG,
        /** 失败：文件不存在 */
        FILE_NOT_FOUND,
        /** 失败：读写操作失败 */
        READ_WRITE_FAIL,
        /** 失败：未知异常 */
        UNKNOWN_EXCEPTION;
    }
    
    /** 下载模式 */
    public static enum Mode
    {
        /** 物理文件 */
        FILE,
        /** 字节数组 */
        BYTES,
        /** 输入流 */
        STREAM;
    }
    
    /** 数值范围类 */
    public static class Range<T extends Number>
    {
        private T begin;
        
        private T end;
        
        public Range()
        {
            
        }
        
        public Range(T begin, T end)
        {
            this.begin = begin;
            this.end = end;
        }
        
        public T getBegin()
        {
            return begin;
        }
        
        public void setBegin(T begin)
        {
            this.begin = begin;
        }
        
        public T getEnd()
        {
            return end;
        }
        
        public void setEnd(T end)
        {
            this.end = end;
        }
        
        @Override
        public String toString()
        {
            return String.format("{%s - %s}", begin, end);
        }
    }
    
}
