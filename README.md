# Pay Project Overview
![Overall Architecture](md_resource/Overall_Architecture_Image.png)

일반적인 간편결제 도메인을 주제로, MSA 를 중점적으로 만든 프로젝트입니다.

회원(Membership), 뱅킹(Banking), 머니(Money), 송금(Remittance), 결제(Payment), 정산(Settlement), 프랜차이즈(Franchise) 7개의 서비스로 구성되어 있으며, 각각의 독립적인 프로젝트로 구성되어 있습니다.


## Membership Service
고객의 회원 가입, 회원 정보 변경, 회원 정보 조회 등의 기능을 제공하는 서비스입니다.


### API Lists
- registerMembership
- registerMembershipEvent
- modifyMembership
- findMembership
- loginMemebership
- refreshJwtTokenByRefreshToken
- validateJwtToken
- getMembershipByJwtToken
#### Using Stack
  - Spring Boot, Java 11, Spring Data JPA, H2, Mysql, Lombok, Gradle, JWT, Axon Framework, Docker, Docker Compose, AWS DynamoDB, Vault

### Sequence Diagram Example (회원 가입, JWT 토큰 인증 프로세스) 
![Membership Sequence Example](md_resource/Membership_Sequence_Example.png)

## Banking Service 
고객의 계좌 정보 등록, 등록된 계좌 정보 조회, 입/출금, 거래내역 조회 등의 기능을 제공하는 서비스입니다.
  
### API Lists
- findBankAccount
- findFirmbanking
- registerBankAccount
- registerBankAccountByEvent
- requestFirmbanking
- requestFirmbankingByEvent
#### Using Stack
  - Spring Boot, Java 11, Spring Data JPA, H2, Mysql, Lombok, Gradle, JWT, Axon Framework, Docker, Docker Compose, Vault

### Sequence Diagram Example (입/출금 요청 프로세스)
![Banking Sequence Example](md_resource/Banking_Sequence_Example.png)

## Money Service
고객의 충전 잔액(머니) CRUD, 충전 내역 조회 등의 기능을 제공하는 서비스입니다.
    
### API Lists
- increaseMoneyRequest
- increaseMoneyRequestByEvent
- decreaseMoneyRequest
- decreaseMoneyRequestByEvent
- createMemberMoney
- getBalanceRequest

### money-query-service (for CQRS)
- queryMoneySumByRegion

#### Using Stack
- Spring Boot, Java 11, Spring Data JPA, H2, Mysql, Lombok, Gradle, JWT, Axon Framework, Docker, Docker Compose, Kafka, Kafka-ui, Zookeeper, AWS DynamoDB, Vault

### Sequence Diagram Example (충전 잔액(머니) 충전 프로세스)
![Money_Sequence_Example](md_resource/Money_Sequence_Example.png)

## Remittance Service
고객 간 송금 기능 및 송금 내역 정보 조회 등의 기능을 제공하는 서비스입니다.


### API Lists
- requestRemittance
- findRemittanceHistory


#### Using Stack
- Spring Boot, Java 11, Spring Data JPA, H2, Mysql, Lombok, Gradle, JWT, Axon Framework, Docker, Docker Compose, AWS DynamoDB, Vault

### Sequence Diagram Example (송금 프로세스)
![Remittance_Sequence_Example](md_resource/Remittance_Sequence_Example.png)

## Payment Service
가맹점에서 Pay 를 이용한 간편 결제 및 결제 내역 조회 등의 기능을 제공하는 서비스입니다. 


### API Lists
- requestPayment
- getNormalStatusPayments
- finishPayment

#### Using Stack
- Spring Boot, Java 11, Spring Data JPA,  Mysql, Lombok, Gradle, Axon Framework, Docker, Docker Compose, Vault

### Sequence Diagram Example (결제 프로세스)
![Payment_Sequence_Example](md_resource/Payment_Sequence_Example.png)


## Settlement Service
완료된 결제 내역을 기준으로 가맹점에 정산된 금액을 입금하고, 수수료 수취를 위한 기능을 제공하는 서비스입니다.

#### Using Stack
- Spring Boot, Java 11, Spring Data JPA, Mysql, Lombok, Gradle, JWT, Axon Framework, Docker, Docker Compose, Vault
 
### Sequence Diagram Example (정산 프로세스)
![Settlement_Sequence_Example](md_resource/Settlement_Sequence_Example.png)

## Service Endpoint & Swagger UI
- Membership Service
    - http://localhost:8081/membership/
    - http://localhost:8081/swagger-ui.html
- Banking Service
    - http://localhost:8082/banking/
    - http://localhost:8082/swagger-ui.html
- Money Service
    - http://localhost:8083/money/
    - http://localhost:8083/swagger-ui.html
- money-query-service (CQRS View Service)
    - http://localhost:8087/money/query/
    - http://localhost:8087/swagger-ui.html
- Remittance Service
    - http://localhost:8084/remittance/
    - http://localhost:8084/swagger-ui.html
- Payment Service
    - http://localhost:8085/payment/
    - http://localhost:8085/swagger-ui.html
- Settlement Service
    - http://localhost:8088/settlement/
    - http://localhost:8088/swagger-ui.html
- Franchise Service
  - http://localhost:8086/settlement/
  - http://localhost:8086/swagger-ui.html

# Infra Overview
![Overall Architecture](md_resource/infra.jpeg)

## Continuous Integration(진행중)
GitHub를 통해 코드를 관리하고, GitHub Actions를 사용하여 컨테이너 이미지를 생성한 후 Amazon ECR에 저장합니다. Helm을 통해 애플리케이션의 구성 변경 사항을 관리합니다.

## Continuous Deployment(진행중)
Terraform을 통해 Kubernetes 클러스터를 프로비저닝하며, 인프라를 코드로 관리합니다.

## Infrastructure configuration(진행중)

### AWS(진행중)
- 전체 인프라는 AWS VPC 내에서 운영되며, Kubernetes가 컨테이너화된 마이크로서비스를 관리합니다.

### Istio(진행중)
- 서비스 간의 통신을 관리하며, eBPF를 통해 성능 모니터링과 데이터 수집을 지원합니다.

### Monitoring and Testing(진행중)
- 모니터링 및 테스트: Prometheus, Loki, Jaeger와 같은 도구를 사용해 텔레메트리 데이터를 수집하고, Kiali 및 Grafana를 통해 시각화합니다. 또한, Chaos Mesh를 이용해 장애 주입 테스트를 수행하여 시스템의 복원력을 검증합니다.
