package softarchlab.cache.mybooks.test.web;

import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;

import softarchlab.cache.mybooks.domain.Reader;
import softarchlab.cache.mybooks.test.web.util.WithMockCustomUser;

@WithMockCustomUser
public class ProfileRestControllerTest extends BaseRestControllerTest {

    private final String RESOURCE_PATH = "/rest/profiles/";

    @Test
    public void getOne() throws Exception {
        Reader reader = loadOneReader();

        mockMvc.perform(get(RESOURCE_PATH + reader.getId())).andExpect(status().isOk())
                .andExpect(content().contentType(jsonContentType))

                .andExpect(jsonPath("$.id", is(reader.getId().intValue())))
                .andExpect(jsonPath("$.username", is(reader.getUsername())))
                .andExpect(jsonPath("$.email", is(reader.getEmail())))
                .andExpect(jsonPath("$.password", is(reader.getPassword())));
    }

    @Test
    public void update() throws Exception {
        Reader reader = loadOneReader();
        String usernameUpdated = reader.getUsername() + "-updated";
        reader.setUsername(usernameUpdated);
        String readerJson = json(reader);

        mockMvc.perform(put(RESOURCE_PATH + reader.getId()).with(csrf().asHeader()).contentType(jsonContentType)
                .content(readerJson))

                .andExpect(status().isOk()).andExpect(content().contentType(jsonContentType))

                .andExpect(jsonPath("$.id", is(reader.getId().intValue())))
                .andExpect(jsonPath("$.username", is(usernameUpdated)));
    }

}
