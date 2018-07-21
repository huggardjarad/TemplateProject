package com.qa.application.template.service;


import com.qa.application.template.domain.User;
import com.qa.application.template.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepo;

    @Qualifier("counterService")
    @Autowired
    CounterService counterService;

    @Qualifier("gaugeService")
    @Autowired
    GaugeService gaugeService;

    public UserService(){

    }

    public User createUser(User user) {return userRepo.save(user);}

    public User getUser(long id){ return userRepo.findOne(id);}

    public void updateUser(User user){  userRepo.save(user);}

    public void deleteUser(Long id){userRepo.delete(id);}

    public Page<User> getAllUsers(Integer page, Integer size){
        Page pageOfUsers = userRepo.findAll(new PageRequest(page, size));
        if (size > 50) {
            counterService.increment("Application.UserSevice.getAll.largePayload");
        }
        return pageOfUsers;
        }
    }

