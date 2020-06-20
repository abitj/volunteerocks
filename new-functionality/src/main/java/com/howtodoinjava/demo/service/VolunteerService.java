package com.howtodoinjava.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.howtodoinjava.demo.exception.RecordNotFoundException;
import com.howtodoinjava.demo.model.VolunteerEntity;
import com.howtodoinjava.demo.repository.VolunteerRepository;

@Service
public class VolunteerService {
	
	@Autowired
	VolunteerRepository repository;
	
	public List<VolunteerEntity> getAllVolunteers()
	{
		List<VolunteerEntity> result = (List<VolunteerEntity>) repository.findAll();
		
		if(result.size() > 0) {
			return result;
		} else {
			return new ArrayList<VolunteerEntity>();
		}
	}
	
	public VolunteerEntity getVolunteerById(Long id) throws RecordNotFoundException 
	{
		Optional<VolunteerEntity> employee = repository.findById(id);
		
		if(employee.isPresent()) {
			return employee.get();
		} else {
			throw new RecordNotFoundException("No employee record exist for given id");
		}
	}
	
	public VolunteerEntity createOrUpdateVolunteer(VolunteerEntity entity) 
	{
		if(entity.getId()  == null) 
		{
			entity = repository.save(entity);
			
			return entity;
		} 
		else 
		{
			Optional<VolunteerEntity> employee = repository.findById(entity.getId());
			
			if(employee.isPresent()) 
			{
				VolunteerEntity newEntity = employee.get();
				newEntity.setVolEmail(entity.getVolEmail());
				newEntity.setVolFullName(entity.getVolFullName());
				newEntity.setVolLocation(entity.getVolLocation());

				newEntity = repository.save(newEntity);
				
				return newEntity;
			} else {
				entity = repository.save(entity);
				
				return entity;
			}
		}
	} 
	
	public void deleteVolunteerById(Long id) throws RecordNotFoundException 
	{
		Optional<VolunteerEntity> employee = repository.findById(id);
		
		if(employee.isPresent()) 
		{
			repository.deleteById(id);
		} else {
			throw new RecordNotFoundException("No employee record exist for given id");
		}
	} 
}