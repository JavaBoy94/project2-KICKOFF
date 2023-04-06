package org.project2.omwp2.naver;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Controller
public class NaverApiController {

	@Value("${navar.api.client-id}")
	String CLIENT_ID;

	@Value("${navar.api.client-secret}")
	String CLIENT_SECRET;

	//http://localhost:8090/naver/auth2
	@GetMapping("/naver/auth2")    // Redirect URL
	public String naver(String code, String state,Model model) throws Exception {
//		인가 코드 발급
//		클라이언트 앱에서 사용자 인증을 통해 인가 코드를 발급받으려면 아래와 같은 URL을 호출한다.
//		Request URL
//		https://auth.worksmobile.com/oauth2/v2.0/authorize//
//		HTTP Method
//		GET//
//		Request Param
//		각 항목의 값을 URL 인코딩하여 기입한다.//
//		파라미터	타입	필수 여부	설명
//		client_id	String	Y	Developer Console에서 발급받은 앱의 client ID
//		redirect_uri	String	Y	인가 코드 발급 후 전달할 고객사의 URL. 인코딩된 URL로 입력하며 클라이언트 앱에 등록한 Redirect URL과 일치해야 한다.
//		scope	String	Y	사용할 API의 요청 범위 정보, 여러 개의 요청 범위를 사용할 때는 쉼표(,)로 연결한다
//		response_type	String	Y	"code"로 고정
//		state	String	Y	CSRF를 방지하기 위한 클라이언트 측의 인증값
//		domain	String	N	도메인명(Lite 상품인 경우 그룹명)

//		각 항목의 값을 URL 인코딩하여 기입한다.
//
//		파라미터	타입	필수 여부	설명
//		code	String	Y	발급받은 Authorization Code 입력
//		grant_type	String	Y	"authorization_code"로 고정
//		client_id	String	Y	Developer Console에서 발급받은 앱의 client ID
//		client_secret	String	Y	Developer Console에서 발급받은 앱의 client secret
//		domain	String	N	도메인명(Lite 상품인 경우 그룹명)
//		SSO 사용 시 필수
		//access 토근 -> 인증
		String apiURL="https://auth.worksmobile.com/oauth2/v2.0/token";
		apiURL += "?code="+code;
		apiURL += "&client_id="+CLIENT_ID;
		apiURL += "&client_secret="+CLIENT_SECRET;
		apiURL += "&grant_type=authorization_code";

		URL url=new URL(apiURL);

//		Access Token 발급
//		발급받은 인가 코드로 Access Token을 발급받을 수 있다. 발급받은 Access Token은 헤더에 추가해 OPEN API를 호출할 때 사용한다.
//		Request URL
//		https://auth.worksmobile.com/oauth2/v2.0/token//
//		HTTP Method
//		POST
//		Request Header
//		Content-Type: application/x-www-form-urlencoded

		HttpURLConnection con=(HttpURLConnection) url.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		int responseCode=con.getResponseCode();

		String responseJSONData=null;
		if(responseCode == HttpURLConnection.HTTP_OK) {
			responseJSONData=readBody(con.getInputStream());
			System.out.println("정상");
		}else {
			responseJSONData=readBody(con.getErrorStream());
			System.out.println("에러");
		}
		con.disconnect();
		//*/
		//System.out.println(responseData); JSON 형식 문자열데이터
		ObjectMapper mapper=new ObjectMapper();

		NaverTokenDTO dto=mapper.readValue(responseJSONData, NaverTokenDTO.class);
		System.out.println(dto+"  <<<");

		// 전송 데이터 return
		OrgResponse orgResponse=getOrgUnit(dto);

		System.out.println("====================");
		System.out.println(orgResponse);
		model.addAttribute("list", orgResponse.getOrgUnits()); // 전송 받은 데이터를 View


		return "naver/auth2";
	}


	// 조직 연동 -> 조직 데이터  get
	private OrgResponse getOrgUnit(NaverTokenDTO dto) throws IOException {
		String apiURL="https://www.worksapis.com/v1.0/orgunits";
	apiURL +="?domainId=300077927";   // Domain ID

		URL url=new URL(apiURL);
		HttpURLConnection con=(HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		//"Bearer {token}"
		con.setRequestProperty("Authorization", "Bearer "+dto.getAccess_token());

		int responseCode=con.getResponseCode();
		String responseJSONData=null;

		if(responseCode == HttpURLConnection.HTTP_OK) {
			responseJSONData=readBody(con.getInputStream()); // 값 리턴
			System.out.println("OK");
		}else {
			responseJSONData=readBody(con.getErrorStream());
			System.out.println("Fail");
		}
		con.disconnect();

		ObjectMapper mapper=new ObjectMapper();
		OrgResponse reponse=mapper.readValue(responseJSONData, OrgResponse.class);
		return reponse;
	}

	//응답데이터를 스트림을 통해서 한줄씩읽어서 문자열로 리턴
	private String readBody(InputStream inputStream) throws IOException {
		InputStreamReader streamReader=new InputStreamReader(inputStream);
		BufferedReader lineReader=new BufferedReader(streamReader);
		StringBuilder responseBody=new StringBuilder();
		String data;
		while((data=lineReader.readLine()) != null) {
			responseBody.append(data);
		}
		lineReader.close();
		streamReader.close();
		return responseBody.toString();
	}
}
