package iuh.fit.backend.models;

import iuh.fit.backend.enums.SkillType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "skill")
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "skill_id", nullable = false)
    private Long id;

    @Column(name = "skill_description")
    private String skillDescription;

    @Column(name = "skill_name", length = 255)
    private String skillName;

    @Column(name = "type")
    @Enumerated(EnumType.ORDINAL)
    private SkillType type;

    @OneToMany(mappedBy = "skill")
    private Set<CandidateSkill> candidateSkills = new LinkedHashSet<>();

    @OneToMany(mappedBy = "skill")
    private Set<JobSkill> jobSkills = new LinkedHashSet<>();

    @Override
    public String toString() {
        return "Skill{" +
                "id=" + id +
                ", skillName='" + skillName + '\'' +
                ", skillDescription='" + skillDescription + '\'' +
                ", type=" + type +
                '}';
    }
}
