package com.swp391.koibe.constants;

public class EmailSubject {

    public static String subjectGreeting(String name) {
        return """
            Java Snake Game Corporation - Welcome %s, thanks for joining us!
            """.formatted(name);
    }

    public static String subjectRunningApp() {
        return """
            Java Snake Game Corporation - Your app is running, Happy Coding!
            """;
    }

    public static String subjectBlockEmail(String name){
        return """
            Java Snake Game Corporation - %s, your account has been blocked!
            """.formatted(name);
    }

}