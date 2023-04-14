<p align="center">
  <img src="https://user-images.githubusercontent.com/116870617/231791531-9e7ee801-a462-4b7a-977d-1e56b195e28b.png">
</p>

# KICKOFF (킥오프)
## Open API와 챗봇을 활용한 동호회 운영/관리 그룹웨어 
## [프로젝트 소개 PDF(영상포함)](https://drive.google.com/file/d/18gtjqQk1KOKh-EcekBsUl4dJVbOpZ3JZ/view?usp=share_link)

## 📅 프로젝트 기간 - 2023.03.14 ~ 2023.04.06

## 🖥️ 프로젝트 소개
- Open API(우편번호, 날씨, 일정관리, 조직관리 등)를 활용한 동호회 운영/관리용 그룹웨어입니다.
- Spring MVC 패턴으로 개발하였습니다.
- 일정, 결재, 회계, 공지사항, 게시판 등 조직관리에 필요한 전반적인 기능들을 구현하였습니다.
- Komoran(한국어 형태소 분석기)을 활용한 시나리오형 챗봇을 구현하였습니다.
- Github Actions와 AWS EC2를 기반으로 CI/CD(지속통합/지속배포) 환경을 구축하였습니다.

## ⚙️ 개발 환경
- `Language` : Java 11, HTML5, CSS3, JavaScript
- `IDE` : IntelliJ IDEA, Visual Studio Code
- `Framework` : Springboot
- `Database` : MySQL
- `Template` Engine : Thymeleaf 
- `ORM` : JPA <br>

## 🧑‍🤝‍🧑 팀 구성 및 역할
#### 👨‍💻 팀장 : 김필수 <br>
#### `java package` : config / member / admin / entity / dto <br>
#### `templates package` : login / join / index / member / admin <br>
- 프로젝트 일정 관리 및 발표준비
- 소스 통합 및 형상관리

<details>
<summary>상세보기</summary>
<br>
  <p align="center"><img src="https://user-images.githubusercontent.com/116870617/231946224-024f51b0-332d-4b84-873d-ae8d5f70f93b.png" style="width: 700px"></p> 
<br>
  <p align="center">프로젝트 형상관리를 위한 기본 저장소를 생성합니다.</p>
<br>
  <p align="center"><img src="https://user-images.githubusercontent.com/116870617/231946228-d8212fef-59a7-431a-94f3-99f76ccc340b.png" style="width: 700px"></p>
<br>
  <p align="center">팀원들을 collaborators 및 contributers로 지정하여 저장소에 대한 pull Request뿐만 아니라 직접적인 push, pull의 권한을 부여하였습니다.</p>
<br>
  <p align="center"><img src="https://user-images.githubusercontent.com/116870617/231946229-022ab668-468c-4acb-9b14-26cd3b3444e8.png" style="width: 700px"></p>
<br>
  <p align="center">프로젝트 저장소를 fork하여 팀원 각자가 복사한 저장소를 통해 담당 파트별 소스코드를 업데이트할 수 있도록 합니다.</p>
<br>
  <p align="center"><img src="https://user-images.githubusercontent.com/116870617/231946230-60aa3a72-5905-4790-880a-a3ec82558599.png" style="width: 700px"></p>
<br>
  <p align="center">특정 파트의 코드가 업데이트 되는대로 fork 저장소에서 프로젝트 저장소에 pull Request를 보냅니다.</p>
<br>
  <p align="center"><img src="https://user-images.githubusercontent.com/116870617/231946232-acf9471d-85b8-4f90-a9b8-83d54c3af94a.png" style="width: 700px"></p>
<br>
  <p align="center">pull request의 커밋 내역을 확인하여 confirm을 통해 해당 수정사항을 프로젝트 저장소의 소스와 merge한 뒤,</p>
  <p align="center">각자의 fork 저장소에서 최신화합니다.</p>
<br>
</details>

- 데이터 모델링 및 Entity, DTO 구현
- Spring Security, Oauth2 기반 로그인 및 회원가입 구현

<details>
<summary>상세보기</summary>
<br>
  <p align="center"><img src="https://user-images.githubusercontent.com/116870617/231950119-3478b0a0-5bc1-4da0-9d0a-81f874d26a91.png" style="width: 700px"></p> 
