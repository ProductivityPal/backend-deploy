package pl.edu.agh.productivitypal.repository;

import org.joda.time.DateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import pl.edu.agh.productivitypal.model.EmailVerification;

import java.util.List;
import java.util.Optional;

public interface EmailVerificationRepository extends
        JpaRepository<EmailVerification, Long>,
        JpaSpecificationExecutor<EmailVerification> {

    boolean existsByCode(String code);

    Optional<EmailVerification> findByUserIdAndCodeAndIsActiveIsTrueAndCreatedLessThan(Integer id, String code, DateTime created);

    List<EmailVerification> findAllByUserIdAndIsActiveIsTrue(Integer id);
}
