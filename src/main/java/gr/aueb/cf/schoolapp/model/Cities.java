package gr.aueb.cf.schoolapp.model;

import gr.aueb.cf.schoolapp.Identity.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Cities extends AbstractEntity {
    @Column(length = 50)
    private String city;

    @OneToMany(mappedBy = "city")
    @Getter(AccessLevel.PROTECTED)
    private Set<Student> students = new HashSet<>();

    public Cities(Long id, String city) {
        this.setId(id);
        this.city = city;
    }

    public void addStudent(Student student){
        this.students.add(student);
        student.setCity(this);
    }
}
