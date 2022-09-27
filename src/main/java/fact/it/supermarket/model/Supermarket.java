// Dina Boshnaq
// r0823887
// 1ITF 1ACS
package fact.it.supermarket.model;

import java.util.ArrayList;

public class Supermarket {
    private String name;
    private int numberCustomers;
    private ArrayList<Department> departmentList = new ArrayList<> ();

    public Supermarket(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberCustomers() {
        return numberCustomers;
    }

    public ArrayList<Department> getDepartmentList() {
        return departmentList;
    }

    public int getNumberOfDepartments() {
        return departmentList.size();
    }

    public void addDepartment(Department department) {
        departmentList.add(department);
    }

    public Department searchDepartmentByName(String name) {
        for (Department department : departmentList) {
            if (department.getName().equals(name)) {
                return department;
            }
        }
        return null;
    }

    public void registerCustomer(Customer customer) {
        numberCustomers += 1;
        customer.setCardNumber(numberCustomers);
    }
}
