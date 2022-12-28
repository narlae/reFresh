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
        page : 0,
        cat_code : 0,
        sort_option : 'bbs_id',
    }
    this.page={};

    this.init = async () => {
        render();
        initEventListeners();
    }

    const render = async () => {
        this.page = await Api.getBoardList(this.list.cat_code, this.list.page, this.list.sort_option);
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


    }

    const initEventListeners = () => {

    }

}

const app = new App();
app.init();
