package com.huick.userservice.domain.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.huick.userservice.domain.entity.User;
import com.huick.userservice.domain.entity.UserPrincipal;
import com.huick.userservice.domain.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findBySocialId(username).orElseThrow(() ->
			new UsernameNotFoundException("Can not find username."));
		return UserPrincipal.from(user);
	}
}

