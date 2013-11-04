<%@page import="com.souyibao.shared.dao.EntityCRUDDao"%>
<%@page import="com.souyibao.shared.entity.Consulting"%>
<%@page import="java.sql.Date"%>
<%@page import="java.util.Properties"%>
<%@page import="javax.mail.Message"%>
<%@page import="javax.mail.MessagingException"%>
<%@page import="javax.mail.PasswordAuthentication"%>
<%@page import="javax.mail.Session"%>
<%@page import="javax.mail.Transport"%>
<%@page import="javax.mail.internet.InternetAddress"%>
<%@page import="javax.mail.internet.MimeMessage"%>
<%
  final String userId = request.getParameter("user_id");
  final String content = request.getParameter("content");
  
  final Consulting consulting = new Consulting();
  consulting.setContent(content);
  consulting.setUserId(userId);
  consulting.setStatus("pending");
  consulting.setCreatedOn(new Date(System.currentTimeMillis()));
  
  final EntityCRUDDao dao = EntityCRUDDao.getInstance();
  dao.save(consulting);
  
   Runnable sendMail = new Runnable(){
	  public void run(){
		  // send email now
		  Properties props = new Properties();
		  props.put("mail.smtp.host", "smtp.gmail.com");
		  props.put("mail.smtp.socketFactory.port", "465");
		  props.put("mail.smtp.socketFactory.class",
		      "javax.net.ssl.SSLSocketFactory");
		  props.put("mail.smtp.auth", "true");
		  props.put("mail.smtp.port", "465");

		  Session mSession = Session.getInstance(props,
		    new javax.mail.Authenticator() {
		      protected PasswordAuthentication getPasswordAuthentication() {
		        return new PasswordAuthentication("jq.ehealth@gmail.com","jq88668");
		      }
		    });

		  try {

		    Message message = new MimeMessage(mSession);
		    message.setFrom(new InternetAddress("jq.ehealth@gmail.com"));
		    message.setRecipients(Message.RecipientType.TO,
		        InternetAddress.parse("jq.ehealth@gmail.com"));
		    message.setSubject(userId);
		    message.setText(content);

		    Transport.send(message);
		    
		    consulting.setStatus("success");
		  } catch (MessagingException e) {
		    consulting.setStatus("failed");
		    consulting.setMessage(e.getMessage());
		  }
		  
		  try {
			  dao.update(consulting);
		  } catch (Exception e) {
			  e.printStackTrace();
		  }
	  }
   };
  
  new Thread(sendMail).start();
%>