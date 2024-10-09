package ro.itschool.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ro.itschool.entity.MyUserDetails;
import ro.itschool.repository.CustomerRepository;

@Service
@RequiredArgsConstructor
public class CustomerService implements UserDetailsService {

  private final CustomerRepository customerRepository;

  @Override
  public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
    var customer = customerRepository.findByUsername(username);
    if (customer.isEmpty()) {
      throw new RuntimeException("User not found");
    }
    return new MyUserDetails(customer.get());
  }
}
