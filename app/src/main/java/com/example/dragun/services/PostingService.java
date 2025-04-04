package com.example.dragun.services;

import android.se.omapi.Session;

import com.example.dragun.data.Bookmark;
import com.example.dragun.data.Posting;
import com.example.dragun.helpers.DatabaseHelper;
import com.example.dragun.helpers.ModelBank;

import java.util.ArrayList;
import java.util.List;

public class PostingService {
    public static List<Posting> getUserPostings() {
        ModelBank<Posting> bank = DatabaseHelper.getPostingBank();
        List<Posting> output = new ArrayList<>();
        for (Posting posting : bank.getAll()) {
            if (posting.getUserId() == SessionService.getCurrentUserId()) {
                output.add(posting);
            }
        }
        return output;
    }

    public static List<Posting> getBookmarks() {
        List<Posting> results = new ArrayList<>();
        ModelBank<Bookmark> bookmarkBank = DatabaseHelper.getBookmarkBank();
        for (Bookmark bookmark : bookmarkBank.getAll()) {
            if (bookmark.getUserId() == SessionService.getCurrentUserId()) {
                results.add(bookmark.getPosting());
            }
        }
        return results;
    }

    public static boolean toggleBookmark(Posting posting) {
        ModelBank<Bookmark> bank = DatabaseHelper.getBookmarkBank();
        boolean isExists = false;
        int bookmarkId = 1;
        for (Bookmark bookmark : bank.getAll()) {
            if (SessionService.getCurrentUserId() == bookmark.getUserId() && bookmark.getPosting().getId() == posting.getId()) {
                isExists = true;
                bookmarkId = bookmark.getId();
                break;
            }
        }
        if (isExists) {
            bank.delete(bookmarkId);
            return false;
        }
        bank.add(new Bookmark(SessionService.getCurrentUserId(), posting));
        return true;
    }

    public static Posting get(int id) {
        return DatabaseHelper.getPostingBank().get(id);
    }

    public static void add(Posting posting) {
        ModelBank<Posting> bank = DatabaseHelper.getPostingBank();
        bank.add(posting);
    }

    public static void edit(Posting posting) {
        ModelBank<Posting> bank = DatabaseHelper.getPostingBank();
        bank.update(posting);
    }

    public static void delete(int id) {
        ModelBank<Posting> bank = DatabaseHelper.getPostingBank();
        bank.delete(id);
    }

    public static void bookmark(Posting posting) {

    }
}
