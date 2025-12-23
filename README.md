# 📝 FastCampus Backend Community Feed - 프로젝트 분석 및 강의 노트

**대상**: 스프링 입문자 ~ 주니어 개발자
**목표**: 스프링 부트와 JPA를 활용한 소셜 미디어 피드 시스템 구현 원리 파악

---

## 1. 프로젝트 개요 (Overview)
이 프로젝트는 사용자 간의 팔로우 관계를 기반으로 게시글을 작성하고, 피드(Feed)를 통해 친구들의 소식을 받아보는 소셜 네트워크 서비스(SNS)의 핵심 기능을 구현한 예제입니다.

### 🛠 기술 스택 (Tech Stack)
- **Framework**: Spring Boot 3.3.1
- **Language**: Java 17+
- **Database**: 
    - **MySQL**: 사용자, 게시글, 관계 데이터 등 영구 저장 (JPA 사용)
    - **Redis**: 뉴스 피드(Feed)의 빠른 조회를 위한 캐시 및 큐 용도
- **ORM**: Spring Data JPA + QueryDSL (복잡한 쿼리 처리)
- **Auth**: JWT (JSON Web Token) 기반 인증
- **Test**: JUnit 5, H2 Database (테스트용 DB)

---

## 2. 아키텍처 및 설계 (Architecture & Design)

이 프로젝트는 **헥사고날 아키텍처(Hexagonal Architecture)** 스타일을 차용하여 도메인 로직을 외부 의존성(DB, Web 등)으로부터 격리시켰습니다.

### 🏗 패키지 구조 (Package Structure)
`도메인 중심 패키징 (Package by Feature)` 방식을 따릅니다.
- `user`: 사용자 관련 모든 코드
- `post`: 게시글 및 피드 관련 코드
- `auth`: 인증/인가 관련 코드
- `common`: 공통 유틸리티

### 🧱 레이어별 역할 (Layer Roles)
1. **UI Layer (`ui`)**: 
    - `Controller` 위치. 외부 요청(HTTP)을 받아 DTO로 변환하여 Application 계층으로 전달합니다.
    - 예: `FeedController`, `UserController`
2. **Application Layer (`application`)**: 
    - `Service` 위치. 비즈니스 흐름을 제어하고 트랜잭션을 관리합니다.
    - 예: `UserService`, `PostService`
3. **Domain Layer (`domain`)**: 
    - 순수한 비즈니스 객체(POJO). JPA 어노테이션(`@Entity`) 없이 비즈니스 규칙만을 담습니다.
    - 예: `User` (도메인 객체), `Post` (도메인 객체)
4. **Repository Layer (`repository`)**: 
    - 데이터 저장소 구현. JPA Entity와 Repository 구현체가 위치합니다.
    - **특징**: 도메인 객체(`User`)를 DB 엔티티(`UserEntity`)로 변환하여 저장합니다.

> **💡 Beginner Tip**: 왜 Entity와 Domain을 분리하나요?
> DB 테이블 구조(`Entity`)와 핵심 비즈니스 로직(`Domain`)은 변경 주기가 다릅니다. 둘을 분리함으로써 DB 스키마 변경이 비즈니스 로직에 영향을 주지 않도록 하고, 테스트하기 쉬운 순수한 자바 객체로 로직을 관리할 수 있습니다.

---

## 3. 핵심 도메인 분석 (Domain Analysis)

### 👤 사용자 (User Domain)
- **책임**: 회원 가입, 프로필 조회, 팔로우/언팔로우 관리.
- **주요 클래스**:
    - `UserEntity`: `community_user` 테이블 매핑. 팔로워/팔로잉 카운트를 가짐.
    - `UserRelationEntity`: `community_user_relation` 테이블. 팔로우 관계(누가 누구를 팔로우하는지) 저장.
    - `UserRelationService`: 팔로우 로직 처리. 팔로우 시 `UserRelationRepository`에 저장하고 카운트를 증가시킴.

### 📝 게시글 (Post Domain)
- **책임**: 게시글 작성, 수정, 좋아요, **피드 발행**.
- **주요 클래스**:
    - `PostEntity`: `community_post` 테이블. 작성자(`UserEntity`)와 관계를 맺음.
    - `PostService`: 게시글 작성 시 `PostRepository`를 통해 저장.

### 🔐 인증 (Auth Domain)
- **책임**: 이메일 인증, 로그인, 토큰 발급.
- **흐름**:
    1. 가입 시 `EmailVerificationService`로 이메일 검증.
    2. 로그인 시 `AuthService`가 DB 확인 후 `TokenProvider`로 JWT 발급.
    3. 클라이언트는 이후 요청 헤더에 JWT를 담아 보냄.

