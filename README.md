# 패션 API 구현

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
GET /api/v1/categories/{category-id}/price-range/products
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

## 구현 전략
### 데이터가 많아졌을 때
현재 요구사항과 데이터의 규모에 따라 1,2,3번 API의 속도를 보장하기 위해 캐시 또는 통계 테이블
설계를 해야한다고 생각한다. 데이터가 정말 많다면 통계 테이블을 통해 조회하도록 설계하는게 맞을거 같지만
현재는 그정도 데이터는 아니라고 가정하고 진행한다.

### 데이터 설계
```
단, 구매 가격 외의 추가적인 비용(예, 배송비 등)은 고려하지 않고, 브랜드의 카테고리에는 1개의 상품은 존재하고,
구매를 고려하는 모든 쇼핑몰에 상품 품절은 없다고 가정합니다.
```
위 요구사항에 따라 데이터를 정규화하기보다 비정규화한 상태로 관리하는 것이 현재 요구사항에 적합하다고 생각한다.

