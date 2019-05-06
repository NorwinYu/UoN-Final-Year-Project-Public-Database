package com.tribesbackend.tribes.controllers;

import com.tribesbackend.tribes.factories.KingdomFactory;
import com.tribesbackend.tribes.factories.TribesUserFactory;
import com.tribesbackend.tribes.factories.TroopFactory;
import com.tribesbackend.tribes.models.Kingdom;
import com.tribesbackend.tribes.models.TribesUser;
import com.tribesbackend.tribes.models.Troop;
import com.tribesbackend.tribes.repositories.KingdomRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;


import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Optional;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringJUnit4ClassRunner.class)
public class TroopRestControllerTest {

    private MockMvc mockMvc;
    @Mock
    KingdomRepository kingdomRepository;
    @InjectMocks
    TroopRestController troopRestController;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(troopRestController).build();
        TribesUser testUser = TribesUserFactory.createValidSampleTribesUser();
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(testUser.getUsername(), testUser.getPassword()));
    }

    @Test
    public void listTroopsTest() throws Exception{
        Kingdom kingdom = KingdomFactory.createValidSampleKingdom();
        Troop troop = TroopFactory.createSampleTroop();
        kingdom.getTroops().add(troop);
        troopRestController.setKingdomRepository(kingdomRepository);
        when(kingdomRepository.findKingdomByTribesUserUsername("Vojtisek")).thenReturn(Optional.of(kingdom));
        mockMvc.perform(get("/kingdom/troops"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.troops").isArray());
    }
}