<br>
  <p align="center">페이지의 인증 및 인가를 처리하는 SecurityFilterChain객체를 통해 페이지별 접근권한과 기본적인 로그인 설정을 구현합니다.</p>
  
  ```java
// --------- Security config ----------
  
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    @Autowired
    private LoginService loginService;

//    private final AuthenticationFailureHandler failureHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        http.csrf().disable();

        // 권한 => GUEST(회원가입 후 관리자 승인 필요), MEMBER(일반회원), ADMIN(관리자), BLACK(정지회원)
        http.authorizeHttpRequests()
                .antMatchers("/login","/join","/naver").permitAll()  // 모든 유저 접근 가능
                .antMatchers("/css/**", "/js/**", "/img/**").permitAll()
  
  // 로그인
  
        http.formLogin()
                .loginPage("/login")
                .usernameParameter("mEmail") // 로그인시 해당하는 아이디 name->userEmail
                .passwordParameter("mPw")
                .loginProcessingUrl("/loginOk") // POST 로 보내는 액션
              .failureUrl("/login")
                .defaultSuccessUrl("/index", true)   // 성공시 URL
                .and()
                .oauth2Login()
                .loginPage("/login");

  // 로그아웃
  
        http.logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login");

        http.userDetailsService(loginService);

        return http.build();
    }
  
// --------- SecurityUser ----------
  
@Getter
@Setter
@ToString
public class SecurityUser extends User {

    // 로그인 정보 사용자
    private MemberEntity memberEntity;

    public SecurityUser(MemberEntity memberEntity) {
        super(memberEntity.getMEmail(), memberEntity.getMPw(),
                AuthorityUtils.createAuthorityList(memberEntity.getMRole().toString()));

        this.memberEntity = memberEntity;
    }
}
  
// --------- LoginService ----------
  
@Service
@RequiredArgsConstructor
public class LoginService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String mEmail) throws UsernameNotFoundException {
        // DB에 해당 회원정보가 있는지 확인
        Optional<MemberEntity> memberEntity = memberRepository.findBymEmail(mEmail);

        if(memberEntity.isEmpty()){
            throw new UsernameNotFoundException("사용자가 없습니다.");
        }


        MemberEntity memberEntity1 = memberEntity.get();

        System.out.println(memberEntity1.getMEmail() + "<<<<<<<< email");
        System.out.println(memberEntity1.getMPw() + "<<<<<<<< pw");
        System.out.println(memberEntity1.getMName() + "<<<<<<<< name");
        System.out.println(memberEntity1.getMTel() + "<<<<<< tel");

        return User.builder()
                .username(memberEntity1.getMEmail())
                .password(memberEntity1.getMPw())
                .roles(memberEntity1.getMRole().toString())
                .build();
    }
}
  
  ```
  
<br>
  <p align="center"><img src="https://user-images.githubusercontent.com/116870617/231950129-6ae46cd3-0cda-4216-976f-f781a26e3927.png" style="width: 700px"></p>
<br>
  <p align="center">.yml에 oauth2를 통한 소셜로그인(google, naver, kakao)을 위한 api설정을 합니다.</p>
<br>
  <p align="center"><img src="https://user-images.githubusercontent.com/116870617/231950130-35133640-cadb-4690-b3d2-414dfb213336.png" style="width: 700px"></p>
<br>
  <p align="center">회원가입시 우편번호 API를 활용하여 사용자의 주소를 입력받으며, multipartfile 객체를 통해 프로필 사진을 등록할 수 있습니다.</p>
<br>
  <p align="center"><img src="https://user-images.githubusercontent.com/116870617/231950133-d8cd18df-eacb-4b01-aca4-ab8e8e7648a2.png" style="width: 700px"></p>
