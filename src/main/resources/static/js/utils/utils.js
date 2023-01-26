export const getLoginForm = () =>{
    if (!confirm("로그인이 필요합니다. 하시겠습니까?")) {
        return;
    }
    const toURI = window.location.pathname;
    window.parent.location.href = "/login?toURL="+ toURI;
}

export const searchParam = (key) => {
    return new URLSearchParams(location.search).get(key);
};

export const pageGoPost = (m) =>{
    let temp = "";

    for (let i = 0; i < m.items.length; i++) {
        temp+= "<input type='hidden' name='"+ m.items[i][0] +"' value='"+ m.items[i][1] +"'>";
    }
    let form = document.createElement('form');
    form.action = m.url;
    form.method = "POST";
    form.innerHTML = temp;
    document.body.append(form);
    form.submit();
}