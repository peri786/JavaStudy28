/**
 * 
 */
/**
 * 
 */
module studyJava {
	// 자바 9버전 부터 도입된 방식. 
	// 외부 라이브러리(ojdbc6.jar)를 마음대로 가져다 쓰지 못하게 차단
	requires java.sql;
	requires java.desktop;
}