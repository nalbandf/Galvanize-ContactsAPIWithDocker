package com.cognizant.galvanize.contacts.controller;


import com.cognizant.galvanize.contacts.domain.ContactBean;
import com.cognizant.galvanize.contacts.repository.ContactRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
        contactBean.setId("1");
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
                .andExpect(jsonPath("$.contactBeanResponseList[0].id", is("1")));
    }
}
