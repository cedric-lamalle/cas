package org.apereo.cas.support.claims;

import org.apereo.cas.authentication.ProtocolAttributeEncoder;
import org.apereo.cas.services.RegisteredService;
import org.apereo.cas.util.EncodingUtils;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * This is {@link WsFederationClaimsEncoder}.
 *
 * @author Misagh Moayyed
 * @since 5.3.0
 */
@Slf4j
public class WsFederationClaimsEncoder implements ProtocolAttributeEncoder {

    @SuppressFBWarnings("PSC_SUBOPTIMAL_COLLECTION_SIZING")
    @Override
    public Map<String, Object> encodeAttributes(final Map<String, Object> attributes, final RegisteredService service) {
        val finalAttributes = new HashMap<String, Object>(attributes.size());
        attributes.forEach((k, v) -> {
            val attributeName = EncodingUtils.hexDecode(k);
            if (StringUtils.isNotBlank(attributeName)) {
                LOGGER.debug("Decoded SAML attribute [{}] to [{}] with value(s) [{}]", k, attributeName, v);
                finalAttributes.put(attributeName, v);
            } else {
                LOGGER.debug("Unable to decode SAML attribute [{}]; accepting it verbatim", k);
                finalAttributes.put(k, v);
            }
        });
        return finalAttributes;
    }

    /**
     * Encode claim string.
     *
     * @param claim the claim
     * @return the string
     */
    public static String encodeClaim(final String claim) {
        val attributeName = EncodingUtils.hexDecode(claim);
        if (StringUtils.isNotBlank(attributeName)) {
            LOGGER.debug("Decoded SAML attribute [{}] to [{}] with value(s) [{}]", claim, attributeName, claim);
            return attributeName;
        }
        LOGGER.debug("Unable to decode SAML attribute [{}]; accepting it verbatim", claim);
        return claim;
    }
}

