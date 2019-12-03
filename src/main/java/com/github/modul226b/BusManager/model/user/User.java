package com.github.modul226b.BusManager.model.user;

import com.github.modul226b.BusManager.model.account.Account;

import java.util.List;

public class User {
    private String username;
    private String firstName;
    private String lastName;
    private String mail;
    private String description;
    private Rank rank;
    private List<Account> accounts;
}
