

/*
 * @Author pangjie
 * @Description //TODO 获取前beforeDays天数的日期
 * @Date 17:00 21.8.23
 * @Param 
 * @return 
 */
function getBeforeDay(beforeDays) {
    let data = [];
    for (let i = beforeDays; i > -1; i--) {
        const end = new Date();
        const start = new Date();
        start.setDate(start.getDate() - i);   //day和month会自动计算到上一个月的
        const startYear = start.getFullYear();
        const startMonth = start.getMonth() + 1 >= 10 ? start.getMonth() + 1 : "" + (start.getMonth() + 1);
        const startDate = start.getDate() >= 10 ? start.getDate() : "" + start.getDate();
        const endYear = end.getFullYear();
        const endtMonth = end.getMonth() + 1 >= 10 ? end.getMonth() + 1 : "" + (end.getMonth() + 1);
        const endDate = end.getDate() >= 10 ? end.getDate() : "" + end.getDate();
        const startDate1 = startMonth + "-" + startDate + '  ';
        const endDate1 = endtMonth + "-" + endDate;
        data.push(startDate1)
    }
    return data;
}

/**
 * @Author PangJie___
 * @Description  计算传秒数的倒计时【天、时、分、秒】
 * @Date 10:16 2023/5/26
 * @param seconds
 * @return {{day : *, hours : *, minutes : *, seconds : *}}
 */
const countTimeDown = seconds => {
    const leftTime = time => {
        if (time < 10) time = "0" + time;
        return time + "";
    };
    return {
        day: leftTime(parseInt(seconds / 60 / 60 / 24, 10)),
        hours: leftTime(parseInt((seconds / 60 / 60) % 24, 10)),
        minutes: leftTime(parseInt((seconds / 60) % 60, 10)),
        seconds: leftTime(parseInt(seconds % 60, 10))
    };
};

/**
 * 计算当前时间到第二天0点的倒计时[秒]
 * @returns {number}
 */
const theNextDayTime = () => {
    const nowDate = new Date();
    const time =
        new Date(
            nowDate.getFullYear(),
            nowDate.getMonth(),
            nowDate.getDate() + 1,
            0,
            0,
            0
        ).getTime() - nowDate.getTime();
    return parseInt(time / 1000);
};