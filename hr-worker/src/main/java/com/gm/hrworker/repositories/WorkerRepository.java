package com.gm.hrworker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gm.hrworker.entities.Worker;

public interface WorkerRepository extends JpaRepository<Worker, Long>{

}
