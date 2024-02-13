package com.ssafy.forest.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AlarmArgs {

    // 알람을 발생시킨 사람
    private long fromMemberId;

    // articleId
    private long targetId;

}
