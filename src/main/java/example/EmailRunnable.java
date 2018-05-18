package example;

import bean.Employee;

import freemarker.core.ParseException;
import freemarker.template.*;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Properties;

/**
 * @jiguiyang
 *
 * send email
 */
public class EmailRunnable implements Runnable {

    private Employee employee;

    public EmailRunnable(Employee employee) {
        this.employee = employee;
    }

    @Override
    public void run() {
        try {
            Properties properties = new Properties();
           // properties.setProperty("mail.host", "smtp.qq.com");
            properties.setProperty("mail.transport.protocol", "smtp");
            properties.setProperty("mail.smtp.auth", "true");
            properties.setProperty("mail.smtp.host", "smtp.163.com");
            // properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
           // properties.setProperty("mail.smtp.port", "465");
            Session session = Session.getDefaultInstance(properties);
            session.setDebug(true);
            Transport transport = session.getTransport();
            transport.connect("15726250811@163.com", "1018cxks");
            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.addRecipients(Message.RecipientType.TO, employee.getEmail());
            mimeMessage.setFrom(new InternetAddress("15726250811@163.com"));
            mimeMessage.setSubject("谷歌：通知");
            MimeMultipart mixed = new MimeMultipart("mixed");
            mimeMessage.setContent(mixed);
            MimeBodyPart content = new MimeBodyPart();
            mixed.addBodyPart(content);
            MimeMultipart bodyMimeMultipart = new MimeMultipart("related");
            content.setContent(bodyMimeMultipart);
            MimeBodyPart bodyPart = new MimeBodyPart();
            //freemarker加载邮件模板
            Configuration cfg = new Configuration(Configuration.VERSION_2_3_27);
            cfg.setClassLoaderForTemplateLoading(ClassLoader.getSystemClassLoader(),"ftl");
            Template emailTemplate = cfg.getTemplate("email.ftl");
            StringWriter out = new StringWriter();
            emailTemplate.process(employee,out);
            bodyPart.setContent(out.toString(),"text/html;charset=utf-8");
            bodyMimeMultipart.addBodyPart(bodyPart);
            mimeMessage.saveChanges();
            transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
            transport.close();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (MalformedTemplateNameException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (TemplateNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }

    public static void main(String []args) throws java.text.ParseException {
        Employee employee =  new Employee();
        employee.setId(1L);employee.setName("郭振江");employee.setWorkID("7878");
        employee.setContractTerm(3.0);SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");employee.setBeginContract(sdf.parse("2008-04-14"));
        employee.setEndContract(sdf.parse("2018-04-14"));employee.setDepartmentName("data");employee.setEmail("1067703992@qq.com");
        employee.setPosName("工程师");

        EmailRunnable emailRunnable = new EmailRunnable(employee);
        emailRunnable.run();

        System.out.println("send Over!");
    }
}
