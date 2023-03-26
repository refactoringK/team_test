package com.example.app.board.vo;

import java.util.List;

import com.example.app.file.dto.FileDTO;

//m.member_number, 
//m.member_id, 
//b.board_number , 
//b.board_title , 
//b.board_content , 
//b.board_date , 
//b.board_read_count

public class BoardVO {
	private int memberNumber;
	private String memberId;
	private int boardNumber;
	private String boardTitle;
	private String boardContent;
	private String boardDate;
	private int boardReadCount;
	private List<FileDTO> files;
	
	public BoardVO() {}

	public int getMemberNumber() {
		return memberNumber;
	}

	public void setMemberNumber(int memberNumber) {
		this.memberNumber = memberNumber;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public int getBoardNumber() {
		return boardNumber;
	}

	public void setBoardNumber(int boardNumber) {
		this.boardNumber = boardNumber;
	}

	public String getBoardTitle() {
		return boardTitle;
	}

	public void setBoardTitle(String boardTitle) {
		this.boardTitle = boardTitle;
	}

	public String getBoardContent() {
		return boardContent;
	}

	public void setBoardContent(String boardContent) {
		this.boardContent = boardContent;
	}

	public String getBoardDate() {
		return boardDate;
	}

	public void setBoardDate(String boardDate) {
		this.boardDate = boardDate;
	}

	public int getBoardReadCount() {
		return boardReadCount;
	}

	public void setBoardReadCount(int boardReadCount) {
		this.boardReadCount = boardReadCount;
	}
	
	public List<FileDTO> getFiles() {
		return files;
	}

	public void setFiles(List<FileDTO> files) {
		this.files = files;
	}

	@Override
	public String toString() {
		return "BoardVO [memberNumber=" + memberNumber + ", memberId=" + memberId + ", boardNumber=" + boardNumber
				+ ", boardTitle=" + boardTitle + ", boardContent=" + boardContent + ", boardDate=" + boardDate
				+ ", boardReadCount=" + boardReadCount + "]";
	}
}
