package com.example.jjbfather.jjjqsrt2go2gorestkakaovx;

import android.content.Context;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Created by pc on 2016-07-03.
 */
public class Gmail {
    final String TAG = "GmailSendClass";

    public void gmailSend(Context paramContext, String paramFromEmail, String paramToEmail, String paramTitle, String paramContents) {
        if (GlobalMemberValues.GLOBALNETWORKSTATUS > 0) {
            Properties p = new Properties();
            p.put("mail.smtp.user", GlobalMemberValues.GMAILACCOUNT); // Google계정 아이디@gmail.com으로 설정
            //p.put("mail.smtp.user", "juwanjuhajuye@gmail.com"); // Google계정 아이디@gmail.com으로 설정
            p.put("mail.smtp.host", "smtp.gmail.com");
            p.put("mail.smtp.port", "465");
            p.put("mail.smtp.starttls.enable","true");
            p.put("mail.smtp.auth", "true");

            p.put("mail.smtp.debug", "true");
            p.put("mail.smtp.socketFactory.port", "465");
            p.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            p.put("mail.smtp.socketFactory.fallback", "false");

            //Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
            try {
                Authenticator auth = new SMTPAuthenticator();
                Session session = Session.getInstance(p, auth);
                session.setDebug(true); // 메일을 전송할 때 상세한 상황을 콘솔에 출력한다.

                //session = Session.getDefaultInstance(p);
                MimeMessage msg = new MimeMessage(session);
                String message = paramContents;
                msg.setSubject(paramTitle);
                Address fromAddr = new InternetAddress(paramFromEmail); // 보내는 사람의 메일주소
                msg.setFrom(fromAddr);
                Address toAddr = new InternetAddress(paramToEmail);  // 받는 사람의 메일주소
                msg.addRecipient(Message.RecipientType.TO, toAddr);
                msg.setContent(message, "text/html;charset=UTF-8");
                //System.out.println("Message: " + msg.getContent());
                Transport.send(msg);
                //Toast.makeText(paramContext, "Successfully sent email", Toast.LENGTH_SHORT).show();
                GlobalMemberValues.logWrite(TAG, "Gmail SMTP서버를 이용한 메일보내기 성공\n");
            }
            catch (Exception mex) { // Prints all nested (chained) exceptions as well
                GlobalMemberValues.logWrite(TAG, "Gmail SMTP서버를 이용한 메일보내기 실패..........\n");
                //GlobalMemberValues.logWrite(TAG, mex.getMessage().toString() + "\n");
                //mex.printStackTrace();
                //Toast.makeText(paramContext, "Failed to send email", Toast.LENGTH_SHORT).show();
            }

            //Toast.makeText(paramContext, "Failed to send email", Toast.LENGTH_SHORT).show();
        } else {
            //GlobalMemberValues.displayDialog(paramContext, "Waraning", "Network is not connection", "Close");
            //Toast.makeText(paramContext, "Network is not connection", Toast.LENGTH_SHORT).show();
        }

    }

    private static class SMTPAuthenticator extends Authenticator {

        public PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(GlobalMemberValues.GMAILID, GlobalMemberValues.GMAILPWD); // Google id(@gmail.com 빼고..), pwd
            //return new PasswordAuthentication("juwanjuhajuye", "Juwan0911!"); // Google id, pwd
        }
    }
}
