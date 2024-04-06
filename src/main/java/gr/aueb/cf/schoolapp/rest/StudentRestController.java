package gr.aueb.cf.schoolapp.rest;

import gr.aueb.cf.schoolapp.dto.studentsDTO.StudentInsertDTO;
import gr.aueb.cf.schoolapp.dto.studentsDTO.StudentReadOnlyDTO;
import gr.aueb.cf.schoolapp.dto.studentsDTO.StudentUpdateDTO;
import gr.aueb.cf.schoolapp.mapper.Mapper;
import gr.aueb.cf.schoolapp.model.Student;
import gr.aueb.cf.schoolapp.service.IStudentService;
import gr.aueb.cf.schoolapp.service.exceptions.EntityNotFoundException;
import gr.aueb.cf.schoolapp.validator.ValidatorUtil;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
@Path("/students")
public class StudentRestController {
    @Inject
    private IStudentService studentService;

    @Path("/")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStudentsByLastname(@QueryParam("lastname") String lastname) {
        List<Student> students;
        try {
            students = studentService.getStudentsByLastname(lastname);
            List<StudentReadOnlyDTO> studentsDTO = new ArrayList<>();
            for (Student student : students) {
                studentsDTO.add(Mapper.mapToReadOnlyDTO(student));
            }
            return Response.status(Response.Status.OK).entity(studentsDTO).build();
        } catch (EntityNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity("NOT FOUND").build();
        }
    }

    @Path("/{studentId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStudent(@PathParam("studentId") Long teacherId) {
        Student student;
        try {
            student = studentService.getStudentById(teacherId);

            StudentReadOnlyDTO studentDto = Mapper.mapToReadOnlyDTO(student);
            return Response.status(Response.Status.OK).entity(studentDto).build();
        } catch (EntityNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).entity("NOT FOUND").build();
        }
    }

    @Path("/")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addStudent(StudentInsertDTO dto, @Context UriInfo uriInfo) {
        List<String> errors = ValidatorUtil.validateDTO(dto);
        if (!errors.isEmpty()) return Response
                .status(Response.Status.BAD_REQUEST).entity(errors).build();

        try {
            Student student = studentService.insertStudent(dto);
            StudentReadOnlyDTO studentDTO= Mapper.mapToReadOnlyDTO(student);
            UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
            return Response.created(uriBuilder.path(Long.toString(studentDTO.getId())).build())
                    .entity(studentDTO).build();
        } catch (Exception e) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity("Student Error in insert")
                    .build();
        }
    }

    @Path("/{studentId}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteTeacher(@PathParam("studentId") Long studentId) {
        try {
            Student student = studentService.getStudentById(studentId);
            studentService.deleteStudent(studentId);
            StudentReadOnlyDTO studentDTO = Mapper.mapToReadOnlyDTO(student);
            return Response.status(Response.Status.OK).entity(studentDTO).build();
        } catch (EntityNotFoundException e1) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity("Student Not Found")
                    .build();
        }
    }

    @Path("/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateStudent(@PathParam("id") Long id, StudentUpdateDTO dto){
        List<String> errors = ValidatorUtil.validateDTO(dto);
        if(!Objects.equals(dto.getId(),id)){
            return Response.status(Response.Status.UNAUTHORIZED).entity("Unothorized").build();
        }
        if(!errors.isEmpty()){
            return Response.status(Response.Status.BAD_REQUEST).entity(errors).build();
        }
        try {


            Student student = studentService.updateStudent(dto);
            StudentReadOnlyDTO readOnlyDTO = Mapper.mapToReadOnlyDTO(student);
            return Response.status(Response.Status.OK).entity(readOnlyDTO).build();
        }catch (EntityNotFoundException e){
            return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
        }

    }

}

