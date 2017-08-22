function currentPage() {
    $.ajax({
        url: '/lms/personalCurrentBorrowedPage.do',
        type: 'GET',
        dataType: 'json',
        contentType:'application/json;charset=UTF-8',
        success:function(msg) {
            $("#currentBorrowed").html("");
            $("#currentReservation").html("");
            $("#currentPenalty").html("");
            if(msg.rbsize == 0) {
                $("#currentBorrowedAll").html("");
                var str = "当前没有任何借阅记录！";
                $("#currentBorrowedAll").append(str);
            } else{
                $.each(msg.readerBorrowList, function(i, item) {
                    var colume1 = i+1;
                    var colume2 = item.barCode;
                    var colume3 = item.infoBook.title;
                    var colume4 = item.dateBorrow;
                    var colume5 = item.dateReturn;
                    var colume6 = item.reborrowCount;
                    var colume7 = "";
                    if(item.infoCollection.isReservation == 0 && item.reborrowCount < msg.reBorrowMax){
                        colume7 = "<a href=\"javascript:void(0)\" onclick=\"rreborrowdo('"+item.id+"')\">续借</a>"
                    }
                    var str1 = "<tr><td>"+colume1+"</td><td>"+colume2+"</td><td>"+colume3+"</td><td>"+colume4+"</td><td>"+colume5+"</td><td>"+colume6+"</td><td>"+colume7+"</td></tr>";
                    $("#currentBorrowed").append(str1);
                });
            }
            if(msg.rrsize == 0) {
                $("#currentReservationAll").html("");
                var str = "当前没有任何预约记录！";
                $("#currentReservationAll").append(str);
            } else {
                $.each(msg.readerReservationList, function(i, item) {
                    var colume1 = i+1;
                    var colume2 = item.barCode;
                    var colume3 = item.infoBook.title;
                    var colume4 = item.dateReservation;
                    var colume5 = item.dateBack;
                    var colume6 = item.dateEffective;
                    var colume7 = "<a href=\"javascript:void(0)\" onclick=\"rcancelReservationdo('"+item.id+"')\">取消预约</a>";
                    var str1 = "<tr><td>"+colume1+"</td><td>"+colume2+"</td><td>"+colume3+"</td><td>"+colume4+"</td><td>"+colume5+"</td><td>"+colume6+"</td><td>"+colume7+"</td></tr>";
                    $("#currentReservation").append(str1);
                });
            }
            if(msg.ipsize == 0) {
                $("#currentPenaltyAll").html("");
                var str = "当前没有任何惩罚记录！";
                $("#currentPenaltyAll").append(str);
            } else {
                $.each(msg.infoPenaltyList, function(i, item) {
                    var colume1 = i+1;
                    var colume2 = item.rbid;
                    var colume3 = item.readerBorrow.barCode;
                    var colume4 = item.infoBook.title;
                    var colume5 = item.readerBorrow.penaltyFlag;
                    var colume6 = item.datePenalty;
                    var colume7 = item.pricePenalty;
                    var colume8 = "否";
                    var str1 = "<tr><td>"+colume1+"</td><td>"+colume2+"</td><td>"+colume3+"</td><td>"+colume4+"</td><td>"+colume5+"</td><td>"+colume6+"</td><td>"+colume7+"</td><td>"+colume8+"</td></tr>";
                    $("#currentPenalty").append(str1);
                });
            }
        },
        error:function() {
            alert("personalCurrentBorrowedPage.do执行失败");
        }
    });
}
function historyPage(){
    $.ajax({
        url: '/lms/personalHistoryPage.do',
        type: 'GET',
        dataType: 'json',
        contentType:'application/json;charset=UTF-8',
        success:function(msg) {
            $("#historyBorrow").html("");
            $("#historyReservation").html("");
            $("#historyPenalty").html("");
            if(msg.rbsize == 0) {
                $("#historyBorrowAll").html("");
                var str = "没有任何历史借阅记录！";
                $("#historyBorrowAll").append(str);
            } else {
                $.each(msg.readerBorrowList, function(i, item) {
                    var colume1 = item.barCode;
                    var colume2 = item.infoBook.title;
                    var colume3 = item.dateBorrow;
                    var colume4 = item.dateReturn;
                    var colume5 = item.dateBack;
                    var colume6 = item.penaltyFlag;
                    var colume7 = item.reborrowCount;
                    var flag = "正常";
                    if(colume6 == 2) {
                        flag = "超期";
                    } else if(colume6 == 3) {
                        flag = "损坏";
                    } else if(colume6 == 4) {
                        flag = "遗失";
                    }
                    var str1 = "<tr class=\"gradeA\"><td>"+colume1+"</td><td>"+colume2+"</td><td>"+colume3+"</td><td>"+colume4+"</td><td>"+colume5+"</td><td>"+flag+"</td><td>"+colume7+"</td></tr>";
                    $("#historyBorrow").append(str1);
                     /* iterate through array or object */
                });
                $("#historyBorrowTable").DataTable();
            }
            if(msg.rrsize == 0) {
                $("#historyReservationAll").html("");
                var str = "没有任何历史预约记录！";
                $("#historyReservationAll").append(str);
            } else {
                $.each(msg.readerReservationList, function(i, item) {
                    var colume1 = item.barCode;
                    var colume2 = item.infoBook.title;
                    var colume3 = item.dateReservation;
                    var colume4 = item.dateBack;
                    var colume5 = item.dateEffective;
                    var colume6 = item.isEffective;
                    var str1 = "<tr class=\"gradeA\"><td>"+colume1+"</td><td>"+colume2+"</td><td>"+colume3+"</td><td>"+colume4+"</td><td>"+colume5+"</td><td>"+colume6+"</td></tr>";
                    $("#historyReservation").append(str1);
                     /* iterate through array or object */
                });
                $("#historyReservationTable").DataTable();
            }
            if(msg.ipsize == 0) {
                $("#historyPenaltyAll").html("");
                var str = "没有任何历史惩罚记录！";
                $("#historyPenaltyAll").append(str);
            } else {
                $.each(msg.infoPenaltyList, function(i, item) {
                    var colume1 = item.rbid;
                    var colume2 = item.readerBorrow.barCode;
                    var colume3 = item.infoBook.title;
                    var colume4 = item.readerBorrow.penaltyFlag;
                    var colume5 = item.datePenalty;
                    var colume6 = item.pricePenalty;
                    var colume7 = item.datePay;
                    var str1 = "<tr class=\"gradeA\"><td>"+colume1+"</td><td>"+colume2+"</td><td>"+colume3+"</td><td>"+colume4+"</td><td>"+colume5+"</td><td>"+colume6+"</td><td>"+colume7+"</td></tr>";
                    $("#historyPenalty").append(str1);
                     /* iterate through array or object */
                });
                $("#historyPenaltyTable").DataTable();
            }
        },
        error:function() {
            alert("historyPage.do执行失败");
        }
    });
}
function rreborrowdo(rbid) {
    $.ajax({
        url: '/lms/rreborrow.do',
        type: 'GET',
        dataType: 'json',
        contentType:'application/json;charset=UTF-8',
        data:{rbid: rbid},
        success:function(msg) {
            alert(msg.reasult);
            currentPage();
        },
        error:function() {
            alert("续借执行失败！");
        }
    });
}
function rcancelReservationdo(rrid) {
    $.ajax({
        url: '/lms/rcancelReservation.do',
        type: 'GET',
        dataType: 'json',
        contentType:'application/json;charset=UTF-8',
        data:{rrid: rrid},
        success:function(msg) {
            alert(msg.reasult);
            currentPage();
        },
        error:function() {
            alert("取消预约执行失败！");
        }
    });
}
function collectionPage() {
    $.get('/lms/myCollection', function(data) {
        $("#myCollection").html("");
        if(data.rcSize == null) {
            $("#myCollectionAll").html("");
            $("#myCollectionAll").append("当前没有任何收藏！");
        } else {
            $.each(data.rcList, function(i, item) {
                var colume1 = i+1;
                var colume2 = item.infoBook.title;
                var colume3 = item.infoBook.author;
                var colume4 = "<a href=\"javascript:void(0)\" onclick=\"delCollection('"+item.id+"')\">删除</a>"
                var str1 = "<tr><td>"+colume1+"</td><td>"+colume2+"</td><td>"+colume3+"</td><td>"+colume4+"</td></tr>";
                $("#myCollection").append(str1);
            });
        }
    }, 'json');
}
function delCollection(rcid) {
    $.post('/lms/delCollection.do', {rcid: rcid}, function(data) {
        alert(data.result);
        collectionPage();
    }, 'json');
}
