package ro.itschool.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class DemoController {

  private final UserDetailsService customerService;

  @GetMapping("/welcome")
  public String sayHello(Principal principal) {
    return principal.getName();
  }

  @GetMapping("/user")
  @PreAuthorize("hasRole('ROLE_USER')")  // Use hasRole for role-based access control
  public String sayHelloUser() {
    return "Hello Authenticated User";
  }

  @GetMapping("/admin")
  @PreAuthorize("hasRole('ADMIN')")  // Use hasRole for role-based access control
  public String sayHelloAdmin() {
    return "Hello Authenticated Admin";
  }
}
