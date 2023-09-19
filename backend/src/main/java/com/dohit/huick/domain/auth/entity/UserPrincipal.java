package com.dohit.huick.domain.auth.entity;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.dohit.huick.domain.auth.constant.Role;
import com.dohit.huick.domain.auth.constant.SocialType;
import com.dohit.huick.domain.user.entity.User;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class UserPrincipal implements OAuth2User, UserDetails, OidcUser {
	private final Long userId;
	private final String socialId;
	private final SocialType socialType;
	private final Role role;
	private final Collection<GrantedAuthority> authorities;
	private Map<String, Object> attributes;

	@Override
	public Map<String, Object> getAttributes() {
		return attributes;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return null;
	}

	@Override
	public String getName() {
		return String.valueOf(userId);
	}

	@Override
	public String getUsername() {
		return String.valueOf(userId);
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public Map<String, Object> getClaims() {
		return null;
	}

	@Override
	public OidcUserInfo getUserInfo() {
		return null;
	}

	@Override
	public OidcIdToken getIdToken() {
		return null;
	}

	@Builder
	private UserPrincipal(Long userId, String socialId, SocialType socialType, Role role,
		Collection<GrantedAuthority> authorities, Map<String, Object> attributes) {
		this.userId = userId;
		this.socialId = socialId;
		this.socialType = socialType;
		this.role = role;
		this.authorities = authorities;
		this.attributes = attributes;
	}

	public static UserPrincipal from(User user) {
		return UserPrincipal.builder()
			.userId(user.getUserId())
			.socialId(user.getSocialId())
			.socialType(user.getSocialType())
			.role(Role.USER)
			.authorities(Collections.singletonList(new SimpleGrantedAuthority(Role.USER.getCode())))
			.build();
	}

	public static UserPrincipal of(User user, Map<String, Object> attributes) {
		return UserPrincipal.builder()
			.userId(user.getUserId())
			.socialId(user.getSocialId())
			.socialType(user.getSocialType())
			.role(Role.USER)
			.authorities(Collections.singletonList(new SimpleGrantedAuthority(Role.USER.getCode())))
			.attributes(attributes)
			.build();
	}

}
