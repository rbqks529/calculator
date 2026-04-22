# 환율 계산기

USD를 KRW, JPY, PHP로 환산해주는 안드로이드 앱입니다.

## 실행 방법

`local.properties`에 [currencylayer](https://currencylayer.com) API 키를 추가합니다.

```
CURRENCY_API_KEY=발급받은_키
```

## 구조

```
data/       → API 통신, Repository
domain/     → Country 모델, InputValidator
viewmodel/  → CalculatorViewModel, UiState
ui/screen/  → Compose 화면 구성
```

MVVM 패턴으로 작성했습니다. ViewModel은 StateFlow로 상태를 관리하고, Repository 인터페이스를 통해 데이터 레이어와 분리했습니다.

## 테스트

```bash
./gradlew testDebugUnitTest
```

InputValidator 경계값, Repository API 응답 파싱, ViewModel 상태 전환을 커버합니다.

## 사용 기술

- Kotlin, Jetpack Compose, Material3
- Retrofit2 + OkHttp
- Coroutines + StateFlow

## AI 도구 활용

개발 과정에서 Claude Code(Anthropic)를 일부 활용했습니다.

- **활용 범위**: Gradle 의존성 설정, RetrofitClient 초기화 패턴 참고
- **직접 작성**: 비즈니스 로직(InputValidator, ViewModel 상태 설계), UI 컴포넌트 구성, 테스트 케이스
