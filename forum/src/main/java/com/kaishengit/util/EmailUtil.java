package com.kaishengit.util;

import org.apache.commons.mail.*;

/**
 * Created by liu on 2016/12/16.
 */
public class EmailUtil {

    private static HtmlEmail email = new HtmlEmail();
    public static void sendHttpemail(String html,String subject ,String userEmail){
        email.setHostName(Config.get("email.smpt"));
        email.setAuthentication(Config.get("email.username"),Config.get("email.password"));
        email.setCharset("UTF-8");
        email.setStartTLSEnabled(true);

        try {
            email.setFrom(Config.get("email.frommail"));
            email.setSubject(subject);
            email.setHtmlMsg(html);
            email.addTo(userEmail);
            email.send();
        } catch (EmailException e) {
            e.printStackTrace();
        }
    }

}
