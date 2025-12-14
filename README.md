# 📘 FastCampus Backend Community Feed Project

**패스트캠퍼스 백엔드 강의 실습용 커뮤니티 피드 서비스**

## 1. 프로젝트 개요
- **목표**: 자바 스프링 핵심 철학 준수 및 확장성 있는 아키텍처 구축
- **특징**: 도메인형 패키지 구조, 헥사고날 아키텍처 적용

## 2. 폴더 구조
- **구조**: 도메인별 패키지 분리 (Domain-Driven Packaging)
```
src/main/java/org/fastcampus
├── common       # 공통 유틸리티, 설정
├── post         # 게시글 도메인 (Service, Entity, Repository, Controller)
└── user         # 사용자 도메인 (Service, Entity, Repository, Controller)
```

## 3. 아키텍처 (Architecture)
- **헥사고날 아키텍처 (Hexagonal Architecture)** 적용
- **데이터 흐름**: `Controller` → `Service` → `Domain` ← `Repository`
- **레이어별 역할**:
    - **UI Layer (`ui`)**: HTTP 요청/응답 처리, DTO 사용
    - **Application Layer (`application`)**: 비즈니스 흐름 제어, 트랜잭션 관리 (`@Transactional`)
    - **Domain Layer (`domain`)**: 핵심 비즈니스 로직, POJO (JPA 의존성 없음)
    - **Repository Layer (`repository`)**: 데이터 저장/조회 구현 (`JpaRepository`)

## 4. 핵심 기술 (Key Concepts)
- **DI (Dependency Injection) & IoC**:
    - 객체 간 결합도 감소, 테스트 용이성 증대
    - 생성자 주입 (`@RequiredArgsConstructor`) 권장
- **JPA (Java Persistence API)**:
    - 객체-RDB 매핑 (`@Entity`), 영속성 컨텍스트 활용
    - 도메인 객체(`User`)와 DB 엔티티(`UserEntity`) 분리 원칙
- **REST API**:
    - 자원 기반의 API 설계 (`@RestController`)
    - 엔티티 대신 DTO(`Request/Response`) 사용으로 안정성 확보
- **테스트 (Testing)**:
    - **Unit Test**: 도메인/서비스 로직 격리 검증 (JUnit 5)
    - **Acceptance Test**: API 전 구간 검증 (RestAssured, `@SpringBootTest`)
    - **H2 Database**: 빠르고 간편한 테스트 환경 구축

## 5. Part 1 요약
- **도메인 중심 설계 (DDD)**: 비즈니스 로직의 중요성 강조
- **유연한 구조**: 인터페이스 기반 설계로 구현체 교체 용이
- **테스트 주도**: 안정적인 서비스 운영을 위한 테스트 코드 작성 습관화
