<p align="center">
  <img src="https://user-images.githubusercontent.com/116870617/231791531-9e7ee801-a462-4b7a-977d-1e56b195e28b.png">
</p>

# KICKOFF (킥오프)
## Open API와 챗봇을 활용한 축구 동호회 운영/관리 그룹웨어 
## [프로젝트 소개 PDF(영상포함)](https://drive.google.com/file/d/18gtjqQk1KOKh-EcekBsUl4dJVbOpZ3JZ/view?usp=share_link)
## [프로젝트 DB 명세서](https://drive.google.com/file/d/187f50y_fUM-Pkhs0hEeW8VLSJVCvn7U-/view?usp=share_link)
## [프로젝트 DB ERD](https://drive.google.com/file/d/1JvddvhrdoV1i78wkrrgPDKJa1tq9k7LW/view?usp=share_link)

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
  <p align="center"><img src="https://user-images.githubusercontent.com/116870617/231946224-024f51b0-332d-4b84-873d-ae8d5f70f93b.png"></p> 
<br>
  <p align="center">프로젝트 형상관리를 위한 기본 저장소를 생성합니다.</p>
<br>
  <p align="center"><img src="https://user-images.githubusercontent.com/116870617/231946228-d8212fef-59a7-431a-94f3-99f76ccc340b.png"></p>
<br>
  <p align="center">팀원들을 collaborators 및 contributers로 지정하여 저장소에 대한 pull Request뿐만 아니라 직접적인 push, pull의 권한을 부여하였습니다.</p>
<br>
  <p align="center"><img src="https://user-images.githubusercontent.com/116870617/231946229-022ab668-468c-4acb-9b14-26cd3b3444e8.png"></p>
<br>
  <p align="center">프로젝트 저장소를 fork하여 팀원 각자가 복사한 저장소를 통해 담당 파트별 소스코드를 업데이트할 수 있도록 합니다.</p>
<br>
  <p align="center"><img src="https://user-images.githubusercontent.com/116870617/231946230-60aa3a72-5905-4790-880a-a3ec82558599.png"></p>
<br>
  <p align="center">특정 파트의 코드가 업데이트 되는대로 fork 저장소에서 프로젝트 저장소에 pull Request를 보냅니다.</p>
<br>
  <p align="center"><img src="https://user-images.githubusercontent.com/116870617/231946232-acf9471d-85b8-4f90-a9b8-83d54c3af94a.png"></p>
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
  <p align="center"><img src="https://user-images.githubusercontent.com/116870617/231950119-3478b0a0-5bc1-4da0-9d0a-81f874d26a91.png"></p> 
<br>
  <p align="center">페이지의 인증 및 인가를 처리하는 SecurityFilterChain객체를 통해 페이지별 접근권한과 기본적인 로그인 설정을 구현합니다.</p>
  
  ```java
// --------- WebSecurityConfig ----------
  
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
  <p align="center"><img src="https://user-images.githubusercontent.com/116870617/231950129-6ae46cd3-0cda-4216-976f-f781a26e3927.png"></p>
<br>
  <p align="center">.yml에 oauth2를 통한 소셜로그인(google, naver, kakao)을 위한 api설정을 합니다.</p>
<br>
  <p align="center"><img src="https://user-images.githubusercontent.com/116870617/231950130-35133640-cadb-4690-b3d2-414dfb213336.png"></p>
<br>
  <p align="center">회원가입시 우편번호 API를 활용하여 사용자의 주소를 입력받으며, multipartfile 객체를 통해 프로필 사진을 등록할 수 있습니다.</p>
<br>
  <p align="center"><img src="https://user-images.githubusercontent.com/116870617/231950133-d8cd18df-eacb-4b01-aca4-ab8e8e7648a2.png"></p>
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
  
<br>
</details>

- 회원관리 CRUD 구현

<details>
<summary>상세보기</summary>
<br>
  <p align="center"><img src="https://user-images.githubusercontent.com/116870617/231952108-cdb090f3-0bae-4257-ba17-95e018937c93.png"></p> 
<br>
  <p align="center">회원상세 메뉴에선 Principal 객체를 통해 현재 로그인한 회원정보를 불러옵니다.</p>
  
  ```java
// ----------- MemberController ------------

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final BoardService boardService;

//    회원정보 상세 (로그인 회원)
    @GetMapping("/detail")
    public String memberDetail(Model model, Principal principal){

        String mEmail = principal.getName();
        System.out.println(mEmail+"<<<< member Email");

        MemberDto memberDto = memberService.getMemberDetail(mEmail);

        model.addAttribute("memberDto",memberDto);

        return "member/memberDetail";
    }
    
