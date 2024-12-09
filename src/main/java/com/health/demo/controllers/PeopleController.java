package com.health.demo.controllers;

import com.health.demo.models.People;
import com.health.demo.repositories.PeopleRepository;
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
@RequestMapping("/people")
public class PeopleController {

	@Autowired
	private PeopleRepository peopleRepository;
	@Autowired
	private WardService wardService;

	@GetMapping
	public String listPeople(Model model) {
		List<People> people = peopleRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
		model.addAttribute("people", people);

		List<Map<String, Object>> wardsOc = wardService.getWardDetails();
		model.addAttribute("wards_oc", wardsOc);

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication.getAuthorities().stream()
						.anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {
			return "adminPeopleList";
		} else {
			return "userPeopleList";
		}
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/new")
	public String createPeopleForm(Model model) {
		List<Map<String, Object>> wardsOc = wardService.getWardDetails();
		model.addAttribute("wards_oc", wardsOc);
		model.addAttribute("people", new People());
		return "peopleForm";
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/new")
	public String savePeople(@ModelAttribute People people, Model model) {
		try {
			peopleRepository.save(people);
		} catch (Exception e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "error";
		}
		return "redirect:/people";
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/edit/{id}")
	public String editPeopleForm(@PathVariable Long id, Model model) {
		List<Map<String, Object>> wardsOc = wardService.getWardDetails();
		model.addAttribute("wards_oc", wardsOc);
		People people = peopleRepository.findById(id)
						.orElseThrow(() -> new IllegalArgumentException("Invalid people Id:" + id));
		model.addAttribute("people", people);
		return "peopleForm";
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/edit/{id}")
	public String updatePeople(@PathVariable Long id, @ModelAttribute People people, Model model) {
		people.setId(id);
		try {
			peopleRepository.save(people);
		} catch (Exception e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "error";
		}
		return "redirect:/people";
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping ("/delete/{id}")
	public String deletePeople(@PathVariable Long id, Model model) {
		try {
			peopleRepository.deleteById(id);
		} catch (Exception e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "error";
		}
		return "redirect:/people";
	}
}
