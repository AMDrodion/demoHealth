package com.health.demo.controllers;

import com.health.demo.models.Ward;
import com.health.demo.repositories.WardRepository;
import com.health.demo.services.WardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/wards")
public class WardController {

	@Autowired
	private WardRepository wardRepository;
	@Autowired
	private WardService wardService;

	@GetMapping
	public String listWards(Model model) {
		List<Ward> wards = wardRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
		model.addAttribute("wards", wards);
		List<Map<String, Object>> wardsOc = wardService.getWardDetails();
		model.addAttribute("wards_oc", wardsOc);

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication.getAuthorities().stream()
						.anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {
			return "adminWardList";
		} else {
			return "userWardList";
		}
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/new")
	public String createWardForm(Model model) {
		List<Map<String, Object>> wardsOc = wardService.getWardDetails();
		model.addAttribute("wards_oc", wardsOc);
		model.addAttribute("ward", new Ward());
		return "wardForm";
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/new")
	public String saveWard(@ModelAttribute Ward ward, Model model) {
		try {
			wardRepository.save(ward);
		} catch (Exception e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "error";
		}
		return "redirect:/wards";
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/edit/{id}")
	public String editWardForm(@PathVariable Integer id, Model model) {
		List<Map<String, Object>> wardsOc = wardService.getWardDetails();
		model.addAttribute("wards_oc", wardsOc);
		Ward ward = wardRepository.findById(id)
						.orElseThrow(() -> new IllegalArgumentException("Invalid ward Id:" + id));
		model.addAttribute("ward", ward);
		return "wardForm";
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/edit/{id}")
	public String updateWard(@PathVariable Integer id, @ModelAttribute Ward ward, Model model) {
		ward.setId(id);
		try {
			wardRepository.save(ward);
		} catch (Exception e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "error";
		}
		return "redirect:/wards";
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/delete/{id}")
	public String deleteWard(@PathVariable Integer id, Model model) {
		try {
			wardRepository.deleteById(id);
		} catch (Exception e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "error";
		}
		return "redirect:/wards";
	}
}
