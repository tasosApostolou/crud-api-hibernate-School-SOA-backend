package gr.aueb.cf.schoolapp.rest;

import gr.aueb.cf.schoolapp.dto.usersDTO.UserInsertDTO;
import gr.aueb.cf.schoolapp.dto.usersDTO.UserReadOnlyDTO;
import gr.aueb.cf.schoolapp.dto.usersDTO.UserUpdateDTO;
import gr.aueb.cf.schoolapp.mapper.Mapper;
import gr.aueb.cf.schoolapp.model.Student;
import gr.aueb.cf.schoolapp.model.User;
import gr.aueb.cf.schoolapp.service.IStudentService;
import gr.aueb.cf.schoolapp.service.IUserService;
import gr.aueb.cf.schoolapp.service.exceptions.EntityNotFoundException;
import gr.aueb.cf.schoolapp.validator.ValidatorUtil;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Path("/users")
public class UserRestController {

    @Inject
    private IUserService userService;

    @Path("/")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsersByUsername(@QueryParam("username") String username) {
        List<User> users;
        try {
            users = userService.getUsersByUsername(username);
            List<UserReadOnlyDTO> usersDTO = new ArrayList<>();
            for (User user : users) {
                usersDTO.add(Mapper.mapToReadOnlyDTO(user));
            }
            return Response.status(Response.Status.OK).entity(usersDTO).build();
        } catch (EntityNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity("NOT FOUND").build();
        }
    }

    @Path("/{userId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("userId") Long userId) {
        User user;
        try {
            user = userService.getUserById(userId);

            UserReadOnlyDTO userDTO = Mapper.mapToReadOnlyDTO(user);
            return Response.status(Response.Status.OK).entity(userDTO).build();
        } catch (EntityNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity("NOT FOUND").build();
        }
    }

    @Path("/")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addUser(UserInsertDTO dto, @Context UriInfo uriInfo) {
        List<String> errors = ValidatorUtil.validateDTO(dto);
        if (!errors.isEmpty()) return Response
                .status(Response.Status.BAD_REQUEST).entity(errors).build();

        try {
            User user = userService.insertUser(dto);

//            User user = userService.insertUser(new UserInsertDTO(dto.getUsername(), SecUtil.hashPassword(dto.getPassword()),dto.getRole()));
            UserReadOnlyDTO userDTO= Mapper.mapToReadOnlyDTO(user);

            UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
            return Response.created(uriBuilder.path(Long.toString(userDTO.getId())).build())
                    .entity(userDTO).build();
        } catch (Exception e) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity("User Error in insert")
                    .build();
        }
    }

    @Path("/{userId}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUser(@PathParam("userId") Long userId) {
        try {
            User user = userService.getUserById(userId);
            userService.deleteUser(userId);
            UserReadOnlyDTO studentDTO = Mapper.mapToReadOnlyDTO(user);
            return Response.status(Response.Status.OK).entity(studentDTO).build();
        } catch (EntityNotFoundException e1) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity("User Not Found")
                    .build();
        }
    }

    @Path("/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(@PathParam("id") Long id, UserUpdateDTO dto){
        List<String> errors = ValidatorUtil.validateDTO(dto);
        if(!Objects.equals(dto.getId(),id)){
            return Response.status(Response.Status.UNAUTHORIZED).entity("Unothorized").build();
        }
        if(!errors.isEmpty()){
            return Response.status(Response.Status.BAD_REQUEST).entity(errors).build();
        }
        try {


            User user = userService.updateUser(dto);
           UserReadOnlyDTO readOnlyDTO = Mapper.mapToReadOnlyDTO(user);
            return Response.status(Response.Status.OK).entity(readOnlyDTO).build();
        }catch (EntityNotFoundException e){
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }

    }

}

