package com.cn.template.xutil;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactory;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.multipart.MultipartFile;

import com.cn.template.service.mail.EmailContentService;
import com.cn.template.web.shiro.ShiroUser;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 系统公用方法集.
 * 
 * @author Libra
 * 
 */
public class Utils {

	private static final Logger logger = LoggerFactory.getLogger(EmailContentService.class); 
	
	/** 保存图片的字节大小 */
	private final static int BUFFER_SIZE = 16 * 1024;
	
	/** 去除html的正则表达式 */
	/** 定义script的正则表达式 */
	private static final String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; 
	/** 定义style的正则表达式 */
    private static final String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>";
    /** 定义HTML标签的正则表达式 */
    private static final String regEx_html = "<[^>]+>";

	/**
	 * 取出Shiro中的当前用户. Subject才是Shiro的“用户”概念.
	 */
	public static Subject getCurrentUser() {
		return SecurityUtils.getSubject();
	}

	/**
	 * 取出登录的用户信息.
	 */
	public static ShiroUser getLoginUser() {
		return (ShiroUser) SecurityUtils.getSubject().getPrincipal();
	}
	
	/**
	 * 取出Shiro中的当前用户Id.
	 */
	public static Long getCurrentUserId() {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		return user.id;
	}

	/**
	 * 按格式要求，获得当前时间，按字符串格式输出
	 * 
	 * @param pattern
	 * @return
	 */
	public static String getNow(String pattern) {
		return DateFormatUtils.format(new Date(), pattern);
	}

	/**
	 * 按格式要求，获得当前时间，按字符串格式输出
	 * 
	 * @param pattern
	 * @return
	 */
	public static String datef(Date date, String pattern) {
		if (date != null) {
			return DateFormatUtils.format(date, pattern);
		} else {
			return null;
		}
	}

	/**
	 * 从日期的字符串形式，转换成Date型，如果转换失败，则转换为当前时间
	 * 
	 * @param datestr
	 *            日期的字符串形式
	 * @return
	 */
	public static Date parseDate(String datestr) {
		try {
			return DateUtils.parseDate(datestr, Constants.FORMAT_PATTERNS);
		} catch (ParseException e) {
		}
		return new Date();
	}

	/**
	 * 将日期转换为小时数
	 * 
	 * @param time
	 * @return
	 */
	public static Double hour(Date time) {
		SimpleDateFormat st = new SimpleDateFormat("yyyyMMddHH");
		return Double.parseDouble(st.format(time));
	}

	/**
	 * 获得指定日期的前一天
	 * 
	 * @param specifiedDay
	 * @return
	 * @throws Exception
	 */
	public static String getSpecifiedDayBefore(String specifiedDay) {
		// SimpleDateFormat simpleDateFormat = new
		// SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		Date date = null;
		try {
			date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day - 1);

		String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
		return dayBefore;
	}

	/**
	 * 获得指定日期的后一天
	 * 
	 * @param specifiedDay
	 * @return
	 */
	public static String getSpecifiedDayAfter(String specifiedDay) {
		Calendar c = Calendar.getInstance();
		Date date = null;
		try {
			date = new SimpleDateFormat("yy-MM-dd").parse(specifiedDay);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day + 1);

		String dayAfter = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
		return dayAfter;
	}

	/**
	 * 获得下个月第一天的日期.
	 * @param date
	 * @return
	 */
	public static String getNextMonthFirst(Date date) {
		String str = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar lastDate = Calendar.getInstance();
		lastDate.setTime(date);
		lastDate.add(Calendar.MONTH, 1);// 减一个月
		lastDate.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		str = sdf.format(lastDate.getTime());
		return str;
	}

	/**
	 * 取得两个时间间隔的天数.
	 * @param smdate
	 * @param bdate
	 * @return
	 * @throws ParseException
	 */
	public static int daysBetween(Date smdate, Date bdate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		smdate = sdf.parse(sdf.format(smdate));
		bdate = sdf.parse(sdf.format(bdate));
		Calendar cal = Calendar.getInstance();
		cal.setTime(smdate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(bdate);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(between_days));
	}
	
