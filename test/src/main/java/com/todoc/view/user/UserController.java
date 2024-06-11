package com.todoc.view.user;

import java.awt.peer.LightweightPeer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.todoc.googlecloudstorage.GCSService;
import com.todoc.hospital.HospitalService;
import com.todoc.hospital.HospitalVO;
import com.todoc.hospital.dao.TimeMapper;
import com.todoc.user.UserService;
import com.todoc.user.UserVO;

@Controller
public class UserController {
	@Autowired
	@Qualifier("userService")
	private UserService userService;
	@Autowired
	@Qualifier("hospitalService")
	private HospitalService hospitalService;
	@Autowired
	@Qualifier("gcsService")
	private GCSService gcsService;
	@Autowired
	@Qualifier("timeMapper")
	private TimeMapper timeMapper;
	
	public UserController() {
		System.out.println("=======> UserController()객체생성");
	}

	@PostMapping("/user/login.do")
	public String login(UserVO vo, Model model, HttpSession session ) {
		System.out.println(">> 로그인 처리");
		System.out.println("vo : " + vo);
		
		if (vo.getEmail() == null || vo.getEmail().equals("")) {
			System.out.println("아이디를 입력하세요");
			
		}
		if (vo.getPassword() == null || vo.getPassword().equals("")) {
			System.out.println("비번입력하세요");
		}
		
		UserVO user = userService.getUser(vo);
		System.out.println("user : " + user);
		
		
		if (user != null) {
			session.setAttribute("user", user);
			model.addAttribute("msg", "로그인 성공");
			System.out.println(">>로그인 성공");
			session.setAttribute("user", user);
			if (user.getRole().equals("admin")) {
				return "redirect:/index.jsp?msg=admin";
			} else {
				return "redirect:/index.jsp?msg=success";
			}

			
		} else {
			model.addAttribute("msg", "로그인 실패");
			System.out.println(">>로그인 실패");
			return "redirect:login.do?msg=failure";
		}
	}
	
	//병원로그인======
	@PostMapping("/user/hoLogin.do")
	public String hoLogin(HospitalVO ho, Model model, HttpSession session) {
		System.out.println(">> 병원로그인 처리");
		System.out.println("ho : " + ho);
		
		if (ho.getHosId() == null || ho.getHosId().equals("")) {
			System.out.println("아이디를 입력하세요");
			
		}
		if (ho.getHosPw() == null || ho.getHosPw().equals("")) {
			System.out.println("비번입력하세요");
		}
		HospitalVO user = hospitalService.getHos(ho);
		System.out.println("hoUser : " + user);
		
		if (user != null) {
			model.addAttribute("msg", "로그인 성공");
			session.setAttribute("hoUser", user);
			System.out.println(">>로그인 성공");
			
			return "redirect:/index.jsp?msg=hosuccess";
			
		} else {
			model.addAttribute("msg", "로그인 실패");
			System.out.println(">>로그인 실패");
			return "redirect:hoLogin.do?msg=failure";
		}
	}
	
	//개인 회원가입으로 화면전환
	@RequestMapping("user/userJoin.do")
	public String userJoin(UserVO vo) {
		return "user/userJoin";
	}
	//개인 회원가입 처리
	@PostMapping("user/userJoin.do")
	public String userJoinOk(UserVO vo, Model model) throws Exception {
		if (vo.getUserImg() == null || vo.getUserImg().isEmpty()) {
	        vo.setUserImg("	https://storage.googleapis.com/todoc-storage/89e6c102-02df-4055-8a9a-afdec82d735f-user.png"); // 기본 프로필 이미지 URL 설정
	    }
		
		int cnt = userService.email(vo);
		
		if (cnt == 0) {
			//관리자 계정 설정(role="admin")
			if (vo.getEmail().equals("admin@naver.com") 
					&& vo.getPassword().equals("admin")) {
				vo.setRole("admin");
				userService.insertUser(vo);
				System.out.println(">> 관리자로 회원가입 완료");
				model.addAttribute("msg", "회원가입 완료");
				return "redirect:login.do?msg=admin";
			} else {
				//관리자 계정 외는 모두 개인유저
				vo.setRole("user");
				userService.insertUser(vo);
				System.out.println("vo : " + vo);
				System.out.println(">>회원가입 완료");
				model.addAttribute("msg", "회원가입 완료");
				return "redirect:login.do?msg=success";
			}
		} else {
			System.out.println("vo : " + vo);
			System.out.println(">>회원가입 실패");
			model.addAttribute("msg", "회원가입 실패");
			return "redirect:userJoin.do";
		}
		
	}

