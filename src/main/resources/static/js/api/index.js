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

const requestWithoutJson = async (url, option) => {
    const response = await fetch(url, option);
    if (!response.ok) {
        response.json().then((body) => alert(body.message));
        return;
    }
    return response;
}

const Api = {
    getBoardList(pdtId, page, sort_option) {
        return request(`${BASE_URL}/board/${pdtId}/${page}`+'?sort_option='+sort_option);
    },
    writeBoard(pdtId, board) {
        return requestWithoutJson(`${BASE_URL}/board/${pdtId}`, HTTP_METHOD.POST(board));
    },
    deleteBoard(pdtId, bbsId) {
        return requestWithoutJson(`${BASE_URL}/board/${pdtId}/${bbsId}`, HTTP_METHOD.DELETE());
    },
    updateBoard(pdtId, board) {
        return requestWithoutJson(`${BASE_URL}/board/${pdtId}`, HTTP_METHOD.PUT(board));
    },
    upBoardLike(pdtId, bbsId) {
        return requestWithoutJson(`${BASE_URL}/board/like/${pdtId}/${bbsId}`);
    },

}

export default Api;
