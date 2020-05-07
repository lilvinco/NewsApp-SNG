package com.startng.newsapp;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {

    public User(String firstname, String lastName, String email){
        this.firstname = firstname;
        this.lastName = lastName;
        this.email = email;
    }

    @PrimaryKey(autoGenerate = true)
        private int id;

    @ColumnInfo(name = "first_name")
        private String firstname;

    @ColumnInfo(name = "last_name")
        private String lastName;

    @ColumnInfo(name = "email")
        private String email;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getFirstname() {
            return firstname;
        }

        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }


