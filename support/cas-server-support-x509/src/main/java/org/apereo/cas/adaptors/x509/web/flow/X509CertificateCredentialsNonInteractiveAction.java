package org.apereo.cas.adaptors.x509.web.flow;

import java.security.cert.X509Certificate;

import org.apereo.cas.adaptors.x509.authentication.principal.X509CertificateCredential;
import org.apereo.cas.authentication.Credential;
import org.apereo.cas.web.flow.AbstractNonInteractiveCredentialsAction;
import org.springframework.webflow.execution.RequestContext;

/**
 * Concrete implementation of AbstractNonInteractiveCredentialsAction that
 * obtains the X509 Certificates from the HttpServletRequest and places them in
 * the X509CertificateCredential.
 *
 * @author Scott Battaglia
 * @since 3.0.0
 */
public class X509CertificateCredentialsNonInteractiveAction extends AbstractNonInteractiveCredentialsAction {

    private static final String CERTIFICATE_REQUEST_ATTRIBUTE = "javax.servlet.request.X509Certificate";

    @Override
    protected Credential constructCredentialsFromRequest(final RequestContext context) {
        final X509Certificate[] certificates = 
                (X509Certificate[]) context.getExternalContext().getRequestMap().get(CERTIFICATE_REQUEST_ATTRIBUTE);

        if (certificates == null || certificates.length == 0) {
            if (logger.isDebugEnabled()) {
                logger.debug("Certificates not found in request.");
            }
            return null;
        }
        logger.debug("Certificate found in request.");
        return new X509CertificateCredential(certificates);
    }
}
