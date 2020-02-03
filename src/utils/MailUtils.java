package utils;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

public class MailUtils {
	/**
	 * 外网发送邮件
	 * @param to
	 * @param code
	 */
	public static void sendMail(String to, String code) {
		//Session对象
		Properties properties = new Properties();
		properties.setProperty("mail.host", "smtp.163.com");
		properties.setProperty("mail.smtp.auth", "true");
		Session session = Session.getInstance(properties,new Authenticator() {
		@Override
		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication("ek774653363@163.com", "ek774653363");
		}
		});
		//Message对象
		Message message = new MimeMessage(session);
		try {
			//设置发件人
			message.setFrom(new InternetAddress("ek774653363@163.com"));
			//设置收件人
			message.addRecipient(RecipientType.TO,new InternetAddress(to));
			//设置主题
			message.setSubject("来自store的激活邮件!");
			//设置内容
			String url = "http://127.0.0.1/Store_WEB/UserServlet?method=active&code="+code;
			message.setContent("<h1>来自store的激活邮件!激活请点击一下链接:</h1><h3><a href='"+url+"'>"+url+"</a></h3>","text/html;charset=UTF-8");
			//使用Transport对象,发送邮件
			Transport.send(message);
			
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
