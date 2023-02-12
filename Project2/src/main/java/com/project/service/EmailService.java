package com.project.service;

import java.io.UnsupportedEncodingException;
import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@PropertySource("classpath:application.yml")
@Slf4j
@RequiredArgsConstructor
@Service
public class EmailService {

   private final JavaMailSender javaMailSender;

   // 인증번호 생성

   @Value("${spring.mail.username}")
   private String id;

   public MimeMessage createMessage(String to,String ePw) throws MessagingException, UnsupportedEncodingException {

      System.out.println("보내는 대상 : " + to);
      System.out.println("인증번호 : " + ePw);
      MimeMessage message = javaMailSender.createMimeMessage();

      message.addRecipients(MimeMessage.RecipientType.TO, to); // to 보내는 대상
      message.setSubject("DOGATHER 회원가입 이메일 인증"); // 메일 제목

      // 메일 내용 메일의 subtype을 html로 지정하여 html문법 사용 가능
      String msg = "";
      msg += "<div><h1 style=\"font-size: 30px; padding-right: 30px; padding-left: 30px;\">DOGATEHR입니다. 아래 인증 번호를 입력해 주세요</h1>";

      msg += "<span style=\"font-size:24px; font-weight:bold;\">" + ePw + "</span>";
      msg += "</div>";
      message.setText(msg, "utf-8", "html"); // 내용, charset타입, subtype
      message.setFrom(new InternetAddress("kdogather@gmail.com", "DOGATHER")); // 보내는 사람의 메일 주소, 보내는 사람 이름 
      return message;
   }

   // 인증코드 만들기
   public String createKey() { // **public void createCode() {
      String key = "";
      Random rnd = new Random();
      for (int i = 0; i < 6; i++) { // 인증코드 6자리
         key += String.valueOf(rnd.nextInt(10));
      }
      return String.valueOf(key);
   }

   public String sendSimpleMessage(String to) throws Exception {
      String ePw = "";
      ePw = createKey();
      MimeMessage message = createMessage(to, ePw);
      try {
         javaMailSender.send(message); // 메일 발송
      } catch (MailException es) {
         es.printStackTrace();
         throw new IllegalArgumentException();
      }
      return ePw; // 메일로 보냈던 인증 코드를 서버로 리턴
   }

}
