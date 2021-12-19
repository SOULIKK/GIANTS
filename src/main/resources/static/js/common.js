function delBoard(stockId, boardId) {
    if (!confirm("삭제된 글은 복구할 수 없습니다. 삭제하시겠습니까?")) {
        return false;
    } else {
        $.ajax({
            type: "POST",
            url: `/board/delete`,
            data: {
                stockId: stockId,
                boardId: boardId
            },
            success: function (res) {
                location.href = `list?stockId=${stockId}`;
            }
        })
    }
}

function like(stockId, boardId) {
    $.ajax({
        type: "POST",
        url: `/board/like`,
        data: {
            s: stockId,
            b: boardId
        },
        success: function (res) {
            const cnt = res;
            alert(res);
            // alert(res);
            if ($("#likeBtn").hasClass("btn-outline-danger border border-danger")) {
                $("#likeBtn").removeClass("btn-outline-danger border border-danger").addClass("btn-outline-danger border border-secondary");
                $("#likeBtn i, #likeBtn span").removeClass("text-danger").addClass("text-secondary");
            } else {
                $("#likeBtn").removeClass("btn-outline-danger border border-secondary").addClass("btn-outline-danger border border-danger");
                $("#likeBtn i, #likeBtn span").removeClass("text-secondary").addClass("text-danger");
            }
            $("#likeCnt").text(res);
        }
    })
}

function bookMark(epId) {
    $.ajax({
        type: "POST",
        url: `/bookmark`,
        data: {
            epId: epId
        },
        success: function (res) {
            const isBookMarked = res;
            if ($(`#bookMarkBtn_${epId} i`).hasClass("far")) {
                $(`#bookMarkBtn_${epId} i`).removeClass("far").addClass("fa");
            } else {
                $(`#bookMarkBtn_${epId} i`).removeClass("fa").addClass("far");
            }
        }
    })
}

function delComment(stockId, boardId, commentId) {
    $.ajax({
        type: "POST",
        url: `/comment/delete`,
        data: {
            stockId: stockId,
            boardId: boardId,
            commentId: commentId
        },
        success: function (res) {
            location.reload();
            // alert(res.stockId);
        }
    })
}

function writeBoard(stockId) {
    location.href = `/board/write?stock=${stockId}`;
}

function noAuth() {
    if (!confirm("로그인이 필요합니다. 로그인 하시겠습니까?")) {
        return false;
    } else {
        location.href = '/auth/login';
    }
}

function corpAnalisys(type, stockCode) {
    const link = "";
    if (type == "naver") {
        const link = 'https://finance.naver.com/item/main.naver?code=' + stockCode;
        window.open(link, "_blank", "toolbar=yes,scrollbars=yes,resizable=yes,top=50,left=50,width=1024,height=768");
    } else if (type == "fnguide") {
        const link = 'https://comp.fnguide.com/SVO2/ASP/SVD_main.asp?MenuYn=Y&gicode=A' + stockCode;
        window.open(link, "_blank", "toolbar=yes,scrollbars=yes,resizable=yes,top=50,left=50,width=1024,height=768");
    } else if (type == "irgo") {
        const link = 'https://m.irgo.co.kr/IR-COMP/' + stockCode;
        window.open(link, "_blank", "toolbar=yes,scrollbars=yes,resizable=yes,top=50,left=50,width=1024,height=768");
    } else if (type == 'home') {
        window.open(stockCode, "_blank", "toolbar=yes,scrollbars=yes,resizable=yes,top=50,left=50,width=1024,height=768");
    }

}



function stockResearch(stockName) {

    const stock = encodeURIComponent(stockName);
    alert(stock);

    $.ajax({
        url: "http://consensus.hankyung.com/apps.analysis/analysis.list",
        type: "GET",
        contentType: 'application/x-www-form-urlencoded;charset=euc-kr',
        data: {
            search_text: stock,
            sdate: "2021-01-01",
            edate: "2021-12-16",
            pagenum: 80
        },
        success: function() {
            window.open(link, '_blank');
            win.focus();
        }
    })


}

function getComments(epId, username) {

    $.ajax({
        type: 'POST',
        dataType: 'JSON',
        data: {
            epId: epId
        },
        url: '/comment/ep',
        success: function(results) {
            const modal_body = $("#m_b_"+epId);
            const comment_table = $("#t_"+epId)[0];

            if (comment_table) {
                return;
            } else {
                var str = "";
                $.each(results , function(i) {

                    str += "<table id='t_"+epId+"' class='w-100 m-auto'><tbody>";

                    if (username != results[i].user.username) {
                        str += "<tr><td class='w-50'>";
                        str += "<div class='p-2' style='border-radius: .75rem; background: #f1f1f1;'>";
                        str += "<div class='text-primary fw-bold p-1'>"+results[i].user.username+"</div>";
                        str += "<div class='py-2'>"+results[i].content+"</div>";
                        str += "<div class='d-flex justify-content-between text-secondary'>";
                        str += "<div>"+results[i].createdAt+"</div>";
                        str += "</div></div></td>";
                        str += "<td></td>";
                        str += "</tr>";
                    } else {
                        str += "<tr><td class='w-50'></td><td>";
                        str += "<div class='p-2' style='border-radius: .75rem; background: #f1f1f1;'>";
                        str += "<div class='text-primary fw-bold p-1'>"+results[i].user.username+"</div>";
                        str += "<div class='py-2'>"+results[i].content+"</div>";
                        str += "<div class='d-flex justify-content-between text-secondary'>";
                        str += "<div>"+results[i].createdAt+"</div>";
                        str += "<div><button type='button' class='btn p-0 text-secondary'><i class='fa fa-trash'></i></button></div>";
                        str += "</div></div></td>";
                        str += "</tr>";
                    }
                    str += "</tr></tbody></table>";
                });
                modal_body.append(str);
            }


        }
    })

}