<br>
  <p align="center">@Valid와 BindingResult 객체를 통해 필수입력정보에 대한 유효성 검사를 진행합니다.</p>
  
  ```java
// ---------- MemberDto ------------
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class MemberDto {

    private Long mId;

    @NotBlank(message = "이메일은 필수 입력 사항입니다.")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식이 맞지 않습니다." )
    private String mEmail;

    @NotBlank(message = "비밀번호는 필수 입력 사항입니다.")
    private String mPw;

    private String mZipcode;
    private String mAddr1;
    private String mAddr2;

    @NotBlank(message = "닉네임은 필수 입력 사항입니다.")
    @Pattern(regexp = "[A-Za-z0-9가-힣]{2,}", message = "닉네임 형식이 올바르지 않습니다.")
    private String mName;

    @NotBlank(message = "전화번호는 필수 입력 사항입니다")
    @Pattern(regexp = "[0-9]{2,3}-[0-9]{3,4}-[0-9]{4}", message = "전화번호 형식이 맞지 않습니다.")
    private String mTel;

    private String mIntro;
    private LocalDateTime mCreate;
    private Role mRole;
    private String mDept;
    private String mPosition;
    private int mAttach;
    private MultipartFile profileImg;

    private String originName;
    private String saveName;

// --------- MainController -----------

  //    회원가입
    @PostMapping("/joinOk")
    public String joinOk(@Valid MemberDto memberDto, BindingResult bindingResult) throws IOException {

        if(bindingResult.hasErrors()){
            return "join";
        }

        int rs = memberService.memberJoin(memberDto);

        if (rs!=1) {
            System.out.println("join fail !");
            return "redirect:/join";
        } else {
            System.out.println("join success !");
            return "redirect:/login";
        }

    }
    
// ---------- MemberService ------------

//    회원가입
    @Transactional
    public int memberJoin(MemberDto memberDto) throws IOException {

        if(memberDto.getProfileImg().isEmpty()){
//            파일이 없을때
            Long id = memberRepository.save(MemberEntity.toMemberEntity(memberDto,passwordEncoder)).getMId();

            if (memberRepository.findById(id).isEmpty()){
                return 0;
            } else {
                return 1;
            }

        } else {
//            파일이 있을때
            
//            1. 실제 파일 저장

            MultipartFile multipartFile = memberDto.getProfileImg();
            String originName = multipartFile.getOriginalFilename();  // 원본파일명
            UUID uuid = UUID.randomUUID();  // 랜덤파일명 생성

            String saveName = uuid+"_"+originName;  // 저장파일명
            String filePath = "C:/saveFiles/"+saveName;  // 파일저장경로

            multipartFile.transferTo(new File(filePath));  // 해당 경로에 저장
            
//            2. DB에 파일 정보 저장 (회원정보 저장 후, 그 id를 받아서 file entity에도 저장)

            Long id = memberRepository.save(MemberEntity.toMemberEntity(memberDto,passwordEncoder)).getMId();
            MemberEntity memberEntity = memberRepository.findById(id).get();

            Long profileId = profileRepository.save(ProfileEntity.toProfileEntity(memberEntity,originName,saveName)).getProfileId();

            if(profileRepository.findById(profileId).isEmpty()){
                return 0;
            } else {
                return 1;
            }

        }

    }
  ```

</details>

- 회원관리 CRUD 구현

<details>
<summary>상세보기</summary>
<br>
  <p align="center"><img src="" style="width: 700px"></p> 
<br>
  <p align="center">프로젝트 형상관리를 위한 기본 저장소를 생성합니다.</p>
<br>
  <p align="center"><img src="" style="width: 700px"></p>
<br>
  <p align="center">팀원들을 collaborators 및 contributers로 지정하여 저장소에 대한 pull Request뿐만 아니라 직접적인 push, pull의 권한을 부여하였습니다.</p>
<br>
  <p align="center"><img src="" style="width: 700px"></p>
<br>
  <p align="center">프로젝트 저장소를 fork하여 팀원 각자가 복사한 저장소를 통해 담당 파트별 소스코드를 업데이트할 수 있도록 합니다.</p>
<br>
  <p align="center"><img src="" style="width: 700px"></p>
<br>
  <p align="center">특정 파트의 코드가 업데이트 되는대로 fork 저장소에서 프로젝트 저장소에 pull Request를 보냅니다.</p>
<br>
  <p align="center"><img src="" style="width: 700px"></p>
<br>
  <p align="center">pull request의 커밋 내역을 확인하여 confirm을 통해 해당 수정사항을 프로젝트 저장소의 소스와 merge한 뒤,</p>
  <p align="center">각자의 fork 저장소에서 최신화합니다.</p>
<br>
</details>

- 메인 페이지, 관리자 페이지 구현
- 날씨(Openweathermap), 우편번호(다음 우편번호) API 연동
- 결재서류 승인/반려 처리 구현 <br>

#### 👨‍💻 팀원 : 김현기 <br>
#### `java package` : account / naver <br>
#### `templates package` : account / naver <br>
- 회계내역(수입/지출) CRUD 구현
- Naver API(workplace, work) 연동 <br>

#### 👨‍💻 팀원 : 김홍록 <br>
#### `java package` : notice / board / comment <br>
#### `templates package` : notice / board <br>
- 공지사항, 커뮤니티 게시판 CRUD 구현
- 공통요소(fragment) 디자인
- Github Actions, AWS EC2 기반 CI/CD 구현 <br>

#### 👨‍💻 팀원 : 이정모 <br>
#### `java package` : calender <br>
#### `templates package` : calender <br>
- 근태관리 구현
- 공식일정, 개인일정 CRUD 구현 
- 일정관리(fullcalender) API 연동 <br>

#### 👨‍💻 팀원 : 장기운 <br>
#### `java package` : member / approval <br>
#### `templates package` : member / approval / management  <br>
- 결재관리 CRUD 구현
- 조직관리 CRUD 구현
- Komoran 기반 챗봇 구현 <br>
