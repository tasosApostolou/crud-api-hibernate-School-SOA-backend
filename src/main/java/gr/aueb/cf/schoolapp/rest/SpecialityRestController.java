package gr.aueb.cf.schoolapp.rest;

import gr.aueb.cf.schoolapp.dto.specialitiesDTO.SpecialityInsertDTO;
import gr.aueb.cf.schoolapp.dto.specialitiesDTO.SpecialityReadOnlyDTO;
import gr.aueb.cf.schoolapp.dto.specialitiesDTO.SpecialityUpdateDTO;
import gr.aueb.cf.schoolapp.dto.teachersDTO.TeacherInsertDTO;
import gr.aueb.cf.schoolapp.dto.teachersDTO.TeacherReadOnlyDTO;
import gr.aueb.cf.schoolapp.dto.teachersDTO.TeacherUpdateDTO;
import gr.aueb.cf.schoolapp.mapper.Mapper;
import gr.aueb.cf.schoolapp.model.Speciality;
import gr.aueb.cf.schoolapp.model.Teacher;
import gr.aueb.cf.schoolapp.service.ISpecialityService;
import gr.aueb.cf.schoolapp.service.ITeacherService;
import gr.aueb.cf.schoolapp.service.exceptions.EntityNotFoundException;
import gr.aueb.cf.schoolapp.service.util.JPAHelper;
import gr.aueb.cf.schoolapp.validator.ValidatorUtil;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import javax.swing.text.html.parser.Entity;
import java.util.List;
import java.util.Objects;

@Path("/specialities")
public class SpecialityRestController {

    @Inject
    private ISpecialityService specialityService;

    @Inject
    private ITeacherService teacherService;

    @Path("/{specialityId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSpeciality(@PathParam("specialityId")Long specialityId){
        Speciality speciality;
        try {
            speciality = specialityService.getSpecialityById(specialityId);
            SpecialityReadOnlyDTO specialityReadOnlyDTO = Mapper.mapToReadOnlyDTO(speciality);
            return Response.status(Response.Status.OK).entity(specialityReadOnlyDTO).build();
        } catch (EntityNotFoundException e){
            return Response.status(Response.Status.NOT_FOUND).entity("Not found").build();
        }
    }


    @Path("/")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addSpeciality(SpecialityInsertDTO dto, @Context UriInfo uriInfo) {
        List<String> errors = ValidatorUtil.validateDTO(dto);
        if (!errors.isEmpty()) return Response
                .status(Response.Status.BAD_REQUEST).entity(errors).build();

        try {
            Speciality speciality = specialityService.insertSpeciality(dto);
//            TeacherReadOnlyDTO teacherDTO = map(teacher);
            SpecialityReadOnlyDTO specialityDTO = Mapper.mapToReadOnlyDTO(speciality);
            UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
            return Response.created(uriBuilder.path(Long.toString(specialityDTO.getId())).build()).entity(specialityDTO).build();
//
        } catch (Exception e) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity("Speciality Error in insert")
                    .build();
        }
    }


    @Path("/{specialityId}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteSpeciality(@PathParam("specialityId") Long specialityd) {
        try {
            Speciality speciality = specialityService.getSpecialityById(specialityd);
            specialityService.deleteSpeciality(specialityd);
            SpecialityReadOnlyDTO specialityDTO = Mapper.mapToReadOnlyDTO(speciality);
            return Response.status(Response.Status.OK).entity(specialityDTO).build();
        } catch (EntityNotFoundException e1) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity("Speciality Not Found")
                    .build();
        }
    }


    @Path("/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateSpeciality(@PathParam("id") Long id, SpecialityUpdateDTO dto){
        List<String> errors = ValidatorUtil.validateDTO(dto);
        if(!Objects.equals(dto.getId(),id)){
            return Response.status(Response.Status.UNAUTHORIZED).entity("Unothorized").build();
        }
        if(!errors.isEmpty()){
            return Response.status(Response.Status.BAD_REQUEST).entity(errors).build();
        }
        try {


            Speciality speciality = specialityService.updateSpeciality(dto);
           SpecialityReadOnlyDTO readOnlyDTO = Mapper.mapToReadOnlyDTO(speciality);
            return Response.status(Response.Status.OK).entity(readOnlyDTO).build();
        }catch (EntityNotFoundException e){
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }

    }


}
