package spring.controller;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import spring.entity.ExamDto;
import spring.entity.ExamQuizDto;
import spring.entity.HistoryDto;
import spring.entity.MemberDto;
import spring.entity.QuizDto;
import spring.entity.RegisterParamDto;
import spring.entity.SampleDto;
import spring.repository.AnswerSheetDao;
import spring.repository.ExamDao;
import spring.repository.ExamQuizDao;
import spring.repository.ExamTypeDao;
import spring.repository.Exam_Quiz_AnswerSheetDao;
import spring.repository.HistoryDao;
import spring.repository.MemberDao;
import spring.repository.MemberGroupDao;
import spring.repository.QuizDao;
import spring.repository.SampleDao;
import spring.service.DateService;


@Controller
@RequestMapping("/register")
public class RegisterController {
	
	@Autowired
	private ExamDao examDao;
	@Autowired
	private QuizDao quizDao;
	@Autowired
	private ExamTypeDao examTypeDao;
	@Autowired
	private HistoryDao historyDao;
	@Autowired
	private Exam_Quiz_AnswerSheetDao exam_Quiz_AnswerSheetDao;
	@Autowired
	private SampleDao sampleDao;
	@Autowired
	private MemberDao memberDao;
	@Autowired
	private MemberGroupDao memberGroupDao;
	@Autowired
	private ExamQuizDao examQuizDao;
	@Autowired
	private AnswerSheetDao answerSheetDao;
	
	@Autowired
	private DateService dateService;
	
	private Logger log = LoggerFactory.getLogger(getClass());
	
	//(출제자) 시험 목록 화면
	//오늘 날짜를 구해서 넘겨준다
	@GetMapping("/list")
	public String list(Model model, HttpSession session) throws ParseException {
		//출제자일 경우
		List<ExamDto> list = null;
		if(session.getAttribute("power").equals("출제자")) {
			list =examDao.registerlist((String)session.getAttribute("id"));
		}
		else if(session.getAttribute("power").equals("관리자")) {
			list=examDao.list();
		}
		for(ExamDto dto: list) {
			dto.setCondition(dateService.compare(dto.getEstart(), dto.getEfinish()));
			log.debug("======================= condition = {}", dto.getCondition());
		}
		model.addAttribute("list",list);
		return "register/list";
	}
	//(출제자) 시험 목록 검색
	//examDao search(key, id); 검색
	//관리자는 목록 전체 확인 가능
	@PostMapping("/list")
	public String list(@RequestParam String key, HttpSession session, Model model) throws ParseException {
		log.debug("key = {}", key);
		List<ExamDto> list = null;
		if(session.getAttribute("power").equals("출제자")) {
			list =  examDao.registersearch((String)session.getAttribute("id"),key);
		}
		else if(session.getAttribute("power").equals("관리자")) {
			list = examDao.search(key);
		}
		for(ExamDto dto: list) {
			dto.setCondition(dateService.compare(dto.getEstart(), dto.getEfinish()));
			log.debug("======================= condition = {}", dto.getCondition());
		}
		model.addAttribute("list",list);
		return "register/list";
	}
	//(출제자) 시험 등록 페이지 - exam_type list 보내기
	//examTypeDao List<examTypeDto> list();
	@GetMapping("/regist")
	public String regist(Model model) {
		model.addAttribute("examtypelist", examTypeDao.list());
		return"register/regist";
	}
	//(출제자) 시험 등록
	//examDao regist(examDto)시험 등록
	@PostMapping("/regist")
	public String regist(@ModelAttribute ExamDto examDto, HttpSession session, 
			@RequestParam String estart_hour, @RequestParam String estart_minute, 
			@RequestParam String efinish_hour, @RequestParam String efinish_minute) {
		int no = examDao.getNo();
		examDto.setNo(no);
		examDto.setEstart(examDto.getEstart()+estart_hour+estart_minute);
		examDto.setEfinish(examDto.getEfinish()+efinish_hour+estart_minute);
		examDto.setRegister((String)session.getAttribute("id"));
		examDao.insert(examDto);
		return "redirect:examinfo?no="+no;
	}
	
