package com.fh.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * <p>
 * Title: 文件处理工具类
 * </p>
 * <p>
 * Description:实现文件的简单处理,复制文件、目录等
 * </p>
 * <p>
 * Copyright: Copyright (c) 2009
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author gjf
 * @version 1.0
 */
@SuppressWarnings("unchecked")
public class FileUtils {

	private static Logger logger = LoggerFactory.getLogger(FileUtils.class);
	// "db/dashboardconfig.properties"
	public static final String[] PROPERTIES_FILE_PATHNAMES = { "application.properties", "apps.xml", "generator.properties", "generator.xml" };
	/**
	 * 复制目录下的文件（不包括该目录）到指定目录，会连同子目录一起复制过去。
	 * 
	 * @param targetFile
	 * @param path
	 * @throws IOException
	 */
	public static void copyFileFromDir(String targetDir, String path)
			throws IOException {
		File file = new File(path);
		createFile(targetDir, false);
		if (file.isDirectory()) {
			copyFileToDir(targetDir, listFile(file));
		}
	}

	/**
	 * 复制目录下的文件（不包含该目录和子目录，只复制目录下的文件）到指定目录。
	 * 
	 * @param targetDir
	 * @param path
	 * @throws IOException
	 */
	public static void copyFileOnly(String targetDir, String path)
			throws IOException {
		File file = new File(path);
		File targetFile = new File(targetDir);
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (File subFile : files) {
				if (subFile.isFile()) {
					copyFile(targetFile, subFile);
				}
			}
		}
	}

	/**
	 * 复制目录到指定目录。targetDir是目标目录，path是源目录。 该方法会将path以及path下的文件和子目录全部复制到目标目录
	 * 
	 * @param targetDir
	 * @param path
	 * @throws IOException
	 */
	public static void copyDir(String targetDir, String path)
			throws IOException {
		File targetFile = new File(targetDir);
		createFile(targetFile, false);
		File file = new File(path);
		if (targetFile.isDirectory() && file.isDirectory()) {
			copyFileToDir(targetFile.getAbsolutePath() + "/" + file.getName(),
					listFile(file));
		}
	}

	/**
	 * 复制一组文件到指定目录。targetDir是目标目录，filePath是需要复制的文件路径
	 * 
	 * @param targetDir
	 * @param filePath
	 * @throws IOException
	 */
	public static void copyFileToDir(String targetDir, String... filePath)
			throws IOException {
		if (targetDir == null || "".equals(targetDir)) {
			System.out.println("参数错误，目标路径不能为空");
			return;
		}
		File targetFile = new File(targetDir);
		if (!targetFile.exists()) {
			targetFile.mkdir();
		} else {
			if (!targetFile.isDirectory()) {
				System.out.println("参数错误，目标路径指向的不是一个目录！");
				return;
			}
		}
		for (String path : filePath) {
			File file = new File(path);
			if (file.isDirectory()) {
				copyFileToDir(targetDir + "/" + file.getName(), listFile(file));
			} else {
				copyFileToDir(targetDir, file, "");
			}
		}
	}

	/**
	 * 复制文件到指定目录。targetDir是目标目录，file是源文件名，newName是重命名的名字。
	 * 
	 * @param targetFile
	 * @param file
	 * @param newName
	 * @throws IOException
	 */
	public static void copyFileToDir(String targetDir, File file, String newName)
			throws IOException {
		String newFile = "";
		if (newName != null && !"".equals(newName)) {
			newFile = targetDir + "/" + newName;
		} else {
			newFile = targetDir + "/" + file.getName();
		}
		File tFile = new File(newFile);
		copyFile(tFile, file);
	}

	/**
	 * 拷贝文件
	 * 
	 * @param oldPath
	 *            源文件
	 * @param newPath
	 *            目标文件
	 */
	public static void copyFile(String oldPath, String newPath) {
		try {
			File temp = new File(oldPath);
			FileInputStream input = new FileInputStream(temp);
			FileOutputStream output = new FileOutputStream(newPath + "/"
					+ (temp.getName()).toString());
			byte[] b = new byte[1024 * 5];
			int len;
			while ((len = input.read(b)) != -1) {
				output.write(b, 0, len);
			}
			output.flush();
			output.close();
			input.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取文件下 所以的文件名称
	 * @return
	 */
	 public static List getFileName(String filepath) throws Exception {
	        File f = new File(filepath);
	        if (!f.exists()) {
	            throw new Exception("传入目录不是一个文件！");                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
	           // return null;
	        } 
	        List<String> filenameList= new ArrayList<String>();
	        File fa[] = f.listFiles();
	        for (int i = 0; i < fa.length; i++) {
	            File fs = fa[i];
	            if (fs.isDirectory()) {
	              //  System.out.println(fs.getName() + " [目录]");
	            } else {
	              //  System.out.println(fs.getName());
	                String rootPath = fs.getParent();
	               // System.out.println("根路径是："+rootPath);
	                
	                

	             /*   File newFile =new File(rootPath+ File.separator 
                            + fs.getName().substring(0,fs.getName().lastIndexOf("."))
                            + "_"
                            + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+i
                            + fs.getName().substring(fs.getName().lastIndexOf("."),fs.getName().length())
                            );  */
	                filenameList.add(fs.getName());
	                /*//修改文件名  
	                if (fs.renameTo(newFile)) {  
	                   // System.out.println("File has been renamed.");  
	                    filenameList.add(newFile.getName());
	                } else {  
	                   // System.out.println("Error renmaing file");  
	                    filenameList.add(fs.getName());
	                }  */
	                
	            }
	        }
	        return filenameList;
	    }
	public static String[] listFile(File dir) {
		String absolutPath = dir.getAbsolutePath();
		String[] paths = dir.list();
		String[] files = new String[paths.length];
		for (int i = 0; i < paths.length; i++) {
			files[i] = absolutPath + "/" + paths[i];
		}
		return files;
	}

	public static void createFile(String path, boolean isFile) {
		createFile(new File(path), isFile);
	}

	public static void createFile(File file, boolean isFile) {
		if (!file.exists()) {
			if (!file.getParentFile().exists()) {
				createFile(file.getParentFile(), false);
			} else {
				if (isFile) {
					try {
						file.createNewFile();
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					file.mkdir();
				}
			}
		}
	}

	/**
	 * 将指定目录下的所有文件压缩并生成指定路径的压缩文件. 如果压缩文件的路径或父路径不存在, 将会自动创建.
	 * 
	 * @param src
	 *            将要进行压缩的目录
	 * @param zip
	 *            最终生成的压缩文件的路径
	 */
	public static void zip(File src, File zip) throws IOException {
		zip(src, org.apache.commons.io.FileUtils.openOutputStream(zip));
	}

	/**
	 * 将指定目录下的所有文件压缩并将流写入指定的输出流中.
	 * 
	 * @param src
	 *            将要进行压缩的目录
	 * @param out
	 *            用于接收压缩产生的文件流的输出流
	 */
	public static void zip(File src, OutputStream out) throws IOException {
		zip(src, new ZipOutputStream(out));
	}

	/**
	 * 将指定目录下的所有文件压缩并将流写入指定的ZIP输出流中.
	 * 
	 * @param src
	 *            将要进行压缩的目录
	 * @param zout
	 *            用于接收压缩产生的文件流的ZIP输出流
	 */
	public static void zip(File src, ZipOutputStream zout) throws IOException {
		try {
			doZip(src, zout, "");
		} finally {
			IOUtils.closeQuietly(zout);
		}
	}

	/**
	 * 将指定的压缩文件解压到指定的目标目录下. 如果指定的目标目录不存在或其父路径不存在, 将会自动创建.
	 * 
	 * @param zip
	 *            将会解压的压缩文件
	 * @param dest
	 *            解压操作的目录目录
	 */
	public static void unzip(File zip, File dest) throws IOException {
		unzip(org.apache.commons.io.FileUtils.openInputStream(zip), dest);
	}

	/**
	 * 将指定的输入流解压到指定的目标目录下.
	 * 
	 * @param in
	 *            将要解压的输入流
	 * @param dest
	 *            解压操作的目标目录
	 */
	public static void unzip(InputStream in, File dest) throws IOException {
		unzip(new ZipInputStream(in), dest);
	}

	/**
	 * 将指定的ZIP输入流解压到指定的目标目录下.
	 * 
	 * @param zin
	 *            将要解压的ZIP输入流
	 * @param dest
	 *            解压操作的目标目录
	 */
	public static void unzip(ZipInputStream zin, File dest) throws IOException {
		try {
			doUnzip(zin, dest);
		} finally {
			IOUtils.closeQuietly(zin);
		}
	}

	/**
	 * @param src
	 * @param zout
	 * @param ns
	 */
	private static void doZip(File src, ZipOutputStream zout, String ns)
			throws IOException {
		for (File file : src.listFiles()) {
			String entryName = ns + file.getName();

			if (file.isDirectory()) {
				logger.debug("adding: {}", entryName += "/");
				zout.putNextEntry(new ZipEntry(entryName));
				doZip(file, zout, entryName);
			} else {
				logger.debug("adding: {}", entryName);
				zout.putNextEntry(new ZipEntry(entryName));
				fillZip(org.apache.commons.io.FileUtils.openInputStream(file),
						zout);
			}
		}
	}

	/**
	 * @param zin
	 * @param dest
	 */
	private static void doUnzip(ZipInputStream zin, File dest)
			throws IOException {
		for (ZipEntry e; (e = zin.getNextEntry()) != null; zin.closeEntry()) {
			File file = new File(dest, e.getName());

			if (e.isDirectory()) {
				logger.debug(" creating: {}", file + File.separator);
				org.apache.commons.io.FileUtils.forceMkdir(file);
			} else {
				logger.debug("inflating: {}", file);
				flushZip(zin, org.apache.commons.io.FileUtils
						.openOutputStream(file));
			}
		}
	}

	/**
	 * @param in
	 * @param zout
	 */
	private static void fillZip(InputStream in, ZipOutputStream zout)
			throws IOException {
		try {
			IOUtils.copy(in, zout);
		} finally {
			IOUtils.closeQuietly(in);
		}
	}

	/**
	 * @param zin
	 * @param out
	 */
	private static void flushZip(ZipInputStream zin, OutputStream out)
			throws IOException {
		try {
			IOUtils.copy(zin, out);
		} finally {
			IOUtils.closeQuietly(out);
		}
	}

	// ==========================================================================
	/*
	 * 读取properties的全部信息
	 */
	public static void readProperties(String fileNamePath) throws IOException {
		Properties props = new Properties();
		InputStream in = null;
		try {
			in = new BufferedInputStream(new FileInputStream(fileNamePath));
			// 如果将in改为下面的方法，必须要将.Properties文件和此class类文件放在同一个包中
			// in = propertiesTools.class.getResourceAsStream(fileNamePath);
			props.load(in);
			Enumeration en = props.propertyNames();
			while (en.hasMoreElements()) {
				String key = (String) en.nextElement();
				String Property = props.getProperty(key);
				System.out.println(key + "=" + Property);
			}
		} catch (Exception e) {
			if (null != in)
				in.close();
			e.printStackTrace();
		} finally {
			if (null != in)
				in.close();
		}
	}

	/*
	 * 根据key读取value
	 */
	public static String getValue(String fileNamePath, String key)
			throws IOException {
		Properties props = new Properties();
		InputStream in = null;
		try {
			in = new FileInputStream(fileNamePath);
			// 如果将in改为下面的方法，必须要将.Properties文件和此class类文件放在同一个包中
			// in = propertiesTools.class.getResourceAsStream(fileNamePath);
			props.load(in);
			String value = props.getProperty(key);
			// 有乱码时要进行重新编码
			// new String(props.getProperty("name").getBytes("ISO-8859-1"),
			// "GBK");
			return value;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			if (null != in)
				in.close();
		}

	}

	/*
	 * 写入properties信息
	 */
	public static void setProperties(String fileNamePath, String parameterName,
			String parameterValue) throws IOException {
		Properties prop = new Properties();
		InputStream in = null;
		OutputStream out = null;
		try {
			in = new FileInputStream(fileNamePath);
			// 如果将in改为下面的方法，必须要将.Properties文件和此class类文件放在同一个包中
			// in = propertiesTools.class.getResourceAsStream(fileNamePath);
			prop.load(in);
			out = new FileOutputStream(fileNamePath);
			prop.setProperty(parameterName, parameterValue);
			prop.store(out, parameterName);// 保存
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != in)
				in.close();
			if (null != out)
				out.close();
		}
	}

	private static final int DEFAULT_BUFFER_SIZE = 100000;
	private static String ENCODING = "UTF-8";

	public FileUtils() {
	}

	/**
	 * 将字节流转换成字符串返回
	 * 
	 * @param is
	 *            输入流
	 * @return 字符串
	 */
	public static String readFileByLines(InputStream is) {
		BufferedReader reader = null;
		StringBuffer sb = new StringBuffer();
		try {
			reader = new BufferedReader(new InputStreamReader(is));
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				sb.append(tempString + "\n");
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		return sb.toString();
	}

	/**
	 * 将文件一行一行的读成List返回
	 * 
	 * @param file
	 *            需要读取的文件
	 * @return 文件的一行就是一个List的Item的返回
	 */
	public static List<String> readFileToList(File file) {
		BufferedReader reader = null;
		List<String> list = new ArrayList<String>();
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				list.add(tempString);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		return list;
	}

	/**
	 * 将文件按照一定的编码方式一行一行的读成List返回
	 * 
	 * @param file
	 *            需要读取的文件
	 * @param encodType
	 *            字符编码
	 * @return 文件的一行就是一个List的Item的返回
	 */
	public static List<String> readFileToList(File file, String encodType) {
		BufferedReader reader = null;
		List<String> list = new ArrayList<String>();
		try {
			reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(file), encodType));
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				if (!(tempString.charAt(0) >= 'a' && tempString.charAt(0) <= 'z'))
					tempString = tempString.substring(1);
				list.add(tempString);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		return list;
	}

	/**
	 * 将指定的字符串内容以指定的方式写入到指定的文件中
	 * 
	 * @param file
	 *            需要写人的文件
	 * @param content
	 *            需要写入的内容
	 * @param flag
	 *            是否追加写入
	 */
	public static void writeFile(File file, String content, Boolean flag) {
		try {
			if (!file.exists())
				file.createNewFile();
			FileWriter writer = new FileWriter(file, flag);
			writer.write(content);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 将指定的字符串内容以指定的方式及编码写入到指定的文件中
	 * 
	 * @param file
	 *            需要写人的文件
	 * @param content
	 *            需要写入的内容
	 * @param flag
	 *            是否追加写入
	 * @param encodType
	 *            文件编码
	 */
	public static void writeFile(File file, String content, Boolean flag,
			String encodType) {
		try {
			FileOutputStream writerStream = new FileOutputStream(file, flag);
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
					writerStream, encodType));
			writer.write(content);
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 拷贝文件夹
	 * 
	 * @param oldPath
	 *            源目录
	 * @param newPath
	 *            目标目录
	 */
	public static void copyFolder(String oldPath, String newPath) {
		try {
			(new File(newPath)).mkdirs();
			File a = new File(oldPath);
			String[] file = a.list();
			File temp = null;
			for (int i = 0; i < file.length; i++) {
				if (oldPath.endsWith(File.separator)) {
					temp = new File(oldPath + file[i]);
				} else {
					temp = new File(oldPath + File.separator + file[i]);
				}
				if (temp.isFile()) {
					FileInputStream input = new FileInputStream(temp);
					FileOutputStream output = new FileOutputStream(newPath
							+ "/" + (temp.getName()).toString());
					byte[] b = new byte[1024 * 5];
					int len;
					while ((len = input.read(b)) != -1) {
						output.write(b, 0, len);
					}
					output.flush();
					output.close();
					input.close();
				}
				if (temp.isDirectory()) {
					copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 将文件重命名
	 * 
	 * @param oldName
	 *            源文件名
	 * @param newName
	 *            新文件名
	 */
	public static void reName(String oldName, String newName) {
		File oldF = new File(oldName);
		File newF = new File(newName);
		oldF.renameTo(newF);
	}

	/**
	 * 将一个文件列表文件中所有文件拷贝到指定目录中
	 * 
	 * @param listFile
	 *            包含需要拷贝的文件的列表的文件，每个文件写在一行
	 * @param targetFloder
	 *            目标目录
	 */
	public static void copyFilesFromList(String listFile, String targetFloder) {
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(listFile));
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				copyFile(tempString, targetFloder);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
	}

	/**
	 * 删除文件列表
	 * 
	 * @param files
	 *            需要删除的文件/文件夹列表
	 * @return 删除成功true，否则返回false
	 */
	public static boolean deleteFiles(List<String> files) {
		boolean flag = true;
		for (String file : files) {
			flag = delete(file);
			if (!flag)
				break;
		}
		return flag;
	}

	/**
	 * 删除文件或文件夹
	 * 
	 * @param fileName
	 *            要删除的文件名
	 * @return 删除成功返回true，否则返回false
	 */
	public static boolean delete(String fileName) {
		File file = new File(fileName);
		if (!file.exists()) {
			return false;
		} else {
			if (file.isFile())
				return deleteFile(fileName);
			else
				return deleteDirectory(fileName);
		}
	}

	/**
	 * 删除文件
	 * 
	 * @param fileName
	 *            要删除的文件的文件名
	 * @return 删除成功返回true，否则返回false
	 */
	public static boolean deleteFile(String fileName) {
		File file = new File(fileName);
		if (file.exists() && file.isFile())
			return file.delete();
		return false;
	}

	/**
	 * 删除目录及目录下的文件
	 * 
	 * @param dir
	 *            要删除的目录路径
	 * @return 删除成功返回true，否则返回false
	 */
	public static boolean deleteDirectory(String dir) {
		if (!dir.endsWith(File.separator))
			dir = dir + File.separator;
		File dirFile = new File(dir);
		if ((!dirFile.exists()) || (!dirFile.isDirectory()))
			return false;
		boolean flag = true;
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile()) {
				flag = deleteFile(files[i].getAbsolutePath());
				if (!flag)
					break;
			} else if (files[i].isDirectory()) {
				flag = deleteDirectory(files[i].getAbsolutePath());
				if (!flag)
					break;
			}
		}
		if (!flag) {
			return false;
		}
		return dirFile.delete();
	}

	/**
	 * 将字符串写入指定文件(当指定的父路径中文件夹不存在时，会最大限度去创建，以保证保存成功！)
	 * 
	 * @param res
	 *            原字符串
	 * @param filePath
	 *            文件路径
	 * @return 成功标记
	 */
	public static boolean string2File(String res, String filePath) {
		boolean flag = true;
		BufferedReader bufferedReader = null;
		BufferedWriter bufferedWriter = null;
		try {
			File distFile = new File(filePath);
			if (!distFile.getParentFile().exists())
				distFile.getParentFile().mkdirs();
			bufferedReader = new BufferedReader(new StringReader(res));
			bufferedWriter = new BufferedWriter(new FileWriter(distFile));
			char buf[] = new char[1024]; // 字符缓冲区
			int len;
			while ((len = bufferedReader.read(buf)) != -1) {
				bufferedWriter.write(buf, 0, len);
			}
			bufferedWriter.flush();
			bufferedReader.close();
			bufferedWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
			flag = false;
			return flag;
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return flag;
	}

	/**
	 * 文本文件转换为指定编码的字符串
	 * 
	 * @param file
	 *            文本文件
	 * @param encoding
	 *            编码类型
	 * @return 转换后的字符串
	 * @throws IOException
	 */
	public static String file2String(File file, String encoding) {
		InputStreamReader reader = null;
		StringWriter writer = new StringWriter();
		try {
			if (encoding == null || "".equals(encoding.trim())) {
				reader = new InputStreamReader(new FileInputStream(file),
						encoding);
			} else {
				reader = new InputStreamReader(new FileInputStream(file));
			}
			// 将输入流写入输出流
			char[] buffer = new char[DEFAULT_BUFFER_SIZE];
			int n = 0;
			while (-1 != (n = reader.read(buffer))) {
				writer.write(buffer, 0, n);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (reader != null)
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		return writer.toString();
	}

	static final int BUFFER = 8 * 1024;

	public static String getStringFromInputStream(final InputStream inputStream)
			throws IOException {
		return getStringFromReader(new InputStreamReader(inputStream));
	}

	public static String getStringFromInputStream(
			final InputStream inputStream, final String charsetName)
			throws IOException {
		return getStringFromReader(new InputStreamReader(inputStream,
				charsetName));
	}

	public static String getStringFromReader(final Reader reader)
			throws IOException {
		BufferedReader br = null;
		try {
			br = new BufferedReader(reader);
			final StringWriter sw = new StringWriter();
			final PrintWriter writer = new PrintWriter(sw);
			String s;
			while ((s = br.readLine()) != null) {
				writer.println(s);
			}
			writer.flush();
			return sw.toString();
		} finally {
			if (br != null) {
				br.close();
			}
		}
	}

	public static String[] getStringsFromReader(final Reader reader)
			throws IOException {
		BufferedReader br = null;
		try {
			br = new BufferedReader(reader);
			final List<String> l = new ArrayList<String>();
			String s;
			while ((s = br.readLine()) != null) {
				l.add(s);
			}
			return l.toArray(new String[l.size()]);
		} finally {
			if (br != null) {
				br.close();
			}
		}
	}

	public static void unzip(final InputStream in, final String target)
			throws IOException {
		unzip(in, target, true);
	}

	public static void unzip(final InputStream in, final String target,
			final boolean rewrite) throws IOException {
		unzip(in, target, rewrite, new IUnZipHandle() {
			public void doFile(final ZipInputStream is, final File destFile)
					throws IOException {
				final BufferedOutputStream oStream = new BufferedOutputStream(
						new FileOutputStream(destFile));
				try {
					copyStream(is, oStream);
				} finally {
					if (oStream != null) {
						oStream.close();
					}
				}
			}
		});
	}

	public static void unzip(final InputStream in, String target,
			final boolean rewrite, final IUnZipHandle unzipHandle)
			throws IOException {
		if (target.charAt(target.length() - 1) != File.separatorChar) {
			target += File.separatorChar;
		}
		ZipInputStream is;
		if (in instanceof ZipInputStream) {
			is = (ZipInputStream) in;
		} else {
			is = new ZipInputStream(new BufferedInputStream(in));
		}
		try {
			ZipEntry entry;
			while ((entry = is.getNextEntry()) != null) {
				final String entryName = entry.getName();
				final int index = entryName.lastIndexOf("/");
				if (index > 0) {
					createDirectoryRecursively(new File(target
							+ entryName.substring(0, index)));
				}
				if (entry.isDirectory()) {
					continue;
				}

				final File destFile = new File(target + entryName);
				if (rewrite || !destFile.exists()) {
					unzipHandle.doFile(is, destFile);
				}
			}
		} finally {
			if (is != null) {
				in.close();
			}
		}
	}

	public static interface IUnZipHandle {

		void doFile(ZipInputStream is, File destFile) throws IOException;
	}

	public static boolean createDirectoryRecursively(File directory) {
		if (directory == null) {
			return false;
		} else if (directory.exists()) {
			return !directory.isFile();
		} else if (!directory.isAbsolute()) {
			directory = new File(directory.getAbsolutePath());
		}
		final String parent = directory.getParent();
		if ((parent == null) || !createDirectoryRecursively(new File(parent))) {
			return false;
		}
		directory.mkdir();
		return directory.exists();
	}

	public static File createFile(final File file) throws IOException {
		if (!file.exists()) {
			createDirectoryRecursively(file.getParentFile());
			file.createNewFile();
		}
		return file;
	}

	public static String readFile(String input) throws Exception {
		char[] buffer = new char[4096];
		int len = 0;
		StringBuffer content = new StringBuffer(4096);

		if (ENCODING == null)
			ENCODING = "UTF-8";
		InputStreamReader fr = null;
		BufferedReader br = null;
		try {
			fr = new InputStreamReader(new FileInputStream(input), ENCODING);
			br = new BufferedReader(fr);
			while ((len = br.read(buffer)) > -1) {
				content.append(buffer, 0, len);
			}
		} catch (Exception e) {
			throw new Exception(e);
		} finally {
			if (br != null)
				br.close();
			if (fr != null)
				fr.close();
		}
		return content.toString();
	}

	public static void copyFile(final File from, final File to)
			throws IOException {
		final InputStream inputStream = new BufferedInputStream(
				new FileInputStream(from));
		copyFile(inputStream, to);
	}

	public static void copyFile(final InputStream inputStream, final File to)
			throws IOException {
		createFile(to);
		OutputStream outputStream = null;
		try {
			copyStream(inputStream, outputStream = new BufferedOutputStream(
					new FileOutputStream(to)));
		} finally {
			inputStream.close();
			if (outputStream != null) {
				outputStream.close();
			}
		}
	}

	public static String toFileSize(final long size) {
		if (size < 0) {
			return "";
		} else if (size > 1024 * 1024) {
			final double d = (double) size / (double) (1024 * 1024);
			return BeanUtils.toString(d) + " MB";
		} else if (size > 1024) {
			final double d = (double) size / (double) 1024;
			return BeanUtils.toString(d) + " KB";
		} else {
			return BeanUtils.toString(size) + " B";
		}
	}

	public static long sizeOfDirectory(final File directory) {
		return FileUtils.sizeOfDirectory(directory);
	}

	public static int copyStream(final InputStream inputStream,
			final OutputStream outputStream) throws IOException {
		int result = 0;
		final byte[] buf = new byte[BUFFER];
		for (;;) {
			final int numRead = inputStream.read(buf);
			if (numRead == -1) {
				break;
			}
			outputStream.write(buf, 0, numRead);
			result += numRead;
		}
		outputStream.flush();
		return result;
	}

	public static byte[] getMacAddressBytes() throws IOException {
		final NetworkInterface ni = NetworkInterface
				.getByInetAddress(InetAddress.getLocalHost());
		return ni.getHardwareAddress();
	}

	// ===============================================================//

	/**
	 * 
	 * 压缩文件
	 * 
	 * @param inputFileName
	 *            要压缩的文件或文件夹路径，例如：c:\\a.txt,c:\\a\
	 * 
	 * @param outputFileName
	 *            输出zip文件的路径，例如：c:\\a.zip
	 * 
	 */

	public static void zip(String inputFileName, String outputFileName)
			throws Exception {

		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(
				outputFileName));

		zip(out, new File(inputFileName), "");

		System.out.println("压缩完成！");

		out.closeEntry();

		out.close();

	}

	/**
	 * 
	 * 压缩文件
	 * 
	 * @param out
	 *            org.apache.tools.zip.ZipOutputStream
	 * 
	 * @param file
	 *            待压缩的文件
	 * 
	 * @param base
	 *            压缩的根目录
	 * 
	 */

	private static void zip(ZipOutputStream out, File file, String base)
			throws Exception {

		if (file.isDirectory()) {

			File[] fl = file.listFiles();

			base = base.length() == 0 ? "" : base + File.separator;

			for (int i = 0; i < fl.length; i++) {

				zip(out, fl[i], base + fl[i].getName());

			}

		} else {

			out.putNextEntry(new ZipEntry(base));

			System.out.println("添加压缩文件：" + base);

			FileInputStream in = new FileInputStream(file);

			int b;

			while ((b = in.read()) != -1) {

				out.write(b);

			}

			in.close();

		}

	}

	/**
	 * 
	 * 解压zip文件
	 * 
	 * @param zipFileName
	 *            待解压的zip文件路径，例如：c:\\a.zip
	 * 
	 * @param outputDirectory
	 *            解压目标文件夹,例如：c:\\a\
	 * 
	 */

	public static void unZip(String zipFileName, String outputDirectory)
			throws Exception {
	    Charset gbk = Charset.forName("GBK");
		ZipFile zipFile = new ZipFile(zipFileName,gbk);

		try {

			Enumeration<?> e = zipFile.entries();

			ZipEntry zipEntry = null;

			createDirectory(outputDirectory, "");

			while (e.hasMoreElements()) {

				zipEntry = (ZipEntry) e.nextElement();

				System.out.println("解压：" + zipEntry.getName());

				if (zipEntry.isDirectory()) {

					String name = zipEntry.getName();

					name = name.substring(0, name.length() - 1);

					File f = new File(outputDirectory + File.separator + name);

					f.mkdir();

					System.out.println("创建目录：" + outputDirectory
							+ File.separator + name);

				} else {

					String fileName = zipEntry.getName();

					fileName = fileName.replace('\\', '/');

					if (fileName.indexOf("/") != -1) {

						createDirectory(outputDirectory, fileName.substring(0,
								fileName.lastIndexOf("/")));

						fileName = fileName.substring(
								fileName.lastIndexOf("/") + 1, fileName
										.length());

					}

					File f = new File(outputDirectory + File.separator
							+ zipEntry.getName());

					f.createNewFile();

					InputStream in = zipFile.getInputStream(zipEntry);

					FileOutputStream out = new FileOutputStream(f);

					byte[] by = new byte[1024];

					int c;

					while ((c = in.read(by)) != -1) {

						out.write(by, 0, c);

					}

					in.close();

					out.close();

				}

			}

		} catch (Exception ex) {

			System.out.println(ex.getMessage());

		} finally {

			zipFile.close();

			System.out.println("解压完成！");

		}

	}

	// ok
	public static void deleteFile(File file) {
		if (file.exists()) { // 判断文件是否存在
			if (file.isFile()) { // 判断是否是文件
				file.delete(); // delete()方法 你应该知道 是删除的意思;
			} else if (file.isDirectory()) { // 否则如果它是一个目录
				File files[] = file.listFiles(); // 声明目录下所有的文件 files[];
				for (int i = 0; i < files.length; i++) { // 遍历目录下所有的文件
					deleteFile(files[i]); // 把每个文件 用这个方法进行迭代
				}
			}
			file.delete();
		} else {
			System.out.println("所删除的文件不存在！" + '\n');
		}
	}

	private static void createDirectory(String directory, String subDirectory) {

		String dir[];

		File fl = new File(directory);

		try {

			if (subDirectory == "" && fl.exists() != true) {

				fl.mkdir();

			} else if (subDirectory != "") {

				dir = subDirectory.replace('\\', '/').split("/");

				for (int i = 0; i < dir.length; i++) {

					File subFile = new File(directory + File.separator + dir[i]);

					if (subFile.exists() == false)

						subFile.mkdir();

					directory += File.separator + dir[i];

				}

			}

		} catch (Exception ex) {

			System.out.println(ex.getMessage());

		}

	}

	/**
	 * 
	 * 拷贝文件夹中的所有文件到另外一个文件夹
	 * 
	 * @param srcDirector
	 *            源文件夹
	 * 
	 * @param desDirector
	 *            目标文件夹
	 * 
	 */

	public static void copyFileWithDirector(String srcDirector,
			String desDirector) throws IOException {

		(new File(desDirector)).mkdirs();

		File[] file = (new File(srcDirector)).listFiles();

		for (int i = 0; i < file.length; i++) {

			if (file[i].isFile()) {

				System.out.println("拷贝：" + file[i].getAbsolutePath() + "-->"
						+ desDirector + "/" + file[i].getName());

				FileInputStream input = new FileInputStream(file[i]);

				FileOutputStream output = new FileOutputStream(desDirector
						+ "/" + file[i].getName());

				byte[] b = new byte[1024 * 5];

				int len;

				while ((len = input.read(b)) != -1) {

					output.write(b, 0, len);

				}

				output.flush();

				output.close();

				input.close();

			}

			if (file[i].isDirectory()) {

				System.out.println("拷贝：" + file[i].getAbsolutePath() + "-->"
						+ desDirector + "/" + file[i].getName());

				copyFileWithDirector(srcDirector + "/" + file[i].getName(),
						desDirector + "/" + file[i].getName());

			}

		}

	}

	/**
	 * 
	 * 删除文件夹
	 * 
	 * @param folderPath
	 *            folderPath 文件夹完整绝对路径
	 * 
	 */

	public static void delFolder(String folderPath) throws Exception {
		// 删除完里面所有内容
		delAllFile(folderPath);
		String filePath = folderPath;
		filePath = filePath.toString();
		File myFilePath = new File(filePath);
		// 删除空文件夹
		myFilePath.delete();
	}

	/**
	 * 
	 * 删除指定文件夹下所有文件
	 * 
	 * @param path
	 *            文件夹完整绝对路径
	 * 
	 */

	public static boolean delAllFile(String path) throws Exception {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}

		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}

			if (temp.isFile()) {
				temp.delete();
			}

			if (temp.isDirectory()) {

				// 先删除文件夹里面的文件
				delAllFile(path + "/" + tempList[i]);
				// 再删除空文件夹
				delFolder(path + "/" + tempList[i]);
				flag = true;
			}

		}

		return flag;

	}

	public static Properties getPropsByPathAndName(String pathName) {
		Properties props = new Properties();
		/*String path = Thread.currentThread().getContextClassLoader()
				.getResource("").getPath();*/
		String path = FileUtils.class.getResource("").toString();  
		 if (path != null)  
	        {  
		     
		         path = path.substring(5, path.indexOf("classes") + 8);//如果是windwos将5变成6  
	        }  
		try {
			props.load(new FileInputStream(path + pathName));
		} catch (FileNotFoundException e) {
			System.out.println(e.toString());
		} catch (IOException e) {
			System.out.println(e.toString());
		}
		return props;
	}

	public static String getPropertiesContext(String key, String pathName) {
		String result = "";
		Properties props = getPropsByPathAndName(pathName);
		result = props.getProperty(key);
		//PROPERTIES_FILE_PATHNAMES
		return result;
	}
	public static String getPropertiesValue(String key) {
		String result = "";
		result = getPropertiesContext(key,PROPERTIES_FILE_PATHNAMES[0]);
		return result;
	}
	public static void saveProperties(Properties searchPro, String searchFile) {
		searchPro.put("searchFile", searchFile);
	}

}
