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
        alert("에러가 발생했습니다.");
        console.error(e);
    }
    return response.json();
}

const requestWithoutJson = async (url, option) => {
    const response = await fetch(url, option);
    if (!response.ok) {
        alert("에러가 발생했습니다.");
        console.error(e);
    }
    return response;
}

const Api = {
    getBoardList(pdt_id, page, sort_option) {
        return request(`${BASE_URL}/board/${pdt_id}/${page}`+'?sort_option='+sort_option);
    },
    writeBoard(pdt_id, board) {
        return requestWithoutJson(`${BASE_URL}/board/${pdt_id}`, HTTP_METHOD.POST(board));
    },
    deleteBoard(pdt_id, bbs_id) {
        return requestWithoutJson(`${BASE_URL}/board/${pdt_id}/${bbs_id}`, HTTP_METHOD.DELETE());
    },
    updateBoard(pdt_id, board) {
        return requestWithoutJson(`${BASE_URL}/board/${pdt_id}`, HTTP_METHOD.PUT(board));
    },

}

export default Api;
