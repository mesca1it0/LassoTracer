package io.lassomarketing.service;

import io.lassomarketing.repository.NpiRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.client.utils.URLEncodedUtils;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

@Service
@Slf4j
public class NpiService {

    private final NpiRepository npiRepository;

    public static final String LASSO_CLICK_URL_PARAM = "redir";
    public static final String USER_ID_PARAM = "uuid";
    public static final String NPI_PARAM = "npi";

    public NpiService(NpiRepository npiRepository) {
        this.npiRepository = npiRepository;
    }


    public String buildFinalPageRedirectWithNpiParam(String apnUrl) {
        String lassoClickUrl = extractParamFromUrl(apnUrl, LASSO_CLICK_URL_PARAM);
        if (lassoClickUrl == null) {
            log.error("Param " + LASSO_CLICK_URL_PARAM + " not found in " + apnUrl);
            return null;
        }
        String userId = extractParamFromUrl(lassoClickUrl, USER_ID_PARAM);
        if (userId == null) {
            log.info("Param " + USER_ID_PARAM + " not found in " + lassoClickUrl);
            return lassoClickUrl;
        }
        String npi = getNpiByUserId(userId);
        if (npi == null) {
            log.info("No npi found for user with id " + userId);
            return lassoClickUrl;
        }
        return addNpiParamToUrl(lassoClickUrl, npi);
    }


    private String getNpiByUserId(String userId) {
        return npiRepository.getByUserId(userId);
    }

    private String addNpiParamToUrl(String url, String npi) {
        try {
            return new URIBuilder(url).addParameter(NPI_PARAM, npi).build().toString();
        } catch (URISyntaxException e) {
            log.error("Can not append " + NPI_PARAM + " param to url " + url);
            return null;
        }
    }

    private String extractParamFromUrl(String url, String param) {
        try {
            return URLEncodedUtils.parse(new URI(url), StandardCharsets.UTF_8)
                    .stream()
                    .filter(keyValue -> param.equals(keyValue.getName()))
                    .map(NameValuePair::getValue)
                    .findFirst().orElse(null);
        } catch (URISyntaxException e) {
            log.error("Can not parse " + param + " param in url " + url, e);
        }
        return null;
    }
}
