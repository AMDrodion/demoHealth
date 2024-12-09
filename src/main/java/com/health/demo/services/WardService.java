package com.health.demo.services;

import com.health.demo.repositories.WardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class WardService {

	private final WardRepository wardRepository;

	@Autowired
	public WardService(WardRepository wardRepository) {
		this.wardRepository = wardRepository;
	}

	public List<Map<String, Object>> getWardDetails() {
		return wardRepository.findWardOccupancies();
	}
}
