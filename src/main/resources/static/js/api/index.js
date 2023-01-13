const BASE_URL = "http://localhost:8080"

const HTTP_METHOD = {
    POST(data) {
        return {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(data),
        };
    },
    DELETE(){
        return {
            method: "DELETE",

        };
    },
    PUT(data) {
        return {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
            },
            body: data ? JSON.stringify(data) : null,
        };
    },

}

const request = async (url, option) => {
        const response = await fetch(url, option);
        if (!response.ok) {
            response.json().then((body) => alert(body.message));
            return;
        }
        return response.json();
}

const requestWithoutJson = async (url, option, message) => {
    const response = await fetch(url, option);
    if (!response.ok) {
        response.json().then((body) => alert(body.message));
        return;
    }
    if (message) {
        alert(message);
        return response;
    }
}

const Api = {
    getBoardList(pdtId, page, sort_option) {
        return request(`${BASE_URL}/board/${pdtId}/${page}`+'?sort_option='+sort_option);
    },
    writeBoard(pdtId, board) {
        return requestWithoutJson(`${BASE_URL}/board/${pdtId}`, HTTP_METHOD.POST(board));
    },
    deleteBoard(pdtId, bbsId) {
        return requestWithoutJson(`${BASE_URL}/board/${pdtId}/${bbsId}`, HTTP_METHOD.DELETE(), "글이 삭제되었습니다.");
    },
    updateBoard(pdtId, board) {
        return requestWithoutJson(`${BASE_URL}/board/${pdtId}`, HTTP_METHOD.PUT(board), "수정되었습니다.");
    },
    upBoardLike(pdtId, bbsId) {
        return requestWithoutJson(`${BASE_URL}/board/like/${pdtId}/${bbsId}`, null,"추천완료");
    },
    getHomeProducts() {
        return request(`${BASE_URL}/home`);
    },

}

export default Api;
