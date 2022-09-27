// Dina Boshnaq
// r0823887
// 1ITF 1ACS
package fact.it.supermarket.controller;

import fact.it.supermarket.model.Customer;
import fact.it.supermarket.model.Department;
import fact.it.supermarket.model.Staff;
import fact.it.supermarket.model.Supermarket;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.ArrayList;

@Controller
public class MainController {
    /*  You will need these ArrayLists in part 3 of the project assignment. */
    private ArrayList<Staff> staffArrayList;
    private ArrayList<Customer> customerArrayList;
    private ArrayList<Supermarket> supermarketArrayList;
    private String errorMessage;

    //    Write your code here
    @PostConstruct
    private void fillAllData(){
        staffArrayList = fillStaffMembers();
        customerArrayList = fillCustomers();
        supermarketArrayList = fillSupermarkets();
    }

    @RequestMapping("/")
    public String index() { return "index"; }

    @RequestMapping("/formCustomer")
    public String newCustomer(Model model) {
        model.addAttribute("allSupermarketsList", supermarketArrayList);
        return "1_newCustomerForm";
    }

    @RequestMapping("/addNewCustomer")
    public String addNewCustomer(HttpServletRequest request, Model model) {
        String firstName = request.getParameter("firstName");
        String surName = request.getParameter("surName");
        int yearOfBirth = Integer.parseInt(request.getParameter("yearOfBirth"));

        Customer newCustomer = new Customer(firstName, surName);
        newCustomer.setYearOfBirth(yearOfBirth);

        int supermarketIndex = Integer.parseInt(request.getParameter("supermarketIndex"));
        Supermarket returnSupermarket = supermarketArrayList.get(supermarketIndex);
        returnSupermarket.registerCustomer(newCustomer);

        customerArrayList.add(newCustomer);

        model.addAttribute("newCustomerDetails", newCustomer);

        return "2_customerDetails";

    }

    @RequestMapping("/formStaff")
    public String newStaff() { return "3_newStaffForm"; }

    @RequestMapping("/addNewStaff")
    public String addNewStaff(HttpServletRequest request, Model model) {
        String firstName = request.getParameter("firstName");
        String surName = request.getParameter("surName");
        LocalDate startDate = LocalDate.parse(request.getParameter("startDate"));
        boolean student = (request.getParameter("student") != null);

        Staff newStaff = new Staff(firstName, surName);
        newStaff.setStartDate(startDate);
        newStaff.setStudent(student);

        staffArrayList.add(newStaff);

        model.addAttribute("newStaffDetails", newStaff);

        return "4_staffDetails";
    }

    @RequestMapping("/staffList")
    public String staffList(Model model) {
        model.addAttribute("aLlStaffList", staffArrayList);
        return "5_listOfStaff";
    }

    @RequestMapping("/customerList")
    public String customerList(Model model) {
        model.addAttribute("allCustomersList", customerArrayList);
        return "6_listOfCustomers";
    }

    @RequestMapping("/formSupermarket")
    public String newSupermarket() { return "7_newSupermarketForm"; }

    @RequestMapping("/addNewSupermarket")
    public String addNewSupermarket(HttpServletRequest request, Model model) {
        String name = request.getParameter("name");

        Supermarket newSupermarket = new Supermarket(name);

        supermarketArrayList.add(newSupermarket);

        model.addAttribute("allSupermarketsList", supermarketArrayList);

        return "8_listOfSupermarkets";
    }

    @RequestMapping("/supermarketList")
    public String supermarketList(Model model) {
        model.addAttribute("allSupermarketsList", supermarketArrayList);
        return "8_listOfSupermarkets";
    }

    @RequestMapping("/formDepartment")
    public String newDepartment(Model model) {
        model.addAttribute("allSupermarketsList", supermarketArrayList);
        model.addAttribute("allStaffList", staffArrayList);
        return "9_newDepartmentForm";
    }

    @RequestMapping("/addNewDepartment")
    public String addNewDepartment(HttpServletRequest request, Model model) {
        String name = request.getParameter("name");
        String photo = request.getParameter("photo");
        boolean refrigerated = (request.getParameter("refrigerated") != null);

        Department newDepartment = new Department(name);
        newDepartment.setPhoto(photo);
        newDepartment.setRefrigerated(refrigerated);

        int supermarketIndex = Integer.parseInt(request.getParameter("supermarketIndex"));
        int staffIndex = Integer.parseInt(request.getParameter("staffIndex"));

        if (supermarketIndex < 0) {
            errorMessage = "You didn't choose a supermarket!";
            model.addAttribute("errorMessage", errorMessage);
            return "error";
        }

        if (staffIndex < 0) {
            errorMessage = "You didn't choose a staff member!";
            model.addAttribute("errorMessage", errorMessage);
            return "error";
        }

        Supermarket returnSupermarket = supermarketArrayList.get(supermarketIndex);
        returnSupermarket.addDepartment(newDepartment);
        Staff returnStaff = staffArrayList.get(staffIndex);
        newDepartment.setResponsible(returnStaff);
        model.addAttribute("newDepartmentDetails", newDepartment);
        model.addAttribute("assignedSupermarket", returnSupermarket);
        return "10_departmentsOfSupermarket";
    }

