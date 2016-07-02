package com.andy.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.log4j.Logger;

public class FileUtil {

	private static Logger log = Logger.getLogger(FileUtil.class);
	interface DealConable {
		public int doContent(byte[] content);
	}
	
	public static String readString(String fileName){
		byte[] bytes = readContentByte(fileName);
		try {
			return new String(bytes,"utf-8");
		} catch (UnsupportedEncodingException e) {
			log.error("读取文件异常", e);
		}
		return "";
	}

	public static String readContentString(String fileName) {
		InputStream is = null;
		StringBuffer sb = new StringBuffer();
		BufferedReader br = null;
		try {
			is = new FileInputStream(new File(fileName));
		} catch (FileNotFoundException e) {
			log.error("读取文件异常", e);
		}
		try {
			if(is != null){
				br = new BufferedReader(new InputStreamReader(is, "utf-8"));
				while (br.ready()) {
					sb.append(br.readLine()).append("\n");
				}
			}
		} catch (IOException e) {
			log.error("读取文件异常", e);
		} finally {
			try {
				if (br != null)
					br.close();
				if (is != null)
					is.close();
			} catch (IOException e) {
				log.error("读取文件异常", e);
			}

		}
		if(sb.length() > 0){
			return sb.substring(0,sb.length()-1);
		}
		return "";
	}

	public static byte[] readContentByte(String fileName) {
		InputStream is = null;
		byte[] content = null;
		try {
			is = new FileInputStream(new File(fileName));
			content = new byte[is.available()];
		} catch (Exception e) {
			log.error("读取文件异常", e);
		}
		try {
			if(is != null){
				if (is.read(content) != -1) {
					return content;
				}
			}
		} catch (IOException e) {
			log.error("读取文件异常", e);
		} finally {
			try {
				if (is != null)
					is.close();
			} catch (IOException e) {
				log.error("读取文件异常", e);
			}

		}
		return null;
	}

	public static int readBigContentByte(String fileName, DealConable callBack) {
		InputStream is = null;
		byte[] content = new byte[1024 * 1024];
		try {
			is = new FileInputStream(new File(fileName));
		} catch (Exception e) {
			log.error("读取文件异常", e);
		}
		try {
			if(is != null){
				while (is.read(content) != -1) {
					if (callBack.doContent(content) == -1) {
						return -1;
					}
				}
				return 0;
			}
			return -1;
		} catch (IOException e) {
			log.error("读取文件异常", e);
			return -1;
		} finally {
			try {
				if (is != null)
					is.close();
			} catch (IOException e) {
				log.error("读取文件异常", e);
			}

		}
	}

	/**
	 * 随机读取文件内容
	 */
	public static byte[] readFileByRandomAccess(String fileName, long position, long size) {
		RandomAccessFile randomFile = null;
		try {
			// 打开一个随机访问文件流，按只读方式
			randomFile = new RandomAccessFile(fileName, "r");
			long length = randomFile.length();
			if (size == 0) {
				size = length - position;
			}
			if (position + size > length) {
				size = length - position;
			}
			MappedByteBuffer mbb = randomFile.getChannel().map(FileChannel.MapMode.READ_ONLY, position, size);
			byte[] bytes = new byte[(int) (size)];
			mbb.get(bytes);
			return bytes;
		} catch (IOException e) {
			log.error("读取文件异常", e);
			return null;
		} finally {
			if (randomFile != null) {
				try {
					randomFile.close();
				} catch (IOException e1) {
					log.error("读取文件异常", e1);
					return null;
				}
			}
		}
	}

	/**
	 * A方法追加文件：使用RandomAccessFile
	 * 
	 * @param fileName
	 * @param content
	 */
	public static void appendMethodA(String fileName, String content) {
		try {
			// 打开一个随机访问文件流，按读写方式
			RandomAccessFile randomFile = new RandomAccessFile(fileName, "rw");
			// 文件长度，字节数
			long fileLength = randomFile.length();
			// 将写文件指针移到文件尾。
			randomFile.seek(fileLength);
			randomFile.write(content.getBytes());
			randomFile.close();
		} catch (IOException e) {
			log.error("读取文件异常", e);
		}
	}

	/**
	 * B方法追加文件：使用FileWriter
	 * 
	 * @param fileName
	 * @param content
	 */
	public static void appendMethodB(String fileName, String content) {
		try {
			// 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
			FileWriter writer = new FileWriter(fileName, true);
			writer.write(content);
			writer.close();
		} catch (IOException e) {
			log.error("读取文件异常", e);
		}
	}

	public static boolean rename(File direc,String regex,String to) {
		if(direc.isDirectory()){
			File[] files = direc.listFiles();
			for (File fl : files) {
				if(fl.isDirectory()){
					if(!rename(fl, regex, to)){
						return false;
					}
				}else{
					if(!fl.renameTo(new File(direc.getAbsolutePath() + "\\" + fl.getName().replaceAll(regex, to)))){
						return false;
					}
				}
			}
			return true;
		}
		return false;
	}

	public static void saveToFile(String fileAbsName, byte[] content) throws IOException {

		FileOutputStream fos = new FileOutputStream(fileAbsName);
		fos.write(content);
		fos.flush();
		fos.close();
	}
	
