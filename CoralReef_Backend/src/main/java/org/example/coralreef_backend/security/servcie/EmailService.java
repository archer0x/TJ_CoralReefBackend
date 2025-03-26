package org.example.coralreef_backend.security.servcie;

import jakarta.servlet.http.HttpSession;
import org.example.coralreef_backend.entity.Email;

public interface EmailService {

    boolean sendMimeMail( String email, HttpSession session);

    boolean registered(Email emailJudge, HttpSession session);
}
