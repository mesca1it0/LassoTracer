package io.lassomarketing.endpoint;

import io.lassomarketing.service.NpiService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/")
public class NpiEndpoint {
    private final NpiService npiService;
    private static final String DEFAULT_REDIRECT_URL = "some_url";

    public NpiEndpoint(NpiService npiService) {
        this.npiService = npiService;
    }

    @GetMapping("/redirectWithNpiParam")
    public RedirectView redirectToFinalPageWithNpiParam(HttpServletRequest request) {
        String requestUrl = getFullHttpRequestUrl(request);
        String finalUrl = npiService.buildFinalPageRedirectWithNpiParam(requestUrl);
        return new RedirectView(finalUrl != null ? finalUrl : DEFAULT_REDIRECT_URL);
    }


    private String getFullHttpRequestUrl(HttpServletRequest request) {
        return request.getScheme() + "://" +
                request.getServerName() +
                ":" +
                request.getServerPort() +
                request.getRequestURI() +
                "?" +
                request.getQueryString();
    }
}
