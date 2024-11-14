package org.example.cards.audit;

import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("auditAwareImplementation")
@Primary
public class AuditAwareImplementation implements AuditorAware<String> {
    @Override

    public Optional<String> getCurrentAuditor() {
        //Username from spring security should be used here
        return Optional.of("CARDS_MS");
    }
}