	//시험 편집 화면
	//examDao find(no); 시험 번호 이용하여 상세내용 불러오기
	@GetMapping("/edit")
	@Transactional
	public String edit(@RequestParam int exam_no, Model model) {
		model.addAttribute("examtypelist",examTypeDao.list());
		model.addAttribute("examDto", examDao.find(exam_no));
		return"register/edit";
	}
	//시험 정보 편집
	//examDao edit(examDto); 
	@PostMapping("/edit")
	public String edit(@ModelAttribute ExamDto examDto,
			@RequestParam String estart_hour, @RequestParam String estart_minute, @RequestParam String efinish_hour, @RequestParam String efinish_minute, @RequestParam int exam_no) {
		examDto.setNo(exam_no);
		examDto.setEstart(examDto.getEstart()+estart_hour+estart_minute);
		examDto.setEfinish(examDto.getEfinish()+efinish_hour+estart_minute);
		examDao.edit(examDto);
		return "redirect:examinfo?no="+examDto.getNo();
	}
	//시험 삭제
	//examDao drop(no);
	@RequestMapping("/drop")
	public String drop(@RequestParam int exam_no) {
		examDao.delete(exam_no);
		return "redirect:list";
	}
	//시험 상세 정보
	//examDao find(no)
	@RequestMapping("/examinfo")
	@Transactional
	public String examinfo(@RequestParam int no,  Model model) {
		model.addAttribute("exam", examDao.find(no));
		model.addAttribute("examinees", historyDao.examlist(no));
		model.addAttribute("quizlist", examQuizDao.examquizlist(no));
		model.addAttribute("isStart", examDao.isStart(no));
		log.debug("examinees = {}", historyDao.examlist(no));
		return "register/examinfo";
	}
	//시험 응시자 등록 화면
	//이미 진행중인 시험인 경우 수정 불가능하게 -  filter 처리
	@GetMapping("/examinee")
	public String examinee(Model model, @RequestParam int exam_no) {
		model.addAttribute("grouplist", memberGroupDao.list());
		return "register/examinee";
	}
	//시험 응시자 검색 처리
	//memberDao List<MemberDto> search(String type, String key);
	@PostMapping("/examinee")
	@Transactional
	public String examinee(
				@RequestParam Integer group_no, Model model, @RequestParam int exam_no) {
		model.addAttribute("grouplist", memberGroupDao.list());
		List<Map<String,String>>examinee = historyDao.examlist(exam_no);
		if(group_no != null) {
			List<MemberDto> list = memberDao.searchGroup(group_no);
			list = overlap(list, examinee);
			model.addAttribute("list", list);			
		}
		else {
			List<MemberDto> list = memberDao.list();
			list=overlap(list, examinee);
			model.addAttribute("list",list);
		}
		return "register/examinee";
	}
	//응시자 중복 추가 불가능하게 아예 목록에서 빼는 메소드
	private List<MemberDto> overlap(List<MemberDto>list, List<Map<String,String>> examinee){
		for(int i = 0 ; i < examinee.size();i ++) {
			Map<String, String> target = examinee.get(i);	//등록된 응시자 목록
			int member_no = Integer.parseInt(String.valueOf(target.get("MEMBER_NO")));
			for(int k = 0; k < list.size(); k++) {
				if(list.get(k).getNo() == member_no) {
					list.remove(k);
					break;
				}
			}
		}
		return list;
	}
	//응시자 등록 처리
	@RequestMapping("/registexaminee")
	@Transactional
	public String registExaminee(@ModelAttribute RegisterParamDto registerParamDto, @RequestParam int exam_no) {
		List<String> memberId = registerParamDto.getMemberid();
		List<Integer> groupNo = registerParamDto.getGroupno();
		if(memberId != null) {
			for(int i =0 ; i < memberId.size(); i++) {
				historyDao.insert(new HistoryDto().builder()
						.member_group(groupNo.get(i))
						.member_id(memberId.get(i))
						.exam_no(exam_no)
						.build());
			}
			//examDao를 이용 -> 시험 응시 인원을 설정해준다
			examDao.setTargetNum(exam_no, memberId.size());			
		}
		return "redirect:examinfo?no="+exam_no;
	}
	//응시자 삭제 처리
	//시험 시작 전에만 가능
	@RequestMapping("/dropexaminee")
	@Transactional
	public String dropExaminee(@RequestParam int history_no, @RequestParam int exam_no) {
		historyDao.delete(history_no);
		examDao.minusTargetNum(exam_no);
		return "redirect:examinfo?no="+exam_no;
	}
	//시험 문제 등록 페이지
	//진행중인 시험인 경우 문제 등록 못하게 하기
	@GetMapping("/quiz")
	public String quiz(@RequestParam int exam_no, Model model) {
		model.addAttribute("typelist", examTypeDao.list());//문제 분야 목록
		return "register/quiz";
	}
	//문제 검색 처리
	//중복 문제 처리 - 완료
	@PostMapping("/quiz")
	@Transactional
	public String quiz(Model model,
			@ModelAttribute QuizDto quizDto ,@RequestParam int exam_no) {
		List<Integer> quiznum = examQuizDao.quizlist(exam_no);
		List<QuizDto> list = overlapquiz(quizDao.search(quizDto), quiznum);
		model.addAttribute("typelist", examTypeDao.list());
		model.addAttribute("list", list);
		return "register/quiz";
	}
	//문제 중복 확인하는 메소드
	private List<QuizDto> overlapquiz(List<QuizDto> list, List<Integer> quiznum) {
		for(int i = 0 ; i < quiznum.size(); i++) {
			for(int k= 0; k < list.size(); k++) {
				if(list.get(k).getNo() == quiznum.get(i)) {
					list.remove(k);
					break;
				}
			}
		}
		return list;
	}
	//문제 등록 처리
	@RequestMapping("/registquiz")
	@Transactional
	public String registQuiz(@ModelAttribute RegisterParamDto registerParamDto, @RequestParam int exam_no) {
		for(int no: registerParamDto.getQuizno()) {
			examQuizDao.insert(new ExamQuizDto().builder()
												.exam_no(exam_no)
												.quiz_no(no)
											.build());
		}
		//시험 내용에 qnum 증가
		examDao.qnumplus(new ExamDto().builder()
						.no(exam_no)
						.qnum(registerParamDto.getQuizno().size())
				.build());
		return "redirect:examinfo?no="+exam_no;
	}
	//등록된 문제 삭제 처리
	@RequestMapping("/dropquiz")
	@Transactional
	public String dropquiz(@RequestParam int quiz_no, @RequestParam int exam_no) {
		ExamQuizDto examQuizDto = new ExamQuizDto(exam_no, quiz_no);
		examQuizDao.delete(examQuizDto);
		//시험 내용에 qnum 감소
		examDao.qnumminus(exam_no);
		return "redirect:examinfo?no="+exam_no;
	}
	//문제 목록
	//quizDao list();
	@GetMapping("/q_list")
	@Transactional
	public String q_list(Model model) {
		model.addAttribute("typelist", examTypeDao.list());
		model.addAttribute("q_list", quizDao.list());
		return "register/q_list";
	}
	//문제목록에서 검색
	//quizDao search(key);
	@PostMapping("/q_list")
	@Transactional
	public String q_list(Model model, @RequestParam String key, @RequestParam int type) {
		List<QuizDto> list = null;
		if(type == -1) {
			list = quizDao.search(key);
		}
		else {
			list = quizDao.search(type, key);
		}
		model.addAttribute("typelist",examTypeDao.list());
		model.addAttribute("q_list", list);			
		return "register/q_list";
	}
	//문제등록 화면
	@GetMapping("/q_regist")
	public String q_regist(Model model) {
		model.addAttribute("typelist",examTypeDao.list());
		return"register/q_regist";
	}
	//문제 등록 처리
	//quizDao regist(quizDto);
	@PostMapping("/q_regist")
	@Transactional
	public String q_regist(@ModelAttribute QuizDto quizDto, HttpSession session , @ModelAttribute RegisterParamDto registerParamDto) {
		log.debug("samples = {}",registerParamDto.getSamples());
		log.debug("quizDto = {}", quizDto);
		int no = quizDao.getNo();
		quizDto.setNo(no);
		quizDao.insert(quizDto);
//		quizDto.setRegister((String)session.getAttribute("id"));
		List<String> samples = registerParamDto.getSamples();
		//문제 보기 등록
		if(samples != null) {
			for(int i=0; i < samples.size(); i++) {
				sampleDao.insert(new SampleDto().builder()
														.quiz_no(no)
														.no(i+1)
														.content(samples.get(i))
												.build());
			}
		}
//		sampleDao.insert();
		return"redirect:q_info?no="+no;
	}
	//문제 삭제 처리
	//quizDao drop(no);
	@RequestMapping("/q_drop")
	public String q_drop(@RequestParam int no) {
		quizDao.delete(no);
		return "redirect:q_list";
	}
	//문제 수정 화면
	//quizDao find(no);
	@GetMapping("/q_edit")
	@Transactional
	public String q_edit(Model model, @RequestParam int no) {
		model.addAttribute("quiz", quizDao.find(no));
		model.addAttribute("typelist", examTypeDao.list());
		model.addAttribute("sample", sampleDao.list(no));
		return"register/q_edit";
	}
	//문제 수정 처리
	//quizDao edit(quizDto);
	@PostMapping("/q_edit")
	@Transactional
	public String q_edit(@RequestParam int no, @ModelAttribute QuizDto quizDto, @ModelAttribute RegisterParamDto registerParamDto) {
		log.debug("---------------- q_edit no = {}", no);
		log.debug("---------------- QuizDto = {}", quizDto);
		quizDao.edit(quizDto);
		List<String> samples = registerParamDto.getSamples();
		if(samples != null) {
			sampleDao.delete(quizDto.getNo());
			for(int i = 0 ; i < samples.size(); i++) {
				sampleDao.insert(new SampleDto().builder()
												.quiz_no(quizDto.getNo())
												.no(i+1)
												.content(samples.get(i))
										.build());
			}
		}
		return "redirect:q_info?no="+quizDto.getNo();
	}
	//문제 상세 정보
	//quizDao find(no)
	@Transactional
	@RequestMapping("/q_info")
	public String q_info(Model model, @RequestParam int no) {
		model.addAttribute("qinfo", quizDao.find(no));
		log.debug("qinfo = {}", quizDao.find(no));
		model.addAttribute("isUsed",examQuizDao.isUsed(no));
		model.addAttribute("samplelist", sampleDao.list(no));
		return "register/q_info";
	}
	
