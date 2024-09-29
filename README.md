# 패션 API 구현

## 구현 전략

### 데이터 설계
```
단, 구매 가격 외의 추가적인 비용(예, 배송비 등)은 고려하지 않고, 브랜드의 카테고리에는 1개의 상품은 존재하고,
구매를 고려하는 모든 쇼핑몰에 상품 품절은 없다고 가정합니다.
```
위 요구사항에 따라 데이터를 정규화하기보다 비정규화한 상태로 관리하는 것이 현재 요구사항에 적합하다고 생각합니다.

### 인덱스
- 현재 사용하는 쿼리에 맞춰 `brand` 인덱스와 `category`와 `price`를 묶어 두가지 인덱스를 사용했습니다.
- 데이터가 얼마나 있고 분포가 어떻게 될 것이냐에 따라 적절하게 인덱스를 만들어야 좋겠지만 데이터가 어떻게 될지는 알 수 없기 때문에 단순히 쿼리에서 사용하는
  컬럼을 기준으로 인덱스를 설정하였습니다.

### 단위테스트 위주의 테스트
- 로직을 구성할 때마다 단위 테스트를 작성하는 것을 좋아하는 편이라 단위 테스트로 구성하였습니다.
- 현재 테스트 커버리지는 라인별로 했을 때 91퍼입니다.

### API 문서 관리
- 테스트 코드를 통해 swagger-ui를 제공하도록 구성하였습니다.
  - 문서의 최신화를 위해 코드로 관리하는 것이 좋다고 생각합니다.
  - 클라이언트에 직접 테스트 해볼 수 있는 화면을 쉽게 제공해야한다고 생각합니다.

### 패키지 구성
- DDD를 좋아하는 편이라 제가 배운 DDD에 맞춰 패키지를 구성하였습니다. (개인적으로 헥사고날을 좋아하는 편은 아닙니다.)
- interfaces : API 인터페이스에 대한 기능을 모아두었습니다.
- infrastructure : 외부로 나가는 아웃바운드에 대한 기능을 모아두었습니다.
- application : 비즈니스 로직(유스케이스)에 대한 절차적인 기능들을 모아두었습니다.
- domain : 도메인 모델, 도메인 로직에 해당하는 기능들을 모아두었습니다.

### 데이터 입력
- 편의상 SampleInsertRunner로 Spring 애플리케이션이 실행될 때나 SpringBootTest가 실행될 때 데이터를 넣도록 했습니다.

## 개선사항
### 캐시 및 조회용 테이블 설계
- 1,2,3번 API의 경우 데이터가 많아지고 조회가 많아진다면 캐시나 조회용 테이블 설계를 하는게 좋을 것으로 보입니다.
- 장기적으로는 조회용 테이블을 설계하여 상품이 등록될 때마다 통계 값을 저장하는 것이 더 효율적이라고 생각이 듭니다.
- 다만, 상품 등록이 너무 수시로 등록된다면 통계용 테이블에 데이터를 삽입할 때 Redis를 통한 분산락이나 RDB의 낙관적락/비관적락을 사용하는 것이 좋을 것이라고 생각듭니다.

## 기능 요구사항
### 카테고리별 최저가격 코디 스타일 조회 API
#### Request Url
```
GET /api/v1/outfit/lowest-price
```

#### Request Body
- 없음
#### Response Body
```json
{
    "total": 34100,
    "items": [
        {
            "category": "상의",
            "brand": "C",
            "price": 10000
        },
        // 생략
    ]
}
```

### 단일 브랜드 최소 가격 코디 스타일 조회 API
#### Request Url
```
GET /api/v1/outfit/single-brand/lowest-price
```
#### Request Body
- 없음
#### Response Body
```json
{
    "lowest-price": {
        "brand": "D",
        "categories": [
            {
                "category": "상의",
                "price": 10100
            },
            // 생략
        ],
        "total": 36100
    }
}
```

### category로 최저/최고 가격 상품 조회 API
#### Request Url
```
GET /api/v1/products/categories/{category-id}/price-range
```
#### Request Body
- 없음
#### Response Body
```json
{
    "category": "상의",
    "min": [
        {
            "brand": "C",
            "price": 10000
        }
    ],
    "max": [
        {
            "brand": "I",
            "price": 11400
        }
    ]
}
```

### 상품 등록 API
#### Request Url
```
POST /api/v1/products
```
#### Request Body
```
{
    "brand": "A",
    "category": "상의",
    "price": "가격",
}
```
#### Response Body
```
{
    "id": 1,
    "brand": "A",
    "category": "상의",
    "price": "가격",
}
```

### 상품 변경 API
#### Request Url
```
PUT /api/v1/products/{product-id}
```
#### Request Body
```
{
    "brand": "A",
    "category": "상의",
    "price": "가격",
}
```
#### Response Body
```
{
    "id": 1,
    "brand": "A",
    "category": "상의",
    "price": "가격",
}
```

### 상품 삭제 API
#### Request Url
```
DELETE /api/v1/products/{product-id}
```
