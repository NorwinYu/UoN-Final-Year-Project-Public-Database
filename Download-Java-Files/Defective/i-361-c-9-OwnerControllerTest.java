package io.joca.petclinic.controllers;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import io.joca.petclinic.models.Owner;
import io.joca.petclinic.services.OwnerService;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

	@Mock
	OwnerService service;
	
	@InjectMocks
	OwnerController controller;
	
	Set<Owner> owners;
	MockMvc mock;
	
	@BeforeEach
	void setUp() {
		owners = new HashSet<Owner>();
		
		owners.add(Owner.builder().id(1L).build());
		owners.add(Owner.builder().id(2L).build());
		
		mock = MockMvcBuilders
				.standaloneSetup(controller)
				.build();
	}

	@Test
	void testListOwners() throws Exception {
		when(service.findAll()).thenReturn(owners);
		
		mock.perform(get("/owners"))
			.andExpect(status().isOk())
			.andExpect(view().name("owners/index"))
			.andExpect(model().attribute("owners", owners));
	}
	
	@Test
	void testListOwnersByIndex() throws Exception {
		when(service.findAll()).thenReturn(owners);
		
		mock.perform(get("/owners/index"))
			.andExpect(status().isOk())
			.andExpect(view().name("owners/index"))
			.andExpect(model().attribute("owners", owners));
	}

	@Test
	void testFind() throws Exception {
		mock.perform(get("/owners/find"))
			.andExpect(status().isOk())
			.andExpect(view().name("notimplemented"));
		
		verifyZeroInteractions(service);
	}
	
    @Test
    void displayOwner() throws Exception {
        when(service.findById(anyLong())).thenReturn(Owner.builder().id(1L).build());

        mock.perform(get("/owners/123"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/ownerDetails"))
                .andExpect(model().attribute("owner", hasProperty("id", is(1L))));
    }
}