package com.howtodoinjava.demo.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.howtodoinjava.demo.exception.RecordNotFoundException;
import com.howtodoinjava.demo.model.VolunteerEntity;
import com.howtodoinjava.demo.service.VolunteerService;

@Controller
@RequestMapping("/")
public class VolunteerMvcController 
{
	@Autowired
	VolunteerService service;

	@RequestMapping
	public String getAllVolunteers(Model model) 
	{
		List<VolunteerEntity> list = service.getAllVolunteers();

		model.addAttribute("employees", list);
		return "list-employees";
	}

	@RequestMapping(path = {"/edit", "/edit/{id}"})
	public String editVolunteerById(Model model, @PathVariable("id") Optional<Long> id) 
							throws RecordNotFoundException 
	{
		if (id.isPresent()) {
			VolunteerEntity entity = service.getVolunteerById(id.get());
			model.addAttribute("employee", entity);
		} else {
			model.addAttribute("employee", new VolunteerEntity());
		}
		return "add-edit-employee";
	}
	
	@RequestMapping(path = "/delete/{id}")
	public String deleteVolunteerById(Model model, @PathVariable("id") Long id) 
							throws RecordNotFoundException 
	{
		service.deleteVolunteerById(id);
		return "redirect:/";
	}

	@RequestMapping(path = "/createVolunteer", method = RequestMethod.POST)
	public String createOrUpdateVolunteer(VolunteerEntity employee) 
	{
		service.createOrUpdateVolunteer(employee);
		return "redirect:/";
	}
}
