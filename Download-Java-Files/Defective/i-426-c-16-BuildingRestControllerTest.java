package com.tribesbackend.tribes.controllers;

import com.tribesbackend.tribes.factories.KingdomFactory;
import com.tribesbackend.tribes.factories.TribesUserFactory;
import com.tribesbackend.tribes.factories.TroopFactory;
import com.tribesbackend.tribes.models.Kingdom;
import com.tribesbackend.tribes.models.TribesUser;
import com.tribesbackend.tribes.models.Troop;
import com.tribesbackend.tribes.repositories.BuildingRepository;
import com.tribesbackend.tribes.repositories.KingdomRepository;
import com.tribesbackend.tribes.services.PurchaseService;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Optional;

import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class BuildingRestControllerTest {
    @Mock
    BaseController baseController;
    @Mock
    private KingdomRepository kingdomRepository;
    @Mock
    private BuildingRepository buildingRepository;
    @Mock
    PurchaseService purchaseService;
    @InjectMocks
    private BuildingRestController buildingRestController;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(buildingRestController).build();
        TribesUser testUser = TribesUserFactory.createValidSampleTribesUser();
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(testUser.getUsername(), testUser.getPassword()));
    }

//    @Test
//    public void createBuildingTest() throws Exception {
//        String json = "{\n" +
//                "  \"type\": \"farm\"\n" +
//                "}";
//
//        Kingdom mightykingdom = KingdomFactory.createValidSampleKingdom();
//        mightykingdom.setId((long) 1);
//        Mockito.when(kingdomRepository.findKingdomByTribesUserUsername("Vojtisek")).thenReturn(Optional.of(mightykingdom));
//        buildingRestController.setKingdomRepository(kingdomRepository);
//        Mockito.when(purchaseService.purchasableItem(mightykingdom.getId(), "farm", 1)).thenReturn(true);
//        mockMvc.perform(MockMvcRequestBuilders.post("/kingdom/buildings")
//                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
//                .content(json))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.type", Matchers.is("farm")))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.level", Matchers.is(1)))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.hp", Matchers.is(100)));
//    }

    @Test
    public void notResourcesTest() throws Exception {
        String json = "{\n" +
                "  \"type\": \"farm\"\n" +
                "}";
        Kingdom mightykingdom = KingdomFactory.createValidSampleKingdom();
        mightykingdom.setId((long) 1);
        Mockito.when(kingdomRepository.findKingdomByTribesUserUsername("Vojtisek")).thenReturn(Optional.of(mightykingdom));
        buildingRestController.setKingdomRepository(kingdomRepository);
        Mockito.when(purchaseService.purchasableItem(mightykingdom.getId(), "farm", 1)).thenReturn(false);
        mockMvc.perform(MockMvcRequestBuilders.post("/kingdom/buildings")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isConflict())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is("Not enough resources")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is("error")));
    }

    @Test
    public void invalidTypeTest() throws Exception {
        String json = "{\n" +
                "  \"type\": \"farmicka\"\n" +
                "}";
        mockMvc.perform(MockMvcRequestBuilders.post("/kingdom/buildings")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isNotAcceptable())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is("Invalid building type")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is("error")));
    }

    @Test
    public void missingParameterTest() throws Exception {
        String json = "{\n" +
                "  \"type\": \"\"\n" +
                "}";
        mockMvc.perform(MockMvcRequestBuilders.post("/kingdom/buildings")
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message", Matchers.is("Missing parameter(s): type!")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is("error")));
    }

}
