package com.springboot.usuarios.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.springboot.usuarios.app.annotation.Unique;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
public class User implements Serializable {

    private static final long serialVersionUID = 2791475845687469572L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @NotEmpty(message = "No puede ser vacio, favor ingrese el campo")
    @Size(min = 4, max = 20, message = "debe tener una longitud entre 4 y 20 caracteres")
    private String name;
    @Column(nullable = false)
    @NotEmpty(message = "No puede ser vacio, favor ingrese el campo")
    @Email(message = "debe tener un formato de email valido")
    @Unique(message = "ya esta registrado")
    @JsonProperty( value = "email", access = JsonProperty.Access.WRITE_ONLY)
    private String email;
    @Column(name = "created")
    @Temporal(TemporalType.DATE)
    private Date created;
    @Column(name = "modified")
    @Temporal(TemporalType.DATE)
    private Date modified;
    @Column(name = "last_login")
    @Temporal(TemporalType.DATE)
    private Date lastLogin;
    @Column(length = 60)
    @Pattern(regexp = "^(?=(?:.*?[0-9]){2})(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$", message = "invalida")
    @JsonProperty( value = "password", access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @Column
    private Boolean active;
    @Column
    private String token;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonProperty( value = "phones", access = JsonProperty.Access.WRITE_ONLY)
    private List<Phone> phones;

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

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

}