    @RequestMapping("/supermarketDetails")
    public String showSupermarketDetails(HttpServletRequest request, Model model) {
        int supermarketIndex = Integer.parseInt(request.getParameter("supermarketIndex"));
        Supermarket returnSupermarket = supermarketArrayList.get(supermarketIndex);
        model.addAttribute("assignedSupermarket", returnSupermarket);
        return "10_departmentsOfSupermarket";
    }

    @RequestMapping("/searchForDepartment")
    public String searchForDepartment(HttpServletRequest request, Model model) {
        String name = request.getParameter("name");
        for (Supermarket supermarket : supermarketArrayList) {
            if (supermarket.searchDepartmentByName(name) != null) {
                Department returnDepartment = supermarket.searchDepartmentByName(name);
                model.addAttribute("returnDepartment", returnDepartment);
                return "11_searchedDepartmentDetails";
            }
        }
        errorMessage = "There is no department with the name '" + name + "'";
        model.addAttribute("errorMessage", errorMessage);
        return "error";
    }




    /*You wll need these methods in part 3 of the project assignment  */
    private ArrayList<Staff> fillStaffMembers() {
        ArrayList<Staff> staffMembers = new ArrayList<>();

        Staff staff1 = new Staff("Johan", "Bertels");
        staff1.setStartDate(LocalDate.of(2002, 5, 1));
        Staff staff2 = new Staff("An", "Van Herck");
        staff2.setStartDate(LocalDate.of(2019, 3, 15));
        staff2.setStudent(true);
        Staff staff3 = new Staff("Bruno", "Coenen");
        staff3.setStartDate(LocalDate.of(1995,1,1));
        Staff staff4 = new Staff("Wout", "Dayaert");
        staff4.setStartDate(LocalDate.of(2002, 12, 15));
        Staff staff5 = new Staff("Louis", "Petit");
        staff5.setStartDate(LocalDate.of(2020, 8, 1));
        staff5.setStudent(true);
        Staff staff6 = new Staff("Jean", "Pinot");
        staff6.setStartDate(LocalDate.of(1999,4,1));
        Staff staff7 = new Staff("Ahmad", "Bezeri");
        staff7.setStartDate(LocalDate.of(2009, 5, 1));
        Staff staff8 = new Staff("Hans", "Volzky");
        staff8.setStartDate(LocalDate.of(2015, 6, 10));
        staff8.setStudent(true);
        Staff staff9 = new Staff("Joachim", "Henau");
        staff9.setStartDate(LocalDate.of(2007,9,18));
        staffMembers.add(staff1);
        staffMembers.add(staff2);
        staffMembers.add(staff3);
        staffMembers.add(staff4);
        staffMembers.add(staff5);
        staffMembers.add(staff6);
        staffMembers.add(staff7);
        staffMembers.add(staff8);
        staffMembers.add(staff9);
        return staffMembers;
    }

    private ArrayList<Customer> fillCustomers() {
        ArrayList<Customer> customers = new ArrayList<>();
        Customer customer1 = new Customer("Dominik", "Mioens");
        customer1.setYearOfBirth(2001);
        Customer customer2 = new Customer("Zion", "Noops");
        customer2.setYearOfBirth(1996);
        Customer customer3 = new Customer("Maria", "Bonetta");
        customer3.setYearOfBirth(1998);
        Customer customerMe = new Customer("Dina", "Boshnaq");
        customerMe.setYearOfBirth(2002);
        customers.add(customer1);
        customers.add(customer2);
        customers.add(customer3);
        customers.add(customerMe);
        customers.get(0).addToShoppingList("Butter");
        customers.get(0).addToShoppingList("Bread");
        customers.get(1).addToShoppingList("Apple");
        customers.get(1).addToShoppingList("Banana");
        customers.get(1).addToShoppingList("Grapes");
        customers.get(1).addToShoppingList("Oranges");
        customers.get(2).addToShoppingList("Fish");
        customers.get(3).addToShoppingList("Strawberry");
        customers.get(3).addToShoppingList("Chicken");
        customers.get(3).addToShoppingList("Soy Milk");
        customers.get(3).addToShoppingList("Rice");
        customers.get(3).addToShoppingList("Cucumber");
        customers.get(3).addToShoppingList("Ice-cream");


        return customers;
    }

    private ArrayList<Supermarket> fillSupermarkets() {
        ArrayList<Supermarket> supermarkets = new ArrayList<>();
        Supermarket supermarket1 = new Supermarket("Delhaize");
        Supermarket supermarket2 = new Supermarket("Colruyt");
        Supermarket supermarket3 = new Supermarket("Albert Heyn");
        Department department1 = new Department("Fruit");
        Department department2 = new Department("Bread");
        Department department3 = new Department("Vegetables");
        department1.setPhoto("/img/fruit.jpg");
        department2.setPhoto("/img/bread.jpg");
        department3.setPhoto("/img/vegetables.jpg");
        department1.setResponsible(staffArrayList.get(0));
        department2.setResponsible(staffArrayList.get(1));
        department3.setResponsible(staffArrayList.get(2));
        supermarket1.addDepartment(department1);
        supermarket1.addDepartment(department2);
        supermarket1.addDepartment(department3);
        supermarket2.addDepartment(department1);
        supermarket2.addDepartment(department2);
        supermarket3.addDepartment(department1);
        supermarket3.addDepartment(department3);
        supermarkets.add(supermarket1);
        supermarkets.add(supermarket2);
        supermarkets.add(supermarket3);
        return supermarkets;
    }


}
