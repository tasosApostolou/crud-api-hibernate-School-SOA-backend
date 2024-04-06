package gr.aueb.cf.schoolapp.mapper;

import gr.aueb.cf.schoolapp.dto.specialitiesDTO.SpecialityInsertDTO;
import gr.aueb.cf.schoolapp.dto.specialitiesDTO.SpecialityReadOnlyDTO;
import gr.aueb.cf.schoolapp.dto.specialitiesDTO.SpecialityUpdateDTO;
import gr.aueb.cf.schoolapp.dto.studentsDTO.StudentInsertDTO;
import gr.aueb.cf.schoolapp.dto.studentsDTO.StudentReadOnlyDTO;
import gr.aueb.cf.schoolapp.dto.studentsDTO.StudentUpdateDTO;
import gr.aueb.cf.schoolapp.dto.teachersDTO.TeacherInsertDTO;
import gr.aueb.cf.schoolapp.dto.teachersDTO.TeacherReadOnlyDTO;
import gr.aueb.cf.schoolapp.dto.teachersDTO.TeacherUpdateDTO;
import gr.aueb.cf.schoolapp.dto.usersDTO.UserInsertDTO;
import gr.aueb.cf.schoolapp.dto.usersDTO.UserReadOnlyDTO;
import gr.aueb.cf.schoolapp.dto.usersDTO.UserUpdateDTO;
import gr.aueb.cf.schoolapp.model.Speciality;
import gr.aueb.cf.schoolapp.model.Student;
import gr.aueb.cf.schoolapp.model.Teacher;
import gr.aueb.cf.schoolapp.model.User;

public class Mapper {

    private Mapper() {
    }

    public static Teacher mapToTeacher(TeacherInsertDTO dto) {
        return new Teacher(null, dto.getSsn(), dto.getFirstname(), dto.getLastname(), dto.getUser(), dto.getSpeciality());
    }

    public static Teacher mapToTeacher(TeacherUpdateDTO dto) {
        return new Teacher(dto.getId(), dto.getSsn(), dto.getFirstname(), dto.getLastname(), dto.getUser(), dto.getSpeciality());
    }

    public static TeacherReadOnlyDTO mapToReadOnlyDTO(Teacher teacher) {
        return new TeacherReadOnlyDTO(teacher.getId(), teacher.getSsn(), teacher.getFirstname(), teacher.getLastname(), teacher.getUser(), teacher.getSpeciality());
    }


    public static Speciality mapToSpeciality(SpecialityInsertDTO dto) {
        return new Speciality(null, dto.getSpeciality(), null);
    }

    public static Speciality mapToSpeciality(SpecialityUpdateDTO dto) {
        return new Speciality(dto.getId(), dto.getSpeciality(), dto.getTeachers());
    }

    public static SpecialityReadOnlyDTO mapToReadOnlyDTO(Speciality speciality) {
        return new SpecialityReadOnlyDTO(speciality.getId(), speciality.getSpeciality());
    }


    public static Student mapToStudent(StudentInsertDTO dto) {
        return new Student(null, dto.getFirstname(), dto.getLastname(), dto.getGender(), dto.getBirthdate(), dto.getCity(), dto.getUser());
    }


    public static Student mapToStudent(StudentUpdateDTO dto) {
        return new Student(dto.getId(), dto.getFirstname(), dto.getLastname(), dto.getGender(), dto.getBirthday(), dto.getCity(), dto.getUser());
    }

    public static StudentReadOnlyDTO mapToReadOnlyDTO(Student student) {
        return new StudentReadOnlyDTO(student.getId(), student.getFirstname(), student.getLastname(), student.getGender(), student.getBirthdate(), student.getCity(), student.getUser());
    }


    public static User mapToUser(UserInsertDTO dto) {
        return new User(null, dto.getUsername(), dto.getPassword(), dto.getRole());

    }

    public static User mapToUser(UserUpdateDTO dto) {
        return new User(dto.getId(), dto.getUsername(), dto.getPassword(), dto.getRole());
    }

    public static UserReadOnlyDTO mapToReadOnlyDTO(User user) {
        return new UserReadOnlyDTO(user.getId(), user.getUsername(), user.getPassword(), user.getRole());
    }
}