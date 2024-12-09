package com.health.demo.repositories;

import com.health.demo.models.People;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeopleRepository extends JpaRepository<People, Long> {
	//User findByLastName(String lastName);
}
