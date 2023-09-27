# SNS (portFolio)
📌 포토폴리용 용도(+공부 목적) 로 제작한 SNS 컨셉 웹페이지 입니다.  

📌 인스타그램을 모티브 삼아 제작했습니다.  

📌 23.09.28 기준 완성본이 아니며, 현재 진행중 입니다.  

📌 웹 개발의 모집단을 공부하고 있기 때문에 html,css도 구글링 도움받아 직접 작성 했습니다.  


📍 java17, spring framework3.1.0, thymeleaf, spring security6, jpa, oracleDB

### Main Page
![image](https://github.com/lee-410/PRAC22/assets/58701102/95f39fd6-bd9a-4e22-a636-de41e3e89a22)
+회원 유저가 피드를 올리면 홈에 업로드됨
+좋아요 및 댓글은 모양만 만들어두고 구현은 아직 하지않음 (구현 방법 생각 중...)

## Navigation Bar (홈, 검색, 프로필, 업로드)
![naviShow](https://github.com/lee-410/PRAC22/assets/58701102/db696b7a-8bd3-4b91-a27c-9101c26f310d)
+밑줄 애니메이션 추가

## Navigation Bar, 검색 
![image](https://github.com/lee-410/PRAC22/assets/58701102/c07dbe55-e615-44db-84cc-9824933220b9)
+회원 유저가 다른 유저의 ID를 검색하면 해당 유저의 프로필 페이지로 이동 (예정)

## Navigation Bar, 프로필

+회원 유저의 프로필 페이지 (비회원 접근 불가)
+회원 유저 본인이 올린 피드 확인 가능
+삭제 원하는 피드 삭제 가능(DB,로컬폴더에서 정보 삭제)

## Navigation Bar, 프로필 편집

+회원 유저의 프로필이미지와 소개를 저장하면 프로필에 업로드됨
+회원 유저의 프로필이미지와 소개는 DB에 저장됨

## Navigation Bar, 업로드

+홈에 업로드할 이미지와 글 작성 가능
+이미지 여러개 업로드 가능
+업로드한 이미지와 글은 DB에 저장됨(이미지-folderPath, UUID, fileName | 글-content)
+업로드한 이미지는 날짜별로 로컬폴더에 저장됨

## Footer (GitHub, velog, 로그인)
![image](https://github.com/lee-410/PRAC22/assets/58701102/b7e69d2e-a8c5-42c1-a6b7-c35274c7b2cb)

## 로그인


## 회원가입



