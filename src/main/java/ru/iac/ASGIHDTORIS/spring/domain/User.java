package ru.iac.ASGIHDTORIS.spring.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity(name = "usr")
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "fio")
    private String fio;

    @Column(name = "secret_key")
    private String secretKey;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    public static User createEmptyUser() {
        return User.builder().id(-1L).secretKey("").fio("").build();
    }

    public boolean isEmpty() {
        return id == -1L && secretKey.equals("") && fio.equals("");
    }

}
