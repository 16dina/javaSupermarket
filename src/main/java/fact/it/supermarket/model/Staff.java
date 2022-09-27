// Dina Boshnaq
// r0823887
// 1ITF 1ACS
package fact.it.supermarket.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Staff extends Person{
    private LocalDate startDate = LocalDate.now();
    private boolean student;

    public Staff(String firstName, String surName) {
        super(firstName, surName);
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public boolean isStudent() {
        return student;
    }

    public void setStudent(boolean student) {
        this.student = student;
    }

    public String toString() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        if (isStudent()) {
            return "Staff member " + super.toString() + " (working student) is employed since " + startDate.format(dtf);
        }
        return "Staff member " + super.toString() + " is employed since " + startDate.format(dtf);
    }
}
