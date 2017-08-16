package com.richard.money.api.service;

import com.richard.money.api.model.Launch;
import com.richard.money.api.model.Person;
import com.richard.money.api.repository.LaunchRepository;
import com.richard.money.api.repository.PersonRepository;
import com.richard.money.api.repository.filter.LaunchFilter;
import com.richard.money.api.repository.projection.ResumeLaunch;
import com.richard.money.api.service.exception.PersonNonexistentOrInactiveException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class LaunchService {

    @Autowired
    private LaunchRepository launchRepository;

    @Autowired
    private PersonRepository personRepository;

    public Launch save(Launch launch) {
        Person person = personRepository.findOne(launch.getPerson().getCode());
        if (null == person || person.isInactive()) {
            throw new PersonNonexistentOrInactiveException();
        }

        return launchRepository.save(launch);
    }

    public Launch findOne(Long code) {
        return searchLaunchByCode(code);
    }

    public Page<Launch> filter(LaunchFilter filter, Pageable pageable) {
        return launchRepository.filter(filter, pageable);
    }

    public Page<ResumeLaunch> resume(LaunchFilter filter, Pageable pageable) {
        return launchRepository.resume(filter, pageable);
    }

    private Launch searchLaunchByCode(Long code) {
        Launch launchSave = launchRepository.findOne(code);
        if (null == launchSave) {
            throw new EmptyResultDataAccessException(1);
        }
        return launchSave;
    }

    public void delete(Long code) {
        launchRepository.delete(code);
    }

    public Launch update(Long code, Launch launch) {
        Launch launchSave = searchLaunchExist(code);
        if (!launch.getPerson().equals(launchSave.getPerson())) {
            valid(launch);
        }

        BeanUtils.copyProperties(launch, launchSave, "code");

        return launchRepository.save(launchSave);
    }

    private void valid(Launch launch) {
        Person person = null;
        if (launch.getPerson().getCode() != null) {
            person = personRepository.findOne(launch.getPerson().getCode());
        }

        if (person == null || person.isInactive()) {
            throw new PersonNonexistentOrInactiveException();
        }
    }

    private Launch searchLaunchExist(Long code) {
        Launch launchSave = launchRepository.findOne(code);

        if (launchSave == null) {
            throw new IllegalArgumentException();
        }

        return launchSave;
    }
}
