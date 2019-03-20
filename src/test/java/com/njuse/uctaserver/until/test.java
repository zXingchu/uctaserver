package com.njuse.uctaserver.until;

public class test {

//    public static void main(String[] args)  {
//        String a = "hello2";
//        final String b = "hello";
//        String d = "hello";
//        String c = b + 2;
//        String e = d + 2;
//        System.out.println((c));
//        System.out.println((e));
//        System.out.println((a == c));
//        System.out.println((a == e));
//    }

    public static void main(String[] args)  {
        String a = "hello2";
        final String b = getHello();
        String c = b + 2;
        System.out.println((a == c));

    }

    public static String getHello() {
        return "hello";
    }

}
