package com.pibigstar.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import com.sun.mail.util.MailSSLSocketFactory;

public class SendEmail {
	private String accout ;//发件人的账号
	private String password;//授权码
	private static String  stmp = "";//协议
	private static int  port = 0;//端口

	/**
	 * @param accout 发送人账号
	 * @param password 授权码
	 */
	public SendEmail(String accout, String password) {
		this.accout = accout;
		this.password = password;
		//根据发送人的账号选择相应的协议
		if (accout.contains("@163.com")) {
			stmp = "smtp.163.com";
			port = 994;
		}else if (accout.contains("@qq.com")) {
			stmp = "smtp.qq.com";
			port = 465;
		}
	}
	/**
	 * @param receiver 收件人
	 * @param title 邮件主题
	 * @param content 邮件正文 （可使用HTML标签）
	 * @throws Exception 
	 */
	public void sendEmail(String receiver,String title,String content){
		for(int i=0;i<5;i++) {//尝试5次，防止网速不好发不出去
			try {
				// 创建Properties 类用于记录邮箱的一些属性
				Properties props = new Properties();
				// 表示SMTP发送邮件，必须进行身份验证
				props.put("mail.smtp.auth", "true");
				//此处填写SMTP服务器
				props.put("mail.smtp.host", stmp);
				//端口号，QQ邮箱给出了两个端口 587 和 465
				props.put("mail.smtp.port", port);
				props.setProperty("mail.transport.protocol", "smtp");
				// 此处填写你的账号
				props.put("mail.user", accout);
				// 此处的密码就是前面说的16位STMP口令
				props.put("mail.password", password);
				//开启 SSL 加密 （163、新浪邮箱不需要 SSL 加密）
				MailSSLSocketFactory sf = new MailSSLSocketFactory();
				sf.setTrustAllHosts(true);
				props.put("mail.smtp.ssl.enable", "true");
				props.put("mail.smtp.ssl.socketFactory", sf);

				// 构建授权信息，用于进行SMTP进行身份验证
				Authenticator authenticator = new Authenticator() {

					protected PasswordAuthentication getPasswordAuthentication() {
						// 用户名、密码
						String userName = props.getProperty("mail.user");
						String password = props.getProperty("mail.password");
						return new PasswordAuthentication(userName, password);
					}
				};
				// 使用环境属性和授权信息，创建邮件会话
				Session mailSession = Session.getInstance(props, authenticator);
				// 创建邮件消息
				MimeMessage message = new MimeMessage(mailSession);
				// 设置发件人
				InternetAddress form = new InternetAddress(
						props.getProperty("mail.user"));

				message.setFrom(form);
				// 设置收件人的邮箱
				InternetAddress to = new InternetAddress(receiver);
				message.setRecipient(RecipientType.TO, to);
				// 设置邮件标题
				message.setSubject(title);
				// 设置邮件的内容体
				message.setContent(content, "text/html;charset=UTF-8");
				// 设置时间
				message.setSentDate(new Date());
				// 发送邮件
				Transport.send(message);
				System.out.println("发送成功");
				break;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
