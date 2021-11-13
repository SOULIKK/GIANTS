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

