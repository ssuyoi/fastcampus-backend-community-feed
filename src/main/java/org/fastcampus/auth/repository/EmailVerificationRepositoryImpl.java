package org.fastcampus.auth.repository;

import jakarta.transaction.Transactional;
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
    @Transactional
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

    @Override
    @Transactional
    public void verifyEmail(Email email, String token) {
        String emailAddress = email.getEmailText();

        EmailVerificationEntity verificationEntity = jpaEmailVerificationRespository.findByEmail(emailAddress).orElseThrow(() -> new IllegalArgumentException("인증 요청하지 않은 이메일입니다."));

        if(verificationEntity.isVerified()) {
            throw new IllegalArgumentException("이미 인증된 이메일입니다.");
        }

        if(!verificationEntity.hasSameToken(token)) {
            throw new IllegalArgumentException("토큰 값이 유효하지 않습니다.");
        }

        verificationEntity.verify();
    }

    @Override
    public boolean isEmailVerified(Email email) {
        EmailVerificationEntity entity = jpaEmailVerificationRespository.findByEmail(email.getEmailText()).orElseThrow(() -> new IllegalArgumentException("인증 요청하지 않은 이메일입니다."));
        return entity.isVerified();
    }
}
