package com.example.app.board;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.app.Execute;
import com.example.app.board.dao.BoardDAO;
import com.example.app.board.dto.BoardDTO;
import com.example.app.file.dao.FileDAO;
import com.example.app.file.dto.FileDTO;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.oreilly.servlet.multipart.FilePart;
import com.oreilly.servlet.multipart.MultipartParser;
import com.oreilly.servlet.multipart.ParamPart;
import com.oreilly.servlet.multipart.Part;

public class BoardUpdateOkController implements Execute {
	@Override
	public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BoardDAO boardDAO = new BoardDAO();
		BoardDTO boardDTO = new BoardDTO();
		FileDAO fileDAO = new FileDAO(); // +++++++++
		FileDTO fileDTO = new FileDTO(); // +++++++++

		final String UPLOAD_PATH = req.getSession().getServletContext().getRealPath("/") + "upload/";
		final int FILE_SIZE = 1024 * 1024 * 5; // 5MB
		final String UPLOAD_PATH2 = "/Users/thirdk/work_data/new-class/jsp/workspace/finalProject/WebContent/upload/";
		
		int boardNumber = 0;
		
		
		// 하나의 input으로 여러 첨부파일을 보낸경우 아래와 같이 처리
		MultipartParser parser = new MultipartParser(req, FILE_SIZE);
		parser.setEncoding("utf-8");
		while (true) {
//			MultipartParser객체는 여러 폼데이터를 part객체로 분리할 수 있다.
			Part part = parser.readNextPart();
			if (part == null) {
				break;
			}
			String fileSystemName = null;
			String fileOriginalName = null;

//			part객체가 파일인 경우
			if (part.isFile()) {
				FilePart filePart = (FilePart) part;
				filePart.setRenamePolicy(new DefaultFileRenamePolicy());
				fileOriginalName = filePart.getFileName();

//				System.out.println(filePart.getFilePath());
				System.out.println(fileOriginalName);
				if (fileOriginalName != null) {
//					파일을 저장하기 위한 정보를 가진 객체를 만든다
					File file = new File(UPLOAD_PATH, fileOriginalName);
					// 파일을 저장한다.
					filePart.writeTo(file);
//					저장 후 파일 이름을 다시 뽑으면 저장될 때의 이름이 나온다.
					fileSystemName = filePart.getFileName();

					fileDTO.setFileSystemName(fileSystemName);
					fileDTO.setFileOriginalName(fileOriginalName);
					fileDTO.setBoardNumber(boardNumber);
					fileDAO.insert(fileDTO);
				}

			} else {
//				part객체가 파일이 아닌 일반 파라미터인 경우
				ParamPart paramPart = (ParamPart) part;
				String param = paramPart.getName(); // 파라미터명
				String value = paramPart.getStringValue(); // 값
				System.out.println(param + " : " + value);

				if (param.equals("boardTitle")) {
					boardDTO.setBoardTitle(value);
				} else if (param.equals("boardContent")) {
					boardDTO.setBoardContent(value);
				} else if (param.equals("boardNumber")) { //++++++++++++
					boardNumber = Integer.valueOf(value);
					boardDTO.setBoardNumber(boardNumber);
				}

				if (boardDTO.getBoardTitle() == null || boardDTO.getBoardContent() == null) {
					continue;
				}
				
				boardDTO.setMemberNumber((Integer) req.getSession().getAttribute("memberNumber"));
				boardDAO.update(boardDTO); //**************
//				게시글 번호 가져오기
//				boardNumber = boardDAO.getSequence(); ------
				
				// ============ BoardDeleteOkController에서 코드 가져오기==============
				List<FileDTO> files = fileDAO.select(boardNumber);
//				스트림과 람다식
				files.stream().map(file -> file.getFileSystemName())
				.map(name -> new File(UPLOAD_PATH, name))
				.filter(tmp -> tmp.exists())
				.forEach(tmp -> tmp.delete());
				
				
				fileDAO.delete(boardNumber);
				// ==================================
			}
		}

		resp.sendRedirect("/board/boardListOk.bo");

	}
}
