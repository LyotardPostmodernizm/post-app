package com.project.postapp.requests;

import lombok.Data;

@Data
public class UserRequest {
    String userName;   //Login ve register olurken lazım olacak şeyler sadece bunlar.
    String password;
}
