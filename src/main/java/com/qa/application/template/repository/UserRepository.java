package com.qa.application.template.repository;

import com.qa.application.template.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    User findUserById(Long id);
    Page findAll(Pageable pageable);

}
