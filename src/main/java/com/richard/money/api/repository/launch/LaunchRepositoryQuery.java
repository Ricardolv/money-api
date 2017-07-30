package com.richard.money.api.repository.launch;

import com.richard.money.api.model.Launch;
import com.richard.money.api.repository.filter.LaunchFilter;

import java.util.List;

public interface LaunchRepositoryQuery {

    List<Launch> filter(LaunchFilter filter);

}
