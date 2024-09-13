
# KT 하이오더 프로젝트

![image](https://github.com/user-attachments/assets/12b180db-4810-4acf-8a40-062418850c79)


---

## 목차

1. 서비스 시나리오
    
    1.1 기능적 요구사항
    
    1.2 비기능적 요구사항
    
2. 체크포인트
    
    2.1 이벤트스토밍
    
    2.2 서브 도메인, 바운디드 컨텍스트 분리
    
    2.3 컨텍스트 매핑 / 이벤트 드리븐 아키텍처
    
    2.4 헥사고날 아키텍처
    
    2.5 구현
    
3. 분석/설계
    
    3.1 최적화된 조직 구조 선정
    
    3.2 Event Storming

    3.3 MSA 요구사항 검증
    
4. 구현 및 운영

    4.1 개발/ 기능 구현
   
    4.2 클라우드 배포

   
---

## 1. 서비스 시나리오

### 1.1 기능적 요구사항

### 고객 기능 요구사항

1. **서비스 접속 및 로그인**
    - 고객은 테이블에 부착된 QR 코드를 스캔하여 하이오더 서비스에 접속하고, 로그인하여 서비스를 이용한다.
      
2. **메인 페이지 구성**
    - **카테고리별 메뉴 소개**
        - 각 카테고리별로 음식 메뉴가 나열되어 있으며, 메뉴명, 가격, 사진이 포함된다.
    - **하단 기능 버튼**
        - **메뉴 선택**: 장바구니로 이동하여 담은 내역을 확인하고, 수정할 수 있다.
          
        - **채팅**: 매장 운영자와 실시간으로 문의 및 상담이 가능한 채팅 페이지로 이동한다.
        
        - **주문 내역**: 해당 테이블의 전체 주문 내역을 확인할 수 있다.
   
    - **우측 상단 아이콘**
        - **순위**: 매장 전체 메뉴의 인기 순위를 확인할 수 있다.
          
3. **메뉴 선택 및 주문 과정**
    - **메뉴 상세 정보**
        - 고객이 메인 페이지에서 특정 메뉴를 클릭하면, 해당 메뉴의 상세 정보(사진, 메뉴명, 가격, 설명, 주문수, 순위)를 확인할 수 있는 상세 페이지로 이동한다.
    - **장바구니 담기**
        - 고객은 원하는 메뉴를 선택하고 ‘주문하기’ 버튼을 클릭하여 장바구니에 담을 수 있다.
    - **주문 확정**
        - 장바구니에서 선택된 메뉴의 개수와 총 금액을 확인한 후, ‘주문하기’ 버튼을 클릭하면 주문이 최종 확정된다.
          
4. **주문 내역 확인**
    - 고객은 주문 내역 페이지에서 자신이 주문한 메뉴의 상세 내역(메뉴명, 가격, 수량)을 확인할 수 있다.
    - 고객은 매장 운영자가 주문 접수 시, 메뉴 완성 시 해당 내용에 대한 푸시 알림을 받는다.
      
5. **문의 채팅**:
    - 고객은 매장 운영자와 실시간 채팅을 통해 메뉴나 주문에 대한 문의 사항을 해결할 수 있다.

### 매장 운영자 기능 요구사항

1. **서비스 접속 및 로그인**
    - 매장 운영자는 하이오더 서비스에 로그인한다.
    
2. **메인 페이지 구성**
    - **테이블 관리**
        - 각 테이블의 주문 상태를 관리하고, 새로운 주문과 기존 주문 내역, 총 금액을 확인한다.
    - **메뉴 관리**
        - 메뉴 추가, 수정, 제거 등의 메뉴 관리 기능을 수행한다
    - **주문 관리**
        - 실시간으로 업데이트되는 주문을 모니터링하며, 주문 상태를 관리한다.
    - **문의 채팅**
        - 고객의 문의에 실시간으로 응답하여 문제를 해결한다.
        
3. **테이블 관리**
    - 운영자는 테이블 관리 페이지에서 각 테이블의 주문 상태와 내역을 실시간으로 확인하고 관리한다.
    
4. **메뉴 관리**
    - 운영자는 메뉴 관리 페이지에서 새로운 메뉴를 추가하거나 기존 메뉴를 수정, 제거한다.
    
5. **주문 관리**
    - 운영자는 주문 관리 페이지에서 실시간으로 주문을 확인하고, 주문 접수, 메뉴 완성 등의 상태를 업데이트하여 고객에게 푸시 알림을 통해 전달한다.
    
6. **문의 채팅**:
    - 운영자는 문의 채팅 페이지에서 고객의 질문에 실시간으로 응답하고 문제를 해결할 수 있다.
    

### 1.2 비기능적 요구사항

1. **트랜잭션 관리**
    - **주문 트랜잭션**: 주문이 생성되면 모든 관련 데이터는 트랜잭션 단위로 일관성 있게 처리되어야 하며, 주문 데이터의 무결성을 보장해야 한다. (Sync 호출)
    - **주문 상태 관리**: 주문 상태가 변경될 때마다 고객과 매장 운영자가 정확하고 일관된 정보를 확인할 수 있어야 하며, 시스템은 이와 관련된 데이터를 지속적으로 업데이트하고 반영해야 한다.

2. **장애 격리**
    - **장애 격리**: 메뉴 관리나 채팅 기능에 장애가 발생하더라도, 고객의 주문 처리 기능은 365일 24시간 원활하게 작동해야 한다. (Async, Event-driven, Eventual Consistency)
    
3. **성능**
    - **주문 상태 조회**: 고객이 주문 상태를 실시간으로 확인할 수 있어야 하며, 모든 상태 업데이트는 최대한 신속하게 처리되어야 한다. (CQRS)
    - **응답 시간**: 시스템의 모든 사용자 인터페이스(UI)는 3초 이내에 응답해야 하며, 높은 트래픽 상황에서도 성능 저하 없이 원활하게 작동해야 한다.


---

## 2. 체크포인트

### 2.1 이벤트스토밍

- 스티커 색상별 객체의 의미를 제대로 이해하여 헥사고날 아키텍처와의 연계 설계에 적절히 반영하고 있는가?
- 각 도메인 이벤트가 의미있는 수준으로 정의되었는가?
- 어그리게잇: Command와 Event 들을 ACID 트랜잭션 단위의 Aggregate 로 제대로 묶었는가?
- 기능적 요구사항과 비기능적 요구사항을 누락 없이 반영하였는가?

### 2.2 서브 도메인, 바운디드 컨텍스트 분리

- 팀별 KPI 와 관심사, 상이한 배포주기 등에 따른 Sub-domain 이나 Bounded Context 를 적절히 분리하였고 그 분리 기준의 합리성이 충분히 설명되는가?
    - 적어도 3개 이상 서비스 분리
- 폴리글랏 설계: 각 마이크로 서비스들의 구현 목표와 기능 특성에 따른 각자의 기술 Stack 과 저장소 구조를 다양하게 채택하여 설계하였는가?
- 서비스 시나리오 중 ACID 트랜잭션이 크리티컬한 Use 케이스에 대하여 무리하게 서비스가 과다하게 조밀히 분리되지 않았는가?

### 2.3 컨텍스트 매핑 / 이벤트 드리븐 아키텍처

- 업무 중요성과 도메인간 서열을 구분할 수 있는가? (Core, Supporting, General Domain)
- Request-Response 방식과 이벤트 드리븐 방식을 구분하여 설계할 수 있는가?
- 장애격리: 서포팅 서비스를 제거 하여도 기존 서비스에 영향이 없도록 설계하였는가?
- 신규 서비스를 추가 하였을때 기존 서비스의 데이터베이스에 영향이 없도록 설계(열려있는 아키택처)할 수 있는가?
- 이벤트와 폴리시를 연결하기 위한 Correlation-key 연결을 제대로 설계하였는가?

### 2.4 헥사고날 아키텍처

- 설계 결과에 따른 헥사고날 아키텍처 다이어그램을 제대로 그렸는가?

### 2.5 구현

- [DDD] 분석단계에서의 스티커별 색상과 헥사고날 아키텍처에 따라 구현체가 매핑되게 개발되었는가?
    - Entity Pattern 과 Repository Pattern 을 적용하여 JPA 를 통하여 데이터 접근 어댑터를 개발하였는가
    - [헥사고날 아키텍처] REST Inbound adaptor 이외에 gRPC 등의 Inbound Adaptor 를 추가함에 있어서 도메인 모델의 손상을 주지 않고 새로운 프로토콜에 기존 구현체를 적응시킬 수 있는가?
    - 분석단계에서의 유비쿼터스 랭귀지 (업무현장에서 쓰는 용어) 를 사용하여 소스코드가 서술되었는가?
- Request-Response 방식의 서비스 중심 아키텍처 구현
    - 마이크로 서비스간 Request-Response 호출에 있어 대상 서비스를 어떠한 방식으로 찾아서 호출 하였는가? (Service Discovery, REST, FeignClient)
    - 서킷브레이커를 통하여 장애를 격리시킬 수 있는가?
- 이벤트 드리븐 아키텍처의 구현
    - 카프카를 이용하여 PubSub 으로 하나 이상의 서비스가 연동되었는가?
    - Correlation-key: 각 이벤트 건 (메시지)가 어떠한 폴리시를 처리할때 어떤 건에 연결된 처리건인지를 구별하기 위한 Correlation-key 연결을 제대로 구현 하였는가?
    - Message Consumer 마이크로서비스가 장애상황에서 수신받지 못했던 기존 이벤트들을 다시 수신받아 처리하는가?
    - Scaling-out: Message Consumer 마이크로서비스의 Replica 를 추가했을때 중복없이 이벤트를 수신할 수 있는가
    - CQRS: Materialized View 를 구현하여, 타 마이크로서비스의 데이터 원본에 접근없이(Composite 서비스나 조인SQL 등 없이) 도 내 서비스의 화면 구성과 잦은 조회가 가능한가?
- 폴리글랏 플로그래밍
    - 각 마이크로 서비스들이 하나이상의 각자의 기술 Stack 으로 구성되었는가?
    - 각 마이크로 서비스들이 각자의 저장소 구조를 자율적으로 채택하고 각자의 저장소 유형 (RDB, NoSQL, File System 등)을 선택하여 구현하였는가?
- API 게이트웨이
    - API GW를 통하여 마이크로 서비스들의 집입점을 통일할 수 있는가?
    - 게이트웨이와 인증서버(OAuth), JWT 토큰 인증을 통하여 마이크로서비스들을 보호할 수 있는가?

---

## 3. 분석/설계

### 3.1 최적화된 조직 구조 선정

각 기능별 마이크로 서비스를 기반으로 하이오더 서비스를 구축
서비스 간 독립성과 확장성을 보장하면서도 전체 시스템이 원활하게 동작할 수 있도록 설계


### As-Is 조직

![image](https://github.com/user-attachments/assets/cce90256-425e-4fe0-8c3a-8b09525a6d0e)


- 각 팀의 역할이 기능별로 구성된 수평적 구조
- 팀원은 UI, 백엔드, 데이터베이스 관리 등 특정 기능에만 집중
- 프로젝트 전반에 걸쳐 기능적인 전문성을 발휘
- 기능 간 조율에 장기간 소요 및 기능 간 영향도 高
- 성과/이슈 발생 시 책임 소재 불명확

### To-Be 조직

![image](https://github.com/user-attachments/assets/46916fac-e066-41fb-80c4-b8cc7f919cc3)



- 서비스별로 팀이 구성된 수직적인 구조
- 특정 마이크로서비스(예: 로그인 및 접근 관리, 메뉴 관리, 주문 관리 등)를 책임
- PO, UI 개발자, 백엔드 개발자, DBA가 한 팀으로 구성되어 서비스를 독립적으로 운영
- 서비스 간의 독립성을 강화하여 빠른 개발과 배포가 가능


### 3.2 Event Storming

https://www.msaez.io/#/storming/team6board_share

1. **Event 도출**
    
    ![image](https://github.com/user-attachments/assets/b6f4aa05-84a3-4f18-be06-453d75b5d9e1)


    

2. **Actor, Command 추가**
    
    ![image](https://github.com/user-attachments/assets/037c6f03-4434-4506-89e7-c1a8c6be432f)


    

3. **Aggregate 분류**
    
    ![image](https://github.com/user-attachments/assets/3a616103-bd48-4fd3-b8e5-3b7326d2870e)


    

4. **Bounded Context 집합**
    
    ![image](https://github.com/user-attachments/assets/06c1b2a1-99c9-472f-85c7-da2431da6870)



    
5. **Policy 생성**
    
    ![image](https://github.com/user-attachments/assets/6982b66d-6842-4d4e-912e-2f9f5658f77b)



    
6. **Context 매핑**
    
    ![image](https://github.com/user-attachments/assets/769daceb-cc3a-415e-aee3-005e51b490d6)



### **3.3 요구사항 커버 검증**

**1. 기능적 요구사항 > 고객**
        
![image](https://github.com/user-attachments/assets/54fbc9e6-cb5e-4390-b5d1-adae91f9a4e2)


        
상기 모델은 고객 기능 요구사항, 매장 운영자 요구사항을 충족함

    
**Order Bounded Context 내 고객의 메뉴 선택 및 주문 과정 시나리오 충족**
        
     
        
![image](https://github.com/user-attachments/assets/b0ab5253-3ea3-40e9-906f-aa170b326e8c)



- 메뉴 상세 정보
    
    고객이 메인 페이지에서 특정 메뉴를 클릭하면, 해당 메뉴의 상세 정보(사진, 메뉴명, 가격, 설명, 주문수, 순위)를 확인할 수 있는 상세 페이지로 이동한다.
    
- 장바구니 담기
    
    고객은 원하는 메뉴를 선택하고 ‘주문하기’ 버튼을 클릭하여 장바구니에 담을 수 있다.
    
- 주문 확정
    
    장바구니에서 선택된 메뉴의 개수와 총 금액을 확인한 후, ‘주문하기’ 버튼을 클릭하면 주문이 최종 확정된다.  


   
        

**고객의 주문 내역 확인**
        
        
![image](https://github.com/user-attachments/assets/8216ee5a-b53a-4325-8a8c-d324aa3358f0)



- 고객은 주문 내역 페이지에서 자신이 주문한 메뉴의 상세 내역(메뉴명, 가격, 수량)을 확인할 수 있다.
 

**채팅 및 알림 기능**  
- 해당 기능은 Frontend 단에서 구현

     
**2. 기능적 요구사항 > 매장 운영자**
        
        
**매장 운영자의 하이오더 서비스 접속 및 로그인 시나리오 충족**

![image](https://github.com/user-attachments/assets/f7f2a63b-b393-415e-8d75-5561d757eeeb)


        
- 매장 운영자는 하이오더 서비스에 로그인한다.  
          
**매장 운영자의 메뉴 관리 시나리오 및 주문 테이블 관리 시나리오 충족**

![image](https://github.com/user-attachments/assets/18e84b46-a890-46d6-a785-6c6812fad149)



- 운영자는 메뉴 관리 페이지에서 새로운 메뉴를 추가하거나 기존 메뉴를 수정, 제거한다.  

- 운영자는 테이블 관리 페이지에서 각 테이블의 주문 상태와 내역을 실시간으로 확인하고 관리한다.
  
**채팅 및 알림 기능**  
- 해당 기능은 Frontend 단에서 구현
        
### 2. 비기능적 요구사항 
        
![image](https://github.com/user-attachments/assets/4ef77ec7-553a-43b0-a44b-28cdef8857b0)


        
상기 이벤트 스토밍 다이어그램은 비기능적 세부 요구사항 3가지를 모두 충족함  
        
        
**1. 트랜잭션 관리**

1.1 주문 트랜잭션

- 이벤트 스토밍 다이어그램에서는 Order와 관련된 주문 생성, 상태 업데이트가 명확하게 정의
- AddToCart 및 Order 커맨드와 이를 반영하는 OrderPlaced 이벤트는 Sync 호출 방식으로 처리

1.2 주문 상태 관리

- 주문이 생성된 후, 주문 상태는 StatusUpdated 이벤트를 통해 관리
- OrderManagement 서비스와 Push 서비스가 상호작용하여 주문 상태가 변경될 때마다 이벤트를 발생 - Eventual Consistency 방식을 적용해 주문 상태가 일관되게 유지되고, 데이터가 확실히 반영되는지 검증 가능

**2. 장애 격리**

- 다이어그램에서 Menu, Chat, Order 서비스가 각각 독립적인 마이크로서비스로 처리
- Menu나 Chat 서비스에 장애가 발생하더라도, Order 서비스는 비동기 호출 및 이벤트 기반(Event-driven) 구조로 설계되어 있어 고객의 주문 처리는 영향받지 않음

**3. 성능**

3.1 주문 상태 조회

- 주문 상태는 OrderManagement에서 관리
- 고객이 실시간으로 자신의 주문 상태를 조회

3.2 응답 시간

- Login, Order, Menu, Chat 등의 서비스는 각각 독립적인 마이크로서비스로 운영 및 처리 시스템은 트래픽 증가 상황에서도 마이크로서비스 간 독립성을 통해 성능 저하 없이 빠르게 응답 가능

        
### 3.4 헥사고날 아키텍처 다이어그램 도출

![image](https://github.com/user-attachments/assets/37b90fff-b190-4295-b1ce-57cda8e53ed1)


---

## 4. 구현 및 운영

### **4.1 개발/ 기능 구현**

분석/설계 단계에서 도출된 헥사고날 아키텍처에 따라, 각 BC별로 대변되는 마이크로 서비스들을 스프링부트와 Flutter로 구현하였다. 구현한 각 서비스를 로컬에서 실행하는 방법은 아래와 같다. 
(각자의 포트넘버는 8082 ~ 8085 이다)

```java
cd login
mvn spring-boot:run

cd menu
mvn spring-boot:run 

cd order
mvn spring-boot:run  

cd order_management
mvn spring-boot:run 

...
```

![image](https://github.com/user-attachments/assets/6b47a48f-9372-48a2-bada-3f5b24813ebf)


### DDD의 적용

```java
@Entity
@Table(name = "Menu_table")
@Data
//<<< DDD / Aggregate Root
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long menuId;

    private String menuName;

    private String description;

    private Integer price;

    private Boolean isAvailable;

    private String category;

    private Integer discount;

    private Integer todayOrderCount;

    private Integer accOrderCount;

    private Boolean isHot;

    private String imageUrl;

    @PostPersist
    public void onPostPersist() {
        MenuCreated menuCreated = new MenuCreated(this);
        menuCreated.publishAfterCommit();
    }

    @PostUpdate
    public void onPostUpdate() {
        MenuUpdated menuUpdated = new MenuUpdated(this);
        menuUpdated.publishAfterCommit();
    }

    @PrePersist
    public void onPrePersist() {}

    @PreUpdate
    public void onPreUpdate() {}

    @PreRemove
    public void onPreRemove() {}

    public static MenuRepository repository() {
        MenuRepository menuRepository = MenuApplication.applicationContext.getBean(
            MenuRepository.class
        );
        return menuRepository;
    }

    //<<< Clean Arch / Port Method
    public void menuDelete() {
        //implement business logic here:

        MenuDeleted menuDeleted = new MenuDeleted(this);
        menuDeleted.publishAfterCommit();
    }

    //>>> Clean Arch / Port Method
    public void menuUpdate() {
        //implement business logic here:

        MenuUpdated menuUpdated = new MenuUpdated(this);
        menuUpdated.publishAfterCommit();
    }

    public void menuCreate() {
        //implement business logic here:

        MenuCreated menuCreated = new MenuCreated(this);
        menuCreated.publishAfterCommit();
    }
}
//>>> DDD / Aggregate Root
```

이 `Menu` 클래스는 도메인 주도 설계(DDD)를 기반으로 한 핵심 Aggregate Root로, 메뉴와 관련된 데이터를 관리하고 관련 이벤트를 처리하는 역할을 합니다. 이 클래스는 메뉴의 생성, 업데이트, 삭제와 같은 주요 비즈니스 로직을 포함하며, 이러한 작업이 완료될 때마다 관련된 도메인 이벤트를 발행하여 시스템의 다른 부분에서 이를 반영할 수 있도록 합니다.

예를 들어, `onPostPersist` 메서드는 메뉴가 생성된 후에 자동으로 호출되어 `MenuCreated` 이벤트를 발행합니다. 이와 유사하게, `onPostUpdate` 메서드는 메뉴가 업데이트된 후에 `MenuUpdated` 이벤트를 발행하여 변경 사항을 반영합니다. 또한, `menuDelete` 메서드는 메뉴 삭제 비즈니스 로직을 수행하고, 삭제가 완료된 후에 `MenuDeleted` 이벤트를 발행하여 다른 서비스에 알립니다.

`Menu` 클래스는 `MenuRepository`를 통해 데이터베이스와 상호작용하며, 이를 통해 메뉴 데이터의 저장, 조회, 삭제 등의 작업이 수행됩니다. 이 클래스는 DDD의 Aggregate Root로서 메뉴 엔티티의 일관성과 상태를 관리하며, 이벤트 기반 아키텍처에서 중요한 역할을 수행합니다

### 분산트랜잭션 - Saga

```java
---

spring:
  profiles: docker
  jpa:
    properties:
      hibernate:
        show_sql: true
        format_sql: true
        implicit_naming_strategy: org.hibernate.boot.model.naming.ImplicitNamingStrategyComponentPathImpl
  cloud:
    stream:
      kafka:
        binder:
          brokers: my-kafka:9092
        streams:
          binder:
            configuration:
              default:
                key:
                  serde: org.apache.kafka.common.serialization.Serdes$StringSerde
                value:
                  serde: org.apache.kafka.common.serialization.Serdes$StringSerde
      bindings:
        event-in:
          group: menu
          destination: team
          contentType: application/json
        event-out:
          destination: team
          contentType: application/json
```

이 Spring 설정 파일은 Docker 환경에서 애플리케이션이 Kafka를 사용하여 메시지를 주고받도록 구성된 것을 보여줍니다. `spring.profiles`가 `docker`로 설정된 이 설정에서는 JPA와 Kafka Stream을 설정하여 분산 환경에서 데이터 처리를 지원합니다.

예를 들어, Kafka를 통한 메시징을 설정하기 위해 `brokers` 항목에서 Kafka 브로커의 주소를 `my-kafka:9092`로 지정했습니다. 또한, Kafka Stream을 통해 기본 키와 값의 직렬화/역직렬화 방식을 String으로 설정하였습니다.

이 설정은 `event-in` 바인딩을 통해 `menu` 그룹으로 들어오는 메시지를 `team` 토픽에서 수신하도록 하며, `event-out` 바인딩을 통해 `team` 토픽으로 메시지를 발행합니다. 메시지의 `contentType`은 JSON으로 설정되어, 애플리케이션이 JSON 형식의 데이터를 주고받도록 구성되었습니다.

이 설정을 통해 Docker 환경에서 애플리케이션이 Kafka를 사용하여 안정적으로 메시지를 처리하고, 분산된 서비스 간의 데이터 일관성을 유지할 수 있도록 하였습니다.

### Gateway

Local Gateway

```java
server:
  port: 8088

---

spring:
  profiles: default
  cloud:
    gateway:
#<<< API Gateway / Routes
      routes:
        - id: order
          uri: http://localhost:8082
          predicates:
            - Path=/orders/**, 
        - id: menu
          uri: http://localhost:8083
          predicates:
            - Path=/menus/**, 
        - id: login
          uri: http://localhost:8084
          predicates:
            - Path=/users/**, 
        - id: order management
          uri: http://localhost:8085
          predicates:
            - Path=/ordermenus/**, 
        - id: frontend
          uri: http://localhost:8080
          predicates:
            - Path=/**
#>>> API Gateway / Routes
      globalcors:
        corsConfigurations:
          '[/**]':
            allowedOrigins:
              - "*"
            allowedMethods:
              - "*"
            allowedHeaders:
              - "*"
            allowCredentials: true

---
```

Docker Gateway

```java
spring:
  profiles: docker
  cloud:
    gateway:
      routes:
        - id: order
          uri: http://order:8080
          predicates:
            - Path=/orders/**, 
        - id: menu
          uri: http://menu:8080
          predicates:
            - Path=/menus/**, 
        - id: login
          uri: http://login:8080
          predicates:
            - Path=/users/**, 
        - id: order management
          uri: http://orderManagement:8080
          predicates:
            - Path=/ordermenus/**, 
        - id: frontend
          uri: http://frontend:8080
          predicates:
            - Path=/**
      globalcors:
        corsConfigurations:
          '[/**]':
            allowedOrigins:
              - "*"
            allowedMethods:
              - "*"
            allowedHeaders:
              - "*"
            allowCredentials: true

server:
  port: 8080

```

Spring Cloud Gateway 서버의 설정 파일을 통해, API Gateway의 라우트와 CORS 설정을 정의했습니다. 기본 프로필(`default`)에서는 각 서비스가 로컬 환경에서 동작하도록 설정되었으며, 요청 경로에 따라 해당 서비스로 요청을 프록시합니다. 

예를 들어, `/orders/**` 경로로 들어오는 요청은 `http://localhost:8082`의 주문 서비스로 전달됩니다. 이와 유사하게 `/menus/**`, `/users/**`, `/ordermenus/**`, 그리고 모든 경로(`/`)에 대한 요청이 각각의 서비스로 프록시됩니다. 전역적으로 모든 경로에 대해 CORS 설정을 적용하여, 모든 origin, method, header를 허용하고, 자격증명(allowCredentials)을 허용했습니다.

Docker 프로필(`docker`)에서는 동일한 라우트 설정을 사용하되, URI에 Docker 컨테이너 이름을 사용하여 컨테이너 간 통신이 가능하도록 구성했습니다. 예를 들어, `/orders/**` 경로로 들어오는 요청은 `http://order:8080`의 Docker 컨테이너로 프록시됩니다. 이 설정을 통해 로컬과 Docker 환경 모두에서 API Gateway가 올바르게 동작하도록 보장했습니다.

### 기능 구현 예시(CRUD)

```java
@RestController
// @RequestMapping(value="/menus")
@Transactional
public class MenuController {

    @Autowired
    MenuRepository menuRepository;

    @RequestMapping(
        value = "/menus/{id}//menudelete",
        method = RequestMethod.DELETE,
        produces = "application/json;charset=UTF-8"
    )
    public Menu menuDelete(
        @PathVariable(value = "id") Long id,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        System.out.println("##### /menu/menuDelete  called #####");
        Optional<Menu> optionalMenu = menuRepository.findById(id);

        optionalMenu.orElseThrow(() -> new Exception("No Entity Found"));
        Menu menu = optionalMenu.get();
        menu.menuDelete();

        menuRepository.delete(menu);
        return menu;
    }
}
//>>> Clean Arch / Inbound Adaptor

```

MenuController는 메뉴와 관련된 HTTP 요청을 처리하는 컨트롤러로, 
@RestController 어노테이션을 통해 RESTful 웹 서비스 엔드포인트로 동작하도록 설계했습니다. 
이 컨트롤러는 메뉴 엔티티의 데이터 액세스를 처리하는 MenuRepository를 의존성으로 가지며, HttpServletRequest와 HttpServletResponse 객체를 통해 요청과 응답을 처리합니다. 

예를 들어, `menuDelete` 메소드는 DELETE /menus/{id}/menudelete 엔드포인트를 처리하며, 경로 변수로 전달된 메뉴 ID를 사용해 메뉴를 조회하고, 존재하지 않을 경우 예외를 발생시킵니다. 
삭제된 메뉴는 JSON 형식으로 반환되며, 응답의 content type은 `application/json;charset=UTF-8`로 설정됩니다. 만약 메뉴가 존재하지 않는 경우, 예외 처리 로직을 통해 클라이언트에게 적절한 상태 코드와 함께 예외 정보를 전달합니다

### 4.2 클라우드 배포

Azure Kubernetes 서비스(AKS)에서 `menu` 애플리케이션을 Docker 이미지를 사용해 Kubernetes 클러스터에 배포했습니다.

### 배포 설정

먼저, `menu` 서비스의 배포 설정은 다음과 같이 구성되었습니다

```java
apiVersion: apps/v1
kind: Deployment
metadata:
  name: menu
  labels:
    app: menu
spec:
  replicas: 1
  selector:
    matchLabels:
      app: menu
  template:
    metadata:
      labels:
        app: menu
    spec:
      containers:
        - name: menu
          image: "username/menu:latest"
          ports:
            - containerPort: 8080
          readinessProbe:
            httpGet:
              path: '/actuator/health'
              port: 8080
            initialDelaySeconds: 10
            timeoutSeconds: 2
            periodSeconds: 5
            failureThreshold: 10
          livenessProbe:
            httpGet:
              path: '/actuator/health'
              port: 8080
            initialDelaySeconds: 120
            timeoutSeconds: 2
            periodSeconds: 5
            failureThreshold: 5
```

이 설정에서 `menu` 서비스는 하나의 레플리카로 배포되며, Docker 이미지를 `username/menu:latest`로 지정했습니다. Readiness Probe와 Liveness Probe를 설정하여, 서비스가 정상적으로 작동하고 있는지 모니터링합니다.

### 배포 후 결과 확인

배포가 완료된 후, `kubectl` 명령어를 사용해 배포된 파드와 서비스의 상태를 확인했습니다.

```java
gitpod /workspace/hiorder_reallast (main) $ kubectl get pods
NAME                               READY   STATUS    RESTARTS   AGE
gateway-677ddf44d5-d9bqh           1/1     Running   0          72m
login-869fdf7c98-6rkbl             1/1     Running   0          74m
menu-84fdf5db6b-c5fzw              1/1     Running   0          71m
my-kafka-0                         1/1     Running   0          90m
order-655f6c665f-7r7rr             1/1     Running   0          71m
ordermanagement-7d77bcc987-b4qd7   1/1     Running   0          74m
```

모든 파드가 `Running` 상태로 정상적으로 실행되고 있음을 확인했습니다. `menu` 파드 역시 정상적으로 실행되고 있으며, 71분간 재시작 없이 운영되고 있습니다.

이어, 클러스터 내의 서비스 상태를 확인했습니다

```java
gitpod /workspace/hiorder_reallast (main) $ kubectl get svc
NAME                TYPE           CLUSTER-IP     EXTERNAL-IP    PORT(S)                      AGE
gateway             LoadBalancer   10.0.29.100    20.249.71.78   8080:30285/TCP               74m
kubernetes          ClusterIP      10.0.0.1       <none>         443/TCP                      8h
login               ClusterIP      10.0.70.95     <none>         8080/TCP                     74m
menu                ClusterIP      10.0.149.85    <none>         8080/TCP                     72m
my-kafka            ClusterIP      10.0.188.132   <none>         9092/TCP                     91m
my-kafka-headless   ClusterIP      None           <none>         9092/TCP,9094/TCP,9093/TCP   91m
order               ClusterIP      10.0.146.58    <none>         8080/TCP                     72m
ordermanagement     ClusterIP      10.0.62.32     <none>         8080/TCP                     75m
```

`menu` 서비스는 `ClusterIP` 타입으로 배포되었으며, 내부 IP `10.0.149.85`를 할당받아 8080 포트에서 정상적으로 서비스 중입니다. 모든 서비스가 정상적으로 배포되었으며, 외부 트래픽은 `gateway` 서비스를 통해 `20.249.71.78`의 외부 IP에서 처리됩니다.

이로써 `menu` 서비스의 무정지 배포가 성공적으로 완료되었으며, Kubernetes 클러스터에서 안정적으로 운영되고 있음을 확인했습니다.

*`20.249.71.78` 외부 IP를 통한 create 테스트

![image](https://github.com/user-attachments/assets/287c5802-2270-433b-84b5-1fbea5a17c24)


### Docker Build 이미지 생성

![image](https://github.com/user-attachments/assets/d319df1d-1260-4d52-8099-5b8aea086417)


![image](https://github.com/user-attachments/assets/1786da45-1acb-45a8-a099-c10a2961c3e7)


### DockerHub 리포지토리

- 리포지토리에 있는 각 서비스(`gateway`, `login`, `menu`, `order`, `order_management`)는 Docker 이미지를 생성하여 배포된 상태입니다.

### `menu` 이미지 세부 사항

- `menu` 이미지는 Linux/amd64 아키텍처를 기반으로 하며, 약 254.62MB 크기로 생성
- 이 이미지는 OpenJDK 15를 포함하고 있으며, Spring Boot 애플리케이션을 실행하기 위한 환경 설정이 되어 있습니다.
- 이미지 생성 과정에서 다양한 단계가 수행되었으며, 중요한 레이어는 다음과 같습니다:
    - **APK 패키지 설치**: `apk` 패키지 관리자를 사용하여 필요한 패키지를 설치.
    - **Java 환경 설정**: OpenJDK 15를 환경 변수로 설정.
    - **애플리케이션 복사**: 빌드된 `app.jar` 파일을 컨테이너로 복사하여 실행 준비.
    - **포트 노출**: 애플리케이션이 8080 포트를 통해 외부와 통신할 수 있도록 설정.
    - **시작 명령**: `ENTRYPOINT`를 통해 Java 애플리케이션이 메모리 설정과 함께 실행되도록 설정.

이 이미지를 기반으로 `menu` 서비스는 Kubernetes 클러스터에 배포되었으며, 설정된 대로 정상적으로 작동된 것 확인

