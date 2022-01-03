function delBoard(boardId) {
    if (!confirm("삭제된 글은 복구할 수 없습니다. 삭제하시겠습니까?")) {
        return false;
    } else {
        $.ajax({
            type: "DELETE",
            url: "/board/delete",
            data: {
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
            if ($("#likeBtn").hasClass("btn-outline-danger")) {
                $("#likeBtn").removeClass("btn-outline-danger").addClass("btn-outline-secondary");
            } else {
                $("#likeBtn").removeClass("btn-outline-secondary").addClass("btn-outline-danger");
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

                    if (username != results[i].username) {
                        str += "<tr><td class='w-50'>";
                        str += "<div class='p-2' style='border-radius: .75rem; background: #f1f1f1;'>";
                        str += "<div class='text-primary fw-bold p-1'>"+results[i].username+"</div>";
                        str += "<div class='py-2'>"+results[i].content+"</div>";
                        str += "<div class='d-flex justify-content-between text-secondary'>";
                        str += "<div>"+results[i].createdAt+"</div>";
                        str += "</div></div></td>";
                        str += "<td></td>";
                        str += "</tr>";
                    } else {
                        str += "<tr><td class='w-50'></td><td id='comment_"+results[i].commentId+"'>";
                        str += "<div class='p-2' style='border-radius: .75rem; background: #f1f1f1;'>";
                        str += "<div class='text-primary fw-bold p-1'>"+results[i].username+"</div>";
                        str += "<div class='py-2'>"+results[i].content+"</div>";
                        str += "<div class='d-flex justify-content-between text-secondary'>";
                        str += "<div>"+results[i].createdAt+"</div>";
                        str += "<div><button type='button' class='btn p-0 text-secondary' onclick='delEpComment("+epId+", "+results[i].commentId+")'><i class='fa fa-trash'></i></button></div>";
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

function delEpComment(epId, commentId) {

    const comment_td = $("#comment_"+commentId);
    comment_td.remove();

    $.ajax({
        type: 'DELETE',
        data: {
            epId: epId,
            commentId: commentId
        },
        url: '/comment/ep',
        dataType: 'JSON',
        success: function(results) {

        }

    })
}

function sendCert() {

    const email = $("#username").val();

    $.ajax({
        type: "POST",
        url: "/cert/send",
        data: {
            email: email
        },
        success(res) {
            alert(email+" 로 인증메일이 발송됐습니다. 메일을 확인해주세요.");
            $("#mail").html("재발송");
            $("#chkForm").removeClass("d-none");

        }
    })
}


function chkCert() {

    const email = $("#username").val();
    const certKey = $("#certKey").val();

    $.ajax({
        type: "POST",
        url: "/cert/check",
        data: {
            certKey: certKey,
            email: email
        },
        success(res) {
            if (res == true) {
                alert("인증이 완료됐습니다.");
                $('#join').attr('disabled', false);
            } else {
                alert("인증번호가 일치하지 않습니다.");
            }

        }

    })

}