	//기업회원가입 화면전환
	@RequestMapping("/user/hoJoin.do")
	public String hoJoin(UserVO vo) {
		return "user/hoJoin";
	}
	//기업회원가입(정보 테이블 3개 입력 처리)
	@PostMapping("/user/hoJoin.do")
	public String hoJoinchk(Model model, HospitalVO vo
			, @RequestParam("hosImgStr") List<MultipartFile> hosImgStr
			, @RequestParam("certificateImgStr") MultipartFile certificateImgStr
			, @RequestParam("openTimeStr")String openTimeStr, @RequestParam ("closeTimeStr")String closeTimeStr
			, @RequestParam("lunchTimeStr")String lunchTimeStr, @RequestParam("endLunchTimeStr")String endLunchTimeStr
			, @RequestParam("satOpenTimeStr")String satOpenTimeStr, @RequestParam ("satCloseTimeStr")String satCloseTimeStr
			, @RequestParam ("satLunchTimeStr")String satLunchTimeStr, @RequestParam ("satEndLunchTimeStr")String satEndLunchTimeStr
			, @RequestParam("sunOpenTimeStr")String sunOpenTimeStr, @RequestParam ("sunCloseTimeStr")String sunCloseTimeStr
			, @RequestParam ("sunLunchTimeStr")String sunLunchTimeStr, @RequestParam ("sunEndLunchTimeStr")String sunEndLunchTimeStr
			, @RequestParam(value="lunchOff" , required=false)String lunchOff
			, @RequestParam (value="satLunchOff" , required=false)String satLunchOff
			, @RequestParam(value="sunDayOff", required=false)String sunDayOff
			) {
		//hoJoin 입력 폼에서 vo 값 넘어오는지 확인
		System.out.println(":: TimeController.saveTime() 메소드 실행~!!");
		//병원 정보 및 파일(사업자등록증) 업로드 처리
		try {
			String certificateImg = gcsService.uploadFile(certificateImgStr);
			vo.setCertificateImg(certificateImg);
			//hospital 테이블 입력
			int cntHospital = hospitalService.insertHospital(vo);
			System.out.println("cntHospital : " + cntHospital);
			if (cntHospital == 0) {
				System.out.println(">> 회원가입 실패");
				return "redirect:hoJoin.do?msg=fail";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:hoJoin.do?msg=fileError";
		}
		
		//병원 파일 여러개(상세 사진) 업로드 처리
		System.out.println("hosImgStr : " + hosImgStr);
		List<MultipartFile> hosImgList = new ArrayList<MultipartFile>();
        hosImgList = hosImgStr;
        
        // List를 배열로 변환
        MultipartFile[] hosImgArr = new MultipartFile[hosImgList.size()];
        hosImgArr = hosImgList.toArray(hosImgArr);
        // 결과 출력 (여기서는 배열의 길이만 출력)
        System.out.println("Array length: " + hosImgArr.length);
        //병원 사진(여러장 처리)
        for (MultipartFile hosImgFile : hosImgArr) {
        	try {
        		System.out.println("MultipartFile hosImg : " + hosImgFile);
        		String hosImg = gcsService.uploadFile(hosImgFile);
        		//병원 정보 입력한 hosIdx 조회
        		int insertHosIdx = hospitalService.getHosIdx(vo);
        		System.out.println(":: insertHosIdx : " + insertHosIdx);
        		vo.setHosIdx(insertHosIdx);
        		//파일 경로 설정
        		vo.setHosImg(hosImg);
        		//병원 사진 1장 입력
        		int cntHosImg = hospitalService.insertHosImg(vo);
    			if (cntHosImg == 0) {
    				System.out.println(">> 업로드 파일 없음");
    				return "redirect:hoJoin.do?msg=noFile";
    			}
			} catch (Exception e) {
				e.printStackTrace();
	            return "redirect:hoJoin.do?msg=fileError";
			}
        }
		
		//hosAddress 테이블 입력
		int cntHosAddress = hospitalService.insertHosAddress(vo);
		System.out.println("cntHosAddress : " + cntHosAddress);
		if (cntHosAddress == 0) {
			System.out.println(">> 회원가입 실패");
			return "redirect:hoJoin.do?msg=fail";
		}
		
		//hosTime 테이블 입력
		//시간 형식에서 ss(초) 문자열 추가
		System.out.println("openTimeStr : " + openTimeStr);
		String fullOpenTimeStr = openTimeStr + ":00";
		String fullCloseTimeStr = closeTimeStr + ":00";
		String fullLunchTimeStr = lunchTimeStr + ":00";
		String fullEndLunchTimeStr = endLunchTimeStr + ":00";
		String fullSatOpenTimeStr = satOpenTimeStr + ":00";
		String fullSatCloseTimeStr = satCloseTimeStr + ":00";
		String fullSatLunchTimeStr = satLunchTimeStr + ":00";
		String fullsatEndLunchTimeStr = satEndLunchTimeStr + ":00";
		String fullSunOpenTimeStr = sunOpenTimeStr + ":00";
		String fullSunCloseTimeStr = sunCloseTimeStr + ":00";
		String fullSunLunchTimeStr = sunLunchTimeStr + ":00";
		String fullSunEndLunchTimeStr = sunEndLunchTimeStr + ":00";
		
		//운영 시간에서 시각 0~23 유효성 검사 및 수정하는 메서드 호출
		String validOpenTime = validateAndCorrectTime(fullOpenTimeStr);
		String validCloseTime = validateAndCorrectTime(fullCloseTimeStr);
		String validLunchTime = validateAndCorrectTime(fullLunchTimeStr);
		String validEndLunchTime = validateAndCorrectTime(fullEndLunchTimeStr);
		String validSatOpenTime = validateAndCorrectTime(fullSatOpenTimeStr);
		String validSatCloseTime = validateAndCorrectTime(fullSatCloseTimeStr);
		String validSatLunchTime = validateAndCorrectTime(fullSatLunchTimeStr);
		String validSatEndLunchTime = validateAndCorrectTime(fullsatEndLunchTimeStr);
		String validSunOpenTime = validateAndCorrectTime(fullSunOpenTimeStr);
		String validSunCloseTime = validateAndCorrectTime(fullSunCloseTimeStr);
		String validSunLunchTime = validateAndCorrectTime(fullSunLunchTimeStr);
		String validSunEndLunchTime = validateAndCorrectTime(fullSunEndLunchTimeStr);
		System.out.println("validOpenTime : " + validOpenTime);
		
		try {
			//hosTime 테이블 입력
			int cntHosTime = timeMapper.insertTime(vo, validOpenTime, validCloseTime, validLunchTime, validEndLunchTime
								, validSatOpenTime, validSatCloseTime, validSatLunchTime, validSatEndLunchTime
								, validSunOpenTime, validSunCloseTime, validSunLunchTime, validSunEndLunchTime
								, lunchOff, satLunchOff, sunDayOff);
			System.out.println("cntHosTime : " + cntHosTime);
			if (cntHosTime == 0) {
				System.out.println(">> 회원가입 실패");
				return "redirect:hoJoin.do?msg=fail";
			}
			System.out.println("vo : " + vo);
			System.out.println(">> 회원가입 완료");
			return "redirect:login.do?msg=success";
		} catch (Exception e) {
			e.printStackTrace();
			return"redirect:hoJoin.do?msg=fail";
		}
		
	}
	//유효성 검사 메소드(운영 시각 0~23만 사용 가능)
	private String validateAndCorrectTime(String timeStr) {
		String[] timeParts = timeStr.split(":");
        int hour = Integer.parseInt(timeParts[0]);
        int minute = Integer.parseInt(timeParts[1]);
        int second = Integer.parseInt(timeParts[2]);

        // 시간 값이 0에서 23 사이인지 확인
        if (hour < 0 || hour > 23) {
            hour = hour % 24;
        }
        // 유효한 시간 값으로 반환
        return String.format("%02d:%02d:%02d", hour, minute, second);
	}


	//로그인 화면 전환
	@RequestMapping("user/login.do")
	public String loginView(@ModelAttribute("user")UserVO vo) {
		System.out.println(">> 로그인화면이동");
		vo.setEmail(vo.getEmail());
		vo.setPassword(vo.getPassword());
		return "user/login";
	}

	//카카오 redirect
	@RequestMapping(value = "user/redirect.do", method = RequestMethod.GET)
	public String kakaoRedirect(@RequestParam("code") String code, @RequestParam("state") String state, HttpSession session, UserVO vo) {
		System.out.println(">> 카카오 로그인 리디렉션 처리 - code: " + code + ", state: " + state);
  
  // 여기에 카카오 API를 사용하여 access token을 요청하고 세션에 저장하는 코드를 추가하세요.
  // 이후, 사용자 정보를 요청하여 session에 저장하거나 적절한 처리를 할 수 있습니다.
 
		return "user/redirect"; 
	}
	
	//병원로그인 화면전환
	@GetMapping("user/hoLogin.do")
	public String hoLoginView(HospitalVO ho) {
		System.out.println(">>병원로그인화면이동");
		ho.setHosId(ho.getHosId());
		ho.setHosPw(ho.getHosPw());
		return "user/hoLogin";
	}
	
	//로그아웃
	@RequestMapping("logout.do")
	public String logout(HttpSession session) {

		System.out.println(">> 로그아웃 처리");
		session.invalidate();
		
		return "redirect:/index.jsp";
		
	}

	//개인 이메일찾기 화면전환
	@RequestMapping("user/findEmail.do")
	public String findEmailView() {
		return "user/findEmail";
	}
	
	//개인 이메일 찾기
	@PostMapping("user/findEmail.do")
	public String findEmail(UserVO vo, HttpServletRequest request, Model model,
			@RequestParam String name, @RequestParam String phone) {
		try {
			vo.setName(name);
			vo.setPhone(phone);
			UserVO email = userService.findEmail(vo);
			String findEmail = email.getEmail();
			model.addAttribute("findEmail", findEmail);
		}  catch (Exception e) {
			e.printStackTrace();
		}
		return "user/findEmailResult";
	}
	
	//개인 비번찾기 화면전환
	@RequestMapping("user/findPwd.do")
	public String findPwdView() {
		return "user/findPwd";
	}
	
	//개인 비번찾기
	@PostMapping("user/findPwd.do")
	public String findPwd(UserVO vo, HttpServletRequest request, Model model,
			@RequestParam String name, @RequestParam String email) {
		vo.setName(name);
		vo.setEmail(email);
		int search = userService.findPwd(vo);
		
		if (search == 0) {
			model.addAttribute("msg", "가입된 정보가 없습니다.");
			return "user/findPwd";
		}
		
		String newPwd = RandomStringUtils.randomAlphanumeric(10);
		vo.setPassword(newPwd);
		userService.updateExPwd(vo);
		
		model.addAttribute("newPwd", newPwd);
		
		return "user/findPwdResult";
	}
	
	//병원 아이디 찾기 화면전환
	@RequestMapping("user/hoFindId.do")
	public String hoFindIdView() {
		return "user/hoFindId";
	}
	
	//병원 아이디 찾기
	@PostMapping("user/hoFindId.do")
	public String hoFindId(HospitalVO vo, HttpServletRequest request, Model model,
			@RequestParam String hosName, @RequestParam String hosPhone) {
		try {
			vo.setHosName(hosName);
			vo.setHosPhone(hosPhone);
			HospitalVO hosId = hospitalService.hoFindId(vo);
			String findHosId = hosId.getHosId();
			model.addAttribute("findHosId", findHosId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "user/hoFindIdResult";
	}
	
	//병원비번 찾기 화면전환
	@RequestMapping("user/hoFindPwd.do")
	public String hoFindPwdView() {
		return "user/hoFindPwd";
	}
	
	//병원 비번찾기 
	@PostMapping("user/hoFindPwd.do")
	public String hoFindPwd(HospitalVO vo, HttpServletRequest request, Model model,
			@RequestParam String hosName, @RequestParam String hosId) {
		vo.setHosName(hosName);
		vo.setHosId(hosId);
		int search = hospitalService.hoFindPwd(vo);
		
		if (search == 0) {
			model.addAttribute("msg", "가입된 정보가 없습니다.");
			return "user/hoFindPwd";
		}
		String newPwd = RandomStringUtils.randomAlphanumeric(10);
		vo.setHosPw(newPwd);
		hospitalService.hoUpdateExPwd(vo);
		model.addAttribute("newPwd", newPwd);
		
		return "user/hoFindPwdResult";
	}
	
	

}
