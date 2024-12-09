package com.health.demo.controllers;

import com.health.demo.models.Diagnosis;
import com.health.demo.repositories.DiagnosisRepository;
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
@RequestMapping("/diagnoses")
public class DiagnosisController {

	@Autowired
	private DiagnosisRepository diagnosisRepository;
	@Autowired
	private WardService wardService;

	@GetMapping
	public String listDiagnoses(Model model) {
		List<Diagnosis> diagnoses = diagnosisRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
		model.addAttribute("diagnoses", diagnoses);
		List<Map<String, Object>> wardsOc = wardService.getWardDetails();
		model.addAttribute("wards_oc", wardsOc);

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication.getAuthorities().stream()
						.anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {
			return "adminDiagnosisList";
		} else {
			return "userDiagnosisList";
		}
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/new")
	public String createDiagnosisForm(Model model) {
		List<Map<String, Object>> wardsOc = wardService.getWardDetails();
		model.addAttribute("wards_oc", wardsOc);
		model.addAttribute("diagnosis", new Diagnosis());
		return "diagnosisForm";
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/new")
	public String saveDiagnosis(@ModelAttribute Diagnosis diagnosis, Model model) {
		try {
			diagnosisRepository.save(diagnosis);
		} catch (Exception e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "error";
		}
		return "redirect:/diagnoses";
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/edit/{id}")
	public String editDiagnosisForm(@PathVariable Integer id, Model model) {
		List<Map<String, Object>> wardsOc = wardService.getWardDetails();
		model.addAttribute("wards_oc", wardsOc);
		Diagnosis diagnosis = diagnosisRepository.findById(id)
						.orElseThrow(() -> new IllegalArgumentException("Invalid diagnosis Id:" + id));
		model.addAttribute("diagnosis", diagnosis);
		return "diagnosisForm";
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/edit/{id}")
	public String updateDiagnosis(@PathVariable Integer id, @ModelAttribute Diagnosis diagnosis, Model model) {
		diagnosis.setId(id);
		try {
			diagnosisRepository.save(diagnosis);
		} catch (Exception e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "error";
		}
		return "redirect:/diagnoses";
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/delete/{id}")
	public String deleteDiagnosis(@PathVariable Integer id, Model model) {
		diagnosisRepository.deleteById(id);
		try {
			diagnosisRepository.deleteById(id);
		} catch (Exception e) {
			model.addAttribute("errorMessage", e.getMessage());
			return "error";
		}
		return "redirect:/diagnoses";
	}
}
