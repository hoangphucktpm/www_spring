package iuh.fit.backend.models;

import com.neovisionaries.i18n.CountryCode;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@ToString
@AllArgsConstructor
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "street", length = 150)
    private String street;

    @Column(name = "city", length = 50)
    private String city;

    @Column(name = "country", nullable = false)
    private CountryCode country;

    @Column(name = "number", length = 20)
    private String number;

    @Column(name = "zipcode", length = 7)
    private String zipcode;

    @OneToOne(mappedBy = "address")
    private Candidate candidate;

    @OneToOne(mappedBy = "address")
    private Company company;

    public Address(String street, String city,  String number, String zipcode, CountryCode country) {
        this.street = street;
        this.city = city;
        this.number = number;
        this.zipcode = zipcode;
        this.country = country;
    }

    public Address() {
    }

    // Address.java
    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", country=" + country +
                ", number='" + number + '\'' +
                ", zipcode='" + zipcode + '\'' +
                // Tránh bao gồm candidate hoặc company để ngăn chặn tham chiếu vòng lặp
                '}';
    }


}