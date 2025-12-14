package org.fastcampus.auth.repository;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.fastcampus.auth.application.interfaces.EmailVerificationRepository;
import org.fastcampus.auth.domain.Email;
import org.fastcampus.auth.repository.entity.EmailVerificationEntity;
import org.fastcampus.auth.repository.jpa.JpaEmailVerificationRespository;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class EmailVerificationRepositoryImpl implements EmailVerificationRepository {

    private final JpaEmailVerificationRespository jpaEmailVerificationRespository;

    @Override
    public void createEmailVerification(Email email, String token) {
        String emailAddress = email.getEmailText();
        Optional<EmailVerificationEntity> entity = jpaEmailVerificationRespository.findByEmail(emailAddress);

        if(entity.isPresent()) {
            EmailVerificationEntity verificationEntity = entity.get();

            if(verificationEntity.isVerified()) {
                throw new IllegalArgumentException("이미 인증된 이메일입니다.");
            }

            verificationEntity.updateToken(token);
            return;
        }

        EmailVerificationEntity verificationEntity = new EmailVerificationEntity(emailAddress, token);
        jpaEmailVerificationRespository.save(verificationEntity);
    }
}
