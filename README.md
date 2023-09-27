# SNS (portFolio)
📌 포토폴리용 용도(+공부 목적) 로 제작한 SNS 컨셉 웹페이지 입니다. <br/>
📌 인스타그램을 모티브 삼아 제작했습니다.<br/>
📌 23.09.28 기준 완성본이 아니며, 현재 진행중 입니다. <br/>
📌 웹 개발의 모집단을 공부하고 있기 때문에 html,css도 직접 작성 했습니다.<br/>  
📍 java17, spring framework3.1.0, thymeleaf, spring security6, jpa, oracleDB<br/>

### Main Page
<img src="https://github.com/lee-410/PRAC22/assets/58701102/95f39fd6-bd9a-4e22-a636-de41e3e89a22" width="500" object-fit="cover" >

+회원 유저가 피드를 올리면 홈에 업로드됨<br/>
+좋아요 및 댓글은 모양만 만들어두고 구현은 아직 하지않음 (구현 방법 생각 중...)

## Navigation Bar (홈, 검색, 프로필, 업로드)
<img src="https://github.com/lee-410/PRAC22/assets/58701102/db696b7a-8bd3-4b91-a27c-9101c26f310d" width="300" object-fit="cover" >

+밑줄 애니메이션 추가<br/>
+비회원 유저일 경우, 프로필 및 업로드페이지 접근 불가(alert("로그인이 필요한 서비스입니다");)

## Navigation Bar, 검색 
<img src="https://github.com/lee-410/PRAC22/assets/58701102/a941ce6f-f3cf-4a13-a05a-5be467aafe03" width="500" object-fit="cover" >

+회원 유저가 다른 유저의 ID를 검색하면 해당 유저의 프로필 페이지로 이동 (할 예정)

## Navigation Bar, 프로필
<img src="https://github.com/lee-410/PRAC22/assets/58701102/9cabe317-cd8d-4d2a-ae55-949c5de5618b" width="500" object-fit="cover" >

+회원 유저 본인이 올린 피드 확인 가능<br/>
+삭제 원하는 피드 삭제 가능(DB,로컬폴더에서 정보 삭제)

## Navigation Bar, 프로필 편집
<img src="https://github.com/lee-410/PRAC22/assets/58701102/76994a16-21cf-454f-9196-90a6dd5a4cdb" width="500" object-fit="cover" >

+회원 유저의 프로필이미지와 소개를 저장하면 프로필에 업로드됨<br/>
+회원 유저의 프로필이미지와 소개는 DB에 저장됨

## Navigation Bar, 업로드
<img src="https://github.com/lee-410/PRAC22/assets/58701102/4d00796d-598f-41ee-a085-2ff9bac498c8" width="500" object-fit="cover" >

+홈에 업로드할 이미지와 글 작성 가능<br/>
+이미지 여러개 업로드 가능<br/>
+업로드한 이미지와 글은 DB에 저장됨(이미지-folderPath, UUID, fileName | 글-content)<br/>
+업로드한 이미지는 날짜별로 로컬폴더에 저장됨<br/>

## Footer (GitHub, velog, 로그인)
<img src="https://github.com/lee-410/PRAC22/assets/58701102/b7e69d2e-a8c5-42c1-a6b7-c35274c7b2cb" width="500" object-fit="cover" >

+[GitHub](https://github.com/lee-410/PRAC22/tree/main)<br/>
+[velog](https://velog.io/@lee-410) (프로젝트하면서 기록 용도로 작성...)</br>

## 로그인
<img src="https://github.com/lee-410/PRAC22/assets/58701102/17e28bf9-d69b-4289-ac59-7cb8b1314823" width="300" object-fit="cover" >

## 회원가입
<img src="https://github.com/lee-410/PRAC22/assets/58701102/930036fc-c911-402d-bd7a-c45b0ddf68c3" width="300" object-fit="cover" >



