# SSING-ANDROID

**스키/스노보드 강습 매칭 서비스 SSING**

강사와 수강생을 빠르고 합리적인 구조로 연결하는 원클릭 매칭 플랫폼으로,
즉시 강습부터 사전 예약까지 다양한 방식으로 나에게 맞는 강습을 시작할 수 있습니다.
스키장 현장에서 준비된 강사와 실시간으로 연결되어, 기다림 없이 바로 강습을 시작해보세요! 

<br/>

## 💙 Contributors

|            🏂한유빈 (LEAD)<br/>[@oilbeaneda](https://github.com/oilbeaneda)            |            🏂전도연<br/>[@doyeon0307](https://github.com/doyeon0307)            |            🏂조예슬<br/>[@joyrii](https://github.com/joyrii)            |            🏂김예지<br/>[@apffkxhsls](https://github.com/apffkxhsls)            |            🏂김예림<br/>[@doorimng](https://github.com/doorimng)            |
|:---:|:---:|:---:|:---:|:---:|
| <img alt="한유빈 avatar" src="https://avatars.githubusercontent.com/oilbeaneda" height="200" /> | <img alt="전도연 avatar" src="https://avatars.githubusercontent.com/doyeon0307" height="200" /> | <img alt="조예슬 avatar" src="https://avatars.githubusercontent.com/joyrii" height="200" /> | <img alt="김예지 avatar" src="https://avatars.githubusercontent.com/apffkxhsls" height="200" /> | <img alt="김예림 avatar" src="https://avatars.githubusercontent.com/doorimng" height="200" /> |
|                        `강사 매칭 플로우`,<br/> `알림`, `기초 세팅`                               |                                `소비자매칭 플로우`,<br/> `WebSocket 세팅`                                          |                            `소비자강습상세`,<br/> `DS 세팅`                                      |                                                                                    `강사 강습상세`,<br/> `FCM 세팅`                                                                                    |                                       `카카오 로그인`,<br/> `홈`, `강습 확정`,<br/> `결제`                                       |

<br/>

## ⚒️ Tech Stacks

| 항목 | 기술 스택 |
| :--- | :--- |
| Architecture | Google Recommended Architecture (Domain 제거) |
| Pattern | MVVM + Contract (MVI Intent 제거) |
| Modularization | Android Multi-Module + Convention Plugin |
| DI | Hilt |
| Asynchronous | Coroutine, Flow |
| Network | Retrofit2, OkHttp |
| Real-time | WebSocket |
| Notification | FCM |
| Navigation | Jetpack Navigation (Type-safe) |
| UI Framework | Jetpack Compose |
| Image | Coil |
| Logging | Timber |
| Local Storage | DataStore |

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
│   ├── 📁 notification        
│   ├── 📁 consumer-home        
│   ├── 📁 consumer-matching    
│   ├── 📁 consumer-payment     
│   ├── 📁 consumer-lesson     
│   ├── 📁 instructor-home    
│   └── 📁 instructor-matching 
│
├── 📂 data                     # 단일 모듈, feature별 패키지
│   ├── 📁 di                   # 의존성 주입 모듈
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
    │   ├── 📁 designsystem     # 색상, 타이포, 테마, 공통 컴포넌트
    │   ├── 📁 extension        # Kotlin 확장 함수
    │   ├── 📁 navigation       # Route 인터페이스
    │   └── 📁 util             # 공통 유틸
    ├── 📂 network              # Retrofit + OkHttp + WebSocket
    │   ├── 📁 di
    │   ├── 📁 model
    │   └── 📁 util
    └── 📂 localstorage         # DataStore (토큰 저장)
        └── 📁 di
```

<br/>

## 📐 Module Dependency Graph

<img width="800" alt="SSING Module Dependency" src="https://github.com/user-attachments/assets/c2e3ae4e-3ae9-4e31-a315-3a7771f4645f" />

<br/>

## **📗 Convention**

📌 [컨벤션 문서 보러가기](https://www.notion.so/20221444hanyubin/SSING-38d913d4ef84808d9c79f831555bf65b?source=copy_link)

- **Github Convention**
- **Naming Convention**
- **Packaging Convention**
