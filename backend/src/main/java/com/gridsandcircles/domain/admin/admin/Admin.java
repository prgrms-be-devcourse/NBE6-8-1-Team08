package com.gridsandcircles.domain.admin.admin;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Admin {

  @Id
  @Column(nullable = false)
  private String adminId;

  @Column(nullable = false)
  private String password;
}
