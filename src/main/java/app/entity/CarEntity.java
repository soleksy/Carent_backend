package app.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "car")
public class CarEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "brand")
    private String brand;
    @Column(name = "model")
    private String model;
    @Column(name = "gearbox")
    private String gearbox;
    @Column(name = "price_per_day")
    private Integer pricePerDay;
    @Column(name = "amount_of_doors")
    private Integer amountOfDoors;
    @Column(name = "amount_of_seats")
    private Integer amountOfSeats;
    @Column(name = "mileage_in_liter")
    private Integer mileageInLiter;
    @Column(name = "air_conditioning")
    private Boolean airConditioning;

    @OneToMany(mappedBy = "car", cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.REFRESH})
    private Set<RentalEntity> rentals;

    public CarEntity() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getGearbox() {
        return gearbox;
    }

    public void setGearbox(String gearbox) {
        this.gearbox = gearbox;
    }

    public Integer getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(Integer pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public Integer getAmountOfDoors() {
        return amountOfDoors;
    }

    public void setAmountOfDoors(Integer amountOfDoors) {
        this.amountOfDoors = amountOfDoors;
    }

    public Integer getAmountOfSeats() {
        return amountOfSeats;
    }

    public void setAmountOfSeats(Integer amountOfSeats) {
        this.amountOfSeats = amountOfSeats;
    }

    public Integer getMileageInLiter() {
        return mileageInLiter;
    }

    public void setMileageInLiter(Integer mileageInLiter) {
        this.mileageInLiter = mileageInLiter;
    }

    public Boolean getAirConditioning() {
        return airConditioning;
    }

    public void setAirConditioning(Boolean airConditioning) {
        this.airConditioning = airConditioning;
    }


    public void addRental(RentalEntity rental){
        if (rentals == null){
            rentals = new HashSet<>();
        }
        rentals.add(rental);
        rental.setCar(this);
    }
}
