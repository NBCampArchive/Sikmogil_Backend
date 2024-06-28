package com.examle.sikmogilbackend.global.util;

import com.examle.sikmogilbackend.community.board.domain.Board;
import com.examle.sikmogilbackend.community.board.domain.repository.BoardRepository;
import com.examle.sikmogilbackend.community.board.exception.BoardNotFoundException;
import com.examle.sikmogilbackend.member.domain.Member;
import com.examle.sikmogilbackend.member.domain.repository.MemberRepository;
import com.examle.sikmogilbackend.member.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GlobalUtil {
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    public Member getMemberByEmail(String email) {
        return memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
    }

    public Board getBoardById(Long boardId) {
        return boardRepository.findById(boardId).orElseThrow(BoardNotFoundException::new);
    }

}