	/**
	 * 判断当前日期是星期几<br>
	 * <br>
	 * 
	 * @param pTime
	 *            修要判断的时间<br>
	 * @return dayForWeek 判断结果<br>
	 * @Exception 发生异常<br>
	 */
	public static int dayForWeek(String pTime) throws Exception {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.setTime(format.parse(pTime));
		int dayForWeek = 0;
		if (c.get(Calendar.DAY_OF_WEEK) != 1) {
			dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
		}
		return dayForWeek;
	}
	
	/**
	 * 获得该日期所在星期的所有日期
	 * 
	 * @param data
	 * @param week
	 * @return
	 */
	public static String[] startEndOfWeek(String date, Integer week) {
		String[] weeks = new String[7];
		Calendar StartCalendar = Calendar.getInstance();
		StartCalendar.setTime(parseDate(date));
		StartCalendar.add(Calendar.DAY_OF_MONTH, -week);
		weeks[0] = datef(StartCalendar.getTime(), Constants.DATE_FORMAT);
		for (int i = 1; i < 7; i++) {
			StartCalendar.add(Calendar.DAY_OF_MONTH, +1);
			weeks[i] = datef(StartCalendar.getTime(), Constants.DATE_FORMAT);
		}
		return weeks;
	}
	

	/**
	 * 将字符串转换成长整型，如果转换失败，则默认为0
	 * 
	 * @param str
	 * @return
	 */
	public static Long toLong(String str) {
		return NumberUtils.toLong(str, 0);
	}

	/**
	 * 将字符串转换成Double型，如果转换失败，则默认为0.00
	 * 
	 * @param str
	 * @return
	 */
	public static Double toDouble(String str) {
		return NumberUtils.toDouble(str, 0.00);
	}

	/**
	 * 将字符串转换成整型，如果转换失败，则默认为0
	 * 
	 * @param str
	 * @return
	 */
	public static Integer toInt(String str) {
		return NumberUtils.toInt(str, 0);
	}

	/**
	 * 判断字符串是否为空格、Null 或 "" Checks if a String is whitespace, empty ("") or
	 * null.
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isBlank(String str) {
		return StringUtils.isBlank(str);
	}

	/**
	 * 判断字符串是否为Null 或 "" 返回指定默认值.
	 * 
	 * @param str
	 * @return
	 */
	public static String isEmpty(String str, String def) {
		if (isBlank(str)) {
			return def;
		}
		return str;
	}

	/**
	 * 判断字符串是否为Null 或 "" Checks if a String is empty ("") or null.
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		return StringUtils.isEmpty(str);
	}

	/**
	 * 判断字符串是否不是空格、Null 或 "" Checks if a String is not empty (""), not null and
	 * not whitespace only.
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNotBlank(String str) {
		return StringUtils.isNotBlank(str);
	}

	/**
	 * 判断字符串是否不是为Null 或 "" Checks if a String is not empty ("") and not null.
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str) {
		return StringUtils.isNotEmpty(str);
	}
	
	/**
	 * 判断是否为空.
	 */
	public static boolean isNotEmpty(Collection collection) {
		return (collection != null && !(collection.isEmpty()));
	}

	
	/**
	 * 文件下载
	 * 
	 * @param path 保存路径
	 * @param filename 文件名
	 * @param contentType 文件类型
	 * @param response
	 * @throws Exception
	 */
	public static void streamDownload(String path, String filename, String contentType, HttpServletResponse response) throws Exception {
		File file = new File(Constants.WEBROOT + "/" + path);
		InputStream is = new BufferedInputStream(new FileInputStream(file));
		byte[] buffer = new byte[is.available()];
		is.read(buffer);
		is.close();
		response.reset();
		response.addHeader("Content-Disposition", "attachments;filename=" + new String(filename.getBytes("GB2312"), "iso-8859-1"));
		response.addHeader("Coutent-Length", "" + file.length());
		response.setContentType(contentType);
		OutputStream os = new BufferedOutputStream(response.getOutputStream());
		os.write(buffer);
		os.flush();
		os.close();
	}

	