	//채점 화면 
	//exam_quiz_answersheetDao
	@GetMapping("/scoring")
	public String scoring(@RequestParam int exam_no, int member_no, Model model) {
		model.addAttribute("list", exam_Quiz_AnswerSheetDao.list(exam_no, member_no));
		return "register/scoring";
	}
	
	//채점 결과 처리 후 
	//historyDao scoring(exam_no, no(history.no), score); 해당 응시자의 응시 기록에 점수 
	//통과/미통과 dao 추가
	@PostMapping("/scoring")
	@Transactional
	public String scoring(
			@RequestParam int exam_no, @RequestParam int no, @RequestParam String score) {
		log.debug("exam_no={}",exam_no);
		log.debug("no={}",no);
		int scr = Integer.parseInt(score);
		//시험 합격 점수 cut을 불러와서 합격인지 여부 판정하여 저장
		int pass = examDao.getPassScore(exam_no);
		String result;
		if(scr >= pass) {
			result="통과";
		}
		else {
			result="미통과";
		}
		HistoryDto historyDto = new HistoryDto().builder()
				.exam_no(exam_no)
				.no(no)
				.result(result)
				.score(scr)
				.build();
		historyDao.scoring(historyDto);
		return "redirect:examinfo?no="+exam_no;
	}
	
	@RequestMapping("/savescore")
	public void savescore(@RequestParam int no, int userscore) {
		log.debug("------------param : no = {}, userscore = {}", no , userscore);
		answerSheetDao.savescore(no, userscore);
	}

}