---

## 4. 🚀 핵심 기능: 피드 구현 (Feed Architecture)

이 프로젝트의 **하이라이트**입니다. 팔로우한 친구들의 글을 모아보는 "피드"를 어떻게 효율적으로 구현했을까요?

### 🔄 Push Model (Fan-out on Write) 방식
사용자가 게시글을 작성하는 시점에 팔로워들의 피드 큐(Loop)에 게시글 ID를 미리 넣어두는 방식입니다.

1. **게시글 작성 (`PostService.createPost`)**:
    - 사용자가 글을 씁니다.
    - `PostRepository.save()`가 호출됩니다.

2. **피드 발행 (`PostRepositoryImpl` -> `UserPostQueueCommandRepository`)**:
    - DB에 글을 저장한 후, **작성자의 팔로워 목록**을 모두 조회합니다 (`jpaUserRelationRepository.findFollower`).
    - **Redis**를 사용하여 모든 팔로워들의 "피드 리스트"에 새 글의 ID를 추가합니다 (`redisRepository.publishPostToFollowingList`).
    - *참고: 코드를 보면 JPA로 `UserPostQueueEntity`를 생성하는 로직도 보이지만, 실제 발행은 Redis를 중심으로 이루어짐을 알 수 있습니다.*

3. **피드 조회 (`FeedController`)**:
    - 사용자가 피드를 요청하면 DB를 복잡하게 조인(Join)하지 않습니다.
    - 단순히 내 Redis 큐에 쌓인 "게시글 ID 목록"을 가져와서 내용을 보여줍니다.
    - **장점**: 읽기 속도가 매우 빠릅니다. (조회 시점에 연산이 거의 없음)
    - **단점**: 팔로워가 수백만 명인 인플루언서가 글을 쓰면, 수백만 번의 쓰기 작업이 발생(Fan-out 부하)할 수 있습니다.

---

## 5. 🔔 알림 시스템 (Notification System)

사용자 경험을 높이기 위해 "좋아요" 등의 이벤트 발생 시 푸시 알림을 발송합니다. **Firebase Cloud Messaging (FCM)** 을 활용합니다.

### 🔑 FCM 토큰 관리
- **저장 시점**: 사용자가 **로그인**할 때 (`AuthService.login` -> `UserAuthRepositoryImpl.loginUser`), 클라이언트로부터 받은 FCM 토큰을 `FcmTokenEntity`에 저장하거나 갱신합니다.
- **테이블**: `community_fcm_token` (Key: UserId, Value: Token)

### 📨 알림 발송 흐름 (예: 좋아요 알림)
1. **좋아요 요청**: 사용자가 `PostService.likePost`를 호출합니다.
2. **이벤트 감지**: `LikeRepositoryImpl.like` 메서드가 실행됩니다.
3. **알림 전송**:
    - `MessageRepository.sendLikeMessage(sender, targetUser)`가 호출됩니다.
    - 게시글 작성자(`targetUser`)의 FCM 토큰을 DB에서 조회합니다.
    - `FirebaseMessaging`을 통해 비동기(`sendAsync`)로 메시지를 발송합니다.

> **💡 Note**: 알림 기능은 핵심 비즈니스 로직(게시글 작성, 좋아요 등)의 성공 여부와 독립적으로 동작하거나, 실패하더라도 메인 트랜잭션을 롤백시키지 않도록 설계하는 것이 일반적입니다. (이 프로젝트에서는 `sendAsync`를 사용하여 비동기 처리)

---

## 6. 학습 포인트 & 상관관계 요약

- **User ↔ Auth**: `AuthService`에서 회원가입 완료 시 `UserEntity`와 `UserAuthEntity`를 함께 생성하여 계정 정보와 인증 정보를 분리 관리합니다.
- **User ↔ Post**: `Post`는 작성자(`User`) 정보를 가집니다. JPA의 `@ManyToOne` 관계를 사용하지만, 도메인 레벨에서는 ID 참조 등을 통해 결합을 느슨하게 할 수도 있습니다.
- **JPA & Redis의 조화**:
    - **영구 저장 (Source of Truth)**: MySQL (사용자 정보, 게시글 내용)
    - **기능 가속 (Cache/Feature)**: Redis (피드 시스템의 속도 향상)

### 🌟 결론
이 프로젝트는 단순한 CRUD 게시판을 넘어, **대용량 트래픽을 고려한 피드 시스템의 기초**를 다지고 있습니다. 특히 **도메인과 영속성 계층의 분리**, **Push 모델을 이용한 피드 최적화**는 백엔드 개발자로서 꼭 이해해야 할 중요한 패턴입니다.
