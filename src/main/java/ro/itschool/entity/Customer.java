package ro.itschool.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Set;

@Entity
@Setter
@Getter
@EntityListeners(AuditingEntityListener.class)
public class Customer extends BaseEntity {

  @Id
  @Getter
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String username;

  private String mail;

  private String password;

  @ManyToMany(fetch = FetchType.EAGER)
  private Set<MyRole> roles;

}