// ------------ MemberService --------------

    public MemberDto getMemberDetail(String mEmail) {

        MemberEntity memberEntity = memberRepository.findBymEmail(mEmail).get();

        if(memberEntity==null){
            System.out.println("======== memberDetail fail ========");
            return null;
        }

        return MemberDto.toMemberDto(memberEntity);
    }
    
  ```
  
<br>
  <p align="center"><img src="https://user-images.githubusercontent.com/116870617/231952118-a699b845-1817-46b1-80d9-b0b501df3455.png"></p>
<br>
  <p align="center">회원수정시 기존의 회원ID값을 넘겨줌으로써, save 메소드가 수정으로 처리될 수 있도록 하였습니다.</p>
  <p align="center">프로필 사진을 수정하지 않을 경우 현재 저장파일명을 계속 사용하여 기존 이미지를 그대로 사용하도록 하였습니다.</p>
  
```java
// ----------- MemberController -------------

//    회원수정 실행
    @PostMapping("/updateOk")
    public String memberUpdateDo(@Valid MemberDto memberDto, BindingResult bindingResult,
                                 Principal principal, Model model) throws IOException {

        if(bindingResult.hasErrors()){
//            유효성 검사 에러 발생시
            String mEmail = principal.getName();

            MemberDto memberDto2 = memberService.getMemberDetail(mEmail);
            model.addAttribute("memberDto",memberDto);

            return "member/memberUpdate";
        }

        int rs = memberService.memberUpdate(memberDto);

        if(rs!=1){
            System.out.println("member update fail !");
            return null;
        }
        return "redirect:/member/detail";
    }
    
// ------------ MemberService ---------------

 @Transactional
    public int memberUpdate(MemberDto memberDto) throws IOException {

        if(memberDto.getProfileImg().isEmpty()) {
//            수정할 파일이 없을 때 => 기존 이미지 사용

            memberRepository.save(MemberEntity.toMemberEntity2(memberDto,passwordEncoder));

        } else {
//            수정할 파일이 있을 때 => 기존 파일 삭제 후, 수정할 파일 저장
//            1. 기존 파일 삭제
           ProfileEntity profileEntity =
                   profileRepository.findAllByMId(memberDto.getMId());
           
           if(profileEntity!=null){
//              기존에 등록된 이미지가 존재할 경우 삭제
               profileRepository.delete(profileEntity);
           }

//               기존 파일 삭제 성공하면 다시 파일 추가
            MultipartFile multipartFile = memberDto.getProfileImg();
            String originName = multipartFile.getOriginalFilename();  // 원본파일명
            UUID uuid = UUID.randomUUID();  // 랜덤파일명 생성

            String saveName = uuid+"_"+originName;  // 저장파일명
            String filePath = "C:/saveFiles/"+saveName;  // 파일저장경로

            multipartFile.transferTo(new File(filePath));  // 해당 경로에 저장

//            2. DB에 파일 정보 저장 (회원정보 저장 후, 그 id를 받아서 file entity에도 저장)

            Long id = memberRepository.save(MemberEntity.toMemberEntity2(memberDto,passwordEncoder)).getMId();
            MemberEntity memberEntity = memberRepository.findById(id).get();

            Long profileId = profileRepository.save(ProfileEntity.toProfileEntity(memberEntity,originName,saveName)).getProfileId();

            if(profileRepository.findById(profileId).isEmpty()){
                return 0;
            }
        }
            return 1;
    }

```

<br>
  <p align="center"><img src="https://user-images.githubusercontent.com/116870617/231952120-482b082c-1671-462a-934a-dcd426e0f768.png"></p>
<br>
  <p align="center">작성글 관리 메뉴에서 전체 게시글 중, 현재 로그인한 회원의 ID값을 갖는 게시글만 findAllby 메소드를 사용하여 가져옵니다.</p>
  
  ```java
// ----------- MemberController --------------

  //    작성글 관리 => 해당 회원이 쓴 글 목록
    @GetMapping("/myBoardList")
    public String myBoardList(@PageableDefault(page = 0, size = 10, sort = "board_id",
                            direction = Sort.Direction.DESC) Pageable pageable,
                            Model model, Principal principal) {

            String mEmail = principal.getName();
            Long mId = memberRepository.findBymEmail(mEmail).get().getMId();

            Page<BoardDto> boardList = null;

            boardList = boardService.myBoardListDo(mId, pageable);

            int totalPage = boardList.getTotalPages();  // 총 페이지 수
            int blockNum = 3;                            // 화면에 표시할 페이지 수
            int nowPage = boardList.getNumber();        // 현재페이지
            int startPage = (int) ((Math.floor(nowPage / blockNum) * blockNum) + 1 <= totalPage ? (Math.floor(nowPage / blockNum) * blockNum) + 1 : totalPage);
            // 블록의 첫페지이지
            // 블록이 3일 경우     123 -> 1, 456  -> 4 , 789 -> 7

            int endPage = (startPage + blockNum - 1 < totalPage ? startPage + blockNum - 1 : totalPage);

            model.addAttribute("boardList", boardList);
            model.addAttribute("nowPage", nowPage);
            model.addAttribute("startPage", startPage);
            model.addAttribute("endPage", endPage);

            return "member/myBoardList";
    }
    
