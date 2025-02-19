package lt.vgtu.hw1.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * The type Customer.
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Customer extends User {
    private String cardNumber;
    private String shippingAddress;
    private String billingAddress;
    private LocalDate birthDate;

    /**
     * Instantiates a new Customer.
     *
     * @param name     the name
     * @param surname  the surname
     * @param login    the login
     * @param password the password
     */
    public Customer(String name, String surname, String login, String password) {
        super(name, surname, login, password);
    }

    /**
     * Instantiates a new Customer.
     *
     * @param name            the name
     * @param surname         the surname
     * @param login           the login
     * @param password        the password
     * @param cardNumber      the card number
     * @param shippingAddress the shipping address
     * @param billingAddress  the billing address
     * @param birthDate       the birth date
     */
    public Customer(String name, String surname, String login, String password, String cardNumber, String shippingAddress, String billingAddress, String birthDate) {
        super(name, surname, login, password);
        this.cardNumber = cardNumber;
        this.shippingAddress = shippingAddress;
        this.billingAddress = billingAddress;
        if (birthDate.equals("")) {
            this.birthDate = LocalDate.of(2000, 1, 1);
        } else {
            DateTimeFormatter[] formatters = {
                    DateTimeFormatter.ofPattern("dd MM yyyy"),
                    DateTimeFormatter.ofPattern("dd-MM-yyyy"),
                    DateTimeFormatter.ofPattern("ddMMyyyy")
            };

            LocalDate parsedDate = null;
            for (DateTimeFormatter formatter : formatters) {
                try {
                    parsedDate = LocalDate.parse(birthDate, formatter);
                    break;
                } catch (DateTimeParseException ignored) {
                }
            }

            this.birthDate = parsedDate == null ? LocalDate.of(2000, 1, 1) : parsedDate;
        }

    }

    /**
     * Sets birth date.
     *
     * @param birthDate the birth date
     */
    public void setBirthDate(String birthDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MM yyyy");
        this.birthDate = LocalDate.parse(birthDate, formatter);
    }
}
