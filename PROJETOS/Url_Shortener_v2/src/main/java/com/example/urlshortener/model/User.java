package com.example.urlshortener.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String email;

  @Column(nullable = false)
  private String password;

  // Construtor padrão
  public User() {
  }

  // Getters e Setters
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  // Implementando equals e hashCode
  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof User)) return false;
    if (!super.equals(o)) return false;

    User user = (User) o;

    if (!id.equals(user.id)) return false;
    if (!name.equals(user.name)) return false;
    if (!email.equals(user.email)) return false;
    return password.equals(user.password);
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + id.hashCode();
    result = 31 * result + name.hashCode();
    result = 31 * result + email.hashCode();
    result = 31 * result + password.hashCode();
    return result;
  }

  // Método toString para facilitar a visualização do objeto
  @Override
  public String toString() {
    return "User{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", email='" + email + '\'' +
            ", password='" + password + '\'' +
            "} " + super.toString();
  }
}
