package com.richard.money.api.repository.launch;

import com.richard.money.api.model.Launch;
import com.richard.money.api.repository.filter.LaunchFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LaunchRepositoryQuery {

    Page<Launch> filter(LaunchFilter filter, Pageable pageable);

}
