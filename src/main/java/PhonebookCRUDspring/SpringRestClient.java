package PhonebookCRUDspring;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import PhonebookCRUDspring.model.Contact;

public class SpringRestClient {

    private static final String GET_EMPLOYEES_ENDPOINT_URL = "http://localhost:8080/api/v1/contacts";
    private static final String GET_EMPLOYEE_ENDPOINT_URL = "http://localhost:8080/api/v1/contacts/{id}";
    private static final String CREATE_EMPLOYEE_ENDPOINT_URL = "http://localhost:8080/api/v1/contacts";
    private static final String UPDATE_EMPLOYEE_ENDPOINT_URL = "http://localhost:8080/api/v1/contacts/{id}";
    private static final String DELETE_EMPLOYEE_ENDPOINT_URL = "http://localhost:8080/api/v1/contacts/{id}";
    private static RestTemplate restTemplate = new RestTemplate();

    public static void main(String[] args) {
        SpringRestClient springRestClient = new SpringRestClient();

        // first create a new employee
        springRestClient.createEmployee();

        // get new created employee from step1
        springRestClient.getEmployeeById();

        // get all employees
        springRestClient.getEmployees();

        // Update employee with id = 1
        springRestClient.updateEmployee();

        // Delete employee with id = 1
        springRestClient.deleteEmployee();
    }

    private void getEmployees() {

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);

        ResponseEntity<String> result = restTemplate.exchange(GET_EMPLOYEES_ENDPOINT_URL, HttpMethod.GET, entity,
                String.class);

        System.out.println(result);
    }

    private void getEmployeeById() {

        Map<String, String> params = new HashMap<String, String>();
        params.put("id", "1");

        RestTemplate restTemplate = new RestTemplate();
        Contact result = restTemplate.getForObject(GET_EMPLOYEE_ENDPOINT_URL, Contact.class, params);

        System.out.println(result);
    }

    private void createEmployee() {

        Contact newEmployee = new Contact(900076543, "admin", "admin@gmail.com");

        RestTemplate restTemplate = new RestTemplate();
        Contact result = restTemplate.postForObject(CREATE_EMPLOYEE_ENDPOINT_URL, newEmployee, Contact.class);

        System.out.println(result);
    }

    private void updateEmployee() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", "1");
        Contact updatedEmployee = new Contact(900076543, "admin123", "admin123@gmail.com");
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.put(UPDATE_EMPLOYEE_ENDPOINT_URL, updatedEmployee, params);
    }

    private void deleteEmployee() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", "1");
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(DELETE_EMPLOYEE_ENDPOINT_URL, params);
    }
}
