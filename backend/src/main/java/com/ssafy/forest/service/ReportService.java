package com.ssafy.forest.service;

import com.ssafy.forest.domain.entity.*;
import com.ssafy.forest.exception.CustomException;
import com.ssafy.forest.exception.ErrorCode;
import com.ssafy.forest.repository.*;
import com.ssafy.forest.security.TokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ReportService {

    private final ArticleRepository articleRepository;
    private final ArticleReportRepository articleReportRepository;
    private final ArticleCommentRepository articleCommentRepository;
    private final CommentReportRepository commentReportRepository;
    private final ArticleCommentReplyRepository articleCommentReplyRepository;
    private final ReplyReportRepository replyReportRepository;
    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;

    //게시글 신고하기
    public void reportArticle(HttpServletRequest request, Long articleId) {
        Member member = getMemberFromAccessToken(request);

        Article article = articleRepository.findById(articleId)
            .orElseThrow(() -> new CustomException(ErrorCode.INVALID_RESOURCE));

        if (articleReportRepository.existsByArticleIdAndMemberId(articleId, member.getId())) {
            throw new CustomException(ErrorCode.DUPLICATED_ARTICLE_REPORT);
        } else {
            articleReportRepository.save(ArticleReport.from(member, article));
        }
    }

    //댓글 신고하기
    public void reportComment(HttpServletRequest request, Long commentId) {
        Member member = getMemberFromAccessToken(request);

        ArticleComment articleComment = articleCommentRepository.findById(commentId)
            .orElseThrow(() -> new CustomException(ErrorCode.INVALID_RESOURCE));

        if (commentReportRepository.existsByArticleCommentIdAndMemberId(commentId,
            member.getId())) {
            throw new CustomException(ErrorCode.DUPLICATED_COMMENT_REPORT);
        } else {
            commentReportRepository.save(CommentReport.from(member, articleComment));
        }
    }

    //대댓글 신고하기
    public void reportReply(HttpServletRequest request, Long replyId) {
        Member member = getMemberFromAccessToken(request);

        ArticleCommentReply articleCommentReply = articleCommentReplyRepository.findById(replyId)
            .orElseThrow(() -> new CustomException(ErrorCode.INVALID_RESOURCE));

        if (replyReportRepository.existsByArticleCommentReplyIdAndMemberId(replyId,
            member.getId())) {
            throw new CustomException(ErrorCode.DUPLICATED_REPLY_REPORT);
        } else {
            replyReportRepository.save(ReplyReport.from(member, articleCommentReply));
        }
    }


    //유저 정보 추출
    public Member getMemberFromAccessToken(HttpServletRequest request) {
        // accessToken으로부터 Member 객체 추출
        Member memberFromAccessToken = tokenProvider.getMemberFromAccessToken(request);
        // memberFromAccessToken의 id로 최신 상태의 Member 객체 조회
        return memberRepository.findById(memberFromAccessToken.getId())
            .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_MEMBER));
    }

}
