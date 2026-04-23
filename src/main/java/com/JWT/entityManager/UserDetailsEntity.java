package com.JWT.entityManager;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "UserDetails", uniqueConstraints = { @UniqueConstraint(columnNames = "user_name") })
@Builder
public class UserDetailsEntity implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6714131659891597579L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "mob_no", unique = true)
	private String mobNo;

	@Column(name = "user_name", unique = true)
	private String userName;

	@Column(name = "Encrypt_password")
	private String password;

	@Column(name = "roles") // optional: if you store as comma-separated string
	private String roles;

	@PrePersist
	public void setDefaultRole() { // method name can be anything
		if (roles == null || roles.isEmpty()) {
			roles = "ROLE_USER";
		}
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return userName;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if (roles == null || roles.isEmpty()) {
			return List.of(); // empty list
		}
		return List.of(roles.split(",")).stream().map(SimpleGrantedAuthority::new).toList();
	}

}
