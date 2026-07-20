package jdbcScore.view;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MainFrame extends JFrame {

	// JFrame 이란 : 윈도우 창
	
	// 패널 배치에 사용할 변수
	private JPanel northPanel;
	private JPanel centerPanel;
	private JPanel southPanel;
	
	// north 패널에 배치할 물건 변수들 => 입력창
	private JTextField tfIdx;
	private JTextField tfName;
	private JTextField tfKor;
	private JTextField tfEng;
	private JTextField tfMat;
	
	// south 패널에 배치할 물건 변수들 => 버튼
	private JButton btnInsert;
	private JButton btnSearch;
	private JButton btnUpdate;
	private JButton btnDelete;
	
	
	public MainFrame() {
		
		initUI();
	
	}
	
	private void initUI() {
		
		setTitle("성적관리 프로그램"); // 창의 제목
		setSize(700, 500); // 가로, 세로 크기
		setLocationRelativeTo(null); // 화면 가운데
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		createPanel();
		createComponent();
		addComponent();
		event();
		setVisible(true); // 창 보이게 하기
	
	}
	
	public void createPanel() {
		northPanel = new JPanel(); // 영역 나누기, 즉 패널 객체를 생성한다
		centerPanel = new JPanel();
		southPanel = new JPanel();
		
		add(northPanel, BorderLayout.NORTH); // 패널 객체 배치. 북쪽 영역에 northPanel을 배치해라
		add(centerPanel, BorderLayout.CENTER);
		add(southPanel, BorderLayout.SOUTH);
		
	}
	
	public void createComponent() {
		// 텍스트 필드 객체 생성
		tfIdx = new JTextField(5);
		tfName = new JTextField(10);
		tfKor = new JTextField(5);
		tfEng = new JTextField(5);
		tfMat = new JTextField(5);
		
		// 버튼 객체 생성
		btnInsert = new JButton("등록");
		btnSearch = new JButton("조회");
		btnUpdate = new JButton("수정");
		btnDelete = new JButton("삭제");
	}
	
	public void addComponent() {
		
		northPanel.add(new JLabel("번호")); // 번호라는 글자를 가진 객체를 north패널 영역에 넣어라
		northPanel.add(tfIdx);
		
		northPanel.add(new JLabel("이름"));
		northPanel.add(tfName);
		
		northPanel.add(new JLabel("국어"));
		northPanel.add(tfKor);
		
		northPanel.add(new JLabel("영어"));
		northPanel.add(tfEng);
		
		northPanel.add(new JLabel("수학"));
		northPanel.add(tfMat);
		
		// 버튼 배치
		southPanel.add(btnInsert);
		southPanel.add(btnSearch);
		southPanel.add(btnUpdate);
		southPanel.add(btnDelete);
		
	}
	
	public void event() {
		
	}

}