	/**
	 * 获取文件夹下的文件，不包括子文件
	 * @param path
	 * @param regex
	 * @return
	 */
	public static List<File> getFileList(String path,String regex){
		List<File> files = new ArrayList<File>();
		File direc = new File(path);
		if(direc.isDirectory()){
			File[] filea = direc.listFiles();
			for(File f: filea){
				if(f.isFile() && f.getName().matches(regex)){
					files.add(f);
				}
			}
		}else{
			if(direc.getName().matches(regex)){
				files.add(direc);
			}
		}
		
		return files;
	}
	
	/**
	 * 从包package中获取所有的Class
	 * @param pack
	 * @return
	 */
	public static ArrayList<Class<?>> getClasses(String packageName) {

		// 第一个class类的集合
		ArrayList<Class<?>> classes = new ArrayList<Class<?>>();
		// 是否循环迭代
		boolean recursive = true;
		String packageDirName = packageName.replace('.', '/');
		// 定义一个枚举的集合 并进行循环来处理这个目录下的things
		Enumeration<URL> dirs;
		try {
			dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
			// 循环迭代下去
			while (dirs.hasMoreElements()) {
				// 获取下一个元素
				URL url = dirs.nextElement();
				// 得到协议的名称
				String protocol = url.getProtocol();
				// 如果是以文件的形式保存在服务器上
				if ("file".equals(protocol)) {
					// 获取包的物理路径
					String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
					// 以文件的方式扫描整个包下的文件 并添加到集合中
					findAndAddClassesInPackageByFile(packageName, filePath, recursive, classes);
				} else if ("jar".equals(protocol)) {
					// 如果是jar包文件
					// 定义一个JarFile
					JarFile jar;
					try {
						// 获取jar
						jar = ((JarURLConnection) url.openConnection()).getJarFile();
						// 从此jar包 得到一个枚举类
						Enumeration<JarEntry> entries = jar.entries();
						// 同样的进行循环迭代
						while (entries.hasMoreElements()) {
							// 获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文件
							JarEntry entry = entries.nextElement();
							String name = entry.getName();
							// 如果是以/开头的
							if (name.charAt(0) == '/') {
								// 获取后面的字符串
								name = name.substring(1);
							}
							// 如果前半部分和定义的包名相同
							if (name.startsWith(packageDirName)) {
								int idx = name.lastIndexOf('/');
								// 如果以"/"结尾 是一个包
								if (idx != -1) {
									// 获取包名 把"/"替换成"."
									packageName = name.substring(0, idx).replace('/', '.');
								}
								// 如果可以迭代下去 并且是一个包
								if ((idx != -1) || recursive) {
									// 如果是一个.class文件 而且不是目录
									if (name.endsWith(".class") && !entry.isDirectory()) {
										// 去掉后面的".class" 获取真正的类名
										String className = name.substring(packageName.length() + 1, name.length() - 6);
										try {
											// 添加到classes
											classes.add(Class.forName(packageName + '.' + className));
										} catch (ClassNotFoundException e) {
											log.error("添加用户自定义视图类错误 找不到此类的.class文件");
											e.printStackTrace();
										}
									}
								}
							}
						}
					} catch (IOException e) {
						log.error("在扫描用户定义视图时从jar包获取文件出错",e);
					}
				}
			}
		} catch (IOException e) {
			log.error("读取文件异常", e);
		}

		return classes;
	}
	
	/**
	 * 以文件的形式来获取包下的所有Class
	 * 
	 * @param packageName
	 * @param packagePath
	 * @param recursive
	 * @param classes
	 */
	public static void findAndAddClassesInPackageByFile(String packageName, String packagePath, final boolean recursive, ArrayList<Class<?>> classes) {
		// 获取此包的目录 建立一个File
		File dir = new File(packagePath);
		// 如果不存在或者 也不是目录就直接返回
		if (!dir.exists() || !dir.isDirectory()) {
			log.warn("用户定义包名 " + packageName + " 下没有任何文件");
			return;
		}
		// 如果存在 就获取包下的所有文件 包括目录
		File[] dirfiles = dir.listFiles(new FileFilter() {
			// 自定义过滤规则 如果可以循环(包含子目录) 或则是以.class结尾的文件(编译好的java类文件)
			public boolean accept(File file) {
				return (recursive && file.isDirectory()) || (file.getName().endsWith(".class"));
			}
		});
		// 循环所有文件
		for (File file : dirfiles) {
			// 如果是目录 则继续扫描
			if (file.isDirectory()) {
				findAndAddClassesInPackageByFile(packageName + "." + file.getName(), file.getAbsolutePath(), recursive, classes);
			} else {
				// 如果是java类文件 去掉后面的.class 只留下类名
				String className = file.getName().substring(0, file.getName().length() - 6);
				try {
					// 添加到集合中去
					classes.add(Class.forName(packageName + '.' + className));
				} catch (ClassNotFoundException e) {
					log.error("添加用户自定义视图类错误 找不到此类的.class文件",e);
				}
			}
		}
	}

	public static void main(String[] args) {
		// FileUtil.appendMethodA("c:/a.txt", "哈哈人热热个的");
		System.out.println(new String(FileUtil.readFileByRandomAccess("c:/a.txt", 0, 6)));
		System.out.println(new String(FileUtil.readFileByRandomAccess("c:/a.txt", 6, 100)));
	}
}
