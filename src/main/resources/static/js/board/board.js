import Api from "../api/index.js";
import {$} from "../utils/dom.js";

function App(){

    this.list = {
        page : 1,
        pdtId : 1,
        sort_option : 'bbsId',

    }
    this.board ={};
    this.page={};

    this.init = async () => {
        await render();
        initEventListeners();
    }

    const render = async () => {
        if (searchParam('page') !== null) {
            this.list.page = searchParam('page');
        }
        this.page = await Api.getBoardList(this.list.pdtId, this.list.page - 1, this.list.sort_option);
        const template =  this.page.content.map((item)=> {

            return `
        <div class="tr1">
            <div id="no" class="th">${item.bbsId}</div>
            <div id="title" class="th" data-bbsId ="${item.bbsId}">
                <div class="title_btn" ><dt class="title_cn">${item.bbsTitle}</dt></div>
                <div id="review_view" class="review_in" style="display: none" data-open=close>
                    <div>
                        <div class="review_content">${item.bbsCn}</div>
                    </div>
                    <div id="buttons">
                        <p class="mod_btn">수정</p>
                        <p class="del_btn">삭제</p>
                        <p class="like_button">추천</p>
                    </div>
                </div>
            </div>
            <div id="grade" class="th" ></div>
            <div id="writer" class="th">${item.userNm}</div>
            <div id="reg_date" class="th">${item.wrtDt}</div>
            <div id="like_cnt" class="th">${item.revwLike}</div>
        </div>
        `;
        }).join("");

        $("#board").innerHTML = template;

        const pagingNav = () => {
            let temp = '';
            let page = this.list.page;
            let naviSize = 10;
            let beginPage = parseInt((page - 1 )/ naviSize) * naviSize + 1;
            let totalPage = this.page.totalPages;
            let endPage = Math.min(beginPage + naviSize - 1, totalPage);
            if (!this.page.empty) {
                if (beginPage !=1) {
                    temp += `<a class="page" href="/board?page=${beginPage-1}"><</a>`
                }
                for (let i = beginPage; i <= endPage; i++) {
                    temp += `<a class="page ${i==page ? 'paging-active' : ''}" href="/board?page=${i}" >${i}</a>`
                }
                if (totalPage != endPage) {
                    temp += `<a class="page" href="/board?page=${endPage+1}">></a>`
                }

            }
            else{
                temp += `<div>게시물이 없습니다.</div>`;
            }

            return temp;
        };
        $(".paging").innerHTML = pagingNav();

    }

    const initEventListeners = () => {
        $("#board").addEventListener("click", async (e) =>{
            if (e.target.classList.contains("title_cn")) {
                openCn(e)
                return;
            }

            if (e.target.classList.contains("del_btn")) {
                if (!confirm("정말로 글을 삭제하시겠습니까?")) {
                    return;
                }
                deleteBoard(e);
            }

            if (e.target.classList.contains("mod_btn")) {

                this.board['bbsTitle'] = e.target.closest("#title").querySelector(".title_cn").innerText;
                this.board['bbsCn'] = e.target.closest("#title").querySelector(".review_content").innerText;
                this.board['bbsId'] = e.target.closest("#title").dataset.bbsid;
                $("#bbs_title").value = this.board.bbsTitle;
                $("#contents").value = this.board.bbsCn;

                $("#modal-btn").className = 'btn-modify';
                openModal();
            }
        });

        $(".border_write_btn").addEventListener("click",(e) => {
            if (e.target.classList.contains("p_write_btn")) {
                $("#modal-btn").className = 'btn-write';
                openModal();
                return;
            }
        });

        $(".modal-footer").addEventListener("click", async (e) => {
            if (e.target.classList.contains("btn-cancel")) {
                closeModal();
                return;
            }
            if (e.target.classList.contains("btn-write")) {
                sendBoard();
            }

            if (e.target.classList.contains("btn-modify")) {
                this.board['bbsTitle'] =  $("#bbs_title").value;
                this.board['bbsCn'] = $("#contents").value;
                await Api.updateBoard(this.list.pdtId, this.board);
                alert("수정되었습니다.");
                closeModal();
                await render();
                $("#bbs_title").value = '';
                $("#contents").value = '';
            }

        });

    }


    const sendBoard = async () => {
        let board = {};

        board['bbsTitle'] = $("#bbs_title").value;
        board['bbsCn'] = $("#contents").value;

        await Api.writeBoard(this.list.pdtId, board);
        closeModal();
        await render();
        $("#bbs_title").value = '';
        $("#contents").value = '';
    }

    const deleteBoard = async (e) => {
        const bbsId = e.target.closest("#title").dataset.bbsid;

        await Api.deleteBoard(this.list.pdtId, bbsId);
        alert("글이 삭제되었습니다.");
        await render();
    }

    const openModal = () =>{
        $(".modal").classList.remove('hide');
    }

    const closeModal = () => {
        $(".modal").classList.add('hide');
    }

    const openCn = (e) =>{
        const $reviewCn = e.target.closest("#title").querySelector("#review_view");
        if ($reviewCn.dataset.open == 'close') {
            $reviewCn.style.display = 'block';
            $reviewCn.dataset.open = 'open';
        } else {
            $reviewCn.style.display = 'none';
            $reviewCn.dataset.open = 'close';
        }
    }

    function searchParam(key) {
        return new URLSearchParams(location.search).get(key);
    };

}

const app = new App();
app.init();