	/**
	 * 判断字符串是否为null，如果为null，转换为""
	 * 
	 * @param value
	 * @return
	 */
	public static String judgeNull(String value) {
		if (value == null) {
			return "";
		} else {
			return value;
		}
	}

	/**
	 * 拷贝文件,返回保存路径.
	 * 
	 * @param src
	 * @param dst
	 */
	public static String copy(MultipartFile src, String fileNewName, String path) {
		File localFile = new File(path + "/" + Constants.FILESITE);
		if (!localFile.exists()) {
			localFile.mkdirs();
		}
		String tempPath = path + "/" + Constants.FILESITE + "/" + fileNewName;
		File imageFile = new File(tempPath);
		try {
			InputStream in = null;
			OutputStream out = null;
			try {
				in = src.getInputStream();
				out = new BufferedOutputStream(new FileOutputStream(imageFile), BUFFER_SIZE);
				byte[] buffer = new byte[BUFFER_SIZE];
				while (in.read(buffer) > 0) {
					out.write(buffer);
				}
				return Constants.FILESITE + "/" + fileNewName;
			} finally {
				if (null != in) {
					in.close();
				}
				if (null != out) {
					out.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 保存文件到物理路径
	 * 
	 * @param src
	 * @param fileNewName
	 * @param path
	 * @return
	 */
	public static String copyToDisk(MultipartFile src, String fileNewName, String path) {
		File localFile = new File(path);
		if (!localFile.exists()) {
			localFile.mkdirs();
		}
		String tempPath = path + fileNewName;
		File imageFile = new File(tempPath);
		try {
			InputStream in = null;
			OutputStream out = null;
			try {
				in = src.getInputStream();
				out = new BufferedOutputStream(new FileOutputStream(imageFile), BUFFER_SIZE);
				byte[] buffer = new byte[BUFFER_SIZE];
				while (in.read(buffer) > 0) {
					out.write(buffer);
				}
				return Constants.FILESITE + "/" + fileNewName;
			} finally {
				if (null != in) {
					in.close();
				}
				if (null != out) {
					out.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 将文件保存到指定路径.
	 * 
	 * @param src
	 * @param path
	 *            在项目保存的目录.
	 * @return
	 */
	public static void copy(MultipartFile multipartFile, String path) {
		try {
			File localFile = new File(Constants.WEBROOT + path);
			if (!localFile.exists()) {
				localFile.mkdirs();
			}
			File file = new File(Constants.WEBROOT + path + multipartFile.getOriginalFilename());
			InputStream in = multipartFile.getInputStream();
			OutputStream out = new BufferedOutputStream(new FileOutputStream(file), BUFFER_SIZE);
			byte[] buffer = new byte[BUFFER_SIZE];
			while (in.read(buffer) > 0) {
				out.write(buffer);
			}
			if (null != in) {
				in.close();
			}
			if (null != out) {
				out.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 将文件转移到新的文件夹下.
	 * 
	 * @param file
	 * @param path
	 */
	public static void changeFold(File file, String path) {
		try {
			File localFile = new File(Constants.WEBROOT + path);
			if (!localFile.exists()) {
				localFile.mkdirs();
			}
			int byteread = 0;
			FileInputStream fin = null;
			FileOutputStream fout = null;
			try {
				if (file.exists()) {
					fin = new FileInputStream(file);
					fout = new FileOutputStream(Constants.WEBROOT + path + file.getName());
					byte[] buffer = new byte[BUFFER_SIZE];
					while ((byteread = fin.read(buffer)) != -1) {
						fout.write(buffer, 0, byteread);
					}
					if (fin != null) {
						fin.close();// 如果流不关闭,则删除不了旧文件
						delFile(file);
					}
				} else {
					throw new Exception("需要转移的文件不存在!");
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			} finally {
				if (fin != null) {
					fin.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除文件,只支持删除文件,不支持删除目录
	 * 
	 * @param file
	 * @throws Exception
	 */
	public static void delFile(File file) throws Exception {
		if (!file.exists()) {
			throw new Exception("文件" + file.getName() + "不存在,请确认!");
		}
		if (file.isFile()) {
			if (file.canWrite()) {
				file.delete();
			} else {
				throw new Exception("文件" + file.getName() + "只读,无法删除,请手动删除!");
			}
		} else {
			throw new Exception("文件" + file.getName() + "不是一个标准的文件,有可能为目录,请确认!");
		}
	}

	/**
	 * 获得项目指定文件夹下的所有指定后缀的文件.
	 * 
	 * @param path
	 * @param suffixs
	 * @return
	 */
	public static File[] fileInFolder(String path, final String... suffixs) {
		File folder = new File(Constants.WEBROOT + path);
		// 判断文件夹是否存在
		if (folder.exists()) {
			// 是否指定了过滤后缀.
			if (suffixs.length > 0) {
				return folder.listFiles(new FileFilter() {
					public boolean accept(File pathname) {
						for (String suffix : suffixs) {
							if (pathname.getName().endsWith(suffix)) {
								return true;
							}
						}
						return false;
					}
				});
			} else {
				// 不指定后缀，获取指定文件夹下的所有文件.
				return folder.listFiles();
			}
		} else {
			// 不存在即进行创建.
			folder.mkdir();
			return null;
		}

	}
	
	
	/**
	 * 往指定的HTTP服务地址发送内容.
	 * @param url
	 * @param content
	 * @return
	 */
	public static String contentPublish(String url,String content){
		try{
			URL urlGet = new URL(url);
			HttpsURLConnection http = (HttpsURLConnection) urlGet.openConnection();

			// 连接超时
			http.setConnectTimeout(25000);
			// 读取超时 --服务器响应比较慢，增大时间
			http.setReadTimeout(25000);
			http.setRequestMethod("POST");
			http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
			http.setDoOutput(true);
			http.setDoInput(true);
			http.connect();

			OutputStream out = http.getOutputStream();
			out.write(content.getBytes("UTF-8"));
			out.flush();
			out.close();

			InputStream in = http.getInputStream();
			BufferedReader read = new BufferedReader(new InputStreamReader(in,"UTF-8"));
			String valueString = null;
			StringBuffer bufferRes = new StringBuffer();
			while ((valueString = read.readLine()) != null) {
				bufferRes.append(valueString);
			}
			in.close();
			if (http != null) {
				// 关闭连接
				http.disconnect();
			}
			logger.info("publish result:{}",bufferRes.toString());
			return bufferRes.toString();
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * FreeMark模板解析，返回解析后的String.
	 * @param masterplate
	 * @param map
	 * @return
	 */
	public static String ftlAnalyze(String masterplate,Map<String,Object> map){
		try{
			FreeMarkerConfigurationFactory freeMarkerConfigurationFactory=new FreeMarkerConfigurationFactory();
			freeMarkerConfigurationFactory.setTemplateLoaderPath("classpath:/freemarker");
		
			Configuration configuration=freeMarkerConfigurationFactory.createConfiguration();
			Template template=configuration.getTemplate(masterplate, "utf-8");
			return FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
		}catch(Exception e){
			return null;	
		}
	}
	
	
	/**
	 * 去除字符串中的HTML标签，只留下文字.
	 * @param htmlStr
	 * @return
	 */
	public static String delHTMLTag(String htmlStr) {
        Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
        Matcher m_script = p_script.matcher(htmlStr);
        htmlStr = m_script.replaceAll(""); // 过滤script标签

        Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
        Matcher m_style = p_style.matcher(htmlStr);
        htmlStr = m_style.replaceAll(""); // 过滤style标签

        Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(htmlStr);
        htmlStr = m_html.replaceAll(""); // 过滤html标签

        return htmlStr.trim(); // 返回文本字符串
    }
	
}
