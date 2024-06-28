package com.examle.sikmogilbackend.community.board.application;

import com.examle.sikmogilbackend.community.board.api.dto.request.BoardSaveReqDto;
import com.examle.sikmogilbackend.community.board.api.dto.request.BoardUpdateReqDto;
import com.examle.sikmogilbackend.community.board.api.dto.response.BoardInfoResDto;
import com.examle.sikmogilbackend.community.board.api.dto.response.BoardListResDto;
import com.examle.sikmogilbackend.community.board.domain.Board;
import com.examle.sikmogilbackend.community.board.domain.BoardPicture;
import com.examle.sikmogilbackend.community.board.domain.repository.BoardLikeRepository;
import com.examle.sikmogilbackend.community.board.domain.repository.BoardPictureRepository;
import com.examle.sikmogilbackend.community.board.domain.repository.BoardRepository;
import com.examle.sikmogilbackend.community.board.exception.NotBoardOwnerException;
import com.examle.sikmogilbackend.global.util.GlobalUtil;
import com.examle.sikmogilbackend.member.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {
    private final GlobalUtil globalUtil;
    private final BoardRepository boardRepository;
    private final BoardPictureRepository boardPictureRepository;
    private final BoardLikeRepository boardLikeRepository;

    @Transactional
    public Long boardSave(String email, BoardSaveReqDto boardSaveReqDto) {
        Member member = globalUtil.getMemberByEmail(email);
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
        Member member = globalUtil.getMemberByEmail(email);

        Page<BoardInfoResDto> byBoards;
        if (category.equals("ALL")) {
            byBoards = boardRepository.findByBoardAll(member, pageable);
        } else {
            byBoards = boardRepository.findByCategoryWithBoard(member, category, pageable);
        }

        // 좋아요, 댓글 Count 추가하기
        return BoardListResDto.from(byBoards);
    }

    // 게시글 상세 조회
    public BoardInfoResDto boardDetail(String email, Long boardId) {
        Member member = globalUtil.getMemberByEmail(email);
        Board board = globalUtil.getBoardById(boardId);

        boolean isLike = boardLikeRepository.existsByBoardAndMember(board, member);

        Board commentAndBoardInfo = boardRepository.findByDetailBoard(board);

        return BoardInfoResDto.detailOf(member, commentAndBoardInfo, isLike);
    }

    // 게시글 삭제
    @Transactional
    public void boardDelete(String email, Long boardId) {
        Member member = globalUtil.getMemberByEmail(email);
        Board board = globalUtil.getBoardById(boardId);

        checkBoardOwnership(member, board);

        boardPictureRepository.deleteByBoardBoardId(boardId);
        boardRepository.delete(board);
    }

    // 게시글 수정
    @Transactional
    public BoardInfoResDto boardUpdate(String email, Long boardId, BoardUpdateReqDto boardUpdateReqDto) {
        Member member = globalUtil.getMemberByEmail(email);
        Board board = globalUtil.getBoardById(boardId);

        checkBoardOwnership(member, board);
        board.boardUpdate(boardUpdateReqDto);

        // 새로운 이미지 url만 받아서 추가한다.
        for (String url : boardUpdateReqDto.newImageUrl()) {
            boardPictureRepository.save(BoardPicture.builder()
                    .board(board)
                    .imageUrl(url)
                    .build());
        }

        boolean isLike = boardLikeRepository.existsByBoardAndMember(board, member);

        return BoardInfoResDto.detailOf(member, board, isLike);
    }

    private void checkBoardOwnership(Member member, Board board) {
        if (!member.getMemberId().equals(board.getWriter().getMemberId())) {
            throw new NotBoardOwnerException();
        }
    }

}
