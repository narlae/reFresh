const BASE_URL = "http://localhost:8080"

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
    getBoardList(cat_code, page, sort_option) {
        return request(`${BASE_URL}/board/${cat_code}/${page}`+'?sort_option='+sort_option);
    },

}

export default Api;
