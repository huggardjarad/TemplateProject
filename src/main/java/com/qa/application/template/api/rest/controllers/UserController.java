package com.qa.application.template.api.rest.controllers;

import com.qa.application.template.api.rest.AbstractRestHandler;
import com.qa.application.template.domain.User;
import com.qa.application.template.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.qa.application.template.exception.DataFormatException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RestController
@RequestMapping(value = "/template")
@Api(tags = "users")
public class UserController extends AbstractRestHandler {

    @Autowired
    private UserService UserService;

    @RequestMapping(value = "",
            method = RequestMethod.POST,
            consumes = {"application/json"},
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Create a User resource.", notes = "Returns the URL of the new resource in the Location header.")
    public void createUser(@RequestBody User User,
                            HttpServletRequest request, HttpServletResponse response) {
        User createdUser = this.UserService.createUser(User);
        response.setHeader("Location", request.getRequestURL().append("/").append(createdUser.getId()).toString());
    }

    @RequestMapping(value = "",
            method = RequestMethod.GET,
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get a paginated list of all Users.", notes = "The list is paginated. You can provide a page number (default 0) and a page size (default 100)")
    public
    @ResponseBody
    Page<User> getAllUser(@ApiParam(value = "The page number (zero-based)", required = true)
                            @RequestParam(value = "page", required = true, defaultValue = DEFAULT_PAGE_NUM) Integer page,
                            @ApiParam(value = "Tha page size", required = true)
                            @RequestParam(value = "size", required = true, defaultValue = DEFAULT_PAGE_SIZE) Integer size,
                            HttpServletRequest request, HttpServletResponse response) {
        return this.UserService.getAllUsers(page, size);
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET,
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get a single User.", notes = "You have to provide a valid User ID.")
    public
    @ResponseBody
    User getUser(@ApiParam(value = "The ID of the User.", required = true)
                   @PathVariable("id") Long id,
                   HttpServletRequest request, HttpServletResponse response) throws Exception {
        User User = this.UserService.getUser(id);
        checkResourceFound(User);
        //todo: http://goo.gl/6iNAkz
        return User;
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.PUT,
            consumes = {"application/json"},
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Update a User resource.", notes = "You have to provide a valid User ID in the URL and in the payload. The ID attribute can not be updated.")
    public void updateUser(@ApiParam(value = "The ID of the existing User resource.", required = true)
                            @PathVariable("id") Long id, @RequestBody User User,
                            HttpServletRequest request, HttpServletResponse response) {
        checkResourceFound(this.UserService.getUser(id));
        if (id != User.getId()) throw new DataFormatException("ID doesn't match!");
        this.UserService.updateUser(User);
    }

    //todo: @ApiImplicitParams, @ApiResponses
    @RequestMapping(value = "/{id}",
            method = RequestMethod.DELETE,
            produces = {"application/json"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Delete a User resource.", notes = "You have to provide a valid User ID in the URL. Once deleted the resource can not be recovered.")
    public void deleteUser(@ApiParam(value = "The ID of the existing User resource.", required = true)
                            @PathVariable("id") Long id, HttpServletRequest request,
                            HttpServletResponse response) {
        checkResourceFound(this.UserService.getUser(id));
        this.UserService.deleteUser(id);
    }
}
