import Api from "../api/index.js";
import {$} from "../utils/dom.js";

/**
 * Board
 * Page render
 *
 * paging
 * 글 읽기
 */

function App(){

    this.list = {
        page : 1,
        cat_code : 0,
        sort_option : 'bbs_id',
    }
    this.page={};

    this.init = async () => {
        render();
        initEventListeners();
    }

    const render = async () => {
        this.list.page = searchParam('page');

        this.page = await Api.getBoardList(this.list.cat_code, this.list.page - 1, this.list.sort_option);
        const template =  this.page.content.map((item)=> {

            return `<table class="tb1" width="100%">
        <colgroup>
        <col style="width:70px;">
        <col style="width:auto;">
        <col style="width:51px;">
        <col style="width:93px;">
        <col style="width:85px;">
        <col style="width:90px;">
        </colgroup>
        <tbody>
        <tr class="tr1">
        <td class="no">${item.bbs_id}</td>
        <td class="title">
        <div class="title_btn" data-bbs_id ="${item.bbs_id}"><dt class="title_cn">${item.bbs_title}</dt></div>
        </td>
        <td class="grade" ></td>
        <td class="writer">${item.user_nm}</td>
        <td class="reg_date">${item.wrt_dt}</td>
        <td class="like_cnt">${item.revw_like}</td>
        </tr>
        </tbody>
        </table>`;
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

    }

    function searchParam(key) {
        return new URLSearchParams(location.search).get(key);
    };

}

const app = new App();
app.init();
