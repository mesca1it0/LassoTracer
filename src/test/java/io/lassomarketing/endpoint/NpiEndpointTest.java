package io.lassomarketing.endpoint;


import io.lassomarketing.config.TestConfig;
import io.lassomarketing.service.NpiService;
import org.apache.http.client.utils.URIBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.net.URISyntaxException;

import static io.lassomarketing.config.TestConfig.TEST_NPI;
import static io.lassomarketing.service.NpiService.NPI_PARAM;
import static io.lassomarketing.service.NpiService.USER_ID_PARAM;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;


@Import(TestConfig.class)
@WebMvcTest(NpiEndpoint.class)
public class NpiEndpointTest {

    private static final String FINAL_PAGE_URL = "http://some-page.com";

    @Autowired
    private MockMvc mvc;

    @Test
    public void testRedirectToFinalPageWithNpiParam() throws Exception {

        mvc.perform(get("/redirectWithNpiParam")
                .queryParam(NpiService.LASSO_CLICK_URL_PARAM, buildLassoClickUrlWithoutNpi())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(redirectedUrl(buildRedirectedUrlWithNpi()));
    }


    private String buildLassoClickUrlWithoutNpi() throws URISyntaxException {
        return new URIBuilder(FINAL_PAGE_URL).addParameter(USER_ID_PARAM, "some-user-id").build().toString();
    }

    private String buildRedirectedUrlWithNpi() throws URISyntaxException {
        return new URIBuilder(buildLassoClickUrlWithoutNpi()).addParameter(NPI_PARAM, TEST_NPI).build().toString();
    }
}
