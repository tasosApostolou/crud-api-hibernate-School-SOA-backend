package gr.aueb.cf.schoolapp.model;

import gr.aueb.cf.schoolapp.Identity.AbstractEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
//@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "teachers")
public class Teacher extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50,unique = true)
    private String ssn;

    @Column(length = 50)
    private String firstname;

    @Column(length = 50)
    private String lastname;

    @OneToOne(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "user_id",referencedColumnName = "id",unique = true)
    private User user;


    @ManyToOne
    @JoinColumn(name = "speciality_id")
    private Speciality speciality;



    @Getter(AccessLevel.PROTECTED)
    @ManyToMany
    @JoinTable(
            name = "meetings",
            joinColumns = @JoinColumn(name = "teacher_id", referencedColumnName = "id",nullable = false),
            inverseJoinColumns = @JoinColumn(name = "student_id", referencedColumnName = "id",nullable = false)
    )
    private Set<Student> students = new HashSet<>();




    public Teacher(Long id, String ssn, String firstname, String lastname, User user, Speciality speciality) {
        this.setId(id);
        this.ssn = ssn;
        this.firstname = firstname;
        this.lastname = lastname;
        this.user = user;
        this.speciality = speciality;

    }


}
