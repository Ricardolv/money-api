package com.richard.money.api.repository.launch;

import com.richard.money.api.model.Launch;
import com.richard.money.api.repository.filter.LaunchFilter;
import com.richard.money.api.repository.projection.ResumeLaunch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LaunchRepositoryQuery {

    Page<Launch> filter(LaunchFilter filter, Pageable pageable);
    Page<ResumeLaunch> resume(LaunchFilter filter, Pageable pageable);
}
