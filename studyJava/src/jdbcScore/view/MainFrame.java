package jdbcScore.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import jdbcScore.ScoreDAO;
import jdbcScore.ScoreDTO;

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
	
	private ScoreDAO dao;
	
	private JTable table;
	private DefaultTableModel model; // 테이블의 데이터를 관리하는 객체
	private JScrollPane scrollPane; // 스크롤
	
	
	public MainFrame() {
		
		initUI();
	
	}
	
	private void initUI() {
		
		setTitle("성적관리 프로그램"); // 창의 제목
		setSize(700, 500); // 가로, 세로 크기
		setLocationRelativeTo(null); // 화면 가운데
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		createPanel(); // 패널생성 - 영역 나누기
		createComponent(); // 배치할 물건 객체 생성
		addComponent(); // 물건 배치 - 텍스트박스, 버튼 등
		event();
		loadTable();
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
		
		dao = new ScoreDAO();
		
		String[] columnNames = {
				"학번",
				"이름",
				"국어",
				"영어",
				"수학",
				"총점",
				"평균",
				"학점"
		};
		
		model = new DefaultTableModel(columnNames, 0); // 데이터를 저장하는 공간
		table = new JTable(model); // 모델(데이터)를 화면에 보여준다
		scrollPane = new JScrollPane(table); // 데이터가 많아지면 스크롤을 생성한다
	}
	
	public void addComponent() {
		
		northPanel.add(new JLabel("번호")); // 번호라는 글자를 가진 객체를 north패널 영역에 넣어라
		northPanel.add(tfIdx);
		tfIdx.setText(String.valueOf(dao.countIdx()));
		// setText()는 String만 받을 수 있다
		
		northPanel.add(new JLabel("이름"));
		northPanel.add(tfName);
		
		northPanel.add(new JLabel("국어"));
		northPanel.add(tfKor);
		
		northPanel.add(new JLabel("영어"));
		northPanel.add(tfEng);
		
		northPanel.add(new JLabel("수학"));
		northPanel.add(tfMat);
		
		// 센터
		centerPanel.setLayout(new BorderLayout());
		centerPanel.add(scrollPane);
		
		
		// 버튼 배치
		southPanel.add(btnInsert);
		southPanel.add(btnSearch);
		southPanel.add(btnUpdate);
		southPanel.add(btnDelete);
		
	}
	
	public void event() {
		btnInsert.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("등록 버튼 클릭 됐어요!");
				ScoreDTO dto = getInputData();
				boolean result = dao.setInsert(dto);
				if(result) {
					JOptionPane.showMessageDialog(null, "저장되었습니다");
					clearInput();
					loadTable();
				} else {
					JOptionPane.showInternalMessageDialog(null, "저장실패");
				}
			}
		});
		
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// 클릭한 행 번호 얻기
				int row = table.getSelectedRow();
				// 번호
				tfIdx.setText(table.getValueAt(row, 0).toString());
				// 이름
				tfName.setText(table.getValueAt(row, 1).toString());
				// 국어
				tfKor.setText(table.getValueAt(row, 2).toString());
				// 영어
				tfEng.setText(table.getValueAt(row, 3).toString());
				// 수학
				tfMat.setText(table.getValueAt(row, 4).toString());
			}
		});
		
		btnUpdate.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ScoreDTO dto = getInputData();
				boolean result = dao.getUpdate(dto);
				if(result) {
					JOptionPane.showMessageDialog(null, "저장되었습니다");
					clearInput();
					loadTable();
				} else {
					JOptionPane.showInternalMessageDialog(null, "저장실패");
				}
			}
		});
	}
	
	private ScoreDTO getInputData() {
		int nextIdx = dao.countIdx();
		int idx = Integer.parseInt(tfIdx.getText());
		String name = tfName.getText();
		int kor = Integer.parseInt(tfKor.getText());
		int eng = Integer.parseInt(tfEng.getText());
		int mat = Integer.parseInt(tfMat.getText());
		
		ScoreDTO dto = new ScoreDTO();
		
		dto.setIdx(idx);
		dto.setName(name);
		dto.setKor(kor);
		dto.setEng(eng);
		dto.setMat(mat);
		
		return dto;
	}
	
	private void loadTable() {
		model.setRowCount(0); // 기존 데이터 삭제
		List<ScoreDTO> list = dao.getOracle();
		for (ScoreDTO dto : list) {
			
			model.addRow(new Object[] {
					dto.getIdx(),
					dto.getName(),
					dto.getKor(),
					dto.getEng(),
					dto.getMat(),
					dto.getTot(),
					dto.getAve(),
					dto.getGrade()
			});
			
		}
	}
	
	public void clearInput() {
		tfName.setText("");
		tfKor.setText("");
		tfEng.setText("");
		tfMat.setText("");
		tfIdx.setText(String.valueOf(dao.countIdx()));
		tfName.requestFocus();
	}

}