// ------------- MemberService --------------

@Transactional
    public void boardDeleteDo(Long productId) {
        boardReposistory.deleteById(productId);
    }

    public Page<BoardDto> myBoardListDo(Long mId, Pageable pageable) {

        Page<BoardEntity> boardEntityPage = boardReposistory.findAllBymId(mId,pageable);
        Page<BoardDto> boardDtoPage = boardEntityPage.map(boardEntity -> BoardDto.toBoardDto(boardEntity));

        return boardDtoPage;
    }
    
// -------------- MemberRepository --------------

    @Query(value = "select * from board " +
            "where m_id=:mId",nativeQuery = true)
    Page<BoardEntity> findAllBymId(@Param("mId") Long mId, Pageable pageable);
    
  ```
  
<br>
</details>

- 메인 페이지, 관리자 페이지 구현
  
<details>
<summary>상세보기</summary>
<br>
  <p align="center"><img src="https://user-images.githubusercontent.com/116870617/231962498-90ed947f-56cb-4bc4-8400-d982f1f4a5e8.png"></p> 
<br>
  <p align="center">Open API를 활용하여 날씨(Openweather)와 일정관리(fullcalender) 기능을 추가하였으며,</p>
  <p align="center">공지사항 및 커뮤니티 게시판의 최신 글목록도 조회하도록 하였습니다.</p>
  
  
  ```javascript
  
// ------------ weather.js --------------
  
  // 현재시간 출력
    $(document).ready(function () {
    
      function convertTime() {
          let now = new Date();
    
          let year = now.getFullYear();
          let month = now.getMonth() + 1;
          let date = now.getDate();
          let hours = now.getHours();
          let minutes = now.getMinutes() <= 9 ? '0' + now.getMinutes() : now.getMinutes();
          // hour(시)가 12 이상이면 오후(pm), 12 미만이면 오전(am)으로 설정
          let ampm = hours >= 12 ? 'pm' : 'am';
          // heour(시)를 12시간 단위로 변경 => 13시부터 12로 나눈 나머지(1~12)로, 그 미만은 그대로
          let hours2 = hours > 12 ? hours % 12 : hours;

          let weekday = new Array(7);
          weekday[0] = "일";
          weekday[1] = "월";
          weekday[2] = "화";
          weekday[3] = "수";
          weekday[4] = "목";
          weekday[5] = "금";
          weekday[6] = "토";
    
          let n = weekday[now.getDay()];
    
          return year +'.'+ month + '.' + date + '(' + n + ') ' + hours2 + ':' + minutes + ampm;
      }
    
      let currentTime = convertTime();
      $('.nowtime').append(currentTime);
    });
    
    // API 요청
    $.getJSON('https://api.openweathermap.org/data/2.5/weather?q=Seoul,kr&appid=5a87979705c7dd0e87fc7cfda0976f92&lang=kr&units=metric',
    function (WeatherResult) {
    
      // 파라미터
      // q : 도시명
      // appid : apikey (발급필요)
      // lang : 언어 (kr : 한국어)
      // units : 온도표시 방식 (metric : 섭씨)
    
      // 날씨정보 출력
      Math.round
      $('.SeoulWeatherDesc').append(WeatherResult.weather[0].description);
      $('.SeoulNowtemp').append(Math.round(WeatherResult.main.temp,1)+'°C');
      $('.SeoulLowtemp').append(Math.round(WeatherResult.main.temp_min,1)+'°C');
      $('.SeoulHightemp').append(Math.round(WeatherResult.main.temp_max,1)+'°C');
    
      //날씨아이콘출력
      //WeatherResult.weater[0].icon
      let weathericonUrl =
          '<img src= "http://openweathermap.org/img/wn/'
          + WeatherResult.weather[0].icon +
          '.png" alt="' + WeatherResult.weather[0].description + '"/>'
    
      $('.SeoulIcon').html(weathericonUrl);
    });

};
  ```
  
<br>
  <p align="center"><img src="https://user-images.githubusercontent.com/116870617/231962503-bab18dc4-db88-429a-8fa6-cf0227712c9c.png"></p>
<br>
  <p align="center">관리자메뉴 내 회원관리 페이지에선 전체회원목록을 Page객체를 통해 정보를 가져오며, 검색기능도 추가하였습니다.</p>
  <p align="center">display:gird 설정을 통해 일정한 사이즈의 회원정보 요소가 자동을 생성되도록 하였습니다.</p>
  
  ```java
