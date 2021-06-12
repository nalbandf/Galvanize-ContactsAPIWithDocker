package com.cognizant.galvanize.contacts.controller;


import com.cognizant.galvanize.contacts.domain.ContactBean;
import com.cognizant.galvanize.contacts.repository.ContactRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ContactControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ContactRepository mockContactRepository;

    /*
     *  Minimal test case, fix only what is red.
     *
     *  Copy the use case before every test case.
     *  Test case method name should be descriptive.
     *
     *  Follow ZOMBIE model.
     *
     *  All test cases should follow,
     *       Arrange
     *       Act
     *       Assert
     */

//    GET /contacts -- returns a list all contacts (ID, given name, surname, and phone number)

    @Test
    public void testIfRequestContactsAndNoContactsFoundThenReturnResponseWithErrorMessage() throws Exception {
        // ZERO CONDITION

        // Arrange
        List<ContactBean> emptyContactBeanList = new ArrayList<>();
        when(mockContactRepository.findAll()).thenReturn(emptyContactBeanList);

        // Act &  // Assert
        RequestBuilder requestBuilder = get("/contacts");
        mockMvc.perform(requestBuilder).andExpect(status().isOk())
                //        .andExpect(jsonPath("$[0]").doesNotExist())
                .andExpect(jsonPath("$.message", is("No data found")))
                .andExpect(content().string("{\"message\":\"No data found\"}"));
    }

    @Test
    public void testIfRequestContactsAndOneContactFoundThenReturnResponseWithOneContact() throws Exception {
        // ONE CONDITION

        // Arrange
        ContactBean contactBean = new ContactBean();
        contactBean.setId(1);
        contactBean.setGivenName("John");
        contactBean.setSurName("Doe");
        contactBean.setPhoneNumber("111-1111-1111");

        List<ContactBean> contactBeanList = new ArrayList<>();
        contactBeanList.add(contactBean);

        when(mockContactRepository.findAll()).thenReturn(contactBeanList);

        // Act &  // Assert
        RequestBuilder requestBuilder = get("/contacts");
        mockMvc.perform(requestBuilder).andExpect(status().isOk())
                //        .andExpect(jsonPath("$[0]").doesNotExist())
                .andExpect(jsonPath("$.contactBeanResponseList[0].id", is(1)));
    }

    @Test
    public void testIfRequestContactsAndManyContactFoundThenReturnResponseWithManyContact() throws Exception {
        // MANY CONDITION

        // Arrange
        ContactBean contactBean = new ContactBean();
        contactBean.setId(1);
        contactBean.setGivenName("John");
        contactBean.setSurName("Doe");
        contactBean.setPhoneNumber("111-1111-1111");

        ContactBean contactBean1 = new ContactBean();
        contactBean1.setId(2);
        contactBean1.setGivenName("Fred");
        contactBean1.setSurName("Chris");
        contactBean1.setPhoneNumber("222-2222-2222");

        List<ContactBean> contactBeanList = new ArrayList<>();
        contactBeanList.add(contactBean);
        contactBeanList.add(contactBean1);

        when(mockContactRepository.findAll()).thenReturn(contactBeanList);

        // Act &  // Assert
        RequestBuilder requestBuilder = get("/contacts");
        mockMvc.perform(requestBuilder).andExpect(status().isOk())
                //        .andExpect(jsonPath("$[0]").doesNotExist())
                .andExpect(jsonPath("$.contactBeanResponseList[0].id", is(1)));
    }

    //GET /contacts?givenname=, surname= --returns a list of contacts matching the provided parameters. Both are optional, making this a modification to GET /contacts.

    @Test
    public void testIfSearchContactsByGivenNameAndNoContactsFoundThenReturnResponseWithErrorMessage() throws Exception {
        // ZERO CONDITION

        // Arrange
        List<ContactBean> emptyContactBeanList = new ArrayList<>();
        when(mockContactRepository.findAll()).thenReturn(emptyContactBeanList);

        // Act &  // Assert
        RequestBuilder requestBuilder = get("/contacts").requestAttr("givenName", "some");
        mockMvc.perform(requestBuilder).andExpect(status().isOk())
                //        .andExpect(jsonPath("$[0]").doesNotExist())
                .andExpect(jsonPath("$.message", is("No data found")))
                .andExpect(content().string("{\"message\":\"No data found\"}"));
    }

    @Test
    public void testIfSearchContactsByIncorrectGivenNameAndNoContactsFoundThenReturnResponseWithErrorMessage() throws Exception {
        // ZERO CONDITION

        // Arrange
        ContactBean contactBean = new ContactBean();
        contactBean.setId(1);
        contactBean.setGivenName("John");
        contactBean.setSurName("Doe");
        contactBean.setPhoneNumber("111-1111-1111");

        List<ContactBean> contactBeanList = new ArrayList<>();
        contactBeanList.add(contactBean);
        when(mockContactRepository.findByGivenName("John")).thenReturn(contactBeanList);

        // Act &  // Assert
        RequestBuilder requestBuilder = get("/contacts").param("givenName", "Some")
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder).andExpect(status().isOk())
                //        .andExpect(jsonPath("$[0]").doesNotExist())
                .andExpect(jsonPath("$.message", is("No data found")));
    }

    @Test
    public void testIfSearchContactsByGivenNameAndOneContactsFoundThenReturnResponseOneContact() throws Exception {
        // ONE CONDITION

        // Arrange
        ContactBean contactBean = new ContactBean();
        contactBean.setId(1);
        contactBean.setGivenName("John");
        contactBean.setSurName("Doe");
        contactBean.setPhoneNumber("111-1111-1111");

        List<ContactBean> contactBeanList = new ArrayList<>();
        contactBeanList.add(contactBean);
        when(mockContactRepository.findByGivenName("John")).thenReturn(contactBeanList);

        // Act &  // Assert
        RequestBuilder requestBuilder = get("/contacts").param("givenName", "John")
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder).andExpect(status().isOk())
                //        .andExpect(jsonPath("$[0]").doesNotExist())
                .andExpect(jsonPath("$.contactBeanResponseList[0].id", is(1)));
    }

    @Test
    public void testIfSearchContactsByGivenNameAndManyContactsFoundThenReturnResponseManyContact() throws Exception {
        // MANY CONDITION

        // Arrange
        ContactBean contactBean = new ContactBean();
        contactBean.setId(1);
        contactBean.setGivenName("John");
        contactBean.setSurName("Doe");
        contactBean.setPhoneNumber("111-1111-1111");

        ContactBean contactBean1 = new ContactBean();
        contactBean1.setId(2);
        contactBean1.setGivenName("John");
        contactBean1.setSurName("Chris");
        contactBean1.setPhoneNumber("222-2222-2222");

        List<ContactBean> contactBeanList = new ArrayList<>();
        contactBeanList.add(contactBean);
        contactBeanList.add(contactBean1);
        when(mockContactRepository.findByGivenName("John")).thenReturn(contactBeanList);

        // Act &  // Assert
        RequestBuilder requestBuilder = get("/contacts").param("givenName", "John");
        mockMvc.perform(requestBuilder).andExpect(status().isOk())
                //        .andExpect(jsonPath("$[0]").doesNotExist())
                .andExpect(jsonPath("$.contactBeanResponseList[0].id", is(1)))
                .andExpect(jsonPath("$.contactBeanResponseList[1].id", is(2)));
    }

    // SURNAME TESTS

    @Test
    public void testIfSearchContactsBySurNameAndNoContactsFoundThenReturnResponseWithErrorMessage() throws Exception {
        // ZERO CONDITION

        // Arrange
        List<ContactBean> contactBeanList = new ArrayList<>();
        when(mockContactRepository.findBySurName("Doe")).thenReturn(contactBeanList);

        // Act &  // Assert
        RequestBuilder requestBuilder = get("/contacts").param("surName", "some");
        mockMvc.perform(requestBuilder).andExpect(status().isOk())
                //        .andExpect(jsonPath("$[0]").doesNotExist())
                .andExpect(jsonPath("$.message", is("No data found")))
                .andExpect(content().string("{\"message\":\"No data found\"}"));
    }

    @Test
    public void testIfSearchContactsByIncorrectSurNameAndNoContactsFoundThenReturnResponseWithErrorMessage() throws Exception {
        // ZERO CONDITION

        // Arrange
        ContactBean contactBean = new ContactBean();
        contactBean.setId(1);
        contactBean.setGivenName("John");
        contactBean.setSurName("Doe");
        contactBean.setPhoneNumber("111-1111-1111");

        List<ContactBean> contactBeanList = new ArrayList<>();
        contactBeanList.add(contactBean);
        when(mockContactRepository.findBySurName("Doe")).thenReturn(contactBeanList);

        // Act &  // Assert
        RequestBuilder requestBuilder = get("/contacts").param("givenName", "Some")
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder).andExpect(status().isOk())
                //        .andExpect(jsonPath("$[0]").doesNotExist())
                .andExpect(jsonPath("$.message", is("No data found")));
    }

    @Test
    public void testIfSearchContactsBySurNameAndOneContactsFoundThenReturnResponseOneContact() throws Exception {
        // ONE CONDITION

        // Arrange
        ContactBean contactBean = new ContactBean();
        contactBean.setId(1);
        contactBean.setGivenName("John");
        contactBean.setSurName("Doe");
        contactBean.setPhoneNumber("111-1111-1111");

        List<ContactBean> contactBeanList = new ArrayList<>();
        contactBeanList.add(contactBean);
        when(mockContactRepository.findBySurName("Doe")).thenReturn(contactBeanList);

        // Act &  // Assert
        RequestBuilder requestBuilder = get("/contacts").param("surName", "Doe")
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder).andExpect(status().isOk())
                //        .andExpect(jsonPath("$[0]").doesNotExist())
                .andExpect(jsonPath("$.contactBeanResponseList[0].id", is(1)));
    }

    @Test
    public void testIfSearchContactsBySurNameAndManyContactsFoundThenReturnResponseManyContact() throws Exception {
        // MANY CONDITION

        // Arrange
        ContactBean contactBean = new ContactBean();
        contactBean.setId(1);
        contactBean.setGivenName("John");
        contactBean.setSurName("Doe");
        contactBean.setPhoneNumber("111-1111-1111");

        ContactBean contactBean1 = new ContactBean();
        contactBean1.setId(2);
        contactBean1.setGivenName("Chris");
        contactBean1.setSurName("Doe");
        contactBean1.setPhoneNumber("222-2222-2222");

        List<ContactBean> contactBeanList = new ArrayList<>();
        contactBeanList.add(contactBean);
        contactBeanList.add(contactBean1);
        when(mockContactRepository.findBySurName("Doe")).thenReturn(contactBeanList);

        // Act &  // Assert
        RequestBuilder requestBuilder = get("/contacts")
                .param("surName", "Doe");
        mockMvc.perform(requestBuilder).andExpect(status().isOk())
                //        .andExpect(jsonPath("$[0]").doesNotExist())
                .andExpect(jsonPath("$.contactBeanResponseList[0].id", is(1)))
                .andExpect(jsonPath("$.contactBeanResponseList[1].id", is(2)));
    }


    // SURNAME AND GIVEN NAME TESTS

    @Test
    public void testIfSearchContactsBySurNameAndGivenNameAndNoContactsFoundThenReturnResponseWithErrorMessage() throws Exception {
        // ZERO CONDITION

        // Arrange
        List<ContactBean> contactBeanList = new ArrayList<>();
        when(mockContactRepository.findByGivenName("John")).thenReturn(contactBeanList);
        when(mockContactRepository.findBySurName("Doe")).thenReturn(contactBeanList);

        // Act &  // Assert
        RequestBuilder requestBuilder = get("/contacts")
                .requestAttr("surName", "some").param("givenName", "other");
        mockMvc.perform(requestBuilder).andExpect(status().isOk())
                //        .andExpect(jsonPath("$[0]").doesNotExist())
                .andExpect(jsonPath("$.message", is("No data found")))
                .andExpect(content().string("{\"message\":\"No data found\"}"));
    }

    @Test
    public void testIfSearchContactsByIncorrectSurNameAndGivenNameAndNoContactsFoundThenReturnResponseWithErrorMessage() throws Exception {
        // ZERO CONDITION

        // Arrange
        ContactBean contactBean = new ContactBean();
        contactBean.setId(1);
        contactBean.setGivenName("John");
        contactBean.setSurName("Doe");
        contactBean.setPhoneNumber("111-1111-1111");

        List<ContactBean> contactBeanList = new ArrayList<>();
        contactBeanList.add(contactBean);
        when(mockContactRepository.findByGivenName("John")).thenReturn(contactBeanList);
        when(mockContactRepository.findBySurName("Doe")).thenReturn(contactBeanList);

        // Act &  // Assert
        RequestBuilder requestBuilder = get("/contacts")
                .param("givenName", "Some")
                .param("surName", "Other")
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder).andExpect(status().isOk())
                //        .andExpect(jsonPath("$[0]").doesNotExist())
                .andExpect(jsonPath("$.message", is("No data found")));
    }

    @Test
    public void testIfSearchContactsBySurNameAndGivenNameAndOneContactsFoundThenReturnResponseOneContact() throws Exception {
        // ONE CONDITION

        // Arrange
        ContactBean contactBean = new ContactBean();
        contactBean.setId(1);
        contactBean.setGivenName("John");
        contactBean.setSurName("Doe");
        contactBean.setPhoneNumber("111-1111-1111");

        List<ContactBean> contactBeanList = new ArrayList<>();
        contactBeanList.add(contactBean);
        when(mockContactRepository.findBySurName("Doe")).thenReturn(contactBeanList);
        when(mockContactRepository.findByGivenName("John")).thenReturn(contactBeanList);

        // Act &  // Assert
        RequestBuilder requestBuilder = get("/contacts")
                .param("givenName", "John")
                .param("surName", "Doe")
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(requestBuilder).andExpect(status().isOk())
                //        .andExpect(jsonPath("$[0]").doesNotExist())
                .andExpect(jsonPath("$.contactBeanResponseList[0].id", is(1)));
    }

    @Test
    public void testIfSearchContactsBySurNameAndGivenNameAndManyContactsFoundThenReturnResponseManyContact() throws Exception {
        // MANY CONDITION

        // Arrange
        ContactBean contactBean = new ContactBean();
        contactBean.setId(1);
        contactBean.setGivenName("John");
        contactBean.setSurName("Doe");
        contactBean.setPhoneNumber("111-1111-1111");

        ContactBean contactBean1 = new ContactBean();
        contactBean1.setId(2);
        contactBean1.setGivenName("Chris");
        contactBean1.setSurName("Doe");
        contactBean1.setPhoneNumber("222-2222-2222");

        ContactBean contactBean3 = new ContactBean();
        contactBean3.setId(3);
        contactBean3.setGivenName("John");
        contactBean3.setSurName("Doe");
        contactBean3.setPhoneNumber("333-3333-3333");

        List<ContactBean> givenNameList = new ArrayList<ContactBean>();
        givenNameList.add(contactBean);
        givenNameList.add(contactBean3);

        List<ContactBean> surNameList = new ArrayList<ContactBean>();
        surNameList.add(contactBean);
        surNameList.add(contactBean1);
        surNameList.add(contactBean3);

        when(mockContactRepository.findByGivenName("John")).thenReturn(givenNameList);
        when(mockContactRepository.findBySurName("Doe")).thenReturn(surNameList);

        // Act &  // Assert
        RequestBuilder requestBuilder = get("/contacts")
                .param("givenName", "John")
                .param("surName", "Doe");
        mockMvc.perform(requestBuilder).andExpect(status().isOk())
                //        .andExpect(jsonPath("$[0]").doesNotExist())
                .andExpect(jsonPath("$.contactBeanResponseList[0].id", is(1)))
                .andExpect(jsonPath("$.contactBeanResponseList[1].id", is(3)));

    }

    @Test
    public void testCreateContact() throws Exception {
        String contactStr = "{\n" +
                "  \"givenName\": \"John\",\n" +
                "  \"surName\": \"Doe\",\n" +
                "  \"phoneNumber\": \"222-111-0000\"\n" +
                "\n" +
                "}";
        ContactBean contactBean = new ContactBean();
        contactBean.setId(1);
        contactBean.setGivenName("John");
        contactBean.setSurName("Doe");
        contactBean.setPhoneNumber("111-1111-1111");

        when(mockContactRepository.save(any(ContactBean.class))).thenReturn(contactBean);

        RequestBuilder request = post("/contact")
                .contentType(MediaType.APPLICATION_JSON)
                .content(contactStr);
        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.contactBeanResponseList[0].id", is(1)));
    }

    //DELETE /contact/{id} -- deletes the contact from the database returns 200 if ID is in database or 404 if not.
    @Test
    public void testDeleteContactAndFoundContactDeletedWithSuccessReturnMessage() throws Exception {
        ContactBean contactBean = new ContactBean();
        contactBean.setId(1);
        contactBean.setGivenName("John");
        contactBean.setSurName("Doe");
        contactBean.setPhoneNumber("111-1111-1111");


        when(mockContactRepository.findById(1)).thenReturn(java.util.Optional.of(contactBean));

        RequestBuilder requestBuilder = delete("/contact/1");
        mockMvc.perform(requestBuilder).andExpect(status().isOk());
    }

    // GET BY ID
    @Test
    public void testIfRequestContactByIdAndOneContactFoundThenReturnResponseWithOneContact() throws Exception {
        // ONE CONDITION

        // Arrange
        ContactBean contactBean = new ContactBean();
        contactBean.setId(1);
        contactBean.setGivenName("John");
        contactBean.setSurName("Doe");
        contactBean.setPhoneNumber("111-1111-1111");

        when(mockContactRepository.findById(any())).thenReturn(java.util.Optional.of(contactBean));

        // Act &  // Assert
        RequestBuilder requestBuilder = get("/contacts/1");
        mockMvc.perform(requestBuilder).andExpect(status().isOk())
                //        .andExpect(jsonPath("$[0]").doesNotExist())
                .andExpect(jsonPath("$.contactBeanResponseList[0].id", is(1)))
                .andExpect(jsonPath("$.contactBeanResponseList[0].givenName", is("John")));
    }
}
