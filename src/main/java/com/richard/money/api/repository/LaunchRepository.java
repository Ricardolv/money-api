package com.richard.money.api.repository;

import com.richard.money.api.model.Launch;
import com.richard.money.api.repository.launch.LaunchRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LaunchRepository extends JpaRepository<Launch, Long>, LaunchRepositoryQuery {

}
