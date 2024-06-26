package com.examle.sikmogilbackend.community.board.application;

import com.examle.sikmogilbackend.community.board.api.dto.request.BoardSaveReqDto;
import com.examle.sikmogilbackend.community.board.api.dto.response.BoardInfoResDto;
import com.examle.sikmogilbackend.community.board.api.dto.response.BoardListResDto;
import com.examle.sikmogilbackend.community.board.domain.Board;
import com.examle.sikmogilbackend.community.board.domain.BoardPicture;
import com.examle.sikmogilbackend.community.board.domain.repository.BoardPictureRepository;
import com.examle.sikmogilbackend.community.board.domain.repository.BoardRepository;
import com.examle.sikmogilbackend.member.domain.Member;
import com.examle.sikmogilbackend.member.domain.repository.MemberRepository;
import com.examle.sikmogilbackend.member.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final BoardPictureRepository boardPictureRepository;

    @Transactional
    public Long boardSave(String email, BoardSaveReqDto boardSaveReqDto) {
        Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
        Board board = boardSaveReqDto.toEntity(member);

        boardImageSave(board, boardSaveReqDto);
        Board saveBoard = boardRepository.save(board);

        return saveBoard.getBoardId();
    }

    private void boardImageSave(Board board, BoardSaveReqDto boardSaveReqDto) {
        for (String imageUrl : boardSaveReqDto.imageUrl()) {
            boardPictureRepository.save(BoardPicture.builder()
                    .board(board)
                    .imageUrl(imageUrl)
                    .build());
        }
    }

    // 게시글 조회 (전체, 다이어트, 운동, 자유)
    public BoardListResDto categoryByBoardAll(String email, String category, Pageable pageable) {
        Member member = memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);

        Page<BoardInfoResDto> byBoards;
        if (category.equals("ALL")) {
            byBoards = boardRepository.findByBoardAll(pageable);
        } else {
            byBoards = boardRepository.findByCategoryWithBoard(category, pageable);
        }

        // 좋아요, 댓글 Count 추가하기
        return BoardListResDto.from(byBoards);
    }

    // 게시글 상세 조회

    // 게시글 삭제

    // 게시글 수정

}
