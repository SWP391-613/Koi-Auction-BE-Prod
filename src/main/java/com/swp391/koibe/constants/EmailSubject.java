package com.swp391.koibe.constants;

public class EmailSubject {

    public static String subjectGreeting(String name) {
        return """
            Koi Auction  Corporation - Welcome %s, thanks for joining us!
            """.formatted(name);
    }

    public static String subjectForgotPassword(String name) {
        return """
            Koi Auction  Corporation - Hi %s, we're here to help you get the password back!
            """.formatted(name);
    }

    public static String subjectRunningApp() {
        return """
            Koi Auction  Corporation - Your app is running, Happy Coding!
            """;
    }

    public static String subjectBlockEmail(String name){
        return """
            Koi Auction  Corporation - %s, your account has been blocked!
            """.formatted(name);
    }

}