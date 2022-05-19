package ru.mirea.linguaschool.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="teachers")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Teacher implements Serializable {
    @Id
    @SequenceGenerator(
            name = "teachers_id_seq",
            sequenceName = "teachers_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "teachers_id_seq"
    )
    private Long id;

    @NotEmpty
    private String name;
    @NotEmpty
    private String surname;
    private String patronymic;

    private int age;

    @Column(name="work_experience")
    private int workExperience;

    @Enumerated(EnumType.STRING)
    @Column(name = "language")
    private Language language;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "teacher")
    private List<User> students;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "teacher")
    private List<Review> reviews;

    public String viewName() {
        return "Учитель: " + surname + " " + name + " " + patronymic;
    }

    public String fullName() {
        return surname + " " + name;
    }

    public String languageString() {
        return language.toString() + " язык";
    }

    public String description() {
        return "Возраст: " + years(age) + ", опыт работы: " + years(workExperience) + ", " + reviewsInfo();
    }

    public String years(int year) {
        int ageLastNumber = year % 10;
        boolean exclusion = (year % 100 >= 11) && (year % 100 <= 14);
        String old = "";
        if (ageLastNumber == 1 && year != 11)
            old = " год";
        else if(ageLastNumber == 0 || ageLastNumber >= 5 || year == 11)
            old = " лет";
        else if(ageLastNumber >= 2)
            old = " года";
        if (exclusion)
            old = " лет";
        return year + old;
    }

    public double reviewPercentage() {
        double count = reviews.size();
        double positive = reviews.stream().filter(Review::isRecommended).count();
        return count == 0 ? 0 : positive/count;
    }

    public String reviewsInfo() {
        long positive = reviews.stream().filter(Review::isRecommended).count();
        return String.format("%.2f", reviewPercentage() * 100) + "% положительных отзывов(" + positive + " из " +
                reviews.size() + ")";
    }
}
