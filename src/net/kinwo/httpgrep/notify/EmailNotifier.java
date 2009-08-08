package net.kinwo.httpgrep.notify;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Send email to notify users the grepping result.
 * 
 * 
 * @author henry
 */
public class EmailNotifier {

	private static final List<String> defaultRecipient = new ArrayList<String>();

	private static final String fromIdentity = "jan.pakinski@gmail.com";

	// CONFIG - SMTP server
	private static final String smtpServer = "smtp.mail.com";

	static {
		defaultRecipient.add("test@test");
	}

	public static void sendTextEmail(List<String> recipients, String subject, String msg) {
		try {
			// Get system properties
			Properties props = System.getProperties();

			// Setup mail server
			props.put("mail.smtp.host", smtpServer);

			// Get session
			Session session = Session.getDefaultInstance(props, null);

			// Define message
			MimeMessage message = new MimeMessage(session);

			// Set the from address
			message.setFrom(new InternetAddress(fromIdentity));

			// Set the to address
			for (String rp : recipients) {
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(rp));
			}

			// Set the subject
			message.setSubject(subject);

			// Set the content
			message.setText(msg);

			// Send message
			Transport.send(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Send email
	 * 
	 * @param subject
	 *            Email subject
	 * @param msg
	 *            Message content
	 */
	public static void sendTextEmail(String subject, String msg) {
		sendTextEmail(defaultRecipient, subject, msg);
	}

	public static void sendTextEmail(String recipient, String subject, String msg) {
		List<String> list = new ArrayList<String>();
		list.add(recipient);
		sendTextEmail(list, subject, msg);
	}

}