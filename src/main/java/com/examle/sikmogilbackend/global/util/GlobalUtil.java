package com.examle.sikmogilbackend.global.util;

import com.examle.sikmogilbackend.challenge.domain.Challenge;
import com.examle.sikmogilbackend.challenge.domain.repository.ChallengeRepository;
import com.examle.sikmogilbackend.challenge.exception.ChallengeNotFoundException;
import com.examle.sikmogilbackend.community.board.domain.Board;
import com.examle.sikmogilbackend.community.board.domain.repository.BoardRepository;
import com.examle.sikmogilbackend.community.board.exception.BoardNotFoundException;
import com.examle.sikmogilbackend.community.comment.domain.Comment;
import com.examle.sikmogilbackend.community.comment.domain.repository.CommentRepository;
import com.examle.sikmogilbackend.community.comment.exception.CommentNotFoundException;
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
    private final CommentRepository commentRepository;
    private final ChallengeRepository challengeRepository;

    public Member getMemberByEmail(String email) {
        return memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
    }

    public Board getBoardById(Long boardId) {
        return boardRepository.findById(boardId).orElseThrow(BoardNotFoundException::new);
    }

    public Comment getCommentById(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);
    }

    public Challenge getChallengeById(Long challengeId) {
        return challengeRepository.findById(challengeId).orElseThrow(ChallengeNotFoundException::new);
    }

}
