

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
