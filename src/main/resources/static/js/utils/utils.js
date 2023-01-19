export
const getLoginForm = () =>{
    if (!confirm("로그인이 필요합니다. 하시겠습니까?")) {
        return;
    }
    const toURI = window.location.pathname;
    window.parent.location.href = "/login?toURL="+ toURI;
}

export const searchParam = (key) => {
    return new URLSearchParams(location.search).get(key);
};