// noinspection JSIgnoredPromiseFromCall

import router from '@/router'
import {useRoute} from "vue-router";

export function back() {
    router.push('/')
}

export function toMain() {
    router.push('/')
}

export function toAttention() {
    router.push('/attention')
}

export function toPublishArticle() {
    router.push('/publishArticle')
}

export function toLogin() {
    router.push('/login')
}

export function toNotification() {
    router.push('/notification')
}

export function toChat() {
    router.push('/chat')
}

export function toFavorites() {
    router.push('/favorites')
}

export function toMy(userId: number) {
    router.push({name: 'My', params: {userId: userId}})
}

export function toArticleDetails(articleId: any) {
    router.push({name: 'ArticleDetails', params: {articleId: articleId}})
}

export function toTagDetails() {
    router.push('/tagDetails')
}

// 判断当前是否已登录
export function isLogin() {
    let access = localStorage.getItem('access_token')
    return !(access === null || access === undefined || access === '');
}

// 判断当前页面是否为文章列表页
export function isArticleListView() {
    return useRoute().path === "/";
}

// 判断当前是否在发帖页面
export function isPublish() {
    return useRoute().path === "/publishArticle";
}
