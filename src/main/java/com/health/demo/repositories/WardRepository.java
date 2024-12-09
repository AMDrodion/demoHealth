package com.health.demo.repositories;

import com.health.demo.models.Ward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface WardRepository extends JpaRepository<Ward, Integer> {

	@Query(value = "SELECT * FROM get_ward_details()", nativeQuery = true)
	List<Map<String, Object>> findWardOccupancies();
}