// ------------ MemberController -------------

  //    관리자메뉴 - 전체 회원목록
    @GetMapping("/memberList")
    public String memberList(@PageableDefault(page = 0, size = 8, sort = "mCreate", direction = Sort.Direction.DESC)
                             Pageable pageable, Model model,
                             @RequestParam(value = "type",required = false) String type,
                             @RequestParam(value = "search",required = false) String search) {

        Page<MemberDto> memberList = memberService.getMemberList(pageable);

//        검색조회
        if(type != null && search != null) {

            if(type.equals("mName")) {
//                이름으로 검색
                memberList = memberService.findMemberName(search,pageable);
            } else if (type.equals("mEmail")) {
//                이메일로 검색
                memberList = memberService.findMemberEmail(search,pageable);
            } else if (type.equals("mTel")) {
//                연락처로 검색
                memberList = memberService.findMemberTel(search,pageable);
            }

        }

        int totalPage = memberList.getTotalPages();  // 총 페이지 수
        int blockNum = 3;                            // 화면에 표시할 페이지 수 => 2페이지씩 표시
        int nowPage = memberList.getNumber();        // 현재페이지
        int startPage = (int)((Math.floor(nowPage/blockNum)*blockNum)+1 <= totalPage ? (Math.floor(nowPage/blockNum)*blockNum)+1 : totalPage);
        // 블록의 첫페지이지
        // 블록이 3일 경우     123 -> 1, 456  -> 4 , 789 -> 7
        // Math.floor -> 올림

        int endPage = (startPage + blockNum-1 < totalPage ? startPage + blockNum-1 : totalPage);
        // 블록의 마지막 페이지
        // 블록이 3일 경우      123 -> 3, 456  -> 5 , 789 -> 9
        // 시작페이지+블록-1> 전체 페이지 -> 마지막페이지숫자(시작페이지+블록-1)

        model.addAttribute("memberList", memberList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "member/adminMemberList";
    }
    
// --------- MemberService -----------

//    관리자메뉴 내 회원목록 검색 - 이름 기준
    public Page<MemberDto> findMemberName(String search, Pageable pageable) {
        
        Page<MemberEntity> memberEntityPage = memberRepository.findBymNameContaining(search,pageable);

        if (memberEntityPage.isEmpty()){
            return null;
        }

        Page<MemberDto> memberDtoPage = memberEntityPage.map(MemberDto::toMemberDto);

        return memberDtoPage;
    }

//    관리자메뉴 내 회원목록 검색 - 이메일 기준
    public Page<MemberDto> findMemberEmail(String search, Pageable pageable) {

        Page<MemberEntity> memberEntityPage = memberRepository.findBymEmailContaining(search,pageable);

        if (memberEntityPage.isEmpty()){
            return null;
        }

        Page<MemberDto> memberDtoPage = memberEntityPage.map(MemberDto::toMemberDto);

        return memberDtoPage;
    }

//    관리자메뉴 내 회원목록 검색 - 연락처 기준
    public Page<MemberDto> findMemberTel(String search, Pageable pageable) {

        Page<MemberEntity> memberEntityPage = memberRepository.findBymTelContaining(search,pageable);

        if (memberEntityPage.isEmpty()){
            return null;
        }

        Page<MemberDto> memberDtoPage = memberEntityPage.map(MemberDto::toMemberDto);

        return memberDtoPage;
    }
    
  ```
  
<br>
  <p align="center"><img src="https://user-images.githubusercontent.com/116870617/231962509-fa6723b9-48a2-42b0-a835-8170301f1788.png"></p>
<br>
  <p align="center">회원목록에서 특정회원 정보 클릭시 회원상세 페이지로 이동합니다.</p>
<br>
  <p align="center"><img src="https://user-images.githubusercontent.com/116870617/231962515-6f286f7f-7b8e-425e-be49-304479530ac7.png"></p>
<br>
  <p align="center">공지사항 관리에선 전체 공지사항 게시물에 대한 RUD가 가능합니다.</p>
<br>
  <p align="center"><img src="https://user-images.githubusercontent.com/116870617/231962518-11b91f74-4777-4acc-9468-80532627ddfd.png"></p>
<br>
  <p align="center">게시판 관리에선 전체 커뮤니티 게시판의 게시물에 대한 RUD가 가능합니다.</p>
<br>
</details>
  
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
