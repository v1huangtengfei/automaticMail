package com.ideepmind.mail;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import com.sun.mail.util.MailSSLSocketFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.*;

public class MailUtil {
	
	private static String account = "huangtengfei@ideepwise.ai";// 登录账户
	private static String password = "Huang137300";// 登录密码
	private static String host = "smtp.exmail.qq.com";// 服务器地址
	private static String port = "465";// 端口
	private static String protocol = "smtp";// 协议
	
	//初始化参数
	public static Session initProperties() {
	    Properties properties = new Properties();
	    properties.setProperty("mail.transport.protocol", protocol);
	    properties.setProperty("mail.smtp.host", host);
	    properties.setProperty("mail.smtp.port", port);
	    // 使用smtp身份验证
	    properties.put("mail.smtp.auth", "true");
	    // 使用SSL,企业邮箱必需 start
	    // 开启安全协议
	    MailSSLSocketFactory mailSSLSocketFactory = null;
	    try {
	        mailSSLSocketFactory = new MailSSLSocketFactory();
	        mailSSLSocketFactory.setTrustAllHosts(true);
	    } catch (GeneralSecurityException e) {
	        e.printStackTrace();
	    }
	    properties.put("mail.smtp.enable", "true");
	    properties.put("mail.smtp.ssl.socketFactory", mailSSLSocketFactory);
	    properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	    properties.put("mail.smtp.socketFactory.fallback", "false");
	    properties.put("mail.smtp.socketFactory.port", port);
	    System.setProperty("mail.mime.splitlongparameters", "false");//设置了附件名过长问题
	    Session session = Session.getDefaultInstance(properties, new Authenticator() {
	        @Override
	        protected PasswordAuthentication getPasswordAuthentication() {
	            return new PasswordAuthentication(account, password);
	        }
	    });
	    // 使用SSL,企业邮箱必需 end
	    // TODO 显示debug信息 正式环境注释掉
	    //session.setDebug(true);
	    return session;
	}
	 
	// @param sender 发件人别名
	// @param subject 邮件主题
	//@param content 邮件内容
	//@param receiverList 接收者列表,多个接收者之间用","隔开
	//@param fileSrc 附件地址
	@SuppressWarnings({ "static-access", "hiding" })
	public static boolean send(String subject, String content, String receiverList, String copyReceiverList,
			List<Map<String,Object>> fileList) {
	    try {
	        Session session = initProperties();
	        MimeMessage mimeMessage = new MimeMessage(session);
	        mimeMessage.setFrom(new InternetAddress(account, account));// 发件人,可以设置发件人的别名
	        // 收件人,多人接收
	        InternetAddress[] internetAddressTo = new InternetAddress().parse(receiverList);
	        mimeMessage.setRecipients(Message.RecipientType.TO, internetAddressTo);
	        //抄送人，多人接受
	        InternetAddress[] internetAddressToCC = new InternetAddress().parse(copyReceiverList);
	        mimeMessage.setRecipients(Message.RecipientType.CC, internetAddressToCC);
	        
	        // 主题
	        mimeMessage.setSubject(subject);
	        // 时间
	        mimeMessage.setSentDate(new Date());
	        // 容器类 附件
	        MimeMultipart mimeMultipart = new MimeMultipart();
	        // 可以包装文本,图片,附件
	        MimeBodyPart bodyPart = new MimeBodyPart();
	        // 设置内容
	        bodyPart.setContent(content, "text/html; charset=UTF-8");
	        mimeMultipart.addBodyPart(bodyPart);
	        // 添加图片&附件
            if (fileList != null && fileList.size() > 0) {
            	for (Map<String,Object> map : fileList) {
            		bodyPart = new MimeBodyPart();
                    bodyPart.setDataHandler(new DataHandler((DataSource)map.get("file")));
                    bodyPart.setFileName(MimeUtility.encodeText(map.get("fileName").toString(), "UTF-8", "B"));
                    mimeMultipart.addBodyPart(bodyPart);
				}
            	
//            	String[] split = fileList.split(",");
//                for (int i = 0; i < split.length; i++) {
//                	bodyPart = new MimeBodyPart();
//                    FileDataSource fds = new FileDataSource(split[i]);
//                    bodyPart.setDataHandler(new DataHandler(fds));
//                    bodyPart.setFileName(MimeUtility.encodeText(fds.getName(), "UTF-8", "B"));
//                    mimeMultipart.addBodyPart(bodyPart);
//                }
            }
	        mimeMessage.setContent(mimeMultipart);
	        mimeMessage.saveChanges();
	        Transport.send(mimeMessage);
	        return true;
	    } catch (MessagingException e) {
	        e.printStackTrace();
	        return false;
	    } catch (UnsupportedEncodingException e) {
	        e.printStackTrace();
	        return false;
	    } catch (IOException e) {
	        e.printStackTrace();
	        return false;
	    }
	}
}