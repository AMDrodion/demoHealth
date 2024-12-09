package com.health.demo.services;

import com.health.demo.models.User;
import com.health.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		Optional<User> user = userRepository.findAll().stream().filter(u -> encoder.matches(username, u.getUsername())).findFirst();
		if (user.isEmpty()) {
			throw new UsernameNotFoundException("Пользователь не найден");
		}
		System.out.println("!!!!!" + user.get().getAuthorities().iterator().next().getAuthority());
		return user.get();
	}
}
