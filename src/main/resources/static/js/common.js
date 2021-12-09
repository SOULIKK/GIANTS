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

