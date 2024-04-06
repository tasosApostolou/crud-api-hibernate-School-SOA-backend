package gr.aueb.cf.schoolapp.model;

import gr.aueb.cf.schoolapp.Identity.AbstractEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "students")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
//@ToString
public class Student extends AbstractEntity {

//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;
    @Column(length = 50)
    private String firstname;

    @Column(length = 50)
    private String lastname;

    @Column(length = 1)
    private String gender;

    @Column (length = 15)
    private String birthdate;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private Cities city;

    @OneToOne(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "user_id",referencedColumnName = "id",unique = true)
    private User user;

    @Getter(AccessLevel.PROTECTED)
    @ManyToMany(mappedBy = "students")
    private Set<Teacher> teachers = new HashSet<>();



    public Student(Long id, String firstname, String lastname, String gender, String birthdate, Cities city, User user) {
        this.setId(id);
        this.firstname = firstname;
        this.lastname = lastname;
        this.gender = gender;
        this.birthdate = birthdate;
        this.city = city;
        this.user = user;
    }

}
