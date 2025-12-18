package com.racks.racks.repository;

import com.racks.racks.model.Clothing;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ClothingRepository extends JpaRepository<Clothing, Long> {
    List<Clothing> findByStatus(String status);
}