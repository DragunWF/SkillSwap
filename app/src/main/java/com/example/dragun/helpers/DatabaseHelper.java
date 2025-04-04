package com.example.dragun.helpers;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.dragun.R;
import com.example.dragun.data.Posting;
import com.example.dragun.data.Bookmark;
import com.example.dragun.data.Request;
import com.example.dragun.data.User;
import com.example.dragun.services.PostingService;
import com.example.dragun.services.UserService;

public class DatabaseHelper {
    private static final String FILE_KEY = "db";

    private static SharedPreferences sharedPref;
    private static SharedPreferences.Editor editor;

    private static ModelBank<User> usersBank;
    private static ModelBank<Posting> postingBank;
    private static ModelBank<Bookmark> bookmarkBank;
    private static ModelBank<Request> requestBank;

    public static void initialize(Context context) {
        sharedPref = context.getSharedPreferences(FILE_KEY, Context.MODE_PRIVATE);
        editor = sharedPref.edit();

        usersBank = new ModelBank<>(sharedPref, editor, "users", User.class);
        postingBank = new ModelBank<>(sharedPref, editor, "postings", Posting.class);
        bookmarkBank = new ModelBank<>(sharedPref, editor, "bookmarks", Bookmark.class);
        requestBank = new ModelBank<>(sharedPref, editor, "requests", Request.class);

        if (usersBank.getAll().isEmpty()) {
            addInitialUsers();
        }
        if (postingBank.getAll().isEmpty()) {
            addPostingData();
        }
    }

    public static ModelBank<User> getUsersBank() {
        return usersBank;
    }

    public static ModelBank<Posting> getPostingBank() {
        return postingBank;
    }

    public static ModelBank<Bookmark> getBookmarkBank() {
        return bookmarkBank;
    }

    public static ModelBank<Request> getRequestBank() {
        return requestBank;
    }

    public static void addInitialUsers() {
        UserService.register(new User(
                "LorainePotato",
                "123",
                "lowreign@gmail.com",
                "09667834495",
                "Graduate of Business Finance, Has experience on public speaking, I would like to teach others of the skills that I have in order to contribute in a small way to society"));
        UserService.register(new User(
                "Sikyel Escuberza",
                "1234",
                "Syk@gmail.com",
                "09277564498",
                "Graduate of Information Technology, Currently working as a Senior Programmer, Open to teaching new programmers on how to get into the world of programming"));
        UserService.register(new User(
                "Marshall Mersene",
                "12345",
                "Mershell@gmail.com",
                "09997564498",
                "Former Boxer, Owner of a Gym at Mandaluyong City"));
        UserService.register(new User(
                "Cloud Strife",
                "123456",
                "SephirothKys@gmail.com",
                "0927984498",
                "Interior Designer and Art Enthusiast who has a spare time on week-ends open to teach others"));
    }

    public static void addPostingData() {
        PostingService.add(new Posting(1,"Time Management Strategies",
                "Business and Finance",
                "People often get lost in time, and time is a valuable thing. It is important to manage one own time," +
                        "I will teach you ways to manage your time in order to not waste precious moments. We would cover simple ways to methods that can be adapt in your daily lives",
                "17 Days",
                "Discord:LowreignPotato, Facebook:LowreignPotato123, Whatsapp",
                "Intermediate"));
        PostingService.add(new Posting(1,"Public Speaking and Confidence Building",
                "Language and Communication",
                "Speaking in front of strangers can be difficult, but they are people like you and i. This would start you with ways to build confindence" +
                        "in front of others to ways in order to attract the attention of your crowd to make them understand what you mean.",
                "2 Months",
                "Discord: User: LowreignPotato23, Phone",
                "Advanced"));
        PostingService.add(new Posting(3,"Strength Training for Beginners",
                "Fitness and Wellness",
                "Training your body is a process. One needs to start in order to begin their journey. I would teach you simple ways in order to build up strength. " +
                        "You can also visit me at my gym at Highway Hills, Mandaluyong City",
                "2 Weeks",
                "Facebook:MershellMersene, Email, Discord:Astreous, In-Person",
                "Beginner"));
        PostingService.add(new Posting(2,"Intro to Python",
                "Technology and Coding",
                "Python is used in many aspects of technology. I would teach you simple things about Python such as Functions, Variables, Data types, etc., or other aspects of Python" +
                        "This course is open for those completely new to programming or those who has experience on other languages",
                "1 Month",
                "Discord, Phone, Facebook",
                "Beginner"));
        PostingService.add(new Posting(2,"DIY Home Decor",
                "Arts and Crafts",
                "Home decors that I can teach includes Plant decors, Book Shelf decors, etc. Open to communication on what decors are you interested on making that I might know.",
                "9 Hours",
                "Email, Phone",
                "Advanced"));
        PostingService.add(new Posting(3,"Meditation Techniques",
                "Fitness and Wellness",
                "Meditation can help clear ones mind in order to control your thoughts. I will teach you ways that can make yourself calm down on stressful situations or breathing techniques" +
                        "to clear your mind",
                "2 Weeks",
                "Email, Phone",
                "Intermediate"));
        PostingService.add(new Posting(3,"Web Development Basics",
                "Technology and Coding",
                "Web is viewed by many is the main way of reaching others in the web. This would help you start with the basics on developing websites by covering Html, Css, and JS",
                "17 Days",
                "Email, Phone",
                "Basic"));
        PostingService.add(new Posting(4,"Digital Illustration for Beginners",
                "Arts and Crafts",
                "Tutor for basics of creating digital illustrations through numerous software like photoshop, adobe, etc",
                "2 Weeks",
                "Email",
                "Basic"));
        PostingService.add(new Posting(2,"How to Set Up a Smart Home",
                "Technology and Coding",
                "Adapt to the modern living by setting up your smart home. Could help with what Smart Appliances to buy as well as installation tutorial",
                "4 Weeks",
                "Phone",
                "Intermediate"));
        PostingService.add(new Posting(1,"Writing a Resume that Stands Out",
                "Language and Communication",
                "Learn how to write the perfect resume to describe who you are and what your accomplishes are. Standing out from the crowd is crucial to be picked for a job interview. This session would help you on bettering " +
                "your professional career",
                "3 Days",
                "Phone, Email, Discord",
                "Intermediate"));
    }

    public static void clear() {
        editor.clear();
        editor.apply();
    }
}
