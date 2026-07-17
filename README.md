# SSING-ANDROID

**스키/스노보드 강습 매칭 서비스 SSING**

강사와 수강생을 빠르고 합리적인 구조로 연결하는 원클릭 매칭 플랫폼으로,
즉시 강습부터 사전 예약까지 다양한 방식으로 나에게 맞는 강습을 시작할 수 있습니다.
스키장 현장에서 준비된 강사와 실시간으로 연결되어, 기다림 없이 바로 강습을 시작해보세요!

<img width="3840" height="2160" alt="KakaoTalk_Photo_2026-07-17-23-31-18" src="https://github.com/user-attachments/assets/f00173d3-a111-4382-816f-71111a87dbc9" />

<br/>

## 💙 Contributors

|                                🏂한유빈 (LEAD)<br/>[@oilbeaneda](https://github.com/oilbeaneda)                                |                                   🏂전도연<br/>[@doyeon0307](https://github.com/doyeon0307)                                    |                                       🏂조예슬<br/>[@joyrii](https://github.com/joyrii)                                        |                                   🏂김예지<br/>[@apffkxhsls](https://github.com/apffkxhsls)                                    |                                     🏂김예림<br/>[@doorimng](https://github.com/doorimng)                                      |
|:---------------------------------------------------------------------------------------------------------------------------:|:---------------------------------------------------------------------------------------------------------------------------:|:---------------------------------------------------------------------------------------------------------------------------:|:---------------------------------------------------------------------------------------------------------------------------:|:---------------------------------------------------------------------------------------------------------------------------:|
| <img alt="한유빈 avatar" src="https://github.com/user-attachments/assets/777d6a78-a17a-4fac-83f7-6c5f4e71cf9c" height="200" /> | <img alt="전도연 avatar" src="https://github.com/user-attachments/assets/e5dae0d2-98b1-4c89-bc61-c14c2ff9d617" height="200" /> | <img alt="조예슬 avatar" src="https://github.com/user-attachments/assets/022fa9b5-a6e9-4059-a9df-0f378cc576fe" height="200" /> | <img alt="김예지 avatar" src="https://github.com/user-attachments/assets/0d2a9981-8303-4c82-a36b-b15d07537987" height="200" /> | <img alt="김예림 avatar" src="https://github.com/user-attachments/assets/c6b03389-963d-4265-a5a0-1fccef83e5e5" height="200" /> |
|                                               `강사 매칭 플로우`,<br/> `알림`, `기초 세팅`                                               |                                              `소비자 매칭 플로우`,<br/> `WebSocket 세팅`                                              |                                                  `소비자 강습상세`,<br/> `DS 세팅`                                                   |                                                  `강사 강습상세`,<br/> `FCM 세팅`                                                   |                                           `카카오 로그인`,<br/> `홈`, `강습 확정`,<br/> `결제`                                           |


<br/>

## 🎥 ScreenShot

| 성공 플로우 | 매칭 중 앱 이탈 > 재시작 시 매칭 화면으로 자동 복구 | 바텀시트로 강습 취소 |
|:---:|:---:|:---:|
| <video src="https://github.com/user-attachments/assets/b8638abc-cb7c-4f18-ab8e-c982224f8923" /> | <video src="https://github.com/user-attachments/assets/caecd8a9-e7a0-4685-8ab2-a008f6c4847d" /> | <video src="https://github.com/user-attachments/assets/33d89648-e45a-4757-a7b1-873b7942060f" /> |


<br/>

## ⚒️ Tech Stacks

| 항목             | 기술 스택                                       |
|:---------------|:--------------------------------------------|
| Architecture   | Google Recommended Architecture (Domain 제거) |
| Pattern        | MVVM + Contract (MVI Intent 제거)             |
| Modularization | Android Multi-Module + Convention Plugin    |
| DI             | Hilt                                        |
| Asynchronous   | Coroutine, Flow                             |
| Network        | Retrofit2, OkHttp                           |
| Real-time      | WebSocket                                   |
| Notification   | FCM                                         |
| Navigation     | Jetpack Navigation (Type-safe)              |
| UI Framework   | Jetpack Compose                             |
| Image          | Coil                                        |
| Logging        | Timber                                      |
| Local Storage  | DataStore                                   |

<br/>

## 🗂️ Project Structure

```text
🗃️ SSING-ANDROID
│
├── 📂 app
│   ├── 📁 consumer             # 소비자 앱 
│   └── 📁 instructor           # 강사 앱 
│
├── 📂 presentation
│   ├── 📁 auth
│   ├── 📁 devauth                 
│   ├── 📁 notification        
│   ├── 📁 consumer-home
│   ├── 📁 consumer-lesson
│   ├── 📁 consumer-profile        
│   ├── 📁 consumer-matching    
│   ├── 📁 consumer-payment          
│   ├── 📁 instructor-home
│   ├── 📁 instructor-lesson
│   ├── 📁 instructor-profile      
│   └── 📁 instructor-matching
│       └── 📁 navigation
│
├── 📂 data                     # 단일 모듈, feature별 패키지
│   ├── 📁 auth
│   ├── 📁 devauth
│   ├── 📁 home
│   ├── 📁 lesson
│   ├── 📁 notification
│   ├── 📁 payment
│   └── 📁 matching           
│       ├── 📁 model
│       ├── 📁 remote
│       │   ├── 📁 datasource
│       │   └── 📁 dto
│       └── 📁 repository
│
└── 📂 core
    ├── 📂 ui                  
    │   ├── 📁 base             # BaseViewModel
    │   ├── 📁 common           # 공통 컴포넌트
    │   ├── 📁 designsystem     # 색상, 타이포, 테마
    │   ├── 📁 extension        # Kotlin 확장 함수
    │   ├── 📁 navigation       # Route 인터페이스
    │   ├── 📁 state
    │   ├── 📁 type
    │   └── 📁 util             # 공통 유틸
    ├── 📂 network              # Retrofit + OkHttp + WebSocket
    │   ├── 📁 authenticator
    │   ├── 📁 constant
    │   ├── 📁 di
    │   ├── 📁 dto
    │   ├── 📁 exception
    │   ├── 📁 extension
    │   ├── 📁 interceptor
    │   ├── 📁 model
    │   ├── 📁 service
    │   ├── 📁 session
    │   ├── 📁 socket
    │   ├── 📁 token
    │   └── 📁 util
    ├── 📂 notification
    │   └── 📁 data
    │       ├── 📁 di
    │       ├── 📁 remote
    │       └── 📁 repository
    │
    └── 📂 localstorage         # DataStore (토큰 저장)
        └── 📁 datastore
            └── 📁 di
```

<br/>

## 📐 Module Dependency Graph

<img width="800" alt="SSING Module Dependency" src="https://github.com/user-attachments/assets/4f669e02-9afa-4bf3-80e6-2f777c7c4a37" />

<br/>

## **📗 Convention**

📌 [컨벤션 문서 보러가기](https://www.notion.so/20221444hanyubin/SSING-38d913d4ef84808d9c79f831555bf65b?source=copy_link)

- **Github Convention**
- **Naming Convention**
- **Packaging Convention**
