package org.bai4.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(
        name = "organization_unit",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_org_unit_name", columnNames = "name"),
                @UniqueConstraint(name = "uk_org_unit_unit_id", columnNames = "unit_id")
        }
)
public class OrganizationUnit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "unit_id", length = 50, unique = true)
    @Size(max = 50, message = "Unit Id tối đa 50 ký tự")
    private String unitId;

    @Column(nullable = false, length = 100, unique = true)
    @NotBlank(message = "Name là bắt buộc")
    @Size(max = 100, message = "Name tối đa 100 ký tự")
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    public OrganizationUnit() {}

    // getters/setters
    public Long getId() { return id; }

    public String getUnitId() { return unitId; }
    public void setUnitId(String unitId) { this.unitId = unitId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
