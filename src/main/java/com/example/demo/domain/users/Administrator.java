package com.example.demo.domain.users;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Administrator extends User {
    public Administrator(long id, String name, String email) {
        super(id, name,email, "Administrator");
    }
}
