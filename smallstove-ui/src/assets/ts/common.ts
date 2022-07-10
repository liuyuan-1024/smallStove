// noinspection JSIgnoredPromiseFromCall

import router from '@/router'
import {useRoute} from "vue-router";

function toArticleDetails(articleId: any) {
    router.push({name: 'ArticleDetails', params: {articleId: articleId}})
}

function toMy(userId: number) {
    router.push({name: 'My', params: {userId: userId}})
}

function toTagDetails() {
    router.push('/tagDetails')
}

// 判断当前是否已登录
const isLogin = () => {
    let access = localStorage.getItem('access_token')
    return !(access === null || access === undefined || access === '');
}

// 判断当前页面是否为文章列表页
const isArticleListView = () => {
    return useRoute().path === "/";
}

// 判断当前是否在发帖页面
const isPublish = () => {
    return useRoute().path === "/publishArticle";
}

export {toArticleDetails, toMy, toTagDetails, isLogin, isArticleListView, isPublish}
